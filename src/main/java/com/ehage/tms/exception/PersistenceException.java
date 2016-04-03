package com.ehage.tms.exception;

public class PersistenceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	public PersistenceException() {
		super();
	}
	
	public PersistenceException(String message) {
		super(message);
	}
	
	public PersistenceException(String message, String id) {
		super(message);
		this.id = id;
	}	
	
	public PersistenceException(String message, Throwable th) {
		super(message, th);
	}
	
	public PersistenceException(String message, Throwable th, String id) {
		super(message, th);
		this.id = id;
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
}
