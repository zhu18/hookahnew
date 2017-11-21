package com.jusfoun.hookah.console.server.api.jf;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 积分规则
 */

@RestController
public class JfRuleController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(JfRuleController.class);

    @Resource
    JfRecordService jfRecordService;

    /**
     * 积分明细
     * @param currentPage
     * @param pageSize
     * @param userId
     * @param action
     * @param sourceId
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/api/jf/detail")
    public ReturnData Test1(String currentPage, String pageSize,
                            String userId, String action,
                            String sourceId,
                            String startTime, String endTime) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {

            returnData = jfRecordService.selectJfRecordListByUserId(currentPage,
                    pageSize, userId, action, sourceId, startTime, endTime);

        }catch (Exception e) {
            logger.error("获取积分明细异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[jf1]!⊙﹏⊙‖∣°");
        }

        return returnData;
    }

    /**
     * 获取用户信息积分概要
     * @return
     */
    @RequestMapping("/api/jf/outLine")
    public ReturnData Test2(String userId) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {
            returnData = jfRecordService.selectOneByUserId(userId);
        }catch (HookahException ex) {
            logger.error("获取用户登录信息异常-{}", ex.getMessage());
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[2]!⊙﹏⊙‖∣°");
        }catch (Exception e) {
            logger.error("获取用户信息积分概要异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[jf2]!⊙﹏⊙‖∣°");
        }

        return returnData;
    }

    /**
     * 获取用户信息积分概要 list
     * @param currentPage
     * @param pageSize
     * @param userName
     * @param userType
     * @param mobile
     * @return
     */
    @RequestMapping("/api/jf/uList")
    public ReturnData Test3(String currentPage, String pageSize,
                            String userName,
                            String userType, String mobile) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {
            returnData = jfRecordService.selectListByUserInfo(currentPage, pageSize, userName, userType, mobile);
        }catch (Exception e) {
            logger.error("获取用户信息积分概要列表异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[jf3]!⊙﹏⊙‖∣°");
        }

        return returnData;
    }

    /**
     * 修改用户积分 支持批量
     * @param userId
     * @param optType
     * @param score
     * @param note
     * @return
     */
    @RequestMapping("/api/jf/optJf")
    public ReturnData Test4(String userId, String optType, String score, String note) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {
            returnData = jfRecordService.optJf(userId, optType, score, note, this.getCurrentUser().getUserId());
        }catch (HookahException ex) {
            logger.error("用户登录异常-{}", ex);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(ex.getMessage());
        }catch (Exception e) {
            logger.error("修改用户积分异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[jf4]!⊙﹏⊙‖∣°");
        }
        return returnData;
    }


}
