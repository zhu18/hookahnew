package com.jusfoun.hookah.console.server.api.account;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.console.server.util.NumberValidationUtils;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/6 下午12:03
 * @desc
 */
@RestController
@RequestMapping(value = "/api/account")
public class AccountApi extends BaseController{

    @Resource
    UserService userService;

    @RequestMapping(value = "/sys_all", method = RequestMethod.GET)
    public ReturnData getSysAccount(HttpServletRequest request, HttpServletResponse response,String userName) {
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("orgId", 0));
        if(StringUtils.isNotBlank(userName)){
            filters.add(Condition.like("userName", userName.trim()));
        }
        List<User> sysUser = userService.selectList(filters);
        return ReturnData.success(sysUser);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional
    public ReturnData saveSysAccount(User user) throws Exception {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            boolean isExists = true;
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("orgId", 0));
            filters.add(Condition.eq("userName", user.getUserName()));
//            if (StringUtils.isNoneBlank(user.getPassword())){
//                filters.add(Condition.eq("password", user.getPassword()));
//            }else {
//                throw new HookahException("密码不能为空");
//            }
            isExists = userService.exists(filters);
            if (isExists) {
                throw new Exception("该账户已注册");
            }else{
                String creatorId = getCurrentUser().getCreatorId();
                user.setOrgId("0");
                user.setCreatorId(creatorId);
                user.setAddTime(new Date());
                User savedUser = userService.insert(user);
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public ReturnData recharge(User user,String recharge) {
        NumberValidationUtils nv = new NumberValidationUtils();

        if (StringUtils.isBlank(user.getUserId())) {
            return ReturnData.error("充值失败请返回重新充值");
        }
        if (nv.isNatureInteger(recharge)){
            Long charge = Long.parseLong(recharge)*100;
            user.setMoneyBalance(user.getMoneyBalance()+charge);
            userService.updateByIdSelective(user);
            return ReturnData.success("充值成功");
        }else {
            return ReturnData.error("只能充值整数金额");
        }
    }
    @RequestMapping("delete")
    public ReturnData deleteAccount(User user){
        try {
            userService.delete(user.getUserId());
        }catch (Exception e){
            return ReturnData.error("删除失败");
        }
        return ReturnData.success("删除成功");
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReturnData getUserById(@PathVariable String id) {
        User user = userService.selectById(id);
        return ReturnData.success(user);
    }

    @RequestMapping("upd")
    @Transactional
    public ReturnData updNoticeByConditionSelective(User user ) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq(("userId"), user.getUserId()));
        try {
           userService.updateByConditionSelective(user, filters);
        } catch (Exception e) {
            return ReturnData.error("修改失败");
        }
        return ReturnData.success("修改成功");
    }
}
