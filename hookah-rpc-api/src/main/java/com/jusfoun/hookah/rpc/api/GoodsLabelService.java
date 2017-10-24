package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.GoodsLabel;
import com.jusfoun.hookah.core.domain.vo.GoodsLabelVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * 标签管理
 * Created by wangjl on 2017-10-24.
 */
public interface GoodsLabelService extends GenericService<GoodsLabel,String> {
    /**
     * 标签创建
     * @param labels
     */
    void createLabels(String labels, String userId, String labType) throws Exception;

    /**
     * 标签删除
     * @param labId
     */
    void deleteLabel(String labId) throws Exception;

    /**
     * 查询所有标签
     * @return
     */
    List<GoodsLabelVo> queryAllLabels();
}
