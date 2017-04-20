package com.jusfoun.hookah.webiste.common.listener;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.utils.AnnotationUtil;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangjl on 2017-4-19.
 */
public class RabbitMQEsGoodsListener {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    ElasticSearchService elasticSearchService;
    /**
     * 当goodsb表数据有更新时，同步更新es
     * @param goodsId
     */
    @RabbitListener(queues = RabbitmqQueue.CONTRACE_GOODS_ID)
    public void operaEs(String goodsId) {
        try {
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            if(goods != null) {
                // 如果isDelete == 0 或者 is_onsale == 0 删除es中的商品
                if(goods.getIsDelete() == HookahConstants.GOODS_STATUS_DELETE
                        || goods.getIsOnsale() == HookahConstants.GOODS_STATUS_OFFSALE) {
                    elasticSearchService.deleteById("qingdao-goods-v1",
                            "goods", goodsId);
                }else if(goods.getIsDelete() == HookahConstants.GOODS_STATUS_UNDELETE
                        && goods.getIsOnsale() == HookahConstants.GOODS_STATUS_ONSALE) {
                    // 如果isDelete == 1 并且 is_onsale == 1 添加或者修改es中的商品
                    elasticSearchService.upsertById("qingdao-goods-v1",
                            "goods", goodsId, AnnotationUtil.convert2Map(goods));
                }
            }else {
                //删除ES中的商品
                elasticSearchService.deleteById("qingdao-goods-v1",
                        "goods", goodsId);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
