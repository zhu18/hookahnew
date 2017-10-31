package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.config.WeChatConfig;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.WeChatOAuthInfo;
import com.jusfoun.hookah.core.domain.WxUserInfo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.UserService;
import com.jusfoun.hookah.rpc.api.WxUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by lt on 2017/10/30.
 */
@Controller
@RequestMapping("/callBack")
public class CallBackController {
    protected final static Logger logger = LoggerFactory.getLogger(CallBackController.class);

    @Resource
    private PayAccountService payAccountService;

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private WxUserInfoService wxUserInfoService;

    @Resource
    private UserService userService;

    /**
     * 支付宝回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alipay_rtn",method = RequestMethod.GET)
    public String returnBack4Alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //商户订单号
        String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");

        Map<String,String> param = getRequestParams(request);
        logger.info("支付宝同步回调"+orderSn);
        boolean flag = payAccountService.aliPay(orderSn, tradeStatus, param);
        BigDecimal money = new BigDecimal(total_fee).multiply((new BigDecimal(100)));
        if (flag){
            request.setAttribute("money",money);
            return "pay/success";
        }else {
            request.setAttribute("orderSn",orderSn);
            return "pay/fail";
        }
    }

    /**
     * 支付宝异步回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/alipay_ntf", method = RequestMethod.POST)
    @ResponseBody
    String notifyBack4Alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //商户订单号
        String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");

        Map<String,String> param = getRequestParams(request);
        logger.info("支付宝异步回调"+orderSn);
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("orderSn",orderSn));
        OrderInfo orderInfo = orderInfoService.selectOne(filter);
        boolean flag = false;
        if (orderInfo != null && orderInfo.getPayStatus() != orderInfo.PAYSTATUS_PAYED){
            flag = payAccountService.aliPay(orderSn, tradeStatus, param);
        }else if (orderInfo != null && orderInfo.getPayStatus() == orderInfo.PAYSTATUS_PAYED){
            return "success";
        }
        if (flag){
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

    @RequestMapping("/loginByWeChat")
    public String loginByWeChat(HttpServletRequest request){
        // 微信接口自带 2 个参数
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (code!=null && !"".equals(code)){
            WeChatOAuthInfo weChatOAuthInfo = getAccess_token(code);
            String openId = weChatOAuthInfo.getOpenId();
            String accessToken = weChatOAuthInfo.getAccessToken();

            if(accessToken == null) {
                return "redirect:";
            }

            // 数据库中查询微信号是否绑定平台账号
            List<Condition> filter = new ArrayList<>();
            filter.add(Condition.eq("openId",openId));
            WxUserInfo wxUserInfo = wxUserInfoService.selectOne(filter);
            if(wxUserInfo == null) {
//                request.getSession().setAttribute(openid, randomStr);
                // 尚未绑定账号 重定向到绑定账号页面
                return "redirect:/index.jsp?openid=";
            }
            User user = userService.selectById(wxUserInfo.getUserid());
            //账号已绑定 重定向到登陆
            return "forward:/login?userName="+user.getUserName()+"&password="+user.getPassword();
        }
        // 未授权
        return null;
    }

    public String getStartURLToGetCode() throws Exception{
        String takenUrl = WeChatConfig.getSnsApiUserInfoUrl;
        takenUrl= takenUrl.replace("APPID", WeChatConfig.appID);
        takenUrl= takenUrl.replace("REDIRECT_URI", URLEncoder.encode("http://www.bdgstore.cn/exchange/index","UTF-8"));
        //FIXME ： snsapi_userinfo
        takenUrl= takenUrl.replace("SCOPE", "snsapi_userinfo");
        return takenUrl;
    }

    public WeChatOAuthInfo getAccess_token(String code){
        String authUrl = WeChatConfig.getAccessTokenUrl;
        authUrl= authUrl.replace("APPID", WeChatConfig.appID);
        authUrl = authUrl.replace("SECRET", WeChatConfig.appSecret);
        authUrl = authUrl.replace("CODE", code);
        Map resultMap = HttpClientUtil.GetMethod(authUrl);
        WeChatOAuthInfo weChatOAuthInfo = null;
        try {
            if (resultMap.get("resultCode").equals("200")) {
                weChatOAuthInfo = JsonUtils.toObject((String) resultMap.get("result"), WeChatOAuthInfo.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return weChatOAuthInfo;
    }

}
