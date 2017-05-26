package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.UserCheckMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserCheck;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
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
                }else if(userCheck.getCheckStatus().equals((byte)2)){
                    user.setUserType(HookahConstants.UserType.ORGANIZATION_CHECK_FAIL.getCode());
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
}
