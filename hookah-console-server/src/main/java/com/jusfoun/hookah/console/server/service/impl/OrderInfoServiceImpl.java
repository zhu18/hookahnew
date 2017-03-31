package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.OrderInfoMapper;
import com.jusfoun.hookah.core.domain.Cart;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.JSONUtils;
import com.jusfoun.hookah.core.utils.OrderHelper;
import com.jusfoun.hookah.rpc.api.CartService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgOrderInfoService;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import org.apache.http.HttpException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单基本信息
 * @author:jsshao
 * @date: 2017-3-17
 */
public class OrderInfoServiceImpl extends GenericServiceImpl<OrderInfo, String> implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderinfoMapper;

    @Resource
    private CartService cartService;

    @Resource
    private GoodsService goodsService;

    @Resource
    MgOrderInfoService mgOrderInfoService;

    @Resource
    public void setDao(OrderInfoMapper orderinfoMapper) {
        super.setDao(orderinfoMapper);
    }

    private OrderInfo init(OrderInfo orderinfo) {
        Date date = new Date();
        orderinfo.setOrderSn(OrderHelper.genOrderSn());
        orderinfo.setOrderStatus(OrderInfo.ORDERSTATUS_CONFIRM);
        orderinfo.setShippingStatus(0);
        orderinfo.setShippingId("");
        orderinfo.setShippingName("");
        orderinfo.setPayId("");
        orderinfo.setPayStatus(0);
        orderinfo.setPayName("");
        orderinfo.setHowOos("");
        orderinfo.setHowSurplus("");
        orderinfo.setPackName("");
        orderinfo.setCardName("");
        orderinfo.setCardMessage("");
        orderinfo.setGoodsAmount(0L);
        orderinfo.setShippingFee(0L);
        orderinfo.setInsureFee(0L);
        orderinfo.setPayFee(0L);
        orderinfo.setPackFee(0L);
        orderinfo.setCardFee(0L);
        orderinfo.setGoodsDiscountFee(0L);
        orderinfo.setMoneyPaid(0L);
        orderinfo.setSurplus(0L);
        orderinfo.setIntegral(0);
        orderinfo.setIntegralMoney(0L);
        orderinfo.setBonus(0L);
        orderinfo.setOrderAmount(0L);
        orderinfo.setFromAd(0);
//		orderinfo.setReferer("管理员添加");
        orderinfo.setAddTime(date);
        orderinfo.setConfirmTime(date);
        orderinfo.setPayTime(date);
        orderinfo.setShippingTime(date);
        orderinfo.setPackId("");
        orderinfo.setCardId("");
        orderinfo.setBonusId("");
        orderinfo.setInvoiceNo("");
        orderinfo.setExtensionCode("");
        orderinfo.setExtensionId("");
        orderinfo.setToBuyer("");
        orderinfo.setPayNote("");
        orderinfo.setAgencyId(0);
        orderinfo.setParentId("");
        orderinfo.setTax(0L);
        orderinfo.setIsSeparate(0);
        orderinfo.setDiscount(0L);
        orderinfo.setCallbackStatus("true");
        orderinfo.setLastmodify(date);
        orderinfo.setEmail("");
        orderinfo.setDelFlag(1);
        orderinfo.setCommentFlag(0);
        return orderinfo;
    }

    private MgOrderGoods getMgOrderGoodsByCart(Cart cart) {
        MgOrderGoods og = new MgOrderGoods();
        og.setDiscountFee(0L);

        og.setGoodsId(cart.getGoodsId());
        og.setGoodsName(cart.getGoodsName());
        og.setGoodsNumber(cart.getGoodsNumber());
        og.setGoodsPrice(cart.getGoodsPrice());
        og.setGoodsSn(cart.getGoodsSn());
        og.setGoodsFormat(cart.getGoodsFormat());
        og.setFormatNumber(cart.getFormatNumber());
        og.setGoodsImg(cart.getGoodsImg());
		og.setIsGift(cart.getIsGift());
        og.setIsReal(0);
        og.setMarketPrice(cart.getMarketPrice());
//		og.setSendNumber(cart.getS);
        return og;
    }

    @Transactional(readOnly=false)
    @Override
    public OrderInfo insert(OrderInfo orderInfo,String cartIds) throws Exception {
        init(orderInfo);

        String[] cartIdArray = cartIds.split(",");

        List<Cart> cartList= cartService.selectByIds(cartIdArray);
        List<MgOrderGoods> ordergoodsList = null;
        if(cartList!=null&&cartList.size()>0){
            ordergoodsList = new ArrayList<MgOrderGoods>();
            Long goodsAmount = new Long(0);
            for(Cart cart:cartList){
                //验证商品是否下架
                Goods g = goodsService.selectById(cart.getGoodsId());
                if(g.getIsOnsale()==null||g.getIsOnsale()!=1){
                    throw new HookahException("商品["+g.getGoodsName()+"]未上架");
                }
                if(cart.getGoodsPrice()!=null&&cart.getGoodsNumber()!=null){
                    goodsAmount += cart.getGoodsPrice() * cart.getFormatNumber() * cart.getGoodsNumber();  //商品单价 * 套餐内数量 * 购买套餐数量
                }
                MgOrderGoods og = getMgOrderGoodsByCart(cart);
                ordergoodsList.add(og);
            }
            orderInfo.setGoodsAmount(goodsAmount);
            orderInfo.setOrderAmount(goodsAmount);

            if(ordergoodsList!=null&&ordergoodsList.size()>0){
                orderInfo.setDelFlag(0);
                OrderInfoVo orderInfoVo = (OrderInfoVo)orderInfo;
                orderInfoVo.setMgOrderGoodsList(ordergoodsList);
                orderinfoMapper.insert(orderInfo);
                mgOrderInfoService.insert(orderInfoVo);
            }
            if(goodsAmount.compareTo(0L)==0){
                updatePayStatus(orderInfo.getOrderSn(),2);
            }

            //逻辑删除已经提交的购物车商品
            cartService.deleteBatchByLogic(cartIdArray);
        }
        return orderInfo;
    }

    @Transactional(readOnly=false)
    @Override
    public void updatePayStatus(String orderSn, Integer status) throws Exception {
        logger.info("updatePayStatus status = {}",status);
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("orderSn",orderSn));
        OrderInfo orderInfo =  super.selectOne(filters);

        orderInfo.setPayTime(new Date());
        orderInfo.setLastmodify(new Date());
        orderInfo.setPayStatus(status);
        super.updateByIdSelective(orderInfo);

        //支付成功后
        if(2== status){
            managePaySuccess(orderInfo);
        }
        //        if(list!=null&&list.size()>0){
        //            mapper.updatePayStatus(orderSn,status);
        //        }else{
        //            throw new ShopException("无法设置支付状态");
        //        }

    }

    private void managePaySuccess(OrderInfo orderInfo) throws HttpException, IOException {
        //支付成功,操作待补充
    }



    @Override
    public Pagination<OrderInfoVo> getDetailListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                           List<OrderBy> orderBys) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize, getOrderBy(orderBys));
        Page<OrderInfo> list =  (Page<OrderInfo>) super.selectList(filters,orderBys);
        Page<OrderInfoVo> page = new Page<OrderInfoVo>(pageNum,pageSize);
        for(OrderInfo order:list){
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            this.copyProperties(order,orderInfoVo,null);
            OrderInfoVo mgOrder = mgOrderInfoService.selectById(orderInfoVo.getOrderId());
            orderInfoVo.setMgOrderGoodsList(mgOrder.getMgOrderGoodsList());
            page.add(orderInfoVo);
        }

        Pagination<OrderInfoVo> pagination = new Pagination<OrderInfoVo>();
        pagination.setTotalItems(list.getTotal());
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        logger.info(JSONUtils.toString(pagination));
        return pagination;
    }
}
