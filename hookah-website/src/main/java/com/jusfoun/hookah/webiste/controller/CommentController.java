package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Comment;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CommentService;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ctp on 2017/4/24.
 */
@RestController
@RequestMapping("/comment")
public class CommentController extends  BaseController{
    protected final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Resource
    CommentService commentService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ReturnData addComm(@RequestBody List<Comment> comments) throws HookahException{
       ReturnData returnData = new ReturnData();
        User user = new User();
        try {
            user = getCurrentUser();
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
            return returnData;
        }
        return commentService.batchAddComment(comments,user);
    }

    @RequestMapping("/serachByGoodsId")
    public ReturnData findByGoodsId(String pageNumber,String pageSize,String goodsId){
        return commentService.findByGoodsId(pageNumber,pageSize,goodsId);
    }

    @RequestMapping("/countGoodsAvgGrades/{goodsId}")
    public ReturnData countGoodsAvgGradesByGoodsId(@PathVariable String goodsId){
        return commentService.countGoodsGradesByGoodsId(goodsId);
    }

    @RequestMapping("/findCommentsByCondition")
    public ReturnData findCommentsByCondition(String orderId,String goodsName,String startTime,
                          Integer pageNumber,Integer pageSize,String endTime,String commentContent){
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",orderId);
        params.put("goodsName",goodsName);
        params.put("startTime",startTime);
        params.put("pageNumber",pageNumber);
        params.put("pageSize",pageSize);
        params.put("endTime",endTime);
        params.put("commentContent",commentContent);
        return commentService.findByCondition(params);

    }

}
