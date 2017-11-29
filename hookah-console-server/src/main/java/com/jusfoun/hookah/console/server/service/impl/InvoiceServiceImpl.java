package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.InvoiceMapper;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.InvoiceVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoInvoiceVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.*;
import com.jusfoun.hookah.rpc.api.ExpressInfoService;
import com.jusfoun.hookah.rpc.api.UserInvoiceAddressService;
import com.jusfoun.hookah.rpc.api.UserInvoiceTitleService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 发票服务
 * @author:jsshao
 * @date: 2017-3-17
 */
public class InvoiceServiceImpl extends GenericServiceImpl<Invoice, String> implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Resource
    public void setDao(InvoiceMapper invoiceMapper) {
        super.setDao(invoiceMapper);
    }

    @Resource
    UserInvoiceTitleService userInvoiceTitleService;

    @Resource
    UserInvoiceAddressService userInvoiceAddressService;

    @Resource
    ExpressInfoService expressInfoService;

    @Resource
    OrderInfoService orderInfoService;

    @Resource
    OrderInvoiceService orderInvoiceService;

    @Resource
    private GoodsService goodsService;

    @Resource
    MgGoodsService mgGoodsService;

    @Resource
    MgOrderInfoService mgOrderInfoService;

    @Resource
    RedisOperate redisOperate;

    /**
     * 开发票
     * @param titleId 抬头ID
     * @param id 收票人ID
     * @param orderIds
     * @throws HookahException
     */
    @Override
    @Transactional
    public void addInvoice(String invoiceId, String titleId, String id, String orderIds) throws HookahException {

        Invoice invoice = buildInvoiceInfo(titleId, id, orderIds);
        if(StringUtils.isNotBlank(invoiceId)){
            super.updateByIdSelective(invoice);
            invoice.setInvoiceId(invoiceId);
        }else{
            invoice = super.insert(invoice);
        }

        // 插入中间表数据
        // 多个订单可以开具一张发票
        if(orderIds != null && orderIds.contains(HookahConstants.COMMA)){
            String[] orderIdArray = orderIds.split(HookahConstants.COMMA);
            List<OrderInvoice> orderInvoiceList  = new ArrayList<>();
            OrderInvoice orderInvoice;
            for(String orderId : orderIdArray){
                orderInvoice = new OrderInvoice();
                orderInvoice.setOrderId(orderId);
                orderInvoice.setInvoiceId(invoice.getInvoiceId());
                orderInvoiceList.add(orderInvoice);
            }
            orderInvoiceService.insertBatch(orderInvoiceList);
        }else{
            OrderInvoice orderInvoice = new OrderInvoice();
            orderInvoice.setOrderId(orderIds);
            orderInvoice.setInvoiceId(invoice.getInvoiceId());
            orderInvoiceService.insert(orderInvoice);
        }
    }

    /**
     * 构建发票信息
     * @param titleId
     * @param id
     * @return
     */
    private Invoice buildInvoiceInfo(String titleId, String id, String orderIds){
        Invoice invoice = new Invoice();
        if(StringUtils.isNotBlank(titleId)){

            UserInvoiceTitle userInvoiceTitle = userInvoiceTitleService.selectById(titleId);
            BeanUtils.copyProperties(userInvoiceTitle, invoice);
            // 发票类型 0：个人 1：普通发票 2：专用发票
            invoice.setInvoiceType(userInvoiceTitle.getUserInvoiceType());
        }else{
            // 抬头为个人
            invoice.setInvoiceTitle("0");
        }
        // 收票人ID
        invoice.setInvoiceAddrId(id);
        // 已申请（待审核）
        invoice.setInvoiceStatus(Byte.valueOf("1"));

        invoice.setInvoiceSn(generateInvoiceSn());

        // 发票金额
        if(orderIds != null && orderIds.contains(HookahConstants.COMMA)){
            String[] orderIdArray = orderIds.split(HookahConstants.COMMA);
            invoice.setInvoiceAmount(orderInfoService.sumOrderAmountByOrderIds(orderIdArray));
        }else{
            invoice.setInvoiceAmount(orderInfoService.sumOrderAmountByOrderIds(new String[]{orderIds}));
        }
        return invoice;
    }

    //  生成发票编码
    private String generateInvoiceSn(){

        StringBuilder goodsSn = new StringBuilder();

        return goodsSn.toString();
    }

    @Override
    public Pagination<OrderInfoInvoiceVo> getDetailListInPage(Integer pageNum, Integer pageSize, String userId, Byte invoiceStatus) throws HookahException {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        Page<OrderInfoInvoiceVo> list =  (Page<OrderInfoInvoiceVo>) invoiceMapper.getOrderInvoiceInfo(userId,invoiceStatus);
        Page<OrderInfoInvoiceVo> page = new Page<OrderInfoInvoiceVo>(pageNum,pageSize);
        for(OrderInfo order:list){
            OrderInfoInvoiceVo orderInfoInvoiceVo = new OrderInfoInvoiceVo();
            this.copyProperties(order,orderInfoInvoiceVo,null);

            OrderInfoVo mgOrder = mgOrderInfoService.selectById(orderInfoInvoiceVo.getOrderId());
            List<MgOrderGoods> goodsList = mgOrder.getMgOrderGoodsList();
            if(goodsList!=null){
                //未支付订单处理
                if(order.getPayStatus()!=OrderInfo.PAYSTATUS_PAYED){
                    for(MgOrderGoods goods:goodsList){
                        goods.setUploadUrl(null);
                        try {
                            Goods curGoods = goodsService.findGoodsById(goods.getGoodsId());
                            goods.setIsOnsale(curGoods.getIsOnsale());
                        } catch (HookahException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }

            orderInfoInvoiceVo.setMgOrderGoodsList(goodsList);
            page.add(orderInfoInvoiceVo);
        }

        Pagination<OrderInfoInvoiceVo> pagination = new Pagination<OrderInfoInvoiceVo>();
        pagination.setTotalItems(list.getTotal());
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        logger.info(JsonUtils.toJson(pagination));
        return pagination;
    }

    @Override
    public InvoiceVo findInvoiceInfo(String invoiceId) throws HookahException {
        InvoiceVo invoiceVo = new InvoiceVo();
        Invoice invoice = super.selectById(invoiceId);

        BeanUtils.copyProperties(invoice, invoiceVo);

        if(!Objects.isNull(invoice)){

            UserInvoiceTitle userInvoiceTitle = userInvoiceTitleService.selectById(invoice.getInvoiceTitle());
            // 抬头相关信息
            invoiceVo.setUserInvoiceTitle(userInvoiceTitle);
            UserInvoiceAddress userInvoiceAddress = userInvoiceAddressService.selectById(invoice.getInvoiceAddrId());
            // 收票人相关信息
            invoiceVo.setUserInvoiceAddress(userInvoiceAddress);

            ExpressInfo expressInfo = expressInfoService.selectById(invoiceId);
            // 邮寄信息
            invoiceVo.setExpressInfo(expressInfo);
        }
        return invoiceVo;
    }
}
