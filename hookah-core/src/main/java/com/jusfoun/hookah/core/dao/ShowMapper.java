package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.ShowVO;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.List;

/**
 * @author zhaoshuai
 * Created by computer on 2017/7/4.
 */
public interface ShowMapper  extends GenericDao<User> {
    List<ShowVO> getRegisterUserCount();

    List<ShowVO> getActiveUserCount();

    List<ShowVO> getPayAmount();

    List<ShowVO> getUnPayAmount();

    List<ShowVO> userArea();

    int getNewUserCount();

    int getNewUserSum();

    int getBuyGoodsCount();

    List<User> getUserTypeCount();

    int getActiveUserCou();

    int getAccessCount();
}
