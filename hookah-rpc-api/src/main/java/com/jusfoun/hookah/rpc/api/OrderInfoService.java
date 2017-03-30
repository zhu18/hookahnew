package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.generic.OrderBy;

import java.util.List;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface OrderInfoService extends GenericService<OrderInfo,String> {

    public OrderInfo insert(OrderInfo orderInfo, String cartIds) throws Exception;

    public void updatePayStatus(String orderSn, Integer status) throws Exception;

    public Pagination<OrderInfoVo> getDetailListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                 List<OrderBy> orderBys);

}
