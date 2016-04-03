package com.ehage.tms.exception;

public class RestExceptionMessage {

	private String message;
	private String id;
	
	public RestExceptionMessage() {
		super();
	}
	
	public RestExceptionMessage(String message) {
		super();
		this.message = message;
	}
	
	public RestExceptionMessage(String message, String id) {
		super();
		this.message = message;
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
