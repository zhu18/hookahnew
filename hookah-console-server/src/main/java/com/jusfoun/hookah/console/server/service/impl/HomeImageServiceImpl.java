package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.HomeImageMapper;
import com.jusfoun.hookah.core.domain.HomeImage;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.HomeImageVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.rpc.api.HomeImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ctp
 */
@Service
public class HomeImageServiceImpl extends GenericServiceImpl<HomeImage, String> implements HomeImageService {


    @Resource
    HomeImageMapper homeImageMapper;

    @Resource
    public void setDao(HomeImageMapper homeImageMapper) {
        super.setDao(homeImageMapper);
    }

    @Override
    public void addImage(HomeImageVo obj, User currentUser) throws HookahException {

        if (obj == null)
            throw new HookahException("空数据！");

        Byte maxSortVal = homeImageMapper.findMaxSortVal(obj.getImgType());
        if(maxSortVal != null){
            //每次插入图片，保证当前数据最大排序值+1
            obj.setImgSort(Byte.valueOf(String.valueOf(maxSortVal.intValue() + 1)));
        }else{
            obj.setImgSort(Byte.valueOf("1"));
        }
        obj.setUserId(currentUser.getUserId());
        obj = (HomeImageVo) super.insert(obj);
    }

    @Override
    public int updateSortValByImgId(String imgId, Byte imgType) {
        return homeImageMapper.updateSortValByImgId(imgId, imgType);
    }

    @Override
    public void sort(String imgId, String flg){
        if("DESC".equals(flg)){
            // 该图片降序，imgSort值加1，相应的下一个图片的imgSort-1
            // 根据imgId获取该图片的imgSort,加1后，作更新处理
            HomeImage descHomeImage = this.selectById(imgId);
            Byte ascImgSort = descHomeImage.getImgSort();
            Byte descImgSort = Byte.valueOf(String.valueOf(ascImgSort.intValue() + 1 ));

            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("imgSort", descImgSort));
            // 下一个图片实例
            HomeImage ascHomeImage = this.selectList(filters).get(0);

            // 该图片imgSort,加1后，作更新操作
            descHomeImage.setImgSort(descImgSort);
            this.updateByIdSelective(descHomeImage);

            // 获取相应下一个图片的imgSort,减1后，作更新操作
            ascHomeImage.setImgSort(ascImgSort);
            this.updateByIdSelective(ascHomeImage);

        } else {

            //该图片升序，imgSort值-1，相应上一个图片的imgSort+1
            // 根据imgId获取该图片的imgSort,减1后，作更新处理
            HomeImage ascHomeImage = this.selectById(imgId);
            Byte descImgSort = ascHomeImage.getImgSort();
            Byte ascImgSort = Byte.valueOf(String.valueOf(descImgSort.intValue() - 1 ));

            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("imgSort", ascImgSort));
            //上一个图片示例
            HomeImage descHomeImage = this.selectList(filters).get(0);

            // 该图片imgSort,减1后，作更新操作
            ascHomeImage.setImgSort(ascImgSort);
            this.updateByIdSelective(ascHomeImage);
            // 获取相应上一个图片的imgSort,加1后，作更新操作
            descHomeImage.setImgSort(descImgSort);
            this.updateByIdSelective(descHomeImage);
        }
    }

    @Override
    public List<HomeImage> getImageInfoList() {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("imgType", 1));
        List<OrderBy> orderBys = new ArrayList();
        orderBys.add(OrderBy.asc("imgSort"));
        return this.selectList(filters, orderBys);
    }
}
