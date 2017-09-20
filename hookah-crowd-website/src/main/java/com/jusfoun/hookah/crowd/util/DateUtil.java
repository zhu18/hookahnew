package com.jusfoun.hookah.crowd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	  // 获得当前日期与本周一相差的天数
    public static  int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    } 
	
	  // 获得当前周- 周一的日期
    public static  String getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String preMonday = df.format(monday);
        preMonday =preMonday.substring(0, 10)+" 00:00:00";
        return preMonday;
    }
    
    // 获得当前周- 周日  的日期
    public static String getPreviousSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus +6);
        Date monday = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String preMonday = df.format(monday);
        return preMonday;
    }
    // 获得当前日期
    public static String getPreviousDay() {
    	GregorianCalendar currentDate = new GregorianCalendar();
    	Date monday = currentDate.getTime();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	String preDay = df.format(monday);
    	return preDay;
    }
    /**
     * 对当前时间进行月份的加减操作
     * @param num 正数为加，负数为减
     * @return
     */
    public static Date monthPlusOrMinus(int num){
    	Calendar calendar=new GregorianCalendar();;
    	calendar.add(Calendar.MONTH, num);
    	return calendar.getTime();
    }
    /**
     * 对当前时间进行月份的加减操作
     * @param num 正数为加，负数为减
     * @return
     */
    public static String monthPlusOrMinusAsString(int num){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(monthPlusOrMinus(num));
    }
    /**
     * 对当前时间进行日期的加减操作
     * @param num 正数为加，负数为减
     * @return
     */
    public static Date datePlusOrMinus(int num){
        Calendar calendar=new GregorianCalendar();;
        calendar.add(Calendar.DATE, num);
        return calendar.getTime();
    }

    /**
     * 对当前时间进行日期的加减操作
     * @param num 正数为加，负数为减
     * @return
     */
    public static String datePlusOrMinusAsString(int num){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(datePlusOrMinus(num));
    }
    
    /**
     * 获取当前时间的 yyyy-MM-dd HH:mm:ss 格式
     * @param date
     * @return
     */
    public static String getSimpleDate(Date date){
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }


    public static void main(String[] args) throws ParseException{

        System.out.println(timeCountDown(new Date()));
    }

    /**
     * 根据传入的时间返回倒计时信息
     * @param deadline
     * @return
     * @throws ParseException
     */
    public static String timeCountDown(Date deadline) throws ParseException{
       /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        deadline=df.parse("2017-9-18 22:50");*/
        Long ms=deadline.getTime()-new Date().getTime();
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day >=0) {
            sb.append(day+"天");
        }
        if(hour >= 0) {
            sb.append(hour+"小时");
        }
        if(minute >= 0) {
            sb.append(minute+"分");
        }
        /*if(second >= 0) {
            sb.append(second+"秒");
        }
        if(milliSecond >= 0) {
            sb.append(milliSecond+"毫秒");
        }*/
        return sb.toString();
    }
}
