package com.google.code.checkmein.BLogic;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.db.Event;
import com.google.code.checkmein.db.PR;
import com.google.code.checkmein.db.Person;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.FacebookJsonMappingException;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;


public class FacebookLogic {
	
	
	public static class EventRSVPStatus{
		
		@Facebook(contains = User.class)
		public List<User> attending;
		
		@Facebook(contains = User.class)
		public List<User> maybe;
		
		@Facebook(contains = User.class)
		public List<User> notAttending;
		
		@Facebook(contains = User.class)
		public List<User> awaitingReply;
		
	}
	
	
	public static EventRSVPStatus getEventRSVP(Event event) {
		PR owner = DatabaseLogic.getPR(event.getOwnerID());
		
		if (owner == null){ //Owner does not exist
			return null;
		}
		
		FacebookClient fbclient = new DefaultFacebookClient(owner.getAccessToken());
				
		//Build queries
		String attending_q = "select uid from event_member where eid = " + event.getId() + " and rsvp_status = 'attending'";
		String maybe_q = "select uid from event_member where eid = " + event.getId() + " and rsvp_status = 'unsure'";
		String notAttending_q = "select uid from event_member where eid = " + event.getId() + " and rsvp_status = 'declined'";
		String awaitingReply_q = "select uid from event_member where eid = " + event.getId() + " and rsvp_status = 'not_replied'";
		
		//Add queries to map
		Map<String, String> queries = new HashMap<String, String>();
		queries.put("attending" ,attending_q);
		queries.put("maybe" ,maybe_q);
		queries.put("notAttending" ,notAttending_q);
		queries.put("awaitingReply" ,awaitingReply_q);
		
		EventRSVPStatus results = null;
		try {
			results = fbclient.executeMultiquery(queries, EventRSVPStatus.class);
		} catch (FacebookException e) {
			//TODO: Log error
			return null;
		}
		
		return results;
		
	}
	

	
	public static com.restfb.types.Event getEventDetials(Event event){
		
		PR owner = DatabaseLogic.getPR(event.getOwnerID());
		
		if (owner == null){ //Owner does not exist
			return null;
		}
		
		FacebookClient fbclient = new DefaultFacebookClient(owner.getAccessToken());
			
		List <com.restfb.types.Event> events = null;
		try {			
			
			//Build query to get user events
			String fql = "SELECT eid,name,location,description,pic_small,start_time,end_time,venue,creator from event where eid = " + event.getId();
			events = fbclient.executeQuery(fql, com.restfb.types.Event.class);			
		} catch (FacebookException e) {
			//TODO: Log error
			return null;
		}
		
		if (events != null && events.size() > 0){
			return events.get(0);
		}
		else
			return null;
		
		
	}
	
	
	public static List<com.restfb.types.Event>  getUserEvents(Person person) {
		
		FacebookClient fbclient = new DefaultFacebookClient(person.getAccessToken());
		
		List<com.restfb.types.Event> futureEvents = new LinkedList<com.restfb.types.Event>();
		List<com.restfb.types.Event> events;
		
		try {			
			
			
			//Build query to get user events
			String fql = "SELECT eid,name,location,description,pic_small,start_time,end_time,venue,creator from event where eid in (SELECT eid from event_member where uid = me() and rsvp_status='attending')";
			events = fbclient.executeQuery(fql, com.restfb.types.Event.class);
			
			

			
		} catch (FacebookException e) {
			return new LinkedList<com.restfb.types.Event>(); //TODO: Log error
		}
		
		//Filter to return only future events and events user is creator
		for (com.restfb.types.Event e : events){
			if (e.getStartTime().after(new Date()) && e.getCreator().equals(person.getId())){
				futureEvents.add(e);
			}				
		}
		
		
		return futureEvents;
	}
	
	
	public static List<String> getUserFriends(Person p){
		
		FacebookClient fbclient = new DefaultFacebookClient(p.getAccessToken());
		List<String> friends;
		
		String fql = "select uid2 from friend where uid1 = " + p.getId();
		
		try{
			friends = fbclient.executeQuery(fql, String.class);
		}
		catch (FacebookException e){
			return null;
		}
		
		return friends;		
		
		
	}
	
	public static void publishFeed(Person person, String message, String link, String linkname){
		
		
		FacebookClient fbclient = new DefaultFacebookClient(person.getAccessToken());
		
		
		
		try {
			fbclient.publish("me/feed", FacebookType.class, Parameter.with("message", message),
															Parameter.with("link", link),
															Parameter.with("name", linkname));
		} catch (FacebookJsonMappingException e) {
			// TODO Auto-generated catch block
			
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			
		}		
		
	}

}
