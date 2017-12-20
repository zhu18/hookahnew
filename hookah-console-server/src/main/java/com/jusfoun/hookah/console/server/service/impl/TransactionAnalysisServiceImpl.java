package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.TongJiEnum;
import com.jusfoun.hookah.core.dao.FlowUserMapper;
import com.jusfoun.hookah.core.dao.TransactionAnalysisMapper;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.TransactionAnalysis;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserCheck;
import com.jusfoun.hookah.core.domain.mongo.MgTongJi;
import com.jusfoun.hookah.core.domain.vo.FlowUserVo;
import com.jusfoun.hookah.core.domain.vo.TransactionAnalysisVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lt on 2017/12/19.
 */
@Service
public class TransactionAnalysisServiceImpl extends GenericServiceImpl<TransactionAnalysis, Long> implements TransactionAnalysisService {

    @Resource
    TransactionAnalysisMapper transactionAnalysisMapper;

    @Resource
    public void setDao(TransactionAnalysisMapper transactionAnalysisMapper) {
        super.setDao(transactionAnalysisMapper);
    }

    @Override
    public ReturnData countOrderList(String startTime, String endTime) {
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.ge("insertTime", startTime.substring(0, 10)));
            filters.add(Condition.le("insertTime", endTime.substring(0, 10)));
            List<TransactionAnalysis> transactionAnalyses = this.selectList(filters);
            if(transactionAnalyses != null && transactionAnalyses.size() > 0){
                //获取统计总计
                TransactionAnalysisVo sum = transactionAnalysisMapper.selectSum(startTime ,endTime);
                //分组获取统计数据
                List<TransactionAnalysisVo> dataSource = transactionAnalysisMapper.selectBySourceList(startTime, endTime);
                sum.setDataSource("总计");
                dataSource.add(sum);
                return ReturnData.success(dataSource);
            }
            return ReturnData.error("所选时间段内没有交易统计数据");
        } catch (Exception e) {
            logger.error("获取统计信息出错{}",e);
            return ReturnData.error("获取统计信息出错，请联系开发人员");
        }

    }
}
