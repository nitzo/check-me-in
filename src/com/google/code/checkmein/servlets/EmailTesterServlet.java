package com.google.code.checkmein.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.util.Messages;
import com.google.code.checkmein.db.Event;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class EmailTesterServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
 	throws IOException {
		
		
		Customer customer = DatabaseLogic.getCustomer("788734623");
		Event event = DatabaseLogic.getEvent("186628021350337");
		
		if (customer == null){
			response.getWriter().println("Error getting customer");
		}
		
		if (event == null){
			response.getWriter().println("Error getting event");
		}
		
		
		response.getWriter().println(Messages.sendQRCode(customer, event));
		
		
			
			
			
	}
		
}


