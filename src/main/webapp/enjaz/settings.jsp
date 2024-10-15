<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Settings</title>
<%@include file="../fragments/resources.jsp"%>
</head>
<body background="">

	<%@include file="../fragments/menubar.jsp"%>

	<div class="bs-example">
		<div>
		
		<h4 id="status_msg" style="color: ${msgClass}"><span>${Success}</span>
		</h4>	
		
		</div>
		
		<h4 style="color: red;" id="errMsg"><span>${Error}</span></h4>
		
		<h4 style="color: red;" id="err_Msg"><span>Fill all mandatory fields</span></h4>
		</div>
		<div id="responseDiv"></div>
		<div class="panel-group" id="accordion">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title" >
						<!-- <input type="radio" checked="checked" name="server" value="live">
						<a data-toggle="collapse" data-parent="#accordion" class="aExpand"
							href="#collapseOne">1. Live Enjaz</a> -->
							OPSYS AUTO-FILL SETTINGS
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<form class="form-horizontal opsys" method="post" id="settingsForm" action="settings">
						<div class="form-group">
							<input type="hidden" class="site_type" name="siteType" value="LiveServer"/>	
							<label for="enjazUrl" class="control-label col-xs-2">OpSys URL</label>
							<div class="col-xs-10">
								<input type="text" name="opsys_url" class="form-control clear" id="enjazUrl"
									placeholder="Opsys Enjaz Url" value="${settings.opsys_url}">
							</div>
						</div>
						<div class="form-group">
							<label for="enzUname" class="control-label col-xs-2">MoFA User Name</label>
							<div class="col-xs-10">
								<input type="text" class="form-control clear" id="enzUname"
									name="enj_user_name" placeholder="User name" value="${settings.enj_user_name}">
							</div>
						</div>
						<div class="form-group">
							<label for="enzPassword" class="control-label col-xs-2">MoFA Password</label>
							<div class="col-xs-10">
								<input type="password" class="form-control clear" id="enzPassword"
									name="enj_password" placeholder="Password" value="${settings.enj_password}">
							</div>
						</div>
						<div class="form-group">
							<label for="enzPay" class="control-label col-xs-2">MoFA Payment Password</label>
							<div class="col-xs-10">
								<input type="password" class="form-control clear" id="enzPay"
									name="enzPayPassword" placeholder="Password" value="${settings.enzPayPassword}">
							</div>
						</div>
						<div class="form-group">
							<input type="hidden" id="print" value="${settings.printLang}" /> 
							<label for="enzPrint" class="control-label col-xs-2">MoFA Print Language</label>
							<div class="col-xs-10" style="margin-top:5px;">
								<input type="radio" name="printLang" id="device1" value="English" />
								<span style="margin-left: 3px;">English</span>
								<input style="margin-left: 12px;" type="radio" name="printLang" id="device2" value="Arabic" />
								<span style="margin-left: 3px;">Arabic</span>
							</div>
						</div>
						<!-- Mofa website language -->
						<div class="form-group">
							<input type="hidden" id="mofalanguage" value="${settings.mofaLang}" /> 
							<label for="enzWeb" class="control-label col-xs-2">MoFA Website Language</label>
							<div class="col-xs-10" style="margin-top:5px;">
								<input type="radio" name="mofaLang" id="dev1" value="English" />
								<span style="margin-left: 3px;">English</span>
								<input style="margin-left: 12px;" type="radio" name="mofaLang" id="dev2" value="Arabic" />
								<span style="margin-left: 3px;">Arabic</span>
							</div>
						</div>
						<!-- Launch OpSyS with -->
						<div class="form-group">
							<input type="hidden" id="launchopsyswith" value="${settings.launchOpsys}" /> 
							<label for="launchOpSys" class="control-label col-xs-2">Download chrome driver</label>
							<div class="col-xs-10" style="margin-top:5px;">
								<input type="radio" name="launchOpsys" id="autoDownload" value="Auto-Download" />
								<span style="margin-left: 3px;">Automatic</span>
								<input style="margin-left: 12px;" type="radio" name="launchOpsys" id="setManually" value="Set Manually" />
								<span style="margin-left: 3px;">Manual</span>
							</div>
						</div>
						<!-- Mofa URL Start -->
						<div class="form-group">
							<label for="enzPay" class="control-label col-xs-2">MoFA URL</label>
							<div class="col-xs-10">
								<input type="text" class="form-control clear" id="mofaURL"
									name="mofaURL" placeholder="MoFA URL" value="${settings.mofaURL}">
							</div>
						</div>
						<!-- Mofa URL End -->
						<!-- Enjaz URL Start -->
						<div class="form-group">
							<label for="enzPay" class="control-label col-xs-2">MoFA Service URL</label>
							<div class="col-xs-10">
								<input type="text" class="form-control clear" id="enjazURL"
									name="enjazURL" placeholder="Enjaz URL" value="${settings.enjazURL}">
							</div>
						</div>
						<div class="form-group">
							<label for="enzRedirect" class="control-label col-xs-2">MoFA Service Redirect URL</label>
							<div class="col-xs-10">
								<input type="text" class="form-control clear" id="redirectUrl"
									name="redirectUrl" placeholder="Enjaz URL" value="${settings.redirectUrl}">
							</div>
						</div>
						<!-- Enjaz URL End -->
						<!-- <div class="form-group">
							<label for="enzRePassword" class="control-label col-xs-2">Re-Type Password</label>
							<div class="col-xs-10">
								<input type="password" class="form-control clear" id="enzRePassword"
									placeholder="Re-Type Password">
							</div>
						</div> -->
						<div class="form-group">
							<label for="submitBtn" class="control-label col-xs-2"></label>
							<div class="col-xs-10">
								<input type="button" class="btn btn-primary settings_btn" value="SUBMIT" />
							</div>
						</div>
						<div><font size="2">*All fields are mandatory</font></div>
					</form>
				</div>
			</div>
			<!-- <div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<input type="radio" name="server" value="uat">
						<a data-toggle="collapse" data-parent="#accordion"
							class="aExpand" href="#collapseTwo">2. UAT-1 Enjaz</a>
					</h4>
				</div>
				<div id="collapseTwo" class="panel-collapse collapse">
					<form class="form-horizontal opsys" method="post" action="settings">
						<div class="form-group">
							<input type="hidden" class="site_type" name="siteType" value="UatServer"/>
							<label for="enjazUrl" class="control-label col-xs-2">Opsys Enjaz Url</label>
							<div class="col-xs-10">
								<input type="text" name="opsys_url" class="form-control clear" id="enjazUrl"
									placeholder="Opsys Enjaz Url">
							</div>
						</div>
						<div class="form-group">
							<label for="enzUname" class="control-label col-xs-2">User name</label>
							<div class="col-xs-10">
								<input type="text" class="form-control clear" id="enzUname"
									name="enj_user_name" placeholder="User name">
							</div>
						</div>
						<div class="form-group">
							<label for="enzPassword" class="control-label col-xs-2">Password</label>
							<div class="col-xs-10">
								<input type="password" class="form-control clear" id="enzPassword"
									name="enj_password" placeholder="Password">
							</div>
						</div>
						<div class="form-group">
							<label for="enzRePassword" class="control-label col-xs-2">Re-Type Password</label>
							<div class="col-xs-10">
								<input type="password" class="form-control clear" id="enzRePassword"
									placeholder="Re-Type Password">
							</div>
						</div>
						<div class="form-group">
							<label for="submitBtn" class="control-label col-xs-2"></label>
							<div class="col-xs-10">
								<input type="button" class="btn btn-primary settings_btn" value="SUBMIT" />
							</div>
						</div>
						<div><font size="2">*All fields are mandatory</font></div>
					</form>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<input type="radio" name="server" value="test">
						<a data-toggle="collapse" data-parent="#accordion"
							class="aExpand" href="#collapseThree">3. Test Enjaz</a>
					</h4>
				</div>
				<div id="collapseThree" class="panel-collapse collapse">
					<form class="form-horizontal opsys" method="post" action="settings">
						<div class="form-group">
							<input type="hidden" class="site_type" name="siteType" value="TestServer"/>
							<label for="enjazUrl" class="control-label col-xs-2">Opsys Enjaz Url</label>
							<div class="col-xs-10">
								<input type="text" name="opsys_url" class="form-control clear" id="enjazUrl"
									placeholder="Opsys Enjaz Url">
							</div>
						</div>
						<div class="form-group">
							<label for="enzUname" class="control-label col-xs-2">User name</label>
							<div class="col-xs-10">
								<input type="text" class="form-control clear" id="enzUname"
									name="enj_user_name" placeholder="User name">
							</div>
						</div>
						<div class="form-group">
							<label for="enzPassword" class="control-label col-xs-2">Password</label>
							<div class="col-xs-10">
								<input type="password" class="form-control clear" id="enzPassword"
									name="enj_password" placeholder="Password">
							</div>
						</div>
						<div class="form-group">
							<label for="enzRePassword" class="control-label col-xs-2">Re-Type Password</label>
							<div class="col-xs-10">
								<input type="password" class="form-control clear" id="enzRePassword"
									placeholder="Re-Type Password">
							</div>
						</div>
						<div class="form-group">
							<label for="submitBtn" class="control-label col-xs-2"></label>
							<div class="col-xs-10">
								<input type="button" class="btn btn-primary settings_btn" value="SUBMIT" />
							</div>
						</div>
						<div><font size="2">*All fields are mandatory</font></div>
					</form>
				</div>
			</div> -->
		</div>
	</div>
</body>

<script type="text/javascript">
$(document).ready(function() {
	$("#err_Msg").css("display","none");
	$(".settings_btn").click(function (evnt) {
		$("#err_Msg").css("display","none");
		$("#errMsg").html("");
		$("#status_msg").html("");
		var btnObj = $(this);
		var formObj = btnObj.parent().parent().parent();
		
		var opsysUrl = (formObj.find("#enjazUrl").val()).trim();
		var uName = (formObj.find("#enzUname").val()).trim();
		var passwd = (formObj.find("#enzPassword").val()).trim();
		var paypass = (formObj.find("#enzPay").val()).trim();
		var mofa_URL = (formObj.find("#mofaURL").val()).trim();
		var enjaz_URL = (formObj.find("#enjazURL").val()).trim();
		var redirectUrl = (formObj.find("#redirectUrl").val()).trim();
		
		// var repswd = (formObj.find("#enzRePassword").val()).trim();
		console.log(opsysUrl+"="+uName+"="+passwd+"="+paypass);
		if(opsysUrl =="" || uName =="" || passwd =="" || paypass == "") {
			$("#errMsg").html("All fields are mandatory");
			$("#err_Msg").css("display","block");
			evnt.preventDefault();
			evnt.stopPropagation();
		} else {
				$("#settingsForm").submit();
			}
	}); 
	
	if($("#print").val() == "English"){
		$("#device1").attr("checked","checked");
	} else if($("#print").val() == "Arabic"){
		$("#device2").attr("checked","checked");
	}
	if($("#mofalanguage").val() == "English"){
		$("#dev1").attr("checked","checked");
	} else if($("#mofalanguage").val() == "Arabic"){
		$("#dev2").attr("checked","checked");
	}
	if($("#launchopsyswith").val() == "Auto-Download"){
		$("#autoDownload").attr("checked","checked");
	} else if($("#launchopsyswith").val() == "Set Manually"){
		$("#setManually").attr("checked","checked");
	}
});

</script>
</html>