package com.jusfoun.hookah.console.server.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsCheckService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/4/7/0007.
 */
@Controller
@RequestMapping("goodsCheck")
public class GoodsCheckController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    @Resource
    GoodsCheckService goodsCheckService;

    @Resource
    GoodsService goodsService;


    @RequestMapping(value = "addCheck", method = RequestMethod.GET)
    public @ResponseBody ReturnData goodsCheck(GoodsCheck goodsCheck){

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        if (StringUtils.isBlank(goodsCheck.getGoodsId())) {
            return ReturnData.invalidParameters("参数goodsId不可为空");
        }

        if (StringUtils.isBlank(goodsCheck.getCheckStatus().toString())) {
            return ReturnData.invalidParameters("参数checkStatus不可为空");
        }

        try {
            goodsCheck.setCheckUser(getCurrentUser().getUserId());
            goodsCheckService.insertRecord(goodsCheck);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public @ResponseBody ReturnData list(String pageNumber, String pageSize, String checkUser, Byte checkStatus) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Pagination<GoodsCheck> page = new Pagination<>();
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("checkTime"));
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if(StringUtils.isNotBlank(pageNumber)){
                pageNumberNew = Integer.parseInt(pageNumber);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if(StringUtils.isNotBlank(pageSize)){
                pageSizeNew = Integer.parseInt(pageSize);
            }
            if (checkStatus != null) {
                filters.add(Condition.eq("checkStatus", checkStatus));
            }
            if (checkUser != null) {
                filters.add(Condition.eq("checkUser", checkUser));
            }
            page = goodsCheckService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            returnData.setData(page);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
