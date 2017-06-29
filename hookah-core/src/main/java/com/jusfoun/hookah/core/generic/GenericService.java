package com.jusfoun.hookah.core.generic;


import com.jusfoun.hookah.core.common.Pagination;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 所有自定义Service的顶级接口,封装常用的增删查改操作
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 * @author ShaoJianshuang
 * @since 2015年4月3日 下午18:02:45
 */
public interface GenericService<Model extends GenericModel, ID extends Serializable> {

    /**
     * 插入对象
     *
     * @param model 对象
     */
    Model insert(Model model);
    
    /**
     * 批量插入对象
     * @param list 对象列表
     */
    int insertBatch(List<Model> list);
    
    /**
     * 对象非空属性更新
     *
     * @param model 对象
     */
    int updateByIdSelective(Model model);
    
    /**
     * 按条件过滤 非空属性更新
     * @param filters
     * @return
     */
    int updateByConditionSelective(Model model, List<Condition> filters);
    
    /**
     * 按条件更新
     * @param filters
     * @return
     */
    int updateByCondition(Model model, List<Condition> filters);
    /**
     * 按主键更新
     * @param model
     * @return
     */
    int updateById(Model model);

    /**
     * 删除对象
     *
     * @param model 主键
     */
    int delete(Model model);
    
    
    /**
     * 通过主键，删除对象
     *
     * @param id 主键
     */
    int delete(ID id);
    
    /**
     * 通过多个主键, 删除对象
     *
     * @param ids 主键
     */
    int delete(ID... ids);
    
    /**
     * 通过条件, 删除对象
     *
     * @param filters 主键
     */
    int deleteByCondtion(List<Condition> filters);
    
    /**
	 * 判断实体对象是否存在
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象是否存在
	 */
    boolean exists(ID id);
    
    /**
	 * 判断实体对象是否存在
	 * 
	 * @param filters
	 *            筛选
	 * @return 实体对象是否存在
	 */
	boolean exists(List<Condition> filters);

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return model 对象
     */
	Model selectById(ID id);


    /**
     * 查询单个对象
     *
     * @return 对象
     */
    Model selectOne(List<Condition> filters);

    /**
     * 查询全部记录
     *
     * @return 对象集合
     */
    List<Model> selectList();

    /**
     * 查询多个对象
     *
     * @return 对象集合
     */
    List<Model> selectList(Model model);

    /**
     * 查询多个对象
     *
     * @return 对象集合
     */
    List<Model> selectList(List<Condition> filters);
    
    /**
     * 查询多个对象，有排序
     *
     * @return 对象集合
     */
    List<Model> selectList(List<Condition> filters, List<OrderBy> orderBys);
    
    
    /**
     * 查询分页，有排序
     *
     * @return 对象集合，带分页
     */
    Pagination<Model> getListInPage(Integer pageNum, Integer pageSize, List<Condition> filters, List<OrderBy> orderBys);

	/**
	 * 查询实体对象数量
	 * 
	 * @param filters
	 *            筛选
	 * @return 实体对象数量
	 */
	long count(List<Condition> filters);

    /**
     * 查询分页，有排序，有按照日期区间查询
     *
     * @return 对象集合，带分页
     */
    Pagination<Model> getSoldOrderList(Integer pageNum, Integer pageSize, List<Condition> filters, List<OrderBy> orderBys, Date startTime, Date endTime);
}
