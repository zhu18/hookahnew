package com.jusfoun.hookah.console.server.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Cooperation;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CooperationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lt on 2017/5/8.
 */
@RestController
@RequestMapping("/coo")
public class CooperationController {
    private static final Logger logger = LoggerFactory.getLogger(CooperationController.class);

    @Resource
    private CooperationService cooperationService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ReturnData add(Cooperation coo){
        try{
            cooperationService.addCooperation(coo);
        }catch (HookahException e){
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error("添加失败");
        }
        logger.info("合作机构["+coo.getCooName()+"]添加成功");
        return ReturnData.success("添加成功");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ReturnData delete(Cooperation coo){
        try{
            cooperationService.delete(coo.getCooperationId());
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error("删除失败");
        }
        logger.info("删除合作机构["+coo.getCooName()+"]成功");
        return ReturnData.success("已删除");
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ReturnData modify(Cooperation coo){
        try{
            cooperationService.modify(coo);
        } catch (HookahException e){
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        } catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error("修改失败");
        }
        return ReturnData.success("修改成功");
    }

    @RequestMapping(value = "/updateState", method = RequestMethod.POST)
    public ReturnData updateState(Cooperation coo){
        try{
            cooperationService.updateState(coo);
        } catch (HookahException e){
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        } catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error("修改状态失败");
        }
        return ReturnData.success("修改成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ReturnData search(String currentPage, String pageSize, Cooperation cooperation){
        Pagination<Cooperation> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));

            if(StringUtils.isNotBlank(cooperation.getCooName())){
                filters.add(Condition.like("cooName", cooperation.getCooName().trim()));
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            page = cooperationService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error("获取失败");
        }
        return ReturnData.success(page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReturnData getUserById(@PathVariable String id) {
        Cooperation coo = cooperationService.selectById(id);
        return ReturnData.success(coo);
    }

}
