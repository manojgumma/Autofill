<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
</head>

<body background="">
	<%@include file="../fragments/menubar.jsp"%>
	<center style="margin-top: 150px;">
		<form method="post" id="handlerForm" action="sample" target="_blank">
			<div>
				<h2 style="color: #577BC4;">ENJAZ WEB AUTOMATION</h2>
			</div>
			<div
				style="background-color: rgba(95, 177, 128, 0.7); color: white; margin: 20px; padding: 20px; width: 450px">
				<table style="width: 300px;">
					<tr>
						<td>Invitation Letter No</td>
						<td><input type="text" class="form-control" id="invitation_number"
							value="${inviteBean.letterNo}" name="letterNo" />
							<input type="hidden" value="${inviteBean.searchType}" name="searchType"/>
							<!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Sponser ID</td>
						<td><input type="text" class="form-control" id="sponser_id" value="${inviteBean.sponser_id}" 
						name="sponser_id" /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Applicant ID</td>
						<td><input type="text" class="form-control" id="applicant_id" value="${inviteBean.applicant_id}"
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
						<td></td>
						<td><input type="button" id="btnValidate" style="margin-top: 10px;"
							class="btn btn-primary" value="SUBMIT" /></td>
					</tr>
				</table>
				<div align="left"><font size="2">*All fields are mandatory</font></div>
			</div>
			
			<div>
					<h4 style="color: red;" id="errMsg"></h4>
			</div>
			<div id="spinner" class="spinner" style="display: none;">
				<img id="img-spinner" src="${pageContext.request.contextPath}/assets/images/spinner_16.gif" alt="Loading" />
			</div>
		</form>
	</center>
</body>
<script>

$(document).ready(function() {
	$("#btnValidate").click(function(ev) {
		console.log("szdasdfsf");
		var btn = $(this);
		var formCtrl = btn.parent().parent().parent().parent().parent().parent();
		formCtrl.submit();
		});
})

</script>
</html>