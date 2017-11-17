package com.jusfoun.hookah.integral.service.impl;

import com.jusfoun.hookah.core.dao.jf.JfRecordMapper;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.vo.JfShowVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public int insertAndGetId(JfRecord jfRecord) {
        return jfRecordMapper.insertAndGetId(jfRecord);
    }

    @Override
    public List<JfShowVo> getJfRecord(String userId, String type) {

//        // 获取用户所有积分记录
//        List<JfShowVo> jfList = jfRecordMapper.selectListByUserId(userId);
//
//        // 获取用户所有获取积分的记录
//        List<JfShowVo> ObtainList = jfList.parallelStream().filter(x -> x.getAction().equals(Short.parseShort("1"))).collect(Collectors.toList());
//
//        // 获取用户所有兑换积分的记录
//        List<JfShowVo> ExchangeList = jfList.parallelStream().filter(x -> x.getAction().equals(Short.parseShort("2"))).collect(Collectors.toList());

        // 根据用户userId 和 请求类型 获取所有积分记录
        List<JfShowVo> jfList = jfRecordMapper.selectListByUserIdAndType(userId, type);

        return jfList;
    }
}
