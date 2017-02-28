package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * All rights Reserved, Designed By
 * Copyright:  Copyright(C) 2016-2020
 * Company:    ronwo.com LTD.
 *
 * @version V1.0
 *          Createdate: 2016/11/12 下午11:53
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          2016/11/12     huanglei         1.0             1.0
 *          Why & What is modified: <修改原因描述>
 * @author: huanglei
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(Model model) {
    model.addAttribute("happy","Hello,world");
    model.addAttribute("x","auth");
    return "login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public Model pLogin(Model model){
    model.addAttribute("ha","dd");
    return model;
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String register(Model model) {
    model.addAttribute("happy","Hello,world");
    model.addAttribute("x","auth");
    return "register";
  }
}
