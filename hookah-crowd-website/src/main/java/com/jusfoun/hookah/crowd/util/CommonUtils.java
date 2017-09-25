package com.jusfoun.hookah.crowd.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    /**
     * 生成需求编号
     * @param prefix    前缀          如zb
     * @param type      需求类型       如 1
     * @return
     */
    public static String getRequireSn(String prefix, String type){

        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append("_");
        sb.append(type);
        sb.append("_");
        sb.append(DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()));
        sb.append(Thread.currentThread().getId());
        return sb.toString();
    }

    public static void main(String[] args){
//        String sn = getRequireSn("zb", "1");
//        System.out.println(sn);
//
//        System.out.println(DateTimeFormatter.ofPattern("uuuuMMdd").format(LocalDate.now()));
//        System.out.println(DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()));
//        System.out.println(DateTimeFormatter.BASIC_ISO_DATE.format(LocalDateTime.now()));


        System.out.println(Math.round(Double.valueOf(0.1)*100));

        Short[] zbStatus = new Short[]{};
        System.out.println(zbStatus.length);
    }
}
