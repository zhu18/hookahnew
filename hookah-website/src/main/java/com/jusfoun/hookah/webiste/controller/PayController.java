package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.PayCoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author bingbing wu
 * @date 2017/3/10 下午21:21
 * @desc
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Resource
    private PayCoreService payCoreService;


    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public String createOrder() {
        return "pay/createOrder";
    }

    @RequestMapping(value = "/cash", method = RequestMethod.GET)
    public String cash(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();

        model.addAttribute("payments", session.getAttribute("payments"));
        model.addAttribute("orderInfo",session.getAttribute("orderInfo"));
        session.removeAttribute("payments");
        session.removeAttribute("orderInfo");
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
