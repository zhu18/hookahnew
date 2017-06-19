package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface OrderInfoMapper extends GenericDao<OrderInfo> {
    int getUserCount();
}