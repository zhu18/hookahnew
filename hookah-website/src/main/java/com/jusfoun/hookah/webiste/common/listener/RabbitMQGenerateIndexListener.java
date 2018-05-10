package com.jusfoun.hookah.webiste.common.listener;


import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.webiste.util.HtmlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * 监听生成首页
 */

@Component
public class RabbitMQGenerateIndexListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQGenerateIndexListener.class);

    @RabbitListener(queues = RabbitmqQueue.CONTRACE_GENERATE_INDEX)
    public void generateIndex(boolean bl) {

        logger.info("<--------------------已收到重新生成首页的消息------------------------>");

        try {

            HtmlGenerator h = new HtmlGenerator("webappname");
            File cfgFile = ResourceUtils.getFile("classpath:");
            String absolutePath = cfgFile.getAbsolutePath();
            String htmlPath = absolutePath + File.separator + "views" + File.separator + "bdgstore.html" ;
            h.createHtmlPage("http://gbdex.bdgstore.cn", htmlPath);
            logger.info("已生成首页静态页面到views---->>>>>>>>-----bdgstore.html");

        }catch (Exception e){
            logger.error("首页生成失败", e.getMessage());
        }
    }
}