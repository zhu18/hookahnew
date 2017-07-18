package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends GenericDao<User> {

    int rechargeMoney(@Param("userId") String userId, @Param("rechagerMoney") long rechagerMoney);

    @Select("select * from user where org_id = #{orgId} order by add_time limit 1")
    User getOrgUser(String orgId);
}