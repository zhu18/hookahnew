package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.IndustryCode;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * Created by admin on 2017/4/7/0007.
 */
public interface IndustryCodeService extends GenericService<IndustryCode, Long> {

    List<IndustryCode> getIndustryCodeList(String parentCode);
}
