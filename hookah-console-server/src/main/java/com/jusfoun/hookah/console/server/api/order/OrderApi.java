package com.jusfoun.hookah.console.server.api.order;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    public ReturnData getListInPage(String currentPage, String pageSize, String orderSn) {
        Pagination<OrderInfoVo> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            //只查询商品状态为未删除的商品
//            filters.add(Condition.eq("isDelete", 1));

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
            String a = new String();
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

}
