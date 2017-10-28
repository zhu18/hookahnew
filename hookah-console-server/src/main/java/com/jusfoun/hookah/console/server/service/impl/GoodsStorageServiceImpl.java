package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsStorageMapper;
import com.jusfoun.hookah.core.domain.GoodsStorage;
import com.jusfoun.hookah.core.domain.vo.GoodsStorageVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.rpc.api.GoodsStorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签管理
 * Created by wangjl on 2017-10-24.
 */
@Service
public class GoodsStorageServiceImpl extends GenericServiceImpl<GoodsStorage, String> implements GoodsStorageService {

    @Resource
    private GoodsStorageMapper goodsStorageMapper;

    @Resource
    public void setDao(GoodsStorageMapper goodsStorageMapper) {
        super.setDao(goodsStorageMapper);
    }

    public int insertSelective(GoodsStorage goodsStorage) {
        return goodsStorageMapper.insertSelective(goodsStorage);
    }

    @Override
    public List<GoodsStorageVo> getGoodsStorageList() {
        //查询货架列表
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("isOpen", 1));
        List<OrderBy> orderBys = new ArrayList<>();
        orderBys.add(OrderBy.asc("stOrder"));
        List<GoodsStorage> list = super.selectList(filters, orderBys);
        //查询mongo里货架配置信息
        if(list != null && list.size() > 0) {

        }
        //拼装数据
        return null;
    }

}
