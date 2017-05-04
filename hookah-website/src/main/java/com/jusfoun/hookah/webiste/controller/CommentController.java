package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Comment;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CommentService;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ctp on 2017/4/24.
 */
@RestController
@RequestMapping("/comment")
public class CommentController extends  BaseController{
    protected final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Resource
    CommentService commentService;

    @RequestMapping("/add")
    public ReturnData addComm(Comment comment) throws HookahException{
        comment.setUserId(super.getCurrentUser() == null?null:super.getCurrentUser().getUserId());
        return commentService.addComment(comment);
    }

    @RequestMapping("/serachByGoodsId")
    public ReturnData findByGoodsId(String pageNumber,String pageSize,String goodsId){
        return commentService.findByGoodsId(pageNumber,pageSize,goodsId);
    }

    @RequestMapping("/countGoodsAvgGrades/{goodsId}")
    public ReturnData countGoodsAvgGradesByGoodsId(@PathVariable String goodsId){
        return commentService.countGoodsGradesByGoodsId(goodsId);
    }


}
