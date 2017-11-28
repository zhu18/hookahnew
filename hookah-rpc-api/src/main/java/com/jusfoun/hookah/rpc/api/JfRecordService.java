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

    /**
     * 根据条件获取所有用户部分信息和可用积分 已兑换积分
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param userType
     * @param mobile
     * @return
     * @throws Exception
     */
    ReturnData selectListByUserInfo(String pageNum, String pageSize, String userName, String userType, String mobile) throws Exception;

    /**
     * 获取用户积分明细
     * @param pageNumberNew
     * @param pageSizeNew
     * @param userId        用户ID
     * @param action        1获取2兑换3admin
     * @param sourceId
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    ReturnData selectJfRecordListByUserId(String pageNumberNew, String pageSizeNew,
                                          String userId, String action,
                                          String sourceId,
                                          String startTime, String endTime) throws Exception;

    /**
     * 根据用户ID获取信息和可用积分 和 已兑换积分
     * @param userId
     * @return
     * @throws Exception
     */
    ReturnData selectOneByUserId(String userId) throws Exception;

    /**
     * admin根据用户ID手动增加减少用户积分
     * @param userId
     * @param optType
     * @param score
     * @return
     * @throws Exception
     */
    ReturnData optJf(String userId, String optType, String score, String note, String operator) throws Exception;

    /**
     * 注册用户 邀请用户送积分
     * @param userId
     * @param recommendUserId
     */
    void registerHandle(String userId, String recommendUserId);
}
