package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.PaymentMapper;
import com.jusfoun.hookah.core.domain.Payment;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.PaymentService;

import javax.annotation.Resource;

/**
 * 支付
 * @author:jsshao
 * @date: 2017-3-17
 */
public class PaymentServiceImpl extends GenericServiceImpl<Payment, String> implements PaymentService {

    @Resource
    private PaymentMapper paymentMapper;


    @Resource
    public void setDao(PaymentMapper paymentMapper) {
        super.setDao(paymentMapper);
    }
}
