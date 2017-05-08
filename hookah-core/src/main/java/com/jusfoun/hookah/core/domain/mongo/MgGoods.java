package com.jusfoun.hookah.core.domain.mongo;

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
    private DataModelBean dataModel;
    private ApplicationSceneBean applicationScene;
    private AloneSoftwareBean aloneSoftware;
    private SaaSBean saaS;
    private Long clickRate;
    private String otherDesc;

    public List<MgCategoryAttrType.AttrTypeBean> getAttrTypeList() {
        return attrTypeList;
    }

    public void setAttrTypeList(List<MgCategoryAttrType.AttrTypeBean> attrTypeList) {
        this.attrTypeList = attrTypeList;
    }

    public Long getClickRate() {
        return clickRate;
    }

    public void setClickRate(Long clickRate) {
        this.clickRate = clickRate;
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

    public ApiInfoBean getApiInfo() {
        return apiInfo;
    }

    public DataModelBean getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModelBean dataModel) {
        this.dataModel = dataModel;
    }

    public ApplicationSceneBean getApplicationScene() {
        return applicationScene;
    }

    public void setApplicationScene(ApplicationSceneBean applicationScene) {
        this.applicationScene = applicationScene;
    }

    public AloneSoftwareBean getAloneSoftware() {
        return aloneSoftware;
    }

    public void setAloneSoftware(AloneSoftwareBean aloneSoftware) {
        this.aloneSoftware = aloneSoftware;
    }

    public SaaSBean getSaaS() {
        return saaS;
    }

    public void setSaaS(SaaSBean saaS) {
        this.saaS = saaS;
    }

    public String getOtherDesc() {
        return otherDesc;
    }

    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }

    public void setApiInfo(ApiInfoBean apiInfo) {
        this.apiInfo = apiInfo;
    }

    public static class FormatBean implements Serializable {
        /**
         * format : 1
         * number : 1
         * price : 500
         * status : 0
         */

        private Integer formatId;
        private String formatName;
        private int format;
        private Integer number;
        private Long price;

        public int getFormat() {
            return format;
        }

        public void setFormat(int format) {
            this.format = format;
        }

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

        public Integer getFormatId() {
            return formatId;
        }

        public void setFormatId(Integer formatId) {
            this.formatId = formatId;
        }

        public String getFormatName() {
            return formatName;
        }

        public void setFormatName(String formatName) {
            this.formatName = formatName;
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

    public static class DataModelBean implements Serializable {

        // 数据模型
        private String complexity;  // 复杂度
        private String maturity;    // 成熟度
        private String aexp;        // 应用经验
        private String modelFile;   // 模型文件
        private String configFile;  // 配置文件
        private String configParams;    // 配置参数

        public String getComplexity() {
            return complexity;
        }

        public void setComplexity(String complexity) {
            this.complexity = complexity;
        }

        public String getMaturity() {
            return maturity;
        }

        public void setMaturity(String maturity) {
            this.maturity = maturity;
        }

        public String getAexp() {
            return aexp;
        }

        public void setAexp(String aexp) {
            this.aexp = aexp;
        }

        public String getModelFile() {
            return modelFile;
        }

        public void setModelFile(String modelFile) {
            this.modelFile = modelFile;
        }

        public String getConfigFile() {
            return configFile;
        }

        public void setConfigFile(String configFile) {
            this.configFile = configFile;
        }

        public String getConfigParams() {
            return configParams;
        }

        public void setConfigParams(String configParams) {
            this.configParams = configParams;
        }
    }

    public static class ApplicationSceneBean implements Serializable {

        // 应用场景
        private String complexity;  // 复杂度
        private String versionDesc;  // 版本说明
        private String serviceLevel;  // 服务等级
        private String aexp;        // 应用经验
        private String aintroduce;  // 应用介绍
        private String cloudHardwareResource;  // 云硬件资源

        public String getComplexity() {
            return complexity;
        }

        public void setComplexity(String complexity) {
            this.complexity = complexity;
        }

        public String getVersionDesc() {
            return versionDesc;
        }

        public void setVersionDesc(String versionDesc) {
            this.versionDesc = versionDesc;
        }

        public String getServiceLevel() {
            return serviceLevel;
        }

        public void setServiceLevel(String serviceLevel) {
            this.serviceLevel = serviceLevel;
        }

        public String getAexp() {
            return aexp;
        }

        public void setAexp(String aexp) {
            this.aexp = aexp;
        }

        public String getAintroduce() {
            return aintroduce;
        }

        public void setAintroduce(String aintroduce) {
            this.aintroduce = aintroduce;
        }

        public String getCloudHardwareResource() {
            return cloudHardwareResource;
        }

        public void setCloudHardwareResource(String cloudHardwareResource) {
            this.cloudHardwareResource = cloudHardwareResource;
        }
    }

    public static class SaaSBean implements Serializable {

        // SaaS
        private String industryField;       // 行业领域
        private String versionDesc;       // 版本说明
        private String toolsIntroduce;       // 工具介绍

        public String getIndustryField() {
            return industryField;
        }

        public void setIndustryField(String industryField) {
            this.industryField = industryField;
        }

        public String getVersionDesc() {
            return versionDesc;
        }

        public void setVersionDesc(String versionDesc) {
            this.versionDesc = versionDesc;
        }

        public String getToolsIntroduce() {
            return toolsIntroduce;
        }

        public void setToolsIntroduce(String toolsIntroduce) {
            this.toolsIntroduce = toolsIntroduce;
        }
    }

    public static class AloneSoftwareBean implements Serializable {

        // 独立软件
        private String AloneIndustryField;       // 行业领域
        private String AloneVersionDesc;       // 版本说明
        private String AloneToolsIntroduce;       // 工具介绍
        private String AloneCloudHardwareResource;  // 云硬件资源

        public String getAloneIndustryField() {
            return AloneIndustryField;
        }

        public void setAloneIndustryField(String aloneIndustryField) {
            AloneIndustryField = aloneIndustryField;
        }

        public String getAloneVersionDesc() {
            return AloneVersionDesc;
        }

        public void setAloneVersionDesc(String aloneVersionDesc) {
            AloneVersionDesc = aloneVersionDesc;
        }

        public String getAloneToolsIntroduce() {
            return AloneToolsIntroduce;
        }

        public void setAloneToolsIntroduce(String aloneToolsIntroduce) {
            AloneToolsIntroduce = aloneToolsIntroduce;
        }

        public String getAloneCloudHardwareResource() {
            return AloneCloudHardwareResource;
        }

        public void setAloneCloudHardwareResource(String aloneCloudHardwareResource) {
            AloneCloudHardwareResource = aloneCloudHardwareResource;
        }
    }
}
