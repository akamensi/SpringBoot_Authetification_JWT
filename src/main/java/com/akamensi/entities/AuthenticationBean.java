package com.akamensi.entities;

public class AuthenticationBean {
	
	private String message;

	public AuthenticationBean() {
		super();
	}

	public AuthenticationBean(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    @Override
    public String toString() {
        return String.format("Bienvenue dans backend [message=%s]", message);
    }
	
	

}
