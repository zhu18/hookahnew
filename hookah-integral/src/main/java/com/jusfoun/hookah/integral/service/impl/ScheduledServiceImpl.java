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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

                JfOverdueDetails jfOverdueDetails = new JfOverdueDetails();

                List<JfRecord> listForUserId = entry.getValue();

                jfOverdueDetails.setUserId(entry.getKey());

                // todo …… 获取当月获得总量
                Integer thisMonthGetTotal = listForUserId.stream()
                        .filter(jfRecord -> (jfRecord.getAction().equals(JfContants.get) || jfRecord.getSourceId().equals(JfContants.JF_11)))
                        .mapToInt(JfRecord::getScore).sum();
                jfOverdueDetails.setThisMonthGetTotal(thisMonthGetTotal);

                // todo …… 获取当月兑换总量
                Integer thisMonthExchangeTotal = listForUserId.stream()
                        .filter(jfRecord -> (jfRecord.getAction().equals(JfContants.take) || jfRecord.getSourceId().equals(JfContants.JF_12)))
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
                if(jfOverdueDetails.getId() != null){
                    logger.info(beforeMonthDate + ">>积分结算完成，操作时间>>" + DateUtils.toDefaultNowTime());
                } else {
                    logger.error(beforeMonthDate + ">>积分结算失败，操作时间>>" + DateUtils.toDefaultNowTime());
                }
            }
        } catch (Exception e) {
            logger.error("积分结算异常>>>{}", e);
            logger.error(LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM")) + ">>积分结算失败，操作时间>>" + DateUtils.toDefaultNowTime());
        }
    }

    public static void main(String[] args) {
//        System.out.println(LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM")));
//        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
//        System.out.println(LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("yyyyMM")));

//        LocalDate date1 = LocalDate.now();
//        System.out.println("Today : " + date1);
//        LocalDate date2 = LocalDate.parse("2012-12-27");
//
//        Period p = Period.between(date2, LocalDate.);
//
//        System.out.printf("年龄 : %d 年 %d 月 %d 日", p.getYears(), p.getMonths(), p.getDays());

//        Period.between(date2, LocalDate.now())


//        LocalDate today = LocalDate.now();
//        System.out.println("Today : " + today);
//        LocalDate birthDate = LocalDate.of(1993, Month.OCTOBER, 19);
//        System.out.println("BirthDate : " + birthDate);
//        Period p = Period.between(birthDate, today);
//        System.out.printf("年龄 : %d 年 %d 月 %d 日", p.getYears(), p.getMonths(), p.getDays());
    }


}
