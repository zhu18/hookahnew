package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.TongJiEnum;
import com.jusfoun.hookah.core.dao.FlowUserMapper;
import com.jusfoun.hookah.core.domain.*;
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
    UserDetailService userDetailService;

    @Resource
    OrganizationService organizationService;

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
    @Scheduled(cron="0 50 23 * * ?")
    public void saveTongJiInfoService(){
        logger.info("------------------开始统计当天访问次数----------------------");
        Date addTime = new Date();
        String sameDay= DateUtils.toDateText(addTime);
        //获取当天的新注册用户数
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.like("addTime", sameDay));
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
        personFilters.add(Condition.like("addTime", sameDay));
        List<UserDetail> personUsers = userDetailService.selectList(personFilters);
        List<MgTongJi> personList = new ArrayList<MgTongJi>();
        for(UserDetail person : personUsers){
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
        orgFilters.add(Condition.like("addTime", sameDay));
        List<Organization> orgUsers = organizationService.selectList(orgFilters);
        List<MgTongJi> orgList = new ArrayList<MgTongJi>();
        for(Organization org : orgUsers){
            if (org!=null){
                orgFilters.clear();
                orgFilters.add(Condition.eq("orgId", org.getOrgId()));
                User user = userService.selectOne(orgFilters);
                if (user!=null){
                    MgTongJi mgTongJiInfo = getMgTongJiInfo(user.getUserId(), TongJiEnum.ORG_URL);
                    orgList.add(mgTongJiInfo);
                }
            }
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
        //当重复执行同天时间的统计数据时， 删除之前的统计数据
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("insertTime", sameDay));
        flowUserService.deleteByCondtion(filter);

        //把统计计算的各来源数据量存库
        flowUserVo.setAddTime(addTime);
        flowUserVo.setInsertTime(sameDay);
        for (String key : regMap.keySet()){
            flowUserVo.setDataSource(key);
            flowUserVo.setNewUserNum(regMap.get(key));
//            if (personMap != null && personMap.get(key) != null){
//                Integer integer = personMap.get(key);
//                flowUserVo.setPersonUser(integer);
//            }
//            if (orgMap != null && orgMap.get(key) != null){
//                Integer integer = orgMap.get(key);
//                flowUserVo.setOrgUser(integer);
//            }
            flowUserService.insert(flowUserVo);
        }
        //获取个人认证信息并存库
        for (String personKey : personMap.keySet()) {
            FlowUserVo flowUserVoByPerson = new FlowUserVo();
            // 查询当前来源数据库是否存在
            filter.clear();
            filter.add(Condition.eq("insertTime", sameDay));
            filter.add(Condition.eq("dataSource", personKey));
            FlowUser flowUser = flowUserService.selectOne(filter);
            if(flowUser != null && personKey.equals(flowUser.getDataSource())){
                // 如果当天来源存在，进行更新操作
                    filter.clear();
                    filter.add(Condition.eq("id", flowUser.getId()));
//                    filter.add(Condition.eq("dataSource", flowUser.getDataSource()));
                    flowUserVoByPerson.setPersonUser(personMap.get(personKey));
                    flowUserService.updateByConditionSelective(flowUserVoByPerson,filter);
            }else {// 如果当天来源不存在，进行添加操作
                flowUserVoByPerson.setDataSource(personKey);
                flowUserVoByPerson.setPersonUser(personMap.get(personKey));
                flowUserVoByPerson.setAddTime(addTime);
                flowUserVoByPerson.setInsertTime(sameDay);
                flowUserService.insert(flowUserVoByPerson);
            }
        }

        //获取企业认证信息并存库
        for (String orgKey : orgMap.keySet()) {
            FlowUserVo flowUserVoByOrg = new FlowUserVo();
            // 查询当前来源数据库是否存在
            filter.clear();
            filter.add(Condition.eq("insertTime", sameDay));
            filter.add(Condition.eq("dataSource", orgKey));
            FlowUser flowUser = flowUserService.selectOne(filter);
            if(flowUser != null && orgKey.equals(flowUser.getDataSource())){
                // 如果当天来源存在，进行更新操作
                    filter.clear();
                    filter.add(Condition.eq("id", flowUser.getId()));
//                    filter.add(Condition.eq("dataSource", flowUser.getDataSource()));
                    flowUserVoByOrg.setOrgUser(orgMap.get(orgKey));
                    flowUserService.updateByConditionSelective(flowUserVoByOrg,filter);
            }else {  // 如果当天来源不存在，进行添加操作
                flowUserVoByOrg.setDataSource(orgKey);
                flowUserVoByOrg.setOrgUser(orgMap.get(orgKey));
                flowUserVoByOrg.setAddTime(addTime);
                flowUserVoByOrg.setInsertTime(sameDay);
                flowUserService.insert(flowUserVoByOrg);
            }
        }
        logger.info("------------------结束统计当天访问次数----------------------");
//        return ReturnData.success("统计完成");
    }

    @Override
    public void countOrderData() {
        logger.info("------------------开始统计当天订单数据----------------------");

        //获取当天的所有新订单数据  包括所有的已付款，未付款，已取消，已删除的订单
        List<Condition> filter = new ArrayList<>();
        Date now = new Date();
        String date = DateUtils.toDateText(now);
        filter.add(Condition.like("addTime", date));
        List<OrderInfo> orderInfos = orderInfoService.selectList(filter);

        //获取当天所有的新支付订单数据  因为有些订单是前一天下单今天支付
        filter.clear();
        filter.add(Condition.like("payTime", date));
        filter.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));
        List<OrderInfo> payedOrders = orderInfoService.selectList(filter);
        if (orderInfos.size() == 0 && payedOrders.size() == 0){
            logger.info("------------------当天无订单交易数据---------------------");
            return;
        }

        //今日下单订单统计列表
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

        //今日支付的订单统计列表
        List<OrderInfoTongJi> payedOrderTongJi = new ArrayList<>();
        //昨日未支付 今日支付的订单统计列表 用于更新昨日的统计信息
        List<OrderInfoTongJi> yesterdayUnPayedOrder = new ArrayList<>();
        for (OrderInfo orderInfo : payedOrders){
            MgTongJi mgTongJi = getMgTongJiInfo(orderInfo.getOrderSn(),TongJiEnum.ORDER_PAY_URL);
            if (mgTongJi != null){
                OrderInfoTongJi orderInfoTongJi = new OrderInfoTongJi();
                orderInfoTongJi.setMgTongJi(mgTongJi);
                orderInfoTongJi.setOrderInfo(orderInfo);
                payedOrderTongJi.add(orderInfoTongJi);
                if (!orderInfo.getAddTime().toString().contains(date)) {
                    yesterdayUnPayedOrder.add(orderInfoTongJi);
                }
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
                if (orderInfo.getPayStatus() != OrderInfo.PAYSTATUS_PAYED && orderInfo.getIsDeleted() != 1 && orderInfo.getForceDeleted() != 1){
                    Integer unPayedOrderNum = (unPayedOrderNumMap.get(mgTongJi.getSource()) == null) ? 0 : unPayedOrderNumMap.get(mgTongJi.getSource());
                    unPayedOrderNum++;
                    unPayedOrderNumMap.put(mgTongJi.getSource(), unPayedOrderNum);
                }
            }else{
                //所有订单数
                orderNumMap.put(mgTongJi.getSource(), 1);
                if (orderInfo.getPayStatus() != OrderInfo.PAYSTATUS_PAYED && orderInfo.getIsDeleted() != 1 && orderInfo.getForceDeleted() != 1){
                    unPayedOrderNumMap.put(mgTongJi.getSource(), 1);
                }
            }
        }

        for (OrderInfoTongJi orderInfoTongJi : payedOrderTongJi) {
            MgTongJi mgTongJi = orderInfoTongJi.getMgTongJi();
            OrderInfo orderInfo = orderInfoTongJi.getOrderInfo();
            if (payedOrderNumMap.containsKey(mgTongJi.getSource())) {
                Integer payedOrderNum = (payedOrderNumMap.get(mgTongJi.getSource()) == null) ? 0 : payedOrderNumMap.get(mgTongJi.getSource());
                payedOrderNum++;
                payedOrderNumMap.put(mgTongJi.getSource(), payedOrderNum);

                Long payedOrderMoney = (payedOrderMoneyMap.get(mgTongJi.getSource()) == null) ? 0L : payedOrderMoneyMap.get(mgTongJi.getSource());
                payedOrderMoney = payedOrderMoney + orderInfo.getOrderAmount();
                payedOrderMoneyMap.put(mgTongJi.getSource(), payedOrderMoney);
            } else {
                payedOrderNumMap.put(mgTongJi.getSource(), 1);
                payedOrderMoneyMap.put(mgTongJi.getSource(), orderInfo.getOrderAmount());
            }
        }
        //当重复执行同天时间的统计数据时， 删除之前的统计数据  并更新前一天未支付数据
        filter.clear();
        filter.add(Condition.eq("insertTime", date));
        transactionAnalysisService.deleteByCondtion(filter);

        for (String key : orderNumMap.keySet()){
            TransactionAnalysis transactionAnalysis = new TransactionAnalysis();
            transactionAnalysis.setDataSource(key);
            transactionAnalysis.setOrderNum(orderNumMap.get(key));
            if (payedOrderNumMap != null && payedOrderNumMap.containsKey(key)){
                transactionAnalysis.setEffecticeOrderNum(payedOrderNumMap.get(key));
            }
            if (payedOrderMoneyMap != null && payedOrderMoneyMap.containsKey(key)){
                transactionAnalysis.setEffectiveOrderAmount(payedOrderMoneyMap.get(key));
            }
            if (unPayedOrderNumMap != null && unPayedOrderNumMap.containsKey(key)){
                transactionAnalysis.setPayingOrderNum(unPayedOrderNumMap.get(key));
            }
            transactionAnalysis.setAddTime(now);
            transactionAnalysis.setInsertTime(date);
            transactionAnalysis.setAddOperator("SYSTEM");
            transactionAnalysisService.insert(transactionAnalysis);
        }
        //订单有前一天下单今天支付的情况  所以今天下单的来源不一定包括所有支付订单的来源
        for (String key : payedOrderNumMap.keySet()) {
            if (!orderNumMap.containsKey(key)) {
                TransactionAnalysis transactionAnalysis = new TransactionAnalysis();
                transactionAnalysis.setDataSource(key);
                transactionAnalysis.setOrderNum(orderNumMap.get(key));
                transactionAnalysis.setEffecticeOrderNum(payedOrderNumMap.get(key));
                transactionAnalysis.setEffectiveOrderAmount(payedOrderMoneyMap.get(key));
                transactionAnalysis.setAddTime(now);
                transactionAnalysis.setInsertTime(date);
                transactionAnalysis.setAddOperator("SYSTEM");
                transactionAnalysisService.insert(transactionAnalysis);
            }
        }
        //更新昨日统计信息
        Map<String,Integer> yesterdayOrderNumMap = new HashMap<String,Integer>();
        for (OrderInfoTongJi orderInfoTongJi : yesterdayUnPayedOrder) {
            MgTongJi mgTongJi = orderInfoTongJi.getMgTongJi();
            if (yesterdayOrderNumMap.containsKey(mgTongJi.getSource())) {
                Integer Num = yesterdayOrderNumMap.get(mgTongJi.getSource());
                yesterdayOrderNumMap.put(mgTongJi.getSource(), Num++);
            } else {
                yesterdayOrderNumMap.put(mgTongJi.getSource(), 1);
            }
        }
        for (String dataSource : yesterdayOrderNumMap.keySet()) {
            String yesterday = DateUtils.getYesterday(now, DateUtils.DATE_FORMAT);
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("insertTime", yesterday));
            filters.add(Condition.eq("dataSource", dataSource));
            TransactionAnalysis transactionAnalysis = transactionAnalysisService.selectOne(filters);
            transactionAnalysis.setPayingOrderNum(transactionAnalysis.getPayingOrderNum() - yesterdayOrderNumMap.get(dataSource));
            transactionAnalysis.setUpdateTime(now);
            transactionAnalysisService.updateByIdSelective(transactionAnalysis);
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
