package com.jusfoun.hookah.console.server.api.es;
import com.jusfoun.hookah.console.server.config.Constants;
import com.jusfoun.hookah.console.server.config.ESTransportClient;
import com.jusfoun.hookah.console.server.util.PropertiesManager;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.es.EsCategory;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.domain.vo.EsGoodsVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created by guoruibing on 2017-5-25.
 */
@RestController
@RequestMapping("/api/es")
public class EsApi {
    @Autowired
    GoodsService goodsService;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Autowired
    ESTransportClient esTransportClient;
    @Autowired
    MgCategoryAttrTypeService mgCategoryAttrTypeService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize,
                                    String goodsName, String goodsSn,
                                    String keywords, String shopName) {
        Pagination<Goods> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            //只查询商品状态为未删除的商品
            filters.add(Condition.eq("isDelete", 1));

            if(StringUtils.isNotBlank(goodsName)){
                filters.add(Condition.like("goodsName", goodsName.trim()));
            }
            if(StringUtils.isNotBlank(goodsSn)){
                filters.add(Condition.like("goodsSn", goodsSn.trim()));
            }
            if(StringUtils.isNotBlank(keywords)){
                filters.add(Condition.like("keywords", keywords.trim()));
            }
            if(StringUtils.isNotBlank(shopName)){
                filters.add(Condition.like("shopName", shopName.trim()));
            }

            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }
//    /**
//     * 商品添加
//     * @param obj
//     * @return
//     */
//    @RequestMapping("/add")
//    @ResponseBody
//    public ReturnData addGoodsBack(@Valid @RequestBody GoodsVo obj) {
//        ReturnData returnData = new ReturnData<>();
//        returnData.setCode(ExceptionConst.Success);
//        try {
////            goodsService.addGoods(obj);
//        } catch (Exception e) {
//            returnData.setCode(ExceptionConst.Failed);
//            returnData.setMessage(e.toString());
//            e.printStackTrace();
//        }
//        return returnData;
//    }

    @RequestMapping("/v1/goods")
    public ReturnData searchByCondition(@RequestBody(required = false) EsGoodsVo vo) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            if(vo == null) {
                vo = new EsGoodsVo();
            }
            returnData.setData(elasticSearchService.search(vo));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/d")
    public Goods getDetail(String id) {
        return goodsService.selectById(id);
    }

    @RequestMapping("/es")
    public Object getES(String key, Boolean isHighLight) {
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setPageSize(10);
        try {
            //elasticSearchService.search(pagination, key, "qingdao-goods-v1", "goods", isHighLight, "goodsName", "goodsDesc", "goodsNamePy" );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagination;
    }

    //删除索引
    @RequestMapping(value  = "/del", method = RequestMethod.POST)
    public void delete(String index) {
        try {
            //"qingdao-goods-v1" "qingdao-category-v1"
            elasticSearchService.deleteIndex(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//删除商品
    @RequestMapping(value  = "/delGoods", method = RequestMethod.POST)
    public void delete(String goodsId, String indexName, String type) {
        try {
            System.exit(0);
            //"qingdao-goods-v1" "qingdao-category-v1"
            elasticSearchService.deleteById(indexName, type, goodsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/v1/category")
    public ReturnData searchCategory(String keyword) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(elasticSearchService.searchCategory(keyword, 10));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public void init(@RequestParam(value= "diff")  String diff) {

        String goodsIndex = null;
        String goodsType = null;
        Integer goodsShards = null;
        Integer goodsReplicas = null;
        if("goods".equals(diff)){
            goodsIndex = PropertiesManager.getInstance().getProperty("goods.index");
            goodsType = PropertiesManager.getInstance().getProperty("goods.type");
            goodsShards = Integer.valueOf(PropertiesManager.getInstance().getProperty("goods.index.shards"));
            goodsReplicas = Integer.valueOf(PropertiesManager.getInstance().getProperty("goods.index.replicas"));

        }else{

            goodsIndex = PropertiesManager.getInstance().getProperty("category.index");
            goodsType = PropertiesManager.getInstance().getProperty("category.type");
            goodsShards = Integer.valueOf(PropertiesManager.getInstance().getProperty("category.index.shards"));
            goodsReplicas = Integer.valueOf(PropertiesManager.getInstance().getProperty("category.index.replicas"));
        }
        String goodsKeyField = null;
        try {
            goodsKeyField = elasticSearchService.initEs(EsGoods.class, HookahConstants.Analyzer.LC_INDEX.val,
                    goodsIndex, goodsType, goodsShards, goodsReplicas);
            //如果能获取到主键字段说明是新创建的type，导入数据
            elasticSearchService.bulkInsert(goodsKeyField, goodsIndex, goodsType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("init/cat")
    public void initCat() {
        try {
            /** 初始化category索引*/
            String catKeyField = elasticSearchService.initEs(EsCategory.class, HookahConstants.Analyzer.LC_INDEX.val,
                    Constants.GOODS_CATEGORY_INDEX, Constants.GOODS_CATEGORY_TYPE, Constants.GOODS_CATEGORY_SHARDS,
                    Constants.GOODS_CATEGORY_REPLICAS);
            elasticSearchService.bulkInsert(catKeyField, Constants.GOODS_CATEGORY_INDEX, Constants.GOODS_CATEGORY_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/test")
    public SearchResponse test(String id) {
        try {
            BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery()
                    .must(termQuery("catIds", id))
                    .must(termQuery("goodsName", "企业"));
            SearchResponse searchResponse = esTransportClient.getObject().prepareSearch("qingdao-goods-v1")
                    .setTypes("goods")
                    .setQuery(queryBuilders)
//                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                    .setPostFilter(termQuery("catIds", id))
                    .addSort("goodsId", SortOrder.ASC)
                    .setFrom(0).setSize(10).setExplain(true)
                    .get();
            return searchResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v1/goods/types", method = RequestMethod.POST)
    public ReturnData getTypes (@RequestBody(required = false) EsGoods esGoods) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(elasticSearchService.getTypes(esGoods));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/findAttr")
    public ReturnData findGoodsAttr(String catId) {
        ReturnData<MgCategoryAttrType> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            MgCategoryAttrType obj = mgCategoryAttrTypeService.findGoodsAttr(catId);
            returnData.setData(obj);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
