package com.windf.core.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final String FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLE_DATE = "yyyy-MM-dd";
    public static final String FULL_DATE_TIME_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String SIMPLE_DATE_CHINESE = "yyyy年MM月dd日";
    public static final String SMART_DATE = "MM-dd";
    public static final String YEAR_DATE = "yyyy";
    public static final String FULL_TIME = "HH:mm:ss";
    public static final String SIMPLE_TIME = "HH:mm";
    public static final String SMART_TIME = "mm:ss";
    public static final String[] FULL_DATE_TIME_Arr = new String[]{FULL_DATE_TIME, SIMPLE_DATE, SMART_DATE, FULL_TIME, YEAR_DATE, SIMPLE_TIME, SMART_TIME};
    public static final String[] SIMPLE_DATE_ARR = new String[]{SIMPLE_DATE};
    
    /**
     * 解析时间字符串为时间格式
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateStr, String format) {
    	Date result = null;
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    	try {
    		result = simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    	
    /**
     * 尝试是用各种方式解析时间字符串为时间格式
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
    	Date result = null;
    	
    	result = parseDate(dateStr, FULL_DATE_TIME);
    	if (result == null) {
    		result = parseDate(dateStr, SIMPLE_DATE);
		}
    	if (result == null) {
    		result = parseDate(dateStr, FULL_DATE_TIME_CHINESE);
    	}
    	if (result == null) {
    		result = parseDate(dateStr, SIMPLE_DATE_CHINESE);
    	}
    	
		return result;
    }

    /**
     * 按照默认格式解析当前时间
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format() {
        return format(new Date(), FULL_DATE_TIME);
    }

    /**
     * 按照指定格式解析当前时间
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format(String format) {
        return format(new Date(), format);
    }

    /**
     * 按照默认格式解析时间
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, FULL_DATE_TIME);
    }

    /**
     * 按指定格式解析时间
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
