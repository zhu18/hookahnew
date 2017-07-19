package com.jusfoun.hookah.console.server.api.userFoun;

import com.jusfoun.hookah.core.domain.vo.UserFundCritVo;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.UserFundService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ctp on 2017/7/18.
 */
@RestController
@RequestMapping(value = "/api/userFund")
public class UserFundApi {

    @Resource
    UserFundService userFundService;

    /**
     *
     */
    @RequestMapping(value = "/all")
    public ReturnData searchUserFund(UserFundCritVo userFundCritVo){
        return userFundService.getUserFundListPage(userFundCritVo);
    }

}
