package com.windf.core.exception;

/**
 * 数据访问异常
 * 一般是数据库、文件等操作发生异常时抛出
 */
public class DataAccessException extends CodeException {

	public DataAccessException() {
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(String type, String message) {
		super(type, message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(String type, String message, Throwable cause) {
		super(type, message, cause);
	}
}
