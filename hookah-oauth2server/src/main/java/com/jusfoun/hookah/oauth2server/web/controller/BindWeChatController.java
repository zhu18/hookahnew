package com.jusfoun.hookah.oauth2server.web.controller;

import com.google.code.kaptcha.Constants;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.config.WeChatConfig;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.constants.TongJiEnum;
import com.jusfoun.hookah.core.domain.MessageCode;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.WeChatAuthInfo;
import com.jusfoun.hookah.core.domain.WxUserInfo;
import com.jusfoun.hookah.core.domain.mongo.MgTongJi;
import com.jusfoun.hookah.core.domain.vo.UserValidVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.exception.UserRegExpiredSmsException;
import com.jusfoun.hookah.core.exception.UserRegInvalidCaptchaException;
import com.jusfoun.hookah.core.exception.UserRegInvalidSmsException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.*;
import com.jusfoun.hookah.oauth2server.config.MyProps;
import com.jusfoun.hookah.oauth2server.config.ReadCookieUtil;
import com.jusfoun.hookah.oauth2server.config.WXConfigUtils;
import com.jusfoun.hookah.oauth2server.security.UsernameAndPasswordToken;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by lt on 2017/9/25.
 */
@Controller
public class BindWeChatController {
    private static final Logger logger = LoggerFactory.getLogger(BindWeChatController.class);

    @Autowired
    MyProps myProps;

    @Resource
    RedisOperate redisOperate;

    @Resource
    UserService userService;

    @Resource
    PayAccountService payAccountService;

    @Resource
    LoginLogService loginLogService;

    @Resource
    MqSenderService mqSenderService;

    @Resource
    WxUserInfoService wxUserInfoService;

    @Resource
    JfRecordService jfRecordService;

    @Resource
    MgTongJiService mgTongJiService;

    private  static final  String DEFAULT_PASSWORD = "000000";

    @RequestMapping(value = "/reg/bindWeChat", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData bindWeChat(UserValidVo user, RedirectAttributes redirectAttributes, HttpServletRequest request){
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList();
            //1、校验图片验证码
            String captcha = user.getCaptcha();
            HttpSession session = request.getSession();
            String value = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

            if (!value.equalsIgnoreCase(captcha)) {
                throw new UserRegInvalidCaptchaException("图片验证码验证未通过,验证码错误");
            }
            //2、校验手机验证码
            String cacheSms = redisOperate.get(HookahConstants.REDIS_SMS_CACHE_PREFIX + ":" + user.getMobile());  //从 redis 获取缓存
            if (cacheSms == null) { //验证码已过期
                throw new UserRegExpiredSmsException("短信验证码验证未通过,短信验证码已过期");
            } else {
                if (!cacheSms.equalsIgnoreCase(user.getValidSms())) {
                    throw new UserRegInvalidSmsException("短信验证码验证未通过,短信验证码错误");
                }
            }

            String openid = user.getOpenid();
            if(Objects.isNull(openid)){
                throw new HookahException("参数异常");
            }

            filters.add(Condition.eq("openid" , openid));
            WxUserInfo wxInfo =  wxUserInfoService.selectOne(filters);
            if(Objects.isNull(wxInfo)){
                throw new HookahException("绑定微信数据异常");
            }

            redisOperate.del(user.getMobile());  //删除缓存

            //校验通过，如果该手机号已经注册过则绑定微信信息，若未注册则进行注册
            filters.clear();
            filters.add(Condition.eq("mobile", user.getMobile()));
            User users = userService.selectOne(filters);

            if (Objects.nonNull(users)) {
                //判断该用户是否已绑定其他微信号
                String userId = users.getUserId();
                filters.clear();
                filters.add(Condition.eq("userid" , userId));
                WxUserInfo wx = wxUserInfoService.selectOne(filters);
                if(Objects.nonNull(wx) && !openid.equals(wx.getOpenid())){
                    throw new HookahException("该手机号已绑定其他微信账号");
                }
                //绑定微信信息
                WxUserInfo wxUserInfo = new WxUserInfo();
                wxUserInfo.setUserid(userId);
                wxUserInfo.setStatus(HookahConstants.WxUserStatus.BIND.getCode());//已绑定
                filters.clear();
                filters.add(Condition.eq("openid" , user.getOpenid()));
                wxUserInfoService.updateByConditionSelective(wxUserInfo,filters);

            }else {
                //注册并绑定微信信息
                users = new User();
                Map<String, String> site = myProps.getSite();
                users.setAddTime(new Date());
                users.setRegTime(new Date());
                users.setIsEnable((byte) 1);
//                users.setHeadImg(site.get("user-default-img"));
                users.setHeadImg(wxInfo.getHeadimgurl() !=null && !"".equals(wxInfo.getHeadimgurl()) ? wxInfo.getHeadimgurl() : site.get("user-default-img"));
                users.setPassword(openid.length() > 6 ? openid.substring(openid.length() - 6) : openid);
                users.setMobile(user.getMobile());
                //用户编码
                users.setUserSn(generateUserSn());
                //默认用户名
                users.setUserName(WXConfigUtils.generateUserName(user.getOpenid()));
                users.setNickName(HookahConstants.BDGStore + (StringUtils.isNotBlank(users.getUserSn()) ? users.getUserSn().substring(2) : System.currentTimeMillis()));
                User regUser = userService.insert(users);

                //发送默认密码到用户手机
                MessageCode messageCode = new MessageCode();
                messageCode.setCode(Integer.valueOf(HookahConstants.SmsTypeNew.SMS_USER_REGISTER_DEFAULT_PWD.toString()));
                messageCode.setMobileNo(user.getMobile());
                messageCode.setPassword(openid.length() > 6 ? openid.substring(openid.length() - 6) : openid);
                if(StringUtils.isNotBlank(regUser.getUserId())) {
                    messageCode.setUserId(regUser.getUserId());
                }
                //添加短信记录
                mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);

                //绑定微信信息
                WxUserInfo wxUserInfo = new WxUserInfo();
                wxUserInfo.setUserid(regUser.getUserId());
                wxUserInfo.setStatus(HookahConstants.WxUserStatus.BIND.getCode());//Byte.valueOf("1") 已绑定
                filters.clear();
                filters.add(Condition.eq("openid" , user.getOpenid()));
                wxUserInfoService.updateByConditionSelective(wxUserInfo,filters);

                //初始化账户信息
                payAccountService.insertPayAccountByUserIdAndName(regUser.getUserId(),regUser.getUserName());

                //统计获取注册地址
                String userId = regUser.getUserId();
                Map<String, Cookie> cookieMap = ReadCookieUtil.ReadCookieMap(request);
                Cookie tongJi = cookieMap.get("TongJi");
                if(tongJi != null){
                    MgTongJi tongJiInfo = mgTongJiService.getTongJiInfo(tongJi.getValue());
                    mgTongJiService.setTongJiInfo(TongJiEnum.REG_URL, tongJiInfo.getTongJiId(),
                            tongJiInfo.getUtmSource(), tongJiInfo.getUtmTerm(), userId);
                }

                //完成注册 发消息到MQ送优惠券
                mqSenderService.sendDirect(RabbitmqQueue.CONTRACT_REG_COUPON,regUser.getUserId());

                //注册赠送积分
                jfRecordService.registerHandle(regUser.getUserId(), null);


                logger.info("用户[" + user.getUserName() + "]注册成功(这里可以进行一些注册通过后的一些系统参数初始化操作)");
            }
            //绑定完成 登录
            if(Objects.nonNull(users)){
                UsernameAndPasswordToken token = new UsernameAndPasswordToken();
                token.setTokenType(UsernameAndPasswordToken.TokenType.CLIENT);
                token.setUsername(user.getOpenid());
                token.setPassword(user.getOpenid().toCharArray());
                Subject currentUser = SecurityUtils.getSubject();
                currentUser.login(token);
                if (currentUser.isAuthenticated()) {
                    //TODO...登录日志
                    loginLogService.addLoginLog(users.getUserName(), NetUtils.getIpAddr(request));
                    logger.info("用户[" + users.getUserName() + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
                }
            }

        } catch (UserRegInvalidCaptchaException e) {
//            request.setAttribute("error", "图片验证码验证未通过,验证码错误");
            returnData.setMessage(e.getMessage());
            logger.error(e.getMessage());
            returnData.setCode(ExceptionConst.AssertFailed);
        } catch (UserRegExpiredSmsException e){
//            request.setAttribute("error", "短信验证码验证未通过或短信验证码已过期");
            returnData.setMessage(e.getMessage());
            logger.error(e.getMessage());
            returnData.setCode(ExceptionConst.SMS_ERROR_MSG);
        } catch (UserRegInvalidSmsException e){
//            request.setAttribute("error", "短信验证码验证未通过,短信验证码错误");
            returnData.setMessage(e.getMessage());
            logger.error(e.getMessage());
            returnData.setCode(ExceptionConst.SMS_ERROR_MSG);
        } catch (HookahException e) {
            returnData.setMessage(e.getMessage());
            logger.error(e.getMessage());
            returnData.setCode(ExceptionConst.SMS_ERROR_MSG);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            logger.error(e.getMessage());
            returnData.setMessage("绑定失败");
            e.printStackTrace();
        }
        return returnData;
    }


    public String generateUserSn(){
        String date = DateUtils.toDateText(new Date(), "yyMM");
        String userSn = HookahConstants.platformCode + date + String.format("%06d",Integer.parseInt(redisOperate.incr("userSn")));
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("userSn",userSn));
        User user = userService.selectOne(filter);
        if (user != null){
            generateUserSn();
        }
        return userSn;
    }

    /**
     * 微信未绑定跳转到绑定页面
     * @param openid
     * @param state
     * @return
     */
    @RequestMapping("/sns/bindWeChat")
    public String bindPhone(String openid, String state){

        return "bindPhone";
    }


    /**
     * 微信二维码
     * @return
     */
    @RequestMapping("/sns/weChat")
    public String authorize(){
        String authorizationUrl = WeChatConfig.getAuthorizationUrl;
        String redirectUrl = myProps.getHost().get("auth") + "/sns/loginByWeChat";
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("微信回调地址编码出错！");
            return "redirect:/sns/weChat";
        }
        String state = GeneratorUtil.getUUID();
        //微信回调状态存入redis
        redisOperate.set("WeChatOauthState:"+state,"1",600);
        authorizationUrl = authorizationUrl.replace("APPID",WeChatConfig.appID);
        authorizationUrl = authorizationUrl.replace("REDIRECT_URI",redirectUrl);
        authorizationUrl = authorizationUrl.replace("SCOPE","snsapi_login");
        authorizationUrl = authorizationUrl.replace("STATE",state);
        return "redirect:"+ authorizationUrl;
    }

    @RequestMapping("/sns/loginByWeChat")
    public String loginByWeChat(String state, String code , HttpServletRequest request){
        //1.微信超时页面 2.无效code页面
        if (!StringUtils.isNotBlank(redisOperate.get("WeChatOauthState:"+state))){
            //回调状态错误或者超时 重新扫描二维码
            return "redirect:/sns/weChat";
        }
        if (!StringUtils.isNotBlank(code)){
            //未获取用户授权
            return "redirect:/sns/weChat";
        }
        //通过code获取access_token  有效期默认为两个小时
        WeChatAuthInfo weChatAuthInfo = WXConfigUtils.getAccessToken(code);
        String openid = weChatAuthInfo.getOpenid();
        String accessToken = weChatAuthInfo.getAccess_token();
        if (weChatAuthInfo == null || weChatAuthInfo.getErrcode()!=null || !StringUtils.isNotBlank(openid)){
            //获取access_token出错  重新扫码
            logger.error("获取微信用户access_token错误！");
            return "redirect:/sns/weChat";
        }

        //检查access_token是否有效
        String refresh_token = weChatAuthInfo.getRefresh_token();
        Boolean checkAccessToken = WXConfigUtils.checkAccessToken(openid,accessToken);
        if(!checkAccessToken){
            //无效刷新access_token
            weChatAuthInfo = WXConfigUtils.refreshAccessToken(refresh_token);
            openid = weChatAuthInfo.getOpenid();
            accessToken = weChatAuthInfo.getAccess_token();
            if (weChatAuthInfo == null || weChatAuthInfo.getErrcode()!=null || !StringUtils.isNotBlank(openid)){
                //获取access_token出错  重新扫码
                logger.error("刷新微信用户access_token错误！");
                return "redirect:/sns/weChat";
            }
        }

        //获取微信用户信息
        WxUserInfo wx = WXConfigUtils.getWxUserInfo(openid,accessToken);
        if(Objects.isNull(wx)){
            //获取access_token出错  重新扫码
            logger.error("获取微信用户信息错误！");
            return "redirect:/sns/weChat";
        }

        //扫描用户信息是否存在
        String wxOpenid = wx.getOpenid();
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("openid",wxOpenid));
        //获取用户openid之后 检查是否已经绑定平台账号
        WxUserInfo wxUserInfo = wxUserInfoService.selectOne(filter);
//        System.out.println("查看wxUserInfoservice的实现类:" + wxUserInfoService);
        //该用户没有绑定过且用户没有扫描过
        if (wxUserInfo == null){
            //保存微信用户信息
            wx.setAddTime(new Date());
            wx.setStatus(HookahConstants.WxUserStatus.NO_BIND.getCode());//默认值0 未绑定
            //用户特权信息
            String[] privilegeArray = wx.getPrivilege();
            if(Objects.nonNull(privilegeArray) && privilegeArray.length > 0){
                StringBuffer privileges = new StringBuffer();
                for (String pr : privilegeArray) {
                    privileges.append(pr).append(",");
                }
                wx.setPrivileges(privileges.toString());
            }
            wxUserInfoService.insert(wx);
            //未绑定账号 重定向到绑定手机页面
            return "redirect:/sns/bindWeChat?openid="+openid+"&state="+state;
        }

        //该用户没有绑定过且用户扫描过
        String userid = wxUserInfo.getUserid();
        if(!StringUtils.isNotBlank(userid)){
            //未绑定账号 重定向到绑定手机页面
            return "redirect:"+myProps.getHost().get("auth")+"/sns/bindWeChat?openid="+openid+"&state="+state;
        }

        User user = userService.selectById(wxUserInfo.getUserid());
        redisOperate.del("WeChatOauthState:"+state);
        if(Objects.nonNull(user)){
            UsernameAndPasswordToken token = new UsernameAndPasswordToken();
            token.setTokenType(UsernameAndPasswordToken.TokenType.CLIENT);
            token.setUsername(wxOpenid);
            token.setPassword(wxOpenid.toCharArray());
            Subject currentUser = SecurityUtils.getSubject();
            try {
                currentUser.login(token);
                if (currentUser.isAuthenticated()) {
                    //TODO...登录日志
                    loginLogService.addLoginLog(user.getUserName(), NetUtils.getIpAddr(request));
                    logger.info("用户[" + user.getUserName() + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        return "forward:"+myProps.getHost().get("auth")+"/login?userName="+user.getUserName()+"&password="+user.getPassword();
        return "redirect:" + myProps.getHost().get("website");
    }

}
