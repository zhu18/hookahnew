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
        List<Condition> filters = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long min = 0;
        Date one;//报名截止时间
        Date two;//当前时间
        String applyLastTime = null;

        List<ZbRecommendVo> zbRecommendVos = zbRecommendMapper.selectRecommendTasksInfo();
        try {
            for(ZbRecommendVo zb : zbRecommendVos){
                if(zb != null){
                    if(zb.getApplyDeadline() != null){
                        one = df.parse(df.format(zb.getApplyDeadline()));
                        two = df.parse(df.format(new Date()));
                        long time1 = one.getTime();
                        long time2 = two.getTime();
                        long diff = 0;
                        if(time1 > time2){
                            diff = time1 - time2;
                        }
                        day = diff / (24 * 60 * 60 * 1000);
                        hour = (diff / (60 * 60 * 1000) - day * 24);
                        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
                        applyLastTime = day + "天" + hour + "小时" + min + "分";
                        zb.setApplyLastTime(applyLastTime != null ? applyLastTime : " ");
                    }
                    List<Condition> filters2 = new ArrayList<>();
                    if(zb.getRequirementId() != null){
                        filters2.add(Condition.eq("requirementId", zb.getRequirementId()));
                        List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyService.selectList(filters2);
                        zb.setCount(zbRequirementApplies.size());
                    }
                }
            }
            map.put("zbRecommendVos",zbRecommendVos);
            return ReturnData.success(map);
        } catch (Exception e) {
            logger.error("系统错误",e);
            return ReturnData.error("系统错误");
        }
    }
}
