package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.MgZbProviderService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: dx
 */
@Controller
public class AuthController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private MgZbProviderService mgZbProviderService;

    /**
     * 校验是否登录
     * @param model
     * @return
     */
    @RequestMapping(value = "/islogin", method = RequestMethod.POST)
    @ResponseBody
    public boolean isLogin(Model model) {
        Map userMap = null;
        try {
            userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
            if (userMap == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 服务商认证
     * @param provider
     * @author  dx
     * @return
     */
    @RequestMapping(value = "/auth/personAuth", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData personAuth(MgZbProvider provider) {
        try {
            provider.setUserId(getCurrentUser().getUserId());
            return mgZbProviderService.userAuth(provider);
        }catch (Exception e){
            logger.error("认证异常", e);
            return ReturnData.error("认证异常");
        }
    }
}
