package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.GoodsCheckMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.GoodsCheckService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by admin on 2017/4/7/0007.
 */
@Service
public class GoodsCheckServiceImpl extends GenericServiceImpl<GoodsCheck, String> implements GoodsCheckService {

    @Resource
    GoodsCheckMapper goodsCheckMapper;

    @Resource
    public void setDao(GoodsCheckMapper goodsCheckMapper) {
        super.setDao(goodsCheckMapper);
    }

    @Resource
    GoodsService goodsService;

    @Override
    public void insertRecord(GoodsCheck goodsCheck) throws HookahException {
        if (goodsCheck == null)
            throw new HookahException("空数据！");

        goodsCheck = super.insert(goodsCheck);

        if(goodsCheck == null)
            throw new HookahException("操作失败");

        Goods goods = new Goods();
        goods.setGoodsId(goodsCheck.getGoodsId());
        if(goodsCheck.getCheckStatus() == 1){
            goods.setCheckStatus(Byte.parseByte(HookahConstants.CheckStatus.audit_success.getCode()));
            goodsService.updateByIdSelective(goods);
        }else if(goodsCheck.getCheckStatus() == 2){
            goods.setCheckStatus(Byte.parseByte(HookahConstants.CheckStatus.audit_fail.getCode()));
            goodsService.updateByIdSelective(goods);
        }
    }
}
