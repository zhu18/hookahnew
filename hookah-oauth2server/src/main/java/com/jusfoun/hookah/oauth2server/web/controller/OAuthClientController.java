package com.jusfoun.hookah.oauth2server.web.controller;


import com.jusfoun.hookah.core.domain.OauthClient;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
@Controller
@RequestMapping(value = "/client/")
public class OAuthClientController {

    @Resource
    private OAuthClientService clientService;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String clientId, Model model) {
        List<OauthClient> listDto = clientService.selectList();
        model.addAttribute("listDto", listDto);
        return "oauth/client_list";
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String get(Model model) {
        OauthClient formDto = clientService.loadClientDetailsFormDto();
        model.addAttribute("formDto", formDto);
        return "oauth/client";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData submitRegisterClient(@ModelAttribute("formDto") OauthClient formDto, BindingResult result) {
//    validator.validate(formDto, result);
//    if (result.hasErrors()) {
//      return "oauth/client_details_plus";
//    }
//        clientService.saveClientDetails(formDto);
        ReturnData returnData;
        try{
            OauthClient oauthClient = clientService.insert(formDto);
            returnData = ReturnData.success(oauthClient);
        }catch (Exception e){
            returnData = ReturnData.error("保存失败");
            System.out.println(e);
        }

        return returnData;
    }

}
