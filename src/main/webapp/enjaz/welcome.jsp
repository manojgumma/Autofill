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
</head>
<body background="">
	<%@include file="../fragments/menubar.jsp"%>
	<center style="margin-top: 150px;">
		<form method="post" id="handlerForm" action="welcome">
			<div>
				<h2 style="color: #577BC4;">ENJAZ WEB AUTOMATION</h2>
			</div>
			<div
				style="background-color: rgba(95, 177, 128, 0.7); color: white; margin: 20px; padding: 20px; width: 450px">
				<table style="width: 300px;">
					<tr>
						<td>Invitation Letter No</td>
						<td><input type="text" class="form-control" id="invitation_number"
							name="invitation_number" /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Sponser ID</td>
						<td><input type="text" class="form-control" id="sponser_id" name="sponser_id" />
						<!-- <span class="mandatory">*</span> --></td>
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
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#spinner").bind("ajaxSend", function() {
							$(this).show();
						}).bind("ajaxStop", function() {
							$(this).hide();
						}).bind("ajaxError", function() {
							$(this).hide();
						});

						$("#btnValidate")
								.click(
										function(ev) {
											$("#errMsg").html("");
											var iLetter = ($("#invitation_number").val()).trim();
											var sponserId = ($("#sponser_id").val()).trim();
											var applicantId = ($("#applicant_id").val()).trim();
											var sType = ($("#siteType").val()).trim();
											if (iLetter == "" || sponserId =="" || applicantId=="" || sType=="") {
												$("#errMsg").html("All fields are mandatory");
												ev.preventDefault();
												ev.stopPropagation();
											} else {
												var formObj = $("#handlerForm");
												var postData = formObj
														.serializeArray();
												//var formURL = "http://localhost:8082/automation/";
												var formURL = formObj
														.attr("action");
												window.top
														.$(window.top.document)
														.trigger(
																'validate',
																{
																	'data' : 'Invitation details validation initiated',
																	'imgsrc' : '../assets/images/wait.png'
																});
												$
														.ajax({
															type : 'POST',
															url : formURL,
															cache : false,
															data : postData,
															success : function(
																	output) {
																//loadOrgID(output,usertype,emailID)
																// next ajax call - Read
																window.top
																		.$(
																				window.top.document)
																		.trigger(
																				'validate',
																				{
																					'data' : 'Invitation details validation completed',
																					'imgsrc' : '../assets/images/tick.png',
																					'colorcode' : 'green'
																				});
																window.top
																		.$(
																				window.top.document)
																		.trigger(
																				'read',
																				{
																					'data' : 'Read VISA details initiated',
																					'imgsrc' : '../assets/images/wait.png'
																				});
																$
																		.ajax({
																			type : 'GET',
																			url : "read",
																			cache : false,
																			success : function(
																					output) {
																				window.top
																						.$(
																								window.top.document)
																						.trigger(
																								'read',
																								{
																									'data' : 'Read VISA details completed',
																									'imgsrc' : '../assets/images/tick.png',
																									'colorcode' : 'green'
																								});
																				window.top
																						.$(
																								window.top.document)
																						.trigger(
																								'fill',
																								{
																									'data' : 'Fill VISA details initiated',
																									'imgsrc' : '../assets/images/wait.png'
																								});
																				// next ajax call -  Fill
																				console.log("postData =="+ JSON.stringify(postData));
																				$
																						.ajax({
																							type : 'POST',
																							url : "fill",
																							cache : false,
																							data : postData,
																							success : function(
																									output) {
																								window.top
																										.$(
																												window.top.document)
																										.trigger(
																												'fill',
																												{
																													'data' : 'Fill VISA details completed',
																													'imgsrc' : '../assets/images/tick.png',
																													'colorcode' : 'green'
																												});
																							},
																							error : function(
																									jqXHR,
																									textStatus,
																									errorThrown) {
																								window.top
																										.$(
																												window.top.document)
																										.trigger(
																												'fill',
																												{
																													'data' : 'Fill VISA details failed',
																													'imgsrc' : '../assets/images/error.png',
																													'colorcode' : 'red'
																												});
																								alert('Error: '
																										+ jqXHR.status
																										+ ':'
																										+ textStatus
																										+ ":"
																										+ errorThrown);
																							}
																						});
																			},
																			error : function(
																					jqXHR,
																					textStatus,
																					errorThrown) {
																				window.top
																						.$(
																								window.top.document)
																						.trigger(
																								'read',
																								{
																									'data' : 'Read VISA details failed',
																									'imgsrc' : '../assets/images/error.png',
																									'colorcode' : 'red'
																								});
																				alert('Error: '
																						+ jqXHR.status
																						+ ':'
																						+ textStatus
																						+ ":"
																						+ errorThrown);
																			}
																		});
															},
															error : function(
																	jqXHR,
																	textStatus,
																	errorThrown) {
																window.top
																		.$(
																				window.top.document)
																		.trigger(
																				'fill',
																				{
																					'data' : 'Invitation details validation failed',
																					'imgsrc' : '../assets/images/error.png',
																					'colorcode' : 'red'
																				});
																alert('Error: '
																		+ jqXHR.status
																		+ ':'
																		+ textStatus
																		+ ":"
																		+ errorThrown);
																//   hide_loader();
															}
														});
											}
										});

					});
	function getContextPath() {
		var ctx = window.location.pathname, path = '/' !== ctx ? ctx.substring(
				0, ctx.indexOf('/', 1) + 1) : ctx;
		return path + (/\/$/.test(path) ? '' : '/');
	}
	function clickme() {

		// $('#spinner').show();
		//show_loader("Loading Registration Number");
		//var url ="/";
		//var data = "orgName=" + encodeURIComponent(orgName)+"&&usertype="+encodeURIComponent(usertype)+"&&orgID="+encodeURIComponent(orgID);

		var formObj = jquery("#handlerForm");
		var postData = formObj.serializeArray();
		//var formURL = getContextPath();
		var formURL = formObj.attr("action");
		$.ajax({
			type : 'POST',
			url : formURL,
			cache : false,
			data : data,
			success : function(output) {
				//loadOrgID(output,usertype,emailID)
				// next ajax call
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error: ' + jqXHR.status + ':' + textStatus + ":"
						+ errorThrown);
				//   hide_loader();
			}
		});

		window.top.$(window.top.document).trigger('validate', {
			'data' : 'Invitation details validated'
		});
		window.top.$(window.top.document).trigger('read', {
			'data' : 'Read deatials - is in-progress'
		});
		window.top.$(window.top.document).trigger('authenticate', {
			'data' : ''
		});
		window.top.$(window.top.document).trigger('filldata', {
			'data' : ''
		});
		return false;
	}
</script>
</html>