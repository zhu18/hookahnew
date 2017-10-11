package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.domain.zb.vo.MgZbProviderVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCheckVo;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.MgZbProviderService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: dx
 */
@Controller
public class AuthController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private MgZbProviderService mgZbProviderService;

    @Resource
    RedisOperate redisOperate;

    @RequestMapping(value = "/testredis", method = RequestMethod.GET)
    public String test() {

        String r1 = redisOperate.hset("123456", "addtime", "2017-09-23 19:30:34");
        String r2 = redisOperate.hset("123456", "updatetime", "2017-09-25 19:36:34");
        String r3 = redisOperate.hset("123456", "applytime", "2017-09-26 19:50:34");
        String r4 = redisOperate.hset("123456", "addtime", "2017-09-29 19:30:34");

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);

        Map<String, String> hmap = redisOperate.getMap("123456");
        Iterator iter = hmap.entrySet().iterator();
        while (iter.hasNext()) {
           Map.Entry entry = (Map.Entry) iter.next();
           System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        return "";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(String redirect_uri, HttpServletRequest request) {
        if (!StringUtils.isEmpty(redirect_uri)) {
            return "redirect:" + redirect_uri;
        } else {
            return "redirect:/crowdsourcing-list";
        }
    }

    /**
     * 校验是否登录
     *
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
     * 校验是否认证
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/isAuth", method = RequestMethod.POST)
    @ResponseBody
    public boolean isAuth(Model model) {
        try {
            MgZbProvider mgZbProvider = mgZbProviderService.selectById(getCurrentUser().getUserId());
            if (mgZbProvider != null && mgZbProvider.getStatus().equals(2)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 查询认证信息
     *
     * @return
     */
    @RequestMapping(value = "/auth/getAuthInfo")
    @ResponseBody
    public ReturnData getAuthInfo() {

        try {
            String userId = getCurrentUser().getUserId();
//            String userId = "1234567890";
            return mgZbProviderService.getAuthInfo(userId);
        } catch (Exception e) {
            logger.error("认证信息查询失败", e);
            return ReturnData.error("系统繁忙，请稍后再试！[query]^_^");
        }
    }

    /**
     * 认证信息操作
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/api/auth/optAuthInfo")
    @ResponseBody
    public ReturnData optAuthInfo(@RequestBody MgZbProviderVo vo) {

        try {
            String userId = getCurrentUser().getUserId();
            vo.setUserId(userId);
            return mgZbProviderService.optAuthInfo(vo);
        } catch (Exception e) {
            logger.error("认证信息删除失败", e);
            return ReturnData.error("系统繁忙，请稍后再试！[del]^_^");
        }
    }

    /**
     * 服务商审核
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/auth/checkAuthInfo", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData dcheckAuthInfo(ZbCheckVo vo) {

        try {
            String userId = getCurrentUser().getUserId();
//            String userId = "1234567890";
            vo.setCheckerId(userId);
            return mgZbProviderService.checkAuthInfo(vo);
        } catch (Exception e) {
            logger.error("认证信息审核异常", e);
            return ReturnData.error("系统繁忙，请稍后再试！[check]^_^");
        }
    }

//    /**
//     * 服务商名片
//     * @return
//     */
//    @RequestMapping(value = "/auth/providerCard", method = RequestMethod.POST)
//    @ResponseBody
//    public ReturnData providerCard() {
//
//        try {
////            String userId = getCurrentUser().getUserId();
//            String userId = "1234567890";
////            vo.setCheckerId(userId);
//            return mgZbProviderService.providerCard(userId);
//        }catch (Exception e){
//            logger.error("认证信息审核异常", e);
//            return ReturnData.error("系统繁忙，请稍后再试！[check]^_^");
//        }
//    }


}
