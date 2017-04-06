package com.jusfoun.hookah.console.server.api.auth;

import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author huang lei
 * @date 2017/4/6 上午10:06
 * @desc 组织机构API
 */
@RestController
@RequestMapping(value = "/api/org")
public class OrgApi {

    @Resource
    private OrganizationService organizationService;

    @RequestMapping(value = "/owner",method = RequestMethod.GET)
    public ReturnData getOwner(){
        return ReturnData.success(organizationService.selectById(new Long(0)));
    }
}
