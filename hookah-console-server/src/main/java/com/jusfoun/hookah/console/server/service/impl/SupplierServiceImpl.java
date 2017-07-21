package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.OrganizationMapper;
import com.jusfoun.hookah.core.dao.SupplierMapper;
import com.jusfoun.hookah.core.dao.UserMapper;
import com.jusfoun.hookah.core.domain.MessageCode;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.Supplier;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.FormatCheckUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import com.jusfoun.hookah.rpc.api.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lt on 2017/5/8.
 */
@Service
public class SupplierServiceImpl extends GenericServiceImpl<Supplier, String> implements SupplierService {

    @Resource
    UserMapper userMapper;

    @Resource
    SupplierMapper supplierMapper;

    @Resource
    OrganizationMapper organizationMapper;

    @Resource
    MqSenderService mqSenderService;

    @Resource
    public void setDao(SupplierMapper supplierMapper) {
        super.setDao(supplierMapper);
    }

    @Override
    @Transactional
    public void toBeSupplier(String contactName, String contactPhone, String contactAddress, String userId) throws Exception{
        User user = userMapper.selectByPrimaryKey(userId);
        Organization organization = organizationMapper.selectByPrimaryKey(user.getOrgId());
        Supplier supplier = new Supplier();
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("userId",user));

        user.setContactName(contactName);
        if (contactPhone!=null && !FormatCheckUtil.checkMobile(contactPhone)){
            throw new HookahException("联系人电话格式不正确");
        }else {
            user.setContactPhone(contactPhone);
        }
        user.setContactAddress(contactAddress);
        user.setUserType(9);

        Supplier list = super.selectOne(filter);
        if (list!=null){
            list.setContactPhone(contactPhone);
            list.setContactName(contactName);
            supplierMapper.updateByPrimaryKeySelective(list);
        }else {
            supplier.setContactPhone(contactPhone);
            supplier.setContactName(contactName);
            supplier.setUserId(userId);
            supplier.setAddTime(new Date());
            supplier.setOrgId(organization.getOrgId());
            supplier.setOrgName(organization.getOrgName());
            supplier.setCheckStatus(supplier.CHECK_STATUS);
            supplierMapper.insert(supplier);
        }
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public Pagination<Supplier> selectListInCondition(Integer pageNumberNew, Integer pageSizeNew,
                                                      List<Condition> filters, List<OrderBy> orderBys){
        Pagination<Supplier> pagination = new Pagination<>();
        PageHelper.startPage(pageNumberNew,pageSizeNew);
        Page<Supplier> page = (Page<Supplier>) super.selectList(filters,orderBys);
        pagination.setTotalItems(page.getTotal());
        pagination.setPageSize(pageSizeNew);
        pagination.setCurrentPage(pageNumberNew);
        pagination.setList(page);
        logger.info(JsonUtils.toJson(pagination));
        return pagination;
    }

    @Override
    @Transactional
    public void checkSupplier(String id, String checkContent, Byte checkStatus, String checkUser) throws Exception{
        Supplier supplier = supplierMapper.selectByPrimaryKey(id);
        if (supplier==null){
            throw new HookahException("保存失败，请重新操作");
        }
        User user = userMapper.selectByPrimaryKey(supplier.getUserId());
        if (user.getUserType()!=4){
            throw new HookahException("单位会员认证尚未通过，请先认证单位会员");
        }
        supplier.setCheckStatus(checkStatus);
        supplier.setCheckContent(checkContent.replaceAll(" ",""));
        supplier.setCheckUser(checkUser);
        MessageCode messageCode = new MessageCode();
        messageCode.setBusinessId(id);
        if (checkStatus.equals("1")){
            messageCode.setCode(HookahConstants.MESSAGE_401);
            user.setUserType(8);
        }else{
            messageCode.setCode(HookahConstants.MESSAGE_402);
            user.setUserType(10);
        }
        supplierMapper.updateByPrimaryKeySelective(supplier);
        userMapper.updateByPrimaryKeySelective(user);
        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);
    }

}
