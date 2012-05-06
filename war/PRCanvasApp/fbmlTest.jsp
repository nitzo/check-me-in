<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Insert title here</title>
</head>

<div id="fb-root"></div>
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script type="text/javascript">
// <![CDATA[
  FB.init({
    appId  : '<%= System.getProperty("148836425163954") %>',
    status : true, // check login status
    cookie : true, // enable cookies to allow the server to access the session
    xfbml  : true  // parse XFBML
  });
  
function streamPublish(name, description, hrefTitle, hrefLink, userPrompt){ 
  FB.ui(     
		{method: 'stream.publish',         
		 message: '',         
		 attachment: {             
			name: name,             
			caption: '',             
			description: (description),             
			href: hrefLink         
		 },        
		 action_links: [{text: hrefTitle, href: hrefLink } ],         
		 user_prompt_message: userPrompt     
		},     
		function(response) {      }); 
	}
	
function publishStream(){     
	streamPublish("Stream Publish", 
			'Thinkdiff.net is AWESOME. I just learned how to develop Iframe+Jquery+Ajax base facebook application development. ', 
			'Checkout the Tutorial', 'http://wp.me/pr3EW-sv', "Demo Facebook Application Tutorial"); 
}
// ]]> 
</script>


<body>
 
<!-- <script>
  window.fbAsyncInit = function() {
    FB.init({appId: 'your app id', status: true, cookie: true,
             xfbml: true});
  };
  (function() {
    var e = document.createElement('script'); e.async = true;
    e.src = document.location.protocol +
      '//connect.facebook.net/en_US/all.js';
    document.getElementById('fb-root').appendChild(e);
  }());

  
</script> -->
<div id="fragment-3">         
<a href="#" onclick="publishStream(); return false;">Click Here To Show A DEMO Stream Publish Box</a>     
</div>



</body>
</html>