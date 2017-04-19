package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.GoodsShelvesMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsShelvesVo;
import com.jusfoun.hookah.core.domain.vo.OptionalShelves;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.GoodsShelvesService;
import com.jusfoun.hookah.rpc.api.MgGoodsShelvesGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author sdw
 */
public class GoodsShelvesServiceImpl extends GenericServiceImpl<GoodsShelves, String> implements GoodsShelvesService {
    @Resource
    private GoodsShelvesMapper goodsShelvesMapper;

    @Resource
    private MgGoodsShelvesGoodsService mgGoodsShelvesGoodsService;

    @Resource
    private GoodsService goodsService;

    @Resource
    public void setDao(GoodsShelvesMapper goodsShelvesMapper) {
        super.setDao(goodsShelvesMapper);
    }

    @Override
    public GoodsShelves addGoodsShelves(GoodsShelves goodsShelves) {
        return super.insert(goodsShelves);
    }

    @Override
    public Map<String,GoodsShelvesVo> getShevlesGoodsVoList(Map<String, Object> params) {
        //查询有效货架集合
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("shelvesStatus", 1));
        List<GoodsShelves> shelfs =  super.selectList(filters);

        Map<String,GoodsShelvesVo> goodsShelvesVoMap = new HashMap<String,GoodsShelvesVo>();
        if(Objects.nonNull(shelfs) && shelfs.size() != 0 ){
            for (GoodsShelves goods : shelfs){
                //查询货架下的商品Id集合
                String shelvesId = goods.getShelvesId();
                MgShelvesGoods mgShelvesGoods = mgGoodsShelvesGoodsService.selectById(shelvesId);

                //获取货架商品集合注入到GoodsShelvesVo
                GoodsShelvesVo goodsShelvesVo = new GoodsShelvesVo();
                BeanUtils.copyProperties(goods, goodsShelvesVo);
                if(Objects.nonNull(mgShelvesGoods)){
                    List<String> sgIds = mgShelvesGoods.getGoodsIdList();
                    if(Objects.nonNull(sgIds) && sgIds.size() != 0 ){
                        //商品Id集对应的商品集
                        List<Condition> goodsfilters = new ArrayList<Condition>();
                        goodsfilters.add(Condition.in("goodsId",sgIds.toArray()));
                        List<Goods> goodsList = goodsService.selectList(goodsfilters);
                        goodsShelvesVo.setGoods(goodsList);
                    }
                }
                //把货架标签按逗号拆分拼装
                String shelvesTag = goods.getShelvesTag();
                goodsShelvesVo.setShelvesTagList(str2StrArray(shelvesTag,"[,，]+"));

                goodsShelvesVoMap.put(goods.getShelvesType(),goodsShelvesVo);
            }
        }
        return goodsShelvesVoMap;
    }


    @Override
    public ReturnData<GoodsShelvesVo> findByShevlesGoodsVoId(String shevlesGoodsVoId) {
        ReturnData<GoodsShelvesVo> returnData = new ReturnData<GoodsShelvesVo>();
        returnData.setCode(ExceptionConst.Success);

        if(StringUtils.isBlank(shevlesGoodsVoId)){
            returnData.setCode(ExceptionConst.InvalidParameters);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.InvalidParameters));
            return returnData;
        }

        GoodsShelves goodsShelves = super.selectById(shevlesGoodsVoId);
        if(Objects.nonNull(goodsShelves)){
            //获取货架商品集合注入到GoodsShelvesVo
            GoodsShelvesVo goodsShelvesVo = new GoodsShelvesVo();
            BeanUtils.copyProperties(goodsShelves, goodsShelvesVo);

            MgShelvesGoods mgShelvesGoods = mgGoodsShelvesGoodsService.selectById(shevlesGoodsVoId);
            if(Objects.nonNull(mgShelvesGoods)){
                List<String> sgIds = mgShelvesGoods.getGoodsIdList();
                if(Objects.nonNull(sgIds) && sgIds.size() != 0 ){
                    //商品Id集对应的商品集
                    List<Condition> goodsfilters = new ArrayList<Condition>();
                    goodsfilters.add(Condition.in("goodsId",sgIds.toArray()));
                    List<Goods> goodsList = goodsService.selectList(goodsfilters);
                    goodsShelvesVo.setGoods(goodsList);
                }
            }

            //把货架标签按逗号拆分拼装
            goodsShelvesVo.setShelvesTagList(str2StrArray(goodsShelves.getShelvesTag(),"[,，]+"));

            returnData.setData(goodsShelvesVo);
        }else{
            returnData.setMessage(ExceptionConst.get(ExceptionConst.InvalidParameters));
        }
        return returnData;
    }

    @Override
    public ReturnData findGoodsByShevlesId(String shevlesId, String pageNumber, String pageSize) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        if(StringUtils.isBlank(shevlesId)){
            returnData.setCode(ExceptionConst.InvalidParameters);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.InvalidParameters));
            return returnData;
        }


        MgShelvesGoods mgShelvesGoods = mgGoodsShelvesGoodsService.selectById(shevlesId);
        if(Objects.nonNull(mgShelvesGoods)){

            try {
                //查询mg里货架内的商品Id集合
                List<String> sgIds = mgShelvesGoods.getGoodsIdList();

                Pagination<Goods> page = new Pagination<>();
                List<Condition> filters = new ArrayList();
                filters.add(Condition.eq("isDelete", 1));
                //从查询到的Id集合里查询商品
                if(Objects.nonNull(sgIds) && sgIds.size() != 0 ){
                    filters.add(Condition.in("goodsId",sgIds.toArray()));
                }else{
                    return returnData;
                }

                //参数校验
                int pageNumberNew = HookahConstants.PAGE_NUM;
                if (StringUtils.isNotBlank(pageNumber)) {
                    pageNumberNew = Integer.parseInt(pageNumber);
                }
                int pageSizeNew = HookahConstants.PAGE_SIZE;
                if (StringUtils.isNotBlank(pageSize)) {
                    pageSizeNew = Integer.parseInt(pageSize);
                }

                List<OrderBy> orderBys = new ArrayList();
                orderBys.add(OrderBy.desc("lastUpdateTime"));

                page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
                returnData.setData(page);
            } catch (Exception e) {
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage(e.toString());
                e.printStackTrace();
            }

        }else{
            returnData.setMessage(ExceptionConst.get(ExceptionConst.InvalidParameters));
        }
        return returnData;
    }

    @Override
    public ReturnData<List<Map<String, GoodsShelves>>> selectAllShelf() {
        ReturnData<List<Map<String, GoodsShelves>>> returnData = new ReturnData<List<Map<String, GoodsShelves>>>();
        returnData.setCode(ExceptionConst.Success);

        List<GoodsShelves> shelfs =  super.selectList();
        if(Objects.nonNull(shelfs) && shelfs.size() != 0 ){
            List<Map<String, GoodsShelves>> GoodsShelfVOList = new ArrayList<Map<String, GoodsShelves>>();
            for (GoodsShelves goods : shelfs){
                Map<String,GoodsShelves> shelfMap = new HashMap<String,GoodsShelves>();
                shelfMap.put(goods.getShelvesName(),goods);
                GoodsShelfVOList.add(shelfMap);
            }
            returnData.setData(GoodsShelfVOList);
        }

        return returnData;
    }

    /**
     * @Title: str2StrArray
     * @Description:  按照正则表达式拆分数字字符串转成字符数组
     * @param target
     * @param regex
     * @return
     */
    public static String[] str2StrArray(String target,String regex){
        String[] strArray = null;
        if(null != target && target.length() > 0){
            strArray = target.split(regex);
        }
        return strArray;
    }


}
