package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbCommentMapper;
import com.jusfoun.hookah.core.domain.zb.ZbComment;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCommentShowVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.ZbCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by computer on 2017/9/26.
 */
@Service
public class zbCommentServiceImpl extends GenericServiceImpl<ZbComment, Long> implements ZbCommentService {

    @Resource
    ZbCommentMapper zbCommentMapper;

    @Resource
    public void setDao(ZbCommentMapper zbCommentMapper) {
        super.setDao(zbCommentMapper);
    }

    @Override
    public List<ZbCommentShowVo> getLevelCountByUserId(String userId) {
        return zbCommentMapper.getLevelCountByUserId(userId);
    }

    @Override
    public List<ZbComment> getCommentRecordByUserId(String userId) {
        return zbCommentMapper.getCommentRecordByUserId(userId);
    }
}
