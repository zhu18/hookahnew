package com.jusfoun.hookah.pay;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

/**
 * dx
 */
@Configuration
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class, DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableScheduling
@ComponentScan
@EnableTransactionManagement
public class PayServerLauncher {

    private static final Logger logger = LoggerFactory.getLogger(PayServerLauncher.class);

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(new Object[]{
            "classpath*:spring/spring-config.xml",
            "classpath*:hookah_rpc_server.xml",
            "classpath*:hookah_rpc_client.xml",
            "classpath*:hookah_rpc_server_pay.xml",
            PayServerLauncher.class
        }, args);
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    }

    @PostConstruct
    public void logSomething() {
        logger.debug("after pay-server start");
    }
}
