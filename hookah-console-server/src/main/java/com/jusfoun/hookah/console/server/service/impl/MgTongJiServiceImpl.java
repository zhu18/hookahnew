package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.domain.mongo.MgTongJi;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.MgTongJiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaoshuai on 2017/11/30.
 */
@Service
public class MgTongJiServiceImpl extends GenericMongoServiceImpl<MgTongJi, String> implements MgTongJiService{
    protected final static Logger logger = LoggerFactory.getLogger(MgTongJiServiceImpl.class);

    @Resource
    MongoTemplate mongoTemplate;

    //插入当前访问页面的来源与关键字
    public void setTongJiInfo(String tongJiUrl, String tongJiId, String utmSource, String utmTerm, String userId) {
        MgTongJi tongJi = new MgTongJi();
        tongJi.setId(StringUtils.getUUID());
        tongJi.setAddTime(DateUtils.toDefaultNowTime());
        tongJi.setTongJiId(tongJiId);
        tongJi.setTongJiUrl(tongJiUrl);
        tongJi.setUtmSource(utmSource);
        tongJi.setUtmTerm(utmTerm);
        tongJi.setUserId(userId);
        mongoTemplate.insert(tongJi);
    }

    //查询统计信息
    @Async
    public MgTongJi getTongJiInfo(String tongJiId){
        //根据时间查询最近一条的关键字和来源
        Query query = new Query();
        query.addCriteria(Criteria.where("tongJiId").is(tongJiId));
        query.with(new Sort(Sort.Direction.DESC, "addTime"));
        List<MgTongJi> mgTongJis = mongoTemplate.find(query, MgTongJi.class);
        System.out.println(mgTongJis);
        for (MgTongJi mg : mgTongJis){
            return mg;
        }
        return null;
    }

    //获取当天统计的所有数据
    public List<MgTongJi>  getTongJiListInfo(Date startTime, Date endTime){
        Query query = new Query();
        query.addCriteria(Criteria.where("addTime").gte(startTime).lte(endTime));
        List<MgTongJi> mgTongJis = mongoTemplate.find(query, MgTongJi.class);
        return mgTongJis;
    }
}
