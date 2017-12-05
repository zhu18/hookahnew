package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.FlowUser;
import com.jusfoun.hookah.core.domain.vo.FlowUserVo;
import com.jusfoun.hookah.core.domain.vo.FlowUsersVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

public interface FlowUserMapper extends GenericDao<FlowUser> {

    int insertAndGetId(FlowUser flowUser);

    FlowUsersVo selectSum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    FlowUsersVo selectBySource(@Param("startTime") String startTime, @Param("endTime") String endTime ,@Param("dataSource")Short dataSource);
}