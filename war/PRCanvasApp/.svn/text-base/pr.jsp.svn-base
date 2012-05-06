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
<link rel="stylesheet" type="text/css" href="../Css/Facebook.css" />

<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script src="http://connect.facebook.net/en_US/all.js"></script>

<div id="fb-root"></div>
<script type="text/javascript">
// <![CDATA[
  FB.init({
    appId  : '<%=System.getProperty("checkmein.facebook.PR.APP_ID")%>',
    status : true, // check login status
    cookie : true, // enable cookies to allow the server to access the session
    xfbml  : true  // parse XFBML
  });  
//]]> 
</script>


<script type="text/javascript">
// <![CDATA[
$(document).ready(function(){

	//Tell facebook to resize iframe;
	FB.Canvas.setSize();
	
	//Hide (Collapse) Past Events on Load
	$("#moreEvents").hide(); 
	$(".moreLink").text('Show All');
	
	//Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
	$(".moreLink").click(function(){
		$("#moreEvents").slideToggle("slow");
		$(this).hide();
		
		FB.Canvas.setSize();
		
	return false; //Prevent the browser jump to the link anchor
	});
});

function promoteEvent(eid){
	document.myform.eid.value = eid;
	document.myform.eid.action = 'promote';
	document.myform.submit();
}


// ]]> 
</script>



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
		List<Event> addtionalPastEvents;
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
		
					
		
		if (pr == null){
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
		
		//Promote event
		if (request.getParameter("action") != null && request.getParameter("action").equals("promote")){
			
			if (request.getParameter("eid") != null){
				prlogic.promoteEvent(request.getParameter("eid"));%>
				
				<script type="text/javascript">
					top.location = "<%=  CANVAS_APP_URL %>pr.jsp";
		 		</script>
				
			<%
				return; //End request to allow reload of page!
			}
			
		}
			
		//get all the events	
		//updates = prlogic.getUpdates();
		promotedEvents = prlogic.getFuturePromotedEvents();
		//promotedEvents = DatabaseLogic.sortEvents(promotedEvents);
		
		unPromotedEvents = prlogic.getUnPromotedEvents();
		
		addtionalFututreEvents = prlogic.getAddtionalFutureEvents();
		
		pastEvents = prlogic.getPastPromotedEvent();
		
		addtionalPastEvents = prlogic.getAddtionalPastEvents();
			
		//Add addtional events to pastEvents
		
		for(Event e : addtionalFututreEvents){
			if(!e.getOwnerID().equals(pr.getId())){
				promotedEvents.add(e);
			}
		}
		promotedEvents = DatabaseLogic.sortEvents(promotedEvents,0);
		
		for(Event e : addtionalPastEvents){
			if(!e.getOwnerID().equals(pr.getId())){
				pastEvents.add(e);
			}
		}
		pastEvents = DatabaseLogic.sortEvents(pastEvents,1);
		
		//pastEvents.addAll(prlogic.getAddtionalPastEvents());
		//promotedEvents.addAll(prlogic.getAddtionalFutureEvents());
		
		
		
		
		
		%>
<div class="appArea">
		
		<form name="myform" action="pr.jsp" method="POST">
			<input type=hidden name="action" value="promote" />
			<input type=hidden name="eid" value="promote" />
		</form>
		
		<!-- check Me In Title -->
		<div class="topHeaderContainer">	
			<div class="topHeader">
				<div>
					<div class="topHeaderText">
						<img src="../pictures/checkMeIn_logo.jpg"/>
						<h2>
							<%= pr.getFirstName() %>'s Events
						</h2>
					</div>
				</div>
			</div>
		</div>

		<!-- Inner Container -->
		<div class="innerContainer">
			
			<!--  Promoted Events (+ Additional Events) -->
			<!-- Inner Container Title-->
			<div class="section">
				<div class="innerContainerHeaderWraper" >
					<div class="innerContainerHeader" >						
						<h3>Promoted Events</h3>						
					</div>
				</div>
				
				<!-- Inner Container List -->
				<div class="innerContainerContent">
					<ul>
						<% for(Event event : promotedEvents){ %>
						<% 						
							String imageUrl = event.getPic().trim().equals("") || event.getPic() == null ? "../pictures/InnerItemPic.png" : event.getPic();
						%>
						<!-- Inner Conteiner Item-->
						<li> 
							<div class="innerContainerContentItem">
								<a target="_top" href="<%= event.getLink() %>">
									<img src="<%= imageUrl %>" style="width:40px; height:40px;" border=0/>								
								</a>
								
								<% String visibility = ""; 
								//Show Settings button only for owned events
								if (!event.getOwnerID().equals(pr.getId())) {
								 	visibility = "visibility: hidden;";
								} %>
								<div class="innerContainerContentItemButton" style="<%= visibility %>">
									<a target="_top" href="<%= CANVAS_APP_URL %>selectPRs.jsp?eid=<%= event.getId() %>">
										<span class="innerContainerContentItemButtonText">Settings</span>
									</a>
								</div>
								
								
								<div class="innerContainerContentItemButton">
									<a target="_top" href="<%= CANVAS_APP_URL %>statistics.jsp?eventid=<%= event.getId() %>">
										<span class="innerContainerContentItemButtonText">Statistics</span>
									</a>
								</div>		
								
								<span class="innerContainerContentItemButtonGraph">
									<img  src="<%= EventCharts.generateInstalledAppStatistics(event) %>" />							
								</span>
														
								<span class="innerContainerContentItemDescription">
									<span class="innerContainerContentItemDescriptionTitle">
										<a target="_top" href="<%= event.getLink() %>"><%= event.getName() %></a>
									</span>
									<div class="innerContainerContentItemDescriptionSubtitle"><%= event.getStartTimeToPrint() %></div>
									<div class="innerContainerContentItemDescriptionSubtitle"><%= event.getLocation() %></div>
								</span>
							</div>
						</li>							
						<% } %>
	
					</ul>
				</div>
			</div>	
			<br/>
			
			<!--  UnPromoted Events  -->
			<!-- Inner Container Title-->
			<div class="section">
				<div class="innerContainerHeaderWraper" >
					<div class="innerContainerHeader" >						
						<h3>Unpromoted Events</h3>						
					</div>
				</div>
				
				<!-- Inner Container List -->
				<div class="innerContainerContent">
					<ul>
						<% for(com.restfb.types.Event event : unPromotedEvents){ %>
	
						<% 
						    start = event.getStartTime();
					   		start.setHours(start.getHours()+ DatabaseLogic.FACEBOOK_TIME); 
					   		st = DatabaseLogic.getTimeToPrint(start);
					   		String imageUrl = event.getPicUrl().trim().equals("") || event.getPicUrl() == null ? "../pictures/InnerItemPic.png" : event.getPicUrl();
						%>
						
	
						<!-- Inner Conteiner Item-->
						<li> 
							<div class="innerContainerContentItem">
								<a target="_top" >
									<img src="<%= imageUrl %>" style="width:40px; height:40px;" broder=0/>						
								</a>
								
								<div class="innerContainerContentItemButton">
										<a onclick="javascript:promoteEvent('<%= event.getId()%>')">
											<span class="innerContainerContentItemButtonText">Promote</span>
										</a>
								</div>					
															
														
								<span class="innerContainerContentItemDescription">
									<span class="innerContainerContentItemDescriptionTitle">
										<a target="_top" href=""><%= event.getName() %></a>
									</span>
									<div class="innerContainerContentItemDescriptionSubtitle"><%= st %></div>
									<div class="innerContainerContentItemDescriptionSubtitle"><%= event.getLocation() %></div>
								</span>
							</div>
						</li>							
						<% } %>
	
					</ul>
				</div>
			</div>	
			<br />
			
			
			<div class="section">
				<div class="innerContainerHeaderWraper">
					<div class="innerContainerHeader" >
						<a href="" class="moreLink"></a>
						<a class="trigger"><h3>Past Promoted Events</h3></a>
					</div>
				</div>
				
				<!-- Inner Container List -->
				<div class="innerContainerContent">
					<ul>
	
						<% 
							int i = unPromotedEvents.size() + promotedEvents.size();
							
							for(Event event : pastEvents){ 
				 		
								if (i == 9){%>
									<div id="moreEvents">
								<%}
								
								
								String imageUrl = event.getPic().trim().equals("") || event.getPic() == null ? "../pictures/InnerItemPic.png" : event.getPic();
								
						%>
						
						<!-- Inner Conteiner Item-->
						
						
						
						
						<li> 
							<div class="innerContainerContentItem">
								<a target="_top" href="<%= event.getLink() %>">
									<img src="<%= imageUrl %>" style="width:40px; height:40px;" border=0 />						
								</a>
								
																
								<div class="innerContainerContentItemButton">
									<a target="_top" href="<%= CANVAS_APP_URL %>statistics.jsp?eventid=<%= event.getId() %>">
										<span class="innerContainerContentItemButtonText">Statistics</span>
									</a>
								</div>		
								
								<span class="innerContainerContentItemButtonGraph">
									<img  src="<%= EventCharts.generateInstalledAppStatistics(event) %>" />							
								</span>
								
								<span class="innerContainerContentItemDescription">
									<span class="innerContainerContentItemDescriptionTitle">
										<a target="_top" href="<%= event.getLink() %>"><%= event.getName() %></a>
									</span>
									<br />
									<div class="innerContainerContentItemDescriptionSubtitle"><%= event.getStartTimeToPrint() %></div>
									<div class="innerContainerContentItemDescriptionSubtitle"><%= event.getLocation() %></div>
								</span>
							</div>
						</li>							
						<% i++;
							} 
							if (i >= 9){%>
								</div>
							<% }
						%>
						
						
						
					</ul>
				</div>
			</div>

	
</body>
</html>