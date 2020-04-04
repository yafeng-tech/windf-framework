package com.windf.core.exception;

public class ParameterException extends UserException{
	public ParameterException() {
	}

	public ParameterException(String message) {
		super(message);
	}

	public ParameterException(String type, String message) {
		super(type, message);
	}

	public ParameterException(Throwable cause) {
		super(cause);
	}

	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameterException(String type, String message, Throwable cause) {
		super(type, message, cause);
	}
}
