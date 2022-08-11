package com.study.securty_jiseok.handler.Exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class CustomvalidationApiException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private Map<String, String> errorMap;
	
	public CustomvalidationApiException() {
		this("error", new HashMap<String, String>());
	}
	
	public CustomvalidationApiException(String message) {
		this(message, new HashMap<String, String>());
	}
	
	public CustomvalidationApiException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}
	
}
