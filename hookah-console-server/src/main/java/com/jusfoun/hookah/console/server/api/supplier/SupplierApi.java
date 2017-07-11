package com.jusfoun.hookah.console.server.api.supplier;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Supplier;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.SupplierService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hhh on 2017/7/11.
 */
@RestController
@RequestMapping("/api/supplier")
public class SupplierApi extends BaseController {

    @Resource
    SupplierService supplierService;

    /**
     * 后台查询审核列表
     * @param checkStatus
     * @param contactPhone
     * @param orgName
     * @param startDate
     * @param endDate
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/getListByCondition")
    public ReturnData getListInPage(String checkStatus, String contactPhone, String orgName, String startDate,
                                    String endDate, String currentPage, String pageSize){
        Pagination<Supplier> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));

            if (StringUtils.isNotBlank(checkStatus)){
                filters.add(Condition.eq("checkStatus",Byte.valueOf(checkStatus)));
            }
            if (StringUtils.isNotBlank(contactPhone)){
                filters.add(Condition.like("contactPhone",contactPhone.trim()));
            }
            if (StringUtils.isNotBlank(orgName)){
                filters.add(Condition.like("orgName",orgName.trim()));
            }
            if (StringUtils.isNotBlank(startDate)){
                filters.add(Condition.ge("addTime",DateUtils.getDate(startDate)));
            }
            if (StringUtils.isNotBlank(endDate)){
                filters.add(Condition.le("endTime", DateUtils.getDate(endDate)));
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = supplierService.selectListInCondition(pageNumberNew, pageSizeNew, filters, orderBys);
        }catch (Exception e){
            e.printStackTrace();
            return ReturnData.error(e.getMessage());
        }
        return ReturnData.success(page);
    }

    @RequestMapping("updateInfo")
    public ReturnData updateInfo(){

        return ReturnData.success();
    }
}
