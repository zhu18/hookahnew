package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementPageHelper;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
    @RequestMapping("/api/require/allRequirement")
    @ResponseBody
    public ReturnData AllRequirement(String currentPage, String pageSize, ZbRequirement zbRequirement, User user) {
        try {
                   user=getCurrentUser();
                return zbRequireService.getAllRequirement(currentPage, pageSize, zbRequirement ,user);
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
            logger.error("getListByUser", e);
            return ReturnData.error("系统错误："+e.getMessage());
        }
    }


    /**
     * 需求大厅-发布
     * @author crs
     */
    @RequestMapping("/require/updateStatus")
    @ResponseBody
    public ReturnData updateStatus( ZbRequirement zbRequirement) {
        try {
            return zbRequireService.updateStatus(zbRequirement);
        }catch (Exception e){
            logger.error("发布失败", e);
            return ReturnData.error("发布失败");
        }
    }
    /**
     * 需求大厅-发布
     * @author crs
     */
    @RequestMapping("/api/require/ReqCheck")
    @ResponseBody
    public ReturnData ReqCheck( ZbRequirement zbRequirement ,User user) {
        try {
            user=getCurrentUser();
            return zbRequireService.reqCheck(zbRequirement ,user);
        }catch (Exception e){
            logger.error("查询失败", e);
            return ReturnData.error("查询失败");
        }
    }

    /**
     * 数据众包列表页，根据条件展示需求列表
     * @author ndf
     */
    @RequestMapping(value = "/require/getRequirementList")
    @ResponseBody
    public ReturnData<ZbRequirement> getRequirementList (ZbRequirementPageHelper helper){
        try {
            String userId = this.getCurrentUser().getUserId();
            if (helper.getPageNumber()==null)helper.setPageNumber(Integer.parseInt(PAGE_NUM));
            if (helper.getPageSize()==null) helper.setPageSize(Integer.parseInt(PAGE_SIZE));
            if (!StringUtils.isNotBlank(helper.getOrder()))  helper.setOrder("apply_deadline");
            if (!StringUtils.isNotBlank(helper.getSort()))  helper.setSort("desc");
            String timeType=helper.getTimeType();
            if (StringUtils.isNotBlank(timeType)){
                String pressTime= "";
                switch (timeType){
                    case ("day"): pressTime = DateUtil.datePlusOrMinusAsString(-1);break;
                    case ("week"): pressTime = DateUtil.datePlusOrMinusAsString(-7);break;
                    case ("month"): pressTime = DateUtil.monthPlusOrMinusAsString(-1);break;
                    case ("gtmonth"): pressTime = DateUtil.monthPlusOrMinusAsString(-1);break;
                    default: pressTime= "";break;
                }
                helper.setPressTime(pressTime);
            }
            return zbRequireService.getRequirementList( helper);
        } catch (Exception e) {
            logger.error("getRequirementList", e);
            return ReturnData.error("系统错误！");
        }
    }

}
