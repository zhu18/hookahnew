package com.jusfoun.hookah.oauth2server.web.controller;

import com.google.code.kaptcha.Constants;
import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.WxUserRecommend;
import com.jusfoun.hookah.core.domain.vo.UserValidVo;
import com.jusfoun.hookah.core.exception.*;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.FormatCheckUtil;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.oauth2server.config.MyProps;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.UserService;
import com.jusfoun.hookah.rpc.api.WXUserRecommendService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/3/23 上午10:15
 * @desc 注册
 */
@Controller
public class RegController {
    private static final Logger logger = LoggerFactory.getLogger(RegController.class);

    @Autowired
    MyProps myProps;

    @Resource
    RedisOperate redisOperate;

    @Resource
    UserService userService;

    @Resource
    PayAccountService payAccountService;

    @Resource
    WXUserRecommendService WXUserRecommendService;

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String reg(Model model) {
        return "register";
    }

    /**
     * 微信推荐 注册
     * @return
     */
    @RequestMapping(value = "/reg/recommendReg", method = RequestMethod.GET)
    public String recommendReg(HttpServletRequest request, String recommendToken) {
        try {
            String unSecret = URLDecoder.decode(recommendToken,"UTF-8");
            String recommendUserId = unSecret.split("&")[0].split(":")[1];
            request.setAttribute("recommendUserId",recommendUserId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "recommendRegister";
    }

    @Log(platform = "front",logType = "f0001",optType = "insert")
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData pReg(UserValidVo user, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        boolean isExists = true;
        List<Condition> filters = new ArrayList();
        //1、校验图片验证码 ,=======>可以跳过这步，我觉得不校验问题也不大
        try {
            String captcha = user.getCaptcha();
            HttpSession session = request.getSession();
            String value = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

            if (!value.equalsIgnoreCase(captcha)) {
                throw new UserRegInvalidCaptchaException("图片验证码验证未通过,验证码错误");
            }
//            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            //2、校验短信验证码
            //获取库里缓存的验证码

            String cacheSms = redisOperate.get(HookahConstants.REDIS_SMS_CACHE_PREFIX + ":" + user.getMobile());  //从 redis 获取缓存
            if (cacheSms == null) { //验证码已过期
                throw new UserRegExpiredSmsException("短信验证码验证未通过,短信验证码已过期");
            } else {
                if (!cacheSms.equalsIgnoreCase(user.getValidSms())) {
                    throw new UserRegInvalidSmsException("短信验证码验证未通过,短信验证码错误");
                }
            }
            redisOperate.del(user.getMobile());  //删除缓存

            //3、校验密码一致
            String password = user.getPassword().trim();
            String passwordRepeat = user.getPasswordRepeat().trim();
            if (StringUtils.isBlank(password) || StringUtils.isBlank(passwordRepeat)) {
                throw new UserRegEmptyPwdException("密码或者确认密码不能为空");
            }
            if (!password.equals(passwordRepeat)) {
                throw new UserRegConfirmPwdException("密码与确认密码不一致");
            }
            if (password.length() < 6) {
                throw new UserRegSimplePwdException("密码过于简单");
            }

            //4，校验重复
            //4.1 用户名
            filters.clear();
            filters.add(Condition.eq("userName", user.getUserName()));
            isExists = userService.exists(filters);
            if (isExists) {
                throw new UserRegExistUsernameException("该邮箱已经被注册");
            }
            //4.2 手机
            filters.clear();
            filters.add(Condition.eq("mobile", user.getMobile()));
            isExists = userService.exists(filters);
            if (isExists) {
                throw new UserRegExistMobileException("该手机已经被注册");
            }
            //4.1 邮箱
//            filters.clear();
//            filters.add(Condition.eq("email", user.getEmail()));
//            isExists = userService.exists(filters);
//            if (isExists) {
//                throw new UserRegExistEmailException("该邮箱已经被注册");
//            }
            //其他校验规则
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }

        Map<String, String> host = myProps.getHost();
        Map<String, String> site = myProps.getSite();
        user.setAddTime(new Date());
        user.setRegTime(new Date());
        user.setIsEnable((byte) 1);
        user.setHeadImg(site.get("user-default-img"));

//        String date = DateUtils.toDateText(new Date(), "yyMM");
//        List<Condition> filter = new ArrayList<>();
//        filter.clear();
//        long count = userService.count(filter)+1;
//        String userSn = HookahConstants.platformCode + date + String.format("%06d",redisOperate.incr("userSn"));
//        String userSn = HookahConstants.platformCode + date + String.format("%06d",count);
        user.setUserSn(generateUserSn());

        User regUser = userService.insert((User) user);
        //redirectAttributes.addAttribute(regUser);
        payAccountService.insertPayAccountByUserIdAndName(regUser.getUserId(),regUser.getUserName());

        String recommendUserId = request.getParameter("recommendUserId");
        if (StringUtils.isNotBlank(recommendUserId)){
            User recommendUser = userService.selectById(recommendUserId);
            if (recommendUser != null){
                WxUserRecommend wxUserRecommend = new WxUserRecommend();
                wxUserRecommend.setRecommenderid(recommendUserId);
                wxUserRecommend.setInviteeid(regUser.getUserId());
                wxUserRecommend.setAddTime(new Date());
                wxUserRecommend.setUpdateTime(new Date());
//                wxUserRecommend.setRewardMoney();
                WXUserRecommendService.insert(wxUserRecommend);
            }
        }
        //TODO...登录日志
        logger.info("用户[" + user.getUserName() + "]注册成功(这里可以进行一些注册通过后的一些系统参数初始化操作)");
//            return "redirect:"+host.get("website")+"/login";
        return ReturnData.success("注册成功");
    }

    public String generateUserSn(){
        String date = DateUtils.toDateText(new Date(), "yyMM");
        String userSn = HookahConstants.platformCode + date + String.format("%06d",redisOperate.incr("userSn"));
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("userSn",userSn));
        User user = userService.selectOne(filter);
        if (user != null){
            generateUserSn();
        }
        return userSn;
    }


    /**
     * 校验邮箱
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/reg/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkEmail(String email) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("email", email));
        boolean isExists = userService.exists(filters);
        if (isExists) {
            return ReturnData.fail("该邮箱已经被注册");
        }
        return ReturnData.success();
    }

    /**
     * 校验邮箱
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/reg/checkMobile", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkMobile(String mobile) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("mobile", mobile));
        boolean isExists = userService.exists(filters);
        if (isExists) {
            return ReturnData.fail("该手机已经被注册");
        }
        return ReturnData.success();
    }

    /**
     * 校验邮箱
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/reg/checkUsername", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkUserName(String username) {
        List<Condition> filters = new ArrayList(1);
        filters.add(Condition.eq("userName", username));
        boolean isExists = userService.exists(filters);
        if (isExists) {
            return ReturnData.fail("该用户名已经被注册");
        }
        return ReturnData.success();
    }

    /**
     * 修改密码
     * @param newPwd  新密碼
     * * @param newPwdRepeat 新密码重复
     * @param model
     * @return
     */
    @RequestMapping(value = "/reg/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData updatePwd(String  newPwd, String  newPwdRepeat,Integer safetyLandScore,  Model model) {
        try {

            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
            String userId = userMap.get("userId");
            if (StringUtils.isBlank(userId)) {
                throw new UserRegEmptyPwdException("请重新登录");
            }
            User user =  userService.selectById(userId);

            if (StringUtils.isBlank(newPwd) || StringUtils.isBlank(newPwdRepeat)) {
                throw new UserRegEmptyPwdException("密码或者确认密码不能为空");
            }
            if (!newPwd.equals(newPwdRepeat)) {
                throw new UserRegConfirmPwdException("密码与确认密码不一致");
            }
            if (newPwd.length() < 6) {
                throw new UserRegSimplePwdException("密码过于简单");
            }
            //更新安全分值
            user.setSafetyLandScore(safetyLandScore);
            user.setPassword(new Md5Hash(newPwd).toString());
            userService.updateById(user);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error("密码修改错误");
        }
        return ReturnData.success();
    }

    @RequestMapping(value = "/findPwd", method = RequestMethod.GET)
    public String findPwd(Integer step,String userId,Model model) {
        if(step==null) step =1;
        if(StringUtils.isNotBlank(userId)){
            User user = userService.selectById(userId);
            model.addAttribute("userId",userId);
            model.addAttribute("mobile",FormatCheckUtil.hideMobile(user.getMobile()));
        }
        switch (step){
            case 1:return "findpassword";
            case 2:return "checkMobile";
            case 3:return "resetPwd";
            case 4:return "complete";
            default:return "findpassword";
        }
    }

    //@Log(platform = "front",logType = "f0003",optType = "modify")
    @RequestMapping(value = "/findPwd", method = RequestMethod.POST)
    @ResponseBody
    public Object findPwd(Integer step, UserValidVo userVo, HttpServletRequest request) {
        if (step == null) step = 1;
        List<Condition> filters = new ArrayList<>(2);
        switch (step) {
            case 1: //post
                try {
                    //校验验证码
                    String captcha = userVo.getCaptcha();
                    HttpSession session = request.getSession();
                    String value = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

                    if (!value.equalsIgnoreCase(captcha)) {
                        throw new UserRegInvalidCaptchaException("图片验证码验证未通过,验证码错误");
                    }
                    //session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
                    //查询用户
                    String username = userVo.getUserName();
                    if (FormatCheckUtil.checkMobile(username)) {
                        filters.add(Condition.eq("mobile", username));
                    } else if (FormatCheckUtil.checkEmail(username)) {
                        filters.add(Condition.eq("email", username));
                    } else {
                        filters.add(Condition.eq("userName", username));
                    }
                    User user = userService.selectOne(filters);
                    if (user != null) {
                        return ReturnData.success(user.getUserId());
                    } else {
                        return ReturnData.error("该用户不存在！");
                    }
                } catch (Exception e) {
                    return ReturnData.error(e.getMessage());
                }
            case 2:  //post
                //校验短信验证码
                //获取库里缓存的验证码
                try {
                    User user = userService.selectById(userVo.getUserId());
                    String cacheSms = redisOperate.get(HookahConstants.REDIS_SMS_CACHE_PREFIX + ":" + user.getMobile());  //从 redis 获取缓存
                    if (cacheSms == null) { //验证码已过期
                        throw new UserRegExpiredSmsException("短信验证码验证未通过,短信验证码已过期");
                    } else {
                        if (!cacheSms.equalsIgnoreCase(userVo.getValidSms())) {
                            throw new UserRegInvalidSmsException("短信验证码验证未通过,短信验证码错误");
                        }
                    }
                    redisOperate.del(user.getMobile());  //删除缓存
                    return ReturnData.success(userVo.getUserId());
                } catch (Exception e) {
                    return ReturnData.error(e.getMessage());
                }
            case 3: //post
                try {
                    //校验密码一致
                    String password = userVo.getPassword().trim();
                    String passwordRepeat = userVo.getPasswordRepeat().trim();
                    if (StringUtils.isBlank(password) || StringUtils.isBlank(passwordRepeat)) {
                        throw new UserRegEmptyPwdException("密码或者确认密码不能为空");
                    }
                    if (!password.equals(passwordRepeat)) {
                        throw new UserRegConfirmPwdException("密码与确认密码不一致");
                    }
                    if (password.length() < 6) {
                        throw new UserRegSimplePwdException("密码过于简单");
                    }
                    User user = new User();
                    BeanUtils.copyProperties(user, userVo);
                    user.setPassword(new Md5Hash(password).toString());
                    userService.updateByIdSelective(user);
                    return ReturnData.success();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    return ReturnData.fail(e.getMessage());
                }
            default:
                return ReturnData.error("wrong step");
        }
    }

}
