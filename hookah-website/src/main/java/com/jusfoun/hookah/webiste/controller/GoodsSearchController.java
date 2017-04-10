package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.vo.EsGoodsVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangjl on 2017-3-30.
 */
@RestController
@RequestMapping("/search")
public class GoodsSearchController {
    @Autowired
    ElasticSearchService elasticSearchService;

    @RequestMapping(value = "/v1/goods", method = RequestMethod.POST)
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

    @RequestMapping("/v1/goods/suggest")
    public ReturnData suggest (@RequestParam String prefix, @RequestParam(required = false) Integer size) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            if(size == null) {
                returnData.setData(elasticSearchService.goodsSuggestion(prefix));
            }else {
                returnData.setData(elasticSearchService.goodsSuggestion(prefix, size));
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
