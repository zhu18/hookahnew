package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Cart;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface CartService extends GenericService<Cart,String> {
    List<Cart> selectByIds(String[] ids);

    void deleteByLogic(String id);

    void deleteBatchByLogic(String[] ids);

    List<CartVo> selectDetailList(List<Condition> filters) throws  Exception;
}
