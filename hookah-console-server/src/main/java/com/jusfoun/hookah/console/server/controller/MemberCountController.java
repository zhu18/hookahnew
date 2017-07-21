package com.jusfoun.hookah.console.server.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.ShowVO;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ShowService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoshuai
 * Created by computer on 2017/7/20.
 */

@RestController
@RequestMapping("/memberCount")
public class MemberCountController {

    @Resource
    ShowService showService;

    @Resource
    UserService userService;

    /**
     * 网络流量统计分析
     * @return
     */
    @RequestMapping("/count")
    public ReturnData newUserCount(){
        Map<String, Object> map = new HashMap<>(6);
        //新注册会员
        int newUserCount = showService.getNewUserCount();
        //注册会员总数
        int newUserSum = showService.getNewUserSum();
        //购买过商品会员数量
        int buyGoodsSum = showService.getBuyGoodsCount();
        /**
         * 已认证会员数量
         * 待审核会员数量
         * 未认证会员数量
         */
        List<User> userTypeCount = showService.getUserTypeCount();
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;
        int count8 = 0;
        int count9 = 0;
        int count10 = 0;
        for (User u : userTypeCount){
            switch (u.getUserType()){
                case 1:
                   count1 += Integer.parseInt(u.getUserId());
                   break;
                case 2:
                    count2 += Integer.parseInt(u.getUserId());
                    break;
                case 3:
                    count3 += Integer.parseInt(u.getUserId());
                    break;
                case 4:
                    count4 += Integer.parseInt(u.getUserId());
                    break;
                case 5:
                    count5 += Integer.parseInt(u.getUserId());
                    break;
                case 8:
                    count8 += Integer.parseInt(u.getUserId());
                    break;
                case 9:
                    count9 += Integer.parseInt(u.getUserId());
                    break;
                case 10:
                    count10 += Integer.parseInt(u.getUserId());
                    break;
            }
        }
        int noAttestCount = count1;
        int dCheckCount = count3 + count5 + count9;
        int attestCount = count2 + count4 + count8 + count10;

        //活跃用户数
        int activeUserCount = showService.getActiveUserCou();
        //访问次数
        int accessCount = showService.getAccessCount();
        //pu浏览次数  uv独立访客
        Map<String, Object> PUV = userService.getPUVCountByDate();

        map.put("PUV",PUV);
        map.put("activeUserCount",activeUserCount);
        map.put("accessCount",accessCount);

        map.put("dCheckCount",dCheckCount);
        map.put("noAttestCount",noAttestCount);
        map.put("attestCount",attestCount);

        map.put("newUserCount",newUserCount);
        map.put("newUserSum",newUserSum);
        map.put("buyGoodsCount",buyGoodsSum);
        return ReturnData.success(map);
    }

}
