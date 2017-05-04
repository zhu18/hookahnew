package com.jusfoun.hookah.console.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jusfoun.hookah.console.server.config.Constants;
import com.jusfoun.hookah.console.server.config.ESTemplate;
import com.jusfoun.hookah.console.server.config.ESTransportClient;
import com.jusfoun.hookah.console.server.util.DictionaryUtil;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.CategoryMapper;
import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.core.domain.es.*;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.EsGoodsVo;
import com.jusfoun.hookah.core.domain.vo.EsTreeVo;
import com.jusfoun.hookah.core.domain.vo.EsTypesVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.AnnotationUtil;
import com.jusfoun.hookah.rpc.api.CategoryService;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import com.jusfoun.hookah.rpc.api.GoodsAttrTypeService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
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
    @Autowired
    CategoryService categoryService;
    @Autowired
    MgGoodsService mgGoodsService;
    @Autowired
    GoodsAttrTypeService goodsAttrTypeService;
    @Autowired
    CategoryMapper categoryMapper;

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
                    maps = this.goodsBulkInsert();
                    break;
                case "category":
                    maps = this.categoryBulkInsert();
            }
            esTemplate.bulkProcessorIndex(esTransportClient.getObject(), index, type,
                    keyField, 5, 10000, 60, 1, maps);
        }else {
            //TODO 从消息队列获取未读取数据
        }
    }

    /**
     * 获取新增商品list
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> goodsBulkInsert() throws Exception {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<EsGoods> list = goodsMapper.getNeedEsGoods();
        if (list != null && list.size() > 0) {
            for(EsGoods goods : list) {
                maps.add(completionEsGoods(goods));
            }
        }
        return maps;
    }

    @Override
    public Map<String, Object> completionEsGoods(EsGoods goods) throws IllegalAccessException {
        // 如果有地区给省市县赋值
        if(goods.getGoodsArea() != null && !"".equals(goods.getGoodsArea())) {
            String[] region = goods.getGoodsAreas().split(" ");
            if(region.length >= 2)
                goods.setAreaCountry(region[1]);
            if(region.length >= 3)
                goods.setAreaProvince(region[2]);
            if(region.length == 4)
                goods.setAreaCity(region[3]);
        }
        //查询mongo中的数据
        MgGoods mgGoods = mgGoodsService.selectById(goods.getGoodsId());
        goods.setSuggest(goods.getGoodsName());
        //获取商品属性
        if(mgGoods != null) {
            List<MgCategoryAttrType.AttrTypeBean> list1 = mgGoods.getAttrTypeList();
            StringBuffer attrType = new StringBuffer();
            StringBuffer attr = new StringBuffer();
            for(MgCategoryAttrType.AttrTypeBean bean : list1) {
                attrType.append(bean.getTypeId()).append(" ");

                if(bean.getAttrList() != null) {
                    for (MgCategoryAttrType.AttrTypeBean.AttrBean attrBean : bean.getAttrList()) {
                        attr.append(attrBean.getAttrId()).append(" ");
                    }
                }else {
                    attrType = new StringBuffer();
                }
            }
            if(!"".equals(attrType.toString()) && !"".equals(attr.toString())) {
                goods.setAttrTypeId(attrType.toString().split(" "));
                goods.setAttrId(attr.toString().split(" "));
                goods.setAttrIds(attrType.toString() + attr.toString());
            }
        }
        return AnnotationUtil.convert2Map(goods);
    }

    /**
     * 获取分类商品list
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> categoryBulkInsert() throws Exception {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<EsCategory> list = categoryMapper.getNeedEsCat();
        if (list != null && list.size() > 0) {
            for(EsCategory category : list) {
                if(StringUtils.isNotBlank(category.getFullIds())) {
                    StringBuffer stringBuffer = new StringBuffer();
                    for (String id : category.getFullIds().split(" ")) {
                        stringBuffer.append(DictionaryUtil.getCategoryById(id) != null
                                ? DictionaryUtil.getCategoryById(id).getCatName() : "未知").append(">");
                    }
                    category.setFullName(stringBuffer.substring(0, stringBuffer.length() - 1));
                }
                maps.add(AnnotationUtil.convert2Map(category));
            }
        }
        return maps;
    }

    @Override
    public void deleteIndex(String indexName) throws Exception{
        esTemplate.deleteIndex(esTransportClient.getObject(), indexName);
    }

    @Override
    public Pagination search(EsGoodsVo vo) throws Exception {
        Integer pageSize = vo.getPageSize();
        Integer pageNum = vo.getPageNumber();
        String orderField = vo.getOrderFiled();
        String order = vo.getOrder();

        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        Map<String, Object> map = new HashedMap();
        if(vo.getEsGoods() != null) {
            map = AnnotationUtil.convert2Map(vo.getEsGoods());
        }
        if(vo.getRange() == null) {
            esTemplate.search(esTransportClient.getObject(), Constants.GOODS_INDEX,
                    Constants.GOODS_TYPE, map, pagination, orderField, order, "goodsName");
        }else {
            esTemplate.search(esTransportClient.getObject(), Constants.GOODS_INDEX,
                    Constants.GOODS_TYPE, map, pagination, orderField, order, vo.getRange(), "goodsName");
        }
        pagination.getTotalPage();
        System.out.println(pagination.getTotalPage());
        return pagination;
    }

    /**
     * 商品搜索Suggest
     * @param prefix
     * @param size
     * @return
     * @throws Exception
     */
    @Override
    public List<String> goodsSuggestion(String prefix, Integer size) throws Exception {
        if(size == null)
            size = HookahConstants.PAGE_SIZE;
        List<String> list = esTemplate.suggest(esTransportClient.getObject(), prefix, size, Constants.GOODS_INDEX,
                Constants.GOODS_TYPE, "suggest", "suggest1");
        return list;
    }

    @Override
    public List<String> goodsSuggestion(String prefix) throws Exception {
        return goodsSuggestion(prefix, null);
    }

    @Override
    public EsTypesVo getTypes(EsGoods goods) throws Exception {
        List<EsAgg> listCnt = new ArrayList<>();
        EsTypesVo esTypesVo = new EsTypesVo();
        List<EsTreeVo<Category>> categoryList = new ArrayList<>();
        List<EsTreeVo<GoodsAttrType>> goodsAttrTypeList = new ArrayList<>();
        List<EsTreeVo<Region>> areaCountryList = new ArrayList<>();
        List<EsTreeVo<Region>> areaProvinceList = new ArrayList<>();
        List<EsTreeVo<Region>> areaCityList = new ArrayList<>();
        //添加需要聚合的字段
        if(goods != null && StringUtils.isNotBlank(goods.getCatIds()) && goods.getCatIds().length() != 9) {
            listCnt.add(new EsAgg(HookahConstants.GOODS_AGG_CATEGORY, HookahConstants.GOODS_AGG_CATEGORY_FIELD));
        }
        listCnt.add(new EsAgg(HookahConstants.GOODS_AGG_ATTR, HookahConstants.GOODS_AGG_ATTR_FIELD));
        listCnt.add(new EsAgg(HookahConstants.GOODS_AGG_ATTR_TYPE, HookahConstants.GOODS_AGG_ATTR_TYPE_FIELD));
        listCnt.add(new EsAgg(HookahConstants.GOODS_AGG_AREA_COUNTRY, HookahConstants.GOODS_AGG_AREA_COUNTRY_FIELD));
        listCnt.add(new EsAgg(HookahConstants.GOODS_AGG_AREA_PROVINCE, HookahConstants.GOODS_AGG_AREA_PROVINCE_FIELD));
        listCnt.add(new EsAgg(HookahConstants.GOODS_AGG_AREA_CITY, HookahConstants.GOODS_AGG_AREA_CITY_FIELD));
        //按查询条件查询分类集合
        Map<String, List<EsAggResult>> map = esTemplate.getCounts(esTransportClient.getObject(),
                Constants.GOODS_INDEX, Constants.GOODS_TYPE, goods != null ? AnnotationUtil.convert2Map(goods) : null, listCnt);
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, List<EsAggResult>> entry : map.entrySet()) {
                switch (entry.getKey()) {
                    case HookahConstants.GOODS_AGG_CATEGORY :
                        this.getCategoryTypes(entry, categoryList);
                        break;
                    case HookahConstants.GOODS_AGG_ATTR_TYPE:
                        this.getGoodsAttrType(entry, goodsAttrTypeList);
                        break;
                    case HookahConstants.GOODS_AGG_ATTR :
                        this.getGoodsAttr(entry, goodsAttrTypeList);
                        break;
                    case HookahConstants.GOODS_AGG_AREA_COUNTRY :
                        this.getRegions(entry, areaCountryList);
                        break;
                    case HookahConstants.GOODS_AGG_AREA_PROVINCE :
                        this.getRegions(entry, areaProvinceList);
                        break;
                    case HookahConstants.GOODS_AGG_AREA_CITY :
                        this.getRegions(entry, areaCityList);
                        break;

                }
            }
        }
        esTypesVo.setCategoryList(categoryList);
        esTypesVo.setGoodsAttrTypeList(goodsAttrTypeList);
        esTypesVo.setAreaCountryList(areaCountryList);
        esTypesVo.setAreaProvinceList(areaProvinceList);
        esTypesVo.setAreaCityList(areaCityList);
        return esTypesVo;
    }

    /**
     * 获取有效的商品分类
     * @param entry
     * @param categoryList
     */
    private void getCategoryTypes(Map.Entry<String, List<EsAggResult>> entry, List<EsTreeVo<Category>> categoryList) {
        List<EsAggResult> esAggResults = entry.getValue();
        for(EsAggResult result : esAggResults) {
            Category category = DictionaryUtil.getCategoryById(result.getId());
            if(category != null && category.getLevel() == 3) {
                EsTreeVo<Category> categoryVo = new EsTreeVo(category.getCatId(), category.getCatName(),
                        category.getLevel(), category.getParentId(), result.getCnt());
                categoryList.add(categoryVo);
            }
        }
    }

    /**
     * 获取有效的商品属性分类
     * @param entry
     * @param goodsAttrTypeList
     */
    private void getGoodsAttrType(Map.Entry<String, List<EsAggResult>> entry, List<EsTreeVo<GoodsAttrType>> goodsAttrTypeList) {
        List<EsAggResult> esAggResults = entry.getValue();
        for(EsAggResult result : esAggResults) {
            GoodsAttrType goodsAttrType = DictionaryUtil.getAttrById(result.getId());
            if(goodsAttrType != null) {
                EsTreeVo<GoodsAttrType> esGoodsAttrVo = new EsTreeVo(goodsAttrType.getTypeId(), goodsAttrType.getTypeName(),
                        goodsAttrType.getLevel(), goodsAttrType.getParentId(), result.getCnt());
                goodsAttrTypeList.add(esGoodsAttrVo);
            }
        }
    }

    /**
     * 获取有效的商品属性
     * @param entry
     * @param goodsAttrTypeList
     */
    private void getGoodsAttr(Map.Entry<String, List<EsAggResult>> entry, List<EsTreeVo<GoodsAttrType>> goodsAttrTypeList) {
        List<EsAggResult> esAggResults = entry.getValue();
        if(goodsAttrTypeList != null) {
            for(EsTreeVo<GoodsAttrType> vo : goodsAttrTypeList) {
                List<EsTreeVo<GoodsAttrType>> children =  new ArrayList<>();
                for(EsAggResult result : esAggResults) {
                    GoodsAttrType goodsAttr = DictionaryUtil.getAttrById(result.getId());
                    if(goodsAttr != null) {
                        if(vo.getNodeId().equals(goodsAttr.getParentId())) {
                            EsTreeVo<GoodsAttrType> esGoodsAttrVo = new EsTreeVo(goodsAttr.getTypeId(), goodsAttr.getTypeName(),
                                    goodsAttr.getLevel(), goodsAttr.getParentId(), result.getCnt());
                            children.add(esGoodsAttrVo);
                        }
                    }
                }
                vo.setChildren(children);
            }
        }
    }

    /**
     * 获取有效的商品分类
     * @param entry
     * @param regionList
     */
    private void getRegions(Map.Entry<String, List<EsAggResult>> entry, List<EsTreeVo<Region>> regionList) {
        List<EsAggResult> esAggResults = entry.getValue();
        for(EsAggResult result : esAggResults) {
            Region region = DictionaryUtil.getRegionById(result.getId());
            if(region != null) {
                EsTreeVo<Region> regionVo = new EsTreeVo(region.getId() + "", region.getName(),
                        region.getLel(), region.getPid() + "", result.getCnt());
                regionList.add(regionVo);
            }
        }
    }

    @Override
    public void deleteById(String indexName, String type, String id) throws Exception {
        esTemplate.deleteById(esTransportClient.getObject(), indexName,
                type, id);
    }

    @Override
    public void upsertById(String indexName, String type, String goodsId, Map<String, Object> map) throws Exception {
        esTemplate.upsertById(esTransportClient.getObject(), indexName,
                type, goodsId, map);
    }

    @Override
    public List searchCategory(String keyword) throws Exception {
        Pagination pagination = new Pagination();
        pagination.setPageSize(HookahConstants.PAGE_SIZE_20);
        pagination.setCurrentPage(HookahConstants.PAGE_NUM);
        Map<String, Object> map = new HashedMap();
        map.put("fullName", keyword);
        esTemplate.search(esTransportClient.getObject(), Constants.GOODS_CATEGORY_INDEX,
                Constants.GOODS_CATEGORY_TYPE, map, pagination, null, null, "fullName");
        return pagination.getList();
    }
}
