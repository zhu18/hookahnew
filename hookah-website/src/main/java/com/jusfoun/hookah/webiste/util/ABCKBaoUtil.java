package com.jusfoun.hookah.webiste.util;

import com.hitrust.trustpay.client.IFunctionID;
import com.hitrust.trustpay.client.market.IMarketTagName;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * Created by ndf on 2017/7/6.
 * 农行k宝接口工具类
 */
public class ABCKBaoUtil {
    //商户编号
    private final String merchantID= "337199903914E01";
    //商户名称
    private final String merchantName="测试商户";

    public void setParam(HttpServletRequest request){

        request.setAttribute("sign_merchantid",IMarketTagName.MARKET_SIGN_MERCHANTID);
        request.setAttribute("sign_merchantid_desc",IMarketTagName.MARKET_SIGN_MERCHANTID_DESC);
        request.setAttribute("sign_merchanttrxno",IMarketTagName.MARKET_SIGN_TRXNO);
        request.setAttribute("sign_merchanttrxno_desc",IMarketTagName.MARKET_SIGN_TRXNO_DESC);
        request.setAttribute("sign_functionid",IMarketTagName.MARKET_SIGN_FUNCTIONID);
        request.setAttribute("sign_functionid_desc",IMarketTagName.MARKET_SIGN_FUNCTIONID_DESC);
        request.setAttribute("sign_MerchantName",IMarketTagName.MARKET_SIGN_MERCHANTNAME);
        request.setAttribute("sign_MerchantName_desc",IMarketTagName.MARKET_SIGN_MERCHANTNAME_DESC);
        request.setAttribute("sign_CustName",IMarketTagName.MARKET_SIGN_CUSTNAME);
        request.setAttribute("sign_CustName_desc",IMarketTagName.MARKET_SIGN_CUSTNAME_DESC);
        request.setAttribute("sign_time",IMarketTagName.MARKET_SIGN_SIGNTIME);
        request.setAttribute("sign_time_desc",IMarketTagName.MARKET_SIGN_SIGNTIME_DESC);
        request.setAttribute("merchantID",merchantID);
        request.setAttribute("merchantName",merchantName);
        request.setAttribute("sign_up", IFunctionID.MARKET_SIGNUP);
        request.setAttribute("requestID", getRequestID());
    }

    /**
     * 获取请求流水号
     */

    public static String getRequestID (){
        Calendar calendar=Calendar.getInstance();
        int weekYear = calendar.getWeekYear();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        String monthStr=month<10?"0"+month:(month+"");
        String dayStr=day<10?"0"+day:(day+"");
        return  "RSU"+(weekYear+"").substring(2,4)+monthStr+dayStr+"001";
    }

}
