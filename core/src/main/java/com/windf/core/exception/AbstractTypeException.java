package com.windf.core.exception;

import java.io.Serializable;

/**
 * 抽象的 类型异常，可以携带类型参数，用于区分不同类型
 */
public abstract class AbstractTypeException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;

	public AbstractTypeException() {
	}

	public AbstractTypeException(String message) {
		super(message);
	}
	
	public AbstractTypeException(String type, String message) {
		super(message);
		this.type = type;
	}

	public AbstractTypeException(Throwable cause) {
		super(cause);
	}

	public AbstractTypeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AbstractTypeException(String type, String message, Throwable cause) {
		super(message, cause);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
