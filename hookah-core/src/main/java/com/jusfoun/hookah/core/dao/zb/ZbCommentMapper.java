package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbComment;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCommentShowVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbTradeRecord;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZbCommentMapper extends GenericDao<ZbComment> {

    int insertAndGetId(ZbComment zbComment);

    List<ZbCommentShowVo> getLevelCountByUserId(@Param("userId") String userId);

    List<ZbComment> getCommentRecordByUserId(@Param("userId") String userId);

    List<ZbTradeRecord> selectTradeRecodeByUserId(@Param("userId") String userId);
}