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
public class MgGoods extends GenericModel {


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
    private ApiInfoBean apiInfo;

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

    public static class ApiInfoBean implements Serializable {
        private String apiUrl; //接口地址
        private String apiMethod;//请求方式：GET/POST
        private String reqSample;//请求示例
        private String apiDesc;//接口描述
        private List<FiledBean> reqParamList;//请求参数
        private List<FiledBean> respParamList;//返回参数
        private String respSample;//返回示例

        public String getApiUrl() {
            return apiUrl;
        }

        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }

        public String getApiMethod() {
            return apiMethod;
        }

        public void setApiMethod(String apiMethod) {
            this.apiMethod = apiMethod;
        }

        public String getReqSample() {
            return reqSample;
        }

        public void setReqSample(String reqSample) {
            this.reqSample = reqSample;
        }

        public String getApiDesc() {
            return apiDesc;
        }

        public void setApiDesc(String apiDesc) {
            this.apiDesc = apiDesc;
        }

        public List<FiledBean> getReqParamList() {
            return reqParamList;
        }

        public void setReqParamList(List<FiledBean> reqParamList) {
            this.reqParamList = reqParamList;
        }

        public List<FiledBean> getRespParamList() {
            return respParamList;
        }

        public void setRespParamList(List<FiledBean> respParamList) {
            this.respParamList = respParamList;
        }

        public String getRespSample() {
            return respSample;
        }

        public void setRespSample(String respSample) {
            this.respSample = respSample;
        }
    }

    public static class FiledBean implements Serializable {
        private String fieldName;
        private String fieldType;
        private String isMust;//是否必须：0 否；1 是
        private String fieldSample;
        private String describle;
        private String fieldDefault;//默认值

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getIsMust() {
            return isMust;
        }

        public void setIsMust(String isMust) {
            this.isMust = isMust;
        }

        public String getFieldSample() {
            return fieldSample;
        }

        public void setFieldSample(String fieldSample) {
            this.fieldSample = fieldSample;
        }

        public String getDescrible() {
            return describle;
        }

        public void setDescrible(String describle) {
            this.describle = describle;
        }

        public String getFieldDefault() {
            return fieldDefault;
        }

        public void setFieldDefault(String fieldDefault) {
            this.fieldDefault = fieldDefault;
        }
    }
}
