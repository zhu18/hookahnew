package com.jusfoun.hookah.console.server.api.settle;


import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.SettleRecord;
import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.domain.vo.WaitSettleVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.SettleRecordService;
import com.jusfoun.hookah.rpc.api.WaitSettleRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 结算处理
 * dx
 */
@RestController
@RequestMapping("/api/waitSettle")
public class SettleController extends BaseController{

    @Resource
    WaitSettleRecordService waitSettleRecordService;

    @Resource
    SettleRecordService settleRecordService;

    @Resource
    OrganizationService organizationService;

    /**
     * 获取所有待结算记录
     * @param waitSettleVo
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ReturnData getList(WaitSettleVo waitSettleVo) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Failed);
        Pagination<WaitSettleRecord> page = new Pagination<>();
        try {

            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));

            List<Condition> filters = new ArrayList();

            if(StringUtils.isNotBlank(waitSettleVo.getOrderSn())){
                filters.add(Condition.eq("orderSn", waitSettleVo.getOrderSn()));
            }

            if(StringUtils.isNotBlank(waitSettleVo.getShopName())){
                filters.add(Condition.eq("shopName", waitSettleVo.getShopName()));
            }

            if(waitSettleVo.getSettleStatus() != null){
                filters.add(Condition.eq("settleStatus", waitSettleVo.getSettleStatus()));
            }

            if(StringUtils.isNotBlank(waitSettleVo.getStartDate())){
                filters.add(Condition.ge("orderTime", waitSettleVo.getStartDate()));
            }

            if(StringUtils.isNotBlank(waitSettleVo.getEndDate())){
                filters.add(Condition.le("orderTime", waitSettleVo.getEndDate()));
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(waitSettleVo.getCurrentPage())) {
                pageNumberNew = Integer.parseInt(waitSettleVo.getCurrentPage());
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(waitSettleVo.getPageSize())) {
                pageSizeNew = Integer.parseInt(waitSettleVo.getPageSize());
            }

            page = waitSettleRecordService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 根据待结算记录 进行结算 并查询已结算记录
     */
    @RequestMapping(value = "/getListBySettleId/{settleId}", method = RequestMethod.GET)
    public ReturnData getListBySettle(@PathVariable Long settleId) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Failed);
        try {

            WaitSettleRecord record = waitSettleRecordService.selectById(settleId);
            if(record == null){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("未查询到该订单，如有疑问请联系管理员！");
                return returnData;
            }

            if(StringUtils.isNotBlank(record.getShopName())){

                record.setShopName(organizationService.findOrgByUserId(record.getShopName()).getOrgName());
            }

            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));

            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("waitSettleRecordId", settleId));
            List<SettleRecord> list = settleRecordService.selectList(filters, orderBys);

            HashMap<String, Object> map = new HashMap<String, Object>() {
                {
                    put("record", record);
                    put("records", list);
                }
            };

            returnData.setData(map);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 进行结算
     * @param sid
     * @param supplierAmount
     * @param tradeCenterAmount
     * @return
     */
    @RequestMapping(value = "/settleOrderBy", method = RequestMethod.GET)
    public ReturnData orderSettleHandle(
            @RequestParam(value="sid", required = true ) Long sid,
            @RequestParam(value="supplierAmount", required = true ) Long supplierAmount,
            @RequestParam(value="tradeCenterAmount", required = true ) Long tradeCenterAmount) {

        ReturnData returnData = new ReturnData<>();
        try {

            // 金额进入后台处理需要 x100
            returnData = settleRecordService.handleSettle(sid, supplierAmount * 100, tradeCenterAmount * 100, getCurrentUser().getUserId());
        } catch (HookahException e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }


}
