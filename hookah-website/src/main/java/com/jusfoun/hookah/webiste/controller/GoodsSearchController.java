package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.vo.EsGoodsVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangjl on 2017-3-30.
 */
@RestController
@RequestMapping("/search")
public class GoodsSearchController {
    @Autowired
    ElasticSearchService elasticSearchService;

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
}
