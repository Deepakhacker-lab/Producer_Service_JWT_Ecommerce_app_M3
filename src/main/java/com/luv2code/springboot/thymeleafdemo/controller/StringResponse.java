package com.luv2code.springboot.thymeleafdemo.controller;

public class StringResponse {

	
    private String response;

	
    public StringResponse(String response) { 
       this.response = response;
    }
	
	public String getResponse() {
		return response;
	}

	
	public void setResponse(String response) {
		this.response = response;
	}

	public StringResponse() {
		
	}

   
}