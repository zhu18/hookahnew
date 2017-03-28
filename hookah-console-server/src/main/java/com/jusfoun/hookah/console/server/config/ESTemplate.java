package com.jusfoun.hookah.console.server.config;

import com.alibaba.fastjson.JSONObject;
import com.jusfoun.hookah.core.common.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjl on 2017-3-27.
 */
public class ESTemplate {
    /**
     * 构建ES索引
     * @param indexName
     * @return boolean
     */
    public boolean createIndex(TransportClient client, String indexName){
        Assert.notNull(indexName, "index不能为空");
        return client.admin().indices().create(Requests.createIndexRequest(indexName))
                .actionGet().isAcknowledged();
    }

    /**
     * 构建索引  根据索引的名称和设置信息
     * @param indexName
     * @param settings
     * @return boolean
     */
    public boolean createIndex(TransportClient client,String indexName,Object settings){
        Assert.notNull(indexName, "index不允许为空");
        CreateIndexRequestBuilder requestBuilder = client.admin().indices().prepareCreate(indexName);
        if(settings != null){
            if(settings instanceof String){
                requestBuilder.setSettings(String.valueOf(settings));
            } else if(settings instanceof Map){
                requestBuilder.setSettings((Map)settings);
            }else if(settings instanceof XContentBuilder){
                requestBuilder.setSettings((XContentBuilder)settings);
            }
        }
        return requestBuilder.execute().actionGet().isAcknowledged();
    }

    /**
     * 使用prepareMapping形式进行创建mapping
     * @param indexName
     * @param type
     * @param mappings
     * @return
     * @throws IOException
     */
    public boolean putMapping(TransportClient client,String indexName,String type,Map<String,Map<String,String>> mappings) throws IOException{
        XContentBuilder mappingSource = XContentFactory.jsonBuilder()
                .startObject().startObject(type)
                .startObject("_all").field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word")
                .field("term_vector", "no").endObject()
                //----初始
                .startObject("properties");

        Map<String,String> currentMap = null;
        for(Map.Entry<String, Map<String,String>> entry : mappings.entrySet()){
            currentMap = entry.getValue();
            mappingSource.startObject(entry.getKey());
            for(Map.Entry<String, String> secondEntry : currentMap.entrySet()){
                mappingSource.field(secondEntry.getKey(), secondEntry.getValue());
            }
            mappingSource.endObject();
        }

        mappingSource.endObject().endObject().endObject();

        return client.admin().indices().preparePutMapping(indexName).setType(type)
                .setSource(mappingSource).execute().actionGet().isAcknowledged();
    }

    /**
     * 由自己进行定义mapping
     * @param indexName
     * @param type
     * @param mapping String  Map  XContentBuilder
     * @return boolean
     */
    public boolean putMapping(TransportClient client,String indexName,String type,Object mapping){
        Assert.notNull(indexName, "No index defined for putMapping()");
        Assert.notNull(type, "No type defined for putMapping()");
        PutMappingRequestBuilder mappingBuilder = client.admin().indices()
                .preparePutMapping(indexName).setType(type);
        if(mapping instanceof String){
            mappingBuilder.setSource(String.valueOf(mapping));
        }else if(mapping instanceof Map){
            mappingBuilder.setSource((Map) mapping);
        }else if(mapping instanceof XContentBuilder){
            mappingBuilder.setSource((XContentBuilder) mapping);
        }
        return mappingBuilder.execute().actionGet().isAcknowledged();
    }

    /**
     * 获取mapping
     * @param indexName
     * @param type
     * @return Map
     */
    public Map getMapping(TransportClient client,String indexName,String type){
        Assert.notNull(indexName, "No index defined for putMapping()");
        Assert.notNull(type, "No type defined for putMapping()");
        Map mappings = null;
        try {
            mappings = client.admin().indices().getMappings(new GetMappingsRequest().indices(indexName).types(type))
                    .actionGet().getMappings().get(indexName).get(type).getSourceAsMap();
        } catch (Exception e) {
            throw new ElasticsearchException("Error while getting mapping for indexName : " + indexName + " type : " + type + " " + e.getMessage());
        }
        return mappings;
    }

    /**
     * 删除指定的索引
     * @param indexName
     * @return boolean
     */
    public boolean deleteIndex(TransportClient client,String indexName){
        Assert.notNull(indexName, "indexName不允许为空");
        if(indexExists(client,indexName)){
            return client.admin().indices().prepareDelete(indexName).execute().actionGet().isAcknowledged();
        }
        return false;
    }


    /**
     * 指定的索引的名称是否存在
     * @param indexName
     * @return boolean
     */
    public boolean indexExists(TransportClient client,String indexName){
        return client.admin().indices().exists(Requests.indicesExistsRequest(indexName)).actionGet().isExists();
    }

    /**
     * 判断mapping是否存在
     * @param client
     * @param indexName
     * @param type
     * @return
     */
    public boolean mappingExists(TransportClient client,String indexName,String type){
        return client.admin().cluster().prepareState().execute()
                .actionGet().getState().metaData().index(indexName)
                .getMappings().containsKey(type);
    }

    /**
     * 小数据量的批量一次提交
     * 批量添加索引数据 ，这里keyId可以允许为空，为空则使用ES为我们默认生成的id，
     * keyId不为空，那么该字段为后面map数据中某个key，然后程序会根据该key获取的值作为id进行加入ES
     * @param indexName
     * @param type
     * @param keyId  @Nullable Object keyId,boolean keyVal2Column,
     * @param listMap
     */
    public void bulkIndex(TransportClient client,String indexName,String type,String keyId,List<Map<String,Object>> listMap){
        Assert.notNull(indexName, "indexName不允许为空");
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        IndexRequestBuilder indexRequest = null;

        if(StringUtils.isNotBlank(keyId)){
            for(Map<String,?> currentMap : listMap){
                indexRequest = client.prepareIndex(indexName, type,currentMap.get(keyId).toString());
                //indexRequest.setSource(currentMap);
                System.out.println(JSONObject.toJSONString(currentMap));
                indexRequest.setSource(JSONObject.toJSONString(currentMap));
                bulkRequest.add(indexRequest);
            }
        }else{
            for(Map<String,?> currentMap : listMap){
                indexRequest = client.prepareIndex(indexName, type);
                indexRequest.setSource(currentMap);
                bulkRequest.add(indexRequest);
            }
        }

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            Map<String, String> failedDocuments = new HashMap<String, String>();
            for (BulkItemResponse item : bulkResponse.getItems()) {
                if (item.isFailed())
                    failedDocuments.put(item.getId(), item.getFailureMessage());
            }
            throw new ElasticsearchException(
                    "Bulk indexing has failures. Use ElasticsearchException.getFailedDocuments() for detailed messages ["
                            + failedDocuments + "]", failedDocuments
            );
        }
    }

    /**
     * 细粒度的批量提交   BackoffPolicy 批量请求重试失败
     * 有失败会抛出异常     throw new ElasticsearchException(failure);
     * @param indexName
     * @param type
     * @param bulkSize  单位为MB，批量提交总大小，设置-1可禁用
     * @param bulkActions 提交的批次量，默认是1000，设置-1可以禁用
     * @param flushInterval  提交时间间隔
     * @param concurrentRequests  线程数
     * @param listMap
     */
    public void bulkProcessorIndex(TransportClient client,String indexName,String type,String keyId,long bulkSize,int bulkActions,
                                   long flushInterval,int concurrentRequests,List<Map<String,Object>> listMap){
        Assert.notNull(indexName, "indexName不允许为空");
        IndexRequest indexRequest = null;
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener(){
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                // TODO Auto-generated method stub
                throw new ElasticsearchException(failure);
            }

        }).setBulkActions(bulkActions).setBulkSize(new ByteSizeValue(bulkSize, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(flushInterval))
                .setConcurrentRequests(concurrentRequests)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(500), 3))
                .build();
        if(StringUtils.isNotBlank(keyId)){
            for(Map<String,?> currentMap : listMap){
                indexRequest = new IndexRequest(indexName, type,currentMap.get(keyId).toString());
                //indexRequest.source(currentMap);
                indexRequest.source(JSONObject.toJSONString(currentMap));
                //System.out.println(JSONObject.toJSONString(currentMap));
                bulkProcessor.add(indexRequest);
            }
        }else{
            for(Map<String,?> currentMap : listMap){
                indexRequest = new IndexRequest(indexName, type);
                indexRequest.source(JSONObject.toJSONString(currentMap));
                bulkProcessor.add(indexRequest);
            }
        }

        bulkProcessor.flush();//收尾刷新一次
        bulkProcessor.close();
    }

    /**
     * 细粒度的批量更新   BackoffPolicy 批量请求重试失败
     * 有失败会抛出异常     throw new ElasticsearchException(failure);
     * @param indexName
     * @param type
     * @param bulkSize  单位为MB，批量提交总大小，设置-1可禁用
     * @param bulkActions 提交的批次量，默认是1000，设置-1可以禁用
     * @param flushInterval  提交时间间隔
     * @param concurrentRequests  线程数
     * @param listMap
     */
    public void bulkProcessorUpdate(TransportClient client,String indexName,String type,String keyId,long bulkSize,int bulkActions,
                                    long flushInterval,int concurrentRequests,List<Map<String,Object>> listMap){
        if(StringUtils.isBlank(keyId)){return;}
        Assert.notNull(indexName, "indexName不允许为空");
        UpdateRequest updateRequest = null;
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener(){
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                // TODO Auto-generated method stub
                throw new ElasticsearchException(failure);
            }

        }).setBulkActions(bulkActions).setBulkSize(new ByteSizeValue(bulkSize, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(flushInterval))
                .setConcurrentRequests(concurrentRequests)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(500), 3))
                .build();
        if(StringUtils.isNotBlank(keyId)){
            for(Map<String,?> currentMap : listMap){
                updateRequest = new UpdateRequest(indexName, type,currentMap.get(keyId).toString());
                updateRequest.doc(JSONObject.toJSONString(currentMap));
                //System.out.println(JSONObject.toJSONString(currentMap));
                bulkProcessor.add(updateRequest);
            }
        }

        bulkProcessor.flush();//收尾刷新一次
        bulkProcessor.close();
    }

    public void bulkDelete(TransportClient client,String indexName,String type,long bulkSize,int bulkActions,
                           long flushInterval,int concurrentRequests,List<Object> ids){
        if(ids == null || ids.isEmpty()){
            return;
        }
        Assert.notNull(indexName, "indexName不允许为空");
        DeleteRequest deleteRequest = null;
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener(){
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                // TODO Auto-generated method stub
                throw new ElasticsearchException(failure);
            }

        }).setBulkActions(bulkActions).setBulkSize(new ByteSizeValue(bulkSize, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(flushInterval))
                .setConcurrentRequests(concurrentRequests)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(500), 3))
                .build();

        for(Object id : ids){
            deleteRequest = new DeleteRequest(indexName, type, String.valueOf(id));
            bulkProcessor.add(deleteRequest);
        }

        bulkProcessor.flush();//收尾刷新一次
        bulkProcessor.close();
    }

    /**
     * 根据传入的index，type和批量的id来进行获取相应的数据
     * @param index
     * @param type
     * @param ids
     * @return
     */
    public List<Map<String,Object>> queryMultiGet(TransportClient client,String index,String type,String... ids){
        List<Map<String,Object>> listData = null;
        GetResponse response = null;
        MultiGetResponse multiGetResponse = client.prepareMultiGet()
                .add(index, type, ids).get();
        listData = new ArrayList<>();
        for (MultiGetItemResponse itemResponse : multiGetResponse) {
            response = itemResponse.getResponse();
            if (response.isExists()) {
                listData.add(response.getSourceAsMap());
            }
        }
        return listData;
    }

    /**
     * 多条件查询
     * @param indexName
     * @param type
     * @param field
     * @param fieldNames
     */
    public void queryMultiMatch(TransportClient client,String indexName,String type,String field,String... fieldNames){
        QueryBuilder qb = QueryBuilders.multiMatchQuery(field, fieldNames);
        SearchRequestBuilder srb = client.prepareSearch();
        srb.setIndices(indexName).setTypes(type).setQuery(qb);
        SearchResponse searchResponse = srb.execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();
        for(int i =0; i < searchHits.getHits().length; i++){
            System.out.println(searchHits.getHits()[i].getSourceAsString());
        }
    }

    /**
     * 过滤字符串,callFields可为null，为null返回全部字段，否则返回指定的字段
     * @param indexName
     * @param type
     * @param filterMap
     * @param callFields
     * @return List<Map>
     */
    public List<Map> queryStringQuery(TransportClient client,String indexName,String type,Map<String,Object> filterMap,
                                      int from,int limit,String... callFields){
        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryStringQueryBuilder queryString = null;
        MatchAllQueryBuilder matchAll = null;
        if(filterMap != null && !filterMap.isEmpty()){
            for(Map.Entry entry : filterMap.entrySet()){
                queryString = QueryBuilders.queryStringQuery(String.valueOf(entry.getValue()));
                queryString.field(String.valueOf(entry.getKey()));
                boolQueryBuilder.must(queryString);
            }
        }else{//检索全部
            matchAll = QueryBuilders.matchAllQuery();
            boolQueryBuilder.must(matchAll);
        }

        requestBuilder.setTypes(type).setQuery(boolQueryBuilder);
        requestBuilder.setFrom(from).setSize(limit);
        if(callFields != null && callFields.length > 0){
            requestBuilder.storedFields(callFields);
        }
        SearchResponse searchResponse = requestBuilder.execute().actionGet();

        return result2MapData(searchResponse);
    }

    /**
     * 测试检索到的记录数
     * @param indexName
     * @param type
     * @param filterMap
     * @param callFields
     * @return
     */
    public long queryCountByStringQuery(TransportClient client,String indexName,String type,
                                        Map<String,Object> filterMap,String... callFields){
        long totalCount = 0;
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryStringQueryBuilder queryString = null;
        MatchAllQueryBuilder matchAll = null;
        if(filterMap != null && !filterMap.isEmpty()){
            for(Map.Entry entry : filterMap.entrySet()){
                queryString = QueryBuilders.queryStringQuery(String.valueOf(entry.getValue()));
                queryString.field(String.valueOf(entry.getKey()));
                boolQueryBuilder.must(queryString);
            }
        }else{//检索全部
            matchAll = QueryBuilders.matchAllQuery();
            boolQueryBuilder.must(matchAll);
        }
        SearchResponse response = client.prepareSearch(indexName).setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_AND_FETCH)
                .setQuery(boolQueryBuilder).execute().actionGet();
        int statusCode = response.status().getStatus();
        if(statusCode == 200 || statusCode == 201){
            totalCount = response.getHits().getTotalHits();
        }
        return totalCount;
    }

    public SearchRequestBuilder getSearchRequest(TransportClient client,String indexName,String type){
        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName);
        requestBuilder.setTypes(type);
        return requestBuilder;
    }

    public SearchResponse queryByCondition(SearchRequestBuilder requestBuilder){
        return requestBuilder.execute().actionGet();
    }

    /**
     * 该方法是response中的数据进行处理成List<Map>数据进行返回
     * @param searchResponse
     * @return List<Map>
     * queryStringQuery  不能使用searchHit.getSourceAsString()来取值，只能使用迭代遍历的方式
     */
    public List<Map> result2MapData(SearchResponse searchResponse){
        List<Map> listData = new ArrayList<>();
        if(searchResponse.status().getStatus() == 200){
            for(SearchHit searchHit : searchResponse.getHits().getHits()){
                listData.add(JSONObject.parseObject(searchHit.getSourceAsString(), Map.class));
            }
        }
        return listData;
    }

    /**
     * 抽取List<Map>中特定的字段的值到listArr中
     * @param fieldName
     * @param listData
     * @return list []
     */
    public List extractField2ListArr(String fieldName,List<Map> listData){
        Assert.notNull(fieldName, "fieldName不允许为空");
        List listArr = null;
        if(listData != null && !listData.isEmpty()){
            listArr = new ArrayList<>();
            Object obj = null;
            for(Map currentMap : listData){
                if((obj = currentMap.get(fieldName)) != null){
                    listArr.add(obj);
                }
            }
        }
        return listArr;
    }

    /**
     * 获取bulkProcessor对象
     * @param bulkActions  根据当前添加的动作次数来刷新新的批量请求时的设置 1000。 设置为-1禁用。
     * @param bulkSize   设置时要根据当前添加的动作的大小来刷新一个新的批量请求。 5MB。 设置为-1禁用它
     * @param flushInterval 多长时间刷新   with the flush interval
     * @param concurrentRequests  线程数
     * @param initialDelay   超时间隔
     * @param maxNumberOfRetries  重试次数
     * @return
     */
    public BulkProcessor getBulkProcess(TransportClient client,int bulkActions,long bulkSize,
                                        long flushInterval,int concurrentRequests,long initialDelay,int maxNumberOfRetries){
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener(){
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                // TODO Auto-generated method stub
                throw new ElasticsearchException("批量操作失败",failure);
            }

        }).setBulkActions(bulkActions).setBulkSize(new ByteSizeValue(bulkSize, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(flushInterval))
                .setConcurrentRequests(concurrentRequests)
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(initialDelay), maxNumberOfRetries))
                .build();
        return bulkProcessor;
    }

    /**
     * 抽取聚合的内容，该方法只抽取两层，封装里面的key
     * @param terms 			parent_bucket.getAggregations().get(childAggAlias)
     * @param childAggAlias 	子文档别名
     * @param parentKey     	要封装到map中父聚合文档的key
     * @param childKey			要封装到map中子聚合文档的key
     * @param parentIsNumber	父文档的值是否是数值类型
     * @return					List<Map<String, Object>>
     */
    public static List<Map<String, Object>> extractAggregationData(Terms terms, String childAggAlias, String parentKey,
                                                                   String childKey, boolean parentIsNumber) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> currentMap = null;
        if(terms != null && !terms.getBuckets().isEmpty()){
            List<Terms.Bucket> parent_buckets = terms.getBuckets();
            if(parent_buckets != null && !parent_buckets.isEmpty()){
                for(Terms.Bucket parent_bucket : parent_buckets){
                    currentMap = new HashMap<>();
                    if(parentIsNumber){
                        currentMap.put(parentKey, parent_bucket.getKeyAsNumber());
                    }else{
                        currentMap.put(parentKey, parent_bucket.getKeyAsString());
                    }
                    Terms child_term = parent_bucket.getAggregations().get(childAggAlias);
                    List<Terms.Bucket> child_buckets = null;
                    if(child_term != null && (child_buckets = child_term.getBuckets()) != null && !child_buckets.isEmpty()){
                        for(Terms.Bucket child_bucket : child_buckets){
                            currentMap.put(childKey, child_bucket.getKeyAsString());
                            currentMap.put("count", child_bucket.getDocCount());
                            resultList.add(currentMap);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 抽取文档的ID
     * @param searchHits
     * @return
     */
    public static List<String> extractEsIds(SearchHits searchHits) {
        SearchHit[] hitArr = searchHits.getHits();
        List<String> currentList = null;
        if(hitArr != null && hitArr.length > 0){
            currentList = new ArrayList<>();
            for(SearchHit searchHit : hitArr){
                currentList.add(searchHit.getId());
            }
        }
        return currentList;
    }

    /**
     * 搜索
     * @param client
     * @param key 搜索关键词
     * @param index 索引
     * @param type
     * @param pagination
     * @param fields
     * @return
     * @throws UnknownHostException
     */
    public void search(TransportClient client, String key, String index, String type,
                       Pagination pagination, Boolean isHighLight, String... fields) throws UnknownHostException {
        //创建查询索引,要查询的索引库为index
        SearchRequestBuilder builder = client.prepareSearch();
        builder.setIndices(index);
        builder.setTypes(type);
        builder.setFrom(pagination.getCurrentPage()*pagination.getPageSize());
        builder.setSize(pagination.getPageSize());

        //设置查询类型：1.SearchType.DFS_QUERY_THEN_FETCH 精确查询； 2.SearchType.SCAN 扫描查询,无序
        builder.setSearchType(SearchType.DFS_QUERY_AND_FETCH);
        if(StringUtils.isNotBlank(key)){
            // 设置查询关键词
            builder.setQuery(QueryBuilders.multiMatchQuery(key, fields));
        }

        //设置是否按查询匹配度排序
        builder.setExplain(true);
        //设置高亮显示
        if(isHighLight) {
            HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
            highlightBuilder.preTags("<span style=\"color:red\">");
            highlightBuilder.postTags("</span>");
            builder.highlighter(highlightBuilder);
        }

        //执行搜索,返回搜索响应信息
        SearchResponse searchResponse = builder.get();
        SearchHits searchHits = searchResponse.getHits();

        //总命中数
        long total = searchHits.getTotalHits();
        SearchHit[] hits = searchHits.getHits();
        pagination.setTotalItems(total);
        List<Map<String, Object>> list = new ArrayList();
        for (SearchHit hit : hits) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            //title高亮
            Map<String, Object> source = hit.getSource();
            for(String filed : fields) {
                HighlightField titleField = highlightFields.get(filed);
                if(titleField!=null){
                    Text[] fragments = titleField.fragments();
                    String name = "";
                    for (Text text : fragments) {
                        name += text;
                    }
                    source.put(filed, name);
                }
            }
            list.add(source);
        }
        if(list != null && list.size() > 0)
            pagination.setList(list);
    }
}
