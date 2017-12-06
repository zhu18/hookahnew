package com.jusfoun.hookah.integral.service.impl;

import com.jusfoun.hookah.core.dao.jf.JfRuleMapper;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.JfRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 积分规则实现类
 *
 * @author : dengxu
 * @create 2017-11-21 19:29
 **/
@Service
public class JfRuleServiceImpl extends GenericServiceImpl<JfRule, Integer> implements JfRuleService {

    @Resource
    JfRuleMapper jfRuleMapper;

    @Resource
    public void setDao(JfRuleMapper jfRuleMapper) {
        super.setDao(jfRuleMapper);
    }

    @Override
    public ReturnData updateRule(String id, String type, String score, String upperLimitScore, String upperLimitTime, String lowerLimitScore, String lowerLimitTime, String note, String userName) throws Exception {

        if(!StringUtils.isNotBlank(id)){
            return ReturnData.error("参数不能为空!⊙﹏⊙‖∣°");
        }

        if(!StringUtils.isNotBlank(type)
                && !StringUtils.isNotBlank(score)
                && !StringUtils.isNotBlank(upperLimitScore)
                && !StringUtils.isNotBlank(upperLimitTime)
                && !StringUtils.isNotBlank(lowerLimitScore)
                && !StringUtils.isNotBlank(lowerLimitTime)
                && !StringUtils.isNotBlank(note)
                ){
            return ReturnData.error("无有效参数!⊙﹏⊙‖∣°");
        }

        JfRule jfRule = this.selectById(Integer.parseInt(id));

        if(jfRule == null){
            return ReturnData.error("未查询到该条积分规则!⊙﹏⊙‖∣°");
        }

        if(StringUtils.isNotBlank(type)
                && StringUtils.isNotBlank(score)
                && score != null
                && Integer.parseInt(score) > 0){

            if(type.equals("11")){
                jfRule.setScore(jfRule.getScore() + Integer.parseInt(score));
            } else if(type.equals("12")){
                jfRule.setScore(jfRule.getScore() - Integer.parseInt(score));
            } else {
                return ReturnData.error("请求参数有误[type]!⊙﹏⊙‖∣°");
            }
        }

        jfRule.setOperator(userName);
        if(StringUtils.isNotBlank(upperLimitScore)){
            jfRule.setUpperLimit(Integer.parseInt(upperLimitScore));
        }
        if(StringUtils.isNotBlank(lowerLimitScore)){
            jfRule.setLowerLimit(Integer.parseInt(lowerLimitScore));
        }
        if(upperLimitTime == null){
            jfRule.setUpperTimeLimit(null);
        }else if(StringUtils.isNotBlank(upperLimitTime)){
            jfRule.setUpperTimeLimit(Byte.parseByte(upperLimitTime));
        }

        if(lowerLimitTime == null){
            jfRule.setLowerTimeLimit(null);
        } else if(StringUtils.isNotBlank(lowerLimitTime)){
            jfRule.setLowerTimeLimit(Byte.parseByte(lowerLimitTime));
        }
        if(StringUtils.isNotBlank(note)){
            jfRule.setNote(note);
        }
        jfRule.setUpdateTime(new Date());

        int n = this.updateById(jfRule);
        if(n == 1){
            return ReturnData.success("积分规则修改成功！^_^");
        } else {
            return ReturnData.error("积分规则修改失败！>_<|||");
        }
    }

}
