package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsLabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangjl on 2017-10-24.
 */
@RestController
@RequestMapping("/goodsLabel")
public class GoodsLabelController extends BaseController {
    @Resource
    GoodsLabelService goodsLabelService;
    @RequestMapping(value = "/findAll")
    public ReturnData findAll() {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(goodsLabelService.queryAllLabels());
        } catch (Exception e) {
            logger.error("查询商品标签错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("查询商品标签错误！" + e.getMessage());
        }
        return returnData;
    }
}
