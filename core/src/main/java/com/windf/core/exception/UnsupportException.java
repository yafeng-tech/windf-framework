package com.windf.core.exception;

/**
 * 不支持 异常
 * 用于方法没有实现，或者不支持此类操作
 */
public class UnsupportException extends UserException {
    public UnsupportException() {
    }

    public UnsupportException(String message) {
        super(message);
    }

    public UnsupportException(String type, String message) {
        super(type, message);
    }

    public UnsupportException(Throwable cause) {
        super(cause);
    }

    public UnsupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportException(String type, String message, Throwable cause) {
        super(type, message, cause);
    }
}
