package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PaySign;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * Created by lt on 2017/7/5.
 */
public interface PaySignService extends GenericService<PaySign,String> {

    void sendMarketLogin();

    void sendMarketLogout();
}
