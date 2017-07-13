package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.MessageSendInfo;
import com.jusfoun.hookah.core.domain.MessageTemplate;
import com.jusfoun.hookah.core.domain.vo.TemplateCritVo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by ctp on 2017/7/13.
 */
public interface MessageTemplateService extends GenericService<MessageTemplate,String> {
    public ReturnData findMessageListPage(TemplateCritVo templateCritVo);
}
