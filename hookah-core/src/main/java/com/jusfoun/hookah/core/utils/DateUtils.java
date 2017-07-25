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

    public static String transferDate(String fromDate){
        String toDate = null;
        if(com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(fromDate)){
            toDate = fromDate.substring(0, 11) + "23:59:59";
        }
        return toDate;
    }

        public static String thisTimeNextMonth(String time, Integer i){
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DEFAULT_DATE_TIME_FORMAT);
        Calendar cld = Calendar.getInstance();
        try {
            Date d1 = df.parse(time);
            cld.setTime(d1);
            cld.add(Calendar.MONTH, i);
            Date d2 = cld.getTime();
            return df.format(d2);
        }catch (ParseException e){
            throw new IllegalStateException("Parse date from [" + time + "," + DateUtils.DEFAULT_DATE_TIME_FORMAT + "] failed", e);
        }
    }

    public static String thisTimeNextYear(String time, Integer i){
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DEFAULT_DATE_TIME_FORMAT);
        Calendar cld = Calendar.getInstance();
        try {
            Date d1 = df.parse(time);
            cld.setTime(d1);
            cld.add(Calendar.YEAR, i);
            Date d2 = cld.getTime();
            return df.format(d2);
        }catch (ParseException e){
            throw new IllegalStateException("Parse date from [" + time + "," + DateUtils.DEFAULT_DATE_TIME_FORMAT + "] failed", e);
        }
    }

}