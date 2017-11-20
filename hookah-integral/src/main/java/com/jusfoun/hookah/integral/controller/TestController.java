package com.jusfoun.hookah.integral.controller;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.bo.JfBo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

@RestController
public class TestController {

    protected Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    MqSenderService mqSenderService;

    @Resource
    RedisOperate redisOperate;

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
            returnData = jfRecordService.optJf(userId, optType, score, note);
        }catch (Exception e) {
            logger.error("修改用户积分异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[8]^_^");
        }

        return returnData;
    }

}
