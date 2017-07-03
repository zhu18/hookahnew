package com.jusfoun.hookah.core.generic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.JsonUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;


/**
 * GenericService的实现类, 其他的自定义 ServiceImpl, 继承自它,可以获得常用的增删查改操作,
 * 未实现的方法有 子类各自实现
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * ID :代表对象的主键类型
 *
 * @author ShaoJianshuang
 * @since 2015年4月3日 下午18:02:45
 */
public class GenericServiceImpl<Model extends GenericModel, ID extends Serializable> implements GenericService<Model, ID> {
    protected final static Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);

    private static final String RAWTYPES = "rawtypes";

    /**
     * baseDao
     */
    private GenericDao<Model> dao;

    public void setDao(GenericDao<Model> dao) {
        this.dao = dao;
    }

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Model selectById(ID id) {
        return dao.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Model selectOne(List<Condition> filters) {
        // TODO Auto-generated method stub
        logger.debug("class {}:method {}", this.getClass().getName(), "selectOne(List<Condition> filters)");
        List<Model> list = dao.selectByExample(convertFilter2Example(filters));
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Model> selectList() {
        // TODO Auto-generated method stub
        List<Model> list = dao.selectByExample(convertFilter2Example(null, null));
        return list;
    }

    @Override
    public List<Model> selectList(Model model) {
        // TODO Auto-generated method stub
        List<Model> list = dao.selectByExample(convertFilter2Example(convertModel2Condition(model), null));
        return list;
    }

    @Override
    public List<Model> selectList(List<Condition> filters, List<OrderBy> orderBys) {
        // TODO Auto-generated method stub
        List<Model> list = dao.selectByExample(convertFilter2Example(filters, orderBys));
        return list;
    }

    @Override
    public List<Model> selectList(List<Condition> filters) {
        // TODO Auto-generated method stub
        List<Model> list = dao.selectByExample(convertFilter2Example(filters));
        return list;
    }

    @Override
    public Pagination<Model> getListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                           List<OrderBy> orderBys) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        Page<Model> page = (Page<Model>) dao.selectByExample(convertFilter2Example(filters, orderBys));
        Pagination<Model> pagination = new Pagination<Model>();
        pagination.setTotalItems(page.getTotal());
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        return pagination;
    }


    /**
     * 插入对象
     *
     * @param model 对象
     */
    @Transactional(readOnly = false)
    @Override
    public Model insert(Model model) {
        logger.debug("class {}:method {}", this.getClass().getName(), "insert(Model model)");
        int result = dao.insertSelective(model);
        if(result==0){
            return null;
        }
        return model;
    }

    /**
     * 插入对象
     *
     * @param list 对象
     */
    @Transactional(readOnly = false)
    @Override
    public int insertBatch(List<Model> list) {
        logger.debug("class {}:method {}", this.getClass().getName(), "insertBatch(List<Model> list)");
        return dao.insertBatch(list);
    }

    /**
     * 更新对象
     *
     * @param model 对象
     */
    @Transactional(readOnly = false)
    @Override
    public int updateByIdSelective(Model model) {
        // TODO Auto-generated method stub
        return dao.updateByPrimaryKeySelective(model);
    }


    /**
     * 更新对象
     *
     * @param model 对象
     */
    @Transactional(readOnly = false)
    @Override
    public int updateByConditionSelective(Model model, List<Condition> filters) {
        // TODO Auto-generated method stub
        return dao.updateByExampleSelective(model, convertFilter2Example(filters));
    }

    /**
     * 更新对象
     *
     * @param model 对象
     */
    @Transactional(readOnly = false)
    @Override
    public int updateByCondition(Model model, List<Condition> filters) {
        // TODO Auto-generated method stub
        return dao.updateByExample(model, convertFilter2Example(filters));
    }

    /**
     * 更新对象
     *
     * @param model 对象
     */
    @Transactional(readOnly = false)
    @Override
    public int updateById(Model model) {
        // TODO Auto-generated method stub
        return dao.updateByPrimaryKey(model);
    }


    @Transactional(readOnly = true)
    @Override
    public long count(List<Condition> filters) {
        // TO DO
        logger.debug("class {}:method {}", this.getClass().getName(), "count(List<Condition> filters)");
        long count = dao.selectCountByExample(convertFilter2Example(filters));
        return count;
    }

    /**
     * 判断实体对象是否存在
     *
     * @param id ID
     * @return 实体对象是否存在
     */
    @Transactional(readOnly = true)
    @Override
    public boolean exists(ID id) {
        logger.debug("class {}:method {}", this.getClass().getName(), "exists(ID id)");
        boolean isExist = dao.existsWithPrimaryKey(id);

        return isExist;
    }

    /**
     * 判断实体对象是否存在
     *
     * @param filters 筛选
     * @return 实体对象是否存在
     */
    @Transactional(readOnly = true)
    @Override
    public boolean exists(List<Condition> filters) {
        logger.debug("class {}:method {}", this.getClass().getName(), "exists(List<Condition> filters)");
        List<Model> list = dao.selectByExample(convertFilter2Example(filters));
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 删除对象
     *
     * @param model 对象
     */
    @Transactional(readOnly = false)
    @Override
    public int delete(Model model) {
        // TODO Auto-generated method stub
        return dao.delete(model);
    }

    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    @Transactional(readOnly = false)
    @Override
    public int delete(ID id) {
        return dao.deleteByPrimaryKey(id);
    }



    @Transactional(readOnly = false)
    @Override
    public int delete(ID... ids) {
        // TODO Auto-generated method stub
        try {
            for (ID id : ids) {
                dao.deleteByPrimaryKey(id);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Transactional(readOnly = false)
    @Override
    public int deleteByCondtion(List<Condition> filters) {
        // TODO Auto-generated method stub
        try {
            return dao.deleteByExample(convertFilter2Example(filters));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
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
     * 根据Filter动态实例化Example类
     *
     * @param filters
     * @return
     */
    protected Object convertFilter2Example(List<Condition> filters) {
        return convertFilter2Example(filters, null);
    }

    /**
     * 根据Filter,和OrderBy 动态实例化Example类
     *
     * @param filters
     * @return
     */
    protected Object convertFilter2Example(List<Condition> filters, List<OrderBy> orderBys) {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        Class entityClass = (Class) trueType;

        Class exampleClass = Example.class;
        Example exampleInstance = new Example(entityClass);
        try {
            if (Objects.nonNull(filters) && !filters.isEmpty()) {
                Method criteriaMethod = exampleClass.getDeclaredMethod("createCriteria");
                Object criteria = criteriaMethod.invoke(exampleInstance);
                for (Condition filter : filters) {
                    //调用example的方法
                    criteria = transMethod(criteria, filter);
                }
            }
            if (Objects.nonNull(orderBys) && !orderBys.isEmpty()) {
                Method orderByMethod = exampleClass.getDeclaredMethod("setOrderByClause", String.class);
                StringBuffer sbuf = new StringBuffer();
                for (OrderBy orderBy : orderBys) {
                    sbuf.append(orderBy).append(",");
                }
                orderByMethod.invoke(exampleInstance, sbuf.deleteCharAt(sbuf.length() - 1).toString());
            }
            return exampleInstance;
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    /**
     * 动态调用Example类里的Criteria的构建条件方法
     */
    protected <C> C transMethod(C c, Condition filter) {
        Class clazz = c.getClass().getSuperclass();
        Method  method  =  null;
        boolean execute = false;
        try {
            switch (filter.getOperator()){
                case EqualTo:
                case NotEqualTo:
                case GreaterThan:
                case LessThan:
                case GreaterThanOrEqualTo:
                case LessThanOrEqualTo:
                    method =  clazz.getDeclaredMethod(filter.toString(),String.class,Object.class);
                    method.invoke(c,filter.getProperty(),filter.getValue());
                    execute = true;
                    break;
                case NotLike:
                case Like:
                    method =  clazz.getDeclaredMethod(filter.toString(),String.class,String.class);
                    method.invoke(c,filter.getProperty(), "%" + filter.getValue() + "%");
                    execute = true;
                    break;
                case In:
                case NotIn:
                    method =  clazz.getDeclaredMethod(filter.toString(),String.class,List.class);
                    method.invoke(c,filter.getProperty(),Arrays.asList((Object[])filter.getValue()));
                    execute = true;
                    break;
                case Between:
                case NotBetween:
                    method =  clazz.getDeclaredMethod(filter.toString(),String.class,Object.class,Object.class);
                    Object[] value= (Object[])filter.getValue();
                    if(value.length!=2){
                        logger.error("between参数个数不为2");
                    }
                    method.invoke(c,filter.getProperty(),value[0],value[1]);
                    execute = true;
                    break;
                case IsNull:
                case IsNotNull:
                    method =  clazz.getDeclaredMethod(filter.toString(),String.class);
                    method.invoke(c,filter.getProperty());
                    break;
                default:
                    logger.info("unknown method:"+filter.toString());
            }
            if (execute) {
                logger.debug("success to add condition:{}-{}",clazz.getName(),filter);
            } else {
                logger.error("fail to add condition:{}-{}" + clazz.getName() , filter);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return c;
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
     * 多值 类型转换
     *
     * @param clazzs
     * @param objs
     * @return
     */
    protected <T> T[] convertParamType(Class<T>[] clazzs, Object[] objs) {
        List<T> list = new LinkedList<T>();
        T[] result = null;
        for (int i = 0; i < objs.length; i++) {
            list.add(convertParamType(clazzs[i], objs[i]));
        }

        return list.toArray(result);
    }

    @SuppressWarnings({"unchecked", RAWTYPES})
    protected void copyProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object sourceValue = readMethod.invoke(source);
                        Object targetValue = readMethod.invoke(target);
                        if (sourceValue != null && targetValue != null && targetValue instanceof Collection) {
                            Collection collection = (Collection) targetValue;
                            collection.clear();
                            collection.addAll((Collection) sourceValue);
                        } else {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, sourceValue);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }

    }

    /**
     * 拼装排序字符串
     *
     * @param orderBys
     * @return
     */
    protected String getOrderBy(List<OrderBy> orderBys) {
        StringBuffer sbuf = new StringBuffer();
        if (Objects.nonNull(orderBys) && !orderBys.isEmpty()) {
            for (OrderBy orderBy : orderBys) {
                sbuf.append(orderBy).append(",");
            }
            sbuf = sbuf.deleteCharAt(sbuf.length() - 1);
        }
        return sbuf.toString();
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

    @Override
    public Pagination<Model> getSoldOrderList(Integer pageNum, Integer pageSize, List<Condition> filters,
                                              Date startTime, Date endTime){
        return new Pagination<Model>();
    }

//    @Override
//    public Pagination<Model> getUserListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
//                                               List<OrderBy> orderBys, Date startTime, Date endTime){
//        return new Pagination<Model>();
//    }
}
