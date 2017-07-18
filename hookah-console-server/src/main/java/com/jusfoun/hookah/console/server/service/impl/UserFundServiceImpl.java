package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.UserMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.UserFundCritVo;
import com.jusfoun.hookah.core.domain.vo.UserFundVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.UserFundService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Created by ctp on 2017/7/18.
 */
@Service
public class UserFundServiceImpl extends GenericServiceImpl<User,String> implements UserFundService{

    @Resource
    UserMapper userMapper;

    @Resource
    public void setDao(UserMapper userMapper) {
        super.setDao(userMapper);
    }

    @Override
    public ReturnData getUserFundListPage(UserFundCritVo userFundCritVo) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        //参数校验
        if (Objects.isNull(userFundCritVo.getPageNumber())) {
            userFundCritVo.setPageNumber(HookahConstants.PAGE_NUM);
        }
        if (Objects.isNull(userFundCritVo.getPageSize())) {
            userFundCritVo.setPageSize(HookahConstants.PAGE_SIZE);
        }

        returnData.setData(this.getListInPage(userFundCritVo));

        return returnData;
    }

    private Pagination getListInPage(UserFundCritVo userFundCritVo){
        PageHelper.startPage(userFundCritVo.getPageNumber(), userFundCritVo.getPageSize());
        Page<UserFundVo> page = (Page<UserFundVo>) userMapper.selectUserFundList(userFundCritVo);
        Pagination<UserFundVo> pagination = new Pagination<UserFundVo>();
        pagination.setTotalItems(page.getTotal());
        pagination.setPageSize(userFundCritVo.getPageSize());
        pagination.setCurrentPage(userFundCritVo.getPageNumber());
        pagination.setList(page);
        return pagination;
    }

}
