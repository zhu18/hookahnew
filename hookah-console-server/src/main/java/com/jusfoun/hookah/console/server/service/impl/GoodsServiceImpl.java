package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    MgGoodsService mgGoodsService;

    @Resource
    MqSenderService mqSenderService;

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
        mgGoods.setApiInfo(obj.getApiInfo());
        mongoTemplate.insert(mgGoods);
    }

    @Override
    @Transactional
    public void updateGoods(GoodsVo obj) throws HookahException {
        Date date = DateUtils.now();
        obj.setLastUpdateTime(date);
        obj.setIsOnsale(HookahConstants.GOODS_STATUS_ONSALE);
        obj.setOnsaleStartDate(date);
        obj.setOnsaleEndDate(null);
        obj.setCheckStatus(HookahConstants.GOODS_CHECK_STATUS_WAIT);
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

    /**
     * 提供给购物车查询商品规格的接口
     * @param goodsId 商品id
     * @param formatId 商品规格id
     * @return
     */
    @Override
    public MgGoods.FormatBean getFormat(String goodsId, Integer formatId)  throws Exception {
        MgGoods mgGoods = mgGoodsService.selectById(goodsId);
        if (mgGoods == null) {
            throw new HookahException("未查到商品信息");
        }

        List<MgGoods.FormatBean> list = mgGoods.getFormatList();
        for(MgGoods.FormatBean format : list) {
            if(format.getFormatId() == formatId) {
                return format;
            }
        }
        throw new HookahException("未查到对应的商品规格");
    }

    /**
     * 商品下架/删除/上架
     * @param goodsId
     * @return
     */
    @Override
    public int updateGoodsStatus(String goodsId, String status) {
        int i = 0;
        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        switch (status) {
            case "del" :
                goods.setIsDelete(HookahConstants.GOODS_STATUS_DELETE);
                goods.setLastUpdateTime(DateUtils.now());
                i = super.updateByIdSelective(goods);
                if(i > 0) {
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goodsId);
                }
                break;
            case "offSale":
                i = goodsMapper.updateOffSale(goodsId);
                if(i > 0) {
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goodsId);
                }
                break;
        }
        return i;
    }

    @Override
    public int onsale(String goodsId, String dateTime) {
        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        goods.setIsOnsale(HookahConstants.GOODS_STATUS_ONSALE);
        if(StringUtils.isNotBlank(dateTime)) {
            goods.setOnsaleStartDate(DateUtils.getDate(dateTime));
        }else {
            goods.setOnsaleStartDate(DateUtils.now());
        }
        int i = super.updateByIdSelective(goods);
        if (i > 0) {
            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goodsId);
        }
        return i;
    }

    // 出售中的商品
    @Override
    public Pagination saleList(String pageNum, String pageSize, String goodsName, String userId) {
        List<Condition> filters = new ArrayList();
        List<OrderBy> orderBys = new ArrayList();
        orderBys.add(OrderBy.desc("lastUpdateTime"));
        filters.add(Condition.eq("isDelete", HookahConstants.GOODS_STATUS_UNDELETE));
        filters.add(Condition.eq("isOnsale", HookahConstants.GOODS_STATUS_ONSALE));
        filters.add(Condition.le("onsaleStartDate", DateUtils.now()));
        filters.add(Condition.eq("checkStatus", HookahConstants.GOODS_CHECK_STATUS_YES));
        filters.add(Condition.eq("addUser", userId));
        if (StringUtils.isNotBlank(goodsName)) {
            filters.add(Condition.like("goodsName", goodsName.trim()));
        }
        Pagination pagination = this.getListInPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize), filters, orderBys);
        return pagination;
    }

    // 待出售商品列表
    @Override
    public List waitList(String goodsName, String userId, Integer checkStatus, Integer isBook) {
        List<Condition> filters = new ArrayList();
        List<OrderBy> orderBys = new ArrayList();
        orderBys.add(OrderBy.desc("lastUpdateTime"));
        filters.add(Condition.eq("isDelete", HookahConstants.GOODS_STATUS_UNDELETE));
        filters.add(Condition.eq("isOnsale", HookahConstants.GOODS_STATUS_ONSALE));
        filters.add(Condition.eq("addUser", userId));
        if (StringUtils.isNotBlank(goodsName)) {
            filters.add(Condition.like("goodsName", goodsName.trim()));
        }
        if (checkStatus != null) {
            filters.add(Condition.eq("checkStatus", checkStatus));
        }
        if(isBook != null) {
            // 查询预约商品
            if(isBook == 1) {
                filters.add(Condition.gt("onsaleStartDate", DateUtils.now()));
            }else {// 查询立即上架商品
                filters.add(Condition.le("onsaleStartDate", DateUtils.now()));
            }
        }
        // 查询条件一，未到预约上架时间且已经审核通过商品
        List<Condition> filters1 = filters;
        filters1.add(Condition.gt("onsaleStartDate", DateUtils.now()));
        filters1.add(Condition.eq("checkStatus", HookahConstants.GOODS_CHECK_STATUS_YES));
        List<Goods> list1 = this.selectList(filters1, orderBys);
        // 查询条件二，审核中的商品
        List<Condition> filters2 = filters;
        filters2.add(Condition.eq("checkStatus", HookahConstants.GOODS_CHECK_STATUS_WAIT));
        List<Goods> list2 = this.selectList(filters2, orderBys);
        // 合并商品
        if(list2 != null && list2.size() > 0) {
            list1.addAll(list2);
        }
        return list1;
    }

    // 已下架商品列表
    @Override
    public Pagination offsaleList(String pageNum, String pageSize, String goodsName, String userId) {
        List<Condition> filters = new ArrayList();
        List<OrderBy> orderBys = new ArrayList();
        orderBys.add(OrderBy.desc("lastUpdateTime"));
        filters.add(Condition.eq("isDelete", HookahConstants.GOODS_STATUS_UNDELETE));
        filters.add(Condition.eq("isOnsale", HookahConstants.GOODS_STATUS_OFFSALE));
        filters.add(Condition.eq("addUser", userId));
        if (StringUtils.isNotBlank(goodsName)) {
            filters.add(Condition.like("goodsName", goodsName.trim()));
        }
        Pagination<Goods> pagination = this.getListInPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize), filters, orderBys);
        return pagination;
    }

    // 违规商品列表
    @Override
    public Pagination illegalList(String pageNum, String pageSize, String goodsName, String userId) {
        List<Condition> filters = new ArrayList();
        List<OrderBy> orderBys = new ArrayList();
        orderBys.add(OrderBy.desc("lastUpdateTime"));
        filters.add(Condition.eq("isDelete", HookahConstants.GOODS_STATUS_UNDELETE));
        filters.add(Condition.eq("isOnsale", HookahConstants.GOODS_STATUS_FORCE_OFFSALE));
        filters.add(Condition.eq("addUser", userId));
        if (StringUtils.isNotBlank(goodsName)) {
            filters.add(Condition.like("goodsName", goodsName.trim()));
        }
        Pagination<Goods> pagination = this.getListInPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize), filters, orderBys);
        return pagination;
    }


}
