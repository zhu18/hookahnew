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

        returnData.setData2(cacheService.getUseScoreByUserId(userId));

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
            pages.getList().parallelStream().forEach(x -> {

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

        jfUserVoList.sort((JfUserVo m, JfUserVo n) -> m.getAddTime().compareTo(n.getAddTime()));
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
            filters.add(Condition.ge("addTime", DateUtils.transferTime(startTime)));
        }

        if (StringUtils.isNotBlank(endTime)) {
            filters.add(Condition.le("addTime", DateUtils.transferTime(startTime)));
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

    @Override
    public ReturnData optJf(String userId, String optType, String score, String note, String operatorId) throws Exception {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        if (!StringUtils.isNotBlank(userId) || !StringUtils.isNotBlank(optType)
                || !StringUtils.isNotBlank(optType) || !StringUtils.isNotBlank(note)
                || Integer.parseInt(score) <= 0) {
            return ReturnData.error("参数不能为空！^_^");
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
            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSG, new JfBo(userId, 1));
        }

        // TODO …… 邀请者送积分  （是不是和上面的分开写）
        if(StringUtils.isNotBlank(recommendUserId)){

            List<Condition> jfRuleFilters = new ArrayList<>();
            jfRuleFilters.add(Condition.eq("sn", Byte.parseByte("2")));
            JfRule jfRule = jfRuleService.selectOne(jfRuleFilters);
            if(jfRule != null && jfRule.getAction() != null){

                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("userId", recommendUserId));
                filters.add(Condition.eq("sourceId", jfRule.getAction()));
                if(jfRule.getUpperTimeLimit() == null){
                    if(jfRule.getUpperLimit() != null){
                        List<JfRecord> jfRecordList = this.selectList(filters);
                        if(jfRule.getUpperLimit() > jfRecordList.stream().mapToInt(JfRecord::getScore).sum()){
                            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSG, new JfBo(recommendUserId, 2));
                        } else {
                            logger.info("该用户邀请赠送积分已达到上限");
                        }
                    }
                } else if(jfRule.getUpperTimeLimit().equals(Byte.parseByte("12"))){

                    // 需要沟通 延迟开发

                } else if(jfRule.getUpperTimeLimit().equals(Byte.parseByte("24"))){
                    filters.add(Condition.eq("addDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"))));
                    List<JfRecord> jfRecordList = this.selectList(filters);
                    if(jfRule.getUpperLimit() > jfRecordList.stream().mapToInt(JfRecord::getScore).sum()){
                        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSG, new JfBo(recommendUserId, 2));
                    } else {
                        logger.info("该用户邀请赠送积分已达到上限");
                    }
                } else {
                    logger.info("该用户邀请赠送积分已达到上限");
                }
            }
        }
    }
}
