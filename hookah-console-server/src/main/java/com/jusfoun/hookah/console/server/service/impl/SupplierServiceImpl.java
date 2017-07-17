package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.OrganizationMapper;
import com.jusfoun.hookah.core.dao.SupplierMapper;
import com.jusfoun.hookah.core.dao.UserMapper;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.Supplier;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.FormatCheckUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.rpc.api.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    public void setDao(SupplierMapper supplierMapper) {
        super.setDao(supplierMapper);
    }

    @Override
    @Transactional
    public void toBeSupplier(String contactName, String contactPhone, String contactAddress, String userId) throws Exception{
        User user = userMapper.selectByPrimaryKey(userId);
        Organization organization = organizationMapper.selectByPrimaryKey(user.getOrgId());
        Supplier supplier = new Supplier();

        user.setContactName(contactName);
        if (contactPhone!=null && !FormatCheckUtil.checkMobile(contactPhone)){
            throw new HookahException("联系人电话格式不正确");
        }else {
            user.setContactPhone(contactPhone);
        }
        user.setContactAddress(contactAddress);
        user.setUserType(9);

        supplier.setUserId(userId);
        supplier.setAddTime(new Date());
        supplier.setOrgId(organization.getOrgId());
        supplier.setOrgName(organization.getOrgName());
        supplier.setContactPhone(contactPhone);
        supplier.setCheckStatus(supplier.CHECK_STATUS);

        userMapper.updateByPrimaryKey(user);
        supplierMapper.insert(supplier);
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
    public void checkSupplier(String id, String checkContent, Byte checkStatus, String checkUser){
        Supplier supplier = supplierMapper.selectByPrimaryKey(id);
        supplier.setCheckStatus(checkStatus);
        supplier.setCheckContent(checkContent.replaceAll(" ",""));
        supplier.setCheckUser(checkUser);
        User user = userMapper.selectByPrimaryKey(supplier.getUserId());
        if (checkStatus.equals("1")){
            user.setUserType(8);
        }else{
            user.setUserType(10);
        }
        supplierMapper.updateByPrimaryKeySelective(supplier);
        userMapper.updateByPrimaryKeySelective(user);
    }

}
