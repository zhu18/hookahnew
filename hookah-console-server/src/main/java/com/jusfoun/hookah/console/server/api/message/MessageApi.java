package com.jusfoun.hookah.console.server.api.message;

import com.jusfoun.hookah.core.dao.GeneralCodesMapper;
import com.jusfoun.hookah.core.domain.GeneralCodes;
import com.jusfoun.hookah.core.domain.vo.MessageCritVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MessageSendInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ctp on 2017/7/12.
 */
@RestController
@RequestMapping(value = "/api/message")
public class MessageApi {

    @Resource
    MessageSendInfoService messageSendInfoService;

    @Resource
    GeneralCodesMapper generalCodesMapper;

    public static final String property_eventType = "EVENT_TYPE";

    /**
     * 获取站内信消息列表
     * @param messageCritVo
     * @return
     */
    @RequestMapping(value = "/system/all")
    public ReturnData getSystemList(MessageCritVo messageCritVo){
        return messageSendInfoService.findList("system",messageCritVo);
    }

    /**
     * 获取邮件通知列表
     * @param messageCritVo
     * @return
     */
    @RequestMapping(value = "/email/all")
    public ReturnData getEmailList(MessageCritVo messageCritVo){
        return messageSendInfoService.findList("email",messageCritVo);
    }

    /**
     * 获取短信通知列表
     * @param messageCritVo
     * @return
     */
    @RequestMapping(value = "/sms/all")
    public ReturnData getSmsList(MessageCritVo messageCritVo){
        return messageSendInfoService.findList("sms",messageCritVo);
    }

    @RequestMapping(value = "/eventType/all")
    public ReturnData getEventTypeList(MessageCritVo messageCritVo){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {

            GeneralCodes generalCodes = new GeneralCodes();
            generalCodes.setProperty(property_eventType);
            List<GeneralCodes> generalCodesList = generalCodesMapper.select(generalCodes);
            GeneralCodes generalCodes1 = new GeneralCodes();
            generalCodes1.setCode("-1");
            generalCodes1.setDescrible("全部");
            generalCodesList.add(0,generalCodes1);
            returnData.setData(generalCodesList);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

}