package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * @author dx
 * @date 2017/4/13 下午1:46
 * @desc
 */
public interface JfRecordService extends GenericService<JfRecord, Long> {

    int insertAndGetId(JfRecord jfRecord);

    ReturnData getJfRecord(Integer pageNumberNew, Integer pageSizeNew, String userId, String type) throws Exception;

    ReturnData selectListByUserInfo(Integer pageNumberNew, Integer pageSizeNew, String userName, String type, String userSn) throws Exception;

}
