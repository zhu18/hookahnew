package com.jusfoun.hookah.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * @author huang lei
 * @date 2017/2/28 上午11:15
 * @desc
 */
@Configuration
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class})
@EnableScheduling
@ComponentScan
public class StaticServerLauncher {
    private static final Logger logger = LoggerFactory.getLogger(StaticServerLauncher.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(StaticServerLauncher.class,args);
    }

    @PostConstruct
    public void logSomething() {
        logger.debug("after server start");
    }
}
