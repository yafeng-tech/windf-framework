package com.windf.core.entity;

import java.io.Serializable;

/**
 * 返回值，用于接口返回的json格式
 */
// TODO 要删除泛型
public class ResultData implements Serializable {

    /**
     * 正常的返回结果code
     */
    public static final String CODE_SUCCESS = "200";
    /**
     * 地址重定向的code
     */
    public static final String CODE_REDIRECT = "302";
    /**
     * 参数错误的code
     */
    public static final String CODE_BAD_REQUEST = "400";
    /**
     * 找不到接口异常
     */
    public static final String CODE_NOT_FOUND = "404";
    /**
     * 通用的错误异常
     */
    public static final String CODE_NORMAL_ERROR = "500";

    /**
     * 参数正确的默认message
     */
    public static final String MESSAGE_SUCCESS = "success";
    /**
     * 参数错误的默认message
     */
    public static final String MESSAGE_ERROR = "error";

    private String code; // 0: 失败，1：成功
    private String message; //提示信息
    private Object data;// 结果数据

    public ResultData() {

    }

    public ResultData(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
