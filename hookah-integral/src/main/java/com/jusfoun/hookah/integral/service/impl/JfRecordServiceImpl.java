package com.jusfoun.hookah.integral.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.jf.JfRecordMapper;
import com.jusfoun.hookah.core.domain.jf.JfOverdueDetails;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.vo.JfShowVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CacheService;
import com.jusfoun.hookah.rpc.api.JfOverdueDetailsService;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 积分服务
 * @author:jsshao
 * @date: 2017-3-17
 */

@Service
public class JfRecordServiceImpl extends GenericServiceImpl<JfRecord, Long> implements JfRecordService {

    @Resource
    private JfRecordMapper jfRecordMapper;

    @Resource
    public void setDao(JfRecordMapper jfRecordMapper) {
        super.setDao(jfRecordMapper);
    }

    @Resource
    CacheService cacheService;

    @Resource
    JfOverdueDetailsService jfOverdueDetailsService;

    @CacheEvict(value = "personUseJfSum", key="#jfRecord.getUserId()")
    @Override
    public int insertAndGetId(JfRecord jfRecord) {
        return jfRecordMapper.insertAndGetId(jfRecord);
    }

    @Override
    public ReturnData getJfRecord(Integer pageNumberNew, Integer pageSizeNew, String userId, String type) throws Exception {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<JfShowVo> pagination = new Pagination<>();
        Pagination<JfOverdueDetails> overPages = new Pagination<>();

        if(!type.equals("3")){

            // 根据用户userId 和 请求类型 获取所有积分记录
            PageHelper.startPage(pageNumberNew, pageSizeNew);
            List<JfShowVo> jfList = jfRecordMapper.selectListByUserIdAndType(userId, type);
            PageInfo<JfShowVo> page = new PageInfo<JfShowVo>(jfList);

            pagination.setTotalItems(page.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(page.getList());

            returnData.setData(pagination);
        }else {

            // 获取处理过的过期积分
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", userId));

            List<OrderBy > orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));

            overPages = jfOverdueDetailsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            returnData.setData(overPages);

        }

        returnData.setData2(cacheService.getUseScoreByUserId(userId));

        return returnData;
    }

    @Override
    public ReturnData selectListByUserInfo(Integer pageNumberNew, Integer pageSizeNew, String userName, String type, String userSn) throws Exception {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        return null;
    }
}
