/*chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
		 var textmsg = request.text
console.log("textmsg"+textmsg)
console.log("sender"+sender.tab.url)
 if(request.text=='blink' && (sender.tab.url!='https://services.ksavisa.sa/SmartForm') ){
		chrome.windows.update(chrome.windows.WINDOW_ID_CURRENT, {drawAttention: true});
}else if(request.text!='blink' && sender.tab.url =='https://services.ksavisa.sa/SmartForm'){
	alert("Enjaz Save");	
	
	var x = new XMLHttpRequest();
		x.open('GET', 'http://localhost:8085/webautofill/enjazsave/'+sender.tab.index+"/"+request.text+'/no');
		x.send();
}else if(request.text!='blink' && sender.tab.url =='https://services.ksavisa.sa/Payment/GetApp'){
	var x = new XMLHttpRequest();
	x.open('GET', 'http://localhost:8085/webautofill/enjazsave/'+sender.tab.index+"/"+request.text+'/no');
	x.send();
}
else if(sender.tab.url.includes('https://services.ksavisa.sa/SmartForm/TraditionalApp')){
		var x = new XMLHttpRequest();
		x.open('GET', 'http://localhost:8085/webautofill/enjazsave/'+sender.tab.index+"/"+request.text+'/yes');
		x.send();

}else if(sender.tab.url.includes('https://services.ksavisa.sa/SmartForm/HealthInsuranceInfo/')){
		var x = new XMLHttpRequest();
		x.open('GET', 'http://localhost:8085/webautofill/fillapplicationno/'+sender.tab.index+"/"+request.text);
		x.send();
}else if(sender.tab.url.includes('https://services.ksavisa.sa/SmartForm/HealthInsurance/')){
		var x = new XMLHttpRequest();
		var fee = request.amount;
		x.open('GET', 'http://localhost:8085/webautofill/fillinsurancedetails/'+sender.tab.index+"/"+request.company+"/"+fee.replace('.','point'));
		x.send();
}		 
	//sendResponse({farewell: sender.tab.index});
	sendResponse({farewell:request.text});
});*/
chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
	//var textmsg = request.text;
	//alert("Enjaz Save"+sender.tab.url.includes('/SmartForm/HealthInsurance/'));
//	alert("company"+(sender.tab.url.includes('/SmartForm/HealthInsurance/') && request.company != 'undefiend'));
	//alert("test"+(sender.tab.url.includes('/SmartForm/HealthInsurance') && request.company == 'undefiend'));
	//alert("Enjaz Save"+ (request.text=='undefiend'));
			 
	if(sender.tab.url.includes('/SmartForm/HealthInsuranceInfo') && request.company == null){
//	alert("3" +sender.tab.url);
		var x = new XMLHttpRequest();
		x.open('GET', 'http://localhost:8085/webautofill/fillapplicationno/'+sender.tab.index+"/"+request.text);
		x.send();
    }
	if(sender.tab.url.includes('/Payment/GetApp')){
//	 alert("1" +sender.tab.url);
	var x = new XMLHttpRequest();
	x.open('GET', 'http://localhost:8085/webautofill/enjazsave/'+sender.tab.index+"/"+request.text+'/no');
	x.send();
}
if(sender.tab.url.includes('/SmartForm/TraditionalApp')){
	 alert("2" +sender.tab.url);
		var x = new XMLHttpRequest();
		x.open('GET', 'http://localhost:8085/webautofill/enjazsave/'+sender.tab.index+"/"+request.text+'/yes');
		x.send();
}
	
	if(sender.tab.url.includes('/SmartForm/HealthInsurance/') && request.company != null){
	//alert("4" +sender.tab.url);
		var x = new XMLHttpRequest();
		var fee = request.amount;
		x.open('GET', 'http://localhost:8085/webautofill/fillinsurancedetails/'+sender.tab.index+"/"+request.company+"/"+fee.replace('.','point'));
		x.send();
}
	
	
});
