package com.jusfoun.hookah.crowd.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.domain.zb.vo.MgZbProviderVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCheckVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbTradeRecord;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public ReturnData getAuthInfo(String userId) {
        try {
            MgZbProvider mgZbProvider = mongoTemplate.findById(userId, MgZbProvider.class);
            return ReturnData.success(mgZbProvider);
        }catch (Exception e){
            logger.error("认证信息获取失败", e);
            return ReturnData.error("认证信息获取失败");
        }
    }

    @Override
    public ReturnData checkAuthInfo(ZbCheckVo vo) {

        try {

            if(vo.getAutherId() == null || vo.getAutherId() == "" || vo.getCheckStatus() == null){
                return ReturnData.error("参数不能为空！^_^");
            }

            if(vo.getCheckStatus().equals(ZbContants.Provider_Auth_Status.AUTH_FAIL.code)){
                if(vo.getCheckContent() == null || vo.getCheckContent() == ""){
                    return ReturnData.error("审核意见不能为空！^_^");
                }
            }

            MgZbProvider mgZbProvider = mongoTemplate.findById(vo.getAutherId(), MgZbProvider.class);

            if(mgZbProvider == null){
                return ReturnData.error("服务商待审核信息不存在！^_^");
            }

            if(mgZbProvider.getStatus().equals(ZbContants.Provider_Auth_Status.AUTH_CHECKING.code)){
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
        }catch (Exception e){
            logger.error("认证信息审核异常", e);
            return ReturnData.error("系统繁忙，请稍后再试！[check]^_^");
        }
    }

    @Override
    public ReturnData getTradeRecod(String userId, String pageNumber, String pageSize) {

        Pagination<ZbTradeRecord> pagination = new Pagination<>();
        PageInfo<ZbTradeRecord> pageInfo = new PageInfo<>();
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        try {
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(pageNumber)) {
                pageNumberNew = Integer.parseInt(pageNumber);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            PageHelper.startPage(pageNumberNew, pageSizeNew);
            List<ZbTradeRecord> list = zbRequirementMapper.selectTradeRecodes(userId);
            pageInfo = new PageInfo<ZbTradeRecord>(list);

            pagination.setTotalItems(pageInfo.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(pageInfo.getList());
            returnData.setData(pagination);
            return returnData;
        }catch (Exception e){
            logger.error("mg交易记录查询失败", e);
            return ReturnData.error("系统繁忙，请稍后再试！[trade]^_^");
        }

//        List<ZbRequirement> list = zbRequireService.selectTradeListByUID(userId);
//        return ReturnData.success(zbRequireService.selectTradeListByUID(userId));
    }

    @Override
    public ReturnData optAuthInfo(MgZbProviderVo vo) {

        try {

            if(!StringUtils.isNotBlank(vo.getUserId())){
                return ReturnData.error("未获取有效的用户信息！^_^");
            }

            MgZbProvider mgZbProvider = mongoTemplate.findById(vo.getUserId(), MgZbProvider.class);
            if(mgZbProvider == null){

//                String userId = this.getCurrentUser().getUserId();
//                User user = userService.selectById(userId);
                MgZbProvider mzp = new MgZbProvider();
                BeanUtils.copyProperties(vo, mzp);

                if(this.getCurrentUser().getUserType().equals(4)){
                    Organization organization = organizationService.findOrgByUserId(this.getCurrentUser().getUserId());
//                    这段注释的代码可能不需要了
//                    if(organization == null || !organization.getIsAuth().equals(Byte.valueOf("2"))){
//                        return ReturnData.error("请先进行企业认证！");
//                    }
                    mzp.setAuthType(ZbContants.ProviderAuthType.COMPANY.code);
                    mzp.setUpname(organization.getOrgName());
                    mzp.setUcity((organization.getRegion() == null || "".equals(organization.getRegion())) ? "" : DictionaryUtil.getRegionById(organization.getRegion()).getMergerName());
                    mzp.setLawPersonName(organization.getLawPersonName());
                    mzp.setPhoneNum(organization.getOrgPhone());
                    mzp.setRegisterTime(DateUtil.getSimpleDate(new Date()));
                    mzp.setRegisterAddr((organization.getRegion() == null || "".equals(organization.getRegion())) ? "" : DictionaryUtil.getRegionById(organization.getRegion()).getMergerName());
                    mzp.setCreditCode(organization.getCreditCode());
                    mzp.setScopeOfBuss(organization.getIndustry());
                }else if(this.getCurrentUser().getUserType().equals(2)){
                    String userId = this.getCurrentUser().getUserId();
                    User user = userService.selectById(userId);
                    UserDetail userDetail = userDetailService.selectById(user.getUserId());
//                    这段注释的代码可能不需要了
//                    if(userDetail == null || !userDetail.getIsAuth().equals(Byte.valueOf("2"))){
//                        return ReturnData.error("请先进行个人认证！");
//                    }
                    mzp.setAuthType(ZbContants.ProviderAuthType.PERSON.code);
                    mzp.setRegisterTime(DateUtil.getSimpleDate(new Date()));
                    mzp.setPhoneNum(user.getContactPhone());
                    mzp.setUpname(user.getUserName());
//                    mzp.setUcity((userDetail.getAddress() == null || "".equals(userDetail.getAddress())) ? "" : DictionaryUtil.getRegionById(userDetail.getAddress()).getMergerName());
                    mzp.setRegisterAddr(user.getContactAddress());
                }else{
                    return ReturnData.error("请先认证！");
                }
                mzp.setAddTime(new Date());
                mzp.setUpdateTime(new Date());
                mongoTemplate.insert(mzp);
                return ReturnData.success("认证信息添加成功");
            }else{

                if(vo.getOptType() == null){ //  插入1删除2修改3

                    Query query = new Query(Criteria.where("_id").is(vo.getUserId()));
                    Update update = new Update();

                    if(StringUtils.isNotBlank(vo.getProviderDesc())){
                        update.set("providerDesc", vo.getProviderDesc());
                    }

                    if(vo.getSpecialSkills() != null && vo.getSpecialSkills().size() > 0){
                        update.addToSet("specialSkills").each(vo.getSpecialSkills());
                    }

                    if(vo.getEducationsExpList() != null && vo.getEducationsExpList().size() > 0){
                        update.addToSet("educationsExpList", vo.getEducationsExpList().get(0));
                    }

                    if(vo.getWorksExpList() != null && vo.getWorksExpList().size() > 0){
                        update.addToSet("worksExpList", vo.getWorksExpList().get(0));
                    }

                    if(vo.getProjectsExpList() != null && vo.getProjectsExpList().size() > 0){
                        update.addToSet("projectsExpList", vo.getProjectsExpList().get(0));
                    }

                    if(vo.getAppCaseList() != null && vo.getAppCaseList().size() > 0){
                        update.addToSet("appCaseList", vo.getAppCaseList().get(0));
                    }

                    if(vo.getSwpList() != null && vo.getSwpList().size() > 0){
                        update.addToSet("swpList", vo.getSwpList().get(0));
                    }

                    if(vo.getInPatentsList() != null && vo.getInPatentsList().size() > 0){
                        update.addToSet("inPatentsList", vo.getInPatentsList().get(0));
                    }

                    update.set("updateTime", new Date());

                    update.set("status", ZbContants.Provider_Auth_Status.AUTH_CHECKING.code);

                    mongoTemplate.upsert(query, update, MgZbProvider.class);
                }else{

                    if(vo.getOptType().equals(2)){  //插入1删除2修改3
                        Query query = new Query(Criteria.where("_id").is(vo.getUserId()));
                        Update update = new Update();
                        if(!StringUtils.isNotBlank(vo.getOptArrAySn())){
                            return ReturnData.error("认证编号参数不能为空！^_^");
                        }
                        String optSn = vo.getOptArrAySn();
                        switch (vo.getOptAuthType()){
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
                    }else if(vo.getOptType().equals(3)){ //插入1删除2修改3
                        Query query = new Query(Criteria.where("_id").is(vo.getUserId()));
                        Update del = new Update();
                        Update add = new Update();
                        switch (vo.getOptAuthType()){
                            case "1":
                                if(vo.getEducationsExpList() == null || vo.getEducationsExpList().size() < 1){
                                    return ReturnData.error("教育经历认证信息不能为空！^_^");
                                }
                                //先删除再添加
                                del.pull("educationsExpList", new BasicDBObject("sn", vo.getEducationsExpList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("educationsExpList", vo.getEducationsExpList().get(0));
                                break;
                            case "2":
                                if(vo.getWorksExpList() == null || vo.getWorksExpList().size() < 1){
                                    return ReturnData.error("工作经历认证信息不能为空！^_^");
                                }
                                del.pull("worksExpList", new BasicDBObject("sn", vo.getWorksExpList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("worksExpList", vo.getWorksExpList().get(0));
                                break;
                            case "3":
                                if(vo.getProjectsExpList() == null || vo.getProjectsExpList().size() < 1){
                                    return ReturnData.error("项目经历认证信息不能为空！^_^");
                                }
                                del.pull("projectsExpList", new BasicDBObject("sn", vo.getProjectsExpList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("projectsExpList", vo.getProjectsExpList().get(0));
                                break;
                            case "4":
                                if(vo.getAppCaseList() == null || vo.getAppCaseList().size() < 1){
                                    return ReturnData.error("应用案例认证信息不能为空！^_^");
                                }
                                del.pull("appCaseList", new BasicDBObject("sn", vo.getAppCaseList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("appCaseList", vo.getAppCaseList().get(0));
                                break;
                            case "5":
                                if(vo.getSwpList() == null || vo.getSwpList().size() < 1){
                                    return ReturnData.error("软件著作权认证信息不能为空！^_^");
                                }
                                del.pull("swpList", new BasicDBObject("sn", vo.getSwpList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("swpList", vo.getSwpList().get(0));
                                break;
                            default:
                                if(vo.getInPatentsList() == null || vo.getInPatentsList().size() < 1){
                                    return ReturnData.error("发明专利认证信息不能为空！^_^");
                                }
                                del.pull("inPatentsList", new BasicDBObject("sn", vo.getInPatentsList().get(0).getSn()));
                                mongoTemplate.updateMulti(query, del, MgZbProvider.class);
                                add.addToSet("inPatentsList", vo.getInPatentsList().get(0));
                                break;
                        }
                        mongoTemplate.upsert(query, add, MgZbProvider.class);
                    }else{
                        return ReturnData.error("操作类型有误！^_^");
                    }
                }
            }
            return ReturnData.success("认证信息操作成功！^_^");
        }catch (Exception e){
            logger.error("认证信息操作失败！^_^", e);
            return ReturnData.error("认证信息操作失败！^_^");
        }
    }

    @Override
    public boolean isAuthRealName() {

        try {
            Integer userType = this.getCurrentUser().getUserType();
//            User user = userService.selectById(userId);

//            org与detail中的实名认证状态无用
//            if(user.getUserType() == 4){
//                Organization organization = organizationService.findOrgByUserId(user.getUserId());
//                if(organization == null || !organization.getIsAuth().equals(Byte.valueOf("2"))){
//                    return false;
//                }
//            }else if(user.getUserType() == 2){
//                UserDetail userDetail = userDetailService.selectById(user.getUserId());
//                if(userDetail == null || !userDetail.getIsAuth().equals(Byte.valueOf("2"))){
//                    return false;
//                }
//            }else{
//                return false;
//            }

//          现在只需要判断是否等于4（企业）和是否等于2（个人）
            if(userType.equals(4) || userType.equals(2)){
                return true;
            }else{
                return false;
            }
        } catch (HookahException e) {
            e.printStackTrace();
        }
        return false;
    }
}
