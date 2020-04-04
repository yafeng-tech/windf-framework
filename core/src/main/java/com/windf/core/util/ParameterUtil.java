package com.windf.core.util;

import com.windf.core.exception.ParameterException;

import java.util.*;

public class ParameterUtil {
    /**
     * 获取int类型的参数
     *
     * @param parameter
     * @return
     */
    public static Integer getInteger(String parameter) {
        Integer result = null;
        if (!StringUtil.isEmpty(parameter)) {
            result = Integer.parseInt(parameter);
        }
        return result;
    }

    /**
     * 获取long类型的参数
     *
     * @param parameter
     * @return
     */
    public static Long getLong(String parameter) {
        Long result = null;
        if (!StringUtil.isEmpty(parameter)) {
            result = Long.parseLong(parameter);
        }
        return result;
    }

    /**
     * 获取date类型的参数
     *
     * @param parameter
     * @return
     */
    public static Date getDate(String parameter) {
        Date result = null;
        if (!StringUtil.isEmpty(parameter)) {
            result = DateUtil.parseDate(parameter);
        }
        return result;
    }

    /**
     * 设置默认值
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static String defaultValue(String value, String defaultValue) {
        String result = value;
        if (StringUtil.isEmpty(value)) {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 设置默认值
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static Integer defaultValue(Integer value, Integer defaultValue) {
        Integer result = value;
        if (value == null) {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 判断字符串中有没有空字符串
     *
     * @param obj
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static boolean hasEmpty(Object... obj) {
        boolean result = false;
        if (obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] == null) {
                    result = true;
                    break;
                } else {
                    if (obj[i] instanceof String && StringUtil.isEmpty(obj[i].toString())) {
                        result = true;
                        break;
                    }
                    if (obj[i] instanceof Collection && CollectionUtil.isEmpty((Collection) obj[i])) {
                        result = true;
                        break;
                    }
                    if (obj[i] instanceof Map && ((Map) obj[i]).isEmpty()) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 判断字符串中是否全部不为空
     *
     * @param obj
     * @return
     */
    public static boolean hasNotEmpty(Object... obj) {
        return !hasEmpty(obj);
    }

    /**
     * 判断字符串中是否全部为空
     *
     * @param obj
     * @return
     */
    public static boolean allEmpty(Object... obj) {
        boolean result = true;
        if (obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null && StringUtil.isNotEmpty(obj[i].toString())) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 将字符串格式的ids（逗号隔开），转换为List的形式
     *
     * @param ids
     * @return
     */
    public static List<String> ids(String ids) {
        return Arrays.asList(StringUtil.split(ids, ","));
    }

    /**
     * 断言参数不为空，如果为空抛出异常
     *
     * @param obj
     * @throws ParameterException
     */
    public static void assertNotEmpty(Object... obj) throws ParameterException {
        if (hasEmpty(obj)) {
            throw new ParameterException();
        }
    }
}
