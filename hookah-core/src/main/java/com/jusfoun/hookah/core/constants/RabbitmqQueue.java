package com.jusfoun.hookah.core.constants;

/**
 * @author:jsshao
 * @date: 2017-4-12
 */
/**
 * queue队列配置
 */
public interface RabbitmqQueue {

    /** 订单 */
    public final String CONTRACE_ORDER ="CONTRACT_ORDER";

    /** 站内消息 */
    public final String CONTRACE_MESSAGE ="CONTRACT_MESSAGE";

    /**  */
    public final String CONTRACT_GOODSCHECK ="CONTRACT_GOODSCHECK";

    /**  */
    public final String CONTRACE_GOODS_ID ="CONTRACT_GOODS_ID";

    /** 新站内信-wangjl */
    public final String CONTRACE_NEW_MESSAGE ="CONTRACE_NEW_MESSAGE";

    /**
     * 订单支付成功 获取订单编号
     */
    public final String WAIT_SETTLE_ORDERS = "CONTRACE_WAIT_SETTLE_ORDERS";

    /** 渠道推送 */
    public final String CONTRACE_CENTER_CHANNEL ="CONTRACE_CENTER_CHANNEL";

    /** 状态推送 */
    public final String CONTRACE_CENTER_STATUS ="CONTRACE_CENTER_STATUS";

    /**
     * 首页分类修改 货架修改 货架商品修改 触发生成新的首页
     */
    public final String CONTRACE_GENERATE_INDEX ="CONTRACE_GENERATE_INDEX";

    /**
     * 积分队列
     */
    public final String CONTRACE_JF_MSG ="CONTRACE_JF_MSG";
}