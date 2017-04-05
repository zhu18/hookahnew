package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/6 下午3:21
 * @desc
 */
@Controller
public class ExpositionController {

    @RequestMapping(value = "/1/exposition", method = RequestMethod.GET)
    public String index(){return "1/exposition/index"; }

    @RequestMapping(value = "/1/exposition/data_trading", method = RequestMethod.GET)
    public String datatrading(){return "1/exposition/data_trading"; }

    @RequestMapping(value = "/1/exposition/data_crowdsourcing", method = RequestMethod.GET)
    public String datacrowdsourcing(){return "1/exposition/data_crowdsourcing"; }

    @RequestMapping(value = "/1/exposition/financial_innovation", method = RequestMethod.GET)
    public String financialinnovation(){return "1/exposition/financial_innovation"; }

    @RequestMapping(value = "/1/exposition/solve_plan", method = RequestMethod.GET)
    public String solveplan(){return "1/exposition/solve_plan"; }
}
