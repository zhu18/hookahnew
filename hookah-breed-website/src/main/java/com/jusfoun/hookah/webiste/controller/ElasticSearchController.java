package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
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
public class ElasticSearchController {
    @Autowired
    ElasticSearchService elasticSearchService;

    /**
     * 根据条件查询商品信息
     * @param vo
     * @return
     */
    @RequestMapping(value = "/v1/goods", method = RequestMethod.POST)
    public ReturnData searchByCondition(@RequestBody(required = false) EsGoodsVo vo) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            if(vo == null) {
                vo = new EsGoodsVo();
            }
            returnData.setData(elasticSearchService.search(vo));
            returnData.setData2(elasticSearchService.getTypes(vo.getEsGoods()));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 检索提示
     * @param prefix
     * @param size
     * @return
     */
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


    /**
     * 按分类fullName进行查询
     * @param keyword
     * @return
     */
    @RequestMapping("/v1/category")
    public ReturnData searchCategory(String keyword, Integer size) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            if(size == null) {
                size = HookahConstants.PAGE_SIZE;
            }
            returnData.setData(elasticSearchService.searchCategory(keyword, size));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
