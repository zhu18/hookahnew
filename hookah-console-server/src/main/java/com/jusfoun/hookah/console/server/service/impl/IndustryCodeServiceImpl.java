package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.IndustryCodeMapper;
import com.jusfoun.hookah.core.domain.IndustryCode;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.IndustryCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admin on 2017/4/7/0007.
 */
@Service
public class IndustryCodeServiceImpl extends GenericServiceImpl<IndustryCode, Long> implements IndustryCodeService {

    @Resource
    IndustryCodeMapper industryCodeMapper;

    @Resource
    public void setDao(IndustryCodeMapper industryCodeMapper) {
        super.setDao(industryCodeMapper);
    }

    @Override
    public List<IndustryCode> getIndustryCodeList(String parentCode) {
        IndustryCode industryCode = new IndustryCode();
        industryCode.setParentIndustryCode(parentCode);
        return industryCodeMapper.select(industryCode);
    }
}
