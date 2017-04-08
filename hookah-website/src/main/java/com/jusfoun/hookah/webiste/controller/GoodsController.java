package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by wangjl on 2017-3-20.
 */
@Controller
@RequestMapping("1/goods")
public class GoodsController {
    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;

    /**
     * 根据id查询商品详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("findById")
    public ReturnData findGoodsById(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            GoodsVo goodsVo = new GoodsVo();
            BeanUtils.copyProperties(goodsService.selectById(id), goodsVo);
            if (goodsVo == null) {
                returnData.setCode(ExceptionConst.empty);
            }else {
                MgGoods mgGoods = mgGoodsService.selectById(id);
                if (mgGoods != null) {
                    goodsVo.setFormatList(mgGoods.getFormatList());
                    goodsVo.setImgList(mgGoods.getImgList());
                    goodsVo.setAttrTypeList(mgGoods.getAttrTypeList());
                }
                returnData.setData(goodsVo);
            }
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


}
