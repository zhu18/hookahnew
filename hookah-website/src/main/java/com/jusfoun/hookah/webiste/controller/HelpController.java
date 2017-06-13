package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.HelpService;
import com.jusfoun.hookah.rpc.api.MgOrderInfoService;
import com.jusfoun.hookah.webiste.util.FreemarkerWord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author huang lei
 * @date 2017/4/10 下午1:47
 * @desc
 */
@Controller
@RequestMapping("/help")
public class HelpController extends BaseController{

    @Resource
    HelpService helpService;

    @Resource
    GoodsService goodsService;

    @Resource
    MgOrderInfoService mgOrderInfoService;

    @RequestMapping(value = "/{id}.html", method = RequestMethod.GET)
    public String index(@PathVariable("id") String id, Model model) {
        Map<String,Object> result = new HashMap<String,Object>();
        List<Help> helpList = helpService.selectList();
        result.put("helpList",helpList);
        Help help = helpService.selectById(id);
        result.put("help",help);
        model.addAttribute("result",result);
        return "/help/help_index";
    }
    @RequestMapping(value = "/privacy", method = RequestMethod.GET)
    public String privacy() {
        return "/help/privacyStatement";
    }

    @RequestMapping(value = "/buyers_guide", method = RequestMethod.GET)
    public String buyers() {
        return "/help/buyers_guide";
    }
    @RequestMapping(value = "/arrival_guide", method = RequestMethod.GET)
    public String arrival () {
        return "/help/arrival_guide";
    }

    @RequestMapping(value = "/resource_cooperation", method = RequestMethod.GET)
    public String resource () {
        return "/help/resource_cooperation";
    }

    @RequestMapping(value = "/brand_cooperation", method = RequestMethod.GET)
    public String brand () {
        return "/help/brand_cooperation";
    }

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public String question() {
        return "/help/question";
    }

    @RequestMapping(value = "/agreement", method = RequestMethod.GET)
    public String agreement() {
        return "/help/service_agreement";
    }

    @ResponseBody
    @RequestMapping(value = "exportWords", method = RequestMethod.GET)
    public ReturnData exportWords(@RequestParam("goodsId") String goodsId,
                                  @RequestParam("orderNo") String orderNo,
                                  @RequestParam("sourceId") String sourceId) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try{

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
            filters.add(Condition.eq("orderGoodsList.goodsId", goodsId));
            filters.add(Condition.eq("payStatus", 2));
            OrderInfoVo orderInfoVo = mgOrderInfoService.selectOne(filters);

            if(orderInfoVo == null){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("仅限购买支付成功用户下载");
                return returnData;
            }

            Map<String, Object> dataMap = new HashMap<>();
            GoodsVo goodsVo = goodsService.findGoodsByIdWebsite(goodsId);

            if(goodsVo != null){
                dataMap.put("goodsName", goodsVo.getGoodsName());

                if(goodsVo.getGoodsType() == 0) { // 普通文件

                    if(!StringUtils.isNotBlank(goodsVo.getUploadUrl())){
                        returnData.setMessage("文件下载地址不存在，请联系管理员！^_^");
                        returnData.setCode(ExceptionConst.Failed);
                        return returnData;
                    }
                    returnData.setData("http://static.qddata.com.cn/upload/download?filePath=" + goodsVo.getUploadUrl());
                    return returnData;
                }else if(goodsVo.getGoodsType() == 1) { //api

//                private String apiUrl; //接口地址
//                private String apiMethod;//请求方式：GET/POST
//                private String reqSample;//请求示例
//                private String apiDesc;//接口描述
//                private List<FiledBean> reqParamList;//请求参数
//                private List<FiledBean> respParamList;//返回参数
//                private String respSample;//返回示例

//                    dataMap.put("apiUrl", goodsVo.getApiInfo().getApiUrl().replace("&", "&amp;"));
//                    dataMap.put("apiMethod", goodsVo.getApiInfo().getApiMethod());
//                    dataMap.put("reqSample", goodsVo.getApiInfo().getReqSample() == null ? goodsVo.getApiInfo().getReqSample() : goodsVo.getApiInfo().getReqSample().replace("&", "&amp;"));
//                    dataMap.put("apiDesc", goodsVo.getApiInfo().getApiDesc() == null ? goodsVo.getApiInfo().getApiDesc() : goodsVo.getApiInfo().getApiDesc().replace("&", "&amp;"));
//                    dataMap.put("reqParamList", goodsVo.getApiInfo().getReqParamList());
//                    dataMap.put("respParamList", goodsVo.getApiInfo().getRespParamList());
//                    dataMap.put("respSample", goodsVo.getApiInfo().getRespSample() == null ? goodsVo.getApiInfo().getRespSample() : goodsVo.getApiInfo().getRespSample().replace("&", "&amp;"));

                    returnData.setData("http://open.galaxybigdata.com/down/doc?apiId=" + sourceId + "&orderNo=" + orderNo);
//                    returnData.setData("http://open.galaxybigdata.com/down/doc?apiId=2&orderNo=1");
                    return returnData;
                }else if(goodsVo.getGoodsType() == 2){ // 数据模型

//                private String complexity;  // 复杂度
//                private String maturity;    // 成熟度
//                private String aexp;        // 应用经验
//                private String modelFile;   // 模型文件
//                private String configFile;  // 配置文件
//                private String configParams;    // 配置参数
//                private String otherDesc;
                    dataMap.put("complexity", goodsVo.getDataModel().getComplexity());
                    dataMap.put("maturity", goodsVo.getDataModel().getMaturity());
                    dataMap.put("aexp", goodsVo.getDataModel().getAexp());
                    dataMap.put("modelFile", goodsVo.getDataModel().getModelFile());
                    dataMap.put("configFile", goodsVo.getDataModel().getConfigFile());
                    dataMap.put("configParams", goodsVo.getDataModel().getConfigParams());
                    dataMap.put("otherDesc", goodsVo.getDataModel().getOtherDesc());


                }else if(goodsVo.getGoodsType() == 4){
                    // 分析工具 独立软件  AnalysisToolAloneSoftware
//                private String aTAloneIndustryField;       // 行业领域
//                private String aTAloneVersionDesc;       // 版本说明
//                private String aTAloneToolsIntroduce;       // 工具介绍
//                private String aTAloneCloudHardwareResource;  // 云硬件资源
//                private String otherDesc;
                    dataMap.put("aTAloneIndustryField", goodsVo.getAtAloneSoftware().getaTAloneIndustryField());
                    dataMap.put("aTAloneVersionDesc", goodsVo.getAtAloneSoftware().getaTAloneVersionDesc());
                    dataMap.put("aTAloneToolsIntroduce", goodsVo.getAtAloneSoftware().getaTAloneToolsIntroduce());
                    dataMap.put("aTAloneCloudHardwareResource", goodsVo.getAtAloneSoftware().getaTAloneCloudHardwareResource());
                    dataMap.put("otherDesc", goodsVo.getAtAloneSoftware().getOtherDesc());


                }else if(goodsVo.getGoodsType() == 5){
                    // 分析工具 SaaS
//                private String aTIndustryField;       // 行业领域
//                private String aTVersionDesc;       // 版本说明
//                private String aTToolsIntroduce;       // 工具介绍
//                private String otherDesc;
                    dataMap.put("aTIndustryField", goodsVo.getAtSaaS().getaTIndustryField());
                    dataMap.put("aTVersionDesc", goodsVo.getAtSaaS().getaTVersionDesc());
                    dataMap.put("aTToolsIntroduce", goodsVo.getAtSaaS().getaTToolsIntroduce());
                    dataMap.put("otherDesc", goodsVo.getAtSaaS().getOtherDesc());


                }else if(goodsVo.getGoodsType() == 6){
                    // 应用场景  ApplicationScene  独立软件
//                private String aSComplexity;  // 复杂度
//                private String aSVersionDesc;  // 版本说明
//                private String aSServiceLevel;  // 服务等级
//                private String aSAexp;        // 应用经验
//                private String aSAintroduce;  // 应用介绍
//                private String aSCloudHardwareResource;  // 云硬件资源
//                private String otherDesc;
                    //last start
//                    dataMap.put("aSComplexity", goodsVo.getAsAloneSoftware().getaSComplexity());
//                    dataMap.put("aSVersionDesc", goodsVo.getAsAloneSoftware().getaSVersionDesc());
//                    dataMap.put("aSServiceLevel", goodsVo.getAsAloneSoftware().getaSServiceLevel());
//                    dataMap.put("aSAexp", goodsVo.getAsAloneSoftware().getaSAexp());
//                    dataMap.put("aSAintroduce", goodsVo.getAsAloneSoftware().getaSAintroduce());
//                    dataMap.put("aSCloudHardwareResource", goodsVo.getAsAloneSoftware().getaSCloudHardwareResource());
//                    dataMap.put("otherDesc", goodsVo.getAsAloneSoftware().getOtherDesc());
                    //last end
                    MgGoods.ASSaaSBean asSaaSBean = goodsVo.getAsSaaS();
                    if(Objects.nonNull(asSaaSBean) && Objects.nonNull(asSaaSBean.getDataAddress())){
                        returnData.setData(asSaaSBean.getDataAddress());
                    }else{
                        returnData.setMessage("数据地址为空，请联系管理员！^_^");
                        returnData.setCode(ExceptionConst.Failed);
                    }
                    return returnData;
                }else if(goodsVo.getGoodsType() == 7){
                    // 应用场景  ApplicationScene SaaS
//                private String sSComplexity;  // 复杂度
//                private String sSVersionDesc;  // 版本说明
//                private String sServiceLevel;  // 服务等级
//                private String sSAexp;        // 应用经验
//                private String sSAintroduce;  // 应用介绍
//                private String otherDesc;

                    //last start
//                    dataMap.put("sSComplexity", goodsVo.getAsSaaS().getsSComplexity());
//                    dataMap.put("sSVersionDesc", goodsVo.getAsSaaS().getsSVersionDesc());
//                    dataMap.put("sServiceLevel", goodsVo.getAsSaaS().getsServiceLevel());
//                    dataMap.put("sSAexp", goodsVo.getAsSaaS().getsSAexp());
//                    dataMap.put("sSAintroduce", goodsVo.getAsSaaS().getsSAintroduce());
//                    dataMap.put("otherDesc", goodsVo.getAsSaaS().getOtherDesc());
                    //last end
                    MgGoods.ASSaaSBean asSaaSBean = goodsVo.getAsSaaS();
                    if(Objects.nonNull(asSaaSBean) && Objects.nonNull(asSaaSBean.getDataAddress())){
                        returnData.setData(asSaaSBean.getDataAddress());
                    }else{
                        returnData.setMessage("数据地址为空，请联系管理员！^_^");
                        returnData.setCode(ExceptionConst.Failed);
                    }
                    return returnData;
                }else{
                    returnData.setMessage("文件类型不存在，请联系管理员！^_^");
                    returnData.setCode(ExceptionConst.Failed);
                    return returnData;
                }

//                FreemarkerWord fw = new FreemarkerWord();
                returnData = new FreemarkerWord().createDoc(dataMap, goodsVo);
            }else{
                returnData.setMessage("商品信息有误，请联系管理员！^_^");
                returnData.setCode(ExceptionConst.Failed);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            returnData.setMessage("下载失败，请联系管理员！^_^");
            returnData.setCode(ExceptionConst.Failed);
        }

        return returnData;
    }

    @ResponseBody
    @RequestMapping(value = "/helpApi/search", method = RequestMethod.GET)
    public ReturnData helpApiSearch() {
        List<Help> helps = helpService.selectList();
        return ReturnData.success(helps);
    }
}
