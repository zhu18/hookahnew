package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.crowd.service.PayAccountService;
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
public class ZBPayController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ZBPayController.class);

    @Resource
    PayService payService;

    @Resource
    PayAccountService payAccountService;

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
                modelAndView.setViewName("zbPay/success");
            }else {
                modelAndView.addObject("orderSn", tradeSn);
                modelAndView.setViewName("zbPay/fail");
            }
        }catch (Exception e){
            modelAndView.addObject("orderSn", "");
            modelAndView.setViewName("zbPay/fail");
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

    @RequestMapping(value = "/toPayPage")
    public ModelAndView toPayPage(String requirementId, String trusteePercent){

        ModelAndView mv = new ModelAndView();

        try {

            mv = payService.toPayPage(requirementId, trusteePercent, getCurrentUser().getUserId());

        }catch (Exception e){
            logger.error("众包请求去支付页面异常", e);
        }

        return mv;

//        String userId = null;
//        try {
//            userId = this.getCurrentUser().getUserId();
//        } catch (HookahException e) {
//            logger.error(e.getMessage());
//        }
//        List<Condition> filters = new ArrayList();
//        filters.add(Condition.eq("userId", userId));
//        PayAccount payAccount = payAccountService.selectOne(filters);
//        if(payAccount != null)
//            model.addAttribute("moneyBalance", payAccount.getUseBalance());
//        model.addAttribute("payments", session.getAttribute("payments"));
//        model.addAttribute("orderInfo",session.getAttribute("orderInfo"));
//        return "pay/cash";
    }

//    @RequestMapping(value = "/balancePay", method = RequestMethod.POST)
//    public String payPassSta(String orderSn, Model model, String passWord) {
//        OrderInfo orderinfo = new OrderInfo();
//        try {
//            List<Condition> filters = new ArrayList();
//            filters.add(Condition.eq("orderSn", orderSn));
//            orderinfo  = orderService.selectOne(filters);
//            if (orderinfo.getPayStatus() == 2){
//                model.addAttribute("message", "订单已支付");
//                model.addAttribute("code", 9);
//                model.addAttribute("orderSn", orderSn);
//                return "pay/fail";
//            }
//            //插流水调接口
//            logger.info("调用余额支付"+orderSn);
//            payAccountService.payByBalance(orderinfo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("余额支付失败"+orderSn);
//            model.addAttribute("message", e.getMessage());
//            model.addAttribute("orderSn", orderSn);
//            return "pay/fail";
//        }
//        logger.info("余额支付成功"+orderSn);
//        model.addAttribute("money",orderinfo.getOrderAmount());
//        return "pay/success";
//    }

}
