package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.domain.mongo.MgSmsValidate;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgSmsValidateService;
import org.springframework.stereotype.Service;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class MgSmsValidateServiceImpl extends GenericMongoServiceImpl<MgSmsValidate, String> implements MgSmsValidateService {

}
