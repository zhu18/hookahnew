package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementPageHelper;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRecommendVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.service.ZbRecommendService;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class RequireController extends BaseController {

    @Resource
    ZbRequireService zbRequireService;

    @Resource
    ZbRecommendService zbRecommendService;


    @RequestMapping("/require/insertRequire")
    public void insertRequire() {

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
     *
     * @author crs
     */
    @RequestMapping("/api/require/allRequirement")
    @ResponseBody
    public ReturnData AllRequirement(String currentPage, String pageSize, ZbRequirement zbRequirement) {
        try {
            User user = this.getCurrentUser();
            return zbRequireService.getAllRequirement(currentPage, pageSize, zbRequirement);
        } catch (Exception e) {
            logger.error("需求查询失败", e);
            return ReturnData.error("需求查询失败");
        }
    }

    /**
     * 我的发布
     *
     * @author lt
     */
    @RequestMapping(value = "/api/require/getListByUser", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData<ZbRequirement> getListByUser(Integer pageNumber, Integer pageSize, Integer status, String title, String requireSn) {
        try {
            String userId = this.getCurrentUser().getUserId();
            if (pageNumber == null) pageNumber = Integer.parseInt(PAGE_NUM);
            if (pageSize == null) pageSize = Integer.parseInt(PAGE_SIZE);
            return zbRequireService.getListByUser(pageNumber, pageSize, userId, status, title, requireSn);
        } catch (Exception e) {
            logger.error("getListByUser", e);
            return ReturnData.error("系统错误：" + e.getMessage());
        }
    }


    /**
     * 需求大厅-发布
     *
     * @author crs
     */
    @RequestMapping("/api/require/updateStatus")
    @ResponseBody
    public ReturnData updateStatus(String id, String status, String applyDeadline ,Long applyId ,Long programId ,String checkAdvice) {
        try {
            return zbRequireService.updateStatus(id, status, applyDeadline ,applyId ,programId ,checkAdvice);
        } catch (Exception e) {
            logger.error("发布失败", e);
            return ReturnData.error("发布失败");
        }
    }

    /**
     * 需求详情查询
     * @author crs
     */
    @RequestMapping("/api/require/ReqCheck")
    @ResponseBody
    public ReturnData ReqCheck(String id) {
        try {
            return zbRequireService.reqCheck(id);
        } catch (Exception e) {
            logger.error("查询失败", e);
            return ReturnData.error("查询失败");
        }
    }

    /**
     * 数据众包列表页，根据条件展示需求列表
     *
     * @author ndf
     */
    @RequestMapping(value = "/require/getRequirementList")
    @ResponseBody
    public ReturnData<ZbRequirement> getRequirementList(ZbRequirementPageHelper helper) {
        try {
            if (helper.getPageNumber() == null) helper.setPageNumber(Integer.parseInt(PAGE_NUM));
            if (helper.getPageSize() == null) helper.setPageSize(Integer.parseInt(PAGE_SIZE));
            if (!StringUtils.isNotBlank(helper.getOrder())) helper.setOrder("apply_deadline");
            if (StringUtils.isNotBlank(helper.getSort())) helper.setSort("asc");
            String timeType = helper.getTimeType();
            if (StringUtils.isNotBlank(timeType)) {
                String pressTime = "";
                switch (timeType) {
                    case ("day"):
                        pressTime = DateUtil.datePlusOrMinusAsString(-1);
                        break;
                    case ("week"):
                        pressTime = DateUtil.datePlusOrMinusAsString(-7);
                        break;
                    case ("month"):
                        pressTime = DateUtil.monthPlusOrMinusAsString(-1);
                        break;
                    case ("gtmonth"):
                        pressTime = DateUtil.monthPlusOrMinusAsString(-1);
                        break;
                    default:
                        pressTime = "";
                        break;
                }
                helper.setPressTime(pressTime);
            }
            return zbRequireService.getRequirementList(helper);
        } catch (Exception e) {
            logger.error("getRequirementList", e);
            return ReturnData.error("系统错误！");
        }
    }

    /**
     * 数据众包首页--分类查询需求信息
     * @author zhaoshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("/require/requirementTypeInfo")
    public ReturnData requirementTypeInfo(){
        return zbRequireService.selectRequirementTypeInfo();
    }

    /**
     * 数据众包首页--推荐任务信息
     * @author zhaoshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("/require/recommendTasks")
    public ReturnData recommendTasksInfo(){
        return zbRecommendService.selectRecommendTasksInfo();
    }

    /**
     * 数据众包后台--选取任务报名
     * @author zhaoshuai
     * id 报名表主键
     * @return
     */
    @ResponseBody
    @RequestMapping("/api/require/checkEnroll")
    public ReturnData checkEnroll(Long id, Long requirementId){
        return zbRequireService.checkEnroll(id,requirementId);
    }

    /**
     * 服务商管理
     *
     * @author crs
     */
    @RequestMapping("/api/require/AllProvider")
    @ResponseBody
    public ReturnData getAllProvider(String currentPage, String pageSize,  Integer authType ,Integer status ,String upname,String userId, Date startTime, Date endTime) {
        try {
            return zbRequireService.getAllProvider(currentPage,pageSize,authType,status,upname,userId,startTime,endTime);
        } catch (Exception e) {
            logger.error("服务商查询失败", e);
            return ReturnData.error("服务商查询失败");
        }
    }

    /**
     * 服务商审核
     *
     * @author crs
     */
    @RequestMapping("/api/require/provideCheck")
    @ResponseBody
    public ReturnData provideCheck(String userId, Integer status) {
        try {
            return zbRequireService.provideCheck(userId, status);
        } catch (Exception e) {
            logger.error("审核失败", e);
            return ReturnData.error("审核失败");
        }
    }

    /**
     * 后台任务管理
     * @author zahoshuai
     */
    @ResponseBody
    @RequestMapping(value = "/api/require/taskManagement", method = RequestMethod.GET)
    public ReturnData<ZbRecommendVo> getTaskManagement(String sort, String currentPage, String pageSize, String userName, String title, String requireSn) {
        try {
            String order = "apply_deadline";
            return zbRequireService.getTaskManagement(order, sort, currentPage, pageSize, userName, title, requireSn);
        } catch (Exception e) {
            logger.error("getTaskManagement", e);
            return ReturnData.error("系统错误：" + e.getMessage());
        }
    }

    /**
     * 保存任务推荐编号
     * @author zahoshuai
     */
    @ResponseBody
    @RequestMapping(value = "/api/require/taskNumber", method = RequestMethod.GET)
    public ReturnData addTaskNumber(int orderNum, Long requirementId){
        return zbRequireService.addTaskNumber(orderNum,requirementId);
    }
}