package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.console.server.util.PropertiesManager;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.InvoiceMapper;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.*;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.*;
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
    UserService userService;

    @Resource
    RegionService regionService;

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
    public void addInvoice(InvoiceDTOVo invoiceDTOVo) throws HookahException {

        Invoice invoice = buildInvoiceInfo(invoiceDTOVo);
        if(StringUtils.isNotBlank(invoiceDTOVo.getInvoiceId())){
            List<Condition> filters = new ArrayList<Condition>();
            filters.add(Condition.eq("invoiceId", invoiceDTOVo.getInvoiceId()));
            if(orderInvoiceService.exists(filters)){
                orderInvoiceService.deleteByCondtion(filters);
            }
            // 换开发票
            if(HookahConstants.INVOICE_STATUS_4 == super.selectById(invoiceDTOVo.getInvoiceId()).getInvoiceStatus()){
                invoice.setInvoiceChange(HookahConstants.INVOICE_CHANGE_1);
            }
            // 修改时，更新状态为已申请(待审核)
            invoice.setInvoiceStatus(HookahConstants.INVOICE_STATUS_1);
            invoice.setInvoiceId(invoiceDTOVo.getInvoiceId());
            super.updateByIdSelective(invoice);
        }else{
            invoice = super.insert(invoice);
        }

        // 插入中间表数据
        // 多个订单可以开具一张发票
        if(invoiceDTOVo.getOrderIds() != null && invoiceDTOVo.getOrderIds().contains(HookahConstants.COMMA)){
            String[] orderIdArray = invoiceDTOVo.getOrderIds().split(HookahConstants.COMMA);
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
            orderInvoice.setOrderId(invoiceDTOVo.getOrderIds());
            orderInvoice.setInvoiceId(invoice.getInvoiceId());
            orderInvoiceService.insert(orderInvoice);
        }
    }

    /**
     * 构建发票信息
     * @param invoiceDTOVo
     * @return
     */
    private Invoice buildInvoiceInfo(InvoiceDTOVo invoiceDTOVo){
        Invoice invoice = new Invoice();
        if(StringUtils.isNotBlank(invoiceDTOVo.getTitleId())){

            UserInvoiceTitle userInvoiceTitle = userInvoiceTitleService.selectById(invoiceDTOVo.getTitleId());
            BeanUtils.copyProperties(userInvoiceTitle, invoice);
            // 发票类型 0：普通发票 1：专用发票
            invoice.setInvoiceType(userInvoiceTitle.getUserInvoiceType());
        }else{
            // 抬头为个人
            invoice.setTitleId("0");
        }
        // 收票人ID
        invoice.setInvoiceAddrId(invoiceDTOVo.getAddressId());
        // 已申请（待审核）
        invoice.setInvoiceStatus(HookahConstants.INVOICE_STATUS_1);

        // 发票编码
        invoice.setInvoiceSn(generateInvoiceSn(invoiceDTOVo.getUserId()));

        // 创建人
        invoice.setAddUser(invoiceDTOVo.getUserId());

        // 发票金额
        if(invoiceDTOVo.getOrderIds() != null && invoiceDTOVo.getOrderIds().contains(HookahConstants.COMMA)){
            String[] orderIdArray = invoiceDTOVo.getOrderIds().split(HookahConstants.COMMA);
            invoice.setInvoiceAmount(orderInfoService.sumOrderAmountByOrderIds(orderIdArray));
        }else{
            invoice.setInvoiceAmount(orderInfoService.sumOrderAmountByOrderIds(new String[]{invoiceDTOVo.getOrderIds()}));
        }
        // 开票时间
        invoice.setAddTime(DateUtils.now());
        return invoice;
    }

    //  生成发票编码
    private String generateInvoiceSn(String userId){

        StringBuilder invoiceSn = new StringBuilder();
        User user = userService.selectById(userId);
        if(user != null){
            // FP+会员编码+6位顺序号
            invoiceSn.append(HookahConstants.platformInvoiceCode);
            invoiceSn.append(StringUtils.isNotBlank(user.getUserSn()) ? user.getUserSn() : "");
        }
        // 编号
        invoiceSn.append(String.format("%06d", Integer.parseInt(redisOperate.incr(PropertiesManager.getInstance().getProperty("platformInvoiceCode")))));

        return invoiceSn.toString();
    }

    @Override
    public Pagination<OrderInfoInvoiceVo> getDetailListInPage(Integer pageNum, Integer pageSize, String userId, Byte invoiceStatus) throws HookahException {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        Page<OrderInfoInvoiceVo> list =  (Page<OrderInfoInvoiceVo>) invoiceMapper.getOrderInvoiceInfoList(userId,invoiceStatus);
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
    public InvoiceDetailVo findInvoiceInfo(String invoiceId) throws HookahException {
        InvoiceDetailVo invoiceDetailVo = new InvoiceDetailVo();
        Invoice invoice = super.selectById(invoiceId);

        BeanUtils.copyProperties(invoice, invoiceDetailVo);

        List<InvoiceDetailVo.InvoiceOrder>  invoiceOrderList = new ArrayList<>();
        for(OrderInfo orderInfo :orderInfoService.getOrderInfoByInvoiceId(invoiceId)){
            InvoiceDetailVo.InvoiceOrder invoiceOrder = new InvoiceDetailVo.InvoiceOrder();
            BeanUtils.copyProperties(orderInfo, invoiceOrder);
            invoiceOrderList.add(invoiceOrder);
        }
        invoiceDetailVo.setInvoiceOrderList(invoiceOrderList);

        if(!Objects.isNull(invoice)){

            UserInvoiceTitle userInvoiceTitle = userInvoiceTitleService.selectById(invoice.getTitleId());
            // 抬头相关信息
            invoiceDetailVo.setUserInvoiceTitle(userInvoiceTitle);
            UserInvoiceAddress userInvoiceAddress = userInvoiceAddressService.selectById(invoice.getInvoiceAddrId());

            if(Objects.nonNull(userInvoiceAddress) && StringUtils.isNotBlank(userInvoiceAddress.getRegion())){

                Region region = regionService.selectById(userInvoiceAddress.getRegion());

                StringBuilder receiveAddress = new StringBuilder();
                for(String str : region.getMergerName().split(HookahConstants.COMMA)){
                    receiveAddress.append(str);
                }
                receiveAddress.append(userInvoiceAddress.getAddress());
                userInvoiceAddress.setReceiveAddress(receiveAddress.toString());
            }
            // 收票人相关信息
            invoiceDetailVo.setUserInvoiceAddress(userInvoiceAddress);

            List<Condition> filters = new ArrayList();
            //只查询商品状态为未删除的商品
            filters.add(Condition.eq("invoiceId", invoiceId));
            ExpressInfo expressInfo = expressInfoService.selectOne(filters);
            // 邮寄信息
            invoiceDetailVo.setExpressInfo(expressInfo);
        }
        return invoiceDetailVo;
    }

    public List<InvoiceVo> getInvoiceListInPage(String userName, Byte userType, Byte invoiceStatus, Byte invoiceType)throws HookahException{

        return invoiceMapper.getInvoiceInfo(userName, userType, invoiceStatus, invoiceType);
    }

    public InvoiceDetailVo findOrderInvoiceInfo(String invoiceId) throws HookahException {

        InvoiceDetailVo invoiceDetailVo = new InvoiceDetailVo();
        List<OrderInfoInvoiceVo> orderInfoInvoiceVoList = invoiceMapper.getOrderInvoiceDetailInfo(invoiceId);
        List<OrderInfoInvoiceVo> list = new ArrayList<>();
        for(OrderInfo order:orderInfoInvoiceVoList){
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
            list.add(orderInfoInvoiceVo);
        }
        BeanUtils.copyProperties(this.findInvoiceInfo(invoiceId), invoiceDetailVo);
        invoiceDetailVo.setOrderInfoInvoiceVoList(list);
        invoiceDetailVo.setUserInvoiceVo(invoiceMapper.getUserInvoiceInfoByInvoiceId(invoiceId));
        return invoiceDetailVo;
    }
}
