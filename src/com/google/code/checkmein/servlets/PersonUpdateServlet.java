package com.google.code.checkmein.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.checkmein.BLogic.FacebookLogic;
import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.db.Event;
import com.google.code.checkmein.db.PR;
import com.google.code.checkmein.util.Messages;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookException;
import com.restfb.types.User;

@SuppressWarnings("serial")
public class PersonUpdateServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		resp.setContentType("text/plain");
		resp.addHeader("X-AppEngine-Cron", "true");
		
		List<Customer> customers = DatabaseLogic.getAllCustomers();
		List<PR> prs = DatabaseLogic.getAllPRs();
		
		DefaultFacebookClient fbclient;
		User u;
		
		for (Customer c : customers){
			
			fbclient = new DefaultFacebookClient(c.getAccessToken());
			
			u = null;
			
			
			//Update user data from FB
			try {
				u = fbclient.fetchObject("me", User.class);
			} catch (FacebookException e) {
				
			}
			
			if (u != null){				
				c.updatePerson(u, c.getAccessToken());
			
			}
			else{
				resp.getWriter().println("Error updating customer " + c.getFirstName() + " " + c.getLastName() + " (" + c.getId() + ")");
			}
			
			//Update user friends from FB
			List<String> friend_list = FacebookLogic.getUserFriends(c);
			Set<String> friends = new HashSet<String>();
			
			if (friend_list != null){
				
				for (String s : friend_list){
					friends.add(s);
					
				}
				
			}			
			
			c.setFriends(friends);
			
			
			//Save customer
			DatabaseLogic.createCustomer(c);
			
			
		}
		
		
			

	}
	
}
