package com.jusfoun.hookah.console.server.api.withdraw;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.WithdrawRecord;
import com.jusfoun.hookah.core.domain.vo.WithdrawVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.*;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.WithdrawRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台提现
 */

@RestController
@RequestMapping("/api/withdrawRecord")
public class WithdrawRecordController extends BaseController {

    @Resource
    WithdrawRecordService withdrawRecordService;

    @Resource
    PayAccountService payAccountService;

    /**
     * 查询所有申请列表
     */
    @RequestMapping("/getList")
    public ReturnData getList(String startDate, String endDate,
                              String currentPage, String pageSize,
                              String checkStatus, String orgName
    ) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<WithdrawVo> pagination = new Pagination<>();
        PageInfo<WithdrawVo> page = new PageInfo<>();
        try {

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            PageHelper.startPage(pageNumberNew, pageSizeNew);   //pageNum为第几页，pageSize为每页数量
            List<WithdrawVo> list = withdrawRecordService.getListForPage(startDate, DateUtils.transferDate(endDate), checkStatus, orgName);
            page = new PageInfo<WithdrawVo>(list);

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
     * 根据id查看审核记录
     * @param id
     * @return
     */
    @RequestMapping("/getOneById")
    public ReturnData getOne(@RequestParam("id") Long id){

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {

            WithdrawRecord record = withdrawRecordService.selectById(id);
            if(record == null){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("未查询到此条提现记录，请联系管理员！");
                return returnData;
            }

            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userId", record.getUserId()));
            PayAccount payAccount = payAccountService.selectOne(filters);
            if(payAccount == null){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("未查询到账户信息，请联系管理员！");
                return returnData;
            }
            WithdrawVo withdrawVo = new WithdrawVo();
            BeanUtils.copyProperties(record, withdrawVo);
            withdrawVo.setBalance(payAccount.getBalance());
            withdrawVo.setUseBalance(payAccount.getUseBalance());

            returnData.setData(withdrawVo);

        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 根据id审核提现记录
     * @param id
     * @return
     */
    @RequestMapping("/checkOne")
    public ReturnData checkOne(
            @RequestParam("id") Long id,
            @RequestParam("checkStatus") byte checkStatus,
            String checkMsg
    ){

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {

            if(checkStatus == HookahConstants.WithdrawStatus.checkFail.code && !StringUtils.isNotBlank(checkMsg)){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("提现审核失败，请填写失败原因！");
                return returnData;
            }
//            withdrawRecordService.checkOne(id, checkStatus, checkMsg);

            WithdrawRecord record = withdrawRecordService.selectById(id);
            if(record == null){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("提现信息查询失败！");
                return returnData;
            }

            record.setCheckStatus(checkStatus);
            record.setCheckMsg(checkMsg);
            record.setCheckTime(new Date());
            record.setCheckOperator(getCurrentUser().getUserName());

            int n = withdrawRecordService.updateByIdSelective(record);

            if(n != 1){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("后台审核失败！");
                return returnData;
            }

            returnData.setMessage("后台审核完成！");

            if(n == 1 && checkStatus == HookahConstants.WithdrawStatus.CheckSuccess.code){

                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("userId", record.getUserId()));
                PayAccount payAccount = payAccountService.selectOne(filters);

                if(payAccount == null){
                    returnData.setCode(ExceptionConst.Failed);
                    returnData.setMessage("审核成功, 客户账户信息查询失败！");
                    return returnData;
                }

                // 审核成功 扣客户账
                payAccountService.operatorByType(payAccount.getId(),
                                            payAccount.getUserId().toString(),
                                            HookahConstants.TradeType.OnlineCash.getCode(),
                                            record.getMoney(), OrderHelper.genOrderSn(),
                                            getCurrentUser().getUserName());

                returnData.setCode(ExceptionConst.Success);
                returnData.setMessage("审核成功，已扣除客户账！");
            }

        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }

}

