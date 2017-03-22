package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bingbing wu
 * @date 2017/3/13 下午9:33
 * @desc
 */
@RestController
@RequestMapping("goods/back")
public class GoodsBackController {
    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;

    @RequestMapping("add")
    public ReturnData addGoodsBack(GoodsVo model) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsService.addGoods(model);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 根据id查询商品详情
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public @ResponseBody ReturnData findGoodsById(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("goodsId", id));
            filters.add(Condition.eq("domainId", "123"));
            filters.add(Condition.in("goodsStatus", new Byte[]{1,2}));
            Goods goods = goodsService.selectOne(filters);
            if (goods == null || goods.getGoodsId() == null) {
                returnData.setCode(ExceptionConst.empty);
            }else {
                GoodsVo goodsVo = new GoodsVo();
                BeanUtils.copyProperties(goods, goodsVo);
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
}
