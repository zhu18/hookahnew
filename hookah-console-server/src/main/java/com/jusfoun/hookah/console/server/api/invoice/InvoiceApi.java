package com.jusfoun.hookah.console.server.api.invoice;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.ExpressInfo;
import com.jusfoun.hookah.core.domain.Invoice;
import com.jusfoun.hookah.core.domain.vo.InvoiceDetailVo;
import com.jusfoun.hookah.core.domain.vo.InvoiceVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ExpressInfoService;
import com.jusfoun.hookah.rpc.api.InvoiceService;
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
@RequestMapping(value = "/api/invoice/back")
public class InvoiceApi extends BaseController{

    @Resource
    InvoiceService invoiceService;

    @Resource
    ExpressInfoService expressInfoService;

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
     * @param invoiceType 发票类型
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getInvoiceInfoList(String currentPage, String pageSize,
                                         String userName, Byte userType,
                                         Byte invoiceStatus, Byte invoiceType)throws HookahException {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<InvoiceVo> pagination = null;
        PageInfo<InvoiceVo> page = new PageInfo<>();
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

            List<InvoiceVo> pOrders = invoiceService.getInvoiceListInPage(userName, userType, invoiceStatus, invoiceType);

            page = new PageInfo<InvoiceVo>(pOrders);
//                if(page.getList() != null && page.getList().size() > 0){
//                    page.getList().stream().forEach(goodsCheckedVo ->
//                            {
//                                goodsCheckedVo.setGoodsArea((goodsCheckedVo.getGoodsArea() == null || "".equals(goodsCheckedVo.getGoodsArea())) ? "全部" : DictionaryUtil.getRegionById(goodsCheckedVo.getGoodsArea()).getMergerName());
//                                goodsCheckedVo.setCatId((goodsCheckedVo.getCatId() == null || "".equals(goodsCheckedVo.getCatId())) ? "全部" : DictionaryUtil.getCategoryById(goodsCheckedVo.getCatId().substring(0, 3)).getCatName());
//                            }
//                    );
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
     * 发票审核
     * @param invoice json字符串对象 发票
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ReturnData invoiceCheck(String invoice) {
        Invoice obj = JSON.parseObject(invoice, Invoice.class);
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            invoiceService.updateByIdSelective(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 邮寄
     * @param expressInfo json字符串对象 邮寄
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ReturnData invoiceSend(String expressInfo) {
        ExpressInfo obj = JSON.parseObject(expressInfo, ExpressInfo.class);
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("invoiceId", obj.getInvoiceId()));
            expressInfoService.deleteByCondtion(filters);
            expressInfoService.insert(obj);
            // 提交邮寄同时更新已开发票
            Invoice invoice = new Invoice();
            invoice.setInvoiceId(obj.getInvoiceId());
            invoice.setInvoiceStatus(HookahConstants.INVOICE_STATUS_4);
            invoiceService.updateByIdSelective(invoice);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 查看发票详情
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ReturnData findInvoiceInfo(String invoiceId, Byte userType){
        ReturnData returnData = new ReturnData<>();
        try {
            InvoiceDetailVo invoiceDetailVo = invoiceService.findOrderInvoiceInfo(invoiceId,userType);
            return ReturnData.success(invoiceDetailVo);
        } catch (Exception e) {
            logger.error("信息不存在", e);
            ReturnData.error("系统异常:" + e.getMessage());
        }
        return returnData;
    }
}
