package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bingbing wu
 * @date 2017/3/13 下午9:33
 * @desc
 */
@Controller
@RequestMapping("/goods/back")
public class GoodsBackController extends BaseController {

    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;


    @RequestMapping("/add")
    @ResponseBody
    public ReturnData addGoodsBack(@Valid @RequestBody GoodsVo obj) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            obj.setAddUser(userId);
            obj.setIsOnsale(HookahConstants.GOODS_STATUS_ONSALE);
            goodsService.addGoods(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData list(String pageNumber, String pageSize, String goodsName, Byte checkStatus) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Pagination<Goods> page = new Pagination<>();
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            filters.add(Condition.eq("isDelete", 1));

            String userId = this.getCurrentUser().getUserId();
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(pageNumber)) {
                pageNumberNew = Integer.parseInt(pageNumber);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            if (StringUtils.isNotBlank(goodsName)) {
                filters.add(Condition.eq("goodsName", goodsName.trim()));
            }
            if (checkStatus != null) {
                filters.add(Condition.like("checkStatus", checkStatus));
            }
            filters.add(Condition.eq("addUser", userId));
            page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            returnData.setData(page);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


    /**
     * 根据id查询商品详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public ReturnData findGoodsById(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Goods goods = goodsService.selectById(id);
            if (goods == null || goods.getGoodsId() == null) {
                returnData.setCode(ExceptionConst.empty);
            } else {
                GoodsVo goodsVo = new GoodsVo();
                BeanUtils.copyProperties(goods, goodsVo);
                MgGoods mgGoods = mgGoodsService.selectById(id);
                if (mgGoods != null) {
                    goodsVo.setFormatList(mgGoods.getFormatList());
                    goodsVo.setImgList(mgGoods.getImgList());
                    goodsVo.setAttrTypeList(mgGoods.getAttrTypeList());
                    goodsVo.setApiInfo(mgGoods.getApiInfo());
                }
                returnData.setData(goodsVo);
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ReturnData update(@Valid @RequestBody GoodsVo obj) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsService.updateGoods(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 商品删除：/status/del
     * 商品下架：/status/offSale
     * @param goodsId
     * @param opera
     * @return
     */
    @RequestMapping("/status/{opera}")
    @ResponseBody
    public ReturnData update(String goodsId, @PathVariable String opera) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            int i = goodsService.updateGoodsStatus(goodsId, opera);
            if(i <= 0)
                throw new HookahException("操作失败！");
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 商品上架
     * @param goodsId
     * @param dateTime
     * @return
     */
    @RequestMapping("/onsale")
    @ResponseBody
    public ReturnData onsale(String goodsId, @RequestParam(required = false) String dateTime) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            int i = goodsService.onsale(goodsId, dateTime);
            if(i <= 0)
                throw new HookahException("操作失败！");
            else {
                returnData.setMessage("商品操作成功，请耐心等待审核！");
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
