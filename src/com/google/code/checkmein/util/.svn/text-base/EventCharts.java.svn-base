package com.google.code.checkmein.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.db.Event;
import com.restfb.StringUtils;

/**
 * 
 * Library for generating Google Charts URL
 */
public class EventCharts {
	
	/** Members **/
	
	private Event event;
	private StringBuilder url;
	
	//Set<String> checkedinList;
	private int[] ages;

	/* status members */
	private int FBattending;
	private int attending;
	private int maybeAttending;
	private int checkedIn;
	//private int liveRegistered;

	/* check in time members */
	private List<Integer> checkInTimeList;

	/* gender members */
	private int females;
	private int males;
	private double femalesPercentage; 

	/** Debug constructor **/
	//TODO: remove Debug constructor 

	/*public EventCharts(){
		
		this.url = new StringBuilder();
		attending = 10;
		maybeAttending = 20;
		checkedIn = 50;
		liveRegistered = 60;
		
		checkInTimeList = new LinkedList<Integer>();
		checkInTimeList.add(new Integer(10));
		checkInTimeList.add(new Integer(20));
		checkInTimeList.add(new Integer(50));
		checkInTimeList.add(new Integer(10));
		checkInTimeList.add(new Integer(5));
		checkInTimeList.add(new Integer(45));
		checkInTimeList.add(new Integer(0));
		checkInTimeList.add(new Integer(35));
		checkInTimeList.add(new Integer(2));
		
		females = 50;
		males = 172;
		
	}*/
	
	/** Constructor **/

	public EventCharts(Event event){
		this.event = event;
		this.url = new StringBuilder();
		this.ages = new int[4];
		ExtractData();
	}
	
	/** utility Methods **/
	
	/**
	 * extract the relevant data from the event
	 */
	private void ExtractData(){
		
		//status chart data
		attending = event.getNumOfAttending();
		checkedIn = event.getNumOfCheckedIn();
		FBattending = event.getNumOfFBattending() - attending - checkedIn ;
		//TODO: fix the getter number of maybe attending
		maybeAttending = event.getNumOfMaybeAttending();
		
		//liveRegistered = event.getNumOfLiveRegistered();

	//	checkedinList = event.getCheckedIn();
	//	if(checkedinList == null){
	//		checkedinList = new HashSet<String>();
	//	}
		//check in graph data
		//TODO: change the returned type from the time list
//		checkInTimeList = event.getCheckInTime();
		
		//gender chart data
		females = event.getNumOfFemales();
		males = event.getNumOfMales();
		if((males+females)==0){
			femalesPercentage=50;
		}else{
			femalesPercentage = ((double)females / (males+females)) * 100;			
		}

		this.ages = event.getAges();
	}
	
	/**
	 * reset the url content
	 * adding the url address and size
	 */
	private void resetURL(){
		url.delete(0, url.length());
		url.append("http://chart.apis.google.com/chart");
		url.append("?chs=400x150");//chart size		
	}
	
	/** Methods **/
	
	/**
	 * Generate URL to google chart pie API with the current event data
	 * @return status cart pie URL
	 */
	public String GeneratePieChartUrl(){
		resetURL();
		//BBCCED|AA0033
		url.append("&cht=p3");//chart type	   
		url.append("&chco=00FF00|FFCC33|FF0000|0000FF");//chart color	   
		url.append("&chdl=" //legend list
					+ FBattending + " Attending (Non Check-Me-In Users)|"
					+ attending + " Attending (Check-Me-In Users)|"
					+ maybeAttending + " Maybe Attending|"
					+ checkedIn + " Checked In");
					//+ liveRegistered + " Live Registered");
		url.append("&chd=t:"+ FBattending + "," + attending + "," + maybeAttending + "," + checkedIn );//the data
		return url.toString();
	}
	
	/**
	 * Generate URL to google Line chart API with the current event data
	 * @return checkIn chart pie URL
	 */
	public String GenerateLineCehckInTimeURL(){

		//TODO: set the chart to show the real numbers and not the percentage

		//set the chart
		resetURL();
		url.append("&cht=lc");//chart type

		//extract chart data from list;
		url.append("&chd=t:");
		for (Integer checkInTime: checkInTimeList) {
			url.append(checkInTime.intValue()+",");
		}
		url.deleteCharAt(url.length()-1);//trim the last ","

		//set the axis
		url.append("&chxt=x,y");//declare the axis
		url.append("&chxr=0,0,");//set x-axis value <axis_index>,<start_val>
		url.append((checkInTimeList.size()/4) + "," + (checkInTimeList.size()/16)); //set x-axis value <end_val>,<step>
		url.append("&chxs=0,000000,12,0,lt|");//set x-axis label <axis_index>,<label_color>,<font_size>,<alignment>,<axis_or_tick>,<tick_color>
		url.append("1,0000ff,10,1,lt");//set y axis label

		//set the line
		url.append("&chls=6");//set line style <line_1_thickness>,<dash_length>,<space_length>
		url.append("&chm=B,76A4FB,0,0,0");//set line fill <color>,<start_line_index>,<end_line_index>,<0>
		url.append("&chco=FF0000");//set line color <series_1_color>

		return url.toString();
	}
	
	/**
	 * Generate URL to google Pie chart API with the current event data
	 * @return gender bar chart URL
	 */
	public String GenerateGenderOmeterChartURL(){

		//set the chart
		resetURL();
		url.append("&cht=gm");

		url.append("&chd=t:" + femalesPercentage);
		url.append("&chxt=y");//declare the axis
		url.append("&chxl=0:|Teddy+Stadium||Playboy+Mansion");//set lable

		return url.toString();

	}
	
	
	public static String generateInstalledAppStatistics(Event event){
		
		StringBuilder url = new StringBuilder();
		
		
		int totalAttending = event.getNumOfFBattending();
		int knownUsers = event.getNumOfCheckedIn() + event.getNumOfAttending();
		float percentage;
		
		if (totalAttending != 0){
		
			percentage = (float) knownUsers / (float) totalAttending;
			
		}
		else{
			percentage = 0;
		}
		
		percentage *= 100;
		
				
		url.append("http://chart.apis.google.com/chart?");
		url.append("chxt=x");
		url.append("&chbh=a");
		url.append("&chs=220x55");
		url.append("&cht=bhs");
		url.append("&chco=0000FF,76A4FB");
		url.append("&chd=t:");
		url.append(percentage);
		url.append("|");
		url.append(100);
		url.append("&chtt=%25+of+Check-Me-In+Users+out+of+Total+Attending");
		url.append("&chts=676767,9.5");
		
		return url.toString();
		
	}


	public String GenerateAgeDistributionChartUrl(){
		
		resetURL();
		int [] tmp = new int[4];
		for(int i=0 ; i<4 ; i++){
			tmp[i] = ages[i];
		}
		Arrays.sort(tmp);
		int max = tmp[3] + 8;
		
		url.append("&chxl=0:|18++-++22|23++-++25|26++-++30|30%2B");
		url.append("&chxr=1,0,"+max);
		url.append("&chxt=x,y&chbh=a&cht=bvg&chco=AA0033");
		url.append("&chd=t:"+(ages[0]*max)+","+(ages[1]*max)+","+(ages[2]*max)+","+(ages[3]*max));
		//url.append("&chd=t:2,3,7,2");
		
		return url.toString();
		
		//return "http://chart.apis.google.com/chart?chxl=0:|18++-++22|23++-++25|26++-++30|30%2B&chxr=1,0,10&chxt=x,y&chbh=a&chs=400x150&cht=bvg&chco=AA0033&chd=t:20,30,70,20";
	}

}
