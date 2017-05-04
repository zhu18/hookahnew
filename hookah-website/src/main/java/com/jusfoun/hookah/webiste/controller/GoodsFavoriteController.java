package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsFavorite;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsFavoriteService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dengxu on 2017/4/8/0008.
 * 商品关注
 */
@Controller
@RequestMapping("goodsFavorite")
public class GoodsFavoriteController extends BaseController {

    @Resource
    GoodsFavoriteService goodsFavoriteService;

    @Resource
    GoodsService goodsService;

    /**
     * 添加关注
     * @param goodsFavorite
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ReturnData addFavorite(GoodsFavorite goodsFavorite) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        if (StringUtils.isBlank(goodsFavorite.getGoodsId())) {
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try {
            goodsFavorite.setUserId(getCurrentUser().getUserId());
            goodsFavoriteService.insert(goodsFavorite);

            Map<String, Object> map = new HashMap<>();
            map.put("type", "plus");
            map.put("goodsId", goodsFavorite.getGoodsId());
            map.put("changeNum", 1);
            goodsService.updateByGidForFollowNum(map);

        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 取消关注
     * @param id
     * @return
     */
    @RequestMapping("del")
    @ResponseBody
    public ReturnData delFavorite(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        if (StringUtils.isBlank(id)) {
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try {
            GoodsFavorite goodsFavorite = new GoodsFavorite();
            goodsFavorite.setGoodsId(id);// 商品id
            goodsFavorite.setUserId(getCurrentUser().getUserId());
            goodsFavorite.setIsDelete(Byte.parseByte("0"));
            goodsFavoriteService.updateByIdSelective(goodsFavorite);

            Map<String, Object> map = new HashMap<>();
            map.put("type", "sub");
            map.put("goodsId", goodsFavorite.getGoodsId());
            map.put("changeNum", 1);
            goodsService.updateByGidForFollowNum(map);

        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("list")
    @ResponseBody
    public ReturnData listFavorite(String pageNumber, String pageSize) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Pagination<Goods> page = new Pagination<>();
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("isDelete", 1));
            filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
            List<GoodsFavorite> list = goodsFavoriteService.selectList(filters);
            List<String> gfIdList = new ArrayList<>();
            if(list.size() > 0){

                for(GoodsFavorite gf : list){
                    gfIdList.add(gf.getGoodsId());
                }
                List<OrderBy> orderBys = new ArrayList();
                orderBys.add(OrderBy.desc("addTime"));
                int pageNumberNew = HookahConstants.PAGE_NUM;
                if (StringUtils.isNotBlank(pageNumber)) {
                    pageNumberNew = Integer.parseInt(pageNumber);
                }
                int pageSizeNew = HookahConstants.PAGE_SIZE;
                if (StringUtils.isNotBlank(pageSize)) {
                    pageSizeNew = Integer.parseInt(pageSize);
                }

                List<Condition> filtersGoods = new ArrayList();
                filtersGoods.add(Condition.in("goodsId", gfIdList.toArray()));

                page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filtersGoods, orderBys);
                returnData.setData(page);
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("check")
    @ResponseBody
    public ReturnData checkFavorite(String goodsId) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        if (StringUtils.isBlank(goodsId)) {
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try {

            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("goodsId", goodsId));
            filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
            GoodsFavorite goodsFavorite = goodsFavoriteService.selectOne(filters);
            if(goodsFavorite != null){
                returnData.setData(true);
            }else{
                returnData.setData(false);
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

}
