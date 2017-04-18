package com.jusfoun.hookah.core.constants;

/**
 * @author:jsshao
 * @date: 2017-4-12
 */
/**
 * exchange交换机配置
 */
public interface RabbitmqExchange {
    /**
     * 合同exchange
     */
    public final String CONTRACT_FANOUT = "CONTRACT_FANOUT";
    public final String CONTRACT_TOPIC = "CONTRACT_TOPIC";
    public final String CONTRACT_DIRECT = "CONTRACT_DIRECT";
    public final String CONTRACT_GOODSCHECK = "CONTRACT_GOODSCHECK";
}