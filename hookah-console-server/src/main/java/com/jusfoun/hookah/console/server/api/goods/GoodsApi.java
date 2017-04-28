package com.jusfoun.hookah.console.server.api.goods;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.console.server.util.DictionaryUtil;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
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
            if(mgShelvesGoods.getGoodsIdList() != null && mgShelvesGoods.getGoodsIdList().size() > 0){
                filters.add(Condition.notIn("goodsId", mgShelvesGoods.getGoodsIdList().toArray()));
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
                filters.add(Condition.eq("goodsName", goodsName.trim()));
            }
            if(StringUtils.isNotBlank(goodsSn)){
                filters.add(Condition.eq("goodsSn", goodsSn.trim()));
            }
            if(StringUtils.isNotBlank(keywords)){
                filters.add(Condition.eq("keywords", keywords.trim()));
            }
            if(StringUtils.isNotBlank(shopName)){
                filters.add(Condition.eq("shopName", shopName.trim()));
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
            if(goods != null){
                if(goods.getGoodsArea() != null && !"全部".equals(goods.getGoodsArea())){
                    goods.setGoodsArea(DictionaryUtil.getRegionById(goods.getGoodsArea()).getMergerName());
                }
                BeanUtils.copyProperties(goods, goodsVo);
            }
            if(mgGoods != null){
                BeanUtils.copyProperties(mgGoods, goodsVo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(goodsVo);
    }

    @RequestMapping(value = "/delGoodsById", method = RequestMethod.POST)
    public ReturnData delGoods(String goodsId, String flag) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            if(StringUtils.isNotBlank(flag)){

                Goods goods = new Goods();
                goods.setGoodsId(goodsId);
                if("1".equals(flag)){  //删除
                    goods.setIsDelete(Byte.parseByte("0"));
                    goods.setIsOnsale(Byte.parseByte("0"));// 0 下架；1 上架；2 管理员强制下架
                }else if("2".equals(flag)){ // 下架
                    goods.setIsOnsale(Byte.parseByte("2"));
                }else if("3".equals(flag)){ // 上架
                    goods.setIsOnsale(Byte.parseByte("1"));
                }
                goodsService.updateByIdSelective(goods);
            }else{

            }

        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 强制下架
     * @param goodsId           商品id
     * @param offReason         下架理由
     * @return
     */
    @RequestMapping(value = "/forceOff", method = RequestMethod.POST)
    public ReturnData forceOff(String goodsId, String offReason) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Goods goods = new Goods();
            goods.setGoodsId(goodsId);
            goods.setOffReason(offReason);
            goods.setIsOnsale(Byte.parseByte(HookahConstants.SaleStatus.forceOff.getCode()));
            goods.setCheckStatus(Byte.parseByte(HookahConstants.CheckStatus.audit_fail.getCode()));

            //消息 es

            goodsService.updateByIdSelective(goods);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


}
