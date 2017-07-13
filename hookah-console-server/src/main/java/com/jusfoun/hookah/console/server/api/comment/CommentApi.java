package com.jusfoun.hookah.console.server.api.comment;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ctp on 2017/4/24.
 */
@RestController
@RequestMapping("/api/comment")
public class CommentApi extends BaseController {
    protected final static Logger logger = LoggerFactory.getLogger(CommentApi.class);

    @Resource
    private CommentService commentService;

    @RequestMapping("/findCommentsByCondition")
    public ReturnData findCommentsByCondition(String orderId,String goodsName,String startTime,
                          Integer pageNumber,Integer pageSize,String endTime,String commentContent,
                          String  status,String  goodsCommentGrade){
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",orderId);
        params.put("goodsName",goodsName);
        params.put("startTime",startTime);
        params.put("pageNumber",pageNumber);
        params.put("pageSize",pageSize);
        params.put("endTime",endTime);
        params.put("commentContent",commentContent);
        params.put("goodsCommentGrade",goodsCommentGrade);
        params.put("status",status);
        return commentService.findByCondition(params);

    }

    @RequestMapping("/checkComments")
    public ReturnData checkComments(String commentIds,String status){
        Map<String,String> params=new HashMap<>();
        params.put("commentIds",commentIds);
        params.put("status",status);
        return commentService.checkComment(params);

    }

}
