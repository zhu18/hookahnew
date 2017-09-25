package com.jusfoun.hookah.crowd.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.domain.zb.vo.MgZbProviderVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCheckVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbTradeRecord;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.MgZbProviderService;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
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
    ZbRequirementMapper zbRequirementMapper;

    @Override
    public ReturnData userAuth(MgZbProvider provider) {

        try {

            MgZbProvider mgZbProvider = mongoTemplate.findById(provider.getUserId(), MgZbProvider.class);
            if(mgZbProvider == null){

                provider.setAddTime(new Date());
                provider.setUpdateTime(new Date());
                mongoTemplate.insert(provider);
                return ReturnData.success("认证信息添加成功");
            }else{

                Query query = new Query(Criteria.where("_id").is(mgZbProvider.getUserId()));
                Update update = new Update();

                if(StringUtils.isNotBlank(provider.getProviderDesc())){
                    update.set("providerDesc", provider.getProviderDesc());
                }

                if(provider.getSpecialSkills() != null && provider.getSpecialSkills().size() > 0){


                    update.addToSet("specialSkills").each(provider.getSpecialSkills());
//                    Update.AddToSetBuilder ab = update.new AddToSetBuilder("specialSkills");
//                    update = ab.each(provider.getSpecialSkills());
//                    update.addToSet("specialSkills", provider.getSpecialSkills());
                }

                if(provider.getEducationsExpList() != null && provider.getEducationsExpList().size() > 0){
                    update.addToSet("educationsExpList", provider.getEducationsExpList().get(0));
                }

                if(provider.getWorksExpList() != null && provider.getWorksExpList().size() > 0){
                    update.addToSet("worksExpList", provider.getWorksExpList().get(0));
                }

                if(provider.getProjectsExpList() != null && provider.getProjectsExpList().size() > 0){
                    update.addToSet("projectsExpList", provider.getProjectsExpList().get(0));
                }

                if(provider.getAppCaseList() != null && provider.getAppCaseList().size() > 0){
                    update.addToSet("appCaseList", provider.getAppCaseList().get(0));
                }

                if(provider.getSwpList() != null && provider.getSwpList().size() > 0){
                    update.addToSet("swpList", provider.getSwpList().get(0));
                }

                if(provider.getInPatentsList() != null && provider.getInPatentsList().size() > 0){
                    update.addToSet("inPatentsList", provider.getInPatentsList().get(0));
                }

                update.set("updateTime", new Date());

                update.set("status", ZbContants.Provider_Auth_Status.AUTH_CHECKING.code);

                mongoTemplate.upsert(query, update, MgZbProvider.class);
                return ReturnData.success("认证信息添加成功");
            }
        }catch (Exception e){
            logger.error("认证信息添加失败", e);
            return ReturnData.error("认证信息添加失败");
        }
    }

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
    public ReturnData delAuthInfo(String userId, String optSn, String optType) {

        try {

            if(!StringUtils.isNotBlank(optSn) || !StringUtils.isNotBlank(optType)){
                return ReturnData.error("参数不能为空！^_^");
            }

            Query query = new Query(Criteria.where("_id").is(userId));
            Update update = new Update();
            switch (optType){
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
            return ReturnData.success();
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

            return ReturnData.success();
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
            logger.error("交易记录查询失败", e);
            return ReturnData.error("系统繁忙，请稍后再试！[trade]^_^");
        }

//        List<ZbRequirement> list = zbRequireService.selectTradeListByUID(userId);
//        return ReturnData.success(zbRequireService.selectTradeListByUID(userId));
    }

    @Override
    public ReturnData optAuthInfo(MgZbProviderVo vo) {

        try {

            if(vo.getOptType() == null){
                return ReturnData.error("参数不能为空！^_^");
            }
            if(vo.getOptType().equals(1)){
                Query query = new Query(Criteria.where("_id").is(vo.getUserId()));
                Update update = new Update();
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
            }else if(vo.getOptType().equals(2)){  //插入1删除2修改3
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
            }
            return ReturnData.success();
        }catch (Exception e){
            logger.error("认证信息操作失败", e);
            return ReturnData.error("认证信息操作失败");
        }
    }

}
