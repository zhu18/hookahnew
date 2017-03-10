package com.jusfoun.hookah.console.other.config;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huanglei on 2016/11/8.
 */
@Configuration
public class BeetlConf {

  @Autowired
  MyProps myProps;

//  @Value("${beetl.templatesPath}")
//  String templatesPath;//模板跟目录
//
//  @Value("${beetl.contentType}")
//  String contentType;
//
//  @Value("${beetl.prefix}")
//  String prefix;
//
//  @Value("${beetl.suffix}")
//  String suffix;
//
//  @Value("${path.static}")
//  String staticPath;
//
//  @Value("${path.auth}")
//  String authPath;
//
//  @Value("${server.host}")
//  String serverHost;
//
//  @Value("${host}")
//  List host;

  @Bean(initMethod = "init", name = "beetlConfig")
  public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
    BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
    try {
      ClasspathResourceLoader cploder = new ClasspathResourceLoader(BeetlConf.class.getClassLoader(), myProps.getBeetl().get("templatesPath"));
      beetlGroupUtilConfiguration.setResourceLoader(cploder);
      Map<String,Object> shared = new HashMap<String,Object>();
      shared.put("staticPath",myProps.getHost().get("static"));
      shared.put("authPath",myProps.getHost().get("auth"));
      shared.put("serverHost",myProps.getHost().get("server"));
      shared.put("host",myProps.getHost());
      beetlGroupUtilConfiguration.setSharedVars(shared);
      return beetlGroupUtilConfiguration;
    } catch (Exception e) {
      System.out.println(e);
      throw new RuntimeException(e);
    }

  }

  @Bean(name = "beetlViewResolver")
  public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
    BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
    beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
    beetlSpringViewResolver.setCache(false);
    beetlSpringViewResolver.setOrder(0);
    beetlSpringViewResolver.setPrefix(myProps.getBeetl().get("prefix"));
    beetlSpringViewResolver.setSuffix(myProps.getBeetl().get("suffix"));
    beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
    return beetlSpringViewResolver;
  }
}
