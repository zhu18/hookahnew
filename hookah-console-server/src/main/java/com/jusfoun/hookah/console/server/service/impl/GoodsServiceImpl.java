package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class GoodsServiceImpl extends GenericServiceImpl<Goods, String> implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    MgGoodsService mgGoodsService;
    @Resource
    MqSenderService mqSenderService;

    @Resource
    public void setDao(GoodsMapper goodsMapper) {
        super.setDao(goodsMapper);
    }

    @Override
    @Transactional
    public void addGoods(GoodsVo obj) throws HookahException {
        if (obj == null)
            throw new HookahException("空数据！");
        // 将数据插入数据库
        obj = (GoodsVo)super.insert(obj);
        if(obj == null)
            throw new HookahException("操作失败");
        // 将数据放入mongo
        MgGoods mgGoods = new MgGoods();
        mgGoods.setAttrTypeList(obj.getAttrTypeList());
        mgGoods.setFormatList(obj.getFormatList());
        mgGoods.setImgList(obj.getImgList());
        mgGoods.setGoodsId(obj.getGoodsId());
        mgGoods.setApiInfo(obj.getApiInfo());
        mongoTemplate.insert(mgGoods);
        mqSenderService.sendDirect(RabbitmqQueue.CONTRACT_GOODSCHECK, obj.getGoodsId());
    }

    @Override
    @Transactional
    public void updateGoods(GoodsVo obj) throws HookahException {
        obj.setLastUpdateTime(DateUtils.now());
        int i = super.updateByIdSelective(obj);
        if(i < 1) {
            throw new HookahException("更新失败！");
        }else {
            // 将数据放入mongo
            MgGoods mgGoods = new MgGoods();
            mgGoods.setAttrTypeList(obj.getAttrTypeList());
            mgGoods.setFormatList(obj.getFormatList());
            mgGoods.setImgList(obj.getImgList());
            mgGoods.setGoodsId(obj.getGoodsId());
            mongoTemplate.save(mgGoods);
        }
        mqSenderService.sendDirect(RabbitmqQueue.CONTRACT_GOODSCHECK, obj.getGoodsId());
    }

    /**
     * 提供给购物车查询商品规格的接口
     * @param goodsId 商品id
     * @param formatId 商品规格id
     * @return
     */
    @Override
    public MgGoods.FormatBean getFormat(String goodsId, Integer formatId)  throws Exception {
        MgGoods mgGoods = mgGoodsService.selectById(goodsId);
        if (mgGoods == null) {
            throw new HookahException("未查到商品信息");
        }

        List<MgGoods.FormatBean> list = mgGoods.getFormatList();
        for(MgGoods.FormatBean format : list) {
            if(format.getFormatId() == formatId) {
                return format;
            }
        }
        throw new HookahException("未查到对应的商品规格");
    }

    /**
     * 商品下架/删除/上架
     * @param goodsId
     * @return
     */
    @Override
    public int updateGoodsStatus(String goodsId, String status) {
        int i = 0;
        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        switch (status) {
            case "del" :
                goods.setIsDelete(HookahConstants.GOODS_STATUS_DELETE);
                goods.setLastUpdateTime(DateUtils.now());
                i = super.updateByIdSelective(goods);
                if(i > 0) {
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goodsId);
                }
                break;
            case "offSale":
                i = goodsMapper.updateOffSale(goodsId);
                if(i > 0) {
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goodsId);
                }
                break;
        }
        return i;
    }

    @Override
    public int onsale(String goodsId, String dateTime) {
        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        if(StringUtils.isNotBlank(dateTime)) {
            goods.setOnsaleStartDate(DateUtils.getDate(dateTime));
        }
        int i = super.updateByIdSelective(goods);
        if (i > 0) {
            mqSenderService.sendDirect(RabbitmqQueue.CONTRACT_GOODSCHECK, goodsId);
        }
        return i;
    }


}
