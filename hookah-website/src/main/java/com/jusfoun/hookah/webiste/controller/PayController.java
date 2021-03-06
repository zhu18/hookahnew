package com.jusfoun.hookah.webiste.controller;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.PayCoreService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bingbing wu
 * @date 2017/3/10 下午21:21
 * @desc
 */
@Controller
@RequestMapping("/pay")
public class PayController {
    protected final static Logger logger = LoggerFactory.getLogger(PayController.class);

    @Resource
    private PayCoreService payCoreService;

    @Resource
    private OrderInfoService orderService;

    @Resource
    UserService userService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public String createOrder() {
        return "pay/createOrder";
    }
    
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "pay/success";
    }

  /*  *//**
     * 订单支付
     * @param paramMap
     * @param model
     * @return
     *//*
    @ResponseBody
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


    @RequestMapping(value = "/payment", method = RequestMethod.GET)
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
    }


    @RequestMapping(value = "/cash", method = RequestMethod.GET)
    public String cash(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        model.addAttribute("moneyBalance", session.getAttribute("moneyBalance"));
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
}
