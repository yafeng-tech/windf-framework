package com.windf.core.util;

import com.windf.core.exception.ParameterException;
import com.windf.core.exception.UserException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTemplateUtil {
    /**
     * 解析sql，把sql中的#{参数名}替换为mapParam中相应的值
     * 如果存在[[]]，eg：[and a.id = #{paramName}]，则判断中括号中的#{参数名}
     * 对应于mapParam中的值，是否为空，若为空，则删除[[]]中间的sql，若不为空，
     * 则替换为mapParam对应的值
     *
     * @param sql
     * @param mapParam
     * @return
     * @throws UserException
     */
    public static String analysisTemplate(String sql, Map<String, Object> mapParam) throws UserException {
        /*
         * 解析条件:[[]]中的sql
         */
        sql = analysisCondition(sql, mapParam);
        /*
         * 解析参数:${}
         */
        sql = analysisParam(sql, mapParam);
        return sql;
    }

    /**
     * 解析sql中包含的[[]]
     *
     * @param sql
     * @param mapParam
     * @return
     */
    private static String analysisCondition(String sql, Map<String, Object> mapParam) throws UserException {
        if (sql.contains("[[") && sql.contains("]]")) {
            String regex = "(\\[\\[[^\\]\\]]*\\]\\])"; //正则匹配，[[]]
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(sql);
            while (matcher.find()) {
                String matchExp = matcher.group();  // eg:[[a.id = #{paramName}]]
                String matchContent = matcher.group(1);  // eg:a.id = #{paramName}

                try {
                    sql = sql.replace(matchContent,analysisParam(matchContent, mapParam));
                } catch (UserException e) {
                    sql = sql.replace(matchExp, "");
                }

            }
        }
        return sql;
    }

    /**
     * 解析sql中包含的#{}，替换mapParam中对应的值
     *
     * @param sql
     * @param mapParam
     * @return
     */
    private static String analysisParam(String sql, Map<String, Object> mapParam) throws UserException {
        if (sql.contains("#{") && sql.contains("}")) { //sql中存在'#{}',表示我们自己传的参数，进行解析
            String regex = "#\\{([\\w\\.]*)\\}"; //正则匹配，eg：#{paramName} or #{paramName.id}
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(sql);
            while (matcher.find()) {
                String paramNameExp = matcher.group();  // #{paramName}
                String paramName = matcher.group(1);    // paramName
                String paramValue = analysisParamValue(paramName, mapParam);
                if (StringUtil.isEmpty(paramValue)) {
                    throw new ParameterException();
                }
                paramValue = paramValue.replaceAll("[\'\"\\s]+", "");//删除查询参数中的空格、单双引号
                sql = sql.replace(paramNameExp, paramValue);
                sql = sql.replace("[[", " ").replace("]]", " ");
            }
        }

        // 若分号";"前面是"\n"或者末尾存在"\n"，则删除
        if (sql.endsWith("\n;") || sql.endsWith("\n")) {
            sql = sql.substring(0,(sql.lastIndexOf("\n;") > -1 ? sql.lastIndexOf("\n;") : sql.lastIndexOf("\n")));
        }
        return sql;
    }

    /**
     * 参数中包含'.',比如，bean.id,参数封装为Map<String,Map<String,String>>，进行参数解析
     * 如果是多个层级的Map，进行递归解析
     * @param paramKey
     * @param mapParam
     * @return
     */
    private static String analysisParamValue(String paramKey, Map<String, Object> mapParam) {
        String paramValue;
        if (paramKey.contains(".")) {
            String subKey = paramKey.substring(0,paramKey.indexOf("."));
            Object value = mapParam.get(subKey);
            paramKey = paramKey.substring(paramKey.indexOf(".")+1);
            if (value instanceof Map){
                mapParam = (Map<String, Object>)value;
                paramValue = analysisParamValue(paramKey, mapParam);
            }else {
                paramValue = (String) value;
            }
        } else {
            paramValue = (String) mapParam.get(paramKey);
        }
        return paramValue;
    }

}
