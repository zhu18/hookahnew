package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.domain.vo.EsGoodsVo;
import com.jusfoun.hookah.core.domain.vo.EsTypesVo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangjl on 2017-3-28.
 */
public interface ElasticSearchService {
    // 创建索引
    void createIndex(String indexName, Integer shards, Integer replicas) throws Exception;

    String initEs(Class clazz, String analyzer, String indexName, String type,
                Integer shards, Integer replicas) throws Exception;

    void bulkInsert(String keyField, String index, String type) throws Exception;

    void deleteIndex(String indexName) throws Exception;

    Pagination search(EsGoodsVo vo) throws Exception;

    List<String> goodsSuggestion(String prefix, Integer size) throws Exception;

    List<String> goodsSuggestion(String prefix) throws Exception;

    EsTypesVo getTypes(EsGoods vo) throws Exception;

    void deleteById(String indexName, String type, String goodsId) throws Exception;

    void upsertById(String indexName, String type, String goodsId, Map<String, Object> map) throws Exception;
}
