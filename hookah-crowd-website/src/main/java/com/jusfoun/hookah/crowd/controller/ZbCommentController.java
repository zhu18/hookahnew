package com.jusfoun.hookah.crowd.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.zb.ZbComment;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCommentShowVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.service.ZbCommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ZbCommentController extends BaseController{

    @Resource
    ZbCommentService zbCommentService;

    @ResponseBody
    @RequestMapping("/api/levelCount")
    public ReturnData getLevelCountByUserId(){

        List<ZbCommentShowVo> list = null;

        try {
            list = zbCommentService.getLevelCountByUserId(getCurrentUser().getUserId());
        } catch (HookahException e) {
            e.printStackTrace();
        }

        return ReturnData.success(list);
    }

    @ResponseBody
    @RequestMapping("/api/getCommentRecord")
    public ReturnData getCommentRecord(String currentPage, String pageSize){

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<ZbComment> pagination = new Pagination<>();
        PageInfo<ZbComment> page = new PageInfo<>();

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
            List<ZbComment> list = zbCommentService.getCommentRecordByUserId(getCurrentUser().getUserId());

            page = new PageInfo<ZbComment>(list);

            pagination.setTotalItems(page.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(page.getList());

            returnData.setData(pagination);


        } catch (HookahException e) {
            logger.error("评价检索异常{}", e);
            returnData.setCode(ExceptionConst.Error);
            return returnData;
        }

        return returnData;
    }
}
