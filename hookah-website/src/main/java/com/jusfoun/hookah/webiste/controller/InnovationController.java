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

    @RequestMapping(value = "/1/innovation", method = RequestMethod.GET)
    public String index(){return "1/innovation/index";    }

    @RequestMapping(value = "/1/innovation/data_crowdsourcing", method = RequestMethod.GET)
    public String datacrowdsourcing(){return "1/innovation/data_crowdsourcing";    }

    @RequestMapping(value = "/1/innovation/incubator", method = RequestMethod.GET)
    public String incubator(){return "1/innovation/incubator";    }

    @RequestMapping(value = "/1/innovation/base_development_platform", method = RequestMethod.GET)
    public String baseplatform(){return "1/innovation/base_development_platform";    }

    @RequestMapping(value = "/1/innovation/equity", method = RequestMethod.GET)
    public String equity(){return "1/innovation/equity";    }

    @RequestMapping(value = "/1/innovation/good_team", method = RequestMethod.GET)
    public String goodteam(){return "1/innovation/good_team";    }

    @RequestMapping(value = "/1/innovation/bigdata_industry", method = RequestMethod.GET)
    public String bigdataindustry(){return "1/innovation/bigdata_industry";    }

    @RequestMapping(value = "/1/innovation/activity", method = RequestMethod.GET)
    public String activity(){return "1/innovation/activity";    }

    @RequestMapping(value = "/1/innovation/guest_competition", method = RequestMethod.GET)
    public String guestcompetition(){return "1/innovation/guest_competition";    }
}
