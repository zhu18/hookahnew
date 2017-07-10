package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

// FIXME generate failure  field _$FormatList191

/**
 * Created by wangjl on 2017-3-17.
 */
@Document
public class MgGoodsHistory extends GenericModel {


    /**
     * goods_id : 商品id
     * attrTypeList: [{xxxx}]
     * formatList : [{"format":1,"number":1,"price":500,"status":0}]
     * imgList : [{"imgUrl":"http: xxxx","imgDesc":"kslkfklskl","weight":1}]
     */
    @Id
    private String goodsId;
    private List<AllGoodsBean> mgGoodsHistoriesList;

    public static class AllGoodsBean {
        private Goods goods;
        private MgGoods mgGoods;

        public Goods getGoods() {
            return goods;
        }

        public void setGoods(Goods goods) {
            this.goods = goods;
        }

        public MgGoods getMgGoods() {
            return mgGoods;
        }

        public void setMgGoods(MgGoods mgGoods) {
            this.mgGoods = mgGoods;
        }
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public List<AllGoodsBean> getMgGoodsHistoriesList() {
        return mgGoodsHistoriesList;
    }

    public void setMgGoodsHistoriesList(List<AllGoodsBean> mgGoodsHistoriesList) {
        this.mgGoodsHistoriesList = mgGoodsHistoriesList;
    }
   }
