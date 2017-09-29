package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.GoodsShelvesService;
import com.jusfoun.hookah.rpc.api.MgGoodsShelvesGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by ctp on 2017/4/5.
 */
public class MgGoodsShelvesGoodsServiceImpl extends GenericMongoServiceImpl<MgShelvesGoods, String> implements MgGoodsShelvesGoodsService {

    private static final Logger logger = LoggerFactory.getLogger(MgGoodsShelvesGoodsServiceImpl.class);

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    GoodsService goodsService;

    @Resource
    GoodsShelvesService goodsShelvesService;

    @Resource
    MgGoodsShelvesGoodsService mgGoodsShelvesGoodsService;


    @Override
    public ReturnData<List<MgShelvesGoods>> updateMgGoodsSG(MgShelvesGoods mgShelvesGoods) {
        ReturnData<List<MgShelvesGoods>> returnData = new ReturnData<List<MgShelvesGoods>>();
        returnData.setCode(ExceptionConst.Success);
        try {

            if(Objects.nonNull(mgShelvesGoods)){
                String shelvesGoodsId = mgShelvesGoods.getShelvesGoodsId();
                String shelvesGoodsName =  mgShelvesGoods.getShelvesGoodsName();
                List<String> goodsIdList = mgShelvesGoods.getGoodsIdList();

                if(StringUtils.isBlank(shelvesGoodsId)){
                    returnData.setCode(ExceptionConst.AssertFailed);
                    returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                    return returnData;
                }

                //根据@Id更新货架数据
                Query query=new Query(Criteria.where("shelvesGoodsId").is(mgShelvesGoods.getShelvesGoodsId()));
                Update update = new Update();
                if(StringUtils.isNoneBlank(shelvesGoodsName)){
                    update.set("shelvesGoodsName",mgShelvesGoods.getShelvesGoodsName());
                }
                if(Objects.nonNull(goodsIdList) && goodsIdList.size() != 0){
                    update.set("goodsIdList",mgShelvesGoods.getGoodsIdList());
                }
                mongoTemplate.updateFirst(query,update,mgShelvesGoods.getClass());
            }

//          super.updateById(mgShelvesGoods);

            if(mgShelvesGoods == null) {
                throw new HookahException("操作失败");
            }
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @Override
    public ReturnData<MgShelvesGoods> findByIdGSMongo(String shelvesGoodsId) {
        ReturnData<MgShelvesGoods> returnData = new ReturnData<MgShelvesGoods>();

        //参数校验
        if(StringUtils.isBlank(shelvesGoodsId)){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }

        MgShelvesGoods mgShelvesGoods = super.selectById(shelvesGoodsId);
        returnData.setData(mgShelvesGoods);
        returnData.setCode(ExceptionConst.Success);
        return returnData;
    }

    @Override
    public ReturnData<MgShelvesGoods> delGSMongo(String shelvesGoodsId) {
        ReturnData<MgShelvesGoods> returnData = new ReturnData<MgShelvesGoods>();
        returnData.setCode(ExceptionConst.Success);
        try {
            MgShelvesGoods mgShelvesGoods = new MgShelvesGoods();
            mgShelvesGoods.setShelvesGoodsId(shelvesGoodsId);
            super.delete(mgShelvesGoods);
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            logger.error(e.getMessage());
        }

        return returnData;
    }

    @Override
    public ReturnData<List<MgShelvesGoods>> addMgGoodsSG(MgShelvesGoods mgShelvesGoods) {
        ReturnData<List<MgShelvesGoods>> returnData = new ReturnData<List<MgShelvesGoods>>();
        returnData.setCode(ExceptionConst.Success);
        try {
            mgShelvesGoods = super.insert(mgShelvesGoods);
            if(mgShelvesGoods == null) {
                throw new HookahException("操作失败");
            }
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @Override
    public ReturnData<Integer> countShelvesGoods(String shelvesGoodsId) {
        ReturnData<Integer> returnData = new ReturnData<Integer>();
        returnData.setCode(ExceptionConst.Success);

        MgShelvesGoods mgShelvesGoods = super.selectById(shelvesGoodsId);
        if(null == mgShelvesGoods){
            returnData.setData(null);
            return returnData;
        }

        List<String> goodsList = mgShelvesGoods.getGoodsIdList();
        if(null == goodsList || goodsList.size() == 0){
            returnData.setData(0);
            return returnData;
        }


        returnData.setData(goodsList.size());
        returnData.setCode(ExceptionConst.Success);

        return returnData;
    }

    @Override
    public ReturnData<List<MgShelvesGoods>> selectMgShelveGoodsList(MgShelvesGoods mgShelvesGoods) {
        return null;
    }

    @Override
    public Pagination<Goods> getData(int pageNumberNew, int pageSizeNew, String shelvesGoodsId) {
        Pagination<Goods> page = new Pagination<>(pageNumberNew, pageSizeNew);
        MgShelvesGoods mgShelvesGoods = super.selectById(shelvesGoodsId);
        if(mgShelvesGoods == null){

            // 新建mg货架数据
            GoodsShelves goodsShelves = goodsShelvesService.selectById(shelvesGoodsId);

            MgShelvesGoods mgShelvesGoodsx = new MgShelvesGoods();
            mgShelvesGoodsx.setShelvesGoodsId(goodsShelves.getShelvesId());
            mgShelvesGoodsx.setShelvesGoodsName(goodsShelves.getShelvesName());
            mgGoodsShelvesGoodsService.insert(mgShelvesGoodsx);

        }else{
            if(mgShelvesGoods.getGoodsIdList() != null && mgShelvesGoods.getGoodsIdList().size() > 0){
                List<Condition> filters = new ArrayList();
                List<OrderBy> orderBys = new ArrayList();
                filters.add(Condition.in("goodsId", mgShelvesGoods.getGoodsIdList().toArray()));
//                orderBys.add(OrderBy.desc("lastUpdateTime"));
                page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
                page.setList(this.formatGoodsList(page.getList(),mgShelvesGoods.getGoodsIdList()));

            }
        }
        return page;
    }

    private List<Goods> formatGoodsList(List<Goods> goodsList,List<String> goodsIds) {
        List<Goods> goodsVoList = new ArrayList<Goods>();
        if(null != goodsIds && goodsIds.size() > 0){
            int count = 0;
            //按mongodb取出来的数据排序
            for (String goodsId : goodsIds) {
                if(null != goodsList && goodsList.size() > 0){
                    for (Goods goods1 : goodsList) {
                        //匹配成功则添加
                        if(goodsId.equals(goods1.getGoodsId())){
                            goodsVoList.add(goods1);
                            break;
                        }
                    }
                }

            }
        } else {
            goodsVoList = goodsList;
        }
        return goodsVoList;
    }

    @Override
    public ReturnData delSMongoGoodsById(String shelvesGoodsId, String goodsId) throws HookahException {

        ReturnData<MgShelvesGoods> returnData = new ReturnData<MgShelvesGoods>();
        returnData.setCode(ExceptionConst.Success);
        try {
            MgShelvesGoods mgShelvesGoods = super.selectById(shelvesGoodsId);
            List<String> gidList = mgShelvesGoods.getGoodsIdList();
            if(gidList != null){

                for(String s : gidList){
                    if(s.equals(goodsId)){
                        gidList.remove(s);
                        break;
                    }
                }

                Query query = new Query(Criteria.where("shelvesGoodsId").is(mgShelvesGoods.getShelvesGoodsId()));
                Update update = new Update();
                update.set("goodsIdList", gidList);
                mongoTemplate.updateFirst(query, update, mgShelvesGoods.getClass());

                GoodsShelves goodsShelves = new GoodsShelves();
                goodsShelves.setShelvesId(shelvesGoodsId);
                goodsShelves.setGoodsNumber(gidList.size());
                goodsShelvesService.updateByIdSelective(goodsShelves);
            }

        }catch (Exception e){
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            logger.error(e.getMessage());
        }
        return returnData;
    }

    @Override
    public ReturnData addGidByMGid(String shelvesId, String goodsId) {
        ReturnData<MgShelvesGoods> returnData = new ReturnData<MgShelvesGoods>();
        returnData.setCode(ExceptionConst.Success);
        try {
            MgShelvesGoods mgShelvesGoods = super.selectById(shelvesId);
            if(mgShelvesGoods == null)
                throw new HookahException("空数据！");

            List<String> gidList = null;

            if(mgShelvesGoods.getGoodsIdList() == null){
                gidList = new ArrayList<>();
            }else{
                gidList = mgShelvesGoods.getGoodsIdList();
            }
            gidList.add(goodsId);
            Query query = new Query(Criteria.where("shelvesGoodsId").is(mgShelvesGoods.getShelvesGoodsId()));
            Update update = new Update();
            update.set("goodsIdList", gidList);
            mongoTemplate.updateFirst(query, update, mgShelvesGoods.getClass());


            GoodsShelves goodsShelves = new GoodsShelves();
            goodsShelves.setShelvesId(shelvesId);
            goodsShelves.setGoodsNumber(gidList.size());
            goodsShelvesService.updateByIdSelective(goodsShelves);

        }catch (Exception e){
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            logger.error(e.getMessage());
        }
        return returnData;
    }

    @Override
    public ReturnData batchAddGidByMGid(String shelvesId, String[] goodsId) {
        ReturnData<MgShelvesGoods> returnData = new ReturnData<MgShelvesGoods>();
        returnData.setCode(ExceptionConst.Success);
        try {
            MgShelvesGoods mgShelvesGoods = super.selectById(shelvesId);
            if(mgShelvesGoods == null)
                throw new HookahException("空数据！");

            List<String> gidList = null;

            if(mgShelvesGoods.getGoodsIdList() == null){
                gidList = new ArrayList<>();
            }else{
                gidList = mgShelvesGoods.getGoodsIdList();
            }
            gidList.addAll(Arrays.asList(goodsId));
            Query query = new Query(Criteria.where("shelvesGoodsId").is(mgShelvesGoods.getShelvesGoodsId()));
            Update update = new Update();
            update.set("goodsIdList", gidList);
            mongoTemplate.updateFirst(query, update, mgShelvesGoods.getClass());


            GoodsShelves goodsShelves = new GoodsShelves();
            goodsShelves.setShelvesId(shelvesId);
            goodsShelves.setGoodsNumber(gidList.size());
            goodsShelvesService.updateByIdSelective(goodsShelves);

        }catch (Exception e){
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            logger.error(e.getMessage());
        }
        return returnData;
    }
}
