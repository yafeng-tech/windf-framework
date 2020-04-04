package com.windf.plugin.controller.api.session;

import com.windf.core.session.Session;
import com.windf.core.session.SessionContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Session session = new WebSession(request.getSession());
        // 开始session
        SessionContext.start(session);

        filterChain.doFilter(servletRequest, servletResponse);

        // 关闭session
        SessionContext.end(session);

    }
}
