package com.jusfoun.hookah.crowd.service;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public interface PayService {

    String toPayByZFB(String requirementSn, String tradeSn, Long amount, String flagNum, String notify_url, String return_url);

    /**
     * 接收支付宝返回结果 进行相应业务处理
     * @param request
     * @return
     */
    ModelAndView handleZFBRs(HttpServletRequest request);
}
