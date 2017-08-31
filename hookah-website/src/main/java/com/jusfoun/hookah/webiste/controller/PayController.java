package com.jusfoun.hookah.webiste.controller;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author bingbing wu
 * @date 2017/3/10 下午21:21
 * @desc
 */
@Controller
@RequestMapping("/pay")
public class PayController extends BaseController{
    protected final static Logger logger = LoggerFactory.getLogger(PayController.class);

    @Resource
    private PayCoreService payCoreService;

    @Resource
    private OrderInfoService orderService;

    @Resource
    UserService userService;

    @Resource
    PayAccountService payAccountService;

    @Resource
    PayTradeRecordService payTradeRecordService;

    @Resource
    PayAccountRecordService payAccountRecordService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public String createOrder() {
        return "pay/createOrder";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "pay/success";
    }

    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public String fail() {
        return "pay/fail";
    }

    /**
     * 订单支付
     * @param model
     * @return
     */
    /*@ResponseBody
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String payPassSta(@RequestBody Map<String,String> paramMap, Model model, HttpServletRequest request) {

        long orderAmount = 0 ; //支付金额
    try {
            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
            User user = userService.selectById(userMap.get("userId"));
            String newPassword = new Md5Hash(paramMap.get("paymentPassword")).toString();
            String oldPayPassword =  user.getPaymentPassword();
        // 验证支付密码
        if(oldPayPassword.equals(newPassword)){

            String orderSn = paramMap.get("orderSn");
            //支付操作
            payCoreService.doPayMoney(orderSn,user.getUserId());
            //根据订单编号获得支付金额
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("orderSn", orderSn));
            OrderInfo orderinfo  = orderService.selectOne(filters);
            orderAmount= orderinfo.getOrderAmount();
        }else{
            model.addAttribute("message", "支付密码错误!");
            return "pay/fail";
        }

    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("message", "支付失败!");
        return "pay/fail";
    }
    model.addAttribute("money",orderAmount);
    return "pay/success";
}*/

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String payPassSta(String orderSn, Model model, HttpServletRequest request) {

        long orderAmount = 0 ; //支付金额
        /*  String orderSn = paramMap.get("orderSn");*/
        try {
            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
            User user = userService.selectById(userMap.get("userId"));
            //支付操作
            payCoreService.doPayMoney(orderSn,user.getUserId());
            //根据订单编号获得支付金额
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("orderSn", orderSn));
            OrderInfo orderinfo  = orderService.selectOne(filters);
            orderAmount= orderinfo.getOrderAmount();

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "支付失败!");
            return "pay/fail";
        }
        model.addAttribute("money",orderAmount);
        return "pay/success";
    }

    @RequestMapping(value = "/balancePay", method = RequestMethod.POST)
    public String payPassSta(String orderSn, Model model, String passWord) {
        OrderInfo orderinfo = new OrderInfo();
        try {
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("orderSn", orderSn));
            orderinfo  = orderService.selectOne(filters);
            if (orderinfo.getPayStatus() == 2){
                model.addAttribute("message", "订单已支付");
                model.addAttribute("code", 9);
                model.addAttribute("orderSn", orderSn);
                return "pay/fail";
            }
            //插流水调接口
            logger.info("调用余额支付"+orderSn);
            payAccountService.payByBalance(orderinfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("余额支付失败"+orderSn);
            model.addAttribute("message", e.getMessage());
            model.addAttribute("orderSn", orderSn);
            return "pay/fail";
        }
        logger.info("余额支付成功"+orderSn);
        model.addAttribute("money",orderinfo.getOrderAmount());
        return "pay/success";
    }

    @RequestMapping(value = "/aliPay", method = RequestMethod.GET)
    public Object alipay(String orderSn, Model model) {
        String reqHtml = null;
        try {
            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
            User user = userService.selectById(userMap.get("userId"));
            //根据订单编号获得支付金额
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("orderSn", orderSn));
            OrderInfo orderinfo  = orderService.selectOne(filters);
            if (orderinfo.getPayStatus() == 2){
                model.addAttribute("message", "订单已支付");
                model.addAttribute("code", 9);
                model.addAttribute("orderSn", orderSn);
                return "pay/fail";
            }
            logger.info("使用支付宝支付"+orderSn);
            reqHtml = payAccountService.payByAli(orderinfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(reqHtml)){
            model.addAttribute("orderSn", orderSn);
            model.addAttribute("message", "订单支付失败");
            return "pay/fail";
        }else
            return new ResponseEntity<String>(reqHtml, HttpStatus.OK);
    }

    @RequestMapping(value = "/cash", method = RequestMethod.GET)
    public String cash(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String userId = null;
        try {
            userId = this.getCurrentUser().getUserId();
        } catch (HookahException e) {
            logger.error(e.getMessage());
        }
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("userId", userId));
        PayAccount payAccount = payAccountService.selectOne(filters);
        if(payAccount != null)
            model.addAttribute("moneyBalance", payAccount.getUseBalance());
            model.addAttribute("payments", session.getAttribute("payments"));
            model.addAttribute("orderInfo",session.getAttribute("orderInfo"));
//            session.removeAttribute("payments");
//            session.removeAttribute("orderInfo");
//            session.removeAttribute("moneyBalance");
        return "pay/cash";
    }

    @RequestMapping(value = "/pay2", method = RequestMethod.GET)
    public Object pay (String orderId){
        String reqHtml = null;
        try {
            //.findPayCoreByOrderSn(null);
            reqHtml = payCoreService.doPay(orderId,"62cb01c71c4711e796c56a3b07101c5a");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(reqHtml)){
            return "redirect:/404.html";
        }else
            return new ResponseEntity<String>(reqHtml, HttpStatus.OK);
    }

    /**
     * 支付宝回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alipay_rtn",method = RequestMethod.GET)
    public String returnBack4Alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //商户订单号
        String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");

        Map<String,String> param = getRequestParams(request);
        logger.info("支付宝同步回调"+orderSn);
        boolean flag = payAccountService.aliPay(orderSn, tradeStatus, param);
        BigDecimal money = new BigDecimal(total_fee).multiply((new BigDecimal(100)));
        if (flag){
            request.setAttribute("money",money);
            return "pay/success";
        }else {
            request.setAttribute("orderSn",orderSn);
            return "pay/fail";
        }
    }

    /**
     * 支付宝异步回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/alipay_ntf", method = RequestMethod.POST)
    @ResponseBody
    String notifyBack4Alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //商户订单号
        String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");

        Map<String,String> param = getRequestParams(request);
        logger.info("支付宝异步回调"+orderSn);
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("orderSn",orderSn));
        OrderInfo orderInfo = orderService.selectOne(filter);
        boolean flag = false;
        if (orderInfo != null && orderInfo.getPayStatus() != orderInfo.PAYSTATUS_PAYED){
            flag = payAccountService.aliPay(orderSn, tradeStatus, param);
        }else if (orderInfo != null && orderInfo.getPayStatus() == orderInfo.PAYSTATUS_PAYED){
            return "success";
        }
        if (flag){
            return "success";
        }else {
            return "fail";
        }
    }

    @SuppressWarnings("rawtypes")
    private Map<String,String> getRequestParams(HttpServletRequest request){
        //处理通知参数
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        return params;
    }
}
