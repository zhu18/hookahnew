package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.jf.JfRecord;

public interface CacheService {

    Integer getUseScoreByUserId(String userId);

    int insertAndGetId(JfRecord jfRecord);
}
