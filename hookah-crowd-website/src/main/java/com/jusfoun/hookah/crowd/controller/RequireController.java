package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/require")
public class RequireController extends BaseController{

    @Resource
    ZbRequireService zbRequireService;

    @RequestMapping("/insertRequire")
    public void insertRequire(){

        ZbRequirement zb = new ZbRequirement();
        zb.setTitle("123456");
        zbRequireService.insertRecord(zb);

        try {
            String Uname = this.getCurrentUser().getUserName();
            System.out.print(Uname);
        } catch (HookahException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/allRequirement")
    public ReturnData AllRequirement(String currentPage, String pageSize, ZbRequirement zbRequirement) {
        Pagination<ZbRequirement> page = new Pagination<>();
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));

            if (StringUtils.isNotBlank(zbRequirement.getRequireSn())) {
                filters.add(Condition.like("requireSn", zbRequirement.getRequireSn()));
            }
            if (StringUtils.isNotBlank(zbRequirement.getTitle())) {
                filters.add(Condition.like("title", zbRequirement.getTitle()));
            }
            if (zbRequirement.getStatus() != null && zbRequirement.getStatus()!= -1) {
                filters.add(Condition.eq("status", zbRequirement.getStatus()));
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = zbRequireService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            returnData.setData(page);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


}
