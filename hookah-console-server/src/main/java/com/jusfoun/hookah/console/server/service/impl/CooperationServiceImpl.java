package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CooperationMapper;
import com.jusfoun.hookah.core.domain.Cooperation;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.CooperationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lt on 2017/5/8.
 */
@Service
public class CooperationServiceImpl  extends GenericServiceImpl<Cooperation, String> implements CooperationService {

    @Resource
    CooperationMapper cooperationMapper;

    @Resource
    public void setDao(CooperationMapper cooperationMapper) {
        super.setDao(cooperationMapper);
    }

    @Override
    @Transactional
    public void addCooperation(Cooperation coo) throws Exception{
        Date date = DateUtils.now();
        boolean isExists = true;
        List<Condition> filters = new ArrayList<>();
        filters.clear();
        if (!coo.getCooName().isEmpty()){
            filters.add(Condition.eq("cooName", coo.getCooName()));
        }else {
            throw new HookahException("合作机构名称不能为空");
        }
        if (coo.getPictureUrl().isEmpty()){
            throw new HookahException("logo地址不能为空");
        }
        if (coo.getUrl().isEmpty()){
            throw new HookahException("机构链接地址不能为空");
        }
        isExists = exists(filters);
        if(isExists){
            throw new HookahException("该合作机构已经添加");
        }
        if (coo.getState() == null){
            coo.setState(coo.COOPERATION_STATE_ON);
        }
        coo.setCreateTime(date);
        coo.setLastUpdateTime(date);
        cooperationMapper.insert(coo);
    }

    @Override
    @Transactional
    public void modify(Cooperation coo) throws Exception{
        Date date = DateUtils.now();
        List<Condition> filters = new ArrayList<>();
        if (StringUtils.isNoneBlank(coo.getCooperationId())) {
            filters.add(Condition.eq("cooperationId", coo.getCooperationId()));
        }else {
            throw new HookahException("未选择修改机构，请重新操作");
        }
        coo.setLastUpdateTime(date);
        cooperationMapper.updateByExampleSelective(coo,convertFilter2Example(filters));
    }
}
