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
public class ExpositionController {

    @RequestMapping(value = "/1/exposition", method = RequestMethod.GET)
    public String index(){
        return "1/exposition/index";
    }

    @RequestMapping(value = "/1/exposition/data_trading", method = RequestMethod.GET)
    public String datatrading(){
        return "1/exposition/data_trading";
    }

    @RequestMapping(value = "/1/exposition/data_crowdsourcing", method = RequestMethod.GET)
    public String datacrowdsourcing(){
        return "1/exposition/data_crowdsourcing";
    }

    @RequestMapping(value = "/1/exposition/financial_innovation", method = RequestMethod.GET)
    public String financialinnovation(){
        return "1/exposition/financial_innovation";
    }

    @RequestMapping(value = "/1/exposition/solve_plan", method = RequestMethod.GET)
    public String solveplan(){
        return "1/exposition/solve_plan";
    }


    @RequestMapping(value = "/exposition", method = RequestMethod.GET)
    public String index2(){
        return "/exposition/index";
    }

    @RequestMapping(value = "/exposition/data_trading", method = RequestMethod.GET)
    public String datatrading2(){
        return "/exposition/data_trading";
    }

    @RequestMapping(value = "/exposition/data_crowdsourcing", method = RequestMethod.GET)
    public String datacrowdsourcing2(){
        return "/exposition/data_crowdsourcing";
    }

    @RequestMapping(value = "/exposition/financial_innovation", method = RequestMethod.GET)
    public String financialinnovation2(){
        return "/exposition/financial_innovation";
    }

    @RequestMapping(value = "/exposition/solve_plan", method = RequestMethod.GET)
    public String solveplan2(){
        return "/exposition/solve_plan";
    }
}
