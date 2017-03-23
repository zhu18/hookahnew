package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.util.List;

// FIXME generate failure  field _$FormatList191
/**
 * Created by wangjl on 2017-3-17.
 */
@Document
public class MgGoods extends GenericModel implements Serializable {


    /**
     * goods_id : 商品id
     * attrTypeList: [{xxxx}]
     * formatList : [{"format":1,"number":1,"price":500,"status":0}]
     * imgList : [{"imgUrl":"http: xxxx","imgDesc":"kslkfklskl","weight":1}]
     */
    @Id
    private String goodsId;
    private List<MgCategoryAttrType.AttrTypeBean> attrTypeList;
    private List<FormatBean> formatList;
    private List<ImgBean> imgList;

    public List<MgCategoryAttrType.AttrTypeBean> getAttrTypeList() {
        return attrTypeList;
    }

    public void setAttrTypeList(List<MgCategoryAttrType.AttrTypeBean> attrTypeList) {
        this.attrTypeList = attrTypeList;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public List<FormatBean> getFormatList() {
        return formatList;
    }

    public void setFormatList(List<FormatBean> formatList) {
        this.formatList = formatList;
    }

    public List<ImgBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgBean> imgList) {
        this.imgList = imgList;
    }

    public static class FormatBean implements Serializable {
        /**
         * format : 1
         * number : 1
         * price : 500
         * status : 0
         */

        private int format;
        private int number;
        @Digits(integer=10,fraction = 2)
        private Float price;

        public int getFormat() {
            return format;
        }

        public void setFormat(int format) {
            this.format = format;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }
    }

    public static class ImgBean implements Serializable {
        /**
         * imgUrl : http: xxxx
         * imgDesc : kslkfklskl
         * weight : 1
         */

        private String imgUrl;
        private String imgDesc;
        private int weight;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgDesc() {
            return imgDesc;
        }

        public void setImgDesc(String imgDesc) {
            this.imgDesc = imgDesc;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
