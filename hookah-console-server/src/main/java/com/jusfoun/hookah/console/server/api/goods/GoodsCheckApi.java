package com.jusfoun.hookah.console.server.api.goods;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsCheckVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsCheckService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by dengxu on 2017/4/25/0025.
 */
@RestController
@RequestMapping(value = "/api/goodsCheck")
public class GoodsCheckApi extends BaseController{

    @Resource
    GoodsCheckService goodsCheckService;

    @Resource
    MgGoodsService mgGoodsService;

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 商品审核
     * @param goodsCheck
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ReturnData goodsCheck(@RequestBody GoodsCheckVo goodsCheckVo) {

        GoodsCheck goodsCheck = goodsCheckVo.getGoodsCheck();
        MgGoods.PackageApiInfoBean apiInfoBeanTar = goodsCheckVo.getApiInfoBean();
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            if(Objects.nonNull(apiInfoBeanTar)){
                if(StringUtils.isNoneBlank(goodsCheck.getGoodsId())){
                    MgGoods mgGoods = mgGoodsService.selectById(goodsCheck.getGoodsId());
                    mgGoods.setPackageApiInfo(apiInfoBeanTar);
                    mongoTemplate.save(mgGoods);
                }
            }
            goodsCheck.setCheckUser(getCurrentUser().getUserName());
            goodsCheckService.insertRecord(goodsCheck);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize,
                                    String goodsName, String goodsSn) {
        Pagination<GoodsCheck> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            if(StringUtils.isNotBlank(goodsName)){
                filters.add(Condition.like("goodsName", goodsName));
            }
            if(StringUtils.isNotBlank(goodsSn)){
                filters.add(Condition.like("goodsSn", goodsSn));
            }
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("checkTime"));
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = goodsCheckService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }



}
