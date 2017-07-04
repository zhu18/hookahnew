package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.domain.mongo.MgGoodsHistory;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgGoodsHistoryService;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

/**
 * Created by wangjl on 2017-3-20.
 */
public class MgGoodsHistoryServiceImpl extends GenericMongoServiceImpl<MgGoodsHistory, String> implements MgGoodsHistoryService {
    @Resource
    MongoTemplate mongoTemplate;
    // 更新关注次数
//    @Override
//    public void updateClickRate(String goodsId) {
//        //查询mongo数据
//        MgGoodsHistory mgGoodsHistory = super.selectById(goodsId);
//        if(mgGoodsHistory != null) {
//            //+1操作
//            if(mgGoodsHistory.getClickRate() == null) {
//                mgGoodsHistory.setClickRate((long)1);
//            }else {
//                mgGoodsHistory.setClickRate(mgGoodsHistory.getClickRate() + 1);
//            }
//            mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(goodsId)),
//                    new Update().set("clickRate", mgGoodsHistory.getClickRate()), MgGoodsHistory.class);
//        }
//    }

}
