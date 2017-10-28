package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.console.server.util.DictionaryUtil;
import com.jusfoun.hookah.core.dao.GoodsStorageMapper;
import com.jusfoun.hookah.core.domain.GoodsLabel;
import com.jusfoun.hookah.core.domain.GoodsStorage;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.domain.mongo.MgGoodsStorage;
import com.jusfoun.hookah.core.domain.vo.EsGoodsVo;
import com.jusfoun.hookah.core.domain.vo.GoodsLabelsPagVo;
import com.jusfoun.hookah.core.domain.vo.GoodsStorageVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import com.jusfoun.hookah.rpc.api.GoodsStorageService;
import com.jusfoun.hookah.rpc.api.MgGoodsStorageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ElasticSearchService elasticSearchService;

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
    public GoodsLabelsPagVo searchByLabels(String storageId, int typeId, String labels, Integer currentPage, Integer pageSize) throws Exception {
        GoodsLabelsPagVo vo = new GoodsLabelsPagVo();
        //拼装标签
        MgGoodsStorage mgGoodsStorage = mgGoodsStorageService.selectById(storageId);
        List<MgGoodsStorage.LabelsType> list = mgGoodsStorage.getTypeList();
        for (MgGoodsStorage.LabelsType type : list) {
            if(typeId == type.getTypeId() && StringUtils.isNotBlank(type.getLabels())) {
                if(StringUtils.isBlank(labels)) {
                    labels = type.getLabels();
                }
                List<GoodsLabel> goodsLabels = new ArrayList<>();
                for(String label : type.getLabels().split(",")) {
                    GoodsLabel goodsLabel = new GoodsLabel();
                    goodsLabel.setLabId(label);
                    goodsLabel.setLabName(DictionaryUtil.getGoodsLabelById(label) == null
                            ? "" : DictionaryUtil.getGoodsLabelById(label).getLabName());
                    goodsLabels.add(goodsLabel);
                }
                vo.setGoodsLabelList(goodsLabels);
                break;
            }
        }
        //es搜索
        EsGoodsVo vo1 = new EsGoodsVo();
        if(pageSize != null)
            vo1.setPageSize(pageSize);
        if(currentPage != null)
            vo1.setPageNumber(currentPage);
        EsGoods esGoods = new EsGoods();
        esGoods.setKeywordsArrays(labels.replace(",", " "));
        vo1.setEsGoods(esGoods);
        vo.setPagination(elasticSearchService.search(vo1));
        return vo;
    }

}
