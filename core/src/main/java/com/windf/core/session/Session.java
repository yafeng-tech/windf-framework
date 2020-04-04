package com.windf.core.session;

public interface Session {
    /**
     * 根据key获取存储的对象 如果找不到，返回null
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 设置键值
     * @param key
     * @param obj
     * @return 是否替换
     */
    boolean set(String key, Object obj);

    /**
     * 注销
     */
    void invalidate();

    /**
     * 获取sessionId
     * 也可能是token，一般用于token的传递
     * @return
     */
    String getSessionId();
}
