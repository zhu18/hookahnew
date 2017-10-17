package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.domain.zb.vo.MgZbProviderVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCheckVo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MgZbProviderService extends GenericService<MgZbProvider, String> {

    ReturnData getAuthInfo(String optAuthType, String optArrAySn);

    ReturnData checkAuthInfo(ZbCheckVo vo);

    ReturnData getTradeRecod(String userId, String pageNumber, String pageSize);

    ReturnData optAuthInfo(MgZbProviderVo vo);

    boolean isAuthRealName();

//    @Query(value="{ 'firstname' : ?0 }", fields="{ 'firstname' : 1, 'lastname' : 1}")
//    List<MgZbProvider> findByTheMgZbProviderFirstname(String firstname);

    @Query(value="{'_id': ?0},{'educationsExpList':{'$elemMatch':{'sn': ?1}}}", fields="{ 'educationsExpList' : 1}")
    List<MgZbProvider> findByTheMgZbProviderIdAndSn(String _id, String sn);

    ReturnData getProviderCard();
}
