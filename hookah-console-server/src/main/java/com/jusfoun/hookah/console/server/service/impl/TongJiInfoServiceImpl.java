package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.TongJiEnum;
import com.jusfoun.hookah.core.dao.FlowUserMapper;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.TransactionAnalysis;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserCheck;
import com.jusfoun.hookah.core.domain.mongo.MgTongJi;
import com.jusfoun.hookah.core.domain.vo.FlowUserVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
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
 * Created by crs on 2017/12/5.
 */
@Service
public class TongJiInfoServiceImpl implements TongJiInfoService {

    private final static Logger logger = LoggerFactory.getLogger(TongJiInfoServiceImpl.class);

    @Resource
    MgTongJiService mgTongJiService;

    @Resource
    UserService userService;

    @Resource
    UserCheckService userCheckService;

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    FlowUserMapper flowUserMapper;

    @Resource
    FlowUserService flowUserService;

    @Resource
    OrderInfoService orderInfoService;

    @Resource
    TransactionAnalysisService transactionAnalysisService;

    // 每隔凌晨执行一次
    @Scheduled(cron="0 59 23 * * ?")
    public void saveTongJiInfoService(){
        logger.info("------------------开始统计当天访问次数----------------------");

        //获取当天的新注册用户数
        List<Condition> filters = new ArrayList<>();
        String date = DateUtils.toDateText(new Date());
        filters.add(Condition.like("addTime", date));
        List<User> users = userService.selectList(filters);
        List<MgTongJi> regList = new ArrayList();
        for (User user : users){
            MgTongJi mgTongJiInfo = getMgTongJiInfo(user.getUserId(), TongJiEnum.REG_URL);
            regList.add(mgTongJiInfo);
        }

        //计算当天时间内按来源的注册数量
        Map<String,Integer> regMap = new HashMap<String,Integer>();
        for (MgTongJi reg : regList){
            if(reg != null){
                if(regMap.containsKey(reg.getSource())){
                    Integer reqNum = regMap.get(reg.getSource());
                    reqNum++;
                    regMap.put(reg.getSource(), reqNum);
                }else{//map中不存在，新建key，用来存放数据
                    regMap.put(reg.getSource(), 1);
                }
            }
        }

        //获取当天个人认证数
        List<Condition> personFilters = new ArrayList<>();
        personFilters.add(Condition.like("checkTime", date));
        personFilters.add(Condition.eq("userType", 0));
        personFilters.add(Condition.eq("checkStatus", 1));
        List<UserCheck> personUsers = userCheckService.selectList(personFilters);
        List<MgTongJi> personList = new ArrayList<MgTongJi>();
        for(UserCheck person : personUsers){
            MgTongJi mgTongJiInfo = getMgTongJiInfo(person.getUserId(), TongJiEnum.PERSON_URL);
            personList.add(mgTongJiInfo);
        }
        //计算当天时间内按来源的个人认证数量
        Map<String,Integer> personMap = new HashMap<String,Integer>();
        for (MgTongJi person : personList){
            if( person != null){
                if(personMap.containsKey(person.getSource())){
                    Integer personNum = personMap.get(person.getSource());
                    personNum++;
                    personMap.put(person.getSource(), personNum);
                }else{//map中不存在，新建key，用来存放数据
                    personMap.put(person.getSource(), 1);
                }
            }
        }

        //获取当天当天企业认证数
        List<Condition> orgFilters = new ArrayList<>();
        orgFilters.add(Condition.like("checkTime", date));
        orgFilters.add(Condition.eq("userType", 1));
        orgFilters.add(Condition.eq("checkStatus", 1));
        List<UserCheck> orgUsers = userCheckService.selectList(orgFilters);
        List<MgTongJi> orgList = new ArrayList<MgTongJi>();
        for(UserCheck org : orgUsers){
            MgTongJi mgTongJiInfo = getMgTongJiInfo(org.getUserId(), TongJiEnum.ORG_URL);
            orgList.add(mgTongJiInfo);
        }
        //计算当天时间内按来源的企业认证数量
        Map<String,Integer> orgMap = new HashMap<String,Integer>();
        for (MgTongJi org : orgList){
            if( org != null){
                if(orgMap.containsKey(org.getSource())){
                    Integer orgNum = orgMap.get(org.getSource());
                    orgNum++;
                    orgMap.put(org.getSource(), orgNum);
                }else{//map中不存在，新建key，用来存放数据
                    orgMap.put(org.getSource(), 1);
                }
            }
        }

        FlowUserVo flowUserVo = new FlowUserVo();
        Date addTime = new Date();
        String s= DateUtils.toDateText(addTime);
        //当重复执行同天时间的统计数据时， 删除之前的统计数据
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("insertTime", s));
        flowUserService.deleteByCondtion(filter);

        //把统计计算的各来源数据量存库
        for (String key : regMap.keySet()){
            flowUserVo.setDataSource(key);
            flowUserVo.setNewUserNum(regMap.get(key));
            if (personMap != null && personMap.get(key) != null){
                Integer integer = personMap.get(key);
                flowUserVo.setPersonUser(integer);
            }
            if (orgMap != null && orgMap.get(key) != null){
                Integer integer = orgMap.get(key);
                flowUserVo.setOrgUser(integer);
            }
            flowUserVo.setAddTime(addTime);
            flowUserVo.setInsertTime(s);
            flowUserMapper.insert(flowUserVo);
        }
        logger.info("------------------结束统计当天访问次数----------------------");
//        return ReturnData.success("统计完成");
    }

    @Override
    public void countOrderData() {
        logger.info("------------------开始统计当天订单数据----------------------");

        //获取当天的所有订单数据  包括所有的已付款，未付款，已取消，已删除的订单
        List<Condition> filter = new ArrayList<>();
        Date now = new Date();
        String date = DateUtils.toDateText(now);
        filter.add(Condition.like("addTime", date));
        List<OrderInfo> orderInfos = orderInfoService.selectList(filter);
        if (orderInfos.size() == 0){
            logger.info("------------------当天无订单交易数据---------------------");
            return;
        }
        List<OrderInfoTongJi> OrderInfoTongJi = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos){
            MgTongJi mgTongJi = getMgTongJiInfo(orderInfo.getOrderSn(),TongJiEnum.ORDER_CREATE_URL);
            if (mgTongJi != null){
                OrderInfoTongJi orderInfoTongJi = new OrderInfoTongJi();
                orderInfoTongJi.setMgTongJi(mgTongJi);
                orderInfoTongJi.setOrderInfo(orderInfo);
                OrderInfoTongJi.add(orderInfoTongJi);
            }
        }

        //各来源订单数
        Map<String,Integer> orderNumMap = new HashMap<String,Integer>();
        Map<String,Integer> payedOrderNumMap = new HashMap<String,Integer>();
        Map<String,Long> payedOrderMoneyMap = new HashMap<String,Long>();
        Map<String,Integer> unPayedOrderNumMap = new HashMap<String,Integer>();
        for (OrderInfoTongJi orderInfoTongJi : OrderInfoTongJi){
            MgTongJi mgTongJi = orderInfoTongJi.getMgTongJi();
            OrderInfo orderInfo = orderInfoTongJi.getOrderInfo();
            if(orderNumMap.containsKey(mgTongJi.getSource())){
                Integer orderNum = orderNumMap.get(mgTongJi.getSource());
                orderNum++;
                orderNumMap.put(mgTongJi.getSource(), orderNum);
                if (orderInfo.getPayStatus() == OrderInfo.PAYSTATUS_PAYED){
                    Integer payedOrderNum = (payedOrderNumMap.get(mgTongJi.getSource()) == null) ? 0 : payedOrderNumMap.get(mgTongJi.getSource());
                    payedOrderNum++;
                    payedOrderNumMap.put(mgTongJi.getSource(), payedOrderNum);

                    Long payedOrderMoney = (payedOrderMoneyMap.get(mgTongJi.getSource()) == null) ? 0L : payedOrderMoneyMap.get(mgTongJi.getSource());
                    payedOrderMoney = payedOrderMoney + orderInfo.getOrderAmount();
                    payedOrderMoneyMap.put(mgTongJi.getSource(), payedOrderMoney);
                }else if (orderInfo.getIsDeleted() != 1 && orderInfo.getForceDeleted() != 1){
                    Integer unPayedOrderNum = (unPayedOrderNumMap.get(mgTongJi.getSource()) == null) ? 0 : unPayedOrderNumMap.get(mgTongJi.getSource());
                    unPayedOrderNum++;
                    unPayedOrderNumMap.put(mgTongJi.getSource(), unPayedOrderNum);
                }
            }else{
                //所有订单数
                orderNumMap.put(mgTongJi.getSource(), 1);
                if (orderInfo.getPayStatus() == OrderInfo.PAYSTATUS_PAYED){
                    Integer payedOrderNum = 0;
                    payedOrderNum++;
                    payedOrderNumMap.put(mgTongJi.getSource(), payedOrderNum);

                    Long payedOrderMoney = 0L;
                    payedOrderMoney = payedOrderMoney + orderInfo.getOrderAmount();
                    payedOrderMoneyMap.put(mgTongJi.getSource(), payedOrderMoney);
                }else if (orderInfo.getIsDeleted() != 1 && orderInfo.getForceDeleted() != 1){
                    Integer unPayedOrderNum = 0;
                    unPayedOrderNum++;
                    unPayedOrderNumMap.put(mgTongJi.getSource(), unPayedOrderNum);
                }
            }
        }

        //当重复执行同天时间的统计数据时， 删除之前的统计数据
        filter.clear();
        filter.add(Condition.eq("insertTime", date));
        transactionAnalysisService.deleteByCondtion(filter);

        for (String key : orderNumMap.keySet()){
            TransactionAnalysis transactionAnalysis = new TransactionAnalysis();
            transactionAnalysis.setDataSource(key);
            transactionAnalysis.setOrderNum(orderNumMap.get(key));
            if (payedOrderNumMap != null && payedOrderNumMap.get(key) != null){
                transactionAnalysis.setEffecticeOrderNum(payedOrderNumMap.get(key));
            }
            if (payedOrderMoneyMap != null && payedOrderMoneyMap.get(key) != null){
                transactionAnalysis.setEffectiveOrderAmount(payedOrderMoneyMap.get(key));
            }
            if (unPayedOrderNumMap != null && unPayedOrderNumMap.get(key) != null){
                transactionAnalysis.setPayingOrderNum(unPayedOrderNumMap.get(key));
            }
            transactionAnalysis.setAddTime(now);
            transactionAnalysis.setInsertTime(date);
            transactionAnalysis.setAddOperator("SYSTEM");
            transactionAnalysisService.insert(transactionAnalysis);
        }
        logger.info("------------------当天订单数据统计结束----------------------");
    }


    //获取当天起始时间
    private static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    //获取当天结束时间
    private static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    public MgTongJi getMgTongJiInfo(String userId, String tongJiUrl){
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        //query.addCriteria(Criteria.where("addTime").gte(getStartTime()).lte(getEndTime()));
        query.addCriteria(Criteria.where("tongJiUrl").is(tongJiUrl));
        MgTongJi mgTongJi = mongoTemplate.findOne(query, MgTongJi.class);
        if(mgTongJi != null){
            String source = null;
            if (mgTongJi.getUtmTerm() == null){
                source = mgTongJi.getUtmSource();
            }
            source = mgTongJi.getUtmSource() + "_" + mgTongJi.getUtmTerm() ;
            mgTongJi.setSource(source);
        }
        return mgTongJi;
    }

    class OrderInfoTongJi{
        private OrderInfo orderInfo;
        private MgTongJi mgTongJi;

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public MgTongJi getMgTongJi() {
            return mgTongJi;
        }

        public void setMgTongJi(MgTongJi mgTongJi) {
            this.mgTongJi = mgTongJi;
        }
    }
}
