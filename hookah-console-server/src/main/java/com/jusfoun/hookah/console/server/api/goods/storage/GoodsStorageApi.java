package com.jusfoun.hookah.console.server.api.goods.storage;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.domain.GoodsStorage;
import com.jusfoun.hookah.core.domain.mongo.MgGoodsStorage;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.GeneratorUtil;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsStorageService;
import com.jusfoun.hookah.rpc.api.MgGoodsStorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjl on 2017-10-24.
 */
@RestController
@RequestMapping(value = "/api/storage")
public class GoodsStorageApi extends BaseController {
    @Resource
    GoodsStorageService goodsStorageService;
    @Resource
    MgGoodsStorageService mgGoodsStorageService;

    @RequestMapping(value = "/create", method= RequestMethod.POST)
    public ReturnData create(GoodsStorage goodsStorage) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsStorage.setAddUser(getCurrentUser().getUserId());
            goodsStorage.setId(GeneratorUtil.getUUID());
            goodsStorageService.insertSelective(goodsStorage);
        } catch (Exception e) {
            logger.error("创建货架错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("创建货架错误！" + e.getMessage());
        }
        return returnData;
    }

    @RequestMapping(value = "/delete")
    public ReturnData delete(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsStorageService.delete(id);
        } catch (Exception e) {
            logger.error("删除货架错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("删除货架错误！" + e.getMessage());
        }
        return returnData;
    }

    @RequestMapping(value = "/update", method= RequestMethod.POST)
    public ReturnData update(GoodsStorage goodsStorage) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsStorage.setAddUser(getCurrentUser().getUserId());
            goodsStorageService.updateByIdSelective(goodsStorage);
        } catch (Exception e) {
            logger.error("创建货架错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("创建货架错误！" + e.getMessage());
        }
        return returnData;
    }

    /**
     * 查询所有已开启货架
     * @return
     */
    @RequestMapping(value = "/findAllOpenStorages")
    public ReturnData findAllOpenStorages() {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("isOpen", 1));
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("stOrder"));
            returnData.setData(goodsStorageService.selectList(filters, orderBys));
        } catch (Exception e) {
            logger.error("查询所有开启货架错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("查询所有开启货架错误！" + e.getMessage());
        }
        return returnData;
    }

    /**
     * 查询所有货架信息
     * @return
     */
    @RequestMapping(value = "/findAllStorages")
    public ReturnData findAllStorages() {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("stOrder"));
            returnData.setData(goodsStorageService.selectList(null, orderBys));
        } catch (Exception e) {
            logger.error("查询所有货架错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("查询所有货架错误！" + e.getMessage());
        }
        return returnData;
    }

    /**
     * 查询单个货架信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findStorage")
    public ReturnData findAllStorages(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(goodsStorageService.selectById(id));
        } catch (Exception e) {
            logger.error("查询货架错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("查询货架错误！" + e.getMessage());
        }
        return returnData;
    }

    /**
     * 新增或更新货架详情
     * @param mgGoodsStorage
     * @return
     */
    @RequestMapping(value = "/upsertDetails", method= RequestMethod.POST)
    public ReturnData upsertDetails(MgGoodsStorage mgGoodsStorage) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            mgGoodsStorage.setAddUser(getCurrentUser().getUserId());
            mgGoodsStorage.setUpdateTime(DateUtils.toDefaultNowTime());
            mgGoodsStorageService.upsertDetails(mgGoodsStorage);
        } catch (Exception e) {
            logger.error("新增或更新货架详情错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("新增或更新货架详情错误！" + e.getMessage());
        }
        return returnData;
    }

    /**
     * 查询货架管理详情
     * @param storageId
     * @return
     */
    @RequestMapping("/findDetail")
    public ReturnData findDetail(String storageId) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(mgGoodsStorageService.findDetail(storageId));
        } catch (Exception e) {
            logger.error("查询货架内容详情错误！",e);
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("查询货架内容详情错误！" + e.getMessage());
        }
        return returnData;
    }


}
