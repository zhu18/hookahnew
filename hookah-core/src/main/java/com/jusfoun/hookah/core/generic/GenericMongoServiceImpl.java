package com.jusfoun.hookah.core.generic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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
    public Pagination<Model> getListInPage(Integer pageNum, Integer pageSize, List<Condition> filters, List<OrderBy> orderBys) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        PageHelper.startPage(pageNum, pageSize);
        Query query = this.convertFilter2Query(filters);
        query.skip(pageNum);
        query.limit(pageSize);
        logger.info("[Mongo Dao ]queryPage:{}({},{})" , query,pageNum,pageSize );
        Page<Model> page = (Page<Model>)this.mongoTemplate.find(query, (Class)trueType);
        Pagination<Model> pagination = new Pagination<Model>();
        pagination.setTotalItems(page.getTotal());
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
                        if(idAno!=null){
                            map.put("id",field.getName());
                            map.put("value",(String)field.get(model));
                        }
                    }
                    entityClass = entityClass.getSuperclass();
                }
            }
            if(map.isEmpty()){
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
        String idColumn = getIdFromModel(model).get("id");
        try {
            if (Objects.nonNull(model)) {
                //实现1
                PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(entityClass);
                for(PropertyDescriptor targetPd:targetPds){
                    if(targetPd.getName().equals(idColumn)) continue; //不修改id
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

                //实现2
//                while(entityClass!=Object.class){
//                    Field[] fields = entityClass.getDeclaredFields();
//                    for(Field field:fields){
//                        int modifier =  field.getModifiers();
//                        if(modifier>4){
//                            continue;
//                        }
//                        field.setAccessible(true);
//                        if(field.get(model)!=null){
//                            Class propertyType = (Class)field.getGenericType();
//                            if(BeanUtils.isSimpleValueType(propertyType)){ //简单类型
//                                update.set(field.getName(),field.get(model));
//                            }else if(propertyType.isArray()){  //数组
//                                logger.debug("{}是数组类型{}！",field.getName(),propertyType);
//                            }else if(Collection.class.isAssignableFrom(propertyType)){ //集合类
//                                logger.debug("{}是集合类型{}！",field.getName(),propertyType);
//                            }else if(Map.class.isAssignableFrom(propertyType)){ //Map 类
//                                logger.debug("{}是Map类型{}！",field.getName(),propertyType);
//                            }else{ //其他
//                                logger.debug("{}是其他类型{}！",field.getName(),propertyType);
//                            }
//                            update.set(field.getName(),convertParamType(propertyType,field.get(model)));
//                        }
//                        field.setAccessible(false);
//                    }
//                    entityClass = entityClass.getSuperclass();
//                }
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
}
