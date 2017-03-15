package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.UserMongoService;
import org.springframework.stereotype.Service;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class UserMongoServiceImpl extends GenericMongoServiceImpl<User, String> implements UserMongoService {

}
