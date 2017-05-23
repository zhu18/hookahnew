package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.console.server.util.DictionaryUtil;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.GoodsCheckService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    GoodsCheckService goodsCheckService;

    @Resource
    public void setDao(GoodsMapper goodsMapper) {
        super.setDao(goodsMapper);
    }

    @Override
    @Transactional
    public void addGoods(GoodsVo obj) throws HookahException {
        Date date = DateUtils.now();
        if (obj == null)
            throw new HookahException("空数据！");
        // 将数据插入数据库
        obj.setIsOnsale(HookahConstants.GOODS_STATUS_ONSALE);
        obj.setCheckStatus(HookahConstants.GOODS_CHECK_STATUS_WAIT);
        if(obj.getOnsaleStartDate() == null) {
            obj.setOnsaleStartDate(date);
        }
        obj.setAddTime(date);
        obj.setLastUpdateTime(date);
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
        mgGoods.setAsAloneSoftware(obj.getAsAloneSoftware());
        mgGoods.setAsSaaS(obj.getAsSaaS());
        mgGoods.setAtAloneSoftware(obj.getAtAloneSoftware());
        mgGoods.setAtSaaS(obj.getAtSaaS());
        mgGoods.setDataModel(obj.getDataModel());
        mgGoods.setClickRate((long) 0);
        mongoTemplate.insert(mgGoods);
    }

    @Override
    @Transactional
    public void updateGoods(GoodsVo obj) throws HookahException {
        Date date = DateUtils.now();
        obj.setLastUpdateTime(date);
        obj.setIsOnsale(HookahConstants.GOODS_STATUS_ONSALE);
        if(obj.getOnsaleStartDate() == null) {
            obj.setOnsaleStartDate(date);
        }
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
            mgGoods.setApiInfo(obj.getApiInfo());
            mgGoods.setImgList(obj.getImgList());
            mgGoods.setGoodsId(obj.getGoodsId());
            MgGoods mgGoods1 = mgGoodsService.selectById(obj.getGoodsId());
            mgGoods.setClickRate(mgGoods1.getClickRate() == null ? (long)0 : mgGoods1.getClickRate());
            mgGoodsService.delete(obj.getGoodsId());
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
            if(format.getFormatId().equals(formatId)) {
                return format;
            }
        }
        throw new HookahException("未查到对应的商品规格");
    }

    /**
     * 商品下架/删除
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

    // 商品上架
    @Override
    public int onsale(String goodsId, String dateTime) {
        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        goods.setIsOnsale(HookahConstants.GOODS_STATUS_ONSALE);
        goods.setCheckStatus((byte)0);
        if(StringUtils.isNotBlank(dateTime)) {
            goods.setOnsaleStartDate(DateUtils.getDate(dateTime, DateUtils.DEFAULT_DATE_TIME_FORMAT));
        }else {
            goods.setOnsaleStartDate(DateUtils.now());
        }
        int i = super.updateByIdSelective(goods);
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
        pagination.setList(this.copyGoodsData(pagination.getList()));
        return pagination;
    }

    // 待出售商品列表
    @Override
    public Pagination waitList(String pageNum, String pageSize, String goodsName, String userId, Integer checkStatus, Integer isBook) {
        Pagination pagination = new Pagination(Integer.parseInt(pageNum), Integer.parseInt(pageSize));

        GoodsVo vo = new GoodsVo();
        vo.setUserId(userId);
        vo.setRowStart((Integer.parseInt(pageNum) - 1) * Integer.parseInt(pageSize));
        vo.setRowEnd(Integer.parseInt(pageNum) * Integer.parseInt(pageSize));
        if(checkStatus != null)
            vo.setCheckStatus(Byte.valueOf(checkStatus + ""));
        if(isBook != null)
            vo.setIsBook(Byte.valueOf(isBook + ""));
        if(goodsName != null)
            vo.setGoodsName(goodsName);
        pagination.setList(goodsMapper.waitList(vo));
        pagination.setTotalItems(goodsMapper.waitListCnt(vo));
        pagination.setList(this.copyGoodsData(pagination.getList()));
        pagination.getTotalPage();
        return pagination;
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
        Pagination pagination = this.getListInPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize), filters, orderBys);
        pagination.setList(this.copyGoodsData(pagination.getList()));
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
        Pagination pagination = this.getListInPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize), filters, orderBys);
        pagination.setList(this.copyGoodsData(pagination.getList()));
        return pagination;
    }

    private List<GoodsVo> copyGoodsData(List<Goods> list) {
        List<GoodsVo> list1 = new ArrayList<>();
        for(Goods goods : list) {
            GoodsVo goodsVo = new GoodsVo();
            BeanUtils.copyProperties(goods, goodsVo);
            goodsVo.setCatName(DictionaryUtil.getCategoryById(goods.getCatId()) == null ? "" : DictionaryUtil.getCategoryById(goods.getCatId()).getCatName());
            goodsVo.setCheckReason(goodsCheckService.selectOneByGoodsId(goods.getGoodsId()) == null
                    ? "" : goodsCheckService.selectOneByGoodsId(goods.getGoodsId()).getCheckContent());
            list1.add(goodsVo);
        }
        return list1;
    }

    /**
     * 查询商品详情
     * @return
     */
    @Override
    public GoodsVo findGoodsById(String goodsId) throws HookahException {
        Goods goods = super.selectById(goodsId);
        if (goods == null || goods.getGoodsId() == null) {
            throw new HookahException("未查询到商品信息！");
        }
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goods, goodsVo);
        // 查询所有区域信息
        EsGoods esGoods = goodsMapper.getNeedGoodsById(goodsId);
        if(esGoods != null) {
            if(esGoods.getGoodsAreas() != null) {
                String[] region = esGoods.getGoodsAreas().split(" ");
                if(region.length >= 2)
                    goodsVo.setAreaCountry(region[1]);
                if(region.length >= 3)
                    goodsVo.setAreaProvince(region[2]);
                if(region.length == 4)
                    goodsVo.setAreaCity(region[3]);
            }
            if(esGoods.getCatIds() != null) {
                String[] catIds = esGoods.getCatIds().split(" ");
                StringBuffer stringBuffer = new StringBuffer();
                for(String item : catIds) {
                    stringBuffer.append(DictionaryUtil.getCategoryById(item) == null
                            ? "" : DictionaryUtil.getCategoryById(item).getCatName()).append("->");
                }
                goodsVo.setCatFullName(stringBuffer.substring(0, stringBuffer.length() - 2));
            }
        }

        MgGoods mgGoods = mgGoodsService.selectById(goodsId);
        if (mgGoods != null) {
            goodsVo.setFormatList(mgGoods.getFormatList());
            goodsVo.setImgList(mgGoods.getImgList());
            goodsVo.setAttrTypeList(mgGoods.getAttrTypeList());
            goodsVo.setApiInfo(mgGoods.getApiInfo());
            goodsVo.setAsAloneSoftware(mgGoods.getAsAloneSoftware());
            goodsVo.setAsSaaS(mgGoods.getAsSaaS());
            goodsVo.setAtAloneSoftware(mgGoods.getAtAloneSoftware());
            goodsVo.setAtSaaS(mgGoods.getAtSaaS());
            goodsVo.setDataModel(mgGoods.getDataModel());
            goodsVo.setClickRate(mgGoods.getClickRate());
        }
        goodsVo.setCatName(DictionaryUtil.getCategoryById(goodsVo.getCatId()) == null
                ? "" : DictionaryUtil.getCategoryById(goodsVo.getCatId()).getCatName());
        return goodsVo;
    }
    /**
     * 作废
     * (前台专用)查询商品详情
     * @return
     */
    @Override
    public GoodsVo findGoodsByIdWebsite(String goodsId) throws HookahException {
        Goods goods = super.selectById(goodsId);
        if (goods == null || goods.getGoodsId() == null) {
            throw new HookahException("未查询到商品信息！");
        }
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goods, goodsVo);

        // 查询所有区域信息
        EsGoods esGoods = goodsMapper.getNeedGoodsById(goodsId);
        if(esGoods != null && esGoods.getCatIds() != null) {
            String[] catIds = esGoods.getCatIds().split(" ");
            StringBuffer stringBuffer = new StringBuffer();
            for(String item : catIds) {
                stringBuffer.append(DictionaryUtil.getCategoryById(item) == null
                        ? "" : DictionaryUtil.getCategoryById(item).getCatName()).append("->");
            }
            goodsVo.setCatFullName(stringBuffer.substring(0, stringBuffer.length() - 2));
        }

        MgGoods mgGoods = mgGoodsService.selectById(goodsId);
        if (mgGoods != null) {
            goodsVo.setFormatList(mgGoods.getFormatList());
            goodsVo.setImgList(mgGoods.getImgList());
            goodsVo.setAttrTypeList(mgGoods.getAttrTypeList());
            goodsVo.setApiInfo(mgGoods.getApiInfo());
            goodsVo.setAsAloneSoftware(mgGoods.getAsAloneSoftware());
            goodsVo.setAsSaaS(mgGoods.getAsSaaS());
            goodsVo.setAtAloneSoftware(mgGoods.getAtAloneSoftware());
            goodsVo.setAtSaaS(mgGoods.getAtSaaS());
            goodsVo.setDataModel(mgGoods.getDataModel());
            goodsVo.setClickRate(mgGoods.getClickRate());
        }
        goodsVo.setCatName(DictionaryUtil.getCategoryById(goodsVo.getCatId()) == null
                ? "" : DictionaryUtil.getCategoryById(goodsVo.getCatId()).getCatName());
        return goodsVo;
    }

    @Override
    public int updateByGidForFollowNum(Map<String, Object> map) {
        return goodsMapper.updateByGidForFollowNum(map);
    }
}
