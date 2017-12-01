package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.vo.InvoiceDTOVo;
import com.jusfoun.hookah.core.domain.vo.InvoiceDetailVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoInvoiceVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 发票
 *
 * @author zhanghanqingo
 * @created 2016年7月7日
 */
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    @Resource
    private InvoiceService invoiceService;

    /**
     *  发票提交,根据invoiceId 是否有值判断是新增还是修改
     * @param invoiceDTOVo 发票传输对象
     *                   包括 invoiceId 发票ID
     *                   包括   titleId 抬头ID
     *                   包括   id  收票人ID
     *                   包括   orderIds 订单IDs
     * @return
     */
    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public ReturnData insert(InvoiceDTOVo invoiceDTOVo) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            invoiceDTOVo.setUserId(this.getCurrentUser().getUserId());
            invoiceService.addInvoice(invoiceDTOVo);
            return ReturnData.success();
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 分页查该用户下的订单发票信息
     *
     * @param pageNum
     * @param pageSize
     * @param invoiceStatus 开票状态
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ReturnData findByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "15") Integer pageSize, Byte invoiceStatus, Model model) {
        try {
            Pagination<OrderInfoInvoiceVo> pOrders = invoiceService.getDetailListInPage(pageNum, pageSize, String.valueOf(1), invoiceStatus);
            model.addAttribute("orderInvoiceList", pOrders);
//            return "/mybuyer/invoice";
            return ReturnData.success(pOrders);
        } catch (Exception e) {
            logger.error("分页查询错误", e);
            ReturnData.error("系统异常");
        }

        return ReturnData.success();
    }

    /**
     * 查看发票详情
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ReturnData findInvoiceInfo(String invoiceId){
        ReturnData returnData = new ReturnData<>();
        try {
            InvoiceDetailVo invoiceDetailVo = invoiceService.findInvoiceInfo(invoiceId);
            return ReturnData.success(invoiceDetailVo);
        } catch (Exception e) {
            logger.error("修改错误", e);
            ReturnData.error("系统异常:" + e.getMessage());
        }
        return returnData;
    }
}
