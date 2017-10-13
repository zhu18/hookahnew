package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.crowd.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class ZBPayController {

    @Resource
    PayService payService;

    /**
     * 支付宝回调
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/zbPay/alipay_rtn", method = RequestMethod.GET)
    public ModelAndView returnBackAlipay(HttpServletRequest request) {
        return payService.handleZFBRs(request);
    }

    /**
     * 支付宝异步回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/zbPay/alipay_ntf", method = RequestMethod.POST)
    @ResponseBody
    String notifyBack4Alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        //商户订单号
//        String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//        //支付宝交易号
//        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
//        //交易状态
//        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
//        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
//
//        Map<String,String> param = getRequestParams(request);
//        logger.info("支付宝异步回调"+orderSn);
//        List<Condition> filter = new ArrayList<>();
//        filter.add(Condition.eq("orderSn",orderSn));
//        OrderInfo orderInfo = orderService.selectOne(filter);
//        boolean flag = false;
//        if (orderInfo != null && orderInfo.getPayStatus() != orderInfo.PAYSTATUS_PAYED){
//            flag = payAccountService.aliPay(orderSn, tradeStatus, param);
//        }else if (orderInfo != null && orderInfo.getPayStatus() == orderInfo.PAYSTATUS_PAYED){
//            return "success";
//        }
//        if (flag){
//            return "success";
//        }else {
//            return "fail";
//        }
        return null;
    }


}
