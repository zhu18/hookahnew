package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbComment;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCommentShowVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * Created by zhaoshuai on 2017/9/26.
 */
public interface ZbCommentService extends GenericService<ZbComment, Long> {

    /**
     * 根据用户ID获取所有相关评价 并分组统计
     * @param userId
     * @return
     */
    List<ZbCommentShowVo> getLevelCountByUserId(String userId);

    List<ZbComment> getCommentRecordByUserId(String userId);
}
