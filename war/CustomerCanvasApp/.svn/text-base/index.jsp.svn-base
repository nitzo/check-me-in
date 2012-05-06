<%@ page import="com.restfb.*" %>
<%@ page import="com.google.code.checkmein.util.*" %>
<html>
 <body>
	<% 
		String APP_ID = System.getProperty("checkmein.facebook.CUSTOMER.APP_ID");
		String APP_SECRET = System.getProperty("checkmein.facebook.CUSTOMER.APP_SECRET");
		String SCOPE = System.getProperty("checkmein.facebook.CUSTOMER.SCOPE");
		String CANVAS_APP_URL = System.getProperty("checkmein.facebook.CUSTOMER.CANVAS_APP_URL");
			
		SessionManager httpSession = new SessionManager(request, response);
		
		ExtendedFaceBookClient fbclient = new ExtendedFaceBookClient(APP_ID, APP_SECRET);
		Parameter scope = Parameter.with("scope", SCOPE);
	
		if (httpSession.getCustomerAccessToken() == null){			
			
			//Verify user has installed our app. If not Redirect to Installation + Redirect Back
			if (!fbclient.checkUser(httpSession, true, scope)){	%>
				<script type="text/javascript">
			        top.location = "<%= fbclient.getCanvasAuthorizeURL(CANVAS_APP_URL, scope) %>";
			 	</script>
			 	</body>
			 	</html>
			<%
				return;
			}
			
			else{
				//Set Access Customer access_token in session for future use
				httpSession.setCustomerAccessToken(fbclient.getAccessToken());
			}
		}
		%>
		
	<script type="text/javascript">
		        top.location = "<%= CANVAS_APP_URL + "checkMeIn.jsp" %>";
	</script>		
</body>
</html>