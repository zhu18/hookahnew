package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.SysNewsMapper;

import com.jusfoun.hookah.core.domain.SysNews;
import com.jusfoun.hookah.core.domain.vo.SysNewsVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;

import com.jusfoun.hookah.rpc.api.SysNewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新闻中心
 */
@Service
public class SysNewsServiceImpl extends GenericServiceImpl<SysNews, String> implements SysNewsService {
    @Resource
    private SysNewsMapper sysNewsMapper;


    @Resource
    public void setDao(SysNewsMapper sysNewsMapper) {
        super.setDao(sysNewsMapper);
    }

    @Override
    public Map<String, Object> getNewsList(String group, String sonGroup, int pageSizeNew) {
        List<SysNewsVo> list=sysNewsMapper.selectByGroup(group,sonGroup);
        List<List<SysNewsVo>> ret=split(list,pageSizeNew);
        Map<String,Object> res=new HashMap<String,Object>();
        res.put("dataList", ret);
        res.put("totalCount", ret.size());
        res.put("totalItem", list.size());
        return res;
    }

    @Override
    public SysNewsVo selectNewsByID(String newId) {
        List<SysNewsVo> list=sysNewsMapper.selectNewsByID(newId);
        SysNewsVo  vo = new SysNewsVo();
        if(list.size()>0){
             vo = list.get(0);
        }
        return vo;
    }


    //截取list方法
    public static  <T> List<List<T>> split(List<T> resList,int count){

        if(resList==null ||count<1)
            return  null ;
        List<List<T>> ret=new ArrayList<List<T>>();
        int size=resList.size();
        if(size<=count){
            ret.add(resList);
        }else{
            int pre=size/count;
            int last=size%count;
            for(int i=0;i<pre;i++){
                List<T> itemList=new ArrayList<T>();
                for(int j=0;j<count;j++){
                    itemList.add(resList.get(i*count+j));
                }
                ret.add(itemList);
            }

            if(last>0){
                List<T> itemList=new ArrayList<T>();
                for(int i=0;i<last;i++){
                    itemList.add(resList.get(pre*count+i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }

}
