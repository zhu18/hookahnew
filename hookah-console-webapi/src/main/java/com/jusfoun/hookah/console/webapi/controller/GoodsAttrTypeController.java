package com.jusfoun.hookah.console.webapi.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.domain.ResultJson;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.rpc.api.GoodsAttrTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 */

//@RestController
@RequestMapping("attrType")
public class GoodsAttrTypeController {
    @Autowired
    GoodsAttrTypeService goodsAttrTypeService;

    /**
     * 新增商品属性分类
     * @param obj
     * @return
     */
    @RequestMapping("add")
    public ResultJson addAttrType(GoodsAttrType obj) {
        ResultJson<List<Category>> resultJson = new ResultJson<>();
        resultJson.setCode(HookahConstants.RESULT_SUCCESS);
        try {
            int i = goodsAttrTypeService.insert(obj);
            if(i < 0) {
                throw new HookahException("操作失败");
            }
        }catch (Exception e) {
            resultJson.setCode(HookahConstants.RESULT_ERROR);
            resultJson.setMessage(e.toString());
            e.printStackTrace();
        }
        return resultJson;
    }

    /**
     * 删除商品属性分类
     * @param id
     * @return
     */
    @RequestMapping("del")
    public ResultJson delAttrType(String id, String shopId) {
        ResultJson<List<GoodsAttrType>> resultJson = new ResultJson<>();
        resultJson.setCode(HookahConstants.RESULT_SUCCESS);
        try {
            //TODO 判断属性分类下是否有商品
            GoodsAttrType attrType = new GoodsAttrType();
            attrType.setTypeId(id);
            attrType.setDomainId(shopId);
            int i = goodsAttrTypeService.delete(attrType);
            if(i < 0) {
                throw new HookahException("操作失败");
            }
        }catch (Exception e) {
            resultJson.setCode(HookahConstants.RESULT_ERROR);
            resultJson.setMessage(e.toString());
            e.printStackTrace();
        }
        return resultJson;
    }
}
