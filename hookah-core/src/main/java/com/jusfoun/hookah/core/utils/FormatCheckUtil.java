package com.jusfoun.hookah.core.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huang lei
 * @date 2017/3/23 下午1:41
 * @desc
 */
public class FormatCheckUtil {

    public static boolean checkEmail(String str) {
        String regExp = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean checkMobile(String mobile){
        String regExp = "^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}$";
        return checkPattern(mobile,regExp);
    }
    /**
     * 手机号码
     * 移动：134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通：130,131,132,145,152,155,156,1709,171,176,185,186
     * 电信：133,134,153,170,177,180,181,189
     * 大陆地区固话及小灵通
     *     区号：010,020,021,022,023,024,025,027,028,029
     *     号码：七位或八位
     *  type 为空  所有手机号码类型
     *         0   所有
     *         1   移动
     *         2   联通
     *         3   电信
     *         4   电信
     *         5   所有号码（手机+座机）
     */
    public static boolean checkMobileAndPhone(String input,Integer...type){
        /**
         * 手机号码
         * 移动：134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通：130,131,132,145,152,155,156,1709,171,176,185,186
         * 电信：133,134,153,170,177,180,181,189
         */
        String mobile = "^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}$";  //所有手机
        /**
         * 中国移动：China Mobile
         * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         */
        String cm = "^1(3[4-9]|4[7]|5[0-27-9]|7[0]|7[8]|8[2-478])\\d{8}$";
        /**
         * 中国联通：China Unicom
         * 130,131,132,145,152,155,156,1709,171,176,185,186
         */
        String cu = "^1(3[0-2]|4[5]|5[56]|709|7[1]|7[6]|8[56])\\d{8}$";
        /**
         * 中国电信：China Telecom
         * 133,134,153,1700,177,180,181,189
         */
        String ct = "^1(3[34]|53|77|700|8[019])\\d{8}$";
        /**
         * 大陆地区固话及小灵通
         *     区号：010,020,021,022,023,024,025,027,028,029
         *     号码：七位或八位 +分机号
         */
        String phone = "^(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-?[0-9]{1,4})?$";
        //手机+固话
        String all = "^(1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8})|((0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-?[0-9]{1,4})?)$";
        String[] patterns = {mobile,cm,cu,ct,phone,all};
        if(type==null || type.length==0){
            return checkPattern(input,mobile);
        }
        for(int i=0;i<type.length;){
            if(checkPattern(input,patterns[type[i]])){
                return true;
            }else{
                i++;
            }
        }
        return false;
    }

    public static boolean checkPattern(String input, String pattern) {
        return Pattern.matches(pattern, input);
    }
}
