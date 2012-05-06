<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.restfb.types.User" %>
<%@ page import="com.restfb.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.google.code.checkmein.util.*" %>
<%@ page import="com.google.code.checkmein.BLogic.PRServerLogic" %>
<%@ page import="com.google.code.checkmein.db.PR" %>
<%@ page import="com.google.code.checkmein.db.Event" %>
<%@ page import="com.google.code.checkmein.db.DatabaseLogic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<link rel="stylesheet" type="text/css" href="../Css/PRwelcome.css"/>
<title>PR welcome Page</title>
</head>
<body>
	<%
		String CANVAS_APP_URL = System.getProperty("checkmein.facebook.PR.CANVAS_APP_URL");
		
		/** Members **/
		PR pr = null;
		PRServerLogic prlogic;
		List<String> updates = null;
		List<Event> promotedEvents;
		List<Event> pastEvents;
		List<com.restfb.types.Event> unPromotedEvents;
		List<Event> addtionalFututreEvents;
		DefaultFacebookClient fbclient;
		SessionManager httpSession;
		Date start;	
		Date end;	
		String st;
		String et;
		
		
		/** Facebook Authenication & Validation procedure **/
		httpSession = new SessionManager(request, response);		
		
		//No access token in session redirect user to index.jsp to pass authentication procedure
		if (httpSession.getPRAccessToken() == null){%>
			
			<script type="text/javascript">
		        top.location = "<%=  CANVAS_APP_URL %>";
		 	</script>
		 	
		<%
			return;		//End response
		}
		/** End Facebook Authentication & Validation procedure **/
		
		
		//We have an access_token get info to display page
		fbclient = new DefaultFacebookClient(httpSession.getPRAccessToken());
				
		//Get current user details for update in DB
		User user;
		user = fbclient.fetchObject("me",User.class);
				
			
		if (user == null){
			//TODO: Print Error
			out.println("Error getting user detials");
			return;
		}
		
		prlogic = new PRServerLogic(user, httpSession.getPRAccessToken());
		pr = prlogic.getPR();
		
					
		//get all the events & updates	
		if (pr != null){			
			
			
			updates = prlogic.getUpdates();
			promotedEvents = prlogic.getFuturePromotedEvents();
			unPromotedEvents = prlogic.getUnPromotedEvents();
			addtionalFututreEvents = prlogic.getAddtionalFutureEvents();
			pastEvents = prlogic.getPastPromotedEvent();
			
			//Add addtional events to pastEvents
			pastEvents.addAll(prlogic.getAddtionalPastEvents());
			
		}
		else{
			//TODO: Handle Error
			out.println("Error getting pr detials"); 	
			return;
		}
		
		//Handle POST back
		
		//Save incoming permitted prs
		if (request.getParameter("submit") != null && request.getParameter("submit").equals("Save")){
			
									
			if (request.getParameter("eid") != null){
				prlogic.setPermittedScanners(request.getParameterValues("prs[]"), request.getParameter("eid"));
			}			
		}
		
		%>

	<div id="appFrame"> 
	
		<div id="appFrameHeader">
			<img id="appFrameLogo" src="../pictures/logo.png"/>
			<%= pr.getFirstName()%>'s Events
		</div>
		
		<div class="InnerFrameHeader">Notification</div>
		<ul class="notificationsDescription">
		
		
			<%	if (updates == null){ %> 
					<li> No new updates</li>	
				<% }
				else {
					for(String update : updates){ %>
					<li><%= update %></li>
					<%} 
				}%>		
		</ul>	
		
	
		<div class="InnerFrameHeader">Promoted Events</div>		
		<% for(Event event : promotedEvents){ %>
		<div class="event">
			<div class="eventDescription">
				<img class="eventImg" src=<%=event.getPic()%> />
				<a target = "_top" href="<%= CANVAS_APP_URL %>statistics.jsp?eventid=<%= event.getId()%>" > <%= event.getName() %></a><br />
				<span class="actionLink">
						<a target = "_top" href="<%= CANVAS_APP_URL %>selectPRs.jsp?eid=<%= event.getId()%>">
							Select PRs
						</a>
				</span>
				<span> <%= event.getStartTimeToPrint() %> to <%= event.getEndTimeToPrint() %></span>
				<br />
				<span> <%= event.getLocation() %></span>
			</div>
		</div>
		<%}%>
		
		
		<div class="InnerFrameHeader">Addtional Events</div>		
		<% for(Event event : addtionalFututreEvents){ %>
		<div class="event">
			<div class="eventDescription">
				<img class="eventImg" src=<%=event.getPic()%> />
				<a target = "_top" href="<%= CANVAS_APP_URL %>statistics.jsp?eventid=<%= event.getId()%>" > <%= event.getName() %></a><br />
					<span> <%= event.getStartTimeToPrint() %> to <%= event.getEndTimeToPrint() %></span>
				<br />
				<span> <%= event.getLocation() %></span>
			</div>
		</div>
		<%}%>
		
		
		<div class="InnerFrameHeader">Unpromoted Events</div>
		<% for(com.restfb.types.Event event : unPromotedEvents){ %>
			<div class="event">
				<div class="eventDescription">			
					<img class="eventImg" src=<%=event.getPicUrl()%> />
					<span><%= event.getName() %></span>
					<span class="actionLink">
						<a target = "_top" href="<%= CANVAS_APP_URL %>buzz.jsp?eventid=<%= event.getId()%>">
							Promote!
						</a>
					</span>
					<% start = event.getStartTime();
					   start.setHours(start.getHours()+ DatabaseLogic.FACEBOOK_TIME); 
					   end = event.getEndTime();
					   end.setHours(end.getHours()+ DatabaseLogic.FACEBOOK_TIME); 
					   st = DatabaseLogic.getTimeToPrint(start);
					   et = DatabaseLogic.getTimeToPrint(end); %>
					   
					<br />
					<span> <%= st %> to <%= et %></span>
					<br />
					<span> <%= event.getLocation() %></span>
				</div>
			</div>
		<%}%>
				
		<div class="InnerFrameHeader">Past Events</div>
		<% for(Event event : pastEvents){ %>
		<div class="event">
			<div class="eventDescription">
				<img class="eventImg" src=<%=event.getPic()%> />
				<a target = "_top" href="<%= CANVAS_APP_URL %>statistics.jsp?eventid=<%= event.getId()%>"><%= event.getName() %></a><br />
				<span> <%= event.getStartTimeToPrint() %> to <%= event.getEndTimeToPrint() %></span>
				<br />
				<span> <%= event.getLocation() %></span>
			</div>
		</div>
		<%} %>

	</div>

	
</body>
</html>