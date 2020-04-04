package com.windf.plugin.repository.file.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.windf.core.exception.CodeException;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.FileUtil;
import com.windf.core.util.JSONUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 用户json持久化的工具
 */
public class JSONRepositoryUtil {

    /**
     * 保存json文件
     * @param realPath    相对路径
     * @param object      要保存的对象
     */
    public static File saveJsonFile(String realPath, Object object) {
        // 转换为内容
        String content = JSONUtil.toJSONStr(object);

        // JSON格式化
        content = prettyJSON(content);

        // 创建文件，用于保存的文件
        File file = new File(realPath);

        return write(file, content, false);
    }

    /**
     * 写入文件
     * @param path      文件路径
     * @param content   文件内容
     * @param append    是否是追加
     * @return
     */
    public static File write(String path, String content, boolean append) {
        File file = new File(path);
        return write(file, content, append);
    }

    /**
     * 获取json文件，转变为对象
     * @param path
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readObjectByJSONFile(String path, Class<T> clazz) {
        // 读取json文件的每一行
        List<String> jsonFileLines;
        try {
            jsonFileLines = FileUtil.readLine(new File(path));
        } catch (FileNotFoundException e) {
            // 如果文件读取发生异常，则没有
            return null;
        }

        // 读取每一行为字符串
        String content = CollectionUtil.readCollectionAsString(jsonFileLines);

        // json装换为字符串
        return JSONUtil.parseJSONStr(content, clazz);
    }

    /**
     * 写入文件
     * @param file      要写入的文件
     * @param content   文件内容
     * @param append    是否是追加
     * @return
     */
    private static File write(File file, String content, boolean append) {

        createFile(file);

        FileWriter fileWritter = null;
        try {
            fileWritter = new FileWriter(file, append);
            fileWritter.write(content);
        } catch (IOException e) {
            throw new CodeException(e);
        } finally {
            if (fileWritter != null) {
                try {
                    fileWritter.close();
                } catch (IOException e) {
                    throw new CodeException(e);
                }
            }
        }

        return file;
    }

    /**
     * 如果文件不存在，创建
     *
     * @param files
     */
    public static void createFile(File... files) {
        for (File file : files) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new CodeException(e);
                }
            }
        }
    }

    /**
     * 美化JSON
     * @param json
     * @return
     */
    public static String prettyJSON(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json, Feature.OrderedField);
        return JSONObject.toJSONString(jsonObject,true);
    }

}
