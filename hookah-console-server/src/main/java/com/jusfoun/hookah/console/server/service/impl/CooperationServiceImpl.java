package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CooperationMapper;
import com.jusfoun.hookah.core.domain.Cooperation;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.CooperationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by lt on 2017/5/8.
 */
@Service
public class CooperationServiceImpl  extends GenericServiceImpl<Cooperation, String> implements CooperationService {

    @Resource
    CooperationMapper cooperationMapper;

    @Resource
    public void setDao(CooperationMapper cooperationMapper) {
        super.setDao(cooperationMapper);
    }

    @Override
    @Transactional
    public void addCooperation(Cooperation coo) throws Exception{
        Date date = DateUtils.now();
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        boolean isExists = true;
        List<Condition> filters = new ArrayList<>();
        filters.clear();
        if (StringUtils.isNoneBlank(coo.getCooName())){
            filters.add(Condition.eq("cooName", coo.getCooName()));
        }else {
            throw new HookahException("合作机构名称不能为空");
        }
        if (StringUtils.isBlank(coo.getUrl())){
            throw new HookahException("机构链接地址不能为空");
        }else if(!pattern.matcher(coo.getUrl()).matches()){
            throw new HookahException("机构链接地址格式不正确");
        }
        if (StringUtils.isBlank(coo.getPictureUrl())){
            throw new HookahException("logo地址不能为空");
        }else if(!pattern.matcher(coo.getPictureUrl()).matches()){
            throw new HookahException("logo地址格式不正确");
        }
        isExists = exists(filters);
        if(isExists){
            throw new HookahException("该合作机构已经添加");
        }
        filters.clear();
        filters.add(Condition.eq("cooOrder", coo.getCooOrder()));
        if (exists(filters)){
            throw new HookahException("该显示顺序已经存在");
        }
        if (coo.getState() == null){
            coo.setState(coo.COOPERATION_STATE_ON);
        }
        coo.setCreateTime(date);
        coo.setLastUpdateTime(date);
        cooperationMapper.insert(coo);
    }

    @Override
    @Transactional
    public void modify(Cooperation coo) throws Exception{
        Date date = DateUtils.now();
        List<Condition> filters = new ArrayList<>();
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        Pattern pattern1 = Pattern.compile("/^1[34578]\\d{9}$/");
        if (StringUtils.isNoneBlank(coo.getCooperationId())) {
            filters.add(Condition.eq("cooperationId", coo.getCooperationId()));
        }else {
            throw new HookahException("未选择修改机构，请重新操作");
        }
        if (StringUtils.isBlank(coo.getCooPhone())){
            throw new HookahException("手机号不能为空");
        }else if(!pattern1.matcher(coo.getCooPhone()).matches()){
            throw new HookahException("手机号格式不正确");
        }
        if (StringUtils.isBlank(coo.getUrl())){
            throw new HookahException("机构链接地址不能为空");
        }else if(!pattern.matcher(coo.getUrl()).matches()){
            throw new HookahException("机构链接地址格式不正确");
        }
        if (StringUtils.isBlank(coo.getPictureUrl())){
            throw new HookahException("logo地址不能为空");
        }else if(!pattern.matcher(coo.getPictureUrl()).matches()){
            throw new HookahException("logo地址格式不正确");
        }
        coo.setLastUpdateTime(date);
        cooperationMapper.updateByExampleSelective(coo,convertFilter2Example(filters));
    }

    @Override
    @Transactional
    public void updateState(Cooperation coo) throws Exception{
        Date date = DateUtils.now();
        List<Condition> filters = new ArrayList<>();
        if (StringUtils.isNoneBlank(coo.getCooperationId())) {
            filters.add(Condition.eq("cooperationId", coo.getCooperationId()));
        }else {
            throw new HookahException("未选中修改机构，请重新操作");
        }
        coo.setLastUpdateTime(date);
        cooperationMapper.updateByExampleSelective(coo,convertFilter2Example(filters));
    }
}
