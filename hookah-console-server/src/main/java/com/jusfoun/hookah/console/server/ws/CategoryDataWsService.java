package com.jusfoun.hookah.console.server.ws;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.vo.ChannelDataVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.CategoryService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gring on 2017/8/11.
 */
@WebService
@Component
public class CategoryDataWsService {

    @Resource
    CategoryService categoryService;

    @Resource
    GoodsService goodsService;

    @Resource
    MqSenderService mqSenderService;

    @WebMethod
    public String receive(String jsonStr) {

        List<Category> categoryTradeList = JSON.parseArray(jsonStr, Category.class);

        List<Condition> filters = new ArrayList();

        if (categoryTradeList != null && categoryTradeList.size() > 0){

            if(categoryService.exists(filters)){
                categoryService.deleteByCondtion(filters);
                categoryService.insertBatch(categoryTradeList);
            }else{
                categoryService.insertBatch(categoryTradeList);
            }
        }else if(categoryTradeList.size() == 0){
            categoryService.deleteByCondtion(filters);
        }
        // 分类删除时，根据该分类创建的在本地上架的商品，推送到中央上架的商品，以及中央推送到目标交易所商品做下架处理
        this.repealOpera(categoryTradeList);

        return  DateUtils.toDefaultNowTime();
    }

    private void repealOpera(List<Category> categoryTradeList){

        List<Goods> goodsList = null;
        if(categoryTradeList != null && categoryTradeList.size() > 0){
            List catIdList = new ArrayList();
            for(int i=0 ;i<categoryTradeList.size(); i++){
                catIdList.add(i);
            }
            String[] catIds = (String[]) catIdList.toArray();

            // 本地商品下架处理
            goodsService.updateGoodsInfoByCatIds(catIds);

            goodsList = goodsService.goodsInfoByCatIds(catIds);

            for(Goods goods: goodsList){

                // 推送出去的商品
                if(Byte.valueOf("1").equals(goods.getIsPush())){

                    // 分类删除时，根据该分类创建的在本地上架的商品，推送到中央上架的商品，以及中央推送到目标交易所商品做下架处理
                    ChannelDataVo channelDataVo = new ChannelDataVo();
                    channelDataVo.setGoodsId(goods.getGoodsId());
                    channelDataVo.setOpera(HookahConstants.CHANNEL_PUSH_OPER_CANCEL);
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_CENTER_CHANNEL, channelDataVo);;
                }

                if(Byte.valueOf("1").equals(goods.getIsOnsale())){
                    // 从ES中删除撤回分类下的商品
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goods.getGoodsId());
                }
            }
        }else{

            goodsList = goodsService.selectList();
            for(Goods goods: goodsList){

                goods.setIsOnsale(Byte.valueOf("0"));
                goodsService.updateByIdSelective(goods);

                if(Byte.valueOf("1").equals(goods.getIsPush())){

                    // 分类删除时，根据该分类创建的在本地上架的商品，推送到中央上架的商品，以及中央推送到目标交易所商品做下架处理
                    ChannelDataVo channelDataVo = new ChannelDataVo();
                    channelDataVo.setGoodsId(goods.getGoodsId());
                    channelDataVo.setOpera(HookahConstants.CHANNEL_PUSH_OPER_CANCEL);
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_CENTER_CHANNEL, channelDataVo);;
                }
                if(Byte.valueOf("1").equals(goods.getIsOnsale())){
                    // 从ES中删除撤回分类下的商品
                    mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goods.getGoodsId());
                }
            }
        }


    }
}
