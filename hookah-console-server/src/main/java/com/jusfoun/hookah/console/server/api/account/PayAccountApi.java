package com.jusfoun.hookah.console.server.api.account;

import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/7/18.
 */

@Controller
@RequestMapping("/api/payAccountBg")
public class PayAccountApi {

    @Resource
    private PayAccountService payAccountService;

    @RequestMapping("/rechargeByHand")
    @ResponseBody
    public ReturnData rechargeByHand(String userId,String money) {
        Map<String,String> map = new HashMap<>();
        map.put("tradeStatus","1");
        map.put("totalFee",money);
        map.put("userId",userId);
        //交易平台类型 5：手工充值
        map.put("tradeType","5");

        return payAccountService.rechargeByHand(map);
    }

}
