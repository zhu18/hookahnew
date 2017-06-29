package com.jusfoun.hookah.console.server.api.user;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserCheck;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.UserCheckService;
import com.jusfoun.hookah.rpc.api.UserDetailService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by ctp on 2017/5/25.
 */
@RestController
@RequestMapping(value = "/api/userCheck")
public class UserCheckApi extends BaseController{

    @Resource
    private UserCheckService userCheckService;

    @Resource
    UserService userService;

    @Resource
    OrganizationService organizationService;

    @Resource
    UserDetailService userDetailService;


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

    @RequestMapping(value = "/all", method = {RequestMethod.POST,RequestMethod.GET})
    public ReturnData search(UserCheck userCheck,Integer currentPage,Integer pageSize){
        return userCheckService.search(userCheck,currentPage,pageSize);
    }

    /**
     * 用户审核结果
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReturnData getUserById(@PathVariable String id) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Map<String, Object> map = new HashedMap();

            UserCheck userCheck = userCheckService.selectById(id);
            if(userCheck.getUserType() != null && userCheck.getUserId() != null){

                User user = userService.selectById(userCheck.getUserId());

                if(user != null){

                    if(user.getMoneyBalance() != null && user.getMoneyBalance() != 0){
                        user.setMoneyBalance(user.getMoneyBalance() / 100);
                    }

                    if("0".equals(userCheck.getUserType())){// 个人
                        UserDetail userDetail = userDetailService.selectById(user.getUserId());
                        map.put("user", user);
                        map.put("userDetail", userDetail);
                        map.put("userCheck", userCheck);
                        returnData.setData(map);
                    }else if("1".equals(userCheck.getUserType()) && user.getOrgId() != null){// 企业
                        Organization organization = organizationService.selectById(user.getOrgId());
                        map.put("user", user);
                        map.put("userCheck", userCheck);
                        map.put("organization", organization);
                        returnData.setData(map);
                    }else{
                        returnData.setCode(ExceptionConst.Failed);
                        returnData.setMessage("系统出错，请联系管理员！");
                    }
                }else{
                    returnData.setCode(ExceptionConst.Failed);
                    returnData.setMessage("系统出错，请联系管理员！");
                }
            }else{
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("系统出错，请联系管理员！");
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }


}
