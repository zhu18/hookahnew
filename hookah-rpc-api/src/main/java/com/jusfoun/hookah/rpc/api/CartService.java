package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface CartService extends GenericService<CartVo,String> {
    List<CartVo> selectByIds(String[] ids);

    void deleteByLogic(String id);

    void deleteBatchByLogic(String[] ids);
}
