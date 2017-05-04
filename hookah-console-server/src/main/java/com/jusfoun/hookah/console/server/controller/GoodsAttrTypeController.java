package com.jusfoun.hookah.console.server.controller;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsAttrTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 */

//@RestController
@RequestMapping("attrType")
public class GoodsAttrTypeController {
    @Resource
    GoodsAttrTypeService goodsAttrTypeService;

    /**
     * 新增商品属性分类
     * @param obj
     * @return
     */
    @RequestMapping("add")
    public ReturnData addAttrType(GoodsAttrType obj) {
        ReturnData<List<Category>> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            obj = goodsAttrTypeService.insert(obj);
            if(obj == null) {
                throw new HookahException("操作失败");
            }
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 删除商品属性分类
     * @param id
     * @return
     */
    @RequestMapping("del")
    public ReturnData delAttrType(String id, String shopId) {
        ReturnData<List<GoodsAttrType>> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
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
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
