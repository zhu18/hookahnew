package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.vo.GoodsLabelsPagVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            GoodsLabelsPagVo vo = goodsStorageService.searchByLabels(storageId, typeId, labels, currentPage,  pageSize);
            returnData.setData(vo.getPagination());
            returnData.setData2(vo.getGoodsLabelList());
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
