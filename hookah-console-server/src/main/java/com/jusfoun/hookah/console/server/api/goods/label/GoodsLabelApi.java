package com.jusfoun.hookah.console.server.api.goods.label;

import com.jusfoun.hookah.console.server.controller.BaseController;
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
@RequestMapping(value = "/api/label")
public class GoodsLabelApi extends BaseController {
    @Resource
    GoodsLabelService goodsLabelService;

    @RequestMapping(value = "/create")
    public ReturnData create(String type, String labels) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsLabelService.createLabels(labels, getCurrentUser().getUserId(), type);
        } catch (Exception e) {
            logger.error("创建商品标签错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("创建商品标签错误！" + e.getMessage());
        }
        return returnData;
    }

    @RequestMapping(value = "/delete")
    public ReturnData delete(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsLabelService.delete(id);
        } catch (Exception e) {
            logger.error("删除商品标签错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("删除商品标签错误！" + e.getMessage());
        }
        return returnData;
    }

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
