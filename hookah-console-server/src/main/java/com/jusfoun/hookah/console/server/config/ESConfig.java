package com.jusfoun.hookah.console.server.config;

import com.jusfoun.hookah.core.constants.HookahConstants.Analyzer;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by wangjl on 2017-3-28.
 */
@Component
@Order(value=2)
public class ESConfig implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ESConfig.class);
    @Autowired
    ElasticSearchService elasticSearchService;

    /***
     * 初始化索引及索引数据
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) {
        logger.info("===========初始化ES-begin=============");
        //如果索引不存在创建索引并导入数据
        try {
            String goodsKeyField = elasticSearchService.initEs(EsGoods.class, Analyzer.IK_MAX_WORD.val,
                    Constants.GOODS_INDEX, Constants.GOODS_TYPE, Constants.GOODS_SHARDS, Constants.GOODS_REPLICAS);
            //如果能获取到主键字段说明是新创建的type，导入数据
            elasticSearchService.bulkInsert(goodsKeyField, Constants.GOODS_INDEX, Constants.GOODS_TYPE);
        } catch (Exception e) {
            logger.error("初始化ES-error:" + e.getMessage());
            e.printStackTrace();
        }
        logger.info("===========初始化ES-end=============");
    }


}
