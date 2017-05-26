package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.UserCheck;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by ctp on 2017/5/25.
 */
public interface UserCheckService extends GenericService<UserCheck, String> {

    public ReturnData addUserCheck(UserCheck userCheck);

    public ReturnData search(UserCheck userCheck,Integer pageNumber,Integer pageSize);

}
