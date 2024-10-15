<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Initiator</title>
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
    .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0,0,0);
            background-color: rgba(0,0,0,0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: 20% auto;
            padding: 10px;
            border: 1px solid #888;
            width: 300px;
            text-align: center;
        }
        .loader {
            border: 8px solid #f3f3f3;
            border-radius: 50%;
            border-top: 8px solid #3498db;
            width: 60px;
            height: 60px;
            animation: spin 2s linear infinite;
            margin: 20px auto;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        .success-message {
            color: #428bca; /* Blue color */
            font-weight: bold;
            font-size: 16px;
        }
        .error-message {
            color: #e74c3c; /* Red color */
            font-weight: bold;
            font-size: 16px;
        }
        .info-message {
           color: #428bca; /* Blue color */
            font-weight: bold;
            font-size: 16px;
        }
.modal-button {
    margin-top: 10px;
    padding: 8px 16px;
    background-color: #428bca;
    color: white;
    border: 1px solid #4CAF50;
    border-radius: 8px; 
    cursor: pointer;
}
</style>
</head>
<body background="">
	<%@include file="../fragments/menubar.jsp"%>
	<center style="margin-top: 150px;">
		<!-- <form method="post" id="handlerForm" action="welcome"> -->
		<form method="post" id="handlerForm" action="init">
		<!-- <form method="post" id="handlerForm"> -->
			<div>
				<h2 style="color: #577BC4;">INITIAL PAGE</h2>
			</div>
			<!-- <div
				style="background-color: rgb(95, 177, 128, 0.7); color: white; margin: 20px; padding: 20px; width: 450px"> -->
				<div
				style=" color: white; margin: 20px; padding: 20px; width: 450px">
				<table style="width: 300px;">
					<%-- <tr>
						<td>Invitation Letter No</td>
						<td><input type="text" class="form-control" id="invitation_number"
							value="${inviteBean.letterNo}" name="letterNo" />
							<input type="hidden" value="${inviteBean.searchType}" name="searchType"/>
							<!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Sponser ID</td>
						<td><input type="text" class="form-control" id="sponser_id" value="${inviteBean.sponsorID}" 
						name="sponsorID" /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Applicant ID</td>
						<td><input type="text" class="form-control" id="applicant_id" value="${inviteBean.applicantID}"
							name="applicantID" /><!-- <span class="mandatory">*</span> --></td>
					</tr>
					<tr>
						<td>Server Type</td>
						<td><select id="siteType" name="siteType" class="form-control">
								<option value="LiveServer">LiveServer</option>
								<!-- <option value="UatServer">UatServer</option>
								<option value="TestServer">TestServer</option> -->
						</select></td>
					</tr> --%>
					<tr>
						<!-- <td></td> -->
						<td colspan="2" align="center"><input type="submit" id="btnValidate" style="margin-top: 10px;"
							class="btn btn-primary" value="LAUNCH OPSYS"/></td>
					</tr>
					<tr>
							<td style="text-align: center; color:#428bca;"><%= pageContext.getServletContext().getAttribute("version") %></td>
					
					</tr>
				</table>
				<!-- <div align="left"><font size="2">*All fields are mandatory</font></div> -->
			</div>
		</form>
	</center>
<div id="launchModal" class="modal">
        <div class="modal-content">
            <div id="loader" class="loader"></div>
            <p id="modalMessage">Initializing OpSys, please wait a moment...</p>
            <button id="okButton" class="modal-button" style="display: none;" onclick="closeModal()">OK</button>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#handlerForm').on('submit', function(e) {
                e.preventDefault();
                $('#launchModal').show();
                $('#loader').show();
                $.ajax({
                    type: 'POST',
                    url: 'init',
                    success: function(response, status, xhr) {
                console.log(response);
                $('#loader').hide();
                if (xhr.status === 208) { // HttpStatus.ALREADY_REPORTED is 208
                    $('#modalMessage').text('OpSys has already been launched. No further action is required.').addClass('info-message').removeClass('error-message success-message');
                } else {
                    $('#modalMessage').text('OpSys launch completed successfully.').addClass('success-message').removeClass('error-message info-message');
                }
                $('#okButton').show(); // Show the OK button after success
            },
            error: function(xhr, status, error) {
                $('#loader').hide();
                $('#modalMessage').text('Error in launching OpSys.').addClass('error-message').removeClass('success-message info-message');
                $('#okButton').show(); // Show the OK button after error
            }
                });
            });
        });

        function closeModal() {
        	  $('#launchModal').hide(); // Hide the modal
        	  $('#modalMessage').text('Initializing OpSys, please wait a moment...').removeClass('success-message error-message info-message'); // Reset message and classes
        	  $('#okButton').hide(); // Hide the OK button        
              }
    </script>

</body>
<script>
$(document).ready(function() {
	// Read From Mofa and fill into Opsys
	/* $("#btnValidate").click(function(ev) {
		modal.open({content: "Hows it going?"});
		$.ajax({
			url: 'http://localhost/webautofill/readmofa',
		    type: 'POST',
		    data: '{"search_type":"admin", "opsys_url":"Bure"}',
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
	
		}); */
	
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
