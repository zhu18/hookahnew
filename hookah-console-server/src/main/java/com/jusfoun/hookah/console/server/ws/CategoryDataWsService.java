package com.jusfoun.hookah.console.server.ws;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.CategoryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gring on 2017/8/11.
 */
@WebService
@Component
public class CategoryDataWsService {

    @Resource
    CategoryService categoryService;

    @WebMethod
    public String receive(String jsonStr) {

        List<Category> categoryTradeList = JSON.parseArray(jsonStr, Category.class);

        if (categoryTradeList != null && categoryTradeList.size() > 0){

            List<Condition> filters = new ArrayList();
            if(categoryService.exists(filters)){
                categoryService.deleteByCondtion(filters);
                categoryService.insertBatch(categoryTradeList);
            }else{
                categoryService.insertBatch(categoryTradeList);
            }

            return  DateUtils.toDefaultNowTime();
        }
        return  DateUtils.toDefaultNowTime();
    }
}
