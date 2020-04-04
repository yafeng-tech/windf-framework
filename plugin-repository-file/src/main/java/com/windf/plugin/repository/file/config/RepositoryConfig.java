package com.windf.plugin.repository.file.config;

public class RepositoryConfig {
    /**
     * 用于存储的路径
     */
    private String homePath = System.getProperty("user.home") + "/.windf";

    public String getHomePath() {
        return homePath;
    }
}
