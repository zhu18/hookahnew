package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.vo.EsGoodsVo;

import java.util.List;

/**
 * Created by wangjl on 2017-3-28.
 */
public interface ElasticSearchService {
    // 创建索引
    void createIndex(String indexName, Integer shards, Integer replicas) throws Exception;

    String initEs(Class clazz, String analyzer, String indexName, String type,
                Integer shards, Integer replicas) throws Exception;

    void bulkInsert(String keyField, String index, String type) throws Exception;

    Pagination search(Pagination pagination, String key, String index, String type,
                      Boolean isHighLight, String... fields) throws Exception;

    void deleteIndex(String indexName) throws Exception;

    Pagination search(EsGoodsVo vo) throws Exception;

    List<String> goodsSuggestion(String prefix, Integer size) throws Exception;

    List<String> goodsSuggestion(String prefix) throws Exception;
}
