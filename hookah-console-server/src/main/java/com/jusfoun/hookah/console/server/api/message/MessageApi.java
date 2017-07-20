package com.jusfoun.hookah.console.server.api.message;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.dao.GeneralCodesMapper;
import com.jusfoun.hookah.core.domain.GeneralCodes;
import com.jusfoun.hookah.core.domain.MessageTemplate;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.MessageCritVo;
import com.jusfoun.hookah.core.domain.vo.TemplateCritVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MessageSendInfoService;
import com.jusfoun.hookah.rpc.api.MessageTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ctp on 2017/7/12.
 */
@RestController
@RequestMapping(value = "/api/message")
public class MessageApi extends BaseController{

    @Resource
    MessageSendInfoService messageSendInfoService;

    @Resource
    GeneralCodesMapper generalCodesMapper;

    @Resource
    MessageTemplateService messageTemplateService;


    public static final String property_eventType = "EVENT_TYPE";

    //是否启用：0 停用；1 启用
    public static final Byte IS_VAILD_NO = 0;
    public static final Byte IS_VAILD_YES = 1;


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
//            GeneralCodes generalCodes1 = new GeneralCodes();
//            generalCodes1.setCode("-1");
//            generalCodes1.setDescrible("全部");
//            generalCodesList.add(0,generalCodes1);
            returnData.setData(generalCodesList);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 获取模板列表
     * @param templateCritVo
     * @return
     */
    @RequestMapping(value = "/template/all")
    public ReturnData getTemplateList(TemplateCritVo templateCritVo){
        return messageTemplateService.findMessageListPage(templateCritVo);
    }

    //获取模板支持的常量
    @RequestMapping(value = "/constants/all")
    public ReturnData getConstantsList(GeneralCodes generalCodes){
//        ReturnData returnData = new ReturnData<>();
//        returnData.setCode(ExceptionConst.Success);
//        try {
//            generalCodes.setProperty(HookahConstants.PROPERTY_MESSAGE_CONSTANTS);
//            returnData.setData();
//        } catch (Exception e) {
//            returnData.setCode(ExceptionConst.Failed);
//            returnData.setMessage(e.getMessage());
//            e.printStackTrace();
//        }
        return messageTemplateService.getTempConstantsList();
    }

    //添加模板
    @RequestMapping(value = "/template/add")
    public ReturnData addTemplate(MessageTemplate messageTemplate){
        User user = null;
        try {
            user = this.getCurrentUser();
            messageTemplate.setUpdateUser(user.getUserId());
        }catch (Exception e){
            e.printStackTrace();
            return ReturnData.error(e.getMessage());
        }
        return messageTemplateService.add(messageTemplate);
    }

    //编辑模板
    @RequestMapping(value = "/template/edit")
    public ReturnData editTemplate(MessageTemplate messageTemplate){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        if(null==messageTemplate || StringUtils.isBlank(messageTemplate.getId())){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }

        MessageTemplate messageTemplate1 = messageTemplateService.selectById(messageTemplate.getId());
        //是否启用：0 停用；1 启用
        if(null == messageTemplate1 || "IS_VAILD_YES".equals(messageTemplate1.getIsVaild())){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.Failed));
            return returnData;
        }

        try{
            User user = this.getCurrentUser();
            messageTemplate.setUpdateUser(user.getUserId());
            returnData.setData(messageTemplateService.updateByIdSelective(messageTemplate));
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }


    //启/停模板
    @RequestMapping(value = "/template/stopOrOpen")
    public ReturnData stopOrOpenTemplate(String tempId){
        return messageTemplateService.stopOrOpenTemplate(tempId);
    }

    //编辑模板
    @RequestMapping(value = "/template/delete")
    public ReturnData deleteTemplate(String tempId){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        if(null == tempId || StringUtils.isBlank(tempId)){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }

        MessageTemplate messageTemplate1 = messageTemplateService.selectById(tempId);
        //是否启用：0 停用；1 启用
        if(null == messageTemplate1 || "IS_VAILD_YES".equals(messageTemplate1.getIsVaild())){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.Failed));
            return returnData;
        }

        try{
            User user = this.getCurrentUser();
            returnData.setData(messageTemplateService.delete(tempId));
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }
}
