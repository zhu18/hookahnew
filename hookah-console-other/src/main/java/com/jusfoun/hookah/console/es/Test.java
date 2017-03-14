package com.jusfoun.hookah.console.es;

import com.jusfoun.hookah.console.util.ESClientUtil;
import com.jusfoun.hookah.core.utils.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * Created by wangjl on 2017-3-13.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        /**
         * document apis
         */
//        // add
//        IndexResponse indexResponse = ESClientUtil.getClient().prepareIndex("twitter", "tweet1")
//                .setSource(jsonBuilder().startObject().field("analyzer","ik")
//                        .field("search_analyzer","ik_max_word")
//                        .field("search_analyzer","pinyin")
//                        .endObject()
//                )
//                .get();
//        System.out.println("add:" + indexResponse.toString());
        // get
//        GetResponse getResponse = ESClientUtil.getClient().prepareGet("twitter", "tweet", "1")
//                .setOperationThreaded(false).get();
//        System.out.println("get:" + getResponse.getSourceAsString());
        // delete
//        DeleteResponse deleteResponse = ESClientUtil.getClient().prepareDelete("twitter", "tweet", "1").get();
//        System.out.println("delete:" + deleteResponse.toString());
        // delete by query api
//        BulkIndexByScrollResponse bulkIndexByScrollResponse =
//                DeleteByQueryAction.INSTANCE.newRequestBuilder(ESClientUtil.getClient())
//                        .filter(QueryBuilders.matchQuery("user", "wjl"))
//                        .source("twitter")
//                        .get();
//        long deleted = bulkIndexByScrollResponse.getDeleted();
//        System.out.println("delete by query api:" + deleted);
        // update
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.index("twitter");
//        updateRequest.type("tweet");
//        updateRequest.id("1");
//        updateRequest.doc(jsonBuilder()
//                .startObject()
//                .field("user", "wjl234")
//                .endObject());
//        try {
//            UpdateResponse updateResponse = ESClientUtil.getClient().update(updateRequest).get();
//            System.out.println("update:" + updateResponse.toString());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        //upsert 如果数据不存在则插入数据，否则更新
//        IndexRequest indexRequest = new IndexRequest("index", "type", "1")
//                .source(jsonBuilder()
//                        .startObject()
//                        .field("name", "Joe Smith")
//                        .field("gender", "male")
//                        .endObject());
//        UpdateRequest updateRequest2 = new UpdateRequest("index", "type", "1")
//                .doc(jsonBuilder()
//                        .startObject()
//                        .field("gender", "male")
//                        .endObject())
//                .upsert(indexRequest);
//        try {
//            UpdateResponse updateResponse1 = ESClientUtil.getClient().update(updateRequest2).get();
//            System.out.println();
//            System.out.println("upsert:" + updateResponse1.toString());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        // multi get api
//        MultiGetResponse multiGetItemResponses = ESClientUtil.getClient().prepareMultiGet()
//                .add("twitter", "tweet", "1")
//                .add("twitter", "tweet", "2", "3", "4")
//                .add("index", "type", "1")
//                .get();
//        System.out.println("---multi get api begin---");
//        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
//            GetResponse response = itemResponse.getResponse();
//            if (response.isExists()) {
//                String json = response.getSourceAsString();
//                System.out.println(json);
//            }
//        }
//        System.out.println("---multi get api end---");
        //TODO bulk api
        /**
         * search api
         */
        // using scrollers in java
//        QueryBuilder qb = termQuery("user", "wjl");
//        SearchResponse scrollResp = ESClientUtil.getClient().prepareSearch("twitter")
//                .addSort("postDate", SortOrder.DESC)
//                .setScroll(new TimeValue(60000))
//                .setQuery(qb)
//                .setSize(100).get(); //max of 100 hits will be returned for each scroll
//        //Scroll until no hits are returned
//        System.out.println("---using scrollers in java begin---");
//        do {
//            for (SearchHit hit : scrollResp.getHits().getHits()) {
//                System.out.println(hit.getSourceAsString());
//            }
//            scrollResp = ESClientUtil.getClient().prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
//            System.out.println("scrollResp:" + scrollResp.toString());
//            System.out.println("---using scrollers in java end---");
//        } while(scrollResp.getHits().getHits().length != 0);
        // multisearch api
//        SearchRequestBuilder srb1 = ESClientUtil.getClient()
//                .prepareSearch().setQuery(QueryBuilders.queryStringQuery("elasticseach")).setSize(3);
//        SearchRequestBuilder srb2 = ESClientUtil.getClient()
//                .prepareSearch().setQuery(QueryBuilders.matchQuery("user", "wjl")).setSize(1);
//
//        MultiSearchResponse sr = ESClientUtil.getClient().prepareMultiSearch()
//                .add(srb1)
//                .add(srb2)
//                .get();
//
//        // You will get all individual responses from MultiSearchResponse#getResponses()
//        long nbHits = 0;
//        for (MultiSearchResponse.Item item : sr.getResponses()) {
//            SearchResponse response = item.getResponse();
//            nbHits += response.getHits().getTotalHits();
//            for (SearchHit hit : response.getHits().getHits()) {
//                System.out.println(hit.getSourceAsString());
//            }
//        }
        //TODO using aggregations
        //TODO search template
        System.out.println(JSONUtils.toString(search("国", "indx", "tweet1", 0, 10)));;
    }

    private XContentBuilder createIKMapping(String indexType) {
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder().startObject()
                    // 索引库名（类似数据库中的表）
                    .startObject(indexType).startObject("properties")
                    .startObject("product_name").field("type", "string")
                    .field("analyzer","ik").field("search_analyzer","ik_smart").endObject()
                    .startObject("title_sub").field("type", "string")
                    .field("analyzer","ik").field("search_analyzer","ik_smart").endObject()
                    .startObject("title_primary").field("type", "string")
                    .field("analyzer","ik").field("search_analyzer","ik_smart").endObject()
                    .startObject("publisher").field("type", "string")
                    .field("analyzer","ik").field("search_analyzer","ik_smart").endObject()
                    .startObject("author_name").field("type", "string")
                    .field("analyzer","ik").field("search_analyzer","ik_smart").endObject()
                    //.field("boost",100).endObject()
                    // 姓名
                    //.startObject("name").field("type", "string").endObject()
                    // 位置
                    //.startObject("location").field("type", "geo_point").endObject()
                    //.endObject().startObject("_all").field("analyzer","ik").field("search_analyzer","ik").endObject().endObject().endObject();
                    .endObject().endObject().endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapping;
    }

    public static Map<String, Object> search(String key,String index,String type,int start,int row) throws UnknownHostException {
        //创建查询索引,要查询的索引库为index
        SearchRequestBuilder builder = ESClientUtil.getClient().prepareSearch(index);
        builder.setTypes(type);
        builder.setFrom(start);
        builder.setSize(row);

        //设置查询类型：1.SearchType.DFS_QUERY_THEN_FETCH 精确查询； 2.SearchType.SCAN 扫描查询,无序
        builder.setSearchType(SearchType.DFS_QUERY_AND_FETCH);
        if(StringUtils.isNotBlank(key)){
            // 设置查询关键词
            builder.setQuery(QueryBuilders.multiMatchQuery(key, "content"));
        }

        //设置是否按查询匹配度排序
        builder.setExplain(true);
        //设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        builder.highlighter(highlightBuilder);

        //执行搜索,返回搜索响应信息
        SearchResponse searchResponse = builder.get();
        SearchHits searchHits = searchResponse.getHits();

        //总命中数
        long total = searchHits.getTotalHits();
        Map<String, Object> map = new HashMap();
        SearchHit[] hits = searchHits.getHits();
        map.put("count", total);
        List<Map<String, Object>> list = new ArrayList();
        for (SearchHit hit : hits) {
            //highlightFields.size=0??
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();

            //title高亮
            HighlightField titleField = highlightFields.get("content");
            Map<String, Object> source = hit.getSource();
            if(titleField!=null){
                Text[] fragments = titleField.fragments();
                String name = "";
                for (Text text : fragments) {
                    name+=text;
                }
                source.put("content", name);
            }
//            //describe高亮
//            HighlightField describeField = highlightFields.get("describe");
//            if(describeField!=null){
//                Text[] fragments = describeField.fragments();
//                String describe = "";
//                for (Text text : fragments) {
//                    describe+=text;
//                }
//                source.put("describe", describe);
//            }
            list.add(source);
        }
        map.put("dataList", list);
        return map;
    }
}
