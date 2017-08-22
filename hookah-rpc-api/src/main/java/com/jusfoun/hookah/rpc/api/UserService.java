package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericService;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface UserService extends GenericService<User, String> {

    User insert(User user);

    int manualRecharge(String userId, long l, String userName) throws Exception;

    void setPVCountByDate();

    void setUVCountByDate();

    Map<String, Object> getPUVCountByDate();

    Map<String, Object> getPUVCount();

    String updatePayPassWord(String oldPayPassWord, String newPayPassWord, Integer safetyPayScore, String userId, Model model);

    Pagination getUsersInPage( HashMap<String,Object> params);

}
