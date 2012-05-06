package dummyLogic;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Event{
	
	/* Members */
	private String pic = "earth.jpg";
	private Date startTime = new Date(System.currentTimeMillis());
	private Date endTime = new Date(System.currentTimeMillis());
	private String location = "Tel Aviv";
	private double AttendingPercent = 10;
	private double AwaitingReplyPercent = 20;
	private double NotAttendingPercent= 30;
	private double CheckedInPercent = 40;
	private double LiveRegisteredPercent = 50;
	private List<Date> AttendanceTime;
	
	public Event(){
		AttendanceTime = new LinkedList<Date>();
		AttendanceTime.add(startTime);
		AttendanceTime.add(endTime);
	}

	public String getPic() {
		return pic;
	}
	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getLocation() {
		return location;
	}

	public double getAttendingPercent() {
		return AttendingPercent;
	}

	public double getAwaitingReplyPercent() {
		return AwaitingReplyPercent;
	}

	public double getNotAttendingPercent() {
		return NotAttendingPercent;
	}

	public double getCheckedInPercent() {
		return CheckedInPercent;
	}

	public double getLiveRegisteredPercent() {
		return LiveRegisteredPercent;
	}

	public List<Date> getAttendanceTime() {
		return AttendanceTime;
	}

}
