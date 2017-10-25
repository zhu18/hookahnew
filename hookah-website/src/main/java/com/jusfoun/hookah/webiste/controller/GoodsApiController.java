package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Gring on 2017/7/31.
 */
@RestController
@RequestMapping(value = "/api/goodsApi")
public class GoodsApiController extends BaseController {

    @Resource
    GoodsService goodsService;

    /**
     * 获取商品类型的完整信息
     * @param url
     * @return
     */
    @RequestMapping(value = "/fetchGoodsApiInfo", method = RequestMethod.GET)
    public ReturnData getGoodsTypeTree(String url) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        GoodsVo goodsVo = new GoodsVo();
        try {
            goodsVo = goodsService.fetchGoodsApiInfo(url);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ReturnData.success(goodsVo);
    }

}
