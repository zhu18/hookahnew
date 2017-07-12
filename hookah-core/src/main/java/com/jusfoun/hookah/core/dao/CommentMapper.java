package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Comment;
import com.jusfoun.hookah.core.domain.vo.CommentVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CommentMapper extends GenericDao<Comment> {
    Double selectGoodsAvgByGoodsId(@Param("goodsId") String goodsId);

    List<String> selectGoodsIdsByData(@Param("startTime") Date startTime ,@Param("endTime") Date endTime);

    List<CommentVo> getListForComment(@Param("goodsId") String goodsId);

    int selectCountByCondition(Map<String,Object> params);

    List<CommentVo> selectCommentsByCondition(Map<String,Object> params);
}