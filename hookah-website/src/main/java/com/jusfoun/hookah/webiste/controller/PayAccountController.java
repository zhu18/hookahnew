package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.PayCoreService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by admin on 2017/7/18.
 */

@Controller
@RequestMapping("/payAccount")
public class PayAccountController extends BaseController{

    @Resource
    private PayAccountService payAccountService;

    @Resource
    PayCoreService payCoreService;

    @RequestMapping("/userRecharge")
    public Object userRecharge(String money, HttpServletResponse response) throws IOException {

        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
        Map<String,String> params = new HashMap<>();
        params.put("userId",userMap.get("userId"));
        params.put("money",money);
        ReturnData r=payAccountService.userRecharge(params);
        if(r.getCode().equals(ExceptionConst.Success) && StrUtil.notBlank(r.getMessage())){
            return new ResponseEntity<String>(r.getMessage(), HttpStatus.OK);

        }else
            return "redirect:/404.html";
       // return r.getCode().equals(ExceptionConst.Success) ? r.getMessage() : JsonUtils.toJson(r);
    }

    /**
     * 支付宝异步回调函数
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/rechargeResultSync", method = RequestMethod.POST)
    public String rechargeResultSync(HttpServletRequest request) throws IOException {

        //商户订单号
        //String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        //String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String totalFee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
        String userId = new String(request.getParameter("extra_common_param").getBytes("ISO-8859-1"),"UTF-8");
        String statusFlag="2";
        if(payCoreService.verifyAlipay(getParams(request))){
            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
                statusFlag="1";
            }
            Map<String,String> map = new HashMap<>();
            //map.put("tradeNo",tradeNo);
            map.put("tradeStatus",statusFlag);
            map.put("totalFee",totalFee);
            map.put("userId",userId);
            //交易平台类型 1：在线充值（入金）
            map.put("tradeType","1");
            payAccountService.saveRechargeResult(map);
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * 支付宝同步回调函数
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/rechargeResult", method = RequestMethod.GET)
    public String rechargeResultPage(HttpServletRequest request) throws  IOException{
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String totalFee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
        String userId = new String(request.getParameter("extra_common_param").getBytes("ISO-8859-1"),"UTF-8");
        String statusFlag="2";
        if(payCoreService.verifyAlipay(getParams(request))){
            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
                statusFlag="1";
            }
            Map<String,String> map = new HashMap<>();
            //map.put("tradeNo",tradeNo);
            map.put("tradeStatus",statusFlag);
            map.put("totalFee",totalFee);
            map.put("userId",userId);
            //交易平台类型 1：在线充值（入金）
            map.put("tradeType","1");
            ReturnData returnData=payAccountService.saveRechargeResult(map);
            if (returnData.getCode().equals(ExceptionConst.Success)){
                request.setAttribute("money",totalFee);
                return "/usercenter/userInfo/rechargeSuccess";
            }
            return "/usercenter/userInfo/rechargeFailure";
        }else{
            return "/usercenter/userInfo/rechargeFailure";
        }
    }

    @ResponseBody
    @RequestMapping("/updatePayPassword")
    public ReturnData updatePayPassword(String payPassword,String newPayPassword){
        String userId=null;
        try {
            userId=getCurrentUser().getUserId();
        } catch (HookahException e) {
            return ReturnData.error(e.getMessage());
        }
        //验证当前交易密码
        if(payAccountService.verifyPassword(userId,payPassword)){
            //验证新老交易密码是否不一致
            if(!payPassword.equals(newPayPassword)) {
                if(payAccountService.resetPayPassword(userId, newPayPassword))
                    return ReturnData.success("修改交易密码成功");
                else
                    return ReturnData.error("修改交易密码失败，请联系管理员。");
            }else{
                return ReturnData.error("当前交易密码与新交易密码不可一致");
            }
        }else{
            return ReturnData.error("当前交易密码有误");
        }
    }

    public Map<String,String> getParams(HttpServletRequest request) throws  IOException{
        //获取支付宝GET过来反馈信息
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
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            params.put(name, valueStr);
        }
        return  params;
    }

    /**
     * 初次设置交易密码
     * @param payPassword
     * @return
     */
    @ResponseBody
    @RequestMapping("/addPayPassword")
    public ReturnData addPayPassword(String payPassword){
        String userId=null;
        try {
            userId=getCurrentUser().getUserId();
        } catch (HookahException e) {
            return ReturnData.error(e.getMessage());
        }
        if(payAccountService.resetPayPassword(userId,payPassword)){
            return ReturnData.success("设置交易密码成功");
        }else{
            return ReturnData.error("设置交易密码失败，请联系管理员。");
        }
    }
}
