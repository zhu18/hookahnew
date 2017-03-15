package com.jusfoun.hookah.console.other.service.impl;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.stereotype.Service;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {
    @Override
    public String sayhello() {
        return "hi guy";
    }
}
