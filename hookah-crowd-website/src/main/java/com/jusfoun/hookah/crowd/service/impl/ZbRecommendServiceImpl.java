package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbRecommendMapper;
import com.jusfoun.hookah.core.domain.zb.ZbRecommend;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRecommendVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRecommendService;
import com.jusfoun.hookah.crowd.service.ZbRequireApplyService;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ZbRecommendServiceImpl extends GenericServiceImpl<ZbRecommend, Long> implements ZbRecommendService{

    @Resource
    ZbRecommendMapper zbRecommendMapper;

    @Resource
    ZbRequireService zbRequireService;

    @Resource
    ZbRequireApplyService zbRequireApplyService;

    @Resource
    public void setDao(ZbRecommendMapper zbRecommendMapper) {
        super.setDao(zbRecommendMapper);
    }

    public ReturnData selectRecommendTasksInfo(){

        Map<String, Object> map = new HashMap<>(6);
        List<ZbRecommendVo> zbRecommendVos = null;
        try {
            //从session获取用户信息
            Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
            if(userMap != null){
                //登录
                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("userId", this.getCurrentUser().getUserId()));
                List<ZbRequirementApply> applies = zbRequireApplyService.selectList(filters);
                List<Long> list = new ArrayList();
                for(ZbRequirementApply app : applies){
                    list.add(app.getRequirementId());
                }

                if(list != null && list.size() > 0){
                    zbRecommendVos = zbRecommendMapper.selectRecommendTasksInfo(list.toArray());
                }else {
                    zbRecommendVos = zbRecommendMapper.selectRecommendTasksInfoNo();
                }
            }else {
                //未登录
                zbRecommendVos = zbRecommendMapper.selectRecommendTasksInfoNo();
            }
            if(zbRecommendVos != null){
                for(ZbRecommendVo zb : zbRecommendVos){
                    if(zb != null){
                        if(zb.getApplyDeadline() != null){
                            String time = DateUtil.timeCountDown(zb.getApplyDeadline());
                            zb.setApplyLastTime(time != null ? time : " ");
                        }
                        List<Condition> filters2 = new ArrayList<>();
                        if(zb.getRequirementId() != null){
                            filters2.add(Condition.eq("requirementId", zb.getRequirementId()));
                            List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyService.selectList(filters2);
                            zb.setCount(zbRequirementApplies.size());
                        }
                    }
                }
            }
            map.put("zbRecommendVos",zbRecommendVos != null ? zbRecommendVos : "");
            return ReturnData.success(map);
        } catch (Exception e) {
            logger.error("系统错误",e);
            return ReturnData.error("系统错误");
        }
    }
}
