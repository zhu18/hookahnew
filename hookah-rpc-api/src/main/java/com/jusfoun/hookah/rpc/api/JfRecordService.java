package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.vo.JfShowVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * @author dx
 * @date 2017/4/13 下午1:46
 * @desc
 */
public interface JfRecordService extends GenericService<JfRecord, Long> {

    int insertAndGetId(JfRecord jfRecord);

    List<JfShowVo> getJfRecord(String userId, String type);


}
