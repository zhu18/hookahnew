package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Comment;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends GenericDao<Comment> {
    Double selectGoodsAvgByGoodsId(@Param("goodsId") String goodsId);
}