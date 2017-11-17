package com.jusfoun.hookah.integral.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.jf.JfRecordMapper;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.vo.JfShowVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.JfRecordCacheService;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    JfRecordCacheService jfRecordCacheService;

    @CacheEvict(value = "personUseJfSum", key="#jfRecord.getUserId()")
    @Override
    public int insertAndGetId(JfRecord jfRecord) {
        return jfRecordMapper.insertAndGetId(jfRecord);
    }

    @Override
    public ReturnData getJfRecord(Integer pageNumberNew, Integer pageSizeNew, String userId, String type) throws Exception {

//        // 获取用户所有积分记录
//        List<JfShowVo> jfList = jfRecordMapper.selectListByUserId(userId);
//
//        // 获取用户所有获取积分的记录
//        List<JfShowVo> ObtainList = jfList.parallelStream().filter(x -> x.getAction().equals(Short.parseShort("1"))).collect(Collectors.toList());
//
//        // 获取用户所有兑换积分的记录
//        List<JfShowVo> ExchangeList = jfList.parallelStream().filter(x -> x.getAction().equals(Short.parseShort("2"))).collect(Collectors.toList());


        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<JfShowVo> pagination = new Pagination<>();

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

            // 处理过期积分
            List<JfShowVo> jfList = jfRecordMapper.selectListByUserIdAndType(userId, type);
        }
        returnData.setData2(jfRecordCacheService.getUseScoreByUserId(userId));

        return returnData;
    }

    @Override
    public ReturnData selectListByUserInfo(Integer pageNumberNew, Integer pageSizeNew, String userName, String type, String userSn) throws Exception {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        return null;
    }
}
