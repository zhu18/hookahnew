package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.HomeImage;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.HomeImageVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * @author guoruibing
 */
public interface HomeImageService extends GenericService<HomeImage,String> {

    void addImage(HomeImageVo obj, User currentUser) throws HookahException;

    int updateSortValByImgId(String imgId);

    void sort(String imgId, String flg);

}
