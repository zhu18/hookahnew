package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.console.server.util.SensitiveWdFilter.WordFilter;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.CommentMapper;
import com.jusfoun.hookah.core.domain.Comment;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.CommentVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CommentService;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ctp on 2017/4/24.
 */
public class CommentServiceImpl extends GenericServiceImpl<Comment,String> implements CommentService{

    @Resource
    CommentMapper commentMapper;

    @Resource
    OrderInfoService orderInfoService;


    //状态(1:有效 0:无效)
    public static final Byte STATUS_INVAL = 1;
    public static final Byte STATUS_NO_INVAL = 0;


    //是否评论(0.未评论 1.已评论)
    public static final Integer COMMENT_STATUS_OK = 1;
    public static final Integer COMMENT_STATUS_NO = 0;

    private static final Double default_goods_grades = 5d;

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

        if(StringUtils.isBlank(goodsId)){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }

//        List<Condition> filters = new ArrayList<Condition>();
//        filters.add(Condition.eq("status",STATUS_INVAL));
//        filters.add(Condition.eq("goodsId",goodsId));
//
//        List<OrderBy> orderBys = new ArrayList<OrderBy>();
//        orderBys.add(OrderBy.desc("commentTime"));

        //参数校验
        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(pageNumber)) {
            pageNumberNew = Integer.parseInt(pageNumber);
        }
        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }

        returnData.setData(this.getListInPage(pageNumberNew,pageSizeNew,goodsId));

        return returnData;
    }

    public Pagination<CommentVo> getListInPage(Integer pageNum, Integer pageSize,String goodsId) {
        PageHelper.startPage(pageNum, pageSize);
        Page<CommentVo> page = (Page<CommentVo>) commentMapper.getListForComment(goodsId);
        Pagination<CommentVo> pagination = new Pagination<CommentVo>();
        pagination.setTotalItems(page.getTotal());
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        return pagination;
    }

    @Override
    public ReturnData findByCondition(Map<String,Object> params) {
        ReturnData returnData=new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        Pagination page = new Pagination<>();
        Object pageNumber=params.get("pageNumber");
        Object pageSize=params.get("pageSize");
        try {
            //参数校验
            if (Objects.isNull(pageNumber)) {
                pageNumber = HookahConstants.PAGE_NUM;
            }
            if (Objects.isNull(pageSize)) {
                pageSize = HookahConstants.PAGE_SIZE;
            }

            params.put("pageNumber",pageNumber);
            params.put("pageSize",pageSize);
            page = getListInPage(params);
            returnData.setData(page);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    public Pagination<CommentVo> getListInPage( Map<String,Object> params) {
        int pageSize=(Integer) params.get("pageSize");
        int pageNumber=(Integer) params.get("pageNumber");
        int startIndex= (pageNumber-1)*pageSize;
        params.put("startIndex",startIndex);
        int total=commentMapper.selectCountByCondition(params);
        List<CommentVo> list=commentMapper.selectCommentsByCondition(params);
        String content="";
        for (CommentVo comment:list) {
            content=comment.getCommentContent();
            comment.setIsContains(WordFilter.isContains(content));
            comment.setSensitiveWords(WordFilter.doFilter(content));
        }
        Pagination<CommentVo> pagination = new Pagination<CommentVo>();
        pagination.setTotalItems(total);
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNumber);
        pagination.setList(list);
        return pagination;
    }

    /**
     * 评价审核
     * @param params
     * @return
     */
    public ReturnData checkComment(Map<String,String> params) {
        ReturnData<Comment> returnData = new ReturnData<Comment>();
        returnData.setCode(ExceptionConst.Success);
        Map<String,Object> map=new HashMap<String,Object>();
        String commentIds=params.get("commentIds");
        String status=params.get("status");
        //参数判断
        if(StringUtils.isBlank(commentIds)){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("评论ID参数为空！");
            return returnData;
        }
        if(StringUtils.isBlank(status)){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("审核状态参数不能为空！");
            return returnData;
        }
        try {
            //参数处理
            String[] ids=commentIds.split(",");
            Byte statusByte = new Byte(status) ;
            map.put("ids",ids);
            map.put("status",statusByte);
            commentMapper.checkCommentByIds(map);
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("操作失败："+e.toString());
            e.printStackTrace();
        }

        return returnData;
    }

    @Override
    public ReturnData countGoodsGradesByGoodsId(String goodsId) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        if(StringUtils.isBlank(goodsId)){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }

        try {
            List<Condition> filters = new ArrayList<Condition>();
            filters.add(Condition.eq("goodsId",goodsId));
            filters.add(Condition.eq("status",STATUS_INVAL));

            List<Comment> comments = super.selectList(filters);
            Double aveGrades = default_goods_grades;
            if(null != comments && comments.size() > 0){
                //计算平均值
                aveGrades = commentMapper.selectGoodsAvgByGoodsId(goodsId);
            }
            returnData.setData(aveGrades);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }


        return returnData;
    }

    @Override
    public ReturnData batchAddComment(List<Comment> comments,User currUser) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        if(comments != null && comments.size() > 0){
            try {
                for (Comment comment : comments){
                    if(StringUtils.isBlank(comment.getOrderId())){
                        returnData.setCode(ExceptionConst.AssertFailed);
                        returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                        return returnData;
                    }
                    comment.setCommentTime(new Date());
                    comment.setAddTime(new Date());
                    comment.setUserId(currUser.getUserId());
                    comment = super.insert(comment);
                }
//                Integer count = super.insertBatch(comments);

                //修改订单评论状态
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setOrderId(comments.get(0).getOrderId());
                orderInfo.setCommentFlag(COMMENT_STATUS_OK);
                orderInfoService.updateByIdSelective(orderInfo);

                returnData.setData(comments);
            }catch (Exception e) {
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage(e.toString());
                e.printStackTrace();
            }

        }else{
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
        }

        return returnData;
    }

    @Override
    public ReturnData findGoodsGradeByData(String startTime, String endTime) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try{
//            List<Condition> fifters = new ArrayList<Condition>();
//            fifters.add(Condition.between("addTime",new Date[]{startTima,endTime}));

            Date startTimeDate = parseDate(startTime,"yyyy-MM-dd HH:mm:ss");
            Date endTimeDate = parseDate(endTime,"yyyy-MM-dd HH:mm:ss");

            List<Map<String,Object>> goodsGradeMapList = new ArrayList<Map<String,Object>>();

            List<String> goodsIds = commentMapper.selectGoodsIdsByData(startTimeDate,endTimeDate);
            if(null != goodsIds && goodsIds.size() > 0){
                for (String goodsId : goodsIds) {
                    Map<String,Object> goodsGradeMap = new HashMap<String,Object>();
                    Double aveGrades = default_goods_grades;
                    //计算平均值
                    aveGrades = commentMapper.selectGoodsAvgByGoodsId(goodsId);
                    goodsGradeMap.put("goodsId",goodsId);
                    goodsGradeMap.put("goodsGrades",aveGrades);
                    goodsGradeMapList.add(goodsGradeMap);
                }
            }

            returnData.setData(goodsGradeMapList);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }

        return returnData;
    }

    //String 转 Date
    private Date parseDate(String dateStr,String pattern) throws ParseException {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        d = sdf.parse(dateStr);
        sdf = null; //help GC
        return d;
    }
}
