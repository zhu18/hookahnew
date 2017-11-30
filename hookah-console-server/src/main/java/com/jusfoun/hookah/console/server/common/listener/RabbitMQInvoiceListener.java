package com.jusfoun.hookah.console.server.common.listener;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.vo.InvoiceDTOVo;
import com.jusfoun.hookah.rpc.api.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class RabbitMQInvoiceListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQInvoiceListener.class);

    @Resource
    InvoiceService invoiceService;

    @RabbitListener(queues = RabbitmqQueue.CONTRACE_CENTER_CHANNEL)
    public void operaPushGoods(InvoiceDTOVo invoiceDTOVo) {

        logger.info("开始执行发票申请：" + (invoiceDTOVo == null ? "数据传输对象为空！": JSON.toJSONString(invoiceDTOVo)));
        if (Objects.nonNull(invoiceDTOVo)) {
            try {
                invoiceService.addInvoice(invoiceDTOVo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("提交失败：" + e.getMessage());
            }
        }
        logger.info("结束执行发票申请：" + (invoiceDTOVo == null ? "数据传输对象为空！": JSON.toJSONString(invoiceDTOVo)));

    }
}
