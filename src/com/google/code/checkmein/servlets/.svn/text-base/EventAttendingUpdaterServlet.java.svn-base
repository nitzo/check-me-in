package com.google.code.checkmein.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.code.checkmein.BLogic.FacebookLogic;
import com.google.code.checkmein.BLogic.FacebookLogic.EventRSVPStatus;
import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.db.Event;
import com.google.code.checkmein.db.PMF;
import com.google.code.checkmein.db.QR;
import com.google.code.checkmein.util.Messages;
import com.restfb.FacebookException;
import com.restfb.types.User;


import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@SuppressWarnings("serial")
public class EventAttendingUpdaterServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.setContentType("text/plain");
		resp.addHeader("X-AppEngine-Cron", "true");
		
		
		//TODO: Move to a new servlet
		List<Event> pastEvents = DatabaseLogic.getPastEvents();
		if(pastEvents != null && pastEvents.size() > 0){
			for(Event e : pastEvents){
				resp.getWriter().println("Deleting QRs from event " + e.getName());
				DatabaseLogic.deleteEventQRs(e.getId());
			}
		}
		
		resp.getWriter().println();
		
		List<Event> events = DatabaseLogic.getNotEndedEvents(); 

		if (events != null && events.size() > 0){
			
			//Iterate over all the events and update Attending Info 
			for (Event event : events){	
				
				
				
				resp.getWriter().println("Proccesiong event: " + event.getName());
				resp.getWriter().println();

				
				//Try updating event from facebook
				com.restfb.types.Event eventFromFB = FacebookLogic.getEventDetials(event);
				
				if (eventFromFB != null){
					
					event.updateEvent(eventFromFB);					
					resp.getWriter().println("Event " + event.getName() + " updated id DB");
				}


				
				//Fetch Event rsvp_status from facebook via FQL
				EventRSVPStatus rsvp_status = null;
				
				rsvp_status = FacebookLogic.getEventRSVP(event);
							
				if (rsvp_status == null){
					//TODO: Log error
					continue;
				}
				
				//TODO: Remove this
				print_event_status(resp.getWriter(), rsvp_status);
				
				
				
//				List<User> attendingInFB = FacebookLogic.getEventAttending(event);
//				List<User> notAttendingInFB = FacebookLogic.getEventNotAttending(event);
//				List<User> maybeAttendingInFB = FacebookLogic.getEventMaybeAttending(event);
//				List<User> awaitingReplyInFB = FacebookLogic.getEventAwaitingReply(event);
				

				
				//Get all Users attending in Facebook
				List<User> attendingInFB = rsvp_status.attending;
				List<User> notAttendingInFB = rsvp_status.notAttending;
				List<User> maybeAttendingInFB = rsvp_status.maybe;
				List<User> awaitingReplyInFB = rsvp_status.awaitingReply;
				
				//Set lists in event for statistics
				event.setFBattending(List2Set(attendingInFB));
				event.setNotAttending(List2Set(notAttendingInFB));
				event.setAwaitingReply(List2Set(awaitingReplyInFB));
				event.setMaybeAttending(List2Set(maybeAttendingInFB));
				
				DatabaseLogic.updateEvent(event);
								
				//Fetch all users attending in the DB
				Set<String> attendingInDB = event.getAttendingAndCheckIn();
				
				if (attendingInFB != null && attendingInFB.size() > 0){
					
					//Find new Customers & notify them
					for (User u : attendingInFB){
						resp.getWriter().println(u.getId() + " is attending the event");
						//New Customer (Has not been notified)
						if (!attendingInDB.contains(u.getId())){ 
							
							//Fetch User from DB
							Customer c = DatabaseLogic.getCustomer(u.getId());
							
							if (c != null){
								resp.getWriter().println("Processing user " + c.getFirstName() + " " + c.getLastName());
								//Try notifing user
								if (Messages.sendQRCode(c, event)){
									//QR qr = new QR(event.getId(),c.getId());
									//DatabaseLogic.createQR(qr);
									DatabaseLogic.setAttend(event, c);	
									resp.getWriter().println("Message sent to " + c.getFirstName() + " " + c.getLastName());
								}					
								
							}
							
							else {//Customer does not exist, continue processing (Customer has not installed APP)
								continue;
							}
							
						}
						//Customer has already been notified
						else {
							//TODO: Update customer data from FB?
						}
					}
					
					Set<String> attendingSet = List2Set(attendingInFB);				
					
					//Go over Users attending in DB
					for (String cid : attendingInDB){
						if (!attendingSet.contains(cid)){ //User is no longer attending, delete from event
							Customer c = DatabaseLogic.getCustomer(cid);
							DatabaseLogic.setUnAttend(event, c);
						}
					}
					
				}
			}
			
		}
		
		

	}

	private void print_event_status(PrintWriter writer, EventRSVPStatus rsvp_status) {
		
		if (rsvp_status != null){
			writer.println("Attending: (" + rsvp_status.attending.size() + ")");
			for (User u : rsvp_status.attending){
				writer.println(u.getId());
			}
			writer.println();
			writer.println("Maybe: (" + rsvp_status.maybe.size() + ")");
			for (User u : rsvp_status.maybe){
				writer.println(u.getId());
			}
			writer.println();
			writer.println("Awaiting Reply: (" + rsvp_status.awaitingReply.size() + ")");
			for (User u : rsvp_status.awaitingReply){
				writer.println(u.getId());
			}
			writer.println();
			writer.println("Not Attending: (" + rsvp_status.notAttending.size() + ")");
			for (User u : rsvp_status.notAttending){
				writer.println(u.getId());
			}
			writer.println();
		}
	}

	/**
	 * @param notAttendingInFB
	 * @return
	 */
	private Set<String> List2Set(List<User> notAttendingInFB) {
		Set<String> temp = new HashSet<String>();
		
		for (User u : notAttendingInFB){
			temp.add(u.getId());
		}
		return temp;
	}


	
}
