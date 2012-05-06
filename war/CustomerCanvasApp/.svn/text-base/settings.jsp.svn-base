<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.restfb.types.User" %>
<%@ page import="com.restfb.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.google.code.checkmein.util.*" %>
<%@ page import="com.google.code.checkmein.BLogic.CustomerServerLogic" %>
<%@ page import="com.google.code.checkmein.db.Customer" %>
<%@ page import="com.google.code.checkmein.db.Event" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<link rel="stylesheet" type="text/css" href="../Css/PRwelcome.css"/>
<title>Customer Settings</title>
</head>
<body>
		
	<%
		
		String CANVAS_APP_URL = System.getProperty("checkmein.facebook.CUSTOMER.CANVAS_APP_URL");
	
		
		DefaultFacebookClient fbclient;
		SessionManager httpSession;
	
		CustomerServerLogic clogic = new CustomerServerLogic();
		
		
		/** Facebook Authenication & Validation procedure **/
		httpSession = new SessionManager(request, response);		
		
		//No access token in session redirect user to index.jsp to pass authentication procedure
		if (httpSession.getCustomerAccessToken() == null){%>
			
			<script type="text/javascript">
		        top.location = "<%=  CANVAS_APP_URL %>";
		 	</script>
		 	
		<%
			return;		//End response
		}
		/** End Facebook Authentication & Validation procedure **/
		
		
		//We have an access_token get info to display page
		fbclient = new DefaultFacebookClient(httpSession.getCustomerAccessToken());
				
		//Get current user details for update in DB
		User user;
		user = fbclient.fetchObject("me",User.class);		
			
		Customer customer = null;
			
		if (user == null){
			//TODO: Print Error
			out.println("Error getting user detials");
			return;
		}
		
		customer = clogic.updateCustomerDetails(user, httpSession.getCustomerAccessToken());
		
		if (customer == null){
			out.println("Error getting customer details");
			return;
		}	
		
		String id= customer.getId();
		String action = "../settings?cid"+id;
		
	%>

</body>
</html>
