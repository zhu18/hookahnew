package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import com.jusfoun.hookah.core.domain.vo.SupplierVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.FormatCheckUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import com.jusfoun.hookah.rpc.api.SupplierService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.beans.BeanUtils;
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
    UserService userService;

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
        filter.add(Condition.eq("userId",userId));

        user.setContactName(contactName);
        if (contactPhone!=null && !FormatCheckUtil.checkMobile(contactPhone)){
            throw new HookahException("联系人电话格式不正确");
        }else {
            user.setContactPhone(contactPhone);
        }
        user.setContactAddress(contactAddress);
        user.setSupplierStatus(supplier.CHECK_STATUS);

        Supplier list = super.selectOne(filter);
        if (list!=null){
            list.setContactPhone(contactPhone);
            list.setContactName(contactName);
            list.setAddTime(new Date());
            list.setCheckStatus(supplier.CHECK_STATUS);
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
        if (checkStatus.equals((byte)1)){
            messageCode.setCode(HookahConstants.MESSAGE_401);
            user.setSupplierStatus(supplier.CHECK_STATUS_SUCCESS);
        }else{
            messageCode.setCode(HookahConstants.MESSAGE_402);
            user.setSupplierStatus(supplier.CHECK_STATUS_FAILED);
        }
        supplierMapper.updateByPrimaryKeySelective(supplier);
        userMapper.updateByPrimaryKeySelective(user);
        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);
    }

    @Override
    public Pagination<SupplierVo> getList(Integer pageNumberNew, Integer pageSizeNew, List<Condition> filters,
                                          List<OrderBy> orderBys){
        PageHelper.startPage(pageNumberNew, pageSizeNew);
        Page<Supplier> page = (Page<Supplier>)selectList(filters, orderBys);
        List<SupplierVo> supplierVos = new ArrayList<>();
        for (Supplier supplier : page){
            SupplierVo supplierVo = new SupplierVo();
            BeanUtils.copyProperties(supplier, supplierVo);
            String userId = supplier.getUserId();
            User user = userService.selectById(userId);
            Integer userType = user.getUserType();
            supplierVo.setUserType(userType);
            supplierVos.add(supplierVo);
        }
        Pagination<SupplierVo> pagination = new Pagination<>();
        pagination.setTotalItems(page.getTotal());
        pagination.setList(supplierVos);
        pagination.setPageSize(pageSizeNew);
        pagination.setCurrentPage(pageNumberNew);
        return pagination;
    }

}
