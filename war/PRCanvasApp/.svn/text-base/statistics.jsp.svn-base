<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.restfb.types.User" %>
<%@ page import="com.restfb.*" %>
<%@ page import="java.util.*" %>
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


<title>Events Statistics</title>
</head>

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
	
	});
});

// ]]> 
</script>

<body>
	
	<%
		String CANVAS_APP_URL = System.getProperty("checkmein.facebook.PR.CANVAS_APP_URL");
		
	
		/** Config Params **/
		
		//TODO: Move to db
		
	
		/** Members **/
		
		PR pr = null;
		PRServerLogic prlogic = null;
		Event event;
		EventCharts charts;
		String updates; 
		
		DefaultFacebookClient fbclient;
		SessionManager httpSession;
		
		
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
						
		/** End Facebook Authentication & Validation procedure **/
			
		if (user == null){
			//TODO: Print Error
			out.println("Error getting user detials");		
			return;
		}
		
		prlogic = new PRServerLogic(user, httpSession.getPRAccessToken());
		pr = prlogic.getPR();
		
					
		//get all the events & updates	
		if (pr != null){			
						
			String eventid = request.getParameter("eventid");
			
			if (eventid == null){
				//TODO: Print error
				return;
			}
			
			event = PRServerLogic.getEvent(eventid);
			
			
			if (event == null){
				//TODO: Print error
				return;
			}
			
			updates = DatabaseLogic.getUpdates(event.getId());
			if(updates.equals("noUpdates")){
				updates = "No new updates";
			}
					
	  		//Check if user is owner of events in order to view statistics
	  		if (!event.getOwnerID().equals(user.getId()) && !event.getPermittedScanners().contains(user.getId())){
			%>
				
				<script type="text/javascript">
			        top.location = "<%=  CANVAS_APP_URL %>";
			 	</script>
			 	
			<%
				return;		//End response
	  		}
			
			charts = new EventCharts(event);
		}
		else{
			//TODO: Handle Error
			out.println("Error getting pr detials");	
			return;
		}
		
		
		
	%>
	<div id="appFrame">
	
	<!-- Statistics Title -->
		<div class="topHeaderContainer">	
			<div class="topHeader">
				<div>
					<div class="innerContainerContentItemButton">
										<a target="_top" href="<%= CANVAS_APP_URL %>pr.jsp">
											<span class="innerContainerContentItemButtonText">Back to My Events</span>
										</a>
					</div>
							
					<div class="topHeaderText">
						<img src="../pictures/checkMeIn_logo.jpg"/>
						<h2>
							<%= event.getName() %> Statistics
						</h2>
					</div>
					
					
				</div>
			</div>
		</div>
		<br/>
		
		
		
		<div class="section">
				<div class="innerContainerHeaderWraper" >
					<div class="innerContainerHeader" >						
						<h3>Event Summary</h3>						
					</div>
				</div>
				
				<div class="innerContainerContentItem">
																						
					<span class="innerContainerContentItemDescription">
						<ul>
						<li>
							<div class="innerContainerContentItemDescriptionTitle">
									<%= event.getNumOfAttending()+ event.getNumOfCheckedIn() %> Check-Me-In users out of <%= event.getNumOfFBattending() %> are Attending the event
							</div>
						</li>
						<br />
						<li>
							<div class="innerContainerContentItemDescriptionTitle">
									<%= event.getNumOfCheckedIn() %> users are currently Checked-In
							</div>
						</li>
						<br />
						<li>
							<div class="innerContainerContentItemDescriptionTitle">
									Updates:
									<br /><br />
									<ul>
									<li><%= updates %></li>
									</ul>
							</div>
						</li>
						
						</ul>
						
						
					</span>
				</div>
				

		</div>
		<br />
		
		<div class="section">
				<div class="innerContainerHeaderWraper" >
					<div class="innerContainerHeader" >						
						<h3>Participant Distribution</h3>						
					</div>
				</div>
				
				<!-- Inner Container List -->
				<div class="innerContainerContent">
					
						
						<!-- Inner Conteiner Item-->
						<div class="innerContainerContentItem">
							<img src="<%= charts.GeneratePieChartUrl() %>"/>
						</div>					
				</div>
		</div>
		<br />
		
		<div class="section">
				<div class="innerContainerHeaderWraper" >
					<div class="innerContainerHeader" >						
						<h3>Gender Meter</h3>						
					</div>
				</div>
				
				<!-- Inner Container List -->
				<div class="innerContainerContent">
					
						
						<!-- Inner Conteiner Item-->
						<div class="innerContainerContentItem">
							<br /><img src="<%= charts.GenerateGenderOmeterChartURL() %>"/>
						</div>					
				</div>
		</div>	
	
	
	</div>
	
	<div class="section">
				<div class="innerContainerHeaderWraper" >
					<div class="innerContainerHeader" >						
						<h3>Age Distribution</h3>						
					</div>
				</div>
				
				<!-- Inner Container List -->
				<div class="innerContainerContent">
					
						
						<!-- Inner Conteiner Item-->
						<div class="innerContainerContentItem">
							<br /><img src="<%= charts.GenerateAgeDistributionChartUrl() %>"/>
						</div>					
				</div>
	</div>	
	
	
	
	
	
	
	

</body>
</html>