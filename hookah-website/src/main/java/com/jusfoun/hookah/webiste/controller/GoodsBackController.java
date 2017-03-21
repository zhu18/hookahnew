package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ReturnData addGoodsBack(@RequestBody GoodsVo obj) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
//            goodsService.addGoods(obj);
            Goods goods = new Goods();
            goods.setGoodsName("hello");
            goodsService.insert(goods);
//            if (obj == null)
//                throw new HookahException("空数据！");
//            // 将数据插入数据库
//            int i = goodsService.insert(obj);
//            if(i < 0)
//                throw new HookahException("操作失败");
//            // 将数据放入mongo
//            MgGoods mgGoods = new MgGoods();
//            mgGoods.setAttrTypeList(obj.getAttrTypeList());
//            mgGoods.setFormatList(obj.getFormatList());
//            mgGoods.setImgList(obj.getImgList());
//            mgGoods.setGoodsId(obj.getGoodsId());
//            mgGoodsService.insert(mgGoods);
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
    public ReturnData findGoodsById(String id, @RequestParam(value = "123")String domainId) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("goodsId", id));
            filters.add(Condition.eq("domainId", domainId));
            filters.add(Condition.in("goodsStatus", new Byte[]{1,2}));
            GoodsVo goodsVo = (GoodsVo) goodsService.selectOne(filters);
            if (goodsVo == null) {
                returnData.setMessage(ExceptionConst.empty);
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
}
