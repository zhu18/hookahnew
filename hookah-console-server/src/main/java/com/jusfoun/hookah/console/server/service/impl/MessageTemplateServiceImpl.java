package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.GeneralCodesMapper;
import com.jusfoun.hookah.core.dao.MessageTemplateMapper;
import com.jusfoun.hookah.core.domain.GeneralCodes;
import com.jusfoun.hookah.core.domain.MessageTemplate;
import com.jusfoun.hookah.core.domain.vo.MessageTemplateVo;
import com.jusfoun.hookah.core.domain.vo.TemplateCritVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MessageTemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ctp on 2017/7/13.
 */
@Service
public class MessageTemplateServiceImpl extends GenericServiceImpl<MessageTemplate,String> implements MessageTemplateService{

    @Resource
    MessageTemplateMapper messageTemplateMapper;

    @Resource
    GeneralCodesMapper generalCodesMapper;

    @Resource
    public void setDao(MessageTemplateMapper messageTemplateMapper) {
        super.setDao(messageTemplateMapper);
    }

    @Override
    public ReturnData findMessageListPage(TemplateCritVo templateCritVo) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try{
            if(Objects.isNull(templateCritVo.getPageNumber())) {
                templateCritVo.setPageNumber(HookahConstants.PAGE_NUM);
            }
            if(Objects.isNull(templateCritVo.getPageSize())) {
                templateCritVo.setPageSize(HookahConstants.PAGE_SIZE);
            }
            List<Condition> filters = new ArrayList<Condition>();
            if(Objects.nonNull(templateCritVo.getIsVaild()) && !Byte.valueOf("-1").equals(templateCritVo.getIsVaild())){
                filters.add(Condition.eq("isVaild",templateCritVo.getIsVaild()));
            }
            if(Objects.nonNull(templateCritVo.getTemplateType()) && !Byte.valueOf("-1").equals(templateCritVo.getTemplateType())){
                filters.add(Condition.eq("templateType",templateCritVo.getTemplateType()));
            }
            if(Objects.nonNull(templateCritVo.getKeywords())){
                filters.add(Condition.like("templateHeader",templateCritVo.getKeywords()));
            }
            if(Objects.nonNull(templateCritVo.getEventType()) && !"-1".equals(templateCritVo.getEventType())){
                filters.add(Condition.like("eventType",templateCritVo.getEventType()));
            }
            if (Objects.nonNull(templateCritVo.getStartTime())) {
                filters.add(Condition.ge("updateTime", templateCritVo.getStartTime()));
            }
            if (Objects.nonNull(templateCritVo.getEndTime())) {
                filters.add(Condition.le("updateTime", templateCritVo.getEndTime()));
            }
            List<OrderBy> orderBys = new ArrayList<OrderBy>();
            orderBys.add(OrderBy.desc("updateTime"));
            Pagination pagination = this.getListInPage(templateCritVo.getPageNumber(),templateCritVo.getPageSize(),filters,orderBys);
            pagination.setList(copyMessageTemplateData(pagination.getList()));
            returnData.setData(pagination);
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @Override
    public ReturnData add(MessageTemplate messageTemplate) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try{
            returnData.setData(this.insert(messageTemplate));
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }


    @Override
    public ReturnData getTempConstantsList() {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try{
            GeneralCodes generalCodes = new GeneralCodes();
            generalCodes.setProperty(HookahConstants.PROPERTY_MESSAGE_CONSTANTS);
            returnData.setData(generalCodesMapper.select(generalCodes));
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    //完善返回的数据
    private List<MessageTemplateVo> copyMessageTemplateData(List<MessageTemplate> messageTemplates){
        List<MessageTemplateVo> list1 = new ArrayList<>();
        for(MessageTemplate messageTemplate : messageTemplates) {
            MessageTemplateVo messageTemplateVo = new MessageTemplateVo();
            BeanUtils.copyProperties(messageTemplate, messageTemplateVo);
            GeneralCodes generalCodes = new GeneralCodes();
            generalCodes.setProperty(HookahConstants.property_eventType);
            generalCodes.setCode(messageTemplate.getEventType());
            List<GeneralCodes> generalCodesList = generalCodesMapper.select(generalCodes);
            if(null != generalCodesList && generalCodesList.size() > 0){
                messageTemplateVo.setEventTypeName(generalCodesList.get(0).getDescrible());
            }
            list1.add(messageTemplateVo);
        }
        return list1;
    }

}
