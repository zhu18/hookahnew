package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.UserMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:29
 * @desc
 */
@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    public void setDao(UserMapper userMapper) {
        super.setDao(userMapper);
    }

    public User insert(User user) {
        String encPassword = new Md5Hash(user.getPassword()).toString();
        user.setPassword(encPassword);
        User u = super.insert(user);
        return u;
    }

//    @Override
//    public String sayhello() {
//        return "hi guy";
//    }
}
