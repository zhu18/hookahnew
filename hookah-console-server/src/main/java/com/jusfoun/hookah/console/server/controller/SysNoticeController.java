package com.jusfoun.hookah.console.server.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Cooperation;
import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.domain.SysNotice;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.SysNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/5/9.
 */
@RestController
@RequestMapping("sysnotice")
public class SysNoticeController extends BaseController{

    @Autowired
    SysNoticeService sysNoticeService;

    @RequestMapping("add")
    @Transactional
    public ReturnData addSysNotice(SysNotice notice)  throws Exception {
        boolean isExists = true;
        List<Condition> filters = new ArrayList();

        try {
            filters.clear();
            filters.add(Condition.eq("noticeTitle", notice.getNoticeTitle()));
            isExists = sysNoticeService.exists(filters);
            if (isExists) {
                throw new Exception("该公告已发布");
            }
        }catch (Exception e){
            return ReturnData.error(e.getMessage());
        }
        notice.setAddTime(new Date());
        sysNoticeService.insert(notice);
        return ReturnData.success("发布成功");
    }
    @RequestMapping("delete")
    public ReturnData deleteSysNotice(SysNotice sysNotice){
        try {
            sysNoticeService.delete(sysNotice.getNoticeId());
        }catch (Exception e){
            return ReturnData.error("删除失败");
        }
        return ReturnData.success("删除成功");
    }

    @RequestMapping("upd")
    @Transactional
    public ReturnData updNoticeByConditionSelective(SysNotice sysNotice ){
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq(("noticeId"),sysNotice.getNoticeId()));
        try {
            sysNoticeService.updateByConditionSelective(sysNotice,filters);
        }catch (Exception e){
            return ReturnData.error("修改失败");
        }
        return ReturnData.success("修改成功");
    }
    @RequestMapping("search")
    public ReturnData searchSysNotice(String currentPage, String pageSize ,SysNotice sysNotice){
        Pagination<SysNotice> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            if(StringUtils.isNotBlank(sysNotice.getNoticeTitle())){
                filters.add(Condition.like("noticeTitle", sysNotice.getNoticeTitle().trim()));
        }
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = sysNoticeService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
    }catch (Exception e){
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReturnData getUserById(@PathVariable String id) {
        SysNotice sysnotice = sysNoticeService.selectById(id);
        return ReturnData.success(sysnotice);
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public ReturnData updateStatus(String noticeId, String status) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            SysNotice sysNotice = new SysNotice();
            sysNotice.setNoticeId(noticeId);
            sysNotice.setStatus(status);
            sysNoticeService.updateByIdSelective(sysNotice);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
