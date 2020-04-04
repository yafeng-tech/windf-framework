package com.windf.core.exception;

/**
 * 普通的用户异常
 * 非系统内部错误，是用户操作不当引起的
 * 用于抛出给客户查看的，里面有用户可以看得懂的信息
 */
public class UserException extends AbstractTypeException {
    private String type;

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String type, String message) {
        super(message);
        this.type = type;
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(String type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
