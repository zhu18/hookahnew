package com.jusfoun.hookah.console.server.api.order;

import com.jusfoun.hookah.console.server.controller.BaseController;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            //只查询商品状态为未删除的商品
//            filters.add(Condition.eq("isDelete", 1));

            String orderSn = orderInfoVo.getOrderSn();
            String userName = orderInfoVo.getUserName();
            Integer userType = orderInfoVo.getUserType();
            Integer payStatus = orderInfoVo.getPayStatus();
            Integer solveStatus = orderInfoVo.getSolveStatus();

            if (StringUtils.isNotBlank(startDate)) {
                filters.add(Condition.ge("addTime", DateUtils.getDate(startDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (StringUtils.isNotBlank(endDate)) {
                filters.add(Condition.le("addTime", DateUtils.getDate(endDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (StringUtils.isNotBlank(userName)){
                filters.add(Condition.eq("userName", userName));
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
                filters.add(Condition.like("orderSn", orderSn));
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
            page = orderInfoService.getUserListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
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

}
