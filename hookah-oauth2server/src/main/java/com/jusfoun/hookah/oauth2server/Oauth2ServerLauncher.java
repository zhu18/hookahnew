package com.jusfoun.hookah.oauth2server;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:29
 * @desc
 */
@Configuration
@SpringBootApplication
@ComponentScan(basePackages = "com.jusfoun.hookah")
@EnableAutoConfiguration
@EntityScan("com.jusfoun.hookah.core.domain")
@EnableAsync
public class Oauth2ServerLauncher {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2ServerLauncher.class);
    public static void main(String[] args) throws InterruptedException {

        ApplicationContext ctx = SpringApplication.run(new Object[]{
            "classpath*:spring/authz-context.xml",
            "classpath*:spring/authz-security.xml",
            "classpath*:hookah_auth2server.xml",
            Oauth2ServerLauncher.class
        }, args);
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        System.out.println("server start...");
    }

    @PostConstruct
    public void doSomething() {
    }
}
