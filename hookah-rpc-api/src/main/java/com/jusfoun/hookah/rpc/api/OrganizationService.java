package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * @author huang lei
 * @date 2017/4/6 上午10:09
 * @desc
 */
public interface OrganizationService extends GenericService<Organization,String> {

    Organization findOrgByUserId(String userId);

}
