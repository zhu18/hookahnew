package com.jusfoun.hookah.core.generic;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.JsonUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;


/**
 * @author:jsshao
 * @date: 2017-3-2
 */
public class GenericMongoServiceImpl<Model extends GenericModel, ID extends Serializable> implements GenericService<Model, ID> {
    protected final static Logger logger = LoggerFactory.getLogger(GenericMongoServiceImpl.class);

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Model insert(Model model) {
        mongoTemplate.insert(model);
        return model;
    }

    @Override
    public int insertBatch(List<Model> list) {
        mongoTemplate.insertAll(list);
        return list.size();
    }

    /**
     * 实现过程和 updateById一样
     * @param model 对象
     * @return
     */
    @Override
    public int updateByIdSelective(Model model) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Map<String,String> map = this.getIdFromModel(model);
        if(map==null){
            return -1;
        }
        Query query = new Query(Criteria.where("_id").is(map.get("value")));
        mongoTemplate.updateFirst(query,this.convertModel2Update(model),(Class)trueType);
        return 0;
    }

    @Override
    public int updateByConditionSelective(Model model, List<Condition> filters) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        mongoTemplate.updateMulti(this.convertFilter2Query(filters),convertModel2Update(model),(Class)trueType);
        return 0;
    }

    @Override
    public int updateByCondition(Model model, List<Condition> filters) {

        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        mongoTemplate.updateMulti(this.convertFilter2Query(filters),convertModel2Update(model),(Class)trueType);
        return 0;
    }

    @Override
    public int updateById(Model model) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Map<String,String> map = this.getIdFromModel(model);
        if(map==null){
            return -1;
        }
        Query query = new Query(Criteria.where(map.get("id")).is(map.get("value")));
        mongoTemplate.updateFirst(query,this.convertModel2Update(model),(Class)trueType);
        return 0;
    }

    @Override
    public int delete(Model model) {
        try{
            mongoTemplate.remove(model);
        }catch(Exception e){
            return -1;
        }
        return 0;
    }

    @Override
    public int delete(ID id) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,(Class)trueType);
        return 0;
    }

    @Override
    public int delete(ID... ids) {

        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Query query = new Query(Criteria.where("_id").in(ids));
        mongoTemplate.remove(query,(Class)trueType);
        return 0;
    }

    @Override
    public int deleteByCondtion(List<Condition> filters) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        mongoTemplate.remove(this.convertFilter2Query(filters),(Class)trueType);
        return 0;
    }

    @Override
    public boolean exists(ID id) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        return mongoTemplate.findById(id,(Class) trueType)==null;

    }

    @Override
    public boolean exists(List<Condition> filters) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        return mongoTemplate.findOne(this.convertFilter2Query(filters),(Class)trueType)==null;

    }

    @Override
    public Model selectById(ID id) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        return (Model) mongoTemplate.findById(id,(Class) trueType);
    }

    @Override
    public Model selectOne(List<Condition> filters) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        return (Model)mongoTemplate.findOne(this.convertFilter2Query(filters),(Class)trueType);
    }

    @Override
    public List<Model> selectList() {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        return mongoTemplate.findAll((Class)trueType);
    }

    @Override
    public List<Model> selectList(Model model) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        return mongoTemplate.find(this.convertFilter2Query(convertModel2Condition(model)),(Class)trueType);
    }

    @Override
    public List<Model> selectList(List<Condition> filters) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        return mongoTemplate.find(this.convertFilter2Query(filters),(Class)trueType);
    }

    @Override
    public List<Model> selectList(List<Condition> filters, List<OrderBy> orderBys) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        return mongoTemplate.find(this.convertFilter2Query(filters),(Class)trueType);
    }

    @Override
    public Pagination<Model> getListInPage(Integer pageNum, Integer pageSize, List<Condition> filters, List<OrderBy> orderBys){
        return new Pagination<Model>();
    }

    /**
     * 新增价格区间查询
     * @param pageNum
     * @param pageSize
     * @param filters
     * @param sorts
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Pagination<Model> getListInPageFromMongo(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                    List<Sort> sorts, Date startTime, Date endTime,
                                                    Long startMoney, Long endMoney) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Query query = this.convertFilter2Query(filters);
        if (startTime!=null && endTime!=null){
            query.addCriteria(Criteria.where("addTime").gte(startTime).lt(endTime));
        }else if (startTime==null && endTime!=null){
            query.addCriteria(Criteria.where("addTime").lt(endTime));
        }else if (startTime!=null && endTime==null){
            query.addCriteria(Criteria.where("addTime").gte(startTime));
        }

        if(startMoney > 0 && endMoney > 0){
            if(startMoney < endMoney){
                query.addCriteria(Criteria.where("orderAmount").gte(startMoney).lte(endMoney));
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "orderAmount")));
            }else if(startMoney > endMoney){
                query.addCriteria(Criteria.where("orderAmount").gte(endMoney).lte(startMoney));
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "orderAmount")));
            }else {
                query.addCriteria(Criteria.where("orderAmount").is(startMoney));
            }
        }else if(startMoney > 0 && endMoney == 0){
            query.addCriteria(Criteria.where("orderAmount").is(startMoney));
        }else if(startMoney == 0 && endMoney > 0){
            query.addCriteria(Criteria.where("orderAmount").is(endMoney));
        }

        long list = this.mongoTemplate.count(query, (Class)trueType);
        if (sorts!=null&&sorts.size()!=0)
            for (Sort sort:sorts){
                query.with(sort);
            };
        query.skip((pageNum-1)*pageSize);
        query.limit(pageSize);
        logger.info("[Mongo Dao ]queryPage:{}({},{})" , query,pageNum,pageSize );
        List<Model> page = this.mongoTemplate.find(query, (Class)trueType);
        Pagination<Model> pagination = new Pagination<Model>();
        pagination.setTotalItems(list);
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        return pagination;
    }

    @Override
    public Pagination<Model> getListInPageFromMongo(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                    List<Sort> sorts, Date startTime, Date endTime) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Query query = this.convertFilter2Query(filters);
        Criteria criteria = null;
        if (startTime!=null && endTime!=null){
            criteria = Criteria.where("addTime").gte(startTime).lt(endTime);
            query.addCriteria(criteria);
        }else if (startTime==null && endTime!=null){
            criteria = Criteria.where("addTime").lt(endTime);
            query.addCriteria(criteria);
        }else if (startTime!=null && endTime==null){
            criteria = Criteria.where("addTime").gte(startTime);
            query.addCriteria(criteria);
        }
        long list = this.mongoTemplate.count(query, (Class)trueType);
        if (sorts!=null&&sorts.size()!=0)
            for (Sort sort:sorts){
                query.with(sort);
            };
        query.skip((pageNum-1)*pageSize);
        query.limit(pageSize);
        logger.info("[Mongo Dao ]queryPage:{}({},{})" , query,pageNum,pageSize );
        List<Model> page = this.mongoTemplate.find(query, (Class)trueType);
        Pagination<Model> pagination = new Pagination<Model>();
        pagination.setTotalItems(list);
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        return pagination;
    }

    @Override
    public Pagination<Model> getSoldOrderList(Integer pageNum, Integer pageSize, List<Condition> filters,
                                              Date startTime, Date endTime) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Query query = new Query();
        query = this.convertFilter2Query(filters);
        Criteria criteria = null;
        if (startTime!=null && endTime!=null){
            criteria = Criteria.where("addTime").gte(startTime).lt(endTime);
            query.addCriteria(criteria);
        }else if (startTime==null && endTime!=null){
            criteria = Criteria.where("addTime").lt(endTime);
            query.addCriteria(criteria);
        }else if (startTime!=null && endTime==null){
            criteria = Criteria.where("addTime").gte(startTime);
            query.addCriteria(criteria);
        }
        long list = this.mongoTemplate.count(query, (Class)trueType);
        query.with(new Sort(Sort.Direction.DESC, "addTime"));
        query.skip((pageNum-1)*pageSize);
        query.limit(pageSize);
        logger.info("[Mongo Dao ]queryPage:{}({},{})" , query,pageNum,pageSize );
        List<Model> page = this.mongoTemplate.find(query, (Class)trueType);
        Pagination<Model> pagination = new Pagination<Model>();
        pagination.setTotalItems(list);
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        return pagination;
    }

    @Override
    public long count(List<Condition> filters) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        List list =  mongoTemplate.find(this.convertFilter2Query(filters),(Class)trueType);
        if(list==null) return 0;
        return list.size();
    }

    protected Query convertFilter2Query(List<Condition> filters){
        Query query = new Query();
        for(Condition condition:filters){
            Criteria criteria = null;
            switch(condition.getOperator()){
                case EqualTo:
                    criteria = Criteria.where(condition.getProperty()).is(condition.getValue());
                    break;
                case NotEqualTo:
                    criteria = Criteria.where(condition.getProperty()).ne(condition.getValue());
                    break;
                case GreaterThan:
                    criteria = Criteria.where(condition.getProperty()).gt(condition.getValue());
                    break;
                case GreaterThanOrEqualTo:
                    criteria = Criteria.where(condition.getProperty()).gte(condition.getValue());
                    break;
                case LessThan:
                    criteria = Criteria.where(condition.getProperty()).lt(condition.getValue());
                    break;
                case LessThanOrEqualTo:
                    criteria = Criteria.where(condition.getProperty()).lte(condition.getValue());
                    break;
                case Like:
                    criteria = Criteria.where(condition.getProperty()).regex((String)condition.getValue());
                    break;
                case NotLike:
                    criteria = Criteria.where(condition.getProperty()).is(condition.getValue());
                    break;
                case In:
                    criteria = Criteria.where(condition.getProperty()).in(condition.getValue());
                    break;
                case IsNull:
                    criteria = Criteria.where(condition.getProperty()).exists(false);
                    break;
                case IsNotNull:
                    criteria = Criteria.where(condition.getProperty()).exists(true);
                    break;
                case NotIn:
                    criteria = Criteria.where(condition.getProperty()).nin(condition.getValue());
                    break;
                default:
                    logger.error("Condition:{} has not parsed!",condition);
                    criteria = Criteria.where(condition.getProperty()).is(condition.getValue());
            }
            query.addCriteria(criteria);
        }
        return query;
    }

    /**
     * 根据Model 动态生成查询条件列表
     *
     * @param model
     * @return
     */
    protected List<Condition> convertModel2Condition(Model model) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Class entityClass = (Class) trueType;
        List<Condition> filters = new ArrayList<>();
        try {
            if (Objects.nonNull(model)) {
                while(entityClass!=Object.class){
                    Field[] fields = entityClass.getDeclaredFields();
                    for(Field field:fields){
                        field.setAccessible(true);
                        if(field.get(model)!=null){
                            filters.add(Condition.eq(field.getName(),convertParamType((Class)field.getGenericType(),field.get(model))));
                        }
                        field.setAccessible(false);
                    }
                    entityClass = entityClass.getSuperclass();
                }
            }
            return filters;
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据Model 动态生成查询条件列表
     *
     * @param model
     * @return
     */
    protected Map<String,String> getIdFromModel(Model model) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Class entityClass = (Class) trueType;
        Map<String,String> map = new HashMap(2);
        try {
            if (Objects.nonNull(model)) {
                while(entityClass!=Object.class){
                    Field[] fields = entityClass.getDeclaredFields();
                    for(Field field:fields){
                        field.setAccessible(true);
                        /*field.setAccessible(true);
                        if(field.get(model)!=null){
                            filters.add(Condition.eq(field.getName(),convertParamType((Class)field.getGenericType(),field.get(model))));
                        }
                        field.setAccessible(false);*/
                        Id idAno = field.getAnnotation(Id.class);
                        if(idAno!=null &&  field.get(model)!=null){
                            map.put("id",field.getName());
                            map.put("value",(String)field.get(model));
                        }
                    }
                    entityClass = entityClass.getSuperclass();
                }
            }
            if(map.isEmpty()){
                logger.info("没有主键字段或者主键字段值为NULL!");
                return null;
            }
            return map;
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据Model 动态生成查询条件列表,暂时不支持内嵌对象的更新
     *
     * @param model
     * @return
     */
    protected Update convertModel2Update(Model model) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Class entityClass = (Class) trueType;
        Update update = new Update();
        Map map = getIdFromModel(model);

        try {
            if (Objects.nonNull(model)) {
                //实现1
                PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(entityClass);
                for(PropertyDescriptor targetPd:targetPds){
                    if(map!=null){
                        if(targetPd.getName().equals((String)map.get("id"))) continue; //不修改id
                    }

                    Method readMethod = targetPd.getReadMethod();
                    int readModifier  = readMethod.getDeclaringClass().getModifiers();
                    if(!targetPd.getName().equals("class")){  //只有public private protected
                        if (!Modifier.isPublic(readModifier)) {
                            readMethod.setAccessible(true);
                        }
                        Object propertyValue = readMethod.invoke(model);
                        if(propertyValue!=null){
                            Class propertyType = targetPd.getPropertyType();
                            if(BeanUtils.isSimpleValueType(propertyType)){ //简单类型
                                update.set(targetPd.getName(),propertyValue);
                            }else if(propertyType.isArray()){  //数组
                                logger.debug("{}是数组类型{}！",targetPd.getName(),propertyType);
                            }else if(Collection.class.isAssignableFrom(propertyType)){ //集合类
                                logger.debug("{}是集合类型{}！",targetPd.getName(),propertyType);
                            }else if(Map.class.isAssignableFrom(propertyType)){ //Map 类
                                logger.debug("{}是Map类型{}！",targetPd.getName(),propertyType);
                            }else{ //其他
                                logger.debug("{}是其他类型{}！",targetPd.getName(),propertyType);
                            }
                        }
                        if (!Modifier.isPublic(readModifier)) {
                            readMethod.setAccessible(false);
                        }
                    }

                }
            }
            return update;
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    /**
     * 单值 类型转换
     *
     * @param clazz
     * @param obj
     * @return
     */
    protected <T> T convertParamType(Class<T> clazz, Object obj) {
        if (clazz == List.class && obj instanceof Object[]) {
            return (T) Arrays.asList(((Object[]) obj));
        }
        return (T) ConvertUtils.convert(obj, clazz);
    }

    /**
     * 获取当前用户
     * @return
     * @throws HookahException
     */
    protected User getCurrentUser() throws HookahException {
        Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
        if(userMap==null){
            throw  new HookahException("没有登录用户信息");
        }
        User user = new User();
        try {
            org.apache.commons.beanutils.BeanUtils.populate(user,userMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new HookahException("获取用户信息出错！",e);
        }
        logger.info(JsonUtils.toJson(user));
        return user;
    }
}
