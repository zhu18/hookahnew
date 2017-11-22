package com.jusfoun.hookah.core.dao.jf;

import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.vo.JfShowVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JfRecordMapper extends GenericDao<JfRecord> {

    /**
     * 返回主键
     * @param jfRecord
     * @return
     */
    int insertAndGetId(JfRecord jfRecord);

    List<JfShowVo> selectListByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID和查询类型获取数据
     * @param userId
     * @param type
     * @return
     */
    List<JfRecord> selectListByUserIdAndType(@Param("userId") String userId, @Param("type") String type);

    /**
     * 根据用户ID获取可用积分总和
     * @param userId
     * @return
     */
    Integer getUseScoreByUserId(@Param("userId") String userId);
}