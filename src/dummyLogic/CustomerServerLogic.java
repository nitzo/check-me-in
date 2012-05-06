package dummyLogic;

import java.util.LinkedList;
import java.util.List;

/**
 * this is a dummy class in order to test the client side
 */
public class CustomerServerLogic{


	/* Members */
	LinkedList<Event> eventList;
	Event dummyevent1;
	Event dummyevent2;
	
	public CustomerServerLogic(String ownerId) {
		eventList = new LinkedList<Event>();
		dummyevent1 = new Event();
		dummyevent2 = new Event();
		eventList.add(dummyevent1);
		eventList.add(dummyevent1);
	}	
	
	public List<Event> getUnPromotedEvents(){
	
		return null;
		
	}

	
	public List<Event> getPromotedEvents(){
	
		return eventList;
	}

	/**
	 * 
	 * 
	 * @param eventId
	 * @return Return NULL if owner is not permitted to view event
	 */
	public Event getEvent(String eventId){
		
		if(eventId.equalsIgnoreCase("1")){
			return dummyevent1;
		}else if(eventId.equalsIgnoreCase("2")){
			return dummyevent2;
		}else{
			return null;
		}
		
	}


	public List<String> getEventUpdates(String eventId){
	
		List<String> updates = new LinkedList<String>();
		updates.add("first update");
		updates.add("second update");
		return updates;
	
	}


}
