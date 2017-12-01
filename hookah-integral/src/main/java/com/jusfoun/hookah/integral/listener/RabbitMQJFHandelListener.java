package com.jusfoun.hookah.integral.listener;

import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.bo.JfBo;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.integral.contants.JfContants;
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

            JfRecord jfRecord = new JfRecord();
            jfRecord.setUserId(jfBo.getUserId());
            jfRecord.setSourceId(Byte.parseByte(jfBo.getSourceId() + ""));
            jfRecord.setAction(jfRule.getAction());

            if(jfRule.getUpperLimit() != null && jfRule.getScore() != null){

                jfRecord.setScore(jfRule.getScore() > jfRule.getUpperLimit() ? jfRule.getUpperLimit() : jfRule.getScore());
            }
            jfRecord.setExpire(Byte.parseByte("0"));
            jfRecord.setAddTime(new Date());
            jfRecord.setOperator("System");
            jfRecord.setAddDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
            jfRecord.setActionDesc(jfRule.getActionDesc());

            if(jfRule.getSn().equals(Byte.parseByte("1"))
                    || jfRule.getSn().equals(Byte.parseByte("2"))
                        || jfRule.getSn().equals(Byte.parseByte("3"))){

                jfRecord.setNote("有效期至" +
                        LocalDate.now().plusYears(JfContants.JF_EXPIRE_YEAR)
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } else {
//                jfRecord.setNote(jfBo.getNotes());
            }

            int n = jfRecordService.insertAndGetId(jfRecord);
            if(n == 1){
                logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<积分消息处理成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }else {
                logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<<积分消息处理失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
        }catch (Exception e){
            logger.error("<积分消息处理失败>>>{}", e);
        }

    }

}
