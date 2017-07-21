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
    public final String WAIT_SETTLE_ORDERS = "CONTRACE_ORDERSN";
}