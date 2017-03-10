package com.jusfoun.hookah.core.generic;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

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
public interface GenericDao<Model extends GenericModel> extends Mapper<Model>, MySqlMapper<Model> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错

    
    /**
     * 批量插入对象
     * @param list 对象列表
     */
    int insertBatchSelective(List<Model> list);
}

