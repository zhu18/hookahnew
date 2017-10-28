package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.GoodsStorageMapper;
import com.jusfoun.hookah.core.domain.GoodsLabel;
import com.jusfoun.hookah.core.domain.GoodsStorage;
import com.jusfoun.hookah.core.domain.vo.GoodsStorageVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.rpc.api.GoodsStorageService;
import com.jusfoun.hookah.rpc.api.MgGoodsStorageService;
import org.springframework.beans.BeanUtils;
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
    private MgGoodsStorageService mgGoodsStorageService;

    @Resource
    public void setDao(GoodsStorageMapper goodsStorageMapper) {
        super.setDao(goodsStorageMapper);
    }

    public int insertSelective(GoodsStorage goodsStorage) {
        return goodsStorageMapper.insertSelective(goodsStorage);
    }

    /**
     * 前台查询货架详情
     * @return
     */
    @Override
    public List<GoodsStorageVo> getGoodsStorageList() {
        List<GoodsStorageVo> vos = new ArrayList<>();
        //查询货架列表
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("isOpen", 1));
        List<OrderBy> orderBys = new ArrayList<>();
        orderBys.add(OrderBy.asc("stOrder"));
        List<GoodsStorage> list = super.selectList(filters, orderBys);
        //查询mongo里货架配置信息，并拼装数据
        if(list != null && list.size() > 0) {
            for (GoodsStorage goodsStorage : list) {
                GoodsStorageVo vo = new GoodsStorageVo();
                BeanUtils.copyProperties(goodsStorage, vo);
                vo.setMgGoodsStorageVo(mgGoodsStorageService.findDetail(goodsStorage.getId()));
                vos.add(vo);
            }
        }
        return vos;
    }

    @Override
    public Pagination searchByLabels(String storageId, String typeId, String labels, List<GoodsLabel> goodsLabels) {
        //查询st
        return null;
    }

}