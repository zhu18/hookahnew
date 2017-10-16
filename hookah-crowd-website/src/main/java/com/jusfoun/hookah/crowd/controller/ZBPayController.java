package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.crowd.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ZBPayController {

    private final static Logger logger = LoggerFactory.getLogger(ZBPayController.class);

    @Resource
    PayService payService;

    /**
     * 支付宝回调
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/zbPay/alipay_rtn", method = RequestMethod.GET)
    public ModelAndView syncByZFB(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();

        try {

            logger.info("众包项目，支付宝同步回调处理中……");

            String tradeSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");

            boolean flag = payService.handleZFBRs(request);

            if (flag){
                modelAndView.addObject("money", total_fee);
                modelAndView.setViewName("pay/success");
            }else {
                modelAndView.addObject("orderSn", tradeSn);
                modelAndView.setViewName("pay/fail");
            }
        }catch (Exception e){
            modelAndView.addObject("orderSn", "");
            modelAndView.setViewName("pay/fail");
            return modelAndView;
        }

        return modelAndView;
    }

    /**
     * 支付宝异步回调
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/zbPay/alipay_ntf", method = RequestMethod.POST)
    @ResponseBody
    public String asyncByZFB(HttpServletRequest request) throws Exception{

        logger.info("众包项目，支付宝异步回调处理中……");

        try {

            boolean flag = payService.handleZFBRs(request);

            if (flag){
                return "success";
            }else {
                return "fail";
            }
        }catch (Exception e){

            logger.info("众包项目，支付宝异步回调失败……{}", e);
            return "fail";
        }
    }


}
