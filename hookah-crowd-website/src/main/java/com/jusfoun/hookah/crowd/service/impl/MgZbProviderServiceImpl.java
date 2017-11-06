package com.jusfoun.hookah.crowd.service.impl;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.domain.zb.vo.MgZbProviderVo;
import com.jusfoun.hookah.core.domain.zb.vo.ProviderQueryVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCheckVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import com.jusfoun.hookah.crowd.util.DateUtil;
import com.jusfoun.hookah.crowd.util.DictionaryUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MgZbProviderServiceImpl extends GenericMongoServiceImpl<MgZbProvider, String> implements MgZbProviderService {

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    ZbRequireService zbRequireService;

    @Resource
    UserDetailService userDetailService;

    @Resource
    UserService userService;

    @Resource
    OrganizationService organizationService;

    @Resource
    ZbRequirementMapper zbRequirementMapper;

    @Resource
    ZbCommentService zbCommentService;

    @Override
    public ReturnData getAuthInfo(String optAuthType, String optArrAySn) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        try {

            String userId = this.getCurrentUser().getUserId();
            MgZbProvider mgZbProvider = null;
            if (!StringUtils.isNotBlank(optArrAySn) || !StringUtils.isNotBlank(optAuthType)) {
                mgZbProvider = mongoTemplate.findById(userId, MgZbProvider.class);
                returnData.setData(mgZbProvider);
                returnData.setMessage("查询成功");
            } else {

                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(userId));
                mgZbProvider = mongoTemplate.findOne(query, MgZbProvider.class);
                switch (optAuthType) {
                    case "1":
//                        query.addCriteria(Criteria.where("_id").is(userId).and("educationsExpList.sn").is(optArrAySn));
//                        mgZbProvider = mongoTemplate.findOne(query, MgZbProvider.class);
                        if (mgZbProvider != null) {
                            if (mgZbProvider.getEducationsExpList() != null) {
                                mgZbProvider.getEducationsExpList().forEach(
                                        educationsExp -> {
                                            if (educationsExp.getSn().equals(optArrAySn)) {
                                                returnData.setData(educationsExp);
                                            }
                                        }
                                );
                            }
                        }
                        break;
                    case "2":
//                        query.addCriteria(Criteria.where("_id").is(userId));
//                        mgZbProvider = mongoTemplate.findOne(query, MgZbProvider.class);
                        if (mgZbProvider != null) {
                            if (mgZbProvider.getWorksExpList() != null) {
                                mgZbProvider.getWorksExpList().forEach(
                                        worksExp -> {
                                            if (worksExp.getSn().equals(optArrAySn)) {
                                                returnData.setData(worksExp);
                                            }
                                        }
                                );
                            }
                        }
                        break;
                    case "3":
//                        query.addCriteria(Criteria.where("_id").is(userId).and("projectsExpList.sn").is(optArrAySn));
                        if (mgZbProvider != null) {
                            if (mgZbProvider.getProjectsExpList() != null) {
                                mgZbProvider.getProjectsExpList().forEach(
                                        projectsExp -> {
                                            if (projectsExp.getSn().equals(optArrAySn)) {
                                                returnData.setData(projectsExp);
                                            }
                                        }
                                );
                            }
                        }
                        break;
                    case "4":
//                        query.addCriteria(Criteria.where("_id").is(userId).and("appCaseList.sn").is(optArrAySn));
                        if (mgZbProvider != null) {
                            if (mgZbProvider.getAppCaseList() != null) {
                                mgZbProvider.getAppCaseList().forEach(
                                        appCase -> {
                                            if (appCase.getSn().equals(optArrAySn)) {
                                                returnData.setData(appCase);
                                            }
                                        }
                                );
                            }
                        }
                        break;
                    case "5":
//                        query.addCriteria(Criteria.where("_id").is(userId).and("swpList.sn").is(optArrAySn));
                        if (mgZbProvider != null) {
                            if (mgZbProvider.getSwpList() != null) {
                                mgZbProvider.getSwpList().forEach(
                                        swp -> {
                                            if (swp.getSn().equals(optArrAySn)) {
                                                returnData.setData(swp);
                                            }
                                        }
                                );
                            }
                        }
                        break;
                    default:
//                        query.addCriteria(Criteria.where("_id").is(userId).and("inPatentsList.sn").is(optArrAySn));
                        if (mgZbProvider != null) {
                            if (mgZbProvider.getInPatentsList() != null) {
                                mgZbProvider.getInPatentsList().forEach(
                                        inventionPatent -> {
                                            if (inventionPatent.getSn().equals(optArrAySn)) {
                                                returnData.setData(inventionPatent);
                                            }
                                        }
                                );
                            }
                        }
                        break;
                }
            }

            returnData.setData2(zbCommentService.getLevelCountByUserId(userId));

            return returnData;
        } catch (Exception e) {
            logger.error("认证信息获取失败", e);
            return ReturnData.error("认证信息获取失败");
        }
    }

    @Override
    public ReturnData checkAuthInfo(ZbCheckVo vo) {

        try {

            if (vo.getAutherId() == null || vo.getAutherId() == "" || vo.getCheckStatus() == null) {
                return ReturnData.error("参数不能为空！^_^");
            }

            if (vo.getCheckStatus().equals(ZbContants.Provider_Auth_Status.AUTH_FAIL.code)) {
                if (vo.getCheckContent() == null || vo.getCheckContent() == "") {
                    return ReturnData.error("审核意见不能为空！^_^");
                }
            }

            MgZbProvider mgZbProvider = mongoTemplate.findById(vo.getAutherId(), MgZbProvider.class);

            if (mgZbProvider == null) {
                return ReturnData.error("服务商待审核信息不存在！^_^");
            }

            if (mgZbProvider.getStatus().equals(ZbContants.Provider_Auth_Status.AUTH_CHECKING.code)) {
                Query query = new Query(Criteria.where("_id").is(mgZbProvider.getUserId()));
                Update update = new Update();
                update.set("status", vo.getCheckStatus());
                update.set("checkUser", vo.getCheckerId());
                update.set("checkContent", vo.getCheckContent());
                update.set("checkTime", new Date());
                WriteResult writeResult = mongoTemplate.upsert(query, update, MgZbProvider.class);
                logger.info(JSON.toJSONString(writeResult));
            }

            return ReturnData.success("认证信息审核成功");
        } catch (Exception e) {
            logger.error("认证信息审核异常", e);
            return ReturnData.error("系统繁忙，请稍后再试！[check]^_^");
        }
    }

    @Override
    public ReturnData optAuthInfo(MgZbProviderVo vo) {

        try {

            if (!StringUtils.isNotBlank(vo.getUserId())) {
                return ReturnData.error("未获取有效的用户信息！^_^");
            }

            MgZbProvider mgZbProvider = mongoTemplate.findById(vo.getUserId(), MgZbProvider.class);
            if (mgZbProvider == null) {
                MgZbProvider mzp = new MgZbProvider();
                BeanUtils.copyProperties(vo, mzp);
                String userId = this.getCurrentUser().getUserId();
                User user = userService.selectById(userId);
                if (this.getCurrentUser().getUserType().equals(4)) {
                    Organization organization = organizationService.findOrgByUserId(this.getCurrentUser().getUserId());
                    mzp.setAuthType(ZbContants.ProviderAuthType.COMPANY.code);
                    mzp.setUpname(organization.getOrgName());
                    mzp.setUcity((organization.getRegion() == null || "".equals(organization.getRegion())) ? "" : DictionaryUtil.getRegionById(organization.getRegion()).getMergerName());
                    mzp.setLawPersonName(organization.getLawPersonName());
                    mzp.setPhoneNum((organization.getOrgPhone() == null ||
                            organization.getOrgPhone().equals("")) ? user.getMobile() : organization.getOrgPhone());
                    mzp.setRegisterTime(DateUtil.getSimpleDate(new Date()));
                    mzp.setRegisterAddr((organization.getRegion() == null || "".equals(organization.getRegion())) ? "" : DictionaryUtil.getRegionById(organization.getRegion()).getMergerName());
                    mzp.setCreditCode(organization.getCreditCode());
                    mzp.setScopeOfBuss(organization.getIndustry());
                    mzp.setAuthType(4);//企业
                } else if (this.getCurrentUser().getUserType().equals(2)) {
                    UserDetail userDetail = userDetailService.selectById(user.getUserId());
                    mzp.setAuthType(ZbContants.ProviderAuthType.PERSON.code);
                    mzp.setRegisterTime(DateUtil.getSimpleDate(new Date()));
                    mzp.setPhoneNum(user.getMobile());
                    mzp.setUpname((userDetail.getRealName() == null ||
                            userDetail.getRealName().equals("")) ? user.getUserName() : userDetail.getRealName());
                    mzp.setRegisterAddr(user.getContactAddress());
                    mzp.setAuthType(2);//个人
                } else {
                    return ReturnData.error("请先认证！");
                }
                mzp.setAddTime(new Date());
                mzp.setUpdateTime(new Date());
                mzp.setStatus(ZbContants.Provider_Auth_Status.AUTH_CHECKING.code);
                mongoTemplate.insert(mzp);
                return ReturnData.success("认证信息添加成功");
            } else {

                if (vo.getOptType() == null) { //  插入1删除2修改3

                    Query query = new Query(Criteria.where("_id").is(vo.getUserId()));
                    Update update = new Update();

                    if (StringUtils.isNotBlank(vo.getProviderDesc())) {
                        update.set("providerDesc", vo.getProviderDesc());
                    }

                    if (vo.getSpecialSkills() != null && vo.getSpecialSkills().size() > 0) {
                        update.set("specialSkills", vo.getSpecialSkills().toArray());
                    }

                    if (vo.getEducationsExpList() != null && vo.getEducationsExpList().size() > 0) {
                        update.addToSet("educationsExpList", vo.getEducationsExpList().get(0));
                    }

                    if (vo.getWorksExpList() != null && vo.getWorksExpList().size() > 0) {
                        update.addToSet("worksExpList", vo.getWorksExpList().get(0));
                    }

                    if (vo.getProjectsExpList() != null && vo.getProjectsExpList().size() > 0) {
                        update.addToSet("projectsExpList", vo.getProjectsExpList().get(0));
                    }

                    if (vo.getAppCaseList() != null && vo.getAppCaseList().size() > 0) {
                        update.addToSet("appCaseList", vo.getAppCaseList().get(0));
                    }

                    if (vo.getSwpList() != null && vo.getSwpList().size() > 0) {
                        update.addToSet("swpList", vo.getSwpList().get(0));
                    }

                    if (vo.getInPatentsList() != null && vo.getInPatentsList().size() > 0) {
                        update.addToSet("inPatentsList", vo.getInPatentsList().get(0));
                    }

                    update.set("updateTime", new Date());

                    update.set("status", ZbContants.Provider_Auth_Status.AUTH_CHECKING.code);

                    mongoTemplate.upsert(query, update, MgZbProvider.class);
                } else {

                    if (vo.getOptType().equals(2)) {  //插入1删除2修改3
                        Query query = new Query(Criteria.where("_id").is(vo.getUserId()));
                        Update update = new Update();
                        if (!StringUtils.isNotBlank(vo.getOptArrAySn())) {
                            return ReturnData.error("认证编号参数不能为空！^_^");
                        }
                        String optSn = vo.getOptArrAySn();
                        switch (vo.getOptAuthType()) {
                            case "1":
                                update.pull("educationsExpList", new BasicDBObject("sn", optSn));
                                break;
                            case "2":
                                update.pull("worksExpList", new BasicDBObject("sn", optSn));
                                break;
                            case "3":
                                update.pull("projectsExpList", new BasicDBObject("sn", optSn));
                                break;
                            case "4":
                                update.pull("appCaseList", new BasicDBObject("sn", optSn));
                                break;
                            case "5":
                                update.pull("swpList", new BasicDBObject("sn", optSn));
                                break;
                            default:
                                update.pull("inPatentsList", new BasicDBObject("sn", optSn));
                                break;
                        }
                        mongoTemplate.updateFirst(query, update, MgZbProvider.class);
                    } else if (vo.getOptType().equals(3)) { //插入1删除2修改3
                        Query query = new Query(Criteria.where("_id").is(vo.getUserId()));
                        Update del = new Update();
                        Update add = new Update();
                        switch (vo.getOptAuthType()) {
                            case "1":
                                if (vo.getEducationsExpList() == null || vo.getEducationsExpList().size() < 1) {
                                    return ReturnData.error("教育经历认证信息不能为空！^_^");
                                }
                                //先删除再添加
                                del.pull("educationsExpList", new BasicDBObject("sn", vo.getEducationsExpList().get(0).getSn()));
                                WriteResult wr = mongoTemplate.updateMulti(query, del, MgZbProvider.class);
//                                add.addToSet("educationsExpList", vo.getEducationsExpList().get(0));
                                add.addToSet("educationsExpList").each(vo.getEducationsExpList());
                                break;
                            case "2":
                                if (vo.getWorksExpList() == null || vo.getWorksExpList().size() < 1) {
                                    return ReturnData.error("工作经历认证信息不能为空！^_^");
                                }
                                del.pull("worksExpList", new BasicDBObject("sn", vo.getWorksExpList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("worksExpList", vo.getWorksExpList().get(0));
                                break;
                            case "3":
                                if (vo.getProjectsExpList() == null || vo.getProjectsExpList().size() < 1) {
                                    return ReturnData.error("项目经历认证信息不能为空！^_^");
                                }
                                del.pull("projectsExpList", new BasicDBObject("sn", vo.getProjectsExpList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("projectsExpList", vo.getProjectsExpList().get(0));
                                break;
                            case "4":
                                if (vo.getAppCaseList() == null || vo.getAppCaseList().size() < 1) {
                                    return ReturnData.error("应用案例认证信息不能为空！^_^");
                                }
                                del.pull("appCaseList", new BasicDBObject("sn", vo.getAppCaseList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("appCaseList", vo.getAppCaseList().get(0));
                                break;
                            case "5":
                                if (vo.getSwpList() == null || vo.getSwpList().size() < 1) {
                                    return ReturnData.error("软件著作权认证信息不能为空！^_^");
                                }
                                del.pull("swpList", new BasicDBObject("sn", vo.getSwpList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("swpList", vo.getSwpList().get(0));
                                break;
                            default:
                                if (vo.getInPatentsList() == null || vo.getInPatentsList().size() < 1) {
                                    return ReturnData.error("发明专利认证信息不能为空！^_^");
                                }
                                del.pull("inPatentsList", new BasicDBObject("sn", vo.getInPatentsList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("inPatentsList", vo.getInPatentsList().get(0));
                                break;
                        }

                        add.set("status", ZbContants.Provider_Auth_Status.AUTH_CHECKING.code);

                        mongoTemplate.upsert(query, add, MgZbProvider.class);
                    } else {
                        return ReturnData.error("操作类型有误！^_^");
                    }
                }
            }
            return ReturnData.success("认证信息操作成功！^_^");
        } catch (Exception e) {
            logger.error("认证信息操作失败！^_^", e);
            return ReturnData.error("认证信息操作失败！^_^");
        }
    }

    @Override
    public boolean isAuthRealName() {

        try {
            Integer userType = this.getCurrentUser().getUserType();

//          现在只需要判断是否等于4（企业）和是否等于2（个人）
            if (userType.equals(4) || userType.equals(2)) {
                return true;
            } else {
                return false;
            }
        } catch (HookahException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<MgZbProvider> findByTheMgZbProviderIdAndSn(String _id, String sn) {
        return null;
    }

    @Override
    public ReturnData getProviderCard() {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        try {

            MgZbProvider mgZbProvider = mongoTemplate.findById(this.getCurrentUser().getUserId(), MgZbProvider.class);
            returnData.setData(mgZbProvider);
            returnData.setData2(zbCommentService.getLevelCountByUserId(this.getCurrentUser().getUserId()));

        } catch (Exception e) {
            logger.error("服务商名片获取异常{}", e);
        }
        return returnData;
    }

    @Override
    public List<MgZbProvider> selectListByCondition(ProviderQueryVo vo) {

        Query query = new Query();
//        if(vo.getAuthType() != null){
//            query.addCriteria(Criteria.where("authType").is(vo.getAuthType()));
//        }
//
//        if(vo.getAuthStatus() != null){
//            query.addCriteria(Criteria.where("status").is(vo.getAuthStatus()));
//        }
//
//        if(StringUtils.isNotBlank(vo.getUpName())){
//            query.addCriteria(Criteria.where("upname").alike());
//        }

        Criteria criatira = new Criteria();

        if (vo.getAuthType() != null) {
            criatira.and("authType").is(vo.getAuthType());
        }

        if (vo.getAuthStatus() != null) {
            criatira.and("status").is(vo.getAuthStatus());
        }

        if (StringUtils.isNotBlank(vo.getUpName())) {
//            criatira.and("upname").regex(vo.getUpName());
            criatira.and("upname").regex(".*?" + vo.getUpName() + ".*");
        }

        if (StringUtils.isNotBlank(vo.getStartTime())) {
            criatira.and("addTime").gte(vo.getStartTime());
        }

        if (StringUtils.isNotBlank(vo.getUpName())) {
            criatira.and("addTime").lte(vo.getEndTime());
        }

        query.addCriteria(criatira);

//        List<Sort> orders = new ArrayList<>();
//        orders.add(new Sort(Sort.Direction.DESC, vo.getSortField()));

        if(StringUtils.isNotBlank(vo.getSortField())){
            query.with(new Sort(Sort.Direction.DESC, vo.getSortField()));
        }







        return null;
    }

    @Override
    public void setUCreditValueByPJ(String userId, Integer level) {

        try {

            if(!StringUtils.isNotBlank(userId)){
                logger.error("用户评价信誉参数异常，userID不能为空");
                return;
            }

            if(level == null){
                logger.error("用户评价信誉参数异常，level不能为空");
                return;
            }

            MgZbProvider mgZbProvider = mongoTemplate.findById(userId, MgZbProvider.class);
            if(mgZbProvider == null){
                logger.error("未查询到用户" + userId + "的服务上认证信息");
                return;
            }

            Query query = new Query(Criteria.where("_id").is(userId));
            Update update = new Update();

            switch (level){
                case 1:
                    update.inc("ucreditValue", ZbContants.UCreditValue.C_V_1.code);
                    break;
                case 2:
                    update.inc("ucreditValue", ZbContants.UCreditValue.C_V_2.code);
                    break;
                case 3:
                    update.inc("ucreditValue", ZbContants.UCreditValue.C_V_3.code);
                    break;
                case 4:
                    update.inc("ucreditValue", ZbContants.UCreditValue.C_V_4.code);
                    break;
                default:
                    update.inc("ucreditValue", ZbContants.UCreditValue.C_V_5.code);
            }

            WriteResult rs =  mongoTemplate.upsert(query, update, MgZbProvider.class);
            if(rs.getN() == 1){

                logger.info("修改信誉值成功^_^");
            }else {
                logger.info("修改信誉值失败^_^");
            }

        }catch (Exception e){
            logger.error("用户评价之修改信誉设置异常", e);
        }

    }

    @Override
    public ReturnData getAuthInfoByUserId(String userId) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        try {

            MgZbProvider mgZbProvider = mongoTemplate.findById(userId, MgZbProvider.class);
            if(mgZbProvider == null){
                returnData.setMessage("查询失败");
                returnData.setCode(ExceptionConst.Failed);
            }

            if(mgZbProvider.getEducationsExpList() != null && mgZbProvider.getEducationsExpList().size() > 1){
                mgZbProvider.getEducationsExpList().
                        sort((x, y) -> {
                            return Integer.parseInt(y.getEdu()) - Integer.parseInt(x.getEdu());
                        });
            }

            returnData.setMessage("查询成功");
            returnData.setData(mgZbProvider);
            returnData.setData2(zbCommentService.getLevelCountByUserId(userId));

        } catch (Exception e) {
            logger.error("认证信息获取失败", e);
            return ReturnData.error("认证信息获取失败");
        }

        return returnData;
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");

        String[] strings = new String[list.size()];

        list.toArray(strings);
        System.out.println(strings);

    }
}
