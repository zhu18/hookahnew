package com.jusfoun.hookah.console.server.config;

import com.jusfoun.hookah.console.server.ws.CategoryDataWsService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {
          @Bean
         public ServletRegistrationBean dispatcherServlet() {
             return new ServletRegistrationBean(new CXFServlet(), "/webservice/*");
         }
         @Bean(name = Bus.DEFAULT_BUS_ID)
         public SpringBus springBus() {
             return new SpringBus();
         }

         @Autowired
         CategoryDataWsService categoryDataWsService;
         @Bean
         public Endpoint endpoint() {
             EndpointImpl endpoint = new EndpointImpl(springBus(), categoryDataWsService);
             endpoint.publish("/category");
//             endpoint.getInInterceptors().add(new AuthInterceptor());
             return endpoint;
         }

}