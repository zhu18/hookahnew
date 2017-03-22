package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface UserService extends GenericService<User,String> {

    String sayhello();
}
