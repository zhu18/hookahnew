package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbTrusteeRecord;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.crowd.service.PayService;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import com.jusfoun.hookah.crowd.service.ZbTrusteeRecordService;
import com.jusfoun.hookah.crowd.util.PayConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ZBPayController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ZBPayController.class);

    @Resource
    PayService payService;

    @Resource
    ZbRequireService zbRequireService;

    @Resource
    ZbTrusteeRecordService zbTrusteeRecordService;

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
            logger.error("众包请求去支付页面异常--{}", e);
        }
        return mv;
    }

    @RequestMapping(value = "/balancePay", method = RequestMethod.POST)
    public ModelAndView toBalancePay(String orderSn, String passWord) {

        ModelAndView mv = new ModelAndView();

        try {
            mv = payService.toBalancePay(orderSn, passWord, getCurrentUser().getUserId());
        }catch (Exception e){
            logger.error("众包余额支付异常--{}", e);
            mv.addObject("message", "系统繁忙^_^");
            mv.addObject("orderSn", orderSn);
            mv.setViewName("pay/fail");
            return mv;
        }
        return mv;
    }

    @RequestMapping(value = "/aliPay", method = RequestMethod.GET)
    public Object toAliPay(String orderSn, Model model) {

        String reqHtml = null;

        try {

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("serialNo", orderSn));
            filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
            ZbTrusteeRecord zbTrusteeRecord = zbTrusteeRecordService.selectOne(filters);
            if(zbTrusteeRecord.getStatus().equals(Short.parseShort("1"))){
                model.addAttribute("orderSn", orderSn);
                model.addAttribute("message", "该订单已支付^_^");
                return "pay/fail";
            }

            ZbRequirement zbRequirement = zbRequireService.selectById(zbTrusteeRecord.getRequirementId());
            if(zbRequirement != null){
                logger.error("众包支付宝支付异常--{未查询到需求信息}");
                model.addAttribute("orderSn", orderSn);
                model.addAttribute("message", "订单支付失败^_^");
                return "pay/fail";
            }

            reqHtml = payService.toPayByZFB(zbRequirement.getRequireSn(), zbTrusteeRecord.getSerialNo(), zbTrusteeRecord.getActualMoney() / 100, zbTrusteeRecord.getTrusteeNum().toString(), PayConfiguration.ALIPAY_NOTIFY_URL, PayConfiguration.ALIPAY_RETURN_URL);

        } catch (Exception e) {

            logger.error("众包支付宝支付异常--{}", e);
            model.addAttribute("orderSn", orderSn);
            model.addAttribute("message", "订单支付失败");
            return "pay/fail";
        }

        if(StringUtils.isEmpty(reqHtml)){

            model.addAttribute("orderSn", orderSn);
            model.addAttribute("message", "订单支付失败");
            return "pay/fail";
        }else
            return new ResponseEntity<String>(reqHtml, HttpStatus.OK);
    }

}
