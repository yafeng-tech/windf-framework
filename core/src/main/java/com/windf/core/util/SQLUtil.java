package com.windf.core.util;

import java.util.ArrayList;
import java.util.List;

public class SQLUtil {

	/**
	 * 获得字符串连个标记中间的字段，用某个字符切割（但是括号、引号中的不算）
	 * 括号和引号中间的东西会被去掉（包括括号和引号本身）
	 * 括号包括：大括号、中括号、小括号
	 * 引号包括：单引号、双引号
	 * TODO 目前只实现了小括号
	 * @param sql
	 * @param startFlag
	 * @param endFlag
	 * @return
	 */
	public static List<String> getSqlNames(String sql, String startFlag, String endFlag, String splitStr) {
		
		sql = sql.toLowerCase();
		String selectStr = sql.substring(sql.indexOf(startFlag) + startFlag.length(), sql.indexOf(endFlag));
		
		// 删除select中的子查询
		while (selectStr.contains("(")) {
			selectStr = selectStr.replaceAll("\\([^\\)\\(]*\\)", "");
		}
		String[] selects = selectStr.split(splitStr);
		
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < selects.length; i++) {
			String key = null;

			String select = selects[i].trim();
			String[] ss = select.split("\\s");
			if (ss.length == 1) {
				ss = ss[0].split(".");
				if (ss.length == 1) {
					key = ss[0];
				} else {
					key = ss[ss.length - 1];
				}
			} else {
				key = ss[ss.length - 1];
			}
			
			if (key != null) {
				names.add(key.trim());
			}
			
		}
		
		return names;
	}

    /**
	 * 表名称转换为实体名称
	 * @param tableName
	 * @return
	 */
	public static String tableName2EntityName(String tableName) {
		if (tableName.contains("_r_")) {
			tableName = tableName.replace("_r_", "_");
		}

		String result = StringUtil.toCamelCase(tableName, "_");
		result = StringUtil.firstLetterUppercase(result);

        return result;
	}

	/**
	 * 实体名称转换为表名称
	 * @param entityName
	 * @return
	 */
	public static String entityName2TableName(String entityName) {
		String result = StringUtil.splitCamelCase(entityName, "_");
		return result;
	}

	/**
	 * 获得查询数量的coun
	 * @param querySql
	 * @return
	 */
	public static String getCountSql(String querySql) {
		querySql = querySql.substring(querySql.indexOf("from "));
		querySql = "select count(*) " + querySql;
		int orderByIndex = querySql.lastIndexOf("order by");
		if (orderByIndex > -1) {
			querySql = querySql.substring(0, querySql.lastIndexOf("order by"));
		}
		int limitIndex = querySql.lastIndexOf("limit");
		if (limitIndex > -1) {
			querySql = querySql.substring(0, querySql.lastIndexOf("limit"));
		}
		return querySql;
	}
}
