package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.SysNews;
import com.jusfoun.hookah.core.domain.vo.SysNewsVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysNewsMapper extends GenericDao<SysNews> {

    //查询列表
    public List<SysNewsVo> selectByGroup(@Param("group") String group, @Param("sonGroup") String sonGroup);

    //g根据ID获得相关信息
    public List<SysNewsVo> selectNewsByID(@Param("newId") String newId);


}