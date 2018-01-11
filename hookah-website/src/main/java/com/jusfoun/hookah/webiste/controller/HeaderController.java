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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    public String getHeader(Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            StringBuffer html = new StringBuffer("");
            if(subject.isAuthenticated()) {
                //已登录
                html.append("<ul class=\"text-align-right topbar-user grid-right margin-left-30\" style=\"margin-top:28px;position: relative\">");
                html.append("<li class=\"display-inline-block margin-left-10\">");
                html.append("<a href=\"http://auth.bdgstore.cn/logout\">退出</a></li></ul>");
                return html.toString();
            }else {
                html.append("<ul class=\"text-align-right topbar-user grid-right margin-left-30\" style=\"margin-top:28px;position: relative\">");
                html.append("<li class=\"display-inline-block\">");
                html.append("<a href=\"http://auth.bdgstore.cn/oauth/authorize?client_id=website&amp;response_type=code&amp;redirect_uri=\" style=\"padding:7px 14px;\">登录</a>");
                html.append("</li>");
                html.append("<li class=\"display-inline-block margin-left-10\">");
                html.append("<a class=\"reg\" href=\"http://auth.bdgstore.cn/reg\">免费注册</a>");
                html.append("</li>");
                html.append("<p class=\"show-ad show\">注册送200元大礼<i class=\"fa fa-close\"></i></p></ul>");
                return html.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return "redirect:/error/500";
        }
    }
    @RequestMapping(value = "/getCartNum", method = RequestMethod.GET)
    public String getCart(Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            StringBuffer html = new StringBuffer("");
            if(subject != null && !subject.isAuthenticated()) {
                //未登录
                html.append("<a href=\"/cart\">");
                html.append("<i class=\"fa fa-shopping-cart\" style=\"color:#d20003;margin-right:5px;\"></i>购物车");
                html.append("<span style=\"color:#000;\">0</span>件<a>");
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
                html.append("<a href=\"/cart\">");
                html.append("<i class=\"fa fa-shopping-cart\" style=\"color:#d20003;margin-right:5px;\"></i>购物车");
                html.append("<span style=\"color:#000;\">");
                html.append(size);
                html.append("</span>件<a>");
            }
            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return "redirect:/error/500";
        }
    }
}
