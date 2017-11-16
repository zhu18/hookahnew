package com.jusfoun.hookah.integral.listener;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.bo.JfBo;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.integral.contants.JfEnum;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@Component
public class RabbitMQJFHandelListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQJFHandelListener.class);

    @Resource
    JfRecordService jfRecordService;

    @Resource
    UserService userService;

    @Resource
    RedisOperate redisOperate;

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

            JfRecord jfRecord = new JfRecord();
            jfRecord.setUserId(jfBo.getUserId());
            jfRecord.setSourceId(jfBo.getSourceId());
            jfRecord.setNote(JfEnum.getMsgByCode(jfBo.getSourceId()));
            jfRecord.setExpire(Short.parseShort("0"));
            jfRecord.setOperator("System");
            jfRecord.setAddTime(new Date());
            jfRecordService.insertAndGetId(jfRecord);
            if(jfRecord.getId() != null){
                logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<积分消息处理成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }else {
                logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<<积分消息处理失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }


        }catch (Exception e){
            logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<<积分消息处理失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }

    }





    public static void main(String[] args) {

//        System.out.println(2 + (-3));
//        System.out.println(2 + (+3));

        System.out.println(new Random().nextInt(8));



    }
}
