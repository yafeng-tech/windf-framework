package com.windf.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.windf.core.entity.BaseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * bean相关的方法
 */
public class BeanUtil {

    /**
     * 创建bean
     *
     * @param clazz
     * @return
     */
    public static <T> T createBean(Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
        }

        return result;
    }

    /**
     * 判断，是否是用户自定义的getter方法
     * 今天方法除外
     *
     * @param method
     * @return
     */
    public static boolean isGetterMethod(Method method) {
        boolean result = false;
        int modifiers = method.getModifiers();
        if (!Modifier.isStatic(modifiers)) {
            String methodName = method.getName();
            result = (methodName.startsWith("get") || methodName.startsWith("is"))
                    && method.getParameterTypes().length == 0
                    && !"getClass".equals(methodName)
                    && !method.getDeclaringClass().getName().contains("$");
        }

        return result;
    }

    /**
     * 根据map设置object的值
     * 遍历map的key，根据key获得setter方法，设置值为key对应的value
     *
     * @param clazz
     * @param values
     * @return
     */
    public static <T> T getBeanByMap(Class<T> clazz, Map<String, ? extends Object> values) {
        T result = createBean(clazz);
        if (result != null) {
            result = JSONObject.parseObject(JSON.toJSONString(values), clazz);
        }

        return result;
    }

    /**
     * 根据map设置setProperty属性
     *
     * @param target
     * @param sourceMap
     */
    public static void copyProperties(Object target, Map<String, Object> sourceMap) {
        try {
            Object source = getBeanByMap(target.getClass(), sourceMap);
            copyProperties(target, source, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 基本的bean复制，从新的bean复制属性到老的bean
     * 老的bean用于保存
     *
     * @param target
     * @param source
     */
    public static void copyProperties(Object target, Object source) {
        copyProperties(target, source, true);
    }

    /**
     * 基本的bean复制，从新的bean复制属性到老的bean
     * 老的bean用于保存
     *
     * @param target
     * @param source
     * @param copyNull 是否复制为空的属性
     */
    public static void copyProperties(Object target, Object source, boolean copyNull) {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        // 获取被copy类的所有属性
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);

        for (PropertyDescriptor targetPd : targetPds) {
            // 获取write方法
            Method writeMethod = targetPd.getWriteMethod();

            // 如果方法为空，不进行赋值操作
            if (writeMethod == null) {
                continue;
            }

            // 获得属性
            PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());

            // 如果source中储存在对应的属性
            if (sourcePd == null) {
                continue;
            }

            try {
                // 获取源bean的读取方法
                Method readMethod = sourcePd.getReadMethod();

                // 如果读写方法不一致，跳过
                if (readMethod == null || !ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                    continue;
                }

                // 获取返回值
                Object value = readMethod.invoke(source);

                // 如果value为空，则跳过，不进行更新
                if (!copyNull && value == null) {
                    continue;
                }

                // 如果是实体bean，getId方法值为null，不设置 TODO 这里使用了管理端框架的bean
                if (BaseEntity.class.isAssignableFrom(readMethod.getReturnType())) {

                    // 如果value为空，则跳过，不进行更新
                    if (value == null) {
                        continue;
                    }

                    // 获取id
                    BaseEntity bean = (BaseEntity) value;
                    Object idValue = bean.getId();

                    // 如果getId 值为 null，则不进行赋值
                    if (idValue == null) {
                        continue;
                    }

                    // 如果getId 有值，则把oldBean中对应obj的属性设置为null
                    if ("".equals(idValue)) {
                        value = null;
                    }
                }

                // 赋值
                writeMethod.invoke(target, value);

            } catch (Throwable ex) {
                throw new FatalBeanException(
                        "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
            }
        }
    }

    /**
     * 获得对象的所有非空属性，静态方法除外，非序列化方法除外
     *
     * @param object
     * @return 属性名-属性值
     */
    public static Map<String, Object> getAllGetterMethods(Object object) {
        Class<? extends Object> clazz = object.getClass();

        Map<String, Object> getterMethodValueMap = new HashMap<String, Object>();
        Method[] methods = clazz.getMethods();
        if (methods != null) {
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                String methodName = method.getName();

                // 是getter方法
                if (isGetterMethod(method)) {

                    Object result = null;
                    try {
                        result = method.invoke(object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result != null) {
                        methodName = getNameByGetterOrSetter(methodName);
                        getterMethodValueMap.put(methodName, result);
                    }
                }
            }
        }

        return getterMethodValueMap;
    }

    /**
     * 根据getter或者setter方法，获得属性名
     *
     * @param methodName
     * @return
     */
    public static String getNameByGetterOrSetter(String methodName) {
        if (methodName.startsWith("is")) {
            return methodName.substring(2, 3).toLowerCase() + methodName.substring(3);
        } else {
            return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
        }
    }

    /**
     * 根据属性名称，获得setter方法名称
     * 只是拼接字符串，不做任何验证
     *
     * @param name
     * @return
     */
    public static String getSetterMethodName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 根据属性名称，获得getter方法名称
     * 只是拼接字符串，不做任何验证
     *
     * @param name
     * @return
     */
    public static String getGetterMethodName(String name) {
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
