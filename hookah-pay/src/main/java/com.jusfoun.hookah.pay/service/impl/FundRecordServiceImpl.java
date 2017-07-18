package com.jusfoun.hookah.pay.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.OrderInfoMapper;
import com.jusfoun.hookah.core.dao.PayTradeRecordMapper;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.*;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.http.HttpException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;

public class FundRecordServiceImpl extends GenericServiceImpl<PayTradeRecord, String> implements FundRecordService {

    @Resource
    private PayTradeRecordMapper payTradeRecordMapper;

    @Resource
    public void setDao(PayTradeRecordMapper payTradeRecordMapper) {
        super.setDao(payTradeRecordMapper);
    }


    @Override
    public Pagination<PayTradeRecord> getDetailListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                       List<OrderBy> orderBys) {
        PageHelper.startPage(pageNum, pageSize);
        Page<PayTradeRecord> list =  (Page<PayTradeRecord>) super.selectList(filters,orderBys);
        Pagination<PayTradeRecord> pagination = new Pagination<PayTradeRecord>();
        pagination.setTotalItems(list.getTotal());
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(list);
        logger.info(JsonUtils.toJson(pagination));
        return pagination;
    }

}
