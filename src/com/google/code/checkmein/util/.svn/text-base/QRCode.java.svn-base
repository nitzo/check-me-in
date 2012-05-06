package com.google.code.checkmein.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.Event;


 

/**
 * this class contains function that handle QR codes
 */
public class QRCode {
	
	/**
	 * parse a URL to embed inside the message sent to the person
	 * @param person the person how gets the invitations
	 * @param eventID the ID of the specified event
	 * @return URL for the QRCodeGeneratorServlet
	 */
	public static String QRCodeUrlParser(String facebookID, String eventID){
		
		String servletUrl = System.getProperty("checkmein.baseurl") + "/" + System.getProperty("checkmein.checkinservlet") + "?";
		String idUrl = "fid=" + facebookID;
		String eventIDUrl = "eid=" + eventID;
		try {
			return URLEncoder.encode(servletUrl + idUrl + "&" + eventIDUrl,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		
	}
	
	public static String generateQRCodeURL(String facebookID, String eventID){
		
		String servletUrl = System.getProperty("checkmein.baseurl") + "/" + System.getProperty("checkmein.qrgeneratorservlet") + "?";		
		String idUrl = "fid=" + facebookID;
		String eventIDUrl = "eid=" + eventID;
		
		return servletUrl + idUrl + "&" + eventIDUrl;
	}
}
