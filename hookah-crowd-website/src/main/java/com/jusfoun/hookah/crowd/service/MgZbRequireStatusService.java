package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.core.generic.GenericService;

public interface MgZbRequireStatusService extends GenericService<MgZbRequireStatus, String> {

   /**
    * 插入流程状态时间
    * @param requirementSn 需求编号
    * @param statusName    当前状态名
    * @param statusValue   当前状态值
    */
   void setRequireStatusInfo(String requirementSn, String statusName, String statusValue);

   /**
    * 根据需求编号查询流程状态时间
    * @param requirementSn 需求编号
    * @return
    */
   MgZbRequireStatus getByRequirementSn(String requirementSn);
}
