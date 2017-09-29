package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.GoodsShelvesMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.domain.es.EsRange;
import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsCritVo;
import com.jusfoun.hookah.core.domain.vo.GoodsShelvesVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OptionalShelves;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.rpc.api.CommentService;
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
    private CommentService commentService;

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
                        goodsfilters.add(Condition.eq("isDelete", 1));
                        goodsfilters.add(Condition.eq("isOnsale", 1));
                        goodsfilters.add(Condition.eq("checkStatus", 1));
                        goodsfilters.add(Condition.in("goodsId",sgIds.toArray()));
                        List<Goods> goodsList = goodsService.selectList(goodsfilters);

                        //获取包含评价分的商品VO集合
                        List<GoodsVo> goodsVoList = bulidGoodsVoList(goodsList,sgIds);

                        goodsShelvesVo.setGoods(goodsVoList);
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

        GoodsShelvesVo goodsShelvesVo = buildGoodsShelveVo(shevlesGoodsVoId);
        if (null != goodsShelvesVo){
            returnData.setData(goodsShelvesVo);
        }else{
            returnData.setMessage(ExceptionConst.get(ExceptionConst.InvalidParameters));
        }
        return returnData;
    }

    @Override
    public ReturnData findGoodsByShevlesId(GoodsCritVo goodsCritVo) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        String shevlesId = goodsCritVo.getId();

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

                List<Condition> filters = new ArrayList();
                filters.add(Condition.eq("isDelete", 1));
                filters.add(Condition.eq("isOnsale", 1));
                filters.add(Condition.eq("checkStatus", 1));
                //从查询到的Id集合里查询商品
                if(Objects.nonNull(sgIds) && sgIds.size() != 0 ){
                    filters.add(Condition.in("goodsId",sgIds.toArray()));
                }else{
                    return returnData;
                }

                String orderField = goodsCritVo.getOrderField();
                String order = goodsCritVo.getOrder();

                List<OrderBy> orderBys = new ArrayList();
//                orderBys.add(OrderBy.desc("lastUpdateTime"));

                if(StringUtils.isNoneBlank(orderField) && !"".equals(orderField)){
                    if("asc".equalsIgnoreCase(order)){
                        orderBys.add(OrderBy.asc(orderField));
                    }else{
                        orderBys.add(OrderBy.desc(orderField));
                    }
                }

                EsRange esRange = goodsCritVo.getRange();
                if(Objects.nonNull(esRange)){
                    Long priceFrom = esRange.getPriceFrom();
                    Long pricTo = esRange.getPriceTo();

                    if(null != priceFrom && pricTo !=null && (!priceFrom.equals(0l) || !pricTo.equals(0l)) ){
                        filters.add(Condition.between("shopPrice",new Long[]{priceFrom,pricTo}));
                    }

                }

                Pagination page = goodsService.getListInPage(goodsCritVo.getPageNumber(), goodsCritVo.getPageSize(), filters, orderBys);
                page.setList(this.bulidGoodsVoList(page.getList(),null));
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


    private List<GoodsVo> bulidGoodsVoList(List<Goods> goodsList,List<String> goodsIds) {
        List<GoodsVo> goodsVoList = new ArrayList<GoodsVo>();
        if(null != goodsIds && goodsIds.size() > 0){
           int count = 0;
            //按mongodb取出来的数据排序
            for (String goodsId : goodsIds) {
                if(null != goodsList && goodsList.size() > 0){
                    for (Goods goods1 : goodsList) {
                        //匹配成功则添加
                        if(goodsId.equals(goods1.getGoodsId())){
                            GoodsVo goodsVo = new GoodsVo();
                            BeanUtils.copyProperties(goods1, goodsVo);
                            //获取当前商品分数
                            Double goodsGrades = (Double) commentService.countGoodsGradesByGoodsId(goods1.getGoodsId()).getData();
                            goodsVo.setGoodsGrades(goodsGrades);
                            goodsVoList.add(goodsVo);
                            logger.info("循环了" + count++ + "次");
                            break;
                        }
                    }
                }

            }
        } else {
            if(null != goodsList && goodsList.size() > 0) {
                for (Goods goods1 : goodsList) {
                    GoodsVo goodsVo = new GoodsVo();
                    BeanUtils.copyProperties(goods1, goodsVo);
                    //获取当前商品分数
                    Double goodsGrades = (Double) commentService.countGoodsGradesByGoodsId(goods1.getGoodsId()).getData();
                    goodsVo.setGoodsGrades(goodsGrades);
                    goodsVoList.add(goodsVo);
                }
            }
        }
        return goodsVoList;
    }


    //构建货架完成信息VO(货架信息,商品信息)
    private GoodsShelvesVo buildGoodsShelveVo (String shevlesGoodsVoId){
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

                    List<GoodsVo> goodsVoList = new ArrayList<GoodsVo>();
                    if(null != goodsList && goodsList.size() > 0){
                        for (Goods goods1 : goodsList) {
                            GoodsVo goodsVo = new GoodsVo();
                            BeanUtils.copyProperties(goods1, goodsVo);
                            //获取当前商品分数
                            Double goodsGrades = (Double) commentService.countGoodsGradesByGoodsId(goods1.getGoodsId()).getData();
                            goodsVo.setGoodsGrades(goodsGrades);
                            goodsVoList.add(goodsVo);
                        }
                    }

                    goodsShelvesVo.setGoods(goodsVoList);
                }
            }

            //把货架标签按逗号拆分拼装
            goodsShelvesVo.setShelvesTagList(str2StrArray(goodsShelves.getShelvesTag(),"[,，]+"));

            return goodsShelvesVo;
        }

        return null;
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
