package com.jusfoun.hookah.crowd.controller;


import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.crowd.service.MgZbRequireStatusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

@Controller
public class TestController {

    @Resource
    MgZbRequireStatusService mgZbRequireStatusService;

    @Resource
    RedisOperate redisOperate;

    @RequestMapping(value = "/testredis", method = RequestMethod.GET)
    public String test() {

        String r1 = redisOperate.hset("123456", "addtime", "2017-09-23 19:30:34");
        String r2 = redisOperate.hset("123456", "updatetime", "2017-09-25 19:36:34");
        String r3 = redisOperate.hset("123456", "applytime", "2017-09-26 19:50:34");
        String r4 = redisOperate.hset("123456", "addtime", "2017-09-29 19:30:34");

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);

        Map<String, String> hmap = redisOperate.getMap("123456");
        Iterator iter = hmap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        return "";
    }

    /**
     * 关于需求状态流程中时间的设置获取
     */
    @RequestMapping("/testMg1")
    public void testMg1(){
        mgZbRequireStatusService.
                setRequireStatusInfo("zb_01_001", "addTime", "2018-08-08 12:00:00");

        mgZbRequireStatusService.
                setRequireStatusInfo("zb_01_001", "checkTime", "2018-08-09 14:00:00");


        MgZbRequireStatus mgZbRequireStatus = mgZbRequireStatusService.getByRequirementSn("zb_01_001");

        System.out.println(mgZbRequireStatus.toString());
    }
}
