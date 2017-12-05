package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.FlowUserMapper;
import com.jusfoun.hookah.core.domain.FlowUser;
import com.jusfoun.hookah.core.domain.vo.FlowUserVo;
import com.jusfoun.hookah.core.domain.vo.FlowUsersVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.FlowUserService;
import org.apache.catalina.LifecycleState;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        FlowUsersVo sum = flowUserMapper.selectSum(startTime ,endTime);
        List<FlowUsersVo> dataSource = flowUserMapper.selectBySourceList(startTime, endTime);
        sum.setDataSource(Short.parseShort("0"));
        dataSource.add(sum);
        return ReturnData.success(dataSource);
    }
}
