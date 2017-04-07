package com.jusfoun.hookah.core.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangjl on 17/4/6.
 */
public class ShopUtils {

	/**
	 * 汉字转拼音     
	 * @param chinese
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 * @author wuxingfeng
	 */
	public static String getFullSpell(String chinese) throws BadHanyuPinyinOutputFormatCombination{
		HanyuPinyinOutputFormat  hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
		hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		return PinyinHelper.toHanYuPinyinString(chinese, hanyuPinyinOutputFormat, "", false);
	}
	
	/**
	 * 汉字拼音首字母
	 * @param chinese
	 * @return
	 * @author wuxingfeng
	 */
	 public static String getFirstSpell(String chinese) {   
         StringBuffer pybf = new StringBuffer();   
         char[] arr = chinese.toCharArray();   
         HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
         defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
         defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
         for (int i = 0; i < arr.length; i++) {   
                 if (arr[i] > 128) {   
                     try {   
                         String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);   
                         if (temp != null && temp.length > 0) {   
                                 pybf.append(temp[0].charAt(0));   
                         }   
                     } catch (BadHanyuPinyinOutputFormatCombination e) {   
                             e.printStackTrace();   
                     }   
                 } else {   
                         pybf.append(arr[i]);   
                 }   
         }   
         return pybf.toString().replaceAll("\\W", "").trim();   
	 }

	/**
	 * 是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
