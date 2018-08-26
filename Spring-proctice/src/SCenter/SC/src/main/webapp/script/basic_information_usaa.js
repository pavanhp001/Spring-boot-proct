function goToConfirm(){
	try{
		$("span.missingFields").remove();
		$("div#validatorMsg").css('display','none');
		
		var fname = $('input#firstName').val();
		var lname = $('input#lastName').val();
		var address1 = $('input#address1').val();
		var city = $('input#city').val();
		var zipcode = $('input#zipcode').val();
		var state = $('select#stateList').val();
		var dwellingtype = $('select#serviceAddressTypeList').val();
		var ownership = $('select#rentOwnList').val();
		var moveDate = $('input#datepicker').val();
		var gasDate = $('input#datepickerGas').val();
		var eleDate = $('input#datepickerElectric').val();
		var unitNum = $('input#unitNumber').val();
		var unitType = $('select#unitTypeList').val();
		var customerMove = $('select#customerMove').val();
		var usaaId = $('input#usaaId').val();
		
		/*
		 * Below Regular Expression will not forwarding to next for given some pattern addresses. 
		 * i.e.,( PO Box, P O Box, P. O. Box, P.O.Box, Post Box, Post Office Box, Post Box #, po vbox fgdgf, po vbox, po bin, post bin, post bin sd )
		*/
		var poboxExp = /([\w\s*\W]*(P(OST)?.?\s*((O(FF(ICE)?)?)?.?\s*(B(IN|OX))|B(OX))+))[\w\s*\W]*/i;
		
		var stat = true;
		var poStat=true;
		if (fname == null || fname == "" || fname == "First Name"){
			 $('input#firstName').after($('<span class="missingFields">*</span>'));
			 //$('input#firstName').focus();
			 stat = false;
		} 
		
		if(lname == null || lname == "" || lname == "Last Name"){
			 $('input#lastName').after($('<span class="missingFields">*</span>'));
			 //$('input#lastName').focus();
			 stat = false;	
		} 
		
		if(address1 == null || address1 == "" ||address1=="Address 1"){
			 $('input#address1').after($('<span class="missingFields">*</span>'));
			 //$('input#address1').focus();
			 stat = false;
		}
		
		if(address1.match(poboxExp)){
			$('input#address1').after($('<span class="missingFields">*</span>'));
			poStat = false;
		}
		
		if(city == null || city == "" || city=="City"){
			 $('input#city').after($('<span class="missingFields">*</span>'));
			 //$('input#address1').focus();
			 stat = false;
		}
		
		if(zipcode == null || zipcode == "" || zipcode=="Zip Code"){
			 $('input#zipcode').after($('<span class="missingFields">*</span>'));
			 //$('input#address1').focus();
			 stat = false;
		}
		
		if(state == null || state == "" || state== "State"){
			 $('select#stateList').after($('<span class="missingFields">*</span>'));
			 //$('input#address1').focus();
			 stat = false;
		}
		
		if(dwellingtype==null || dwellingtype == "" || dwellingtype == "Service Address Type"){
			 $('select#serviceAddressTypeList').after($('<span class="missingFields">*</span>'));
			 //$('input#address1').focus();
			 stat = false;
		}
		
		if(ownership==null || ownership == "" || ownership == "addressOwnRent"){
			$('select#rentOwnList').after($('<span class="missingFields">*</span>'));
			 //$('input#address1').focus();
			 stat = false;
		}
		
		if(usaaId==null || usaaId == ""){
			$('input#usaaId').after($('<span class="missingFields">*</span>'));
			 //$('input#address1').focus();
			 stat = false;
		}		
		
		if($('input#datepicker').is(":visible"))
		{
			if(moveDate == undefined || moveDate == "" || moveDate == "Move-In Date"){
				$('input#datepicker').after($('<span class="missingFields">*</span>'));
				stat = false;
			}
			if(moveDate.length < 10 && moveDate.length!=0){
				disPlayErrorMessageByFlag(stat,poStat);
				alert("Invalid Move-In Date format.  Please enter date in mm/dd/yyyy format.");
				return false;
			}
			else
			{
				var status = validateDates($('input#datepicker'));
				if( !status )
				{
					disPlayErrorMessageByFlag(stat,poStat);
					return false;
				}
			}
		}
		if(gasDate != undefined){
			if(gasDate.length < 10 && gasDate.length!=0){
				disPlayErrorMessageByFlag(stat,poStat);
				alert("Invalid Gas Start Date format.  Please enter date in mm/dd/yyyy format.");
				return false;
			}
			else
			{
				var status = validateDates($('input#datepickerGas'));
				if( !status )
				{
					disPlayErrorMessageByFlag(stat,poStat);
					return false;
				}
			}
		}
		if(eleDate != undefined){
			if(eleDate.length < 10 && eleDate.length!=0){
				disPlayErrorMessageByFlag(stat,poStat);
				alert("Invalid Electric Start Date format.  Please enter date in mm/dd/yyyy format.");
				return false;
			}
			else
			{
				var status = validateDates($('input#datepickerElectric'));
				if( !status )
				{
					disPlayErrorMessageByFlag(stat,poStat);
					return false;
				}
				
			}
		}
		if(unitType!=null && unitType!="" && unitType!="Unit Type"){
			if(unitNum == null || unitNum == "" || unitNum == "Unit Number"){
				$('input#unitNumber').after($('<span class="missingFields">*</span>'));
				stat = false;
			}
		}
		if(unitNum!=null && unitNum!="" && unitNum != "Unit Number"){
			if(unitType == null || unitType == "" || unitType == "Unit Type"){
				$('select#unitTypeList').after($('<span class="missingFields">*</span>'));
				stat = false;
			}
		}
		if(customerMove != undefined){
			if(customerMove == null || customerMove == ""){
				$('select#customerMove').after($('<span class="missingFields">*</span>'));
				//$('input#address1').focus();
				stat = false;
			}
		}
		
		disPlayErrorMessageByFlag(stat,poStat);
		
		if(document.getElementById("error").style.display=="inline"){
			stat = false;
		}
		
		if(document.getElementById("loader").style.display=="inline"){
			stat = false;
		}
		if(stat){
			//To save the html on page submit
			savePageHtml(false, "");
		}
		return (stat && poStat);
	}catch(err){
		alert(err);
		return false;
	}
	
}

function disPlayErrorMessageByFlag(stat,poStat)
{
	if(!stat){
		$("div#validatorMsg").css('display','block');
	}else{
		$("div#validatorMsg").css('display','none');
	}
	if(!poStat){
		$("div#poStatValidatorMsg").css('display','block');
	}else{
		$("div#poStatValidatorMsg").css('display','none');
	}
}

function populateCityState(){
	$('#error').css('display','none');
	$("span.missingFields").remove();
	$("div#validatorMsg").css('display','none');
	
	var zip = $(this).val().trim();
	var zipLength = zip.length;
	var path = $("#contextPath").val();
	var url = path+"/salescenter/populate";
	var data = {};
	data["zipCode"] = zip;
	if((zipLength == 5 || zipLength == 10) && isValidZipNumber(zip)){
		$('#loader').css('display','inline');
		try{    
			$.ajax({
				type: 'POST',
				url: url,
				data: data,
				success: onCompletePopulateCityState,
				error: onError
			});
		} catch(e){
			alert(e);
		}
	}else{
		if(zipLength == 0){
			$('input#zipcode').after($('<span class="missingFields">*</span>'));
			$("div#validatorMsg").css('display','block');
		}else{
			$('#error').text('Invalid Zip');
			$('#error').css('color','#F00');
			$('#error').css('display','inline');
		}
	}
}


function isValidZipNumber(zip){
	var splittedValue = zip.split("-");
	if(splittedValue.length==2){
		if(!isNaN(splittedValue[0]) && !isNaN(splittedValue[1])){
			return true;
		}
	}else if(splittedValue.length==1){
		if(!isNaN(splittedValue[0])){
			return true;
		}
	}
	return false;
}



var onCompletePopulateCityState = function(data){
	if(data=="sessionTimeOut"){
		var path = $("#contextPath").val();
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		data = JSON.parse(data);
		if($.isEmptyObject(data)){
			$('#loader').css('display','none');
			$('#error').text('Invalid Zip');
			$('#error').css('color','#F00');
			$('#error').css('display','inline');
		}else{
			var city = data.city;
			var state = data.state;
			$('#loader').css('display','none');
			if(city != undefined && city.trim() != ""){
				$('input#city').val(city);
				$('input#city').css('color','#000');
			}
		
			if(state != undefined && state.trim() != ""){
				$('select#stateList').val(state);
				$('select#stateList').removeClass("greyColor");
			}
			if(city.trim()==""){
				$('#loader').css('display','none');
				$('#error').text('Invalid Zip');
				$('#error').css('color','#F00');
				$('#error').css('display','inline');
			}
		}
	}
}


var onError = function() {
	return function(data, textStatus, xhr, h4_id){
		alert("error\n" + data + ", " + textStatus + ", " + xhr);
	}
}


function validateDates(currentObj)
{
	var isDate = false;
	var moveInDate = $(currentObj).val();
	var date_re = new RegExp("^\\d{2}\\d{2}\\d{4}$");
	var calDate_re = new RegExp("^\\d{2}/\\d{2}/\\d{4}$");
	var title = $(currentObj).attr('title');
	var defaultValue = $(currentObj).prop("defaultValue");

	var moveDate = moveInDate.replace(/\//g, "");
	
	if(moveDate.length > 8){
		alert("Invalid "+ title +" format.  Please enter date in mm/dd/yyyy format.");
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
				isDate = false;
				alert("Invalid "+ title +" format.  Please enter date in mm/dd/yyyy format.");
			}
		}else if(!moveDate.match(calDate_re)){
			isDate = false;
			alert("Invalid "+ title +" format.  Please enter date in mm/dd/yyyy format.");
		}
		$("input#moveDate").blur();
	}
	if(isDate)
	{
		$(currentObj).val(moveDate);
	}
	else
	{
		$(currentObj).val(moveInDate);
	}
	return isDate;
}

var autoChange = function(event){
	
	var key = event.keyCode || event.charCode;
	//alert(key);
	if (key ==  8 || key == 13) {
		
		event.preventDefault();
		
	}else{
		validateDates(this);
	}
	
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