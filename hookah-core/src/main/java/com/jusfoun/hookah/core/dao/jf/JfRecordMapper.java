package com.jusfoun.hookah.core.dao.jf;

import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.vo.JfShowVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JfRecordMapper extends GenericDao<JfRecord> {

    int insertAndGetId(JfRecord jfRecord);

    List<JfShowVo> selectListByUserId(@Param("userId") String userId);

    List<JfShowVo> selectListByUserIdAndType(@Param("userId") String userId, @Param("type") String type);
}