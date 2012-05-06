package com.google.code.checkmein.BLogic;

import java.util.List;

import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.db.Event;
import com.google.code.checkmein.util.Messages;

import com.restfb.types.User;


public class CustomerServerLogic {
	
	
	/**
	 * Create a new customer in the database or update existing with facebook details
	 * @param User u 
	 * @param String access_token
	 */
	public Customer updateCustomerDetails(User u, String access_token){
		
		Customer c;
		c = DatabaseLogic.getCustomer(u.getId());
		
		if (c == null){ //No Customer exists
			c = new Customer(u, access_token);
			
						
		}
		else{ //Update facebook detials
			c.updatePerson(u, access_token);
			
		}
		
		DatabaseLogic.createCustomer(c);
		return c;
	}
	
	public List<Event> getCustomerEvents(Customer c){
		return DatabaseLogic.getCustomerFutureEvents(c);
	}
	
	/** 
	 * Save customer settings and attempt to send coupon again
	 * @param c
	 * @param eid
	 * @param addtionalEmail
	 * @param phoneNumber
	 * @return
	 */
	public boolean sendCoupon(Customer c, String eid, String addtionalEmail, String phoneNumber){
				
		saveSettings(c, addtionalEmail, phoneNumber);
		
		Event event = null;
		
		if(eid != null){
			 event = DatabaseLogic.getEvent(eid);			
		}
		if (event != null){
			return Messages.sendQRCode(c, event);
		}
		
		return false;
		
	}
	
	/** 
	 * Save customer settings (Email / Phone number)
	 * @param c
	 * @param addtionalEmail
	 * @param phoneNumber
	 */
	public void saveSettings(Customer c, String addtionalEmail, String phoneNumber){
		
		DatabaseLogic.setSettings(c,addtionalEmail,phoneNumber);
	}
	
	

}
