package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.console.server.config.ESTransportClient;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangjl on 2017-3-28.
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    ESTransportClient esTransportClient;

    // 创建索引
    public void createIndex(String indexName, String type) {

    }
}
