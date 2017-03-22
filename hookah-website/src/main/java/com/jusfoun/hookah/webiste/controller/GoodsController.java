package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangjl on 2017-3-20.
 */
@Controller
@RequestMapping("goods")
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
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(){
        return "/goods/list";
    }

    /**
     * 商品查询
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "details", method = RequestMethod.GET)
    public String details(String id, Model model){
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goodsService.selectById(id), goodsVo);
        if(goodsVo != null) {
            MgGoods mgGoods = mgGoodsService.selectById(id);
            if (mgGoods != null) {
                goodsVo.setFormatList(mgGoods.getFormatList());
                goodsVo.setImgList(mgGoods.getImgList());
                goodsVo.setAttrTypeList(mgGoods.getAttrTypeList());
            }
        }
        model.addAttribute("goods", goodsVo);
        return "/goods/details";
    }



}
