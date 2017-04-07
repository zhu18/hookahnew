package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

// FIXME generate failure  field _$FormatList191

/**
 * Created by ctp 2017-04-05.
 */
@Document
public class MgShelvesGoods extends GenericModel {


    /**
     * shelvesGoodsId: 货架id
     * shelvesGoodsName: 货架名字
     * goodsIdList : [goodsId]
     * goodsList : [{"imgUrl":"http: xxxx","imgDesc":"kslkfklskl","weight":1}]
     */
    @Id
    private String shelvesGoodsId;
    private String shelvesGoodsName;
//    private List<Goods> goodsList;
    private List<String> goodsIdList;
    private List<GoodsBean> goodsList;


    public String getShelvesGoodsId() {
        return shelvesGoodsId;
    }

    public String getShelvesGoodsName() {
        return shelvesGoodsName;
    }

    public void setShelvesGoodsId(String shelvesGoodsId) {
        this.shelvesGoodsId = shelvesGoodsId;
    }

    public void setShelvesGoodsName(String shelvesGoodsName) {
        this.shelvesGoodsName = shelvesGoodsName;
    }

    public List<String> getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List<String> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    public List<GoodsBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsBean implements Serializable {
        /**
         * goodsId : 1
         * goodsDesc : 1
         * number : 500
         * price : 0
         * imageUrl
         * goodsUrl
         */

        private Integer goodsId;
        private String goodsDesc;
        private Integer number;
        private Long price;
        private String imageUrl;
        private String goodsUrl;


        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }

        public Integer getGoodsId() {
            return goodsId;
        }

        public String getGoodsDesc() {
            return goodsDesc;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getGoodsUrl() {
            return goodsUrl;
        }

        public void setGoodsId(Integer goodsId) {
            this.goodsId = goodsId;
        }

        public void setGoodsDesc(String goodsDesc) {
            this.goodsDesc = goodsDesc;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setGoodsUrl(String goodsUrl) {
            this.goodsUrl = goodsUrl;
        }
    }
}