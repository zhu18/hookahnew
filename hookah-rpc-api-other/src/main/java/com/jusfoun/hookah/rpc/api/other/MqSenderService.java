package com.jusfoun.hookah.rpc.api.other;

import com.jusfoun.hookah.core.domain.Test;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * @author huang lei
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
