package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * @author dx
 * @date 2017/4/13 下午1:46
 * @desc
 */
public interface JfRecordService extends GenericService<JfRecord, Long> {

    int insertAndGetId(JfRecord jfRecord);
}
