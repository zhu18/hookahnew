package com.jusfoun.hookah.console.server.api.GoodsShelves;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MgGoodsShelvesGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ctp on 2017/4/5.
 * 货架下的商品管理
 */
@Controller
@RequestMapping("/api/mgGoodssg")
public class MgGoodsShelvesGoodsApi {

    @Autowired
    private MgGoodsShelvesGoodsService mgGoodsShelvesGoodsService;


    @RequestMapping("/addGSMongo")
    @ResponseBody
    public ReturnData addGoodsShelvesGoodsMongo(MgShelvesGoods mgShelvesGoods){
        return mgGoodsShelvesGoodsService.addMgGoodsSG(mgShelvesGoods);
    }


    @RequestMapping("/saveGSMongo")
    @ResponseBody
    public ReturnData saveGoodsShelvesGoodsMongo(MgShelvesGoods mgShelvesGoods){
        return  mgGoodsShelvesGoodsService.updateMgGoodsSG(mgShelvesGoods);
    }


    @RequestMapping("/findByIdGSMongo")
    @ResponseBody
    public ReturnData findByIdGSMongo(String shelvesGoodsId){
        return mgGoodsShelvesGoodsService.findByIdGSMongo(shelvesGoodsId);
    }

    @RequestMapping("/delGSMongo")
    @ResponseBody
    public ReturnData delGSMongo(String shelvesGoodsId){
        return mgGoodsShelvesGoodsService.delGSMongo(shelvesGoodsId);
    }

    /**
     * 根据货架id删除货架中某一个商品
     * @param shelvesGoodsId
     * @param goodsId
     * @return
     */
    @RequestMapping("/delSMongoGoodsById")
    @ResponseBody
    public ReturnData delSMongoGoodsById(String shelvesGoodsId, String goodsId) throws HookahException {
        return mgGoodsShelvesGoodsService.delSMongoGoodsById(shelvesGoodsId, goodsId);
    }

    @RequestMapping(value = "/findGSMongoById", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData getListInPage(String currentPage, String pageSize, String shelvesGoodsId) {
        Pagination<Goods> page = null;
        try {
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = mgGoodsShelvesGoodsService.getData(pageNumberNew, pageSizeNew, shelvesGoodsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    @RequestMapping("/addGidByMGid")
    @ResponseBody
    public ReturnData addGidByMGid(String shelvesId, String goodsId){
        return mgGoodsShelvesGoodsService.addGidByMGid(shelvesId, goodsId);
    }

}
