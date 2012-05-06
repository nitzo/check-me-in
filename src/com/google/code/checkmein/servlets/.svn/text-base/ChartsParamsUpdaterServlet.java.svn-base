package com.google.code.checkmein.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.db.Event;

@SuppressWarnings("serial")
public class ChartsParamsUpdaterServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.setContentType("text/plain");
		//resp.addHeader("X-AppEngine-Cron", "true");
		Date now = new Date();
		
		List<Event> events = DatabaseLogic.getNotEndedEvents();
		for(Event event : events){
			Set<String> checkedIn = event.getCheckedIn();
			Integer[] ages = new Integer[4];
			for(String id : checkedIn){
				Customer customer = DatabaseLogic.getCustomer(id);
				String birthday = customer.getBirthday();
				if(birthday != null){
					System.out.println(birthday.substring(6));
					int d = Integer.parseInt(birthday.substring(6));
					//int d1 = now.getYear()
					if(d >= 18 && d <= 22){
						
					}
					else if(d >= 23 && d <= 25){
						
					}
					else if(d >= 26 && d <= 30){
						
					}
					else {
						
					}
					
				}
				
			}
		}
		
		
	}
	
}
