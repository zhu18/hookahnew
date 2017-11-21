package com.jusfoun.hookah.integral.listener;

import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.bo.JfBo;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.integral.contants.JfEnum;
import com.jusfoun.hookah.rpc.api.CacheService;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import com.jusfoun.hookah.rpc.api.JfRuleService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理积分消息业务
 * ps:非生产环境下由于有多个消费端，不一定是你的消费端消费消息，收不到消息正常
 */
@Component
public class RabbitMQJFHandelListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQJFHandelListener.class);

    @Resource
    JfRecordService jfRecordService;

    @Resource
    UserService userService;

    @Resource
    JfRuleService jfRuleService;

    @RabbitListener(queues = RabbitmqQueue.CONTRACE_JF_MSG)
    public void jfHandle(JfBo jfBo) {

        logger.info("积分消息处理BO-{}", jfBo.toString());

        try {

            if(jfBo == null || !StringUtils.isNotBlank(jfBo.getUserId()) || jfBo.getSourceId() == null){

                logger.error("<<<<<<<积分消息处理BO为空>>>>>>>>>");
                return;
            }

            if(userService.selectById(jfBo.getUserId()) == null){
                logger.error("<<<<<<<用户不存在>>>>>>>");
                return;
            }

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("sn", jfBo.getSourceId()));
            JfRule jfRule = jfRuleService.selectOne(filters);
            if(jfRule == null){
                logger.error("<<<<<<<积分规则查询不存在>>>>>>>");
                return;
            }

            int n = jfRecordService.insertAndGetId(
                    new JfRecord(
                            jfBo.getUserId(),
                            Byte.parseByte(jfBo.getSourceId() + ""),
                            jfRule.getAction(),
                            jfRule.getScore(),
                            jfRule.getActionDesc(),
                            Byte.parseByte("0"),
                            new Date(),
                            "System",
                            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")),
                            jfRule.getActionDesc()));
            if(n == 1){
                logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<积分消息处理成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }else {
                logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<<积分消息处理失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
        }catch (Exception e){
            logger.error("<积分消息处理失败>>>{}", e);
        }

    }

    public static void main(String[] args) {
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
    }

}
