package com.jusfoun.hookah.console.server.api.settle;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.SettleRecord;
import com.jusfoun.hookah.core.domain.vo.WaitSettleRecordVo;
import com.jusfoun.hookah.core.domain.vo.WaitSettleVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.SettleRecordService;
import com.jusfoun.hookah.rpc.api.WaitSettleRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 结算处理
 * dx
 */
@RestController
@RequestMapping("/api/settleOrder")
public class SettleController extends BaseController{

    @Resource
    WaitSettleRecordService waitSettleRecordService;

    @Resource
    SettleRecordService settleRecordService;

    @Resource
    OrganizationService organizationService;

    /**
     * 获取所有待结算记录
     * @param vo
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ReturnData getList(WaitSettleVo vo) {


        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<WaitSettleRecordVo> pagination = new Pagination<>();
        PageInfo<WaitSettleRecordVo> page = new PageInfo<>();
        try {

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(vo.getCurrentPage())) {
                pageNumberNew = Integer.parseInt(vo.getCurrentPage());
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(vo.getPageSize())) {
                pageSizeNew = Integer.parseInt(vo.getPageSize());
            }

            PageHelper.startPage(pageNumberNew, pageSizeNew);   //pageNum为第几页，pageSize为每页数量
            List<WaitSettleRecordVo> list = waitSettleRecordService.getListForPage(vo.getStartDate(), vo.getEndDate(), vo.getSettleStatus(), vo.getShopName(), vo.getOrderSn());
            page = new PageInfo<WaitSettleRecordVo>(list);

            pagination.setTotalItems(page.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(page.getList());

            returnData.setData(pagination);
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
    @RequestMapping(value = "/getListBySettleId", method = RequestMethod.GET)
    public ReturnData getListBySettle(Long id) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {

            WaitSettleRecordVo record = waitSettleRecordService.selectDetailById(id);
            if(record == null){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("未查询到该订单，如有疑问请联系管理员！");
                return returnData;
            }

            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));

            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("waitSettleRecordId", id));
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
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }


}
