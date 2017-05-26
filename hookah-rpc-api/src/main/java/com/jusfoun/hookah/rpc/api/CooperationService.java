package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Cooperation;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * Created by lt on 2017/5/8.
 */

public interface CooperationService extends GenericService<Cooperation,String> {

    public void addCooperation(Cooperation cooperation) throws Exception;

    public void modify(Cooperation cooperation) throws Exception;
}
