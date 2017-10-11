package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.service.MgZbRequireStatusService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class MgZbRequireStatusServiceImpl extends GenericMongoServiceImpl<MgZbRequireStatus, String> implements MgZbRequireStatusService {

    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public void setRequireStatusInfo(String requirementNum, String statusName, String statusValue) {

        try {
            MgZbRequireStatus mgZbRequireStatus = mongoTemplate.findById(requirementNum, MgZbRequireStatus.class);
            if(mgZbRequireStatus == null){

                PropertyDescriptor pd = new PropertyDescriptor(statusName, MgZbRequireStatus.class);
                Method set = pd.getWriteMethod();
                set.invoke(mgZbRequireStatus, statusValue);
                mgZbRequireStatus.setRequirementNum(requirementNum);

                mongoTemplate.insert(mgZbRequireStatus);
            }else{
                Query query = new Query(Criteria.where("_id").is(requirementNum));
                Update update = new Update();
                if(StringUtils.isNotBlank(statusValue)){
                    update.set(statusName, statusValue);
                }
                mongoTemplate.upsert(query, update, MgZbRequireStatus.class);
            }
        }catch (Exception e){

        }
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
