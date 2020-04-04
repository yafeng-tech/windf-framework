package com.windf.plugin.repository.file;

import com.windf.plugin.repository.file.config.RepositoryConfig;
import com.windf.plugin.repository.file.util.FileManageUtil;
import com.windf.plugin.repository.file.util.JSONRepositoryUtil;

import java.io.File;

public class BaseJSONFileRepository {

    /**
     * 保存json文件
     * @param relativePath  相对路径
     * @param object        要保存的对象
     */
    protected void write(String relativePath, Object object) {
        JSONRepositoryUtil.saveJsonFile(this.getHomePath() + "/" + relativePath, object);
    }

    /**
     * 根据路径读取文件中的json
     * 转换为对象
     * @param relativePath 相对路径
     * @param clazz
     * @return
     */
    protected <T> T read(String relativePath, Class<T> clazz) {
        return JSONRepositoryUtil.readObjectByJSONFile(this.getHomePath() + "/" + relativePath, clazz);
    }

    /**
     * 删除文件
     * @param relativePath
     * @return
     */
    protected boolean delete(String relativePath) {
        String realFilePath = this.getHomePath() + "/" + relativePath;

        // 检测文件是否存在，如果不存在，删除失败，返回false
        File file = new File(realFilePath);
        if (!file.exists()) {
            return false;
        }

        FileManageUtil.deleteFile(realFilePath);
        // 删除成功，返回true
        return true;
    }

    /**
     * 获取文件保存的路径
     * @return
     */
    protected String getHomePath() {
        return new RepositoryConfig().getHomePath();
    }

}
