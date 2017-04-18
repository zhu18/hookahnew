package com.jusfoun.hookah.console.server;

import com.jusfoun.hookah.console.server.pay.unionpay.sdk.SDKConfig;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.PostConstruct;

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
       //读取银联支付配置文件
        SDKConfig.getConfig().loadPropertiesFromSrc();

        ApplicationContext ctx = SpringApplication.run(new Object[]{
//      "classpath*:spring/rs-context.xml",
            "classpath*:spring/spring-config-shiro.xml",
            "classpath*:hookah_rpc_server.xml",
            "classpath*:hookah_rpc_server_goods.xml",
            "classpath*:hookah_rpc_server_order.xml",
            "classpath*:hookah_rpc_server_region.xml",
            "classpath*:hookah_rpc_server_system.xml",
            "classpath*:hookah_rpc_server_goodsShelves.xml",
            "classpath*:hookah_rpc_server_other.xml",
            "classpath*:hookah_rpc_client.xml",
            ConsoleServerLauncher.class
        }, args);
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    }

    @PostConstruct
    public void logSomething() {
        logger.debug("after server start");
    }
}
