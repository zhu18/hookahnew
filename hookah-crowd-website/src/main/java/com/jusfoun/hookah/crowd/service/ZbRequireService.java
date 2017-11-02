package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementPageHelper;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.Date;
import java.util.List;

public interface ZbRequireService extends GenericService<ZbRequirement, Long> {

    int insertRecord(ZbRequirement zbRequirement);

    ReturnData<ZbRequirement> getListByUser(Integer pageNum, Integer pageSize, String userId,
                                            Integer status, String title , String requireSn) throws HookahException;

    ReturnData<ZbRequirement> getAllRequirement(String currentPage, String pageSize, ZbRequirement zbRequirement);

    ReturnData<ZbRequirement> updateStatus(String id, String status ,String applyDeadline ,Long applyId ,Long programId ,String checkAdvice);

    ReturnData<ZbRequirement> getRequirementList(ZbRequirementPageHelper helper);

    ReturnData<ZbRequirement> reqCheck(String id);

    List<ZbRequirement> selectTradeListByUID(String userId);

    ReturnData selectRequirementTypeInfo();

    //服务商管理
    ReturnData<MgZbProvider> getAllProvider(String currentPage, String pageSize,  Integer authType ,Integer status ,String upname,String userId, Date startTime, Date endTime);

    ReturnData checkEnroll(Long id, Long requirementId);

    //服务商审核
    ReturnData<MgZbProvider> provideCheck(String userId, Integer status);

    //任务管理
    ReturnData<ZbRequirement> getTaskManagement(String currentPage, String pageSize, String userName, String title, String requireSn);

    ReturnData addTaskNumber(int orderNum, Long requirementId);

}
