package com.jusfoun.hookah.crowd.service;

/**
 * 需求到交互截止日期  方案为提交
 *  1) 修改报名表信息status 为9违约失败
 * Created by ctp on 2017/10/25.
 */
public interface ZbRequirementTaskService {

    void flushRequireRenegeStatusTask();

}
