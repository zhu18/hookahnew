package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.ShowMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.ShowVO;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.ShowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaoshuai
 * Created by computer on 2017/7/4.
 */
@Service
public class ShowServiceImpl extends GenericServiceImpl<User, String> implements ShowService {
    @Resource
    private ShowMapper showMapper;

    @Resource
    public void setDao(ShowMapper showMapper) {
         super.setDao(showMapper);
    }
    public List<ShowVO> getRegisterUserCount(){
        return showMapper.getRegisterUserCount();
    }

    public List<ShowVO> getActiveUserCount(){
        return showMapper.getActiveUserCount();
    }

    public List<ShowVO> getPayAmount(){
        return showMapper.getPayAmount();
    }

    public List<ShowVO> userArea(){
        return showMapper.userArea();
    }

    public List<ShowVO> getUnPayAmount(){
        return showMapper.getUnPayAmount();
    }

    public int getNewUserCount(){
        return showMapper.getNewUserCount();
    }

    public int getNewUserSum(){
        return showMapper.getNewUserSum();
    }

    public int getBuyGoodsCount(){
        return showMapper.getBuyGoodsCount();
    }

    public List<User> getUserTypeCount(){
        return showMapper.getUserTypeCount();
    }

}
