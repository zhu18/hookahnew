package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface OrganizationMapper extends GenericDao<Organization> {

    String selectRegion(String orgId);

    String selectOfficeRegion(String orgId);

    String selectRegionProvince(String orgId);

    String selectOfficeRegionProvince(String orgId);

}