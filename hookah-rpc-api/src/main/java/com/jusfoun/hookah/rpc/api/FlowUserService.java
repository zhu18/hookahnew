package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.FlowUser;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by admin on 2017/12/1.
 */
public interface FlowUserService extends GenericService<FlowUser,Long> {

    public ReturnData tongjiList (String startTime, String endTime);
}
