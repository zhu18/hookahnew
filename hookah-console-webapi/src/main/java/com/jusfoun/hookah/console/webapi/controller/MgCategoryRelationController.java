package com.jusfoun.hookah.console.webapi.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.ResultJson;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 */
@RestController
@RequestMapping("mgCategory")
public class MgCategoryRelationController {
    @Autowired
    MgCategoryAttrTypeService mgCategoryAttrTypeService;

    @RequestMapping(value = "addAttrType", method= RequestMethod.POST)
    public ResultJson addRelationMongo(@RequestBody MgCategoryAttrType obj) {
        ResultJson<List<Category>> resultJson = new ResultJson<>();
        resultJson.setCode(HookahConstants.RESULT_SUCCESS);
        try {
            int i = mgCategoryAttrTypeService.insert(obj);
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
