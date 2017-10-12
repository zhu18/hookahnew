package com.jusfoun.hookah.crowd.util;

import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.crowd.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wangjl on 2017-4-13.
 * 字典表
 */
@Component
public class DictionaryUtil {

//    @Autowired
//    CategoryService categoryService;
//
//    @Autowired
//    GoodsAttrTypeService goodsAttrTypeService;

    @Autowired
    RegionService regionService;

    private static DictionaryUtil dictionaryUtil;

    @PostConstruct
    public void init() {
        dictionaryUtil = this;
//        dictionaryUtil.categoryService = this.categoryService;
//        dictionaryUtil.goodsAttrTypeService = this.goodsAttrTypeService;
        dictionaryUtil.regionService = this.regionService;
    }

//    /**
//     * 获取商品分类信息
//     * @param id 商品分类id
//     * @return
//     */
//    public static Category getCategoryById(String id) {
//        return dictionaryUtil.categoryService.selectById(id);
//    }
//
//    /**
//     * 查询商品属性分类及商品属性分类信息
//     * @param id
//     * @return
//     */
//    public static GoodsAttrType getAttrById(String id) {
//        return dictionaryUtil.goodsAttrTypeService.selectById(id);
//    }

    public static Region getRegionById(String id) {
        return dictionaryUtil.regionService.selectById(id);
    }
}
