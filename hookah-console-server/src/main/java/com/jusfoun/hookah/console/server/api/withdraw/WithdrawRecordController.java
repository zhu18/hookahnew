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
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.WithdrawRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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
            returnData.setMessage("系统繁忙，请稍后再试！");
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
            returnData.setMessage("系统繁忙，请稍后再试！");
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
            returnData = withdrawRecordService.checkOneApply(id, checkStatus, checkMsg, getCurrentUser().getUserName());
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统繁忙，请稍后再试！");
            e.printStackTrace();
        }
        return returnData;
    }

}

