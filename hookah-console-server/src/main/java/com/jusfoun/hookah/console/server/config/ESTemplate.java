package com.jusfoun.hookah.console.server.config;

import com.alibaba.fastjson.JSONObject;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.es.EsAgg;
import com.jusfoun.hookah.core.domain.es.EsAggResult;
import com.jusfoun.hookah.core.domain.es.EsRange;
import com.jusfoun.hookah.core.utils.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
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
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by wangjl on 2017-3-27.
 */
@Component
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
        XContentBuilder mappingSource = jsonBuilder()
                .startObject().startObject(type)
                .startObject("_all").field("analyzer", HookahConstants.Analyzer.LC_INDEX.val)
                .field("search_analyzer", HookahConstants.Analyzer.LC_SEARCH.val)
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
        QueryBuilder queryString = null;
        MatchAllQueryBuilder matchAll = null;
        if(filterMap != null && !filterMap.isEmpty()){
            for(Map.Entry entry : filterMap.entrySet()){
//                    queryString = QueryBuilders.multiMatchQuery(entry.getValue(), "goodsName", "goodsNamePy");
                queryString = QueryBuilders.queryStringQuery(String.valueOf(entry.getValue()));
                ((QueryStringQueryBuilder)queryString).field(String.valueOf(entry.getKey()));
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
     * 复合查询
     * @param client
     * @param indexName
     * @param type
     * @param filterMap
     * @param pagination
     * @param orderField
     * @param order
     * @return
     */
    public Pagination search(TransportClient client, String indexName, String type, Map<String,Object> filterMap,
                             Pagination pagination, String orderField, String order, String ... highLightFields) {
        return search(client, indexName, type, filterMap, pagination, orderField, order, null, highLightFields);
    }

    public Pagination search(TransportClient client, String indexName, String type, Map<String,Object> filterMap,
                             Pagination pagination, String orderField, String order, EsRange range, String ... highLightFields){
        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName);
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        requestBuilder.highlighter(highlightBuilder);
        requestBuilder.setTypes(type).setQuery(range == null ? getBoolQueryBuilder(filterMap) : getBoolQueryBuilder(filterMap, range));
        if(StringUtils.isNotBlank(orderField)) {
            requestBuilder.addSort(orderField, StringUtils.isNotBlank(order) &&
                    order.equals(SortOrder.DESC.toString()) ? SortOrder.DESC : SortOrder.ASC);
        }
        requestBuilder.setFrom((pagination.getCurrentPage() -1) * pagination.getPageSize())
                .setSize(pagination.getPageSize());
        SearchResponse searchResponse = requestBuilder.execute().actionGet();

        List<Map<String, Object>> list = new ArrayList();
        for (SearchHit hit : searchResponse.getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            //title高亮
            Map<String, Object> source = hit.getSource();
            int i = 0;
            for(String filed : highLightFields) {
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
        pagination.setList(list);
        pagination.setTotalItems(searchResponse.getHits().getTotalHits());
        return pagination;
    }

    public BoolQueryBuilder getBoolQueryBuilder(Map<String,Object> filterMap) {
        return getBoolQueryBuilder(filterMap, null);
    }

    public BoolQueryBuilder getBoolQueryBuilder(Map<String,Object> filterMap, EsRange range) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder queryString = null;
        if(filterMap != null && !filterMap.isEmpty() && filterMap.size() > 0){
            for(Map.Entry entry : filterMap.entrySet()){
                if(entry.getValue() != null && !HookahConstants.ONSALE_START_DATE_FILEDNAME.equals(entry.getValue())) {
                    queryString = QueryBuilders.simpleQueryStringQuery(String.valueOf(entry.getValue()))
                            .field(String.valueOf(entry.getKey()));
                    boolQueryBuilder.must(queryString);
                }
            }

            if(range != null) {
                if(range.getPriceFrom() != null && range.getPriceTo() != null) {
                    queryString = QueryBuilders.rangeQuery("shopPrice").from(range.getPriceFrom()).to(range.getPriceTo());
                }else if(range.getPriceFrom() != null && range.getPriceTo() == null) {
                    queryString = QueryBuilders.rangeQuery("shopPrice").from(range.getPriceFrom());
                }else if(range.getPriceFrom() == null && range.getPriceTo() != null) {
                    queryString = QueryBuilders.rangeQuery("shopPrice").to(range.getPriceTo());
                }
                if(queryString != null)
                    boolQueryBuilder.must(queryString);
            }
        }else if((filterMap == null || filterMap.size() == 0) && range != null) {
            if(range.getPriceFrom() != null && range.getPriceTo() != null) {
                queryString = QueryBuilders.rangeQuery("shopPrice").from(range.getPriceFrom()).to(range.getPriceTo());
            }else if(range.getPriceFrom() != null && range.getPriceTo() == null) {
                queryString = QueryBuilders.rangeQuery("shopPrice").from(range.getPriceFrom());
            }else if(range.getPriceFrom() == null && range.getPriceTo() != null) {
                queryString = QueryBuilders.rangeQuery("shopPrice").to(range.getPriceTo());
            }
            boolQueryBuilder.must(queryString);
        }else {//检索全部
            MatchAllQueryBuilder matchAll = QueryBuilders.matchAllQuery();
            boolQueryBuilder.must(matchAll);
        }

        if(filterMap.get(HookahConstants.ONSALE_START_DATE_FILED) != null) {
            boolQueryBuilder.filter( QueryBuilders.rangeQuery(HookahConstants.ONSALE_START_DATE_FILEDNAME)
                    .lte(DateUtils.toDateText(new Date(), DateUtils.DEFAULT_DATE_TIME_FORMAT)));
        }

        return boolQueryBuilder;
    }


    /**
     * suggest
     * @param client
     * @param prefix 搜索关键字
     * @param size 展示结果个数
     * @param indexName 索引名称
     * @param type 索引类型
     * @param suggestName 定义suggest名称
     * @param searchSuggestName 搜索时候suggest查询名称
     * @return
     */
    public List<String> suggest(TransportClient client, String prefix, Integer size, String indexName,
                                String type, String suggestName, String searchSuggestName) {
        List<String> listResult = new ArrayList<>();
        CompletionSuggestionBuilder suggestionBuilder = new CompletionSuggestionBuilder(suggestName);
        suggestionBuilder.text(prefix);
        suggestionBuilder.size(size);
        SuggestBuilder sb = new SuggestBuilder();
        sb.addSuggestion(searchSuggestName, suggestionBuilder);
        SearchResponse resp = client.prepareSearch().setIndices(indexName).setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery()).suggest(sb).get();
        Suggest sugg = resp.getSuggest();
        if(sugg != null) {
            CompletionSuggestion suggestion = sugg.getSuggestion(searchSuggestName);
            List<CompletionSuggestion.Entry> list = suggestion.getEntries();
            for (int i = 0; i < list.size(); i++) {
                List<?> options = list.get(i).getOptions();
                for (int j = 0; j < options.size(); j++) {
                    if (options.get(j) instanceof CompletionSuggestion.Entry.Option) {
                        CompletionSuggestion.Entry.Option op = (CompletionSuggestion.Entry.Option) options.get(j);
                        listResult.add(op.getText().toString());
                    }
                }
            }
        }
        return listResult;
    }

    public Map<String, List<EsAggResult>> getCounts(TransportClient client, String indexName, String type,
                                                    Map<String,Object> filterMap, List<EsAgg> listCnt) {
        return getCounts(client, indexName, type, filterMap, listCnt, null);
    }

    public Map<String, List<EsAggResult>> getCounts(TransportClient client, String indexName, String type,
                                                    Map<String,Object> filterMap, List<EsAgg> listCnt, EsRange range) {
        Map<String, List<EsAggResult>> map = new HashedMap();
        SearchRequestBuilder requestBuilder = client.prepareSearch()
                .setIndices(indexName).setTypes(type);
        if(filterMap != null) {
            requestBuilder.setQuery(range == null ? getBoolQueryBuilder(filterMap) : getBoolQueryBuilder(filterMap, range));
        }
        if (listCnt != null && listCnt.size() > 0) {
            for (EsAgg obj : listCnt) {
                requestBuilder.addAggregation(AggregationBuilders.terms(obj.getAggName())
                        .field(obj.getFieldName()).size(HookahConstants.ES_SIZE_BIGGER));
            }

            SearchResponse sr = requestBuilder.execute().actionGet();
            if (sr != null) {
                for(EsAgg obj : listCnt) {
                    List<EsAggResult> list = new ArrayList<>();
                    Map<String, Aggregation> aggMap = sr.getAggregations().asMap();
                    StringTerms terms = (StringTerms) aggMap.get(obj.getAggName());
                    Iterator<Terms.Bucket> bucketIterator = terms.getBuckets().iterator();
                    while(bucketIterator.hasNext()) {
                        Terms.Bucket bucket = bucketIterator.next();
                        list.add(new EsAggResult((String) bucket.getKey(), bucket.getDocCount()));
                    }
                    map.put(obj.getAggName(), list);
                }
            }
        }
        return map;
    }

    /**
     * 按主键删除
     * @param client
     * @param indexName
     * @param type
     * @param id
     */
    public void deleteById(TransportClient client, String indexName, String type, String id) {
        DeleteResponse response = client.prepareDelete(indexName, type, id).execute().actionGet();
    }

    public void upsertById(TransportClient client, String indexName, String type, String id, Map<String,Object> map) throws ExecutionException, InterruptedException {
        IndexRequest indexRequest = new IndexRequest(indexName, type, id)
                .source(map);
        UpdateRequest updateRequest = new UpdateRequest(indexName, type, id)
                .doc(map)
                .upsert(indexRequest);
        client.update(updateRequest).get();
    }
}
