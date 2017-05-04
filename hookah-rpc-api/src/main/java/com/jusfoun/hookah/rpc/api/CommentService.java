package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Comment;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;
import java.util.Map;

/**
 * Created by ctp on 2017/4/24.
 */
public interface CommentService extends GenericService<Comment,String>{

    /**
     * 添加评论
     * @param comment
     * @return
     */
    ReturnData addComment(Comment comment);

    /**
     * 查询该商品下的所有评论
     * @param GoodsId
     * @return
     */
    ReturnData findByGoodsId(String pageNumber,String pageSize,String GoodsId);

    /**
     * 查询条件下的所有评论
     * @param params
     * @return
     */
    ReturnData findByCondition(Map<String,Object> params);

    /**
     * 计算
     * @param GoodsId
     * @return
     */
    ReturnData countGoodsGradesByGoodsId(String GoodsId);


    /**
     * 添加评论
     * @param comments
     * @return
     */
    ReturnData batchAddComment(List<Comment> comments, User currUser);

}
