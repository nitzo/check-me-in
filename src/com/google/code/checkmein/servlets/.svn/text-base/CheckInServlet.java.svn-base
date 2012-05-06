package com.google.code.checkmein.servlets;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.google.code.checkmein.BLogic.FacebookLogic;
import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.db.Event;
import com.google.code.checkmein.db.QR;
import com.google.code.checkmein.util.SessionManager;
import com.restfb.ExtendedFaceBookClient;
import com.restfb.FacebookException;
import com.restfb.FacebookJsonMappingException;
import com.restfb.Parameter;
import com.restfb.types.User;

@SuppressWarnings("serial")
public class CheckInServlet extends HttpServlet {
	
	int a=0;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

	
		SessionManager session = new SessionManager(req, resp);
		String scanningUserID = getScanningUserID(session);
		
		if (scanningUserID == null){ // If no scanning user id was returned - end the request
			return;
		}
		
		String color = "FF0000"; //red
		String approved = "DENIED";

		String facebookID = req.getParameter("fid");
		String eventID = req.getParameter("eid");
		String qrID = req.getParameter("qrid");
		String url = req.getParameter("url");
		
		if(facebookID == null && eventID == null){
			QR q = null;
			if(qrID != null){
				q = DatabaseLogic.getQRbyCode(qrID);
			}
			else if(url != null){
				q = DatabaseLogic.getQRbyURL(url);
			}
			if (q != null){ //Coupon found
				facebookID = q.getCid();
				eventID = q.getEid();				
			}
			else{
				//TODO: error
			}
			
		}
		
				
		Customer customer = DatabaseLogic.getCustomer(facebookID);
		Event event = DatabaseLogic.getEvent(eventID);		
		
		Date now = new Date();
		now.setHours(now.getHours()+ DatabaseLogic.GMT);
		
		//Check for user permissions to scan-in
		if (event != null && !event.getPermittedScanners().contains(scanningUserID)){
			 //SCANNING user does not have permissions to scan-in
				approved = "ACCESS DENIED";
					
		}		
		else if(customer == null){
			approved = "NO SUCH CUSTOMER";
		}
		else if(event == null){
			approved = "WRONG EVENT-ID";			
		}
		else if(event.getStartTime().after(now)){
			approved = "EVENT NOT STARTED";
		}
		else if(event.getEndTime().before(now)){
			approved = "EVENT ENDED";
		}
		else if(event.getCheckedIn().contains(facebookID)){
			approved="ALREADY <br> CHECKED-IN";
		}
		else if(event.getAttending().contains(facebookID)){
					
			color="00FF00"; //green
			approved="APPROVED";
			DatabaseLogic.setCheckedIn(event, customer);
						
			String postmessage;
			
			postmessage = "בטקס הענקת פרסים לסטודנטים מצטיינים באוניברסיטת ת\"א";
			
//			if(a==0){
//				a++;
//				postmessage = "Install check-me-in now!!";
//			}
//			else{
//				a--;
//				postmessage = "Check-me-In rules!!";
//			}
			
			
			//String link = "http://www.facebook.com/pages/Morty-Helen-mwrty-whln/131089956921248";
			//String linkName = "מורטי & הלן";
			
			String link = "http://apps.facebook.com/check-me-in/checkMeIn.jsp";
			String linkName = "Check-me-In";
			
			FacebookLogic.publishFeed(customer, postmessage, link, linkName);								
		}
		else {
			approved="CUSTOMER <br> DIDN'T CONFIRM <br> THIS EVENT";
		}
		
		//String logo="http://img823.imageshack.us/img823/6738/logo1st.png"; 
		//String font="Tahoma";
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title> register </title>");
		out.println("</head>");
		out.println("<body bgcolor=" + color + ">");
		out.println("<table width=100% height=100% border=\"0\">");
		out.println("<tr>");
		out.println("<td align=\"center\" valign=\"middle\" width=100% height=100%><p style=\"font-size:700%\"><b>" + approved + "</b></p></td>");
		out.println("</tr>");
		out.println("</table>" );
		out.println("</body>");
		out.println("</html>");
		out.close();

	}

	/**
	 * @param session
	 */
	private String getScanningUserID(SessionManager session) {
		
		String APP_ID = System.getProperty("checkmein.facebook.PR.APP_ID");
		String APP_SECRET = System.getProperty("checkmein.facebook.PR.APP_SECRET");
		String SCOPE = System.getProperty("checkmein.facebook.PR.SCOPE");
		
		//Make sure scanning user is logged on with facebook
		ExtendedFaceBookClient fbclient = new ExtendedFaceBookClient(APP_ID, APP_SECRET);
		
		Parameter scope = null;
		try {
			scope = Parameter.with("scope",SCOPE);
		} catch (FacebookJsonMappingException e1) {
			return null;
		}
		
		
		
		if (session.getPRAccessToken() == null){
			if (!fbclient.checkUser(session, false, scope)){ // Check currently logged on user
				return null; //User is not authenticated yet - return. (User Redirect will happen within checkUser procedure)
			}
			else{
				session.setPRAccessToken(fbclient.getAccessToken());
			}
		}
		
		
		
		String scanningUserID = null;
				
		if (session.getPRAccessToken() != null ){ //We have an access token
			if (session.getFacebookID() == null){ //First time - get Scanning User FID				
				User user;				
				//Get current user details				
				try {
					user = fbclient.fetchObject("me", User.class);
					session.setFacebookID(user.getId()); //Set fid for future requests
					scanningUserID = user.getId(); //Save scanning user ID
				} catch (FacebookException e) {
					//TODO: Log error
					return null;
				}			
				
			}
			else{ //Not the first time, get scanning user ID from session				
				scanningUserID = session.getFacebookID();
			}			
		}
		
		return scanningUserID;
	}
	
}
