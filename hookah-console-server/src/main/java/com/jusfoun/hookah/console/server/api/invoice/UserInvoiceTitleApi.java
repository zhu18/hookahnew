package com.jusfoun.hookah.console.server.api.invoice;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.ExpressInfo;
import com.jusfoun.hookah.core.domain.Invoice;
import com.jusfoun.hookah.core.domain.UserInvoiceTitle;
import com.jusfoun.hookah.core.domain.vo.InvoiceDetailVo;
import com.jusfoun.hookah.core.domain.vo.InvoiceVo;
import com.jusfoun.hookah.core.domain.vo.UserInvoiceTitleVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ExpressInfoService;
import com.jusfoun.hookah.rpc.api.InvoiceService;
import com.jusfoun.hookah.rpc.api.UserInvoiceTitleService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoruibing on 2017/12/01.
 */

@RestController
@RequestMapping(value = "/api/userInvoiceTitle/back")
public class UserInvoiceTitleApi extends BaseController{

    @Resource
    UserInvoiceTitleService userInvoiceTitleService;

@Resource
    InvoiceService invoiceService;

    @Resource
    UserService userService;


    /**
     * 分页查发票信息
     *
     * @param currentPage
     * @param pageSize
     * @param userName 买家账号
     * @param userType 用户类型
     * @param invoiceStatus 发票状态
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getInvoiceInfoList(String currentPage, String pageSize,
                                         String userName, Byte userType,
                                         Byte invoiceStatus)throws HookahException {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<UserInvoiceTitleVo> pagination = null;
        PageInfo<UserInvoiceTitleVo> page = new PageInfo<>();
        try {
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (org.apache.commons.lang3.StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (org.apache.commons.lang3.StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            pagination = new Pagination<>(pageNumberNew, pageSizeNew);
            PageHelper.startPage(pageNumberNew, pageSizeNew);//pageNum为第几页，pageSize为每页数量

            List<UserInvoiceTitleVo> pOrders = userInvoiceTitleService.getIncreInvoiceList(userName, userType, invoiceStatus);

            page = new PageInfo<UserInvoiceTitleVo>(pOrders);
            pagination.setTotalItems(page.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(page.getList());

        } catch (Exception e) {
            logger.error("分页查询错误", e);
            ReturnData.error("系统异常");
        }

        return ReturnData.success(pagination);
    }

    /**
     * 增票资质审核
     * @param userInvoiceTitle json字符串对象 增票资质
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ReturnData invoiceCheck(String userInvoiceTitle) {
        UserInvoiceTitle obj = JSON.parseObject(userInvoiceTitle, UserInvoiceTitle.class);
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            userInvoiceTitleService.updateByIdSelective(obj);
            Invoice invoice = new Invoice();
            invoice.setTitleId(obj.getTitleId());
            invoice.setQualificationStatus(obj.getInvoiceStatus());
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("titleId", obj.getTitleId()));
            invoiceService.updateByConditionSelective(invoice, filters);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 查看增票资质详情
     * @param titleId
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ReturnData findInvoiceInfo(String titleId, Byte userType){
        ReturnData returnData = new ReturnData<>();
        try {
            UserInvoiceTitleVo userInvoiceTitleVo= userInvoiceTitleService.findUserInvoiceTitleInfo(titleId, userType);
            return ReturnData.success(userInvoiceTitleVo);
        } catch (Exception e) {
            logger.error("查看详情错误", e);
            ReturnData.error("系统异常:" + e.getMessage());
        }
        return returnData;
    }
}
