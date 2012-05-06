package com.google.code.checkmein.BLogic;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.datanucleus.util.StringUtils;

import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.db.Event;
import com.google.code.checkmein.db.PR;
import com.restfb.types.User;

public class PRServerLogic {
	
	/** Members **/
	
	private PR pr;
	private List<Event> futurePromotedEvents;
	private List<Event> pastPromotedEventList;
	private List<Event> promotedEvents;
	
	
	
	private List<Event> futureAddtionalEvents;
	private List<Event> pastAdditionalEvents;
	private List<Event> addtionalEvents;
	
	private List<com.restfb.types.Event> unPromotedEvents;
	private HashMap<String, com.restfb.types.Event> unPromotedEventsMap;
	
	//private List<String> updates;

	
	/** Constructor **/
	
	public PRServerLogic(User user, String access_token) {

		this.pr = updatePRDetails(user, access_token);
			
		initEventLists();
		//InitializePastPromotedEvents();
		//InitializeUpdates();
		
	}

	/** Utility methods **/
	
	/**
	 * Initializing the unpromopted event list from facebook
	 */
	@SuppressWarnings("deprecation")
	private void initEventLists() {

		this.promotedEvents = DatabaseLogic.getPromotedEvents(pr.getId());
		this.pastPromotedEventList = new LinkedList<Event>();
		this.futurePromotedEvents = new LinkedList<Event>();
		this.unPromotedEventsMap = new HashMap<String, com.restfb.types.Event>();
		this.addtionalEvents = DatabaseLogic.getAddtionalEvents(pr);
		this.futureAddtionalEvents = new LinkedList<Event>();
		this.pastAdditionalEvents = new LinkedList<Event>();
		
		List<com.restfb.types.Event> fbEvents = FacebookLogic.getUserEvents(pr);
		
		if (promotedEvents == null){ //No promoted events
			
			this.unPromotedEvents = fbEvents;
			
			for (com.restfb.types.Event e : fbEvents){ //Add all events to MAp
				this.unPromotedEventsMap.put(e.getId(), e);
			}
			
			this.promotedEvents = new LinkedList<Event>();
		}
		else{ //Filter promoted events
			Set<String> dbEventIds;
			dbEventIds = new HashSet<String>();
			for (Event e : promotedEvents){
				dbEventIds.add(e.getId());				
			}
			
			this.unPromotedEvents = new LinkedList<com.restfb.types.Event>();
			
			for (com.restfb.types.Event e : fbEvents){
				if (!dbEventIds.contains(e.getId())){
					this.unPromotedEvents.add(e);
					this.unPromotedEventsMap.put(e.getId(), e);
				}				
			}
			
			Date now = new Date();
			now.setHours(now.getHours()+DatabaseLogic.GMT);
			
			//Filter past / future events
			for(Event e : promotedEvents){
				if(e.getEndTime().after(now)){
					futurePromotedEvents.add(e);
				}
				if(e.getEndTime().before(now)){
					pastPromotedEventList.add(e);
				}
			}
			
			//Filter past / future addtional events
			for(Event e : addtionalEvents){
				if(e.getEndTime().after(now)){
					futureAddtionalEvents.add(e);
				}
				if(e.getEndTime().before(now)){
					pastAdditionalEvents.add(e);
				}
			}
			
						
			
		}
	}
	
	/**
	 * extract update from promoted event
	 */
	/*private void InitializeUpdates(){
		updates = new LinkedList<String>();
		//TODO: extract this implementation from the getUpdates methods
	}*/
	
	
	/** Getter **/
	
	public PR getPR(){
		return this.pr;
	}
	
	/**
	 * get all the current PR promoted events
	 * @return List of all the promoted events
	 */
	public List<Event> getPromotedEvents() {
		return promotedEvents;
	}

	/**
	 * get all the current PR Unpromoted events
	 * @return List of all the promoted events
	 */
	public List<com.restfb.types.Event> getUnPromotedEvents() {
		return unPromotedEvents;
	}
	
	/**
	 * get all the current PR past promoted events
	 * @return List of all the past promoted events
	 */
	public List<Event> getPastPromotedEvent(){
		return pastPromotedEventList;
	}

	
	public List<Event> getFuturePromotedEvents() {
		return futurePromotedEvents;
	}
	
	public List<Event> getAddtionalFutureEvents(){
		return futureAddtionalEvents;
	}
	
	public List<Event> getAddtionalPastEvents(){
		return pastAdditionalEvents;
	}

	/**
	 * get the latest update from all the current PR events
	 * @return list of the updates
	 */
	@SuppressWarnings("deprecation")
	public List<String> getUpdates(){
		List<String> beforStartUpdates = new LinkedList<String>();
		List<String> afterEndUpdates = new LinkedList<String>();
		Date now = new Date();
		now.setHours(now.getHours()+DatabaseLogic.GMT);
		
		if(futurePromotedEvents.size() > 0){
			for(Event e: futurePromotedEvents){
				//if(e.getStartTime().after(now)){
					String temp = DatabaseLogic.getUpdates(e.getId());
					if(!temp.equals("noUpdates")){
						beforStartUpdates.add(e.getName()+ " : <br>" + temp);
						//beforStartUpdates.add(temp);
					}
				//}
			}
		}
		
		for(Event e : pastPromotedEventList){
			Date eventEnd = e.getEndTime();
			if(now.getYear() == eventEnd.getYear() && now.getMonth() == eventEnd.getMonth() && now.getDate() == eventEnd.getDate()){
				int checkedIn= e.getCheckedIn().size();
				if(checkedIn >= Integer.parseInt(System.getProperty("checkmein.event.Congratulations"))){
					afterEndUpdates.add("Congratulations on " + checkedIn + " \"Checked-In \" on " + "\" "+e.getName()+ " \" !");
				}
			}
		}
		
		beforStartUpdates.addAll(afterEndUpdates);
		if(beforStartUpdates.size() == 0){
			beforStartUpdates.add("No new updates");
		}
		return beforStartUpdates;
	}
		
	/**
	 * Promote the event with id ID
	 * If event already promoted just return event object
	 * @param the event to promote
	 */
	public Event promoteEvent(String eventid){
		
		com.restfb.types.Event event = this.unPromotedEventsMap.get(eventid);
		
		if (event == null){
			return null;
		}		
		else{		
			return DatabaseLogic.promoteEvent(event, this.pr);			
		}

	}
	
	private PR updatePRDetails(User user, String access_token){
		
		PR pr;
		
		pr = DatabaseLogic.getPR(user.getId());
		
		
		if (pr == null){ //No PR exists
			pr = new PR(user, access_token);
			
			
		}
		else{ //Update facebook detials
			pr.updatePerson(user, access_token);
		}	
		DatabaseLogic.createPR(pr);			
		return pr;		
	}
	


	
	public boolean setPermittedScanners(String[] prs, String eid){
		
		Event event = getEvent(eid);
		Set<String> prSet = new HashSet<String>();
		
		if (event == null){
			return false;
		}
		
		if (prs != null){
			for (String id : prs){
				prSet.add(id);
			}
		}
		
		//event.clearPermmitedScanners();
		
		//Remove PRs from event
		for (String id : event.getPermittedScanners()){
			
			if (!prSet.contains(id)){				
				PR pr = DatabaseLogic.getPR(id);				
				if (pr != null){
					pr.removeAditionalEvent(event.getId());
					DatabaseLogic.createPR(pr);
				}				
				event.removePermmitedScanner(id);			
			}
		}
		
				
		//Add new PRs to event			
		for (String id : prSet){
			
			PR pr = DatabaseLogic.getPR(id);				
			if (pr != null){
				pr.addAditionalEvent(event.getId());
				DatabaseLogic.createPR(pr);
			}				
			event.addPermittedScanner(id);
		
		}
		
		DatabaseLogic.updateEvent(event);
		
		return true;
	}
	
	
	/************ Static methods ********/
	public static List<PR> getAllPRs(){
		return DatabaseLogic.getAllPRs();
	}
	
	
	public static Event getEvent(String eventID){
		return	DatabaseLogic.getEvent(eventID);
	}
}

