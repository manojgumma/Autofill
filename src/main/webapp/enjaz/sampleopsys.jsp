<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../fragments/resources.jsp"%>
<style>
body {
	background: url('https://enjazit.com.sa/Styles/Images/bodybg.jpg')
		no-repeat top center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}
</style>
<style>
#overlay {
    position: fixed; 
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #000;
    opacity: 0.5;
    filter: alpha(opacity=50);
}
#modal {
    position:absolute;
    background:url(tint20.png) 0 0 repeat;
    background:rgba(0,0,0,0.2);
    border-radius:14px;
    padding:8px;
}

#content {
    border-radius:8px;
    background:#fff;
    padding:20px;
}
#close {
    position:absolute;
    background:url(assets/images/close.png) 0 0 no-repeat;
    width:24px;
    height:27px;
    display:block;
    text-indent:-9999px;
    top:-7px;
    right:-7px;
}
</style>
<script>
			var modal = (function(){
				var 
				method = {},
				$overlay,
				$modal,
				$content,
				$close;

				// Center the modal in the viewport
				method.center = function () {
					var top, left;

					top = Math.max($(window).height() - $modal.outerHeight(), 0) / 2;
					left = Math.max($(window).width() - $modal.outerWidth(), 0) / 2;

					$modal.css({
						top:top + $(window).scrollTop(), 
						left:left + $(window).scrollLeft()
					});
				};

				// Open the modal
				method.open = function (settings) {
					$content.empty().append(settings.content);

					$modal.css({
						width: settings.width || 'auto', 
						height: settings.height || 'auto'
					});

					method.center();
					$(window).bind('resize.modal', method.center);
					$modal.show();
					$overlay.show();
				};

				// Close the modal
				method.close = function () {
					$modal.hide();
					$overlay.hide();
					$content.empty();
					$(window).unbind('resize.modal');
				};

				// Generate the HTML and add it to the document
				$overlay = $('<div id="overlay"></div>');
				$modal = $('<div id="modal"></div>');
				$content = $('<div id="content"></div>');
				$close = $('<a id="close" href="#">close</a>');

				$modal.hide();
				$overlay.hide();
				$modal.append($content, $close);

				$(document).ready(function(){
					$('body').append($overlay, $modal);						
				});

				$close.click(function(e){
					e.preventDefault();
					method.close();
				});

				return method;
			}());

			// Wait until the DOM has loaded before querying the document
			$(document).ready(function(){

			//	$.get('ajax.html', function(data){
			//		modal.open({content: data});
			//	});
				
				$('#close').click(function(e){
					$('#modal').hide();
					$('#overlay').hide();
					$('#content').empty();
					$(window).unbind('resize.modal');
				});
				
				$('a#howdy').click(function(e){
					modal.open({content: "Hows it going?"});
					e.preventDefault();
				});
			});
		</script>
</head>
<body>
	<%@include file="../fragments/menubar.jsp"%>
	<center style="margin-top: 150px;">
		<!-- <form method="post" id="handlerForm" action="welcome"> -->
		<form method="post" id="handlerForm" action="mofa">
			<div>
				<h2 style="color: #577BC4;">INITIAL PAGE</h2>
			</div>
			<div
				style="background-color: rgba(95, 177, 128, 0.7); color: white; margin: 20px; padding: 20px; width: 450px">
				<table style="width: 300px;">
					<tr>
						<td>Invitation Letter No</td>
						<td><input type="text" class="form-control" id="invitation_number"
							name="invitation_number" />
							<input type="hidden" value="${inviteBean.searchType}" name="searchType"/>
							<!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Sponser ID</td>
						<td><input type="text" class="form-control" id="sponser_id" 
						name="sponser_id" /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Applicant ID</td>
						<td><input type="text" class="form-control" id="applicant_id" 
							name="applicant_id" /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Server Type</td>
						<td><select id="siteType" name="siteType" class="form-control">
								<option value="LiveServer">LiveServer</option>
								<!-- <option value="UatServer">UatServer</option>
								<option value="TestServer">TestServer</option> -->
						</select></td>
					</tr>
					<tr>
						<td>Sponser Address</td>
						<td><input type="text" class="form-control" id="sponser_address"
							name="sponser_address" />
							<!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Sponser Name</td>
						<td><input type="text" class="form-control" id="sponser_name" name="sponser_name"
						 /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Sponser Phone</td>
						<td><input type="text" class="form-control" id="sponser_phone" name="sponser_phone"
							 /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Occupation</td>
						<td><input type="text" class="form-control" id="occupation" name="occupation"
							 /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Enjaz Acknowledgement</td>
						<td><input type="text" class="form-control" id="ezjazAck" name="ezjazAck"
							 /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td><input type="button" id="btnValidate" style="margin-top: 10px;"
							class="btn btn-primary" value="Read Mofa" /></td>
						<td><input type="button" id="btnEnjaz" style="margin-top: 10px;"
							class="btn btn-primary" value="Enjaz Fill" /></td>
					</tr>
				</table>
				<div align="left"><font size="2">*All fields are mandatory</font></div>
			</div>
		</form>
	</center>
	
<div id="overlay" style="display: none;"></div>
<div id="modal" style="width: auto; height: auto; top: 59px; left: 590.5px; display: none;"><div id="content">Hows it going?</div><a id="close" href="#">close</a></div>

</body>
<script>
$(document).ready(function() {
	// Read From Mofa and fill into Opsys
	$("#btnValidate").click(function(ev) {
		var formObj = $("#handlerForm");
		modal.open({content: "Hows it going?"});
		setTimeout(function() {
		      // Do something after 5 seconds
		}, 10000);
		//var postData = new Object();
		//postData.username="admin";
		//admin.password="Bure@u@)!#";
		//var myString = JSON.stringify(myObject);
		//var formURL = formObj
		//		.attr("action");
		$.ajax({
		    //url: 'http://10.81.221.25/webautofill/readmofa',
			url: 'http://localhost/webautofill/readmofa',
		    type: 'POST',
		    data: '{"search_type":"admin", "opsys_url":"Bure"}',
		  //	data: myString,
		    async: true,
			dataType: "json",
    		contentType: 'application/json',
		    success: function(jqXHR, textStatus, errorThrown){
		        console.log('Success' + textStatus);
		    },
		    error: function(jqXHR, textStatus, errorThrown){
		    	console.log("jqXHR - " + jqXHR.statusText + "\n textStatus - " + textStatus + "\n errorThrown - " + errorThrown);
		    }
		});
		
		});
	
	// Pass values to Enjaz and fill Acknowledgement
	$("#btnEnjaz").click(function(ev) {
		console.log("szdasdfsf");
		/* var btn = $(this);
		var formCtrl = btn.parent().parent().parent().parent().parent().parent();
		formCtrl.submit(); */
		var formObj = $("#handlerForm");
		var postData = formObj
				.serializeArray();
		var formURL = formObj
				.attr("action");
		$.ajax({
			type : 'POST',
			url : "fillenjaz",
			cache : false,
			data : postData,
			success : function(
					output) {
				console.log("Completed");
			},
			error : function(
					jqXHR,
					textStatus,
					errorThrown) {
				console.log("Failed");
			}
		});
		
		});
	
})
</script>
</html>

<!-- <html>
<body>
<form action="sample" method="POST">
First Name: <input type="text" name="first_name">
<br />
Last Name: <input type="text" name="last_name" />
<input type="submit" value="Submit" />
</form>
</body>
</html> -->
