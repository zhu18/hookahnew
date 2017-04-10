package com.jusfoun.hookah.console.server.api.help;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.HelpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/10 下午4:47
 * @desc
 */
@RestController
@RequestMapping("/help")
public class HelpApi {

    @Resource
    HelpService helpService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize, HttpServletRequest request) {
        Pagination<Help> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = helpService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

}
