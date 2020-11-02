package com.zxelec.yhkk.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
	public final static String deyerPattern = "yyyy-MM";
	public final static String defaultPattern = "yyyy-MM-dd";
	public final static String dateTimePattern = "yyyy-MM-dd HH:mm";
	public final static String dateTimeHourPattern = "yyyy-MM-dd HH";
	public final static String dateMonthHourPattern = "M月d日HH:mm";
	public final static String dateMonthPattern = "yyyy年MM月dd日";
	public final static String dateTimeSecondPattern = "yyyy-MM-dd HH:mm:ss";
	public final static String dateTimeSecondPattern_MMSS = "HH:mm:ss";
	public final static String dateTimeSecondPatternSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String dateTimeYNDPattern = "yyyy年MM月dd日HH点mm分";
	public final static String dateTimeYNDHMSPattern = "yyyyMMddHHmmssSSS";
	public final static String dateYNDPattern = "yyyyMMdd";

	public static String data2String(Date date,String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 当前时间减去N天
	 */
	public static Date getDateSubtractNday(Integer n) {
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.add(Calendar.DAY_OF_MONTH, n);
		return date.getTime();
	}
	
	/**
	 * 指定时间减去N天
	 */
	public static Date getDateSubtractNday(Date beginDate,Integer n) {
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.add(Calendar.DAY_OF_MONTH, n);
		return date.getTime();
	}
	
	public static String data2String() {
		Date date = new Date();
		return data2String(date, dateTimeSecondPatternSSS);
	}
	
	public static String data2String(String pattern) {
		Date date = new Date();
		return data2String(date, pattern);
	}
	
	/**
	 * Description: 将日期字符串转换成指定格式日期
	 *
	 * @param dateStr
	 * @param partner
	 * @return @Version1.0 2012-11-5 上午08:50:55
	 */
	public static Date dateString2Date(String dateStr, String pattern) {
		try {
			SimpleDateFormat formatter = getSimpleDateFormat(pattern);
			ParsePosition pos = new ParsePosition(0);
			return formatter.parse(dateStr, pos);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Description: 将当前日期转换成指定格式日期
	 *
	 * @param dateStr
	 * @param partner
	 * @return @Version1.0 2012-11-5 上午08:50:55
	 */
	public static Date dateString2Date(String pattern) {
		try {
			String dateStr = data2String(pattern);
			SimpleDateFormat formatter = getSimpleDateFormat(pattern);
			ParsePosition pos = new ParsePosition(0);
			return formatter.parse(dateStr, pos);
		} catch (NullPointerException e) {
			return null;
		}
	}

	
	
	public static String data2StringYNDHMS() {
		Date date = new Date();
		return data2String(date, dateTimeYNDHMSPattern);
	}
	
	private static SimpleDateFormat getSimpleDateFormat(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf;
	}
	public static void main(String[] args) {
		System.out.println(DateUtils.data2String(
				DateUtils.getDateSubtractNday(
						DateUtils.dateString2Date("yyyy-MM-dd"),-30),"yyyy-MM-dd HH:mm:ss"));
		
	}

}