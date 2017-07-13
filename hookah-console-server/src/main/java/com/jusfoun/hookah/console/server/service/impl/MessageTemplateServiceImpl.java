package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.MessageTemplateMapper;
import com.jusfoun.hookah.core.domain.MessageTemplate;
import com.jusfoun.hookah.core.domain.vo.TemplateCritVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MessageTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ctp on 2017/7/13.
 */
@Service
public class MessageTemplateServiceImpl extends GenericServiceImpl<MessageTemplate,String> implements MessageTemplateService{

    @Resource
    MessageTemplateMapper messageTemplateMapper;

    @Resource
    public void setDao(MessageTemplateMapper messageTemplateMapper) {
        super.setDao(messageTemplateMapper);
    }

    @Override
    public ReturnData findMessageListPage(TemplateCritVo templateCritVo) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        return returnData;
    }
}
