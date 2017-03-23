package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class GoodsServiceImpl extends GenericServiceImpl<Goods, String> implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    public void setDao(GoodsMapper goodsMapper) {
        super.setDao(goodsMapper);
    }

    @Override
    @Transactional
    public void addGoods(GoodsVo obj) throws HookahException {
        if (obj == null)
            throw new HookahException("空数据！");
        // 将数据插入数据库
        obj = (GoodsVo)super.insert(obj);
        if(obj == null)
            throw new HookahException("操作失败");
        // 将数据放入mongo
            MgGoods mgGoods = new MgGoods();
            mgGoods.setAttrTypeList(obj.getAttrTypeList());
            mgGoods.setFormatList(obj.getFormatList());
            mgGoods.setImgList(obj.getImgList());
            mgGoods.setGoodsId(obj.getGoodsId());
            mongoTemplate.insert(mgGoods);
    }

    @Override
    @Transactional
    public void updateGoods(GoodsVo obj) throws HookahException {
        int i = super.updateByIdSelective(obj);
        if(i < 1) {
            throw new HookahException("更新失败！");
        }else {
            // 将数据放入mongo
            MgGoods mgGoods = new MgGoods();
            mgGoods.setAttrTypeList(obj.getAttrTypeList());
            mgGoods.setFormatList(obj.getFormatList());
            mgGoods.setImgList(obj.getImgList());
            mgGoods.setGoodsId(obj.getGoodsId());
            mongoTemplate.save(mgGoods);
        }
    }
}
