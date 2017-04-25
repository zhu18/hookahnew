package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.CommentMapper;
import com.jusfoun.hookah.core.domain.Comment;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ctp on 2017/4/24.
 */
public class CommentServiceImpl extends GenericServiceImpl<Comment,String> implements CommentService{

    @Resource
    CommentMapper commentMapper;

    //状态(1:有效 0:无效)
    public static final Byte STATUS_INVAL = 1;
    public static final Byte STATUS_NO_INVAL = 0;


    @Resource
    public void setDao(CommentMapper commentMapper) {
        super.setDao(commentMapper);
    }


    @Override
    public ReturnData addComment(Comment comment) {
        ReturnData<Comment> returnData = new ReturnData<Comment>();
        returnData.setCode(ExceptionConst.Success);

        String orderId = comment.getOrderId();
        if(StringUtils.isBlank(orderId)){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try {
            comment.setCommentTime(new Date());
            comment.setAddTime(new Date());
            comment = super.insert(comment);
            if(comment == null) {
                throw new HookahException("操作失败");
            }
            return ReturnData.success(comment);
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }

        return returnData;
    }

    @Override
    public ReturnData findByGoodsId(String pageNumber,String pageSize,String goodsId) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("status",STATUS_INVAL));
        filters.add(Condition.eq("goodsId",goodsId));

        List<OrderBy> orderBys = new ArrayList<OrderBy>();
        orderBys.add(OrderBy.desc("commentTime"));

        //参数校验
        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(pageNumber)) {
            pageNumberNew = Integer.parseInt(pageNumber);
        }
        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }

        returnData.setData(super.getListInPage(pageNumberNew,pageSizeNew,filters,orderBys));

        return returnData;
    }

    @Override
    public ReturnData findByCondition(Map<String, Object> params) {
        return null;
    }
}
