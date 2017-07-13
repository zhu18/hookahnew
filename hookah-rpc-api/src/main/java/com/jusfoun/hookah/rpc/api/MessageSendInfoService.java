package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.MessageSendInfo;
import com.jusfoun.hookah.core.domain.vo.MessageCritVo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by ctp on 2017/7/11.
 */
public interface MessageSendInfoService extends GenericService<MessageSendInfo,String> {
    /**
     * 获取模板信息列表
     * @param messageType
     * @param messageCritVo
     * @return
     */
    public ReturnData findList(String messageType,MessageCritVo messageCritVo);

    public ReturnData findByReceiveUser(MessageCritVo messageCritVo);

    public ReturnData updateMessageStatus(String operator,String[] messages);
}
