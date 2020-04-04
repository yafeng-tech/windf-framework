package com.windf.plugin.controller.api.controller;

import com.windf.core.Constant;
import com.windf.core.exception.CodeException;
import com.windf.core.session.Session;
import com.windf.core.util.DateUtil;
import com.windf.core.util.FileUtil;
import com.windf.core.util.StringUtil;
import com.windf.plugin.controller.api.response.JsonResponseReturn;
import com.windf.plugin.controller.api.response.ResponseReturn;
import com.windf.plugin.controller.api.session.WebSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 控制层的父类，用于提供api，基于spring MVC
 */
public abstract class BaseController {
    /**
     * 基础路径，所有api都需要以这个开头
     */
    public static final String BASE_API_PATH = "/api";

    /**
     * 文件上传的路径
     */
    private static final String INCOMING_PATH = "/incoming";

    /**
     * 获取request
     * @return
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取response
     * @return
     */
    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取ResponseReturn {@link ResponseReturn}
     * 用于返回数据
     * @return
     */
    protected ResponseReturn response() {
        return new JsonResponseReturn();
    }

    /**
     * 获取session
     * @return
     */
    protected Session getSession() {
        return new WebSession(this.getRequest().getSession());
    }

    /**
     * 获取文件
     * @param multipartFile
     * @return
     */
    protected File saveFile(MultipartFile multipartFile, String type) {
        String ext = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        File file = this.createIncomingFile(null, null, type, ext);

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new CodeException(e);
        }

        return file;
    }

    /**
     * 创建一个用于上传的文件
     * @param siteCode
     * @param moduleCode
     * @param type
     * @param ext
     * @return
     */
    protected File createIncomingFile(String siteCode, String moduleCode, String type, String ext) {

        // 生成上传目录
        // eg：/webapp/product/incoming/{siteCode}/{module}/[{type}/]/2010/01/14/195609
        StringBuffer saveFileName = new StringBuffer();
        saveFileName.append(FileUtil.getWebappPath())   // 服务器路径
                .append(INCOMING_PATH).append("/")  // incoming文件
                .append(siteCode).append("/")       // 站点编号
                .append(moduleCode).append("/");    // 模块code
        if (StringUtil.isNotEmpty(type)) {
            saveFileName.append(type).append("/");  // 可能会指定类型
        }
        saveFileName.append(DateUtil.format("yyyy/MM/dd/hhmmss"))   // 日期部分
                .append(StringUtil.getRandomString(4))
                .append(".").append(ext);

        File file = new File(saveFileName.toString());

        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    /**
     * 文件下载
     * @param file
     */
    protected void download(File file, String fileName) {
        HttpServletRequest request = this.getRequest();
        HttpServletResponse response = this.getResponse();

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding(Constant.DEFAULT_ENCODING);

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            long fileLength = file.length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(Constant.DEFAULT_ENCODING), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }
}
