package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Digits;
import java.io.Serializable;

/**
 * 货架类型
 * Created by wangjl on 2017-3-24.
 */
public class MgShelves extends GenericModel {
    @Id
    private String shelvesId;// 货架id
    private Integer couponType;// 优惠分类： 0 促销；1 秒杀； 2 团购；3 积分
    private PromotionBean promotion;

    public static class PromotionBean implements Serializable {
        private Integer promotionType;// 优惠类型： 0 打折；1 满减；2 满送；3 返现；4 优惠券；5 买送（买二送10次）
        private DiscountBean discount;// 打折
        private FullCutBean fullCut;// 满减
        private FullSendBean fullSend;// 满送

        public static class FullCutBean implements Serializable {
            @Digits(integer=10,fraction = 2)
            private Float fullPrice;// 满多少钱
            @Digits(integer=10,fraction = 2)
            private Float cutPrice;// 减多少钱
            private Integer capTimes;// 最多可满减的次数
            private Integer isPromSame;// 是否同时优惠 0 否；1 是
        }

        public static class FullSendBean implements Serializable {
            private Integer fullSendType;// 满送类型：0 按购买总价计算（满多少钱送多少次）；1 按购买总数计算（购买多少个商品，送多少次）
            @Digits(integer=10,fraction = 2)
            private Float fullPrice;// 满多少钱
            @Digits(integer=10,fraction = 2)
            private Float cutPrice;// 减多少钱
            private Integer isPromSame;// 是否同时优惠 0 否；1 是
        }

    }

    public static class DiscountBean implements Serializable {
        @Digits(integer=10,fraction = 2)
        private Float discountRate;//折扣
        private Integer isPromSame;// 是否同时优惠 0 否；1 是
    }
}
