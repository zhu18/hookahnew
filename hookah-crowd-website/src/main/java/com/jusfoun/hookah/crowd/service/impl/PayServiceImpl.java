package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbTrusteeRecord;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import com.jusfoun.hookah.crowd.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class PayServiceImpl implements PayService {

    protected final static Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Resource
    private ZbTrusteeRecordService zbTrusteeRecordService;

    @Resource
    MgZbRequireStatusService mgZbRequireStatusService;

    @Resource
    private ZbRequireService zbRequireService;

    @Resource
    PayAccountService payAccountService;

    @Resource
    UserService userService;

    @Override
    public String toPayByZFB(String requirementSn, String tradeSn, Long amount, String flagNum, String notify_url, String return_url) {
        return buildRequestParams(requirementSn, tradeSn, amount, flagNum, notify_url, return_url);
    }

    private String buildRequestParams(String requirementSn, String tradeSn, Long amount, String flagNum, String notify_url, String return_url) {
        Map<String, String> map = new HashMap<String, String>();
        //基本信息
        map.put("service", AlipayConfig.service);
        map.put("partner", AlipayConfig.partner);
        map.put("seller_id", AlipayConfig.seller_id);
        map.put("_input_charset", AlipayConfig.input_charset);
        map.put("payment_type", AlipayConfig.payment_type);
        map.put("notify_url", notify_url);
        map.put("return_url", return_url);
        map.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
        map.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
        //订单信息
        map.put("out_trade_no", tradeSn);//订单号
        map.put("subject", tradeSn);//String(256),商品名称/商品的标题/交易标题/订单标题/订单关键字等
        map.put("total_fee", String.valueOf((float)amount / 100));//该笔订单的资金总额,单位为RMB-Yuan。精确到小数点后两位。
        map.put("body", flagNum);//String(1000),对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        map.put("extra_common_param", requirementSn);//用户在系统中的账号（手机号或者邮箱）

        Map<String, String> params = FormFactory.paramFilter(map);
        String mysign = buildRequestMysign(params);
        params.put("sign", mysign);
        params.put("sign_type", AlipayConfig.sign_type);
        return FormFactory.buildForm(params, AlipayConfig.alipayGateway
                + "_input_charset=" + AlipayConfig.input_charset, "get");
    }

    private static String buildRequestMysign(Map<String, String> params) {
        String prestr = FormFactory.createLinkString(params); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if (AlipayConfig.sign_type.equals("MD5")) {
            mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
        }
        return mysign;
    }

    @Override
    public boolean handleZFBRs(HttpServletRequest request) {

        boolean flag = false;

        try {

            //商户订单号
            String tradeSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            Map<String,String> param = getRequestParams(request);

            flag = aliPayHandle(tradeSn, tradeStatus, param);

            return flag;

        }catch(Exception e){
           logger.error("众包项目，支付宝回调失败{}", e);
        }

        return flag;
    }

    private boolean aliPayHandle(String tradeSn, String tradeStatus, Map<String, String> param) {

        if (AlipayNotify.verify(param)){

            if(!StringUtils.isNotBlank(tradeSn)){
                return false;
            }
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("serialNo", tradeSn));
            ZbTrusteeRecord zbTrusteeRecord = zbTrusteeRecordService.selectOne(filters);
            if(zbTrusteeRecord == null){
                return false;
            }

            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
                if(zbTrusteeRecord.getRequirementId() != null){
                    ZbRequirement zbRequirement = zbRequireService.selectById(zbTrusteeRecord.getRequirementId());
                    if(zbTrusteeRecord.getTrusteeNum() == 1){
                        zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_FB.getCode().shortValue());
                    }else if(zbTrusteeRecord.getTrusteeNum() == 2){
                        zbRequirement.setStatus(ZbContants.Zb_Require_Status.WORKINGING.getCode().shortValue());
                        zbRequirement.setTrusteePercent(100);
                    }
                    zbRequireService.updateByIdSelective(zbRequirement);
                    if(zbTrusteeRecord.getTrusteeNum() == 2){
                        //供应商工作时间
                        mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.WORKINGTIME, DateUtils.toDefaultNowTime());
                    }
                }
                zbTrusteeRecord.setStatus(Short.parseShort("1"));
                int n = zbTrusteeRecordService.updateByIdSelective(zbTrusteeRecord);
                logger.info("支付宝支付成功====>" + n);
                return true;
            }else{
                zbTrusteeRecord.setStatus(Short.parseShort("2"));
                int n = zbTrusteeRecordService.updateByIdSelective(zbTrusteeRecord);
                logger.info("支付宝支付失败====>" + n);
                return false;
            }
        }else{
            logger.error("支付宝回调验证失败" + tradeSn);
            return false;
        }

    }

    @SuppressWarnings("rawtypes")
    private Map<String,String> getRequestParams(HttpServletRequest request){
        //处理通知参数
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        return params;
    }

    @Override
    public ModelAndView toPayPage(String requirementId, String trusteePercent, String userId) {

        ModelAndView mv = new ModelAndView();

        try {

            // 校验参数
            if(!StringUtils.isNotBlank(requirementId)
                    || !StringUtils.isNotBlank(trusteePercent)
                        || !StringUtils.isNotBlank(userId)){
                mv.setViewName("");
                mv.addObject("msg", "请求参数不能为空^_^");
                return mv;
            }

            // 查询资金账户信息
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userId", userId));
            PayAccount payAccount = payAccountService.selectOne(filters);
            if(payAccount == null){
                mv.setViewName("");
                mv.addObject("msg", "账户信息不存在^_^");
                return mv;
            }

            // 查询需求信息
            ZbRequirement zbRequirement =  zbRequireService.selectById(Long.parseLong(requirementId));
            if(zbRequirement == null){
                mv.setViewName("");
                mv.addObject("msg", "该需求数据不存在^_^");
                return mv;
            }

            // 根据托管比例参数  修改比例
            if (zbRequirement.getStatus().equals(Short.parseShort("3"))) {
                if (StringUtils.isNotBlank(trusteePercent) && !trusteePercent.equals("30")) {
                    zbRequirement.setId(zbRequirement.getId());
                    zbRequirement.setTrusteePercent(Integer.parseInt(trusteePercent));
                    zbRequireService.updateByIdSelective(zbRequirement);
                }
            }

            // 插入托管资金记录
            List<Condition> zbTrusFilter = new ArrayList<>();
            zbTrusFilter.add(Condition.eq("requirementId", requirementId));
            zbTrusFilter.add(Condition.eq("status", Short.parseShort("0")));
            zbTrusFilter.add(Condition.eq("userId", userId));
            ZbTrusteeRecord zbTrusteeRecord = zbTrusteeRecordService.selectOne(zbTrusFilter);
            if (zbTrusteeRecord != null) {
                if (zbRequirement.getStatus().equals(Short.parseShort("3"))) {
                    //第一次托管
                    zbTrusteeRecord.setActualMoney(zbRequirement.getRewardMoney() * zbRequirement.getTrusteePercent());
                    zbTrusteeRecord.setTrusteeNum(1);
                } else if (zbRequirement.getStatus().equals(Short.parseShort("7"))) {
                    //第二次托管
                    zbTrusteeRecord.setActualMoney(zbRequirement.getRewardMoney() * (100 - zbRequirement.getTrusteePercent()));
                    zbTrusteeRecord.setTrusteeNum(2);
                } else {
                    mv.setViewName("");
                    mv.addObject("msg", "该需求数据异常^_^");
                    return mv;
                }
                zbTrusteeRecordService.updateById(zbTrusteeRecord);

            } else {
                zbTrusteeRecord = new ZbTrusteeRecord();
                zbTrusteeRecord.setUserId(zbRequirement.getUserId());
                zbTrusteeRecord.setRequirementId(Long.parseLong(requirementId));
                zbTrusteeRecord.setRewardMoney(zbRequirement.getRewardMoney());
                zbTrusteeRecord.setTrusteePercent(zbRequirement.getTrusteePercent());
                if (zbRequirement.getStatus().equals(Short.parseShort("3"))) {
                    //第一次托管
                    zbTrusteeRecord.setActualMoney(zbRequirement.getRewardMoney() * zbRequirement.getTrusteePercent());
                    zbTrusteeRecord.setTrusteeNum(1);
                } else if (zbRequirement.getStatus().equals(Short.parseShort("7"))) {
                    //第二次托管
                    zbTrusteeRecord.setActualMoney(zbRequirement.getRewardMoney() * (100 - zbRequirement.getTrusteePercent()));
                    zbTrusteeRecord.setTrusteeNum(2);
                } else {
                    mv.setViewName("");
                    mv.addObject("msg", "该需求数据异常^_^");
                    return mv;
                }
                zbTrusteeRecord.setSerialNo(CommonUtils.getRequireSn("ZB", "ZFB"));
                zbTrusteeRecord.setStatus(ZbContants.Trustee_Record_Status.RECORD_INITIAL.getCode());
                zbTrusteeRecord.setAddTime(new Date());
                zbTrusteeRecordService.insert(zbTrusteeRecord);
            }

            if (zbRequirement.getStatus().equals(Short.parseShort("3"))) {
                //添加悬赏费用托管时间
                mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.TRUSTEETIME, DateUtil.getSimpleDate(new Date()));
            }

            List<Map> paymentList = initPaymentList(userId);

            mv.addObject("moneyBalance", payAccount.getUseBalance());
            mv.addObject("payments", paymentList);

            Map<String, Object> map = new HashMap<>();
            map.put("tradeNo", zbTrusteeRecord.getSerialNo());
            map.put("tradeAmount", zbTrusteeRecord.getActualMoney());
            map.put("tradeDate", new Date());
            map.put("tradeType", "即时到账交易");
            mv.addObject("orderInfo", map);
            mv.setViewName("");

        }catch (Exception e){
            logger.error("跳转支付页面异常", e);
        }

        return mv;
    }

    private List<Map> initPaymentList(String userId){
        List<Map> list = new ArrayList<>(2);
        User user = userService.selectById(userId);
        if(user != null){
            Object[][] payments = {{"账户余额",user.getMoneyBalance()}, {"支付宝",user.getMobile()}};
            for(int i=0;i<2;i++){
                Map pay = new HashMap();
                pay.put("payCode",i+1);
                pay.put("payName",payments[i][0]);
                pay.put("payDetail",payments[i][1]);
                list.add(pay);
            }
        }
        return list;
    }
}
