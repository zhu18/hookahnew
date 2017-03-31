package com.jusfoun.hookah.rpc.api;


import com.jusfoun.hookah.core.domain.SysNews;
import com.jusfoun.hookah.core.domain.vo.SysNewsVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.Map;

/**
 * 资讯中心
 */
public interface SysNewsService extends GenericService<SysNews,String> {


    /**
     * 新闻分页信息
     * @param group 分组信息
     * @param sonGroup 子分组信息
     * @param pageSizeNew 每页条数
     * @return
     */
    public Map<String, Object> getNewsList(String group, String sonGroup,int pageSizeNew);

    /**
     *
     * @param newId
     * @return
     */
    public SysNewsVo selectNewsByID(String newId);

}
