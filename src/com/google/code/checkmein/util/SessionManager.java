package com.google.code.checkmein.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionManager {

	
	private final int SESSION_TIMEOUT = 3600; //Session inactive Timeout in seconds
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession currentSession;
	
	
	
	public SessionManager(HttpServletRequest request, HttpServletResponse response){
		
		this.request = request;
		this.response = response;
		
			
		this.currentSession = request.getSession(true); //Get session, create new session if necessary
		
		if (this.currentSession != null){
			this.currentSession.setMaxInactiveInterval(SESSION_TIMEOUT);
		}
		
		//Add a P3P Header
		//TODO: http://everydayopenslikeaflower.blogspot.com/2009/08/how-to-create-p3p-policy-and-implement.html
		this.response.addHeader("p3p", "CP=\"CAO PSA OUR\"");
		
	}
	
	public void setPRAccessToken(String access_token){
		
		setAttribute("pr_access_token", access_token);
		
	}
	
	
	public String getPRAccessToken(){
		return getAttribute("pr_access_token");
	}
	
	public void setCustomerAccessToken(String access_token){
		
		setAttribute("customer_access_token", access_token);
		
	}
	
	
	public String getCustomerAccessToken(){
		return getAttribute("customer_access_token");
	}
	
	
	private void setAttribute(String attribute, String value){
		if (this.currentSession != null){
			this.currentSession.setAttribute(attribute, value);
		}
	}
	
	private String getAttribute(String attribute){
		
		if (this.currentSession != null){
			return (String) this.currentSession.getAttribute(attribute);
		}
		else
			return null;
		
	}
	
	public HttpServletRequest getRequest(){
		return this.request;
	}
	
	public HttpServletResponse getResponse(){
		return this.response;
	}

	public String getFacebookID() {
		
		return getAttribute("fid");		
		
	}

	public void setFacebookID(String id) {
		
		setAttribute("fid", id);
		
	}
	
	
	
}
