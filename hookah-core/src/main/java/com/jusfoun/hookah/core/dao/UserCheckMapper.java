package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.UserCheck;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface UserCheckMapper extends GenericDao<UserCheck> {

    public UserCheck selectUserCheckInfo(String userId);
}