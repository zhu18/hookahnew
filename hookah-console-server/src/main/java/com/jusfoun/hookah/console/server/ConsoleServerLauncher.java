package com.jusfoun.hookah.console.server;

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

/**
 * @author huang lei
 * @date 2017/2/28 下午1:55
 * @desc
 */
@Configuration
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class, DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableScheduling
@ComponentScan
@EnableTransactionManagement
public class ConsoleServerLauncher {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleServerLauncher.class);

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(new Object[]{
//      "classpath*:spring/rs-context.xml",
//            "classpath*:spring/spring-config-shiro.xml",
            "classpath*:hookah_rpc_server.xml",
            "classpath*:hookah_rpc_server_goods.xml",
            "classpath*:hookah_rpc_server_order.xml",
            "classpath*:hookah_rpc_server_system.xml",
            "classpath*:hookah_rpc_server_other.xml",
            ConsoleServerLauncher.class
        }, args);
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    }
}
