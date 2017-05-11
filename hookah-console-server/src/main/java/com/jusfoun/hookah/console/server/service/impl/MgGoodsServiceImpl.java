package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;

/**
 * Created by wangjl on 2017-3-20.
 */
public class MgGoodsServiceImpl extends GenericMongoServiceImpl<MgGoods, String> implements MgGoodsService {
    @Resource
    MongoTemplate mongoTemplate;
    // 更新关注次数
    @Override
    public void updateClickRate(String goodsId) {
        //查询mongo数据
        MgGoods mgGoods = super.selectById(goodsId);
        if(mgGoods != null) {
            //+1操作
            if(mgGoods.getClickRate() == null) {
                mgGoods.setClickRate((long)1);
            }else {
                mgGoods.setClickRate(mgGoods.getClickRate() + 1);
            }
            mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(goodsId)),
                    new Update().set("clickRate", mgGoods.getClickRate()), MgGoods.class);
        }
    }

}
