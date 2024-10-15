<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Website Automation</title>
<%@include file="../fragments/resources.jsp"%>
<style>
	body{
		background: url('https://enjazit.com.sa/Styles/Images/bodybg.jpg') no-repeat top center fixed; 
	  -webkit-background-size: cover;
	  -moz-background-size: cover;
	  -o-background-size: cover;
	  background-size: cover;
		
	}
</style>
</head>
<frameset cols="60%, 40%">
<frame src="${pageContext.request.contextPath}/enjaz/welcome.jsp" scrolling="yes">
<frame src="${pageContext.request.contextPath}/enjaz/fragtwo.jsp" scrolling="yes">
</frameset>
</html>