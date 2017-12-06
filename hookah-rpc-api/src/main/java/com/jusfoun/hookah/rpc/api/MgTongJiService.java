package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.mongo.MgTongJi;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.Date;
import java.util.List;

public interface MgTongJiService extends GenericService<MgTongJi, String> {

    /**
     * 插入统计
     * @param tongJiUrl 当前统计地址
     * @param tongJiId  当前统计cookieID
     * @param utmSource 当前统计来源
     * @param utmTerm  当前统计关键字
     */
    void setTongJiInfo( String tongJiUrl, String tongJiId, String utmSource, String utmTerm, String userId);

    MgTongJi getTongJiInfo(String tongJiId);

    List<MgTongJi>  getTongJiListInfo(Date startTime, Date endTime);
}
