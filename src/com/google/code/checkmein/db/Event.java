package com.google.code.checkmein.db;


import java.util.Date; 


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

//TODO: add exceptions
@PersistenceCapable
public class Event  {
	 
	@PrimaryKey     
	@Persistent   
	private String id; 
	@Persistent
	private String ownerID;
	@Persistent
	private String privacy;
	@Persistent
	private String link; //link to the facebook page of the event
	@Persistent
	private String name;
	@Persistent
	private String pic;
	@Persistent
	private String description;
	@Persistent
	private Date startTime;
	@Persistent
	private Date endTime;
	@Persistent
	private String location;
	@Persistent
	private String street;
	@Persistent
	private String city;
	@Persistent
	private String state;
	@Persistent
	private String country;	
	//@Persistent
	//private Set<String> invited;	
	@Persistent
	private Set<String> FBattending;
	@Persistent
	private Set<String> attending;
	@Persistent
	private Set<String> awaitingReply;
	@Persistent
	private Set<String> notAttending;
	@Persistent
	private Set<String> maybeAttending;
	@Persistent
	private Set<String> checkedIn;
	//@Persistent
	//private Set<String> liveRegistered;
	@Persistent
	private List<Integer> checkInTime;
	@Persistent
	private int[] checkInTimesArray;
	//@Persistent
	//private String chart;
	@Persistent
	private double avgAttendanceRating;  //����� ���� ������ �� ����� �����
	@Persistent
	private int numOfFemales; //keeps num of females checked in
	@Persistent
	private int numOfMales;	//keeps number of males checked in
	@Persistent
	private Integer newAttending;
	@Persistent
	private Integer newNotAttending;
	@Persistent
	private Integer newFBAttending;
	//@Persistent
	//private Integer newLiveRegistered;
	@Persistent
	private String updates;
	//@Persistent
	//private List<String> update;
	@Persistent
	private List<String> posts;
	@Persistent
	private Set<String> permittedScanners;
	@Persistent
	private Date creationDate;
	@Persistent
	private int[] ages;

	/**
	 * description: gets a facebook event and constructs all needed lists - attending, awaitingreply, not attending etc.
	 * links the new Event to the facebook event by using setLink
	 * creates a new chart using setChart
	 * @param facebookEvent
	 */
	public Event(com.restfb.types.Event facebookEvent) {
		setFaceboofParams(facebookEvent);
		this.checkInTime = new LinkedList<Integer>();
		this.awaitingReply = new HashSet<String>();
		this.notAttending = new HashSet<String>();
		this.maybeAttending = new HashSet<String>();
		this.attending = new HashSet<String>();
		this.checkedIn = new HashSet<String>();
		this.permittedScanners = new HashSet<String>();
		this.FBattending = new HashSet<String>();
		this.posts = new LinkedList<String>();
		this.ages = new int[4];
	//	this.invited = new HashSet<String>();
		//this.liveRegistered = new HashSet<String>();
		//this.update = new LinkedList<String>();
		this.numOfFemales = 0;
		this.numOfMales = 0;
		this.newAttending = 0;
		this.newNotAttending = 0;
		this.newFBAttending = 0;
		//this.newLiveRegistered = 0;
		this.avgAttendanceRating = 0.0;
		this.updates = "No new updates";
		
		Date temp = new Date();
		temp.setHours(temp.getHours()+ DatabaseLogic.GMT);
		this.creationDate = temp;
		setCheckInTimesArray();
		setLink();
		//setChart();
	}
	
	/**
	 * 	  
	 * @param facebookEvent a new facebook event to add as event
	 * sets all private fields to their value from the facebook event
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void setFaceboofParams(com.restfb.types.Event facebookEvent){
		this.id = facebookEvent.getId();
		this.name = facebookEvent.getName();
		Date temp = facebookEvent.getEndTime();
		temp.setHours(temp.getHours()+ DatabaseLogic.FACEBOOK_TIME);
		this.endTime = temp;
		//endTime.setHours(endTime.getHours()-8);
		temp = facebookEvent.getStartTime();
		temp.setHours(temp.getHours()+ DatabaseLogic.FACEBOOK_TIME);
		this.startTime = temp;
		//startTime.setHours(startTime.getHours()-8);
		
		String tmp = facebookEvent.getDescription();
		if(tmp.length() > 450){
			this.description = tmp.substring(0, 449);
		}
		
		this.location = facebookEvent.getLocation();
		
		if (facebookEvent.getVenue() != null){
			this.street = facebookEvent.getVenue().getStreet();
			this.state = facebookEvent.getVenue().getState();
			this.city = facebookEvent.getVenue().getCity();
			this.country = facebookEvent.getVenue().getCountry();
		}
		
		this.pic = facebookEvent.getPicUrl();
		
		this.privacy=facebookEvent.getPrivacy();
	}

	
	/**
	 * @return a String representing the event id from facebook 
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return pic - the facebook pic of the event
	 */
	public String getPic() {
		return pic;
	}
	
	/**
	 * 
	 * @param pic - sets the event pic to be the one given in pic
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}

	/**
	 * @return the event start time in a Date format
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * sets the event start time to be the time given in startTime
	 * @param startTime 
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		setCheckInTimesArray();
	}

	/**
	 * @return the end time of the event in a Date format
	 */
	public Date getEndTime() {
		
		return endTime;
	}

	/**
	 * sets the end time of the event to be the time given in endTime
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
		setCheckInTimesArray();
	}

	public String getStartTimeToPrint(){
		return DatabaseLogic.getTimeToPrint(startTime);
	}
	
	public String getEndTimeToPrint(){
		return DatabaseLogic.getTimeToPrint(endTime);
	}
	
	/**
	 * @return description - a String describing the event 
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * sets the event description to be the one given in description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return a String representing the event's owner's id from facebook 
	 */
	public String getOwnerID() {
		return ownerID;
	}

	/**
	 * sets the event's owner's id to be the one given in ownerID
	 * @param ownerID
	 * TODO: ����� �� ������ ��� ����� �� ����� ����� ������ �������? 
	 */
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
		this.permittedScanners.add(ownerID);
	}

	/**
	 * @return a String containing the event location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * sets the event location to be the location given
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return a String containing the url of the facebook event
	 * make sure it is called only after setLink was called so the url is correct 
	 */
	public String getLink() {
		return link;
	}

	/**
	 * sets the url of the event to be the event url taken from facebook using the event's id
	 */
	public void setLink() {
		this.link = "http://www.facebook.com/event.php?eid="+id;
	}

	/**
	 * @return a String representing the name of the event
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the event to be the one given
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return a String representing the street of the event
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * sets the street to be the one given
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return a String representing the city of the event
	 */
	public String getCity() {
		return city;
	}

	/**
	 * sets the city to be the one given
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return a String representing the state of the event
	 */
	public String getState() {
		return state;
	}

	/**
	 * sets the state to be the one given
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 
	 * @return a String representing the event country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * sets the country to be the one given
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return The visibility of this event. Can be 'OPEN', 'CLOSED', or 'SECRET'.
	 */
	public String getPrivacy() {
	    return privacy;
	}
	
	/**
	 * sets the privacy to be the one given in privacy
	 * @param privacy
	 */
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	// THE LISTS
	
	/**
	 * @return a Set<String> containing all the id-s of the invited customers to the event right now
	 */
	//public Set<String> getInvited() {
	//	return invited;
	//}

	/**
	 * adds an id of a new customer invited to the event to Invited
	 * @param id
	 */
//	public void addInvited(String id) {
	//	invited.add(id);
	//}

	public Set<String> getFBattending() {
		return FBattending;
	}

	public void setFBattending(Set<String> FBattending) {
		if(this.FBattending.size() < FBattending.size()){
			newFBAttending = newFBAttending + FBattending.size() - this.FBattending.size();
			setUpdates();
		}
		
		this.FBattending = FBattending;
		
	}
	
	/**
	 * @return a Set<String> with all the id-s of people who chose to attend the event up to now
	 */
	public Set<String> getAttending() {
		return attending;
	}
	
	public Set<String> getAttendingAndCheckIn(){
		
		Set<String> result;
		
		result = new HashSet<String>();
		
		result.addAll(this.attending);
		result.addAll(this.checkedIn);
		
		return result;
	}

	/**
	 * adds the facebook id given to the Attending list and increases newAttending so when updates are checked 
	 * you can know how many newAttendings are since last time, and calls setUpdates
	 * @param id
	 */
	public void addAttending(String id) {
		attending.add(id);
		newAttending++;
		setUpdates();
	}

	/**
	 * removes the facebook id given from the Attending list
	 * @param id
	 */
	public void removeAttending(String id) {
		attending.remove(id);
	}
	
	/**
	 * @return a Set<String> of the customers id-s that are in awaiting reply 
	 */
	public Set<String> getAwaitingReply() {
		return awaitingReply;
	}

	public void setAwaitingReply(Set<String> awaitingReply) {
		this.awaitingReply = awaitingReply;
	}

	/**
	 * adds a facebook id of a new customer who is awaiting reply
	 * @param id
	 */
	public void addAwaitingReply(String id) {
		awaitingReply.add(id);
	}

	/**
	 * removes the id given from the awaiting reply list
	 * @param id
	 */
	public void removeAwaitingReply(String id) {
		awaitingReply.remove(id);
	}
	
	/**
	 * @return a Set<String> with the id-s of the NotAttending customers
	 */
	public Set<String> getNotAttending() {
		return notAttending;
	}

	public void setNotAttending(Set<String> notAttending) {
		if(this.notAttending.size() < notAttending.size()){
			newNotAttending = newNotAttending + notAttending.size() - this.notAttending.size();
			setUpdates();
		}
		this.notAttending = notAttending;
	}

	/**
	 * adds the facebook id given to the NotAttending list and increases newNotAttending so when updates are checked 
	 * you can know how many newNotAttendings are since last time, and calls setUpdates
	 * @param name
	 */
	public void addNotAttending(String id) {
		notAttending.add(id);
		newNotAttending++;
		setUpdates();
	}
	/**
	 * removes the id given from NotAttending list
	 * @param id
	 */
	public void removeNotAttending(String id) {
		notAttending.remove(id);
	}

	/**
	 * adds a live registered id to the LiveRegistered list, increases newLiveRegistered and calls setUpdates
	 */
	//public void addLiveRegistered(String id) {
	//	liveRegistered.add(id);
	//	newLiveRegistered++;
	//	setUpdates();
	//}

	/**
	 * @return a Set<String> with the id-s of the MaybeAttending customers
	 */
	public Set<String> getMaybeAttending() {
		return maybeAttending;
	}

	public void setMaybeAttending(Set<String> maybeAttending) {
		this.maybeAttending = maybeAttending;
	}

	
	public Set<String> getPermittedScanners() {
		return permittedScanners;
	}

	public void addPermittedScanner(String scannersID) {
		this.permittedScanners.add(scannersID);
	}

	public void removePermmitedScanner(String id){
		this.permittedScanners.remove(id);
	}
	
	public List<String> getPosts() {
		return posts;
	}

	public void setPosts(List<String> posts) {
		this.posts = posts;
	}
	
	public void addPost(String post){
		this.posts.add(post);
	}

	/**
	 * @return an int reperesenting the number of maybe attending people
	 */
	public int getNumOfMaybeAttending() {
		return maybeAttending.size();
	}
	
	/**
	 * @return an int reperesenting the number of people invited to the event
	 */
	public int getNumOfInvited() {
		return this.attending.size() + this.notAttending.size() + this.maybeAttending.size() + this.checkedIn.size();
	}
	
	/**
	 * returns an int - the number of Attending people to the event
	 */
	public int getNumOfAttending() {
		return attending.size();
	}

	/**
	 * @return an int describing the number of AwaitingReply people in the event 
	 */
	public int getNumOfAwaitingReply() {
		return awaitingReply.size();
	}
	
	/**
	 * @return an int reperesenting the number of NotAttending to the event
	 */
	public int getNumOfNotAttending() {
		return notAttending.size();
	}

	public int getNumOfFBattending() {
		return FBattending.size();
	}
	
	/**
	 * @return an int reperesenting the number of CheckedIn in the event
	 */
	public int getNumOfCheckedIn() {
		return checkedIn.size();
	}
	
	/**
	 * @return an int reperesenting the Live registered people number
	 */
	//TODO: change this to milestone 3
	public int getNumOfLiveRegistered() {
		return 0;
	}
	
	/**
	 * @return a list with the id-s of the checked in customers
	 */
	public Set<String> getCheckedIn() {
		return checkedIn;
	}
	
	/**
	 * adds a new checked in customer with ID id to CheckedIn list. adds the CheckIn time depends on the gender of the new customer
	 * increases females or males - to use with the statistics tools
	 * @param id
	 */
	public void addCheckedIn(String id, String gender, String birthday, double AVGpersonRate ) {

		if (gender == null){//Did not recieve gender info
			//TODO: Add unkown gender?
		}
		else if(gender.equals("female")){ //TODO: how facebook keeps the gender
			numOfFemales++;
		}
		else if(gender.equals("male")){
			numOfMales++;
		}
		
		if(birthday != null){
			Date now = new Date();
			@SuppressWarnings("deprecation")
			int d = now.getYear() + 1900 - Integer.parseInt(birthday.substring(6));
			if(d >= 18 && d <= 22){
				ages[0]++;
			}
			else if(d >= 23 && d <= 25){
				ages[1]++;
			}
			else if(d >= 26 && d <= 30){
				ages[2]++;
			}
			else if(d >= 31){
				ages[3]++;
			}
		}
		
		checkedIn.add(id);
		updateAVGattendanceRating(AVGpersonRate);
		//addCheckInTime();
	}
	
	/**
	 * sets the checkInTimesArray to the size
	 */
	@SuppressWarnings("deprecation")
	public void setCheckInTimesArray() {
		int hours = this.endTime.getHours() - this.startTime.getHours();
		int minutes = this.endTime.getMinutes()-this.startTime.getMinutes();
		if((hours >= 0)  &&  (minutes >= 0)){
			this.checkInTimesArray = new int[hours*4 + (int) Math.floor(minutes/15) + 1];
		}
		else if((hours >= 0) &&  (minutes <= 0)){
			int min = (60 - this.startTime.getMinutes()) + this.endTime.getMinutes();
			this.checkInTimesArray = new int[hours*4 + (int) Math.floor(min/15) + 1];
		}
		else if((hours <=0) &&  (minutes <= 0)){
			int hou = (24 - this.startTime.getHours() + this.endTime.getHours());
			int min = (60 - this.startTime.getMinutes()) + this.endTime.getMinutes();
			this.checkInTimesArray = new int[hou*4 + (int) Math.floor(min/15) +1];
		}
		else if((hours <= 0) &&  (minutes >= 0)){
			int hou = (24 - this.startTime.getHours() + this.endTime.getHours());
			this.checkInTimesArray = new int[hou*4 + (int) Math.floor(minutes/15) +1];
		}
		//this.checkInTimesArray = new int[Math.abs((this.endTime.getHours()-this.startTime.getHours()))*4 + (int) Math.floor((this.endTime.getMinutes()-this.startTime.getMinutes())/15) + 1];
	}
	
	/**
	 * @return an int representing the number of females that checked in the event
	 */
	public int getNumOfFemales() {
		return numOfFemales;
	}
	
	/**
	 * @return an int representing the number of males that had checked in the event
	 */
	public int getNumOfMales() {
		return numOfMales;
	}
	
	/**
	 * @return CheckInTime - a List<Integer> of the number of customers checked in the event
	 * every quarter of an hour from startTime.
	 */
	public List<Integer> getCheckInTime() {
		for (int i=0;i<checkInTimesArray.length;i++){
			checkInTime.add(checkInTimesArray[i]);
		}
		return checkInTime;
	}

	/**
	 * adds a check in time to checkInTimesArray. 
	 * checkInTimesArray is containing a cell for every 15 minutes of the event, from startTime to EndTime
	 * after a call to addCheckInTime the plave in the array corresponding to the 15 minutes is increased by 1
	 * 
	 * pay attention that the time is in UTC and not GMT so u need to add 2 hours!!
	 */
	@SuppressWarnings("deprecation")
	public void addCheckInTime() {
		Date now = new Date();
		now.setHours(now.getHours()+2);
		
		int quarters=0;
		
		int hours = now.getHours() - this.startTime.getHours();
		int min = now.getMinutes()-this.startTime.getMinutes();
		
		if((hours >= 0)  &&  (min >= 0)){
			//this.checkInTimesArray = new int[hours*4 + (int) Math.floor(min/15) + 1];
			quarters = hours*4 + (int) Math.floor(min/15);
		}
		else if((hours >= 0) &&  (min <= 0)){
			int tmin = (60 - this.startTime.getMinutes()) + this.endTime.getMinutes();
			//this.checkInTimesArray = new int[hours*4 + (int) Math.floor(tmin/15) + 1];
			quarters = hours*4 + (int) Math.floor(tmin/15);
		}
		else if((hours <=0) &&  (min <= 0)){
			int hou = (24 - this.startTime.getHours() + this.endTime.getHours());
			int tmin = (60 - this.startTime.getMinutes()) + this.endTime.getMinutes();
			//this.checkInTimesArray = new int[hou*4 + (int) Math.floor(tmin/15) +1];
			quarters = hou*4 + (int) Math.floor(tmin/15);
		}
		else if((hours <= 0) &&  (min >= 0)){
			int hou = (24 - this.startTime.getHours() + this.endTime.getHours());
			//this.checkInTimesArray = new int[hou*4 + (int) Math.floor(min/15) +1];
			quarters = hou*4 + (int) Math.floor(min/15);
		}
		
		//quarters=(now.getHours()-this.startTime.getHours())*4;
		//int minutes=(int) Math.floor((now.getMinutes()-this.startTime.getMinutes())/15);
		//quarters=quarters+minutes;
		checkInTimesArray[quarters]++;
	}

	/**
	 * @return AVGattendanceRating a double (0-10) of the average attendance rating of the people invited to the event
	 */
	public double getAVGattendanceRating() {
		return avgAttendanceRating;
	}

	/**
	 * sets AVGattendanceRating to be the one given
	 * @param AVGattendanceRating
	 */
	public void setAVGattendanceRating(double AVGattendanceRating) {
		this.avgAttendanceRating = AVGattendanceRating;
	}

	/**
	 * 	updates the average attendance rating to be correct to this moment. 
	 *  computed by dividing the average rating of the people + this one in the total number of people  
	 *  
	 *  call this method after each time u invite another customer to the event - and send this new person AVGpersonRate to the method
	 */
	public void updateAVGattendanceRating(double AVGpersonRate) {
		this.avgAttendanceRating = (this.avgAttendanceRating*(this.attending.size()-1))/(double)this.attending.size() + (double)AVGpersonRate/(double)this.attending.size();
	}

	public int[] getAges() {
		return ages;
	}

	public void setAges(int[] ages) {
		this.ages = ages;
	}

	/**
	 * @return a String with all the last updates of the event
	 */
	public String getUpdates() {
		String temp = updates;
		updates = "No new updates";
		
		//List<String> tmp = update;
		//update.clear(); 
		//update = new LinkedList<String>();
		newAttending = 0;
		newNotAttending = 0;
		newFBAttending = 0;
	//	newLiveRegistered=0;
		return temp;
		//return tmp;
	}

	/**
	 * sets updates to be the String that contains the new update about new attendies, new not attending 
	 * or new live registered.  
	 * the method is called every time one of this parameters is changed.   
	 */
	public void setUpdates() {
		
		StringBuilder s = new StringBuilder();
		
		if(newFBAttending!=0){
			s.append(newFBAttending + " new \"Attending\"  ");
			//update.add(newFBAttending + " new \"Attending\" on \" "+ name + " \" \n ");
		}
		if(newAttending!=0 && newFBAttending!=0){
			s.append(", "+ newAttending + " of them are Check-me-In users  ");
			//update.add(newAttending + " of them are Check-me-In users  \n ");
		}
		if(newAttending!=0 && newFBAttending==0){
			s.append("<br><br> "+ newAttending + " new Check-me-In users are \"Attending\"  ");
		}
		if(newNotAttending!=0){
			s.append("<br><br> "+newNotAttending + "   new \"Not Attending\"  ");
			//update.add(newNotAttending + " new \"Not Attending\" on \" "+ name + " \" \n");
		}
		
	//	if(newLiveRegistered!=0){
	//		s.append(newLiveRegistered + " new \"Live Registered\" on "+ name + " ");
	//	}
		
		updates = s.toString();
	}

	public void updateEvent(com.restfb.types.Event eventFromFB) {
		
		setFaceboofParams(eventFromFB);
		
	}	
	

	/**
	 * 
	 * @return a String containing the url of the chart of the checkedIn percent customers from the total customers 
	 */
	//public String getChart() {
	//	return chart;
	//}

	/**
	 * sets chart to be correct according to the checked in customers from the total customers invited
	 */
	/*public void setChart() {
		float percent= ((float)this.checkedIn.size()/(float)this.attending.size())*100;
		this.chart = "http://chart.apis.google.com/chart?&chs=300x230&cht=p3&chco=FF0000|224499&chd=t:"+(100-percent)+","+percent+"&chdl=not+attended|attended&chdlp=b&chl="+(this.attending.size()-this.checkedIn.size())+"|"+this.checkedIn.size()+"&chtt=Attendence&chts=676767,20";
		//this.chart = "http://chart.apis.google.com/chart?chf=bg,s,F5F5F5&chs=300x230&cht=p3&chco=FF0000|224499&chd=t:"+(100-persent)+","+persent+"&chdl=not+attended|attended&chdlp=b&chl="+(this.Attending-this.CheckedIn)+"|"+this.CheckedIn+"&chtt=Attendence&chts=676767,20";
	}*/
	
	
}
