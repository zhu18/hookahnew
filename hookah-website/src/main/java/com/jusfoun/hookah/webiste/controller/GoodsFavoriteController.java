package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.domain.GoodsFavorite;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsFavoriteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengxu on 2017/4/8/0008.
 */
@Controller
@RequestMapping("goodsFavorite")
public class GoodsFavoriteController extends BaseController {

    @Resource
    GoodsFavoriteService goodsFavoriteService;

    @RequestMapping("add")
    public @ResponseBody ReturnData addFavorite(GoodsFavorite goodsFavorite) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        if (StringUtils.isBlank(goodsFavorite.getGoodsId()) || StringUtils.isBlank(goodsFavorite.getUserId())) {
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try {
            goodsFavoriteService.insert(goodsFavorite);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("del")
    public @ResponseBody ReturnData delFavorite(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        if (StringUtils.isBlank(id)) {
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try {
            goodsFavoriteService.delete(id);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("list")
    public @ResponseBody ReturnData listFavorite(String pageNumber, String pageSize) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Pagination<GoodsFavorite> page = new Pagination<>();
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("isDelete", 1));
            filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if(StringUtils.isNotBlank(pageNumber)){
                pageNumberNew = Integer.parseInt(pageNumber);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if(StringUtils.isNotBlank(pageSize)){
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = goodsFavoriteService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            returnData.setData(page);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

}
