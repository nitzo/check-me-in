<%@ page language="java" contentType="text/html; charset=windows-1255" pageEncoding="windows-1255"%>
<%@ page import="com.google.code.checkmein.BLogic.PRServerLogic" %>
<%@ page import="com.google.code.checkmein.db.PR" %>
<%@ page import="com.restfb.types.User" %>
<%@ page import="com.restfb.*" %>
<%@ page import="com.google.code.checkmein.util.*" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.google.code.checkmein.db.Event" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
	<link rel="stylesheet" href="css/common.css" type="text/css" />
	<link type="text/css" rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/base/ui.all.css" />
	<link type="text/css" href="css/ui.multiselect.css" rel="stylesheet" />
	<link type="text/css" href="../Css/Facebook.css" rel="stylesheet" />
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
	<script type="text/javascript" src="js/plugins/localisation/jquery.localisation-min.js"></script>
	<script type="text/javascript" src="js/plugins/scrollTo/jquery.scrollTo-min.js"></script>
	<script type="text/javascript" src="js/ui.multiselect.js"></script>
	<script type="text/javascript">
		$(function(){
			//$.localise('ui-multiselect', {/*language: 'en',*/ path: 'js/locale/'});
			//$(".multiselect").multiselect();
			 $(".multiselect").multiselect({sortable: false, searchable: true});
			 
			//$('#switcher').themeswitcher();
		});
	</script>
<title>Insert title here</title>
</head>
<body>
<% 		
		String CANVAS_APP_URL = System.getProperty("checkmein.facebook.PR.CANVAS_APP_URL");

		DefaultFacebookClient fbclient;
		SessionManager httpSession;
		Set<String> permittedScanners;
		Event event = null;

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
		
			
		
  		if (request.getParameter("eid") != null){
  			event = PRServerLogic.getEvent(request.getParameter("eid"));
  			
  		}
  		
  		//Cannot get event
  		if (event == null){
		%>
			
			<script type="text/javascript">
		        top.location = "<%=  CANVAS_APP_URL %>";
		 	</script>
		 	
		<%
			return;		//End response
  		}
  	
  		
  		//Check user is owner of evets in order to save settings
  		if (!event.getOwnerID().equals(user.getId())){
		%>
			
			<script type="text/javascript">
		        top.location = "<%=  CANVAS_APP_URL %>";
		 	</script>
		 	
		<%
			return;		//End response
  		}
  		
  		//Get list of already permitted scanners
  		permittedScanners = event.getPermittedScanners();
  			
  		
  		

%>
<form name="myform" action="pr.jsp" method=post>
      <select align="center" id="prs" class="multiselect" multiple="multiple" name="prs[]">
      	<%
      		      		
      		 
      		for (PR pr : PRServerLogic.getAllPRs()){
      			 String value = pr.getId();
      			 String name = pr.getFirstName() + " " + pr.getLastName();
      			 String selected = permittedScanners.contains(pr.getId()) ? "selected=\"selected\"" : "";
      			 %>
      			 
      			 <option value="<%= value %>" <%= selected %>><%= name %></option>
      			 
      		<%}%>
             
      </select>
      <% if  (request.getParameter("eid") != null) {%>
      	<input type="hidden" name="eid" value="<%= request.getParameter("eid") %>">
      <% } %>
      <br/>
      
      <div align="center"><input type="submit" value="Save" name="submit"/></div>
    </form>
</body>
</html>