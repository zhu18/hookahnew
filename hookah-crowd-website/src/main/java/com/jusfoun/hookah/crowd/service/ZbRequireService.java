package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;

public interface ZbRequireService extends GenericService<ZbRequirement, Long> {

    int insertRecord(ZbRequirement zbRequirement);

    ReturnData<ZbRequirement> getListByUser(Integer pageNum, Integer pageSize, String userId,
                                            Integer status, String title , String requireSn) throws HookahException;

    ReturnData<ZbRequirement> getAllRequirement(String currentPage, String pageSize, ZbRequirement zbRequirement);
}
