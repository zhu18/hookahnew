package com.jusfoun.hookah.webiste.config;
/**
 * @author:jsshao
 * @date: 2017-4-12
 */

import com.jusfoun.hookah.webiste.common.handler.SystemWebSocketHandler;
import com.jusfoun.hookah.webiste.interceptor.HandshakeInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements
        WebSocketConfigurer {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(ApplicationContext context) {
        return new ServerEndpointExporter();
    }


    public WebSocketConfig() {
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(systemWebSocketHandler(), "/receiver").addInterceptors(new HandshakeInterceptor());
        System.out.println("registed!");
        registry.addHandler(systemWebSocketHandler(), "/sockjs/websck/info").addInterceptors(new HandshakeInterceptor())
                .withSockJS();
    }

    @Bean
    public WebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }
}