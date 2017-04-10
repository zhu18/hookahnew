package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/6 下午3:22
 * @desc
 */
@Controller
@RequestMapping("/exposition")
public class ExpositionController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "/exposition/index";
    }

    @RequestMapping(value = "/data_trading", method = RequestMethod.GET)
    public String datatrading(){
        return "/exposition/data_trading";
    }

    @RequestMapping(value = "/data_crowdsourcing", method = RequestMethod.GET)
    public String datacrowdsourcing(){
        return "/exposition/data_crowdsourcing";
    }

    @RequestMapping(value = "/financial_innovation", method = RequestMethod.GET)
    public String financialinnovation(){
        return "/exposition/financial_innovation";
    }

    @RequestMapping(value = "/solve_plan", method = RequestMethod.GET)
    public String solveplan(){
        return "/exposition/solve_plan";
    }
}
