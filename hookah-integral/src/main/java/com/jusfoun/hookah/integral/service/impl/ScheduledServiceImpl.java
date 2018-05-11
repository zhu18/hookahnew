package com.jusfoun.hookah.integral.service.impl;

import com.jusfoun.hookah.core.domain.jf.JfOverdueDetails;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.integral.contants.JfContants;
import com.jusfoun.hookah.rpc.api.JfOverdueDetailsService;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  积分结算
 * @author : dengxu
 * @create 2017-11-22 15:37
 **/

@Component
public class ScheduledServiceImpl {

    protected final static Logger logger = LoggerFactory.getLogger(ScheduledServiceImpl.class);

    @Resource
    JfRecordService jfRecordService;

    @Resource
    JfOverdueDetailsService jfOverdueDetailsService;

    // todo …… 每月月初结算上一个月的积分
    @Scheduled(cron = "0 0 0 1 * ?")
    public void handleSettle(){

        try {

            // todo …… 获取前一个月时间日期
            String beforeMonthDate = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM"));

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("addDate", beforeMonthDate));
            List<JfRecord> jfRecordList = jfRecordService.selectList(filters);

            if(jfRecordList == null || jfRecordList.size() == 0){
                logger.error(beforeMonthDate + ">>当前月份未获取有效数据！");
                return;
            }

            Map<String, List<JfRecord>> mapGroupByUserId =
                    jfRecordList.stream().collect(Collectors.groupingBy(JfRecord::getUserId));

            for (Map.Entry<String, List<JfRecord>> entry : mapGroupByUserId.entrySet()) {

                // todo …… 校验当前用户是否已结算
                List<Condition> overFilters = new ArrayList<>();
                overFilters.add(Condition.eq("addDate", beforeMonthDate));
                overFilters.add(Condition.eq("userId", entry.getKey()));
                JfOverdueDetails jfOverdueDetails = jfOverdueDetailsService.selectOne(overFilters);
                if(jfOverdueDetails != null){
                    logger.info(beforeMonthDate + ">>当前月份积分已结算！");
                    return;
                }

                jfOverdueDetails = new JfOverdueDetails();

                jfOverdueDetails.setUserId(entry.getKey());

                List<JfRecord> listForUserId = entry.getValue();

                // todo …… 获取当月获得总量
                Integer thisMonthGetTotal = listForUserId.stream()
                        .filter(jfRecord -> (jfRecord.getAction().equals(JfContants.GET) || jfRecord.getSourceId().equals(JfContants.JF_11)))
                        .mapToInt(JfRecord::getScore).sum();
                jfOverdueDetails.setThisMonthGetTotal(thisMonthGetTotal);

                // todo …… 获取当月兑换总量
                Integer thisMonthExchangeTotal = listForUserId.stream()
                        .filter(jfRecord -> (jfRecord.getAction().equals(JfContants.TAKE) || jfRecord.getSourceId().equals(JfContants.JF_12)))
                        .mapToInt(JfRecord::getScore).sum();

                // todo …… 获取当月兑换总量
                jfOverdueDetails.setThisMonthSurTotal(thisMonthGetTotal - thisMonthExchangeTotal);

                // todo …… 结算积分最近有效期  5年过期
                jfOverdueDetails.setLastExpire(LocalDate.now().plusYears(JfContants.JF_EXPIRE_YEAR).format(DateTimeFormatter.ofPattern("yyyyMM")));

                // todo …… 获取当月过期积分
                Integer thisMonthExpTotal = listForUserId.stream()
                        .filter(jfRecord -> (jfRecord.getExpire().equals(JfContants.JF_EXPIRE_FALSE)
                                && Period.between(jfRecord.getAddTime().toInstant().atZone(ZoneId.systemDefault())
                                .toLocalDate(), LocalDate.now()).getYears() >= JfContants.JF_EXPIRE_YEAR))
                        .mapToInt(JfRecord::getScore).sum();
                jfOverdueDetails.setThisMonthExpTotal(thisMonthExpTotal);

                // todo …… 过期积分获取时间
                if(thisMonthExpTotal > 0){
                    jfOverdueDetails.setObtainDate(LocalDate.now().minusYears(JfContants.JF_EXPIRE_YEAR).format(DateTimeFormatter.ofPattern("yyyyMM")));
                }

                jfOverdueDetails.setAddTime(new Date());
                jfOverdueDetails.setSettleDate(beforeMonthDate);
                jfOverdueDetails.setOperator("System");

                // todo …… 入库
                jfOverdueDetailsService.insert(jfOverdueDetails);
            }
        } catch (Exception e) {
            logger.error("积分结算异常>>>{}", e);
            logger.error(LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM")) + ">>积分结算失败，操作时间>>" + DateUtils.toDefaultNowTime());
        }
    }

}