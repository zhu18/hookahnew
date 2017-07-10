package com.jusfoun.hookah.pay.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dengxu on 2017/7/4
 */
public class DateUtil {
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 返回当前时间
	 * @return yyyyMMddHHmmssSSS格式
	 */
	public static String getCurrentTimeFormat(Date date){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date).toString();
	}
	
	/**
	 * String类型时间转换为Date类型
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByString(String date) throws ParseException{
		return new SimpleDateFormat(DATE_FORMAT).parse(date);
	}
	
	/**
	 * 返回指定格式的当前日期或时间,默认：yyyy-MM-dd HH:mm:ss
	 * @param fmt
	 * @return
	 */
	public static String getCurrentTime(){
		return getCurrentTime(TIME_FORMAT);
	}
	
	/**
	 * 返回指定格式的当前日期或时间
	 * @param fmt
	 * @return
	 */
	public static String getCurrentTime(String fmt){
		return new SimpleDateFormat(fmt).format(new Date());
	}
	
	/**
	 * 返回指定格式的当前日期或时间 默认日期格式yyyy-MM-dd
	 * @param fmt
	 * @return
	 */
	public static String getCurrentDate(){
		return getCurrentDate(DATE_FORMAT);
	}
	
	/**
	 * 返回指定格式的当前日期或时间
	 * @param fmt
	 * @return
	 */
	public static String getCurrentDate(String fmt){
		return new SimpleDateFormat(fmt).format(new Date());
	}
	
	/**
	 * 日期格式化操作:默认日期格式yyyy-MM-dd
	 * @param fmt
	 * @return
	 */
	public static String dateFormat(Date date){
		return dateFormat(date, DATE_FORMAT);
	}
	
	/**
	 * 日期格式化操作
	 * @param fmt
	 * @return
	 */
	public static String dateFormat(Date date,String fmt){
		return new SimpleDateFormat(fmt).format(date);
	}
	
	/**
	 * 将日期字符串转按指定格式解析
	 * @param date 日期字符串
	 * @param fmt 日期格式
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException{
		return parseDate(date, DATE_FORMAT);
	}

	/**
	 * 将日期字符串转按指定格式解析
	 * @param date 日期字符串
	 * @param fmt 日期格式
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date,String fmt) throws ParseException{
		return new SimpleDateFormat(fmt).parse(date);
	}
	
	public static Date dateBefore() throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); //得到前一天
		Date date = calendar.getTime();
		return date;
	}
	
	/**
	 * 返回前一天MMdd日期格式
	 * @return
	 * @throws Exception
	 */
	public static String dateBeforeForMD() throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); //得到前一天
		Date date = calendar.getTime();
		String mmdd = new SimpleDateFormat("MMdd").format(date);
		return mmdd;
	}
	
	/**
	 * 格式化日期为MMdd
	 * @param str
	 * @return
	 * @throws ParseException 
	 */
	public static String dateFmtForMD(String str) throws ParseException {
		return new SimpleDateFormat("MMdd").format(new SimpleDateFormat(DATE_FORMAT).parse(str));
	}
	
	
	/**
	 * 返回前一天  yyyy-MM-dd  日期格式
	 * @return
	 * @throws Exception
	 */
	public static String dateBeforeForYMD() throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); //得到前一天
		Date date = calendar.getTime();
		String mmdd = new SimpleDateFormat(DATE_FORMAT).format(date);
		return mmdd;
	}
	
	/**
	 * 获取所选择日期的前一天的日期
	 * @return
	 * @throws Exception
	 */
	public static String getDateBySelectDate(String selectDate, String dateFmt) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat(DATE_FORMAT).parse(selectDate));
		calendar.add(Calendar.DATE, -1);
		Date date = calendar.getTime();
		String mmdd = new SimpleDateFormat(dateFmt).format(date);
		return mmdd;
	}
	
	/**
	 * 返回当前    yyyyMMdd 日期格式
	 * @return
	 * @throws Exception
	 */
	public static String dateCurrentForYMD() throws Exception{
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	/**
	 * 返回当前  yyyy-MM-dd 日期格式
	 * @return
	 * @throws Exception
	 */
	public static String fmtCurrentForYMD() throws Exception{
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}
	
	/**
	 * yyyy-MM-dd日期格式转为yyyyMMdd
	 * @return
	 * @throws Exception
	 */
	public static String fmtStrDate(String strDate) throws Exception{
		return new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyy-MM-dd").parse(strDate));
	}
	
	/**
	 * yyyyMMdd日期格式转为yyyy-MM-dd
	 * @return
	 * @throws Exception
	 */
	public static String fmtDateStr(String strDate) throws Exception{
		return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(strDate));
	}
	
	/**
	 * 根据所选日期获取当前月份的开始日期
	 * @param selectDate
	 * @return
	 * @throws ParseException
	 */
	public static String getEarlyTimeBySelectDate(String selectDate, String dateFmt) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat(DATE_FORMAT).parse(selectDate));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(dateFmt).format(calendar.getTime());
	}
	
	public static void main(String[] args) throws Exception {
		
//		String date = "2017-09-16";
//		
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new SimpleDateFormat(DATE_FORMAT).parse(date));
//		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
//		//return dateFormat.format(calendar.getTime());
//		
//		System.out.println(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
		
//		Date date = new Date();
//		System.out.println(date.getTime());
		
//		UUID uuid = new UUID(10, 8);
//		 UUID uuid = UUID.randomUUID();
		System.out.println(getCurrentTime());
	}
}
