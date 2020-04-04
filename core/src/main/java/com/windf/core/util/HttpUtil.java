package com.windf.core.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String cookies) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = StringUtil.isNotBlank(param) ? url + "?" + param : url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if(StringUtil.isNotBlank(cookies)){
                connection.setRequestProperty("Cookie", cookies);
            }
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally { // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String param, String cookies) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            if(StringUtil.isNotBlank(cookies)){
                conn.setRequestProperty("Cookie", cookies);
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {  // 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 对ip地址进行简化处理
     *
     * @param strIp
     * @return
     */
    public static long ipToLong(String strIp) {
        String[] iptemp = strIp.split("\\.");
        if (iptemp.length == 4) {
            long[] ip = new long[4];
            ip[0] = Long.parseLong(iptemp[0]);
            ip[1] = Long.parseLong(iptemp[1]);
            ip[2] = Long.parseLong(iptemp[2]);
            ip[3] = Long.parseLong(iptemp[3]);
            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        } else {
            return 0;
        }
    }
    
    /**
     * map转换成表单形式（a=1&b=3）
     * @param map
     * @param isURLEncoder 是否进行url编码
     * @return
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static String mapToFormData(Map map, boolean isURLEncoder) {
    	String result = null;
    	
    	StringBuffer formData = new StringBuffer();
        if (CollectionUtil.isNotEmpty(map)) {
        	Iterator<String> iterator = map.keySet().iterator();
        	while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = String.valueOf(map.get(key));
				
				if (StringUtil.isEmpty(key)) {
					continue;
				}
				
				if (formData.length() > 0) {
					formData.append("&");
				}
				formData.append(key + "=" + StringUtil.fixNull(value));
			}
        	
        	result = formData.toString();
            if (isURLEncoder) {
            	try {
					result = URLEncoder.encode(result, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            }
        }
        
        return result;
    }

    /**
     * 获取 完成的访问路径
     * 获得完整的url，如果没有http添加http，如果没有域名，添加域名
     * @param url 如果url中包含domain以url中为准
     * @param domain 如果url中不包含域名，domain中包含http或者https，以domain为准
     * @param isHttps 如果没有协议，是用https?还是用http
     * @return
     */
    public static String getFullUrl(String url, String domain, boolean isHttps) {
        String result = "";

        // 如果url以http开头，则直接返回
        if (url.startsWith("http")) {
            return url;
        }

        // 设置协议
        String protocol = isHttps? "https://": "http://";

        // 如果url以域名开头，返回协议+url
        String urlStart = url.split("//")[0];
        if (urlStart.indexOf(".") > 0) {    // 如果域名中第一部分（/之前），包含.,则认定为域名开头
            return protocol + url;
        }

        // 判断domain中是否有协议
        if (!domain.startsWith("http")) {
            result = protocol + domain;
        } else {
            result += domain;
        }

        // 如果url开头不是/则添加 /
        if (!url.startsWith("/")) {
            result += "/";
        }

        return result + url;

    }
}
