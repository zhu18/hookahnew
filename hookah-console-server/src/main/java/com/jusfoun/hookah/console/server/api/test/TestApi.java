package com.jusfoun.hookah.console.server.api.test;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/19 下午12:55
 * @desc
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestApi {

    @Resource
    RedisOperate redisOperate;

    @RequestMapping(value = "/save")
    public Object saveRedis(String key,String value){
        return redisOperate.set(key,value,1000);
    }
    @RequestMapping(value = "/get/{key}")
    public Object saveRedis(@PathVariable String key){
        return redisOperate.get(key);
    }
}
