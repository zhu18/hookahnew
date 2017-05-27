package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.UserRegConfirmPwdException;
import com.jusfoun.hookah.core.exception.UserRegEmptyPwdException;
import com.jusfoun.hookah.core.exception.UserRegSimplePwdException;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;


/**
 * @author huang lei
 * @date 2017/4/12 上午9:39
 * @desc
 */
@Controller
@RequestMapping("/modify")
public class ModifyController {

    private static final Logger logger = LoggerFactory.getLogger(ModifyController.class);

    @Resource
    UserService userService;

    @RequestMapping(value = "/loginPassword", method = RequestMethod.GET)
    public String loginPassword(Model model) {
        model.addAttribute("title", "修改登录密码");
        return "modify/loginPassword";
    }

    /**
     *   修改支付密码
     * @return
     */
    @RequestMapping(value = "/payPassword", method = RequestMethod.GET)
    public String payPassword(Model model) {
        model.addAttribute("title", "修改支付密码");
        return "modify/payPassword";
    }
    @RequestMapping(value = "/payPassword", method = RequestMethod.POST)
    public String pPayPassword(User userForm,Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
        User user = userService.selectById(userMap.get("userId"));
        if(StringUtils.isNotBlank(userForm.getPaymentPassword())){
            String othpassword = new Md5Hash(userForm.getPaymentPassword()).toString();
            user.setPaymentPassword(othpassword);
            userService.updateById(user);
            return "redirect:/modify/success?type=payPassword";
        }else{
            model.addAttribute("error","支付密码为空");
            return "modify/payPassword";
        }
    }


    @RequestMapping(value = "/updateLoginPwd", method = RequestMethod.GET)
    public String updateLoginPwd(Model model) {
        model.addAttribute("title", "设置登录密码");
        return "modify/updateLoginPwd";
    }

    /**
     * 修改密码
     * @param newPwd  新密碼
     * * @param newPwdRepeat 新密码重复
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateLoginPwd", method = RequestMethod.POST)
    @ResponseBody
    public String updateLoginPwd(String  newPwd, String  newPwdRepeat, Model model) {
        try {

            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
            String userId = userMap.get("userId");
            if (!StringUtils.isNotBlank(userId)) {
                model.addAttribute("title", "请重新登录");
                return "modify/updateLoginPwd";
            }
            User user =  userService.selectById(userId);

            if (!StringUtils.isNotBlank(newPwd) || !StringUtils.isNotBlank(newPwdRepeat)) {
                model.addAttribute("title", "新密码或者确认密码不能为空");
                return "modify/updateLoginPwd";
            }
            if (!newPwd.equals(newPwdRepeat)) {
                model.addAttribute("title", "密码与确认密码不一致");
                return "modify/updateLoginPwd";
            }
            if (newPwd.length() < 6) {
                model.addAttribute("title", "密码过于简单");
                return "modify/updateLoginPwd";
            }

            user.setPassword(new Md5Hash(newPwd).toString());
            userService.updateById(user);
            return "redirect:/modify/success?type=logingPassword";
        } catch (Exception e) {
            logger.info(e.getMessage());
            model.addAttribute("title", "密码修改错误");
            return "modify/updateLoginPwd";
        }

    }



  /*  @RequestMapping(value = "/modify/setPayPassword", method = RequestMethod.GET)
    public String setPayPassword(Model model) {
        model.addAttribute("title", "设置支付密码");
        return "modify/setPayPassword";
    }*/

    /**
     *   设置支付密码

     * @return
     */
    @RequestMapping(value = "/setPayPassword", method = RequestMethod.GET)
    public String setPayPassword(Model model) {
        model.addAttribute("title", "设置支付密码");
        return "modify/setPayPassword";
    }
    @RequestMapping(value = "/setPayPassword", method = RequestMethod.POST)
    public String pSetPayPassword(User userForm,Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
        User user = userService.selectById(userMap.get("userId"));
            if(StringUtils.isNotBlank(userForm.getPaymentPassword())){
                String othpassword = new Md5Hash(userForm.getPaymentPassword()).toString();
                user.setPaymentPassword(othpassword);
                user.setPaymentPasswordStatus(1);
                userService.updateById(user);
                return "redirect:/modify/success?type=setPayPassword";
            }else{
                model.addAttribute("error","支付密码为空");
                return "modify/setPayPassword";
            }
    }
/*    @ResponseBody
    @RequestMapping(value = "/setPayPassword", method = RequestMethod.POST)
    public ReturnData setPayPassword(String userId,String payPassword, HttpServletRequest request) {
        ReturnData returnData = new ReturnData();
        User user = new User();
        if(StringUtils.isNotBlank(userId)){
            user.setUserId(userId);
            if(StringUtils.isNotBlank(payPassword)){
                String othpassword = new Md5Hash(payPassword).toString();
                user.setPaymentPassword(othpassword);
                userService.updateByIdSelective(user);
                returnData.setMessage("设置成功！");

            }
        }else {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("用户ID不能为空");
        }
        return  returnData;
    }*/




    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success(String type,Model model) {
        model.addAttribute("title", "成功");
        if(type.equals("mobile")){
            model.addAttribute("message", "手机号修改成功，请牢记");
        }else if(type.equals("mail")){
            model.addAttribute("message", "邮箱修改成功，请牢记");
        }else if(type.equals("loginPassword")){
            model.addAttribute("message", "登录密码修改成功，请牢记");
        }else if(type.equals("payPassword")){
            model.addAttribute("message", "支付密码修改成功，请牢记");
        }else if(type.equals("setPayPassword")){
            model.addAttribute("message", "支付密码设置成功，请牢记");
        }else if(type.equals("updateLoginPwd")){
            model.addAttribute("message", "登录密码设置成功，请牢记");
        }
        return "modify/success";
    }

    @RequestMapping(value = "/mobile", method = RequestMethod.GET)
    public String mobile(Model model) {
        model.addAttribute("title", "修改手机号");
        return "modify/mobile";
    }

    @RequestMapping(value = "/mobile", method = RequestMethod.POST)
    public String pMobile(User userForm,Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
        User user = userService.selectById(userMap.get("userId"));
        if (StringUtils.isNotBlank(userForm.getMobile())) {
            user.setMobile(userForm.getMobile());
            try {
                userService.updateById(user);
                return "redirect:/modify/success?type=mobile";
            } catch (Exception e) {
                model.addAttribute("error","修改错误");
                return "modify/mobile";
            }
        }
        model.addAttribute("error","修改错误");
        return "modify/mobile";
    }

    @RequestMapping(value = "/mail", method = RequestMethod.GET)
    public String mail(Model model) {
        model.addAttribute("title", "修改邮箱");
        return "modify/mail";
    }
}

