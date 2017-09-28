package com.jusfoun.hookah.crowd.service.impl;

/**
 * Created by zhaoshuai on 2017/9/18.
 */

import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.*;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import com.jusfoun.hookah.crowd.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaoshuai on 2017/7/12.
 */
@Service
public class ReleaseServiceImpl extends GenericServiceImpl<ZbRequirement, String> implements ReleaseService {

    @Resource
    ZbAnnexService zbAnnexService;

    @Resource
    ZbRequireService zbRequireService;

    @Resource
    ZbTypeService zbTypeService;

    @Resource
    ZbProgramService zbProgramService;

    @Resource
    ZbRequireApplyService zbRequireApplyService;

    @Resource
    ZbCommentService zbCommentService;

    @Resource
    UserService userService;

    @Resource
    private ZbRequirementMapper zbRequirementMapper;

    @Resource
    public void setDao(ZbRequirementMapper zbRequirementMapper) {
        super.setDao(zbRequirementMapper);
    }

    /**
     * 数据众包--发布需求
     */
    public ReturnData insertRequirements(ZbRequirementVo vo){

        if(vo.getZbRequirement() != null){
            ZbRequirement ment = vo.getZbRequirement();
            ment.setStatus(Short.parseShort("0"));
            if(ment.getId() == null){
                ment.setAddOperator(vo.getZbRequirement().getUserId());
                ment.setAddTime(new Date());
                ment.setRewardMoney(Math.round(Double.valueOf(vo.getRewardMoney())*100));
                ment.setTrusteePercent(30);
                if(vo.getZbRequirement().getType() != null)
                    ment.setRequireSn(CommonUtils.getRequireSn("ZB",vo.getZbRequirement().getType().toString()));
                zbRequirementMapper.insertAndGetId(ment);

                if(vo.getAnnex().size() > 0){
                    for(ZbAnnex zbAnnex : vo.getAnnex()){
                        zbAnnex.setCorrelationId(ment.getId());
                        zbAnnex.setAddTime(new Date());
                        zbAnnex.setType(Short.parseShort("0"));
                        zbAnnexService.insert(zbAnnex);
                    }
                }
            }else{
                ZbRequirement zbRequirement = zbRequireService.selectById(ment.getId());
                if(zbRequirement != null){
                    if(zbRequirement.getStatus() == ZbContants.Zb_Require_Status.CHECK_NOT.getCode().shortValue()){
                        ment.setStatus(ZbContants.Zb_Require_Status.CHECK_NOT.getCode().shortValue());
                    }
                    ment.setRequireSn(zbRequirement.getRequireSn());
                    ment.setAddTime(zbRequirement.getAddTime());
                    ment.setAddOperator(zbRequirement.getAddOperator());
                    ment.setUpdateTime(new Date());
                    ment.setUpdateOperator(vo.getZbRequirement().getUserId());
                    ment.setRewardMoney(Math.round(Double.valueOf(vo.getRewardMoney())*100));
                    ment.setTrusteePercent(vo.getZbRequirement().getTrusteePercent());
                    super.updateById(ment);

                    List<Condition> filter = new ArrayList<>();
                    filter.add(Condition.eq("correlationId", ment.getId()));
                    zbAnnexService.deleteByCondtion(filter);
                    if(vo.getAnnex() != null){
                        if(vo.getAnnex().size() > 0){
                            for(ZbAnnex zbAnnex : vo.getAnnex()){
                                zbAnnex.setCorrelationId(ment.getId());
                                zbAnnex.setAddTime(new Date());
                                zbAnnex.setType(Short.parseShort("0"));
                                zbAnnexService.insert(zbAnnex);
                            }
                        }
                    }
                }
            }
            return ReturnData.success(vo);
        }else {
            return  ReturnData.error("发布需求失败");
        }
    }


    /**
     * 数据众包-发布需求--确认信息
     * @return
     */
    public ReturnData getRequirementInfo(String userId){
        Map<String, Object> map = new HashMap<>(6);
        List<Condition> filters = new ArrayList<>();
        List<Condition> filters1 = new ArrayList<>();
        if(StringUtils.isNotBlank(userId)){
            filters.add(Condition.eq("userId", userId));
        }
        filters.add(Condition.eq("status", 0));
        ZbRequirement zbRequirement = zbRequireService.selectOne(filters);
        if(zbRequirement != null){
            if(zbRequirement.getTag() != null){
                String[] strArray = null;
                strArray = zbRequirement.getTag().split(",");
                map.put("tag",strArray);
            }

            if(zbRequirement.getId() != null){
                filters1.add(Condition.eq("correlationId", zbRequirement.getId()));
            }
            List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filters1);
            map.put("zbRequirement",zbRequirement);
            map.put("zbRequirementFiles",zbAnnexes);
        }
        return ReturnData.success(map);
    }

    /**
     * 数据众包-发布需求--变更待审核状态需求
     * @return
     */
    public ReturnData getRequirementSubmit(Long id){
        ZbRequirement zbRequirement = zbRequireService.selectById(id);
        zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_CHECK.getCode().shortValue());
        List<Condition> filters = new ArrayList<>();
        if(StringUtils.isNotBlank(id.toString())){
            filters.add(Condition.eq("id", id));
        }
        int i = zbRequireService.updateByConditionSelective(zbRequirement, filters);
        return ReturnData.success(i);
    }

    /**
     * 数据众包-需求方--我的发布
     * @return
     */
    public ReturnData getUpdateReleaseStatus(Long id){

        Map<String, Object> map = new HashMap<>(6);
        List list = new ArrayList();
        List<Condition> filters = new ArrayList<>();
        List<Condition> filters1 = new ArrayList<>();
        List<Condition> filters2 = new ArrayList<>();
        if(StringUtils.isNotBlank(id.toString())){
            filters.add(Condition.eq("id", id));
        }
        ZbRequirement zbRequirement = zbRequireService.selectOne(filters);
        //我的发布-待审核状态
        if(zbRequirement != null){
            Short status = zbRequirement.getStatus();
            if(StringUtils.isNotBlank(status.toString())){
                Object info = null;
                switch (status){
                    case 1://待审核
                    case 2://审核未通过
                        info = requirementInfo(id).getData();
                        list.add(info);
                        break;
                    case 3: //审核通过,待托管赏金
                        info = requirementInfo(id).getData();
                        list.add(info);
                        break;
                    case 7: //待二次托管或报名结束
                        info = requirementInfo(id).getData();
                        if(StringUtils.isNotBlank(zbRequirement.getId().toString())){
                            filters1.add(Condition.eq("requirementId", zbRequirement.getId()));
                        }
                        ZbRequirementApply zbRequirementApply = zbRequireApplyService.selectOne(filters1);
                        list.add(info);
                        if(zbRequirementApply != null){
                            //计算报名人数
                            List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyService.selectList();
                            map.put("count",zbRequirementApplies.size());
                            list.add(map);
                            //已选中信息
                            if(zbRequirementApply.getStatus() == 1){
                                User user = zbRequirementMapper.selectReleaseInfo(zbRequirement.getUserId());
                                list.add(user);
                            }
                        }
                        break;
                    case 8: //工作中
                    case 9: //待验收
                    case 13://待评价
                    case 18://需方驳回
                        info = requirementInfo(id).getData();
                        list.add(info);
                        if(zbRequirement.getId() != null){
                            filters1.add(Condition.eq("requirementId", zbRequirement.getId()));
                            ZbProgram zbProgram = zbProgramService.selectOne(filters1);
                            if(zbProgram != null){
                                Map<String, Object> zpMap = new HashMap<>(6);
                                zpMap.put("zbProgram",zbProgram);
                                list.add(zpMap);
                                if(StringUtils.isNotBlank(zbProgram.getId().toString())){
                                    filters2.add(Condition.eq("correlationId", zbProgram.getId()));
                                }
                                filters2.add(Condition.eq("type", 1));
                                List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filters2);
                                if(zbAnnexes != null){
                                    Map<String, Object> zaMap = new HashMap<>(6);
                                    zaMap.put("zbAnnexes",zbAnnexes);
                                    list.add(zaMap);
                                }
                            }
                        }
                        break;
                    case 15://交易完成
                        info = requirementInfo(id).getData();
                        list.add(info);
                        if(zbRequirement.getId() != null){
                            filters1.add(Condition.eq("requirementId", zbRequirement.getId()));
                            ZbProgram zbProgram = zbProgramService.selectOne(filters1);
                            if(zbProgram != null){
                                Map<String, Object> zpMap = new HashMap<>(6);
                                zpMap.put("zbProgram",zbProgram);
                                list.add(zpMap);
                                if(StringUtils.isNotBlank(zbProgram.getId().toString())){
                                    filters2.add(Condition.eq("correlationId", zbProgram.getId()));
                                }
                                filters2.add(Condition.eq("type", 1));
                                List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filters2);
                                if(zbAnnexes != null){
                                    Map<String, Object> zaMap = new HashMap<>(6);
                                    zaMap.put("zbAnnexes",zbAnnexes);
                                    list.add(zaMap);
                                }
                                List<Condition> filters3 = new ArrayList<>();
                                if(StringUtils.isNotBlank(zbProgram.getId().toString())){
                                    filters3.add(Condition.eq("programId", zbProgram.getId()));
                                }
                                ZbComment zbComment = zbCommentService.selectOne(filters3);
                                Map<String, Object> zcMap = new HashMap<>(6);
                                zcMap.put("zbComment",zbComment);
                                list.add(zcMap);
                            }
                        }
                        break;
                    case 19://流标
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        try {
                            Date time1 = df.parse(df.format(date));//当前时间
                            Date time2 = df.parse(df.format(zbRequirement.getApplyDeadline()));//截止报名时间
                            if(time2.before(time1)){//截止报名时间小于当前时间----流标
                                info = requirementInfo(id).getData();
                                list.add(info);
                            }
                        } catch (Exception e) {
                            logger.error("获取时间有误", e);
                            return ReturnData.error("获取时间有误：" + e.getMessage());
                        }
                        break;
                    default:
                        list = null;
                        break;
                }
            }
            return ReturnData.success(list);
        }
        return ReturnData.error("查询错误");
    }

    //获取需求信息
    public ReturnData  requirementInfo(Long id){
        Map<String, Object> map = new HashMap<>(6);
        List<Condition> filters = new ArrayList<>();
        List<Condition> filters1 = new ArrayList<>();
        if(StringUtils.isNotBlank(id.toString())){
            filters.add(Condition.eq("id", id));
        }
        ZbRequirement zbRequirement = zbRequireService.selectOne(filters);
        if(zbRequirement != null){
            if(zbRequirement.getStatus() == 3 ||
                    zbRequirement.getStatus() == 7 ||
                        zbRequirement.getStatus() == 8 ||
                            zbRequirement.getStatus() == 9 ||
                            zbRequirement .getStatus() == 13 ||
                                zbRequirement.getStatus() == 18 ||
                                    zbRequirement.getStatus() == 15 ||
                                        zbRequirement.getStatus() == 19){
                if(zbRequirement.getTrusteePercent() != null){
                    String managedMoney = null;
                    managedMoney = String.valueOf(zbRequirement.getRewardMoney()*zbRequirement.getTrusteePercent());
                    map.put("managedMoney",managedMoney);
                }
            }
            if(zbRequirement.getTag() != null){
                String[] strArray = null;
                strArray = zbRequirement.getTag().split(",");
                map.put("tag",strArray);
            }

            if(zbRequirement.getId() != null){
                filters1.add(Condition.eq("correlationId", zbRequirement.getId()));
            }
            List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filters1);
            map.put("zbRequirement",zbRequirement);
            map.put("zbRequirementFiles",zbAnnexes);
            return ReturnData.success(map);
        }
        return ReturnData.error("未查询到需求信息");
    }

    /**
     * 数据众包-需求方-添加验收意见
     * @return
     */
    public ReturnData getAcceptanceAdvice(Short status, String checkAdvice, Long id){
        if(id != null){
            List<Condition> filters = new ArrayList<>();
            if(StringUtils.isNotBlank(id.toString())){
                filters.add(Condition.eq("id", id));
            }
            ZbProgram zbProgram = zbProgramService.selectOne(filters);
            zbProgram.setStatus(status);
            zbProgram.setCheckAdvice(checkAdvice);
            int i = zbProgramService.updateByCondition(zbProgram, filters);
            return ReturnData.success(i);
        }
        return ReturnData.error("添加验收成果失败！");
    }

    /**
     * 数据众包-需求方-添加对服务商的评价
     * @return
     */
    public ReturnData getInsertEvaluation(int level, String content, Long programId, String userId){
        if(programId != null){
            List<Condition> filters = new ArrayList<>();
            if(StringUtils.isNotBlank(programId.toString())){
                filters.add(Condition.eq("programId", programId));
            }
            ZbProgram zbProgram = zbProgramService.selectById(programId);
            ZbComment zbComment = new ZbComment();
            zbComment.setRequirementId(zbProgram.getRequirementId());
            zbComment.setUserId(userId);
            zbComment.setProgramId(programId);
            zbComment.setLevel(level);
            zbComment.setContent(content);
            zbComment.setAddTime(new Date());
            zbComment.setUserType(2);
            ZbComment insert = zbCommentService.insert(zbComment);
            return ReturnData.success(insert);
        }
        return ReturnData.error("添加评价意见失败！");
    }

    /**
     * 数据众包-需求方-取消需求
     * @return
     */
    public ReturnData deleteRequirement(Long id){
        if(id != null){
            ZbRequirement zbRequirement = zbRequireService.selectById(id);
            List<Condition> filter = new ArrayList<>();
            /*if(StringUtils.isNotBlank(zbRequirement.getId().toString())){
                filter.add(Condition.eq("correlationId", zbRequirement.getId()));
            }
            zbAnnexService.deleteByCondtion(filter);
            int i = zbRequireService.delete(id);*/
            if(StringUtils.isNotBlank(id.toString())){
                filter.add(Condition.eq("id", id));
            }
            zbRequirement.setStatus(Short.parseShort("18"));
            int i = zbRequireService.updateByConditionSelective(zbRequirement, filter);
            return ReturnData.success(i);
        }
        return  ReturnData.error("取消需求信息失败！");
    }
}
