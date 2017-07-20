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
        long orderAmount = 0 ; //支付金额
        try {
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("orderSn", orderSn));
            OrderInfo orderinfo  = orderService.selectOne(filters);
            orderAmount= orderinfo.getOrderAmount();
            //验证支付密码和余额
            boolean flag = payAccountService.verifyPassword(passWord);
            if (flag){
                List<Condition> filter = new ArrayList();
                filter.add(Condition.eq("userId", orderinfo.getUserId()));
                PayAccount payAccount = payAccountService.selectOne(filter);
                if (payAccount.getUseBalance()<orderAmount){
                    model.addAttribute("message", "余额不足，支付失败!");
                    return "pay/fail";
                }
                //插流水调接口
                payAccountService.payByBalance(orderinfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "pay/fail";
        }
        model.addAttribute("money",orderAmount);
        return "pay/success";
    }

    @RequestMapping(value = "/aliPay", method = RequestMethod.GET)
    public Object alipay(String orderSn) {
        String reqHtml = null;
        try {
            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
            User user = userService.selectById(userMap.get("userId"));
            //根据订单编号获得支付金额
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("orderSn", orderSn));
            OrderInfo orderinfo  = orderService.selectOne(filters);
            reqHtml = payAccountService.payByAli(orderinfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(reqHtml)){
            return "redirect:/404.html";
        }else
            return new ResponseEntity<String>(reqHtml, HttpStatus.OK);
    }

 /*   @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String payPassSta(String orderSn,Model model) {

        long orderAmount = 0 ; //支付金额
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
    }*/


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
        session.removeAttribute("payments");
        session.removeAttribute("orderInfo");
        session.removeAttribute("moneyBalance");
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
    public ModelAndView returnBack4Alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = null;
        //商户订单号
        String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("orderSn",orderSn));
        OrderInfo orderInfo = orderService.selectOne(filter);
        if (payCoreService.verifyAlipay(getRequestParams(request))){
            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
                //交易成功,插交易中心冻结收入流水，更新交易中心虚拟账户金额
                PayTradeRecord payTradeRecord = new PayTradeRecord();
                payTradeRecord.setPayAccountId(HookahConstants.TRADECENTERACCOUNT);
                payTradeRecord.setMoney(orderInfo.getOrderAmount());
                payTradeRecord.setTradeType(HookahConstants.TradeType.FreezaIn.getCode());
                payTradeRecord.setTradeStatus(HookahConstants.TransferStatus.handing.getCode());
                payTradeRecord.setAddTime(new Date());
                payTradeRecord.setOrderSn(orderSn);
                payTradeRecord.setAddOperator(orderInfo.getUserId());
                payTradeRecord.setTransferDate(new Date());
                payTradeRecordService.insertAndGetId(payTradeRecord);
                //更新交易中心虚拟账户金额,更新流水表状态
                int n = payAccountService.operatorByType(HookahConstants.TRADECENTERACCOUNT,HookahConstants.TradeType.FreezaIn.getCode(),
                        orderInfo.getOrderAmount());

                //修改内部流水的状态和外部充值状态
                List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
                for (PayTradeRecord payTradeRecord1 : payTradeRecords){
                    payTradeRecord1.setTradeStatus(HookahConstants.TransferStatus.success.getCode());
                    payTradeRecordService.updateByIdSelective(payTradeRecord1);
                }
                PayAccountRecord payAccountRecord = payAccountRecordService.selectOne(filter);
                payAccountRecord.setTransferStatus(HookahConstants.TransferStatus.success.getCode());
                payAccountRecordService.updateByIdSelective(payAccountRecord);

                //更新订单状态
                orderService.updatePayStatus(orderSn,orderInfo.PAYSTATUS_PAYED,1);
                // 支付成功 查询订单 获取订单中商品插入到待清算记录
                orderService.waitSettleRecordInsert(orderSn);
                view = new ModelAndView("redirect:/paySucess.html?orderSn="+orderSn);
            }else{
                //交易失败
                List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
                for (PayTradeRecord payTradeRecord1 : payTradeRecords){
                    payTradeRecord1.setTradeStatus(HookahConstants.TransferStatus.fail.getCode());
                }
                view = new ModelAndView("redirect:/payError.html?orderSn="+orderSn);
            }
        }else{
            view = new ModelAndView("redirect:/payError.html?orderSn="+orderSn);
        }
        return view;
    }

    /**
     * 支付宝异步回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/alipay_ntf", method = RequestMethod.POST)
    @Transactional
    String notifyBack4Alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //商户订单号
        String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("orderSn",orderSn));
        OrderInfo orderInfo = orderService.selectOne(filter);
        Date date = new Date();
        if(payCoreService.verifyAlipay(getRequestParams(request))){
            if(tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")){
                //交易成功,插交易中心冻结收入流水，
                PayTradeRecord payTradeRecord = new PayTradeRecord();
                payTradeRecord.setPayAccountId(HookahConstants.TRADECENTERACCOUNT);//交易中心虚拟账号Id
                payTradeRecord.setMoney(orderInfo.getOrderAmount());
                payTradeRecord.setTradeType(HookahConstants.TradeType.FreezaIn.getCode());
                payTradeRecord.setTradeStatus(HookahConstants.TransferStatus.handing.getCode());
                payTradeRecord.setAddTime(date);
                payTradeRecord.setOrderSn(orderSn);
                payTradeRecord.setAddOperator(orderInfo.getUserId());
                payTradeRecord.setTransferDate(date);
                payTradeRecordService.insertAndGetId(payTradeRecord);
                //更新交易中心虚拟账户金额,更新流水表状态
                int n = payAccountService.operatorByType(HookahConstants.TRADECENTERACCOUNT,HookahConstants.TradeType.FreezaIn.getCode(),
                        orderInfo.getOrderAmount());
//                payTradeRecord.setTradeStatus(HookahConstants.TransferStatus.success.getCode());
//                payTradeRecordService.updateByIdSelective(payTradeRecord);

                //修改内部流水的状态和外部充值状态
                List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
                for (PayTradeRecord payTradeRecord1 : payTradeRecords){
                    payTradeRecord1.setTradeStatus(HookahConstants.TransferStatus.success.getCode());
                }
                PayAccountRecord payAccountRecord = payAccountRecordService.selectOne(filter);
                payAccountRecord.setTransferStatus(HookahConstants.TransferStatus.success.getCode());
                payAccountRecordService.updateByIdSelective(payAccountRecord);
                //更新订单状态
                orderService.updatePayStatus(orderSn,orderInfo.PAYSTATUS_PAYED,1);
                orderService.waitSettleRecordInsert(orderSn);
            }else {
                List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
                for (PayTradeRecord payTradeRecord1 : payTradeRecords){
                    payTradeRecord1.setTradeStatus(HookahConstants.TransferStatus.fail.getCode());
                }
            }
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
