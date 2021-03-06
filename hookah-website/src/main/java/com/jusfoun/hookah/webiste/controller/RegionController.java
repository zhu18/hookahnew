package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.RegionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/5/0005.
 */

@Controller
@RequestMapping("/region")
public class RegionController {

    @Resource
    RegionService regionService;

    /**
     * 根据pid获取省市县代码
     * @param parentId
     * @return
     */
    @RequestMapping("/getRegionCodeByPid")
    @ResponseBody
    public ReturnData getRegionCodeByPid(Long parentId) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("pid", parentId));
            returnData.setData(regionService.selectList(filters));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;


    }

}
