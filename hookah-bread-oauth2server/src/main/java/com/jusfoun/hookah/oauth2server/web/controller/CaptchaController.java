package com.jusfoun.hookah.oauth2server.web.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.jusfoun.hookah.core.utils.ReturnData;
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
import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "/captcha")
    public ReturnData getKaptchaImage(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        logger.info("capText: {}" , capText);
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  //存在session里

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
    public ReturnData checkCaptcha(String text, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        String value = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        if(!value.equalsIgnoreCase(text)){
            return ReturnData.success(0);
        }else{
            return ReturnData.success(1);
        }
    }
}
