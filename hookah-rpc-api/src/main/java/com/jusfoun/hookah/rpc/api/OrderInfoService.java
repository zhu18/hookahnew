package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.generic.OrderBy;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface OrderInfoService extends GenericService<OrderInfo,String> {

    public OrderInfo insert(OrderInfo orderInfo, String[] cartIdArray) throws Exception;

    public OrderInfo insert(OrderInfo orderInfo, String goodsId, Integer formatId,Long goodsNumber) throws Exception;

    public void updatePayStatus(String orderSn, Integer payStatus, Integer payMode) throws Exception;

    public Pagination<OrderInfoVo> getDetailListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                 List<OrderBy> orderBys);

    public Pagination<OrderInfoVo> getSoldOrderListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                          Byte goodsType, Date startTime, Date endTime, String userId);

    Pagination<OrderInfoVo> getSoldOrderListByCondition(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                    Date startTime, Date endTime, String addUser);

    public Pagination<MgOrderGoods> getGoodsListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                       List<OrderBy> orderBys);

    public Pagination<OrderInfoVo> getUserListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                     Date startTime, Date endTime);

    /**
     * 根据订单号查询支付信息
     * @param orderId
     * @return
     */
    public PayVo getPayParam(String orderId);

    public OrderInfoVo findDetailById(String orderId) throws HookahException;

    void deleteByLogic(String id);

    void deleteBatchByLogic(String[] ids);

    public Map<String,Long> getOrderStatisticWithBuydate(Date startTime,Date endTime) throws HookahException;

    public MgOrderGoods getGoodsUserBuyed(String userId,String goodsId);

    Map<String,Integer> getOrderCount();

    /**
     * 根据订单Id和商品Id修改备注
     * @param mgOrderGoods
     * @throws HookahException
     * @author lt
     */
    void updateMgOrderGoodsRemark(MgOrderGoods mgOrderGoods) throws HookahException;

    /**
     * 根据订单Id和商品Id获取备注信息
     * @param mgOrderGoods
     * @return
     * @throws HookahException
     */
    Map getRemark(MgOrderGoods mgOrderGoods);

    void updateConcatInfo(String orderId,String goodsId,String concatName,String concatPhone,String concatEmail) throws HookahException;
}
