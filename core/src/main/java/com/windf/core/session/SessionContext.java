package com.windf.core.session;

import com.windf.core.exception.CodeException;

public class SessionContext {
    private static InheritableThreadLocal<Session> sessionThread = new InheritableThreadLocal<Session>();

    public static void start(Session session) {
        sessionThread.set(session);
    }

    public static void end(Session session) {
        sessionThread.set(null);
    }

    public static Object get(String key) {
        Session session = sessionThread.get();
        if (session == null) {
            throw new CodeException("没有初始化session");
        }
        return session.get(key);
    }

    public static Object getSessionId() {
        Session session = sessionThread.get();
        if (session == null) {
            throw new CodeException("没有初始化session");
        }
        return session.getSessionId();
    }

    public static void set(String key, Object value) {
        Session session = sessionThread.get();
        if (session == null) {
            throw new CodeException("没有初始化session");
        }
        session.set(key, value);
    }

    public static void invalidate() {
        Session session = sessionThread.get();
        if (session == null) {
            throw new CodeException("没有初始化session");
        }
        session.invalidate();
    }

}
