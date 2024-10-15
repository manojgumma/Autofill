<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../fragments/resources.jsp"%>
<script type="text/javascript">
window.onload = function(){
	/* setTimeout(function(){
		window.top.$(window.top.document).trigger('test', {'data': 'hi'});
	}, 2000); */
	window.top.$(window.top.document).bind('validate', function(ev, data){
		$(".clear").remove();
		$('#status_div').html('');
		$('#status_div').append('<h3 class="clear" id="validating"></h3>');
		$('#validating').html(data.data);
		$("#validating").css('color', ''+data.colorcode+'');
	});
	window.top.$(window.top.document).bind('read', function(ev, data){
		$('#status_div').append('<h3 class="clear" id="read"></h3>');
		$('#read').html(data.data);
		$('#read').css('color', ''+data.colorcode+'');
	});
	window.top.$(window.top.document).bind('authenticate', function(ev, data){
		$('#status_div').append('<h3 class="clear" id="authenticate"></h3>');
		$('#authenticate').html(data.data);
		$('#authenticate').css('color', ''+data.colorcode+'');
	});
	window.top.$(window.top.document).bind('fill', function(ev, data){
		$('#status_div').append('<h3 class="clear" id="fill"></h3>');
		$('#fill').html(data.data);
		$('#fill').css('color', ''+data.colorcode+'');
	});
}
</script>
</head>
<body background="">
	<center style="margin-top: 100px;">
		<form method="post" action="Automation">
			<div>
				<h3 style="color: #577BC4;">ENJAZ WEB AUTOMATION STATUS</h2>
			</div>
			<div>
				<div id="status_div" class="parent">
				</div>
			</div>

		</form>
	</center>
</body>

</html>