package com.jusfoun.hookah.console.server.api.goods;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsCheckService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsShelvesGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/7 上午10:50
 * @desc
 */
@RestController
@RequestMapping(value = "/api/goods")
public class GoodsApi extends BaseController{

    @Resource
    GoodsService goodsService;

    @Resource
    GoodsCheckService goodsCheckService;

    @Resource
    MgGoodsService mgGoodsService;

    @Resource
    MgGoodsShelvesGoodsService mgGoodsShelvesGoodsService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize, HttpServletRequest request) {
        Pagination<Goods> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            //只查询商品状态为未删除的商品
            filters.add(Condition.eq("isDelete", 1));
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    @RequestMapping(value = "/allNotInShelf", method = RequestMethod.GET)
    public ReturnData getListInPage2(String currentPage, String pageSize, String shelvesGoodsId) {
        Pagination<Goods> page = new Pagination<>();
        try {

            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));

            MgShelvesGoods mgShelvesGoods = mgGoodsShelvesGoodsService.selectById(shelvesGoodsId);
            List<String> gidList = mgShelvesGoods.getGoodsIdList();
            if(gidList != null){
                filters.add(Condition.notIn("goodsId", gidList.toArray()));
            }

            //只查询商品状态为未删除的商品
            filters.add(Condition.eq("isDelete", 1));
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    /**
     * 查询 未删除 审核中 下架  状态的商品
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/allNotCheck", method = RequestMethod.GET)
    public ReturnData allNotCheck(String currentPage, String pageSize,
                                  String goodsName, String goodsSn,
                                  String keywords, String shopName) {
        Pagination<Goods> page = new Pagination<>();
        try {

            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));

            //只查询商品状态为未删除  审核中  下架状态  的商品
            filters.add(Condition.eq("isDelete", 1));
            filters.add(Condition.eq("checkStatus", 0));
            filters.add(Condition.eq("isOnsale", 0));
            if(StringUtils.isNotBlank(goodsName)){
                filters.add(Condition.eq("goodsName", goodsName));
            }
            if(StringUtils.isNotBlank(goodsSn)){
                filters.add(Condition.eq("goodsSn", goodsSn));
            }
            if(StringUtils.isNotBlank(keywords)){
                filters.add(Condition.eq("keywords", keywords));
            }
            if(StringUtils.isNotBlank(shopName)){
                filters.add(Condition.eq("shopName", shopName));
            }

            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    /**
     * 获取商品的完整信息
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/getGoodsInfo", method = RequestMethod.GET)
    public ReturnData allNotCheck(String goodsId) {
        GoodsVo goodsVo = new GoodsVo();
        try {

            Goods goods = goodsService.selectById(goodsId);
            MgGoods mgGoods = mgGoodsService.selectById(goodsId);
            BeanUtils.copyProperties(goods, goodsVo);
            BeanUtils.copyProperties(mgGoods, goodsVo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(goodsVo);
    }

    /**
     * 商品审核
     * @param goodsCheck
     * @return
     */
    @RequestMapping(value = "/goodsCheck", method = RequestMethod.POST)
    public ReturnData goodsCheck(GoodsCheck goodsCheck) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsCheck.setCheckUser(getCurrentUser().getUserName());
            goodsCheckService.insertRecord(goodsCheck);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


}
