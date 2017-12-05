package com.jusfoun.hookah.webiste.config;

import com.jusfoun.hookah.webiste.interceptor.*;
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
        registry.addInterceptor(new TongJiInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new CategoryInterceptor()).addPathPatterns("/exchange").addPathPatterns("/exchange/**");
        registry.addInterceptor(new CartInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new UserAuthInterceptor()).addPathPatterns("/order/orderInfo*").addPathPatterns("/order/directInfo*");
        registry.addInterceptor(new WeiXInMPInterceptor()).addPathPatterns("/exchange/details*");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/*").allowedOrigins("http://localhost:8080");
    }
}
