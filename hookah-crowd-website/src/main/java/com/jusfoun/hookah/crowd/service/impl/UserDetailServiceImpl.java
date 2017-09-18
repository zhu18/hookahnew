package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.UserDetailMapper;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.UserDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/11 下午6:40
 * @desc
 */
@Service
public class UserDetailServiceImpl extends GenericServiceImpl<UserDetail, String> implements UserDetailService {
    @Resource
    UserDetailMapper userDetailMapper;

    @Resource
    public void setDao(UserDetailMapper userDetailMapper) {
        super.setDao(userDetailMapper);
    }

}
