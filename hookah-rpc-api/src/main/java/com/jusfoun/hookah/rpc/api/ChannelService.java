package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.ChannelTransData;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsCheckedVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;
import java.util.Map;

/**
 * @author ctp
 * @date 2017/8/29 下午3:06
 * @desc
 */
public interface ChannelService extends GenericService<Goods,String> {
    public ReturnData acceptGoods(ChannelTransData channelTransData);
}
