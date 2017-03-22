package com.jusfoun.hookah.oauth2server.web.controller;


import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
@Controller
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private UserService userService;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String overview(String username, Model model) {
        List<User> userList = userService.selectList();
        model.addAttribute("userList", userList);
        return "user/user_list";
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String plusUser(Model model) {
        return "user/user";
    }


}
