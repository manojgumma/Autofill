function test(){
	var element = $('.alert');
	if($('.alert').length && !$('#EnjazNumber').length > 0){
		if($('.alert').length == 1) {
			chrome.runtime.sendMessage({text: $('.alert').html()}, function(response) {
		//console.log(response.farewell);
		});		
		} else {
			chrome.runtime.sendMessage({text: $('.alert.alert-success').html()}, function(response) {
			//console.log(response.farewell);
			});		
		}
		
	}else if($('#EnjazNumber').length > 0){
		chrome.runtime.sendMessage({text: $('#EnjazNumber').val()}, function(response) {
		//console.log(response.farewell);
		});		
	}else{
		chrome.runtime.sendMessage({text: 'blink'}, function(response) {
		//console.log(response.farewell);
		});	
	
	}
	
	$(document).on( "click", "#CompanyNumber", function() {
          
	var insCompanyName = $("input:radio[name=CompanyNumber]:checked").parent().text();
	var insCompanyAmt = $("input:radio[name=CompanyNumber]:checked").parent().siblings().text();
			
			console.log("insCompanyName "+insCompanyName);
			console.log("insCompanyAmt "+insCompanyAmt);
	chrome.runtime.sendMessage({company: insCompanyName,amount: insCompanyAmt}, function(response) {
		//console.log(response.farewell);
		});	
});
	
}
	
test();