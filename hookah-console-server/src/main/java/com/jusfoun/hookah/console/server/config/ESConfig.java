package com.jusfoun.hookah.console.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by wangjl on 2017-3-28.
 */
@Component
@Order(value=2)
public class ESConfig implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ESConfig.class);
    @Autowired
    ESTransportClient esTransportClient;

    /***
     * 初始化索引及索引数据
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        //TODO 可从数据库查询需要新建的索引信息（暂时从配置文件里获取）

        //TODO 判断索引是否存在
        //TODO 如果索引不存在创建索引并导入数据
        //TODO 如果队列里有操作数据，进行依次处理
    }
}
