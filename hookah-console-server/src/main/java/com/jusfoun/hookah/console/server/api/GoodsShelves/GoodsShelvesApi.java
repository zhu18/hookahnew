package com.jusfoun.hookah.console.server.api.GoodsShelves;

import com.jusfoun.hookah.console.server.controller.OrderInfoController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.rpc.api.GoodsShelvesService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ctp on 2017/4/11.
 */
@Controller
@RequestMapping("api/goodsShelves")
public class GoodsShelvesApi {
    private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    @Autowired
    GoodsShelvesService goodsShelvesService;

    /**
     * 插入货架
     *
     * @param goodsShelves
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ReturnData addGoodsShelves(GoodsShelves goodsShelves) {
        ReturnData<GoodsShelves> returnData = new ReturnData<GoodsShelves>();
        returnData.setCode(ExceptionConst.Success);
        try {
            goodsShelves = goodsShelvesService.addGoodsShelves(goodsShelves);
            if (null == goodsShelves) {
                throw new HookahException("操作失败");
            }
            returnData.setData(goodsShelves);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 删除货架
     *
     * @param id
     * @return
     */
    @RequestMapping("del")
    @ResponseBody
    public ReturnData delGoodsShelves(String id) {
        ReturnData<List<GoodsShelves>> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            int i = goodsShelvesService.delete(id);
            if (i < 0) {
                throw new HookahException("操作失败");
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 修改 订单
     *
     * @param goodsShelves
     * @return
     */
    @RequestMapping("update")
    public ReturnData update(GoodsShelves goodsShelves) {
        if (StringUtils.isBlank(goodsShelves.getShelvesId())) {
            return ReturnData.invalidParameters("参数ShelvesId不可为空");
        }
        try {
            goodsShelves.setLastUpdateTime(new Date());
            goodsShelvesService.updateByIdSelective(goodsShelves);
            return ReturnData.success();
        } catch (Exception e) {
            logger.error("修改错误", e);
            return ReturnData.error("系统异常");
        }
    }
    /**
     * 分页查询货架
     * @param pageNum
     * @param pageSize
     * @param shelvesName
     * @param shelvesTag
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String findByPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "15") Integer pageSize, String shelvesName, String shelvesTag, Model model) {
        try {
            List<Condition> filters = new ArrayList<>();
            if (StrUtil.notBlank(shelvesName)) {
                filters.add(Condition.like("shelvesName", shelvesName));
            }
            if (StrUtil.notBlank(shelvesTag)) {
                filters.add(Condition.like("shelvesTag", shelvesTag));
            }
            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));
            Pagination<GoodsShelves> goodsShelvess = goodsShelvesService.getListInPage(pageNum, pageSize, filters, orderBys);
            model.addAttribute("goodsShelvesList", goodsShelvess);
            return "/list";
        } catch (Exception e) {
            logger.error("分页查询错误", e);
            ReturnData.error("系统异常");
        }

        return "/error/500";
    }

}
