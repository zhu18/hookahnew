package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbComment;
import com.jusfoun.hookah.core.domain.zb.ZbProgram;
import com.jusfoun.hookah.core.domain.zb.vo.ZbProgramVo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by hhh on 2017/9/20.
 */
public interface ZbProgramService extends GenericService<ZbProgram, Long> {
    /**
     * 提交方案
     */
    ReturnData insertRecord(ZbProgramVo zbProgramVo);

    /**
     * 修改方案状态
     */
    ReturnData updataStatus(Long progId,Short status);

    /**
     *修改方案
     */
    ReturnData editProgram(ZbProgramVo zbProgramVo);


    /**
     *查询方案(根据需求id 查询当前的方案)
     */
    ReturnData selectProgramByReqId(Long reqId);

    /**
     * 对需求方评价
     */
    ReturnData addRequirementComment(ZbComment zbComment);
}
