package com.jusfoun.hookah.rpc.api;

/**
 * @author jsshao
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface MqSenderService{

    /**
     * 发送消息，需要订阅
     * @param msg
     */
    public void sendTopic(String msg);


    /**
     * 发送消息，需要监听
     * @param msg
     */
    public void sendDirect(String msg);
}
