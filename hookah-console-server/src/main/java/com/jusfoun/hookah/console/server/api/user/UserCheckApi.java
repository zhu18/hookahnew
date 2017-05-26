package com.jusfoun.hookah.console.server.api.user;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.domain.UserCheck;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.UserCheckService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ctp on 2017/5/25.
 */
@RestController
@RequestMapping(value = "/api/userCheck")
public class UserCheckApi extends BaseController{

    @Resource
    private UserCheckService userCheckService;

    /**
     * 添加用户审核记录
     * @param userCheck
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ReturnData goodsCheck(UserCheck userCheck) throws HookahException{
        userCheck.setCheckUser(getCurrentUser().getUserName());
        return userCheckService.addUserCheck(userCheck);
    }

}
