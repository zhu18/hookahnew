package com.jusfoun.hookah.integral.controller;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.bo.JfBo;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

@RestController
public class TestController {

    @Resource
    MqSenderService mqSenderService;

    @Resource
    RedisOperate redisOperate;

    @RequestMapping("/")
    public String Test1() {

        String set = redisOperate.set("zs", "123456", 0);
        String get = redisOperate.get("zs");

        System.out.println(set);
        System.out.println(get);
        return "jf";
    }

    @RequestMapping("/msg")
    public String Test2(String userId, Integer v) {
        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSG, new JfBo(userId, v));
        return "jf";
    }


    @RequestMapping("/msg3")
    public String Test3(String userId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < 10; x++) {
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSG,
                            new JfBo(userId, new Random().nextInt(7) + 1));
                }
            }
        }) {
        }.start();

        return "jf";
    }

    @RequestMapping("/msg4")
    public String Test4(String userId) {

        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSG,
                new JfBo(userId, new Random().nextInt(7) + 1));

        return "jf";
    }

}
