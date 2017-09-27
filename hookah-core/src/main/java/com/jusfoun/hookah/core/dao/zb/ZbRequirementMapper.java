package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementPageHelper;
import com.jusfoun.hookah.core.domain.zb.vo.ZbTradeRecord;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZbRequirementMapper extends GenericDao<ZbRequirement> {

    int insertAndGetId(ZbRequirement zbRequirement);

    int countRequirementList(ZbRequirementPageHelper helper);

    List<ZbRequirement> getRequirementList(ZbRequirementPageHelper helper);

    List<ZbRequirement> selectListForPageByFilters(
            @Param("requireSn") String requireSn,
            @Param("title") String title,
            @Param("zbStatus") Short[] zbStatus);

    List<ZbTradeRecord> selectTradeRecodes(@Param("userId") String userId);

    ZbRequirement selectForDetail(@Param("id") Long id);

    User selectReleaseInfo(String userId);
}