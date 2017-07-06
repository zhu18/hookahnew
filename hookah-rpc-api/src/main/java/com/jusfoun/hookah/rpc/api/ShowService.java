package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.ShowVO;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * @author zhaoshuai
 * Created by computer on 2017/7/4.
 */
public interface ShowService extends GenericService<User,String> {
    List<ShowVO> getRegisterUserCount();

    List<ShowVO> getPayAmount();

    List<ShowVO> getUnPayAmount();

    List<ShowVO> getActiveUserCount();

    List<ShowVO> userArea();
}
