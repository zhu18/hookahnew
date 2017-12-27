package com.jusfoun.hookah.console.server.common.timer;

import com.jusfoun.hookah.console.server.config.MyProps;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.MessageCode;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected Logger logger = LoggerFactory.getLogger(TimerTask.class);
    static final String COMMA = ",";

    @Resource
    UserService userService;

    @Resource
    MyProps myProps;

    @Resource
    MqSenderService mqSenderService;

    @Resource
    RedisOperate redisOperate;

    @Resource
    OrderInfoService orderInfoService;

    @Resource
    CouponService couponService;

    @Resource
    TongJiInfoService tongJiInfoService;

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

    /**
     * 1、每天零点清除当天的订单编号
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanOrderNum(){
        redisOperate.del("orderNumPerDay");
    }

    /**
     * 1、每天零点修改已过期的优惠券的状态
     * 2、每天零点修改用户超过有效使用时间的优惠券的状态
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateCouponStatus(){
        // TODO …… 修改过期优惠券状态
        try {
            couponService.updateStatusEveryDay();
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * 激活到使用日期的优惠券
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void activeCoupons(){
        try {
            couponService.activeCoupons();
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * 每隔十分钟查询一次订单状态，将超过24小时未付款的订单取消掉
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    @Transactional
    public void deleteOrderByTime(){
        Date date = new Date();
        Date start = DateUtils.getTodayStart();
        long now = date.getTime();
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.ne("payStatus",OrderInfo.PAYSTATUS_PAYED));
        filter.add(Condition.eq("isDeleted",(byte)0));
        filter.add(Condition.eq("forceDeleted",(byte)0));
        filter.add(Condition.le("addTime",start));
        try {
            List<OrderInfo> orderInfos = orderInfoService.selectList(filter);
            if (orderInfos.size()>0){
                for (OrderInfo orderInfo:orderInfos){
                    long addTime = orderInfo.getAddTime().getTime();
                    long time = now - addTime;
                    if (orderInfo.getPayStatus() == 0 && time > 24 * 60 * 60 * 1000) {
                        orderInfoService.deleteByLogic(orderInfo.getOrderId());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("定时取消订单失败："+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 交易运营统计
     */
//    @Scheduled(cron="30 59 23 * * ?")
//    public void countOrderData(){
//        try {
//            Date date = new Date();
//            tongJiInfoService.countOrderData(date);
//        } catch (Exception e) {
//            logger.error("交易运营统计失败："+e.getMessage());
//        }
//    }

    @Scheduled(cron="0 59 23 * * ?")
    public void saveTongJiInfoService(){
        try {
            Date date = new Date();
            tongJiInfoService.saveTongJiInfoService(date);
        } catch (Exception e) {
            logger.error("统计失败："+e.getMessage());
        }
    }

}
