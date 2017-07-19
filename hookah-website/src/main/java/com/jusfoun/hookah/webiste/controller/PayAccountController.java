package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/18.
 */

@Controller
@RequestMapping("/payAccount")
public class PayAccountController {

    @Resource
    private PayAccountService payAccountService;

    @RequestMapping("/userRecharge")
    @ResponseBody
    public String userRecharge(String userId,String money){
        Map<String,Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("money",money);
        ReturnData r=payAccountService.userRecharge(params);
        return r.getCode().equals(ExceptionConst.Success) ? r.getMessage() : JsonUtils.toJson(r);
    }

    @RequestMapping("/rechargeResultSync")
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
    }


    @RequestMapping("/rechargeResult")
    public String rechargeResultPage(HttpServletRequest request) throws  IOException{
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String totalFee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
        String userId = new String(request.getParameter("extra_common_param").getBytes("ISO-8859-1"),"UTF-8");
        request.setAttribute("money",totalFee);
        if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
            return "/usercenter/success";
        }
        return "/usercenter/fail";

    }

    @ResponseBody
    @RequestMapping("/updatePayPassword")
    public ReturnData updatePayPassword(String userId,String payPassword,String newPayPassword){
        List<Condition> filters = new ArrayList();
        if (StringUtils.isNotBlank(userId)) {
            filters.add(Condition.eq("userId", userId));
        }
        PayAccount payAccount = payAccountService.selectOne(filters);
        if(StringUtils.isNotBlank(payAccount.getPayPassword())){
            if(payAccount.getPayPassword().equals(payPassword)){
                payAccount.setPayPassword(newPayPassword);
                payAccountService.updateByIdSelective(payAccount);
                return ReturnData.success("修改密码成功");
            }
        }else{
            return ReturnData.error("密码不可为空");
        }
        return ReturnData.error("修改密码失败");
    }
}
