package com.google.code.checkmein.db;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.restfb.types.User;

@PersistenceCapable
public class Customer extends Person{

	@Persistent 
	private double AVGattendingRate;
	@Persistent     
	private Set<String> virtualAtendingEvents; //all events that attendance to them was confirmed
	@Persistent     
	private Set<String> attendedEvents; //all events that were attended
	//@Persistent
//	private String howToGetQR;
	@Persistent
	private String other_email;
	@Persistent
	private Set<String> sentSMSEventList; //The list of events user was already notified by SMS about 
	@Persistent
	private Set<String> sentEmailEventList; //The list of events user was already notified by mail about
	
	public Customer(User facebookUser,String access_token){
		super(facebookUser, access_token);
		this.attendedEvents = new HashSet<String>();
		this.virtualAtendingEvents = new HashSet<String>();
		this.sentSMSEventList = new HashSet<String>();
		this.AVGattendingRate = 0.0;
	}
	
	public Set<String> getVirtualAtendingEvents() {
		return virtualAtendingEvents;
	}

	public void confirmEvent(String eid) {
		this.virtualAtendingEvents.add(eid);
	}
	
	public void removeEvent(String eid) {
		this.virtualAtendingEvents.remove(eid);
	}

	public Set<String> getAttendedEvents() {
		return attendedEvents;
	}

	public void attendEvent(String eid) {
		this.attendedEvents.add(eid);
		updateAVGattendanceRating();
	}
	
	public double getAVGattendingRate() {
		return AVGattendingRate;
	}

	public void updateAVGattendanceRating() {
		AVGattendingRate = ((double)this.attendedEvents.size()/(double)this.virtualAtendingEvents.size())*10;
	}

	/*public String getHowToGetQR() {
		
		if (howToGetQR == null){ //Default Method		
			return "email";
		}
		
		return howToGetQR;
	}*/

	//public void setGetQRbyDefaultEmail() {
		//this.howToGetQR = "fbemail";
	//}
	
//	public void setGetQRbyOtherEmail(String other_email) {
//		//this.howToGetQR = "email";
//		this.other_email = other_email;
//	}
//	
//	public void setGetQRbyPhone(String cell_phone) {
//		//this.howToGetQR = "phone";
//		this.setCell_phone(cell_phone);
//	}

	public String getOther_email() {
		return other_email;
	}

	public void setOther_email(String other_email) {
		this.other_email = other_email;
	}
	
	public void addNotifiedBySMSEvent(String eventid){
		this.sentSMSEventList.add(eventid);
	}

	public Set<String> getSentSMSEventList() {
		return sentSMSEventList;
	}

	public void addNotifiedByEmailEvent(String eventid){
		this.sentEmailEventList.add(eventid);
	}

	public Set<String> getSentEmailEventList() {
		return sentEmailEventList;
	}
	
	public String getPreferredEmail(){
		
		if (this.other_email == null || this.other_email == ""){
			return this.getEmail();
		}
		else
			return this.getOther_email();
		
	}

}
