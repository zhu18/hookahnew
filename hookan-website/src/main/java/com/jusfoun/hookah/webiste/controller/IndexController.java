package com.jusfoun.hookah.webiste.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by huanglei on 2016/11/8.
 */
@Controller
public class IndexController {


  @Autowired
  ApplicationContext context;
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String home(Model model) {
    model.addAttribute("happy", "Hello,world");
    model.addAttribute("x", "index");
    return "index";
  }
}
