package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RequireController extends BaseController{

    @Resource
    ZbRequireService zbRequireService;

    @RequestMapping("/require/insertRequire")
    public void insertRequire(){

        ZbRequirement zb = new ZbRequirement();
        zb.setTitle("123456");
        zbRequireService.insertRecord(zb);

        try {
            String Uname = this.getCurrentUser().getUserName();
            System.out.println(Uname);
        } catch (HookahException e) {
            e.printStackTrace();
        }
    }
    /**
     * 需求大厅
     * @author crs
     */
    @RequestMapping("/require/allRequirement")
    @ResponseBody
    public ReturnData AllRequirement(String currentPage, String pageSize, ZbRequirement zbRequirement) {
        try {
                return zbRequireService.getAllRequirement(currentPage, pageSize, zbRequirement);
        }catch (Exception e){
                logger.error("需求查询失败", e);
                return ReturnData.error("需求查询失败");
        }
    }

    /**
     * 我的发布
     * @author lt
     */
    @RequestMapping(value = "/api/require/getListByUser", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData<ZbRequirement> getListByUser (Integer pageNumber, Integer pageSize, Integer status, String title ,String requireSn){
        try {
            String userId = this.getCurrentUser().getUserId();
            if (pageNumber==null) pageNumber = Integer.parseInt(PAGE_NUM);
            if (pageSize==null) pageSize = Integer.parseInt(PAGE_SIZE);
            return zbRequireService.getListByUser( pageNumber, pageSize, userId ,status, title, requireSn);
        } catch (Exception e) {
            logger.error("分页查询我的发布错误", e);
            return ReturnData.error("分页查询我的发布错误");
        }
    }


}
