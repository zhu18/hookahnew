package com.jusfoun.hookah.console.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jusfoun.hookah.console.server.config.ESTemplate;
import com.jusfoun.hookah.console.server.config.ESTransportClient;
import com.jusfoun.hookah.console.server.util.AnnotationUtil;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.es.EsAllMapping;
import com.jusfoun.hookah.core.domain.es.EsFieldMapping;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.domain.es.EsMapping;
import com.jusfoun.hookah.core.domain.vo.EsGoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjl on 2017-3-28.
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    ESTransportClient esTransportClient;
    @Autowired
    ESTemplate esTemplate;
    @Autowired
    GoodsMapper goodsMapper;

    // 创建索引
    @Override
    public void createIndex(String indexName, Integer shards,Integer replicas) throws Exception {
        Map<String,Object> allMapper = new HashMap<>();
        allMapper.put("number_of_shards", shards);
        allMapper.put("number_of_replicas", replicas);
        esTemplate.createIndex(esTransportClient.getObject(), indexName, allMapper);
    }

    public String createMapping(String analyzer, String indexName, String type, Class clazz) throws Exception{
        EsAllMapping allMapper = new EsAllMapping(analyzer);
        Map<String, EsFieldMapping> properties = new HashMap<>();
        String keyFieldName = AnnotationUtil.getEsField(clazz, properties);
        EsMapping mapping = new EsMapping(allMapper, properties);
        boolean b = esTemplate.putMapping(esTransportClient.getObject(), indexName, type, JSONObject.toJSONString(mapping));
        if(!b) {
            throw new HookahException("创建mapping失败，返回false！");
        }
        return keyFieldName;
    }

    @Override
    public String initEs(Class clazz, String analyzer, String indexName, String type,
                       Integer shards, Integer replicas) throws Exception {
        TransportClient client = esTransportClient.getObject();
        String keyFieldName = "";
        //判断索引是否存在，如果不存在则创建索引
        if(!esTemplate.indexExists(client, indexName)){
            this.createIndex(indexName, shards, replicas);
        }
        //mapping不完整需要补充 判断mapping是否存在，如果不存在则创建mapping
        if(!esTemplate.mappingExists(client, indexName, type)){
            keyFieldName = createMapping(analyzer, indexName, type, clazz);
        }
        return keyFieldName;
    }

    @Override
    public void bulkInsert(String keyField, String index, String type) throws Exception {
        if(StringUtils.isNotBlank(keyField)) {
            List<Map<String, Object>> maps = new ArrayList<>();
            switch (type) {
                case "goods" :
                    List<EsGoods> list = goodsMapper.getNeedEsGoods();
                    if (list != null && list.size() > 0) {
                        for(EsGoods goods : list) {
                            List<String> input = new ArrayList<>();
                            input.add(goods.getGoodsId());
                            input.add(goods.getGoodsName());
                            input.add(goods.getGoodsNamePy());
                            goods.setSuggest(input);
                            maps.add(AnnotationUtil.convert2Map(goods));
                        }
                    }
                 break;
            }
            esTemplate.bulkProcessorIndex(esTransportClient.getObject(), index, type,
                    keyField, 5, 10000, 60, 1, maps);
        }else {
            //TODO 从消息队列获取未读取数据
        }
    }

    @Override
    public Pagination search(Pagination pagination, String key, String index, String type,
                             Boolean isHighLight, String... fields) throws Exception {
        esTemplate.search(esTransportClient.getObject(), key, index, type, pagination, isHighLight, fields);
        return pagination;
    }

    @Override
    public void deleteIndex(String indexName) throws Exception{
        esTemplate.deleteIndex(esTransportClient.getObject(), indexName);
    }

    @Override
    public Pagination search(EsGoodsVo vo) throws Exception {
        Integer pageSize = vo.getPageSize();
        Integer pageNum = vo.getPageNum();
        String orderField = vo.getOrderFiled();
        String order = vo.getOrder();

        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        Map<String, Object> map = new HashedMap();
        if(vo.getEsGoods() != null) {
            map = AnnotationUtil.convert2Map(vo.getEsGoods());
        }
        esTemplate.search(esTransportClient.getObject(), "qingdao-goods-v1",
                "goods", map, pagination, orderField, order);
        return pagination;
    }
}
