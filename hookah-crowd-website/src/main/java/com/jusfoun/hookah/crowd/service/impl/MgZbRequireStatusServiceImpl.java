package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.service.MgZbRequireStatusService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

@Service
public class MgZbRequireStatusServiceImpl extends GenericMongoServiceImpl<MgZbRequireStatus, String> implements MgZbRequireStatusService {

    @Resource
    MongoTemplate mongoTemplate;

    @Async
    public void setRequireStatusInfo(String requirementSn, String statusName, String statusValue) {

        try {
            MgZbRequireStatus mgZbRequireStatus = mongoTemplate.findById(requirementSn, MgZbRequireStatus.class);
            if(mgZbRequireStatus == null){

                Class clazz = MgZbRequireStatus.class;
                MgZbRequireStatus obj = (MgZbRequireStatus) clazz.newInstance();
                PropertyDescriptor pd = new PropertyDescriptor(statusName, clazz);
                Method set = pd.getWriteMethod();
                set.invoke(obj, statusValue);
                obj.setRequirementNum(requirementSn);

                mongoTemplate.insert(obj);
            }else{
                Query query = new Query(Criteria.where("_id").is(requirementSn));
                Update update = new Update();
                if(StringUtils.isNotBlank(statusValue)){
                    update.set(statusName, statusValue);
                }
                mongoTemplate.upsert(query, update, MgZbRequireStatus.class);
            }
            logger.error("添加需求流程状态时间成功");
        }catch (Exception e){
            logger.error("添加需求流程状态时间失败", e);
        }
    }

    @Override
    public MgZbRequireStatus getByRequirementSn(String requirementSn) {
        return mongoTemplate.findById(requirementSn, MgZbRequireStatus.class);
    }

    public static void main(String[] args) throws Exception{
//        Class clazz = Class.forName("com.itcast.day26.Person");
        //Field field = clazz.getDeclaredField("name");
        Class clazz = MgZbRequireStatus.class;
        MgZbRequireStatus obj = (MgZbRequireStatus) clazz.newInstance();
        PropertyDescriptor pd = new PropertyDescriptor("requirementNum", clazz);
        Method setMethod = pd.getWriteMethod();
        setMethod.invoke(obj, "111111");
        System.out.println(obj.getRequirementNum());
    }
}
