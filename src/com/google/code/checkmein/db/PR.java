package com.google.code.checkmein.db;

import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.PersistenceCapable;
import com.restfb.types.User;

@PersistenceCapable
public class PR extends Person{

	private Set<String> ownedEvents;
	private Set<String> additionalEvents; //Aditional Permitted Scanin Events
	
	public PR(User facebookUser,String access_token){
		super(facebookUser, access_token);
		this.ownedEvents = new HashSet<String>();
		this.additionalEvents = new HashSet<String>();
	}

	public Set<String> getOwnedEvents() {
		return ownedEvents;
	}
	
	public Set<String> getAddtionalEvents(){
		return this.additionalEvents;
	}
	
	public void addEvent(String e){
		this.ownedEvents.add(e);
	}
	
	public void addAditionalEvent(String e){
		this.additionalEvents.add(e);
	}
	
	public void removeAditionalEvent(String e){
		this.additionalEvents.remove(e);
	}
}
