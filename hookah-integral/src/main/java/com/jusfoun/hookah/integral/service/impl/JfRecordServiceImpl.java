package com.jusfoun.hookah.integral.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.jf.JfRecordMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.bo.JfBo;
import com.jusfoun.hookah.core.domain.jf.JfOverdueDetails;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.domain.vo.JfUserVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.integral.contants.JfContants;
import com.jusfoun.hookah.rpc.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 积分服务
 *
 * @author:jsshao
 * @date: 2017-3-17
 */

@Service
public class JfRecordServiceImpl extends GenericServiceImpl<JfRecord, Long> implements JfRecordService {

    @Resource
    private JfRecordMapper jfRecordMapper;

    @Resource
    public void setDao(JfRecordMapper jfRecordMapper) {
        super.setDao(jfRecordMapper);
    }

    @Resource
    CacheService cacheService;

    @Resource
    UserService userService;

    @Resource
    JfOverdueDetailsService jfOverdueDetailsService;

    @Resource
    MqSenderService mqSenderService;

    @Resource
    JfRuleService jfRuleService;

    @CacheEvict(value = "personUseJfSum", key = "#jfRecord.getUserId()")
    @Override
    public int insertAndGetId(JfRecord jfRecord) {
        return jfRecordMapper.insertAndGetId(jfRecord);
    }

    @Override
    public ReturnData getJfRecord(Integer pageNumberNew, Integer pageSizeNew, String userId, String type) throws Exception {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<JfRecord> pagination = new Pagination<>();
        Pagination<JfOverdueDetails> overPages = new Pagination<>();

        if (!type.equals("3")) {

            // 根据用户userId 和 请求类型 获取所有积分记录
            PageHelper.startPage(pageNumberNew, pageSizeNew);
            List<JfRecord> jfList = jfRecordMapper.selectListByUserIdAndType(userId, type);
            PageInfo<JfRecord> page = new PageInfo<JfRecord>(jfList);

            pagination.setTotalItems(page.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(page.getList());

            returnData.setData(pagination);
        } else {

            // 获取处理过的过期积分
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", userId));

            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));

            overPages = jfOverdueDetailsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            returnData.setData(overPages);

        }

        Integer useJf = cacheService.getUseScoreByUserId(userId);

        returnData.setData2(useJf == null ? 0 : useJf);

        return returnData;
    }

    @Override
    public ReturnData selectListByUserInfo(String pageNum, String pageSize, String userName, String userType, String mobile) throws Exception {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<User> pages = new Pagination<>();
        Pagination<JfUserVo> jfUserVoPag = new Pagination<>();

        List<Condition> filters = new ArrayList<>();

        if (StringUtils.isNotBlank(userName)) {
            filters.add(Condition.like("userName", userName));
        }
        if (StringUtils.isNotBlank(mobile)) {
            filters.add(Condition.eq("mobile", mobile));
        }
        if (StringUtils.isNotBlank(userType)) {
            filters.add(Condition.eq("userType", Integer.parseInt(userType)));
        }

        List<OrderBy> orderBys = new ArrayList<>();
        orderBys.add(OrderBy.desc("addTime"));

        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(pageNum)) {
            pageNumberNew = Integer.parseInt(pageNum);
        }

        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }

        List<JfUserVo> jfUserVoList = new ArrayList<>();

        pages = userService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        if (pages.getList() != null && pages.getList().size() > 0) {
            pages.getList().parallelStream().forEachOrdered(x -> {

                List<Condition> jfilters = new ArrayList<>();
                jfilters.add(Condition.eq("userId", x.getUserId()));
                jfilters.add(Condition.eq("expire", Short.parseShort("0")));

                List<JfRecord> jfRecordList = selectList(jfilters);
                JfUserVo jfUserVo = new JfUserVo();
                BeanUtils.copyProperties(x, jfUserVo);

                jfUserVo.setUseJf(jfRecordList.parallelStream().mapToInt(y -> y.getScore()).sum());

                jfUserVo.setExchangeJf(jfRecordList.parallelStream().filter(JfRecord
                        -> JfRecord.getAction().equals(Short.parseShort("2"))).mapToInt(JfRecord::getScore).sum());

                jfUserVoList.add(jfUserVo);
            });
        }

//        jfUserVoList.stream().sorted((JfUserVo m, JfUserVo n) -> m.getAddTime().compareTo(n.getAddTime()));
        jfUserVoPag.setTotalItems(pages.getTotalItems());
        jfUserVoPag.setPageSize(pageSizeNew);
        jfUserVoPag.setCurrentPage(pageNumberNew);
        jfUserVoPag.setList(jfUserVoList);

        returnData.setData(jfUserVoPag);

        return returnData;
    }

    @Override
    public ReturnData selectJfRecordListByUserId(String pageNum, String pageSize,
                                                 String userId, String action, String sourceId, String startTime, String endTime) throws Exception {

        Pagination<JfRecord> page = new Pagination<>();
        List<Condition> filters = new ArrayList();
        List<OrderBy> orderBys = new ArrayList();
        orderBys.add(OrderBy.desc("addTime"));

        if (StringUtils.isNotBlank(userId)) {
            filters.add(Condition.eq("userId", userId));
        } else {

            return ReturnData.error("用户标识不能为空！^_^");
        }

        if (StringUtils.isNotBlank(action)) {
            filters.add(Condition.eq("action", action));
        }

        if (StringUtils.isNotBlank(sourceId)) {
            filters.add(Condition.eq("sourceId", sourceId));
        }

        if (StringUtils.isNotBlank(startTime)) {
            filters.add(Condition.ge("addTime", startTime));
        }

        if (StringUtils.isNotBlank(endTime)) {
            filters.add(Condition.le("addTime", DateUtils.transferTime(endTime)));
        }

        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(pageNum)) {
            pageNumberNew = Integer.parseInt(pageNum);
        }

        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }

        page = this.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        return ReturnData.success(page);
    }

    @Override
    public ReturnData selectOneByUserId(String userId) throws Exception {

        if (!StringUtils.isNotBlank(userId)) {
            return ReturnData.error("参数不能为空");
        }

        User user = userService.selectById(userId);
        if (user == null) {
            return ReturnData.error("请求用户信息不存在");
        }

        JfUserVo jfUserVo = new JfUserVo();

        BeanUtils.copyProperties(user, jfUserVo);

        List<Condition> jfilters = new ArrayList<>();
        jfilters.add(Condition.eq("userId", userId));
        jfilters.add(Condition.eq("expire", Short.parseShort("0")));
        List<JfRecord> jfRecordList = selectList(jfilters);
        if (jfRecordList != null) {
            jfUserVo.setUseJf(jfRecordList.parallelStream().mapToInt(y -> y.getScore()).sum());

            jfUserVo.setExchangeJf(jfRecordList.parallelStream().filter(JfRecord
                    -> JfRecord.getAction().equals(Short.parseShort("2"))).mapToInt(JfRecord::getScore).sum());
        }

        return ReturnData.success(jfUserVo);
    }

    @CacheEvict(value = "personUseJfSum", key = "#userId")
    @Override
    public ReturnData optJf(String userId, String optType, String score, String note, String operatorId) throws Exception {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        if (!StringUtils.isNotBlank(userId)
                || !StringUtils.isNotBlank(optType)) {
            return ReturnData.error("参数不能为空！^_^");
        }

        if (Integer.parseInt(score) <= 0) {
            return ReturnData.error("积分变动值应大于0！^_^");
        }

        if (optType.equals("11") || optType.equals("12")) {

            List<String> list = new ArrayList<>();

            Arrays.asList(userId.split(",")).parallelStream().forEach((String uid) -> {

                User user = userService.selectById(uid);
                if (user == null) {
                    returnData.setCode(ExceptionConst.Error);
                    returnData.setMessage("用户不存在！^_^");
                    logger.info("用户不存在！^_^");
                } else {

                    if (optType.equals("11")) {

                        // optType 归到source_id中去 增加为11 减少为12
                        // action  admin的action为3

//                        JfRecord jfRecord = new JfRecord();
//                        jfRecord.setUserId(uid);
//                        jfRecord.setSourceId(Byte.parseByte(optType));
//                        jfRecord.setAction(Byte.parseByte("3"));
//                        jfRecord.setScore(Integer.parseInt(score));
//                        jfRecord.setNote(note);
//                        jfRecord.setExpire(Byte.parseByte("0"));
//                        jfRecord.setAddTime(new Date());
//                        jfRecord.setOperator(operatorId);
//                        jfRecord.setAddDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
//                        jfRecord.setActionDesc("管理员操作");

                        int n = insertAndGetId(
                                new JfRecord(
                                    uid,
                                    Byte.parseByte(optType),
                                    Byte.parseByte("3"),
                                    Integer.parseInt(score),
                                    note,
                                    Byte.parseByte("0"),
                                    new Date(),
                                    operatorId,
                                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")),
                                    "管理员操作"));

                        if (n == 1) {
                            logger.info("用户【" + user.getUserName() + "】操作积分成功！");
                            list.add("用户【" + user.getUserName() + "】操作积分成功！");
                        } else {
                            logger.info("用户【" + user.getUserName() + "】操作积分失败！");
                            list.add("用户【" + user.getUserName() + "】操作积分失败！");
                        }

                    } else if (optType.equals("12")) {

                        List<Condition> jfilters = new ArrayList<>();
                        jfilters.add(Condition.eq("userId", uid));
                        jfilters.add(Condition.eq("expire", Short.parseShort("0")));
                        List<JfRecord> jfRecordList = selectList(jfilters);
                        if (jfRecordList == null || jfRecordList.size() <= 0) {
                            logger.info("用户【" + user.getUserName() + "】可用积分不足！");
                            list.add("用户【" + user.getUserName() + "】可用积分不足！");
                        } else {
                            if (jfRecordList.parallelStream().mapToInt(v -> v.getScore()).sum() < Math.abs(Integer.parseInt(score))) {
                                logger.info("用户【" + user.getUserName() + "】可用积分不足！");
                                list.add("用户【" + user.getUserName() + "】可用积分不足！");
                            } else {
                                // optType 归到source_id中去 增加为11 减少为12
                                // action  admin的action为3
                                int n = insertAndGetId(
                                        new JfRecord(
                                                uid,
                                                Byte.parseByte(optType),
                                                Byte.parseByte("3"),
                                                0 - Integer.parseInt(score),
                                                note,
                                                Byte.parseByte("0"),
                                                new Date(),
                                                operatorId,
                                                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")),
                                                "管理员操作"));

                                if (n == 1) {
                                    logger.info("用户【" + user.getUserName() + "】操作积分成功！");
                                    list.add("用户【" + user.getUserName() + "】操作积分成功！");
                                } else {
                                    logger.info("用户【" + user.getUserName() + "】操作积分失败！");
                                    list.add("用户【" + user.getUserName() + "】操作积分失败！");
                                }
                            }
                        }
                    } else {
                        returnData.setCode(ExceptionConst.Success);
                        returnData.setMessage("参数有误！^_^");
                    }
                }
            });
            returnData.setMessage("管理员操作完成！^_^");
            returnData.setData(list);
        } else {
            returnData.setCode(ExceptionConst.Success);
            returnData.setMessage("参数有误！^_^");
        }
        return returnData;
    }

    @Async
    @Override
    public void registerHandle(String userId, String recommendUserId) {

         // TODO …… 新注册用户赠送积分
        if(StringUtils.isNotBlank(userId)){
//            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSGINFO, new JfBo(userId, 1, ""));
            this.handleJfMsg(new JfBo(userId, 1, ""));
            logger.info("新用户注册送积分，userID = " + userId);
        }

        // TODO …… 邀请者送积分  （是不是和上面的分开写）
        if(StringUtils.isNotBlank(recommendUserId)){

            List<Condition> jfRuleFilters = new ArrayList<>();
            jfRuleFilters.add(Condition.eq("sn", Byte.parseByte("2")));
            JfRule jfRule = jfRuleService.selectOne(jfRuleFilters);
            if(jfRule != null && jfRule.getAction() != null){

                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("userId", recommendUserId));
                filters.add(Condition.eq("sourceId", jfRule.getSn()));
                if(jfRule.getUpperTimeLimit() == null){
                    if(jfRule.getUpperLimit() != null){
                        List<JfRecord> jfRecordList = this.selectList(filters);
                        if(jfRule.getUpperLimit() > jfRecordList.stream().mapToInt(JfRecord::getScore).sum()){
//                            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSGINFO, new JfBo(recommendUserId, 2, ""));
                            this.handleJfMsg(new JfBo(recommendUserId, 2, ""));
                            logger.info("邀请用户送积分，userID = " + recommendUserId);
                        } else {
                            logger.info("该用户邀请赠送积分已达到上限");
                        }
                    }
                } else if(jfRule.getUpperTimeLimit().equals(Byte.parseByte("12"))){

                    // 需要沟通 延迟开发

                } else if(jfRule.getUpperTimeLimit().equals(Byte.parseByte("24"))){
                    filters.add(Condition.ge("addTime", DateUtils.toDateText(new Date(), "yyyy-MM-dd") + " 00:00:00"));
                    filters.add(Condition.le("addTime", DateUtils.toDateText(new Date(), "yyyy-MM-dd") + " 23:59:59"));
                    List<JfRecord> jfRecordList = this.selectList(filters);
                    if(jfRule.getUpperLimit() > jfRecordList.stream().mapToInt(JfRecord::getScore).sum()){
//                        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSGINFO, new JfBo(recommendUserId, 2, ""));
                        this.handleJfMsg(new JfBo(recommendUserId, 2, ""));
                        logger.info("邀请用户送积分，userID = " + userId);
                    } else {
                        logger.info("该用户邀请赠送积分已达到上限");
                    }
                } else {
                    logger.info("该用户邀请赠送积分已达到上限");
                }
            }
        }
    }

    @Override
    public void handleJfMsg(JfBo jfBo) {

        logger.info("积分消息处理BO-{}", jfBo.toString());

        try {

            if(jfBo == null || !StringUtils.isNotBlank(jfBo.getUserId()) || jfBo.getSourceId() == null){

                logger.error("<<<<<<<积分消息处理BO为空>>>>>>>>>");
                return;
            }

            if(userService.selectById(jfBo.getUserId()) == null){
                logger.error("<<<<<<<用户不存在>>>>>>>");
                return;
            }

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("sn", jfBo.getSourceId()));
            JfRule jfRule = jfRuleService.selectOne(filters);
            if(jfRule == null){
                logger.error("<<<<<<<积分规则查询不存在>>>>>>>");
                return;
            }

            JfRecord jfRecord = new JfRecord();
            jfRecord.setUserId(jfBo.getUserId());
            jfRecord.setSourceId(Byte.parseByte(jfBo.getSourceId() + ""));
            jfRecord.setAction(jfRule.getAction());

            if(jfRule.getUpperLimit() != null && jfRule.getScore() != null){

                jfRecord.setScore(jfRule.getScore() > jfRule.getUpperLimit() ? jfRule.getUpperLimit() : jfRule.getScore());
            }
            jfRecord.setExpire(Byte.parseByte("0"));
            jfRecord.setAddTime(new Date());
            jfRecord.setOperator("System");
            jfRecord.setAddDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
            jfRecord.setActionDesc(jfRule.getActionDesc());

            if(jfRule.getSn().equals(Byte.parseByte("1"))
                    || jfRule.getSn().equals(Byte.parseByte("2"))
                    || jfRule.getSn().equals(Byte.parseByte("3"))){

                jfRecord.setNote("有效期至" +
                        LocalDate.now().plusYears(JfContants.JF_EXPIRE_YEAR)
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } else {
                jfRecord.setNote(jfBo.getNotes());
            }

            logger.info("积分消息处理BO-{}", jfRecord.toString());

            int n = jfRecordMapper.insertAndGetId(jfRecord);
            if(n == 1){
                logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<积分消息处理成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }else {
                logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<<积分消息处理失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
        }catch (Exception e){
            logger.error("<积分消息处理失败>>>{}", e);
        }
    }
}
