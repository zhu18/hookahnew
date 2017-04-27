package com.jusfoun.hookah.console.server.api.shelf;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsShelvesService;
import com.jusfoun.hookah.rpc.api.MgGoodsShelvesGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/7 上午10:50
 * @desc
 */
@RestController
@RequestMapping(value = "/api/shelf")
public class ShelfApi extends BaseController {


    @Resource
    GoodsShelvesService goodsShelvesService;

    @Resource
    MgGoodsShelvesGoodsService mgGoodsShelvesGoodsService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize, HttpServletRequest request) {
        Pagination<GoodsShelves> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            filters.add(Condition.ne("shelvesStatus", 3));
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = goodsShelvesService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ReturnData addShelf(GoodsShelves goodsShelves) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsShelves.setAddUser(getCurrentUser().getUserName());
            goodsShelves.setAddTime(new Date());
            goodsShelves.setUserId(getCurrentUser().getUserId());
            GoodsShelves result = goodsShelvesService.insert(goodsShelves);

            MgShelvesGoods mgShelvesGoods = new MgShelvesGoods();
            mgShelvesGoods.setShelvesGoodsId(result.getShelvesId());
            mgShelvesGoods.setShelvesGoodsName(result.getShelvesName());
            mgGoodsShelvesGoodsService.insert(mgShelvesGoods);

        } catch (HookahException e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnData updateShelf(GoodsShelves goodsShelves) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsShelvesService.updateByIdSelective(goodsShelves);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

//    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
//    public ReturnData updateStatus(String shelvesId, String shelvesStatus) {
//        GoodsShelves goodsShelves = new GoodsShelves();
//        goodsShelves.setShelvesId(shelvesId);
//        if("0".equals(shelvesStatus)){
//            goodsShelves.setShelvesStatus(Byte.parseByte("1"));
//        }else if("1".equals(shelvesStatus)){
//            goodsShelves.setShelvesStatus(Byte.parseByte("0"));
//        }
//        int result = goodsShelvesService.updateByIdSelective(goodsShelves);
//        return ReturnData.success(result);
//    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public ReturnData updateStatus(String shelvesId, String shelvesStatus) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {

            GoodsShelves goodsShelves = new GoodsShelves();
            goodsShelves.setShelvesId(shelvesId);
            goodsShelves.setShelvesStatus(Byte.parseByte(shelvesStatus));
            goodsShelvesService.updateByIdSelective(goodsShelves);

            // 删除货架是

        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
