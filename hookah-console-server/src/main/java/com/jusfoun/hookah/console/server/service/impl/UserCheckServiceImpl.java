package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.UserCheckMapper;
import com.jusfoun.hookah.core.domain.MessageCode;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserCheck;
import com.jusfoun.hookah.core.domain.vo.UserCheckVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import com.jusfoun.hookah.rpc.api.UserCheckService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ctp on 2017/5/25.
 */
@Service
public class UserCheckServiceImpl extends GenericServiceImpl<UserCheck, String> implements UserCheckService {
    @Resource
    UserCheckMapper userCheckMapper;

    @Resource
    UserService userService;

    @Resource
    MqSenderService mqSenderService;

    //用户类型(0.个人 1.企业)
    public static final String USER_TYPE_PERSON = "0";
    public static final String USER_TYPE_ORG= "1";


    @Resource
    public void setDao(UserCheckMapper userCheckMapper) {
        super.setDao(userCheckMapper);
    }

    @Override
    public ReturnData addUserCheck(UserCheck userCheck) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            if (userCheck == null)
                throw new HookahException("空数据！");

            userCheck = super.insert(userCheck);

            User user = new User();
            user.setUserId(userCheck.getUserId());

            if(USER_TYPE_PERSON.equals(userCheck.getUserType())){
                if(userCheck.getCheckStatus().equals((byte)1)){
                    user.setUserType(HookahConstants.UserType.PERSON_CHECK_OK.getCode());
                }else if(userCheck.getCheckStatus().equals((byte)2)){
                    user.setUserType(HookahConstants.UserType.PERSON_CHECK_FAIL.getCode());
                }
            }else if(USER_TYPE_ORG.equals(userCheck.getUserType())){
                if(userCheck.getCheckStatus().equals((byte)1)){
                    user.setUserType(HookahConstants.UserType.ORGANIZATION_CHECK_OK.getCode());
                    //发送消息，下发短信/站内信/邮件
                    MessageCode messageCode = new MessageCode();
                    messageCode.setCode(HookahConstants.MESSAGE_201);//此处填写相关事件编号
                    messageCode.setBusinessId(userCheck.getId());//此处填写业务id号（即主键id）
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);//将数据添加到队列
                }else if(userCheck.getCheckStatus().equals((byte)2)){
                    user.setUserType(HookahConstants.UserType.ORGANIZATION_CHECK_FAIL.getCode());
                    //发送消息，下发短信/站内信/邮件
                    MessageCode messageCode = new MessageCode();
                    messageCode.setCode(HookahConstants.MESSAGE_202);//此处填写相关事件编号
                    messageCode.setBusinessId(userCheck.getId());//此处填写业务id号（即主键id）
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);//将数据添加到队列
                }
            }
            userService.updateByIdSelective(user);
            returnData.setData(userCheck);

        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @Override
    public ReturnData search(UserCheck userCheck,Integer pageNumber,Integer pageSize) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination page = new Pagination<>();
        try {
            //参数校验
            if (Objects.isNull(pageNumber)) {
                pageNumber = HookahConstants.PAGE_NUM;
            }
            if (Objects.isNull(pageSize)) {
                pageSize = HookahConstants.PAGE_SIZE;
            }

            List<Condition> fifters = new ArrayList<Condition>();
            if(Objects.nonNull(userCheck)){
                if(Objects.nonNull(userCheck.getUserName())){
                    fifters.add(Condition.like("userName",userCheck.getUserName()));
                }
                if(Objects.nonNull(userCheck.getUserType()) && !"-1".equals(userCheck.getUserType())){
                    fifters.add(Condition.eq("userType",userCheck.getUserType()));
                }
            }

            List<OrderBy> orderBys = new ArrayList<OrderBy>();
            orderBys.add(OrderBy.desc("checkTime"));

            page = getListInPage(pageNumber,pageSize,fifters,orderBys);
            returnData.setData(page);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @Override
    public ReturnData checkDetail(String userId) {
        ReturnData<UserCheck> returnData = new ReturnData<UserCheck>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> fifters = new ArrayList<Condition>();
            fifters.add(Condition.eq("userId",userId));

            List<OrderBy> orderBys = new ArrayList<OrderBy>();
            orderBys.add(OrderBy.desc("checkTime"));

            List<UserCheck> userChecks = selectList(fifters,orderBys);
            if (userChecks == null || userChecks.isEmpty()) {
                returnData.setData(new UserCheck());
                return returnData;
            }
            returnData.setData(userChecks.get(0));
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }

        return returnData;
    }

    @Override
    public ReturnData authDetail(User user) {
        ReturnData returnData = new ReturnData<>();
        try {
            Integer userType = user.getUserType();
            UserCheckVo.UserCheckResult userCheckResult = new UserCheckVo.UserCheckResult();
            switch (userType) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    userCheckResult.setCode(String.valueOf(userType));
                    break;
                case 6:
                case 7:
                    userCheckResult.setCode(String.valueOf(userType));
                    userCheckResult.setMessage(((UserCheck)checkDetail(user.getUserId()).getData()).getCheckContent());
                    break;
            }
            returnData.setData(userCheckResult);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    public UserCheck selectUserCheckInfo(String userId){
        return userCheckMapper.selectUserCheckInfo(userId);
    }
}
