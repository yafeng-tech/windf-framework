package com.windf.core.exception;

/**
 * 编码阶段的异常
 * 如果发现此类异常，需要联系管理员
 */
public class CodeException extends AbstractTypeException {

	private String type;

	public CodeException() {
	}

	public CodeException(String message) {
		super(message);
	}

	public CodeException(String type, String message) {
	}

	public CodeException(Throwable cause) {
		super(cause);
	}

	public CodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodeException(String type, String message, Throwable cause) {
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
