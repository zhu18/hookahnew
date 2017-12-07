package com.jusfoun.hookah.console.server.service.impl;

import com.ctc.wstx.util.DataUtil;
import com.jusfoun.hookah.core.constants.TongJiEnum;
import com.jusfoun.hookah.core.dao.FlowUserMapper;
import com.jusfoun.hookah.core.domain.FlowUser;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgTongJi;
import com.jusfoun.hookah.core.domain.vo.FlowUserVo;
import com.jusfoun.hookah.core.domain.vo.FlowUsersVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import sun.security.pkcs11.wrapper.Constants;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zhaoshuai on 2017/12/5.
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

    // 每隔凌晨执行一次
    //@Scheduled(cron="59 59 23 * * ?")
    public void saveTongJiInfoService(){
        logger.info("------------------开始统计当天访问次数----------------------");
        //获取当天访问所有数据
        //List<MgTongJi> tongJiListInfo = mgTongJiService.getTongJiListInfo(getStartTime(), getEndTime());
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

        Map<String,Integer> regMap = new HashMap<String,Integer>();
        for (MgTongJi reg : regList){
            if(reg != null){
                if(regMap.containsKey(reg.getUtmSource())){
                    Integer reqNum = regMap.get(reg.getUtmSource());
                    reqNum++;
                    regMap.put(reg.getUtmSource(), reqNum);
                }else{//map中不存在，新建key，用来存放数据
                    regMap.put(reg.getUtmSource(), 1);
                }
            }
        }

        //获取当天个人认证数
        List<Condition> personFilters = new ArrayList<>();
        String date1 = DateUtils.toDateText(new Date());
        personFilters.add(Condition.like("addTime", date1));
        personFilters.add(Condition.eq("userType", 2));
        List<User> personUsers = userService.selectList(filters);
        List<MgTongJi> personList = new ArrayList<MgTongJi>();
        for(User person : personUsers){
            MgTongJi mgTongJiInfo = getMgTongJiInfo(person.getUserId(), TongJiEnum.PERSON_URL);
            personList.add(mgTongJiInfo);
        }
        Map<String,Integer> personMap = new HashMap<String,Integer>();
        for (MgTongJi person : personList){
            if( person != null){
                if(personMap.containsKey(person.getUtmSource())){
                    Integer personNum = personMap.get(person.getUtmSource());
                    personNum++;
                    regMap.put(person.getUtmSource(), personNum);
                }else{//map中不存在，新建key，用来存放数据
                    personMap.put(person.getUtmSource(), 1);
                }
            }
        }

        //获取当天当天企业认证数
        List<Condition> orgFilters = new ArrayList<>();
        String date2 = DateUtils.toDateText(new Date());
        orgFilters.add(Condition.like("addTime", date2));
        orgFilters.add(Condition.eq("userType", 4));
        List<User> orgUsers = userService.selectList(filters);
        List<MgTongJi> orgList = new ArrayList<MgTongJi>();
        for(User org : orgUsers){
            MgTongJi mgTongJiInfo = getMgTongJiInfo(org.getUserId(), TongJiEnum.ORG_URL);
            orgList.add(mgTongJiInfo);
        }
        Map<String,Integer> orgMap = new HashMap<String,Integer>();
        for (MgTongJi org : orgList){
            if( org != null){
                if(orgMap.containsKey(org.getUtmSource())){
                    Integer orgNum = orgMap.get(org.getUtmSource());
                    orgNum++;
                    orgMap.put(org.getUtmSource(), orgNum);
                }else{//map中不存在，新建key，用来存放数据
                    orgMap.put(org.getUtmSource(), 1);
                }
            }
        }
        logger.info("------------------结束统计当天访问次数----------------------");
        FlowUserVo flowUserVo = new FlowUserVo();
        for (String key : regMap.keySet()){
            flowUserVo.setDataSource(key);
            flowUserVo.setNewUserNum(regMap.get(key));
            if (personMap!=null&&personMap.get(key)!=null){
                Integer integer = personMap.get(key);
                flowUserVo.setPersonUser(integer);
            }
            if (orgMap!=null&&orgMap.get(key)!=null){
                Integer integer = orgMap.get(key);
                flowUserVo.setOrgUser(integer);
            }
            flowUserVo.setAddTime(new Date());
            flowUserMapper.insert(flowUserVo);
        }
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
        return mongoTemplate.findOne(query, MgTongJi.class);
    }
}
