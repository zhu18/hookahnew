package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.GeneralCodesMapper;
import com.jusfoun.hookah.core.dao.MessageSendInfoMapper;
import com.jusfoun.hookah.core.domain.GeneralCodes;
import com.jusfoun.hookah.core.domain.MessageSendInfo;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.MessageCritVo;
import com.jusfoun.hookah.core.domain.vo.MessageSendInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MessageSendInfoService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ctp on 2017/7/11.
 */
@Service
public class MessageSendInfoServiceImpl extends GenericServiceImpl<MessageSendInfo,String> implements MessageSendInfoService {

    //站内信:2 邮件:1 短信:0
    public static final Byte SEND_TYPE_SYSTEM = 2;
    public static final Byte SEND_TYPE_EMAIL = 1;
    public static final Byte SEND_TYPE_SMS = 0;

    public static final String property_eventType = "EVENT_TYPE";

    @Resource
    MessageSendInfoMapper messageSendInfoMapper;

    @Resource
    GeneralCodesMapper generalCodesMapper;

    @Resource
    UserService userService;

    @Resource
    public void setDao(MessageSendInfoMapper messageSendInfoMapper) {
        super.setDao(messageSendInfoMapper);
    }

    @Override
    public ReturnData findList(String messageType, MessageCritVo messageCritVo) {
        switch (messageType) {
            case "system":
                messageCritVo.setSendType(SEND_TYPE_SYSTEM);
                break;
            case "email":
                messageCritVo.setSendType(SEND_TYPE_EMAIL);
                break;
            case "sms":
                messageCritVo.setSendType(SEND_TYPE_SMS);
                break;
        }
        return getMessageList(messageCritVo);
    }

    @Override
    public ReturnData findByReceiveUser(MessageCritVo messageCritVo) {
        return getMessageList(messageCritVo);
    }

    @Override
    public ReturnData updateMessageStatus(String operator,String[] messages) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        List<Condition> fifters = new ArrayList<Condition>();
        if(null == messages || messages.length == 0){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        fifters.add(Condition.in("id",messages));
        MessageSendInfo messageSendInfo = new MessageSendInfo();
        try {
            switch (operator){
                case "read":
                    messageSendInfo.setIsRead(Byte.valueOf("1"));
                    break;
                case "del":
                    messageSendInfo.setIsDelete(Byte.valueOf("0"));
                    break;
            }

            int count = this.updateByConditionSelective(messageSendInfo,fifters);
            returnData.setData(count);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    //通用修改待完善
    private ReturnData updateMessage(){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            //this.updateByConditionSelective();
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    //通用带分页查询
    private ReturnData getMessageList(MessageCritVo messageCritVo){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            if(Objects.isNull(messageCritVo.getPageNumber())) {
                messageCritVo.setPageNumber(HookahConstants.PAGE_NUM);
            }
            if(Objects.isNull(messageCritVo.getPageSize())) {
                messageCritVo.setPageSize(HookahConstants.PAGE_SIZE);
            }
            List<Condition> filters = new ArrayList<Condition>();
            if(Objects.nonNull(messageCritVo.getSendType())){
                filters.add(Condition.eq("sendType",messageCritVo.getSendType()));
            }
            if (Objects.nonNull(messageCritVo.getStartTime())) {
                filters.add(Condition.ge("sendTime", messageCritVo.getStartTime()));
            }
//            if (Objects.nonNull(messageCritVo.getSendType())) {
//                filters.add(Condition.le("sendTime", messageCritVo.getEndTime()));
//            }
            if (Objects.nonNull(messageCritVo.getEndTime())) {
                filters.add(Condition.le("sendTime", messageCritVo.getEndTime()));
            }
            if (Objects.nonNull(messageCritVo.getKeywords())) {
                filters.add(Condition.like("sendHeader", messageCritVo.getKeywords()));
            }
            if(Objects.nonNull(messageCritVo.getEventType()) && !"-1".equals(messageCritVo.getEventType())){
                filters.add(Condition.eq("eventType", messageCritVo.getEventType()));
            }
            if(Objects.nonNull(messageCritVo.getIsRead()) && !(Byte.valueOf("-1")).equals(messageCritVo.getIsRead())){
                filters.add(Condition.eq("isRead",messageCritVo.getIsRead()));
            }
            if(Objects.nonNull(messageCritVo.getReceiveUser())){
                filters.add(Condition.eq("receiveUser",messageCritVo.getReceiveUser()));
            }
            if(Objects.nonNull(messageCritVo.getIsDelete())){
                filters.add(Condition.eq("isDelete",messageCritVo.getIsDelete()));
            }

            List<OrderBy> orderBys = new ArrayList<OrderBy>();
            orderBys.add(OrderBy.desc("sendTime"));

            Pagination pagination = super.getListInPage(messageCritVo.getPageNumber(),messageCritVo.getPageSize(),filters,orderBys);
            pagination.setList(this.copyMessageSendInfoData(pagination.getList()));

            returnData.setData(pagination);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }
    //完善返回的数据
    private List<MessageSendInfoVo> copyMessageSendInfoData(List<MessageSendInfo> messageSendInfos){
        List<MessageSendInfoVo> list1 = new ArrayList<>();
        for(MessageSendInfo messageSendInfo : messageSendInfos) {
            MessageSendInfoVo messageSendInfoVo = new MessageSendInfoVo();
            BeanUtils.copyProperties(messageSendInfo, messageSendInfoVo);
            User user = userService.selectById(messageSendInfo.getReceiveUser());
            messageSendInfoVo.setReceiveUserName(user == null ?"":user.getUserName());
            GeneralCodes generalCodes = new GeneralCodes();
            generalCodes.setProperty(property_eventType);
            generalCodes.setCode(messageSendInfo.getEventType());
            List<GeneralCodes> generalCodesList = generalCodesMapper.select(generalCodes);
            if(null != generalCodesList && generalCodesList.size() > 0){
                messageSendInfoVo.setEventTypeName(generalCodesList.get(0).getDescrible());
            }
            list1.add(messageSendInfoVo);
        }
        return list1;
    }
}
