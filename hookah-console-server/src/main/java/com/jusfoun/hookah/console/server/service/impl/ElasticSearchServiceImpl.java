package com.jusfoun.hookah.console.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jusfoun.hookah.console.server.config.ESTemplate;
import com.jusfoun.hookah.console.server.config.ESTransportClient;
import com.jusfoun.hookah.console.server.util.AnnotationUtil;
import com.jusfoun.hookah.core.domain.es.EsAllMapping;
import com.jusfoun.hookah.core.domain.es.EsFieldMapping;
import com.jusfoun.hookah.core.domain.es.EsMapping;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjl on 2017-3-28.
 */
@Service
public class ElasticSearchServiceImpl<T> implements ElasticSearchService {
    @Autowired
    ESTransportClient esTransportClient;
    @Autowired
    ESTemplate esTemplate;

    // 创建索引
    public void createIndex(String indexName, Integer shards,Integer replicas) throws Exception {
        Map<String,Object> allMapper = new HashMap<>();
        allMapper.put("number_of_shards", shards);
        allMapper.put("number_of_replicas", replicas);
        esTemplate.createIndex(esTransportClient.getObject(), indexName, allMapper);
    }

    public boolean createMapping(String analyzer, String indexName, String type, Class clazz) throws Exception{
        EsAllMapping allMapper = new EsAllMapping(analyzer);
        Map<String, EsFieldMapping> properties = AnnotationUtil.getEsField(clazz);
        EsMapping mapping = new EsMapping(allMapper, properties);
        return esTemplate.putMapping(esTransportClient.getObject(), indexName, type, JSONObject.toJSONString(mapping));
    }

    public void initEs(Class clazz, String analyzer, String indexName, String type,
                       Integer shards,Integer replicas, ESTemplate esTemplate) throws Exception {
        TransportClient client = esTransportClient.getObject();
        //判断索引是否存在，如果不存在则创建索引
        if(!esTemplate.indexExists(client, indexName)){
            this.createIndex(indexName, shards, replicas);
        }
        //判断mapping是否存在，如果不存在则创建mapping
        if(!esTemplate.mappingExists(client, indexName, type)){
            createMapping(analyzer, indexName, type, clazz);
        }
    }
}
