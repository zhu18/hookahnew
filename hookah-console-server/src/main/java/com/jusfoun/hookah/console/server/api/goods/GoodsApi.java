package com.jusfoun.hookah.console.server.api.goods;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.console.server.util.DictionaryUtil;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsCheckedVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @Resource
    MqSenderService mqSenderService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize,
                                    String goodsName, String goodsSn,
                                    String keywords, String shopName,
                                    Byte checkStatus, Byte onSaleStatus
    ) {
        Pagination<Goods> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            //只查询商品状态为未删除的商品
            filters.add(Condition.eq("isDelete", 1));

            if(StringUtils.isNotBlank(goodsName)){
                filters.add(Condition.like("goodsName", goodsName.trim()));
            }
            if(StringUtils.isNotBlank(goodsSn)){
                filters.add(Condition.like("goodsSn", goodsSn.trim()));
            }
            if(StringUtils.isNotBlank(keywords)){
                filters.add(Condition.like("keywords", keywords.trim()));
            }
            if(StringUtils.isNotBlank(shopName)){
                filters.add(Condition.like("shopName", shopName.trim()));
            }
            if(checkStatus != null && checkStatus != -1){
                filters.add(Condition.eq("checkStatus", checkStatus));
            }
            if(onSaleStatus != null && onSaleStatus != -1){
                filters.add(Condition.eq("isOnsale", onSaleStatus));
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

    @RequestMapping(value = "/allNotInShelf", method = RequestMethod.GET)
    public ReturnData getListInPage2(String currentPage, String pageSize, String shelvesGoodsId, String searchName) {
        Pagination<Goods> page = new Pagination<>();
        try {

            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));

            MgShelvesGoods mgShelvesGoods = mgGoodsShelvesGoodsService.selectById(shelvesGoodsId);

            if(mgShelvesGoods != null){
                if(mgShelvesGoods.getGoodsIdList() != null && mgShelvesGoods.getGoodsIdList().size() > 0){
                    filters.add(Condition.notIn("goodsId", mgShelvesGoods.getGoodsIdList().toArray()));
                }
            }

            //只查询商品状态为未删除的商品
            filters.add(Condition.eq("isDelete", 1));
            filters.add(Condition.eq("checkStatus", 1));
            filters.add(Condition.eq("isOnsale", 1));
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            if (StringUtils.isNotBlank(searchName)) {
                filters.add(Condition.like("goodsName", searchName));
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
            filters.add(Condition.eq("isOnsale", 1));
            if(StringUtils.isNotBlank(goodsName)){
                filters.add(Condition.like("goodsName", goodsName.trim()));
            }
            if(StringUtils.isNotBlank(goodsSn)){
                filters.add(Condition.like("goodsSn", goodsSn.trim()));
            }
            if(StringUtils.isNotBlank(keywords)){
                filters.add(Condition.like("keywords", keywords.trim()));
            }
            if(StringUtils.isNotBlank(shopName)){
                filters.add(Condition.like("shopName", shopName.trim()));
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

            List<Goods> list = page.getList();
            if(list .size() > 0 && list != null){
                list.stream().forEach(goods ->
                        {
                            goods.setGoodsArea(goods.getGoodsArea() == null ? "" : DictionaryUtil.getRegionById(goods.getGoodsArea()).getMergerName());
                            goods.setCatId(goods.getCatId() == null ? "" : DictionaryUtil.getCategoryById(goods.getCatId().substring(0, 3)).getCatName());
                        });
            }
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

            int n = goodsService.updateByIdSelective(goods);

            //消息 es
            if(n > 0) {
                mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goodsId);
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 获取已审核的数据
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/checkedList")
    public ReturnData checkedList(String currentPage, String pageSize, String goodsName, String goodsSn) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<GoodsCheckedVo> pagination = null;
        PageInfo<GoodsCheckedVo> page = new PageInfo<>();
        try {

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            pagination = new Pagination<>(pageNumberNew, pageSizeNew);
            PageHelper.startPage(pageNumberNew, pageSizeNew);//pageNum为第几页，pageSize为每页数量

            List<GoodsCheckedVo> list = goodsService.getListForChecked(goodsName, goodsSn);
            page = new PageInfo<GoodsCheckedVo>(list);
            if(page.getList() != null && page.getList().size() > 0){
                page.getList().stream().forEach(goodsCheckedVo ->
                        {
                            goodsCheckedVo.setGoodsArea((goodsCheckedVo.getGoodsArea() == null || "".equals(goodsCheckedVo.getGoodsArea())) ? "" : DictionaryUtil.getRegionById(goodsCheckedVo.getGoodsArea()).getMergerName());
                            goodsCheckedVo.setCatId((goodsCheckedVo.getCatId() == null || "".equals(goodsCheckedVo.getCatId())) ? "" : DictionaryUtil.getCategoryById(goodsCheckedVo.getCatId().substring(0, 3)).getCatName());
                        }
                );
                pagination.setTotalItems(page.getTotal());
                pagination.setPageSize(pageSizeNew);
                pagination.setCurrentPage(pageNumberNew);
                pagination.setList(page.getList());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(pagination);
    }

}
