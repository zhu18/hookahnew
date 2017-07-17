package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.Supplier;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.generic.OrderBy;

import java.util.List;

/**
 * Created by lt on 2017/7/11.
 */

public interface SupplierService extends GenericService<Supplier,String> {

    void toBeSupplier(String contactName, String contactPhone, String contactAddress, String userId) throws Exception;

    Pagination<Supplier> selectListInCondition(Integer pageNumberNew, Integer pageSizeNew, List<Condition> filters, List<OrderBy> orderBys);

    void checkSupplier(String id, String checkContent, Byte checkStatus, String checkUser);
}
