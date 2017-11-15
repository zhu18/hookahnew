package com.jusfoun.hookah.integral.listener;

import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.bo.JfBo;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class RabbitMQJFHandelListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQJFHandelListener.class);

    @Resource
    JfRecordService jfRecordService;

    @RabbitListener(queues = RabbitmqQueue.WAIT_SETTLE_ORDERS)
    public void jfHandle(JfBo jfBo) {

        logger.info("积分消息处理BO-{}", jfBo.toString());


        JfRecord jfRecord = new JfRecord();
        jfRecord.setUserId(jfBo.getUserId());
        jfRecord.setSourceId(jfBo.getSourceId());
//        jfRecord.setNote();
        jfRecord.setExpire(Short.parseShort("0"));
        jfRecord.setOperator("System");
        jfRecord.setAddTime(new Date());

    }

    public static void main(String[] args) {

        System.out.println(2 + (-3));
        System.out.println(2 + (+3));
    }
}
