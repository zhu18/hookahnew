package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bingbing wu
 * @date 2017/3/13 下午9:33
 * @desc
 */
@Controller
@RequestMapping("/goods/back")
public class GoodsBackController extends BaseController {

    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;


    @RequestMapping("/add")
    @ResponseBody
    public ReturnData addGoodsBack(@Valid @RequestBody GoodsVo obj) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            obj.setAddUser(userId);
            goodsService.addGoods(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 已上架的商品列表
     * @param pageNum
     * @param pageSize
     * @param goodsName
     * @return
     */
    @RequestMapping(value = "/sale/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData saleList(String pageNum, String pageSize, String goodsName) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            if(StringUtils.isBlank(pageNum)) {
                pageNum = this.PAGE_NUM;
            }
            if(StringUtils.isBlank(pageSize)) {
                pageSize = this.PAGE_SIZE;
            }
            returnData.setData(goodsService.saleList(pageNum, pageSize, goodsName, userId));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
    // 待上架商品列表
    @RequestMapping(value = "/wait/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData waitList(String pageNum, String pageSize, String goodsName, Integer checkStatus, Integer isBook) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            if(StringUtils.isBlank(pageNum)) {
                pageNum = this.PAGE_NUM;
            }
            if(StringUtils.isBlank(pageSize)) {
                pageSize = this.PAGE_SIZE;
            }
            returnData.setData(goodsService.waitList(pageNum, pageSize, goodsName, userId, checkStatus, isBook));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 已下架商品列表
     * @param pageNum
     * @param pageSize
     * @param goodsName
     * @return
     */
    @RequestMapping(value = "/offsale/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData offsaleList(String pageNum, String pageSize, String goodsName) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            if(StringUtils.isBlank(pageNum)) {
                pageNum = this.PAGE_NUM;
            }
            if(StringUtils.isBlank(pageSize)) {
                pageSize = this.PAGE_SIZE;
            }
            returnData.setData(goodsService.offsaleList(pageNum, pageSize, goodsName, userId));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
    // 违规商品列表
    @RequestMapping(value = "/illegal/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData illegalList(String pageNum, String pageSize, String goodsName) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            if(StringUtils.isBlank(pageNum)) {
                pageNum = this.PAGE_NUM;
            }
            if(StringUtils.isBlank(pageSize)) {
                pageSize = this.PAGE_SIZE;
            }
            returnData.setData(goodsService.illegalList(pageNum, pageSize, goodsName, userId));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData list(String pageNumber, String pageSize, String goodsName, Byte checkStatus) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Pagination<Goods> page = new Pagination<>();
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            filters.add(Condition.eq("isDelete", 1));

            String userId = this.getCurrentUser().getUserId();
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(pageNumber)) {
                pageNumberNew = Integer.parseInt(pageNumber);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            if (StringUtils.isNotBlank(goodsName)) {
                filters.add(Condition.eq("goodsName", goodsName.trim()));
            }
            if (checkStatus != null) {
                filters.add(Condition.like("checkStatus", checkStatus));
            }
            filters.add(Condition.eq("addUser", userId));
            page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            returnData.setData(page);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


    /**
     * 根据id查询商品详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public ReturnData findGoodsById(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(goodsService.findGoodsById(id));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ReturnData update(@Valid @RequestBody GoodsVo obj) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsService.updateGoods(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 商品删除：/status/del
     * 商品下架：/status/offSale
     * @param goodsId
     * @param opera
     * @return
     */
    @RequestMapping("/status/{opera}")
    @ResponseBody
    public ReturnData update(String goodsId, @PathVariable String opera) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            int i = goodsService.updateGoodsStatus(goodsId, opera);
            if(i <= 0)
                throw new HookahException("操作失败！");
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 商品重新上架
     * @param goodsId
     * @param dateTime
     * @return
     */
    @RequestMapping("/onsale")
    @ResponseBody
    public ReturnData onsale(String goodsId, @RequestParam(required = false) String dateTime) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            int i = goodsService.onsale(goodsId, dateTime);
            if(i <= 0)
                throw new HookahException("操作失败！");
            else {
                returnData.setMessage("商品操作成功，请耐心等待审核！");
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
