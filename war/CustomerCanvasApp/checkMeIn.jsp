<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.restfb.types.User" %>
<%@ page import="com.restfb.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.google.code.checkmein.util.*" %>
<%@ page import="com.google.code.checkmein.BLogic.CustomerServerLogic" %>
<%@ page import="com.google.code.checkmein.db.Customer" %>
<%@ page import="com.google.code.checkmein.db.Event" %>
<%@ page import="com.google.code.checkmein.db.DatabaseLogic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<link rel="stylesheet" type="text/css" href="../Css/Facebook.css" />
<script type="text/javascript">
// <![CDATA[
function setVisible(obj, bool){

	obj = document.getElementById(obj);

	//case hide
	if(bool == false){
		if(obj.style.display != 'none');
		obj.style.display = 'none';
	//case show
	}else {
		 if(obj.style.display != 'block');
		 obj.style.display = 'block';
	}
}

function setForm(newValue){
	
	eventID = document.getElementById('eventID');
	eventID.value = newValue;

	setVisible('popupMessage',true);
	
}
// ]]> 
</script>

<script type="text/javascript">
/**
 * DHTML phone number validation script. Courtesy of SmartWebby.com (http://www.smartwebby.com/dhtml/)
 */

// Declaring required variables
var digits = "0123456789";
// non-digit characters which are allowed in phone numbers
var phoneNumberDelimiters = "-";
// characters which are allowed in international phone numbers
// (a leading + is OK)
var validWorldPhoneChars = phoneNumberDelimiters;
// Minimum no of digits in an international phone no.
var minDigitsInIPhoneNumber = 10;

function isInteger(s)
{   var i;
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}
function trim(s)
{   var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not a whitespace, append to returnString.
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (c != " ") returnString += c;
    }
    return returnString;
}
function stripCharsInBag(s, bag)
{   var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function checkInternationalPhone(strPhone){
	strPhone=trim(strPhone)
	if((strPhone.charAt(0)!= '0') )return false
	if((strPhone.charAt(1)!= '5') )return false
	return (isInteger(strPhone) && s.length == minDigitsInIPhoneNumber );
}

function ValidateForm(){
	var Phone=document.settings.phonenumber
	
	if ((Phone.value==null)||(Phone.value=="")||(Phone.length == 0)){
		return true
	}
	if (checkInternationalPhone(Phone.value)==false){
		alert("Please Enter a Valid Phone Number")
		Phone.value=""
		Phone.focus()
		return false
	}
	return true
 }
</script>


<title>Check Me In Home Page</title>
</head>
<body>
		
	<%
		//Config Params:
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
			//TODO: Print Error
			out.println("Error getting customer detials");
			return;
		}
		
		List<Event> customerEvents = clogic.getCustomerEvents(customer);
		
		customerEvents = DatabaseLogic.sortEvents(customerEvents,0);
		
		//Handle POST back
		
		//Try sending coupon again
		if (request.getParameter("submit") != null && request.getParameter("submit").equals("Send!")){
			String eid = request.getParameter("eid");
			String addtionalemail = request.getParameter("addtionalemail");
			String phonenumber = request.getParameter("phonenumber");
			//phonenumber.replaceAll("-", "");
			boolean sendResult = clogic.sendCoupon(customer, eid, addtionalemail, phonenumber);
			
		}
		
	%>
		
	<div class="appArea">
		
		<!-- check Me In Title -->
		<div class="topHeaderContainer">	
			<div class="topHeader">
				<div>
					<div class="topHeaderText">
						<img src="../pictures/checkMeIn_logo.jpg"/>
						<h2>
							Check Me In
						</h2>
					</div>
				</div>
			</div>
		</div>

		<!-- Inner Container -->
		<div class="innerContainer">
	
			<!-- Inner Container Title-->
			<div class="innerContainerHeaderWraper">
				<div class="innerContainerHeader">
					<h3><%= customer.getFirstName()%>'s Coupons</h3>
				</div>
			</div>

			<!-- Inner Container List -->
			<div class="innerContainerContent">	
				<ul>
				<% for(Event e : customerEvents){ %>
					<% String imageUrl = e.getPic().trim().equals("") || e.getPic() == null ? "../pictures/InnerItemPic.png" : e.getPic(); %>						
					<!-- Inner Container Item-->
					<li> 
						<div class="innerContainerContentItem">
							<a target="_top" href="<%= e.getLink() %>">
								<img src="<%= imageUrl %>" style="width:40px; height:40px;" border=0 />						
							</a>
							<div class="innerContainerContentItemButton">
								<a onclick="setForm('<%= e.getId() %>');">
									<span class="innerContainerContentItemButtonText">Get Your Coupon!</span>
								</a>
							</div>
							
							<span class="innerContainerContentItemDescription">
								<span class="innerContainerContentItemDescriptionTitle">
									<a target="_top" href='<%= e.getLink() %>'><%= e.getName() %></a>
								</span>
								<div class="innerContainerContentItemDescriptionSubtitle"><%= e.getStartTimeToPrint() %></div>
								<div class="innerContainerContentItemDescriptionSubtitle"><%= e.getLocation() %></div>
							</span>
						</div>
					</li>											
				<% } %>
				</ul>
			</div>					

		</div>	

		<!-- popup container -->
		<div id="popupMessage"class="popContainerWarper">
			<div class="popContainer">
				<div class="popContentWarper">
					<div class="popContent">
						<h2>
							<span>Send Me The Coupon</span>
						</h2>
						<div class="popTextAreaOuterWraper">
							<div class="popTextAreaInnerWraper">
								<div class="popTextArea">
									
									<form name=settings method="post" action="#" onSubmit="return ValidateForm()">
										<input id="eventID" type="hidden" name="eid" value=""/>
										<table>
											<tbody>	
												<tr>
													<th class="label">E mail: </th>
													<td class="data">
														<input id="Email" class="formData" name="addtionalemail" value="<%= customer.getPreferredEmail() %>" />
													</td>
												</tr>
												<tr>
													<th class="label">Mobile: </th>
													<td class="data">
														<input type="text" id="Mobile" class="formData" name="phonenumber" value="<%= customer.getCell_phone() == null ?  "" : customer.getCell_phone() %>" />
														<br />* Free SMS
													</td>
												</tr>
						
												<tr>
													<th class="label"></th>
													<td class="data">
														<a class="formGrayButton" onclick="setVisible('popupMessage',false);">
															<span>Cancel</span>
														</a>
														<div class="formBlueButton">
														<input type="submit" value="Send!" name="submit"/>
														</div>	
													</td>
												</tr>
											</tbody>	
										</table>
									</form>
				
								</div>							
							</div>
						</div>				
					</div>
				</div>
			</div>
		</div>

	</div>

</body>
</html>
