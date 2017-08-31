package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.MessageSendInfo;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.MessageCritVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MessageSendInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 平台消息管理
 * Created by ctp on 2017/7/12.
 */
@Controller
@RequestMapping("/message")
public class MessageSendInfoController extends BaseController{

    @Resource
    MessageSendInfoService messageSendInfoService;

    private static final Byte MESSAGE_NO_DEL = 1;

    @RequestMapping(value="/findByUserId")
    @ResponseBody
    public ReturnData findByUserId(MessageCritVo messageCritVo){
        User user = new User();
        try {
            user = getCurrentUser();
        } catch (HookahException e) {
            e.printStackTrace();
            return ReturnData.error(e.getMessage());
        }
        messageCritVo.setSendType(HookahConstants.SEND_TYPE_SYSTEM);
        messageCritVo.setReceiveUser(user.getUserId());
        messageCritVo.setIsDelete(MESSAGE_NO_DEL);
        return  messageSendInfoService.findByReceiveUser(messageCritVo);
    }

    @RequestMapping(value="/countMessageNumber")
    @ResponseBody
    public ReturnData countMessageNumber(){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Map<String,Object> messageNumber = new HashMap<String,Object>();
            messageNumber.put("allMessage",0);
            messageNumber.put("noReadMessage",0);
            User user = getCurrentUser();
            List<Condition> filters = new ArrayList<Condition>();
            filters.add(Condition.eq("sendType",HookahConstants.SEND_TYPE_SYSTEM));
            filters.add(Condition.eq("receiveUser",user.getUserId()));
            filters.add(Condition.eq("isDelete",MESSAGE_NO_DEL));
            List<MessageSendInfo> messageSendInfoList = messageSendInfoService.selectList(filters);
            if(null != messageSendInfoList && messageSendInfoList.size() > 0){
                messageNumber.put("allMessage",messageSendInfoList.size());
                List<MessageSendInfo> noReadMessageList = messageSendInfoList.stream().filter(messageInfo -> messageInfo.getIsRead() == 0).collect(Collectors.toList());
                if(null != noReadMessageList && noReadMessageList.size() > 0){
                    messageNumber.put("noReadMessage",noReadMessageList.size());
                }
            }
            returnData.setData(messageNumber);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value="/markToReadMessage")
    @ResponseBody
    public ReturnData markMessage(@RequestBody String[] messageId){
        return messageSendInfoService.updateMessageStatus("read",messageId);
    }

    @RequestMapping(value="/delMessage")
    @ResponseBody
    public ReturnData delMessage(@RequestBody String[] messageId){
        return messageSendInfoService.updateMessageStatus("del",messageId);
    }

    @RequestMapping(value="/detail/{id}")
    @ResponseBody
    public ReturnData detail(@PathVariable String id){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            MessageSendInfo messageSendInfo = new MessageSendInfo();
            messageSendInfo.setId(id);
            messageSendInfo.setIsRead((byte)1);
            messageSendInfoService.updateByIdSelective(messageSendInfo);
            returnData.setData(messageSendInfoService.selectById(id)==null?new MessageSendInfo():messageSendInfoService.selectById(id));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

}
