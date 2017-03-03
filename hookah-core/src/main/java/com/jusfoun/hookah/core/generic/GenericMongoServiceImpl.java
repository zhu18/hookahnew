package com.jusfoun.hookah.core.generic;

import com.jusfoun.hookah.core.common.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author:jsshao
 * @date: 2017-3-2
 */
public class GenericMongoServiceImpl<Model extends GenericModel, ID extends Serializable> implements GenericService<Model, ID> {
    protected final static Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public int insert(Model model) {
        mongoTemplate.insert(model);
        return 0;
    }

    @Override
    public int insertBatch(List<Model> list) {
        return 0;
    }

    @Override
    public int updateByIdSelective(Model model) {
        return 0;
    }

    @Override
    public int updateByConditionSelective(Model model, List<Condition> filters) {
        return 0;
    }

    @Override
    public int updateByCondition(Model model, List<Condition> filters) {
        return 0;
    }

    @Override
    public int updateById(Model model) {
        return 0;
    }

    @Override
    public int delete(Model model) {
        return 0;
    }

    @Override
    public int delete(ID id) {
        return 0;
    }

    @Override
    public int delete(ID... ids) {
        return 0;
    }

    @Override
    public int deleteByCondtion(List<Condition> filters) {
        return 0;
    }

    @Override
    public boolean exists(ID id) {
        return false;
    }

    @Override
    public boolean exists(List<Condition> filters) {
        return false;
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

        mongoTemplate.findOne(this.convertFilter2Query(filters),(Class)trueType);
        return null;
    }

    @Override
    public List<Model> selectList() {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        return mongoTemplate.findAll((Class)trueType);
    }

    @Override
    public List<Model> selectList(List<Condition> filters) {
        return null;
    }

    @Override
    public List<Model> selectList(List<Condition> filters, List<OrderBy> orderBys) {
        return null;
    }

    @Override
    public Pagination<Model> getListInPage(Integer pageNum, Integer pageSize, List<Condition> filters, List<OrderBy> orderBys) {
        return null;
    }

    @Override
    public long count(List<Condition> filters) {
        return 0;
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
                case NotIn:
                    criteria = Criteria.where(condition.getProperty()).nin(condition.getValue());
                    break;
                default:
                    logger.info("Condition:"+condition +" has not parsed!");
                    criteria = Criteria.where(condition.getProperty()).is(condition.getValue());
            }
            query.addCriteria(criteria);
        }
        return query;
    }
}
