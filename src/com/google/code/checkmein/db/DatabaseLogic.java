package com.google.code.checkmein.db;

import java.util.Date;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;


public class DatabaseLogic {

	public static final int FACEBOOK_TIME = -8;
	public static final int GMT = 2;
	
	/**
	 * getEvent
	 * @return Event whose EventID equals id. 
	 */
	public static Event getEvent(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//List<Event> events = new LinkedList<Event>();
		Event e = null;
		String query= "select from " + Event.class.getName() + " where id == '" + id +"'";
		try { 
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) pm.newQuery(query).execute();
			if(events.size() > 0){
				e = events.get(0);
			}
		}
		finally{
			pm.close();
		}
		return e;
	}
	
	/**
	 * getCustomer
	 * @return Customer whose id equals a given id. 
	 */
	public static Customer getCustomer(String id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//List<Customer> customer = new LinkedList<Customer>();
		Customer c = null;
		String query= "select from " + Customer.class.getName() + " where id == '" + id +"'";
		try{
			@SuppressWarnings("unchecked")
			List<Customer> customer = (List<Customer>) pm.newQuery(query).execute();
			if (customer.size() > 0){
				c = customer.get(0);
			}
		}
		finally{
			pm.close();
		}
		return c;
	}
	
	/**
	 * getOwner
	 * @return PR whose id equals a given id.
	 */
	
	public static PR getPR(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//List<PR> prs =  new LinkedList<PR>();
		PR pr = null;
		String query= "select from " + PR.class.getName() + " where id == '" + id +"'";
		try{
			@SuppressWarnings("unchecked")
			List<PR> prs = (List<PR>) pm.newQuery(query).execute();
			if (prs.size() > 0){
				pr = prs.get(0);
			}
		}
		finally{
			pm.close();
		}
		return pr;
	}
	
	public static QR getQRbyCode(String id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		QR q = null;
		String query= "select from " + QR.class.getName() + " where code == '" + id +"'";
		try{
			@SuppressWarnings("unchecked")
			List<QR> customer = (List<QR>) pm.newQuery(query).execute();
			if (customer.size() > 0){
				q = customer.get(0);
			}
		}
		finally{
			pm.close();
		}
		return q;
	}
	
	public static QR getQRbyURL(String url) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		QR q = null;
		String query= "select from " + QR.class.getName() + " where url == '" + url +"'";
		try{
			@SuppressWarnings("unchecked")
			List<QR> customer = (List<QR>) pm.newQuery(query).execute();
			if (customer.size() > 0){
				q = customer.get(0);
			}
		}
		finally{
			pm.close();
		}
		return q;
	}
	
	/**
	 * getNotStartedEvents 
	 * @return List<Event> notStarted - a linked list of events not started right now 
	 * if no event has started yet - returns null
	 */
	@SuppressWarnings("deprecation")
	public static List<Event> getNotEndedEvents(){ //TODO: Optimize query
		List<Event> notStarted = new LinkedList<Event>();;
		Date now = new Date();
		now.setHours(now.getHours()+ GMT);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + Event.class.getName() ;
		try{
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) pm.newQuery(query).execute();
			if(events.size() > 0){
				//notStarted = new LinkedList<Event>();
				for(Event e: events){
					if(e.getEndTime().after(now)){
						notStarted.add(e);
					}
				}
			}
		}
		finally {
			pm.close();
		}
		
		return notStarted;
	}
	
	@SuppressWarnings("deprecation")
	public static List<Event> getPastEvents(){ 
		List<Event> ended = new LinkedList<Event>();
		Date now = new Date();
		now.setHours(now.getHours()+ GMT);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + Event.class.getName() ;
		try{
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) pm.newQuery(query).execute();
			if(events.size() > 0){
				//ended = new LinkedList<Event>();
				for(Event e: events){
					if(e.getEndTime().before(now)){
						ended.add(e);
					}
				}
			}
		}
		finally {
			pm.close();
		}
		
		return ended;
	}
	
	public static List<Event> getPromotedEvents(String ownerID){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + Event.class.getName() + " where ownerID == '" + ownerID + "'";
		List<Event> events = new LinkedList<Event>();
		try{
			@SuppressWarnings("unchecked")
			List<Event> eventsDB = (List<Event>) pm.newQuery(query).execute();
			//Copy events to a new list becuase of DB errors
			//TODO: Fix this
			if(eventsDB.size() > 0){
				//events = new LinkedList<Event>();
				events.addAll(eventsDB);
			}
		}
		finally{
			pm.close();
		}
		
		return events;
	}
	
	public static List<Event> getAllEvents(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + Event.class.getName();
		
		List<Event> events = new LinkedList<Event>();
		try{
			@SuppressWarnings("unchecked")
			List<Event> eventsDB = (List<Event>) pm.newQuery(query).execute();
			//Copy events to a new list becuase of DB errors
			//TODO: Fix this
			if(eventsDB.size() > 0){
				//events = new LinkedList<Event>();
				events.addAll(eventsDB);
			}
		}
		finally{
			pm.close();
		}
		
		return events;
	}
	
	
	public static List<PR> getAllPRs(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + PR.class.getName();
		List<PR> prs = new LinkedList<PR>();
		try{
			@SuppressWarnings("unchecked")
			List<PR> prsDB = (List<PR>) pm.newQuery(query).execute();
			//Copy events to a new list becuase of DB errors
			//TODO: Fix this
			if(prsDB.size() > 0){
				//events = new LinkedList<Event>();
				prs.addAll(prsDB);
			}
		}
		finally{
			pm.close();
		}
		
		return prs;
	}
	
	public static List<Customer> getAllCustomers(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + Customer.class.getName();
		List<Customer> customers = new LinkedList<Customer>();
		try{
			@SuppressWarnings("unchecked")
			List<Customer> customersDB = (List<Customer>) pm.newQuery(query).execute();
			//Copy events to a new list becuase of DB errors
			//TODO: Fix this
			if(customersDB.size() > 0){
				//events = new LinkedList<Event>();
				customers.addAll(customersDB);
			}
		}
		finally{
			pm.close();
		}
		
		return customers;
	}
	
	
	/**
	 * getPromotedEvents(String ownerID) returns a list of promoted events of a PR with the id = ownerID
	 * @return List<Event> events - list of promoted events from facebook
	 */
	//@SuppressWarnings("deprecation")
	/*public static List<Event> getNotStartedPromotedEvents(String ownerID){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + Event.class.getName() + " where ownerID == '" + ownerID + "'";
		List<Event> events = null;
		Date now = new Date();
		now.setHours(now.getHours()+ GMT);
		try{
			@SuppressWarnings("unchecked")
			List<Event> eventsDB = (List<Event>) pm.newQuery(query).execute();
			//Copy events to a new list becuase of DB errors
			//TODO: Fix this
			if(eventsDB.size() > 0){
				events = new LinkedList<Event>();
				//events.addAll(eventsDB);
				for (Event e : eventsDB){
					if(e.getStartTime().after(now)){
						events.add(e);
					}
				}
			}
		}
		finally{
			pm.close();
		}
		
		return events;
	}*/
	
	//TODO: Handle Exception??
	public static Event promoteEvent(com.restfb.types.Event event, PR owner){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		Event promotedEvent = new Event(event);
				
		promotedEvent.setOwnerID(owner.getId());
		owner.addEvent(promotedEvent.getId());
		
		try{
			pm.makePersistent(promotedEvent);
			pm.makePersistent(owner);
		}
		finally{
			pm.close();
		}
		
		return promotedEvent;
		
	}
	
	//TODO: Handle Exception??
	private static void saveClass(Object T){ 
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(T);	
		}
		finally{
			pm.close();
		}
	}

	public static void createCustomer(Customer customer) {
		saveClass(customer);
	}

	public static void createPR(PR pr) {
		saveClass(pr);		
	} 
	
	public static void createQR(QR qr){
		saveClass(qr);
	}
	
	//TODO: Handle Exception??
	public static void setAttend(Event event, Customer c) {
				
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		event.addAttending(c.getId());
		c.confirmEvent(event.getId());
			
		try {
			pm.makePersistent(event);
			pm.makePersistent(c);
		}
		finally{
			pm.close();
		}
	}

	//TODO: Handle Exception??
	public static void setUnAttend(Event event, Customer c) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		event.removeAttending(c.getId());
		c.removeEvent(event.getId());

		try {
			pm.makePersistent(event);
			pm.makePersistent(c);
		}
		finally{
			pm.close();
		}
	}

	//TODO: Handle Exception??
	public static void setCheckedIn(Event event, Customer customer) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
			
		customer.attendEvent(event.getId());
		customer.removeEvent(event.getId());
		event.addCheckedIn(customer.getId(),customer.getGender(),customer.getBirthday(),customer.getAVGattendingRate());
		event.removeAttending(customer.getId());
		
		try {
			pm.makePersistent(event);
			pm.makePersistent(customer);
		}
		finally {
			pm.close();
		}
	}
	
	public static void setSettings(Customer customer, String other_email, String phone){
		
				
		//Set Addtional email.Do not set if it equals Facebook email
		if (other_email != null && !other_email.equals("") && !other_email.equals(customer.getEmail())){
			customer.setOther_email(other_email);
		}
		else {
			customer.setOther_email(null);
		}
		
		if (phone != null && !phone.equals("")){
			customer.setCell_phone(phone);
		}
		else{
			customer.setCell_phone(null);
		}
		
		
		
		createCustomer(customer);
	}

	@SuppressWarnings("deprecation")
	public static List<Event> getCustomerFutureEvents(Customer c) {
		
		List<Event> events = new LinkedList<Event>();
		Set<String> customerEvents = c.getVirtualAtendingEvents();
		if(customerEvents.size() == 0){
			return events;
		}
		//Event e = null;
		Date now = new Date();
		now.setHours(now.getHours()+ GMT);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			for (String eid : customerEvents){	
				
				String query= "select from " + Event.class.getName() + " where id == '" + eid + "'";
				@SuppressWarnings("unchecked")
				List<Event> eventsDB = (List<Event>) pm.newQuery(query).execute();
				if(eventsDB.size() > 0){
					if(eventsDB.get(0).getEndTime().after(now)){
						events.add(eventsDB.get(0));
					}
				}
				//TODO: not good
			//		e = pm.getObjectById(Event.class, eid);				
						
			//	if (e != null && e.getStartTime().after(now)){
			//		events.add(e);
			//	}
			}
		}
		finally{
			pm.close();
		}

		return events;
	}

	public static void updateEvent(Event event) {
		saveClass(event);
	}
	
	public static String getUpdates(String eid){
		String updates = "No new Updates";
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Event e;
		Date now = new Date();
		now.setHours(now.getHours()+DatabaseLogic.GMT);
		
		String query= "select from " + Event.class.getName() + " where id == '" + eid + "'";
		try{
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) pm.newQuery(query).execute();
			if(events.size() > 0){
				e = events.get(0);
				if(e.getEndTime().after(now)){
					updates = e.getUpdates();
				}
				else {
					int checkedIn= e.getCheckedIn().size();
					if(checkedIn >= Integer.parseInt(System.getProperty("checkmein.event.Congratulations"))){
						updates = "Congratulations on " + checkedIn + " \"Checked-In \" !";
					}
				}
			}
		}finally{
			pm.close();
		}
		return updates;
	}
	
	@SuppressWarnings("deprecation")
	public static List<Event> getPastPromotedEvents(String ownerID){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + Event.class.getName() + " where ownerID == '" + ownerID + "'";
		List<Event> events = new LinkedList<Event>();
		Date now = new Date();
		now.setHours(now.getHours()+DatabaseLogic.GMT);
		try{
			@SuppressWarnings("unchecked")
			List<Event> eventsDB = (List<Event>) pm.newQuery(query).execute();
			if(eventsDB.size() > 0){
				for(Event e : eventsDB){
					if(e.getEndTime().before(now)){
						events.add(e);
					}
				}
			}
		}
		finally{
			pm.close();
		}
		
	//	if(events.size() > 0){
		//	return events;
	//	}
		return events;
	}
	
	public static String getQRcode(String eid, String cid){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + QR.class.getName() + " where eid == '" + eid + "'" + " && cid == '" + cid + "'";
		String qrCode = null;
		try{
			@SuppressWarnings("unchecked")
			List<QR> qrs = (List<QR>) pm.newQuery(query).execute();
			if(qrs.size() > 0){
				qrCode = qrs.get(0).getCode();
			}
		}
		finally{
			pm.close();
		}
		return qrCode;
	}

	public static QR getQR(String eid, String cid){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + QR.class.getName() + " where eid == '" + eid + "'" + " && cid == '" + cid + "'";
		QR qr = null;
		try{
			@SuppressWarnings("unchecked")
			List<QR> qrs = (List<QR>) pm.newQuery(query).execute();
			if(qrs.size() > 0){
				qr = qrs.get(0);
			}
		}
		finally{
			pm.close();
		}
		return qr;
	}
	
	public static List<QR> getQRs(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<QR> allQRs = new LinkedList<QR>();
		String query= "select from " + QR.class.getName();
		try{
			@SuppressWarnings("unchecked")
			List<QR> qrs = (List<QR>) pm.newQuery(query).execute();
			if(qrs.size() > 0){
				allQRs.addAll(qrs);
			}
		}
		finally{
			pm.close();
		}
		return allQRs;
	}
	
//	public static List<QR> getEventQRs(String id){
//		PersistenceManager pm = PMF.get().getPersistenceManager();
//		List<QR> allQRs = new LinkedList<QR>();
//		String query= "select from " + QR.class.getName() + " where eid == '" + id + "'";
//		try{
//			@SuppressWarnings("unchecked")
//			List<QR> qrs = (List<QR>) pm.newQuery(query).execute();
//			if(qrs.size() > 0){
//				allQRs.addAll(qrs);
//			}
//		}
//		finally{
//			pm.close();
//		}
//		return allQRs;
//	}
	
	public static void deleteEventQRs(String eventID){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query= "select from " + QR.class.getName() + " where eid == '" + eventID + "'";
		try{
			@SuppressWarnings("unchecked")
			List<QR> qrs = (List<QR>) pm.newQuery(query).execute();
			
			pm.deletePersistentAll(qrs);
			
		}
		finally{
			pm.close();
		}
	}

	public static List<Event> getAddtionalEvents(PR pr) {
		List<Event> events = new LinkedList<Event>();
		Set<String> addtionalEvents = pr.getAddtionalEvents();
		
		if(addtionalEvents.size() == 0){
			return events;
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			for (String eid : addtionalEvents){	
				
				String query= "select from " + Event.class.getName() + " where id == '" + eid + "'";
				@SuppressWarnings("unchecked")
				List<Event> eventsDB = (List<Event>) pm.newQuery(query).execute();
				if(eventsDB.size() > 0){
		
						events.add(eventsDB.get(0));
					
				}
			}
		}
		finally{
			pm.close();
		}

		return events;	
	}

	public static String getTimeToPrint(Date date){
		@SuppressWarnings("deprecation")
		String gmt = date.toGMTString();
		int l = gmt.length();
		return gmt.substring(0, l-7);
	}

	public static List<Event> sortEvents(List<Event> events, int flag){
		
		Event[] tmp = new Event[events.size()];
		int k =0;
		for(Event e : events){
			tmp[k] = e;
			k++;
		}
		
		List<Event> sortedEvents = new LinkedList<Event>();
	
		for(int i = 0 ; i < tmp.length - 1 ; i++ ){
			
			for(int j = 0 ; j < tmp.length - i - 1 ; j++ ){
				if(tmp[j].getStartTime().after(tmp[j+1].getStartTime())){
					Event e = tmp[j];
					tmp[j] = tmp[j+1];
					tmp[j+1] = e;
				}
			}
			
		}
		
		if(flag == 0){
			for(int i = 0 ; i < tmp.length ; i++){
				sortedEvents.add(tmp[i]);
			}
		}
		else {
			for(int i = tmp.length-1 ; i >= 0 ; i--){
				sortedEvents.add(tmp[i]);
			}
		}
		return sortedEvents;
		
	}
	
	public static List<Event> sortEvents1(List<Event> events){
		List<Event> sortedEvents = new LinkedList<Event>();
		Event curr,curr1;
		int i=0;
		while(events.size() > 0){
			curr = events.get(0);
			events.remove(0);
			if(sortedEvents.size()>0){
				curr1=sortedEvents.get(0);
				while(sortedEvents.size()>i){
					if(curr.getStartTime().after(curr1.getStartTime())){
						i++;
						if (sortedEvents.size()>i){
							curr1=sortedEvents.get(i);
						}
					}
					else {
						sortedEvents.add(i, curr);
						break;
					}
				}
				if (i==sortedEvents.size()){
					sortedEvents.add(i, curr);
				}
			}else {
				sortedEvents.add(curr);
			}
			i=0;
		}
		return sortedEvents;
           
	}
}
