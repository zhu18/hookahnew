package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CartMapper;
import com.jusfoun.hookah.core.dao.PayBankCardMapper;
import com.jusfoun.hookah.core.domain.Cart;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.PayBankCardService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


public class PayBankCardImpl extends GenericServiceImpl<PayBankCard, String> implements PayBankCardService {

    @Resource
    private PayBankCardMapper payBankCardMapper;

    @Resource
    public void setDao(PayBankCardMapper payBankCardMapper) {
        super.setDao(payBankCardMapper);
    }

}
