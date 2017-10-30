package com.jusfoun.hookah.crowd.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.zb.ZbComment;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCommentShowVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbTradeRecord;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.service.ZbCommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ZbCommentController extends BaseController{

    @Resource
    ZbCommentService zbCommentService;

    @ResponseBody
    @RequestMapping("/api/levelCount")
    public ReturnData getLevelCountByUserId(String userId){

        List<ZbCommentShowVo> list = null;

        try {
            list = zbCommentService.getLevelCountByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ReturnData.success(list);
    }

    /**
     * 客户评价
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/getCommentRecord", method = RequestMethod.POST)
    public ReturnData getCommentRecord(String currentPage, String pageSize, String userId){

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<ZbComment> pagination = new Pagination<>();
        PageInfo<ZbComment> page = new PageInfo<>();

        try {

            if(!StringUtils.isNotBlank(userId)){
                returnData.setMessage("查询参数不能为空^_^");
                returnData.setCode(ExceptionConst.Error);
                return returnData;
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            PageHelper.startPage(pageNumberNew, pageSizeNew);   //pageNum为第几页，pageSize为每页数量
            List<ZbComment> list = zbCommentService.getCommentRecordByUserId(userId);

            page = new PageInfo<ZbComment>(list);

            pagination.setTotalItems(page.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(page.getList());

            returnData.setData(pagination);


        } catch (Exception e) {
            logger.error("评价检索异常{}", e);
            returnData.setCode(ExceptionConst.Error);
            return returnData;
        }

        return returnData;
    }

    /**
     * 交易记录
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/getTradeRecord", method = RequestMethod.POST)
    public ReturnData getTradeRecord(String currentPage, String pageSize, String userId){

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<ZbTradeRecord> pagination = new Pagination<>();
        PageInfo<ZbTradeRecord> page = new PageInfo<>();

        try {

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            PageHelper.startPage(pageNumberNew, pageSizeNew);   //pageNum为第几页，pageSize为每页数量
            List<ZbTradeRecord> list = zbCommentService.getTradeRecordByUserId(userId);
            page = new PageInfo<ZbTradeRecord>(list);

            pagination.setTotalItems(page.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(page.getList());

            returnData.setData(pagination);


        } catch (Exception e) {
            logger.error("评价检索异常{}", e);
            returnData.setCode(ExceptionConst.Error);
            return returnData;
        }

        return returnData;
    }
}
