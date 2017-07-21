package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.OperateInfo;
import com.jusfoun.hookah.core.domain.vo.OperateVO;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.OperateInfoMongoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenhf on 2017/7/11.
 */
@Service
public class OperateInfoMongoServiceImpl extends GenericMongoServiceImpl<OperateInfo, String> implements OperateInfoMongoService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Pagination<OperateInfo> getSoldOrderList(OperateVO operateVO) {
        List<Condition> filters = new ArrayList();
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        //设置查询平台
        if(StringUtils.isNotBlank(operateVO.getPlatform())){
            filters.add(Condition.eq("platform", operateVO.getPlatform().trim()));
        }
        //设置查询事件类型
        if(StringUtils.isNotBlank(operateVO.getLogType())){
            filters.add(Condition.eq("logType", operateVO.getLogType().trim()));
        }
        //设置操作类型
        if (StringUtils.isNotBlank(operateVO.getOptType())){
            filters.add(Condition.eq("optType",operateVO.getOptType()));
        }
        //设置用户名
        if (StringUtils.isNotBlank(operateVO.getUserName())){
            filters.add(Condition.eq("userName",operateVO.getUserName()));
        }
        Query query = this.convertFilter2Query(filters);
        //设置操作起始时间、结束时间
        Criteria criteria = null;
        Date optStartTime = StringUtils.isNotBlank(operateVO.getOptStartTime())?DateUtils.getDate(operateVO.getOptStartTime(),DateUtils.DEFAULT_DATE_TIME_FORMAT):null;
        Date optEndTime = StringUtils.isNotBlank(operateVO.getOptEndTime())?DateUtils.getDate(operateVO.getOptEndTime().substring(0, 11) + "23:59:59",DateUtils.DEFAULT_DATE_TIME_FORMAT):null;
        if (optStartTime!=null && optEndTime!=null){
            criteria = Criteria.where("operateTime").gte(optStartTime).lte(optEndTime);
            query.addCriteria(criteria);
        }else if (optStartTime==null && optEndTime!=null){
            criteria = Criteria.where("operateTime").lt(optEndTime);
            query.addCriteria(criteria);
        }else if (optStartTime!=null && optEndTime==null){
            criteria = Criteria.where("operateTime").gte(optStartTime);
            query.addCriteria(criteria);
        }
        query.with(new Sort(Sort.Direction.DESC, "operateTime"));
        List<OperateInfo> list = this.mongoTemplate.find(query, (Class)trueType);
        //设置分页信息
        query.skip((operateVO.getPageNumber()-1)*operateVO.getPageSize());
        query.limit(operateVO.getPageSize());
        logger.info("[Mongo Dao ]queryPage:{}({},{})" , query,operateVO.getPageNumber(),operateVO.getPageSize() );
        List<OperateInfo> page = this.mongoTemplate.find(query, (Class)trueType);
        List<OperateInfo> retrun_page = new ArrayList<>();
        for (OperateInfo operateInfo: page){
            OperateInfo optInfo = new OperateInfo();
            optInfo = operateInfo;
            optInfo.setOptTypeName(operateInfo.getOptType());
            optInfo.setLogTypeName(operateInfo.getLogType());
            retrun_page.add(optInfo);
        }
        Pagination<OperateInfo> pagination = new Pagination<OperateInfo>();
        pagination.setTotalItems(list.size());
        pagination.setPageSize(operateVO.getPageSize());
        pagination.setCurrentPage(operateVO.getPageNumber());
        pagination.setList(retrun_page);
        return pagination;
    }
}
