package com.jusfoun.hookah.crowd.service.impl;

/**
 * Created by zhaoshuai on 2017/9/18.
 */

import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.*;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import com.jusfoun.hookah.crowd.util.CommonUtils;
import com.jusfoun.hookah.crowd.util.DateUtil;
import com.jusfoun.hookah.crowd.util.PayConfiguration;
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
    PayService payService;

    @Resource
    ZbTrusteeRecordService zbTrusteeRecordService;

    @Resource
    MgZbRequireStatusService mgZbRequireStatusService;

    @Resource
    MgZbProviderService mgZbProviderService;

    @Resource
    private ZbRequirementMapper zbRequirementMapper;

    @Resource
    ZbRequireApplyWebsiteService zbRequireApplyWebsiteService;

    @Resource
    public void setDao(ZbRequirementMapper zbRequirementMapper) {
        super.setDao(zbRequirementMapper);
    }

    /**
     * 数据众包--发布需求
     */
    public ReturnData insertRequirements(ZbRequirementVo vo) {

        if (vo.getZbRequirement() != null) {
            ZbRequirement ment = vo.getZbRequirement();
            ment.setStatus(Short.parseShort("0"));
            if (ment.getId() == null) {
                ment.setAddOperator(vo.getZbRequirement().getUserId());
                ment.setAddTime(new Date());
                ment.setRewardMoney(Math.round(Double.valueOf(vo.getRewardMoney()) * 100));
                ment.setTrusteePercent(30);
                if (vo.getZbRequirement().getType() != null)
                    ment.setRequireSn(CommonUtils.getRequireSn("ZB", vo.getZbRequirement().getType().toString()));
                zbRequirementMapper.insertAndGetId(ment);
                //mongo中添加需求发布时间
                mgZbRequireStatusService.setRequireStatusInfo(ment.getRequireSn(), ZbContants.ADDTIME, DateUtil.getSimpleDate(ment.getAddTime()));

                if (vo.getAnnex().size() > 0) {
                    for (ZbAnnex zbAnnex : vo.getAnnex()) {
                        zbAnnex.setCorrelationId(ment.getId());
                        zbAnnex.setAddTime(new Date());
                        zbAnnex.setType(Short.parseShort("0"));
                        zbAnnexService.insert(zbAnnex);
                    }
                }
            } else {
                ZbRequirement zbRequirement = zbRequireService.selectById(ment.getId());
                if (zbRequirement != null) {
                    if (zbRequirement.getStatus() == ZbContants.Zb_Require_Status.CHECK_NOT.getCode().shortValue()) {
                        ment.setStatus(ZbContants.Zb_Require_Status.CHECK_NOT.getCode().shortValue());
                    }
                    ment.setRequireSn(zbRequirement.getRequireSn());
                    ment.setAddTime(zbRequirement.getAddTime());
                    ment.setAddOperator(zbRequirement.getAddOperator());
                    ment.setUpdateTime(new Date());
                    ment.setUpdateOperator(vo.getZbRequirement().getUserId());
                    ment.setRewardMoney(Math.round(Double.valueOf(vo.getRewardMoney()) * 100));
                    ment.setTrusteePercent(zbRequirement.getTrusteePercent());
                    super.updateById(ment);

                    List<Condition> filter = new ArrayList<>();
                    filter.add(Condition.eq("correlationId", ment.getId()));
                    zbAnnexService.deleteByCondtion(filter);
                    if (vo.getAnnex() != null) {
                        if (vo.getAnnex().size() > 0) {
                            for (ZbAnnex zbAnnex : vo.getAnnex()) {
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
        } else {
            return ReturnData.error("发布需求失败");
        }
    }


    /**
     * 数据众包-发布需求--确认信息
     *
     * @return
     */
    public ReturnData getRequirementInfo(String userId) {
        Map<String, Object> map = new HashMap<>(6);
        List<Condition> filters = new ArrayList<>();
        List<Condition> filters1 = new ArrayList<>();
        if (StringUtils.isNotBlank(userId)) {
            filters.add(Condition.eq("userId", userId));
        }
        filters.add(Condition.eq("status", 0));
        ZbRequirement zbRequirement = zbRequireService.selectOne(filters);
        if (zbRequirement != null) {
            if (zbRequirement.getTag() != null) {
                String[] strArray = null;
                strArray = zbRequirement.getTag().split(",");
                map.put("tag", strArray);
            }

            if (zbRequirement.getId() != null) {
                filters1.add(Condition.eq("correlationId", zbRequirement.getId()));
            }
            List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filters1);

            map.put("zbRequirement", zbRequirement);
            map.put("zbRequirementFiles", zbAnnexes);
        }
        //查询联系人信息
        User user = zbRequirementMapper.selectReleaseInfo(userId);
        map.put("user", user != null ? user : new User());
        return ReturnData.success(map);
    }

    /**
     * 数据众包-发布需求--变更待审核状态需求
     *
     * @return
     */
    public ReturnData getRequirementSubmit(Long id) {
        ZbRequirement zbRequirement = zbRequireService.selectById(id);
        zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_CHECK.getCode().shortValue());
        List<Condition> filters = new ArrayList<>();
        if (StringUtils.isNotBlank(id.toString())) {
            filters.add(Condition.eq("id", id));
        }
        int i = zbRequireService.updateByConditionSelective(zbRequirement, filters);
        return ReturnData.success(i);
    }

    /**
     * 数据众包-需求方--我的发布
     *
     * @return
     */
    public ReturnData getUpdateReleaseStatus(Long id) {

        Map<String, Object> map = new HashMap<>(6);
        List<Condition> filters = new ArrayList<>();
        List<Condition> filters1 = new ArrayList<>();
        List<Condition> filters2 = new ArrayList<>();
        List<Condition> filters3 = new ArrayList<>();
        if (StringUtils.isNotBlank(id.toString())) {
            filters.add(Condition.eq("id", id));
        }
        ZbRequirement zbRequirement = zbRequireService.selectOne(filters);
        String managedMoney = null;
        List<ZbAnnex> zbAnnexes = null;
        String[] strArray = null;
        //我的发布-待审核状态
        if (zbRequirement != null) {
            //---------
            if (zbRequirement.getStatus() == 3 ||
                    zbRequirement.getStatus() == 5 ||
                    zbRequirement.getStatus() == 6 ||
                    zbRequirement.getStatus() == 7 ||
                    zbRequirement.getStatus() == 8 ||
                    zbRequirement.getStatus() == 9 ||
                    zbRequirement.getStatus() == 10 ||
                    zbRequirement.getStatus() == 13 ||
                    zbRequirement.getStatus() == 14 ||
                    zbRequirement.getStatus() == 15 ||
                    zbRequirement.getStatus() == 16 ||
                    zbRequirement.getStatus() == 19) {
                if (zbRequirement.getTrusteePercent() != null) {
                    managedMoney = String.valueOf(zbRequirement.getRewardMoney() * zbRequirement.getTrusteePercent());
                }
            }
            //自定义标签
            if (zbRequirement.getTag() != null) {
                strArray = zbRequirement.getTag().split(",");
            }
            if (zbRequirement.getId() != null) {
                filters1.add(Condition.eq("correlationId", zbRequirement.getId()));
            }
            filters1.add(Condition.eq("type", 0));
            //需求附件
            zbAnnexes = zbAnnexService.selectList(filters1);

            //选中人信息
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(zbRequirement.getId().toString())) {
                filters2.add(Condition.eq("requirementId", zbRequirement.getId()));
            }
            ZbRequirementApply zbRequirementApply = zbRequireApplyService.selectOne(filters2);
            int count = 0;
            ZbRequirementApply apply = null;
            MgZbProvider user = null;
            if (zbRequirementApply != null) {
                //计算报名人数
                List<Condition> filters5 = new ArrayList<>();
                filters5.add(Condition.eq("requirementId", zbRequirement.getId()));
                List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyService.selectList(filters5);
                count = zbRequirementApplies.size();
                //已选中信息
                List<Condition> filters6 = new ArrayList<>();
                filters6.add(Condition.eq("requirementId", zbRequirement.getId()));
                filters6.add(Condition.notIn("status", new Short[]{0, 2}));
                apply = zbRequireApplyService.selectOne(filters6);
                if (apply != null) {
                    //user = zbRequirementMapper.selectReleaseInfo(apply.getUserId());
                    user = mgZbProviderService.selectById(apply.getUserId());
                }
            }

            //获取状态时间
            MgZbRequireStatus byRequirementSn = mgZbRequireStatusService.getByRequirementSn(zbRequirement.getRequireSn());
            map.put("byRequirementSn", byRequirementSn);

            Short status = zbRequirement.getStatus();
            if (StringUtils.isNotBlank(status.toString())) {
                switch (status) {
                    case 1://待审核
                    case 2://审核未通过
                        map.put("tag", strArray);
                        map.put("zbRequirement", zbRequirement);
                        map.put("zbRequirementFiles", zbAnnexes);
                        break;
                    case 3: //审核通过,待托管赏金
                        map.put("tag", strArray);
                        map.put("managedMoney", managedMoney);
                        map.put("zbRequirement", zbRequirement);
                        map.put("zbRequirementFiles", zbAnnexes);
                        break;
                    case 5:
                    case 6:
                    case 7: //待二次托管或报名结束
                        map.put("tag", strArray);
                        map.put("managedMoney", managedMoney);
                        map.put("zbRequirement", zbRequirement);
                        map.put("zbRequirementFiles", zbAnnexes);
                        map.put("count", count > 0 ? count : 0);
                        if (zbRequirementApply != null) {
                            //计算报名人数
                            if (apply != null) {
                                map.put("zbProgram", "");
                                map.put("programFiles", "");
                                //报名时间
                                map.put("applyTime", df.format(apply.getAddTime()));
                                //已选中信息
                                map.put("user", user != null ? user : "");
                            }
                        }
                        break;
                    case 8:  //工作中
                    case 9:  //待平台验收
                    case 10: //待需方验收
                    case 13: //待评价
                    case 14: //交易取消
                    case 16: //待退款
                        map.put("tag", strArray);
                        map.put("managedMoney", managedMoney);
                        map.put("zbRequirement", zbRequirement);
                        map.put("zbRequirementFiles", zbAnnexes);
                        if (zbRequirementApply != null) {
                            //计算报名人数
                            map.put("count", count);
                            if (apply != null) {
                                //报名时间
                                map.put("applyTime", df.format(apply.getAddTime()));
                                //已选中信息
                                map.put("user", user != null ? user : "");
                            }
                        }
                        if (zbRequirement.getId() != null) {
                            ZbProgram zbProgram = zbProgramService.selectOne(filters2);
                            if (zbProgram != null) {
                                map.put("zbProgram", zbProgram != null ? zbProgram : "");
                                if (StringUtils.isNotBlank(zbProgram.getId().toString())) {
                                    filters3.add(Condition.eq("correlationId", zbProgram.getId()));
                                }
                                filters3.add(Condition.eq("type", 1));
                                List<ZbAnnex> programFiles = zbAnnexService.selectList(filters3);
                                if (zbAnnexes != null) {
                                    map.put("programFiles", programFiles != null ? programFiles : "");
                                }
                                //服务商的评价
                                if (zbRequirement.getStatus().equals(ZbContants.Zb_Require_Status.WAIT_PJ.getCode().shortValue())) {
                                    List<Condition> filters4 = new ArrayList<>();
                                    if (StringUtils.isNotBlank(zbProgram.getId().toString())) {
                                        filters4.add(Condition.eq("programId", zbProgram.getId()));
                                        filters4.add(Condition.eq("userType", 1));
                                    }
                                    ZbComment zbComment = zbCommentService.selectOne(filters4);
                                    map.put("zbComment", zbComment != null ? zbComment : "");
                                }
                            }
                        }
                        break;
                    case 15://交易完成
                        map.put("tag", strArray);
                        map.put("managedMoney", managedMoney);
                        map.put("zbRequirement", zbRequirement);
                        map.put("zbRequirementFiles", zbAnnexes);
                        //计算报名人数
                        if (zbRequirementApply != null) {
                            map.put("count", count);
                            if (apply != null) {
                                map.put("applyTime", df.format(apply.getAddTime()));
                                map.put("user", user != null ? user : "");
                            }
                        }
                        if (zbRequirement.getId() != null) {
                            ZbProgram zbProgram = zbProgramService.selectOne(filters2);
                            if (zbProgram != null) {
                                map.put("zbProgram", zbProgram != null ? zbProgram : "");
                                if (StringUtils.isNotBlank(zbProgram.getId().toString())) {
                                    filters3.add(Condition.eq("correlationId", zbProgram.getId()));
                                }
                                filters2.add(Condition.eq("type", 1));
                                List<ZbAnnex> programFiles = zbAnnexService.selectList(filters3);
                                if (zbAnnexes != null) {
                                    map.put("programFiles", programFiles != null ? programFiles : "");
                                }
                                //服务商的平价
                                List<Condition> filters4 = new ArrayList<>();
                                if (StringUtils.isNotBlank(zbProgram.getId().toString())) {
                                    filters4.add(Condition.eq("programId", zbProgram.getId()));
                                    filters4.add(Condition.eq("userType", 1));
                                }
                                ZbComment zbComment = zbCommentService.selectOne(filters4);
                                map.put("zbComment", zbComment != null ? zbComment : "");
                                //对服务商的评价
                                List<Condition> filters7 = new ArrayList<>();
                                if (StringUtils.isNotBlank(zbProgram.getId().toString())) {
                                    filters7.add(Condition.eq("programId", zbProgram.getId()));
                                    filters7.add(Condition.eq("userType", 2));
                                }
                                ZbComment zbDemandComment = zbCommentService.selectOne(filters7);
                                map.put("zbDemandComment", zbDemandComment != null ? zbDemandComment : "");
                            }
                        }
                        break;
                    case 19://流标-到期
                        Date date = new Date();
                        try {
                            Date time1 = df.parse(df.format(date));//当前时间
                            Date time2 = df.parse(df.format(zbRequirement.getApplyDeadline()));//截止报名时间
                            if (time2.before(time1)) {//截止报名时间小于当前时间----流标
                                map.put("tag", strArray);
                                map.put("managedMoney", managedMoney);
                                map.put("zbRequirement", zbRequirement);
                                map.put("zbRequirementFiles", zbAnnexes);
                            }
                        } catch (Exception e) {
                            logger.error("获取时间有误", e);
                            return ReturnData.error("获取时间有误：" + e.getMessage());
                        }
                        break;
                    default:
                        map = null;
                        break;
                }
            }
            return ReturnData.success(map);
        }
        return ReturnData.error("查询错误");
    }


    /**
     * 数据众包-需求方-添加验收意见
     *
     * @return
     */
    public ReturnData getAcceptanceAdvice(Short status, String checkAdvice, Long id) {
        if (id != null) {
            List<Condition> filters = new ArrayList<>();
            List<Condition> filters1 = new ArrayList<>();
            if (StringUtils.isNotBlank(id.toString())) {
                filters.add(Condition.eq("id", id));
            }
            ZbProgram program = zbProgramService.selectOne(filters);
            if (program != null) {
                program.setStatus(status);
                program.setCheckAdvice(checkAdvice);
                zbProgramService.updateByCondition(program, filters);

                //验收之后更改需求状态
                ZbRequirement zbRequirement = zbRequireService.selectById(program.getRequirementId());
                if (zbRequirement.getId() != null) {
                    filters1.add(Condition.eq("id", zbRequirement.getId()));
                }
                if (zbRequirement != null) {

                    //需方验收通过
                    if (program.getStatus().equals(ZbContants.Program_Status.PROGRAM_SUCCESS.getCode())) {
                        zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_FK.getCode().shortValue());

                        //添加需求验收时间
                        mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.REQUIREDACCEPTTIME, DateUtils.toDefaultNowTime());

                        //修改报名状态为待付款
                        zbRequireApplyWebsiteService.updateStatus(program.getApplyId(), ZbContants.ZbRequireMentApplyStatus.PAYMENT_ING.getCode());

                    } else if (program.getStatus().equals(ZbContants.Program_Status.PROGRAM_FAIL.getCode())) {//验收不通过
                        zbRequirement.setStatus(ZbContants.Zb_Require_Status.TWO_WORKING.getCode().shortValue());

//                        //修改报名状态为工作中
//                        zbRequireApplyWebsiteService.updateStatus(program.getApplyId(),ZbContants.ZbRequireMentApplyStatus.WORKING.getCode());

                    } else if (program.getStatus().equals(ZbContants.Program_Status.PROGRAM_REJECT.getCode())) {//验收驳回
                        zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_TK.getCode().shortValue());

                        //修改报名状态为驳回失败(交易取消)
                        zbRequireApplyWebsiteService.updateStatus(program.getApplyId(), ZbContants.ZbRequireMentApplyStatus.DEAL_CANCE.getCode());

                    }
                    zbRequireService.updateByCondition(zbRequirement, filters1);
                    if (zbRequirement.getStatus().equals(ZbContants.Zb_Require_Status.WAIT_TK.code.shortValue())) {
                        //待退款时间
                        mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.TOBEREFUNDEDTIME, DateUtils.toDefaultNowTime());
                    }
                }
            }
            return ReturnData.success("添加验收成果成功！");
        }
        return ReturnData.error("添加验收成果失败！");
    }

    /**
     * 数据众包-需求方-添加对服务商的评价
     *
     * @return
     */
    public ReturnData getInsertEvaluation(int level, String content, Long programId, String userId) {
        if (programId != null) {
            List<Condition> filters = new ArrayList<>();
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

            //修改报名状态为待评价
            zbRequireApplyWebsiteService.updateStatus(zbProgram.getApplyId(), ZbContants.ZbRequireMentApplyStatus.COMMENT_ING.getCode());

            //评价完之后变更需求状态为交易完成
            ZbRequirement zbRequirement = zbRequireService.selectById(zbProgram.getRequirementId());
            if (zbRequirement.getId() != null) {
                filters.add(Condition.eq("id", zbRequirement.getId()));
            }
            if (zbRequirement != null) {
                //需求方评价时间
                mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.COMMENTTIME, DateUtil.getSimpleDate(zbComment.getAddTime()));
                if (zbComment != null) {
                    if (zbComment.getUserType() == 2) {
                        zbRequirement.setStatus(ZbContants.Zb_Require_Status.ZB_SUCCESS.getCode().shortValue());
                        zbRequireService.updateByCondition(zbRequirement, filters);
                    }
                }
            }
            return ReturnData.success(insert);
        }
        return ReturnData.error("添加评价意见失败！");
    }

    /**
     * 数据众包-需求方-取消需求
     *
     * @return
     */
    public ReturnData deleteRequirement(Long id) {
        if (id != null) {
            ZbRequirement zbRequirement = zbRequireService.selectById(id);
            List<Condition> filter = new ArrayList<>();
            /*if(StringUtils.isNotBlank(zbRequirement.getId().toString())){
                filter.add(Condition.eq("correlationId", zbRequirement.getId()));
            }
            zbAnnexService.deleteByCondtion(filter);
            int i = zbRequireService.delete(id);*/
            if (StringUtils.isNotBlank(id.toString())) {
                filter.add(Condition.eq("id", id));
            }
            zbRequirement.setStatus(Short.parseShort("18"));
            int i = zbRequireService.updateByConditionSelective(zbRequirement, filter);
            return ReturnData.success(i);
        }
        return ReturnData.error("取消需求信息失败！");
    }

    public ReturnData updateMoneyStatus(String requireSn) {
        return ReturnData.success();
    }


    public String getManagedMoney(String requirementId, String trusteePercent) {

        String html = null;

        try {

            List<Condition> filter = new ArrayList<>();
            filter.add(Condition.eq("id", requirementId));
            ZbRequirement zbRequirement = zbRequireService.selectOne(filter);
            if (zbRequirement != null) {
                if (zbRequirement.getStatus().equals(Short.parseShort("3"))) {
                    if (com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(trusteePercent) && !trusteePercent.equals("30")) {
                        zbRequirement.setId(zbRequirement.getId());
                        zbRequirement.setTrusteePercent(Integer.parseInt(trusteePercent));
                        zbRequireService.updateByIdSelective(zbRequirement);
                    }
                }

                List<Condition> zbTrusFilter = new ArrayList<>();
                zbTrusFilter.add(Condition.eq("requirementId", requirementId));
                zbTrusFilter.add(Condition.eq("status", Short.parseShort("0")));
                zbTrusFilter.add(Condition.eq("userId", getCurrentUser().getUserId()));
                ZbTrusteeRecord zbTrusteeRecord = zbTrusteeRecordService.selectOne(zbTrusFilter);
                if (zbTrusteeRecord != null) {
                    if (zbRequirement.getStatus().equals(Short.parseShort("3"))) {
                        //第一次托管
                        zbTrusteeRecord.setActualMoney(zbRequirement.getRewardMoney() * zbRequirement.getTrusteePercent());
                        zbTrusteeRecord.setTrusteeNum(1);
                    } else if (zbRequirement.getStatus().equals(Short.parseShort("7"))) {
                        //第二次托管
                        zbTrusteeRecord.setActualMoney(zbRequirement.getRewardMoney() * (100 - zbRequirement.getTrusteePercent()));
                        zbTrusteeRecord.setTrusteeNum(2);
                    } else {
                        return "状态有误";
                    }
                    zbTrusteeRecordService.updateById(zbTrusteeRecord);

                } else {
                    zbTrusteeRecord = new ZbTrusteeRecord();
                    zbTrusteeRecord.setUserId(zbRequirement.getUserId());
                    zbTrusteeRecord.setRequirementId(Long.parseLong(requirementId));
                    zbTrusteeRecord.setRewardMoney(zbRequirement.getRewardMoney());
                    zbTrusteeRecord.setTrusteePercent(zbRequirement.getTrusteePercent());
                    if (zbRequirement.getStatus().equals(Short.parseShort("3"))) {
                        //第一次托管
                        zbTrusteeRecord.setActualMoney(zbRequirement.getRewardMoney() * zbRequirement.getTrusteePercent());
                        zbTrusteeRecord.setTrusteeNum(1);
                    } else if (zbRequirement.getStatus().equals(Short.parseShort("7"))) {
                        //第二次托管
                        zbTrusteeRecord.setActualMoney(zbRequirement.getRewardMoney() * (100 - zbRequirement.getTrusteePercent()));
                        zbTrusteeRecord.setTrusteeNum(2);
                    } else {
                        return "状态有误";
                    }
                    zbTrusteeRecord.setSerialNo(CommonUtils.getRequireSn("ZB", "ZFB"));
                    zbTrusteeRecord.setStatus(ZbContants.Trustee_Record_Status.RECORD_INITIAL.getCode());
                    zbTrusteeRecord.setAddTime(new Date());
                    zbTrusteeRecordService.insert(zbTrusteeRecord);
                }

                if (zbRequirement.getStatus().equals(Short.parseShort("3"))) {
                    //添加悬赏费用托管时间
                    mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.TRUSTEETIME, DateUtil.getSimpleDate(new Date()));
                }

                html = payService.toPayByZFB(zbRequirement.getRequireSn(), zbTrusteeRecord.getSerialNo(), zbTrusteeRecord.getActualMoney() / 100, zbTrusteeRecord.getTrusteeNum().toString(), PayConfiguration.ALIPAY_NOTIFY_URL, PayConfiguration.ALIPAY_RETURN_URL);
                System.out.print(html);
            }

        } catch (Exception e) {
            logger.error("支付回调后续处理异常{}", e);
        }

        return html;
    }
}
