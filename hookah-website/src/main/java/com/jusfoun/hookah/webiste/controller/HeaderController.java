package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.CartService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lt on 2018/1/11.
 */
@RestController
@RequestMapping("/header")
public class HeaderController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(HeaderController.class);

    @Resource
    CartService cartService;

    @RequestMapping(value = "/getHeader", method = RequestMethod.GET)
    public void getHeader(HttpServletRequest request, HttpServletResponse response) {
        try {
            Subject subject = SecurityUtils.getSubject();
            StringBuffer html = new StringBuffer("");
            if(subject.isAuthenticated()) {
                //已登录
                Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");

                html.append("var str = '<li class=\"display-inline-block margin-left-10\"><a href=\"http://auth.gbdex.bdgstore.cn/logout\">退出</a></li>';");
                html.append("var userInfo = '<input type=\"hidden\" id=\"J_userType\" value=\""+userMap.get("userType")+"\">';");
                html.append("document.getElementById('userStatus').innerHTML = str + userInfo;");
            }else {
                html.append("var str = '<li class=\"display-inline-block\"> <a href=\"http://auth.gbdex.bdgstore.cn/oauth/authorize?client_id=website&amp;response_type=code&amp;redirect_uri=\" style=\"padding:7px 14px;\">登录</a> </li> <li class=\"display-inline-block margin-left-10\"> <a class=\"reg\" href=\"http://auth.gbdex.bdgstore.cn/reg\">注册</a> </li> <p class=\"show-ad show\">注册送200元大礼 <i class=\"fa fa-close\"></i> </p>';");
                html.append("document.getElementById('userStatus').innerHTML = str;");

            }
            response.setHeader("Content-Type", "application/x-javascript; charset=utf-8");
            response.getWriter().write(html.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
//            return "redirect:/error/500";
        }
    }
    @RequestMapping(value = "/getCartNum", method = RequestMethod.GET)
    public void getCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            Subject subject = SecurityUtils.getSubject();
            StringBuffer html = new StringBuffer("");
            if(subject != null && !subject.isAuthenticated()) {
                //未登录
                html.append("document.getElementById('cartNum').innerHTML = \"0\";");
            }else if (subject != null && subject.isAuthenticated()){
                Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
                User user = new User();
                BeanUtils.populate(user,userMap);

                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("userId", user.getUserId()));
                filters.add(Condition.eq("isDeleted", (byte)0));
                List<CartVo> cartVos = cartService.selectDetailList(filters);
                Long size = 0l;
                if(cartVos != null && cartVos.size() > 0){
                    size = cartVos.stream().parallel().mapToLong(cart->cart.getGoodsNumber()).sum();
                }
                html.append("document.getElementById('cartNum').innerHTML = "+size+";");
            }
            response.setHeader("Content-Type", "application/x-javascript; charset=utf-8");
            response.getWriter().write(html.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
