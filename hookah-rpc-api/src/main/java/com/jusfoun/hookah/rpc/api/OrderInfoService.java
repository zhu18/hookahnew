package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface OrderInfoService extends GenericService<OrderInfoVo,String> {

    public OrderInfoVo insert(OrderInfoVo orderInfo, String cartIds) throws Exception;

    public void updatePayStatus(String orderSn, Integer status) throws Exception;

}
