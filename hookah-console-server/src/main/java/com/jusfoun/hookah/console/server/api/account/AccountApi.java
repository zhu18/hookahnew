package com.jusfoun.hookah.console.server.api.account;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/6 下午12:03
 * @desc
 */
@RestController
@RequestMapping(value = "/api/account")
public class AccountApi {

    @Resource
    UserService userService;

    @RequestMapping(value = "/sys_all", method = RequestMethod.GET)
    public ReturnData getSysAccount(HttpServletRequest request, HttpServletResponse response) {
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("orgId", 0));
        List<User> sysUser = userService.selectList(filters);
        return ReturnData.success(sysUser);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ReturnData saveSysAccount(User user,HttpServletRequest request, HttpServletResponse response) {
        HashMap<String,String> o = (HashMap<String,String>)SecurityUtils.getSubject().getPrincipal();
        String creatorId = o.get("userId");
        user.setOrgId(new Long(0));
        user.setCreatorId(creatorId);
        User savedUser=userService.insert(user);
        return ReturnData.success(savedUser);
    }
}
