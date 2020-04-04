package com.windf.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
	public static final String ID_NUMBER = "^([0-9]{15})|([0-9]{17}[xX]{1})([0-9]{18})$";
	public static final String ID_NUMBER_NOTE = "身份证号";
	public static final String USER_NAME = "^[\u4E00-\u9FA5A-Za-z0-9_]+$";
	public static final String USER_NAME_NOTE = "中文、英文或数字";	
	public static final String SCORE = "^(([1-9]?\\d)(\\.\\d)?)$|^100$", SCORE_NOTE = "^0至100的整数，0至1位小数";
	public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-]\\w+)*(\\.\\w+([-]\\w+)*){1,3}$";	// 邮箱
	public static final String EMAIL_NOTE = "邮箱";
	public static final String TELPHONE = "^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";	// 3至4位区号-7至8位直播号码-1至4位分机号
	public static final String TELPHONE_NOTE = "3至4位区号-7至8位直播号码-1至4位分机号";
	public static final String FEE = "^\\d{1,8}(\\.\\d{1,2})?$";
	public static final String FEE_NOTE = "1到8位整数 0到2位小数"; 
	public static final String EDU = "^[\u521D\u4E2D|\u9AD8\u4E2D|\u804C\u9AD8|\u4E2D\u4E13|\u6280\u6821|\u5927\u4E13|\u5927\u672C|\u7855\u58EB|\u535A\u58EB]{1,}$";	// 学历只能填写：初中、高中、职高、中专、技校、大专、大本、硕士、博士
	public static final String EDU_NOTE = "初中、高中、职高、中专、技校、大专、大本、硕士、博士";	
	// 民族只能填写：汉族、蒙古族、回族、藏族、维吾尔族、苗族、彝族、壮族、布依族、朝鲜族、满族、侗族、瑶族、白族、土家族、哈尼族、哈萨克族、傣族、
	// 傈僳族、佤族、畲族、高山族、拉祜族、水族、东乡族、纳西族、景颇族、柯尔克孜族、土族、达斡尔族、仫佬族、羌族、布朗族、撒拉族、毛难族、仡佬族、锡伯族、阿昌族、普米族、塔吉克族、怒族、乌孜别克族、
	// 俄罗斯族、鄂温克族、崩龙族、保安族、裕固族、京族、塔塔尔族、独龙族、鄂伦春族、赫哲族、门巴族、珞巴族、基诺族、其他、外国血统中国籍人士
	public static final String FOLK = "^[\u6c49\u65cf|\u8499\u53e4\u65cf|\u56de\u65cf|\u85cf\u65cf|\u7ef4\u543e\u5c14\u65cf|\u82d7\u65cf|\u5f5d\u65cf|\u58ee\u65cf|"
			+"\u5e03\u4f9d\u65cf|\u671d\u9c9c\u65cf|\u6ee1\u65cf|\u4f97\u65cf|\u7476\u65cf|\u767d\u65cf|\u571f\u5bb6\u65cf|\u54c8\u5c3c\u65cf|\u54c8\u8428\u514b\u65cf|"
			+"\u50a3\u65cf|\u5088\u50f3\u65cf|\u4f64\u65cf|\u7572\u65cf|\u9ad8\u5c71\u65cf|\u62c9\u795c\u65cf|\u6c34\u65cf|\u4e1c\u4e61\u65cf|\u7eb3\u897f\u65cf|"
			+"\u666f\u9887\u65cf|\u67ef\u5c14\u514b\u5b5c\u65cf|\u571f\u65cf|\u8fbe\u65a1\u5c14\u65cf|\u4eeb\u4f6c\u65cf|\u7f8c\u65cf|\u5e03\u6717\u65cf|"
			+"\u6492\u62c9\u65cf|\u6bdb\u96be\u65cf|\u4ee1\u4f6c\u65cf|\u9521\u4f2f\u65cf|\u963f\u660c\u65cf|\u666e\u7c73\u65cf|\u5854\u5409\u514b\u65cf|\u6012\u65cf|"
			+"\u4e4c\u5b5c\u522b\u514b\u65cf|\u4fc4\u7f57\u65af\u65cf|\u9102\u6e29\u514b\u65cf|\u5d29\u9f99\u65cf|\u4fdd\u5b89\u65cf|\u88d5\u56fa\u65cf|\u4eac\u65cf|"
			+"\u5854\u5854\u5c14\u65cf|\u72ec\u9f99\u65cf|\u9102\u4f26\u6625\u65cf|\u8d6b\u54f2\u65cf|\u95e8\u5df4\u65cf|\u73de\u5df4\u65cf|\u57fa\u8bfa\u65cf|"
			+"\u5176\u4ed6|\u5916\u56fd\u8840\u7edf\u4e2d\u56fd\u7c4d\u4eba\u58eb]{1,}$";
	public static final String FOLK_NOTE = "民族";
	public static final String MOBILE = "^(\\+86)?0?1[3|5|7|8]\\d{9}$";
	public static final String MOBILE_NOTE = "移动电话";
	public static final String ZIP = "^\\d{6}$"; 
	public static final String ZIP_NOTE = "邮编";
	public static final String NUMBER = "^\\d+$";
	public static final String NUMBER_NOTE = "数字"; 
	public static final String NUMBER_VALUE = "^(-)?[([1-9]\\d*)|0](\\.\\d*[1-9])?$";
	public static final String NUMBER_VALUE_NOTE = "数"; 
	public static final String SEX = "^[\u7537|\u5973]{1,1}$";
	public static final String SEX_NOTE = "性别";
	
	public static boolean match(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(str);
		return matcher.matches();
	}

	/**
	 * 反射获得ReflectUtil中所有正则表达式的常量
	 * @return 常量备注-变量名
	 */
	public static Map<String, String> getAllPattern() {
		Map<String, String> result = new HashMap<String, String>();
		
		Map<String, Object> constantValues = ReflectUtil.getAllConstantValue(RegularUtil.class);
		if (CollectionUtil.isNotEmpty(constantValues)) {
			Iterator<String> iterator = constantValues.keySet().iterator();
			while (iterator.hasNext()) {
				 String noteName = iterator.next();
				 Object noteValue = constantValues.get(noteName);
				 
				 if (noteName.endsWith("_NOTE") && noteValue != null) {
					String patternName = noteName.substring(0, noteName.length() -"_NOTE".length() );
					if (constantValues.get(patternName) != null) {
						result.put((String) noteValue, patternName);
					}
				}
				
			}
		}
		
		return result;
	}
}
