package com.jusfoun.hookah.coupon.common.listener;

import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.UserCoupon;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.CouponService;
import com.jusfoun.hookah.rpc.api.UserCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RabbitMQRegCouponListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQRegCouponListener.class);

    @Resource
    UserCouponService userCouponService;

    @Resource
    CouponService couponService;

    @RabbitListener(queues = RabbitmqQueue.CONTRACT_REG_COUPON)
    public void handleRegCoupon(String userId) {
        logger.info("CONTRACT_REG_COUPON待处理注册送优惠券-->用户ID{}", userId);

        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("couponType",(byte)0));
        filter.add(Condition.eq("isDeleted",(byte)0));
        filter.add(Condition.eq("couponStatus",(byte)1));
        List<Coupon> coupons = couponService.selectList(filter);
        try {
            if (coupons!=null&&coupons.size()>0){
                Long[] couponList = new Long[]{};
                for (Coupon coupon : coupons){
                    couponList[couponList.length] = coupon.getId();
                }
                couponService.sendCoupon2User(userId,couponList);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("注册送优惠券失败"+e.getMessage());
        }
    }
}
