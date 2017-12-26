package com.jusfoun.hookah.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Shengzhao Li
 */
public abstract class DateUtils {

    /**
     * Default time format :  yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Time format :  yyyy-MM-dd HH:mm
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT = "HH:mm";

    /**
     * Default date format
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * Default month format
     */
    public static final String MONTH_FORMAT = "yyyy-MM";
    /**
     * Default day format
     */
    public static final String DAY_FORMAT = "dd";


    //Date pattern,  demo:  2013-09-11
    public static final String DATE_PATTERN = "^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$";


    private DateUtils() {
    }

    public static boolean isDate(String dateAsText) {
        return StringUtils.isNotEmpty(dateAsText) && dateAsText.matches(DATE_PATTERN);
    }

    public static Date now() {
        return new Date();
    }

    public static String toDateText(Date date) {
        return toDateText(date, DATE_FORMAT);
    }

    public static String toDateText(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date getDate(String dateText) {
        return getDate(dateText, DATE_FORMAT);
    }


    public static Date getDate(String dateText, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateText);
        } catch (ParseException e) {
            throw new IllegalStateException("Parse date from [" + dateText + "," + pattern + "] failed", e);
        }
    }

    public static String toDateTime(Date date) {
        return toDateText(date, DATE_TIME_FORMAT);
    }

    public static String toDefaultNowTime() {
        return toDateText(new Date(), DEFAULT_DATE_TIME_FORMAT);
    }


    /**
     * Return current year.
     *
     * @return Current year
     */
    public static int currentYear() {
        return calendar().get(Calendar.YEAR);
    }

    public static Calendar calendar() {
        return Calendar.getInstance();
    }

    /**
     * 返回当前时间
     * @return yyyyMMddHHmmssSSS格式
     */
    public static String getCurrentTimeFormat(Date date){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date).toString();
    }

    /**
     * 返回传入时间的前一天
     * @return
     */
    public static String getYesterday(Date date, String pattern ) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(pattern).format(cal.getTime()).toString();
    }

    public static String transferDate(String fromDate){
        String toDate = null;
        if(com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(fromDate)){
            toDate = fromDate.substring(0, 11) + "23:59:59";
        }
        return toDate;
    }

    public static Date getTodayStart(){
        Date now = new Date();
        Date startDate = getDate((toDateText(now,DATE_FORMAT) + " 00:00:00"), DEFAULT_DATE_TIME_FORMAT);
        return startDate;
    }

    public static String transferTime(String fromDate){
        String toDate = null;
        if(com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(fromDate)){
            toDate = fromDate.substring(0, 10) + " 23:59:59";
        }
        return toDate;
    }

    public static Date thisTimeNextFewDays(Date time, Integer i){
        Long date = time.getTime();
        Long d1 = i*24*60*60*1000L;
        Date d2 = new Date(date + d1);
        return d2;
    }

    public static Date thisTimeNextMonth(Date time, Integer i){
        Calendar cld = Calendar.getInstance();
        cld.setTime(time);
        cld.add(Calendar.MONTH, i);
        Date d2 = cld.getTime();
        return d2;
    }

    public static Date thisTimeNextYear(Date time, Integer i){
        Calendar cld = Calendar.getInstance();
        cld.setTime(time);
        cld.add(Calendar.YEAR, i);
        Date d2 = cld.getTime();
        return d2;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isSoonExpire(Date date1, Date date2, Integer day){
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 < year2){
            int timeDistance = 0 ;
            for(int i = year1; i < year2; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return (timeDistance + day2 - day1 + 1) <= day;
        }else {
            int n = day2-day1+1;
            return n<=day;
        }
    }

    public static boolean isExpired(Date settledDate, Date now){
        if (settledDate == null || now == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(settledDate);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(now);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1>year2){
            return false;
        }else if (year1<year2){
            return true;
        }
        int n = cal2.get(Calendar.DAY_OF_YEAR)-cal1.get(Calendar.DAY_OF_YEAR);
        return n>0;
    }

    public static boolean isExpired(Date receivedDate, Integer validDays, Date now){
        if (receivedDate == null || now == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(receivedDate);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(now);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        if (year1 != year2){
            int timeDistance = 0 ;
            for(int i = year1; i < year2; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return (timeDistance + day2 - day1 - validDays + 1)>0;
        }
        int n = day2-day1-validDays+1;
        return n>0;
    }

}