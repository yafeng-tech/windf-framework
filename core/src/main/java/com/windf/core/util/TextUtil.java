package com.windf.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本处理
 * @author windf
 *
 */
public class TextUtil {
	
	private final static String REGXP_FOR_Html = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签   
	
	/**  
     *   
     * 基本功能：过滤所有以"<"开头以">"结尾的标签  
     * <p>  
     *   
     * @param str  
     * @return String  
     */  
    public static String filterHtml(String str) {   
        Pattern pattern = Pattern.compile(REGXP_FOR_Html);   
        Matcher matcher = pattern.matcher(str);   
        StringBuffer sb = new StringBuffer();   
        boolean result1 = matcher.find();   
        while (result1) {   
            matcher.appendReplacement(sb, "");   
            result1 = matcher.find();   
        }   
        matcher.appendTail(sb);   
        return sb.toString();   
    }   
}
