package com.jusfoun.hookah.console.server.common.listener;

import com.jusfoun.hookah.console.server.config.Constants;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.rpc.api.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wangjl on 2017-4-19.
 */
@Component
public class RabbitMQEsGoodsListener {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQEsGoodsListener.class);
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
                if(HookahConstants.GOODS_STATUS_DELETE.equals(goods.getIsDelete())
                        || HookahConstants.GOODS_STATUS_OFFSALE.equals(goods.getIsOnsale())) {
                    elasticSearchService.deleteById(Constants.GOODS_INDEX,
                            Constants.GOODS_TYPE, goodsId);
                }else if(HookahConstants.GOODS_STATUS_UNDELETE.equals(goods.getIsDelete())
                        && HookahConstants.GOODS_STATUS_ONSALE.equals(goods.getIsOnsale())) {

                    EsGoods esGoods = goodsMapper.getNeedEsGoodsById(goodsId);
                    if(esGoods != null) {
                        Map<String, Object> map = elasticSearchService.completionEsGoods(esGoods);
                        // 如果isDelete == 1 并且 is_onsale == 1 添加或者修改es中的商品
                        elasticSearchService.upsertById(Constants.GOODS_INDEX,
                                Constants.GOODS_TYPE, goodsId, map);
                    }else {
                        logger.warn(goodsId + ":esGoods赋值为null");
                    }
                }
            }else {
                //删除ES中的商品
                elasticSearchService.deleteById("qingdao-goods-v1",
                        "goods", goodsId);
            }
        }catch (Exception e) {
            logger.error(goodsId + "执行错误！" + e);
            e.printStackTrace();
        }
    }
}
