package com.jusfoun.hookah.console.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.console.server.util.PropertiesManager;
import com.jusfoun.hookah.core.Md5Utils;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.ChannelTransData;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.ChannelDataVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.*;
import com.jusfoun.hookah.rpc.api.ChannelService;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by ctp on 2017/8/30.
 */
@Service
public class ChannelServiceImpl extends GenericServiceImpl<Goods,String> implements ChannelService{

    private static final Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    public void setDao(GoodsMapper goodsMapper) {
        super.setDao(goodsMapper);
    }

    @Resource
    UserService userService;

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    MqSenderService mqSenderService;

    @Override
    public ReturnData acceptGoods(ChannelTransData channelTransData) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        //参数校验
        if(Objects.isNull(channelTransData) || StringUtils.isBlank(channelTransData.getCheckCode()) ||
                StringUtils.isBlank(channelTransData.getTransData()) || Objects.isNull(channelTransData.getTimestamp())){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try {
            String checkCode = channelTransData.getCheckCode();
            String transData = channelTransData.getTransData();
            Long timestamp = channelTransData.getTimestamp();

            //获取解密后的数据
            String data = deCrypt(timestamp,transData);

            //检查是否篡改
            if(checkDataIsTamper(checkCode,data)){
                //校验用户的合理性
                if(checkUserLegal(data)){
                    pushOrRepalData(data);
                } else {
                    returnData.setCode(ExceptionConst.Failed);
                    returnData.setMessage("没有权限操作!");
                    logger.warn("没有权限操作!");
                }
            } else {
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage("数据已被篡改!");
                logger.warn("数据已被篡改!");
            }
        }catch (HookahException e) {
            returnData.setCode(ExceptionConst.Failed);

            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    //处理数据
    private void pushOrRepalData(String deCryptData) throws HookahException{
        if(StringUtils.isNotEmpty(deCryptData) && !"".equals(deCryptData)){
            ChannelTransData.RelationData relationData = JSON.parseObject(deCryptData, ChannelTransData.RelationData.class);
            List<GoodsVo> goodsVoList = getGoodsDataList(deCryptData);

            //撤回
            if(relationData.getOpera() == HookahConstants.CHANNEL_PUSH_OPER_CANCEL){
                repealGoods(goodsVoList);
            } else if(relationData.getOpera() == HookahConstants.CHANNEL_PUSH_OPER_PUSH){//推送
                pushGoods(goodsVoList);
            }
        }
    }

    //接收推送
    private void pushGoods(List<GoodsVo> goodsVoList) throws HookahException{
        if(null != goodsVoList && goodsVoList.size() > 0){
            for (GoodsVo goodsVo : goodsVoList) {

                String goodsId = goodsVo.getGoodsId();
                String goodsSn = goodsVo.getGoodsSn();

                List<Condition> filters = new ArrayList<Condition>();
                filters.add(Condition.eq("goodsSn",goodsSn));
                filters.add(Condition.eq("goodsId",goodsId));
                List<Goods> goodsList = this.selectList(filters);
                //存在更新  不存在插入
                Date date = DateUtils.now();
                goodsVo.setIsOnsale(HookahConstants.GOODS_STATUS_OFFSALE);//下架状态
                goodsVo.setCheckStatus(HookahConstants.GOODS_CHECK_STATUS_YES);//审核通过
                goodsVo.setIsLocal(HookahConstants.IS_LOCAL_NO);//不是本地商品
                if(goodsVo.getOnsaleStartDate() == null) {
                    goodsVo.setOnsaleStartDate(date);
                }
                goodsVo.setAddTime(date);
                goodsVo.setLastUpdateTime(date);


                // 将数据放入mongo
                MgGoods mgGoods = new MgGoods();

                //结算价改为代理价 代理价改为null
                List<MgGoods.FormatBean> formatBeanList = goodsVo.getFormatList();
                if(null != formatBeanList && formatBeanList.size() > 0){
                    for(MgGoods.FormatBean formatBean : formatBeanList){
                        formatBean.setSettlementPrice(formatBean.getAgencyPrice());
                        formatBean.setAgencyPrice(null);
                    }
                }

                mgGoods.setAttrTypeList(goodsVo.getAttrTypeList());
                mgGoods.setFormatList(formatBeanList);
                mgGoods.setImgList(goodsVo.getImgList());
                mgGoods.setApiInfo(goodsVo.getApiInfo());
                mgGoods.setAsAloneSoftware(goodsVo.getAsAloneSoftware());
                mgGoods.setAsSaaS(goodsVo.getAsSaaS());
                mgGoods.setAtAloneSoftware(goodsVo.getAtAloneSoftware());
                mgGoods.setAtSaaS(goodsVo.getAtSaaS());
                mgGoods.setDataModel(goodsVo.getDataModel());
                mgGoods.setClickRate((long) 0);
                mgGoods.setOffLineData(goodsVo.getOffLineData());
                mgGoods.setOffLineInfo(goodsVo.getOffLineInfo());

                if(null != goodsList && goodsList.size() > 0){
                    this.updateByConditionSelective(goodsVo,filters);
                    mgGoods.setGoodsId(goodsVo.getGoodsId());
                    mongoTemplate.save(mgGoods);
                } else {
                    // 将数据插入数据库
                    goodsVo = (GoodsVo)super.insert(goodsVo);
                    if(goodsVo == null)
                        throw new HookahException("操作失败");
                    mgGoods.setGoodsId(goodsVo.getGoodsId());
                    mongoTemplate.insert(mgGoods);
                }
            }
        }
    }

    //接收撤回
    private void repealGoods(List<GoodsVo> goodsVoList) throws HookahException{
        if(null != goodsVoList && goodsVoList.size() > 0){
            for(GoodsVo goodsVo : goodsVoList){
                String goodsId = goodsVo.getGoodsId();
                String goodsSn = goodsVo.getGoodsSn();

                List<Condition> filters = new ArrayList<Condition>();
                filters.add(Condition.eq("goodsSn",goodsSn));
                List<Goods> goodsList = this.selectList(filters);
                if(null != goodsList && goodsList.size() > 0){
                    //下架查询到的数据
                    List<Condition> goodsFifters = new ArrayList<>();
                    goodsFifters.add(Condition.eq("goodsId",goodsId));
                    goodsFifters.add(Condition.eq("goodsSn",goodsSn));
                    Goods updateGoods = new Goods();
                    updateGoods.setGoodsId(goodsId);
                    updateGoods.setIsOffline(HookahConstants.GOODS_STATUS_OFFSALE);
                    int count = this.updateByConditionSelective(updateGoods,goodsFifters);
                    //推送es 更新索引
                    if(count > 0) {
                        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goodsId);
                    }
                } else {
                    throw new HookahException("地方数据不存在");
                }
            }
        }
    }

    //解密
    private static String deCrypt(Long timestamp, String transData){
        String skey = PropertiesManager.getInstance().getProperty("platformCode") + HookahConstants.CHANNEL_KEY;
        skey += String.valueOf(timestamp).substring(16 - skey.length());
        String data = DESUtils.dePass(transData, skey);
        return data;
    }

    //校验数据是否被篡改
    private boolean checkDataIsTamper(String checkCode,String deCryptData){
        return checkCode != null && checkCode.equals(Md5Utils.encoderByMd5(SHAUtils.encryptSHA(deCryptData)));
    }

    //检查用户的合法性
    private Boolean checkUserLegal(String deCryptData){
        Boolean userFlag = false;
        if(StringUtils.isNotEmpty(deCryptData) && !"".equals(deCryptData)){
            ChannelTransData.RelationData relationData = JSON.parseObject(deCryptData, ChannelTransData.RelationData.class);
            String userId = relationData.getUserId();
            String pwd = relationData.getPwd();
            if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(pwd)){
                List<Condition> filters = new ArrayList<Condition>();
                filters.add(Condition.eq("userName",userId));
                filters.add(Condition.eq("password",pwd));
                List<User> users = userService.selectList(filters);
                if(Objects.nonNull(users) && users.size() > 0){
                    userFlag = true;
                }
            }
        }
        return userFlag;
    }

    //获取goodsVoList
    private List<GoodsVo> getGoodsDataList(String deCryptData){
        List<GoodsVo> goodsVoList = new ArrayList<GoodsVo>();
        if(StringUtils.isNotEmpty(deCryptData) && !"".equals(deCryptData)){
            goodsVoList = JSON.parseArray(JSON.parseObject(deCryptData).getString("data"),GoodsVo.class);
        }
        return goodsVoList;
    }

    public static void main(String[] aaa){

        List<GoodsVo> goodsVoList = new ArrayList<>();
        System.out.println(goodsVoList.getClass().getSimpleName());

        String transData = "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\r\nyNmniWagtxOT";
        Long timestamp = 1503988334733l;

        String data = deCrypt(timestamp,transData);

        System.out.println(deCrypt(timestamp,transData));
        ChannelTransData.RelationData<List<GoodsVo>> relationData = new ChannelTransData.RelationData<List<GoodsVo>>();
        relationData = JSON.parseObject(data,relationData.getClass());
//        System.out.println("json Array:" + JSONArray.parseArray(transData));
        System.out.println(relationData.getData());
        List<GoodsVo> goodsVoList1 = JSON.parseArray(JSON.parseObject(data).getString("data"),GoodsVo.class);

        System.out.println(goodsVoList1);
    }
}
