package com.jusfoun.hookah.oauth2server.config;

import com.jusfoun.hookah.core.domain.vo.UserValidVo;
import com.jusfoun.hookah.core.utils.RSAUtils;
import com.jusfoun.hookah.core.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by ctp on 2018/5/17.
 *  登录加密传输工具类
 */
public class RsaLoginUtils {

    private static final Logger logger = LoggerFactory.getLogger(RsaLoginUtils.class);

    //前端公钥属性值
    public static final String LOGIN_PUBLIC_KEY = "loginPublicKey";

    //私钥session key
    public static final String LOGIN_PRIVATE_KEY_ATTRIBUTE_NAME = "login_private_key_attribute_name";

    /**
     * 生成密钥对 返回公钥
     *
     * @return
     */
    public static String getLoginPublicKey() {
        // 生成密钥对
        Map<String, Object> keypairs = RSAUtils.genKeyPair();
        // 获取公钥
        String publicKey = new String(RSAUtils.getPublicKey(keypairs));
        logger.info("----------------生成的rsa公钥---------------------:" + publicKey);
        //获取私钥
        String privateKey = new String(RSAUtils.getPrivateKey(keypairs));
        logger.info("----------------生成的rsa私钥---------------------:" + privateKey);
        //私钥存seesion
        SecurityUtils.getSubject().getSession().setAttribute(LOGIN_PRIVATE_KEY_ATTRIBUTE_NAME, privateKey);

        //返回公钥
        return publicKey;
    }


    //解密数据(userNmae password)
    public static void decryptByRSALoginInfo(UserValidVo userValidVo) {

        String username = userValidVo.getUserName();
        String password = userValidVo.getPassword();
        //获取私钥
        String privateKey = (String) SecurityUtils.getSubject().getSession().getAttribute(LOGIN_PRIVATE_KEY_ATTRIBUTE_NAME);

        try {
            if (!StringUtils.stringsIsEmpty(username, password)) {
                //解密
                byte[] userNameData = RSAUtils.decryptByPrivateKey(username.getBytes(), privateKey.getBytes());
                byte[] passwordData = RSAUtils.decryptByPrivateKey(password.getBytes(), privateKey.getBytes());

                logger.info("----------------解密后的数据userName---------------------:" + new String(userNameData));
                logger.info("----------------解密后的数据password---------------------:" + new String(passwordData));

                //还原数据
                userValidVo.setUserName(new String(userNameData));
                userValidVo.setPassword(new String(passwordData));
            }
        } catch (Exception e) {
            logger.error("----------------解密出错---------------------:" + e.getMessage());
        }
    }

    //解密数据
    public static String decryptByRSALoginInfo(String encryptedata) {

        //获取私钥
        String privateKey = (String) SecurityUtils.getSubject().getSession().getAttribute(LOGIN_PRIVATE_KEY_ATTRIBUTE_NAME);

        try {
            if (!StringUtils.stringsIsEmpty(encryptedata)) {
                //解密
                byte[] userNameData = RSAUtils.decryptByPrivateKey(encryptedata.getBytes(), privateKey.getBytes());
                logger.info("----------------解密后的数据---------------------:" + new String(userNameData));

                //还原数据
                return new String(userNameData);
            }
        } catch (Exception e) {
            logger.error("----------------解密出错---------------------:" + e.getMessage());
        }
        return null;
    }


}
