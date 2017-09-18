package com.jusfoun.hookah.crowd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.PostConstruct;

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
public class CrowdWebsitLauncher {
    private static final Logger logger = LoggerFactory.getLogger(CrowdWebsitLauncher.class);

    @Bean
    public FilterRegistrationBean securityFilterChainRegistration() {
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        delegatingFilterProxy.setTargetBeanName("shiroFilter");
        delegatingFilterProxy.setTargetFilterLifecycle(true);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(delegatingFilterProxy);
        registrationBean.setName("shiroFilter");
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(new Object[]{
                "classpath*:spring-config-shiro.xml",
                CrowdWebsitLauncher.class},args);
//        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        System.out.println("-------------->Crowd WebSite Start <---------------");
    }

    @PostConstruct
    public void logSomething() {
        logger.debug("after server start");
    }
}
