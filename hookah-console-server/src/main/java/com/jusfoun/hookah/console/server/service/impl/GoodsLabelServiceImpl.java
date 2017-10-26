package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsLabelMapper;
import com.jusfoun.hookah.core.domain.GoodsLabel;
import com.jusfoun.hookah.core.domain.vo.GoodsLabelVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.PinyinUtils;
import com.jusfoun.hookah.rpc.api.GoodsLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签管理
 * Created by wangjl on 2017-10-24.
 */
@Service
public class GoodsLabelServiceImpl extends GenericServiceImpl<GoodsLabel, String> implements GoodsLabelService {

    @Resource
    private GoodsLabelMapper goodsLabelMapper;

    @Resource
    public void setDao(GoodsLabelMapper goodsLabelMapper) {
        super.setDao(goodsLabelMapper);
    }

    @Override
    public void createLabels(String labels, String userId, String labType) throws Exception {
        String[] labelsArray = labels.split(",");
        List<GoodsLabel> list = new ArrayList<>();
        for(String label : labelsArray) {
            GoodsLabel goodsLabel = new GoodsLabel();
            goodsLabel.setLabName(label);
            goodsLabel.setLabPy(PinyinUtils.getFirstSpell(label));
            goodsLabel.setLabAllPy(PinyinUtils.getFullSpell(label));
            goodsLabel.setUserId(userId);
            goodsLabel.setLabType(labType);
            list.add(goodsLabel);
        }
        super.insertBatch(list);
    }

    @Override
    public void deleteLabel(String labId) {
        super.delete(labId);
    }

    @Override
    public List<GoodsLabelVo> queryAllLabels() {
        List<GoodsLabel> list = goodsLabelMapper.getAllGoodsLabel();
        List<GoodsLabelVo> listVo = new ArrayList<>();
        String[] types = {"A-E", "F-J", "K-O", "P-T", "U-Z"};
        //数据组装
        for(String type : types) {
            GoodsLabelVo vo = new GoodsLabelVo();
            vo.setType(type);
            for(GoodsLabel goodsLabel : list) {
                if(type.equals(goodsLabel.getLabType())) {
                    if(vo.getGoodsLabels() == null) {
                        List<GoodsLabel> list1 = new ArrayList<>();
                        vo.setGoodsLabels(list1);
                    }
                    vo.getGoodsLabels().add(goodsLabel);
                }
            }
            listVo.add(vo);
        }
        return listVo;
    }
}
