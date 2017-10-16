package com.jusfoun.hookah.console.server.api.es;
import com.jusfoun.hookah.console.server.config.ESTransportClient;
import com.jusfoun.hookah.console.server.config.EsProps;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.es.EsCategory;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.domain.vo.EsGoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    EsProps esProps;

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

        //@RequestBody(required = false) EsGoodsVo vo
    @RequestMapping("/v1/goods")
    public ReturnData searchByCondition(String currentPage, String pageSize,
                                        String goodsName) {
        ReturnData returnData = new ReturnData<>();
        EsGoodsVo vo = new EsGoodsVo();

        //参数校验
        int pageNumberNew = HookahConstants.PAGE_NUM;

        if (StringUtils.isNotBlank(currentPage)) {
            pageNumberNew = Integer.parseInt(currentPage);

            vo.setPageNumber(pageNumberNew);
        }
        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
            vo.setPageSize(pageSizeNew);
        }
        if (StringUtils.isNotBlank(goodsName)) {
            EsGoods esGoods = new EsGoods();
            esGoods.setGoodsName(goodsName);
            vo.setEsGoods(esGoods);
        }
        returnData.setCode(ExceptionConst.Success);
        try {
//            if(vo == null) {
//                vo = new EsGoodsVo();
//            }
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
    public ReturnData delete(String index) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            //"qingdao-goods-v1" "qingdao-category-v1"
            elasticSearchService.deleteIndex(index);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


//删除商品
    @RequestMapping(value  = "/delGoods", method = RequestMethod.POST)
    public ReturnData delete(String goodsId, String indexName, String type) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            //"qingdao-goods-v1" "qingdao-category-v1"
            elasticSearchService.deleteById(indexName, type, goodsId);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
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
    public ReturnData init(@RequestParam(value= "diff")  String diff) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        String goodsIndex = null;
        String goodsType = null;
        Integer goodsShards = null;
        Integer goodsReplicas = null;
        if("goods".equals(diff)){
            goodsIndex = esProps.getGoods().get("index");
            goodsType = esProps.getGoods().get("type");
            goodsShards = Integer.parseInt(esProps.getGoods().get("shards"));
            goodsReplicas = Integer.parseInt(esProps.getGoods().get("replicas"));
        }else{
            goodsIndex = esProps.getCategory().get("index");
            goodsType = esProps.getCategory().get("type");
            goodsShards = Integer.parseInt(esProps.getCategory().get("shards"));
            goodsReplicas = Integer.parseInt(esProps.getCategory().get("replicas"));
        }
        String goodsKeyField = null;
        try {
            goodsKeyField = elasticSearchService.initEs(EsGoods.class, HookahConstants.Analyzer.LC_INDEX.val,
                    goodsIndex, goodsType, goodsShards, goodsReplicas);
            //如果能获取到主键字段说明是新创建的type，导入数据
            elasticSearchService.bulkInsert(goodsKeyField, goodsIndex, goodsType);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("init/cat")
    public void initCat() {
        try {
            /** 初始化category索引*/
            String catKeyField = elasticSearchService.initEs(EsCategory.class, HookahConstants.Analyzer.LC_INDEX.val,
                    esProps.getCategory().get("index"), esProps.getCategory().get("type"), Integer.parseInt(esProps.getCategory().get("shards")),
                    Integer.parseInt(esProps.getCategory().get("replicas")));
            elasticSearchService.bulkInsert(catKeyField, esProps.getCategory().get("index"), esProps.getCategory().get("type"));
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
            SearchResponse searchResponse = esTransportClient.getObject().prepareSearch(esProps.getGoods().get("index"))
                    .setTypes(esProps.getGoods().get("type"))
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
