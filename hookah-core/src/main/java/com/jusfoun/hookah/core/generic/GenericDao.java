package com.jusfoun.hookah.core.generic;

import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 所有自定义Dao的顶级接口, 封装常用的增删查改操作,
 * 可以通过Mybatis Generator Maven 插件自动生成Dao,
 * 也可以手动编码,然后继承GenericDao 即可.
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * ID :代表对象的主键类型
 *
 * @author ShaoJianshuang
 * @since 2015年4月3日 下午18:02:45
 */
public interface GenericDao<Model extends GenericModel> {

    /**
     * 插入对象
     *
     * @param model 对象
     */
    int insertSelective(Model model);
    
    /**
     * 批量插入对象
     * @param list 对象列表
     */
    int insertBatch(List<Model> list);

    /**
     * 更新对象
     *
     * @param model 对象
     */
    int updateByPrimaryKeySelective(Model model);

    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    <ID> int deleteByPrimaryKey(ID id);
    
    /**
     * 通过主键, 删除对象
     *
     * @param model 主键
     */
    <ID> int delete(Model model);
    /**
     * 通过条件，删除对象
     * @param example
     * @return
     */
    <E> int deleteByExample(E example);

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return
     */
    <ID>Model selectByPrimaryKey(ID id);
    
    <E> List<Model> selectByExample(E example);

    <E> int updateByExampleSelective(@Param("record") Model record, @Param("example") E example);

    <E> int updateByExample(@Param("record") Model record, @Param("example") E example);

    int updateByPrimaryKey(Model record);
    
    <E> int countByExample(E example);
}
