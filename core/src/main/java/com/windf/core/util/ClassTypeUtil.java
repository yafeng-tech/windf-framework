package com.windf.core.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassTypeUtil {

	/**
	 * 将字符串类型转换为指定的类型
	 *
	 * @param clazz
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T> T parseType(Class<T> clazz, String value) {

		Object result;
		if (isString(clazz)) {
			result = value;
		} else if (isInteger(clazz)) {
			result = Integer.parseInt(value);
		} else if (isBigDecimal(clazz)) {
			result = BigDecimal.valueOf(Double.parseDouble(value));
		} else if (isDate(clazz)) {
			result = new Date(Long.parseLong(value));
		} else if (isBoolean(clazz)) {
            result = false;
            if (value != null) {
                if ("Y".equals(value.toString())
                        || "true".equals(value.toString())
                        || "1".equals(value.toString())) {
                    result = true;
                }
            }

        } else {
			result = value;
		}

		return (T) result;
	}

    /**
     * 根据类的属性名获得属性类型
     * 支出级联属性，用“.”分开
     *
     * @return
     */
    public static Class getTypeByAttrName(Class clazz, String attrName) {
        // 分解attrName，如果包含“.”,要分开，剩下的递归处理
        String myAttrName = attrName;
        String subAttrName = null;
        int pointIndex = attrName.indexOf(".");
        if (pointIndex > 0) {
            myAttrName = attrName.substring(0, pointIndex);
            subAttrName = attrName.substring(pointIndex + 1);
        }

        // 获得当前clazz的属性
        Class fieldType = null;
        try {
            Field field = clazz.getDeclaredField(myAttrName);
            fieldType = field.getType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 如果当前属性是对象，且subAttrName不为空（意思是attrName包含“.”）,递归获取（返回子类型的属性类型）
        if (!ClassTypeUtil.isBaseType(fieldType) && subAttrName != null) {
            return getTypeByAttrName(fieldType, subAttrName);
        } else {
            // 直接返回
            return fieldType;
        }
    }

    /**
     * 判断类是否是基本类型
     *
     * @param clazz
     * @return
     */
    public static boolean isBaseType(Class<? extends Object> clazz) {
        boolean result = true;
        if (!clazz.isPrimitive()) { // 是否是原始类型
            if (!clazz.getName().startsWith("java.lang")) { // 是否是包装类
                result = false;
            } else if ("java.lang.Object".equals(clazz.getName())) {
                result = false;
            }
        }
        return result;
    }

    public static boolean isInteger(Class<? extends Object> clazz) {
        boolean result = false;

        if (clazz != null) {
            if ("java.lang.Integer".equals(clazz.getName())) {
                result = true;
            }
        }

        return result;
    }

    public static boolean isNumber(Class<? extends Object> clazz) {
        boolean result = false;
        if ((Number.class.isAssignableFrom(clazz))) {
            result = true;
        }
        return result;
    }

    public static boolean isLong(Class<? extends Object> clazz) {
        boolean result = false;

        if (clazz != null) {
            if ("java.lang.Long".equals(clazz.getName())) {
                result = true;
            }
        }

        return result;
    }

    public static boolean isBoolean(Class<? extends Object> clazz) {
        boolean result = false;

        if (clazz != null) {
            if ("java.lang.Boolean".equals(clazz.getName())) {
                result = true;
            } else if ("boolean".equals(clazz.getName())) {
                result = true;
            }
        }

        return result;
    }

    public static boolean isString(Class clazz) {
        boolean result = false;

        if (clazz != null) {
            if ("java.lang.String".equals(clazz.getName())) {
                result = true;
            }
        }

        return result;
    }

    public static boolean isDate(Class<? extends Object> clazz) {
        boolean result = false;

        if (clazz != null) {
            if ("java.util.Date".equals(clazz.getName())) {
                result = true;
            } else if ("java.sql.Date".equals(clazz.getName())) {
                result = true;
            }
        }

        return result;
    }

    public static boolean isBigDecimal(Class<? extends Object> clazz) {
        boolean result = false;

        if (clazz != null) {
            if ("java.math.BigDecimal".equals(clazz.getName())) {
                result = true;
            }
        }

        return result;
    }

    public static boolean isCollection(Class<? extends Object> clazz) {
        boolean result = false;
        if (clazz.isAssignableFrom(List.class) || clazz.isAssignableFrom(Set.class)) {
            result = true;
        }
        if (!result) {
            if (!result) {
                Class<? extends Object>[] interfaces = clazz.getInterfaces();
                for (int i = 0; i < interfaces.length; i++) {
                    Class<? extends Object> c = interfaces[i];
                    if (List.class.isAssignableFrom(c) || Set.class.isAssignableFrom(c)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static boolean isMap(Class<? extends Object> clazz) {
        boolean result = false;
        if (Map.class.isAssignableFrom(clazz)) {
            result = true;
        }

        if (!result) {
            Class<? extends Object>[] interfaces = clazz.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                Class<? extends Object> c = interfaces[i];
                if (Map.class.isAssignableFrom(c)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}
