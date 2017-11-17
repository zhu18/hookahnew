package com.jusfoun.hookah.console.server.common.timer;

import com.jusfoun.hookah.console.server.config.MyProps;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.MessageCode;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Gring on 2017/8/14.
 */
@Component
public class TimerTask {
    static final String COMMA = ",";

    @Resource
    UserService userService;

    @Resource
    MyProps myProps;

    @Resource
    MqSenderService mqSenderService;

    /**
     * 待审核用户（企业）每一小时通知审批人
     */
    @Scheduled(cron = "0 0 0 * * ?") //每一小时执行一次
//    @Scheduled(cron = "0 0/2 * * * ?")
    public void sendMessage() {

        List<Condition> filters = new ArrayList();

        //只查询企业待审核
        filters.add(Condition.eq("userType", HookahConstants.UserType.ORGANIZATION_CHECK_NO.getCode()));
        List<User> userList = userService.selectList(filters);

        // 定时下架后，中央通知地方下架推送的商品
        if(!Objects.isNull(userList) && userList.size() > 0){
            //发送消息，下发短信/站内信/邮件
            MessageCode messageCode = new MessageCode();
            messageCode.setCode(HookahConstants.MESSAGE_203);

            for(String mobileNo : StringUtils.split(myProps.getOperateMobileNo(), COMMA)){
                messageCode.setMobileNo(mobileNo);
                mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE,messageCode);
            }
        }
    }
}
