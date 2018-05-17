package com.jusfoun.hookah.crowd.config;

import com.jusfoun.hookah.crowd.interceptor.CommonInterceptor;
import com.jusfoun.hookah.crowd.interceptor.SecurityInterceptor;
import com.jusfoun.hookah.crowd.interceptor.UserAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @description:
 * @author: huanglei
 * @date:2016/12/19 12:06
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new UserAuthInterceptor()).addPathPatterns("/usercenter/authentication");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/*").allowedOrigins("http://localhost:8080");
    }
}
