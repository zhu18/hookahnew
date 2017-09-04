package com.jusfoun.hookah.console.server.api.pushGoods;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.console.server.util.DictionaryUtil;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/8/30.
 */
@RestController
@RequestMapping(value = "/api/pushGoods")
public class PushGoodsApi extends BaseController {
    @Resource
    GoodsService goodsService;

    @Resource
    OrganizationService organizationService;

    @Resource
    GoodsCheckService goodsCheckService;

    @Resource
    MgGoodsService mgGoodsService;


    @Resource
    MqSenderService mqSenderService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize,
                                    String goodsName, String goodsSn,
                                    String keywords, String shopName,
                                    Byte checkStatus, Byte onSaleStatus
    ) {
        Pagination page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            //只查询商品状态为未删除的商品
            filters.add(Condition.eq("isDelete", 1));
            //只查询推送商品
            filters.add(Condition.eq("isLocal", 1));

            if(StringUtils.isNotBlank(goodsName)){
                filters.add(Condition.like("goodsName", goodsName.trim()));
            }
            if(StringUtils.isNotBlank(goodsSn)){
                filters.add(Condition.like("goodsSn", goodsSn.trim()));
            }
            if(StringUtils.isNotBlank(keywords)){
                filters.add(Condition.like("keywords", keywords.trim()));
            }
            if(StringUtils.isNotBlank(shopName)){
                filters.add(Condition.like("shopName", shopName.trim()));
            }
            if(checkStatus != null && checkStatus != -1){
                filters.add(Condition.eq("checkStatus", checkStatus));
            }
            if(onSaleStatus != null && onSaleStatus != -1){
                filters.add(Condition.eq("isOnsale", onSaleStatus));
            }

            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

            page.setList(copyGoodsData(page.getList()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    private List<GoodsVo> copyGoodsData(List<Goods> list) {
        List<GoodsVo> list1 = new ArrayList<>();
        for(Goods goods : list) {
            GoodsVo goodsVo = new GoodsVo();
            BeanUtils.copyProperties(goods, goodsVo);
            goodsVo.setGoodsArea((goods.getGoodsArea() == null || "".equals(goods.getGoodsArea())) ? "全部" : DictionaryUtil.getRegionById(goods.getGoodsArea()).getMergerName());
            goodsVo.setCatName(DictionaryUtil.getCategoryById(goods.getCatId()) == null ? "" : DictionaryUtil.getCategoryById(goods.getCatId()).getCatName());
            goodsVo.setOrgName(organizationService.findOrgByUserId(goods.getAddUser())==null?"":organizationService.findOrgByUserId(goods.getAddUser()).getOrgName());
            goodsVo.setCheckUser(goodsCheckService.selectOneByGoodsId(goods.getGoodsId())==null?"":goodsCheckService.selectOneByGoodsId(goods.getGoodsId()).getCheckUser());
            goodsVo.setAgencyPrice(mgGoodsService.selectById(goods.getGoodsId())==null?null:mgGoodsService.selectById(goods.getGoodsId()).getFormatList().get(0).getAgencyPrice());
            goodsVo.setSettlementPrice(mgGoodsService.selectById(goods.getGoodsId())==null?null:mgGoodsService.selectById(goods.getGoodsId()).getFormatList().get(0).getSettlementPrice());
            list1.add(goodsVo);

        }
        return list1;
    }

    @RequestMapping("edit")
    @Transactional
    public ReturnData updByConditionSelective(Goods goods){
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq(("goodsId"),goods.getGoodsId()));
        try {
            goodsService.updateByConditionSelective(goods,filters);
            //添加商品到ES
            GoodsCheck goodsCheck = new GoodsCheck();
            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goodsCheck.getGoodsId());
        }catch (Exception e){
            return ReturnData.error("修改失败");
        }
        return ReturnData.success("修改成功");
    }

}