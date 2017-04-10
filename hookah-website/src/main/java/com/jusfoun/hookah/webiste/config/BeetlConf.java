package com.jusfoun.hookah.webiste.config;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huanglei on 2016/11/8.
 */
@Configuration
public class BeetlConf {

    @Autowired
    MyProps myProps;

    @Bean(initMethod = "init", name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        try {
            ClasspathResourceLoader cploder = new ClasspathResourceLoader(BeetlConf.class.getClassLoader(), myProps.getBeetl().get("templatesPath"));
//            ClasspathResourceLoader cploder = new ClasspathResourceLoader("/views/default/");
            beetlGroupUtilConfiguration.setResourceLoader(cploder);
            Map<String, Object> shared = new HashMap<String, Object>();
            shared.put("host", myProps.getHost());
            shared.put("oauth2", myProps.getOauth2());
            beetlGroupUtilConfiguration.setSharedVars(shared);
            return beetlGroupUtilConfiguration;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
//        String root = System.getProperty("user.dir")+ File.separator+"hookah-website/target/classes/views/default";
//        FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
//        beetlGroupUtilConfiguration.getGroupTemplate().setResourceLoader(resourceLoader);
        beetlGroupUtilConfiguration.getGroupTemplate().registerFunctionPackage("shiro", new ShiroExt());
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
