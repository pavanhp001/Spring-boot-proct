$(document).ready(function(){
	$("#custEdit").click(editCustInfomation);
	
});


$.extend({ alert: function (message, title,height,width) {
    $("<div></div>").attr({'class': 'alert'}).html(message).dialog( {
      buttons: { "Ok": function () { $(this).dialog("close"); } },
      close: function (event, ui) { $(this).remove(); },
      resizable: false,
      title: title,
      modal: true,
      height : height,
        width : width,
        zIndex : 10000
    }).text(message);
  }
});


var isDate = true;
var isPhone = true;
var isValidEmail = true;
var pageUrl = window.location.href;

var checkDate = function(){
	var moveDate = $("input#moveDate").val();
    moveDate = moveDate.replace(/\//g, "");
    
    
    if(pageUrl.indexOf("nonmovers")>=0 || pageUrl.indexOf("webCallFlow")>=0)
	{
		isDate = true;
		return true;
	}
	
	if(moveDate.length != 8 && moveDate.length!=0){
		alert("Invalid Move-In Date format.  Please enter date in mm/dd/yyyy format.");
		isDate = false;
		return false;
	}
	else
	{
		if( !autoChange() )
		{
			isDate = false;
			return false;
		}
	}
	isDate = true;
	return true;
}
function isPastDate(dtStr){
	var curr = new Date();
	var calendar=new Date(dtStr);
	if((calendar.valueOf() - curr.valueOf()) <= 0)
	{ 
		return true;
	}
	return false;
}
var checkPhone = function(){
	autoChangePhone();
}

var autoChange = function(){
	isDate = false;
	var moveDate = $("input#moveDate").val();
	var date_re = new RegExp("^\\d{2}\\d{2}\\d{4}$");
	var calDate_re = new RegExp("^\\d{2}/\\d{2}/\\d{4}$");
	var defaultValue = $('input#moveDate').prop("defaultValue");

	moveDate = moveDate.replace(/\//g, "");
	
	if(moveDate.length > 8){
		alert("Invalid Move-In Date format.  Please enter date in mm/dd/yyyy format.");
		isDate = false;
	}
	
	if(moveDate.length == 8){
		if(moveDate.match(date_re)){
			var mm = moveDate.slice(0,2);
			var dd = moveDate.slice(2,4);
			var yyyy = moveDate.slice(4);
			var month = parseInt(mm,10); // was gg (giorno / day)
			var date = parseInt(dd,10); // was mm (mese / month)
			var year = parseInt(yyyy,10); // was aaaa (anno / year)
			var xdata = new Date(yyyy,month-1,dd);
			
			if ( ( xdata.getFullYear() == year ) && ( xdata.getMonth () == month - 1 ) && ( xdata.getDate() == date ) ){
				moveDate =mm+"/"+dd+"/"+yyyy;
				isDate = true;
			} else{
				alert("Invalid Move-In Date format.  Please enter date in mm/dd/yyyy format.");
				isDate = false;
			}
		}else if(!moveDate.match(calDate_re)){
			alert("Invalid Move-In Date format.  Please enter date in mm/dd/yyyy format.");
			isDate = false;
		}
		if(isDate)
			$("input#moveDate").val(moveDate);
		else
			$("input#moveDate").val(defaultValue);
		$("input#moveDate").blur();
		return isDate;
	}
}

function autoChangePhoneData(){
	var phone = $("input#bestPhoneContact").val();
	if(phone.indexOf("-") != -1){
		phone = phone.replace("-","");
		phone = phone.replace("-","");
	}
	phone = phone.trim();
	var npa = '', nxx = '';
	var userInput = phone;
	var inputValue = phone;
	var val = inputValue.replace(/\D/g, '');
	var newVal = '';
	var last4 = val;
	if(val.length >=6) {
		npa = val.substring(0, 3);
		nxx = val.substring(3, 6);
		newVal += npa + '-' + nxx+ '-';
		last4 = val.substring(6);
	} else if(val.length >=3) {
		npa = val.substring(0, 3);
		newVal += npa + '-';
		last4 = val.substring(3);
	}
	newVal += last4; 
	//If user types '-' after 3rd digit or 6th digit keep it
	if((userInput.length == 4) && (userInput.charAt(3) == '-')) {
		newVal += '-';
	} else if((userInput.length == 8) && (userInput.charAt(7) == '-'))  {
		newVal += '-';
	} 
/*	if(phone.length == 10){
		phone = phone.slice(0,3)+"-"+phone.slice(3,6)+"-"+phone.slice(6);
	}*/
	$("input#bestPhoneContact").val(newVal);
}

var autoChangePhone = function(){
	isPhone = true;
	var phone = $("input#bestPhoneContact").val();
	if(phone.length>0){
		if(phone.indexOf("-") != -1){
			phone = phone.replace("-","");
			phone = phone.replace("-","");
		}
		phone = phone.trim();
	
		if(phone.length == 10){
			phone = phone.slice(0,3)+"-"+phone.slice(3,6)+"-"+phone.slice(6);
			isPhone = true;
		}
		else{
			 alert("Invalid Phone format.  Please enter 10 digit number.");
			 isPhone = false;
		}
	}/*else{
		 alert("Best Phone is required.");
		 isPhone = false;
	}*/
	//$("input#bestPhoneContact").val(phone);
	return isPhone;
}

var editCustInfomation = function(){
	updateInputFields(true);
}

var validateCustEmail = function(){
	isValidEmail = false;
	var custEmail = $("input#bestEmailContact").val().trim();
	if(custEmail.length>0){
		var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		if(!email_regex.test(custEmail)){ 
			$.alert("Please enter valid Email", "Alert", 200, 300);
			isValidEmail = false;
			return false;  
		}
	}/*else{
		alert("Email is required."); 
		isValidEmail = false;
		return false;  
	}*/
	isValidEmail = true;
	return true;
}


var saveCustomer = function(){
	if(checkDate() && validateCustEmail() && autoChangePhone()){
		//To save the html on edit customer
		savePageHtml(false, "");
		
		var path = $("#contextPath").val();
		var url = path+"/rest/scart/editCustomerInfo";
		var data = {};
		
		data["bestEmailContact"] = $("input#bestEmailContact").val().trim();
		data["moveDate"] = $("input#moveDate").val();
		data["bestPhoneContact"] = $("input#bestPhoneContact").val();
		data["customerId"] = $("input#customerId").val();
		data["orderIdForMoveDate"] = $("input#orderIdForMoveDate").val();
	
		$("#custSaveCancel").css("display", "none");
		$(".custLoader").css("display", "block");
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			complete: onCustSaveComplete
		});
	}
	return false;
}

var onCustSaveComplete = function(data){
	if(data=="sessionTimeOut"){
		var path = $("#contextPath").val();
		window.location.href = path+"/salescenter/session_timeout";
	}else if(data.responseText=="sessionTimeOut"){
		var path = $("#contextPath").val();
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		updateInputFields(false);
	}
}

var cancelUpdate = function(){
	$("#custSave").unbind("click");
	$("#custCancel").unbind("click");
	
	$("#custSaveCancel").css("display", "none");
	$(".custLoader").css("display", "block");
	
	updateInputFields(false);
	$("#moveDate").val($("#moveDateHid").val());
	$("#bestEmailContact").val($("#bestEmailContactHid").val());
	$("#bestPhoneContact").val($("#bestPhoneContactHid").val());
}

function updateInputFields(status){
	if(status){
		$("#bestEmailContactHid").val($("#bestEmailContact").val());
		$("#bestPhoneContactHid").val($("#bestPhoneContact").val());
		$("#moveDateHid").val($("#moveDate").val());
		$("#custSave").unbind("click");
		$("#custCancel").unbind("click");
		
		$("#custEdit").css("display", "none");
		$("#custSaveCancel").css("display", "block");
		
		$("#custSave").click(saveCustomer);
		$("#custCancel").click(cancelUpdate);
		
		$("#moveDate").prev().addClass("custLLabel");
		if(pageUrl.indexOf("nonmovers")>=0 || $("#coustmerMove").val()== "No")
		{
			$('#moveDate').attr('disabled', 'disabled');
		}else{
			$("#moveDate").removeAttr("disabled");
         }
		
		$("#moveDate").attr("class", "inputStyleEna");
		//$("#moveDate").datepicker();
		
		$("#bestEmailContact").prev().addClass("custLLabel");
		$("#bestEmailContact").removeAttr("disabled");
		$("#bestEmailContact").attr("class", "inputStyleEna");
		//$("#bestEmailContact").css("width", "100px");
		
		$("#bestPhoneContact").prev().addClass("custLLabel");
		$("#bestPhoneContact").removeAttr("disabled");
		$("#bestPhoneContact").attr("class", "inputStyleEna");
	} else {
		
		$(".custLoader").css("display", "none");
		$("#custEdit").css("display", "block");
		
		$("#moveDate").prev().removeClass("custLLabel");
		$("#moveDate").attr("class", "inputStyleDis");
		$("#moveDate").attr("disabled", "disabled");
		
		$("#bestEmailContact").prev().removeClass("custLLabel");
		$("#bestEmailContact").attr("class", "inputStyleDis");
		$("#bestEmailContact").attr("disabled", "disabled");
		//$("#bestEmailContact").css("width", "180px");
		
		$("#bestPhoneContact").prev().removeClass("custLLabel");
		$("#bestPhoneContact").attr("class", "inputStyleDis");
		$("#bestPhoneContact").attr("disabled", "disabled");
	}
}