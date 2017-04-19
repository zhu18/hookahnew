package com.jusfoun.hookah.core.constants;

/**
 * @author:jsshao
 * @date: 2017-4-12
 */
/**
 * queue队列配置
 */
public interface RabbitmqRoutekey {
    /** 站内消息 */
    public final String CONTRACE_RK_MESSAGE_COMMON ="CONTRACT_MESSAGE.#";    // 站内信接收规则1

    public final String CONTRACE_RK_MESSAGE_SYSTEM ="CONTRACT_MESSAGE.SYSTEM"; //系统信息

    /** 订单 */
    public final String CONTRACE_RK_ORDER_COMMON ="CONTRACT_ORDER.#";          //订单接收规则

}