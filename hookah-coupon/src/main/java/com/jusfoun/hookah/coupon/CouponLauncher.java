package com.jusfoun.hookah.coupon;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author huang lei
 * @date 2017/2/28 上午11:15
 * @desc
 */
@Configuration
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class})
@EnableScheduling
@EnableAsync
@ComponentScan
public class CouponLauncher {
    private static final Logger logger = LoggerFactory.getLogger(CouponLauncher.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(new Object[]{
                "classpath*:spring/spring-config.xml",
                "classpath*:hookah_rpc_server.xml",
                "classpath*:hookah_rpc_client.xml",
                "classpath*:hookah_rpc_client_coupon.xml",
                "classpath*:hookah_rpc_server_coupon.xml",
                CouponLauncher.class},args);
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        System.out.println("-------------->CouponLauncher Start <---------------");
    }
}
