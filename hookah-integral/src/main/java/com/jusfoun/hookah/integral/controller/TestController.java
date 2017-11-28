package com.jusfoun.hookah.integral.controller;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.bo.JfBo;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.integral.service.impl.ScheduledServiceImpl;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import com.jusfoun.hookah.rpc.api.JfRuleService;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@RestController
public class TestController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    MqSenderService mqSenderService;

    @Resource
    RedisOperate redisOperate;

    @Resource
    JfRuleService jfRuleService;

    @Resource
    JfRecordService jfRecordService;

    @RequestMapping("/")
    public String Test1() {

        String set = redisOperate.set("zs", "123456", 0);
        String get = redisOperate.get("zs");

        System.out.println(set);
        System.out.println(get);
        return "jf";
    }

    @RequestMapping("/msg")
    public String Test2(String userId, Integer v) {
        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSG, new JfBo(userId, v));
        return "jf";
    }


    @RequestMapping("/msg3")
    public String Test3(String userId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < 10; x++) {
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSG,
                            new JfBo(userId, new Random().nextInt(7) + 1));
                }
            }
        }) {
        }.start();

        return "jf";
    }

    @RequestMapping("/msg4")
    public String Test4(String userId) {

        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSG,
                new JfBo(userId, new Random().nextInt(7) + 1));

        return "jf";
    }

    /**
     * 测试 积分明细
     * @return
     */
    @RequestMapping("/msg5")
    public ReturnData Test5(String pageNum, String pageSize,
                            String userId, String action,
                            String sourceId,
                            String startTime, String endTime) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {

            returnData = jfRecordService.selectJfRecordListByUserId(pageNum,
                    pageSize, userId, action, sourceId, startTime, endTime);

        }catch (Exception e) {
            logger.error("获取积分明细异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[5]^_^");
        }

        return returnData;
    }

    /**
     * 获取用户信息积分概要
     * @return
     */
    @RequestMapping("/msg6")
    public ReturnData Test6() {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {
            String userId = "73b93bda947611e7b7dd26d6fa745dc9";

            returnData = jfRecordService.selectOneByUserId(userId);
        }catch (Exception e) {
            logger.error("获取用户信息积分概要异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[6]^_^");
        }

        return returnData;
    }

    /**
     * 获取用户信息积分概要 list
     * @return
     */
    @RequestMapping("/msg7")
    public ReturnData Test7(String pageNum, String pageSize,
                            String userName,
                            String userType, String mobile) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {
            returnData = jfRecordService.selectListByUserInfo(pageNum, pageSize, userName, userType, mobile);
        }catch (Exception e) {
            logger.error("获取用户信息积分概要列表异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[7]^_^");
        }

        return returnData;
    }

    /**
     * 修改用户积分
     * @param userId
     * @param optType
     * @param score
     * @param note
     * @return
     */
    @RequestMapping("/msg8")
    public ReturnData Test8(String userId, String optType, String score, String note) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {
            returnData = jfRecordService.optJf(userId, optType, score, note, this.getCurrentUser().getUserId());
        }catch (HookahException ex) {
            logger.error("修改用户积分异常-{}", ex);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(ex.getMessage());
        }catch (Exception e) {
            logger.error("修改用户积分异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[8]^_^");
        }

        return returnData;
    }

    /**
     * 获取积分设置规则
     * @return
     */
    @RequestMapping("/msg9")
    public ReturnData Test9(String currentPage, String pageSize) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(currentPage)) {
            pageNumberNew = Integer.parseInt(currentPage);
        }

        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }

        try {
            returnData.setData(jfRuleService.getListInPage(pageNumberNew, pageSizeNew, null, null));
        }catch (Exception e) {
            logger.error("获取积分规则异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[jr1]!⊙﹏⊙‖∣°");
        }
        return returnData;
    }

    /**
     * 根据类型修改积分规则
     * @return
     */
    @RequestMapping("/msg10")
    public ReturnData Test10(String id, String type, String score,
                             String upperLimitScore, String upperLimitTime,
                             String lowerLimitScore, String lowerLimitTime) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {

            if(!StringUtils.isNotBlank(id)){
                return ReturnData.error("参数不能为空!⊙﹏⊙‖∣°");
            }

            if(!StringUtils.isNotBlank(type)
                    && !StringUtils.isNotBlank(score)
                        && !StringUtils.isNotBlank(upperLimitScore)
                            && !StringUtils.isNotBlank(upperLimitTime)
                                && !StringUtils.isNotBlank(lowerLimitScore)
                                    && !StringUtils.isNotBlank(lowerLimitTime)
                    ){
                return ReturnData.error("无有效参数!⊙﹏⊙‖∣°");
            }

            JfRule jfRule = jfRuleService.selectById(Integer.parseInt(id));

            if(jfRule == null){
                return ReturnData.error("未查询到该条积分规则!⊙﹏⊙‖∣°");
            }

            if(!StringUtils.isNotBlank(type)
                    && !StringUtils.isNotBlank(score)
                        && score != null
                            && Integer.parseInt(score) > 0){

                if(type.equals("1")){
                    jfRule.setScore(jfRule.getScore() + Integer.parseInt(score));
                } else if(type.equals("2")){
                    jfRule.setScore(jfRule.getScore() - Integer.parseInt(score));
                } else {
                    return ReturnData.error("请求参数有误[type]!⊙﹏⊙‖∣°");
                }
            }

//            jfRule.setOperator(this.getCurrentUser().getUserId());
            if(StringUtils.isNotBlank(upperLimitScore)){
                jfRule.setUpperLimit(Integer.parseInt(upperLimitScore));
            }
            if(StringUtils.isNotBlank(lowerLimitScore)){
                jfRule.setLowerLimit(Integer.parseInt(lowerLimitScore));
            }
            if(StringUtils.isNotBlank(upperLimitTime)){
                jfRule.setUpperTimeLimit(Byte.parseByte(upperLimitTime));
            }
            if(StringUtils.isNotBlank(lowerLimitTime)){
                jfRule.setLowerTimeLimit(Byte.parseByte(lowerLimitTime));
            }
            jfRule.setUpdateTime(new Date());

            int n = jfRuleService.updateByIdSelective(jfRule);
            if(n == 1){
                returnData.setMessage("积分规则修改成功！^_^");
            } else {
                returnData.setMessage("积分规则修改失败！>_<|||");
            }
//        }catch (HookahException ex) {
//            logger.error("获取积分规则异常-{}", ex);
//            returnData.setCode(ExceptionConst.Error);
//            returnData.setMessage(ex.getMessage());
        }catch (Exception e) {
            logger.error("获取积分规则异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[jr1]!⊙﹏⊙‖∣°");
        }
        return returnData;
    }

    @Resource
    ScheduledServiceImpl scheduled;

    @RequestMapping("/msg11")
    public String Test11() {

        scheduled.handleSettle();

        return "jf";
    }

    @RequestMapping("/msg12")
    public String Test12(String u1, String u2) {

        jfRecordService.registerHandle(u1, u2);

        return "jf";
    }


}
