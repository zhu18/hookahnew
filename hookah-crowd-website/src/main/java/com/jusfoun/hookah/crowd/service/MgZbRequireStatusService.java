package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.core.generic.GenericService;

public interface MgZbRequireStatusService extends GenericService<MgZbRequireStatus, String> {

   void setRequireStatusInfo(String requirementSn, String statusName, String statusValue);

   MgZbRequireStatus getByRequirementSn(String requirementSn);
}
