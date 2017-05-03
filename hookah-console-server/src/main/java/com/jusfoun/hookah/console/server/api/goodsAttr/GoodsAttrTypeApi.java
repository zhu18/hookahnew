package com.jusfoun.hookah.console.server.api.goodsAttr;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.domain.vo.GoodsAttrTypeVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsAttrTypeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ctp
 */

@RestController
@RequestMapping("/api/attrType")
public class GoodsAttrTypeApi extends BaseController{

    @Resource
    GoodsAttrTypeService goodsAttrTypeService;



    @RequestMapping("allTree")
    public ReturnData selectTree(){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<GoodsAttrTypeVo> goodsAttrTypeVos = goodsAttrTypeService.findTree();
            if(goodsAttrTypeVos == null) {
                throw new HookahException("操作失败");
            }
            returnData.setData(goodsAttrTypeVos);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 新增商品属性分类
     * @param obj
     * @return
     */
    @RequestMapping("add")
    public ReturnData addAttrType(GoodsAttrType obj) throws HookahException {
        obj.setUserId(getCurrentUser().getUserId());
        return goodsAttrTypeService.addAttr(obj);
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

    @RequestMapping(value = "edit" , method = RequestMethod.POST)
    public ReturnData editCategory(GoodsAttrType attrType) {
        return goodsAttrTypeService.editAttrType(attrType);
    }


    @RequestMapping(value = "/delete/", method = RequestMethod.POST)
    public ReturnData deleteCategory(String typeId,String parentId) {
        return goodsAttrTypeService.deleteById(typeId,parentId);
    }
}
