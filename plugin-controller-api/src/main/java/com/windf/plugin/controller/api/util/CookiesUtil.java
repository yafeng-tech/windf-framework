package com.windf.plugin.controller.api.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookiesUtil {
    /**
     * 获取cookies值
     *
     * @param request
     * @param key
     * @return
     */
    public static String getCookies(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        // 如果用户是第一次访问，那么得到的cookies将是null
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().endsWith(key))
                    return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 设置cookies值
     *
     * @param response
     * @param key
     * @param value
     * @throws IOException
     */
    public static void setCookies(HttpServletResponse response, String key, String value, Integer time) {
        //先清空cookie
        setCookies(response, key, null, 0, false);
        //然后设置cookie
        setCookies(response, key, value, time, false);
    }

    /**
     * 设置cookies值
     *
     * @param response
     * @param key
     * @param value
     * @throws IOException
     */
    public static void setCookies(HttpServletResponse response, String key, String value) {
        setCookies(response, key, value, null);
    }

    /**
     * 设置cookies值
     * 此方法只供内部使用
     *
     * @param response
     * @param key
     * @param value
     * @throws IOException
     */
    private static void setCookies(HttpServletResponse response, String key, String value, Integer time, boolean extra) {
        //设置服务器端以UTF-8编码进行输出
        response.setCharacterEncoding("UTF-8");
        //设置浏览器以UTF-8编码进行接收,解决中文乱码问题
        response.setContentType("text/html;charset=UTF-8");
        //获取浏览器访问访问服务器时传递过来的cookie数组
        //用户访问过之后重新设置用户的访问时间，存储到cookie中，然后发送到客户端浏览器
        Cookie cookie = new Cookie(key, value);
        if (time != null) {
            cookie.setMaxAge(time);
        }
        cookie.setPath("/");
        //将cookie对象添加到response对象中，这样服务器在输出response对象中的内容时就会把cookie也输出到客户端浏览器
        response.addCookie(cookie);
    }
}
