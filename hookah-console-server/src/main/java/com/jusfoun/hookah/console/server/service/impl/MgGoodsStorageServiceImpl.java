package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoodsStorage;
import com.jusfoun.hookah.core.domain.vo.MgGoodsStorageVo;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsStorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjl on 2017-3-20.
 */
public class MgGoodsStorageServiceImpl extends GenericMongoServiceImpl<MgGoodsStorage, String> implements MgGoodsStorageService {
    @Resource
    MongoTemplate mongoTemplate;
    @Resource
    GoodsService goodsService;

    @Override
    public void upsertDetails(MgGoodsStorage mgGoodsStorage) {
        mongoTemplate.save(mgGoodsStorage);
    }

    @Override
    public MgGoodsStorageVo findDetail(String storageId) {
        MgGoodsStorageVo vo = new MgGoodsStorageVo();
        MgGoodsStorage mgGoodsStorage = super.selectById(storageId);
        //数据为空时，前端要求特殊处理
        if(mgGoodsStorage == null || mgGoodsStorage.getTypeList() == null || mgGoodsStorage.getTypeList().size() == 0) {
            mgGoodsStorage = new MgGoodsStorage();
            List<MgGoodsStorage.LabelsType> typeList = new ArrayList<>();
            for(int i = 0; i < HookahConstants.HOME_LABELS_NUM; i++) {
                MgGoodsStorage.LabelsType type = new MgGoodsStorage.LabelsType();
                type.setTypeId(i + 1);
                typeList.add(type);
            }
            mgGoodsStorage.setTypeList(typeList);
        }
        BeanUtils.copyProperties(mgGoodsStorage, vo);
        //商品详情补充
        List<Goods> list = new ArrayList<>();
        if(StringUtils.isNotBlank(mgGoodsStorage.getGoodsIds())) {
            String[] goodsIds = mgGoodsStorage.getGoodsIds().split(",");
            List<Goods> list1 = goodsService.findGoodsByIds(goodsIds);
            StringBuilder newGoodsIds = new StringBuilder();
            if(list1 != null && list1.size() > 0) {
                //商品详情按顺序排放
                for (String goodsId : goodsIds) {
                    for(Goods goods : list1) {
                        if(goodsId.equals(goods.getGoodsId())) {
                            list.add(goods);
                            newGoodsIds.append(goodsId).append(",");
                            break;
                        }
                    }
                }
                //去除已下架或审核不通过的商品id
                if(goodsIds.length != list1.size()) {
                    mgGoodsStorage.setGoodsIds(newGoodsIds == null ? "" : newGoodsIds.toString());
                    mongoTemplate.save(mgGoodsStorage);
                }
            }
        }
        vo.setGoodsList(list);
        return vo;
    }

}
