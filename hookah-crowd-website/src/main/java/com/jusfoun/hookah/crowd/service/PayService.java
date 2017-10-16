package com.jusfoun.hookah.crowd.service;

import javax.servlet.http.HttpServletRequest;

public interface PayService {

    String toPayByZFB(String requirementSn, String tradeSn, Long amount, String flagNum, String notify_url, String return_url);

    /**
     * 接收支付宝返回结果 进行相应业务处理
     * @param request
     * @return
     */
    boolean handleZFBRs(HttpServletRequest request);
}
