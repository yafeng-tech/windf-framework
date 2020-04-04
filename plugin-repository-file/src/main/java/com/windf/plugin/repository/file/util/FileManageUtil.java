package com.windf.plugin.repository.file.util;

import java.io.File;

public class FileManageUtil {
    /**
     * 删除文件
     * @param path
     */
    public static void deleteFile(String path) {
        new File(path).delete();
    }
}
