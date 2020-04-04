package com.windf.core.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtil {
	/**
	 * 把对象转换为json字符串
	 * @param obj
	 * @return
	 */
	public static String toJSONStr(Object obj) {
		String result = null;
		if (obj == null) {
			
		} else if (obj instanceof Array || obj instanceof Collection) {
			result = JSONArray.toJSONString(obj,SerializerFeature.WriteDateUseDateFormat);
		} else {
			result = JSONObject.toJSONString(obj,SerializerFeature.WriteDateUseDateFormat);
		}
		return result;
	}

	/**
	 * 解析JSON字符串，变成对象
	 * @param text
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T parseJSONStr(String text, Class<T> clazz) {
		return JSONObject.parseObject(text, clazz);
	}


	/**
	 * 解析JSON字符串，变成对象的集合
	 * @param text
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> parseJSONStrToList(String text, Class<T> clazz) {
		return JSONArray.parseArray(text, clazz);
	}

	public static Map<String, Object> parseJSON(String str) {
		return JSONObject.parseObject(str);
	}
}
