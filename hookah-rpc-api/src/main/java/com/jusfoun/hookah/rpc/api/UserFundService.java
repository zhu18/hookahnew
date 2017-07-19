package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.UserFundCritVo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by ctp on 2017/7/18.
 */
public interface UserFundService extends GenericService<User,String>{
    ReturnData getUserFundListPage(UserFundCritVo userFundCritVo);
}
