<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.code.checkmein.util.QRCode" %>
<%@ page import="com.google.code.checkmein.db.DatabaseLogic" %>
<%@ page import="com.google.code.checkmein.db.Customer" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255" />
<link rel="stylesheet" type="text/css" href="Css/applicationStyle.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="Css/printStyle.css" media="print"/>
<script type="text/javascript">
// <![CDATA[
window.onload=window.print();
// ]]> 
</script>
<%

	String facebookID = request.getParameter("fid");
	String eventID = request.getParameter("eid");
	
	
	//Generate QRCode URL
	String qrUrl = QRCode.QRCodeUrlParser(facebookID, eventID);
	
	//Get customer name
	Customer c = DatabaseLogic.getCustomer(facebookID);
	String name = "";
	if (c != null){
		name = c.getFirstName();
	}
	
	//Generate Backup qrcode
	String backupCode = DatabaseLogic.getQRcode(eventID,facebookID);
	if(backupCode == null){
		backupCode = "No code";
	}	
%>
<title><%= c.getFirstName() %>'s coupon</title>
</head>
<body>
	<div class="frameWraper">
		<div class="middleContent">
			<div class="printContent">
				<h4>Bring this coupon to: </h4>
				<h5>Google Office on 7.5 10:00</h5>				
				<br />					
				<h4>We will take care for the rest...</h4>					
			</div>
			<div class="couponContainer">
				<div class="logoWraper">
					<img class="Logo" src="pictures/Coupon_Logo/google_milestone_3.png" alt="Check Me In"/>
				</div>
				<input type="image" class="qrCoupon" onclick="window.print()" src="http://chart.apis.google.com/chart?chs=400x400&cht=qr&chld=l|0&chl=<%= qrUrl %>" alt="<%= name %>'s coupon" />
				<div class="text">
					<%= backupCode %>
				</div>
			</div>
		</div>
	</div>
</body>
</html>