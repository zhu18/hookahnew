package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

            returnData = jfRecordService.getJfRecord(pageNumberNew, pageSizeNew, userId, type);

        }catch (HookahException e2){
            logger.error("用户登录异常", e2);
            return ReturnData.error(e2.getMessage());
        }catch (Exception e){
            logger.error("用户积分查询异常", e);
            return ReturnData.error("系统繁忙，请稍后再试！[del]^_^");
        }

        return returnData;
    }

}
