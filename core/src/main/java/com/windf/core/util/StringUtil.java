package com.windf.core.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;

/**
 * 字符串工具
 *
 * @author windf
 */
public class StringUtil extends StringUtils {
    public static final String UTF8 = "UTF-8";
    private static final String ALL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 如果字符串为null返回空字符串，否则返回该字符串
     *
     * @param str
     * @return
     */
    public static String fixNull(String str) {
        return fixNull(str, "");
    }


    /**
     * 如果字符串为null返回特定字符串，否则返回该字符串
     *
     * @param str
     * @param defaultString 默认的字符串
     * @return
     */
    public static String fixNull(String str, String defaultString) {
        return str == null ? defaultString : str;
    }

    /**
     * 将以长串压缩
     *
     * @param data
     * @return
     */
    public static int getHashCode(String data) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", data);
        return map.hashCode();
    }

    /**
     * ant形式验证
     * @param pattern
     * @param path
     * @return
     */
    public static boolean antMatch(String pattern, String path) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.match(pattern, path);
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String firstLetterUppercase(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    /**
     * 将驼峰命名法的字符串，单词之间用指定字符隔开，切单词首字母小写
     *
     * @param str
     * @param separator
     * @return
     */
    public static String splitCamelCase(String str, String separator) {
        StringBuffer result = new StringBuffer();

        if (StringUtil.isNotEmpty(str)) {
            String[] ss = str.split("(?=[A-Z])");
            for (int i = 0; i < ss.length; i++) {
                if (result.length() > 0) {
                    result.append(separator);
                }
                result.append(ss[i].toLowerCase());
            }
        }

        return result.toString();
    }

    /**
     * 将字符中指定字符作为分隔符，分隔符后首字母大写
     *
     * @param str
     * @param separator
     * @return
     */
    public static String toCamelCase(String str, String separator) {
        if (ParameterUtil.hasEmpty(str, separator)) {
            return null;
        }

        StringBuffer result = new StringBuffer();

        String[] ss = str.split(separator);
        for (int i = 0; i < ss.length; i++) {
            if (result.length() > 0) {
                result.append(firstLetterUppercase(ss[i]));
            } else {
                result.append(ss[i]);
            }
        }

        return result.toString();
    }

    /**
     * 将集合转换为字符串，中间用指定字符隔开
     *
     * @param ss
     * @param separator
     * @return
     */
    public static String join(String[] ss, String separator) {
        List<String> collection = Arrays.asList(ss);
        return StringUtils.join(collection, separator);
    }

    /**
     * 获得句子的第一个单词，
     * 如果句子中没有空格，数字第一返回null，数组第二个是参数本身
     *
     * @param s "第一个单词","剩下的句子"
     * @return
     */
    public static String[] getFirstWord(String s) {
        int firstBlank = s.indexOf(" ");
        String firstWord = null;
        if (firstBlank > -1) {
            firstWord = s.substring(0, firstBlank);
            s = s.substring(firstBlank + 1);
        }
        return new String[]{firstWord, s};
    }

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        int len = ALL_CHARS.length();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHARS.charAt(NumberUtil.getRandom(len - 1)));
        }
        return sb.toString();
    }

    /**
     * 如果obj不为null,调用toString，否则返回null
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(String a, String b) {
        // 如果a=null，则是否相等，决定于b是否为null
        if (a == null) {
            return b == null;
        }

        // 如果a部位null，则正常比较
        return a.equals(b);
    }
}
