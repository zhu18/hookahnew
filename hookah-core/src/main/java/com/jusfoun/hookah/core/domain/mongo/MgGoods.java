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
    private PackageApiInfoBean packageApiInfo;
    private DataModelBean dataModel;
    private ASSaaSBean asSaaS;
    private ASAloneSoftwareBean asAloneSoftware;
    private ATSaaSBean atSaaS;
    private ATAloneSoftwareBean atAloneSoftware;
    private Long clickRate;
    private OffLineInfoBean offLineInfo;//线下交付信息
    private OffLineDataBean offLineData;//离线数据信息

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

    public ASSaaSBean getAsSaaS() {
        return asSaaS;
    }

    public void setAsSaaS(ASSaaSBean asSaaS) {
        this.asSaaS = asSaaS;
    }

    public ASAloneSoftwareBean getAsAloneSoftware() {
        return asAloneSoftware;
    }

    public void setAsAloneSoftware(ASAloneSoftwareBean asAloneSoftware) {
        this.asAloneSoftware = asAloneSoftware;
    }

    public ATSaaSBean getAtSaaS() {
        return atSaaS;
    }

    public void setAtSaaS(ATSaaSBean atSaaS) {
        this.atSaaS = atSaaS;
    }

    public ATAloneSoftwareBean getAtAloneSoftware() {
        return atAloneSoftware;
    }

    public void setAtAloneSoftware(ATAloneSoftwareBean atAloneSoftware) {
        this.atAloneSoftware = atAloneSoftware;
    }

    public void setApiInfo(ApiInfoBean apiInfo) {
        this.apiInfo = apiInfo;
    }

    public PackageApiInfoBean getPackageApiInfo() {
        return packageApiInfo;
    }

    public void setPackageApiInfo(PackageApiInfoBean packageApiInfo) {
        this.packageApiInfo = packageApiInfo;
    }

    public static class FormatBean implements Serializable {
        /**
         * format : 1
         * number : 1
         * price : 500
         * status : 0
         */

        private Integer formatId; //规格ID
        private String formatName; //名称
        private int format; //类型
        private Integer number; //规格
        private Long price; //价格

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

        private String apiType; // 接口类型： 0：restful ,1：webservice
        private String invokeMethod; //调用方法名
        private String respDataFormat; // 返回数据格式 0：json ,1：xml , 2：text
        private EncryptInfo encryptInfo; //加密信息
//        private String secretKeyName; // 密钥名称
//        private String secretKeyValue; //密钥值

        private RespDataMapping respDataMapping; //返回数据映射
        private String updateFreq; //更新频率
        private Integer dataNumDivRowNum; //数据条数/行数


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

        public void setReqParamList(List<FiledBean> reqParamList) { this.reqParamList = reqParamList; }

        public List<FiledBean> getRespParamList() {
            return respParamList;
        }

        public void setRespParamList(List<FiledBean> respParamList) {
            this.respParamList = respParamList;
        }

        public String getRespSample() { return respSample; }

        public void setRespSample(String respSample) {
            this.respSample = respSample;
        }

        public String getApiType() {
            return apiType;
        }

        public void setApiType(String apiType) {
            this.apiType = apiType;
        }

        public String getRespDataFormat() {
            return respDataFormat;
        }

        public void setRespDataFormat(String respDataFormat) {
            this.respDataFormat = respDataFormat;
        }

        public String getInvokeMethod() {
            return invokeMethod;
        }

        public void setInvokeMethod(String invokeMethod) {
            this.invokeMethod = invokeMethod;
        }

        public EncryptInfo getEncryptInfo() {
            return encryptInfo;
        }

        public void setEncryptInfo(EncryptInfo encryptInfo) {
            this.encryptInfo = encryptInfo;
        }

        public RespDataMapping getRespDataMapping() {
            return respDataMapping;
        }

        public void setRespDataMapping(RespDataMapping respDataMapping) {
            this.respDataMapping = respDataMapping;
        }

        public String getUpdateFreq() {
            return updateFreq;
        }

        public void setUpdateFreq(String updateFreq) {
            this.updateFreq = updateFreq;
        }

        public Integer getDataNumDivRowNum() {
            return dataNumDivRowNum;
        }

        public void setDataNumDivRowNum(Integer dataNumDivRowNum) {
            this.dataNumDivRowNum = dataNumDivRowNum;
        }
    }

    /**
     *  add by guoruibing 2017-07-05
     */
    public static class EncryptInfo implements Serializable {
        private String secretKeyName; // 密钥名称
        private String secretKeyValue; //密钥值

        public String getSecretKeyName() {
            return secretKeyName;
        }

        public void setSecretKeyName(String secretKeyName) {
            this.secretKeyName = secretKeyName;
        }

        public String getSecretKeyValue() {
            return secretKeyValue;
        }

        public void setSecretKeyValue(String secretKeyValue) {
            this.secretKeyValue = secretKeyValue;
        }
    }
    public static class RespDataMapping implements Serializable {

        private String codeAttr; //编码属性
        private String successCode; //成功
        private String failedCode; //失败
        private String successNoData; // 成功无数据
        private String infoAttr; // 信息属性
        private String dataAttr; //数据属性
        private String totalNumAttr; //总条数属性

        public String getCodeAttr() {
            return codeAttr;
        }

        public void setCodeAttr(String codeAttr) {
            this.codeAttr = codeAttr;
        }

        public String getSuccessCode() {
            return successCode;
        }

        public void setSuccessCode(String successCode) {
            this.successCode = successCode;
        }

        public String getFailedCode() {
            return failedCode;
        }

        public void setFailedCode(String failedCode) {
            this.failedCode = failedCode;
        }

        public String getSuccessNoData() {
            return successNoData;
        }

        public void setSuccessNoData(String successNoData) {
            this.successNoData = successNoData;
        }

        public String getInfoAttr() {
            return infoAttr;
        }

        public void setInfoAttr(String infoAttr) {
            this.infoAttr = infoAttr;
        }

        public String getDataAttr() {
            return dataAttr;
        }

        public void setDataAttr(String dataAttr) {
            this.dataAttr = dataAttr;
        }

        public String getTotalNumAttr() {
            return totalNumAttr;
        }

        public void setTotalNumAttr(String totalNumAttr) {
            this.totalNumAttr = totalNumAttr;
        }
    }

    public static class PackageApiInfoBean implements Serializable {
        private String apiUrl; //接口地址
        private String apiMethod;//请求方式：GET/POST
        private String reqSample;//请求示例
        private String apiDesc;//接口描述
        private List<FiledBean> reqParamList;//请求参数
        private List<FiledBean> respParamList;//返回参数
        private String respSample;//返回示例

        private String apiType; // 接口类型： restful/webservice
        private String invokeMethod; //调用方法名
        private String respDataFormat; // 返回数据格式 json/xml
        private String secretKeyName; // 密钥名称
        private String secretKeyValue; //密钥值

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

        public void setReqParamList(List<FiledBean> reqParamList) { this.reqParamList = reqParamList; }

        public List<FiledBean> getRespParamList() {
            return respParamList;
        }

        public void setRespParamList(List<FiledBean> respParamList) {
            this.respParamList = respParamList;
        }

        public String getRespSample() { return respSample; }

        public void setRespSample(String respSample) {
            this.respSample = respSample;
        }

        public String getApiType() {
            return apiType;
        }

        public void setApiType(String apiType) {
            this.apiType = apiType;
        }

        public String getRespDataFormat() {
            return respDataFormat;
        }

        public void setRespDataFormat(String respDataFormat) {
            this.respDataFormat = respDataFormat;
        }

        public String getInvokeMethod() {
            return invokeMethod;
        }

        public void setInvokeMethod(String invokeMethod) {
            this.invokeMethod = invokeMethod;
        }

        public String getSecretKeyName() {
            return secretKeyName;
        }

        public void setSecretKeyName(String secretKeyName) {
            this.secretKeyName = secretKeyName;
        }

        public String getSecretKeyValue() {
            return secretKeyValue;
        }

        public void setSecretKeyValue(String secretKeyValue) {
            this.secretKeyValue = secretKeyValue;
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
        private String modelFilePwd; //模型文件密码
        private String configFilePwd; // 配置文件密码
        private String configParamsPwd;// 配置参数密码
        private String otherDesc;
        private OffLineInfoBean concatInfo;// 联系信息
        private String deliveryMethod;//交付方式

        public String getDeliveryMethod() {
            return deliveryMethod;
        }

        public void setDeliveryMethod(String deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

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

        public String getOtherDesc() {
            return otherDesc;
        }

        public void setOtherDesc(String otherDesc) {
            this.otherDesc = otherDesc;
        }

        public String getModelFilePwd() {
            return modelFilePwd;
        }

        public void setModelFilePwd(String modelFilePwd) {
            this.modelFilePwd = modelFilePwd;
        }

        public String getConfigFilePwd() {
            return configFilePwd;
        }

        public void setConfigFilePwd(String configFilePwd) {
            this.configFilePwd = configFilePwd;
        }

        public String getConfigParamsPwd() {
            return configParamsPwd;
        }

        public void setConfigParamsPwd(String configParamsPwd) {
            this.configParamsPwd = configParamsPwd;
        }

        public OffLineInfoBean getConcatInfo() {
            return concatInfo;
        }

        public void setConcatInfo(OffLineInfoBean concatInfo) {
            this.concatInfo = concatInfo;
        }
    }

    public static class ASSaaSBean implements Serializable {

        // 应用场景  ApplicationScene SaaS
        private String sSComplexity;  // 复杂度
        private String sSVersionDesc;  // 版本说明
        private String sServiceLevel;  // 服务等级
        private String sSAexp;        // 应用经验
        private String sSAintroduce;  // 应用介绍
        private String otherDesc;
        private String dataAddress;//数据地址
        private String sUser;//用户名
        private String sPwd;//密码
        private String coreFunction;//核心功能
        private String teamAdvantage;//团队优势
        private String desiredEnvironment;//所需环境
        private String dataNeeded;//所需数据
        private String deliveryMethod;//交付方式

        public String getCoreFunction() {
            return coreFunction;
        }

        public void setCoreFunction(String coreFunction) {
            this.coreFunction = coreFunction;
        }

        public String getTeamAdvantage() {
            return teamAdvantage;
        }

        public void setTeamAdvantage(String teamAdvantage) {
            this.teamAdvantage = teamAdvantage;
        }

        public String getDesiredEnvironment() {
            return desiredEnvironment;
        }

        public void setDesiredEnvironment(String desiredEnvironment) {
            this.desiredEnvironment = desiredEnvironment;
        }

        public String getDataNeeded() {
            return dataNeeded;
        }

        public void setDataNeeded(String dataNeeded) {
            this.dataNeeded = dataNeeded;
        }

        public String getDeliveryMethod() {
            return deliveryMethod;
        }

        public void setDeliveryMethod(String deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

        public String getsSComplexity() {
            return sSComplexity;
        }

        public void setsSComplexity(String sSComplexity) {
            this.sSComplexity = sSComplexity;
        }

        public String getsSVersionDesc() {
            return sSVersionDesc;
        }

        public void setsSVersionDesc(String sSVersionDesc) {
            this.sSVersionDesc = sSVersionDesc;
        }

        public String getsServiceLevel() {
            return sServiceLevel;
        }

        public void setsServiceLevel(String sServiceLevel) {
            this.sServiceLevel = sServiceLevel;
        }

        public String getsSAexp() {
            return sSAexp;
        }

        public void setsSAexp(String sSAexp) {
            this.sSAexp = sSAexp;
        }

        public String getsSAintroduce() {
            return sSAintroduce;
        }

        public void setsSAintroduce(String sSAintroduce) {
            this.sSAintroduce = sSAintroduce;
        }

        public String getOtherDesc() {
            return otherDesc;
        }

        public void setOtherDesc(String otherDesc) {
            this.otherDesc = otherDesc;
        }

        public String getDataAddress() {
            return dataAddress;
        }

        public void setDataAddress(String dataAddress) {
            this.dataAddress = dataAddress;
        }

        public String getsUser() {
            return sUser;
        }

        public void setsUser(String sUser) {
            this.sUser = sUser;
        }

        public String getsPwd() {
            return sPwd;
        }

        public void setsPwd(String sPwd) {
            this.sPwd = sPwd;
        }
    }

    public static class ASAloneSoftwareBean implements Serializable {

        // 应用场景  ApplicationScene  独立软件
        private String aSComplexity;  // 复杂度
        private String aSVersionDesc;  // 版本说明
        private String aSServiceLevel;  // 服务等级
        private String aSAexp;        // 应用经验
        private String aSAintroduce;  // 应用介绍
        private String aSCloudHardwareResource;  // 云硬件资源
        private String otherDesc;
        private String dataAddress;//数据地址
        private String coreFunction;//核心功能
        private String technologicalSuperiority;//技术优势
        private String teamAdvantage;//团队优势
        private String desiredEnvironment;//所需环境
        private String dataNeeded;//所需数据
        private String deliveryMethod;//交付方式

        public String getCoreFunction() {
            return coreFunction;
        }

        public void setCoreFunction(String coreFunction) {
            this.coreFunction = coreFunction;
        }

        public String getTechnologicalSuperiority() {
            return technologicalSuperiority;
        }

        public void setTechnologicalSuperiority(String technologicalSuperiority) {
            this.technologicalSuperiority = technologicalSuperiority;
        }

        public String getTeamAdvantage() {
            return teamAdvantage;
        }

        public void setTeamAdvantage(String teamAdvantage) {
            this.teamAdvantage = teamAdvantage;
        }

        public String getDesiredEnvironment() {
            return desiredEnvironment;
        }

        public void setDesiredEnvironment(String desiredEnvironment) {
            this.desiredEnvironment = desiredEnvironment;
        }

        public String getDataNeeded() {
            return dataNeeded;
        }

        public void setDataNeeded(String dataNeeded) {
            this.dataNeeded = dataNeeded;
        }

        public String getDeliveryMethod() {
            return deliveryMethod;
        }

        public void setDeliveryMethod(String deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

        public String getaSComplexity() {
            return aSComplexity;
        }

        public void setaSComplexity(String aSComplexity) {
            this.aSComplexity = aSComplexity;
        }

        public String getaSVersionDesc() {
            return aSVersionDesc;
        }

        public void setaSVersionDesc(String aSVersionDesc) {
            this.aSVersionDesc = aSVersionDesc;
        }

        public String getaSServiceLevel() {
            return aSServiceLevel;
        }

        public void setaSServiceLevel(String aSServiceLevel) {
            this.aSServiceLevel = aSServiceLevel;
        }

        public String getaSAexp() {
            return aSAexp;
        }

        public void setaSAexp(String aSAexp) {
            this.aSAexp = aSAexp;
        }

        public String getaSAintroduce() {
            return aSAintroduce;
        }

        public void setaSAintroduce(String aSAintroduce) {
            this.aSAintroduce = aSAintroduce;
        }

        public String getaSCloudHardwareResource() {
            return aSCloudHardwareResource;
        }

        public void setaSCloudHardwareResource(String aSCloudHardwareResource) {
            this.aSCloudHardwareResource = aSCloudHardwareResource;
        }

        public String getOtherDesc() {
            return otherDesc;
        }

        public void setOtherDesc(String otherDesc) {
            this.otherDesc = otherDesc;
        }

        public String getDataAddress() {
            return dataAddress;
        }

        public void setDataAddress(String dataAddress) {
            this.dataAddress = dataAddress;
        }
    }

    public static class ATSaaSBean implements Serializable {

        // 分析工具 SaaS
        private String aTIndustryField;       // 行业领域
        private String aTVersionDesc;       // 版本说明
        private String aTToolsIntroduce;       // 工具介绍
        private String otherDesc;
        private String dataAddress;//数据地址
        private String sUser;//用户名
        private String sPwd;//密码

        public String getaTIndustryField() {
            return aTIndustryField;
        }

        public void setaTIndustryField(String aTIndustryField) {
            this.aTIndustryField = aTIndustryField;
        }

        public String getaTVersionDesc() {
            return aTVersionDesc;
        }

        public void setaTVersionDesc(String aTVersionDesc) {
            this.aTVersionDesc = aTVersionDesc;
        }

        public String getaTToolsIntroduce() {
            return aTToolsIntroduce;
        }

        public void setaTToolsIntroduce(String aTToolsIntroduce) {
            this.aTToolsIntroduce = aTToolsIntroduce;
        }

        public String getOtherDesc() {
            return otherDesc;
        }

        public void setOtherDesc(String otherDesc) {
            this.otherDesc = otherDesc;
        }

        public String getDataAddress() {
            return dataAddress;
        }

        public void setDataAddress(String dataAddress) {
            this.dataAddress = dataAddress;
        }

        public String getsUser() {
            return sUser;
        }

        public void setsUser(String sUser) {
            this.sUser = sUser;
        }

        public String getsPwd() {
            return sPwd;
        }

        public void setsPwd(String sPwd) {
            this.sPwd = sPwd;
        }
    }

    public static class ATAloneSoftwareBean implements Serializable {

        // 分析工具 独立软件  AnalysisToolAloneSoftware
        private String aTAloneIndustryField;       // 行业领域
        private String aTAloneVersionDesc;       // 版本说明
        private String aTAloneToolsIntroduce;       // 工具介绍
        private String aTAloneCloudHardwareResource;  // 云硬件资源
        private String dataAddress;//数据地址
        private String otherDesc;

        public String getaTAloneIndustryField() {
            return aTAloneIndustryField;
        }

        public void setaTAloneIndustryField(String aTAloneIndustryField) {
            this.aTAloneIndustryField = aTAloneIndustryField;
        }

        public String getaTAloneVersionDesc() {
            return aTAloneVersionDesc;
        }

        public void setaTAloneVersionDesc(String aTAloneVersionDesc) {
            this.aTAloneVersionDesc = aTAloneVersionDesc;
        }

        public String getaTAloneToolsIntroduce() {
            return aTAloneToolsIntroduce;
        }

        public void setaTAloneToolsIntroduce(String aTAloneToolsIntroduce) {
            this.aTAloneToolsIntroduce = aTAloneToolsIntroduce;
        }

        public String getaTAloneCloudHardwareResource() {
            return aTAloneCloudHardwareResource;
        }

        public void setaTAloneCloudHardwareResource(String aTAloneCloudHardwareResource) {
            this.aTAloneCloudHardwareResource = aTAloneCloudHardwareResource;
        }

        public String getOtherDesc() {
            return otherDesc;
        }

        public void setOtherDesc(String otherDesc) {
            this.otherDesc = otherDesc;
        }

        public String getDataAddress() {
            return dataAddress;
        }

        public void setDataAddress(String dataAddress) {
            this.dataAddress = dataAddress;
        }
    }

    public static class OffLineInfoBean implements Serializable {
        private String concatName; // 联系名称
        private String concatPhone; // 联系电话
        private String concatEmail; // 联系邮件

        public String getConcatName() {
            return concatName;
        }

        public void setConcatName(String concatName) {
            this.concatName = concatName;
        }

        public String getConcatPhone() {
            return concatPhone;
        }

        public void setConcatPhone(String concatPhone) {
            this.concatPhone = concatPhone;
        }

        public String getConcatEmail() {
            return concatEmail;
        }

        public void setConcatEmail(String concatEmail) {
            this.concatEmail = concatEmail;
        }
    }

    public static class OffLineDataBean implements Serializable {
        private String isOnline; //数据来源: 0 数据文件； 1 数据地址
        private String onlineUrl;//在线地址
        private String localUrl;//本地上传地址
        private String dataPwd;//数据密码
        private String timeFrame;//时间范围
        private String dataRows;//数据行数
        private String dataCapacity;//数据容量
        private String dataFormat ;//数据格式
        private String deliveryMethod;//交付方式

        public String getTimeFrame() {
            return timeFrame;
        }

        public void setTimeFrame(String timeFrame) {
            this.timeFrame = timeFrame;
        }

        public String getDataRows() {
            return dataRows;
        }

        public void setDataRows(String dataRows) {
            this.dataRows = dataRows;
        }

        public String getDataCapacity() {
            return dataCapacity;
        }

        public void setDataCapacity(String dataCapacity) {
            this.dataCapacity = dataCapacity;
        }

        public String getDataFormat() {
            return dataFormat;
        }

        public void setDataFormat(String dataFormat) {
            this.dataFormat = dataFormat;
        }

        public String getDeliveryMethod() {
            return deliveryMethod;
        }

        public void setDeliveryMethod(String deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
        }

        public String getOnlineUrl() {
            return onlineUrl;
        }

        public void setOnlineUrl(String onlineUrl) {
            this.onlineUrl = onlineUrl;
        }

        public String getLocalUrl() {
            return localUrl;
        }

        public void setLocalUrl(String localUrl) {
            this.localUrl = localUrl;
        }

        public String getDataPwd() {
            return dataPwd;
        }

        public void setDataPwd(String dataPwd) {
            this.dataPwd = dataPwd;
        }
    }

    public OffLineInfoBean getOffLineInfo() {
        return offLineInfo;
    }

    public void setOffLineInfo(OffLineInfoBean offLineInfo) {
        this.offLineInfo = offLineInfo;
    }

    public OffLineDataBean getOffLineData() {
        return offLineData;
    }

    public void setOffLineData(OffLineDataBean offLineData) {
        this.offLineData = offLineData;
    }

}
