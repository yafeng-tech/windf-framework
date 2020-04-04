package com.windf.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class ReflectUtil {

    /**
     * 类是否可以被实例化
     *
     * @param clazz
     * @return
     */
    public static boolean isCanInstancable(Class clazz) {
        return clazz != null && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }

    /**
     * 获得map的key的类型
     *
     * @param type
     * @return
     */
    public static Type getGenericOfMapKey(Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type keyType = parameterizedType.getActualTypeArguments()[0];
        return keyType;
    }

    /**
     * 获得map的value的类型
     *
     * @param type
     * @return
     */
    public static Type getGenericOfMapValue(Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type valueType = parameterizedType.getActualTypeArguments()[1];
        return valueType;
    }

    /**
     * 获得集合的泛型
     *
     * @param type
     * @return
     */
    public static Type getGenericOfCollection(Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return parameterizedType.getActualTypeArguments()[0];
    }

    /**
     * 获得类的所有泛型
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Object>[] getGenericOfClass(Class<? extends Object> clazz) {
        Type[] types = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
        Class<? extends Object>[] result = new Class[types.length];
        for (int i = 0; i < types.length; i++) {
            result[i] = (Class<? extends Object>) types[i];
        }
        return result;
    }

    /**
     * 获得所有常量
     * @param clazz
     * @return 变量名-value的键值对
     */
    public static Map<String, Object> getAllConstantValue(Class<? extends Object> clazz) {
        Map<String, Object> result = new HashMap<>();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
                if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                    try {
                        result.put(field.getName(), field.get(clazz));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取注解
     * TODO 等待优化，直接获取值，而不是返回注解基类
     *
     * @param obj
     * @param annotationClass
     * @return
     */
    public static Annotation getAnnotation(Object obj, Class<? extends Object> annotationClass) {
        if (obj == null) {
            return null;
        }

        Annotation result = null;
        for (Annotation annotation : obj.getClass().getAnnotations()) {
            if (annotation.annotationType().equals(annotationClass)) {
                result = annotation;
                break;
            }
        }

        return result;
    }

    /**
     * 获取包括父类在内的所有字段
     *
     * @param clazz
     * @return
     */
    public static Field[] getAllField(Class<? extends Object> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass(); //得到父类,然后赋给自己
        }

        return fieldList.toArray(new Field[fieldList.size()]);
    }

    /**
     * 获得调用者的class名称
     *
     * @return
     */
    public static String getInvokerClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 4) {    // 如果超出堆栈，返回null
            return null;
        }
        return stackTrace[3].getClassName();
    }
}
