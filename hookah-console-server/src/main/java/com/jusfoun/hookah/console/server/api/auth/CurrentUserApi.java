package com.jusfoun.hookah.console.server.api.auth;

import com.jusfoun.hookah.core.utils.ReturnData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: huanglei
 * @date:2016/12/26 15:00
 */
@RestController
@RequestMapping(value = "/api/auth")
public class CurrentUserApi {

    @RequestMapping(value = "/current_user",method = RequestMethod.GET)
    public ReturnData getCurrentUser(HttpServletRequest request, HttpServletResponse response){
        Session session = SecurityUtils.getSubject().getSession();
        if(session != null){
            return ReturnData.success(session.getAttribute("user"));
        }else{
            return ReturnData.error("没有权限");
        }
    }
}
