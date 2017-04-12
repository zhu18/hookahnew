package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsShelvesMapper;
import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.domain.vo.OptionalShelves;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsShelvesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author sdw
 */
public class GoodsShelvesServiceImpl extends GenericServiceImpl<GoodsShelves, String> implements GoodsShelvesService {
    @Resource
    private GoodsShelvesMapper goodsShelvesMapper;

    @Resource
    public void setDao(GoodsShelvesMapper goodsShelvesMapper) {
        super.setDao(goodsShelvesMapper);
    }

    @Override
    public GoodsShelves addGoodsShelves(GoodsShelves goodsShelves) {
        return super.insert(goodsShelves);
    }

    @Override
    public ReturnData<List<Map<String, GoodsShelves>>> selectAllShelf() {
        ReturnData<List<Map<String, GoodsShelves>>> returnData = new ReturnData<List<Map<String, GoodsShelves>>>();
        returnData.setCode(ExceptionConst.Success);

        List<GoodsShelves> shelfs =  super.selectList();
        if(Objects.nonNull(shelfs) && shelfs.size() != 0 ){
            List<Map<String, GoodsShelves>> GoodsShelfVOList = new ArrayList<Map<String, GoodsShelves>>();
            for (GoodsShelves goods : shelfs){
                Map<String,GoodsShelves> shelfMap = new HashMap<String,GoodsShelves>();
                shelfMap.put(goods.getShelvesName(),goods);
                GoodsShelfVOList.add(shelfMap);
            }
            returnData.setData(GoodsShelfVOList);
        }

        return returnData;
    }
}
