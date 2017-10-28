package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.GoodsLabel;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjl on 2017-10-28.
 */
@RestController
@RequestMapping("/goodsStorage")
public class GoodsStorageContorller extends BaseController {
    @Autowired
    GoodsStorageService goodsStorageService;

    /**
     * 根据标签查询数据
     * @param storageId
     * @param typeId
     * @param labels
     * @return
     */
    @RequestMapping(value = "/searchByLabels")
    public ReturnData searchByLabels(String storageId, Integer typeId, String labels, Integer currentPage, Integer pageSize) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<GoodsLabel> goodsLabels = new ArrayList<>();
            returnData.setData(goodsStorageService.searchByLabels(storageId, typeId, labels, currentPage,  pageSize, goodsLabels));
            returnData.setData2(goodsLabels);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
