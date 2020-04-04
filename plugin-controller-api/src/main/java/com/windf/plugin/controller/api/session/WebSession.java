package com.windf.plugin.controller.api.session;

import com.windf.core.session.Session;

import javax.servlet.http.HttpSession;

public class WebSession implements Session {

    private HttpSession session;

    public WebSession(HttpSession session) {
        this.session = session;
    }

    @Override
    public Object get(String key) {
        return session.getAttribute(key);
    }

    @Override
    public boolean set(String key, Object value) {
        boolean result = true;

        Object o = session.getAttribute(key);
        if (o == null || o.equals(value)) {
            result = false;
        }

        session.setAttribute(key, value);

        return result;
    }

    @Override
    public void invalidate() {
        session.invalidate();
    }

    @Override
    public String getSessionId() {
        return session.getId();
    }

}
