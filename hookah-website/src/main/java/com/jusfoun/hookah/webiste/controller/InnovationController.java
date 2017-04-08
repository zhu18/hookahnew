package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/6 下午3:18
 * @desc
 */
@Controller
public class InnovationController {

    @RequestMapping(value = "/innovation", method = RequestMethod.GET)
    public String index(){return "/innovation/index";    }

    @RequestMapping(value = "/innovation/data_crowdsourcing", method = RequestMethod.GET)
    public String datacrowdsourcing(){return "/innovation/data_crowdsourcing";    }

    @RequestMapping(value = "/innovation/incubator", method = RequestMethod.GET)
    public String incubator(){return "/innovation/incubator";    }

    @RequestMapping(value = "/innovation/base_development_platform", method = RequestMethod.GET)
    public String baseplatform(){return "/innovation/base_development_platform";    }

    @RequestMapping(value = "/innovation/equity", method = RequestMethod.GET)
    public String equity(){return "/innovation/equity";    }

    @RequestMapping(value = "/innovation/good_team", method = RequestMethod.GET)
    public String goodteam(){return "/innovation/good_team";    }

    @RequestMapping(value = "/innovation/bigdata_industry", method = RequestMethod.GET)
    public String bigdataindustry(){return "/innovation/bigdata_industry";    }

    @RequestMapping(value = "/innovation/activity", method = RequestMethod.GET)
    public String activity(){return "/innovation/activity";    }

    @RequestMapping(value = "/innovation/guest_competition", method = RequestMethod.GET)
    public String guestcompetition(){return "/innovation/guest_competition";    }


}
