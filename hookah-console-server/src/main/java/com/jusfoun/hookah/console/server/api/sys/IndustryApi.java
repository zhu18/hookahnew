package com.jusfoun.hookah.console.server.api.sys;

import com.jusfoun.hookah.core.domain.IndustryCode;
import com.jusfoun.hookah.rpc.api.IndustryCodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dengxu on 2017/4/17/0017.
 */
@RestController
@RequestMapping(value = "/api/sys/industry")
public class IndustryApi {

    @Resource
    IndustryCodeService industryCodeService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Object getListInPage() {
        List<IndustryCode> dictTreeList = industryCodeService.selectTree();
        return dictTreeList;
    }
}
