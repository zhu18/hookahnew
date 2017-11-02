package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbTrusteeRecord;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.*;
import com.jusfoun.hookah.crowd.util.CommonUtils;
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
import tk.mybatis.mapper.util.StringUtil;

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

    @Resource
    PayAccountService payAccountService;

    @Resource
    UserService userService;

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

    @RequestMapping(value = "/api/zbPay/toPayPage")
    public ModelAndView toPayPage(String requirementId, String trusteePercent){

        ModelAndView mv = new ModelAndView();
        try {
            mv = payService.toPayPage(requirementId, trusteePercent, getCurrentUser().getUserId());
        }catch (Exception e){
            logger.error("众包请求去支付页面异常--{}", e);
        }
        return mv;
    }

    /**
     * 余额支付方式
     * @param tradeNo
     * @param passWord
     * @return
     */
    @RequestMapping(value = "/api/zbPay/balancePay", method = RequestMethod.POST)
    public ModelAndView toBalancePay(String tradeNo, String passWord) {

        ModelAndView mv = new ModelAndView();

        try {
            mv = payService.toBalancePay(tradeNo, passWord, getCurrentUser().getUserId());
        }catch (Exception e){
            logger.error("众包余额支付异常--{}", e);
            mv.addObject("message", "系统繁忙^_^");
            mv.addObject("orderSn", tradeNo);
            mv.addObject("code", 9);
            mv.setViewName("pay/fail");
            return mv;
        }
        return mv;
    }

    /**
     * 支付宝支付方式
     * @param tradeNo
     * @param model
     * @return
     */
    @RequestMapping(value = "/api/zbPay/aliPay", method = RequestMethod.GET)
    public Object toAliPay(String tradeNo, Model model) {

        String reqHtml = null;

        try {

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("serialNo", tradeNo));
            filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
            ZbTrusteeRecord zbTrusteeRecord = zbTrusteeRecordService.selectOne(filters);

            if(zbTrusteeRecord == null){
                model.addAttribute("orderSn", tradeNo);
                model.addAttribute("message", "订单异常，请重新支付^_^");
                model.addAttribute("code", 9);
                return "pay/fail";
            }

            if(zbTrusteeRecord.getStatus().equals(Short.parseShort("1"))){
                model.addAttribute("orderSn", tradeNo);
                model.addAttribute("message", "该订单已支付^_^");
                model.addAttribute("code", 9);
                return "pay/fail";
            }

            ZbRequirement zbRequirement = zbRequireService.selectById(zbTrusteeRecord.getRequirementId());
            if(zbRequirement == null){
                logger.error("众包支付宝支付异常--{未查询到需求信息}");
                model.addAttribute("orderSn", tradeNo);
                model.addAttribute("message", "订单已过期，支付失败^_^");
                model.addAttribute("code", 9);
                return "pay/fail";
            }

            // 为防止逗比用户使用支付宝扫码成功但不支付，再去修改订单数据，最后去支付支付宝校验失败，每次重新生成订单号
            zbTrusteeRecord.setSerialNo(CommonUtils.getRequireSn("ZB", "PAY"));
            int n = zbTrusteeRecordService.updateByIdSelective(zbTrusteeRecord);
            if(n != 1){
                logger.error("众包支付宝支付修改订单号失败^_^");
                throw new Exception();
            }

            reqHtml = payService.toPayByZFB(zbRequirement.getRequireSn(), zbTrusteeRecord.getSerialNo(), zbTrusteeRecord.getActualMoney() / 100, zbTrusteeRecord.getTrusteeNum().toString(), PayConfiguration.ALIPAY_NOTIFY_URL, PayConfiguration.ALIPAY_RETURN_URL);

        } catch (Exception e) {

            logger.error("众包支付宝支付异常--{}", e);
            model.addAttribute("orderSn", tradeNo);
            model.addAttribute("message", "订单支付失败");
            model.addAttribute("code", 9);
            return "pay/fail";
        }

        if(StringUtils.isEmpty(reqHtml)){

            model.addAttribute("orderSn", tradeNo);
            model.addAttribute("message", "订单支付失败");
            model.addAttribute("code", 9);
            return "pay/fail";
        }else
            return new ResponseEntity<String>(reqHtml, HttpStatus.OK);
    }

    /**
     * 验证支付密码
     * @return
     */
    @RequestMapping(value = "/api/zbPay/verifyPayPassword")
    @ResponseBody
    public ReturnData verifyPayPassword(String passWord) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {

            if(StringUtil.isEmpty(passWord)){
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("请输入支付密码^_^");
                return returnData;
            }

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
            PayAccount payAccount = payAccountService.selectOne(filters);
            if(payAccount == null){
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("账户信息不存在^_^");
                return returnData;
            }

            if(payAccount.getPayPassword() == null){
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("请先设置支付密码^_^");
                return returnData;
            }

            if(!payAccount.getPayPassword().equals(passWord)){
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("支付密码不正确^_^");
                return returnData;
            }

            returnData.setMessage("支付密码验证成功^_^");

        }catch (Exception e){
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙^_^");
            return returnData;
        }

        return  returnData;
    }

    /**
     * 验证是否设置支付密码
     * @return
     */
    @RequestMapping(value = "/api/zbPay/payPassSta")
    @ResponseBody
    public ReturnData payPassSta() {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {

            User user = userService.selectById(getCurrentUser().getUserId());
            returnData.setData(user.getPaymentPasswordStatus());

            returnData.setMessage("验证是否设置支付密码^_^");

        }catch (Exception e){
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙^_^");
            return returnData;
        }

        return  returnData;
    }
}
