package com.jusfoun.hookah.console.server.api.order;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dengxu on 2017/4/24/0024.
 */

@RestController
@RequestMapping(value = "/api/order")
public class OrderApi extends BaseController{

    @Resource
    OrderInfoService orderInfoService;
    @Resource
    UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize, String startDate, String endDate, OrderInfoVo orderInfoVo ) {
        Pagination<OrderInfoVo> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            //只查询商品状态为未删除的商品
//            filters.add(Condition.eq("isDelete", 1));

            String orderSn = orderInfoVo.getOrderSn();
            String userName = orderInfoVo.getUserName();
            Integer userType = orderInfoVo.getUserType();
            Integer payStatus = orderInfoVo.getPayStatus();
            Integer solveStatus = orderInfoVo.getSolveStatus();


            Long startMoney = 0L;
            Long endMoney = 0L;

            if(orderInfoVo.getStartMoney() != null){

                startMoney = new Double(orderInfoVo.getStartMoney() * 100).longValue();
            }
            if(orderInfoVo.getEndMoney() != null){

                endMoney = new Double(orderInfoVo.getEndMoney() * 100).longValue();
            }

            Date startTime = null;
            Date endTime = null;
            if (StringUtils.isNotBlank(startDate)) {
                startTime = DateUtils.getDate(startDate,DateUtils.DEFAULT_DATE_TIME_FORMAT);
            }
            if (StringUtils.isNotBlank(endDate)) {
                endDate = DateUtils.transferDate(endDate);
                endTime = DateUtils.getDate(endDate,DateUtils.DEFAULT_DATE_TIME_FORMAT);
            }
            if (StringUtils.isNotBlank(userName)){
                filters.add(Condition.like("userName", userName));
            }
            if (userType != null){
                filters.add(Condition.eq("userType", userType));
            }
            if (payStatus != null){
                filters.add(Condition.eq("payStatus", payStatus));
            }

            if (solveStatus != null){
                filters.add(Condition.eq("solveStatus", solveStatus));
            }

            if(StringUtils.isNotBlank(orderSn)){
                filters.add(Condition.eq("orderSn", orderSn));
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
            page = orderInfoService.getUserListInPage(pageNumberNew, pageSizeNew,
                    filters, startTime, endTime, startMoney, endMoney);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/viewDetails", method = RequestMethod.GET)
    public ReturnData getOrderDetail(@RequestParam String orderId){
        OrderInfoVo vo = new OrderInfoVo();
        List list = new ArrayList();
        try{
            vo = orderInfoService.findDetailById(orderId);
            User user = userService.selectById(vo.getUserId());
            list.add(vo);
            list.add(user);
            logger.info(JsonUtils.toJson(vo));
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
        return ReturnData.success(list);
    }

    /**
     * 订单商品修改交付信息
     * @param mgOrderGoods
     * @return
     * @author lt
     */
    @Log(platform = "back",logType = "b007",optType = "insert")
    @RequestMapping(value = "/updRemark", method = RequestMethod.POST)
    public ReturnData updateRemark(MgOrderGoods mgOrderGoods){
        try {
            if (StringUtils.isNotBlank(mgOrderGoods.getOrderId()) && StringUtils.isNotBlank(mgOrderGoods.getGoodsId())){
                orderInfoService.updateMgOrderGoodsRemark(mgOrderGoods);
            }else {
                return ReturnData.error("保存失败");
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
        return ReturnData.success();
    }

    /**
     * 订单商品修改联系信息
     * @param
     * @return
     * @author lt
     */
    @Log(platform = "back",logType = "b007",optType = "insert")
    @RequestMapping(value = "/updConcatInfo", method = RequestMethod.POST)
    public ReturnData updateConcatInfo(String orderId,String goodsId,String concatName,String concatPhone,String concatEmail){
        try {
            if (StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(goodsId)){
//                GoodsServiceImpl goodsService = new GoodsServiceImpl();
//                goodsService.changeConcatInfo(mgOrderGoods.getGoodsId(),mgOrderGoods.getIsOffline(),
//                        mgOrderGoods.getGoodsType(),mgOrderGoods.getDataModel().getConcatInfo());
                orderInfoService.updateConcatInfo(orderId,goodsId,concatName,concatPhone,concatEmail);
            }else {
                return ReturnData.error("保存失败");
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
        return ReturnData.success("修改成功");
    }

    /**
     * 订单商品获取交付信息
     * @param mgOrderGoods
     * @return
     * @author lt
     */
    @RequestMapping(value = "/getRemark", method = RequestMethod.GET)
    public ReturnData getOrderDetail(MgOrderGoods mgOrderGoods){
        Map map = new HashMap();
        try {
            if (StringUtils.isNotBlank(mgOrderGoods.getOrderId()) && StringUtils.isNotBlank(mgOrderGoods.getGoodsId())){
                map = orderInfoService.getRemark(mgOrderGoods);
            }else {
                return ReturnData.error("加载失败");
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
        return ReturnData.success(map);
    }

    /**
     * 按照条件查询商家已卖出的商品订单
     * @param currentPage
     * @param pageSize
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/soldOrder", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData getSoldOrder(Integer currentPage, Integer pageSize, String startDate, String endDate,
                                   OrderInfoVo orderInfoVo, String addUser){
        try {
            String userName = orderInfoVo.getUserName(); //买家账号
            String orderSn = orderInfoVo.getOrderSn();//订单号
            Integer solveStatus = orderInfoVo.getSolveStatus();//处理状态

            if (currentPage==null) currentPage = Integer.parseInt(PAGE_NUM);
            if (pageSize==null) pageSize = Integer.parseInt(PAGE_SIZE);

            List<Condition> listFilters = new ArrayList<>();
            Date startTime = null;
            Date endTime = null;
            if (org.apache.commons.lang3.StringUtils.isNotBlank(startDate)) {
                startTime = DateUtils.getDate(startDate,DateUtils.DEFAULT_DATE_TIME_FORMAT);
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(endDate)) {
                endTime = DateUtils.getDate(endDate,DateUtils.DEFAULT_DATE_TIME_FORMAT);
            }

            if (userName!=null){
                listFilters.add(Condition.like("userName",userName));
            }
            if (orderSn!=null){
                listFilters.add(Condition.eq("orderSn",orderSn));
            }
            if (solveStatus!=null){
                listFilters.add(Condition.eq("solveStatus",solveStatus));
            }
            if (addUser!=null){
                listFilters.add(Condition.eq("orderGoodsList.addUser",addUser));
            }

            listFilters.add(Condition.eq("payStatus", 2));

            //查询列表
            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));
            Pagination<OrderInfoVo> pOrders = orderInfoService.getSoldOrderListByCondition(currentPage, pageSize, listFilters,
                     startTime, endTime, addUser);

            return ReturnData.success(pOrders);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("分页查询已售出商品错误", e);
            return ReturnData.error("查询已售出商品错误");
        }
    }

    /**
     * 查询api调用日志
     * @return
     */
    @RequestMapping(value = "/findInvokeStatus", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData findInvokeStatus(String orderSn, String goodsSn, Integer pageNumber, Integer pageSize,
                                       String startDate, String endDate, String ip){
        try {
            if (pageNumber==null) pageNumber = Integer.parseInt(PAGE_NUM);
            if (pageSize==null) pageSize = Integer.parseInt(PAGE_SIZE);
            List<Condition> filters = new ArrayList<>();
            String status = null;
            return orderInfoService.findInvokeStatus(orderSn,goodsSn,pageNumber,pageSize,startDate,endDate,ip,status);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取API调用日志失败！{} {}", orderSn, goodsSn);
            return ReturnData.error(e.getMessage());
        }
    }
}
