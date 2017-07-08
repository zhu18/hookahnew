package com.jusfoun.hookah.pay.service.impl;

import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.domain.mongo.MgMoneyInOutLog;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgMoneyInOutLogService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

/**
 * dx
 */

@Service
public class MgMoneyInOutLogServiceImpl extends GenericMongoServiceImpl<MgMoneyInOutLog, String> implements MgMoneyInOutLogService {


    @Resource
    private MongoTemplate mongoTemplate;

    @Async
    public void initMgMoneyInOutLog(MoneyInOutBo moneyInOutBo, Map<String, String> paramMap, Long id) {

        MgMoneyInOutLog mgMoneyInOutLog = new MgMoneyInOutLog();
        MgMoneyInOutLog.SendArgs sendArgs = new MgMoneyInOutLog.SendArgs();
        try {
            BeanUtils.populate(sendArgs, paramMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("数据转换异常", e);
        }
        mgMoneyInOutLog.setPayAccountRecordId(id);
        mgMoneyInOutLog.setUserId(moneyInOutBo.getUserId());
        mgMoneyInOutLog.setTransferType(moneyInOutBo.getOperatorType());
        mgMoneyInOutLog.setAddTime(new Date());
        mgMoneyInOutLog.setSendArgs(sendArgs);
        mongoTemplate.insert(mgMoneyInOutLog);
    }

    @Async
    public void updateMgMoneyInOutLog(MoneyInOutBo moneyInOutBo, Map<String, Object> resultMap, Long id) {

        MgMoneyInOutLog mgMoneyInOutLog = new MgMoneyInOutLog();
        MgMoneyInOutLog.AcceptArgs acceptArgs = new MgMoneyInOutLog.AcceptArgs();
        try {
            BeanUtils.populate(acceptArgs, resultMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("数据转换异常", e);
        }

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("acceptArgs", acceptArgs);
        update.set("updateTime", new Date());
        mongoTemplate.updateFirst(query, update, mgMoneyInOutLog.getClass());
    }
}
