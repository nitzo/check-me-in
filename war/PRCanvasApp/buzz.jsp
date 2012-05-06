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
<link rel="stylesheet" type="text/css" href="../Css/PRwelcome.css"/>
<title>Event Buzz</title>
</head>
<body>

	<%
	
		/** Config Params **/
		String CANVAS_APP_URL = System.getProperty("checkmein.facebook.PR.CANVAS_APP_URL");
				
		/** Members **/
		
		PR pr = null;
		PRServerLogic prlogic = null;
		Event event;
		SessionManager httpSession;
		DefaultFacebookClient fbclient;
		
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
				return; //TODO: Print error
			}				
			
			event = prlogic.promoteEvent(eventid); //Promote the event (If not promoted uet)
			
			
			if (event == null){ //Error promoting event
				out.println("Error promoting event"); 
				return; //TODO: Print error
			}
			
		}
		else{
			//TODO: Handle Error
			out.println("Error getting pr detials"); 	
			return;
		}
			
		%>

	<div id="appFrame">

		<div id="appFrameHeader">
			<img id="appFrameLogo" src="../pictures/logo.png"/>
			<a target="_top" href="<%= CANVAS_APP_URL %>welcome.jsp">			
				<img id="appFrameButton" src="../pictures/BackToEventsButton.png">
			</a>
			<%= event.getName() %> Buzz
		</div>
			
		<div class="InnerFrameHeader">
			<img class="InnerFreameButton" src="../pictures/AddBuzzButton.png">
			Photo
		</div>
		<div class="InnerFrameHeader">
			<img class="InnerFreameButton" src="../pictures/AddBuzzButton.png">
			Status
		</div>
		<div class="InnerFrameHeader">
			<img class="InnerFreameButton" src="../pictures/AddBuzzButton.png">
			Link
		</div>
		<div class="InnerFrameHeader">
			<img class="InnerFreameButton" src="../pictures/AddBuzzButton.png">
			SMS
		</div>
		<div class="InnerFrameHeader">
			<img class="InnerFreameButton" src="../pictures/AddBuzzButton.png">
			Messages
		</div>

    </div>
    
</body>
</html>