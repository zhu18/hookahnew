package com.jusfoun.hookah.oauth2server.web.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.jusfoun.hookah.core.domain.mongo.MgCaptcha;
import com.jusfoun.hookah.core.utils.GeneratorUtil;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MgCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @author:jsshao
 * @date: 2017-3-31
 */
@Controller
public class CaptchaController {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Autowired
    DefaultKaptcha captchaProducer;

    @Autowired
    MgCaptchaService captchaService;

    @RequestMapping(value = "/captcha")
    public ReturnData getKaptchaImage(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        logger.info("capText: {}" , capText);
        String captchaId = GeneratorUtil.getUUID();
        try {

            MgCaptcha captcha = new MgCaptcha(captchaId,capText);
            captchaService.insert(captcha);
            response.setHeader("cuid",captchaId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    @RequestMapping(value = "/captcha_check")
    @ResponseBody
    public ReturnData checkCaptcha(String captchaId, String text, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        MgCaptcha captcha = captchaService.selectById(captchaId);
        if(captcha==null){
            return ReturnData.error("过期");
        }else{
            if(!captcha.getValidCode().equals(text)){
                return ReturnData.fail();
            }else{
                return ReturnData.success("输入正确");
            }
        }
    }
}
