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
    private DataModelBean dataModel; // 数据模型
    private SaasAndAloneBean asSaaS; //应用场景  ApplicationScene SaaS
    private SaasAndAloneBean asAloneSoftware; //应用场景 独立软件
    private SaasAndAloneBean atSaaS; //分析工具 SaaS
    private SaasAndAloneBean atAloneSoftware;// 分析工具 独立软件
    private Long clickRate;
    private OffLineInfoBean offLineInfo;//线下交付信息
    private OffLineDataBean offLineData;//离线数据信息
    private long sales;//商品销量

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

    public void setApiInfo(ApiInfoBean apiInfo) {
        this.apiInfo = apiInfo;
    }

    public PackageApiInfoBean getPackageApiInfo() {
        return packageApiInfo;
    }

    public void setPackageApiInfo(PackageApiInfoBean packageApiInfo) {
        this.packageApiInfo = packageApiInfo;
    }

    public static class SaasAndAloneBean implements Serializable {
        private String coreFunction;//核心功能
        private String technologicalSuperiority;//技术优势
        private String teamAdvantage;//团队优势
        private String desiredEnvironment;//所需环境
        private String dataNeeded;//所需数据
        private String dataAddress;//下载地址,在线访问地址

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

        public String getDataAddress() {
            return dataAddress;
        }

        public void setDataAddress(String dataAddress) {
            this.dataAddress = dataAddress;
        }
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
        private Long settlementPrice;//结算价
        private Long agencyPrice;//代理价

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

        public Long getSettlementPrice() {
            return settlementPrice;
        }

        public void setSettlementPrice(Long settlementPrice) {
            this.settlementPrice = settlementPrice;
        }

        public Long getAgencyPrice() {
            return agencyPrice;
        }

        public void setAgencyPrice(Long agencyPrice) {
            this.agencyPrice = agencyPrice;
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
        private String dataNumDivRowNum; //数据条数/行数


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

        public String getDataNumDivRowNum() {
            return dataNumDivRowNum;
        }

        public void setDataNumDivRowNum(String dataNumDivRowNum) {
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

        private CodeAttrBean codeAttrBean; //编码属性对象
        private String infoAttr; // 信息属性
        private String dataAttr; //数据属性
        private String totalNumAttr; //总条数属性

        public CodeAttrBean getCodeAttrBean() {
            return codeAttrBean;
        }

        public void setCodeAttrBean(CodeAttrBean codeAttrBean) {
            this.codeAttrBean = codeAttrBean;
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

    public static class CodeAttrBean implements Serializable {

        private String codeAttr; //编码属性
        private CodeInfoBean codeInfoBean; // 编码属性对象信息

        public String getCodeAttr() {
            return codeAttr;
        }

        public void setCodeAttr(String codeAttr) {
            this.codeAttr = codeAttr;
        }

        public CodeInfoBean getCodeInfoBean() {
            return codeInfoBean;
        }

        public void setCodeInfoBean(CodeInfoBean codeInfoBean) {
            this.codeInfoBean = codeInfoBean;
        }
    }
    public static class CodeInfoBean implements Serializable {

        private String successCode; //成功
        private String failedCode; //失败
        private String successNoData; // 成功无数据

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
    }

    public static class PackageApiInfoBean implements Serializable {
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
        private RespDataMapping respDataMapping; //返回数据映射
        private String updateFreq; //更新频率
        private String dataNumDivRowNum; //数据条数/行数

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

        public String getDataNumDivRowNum() {
            return dataNumDivRowNum;
        }

        public void setDataNumDivRowNum(String dataNumDivRowNum) {
            this.dataNumDivRowNum = dataNumDivRowNum;
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
        private Integer modelType; // 模型类型：0 分类，1 回归，2 识别，3 预测，4 聚类，5 时间序列化，6 关联将维，7 优化
        private Integer complexity; // 复杂度: 0 高；1 中； 2 低
        private Integer maturity;    // 成熟度：0 高；1 中； 2 低
        private String aexp;        // 应用经验
        private String relationServ;   // 配套服务
        private FileBean modelFile; // 模型文件及密码
        private FileBean configFile; // 配置文件及密码
        private FileBean paramFile; // 配置参数及密码
        private OffLineInfoBean concatInfo;// 联系信息

        public Integer getModelType() {
            return modelType;
        }

        public void setModelType(Integer modelType) {
            this.modelType = modelType;
        }

        public Integer getComplexity() {
            return complexity;
        }

        public void setComplexity(Integer complexity) {
            this.complexity = complexity;
        }

        public Integer getMaturity() {
            return maturity;
        }

        public void setMaturity(Integer maturity) {
            this.maturity = maturity;
        }

        public String getAexp() {
            return aexp;
        }

        public void setAexp(String aexp) {
            this.aexp = aexp;
        }

        public String getRelationServ() {
            return relationServ;
        }

        public void setRelationServ(String relationServ) {
            this.relationServ = relationServ;
        }

        public FileBean getModelFile() {
            return modelFile;
        }

        public void setModelFile(FileBean modelFile) {
            this.modelFile = modelFile;
        }

        public FileBean getConfigFile() {
            return configFile;
        }

        public void setConfigFile(FileBean configFile) {
            this.configFile = configFile;
        }

        public FileBean getParamFile() {
            return paramFile;
        }

        public void setParamFile(FileBean paramFile) {
            this.paramFile = paramFile;
        }

        public OffLineInfoBean getConcatInfo() {
            return concatInfo;
        }

        public void setConcatInfo(OffLineInfoBean concatInfo) {
            this.concatInfo = concatInfo;
        }
    }

    public static class FileBean implements Serializable{
        private String fileAddress; // 文件地址
        private String filePwd; // 密码

        public String getFileAddress() {
            return fileAddress;
        }

        public void setFileAddress(String fileAddress) {
            this.fileAddress = fileAddress;
        }

        public String getFilePwd() {
            return filePwd;
        }

        public void setFilePwd(String filePwd) {
            this.filePwd = filePwd;
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

    public static class TimeFrameBean implements Serializable{
        private String startDate; // 开始时间
        private String endDate; // 结束时间

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }

    public static class OffLineDataBean implements Serializable {
        private TimeFrameBean timeFrame;//时间范围
        private String dataRows;//数据行数
        private String dataCapacity;//数据容量
        private Integer dataFormat ;//数据格式： 0 xls/xlsx, 1 csv, 2 txt, 3 图片， 4 音频， 5 视频， 6 其它
        private String isOnline; //数据来源: 0 数据文件； 1 数据地址
        private String onlineUrl;//在线地址
        private String localUrl;//本地上传地址
        private String dataPwd;//数据包密码

        public TimeFrameBean getTimeFrame() {
            return timeFrame;
        }

        public void setTimeFrame(TimeFrameBean timeFrame) {
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

        public Integer getDataFormat() {
            return dataFormat;
        }

        public void setDataFormat(Integer dataFormat) {
            this.dataFormat = dataFormat;
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

    public SaasAndAloneBean getAsSaaS() {
        return asSaaS;
    }

    public void setAsSaaS(SaasAndAloneBean asSaaS) {
        this.asSaaS = asSaaS;
    }

    public SaasAndAloneBean getAsAloneSoftware() {
        return asAloneSoftware;
    }

    public void setAsAloneSoftware(SaasAndAloneBean asAloneSoftware) {
        this.asAloneSoftware = asAloneSoftware;
    }

    public SaasAndAloneBean getAtSaaS() {
        return atSaaS;
    }

    public void setAtSaaS(SaasAndAloneBean atSaaS) {
        this.atSaaS = atSaaS;
    }

    public SaasAndAloneBean getAtAloneSoftware() {
        return atAloneSoftware;
    }

    public void setAtAloneSoftware(SaasAndAloneBean atAloneSoftware) {
        this.atAloneSoftware = atAloneSoftware;
    }

    public long getSales() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales = sales;
    }
}
