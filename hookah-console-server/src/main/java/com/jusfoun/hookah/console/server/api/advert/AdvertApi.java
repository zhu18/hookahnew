package com.jusfoun.hookah.console.server.api.advert;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Advert;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.AdvertService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/13 下午1:50
 * @desc
 */
@RestController
@RequestMapping(value = "/api/advert")
public class AdvertApi {

    @Resource
    private AdvertService advertService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData selectCarouselFigure(String currentPage, String pageSize,Advert advert){
        Pagination<Advert> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            //只查询广告类型为2的轮播
            filters.add(Condition.eq("advertType", "2"));

            if(StringUtils.isNotBlank(advert.getAdvertGroup())){
                filters.add(Condition.like("advertGroup", advert.getAdvertGroup().trim()));
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = advertService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    @RequestMapping("/add")
    public ReturnData addCarouselFigure(Advert advert) {
        try {
            advert.setAdvertType("2");
            advert = advertService.insert(advert);
            if(advert == null) {
                return ReturnData.error("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(advert);
    }

    @RequestMapping("/delete")
    public ReturnData delCarouselFigure(String advertId){
        try {
            advertService.delete(advertId);
        } catch (Exception e) {
            return ReturnData.error("删除失败");
        }
        return ReturnData.success("删除成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReturnData getUserById(@PathVariable String id) {
        Advert advert = advertService.selectById(id);
        return ReturnData.success(advert);
    }

    @RequestMapping("/update")
    public ReturnData updateCarouselFigure(Advert advert){
        if(StringUtils.isBlank(advert.getUrl())){
            return ReturnData.error("跳转路径不可为空");
        }
        if(StringUtils.isBlank(advert.getHref())){
            return ReturnData.error("图片路径不可为空");
        }
        try {
            advertService.updateByIdSelective(advert);
        } catch (Exception e) {
            return ReturnData.error("修改失败");
        }
        return ReturnData.success("修改成功");
    }
}