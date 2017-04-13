package com.jusfoun.hookah.rpc.api;

/**
 * @author jsshao
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface MqSenderService{

    /**
     * 发送消息
     * @param msg
     */
    public void send(String msg);
}
