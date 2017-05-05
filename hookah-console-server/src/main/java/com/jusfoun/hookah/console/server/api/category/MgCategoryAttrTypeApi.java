package com.jusfoun.hookah.console.server.api.category;

import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ctp on 2017/5/2.
 */
@RestController
@RequestMapping("/api/mgCateAttrType")
public class MgCategoryAttrTypeApi {

    @Resource
    MgCategoryAttrTypeService mgCategoryAttrTypeService;

    @RequestMapping(value="/findGoodsAttrByCateId/{cateId}")
    public ReturnData findGoodsAttrByCateId(@PathVariable String cateId){
        return  mgCategoryAttrTypeService.findGoodsAttrByCatId(cateId);
    }

    @RequestMapping(value="/addMgGoodsAttr")
    public ReturnData addMgGoodsAttr(String cateId,String attrTypeId){
        return  mgCategoryAttrTypeService.addMgGoodsAttr(cateId,attrTypeId);
    }

    @RequestMapping(value="/removeMgGoodsAttr")
    public ReturnData removeMgGoodsAttr(String cateId,String attrTypeId){
        return  mgCategoryAttrTypeService.removeMgGoodsAttr(cateId,attrTypeId);
    }
}
