package com.jusfoun.hookah.webiste.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.vo.JfShowVo;
import com.jusfoun.hookah.core.domain.vo.WithdrawVo;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 积分业务
 */

@RestController
public class JfController extends BaseController {

    @Resource
    JfRecordService jfRecordService;

    @RequestMapping("/jf/getList")
    public ReturnData getJfList(String pageNum, String pageSize, String type){

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        PageInfo<JfShowVo> page = new PageInfo<>();
        Pagination<JfShowVo> pagination = new Pagination<>();

        try {

            if(StringUtils.isBlank(type)){
                return ReturnData.error("参数有误！^_^");
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(pageNum)) {
                pageNumberNew = Integer.parseInt(pageNum);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            String userId = getCurrentUser().getUserId();

            PageHelper.startPage(pageNumberNew, pageSizeNew);
            List<JfShowVo> list = jfRecordService.getJfRecord(userId, type);
            page = new PageInfo<JfShowVo>(list);

            pagination.setTotalItems(page.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(page.getList());

            returnData.setData(pagination);

        } catch (Exception e) {
            logger.error("用户积分查询异常", e);
            return ReturnData.error("系统繁忙，请稍后再试！[del]^_^");
        }

        return returnData;
    }
}
