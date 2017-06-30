package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends GenericDao<User> {

    int rechargeMoney(@Param("userId") String userId, @Param("rechagerMoney") long rechagerMoney);
}