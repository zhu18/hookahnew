package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.FlowUserMapper;
import com.jusfoun.hookah.core.domain.FlowUser;
import com.jusfoun.hookah.core.domain.vo.FlowUserVo;
import com.jusfoun.hookah.core.domain.vo.FlowUsersVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.FlowUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by admin on 2017/12/4.
 */
@Service
public class FlowUserServiceImpl  extends GenericServiceImpl<FlowUser,Long> implements FlowUserService {

    @Resource
    private FlowUserMapper flowUserMapper;

    @Resource
    public void setDao(FlowUserMapper flowUserMapper) {
        super.setDao(flowUserMapper);
    }

    public ReturnData tongjiList (String startTime,String endTime){
        //SimpleDateFormat dft = new SimpleDateFormat( "yyyy-MM-dd" );
        //String startTime1 = dft.format(startTime);
        //String endTime1 = dft.format(endTime);
        FlowUsersVo flowUser = flowUserMapper.selectSum(startTime ,endTime);
        return ReturnData.success(flowUser);
    }


}
