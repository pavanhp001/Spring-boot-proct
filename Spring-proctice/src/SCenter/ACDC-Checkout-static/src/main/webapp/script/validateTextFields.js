var encrptExtIDArray =["ArmstrongPIN","ATTUVSecurityPinAnswerQ",
                       "ATTUVSecurityPinBackupQ","ATTUVSecurityPinQ",
                       "BankNumber","BuckeyePIN","BuckeyeSecurityAnswer",
                       "BuckeyeSecurityQuestion","CinBellLDProviderSSNPermQ",
                       "CincFiopCustSSNQ","CincFiopHasCustSSNQ","COXASISSecretAnswer",
                       "COXASISSecretQuestion","COXSecretAnswer","COXSecretQuestion","DisabledCreditCardType",
                       "DisabledSP/RMSSN","DisabledSPRMSSN","RoutingNumber","ScanaContactSSN",
                       "SSN","SuddenlinkSecretQuestionALL","SuddenlinkSecretQuestionAnswerWEST",
                       "SuddenlinkSecretQuestionWEST","WindsAccHoldSSNQ","WindsHaveAccHoldSSNQ"];


$('.DesiredStartDate2').live('change',function(){
	var selectedSecondInstallationDate = $(this).val();
	var selectedFirstInstallationDate = $('.DesiredStartDate1').val();
	var selectedFirstInstallationTime = $('.DesiredStartTime1').val();
	var removedDialogue = selectedFirstInstallationTime;
	var i = 0;
	var j = 0;
	if((selectedFirstInstallationDate == selectedSecondInstallationDate) && removedDialogue != ""){
		$('.DesiredStartTime2 option[value="'+removedDialogue+'"]').remove();	
	}
	else{
		var exists = false;
		$('.DesiredStartTime2 option').each(function(){
			if (this.value == removedDialogue) {

			}
			else{
				if(!($(".DesiredStartTime2 option[value='"+removedDialogue+"']").length > 0) && removedDialogue != ""){
					$(".DesiredStartTime2").append($("<option></option>").attr("value", removedDialogue).text(removedDialogue));
				}
			}
		});
	}
});

$('input[type=text]').live('focus', function(){
	var id = $(this).attr('id');
	var hiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
	if(hiddenJsonElementVal!=undefined){
		var hiddenElementJSONVal = JSON.parse(hiddenJsonElementVal);
		if(hiddenElementJSONVal.validation != null){
			var validationType = hiddenElementJSONVal.validation;
			var valueTarget = hiddenElementJSONVal.valueTarget;
			if(valueTarget != null && valueTarget == 'consumer.dateOfBirth'){
				$(this).datepick({
					dateFormat : 'mm/dd/yyyy',
					maxDate : new Date($("#validmaxdate").val()),
					onShow: function(dates) {
						if($(this).val().indexOf('*') >= 0)
						{	
							var updateHiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
							var updateHiddenJsonElementJSONVal = JSON.parse(updateHiddenJsonElementVal);
							$(this).val(updateHiddenJsonElementJSONVal['enteredValue']);
						}
					},
					onClose: function(dates) {
						
					if($(this).val() != null && $(this).val().trim().length > 0){
						if($(this).val().indexOf('*') >= 0){						
							hiddenElementJSONVal.enteredValue = (hiddenElementJSONVal['enteredValue']);
							$('input[id = '+ id1 +'_JSON]').val(JSON.stringify(hiddenElementJSONVal));	
						} else {
							var id1 = $(this).attr('id');
							var test = $(this).val();
							var selValue1 = $(this).val();
							formatDateValue(selValue1, id1);
							var formattedValue = $(this).val();
							if(isValidDateOfBirth(formattedValue, id1)){
								formatDateValue(formattedValue, id1);
							}							
							hiddenElementJSONVal.enteredValue = $(this).val();
							var jsonValue = $('input[id='+id+'_JSON]').val();
							if(jsonValue != undefined){
								jsonValue = JSON.parse(jsonValue);

								if(jsonValue.toBeMasked == 'true'){
									var unMaskedValue = $(this).val();
									createMasking(unMaskedValue, id, jsonValue);
								}
							}	
							
							$('input[id = '+ id1 +'_JSON]').val(JSON.stringify(hiddenElementJSONVal));							
						}
						
					}

				}
				});
			}
			else if(validationType.toUpperCase() == 'date'.toUpperCase()){
				$(this).datepicker();
			}
			else if(validationType.match(/^([5-7]+)CAL$/)){
				if(validationType == "6CAL"){
					$(this).datepicker({
						beforeShowDay: noSundays
					});
				}
				else if(validationType == '5CAL'){
					$(this).datepicker({
						beforeShowDay: $.datepicker.noWeekends
					});
				}
				else if(validationType == '7CAL'){
					$(this).datepicker();
				};
			}
			else if(validationType.match(/^([5-7]+)CAL:/)){
				displayCal(validationType, id);
			}else if($(this).val() != null && $(this).val().trim().length > 0){
				$(this).val(hiddenElementJSONVal['enteredValue']);
			}
		}
	}
});

$('input[type=text]').live('keypress', function(event){
	var id = $(this).attr('id');
	var hiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
	var hiddenElementJSONVal = JSON.parse(hiddenJsonElementVal);
	var validationType = hiddenElementJSONVal.validation;
	var regex = /^([0-9]+):(Numeric|Alphanumeric)$/;
	if(validationType != null && validationType.trim().length > 0){
		if(validationType.toUpperCase() == 'SSN'
				|| validationType.toUpperCase() == 'ROUTING NUMBER' 
				|| validationType.toUpperCase() == 'PHONE' 
				|| validationType.toUpperCase() == 'CREDIT CARD NUMBER'
				|| validationType.toUpperCase() == 'CHECK NUMBER' 
				|| validationType.toUpperCase() == 'CREDIT CARD DATE'
				|| (validationType.toUpperCase().indexOf('CAL') >= 0)
				|| validationType.match(regex)){
			doNotAllowAlphabets(event);
		}
	}
	else if(id == 'MonthDD' || id == 'YearDD'){
		doNotAllowAlphabets(event);
	}
	if (validationType != null && validationType.trim().length > 0) {
		if (validationType.toUpperCase() == 'CREDIT CARD NAME') {
			doAllowAlphabets(event);	
		}
	}
});


function doNotAllowAlphabets(event){
	if(event.key != undefined && event.keyCode != 0){
		if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 47 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
				// Allow: Ctrl+A
				(event.keyCode == 65 && event.ctrlKey === true) || (event.keyCode == 97 && event.ctrlKey === true) ||
				// Allow: home, end, left, right
				(event.keyCode >= 35 && event.keyCode <= 39) || (event.keyCode >= 48 && event.keyCode <= 57)) {
			// let it happen, don't do anything
			return;
		}
		else {
			/*// Ensure that it is a number and stop the keypress
			if (event.shiftKey || (event.keyCode < 96 || event.keyCode > 105 )) {*/
			event.preventDefault(); 
			//}
		}
	}
	else{
		if ( event.keyCode == 46 || event.keyCode == 8 || event.charCode == 47 || event.keyCode == 9 || event.charCode == 27 || event.charCode == 13 || 
				// Allow: Ctrl+A
				(event.charCode == 65 && event.ctrlKey === true) || (event.charCode == 97 && event.ctrlKey === true) ||
				// Allow: home, end, left, right
				(event.keyCode >= 35 && event.keyCode <= 39) || (event.charCode >= 48 && event.charCode <= 57)) {
			// let it happen, don't do anything
			return;
		}
		else {
			/*// Ensure that it is a number and stop the keypress
			if (event.shiftKey) {*/
			event.preventDefault(); 
			//}
		}
	}
};

function doAllowAlphabets(event){
	if(event.key != undefined && event.keyCode != 0){
		if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 47 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
				// Allow: Ctrl+A
				(event.keyCode == 65 && event.ctrlKey === true) || (event.keyCode == 97 && event.ctrlKey === true) ||
				// Allow: home, end, left, right
				(event.keyCode >= 35 && event.keyCode <= 39) || (event.keyCode >= 48 && event.keyCode <= 57)) {
			// let it happen, don't do anything
			event.preventDefault(); 
		}
		else {
			/*// Ensure that it is a number and stop the keypress
			if (event.shiftKey || (event.keyCode < 96 || event.keyCode > 105 )) {*/
			return;
			//}
		}
	}
	else{
		if ( event.keyCode == 46 || event.keyCode == 8 || event.charCode == 47 || event.keyCode == 9 || event.charCode == 27 || event.charCode == 13 || 
				// Allow: Ctrl+A
				(event.charCode == 65 && event.ctrlKey === true) || (event.charCode == 97 && event.ctrlKey === true) ||
				// Allow: home, end, left, right
				(event.keyCode >= 35 && event.keyCode <= 39) || (event.charCode >= 48 && event.charCode <= 57)) {
			// let it happen, don't do anything
			event.preventDefault(); 
		}
		else {
			/*// Ensure that it is a number and stop the keypress
			if (event.shiftKey) {*/
			return;
			//}
		}
	}
};


$('.billingInfoSelect').live('change', function(){
	var id = $(this).attr('id');
	var val = $(this).val();
	var hiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
	var hiddenJSON = JSON.parse(hiddenJsonElementVal);
	hiddenJSON.enteredValue = $(this).val();
	$('input[id = '+ id +'_JSON]').val(JSON.stringify(hiddenJSON));
});

$('input[type=text]').live('blur', function(){
	var id = $(this).attr('id');
	
	var hiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
	
	//Frontier AS IS Plan validations for Confirmation Number
	if(id.length > 0 && id.indexOf('FrontierIntAsIsConfNumberQ')!= -1){
		var val = $(this).val();
		var regExp = /(^[0]\d{8})([a-z]{2}$)/i; 
		if($('#'+id +'_error').length > 0 ){
			$('#'+id +'_error').remove();
		}
		if(val.length > 0 && regExp.exec(val) === null){
			var errorMsg = "Confirmation Number should begin with a zero and total nine digits and end with 2 alpha characters.";
			$('#'+id).after("<span id='"+id +"_error' class='error' style='background-color:yellow;' ><font color='red'>"+errorMsg+"</font></span>");
		}
	}
	
	if(hiddenJsonElementVal!=undefined){
		var hiddenElementJSONVal = JSON.parse(hiddenJsonElementVal);
		
		if(hiddenElementJSONVal.validation != null){
			var validationType = hiddenElementJSONVal.validation;
			var valueTarget = hiddenElementJSONVal.valueTarget;
			var selValue = $(this).val();
			var regex = /^([0-9]+):(Numeric|Alphanumeric)$/;
			if(valueTarget != null && valueTarget == 'consumer.dateOfBirth'){
				if(selValue.indexOf('*') >= 0){
					selValue = hiddenElementJSONVal.enteredValue;
				}
				formatDateValue(selValue, id);
				hiddenElementJSONVal.enteredValue = selValue;
				$('input[id = '+ id +'_JSON]').val(JSON.stringify(hiddenElementJSONVal));
			}
			
			else if(validationType == 'SSN'){
				if(selValue.indexOf('*') >= 0){
					formatSSN(hiddenElementJSONVal.enteredValue, id);
				}else{
					formatSSN(selValue, id);
				}
			}
			else if(validationType == 'Routing Number'){
				formatRoutingNumber(selValue, id);
			}
			else if(validationType == 'Phone' || validationType == 'phone'){
				formatPhoneNumber(selValue, id);
			}
			else if(validationType == 'credit card number'){
				if(selValue.indexOf('*') >= 0){
					formatCreditCardNumber(hiddenElementJSONVal.enteredValue, id);
				}
				else{
					formatCreditCardNumber(selValue, id);
				}
			}
			else if(validationType.match(regex)){
				var pinLength = 4;
				if(validationType.indexOf(':') > 0){
					var pinLengthVal = validationType.split(':');
					pinLength = pinLengthVal[0];
				}
				validatePinNumberForLenght(selValue, id,pinLength);
			}
			else if(validationType == 'Date' || validationType.match(/^([5-7]+)CAL$/) 
					|| validationType.match(/^([5-7]+)CAL:/)){
				var isSelected = isDatePickerSelected(id);
				if(!isSelected){
					formatDateValue(selValue, id);
				}
				
				/*	
				 * when we select date from date picker
				 * 	Starts from here.....
				 */
				$("#"+id).unbind("change");
				$("#"+id).change(function(){
					var id1 = $(this).attr('id');
					var selValue1 = $(this).val();
					var isSelected = isDatePickerSelected(id1);
					if(!isSelected){
						formatDateValue(selValue1, id1);
					}

					if($(this).val() != null && $(this).val().trim().length > 0){
						hiddenElementJSONVal.enteredValue = $(this).val();
					}
					$('input[id = '+ id1 +'_JSON]').val(JSON.stringify(hiddenElementJSONVal));
				});
				/*
				 * Ends here.....
				 */
			}
			else if(validationType == "Email"){
				var data = { "selectedValue" : selValue};
				$.ajax({
					type: 'POST',
					data: data,
					async:false,
					url: $('#contextPath').val()+"/static/updateDialogue/validateEmail",
					success: function(data){
						var responseJSON = JSON.parse(data);
						if(responseJSON.error != null && responseJSON.error != undefined){
							$('#'+id +'_emailClass').each(function(){
								if($(this).text() != null){
									$(this).remove();
								}
							});
							$('#'+id).after("<span id='"+id +"_emailClass' class='emailClass error' style='background-color:yellow;' ><font color='red'>"+responseJSON.error+"</font></span>");			
							return false;
						}else{
							$('#'+id +'_emailClass').each(function(){
								if($(this).text() != null){
									$(this).remove();
								}
							});
						}
					},
					error:function(status){
					}
				});
			}
			
			if(valueTarget != null){
				if(valueTarget == 'consumer.shippingAddress.dwelling.postalCode' || valueTarget == 'consumer.billingAddress.dwelling.postalCode' 
					|| valueTarget == 'consumer.previousAddress.dwelling.postalCode'){
					prepopulateCityAndState(selValue, id, valueTarget);
				}
			}
		}
		if($(this).val().indexOf('*') >= 0){
		}
		else{
			if($(this).val() != null && $(this).val().trim().length > 0){
				hiddenElementJSONVal.enteredValue = $(this).val();
				$.each(encrptExtIDArray,function(index,value){
					if(id.indexOf(value) != -1){
						if($("#"+id).val().trim().length > 0){
							var roundVal = $("#"+id).val().trim().length;
							var regExp = new RegExp("^.{"+roundVal+"}");
							var astrekStr = "";
							for ( var int = 0; int < roundVal; int++) {
								astrekStr = astrekStr+"*";
							}
							$("#"+id).val($("#"+id).val().replace(regExp,astrekStr));
						}
						hiddenElementJSONVal.maskedValue = $("#"+id).val();
						$("#"+id).val(hiddenElementJSONVal['maskedValue']);
					}
				});
			}
		}
		if(valueTarget != null && valueTarget == 'consumer.dateOfBirth'){
		var jsonValue = $('input[id='+id+'_JSON]').val();
		if(jsonValue != undefined){
			jsonValue = JSON.parse(jsonValue);
			var keepEnteredValue = jsonValue.enteredValue;
			if(jsonValue.toBeMasked == 'true'){
				var unMaskedValue = $(this).val();
				createMasking(unMaskedValue, id, jsonValue);
			}
			hiddenElementJSONVal.enteredValue = keepEnteredValue;
		}
		}
		$('input[id = '+ id +'_JSON]').val(JSON.stringify(hiddenElementJSONVal));
	}
});



/*
 * checks whether the entered DOB Value is in range 
 * and throws error if the entered date is out of range..
 */
function isEnteredValidDateOfBirth(dateOfBirthValue,dateOfBirthDateId){
	
	try{
		if($('#'+dateOfBirthDateId).val()!=""){
			var tempDateOfBirthValue = new Date($('#'+dateOfBirthDateId).val());
			if(tempDateOfBirthValue>dateOfBirthValue){
				$("#"+dateOfBirthDateId +"_dobClass").each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				$('#'+dateOfBirthDateId).after("<span id='"+dateOfBirthDateId +"_dobClass' class='dobClass error' style='background-color:yellow;' ><font color='red'>Entered Date out of range</font></span>");			
				return false;
			}else{
				$("#"+dateOfBirthDateId +"_dobClass").each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				return true;
			}
		}
		return true;
	}catch(e){}
}


var zipCodeValueTargets = {"valueTargets":[
                                           {"zipCodeValueTarget":"consumer.previousAddress.dwelling.postalCode","cityValueTarget":"consumer.previousAddress.dwelling.city","stateValueTarget":"consumer.previousAddress.dwelling.stateOrProvince"},
                                           {"zipCodeValueTarget":"consumer.billingAddress.dwelling.postalCode","cityValueTarget":"consumer.billingAddress.dwelling.city","stateValueTarget":"consumer.billingAddress.dwelling.stateOrProvince"},
                                           {"zipCodeValueTarget":"consumer.shippingAddress.dwelling.postalCode","cityValueTarget":"consumer.shippingAddress.dwelling.city","stateValueTarget":"consumer.shippingAddress.dwelling.stateOrProvince"}]};

function prepopulateCityAndState(zip, id, valueTarget){
	var zipLength = zip.length;
	var path = $("#contextPath").val();
	var url = path+"/static/updateDialogue/populateCityAndState";
	var data = {};
	data["zipCode"] = zip;
	if(zipLength == 5 || zipLength == 10){
		$('#'+id +'_zipCode').each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		var path = $('#contextPath').val();

		$("#"+id).after('<img id="loader" class="validateZipLoader" width="18" height="18" alt="Loading ..."'+
				'src="'+path+'/image/spinner1.gif" style="padding-left: 2px; vertical-align: top; padding-top: 1px;">');

		try{
			$.ajax({
				type: 'POST',
				url: url,
				data: data,
				async: false,
				success: function(data){
				data = JSON.parse(data);
				if($.isEmptyObject(data)){
					$('.validateZipLoader').remove();
					$('#'+id +'_zipCode').each(function(){
						if($(this).text() != null){
							$(this).remove();
						}
					});
					$('#'+id).after("<span id='"+id +"_zipCode' class='emailClass error' style='background-color:yellow;' ><font color='red'>Invalid Zip</font></span>");			
					return false;
				}
				else{
					var city = data.city;
					var state = data.state;
					var cityValueTarget = "";
					var stateValueTarget = "";
					var zipCodeIndividualValueTargets = zipCodeValueTargets.valueTargets;
					$(zipCodeIndividualValueTargets).each(function(){
						if(this.zipCodeValueTarget == valueTarget){
							cityValueTarget = this.cityValueTarget;
							stateValueTarget = this.stateValueTarget;
						}
					});
					$('.validateZipLoader').remove();
					if(city != undefined && city.trim() != ""){
						var cityId = returnIdBasedOnValueTarget(cityValueTarget);
						$('input#'+cityId).val(city);
						$('input#'+cityId).css('color','#000');
					}
					if(state != undefined && state.trim() != ""){
						var stateId = returnIdBasedOnValueTarget(stateValueTarget);
						$('select#'+stateId).val(state);
						addSelectChange(stateId, state);
					}
				}
			},
			error:function() {
				$('.validateZipLoader').remove();
				return function(data, textStatus, xhr, h4_id){
					alert("error\n" + data + ", " + textStatus + ", " + xhr);
				};
			}
			});
		} catch(e){
			
		}
	}else{
		if(zipLength == 0){
			$('#'+id +'_zipCode').each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			$('#'+id).after("<span id='"+id +"_zipCode' class='emailClass error'><font color='red'>*</font></span>");
		}else{
			$('#'+id +'_zipCode').each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			$('#'+id).after("<span id='"+id +"_zipCode' class='emailClass error' style='background-color:yellow;' ><font color='red'>Invalid Zip</font></span>");
		}
	}
}

function returnIdBasedOnValueTarget(valueTarget){
	var id ="";
	$(':text').each(function(){
		var jsonValue = $('input#'+$(this).attr('id')+'_JSON').val();
		if(jsonValue != null){
			jsonValue = JSON.parse(jsonValue);
			if(jsonValue.valueTarget== valueTarget ){
				id = $(this).attr('id');
				return;
			}
		}
	});
	$('select').each( function() {
		var jsonValue = $('input#'+$(this).attr('id')+'_JSON').val();
		if(jsonValue != null){
			jsonValue = JSON.parse(jsonValue);
			if(jsonValue.valueTarget== valueTarget ){
				id = $(this).attr('id');
				return;
			}
		}
	});
	
	return id;
}

$('.addressStateOrProvince').on('change', function(){
	var id = $(this).attr('id');
	var val = $(this).val();
	var hiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
	var hiddenJSON = JSON.parse(hiddenJsonElementVal);
	hiddenJSON.enteredValue = $(this).val();
	$('input[id = '+ id +'_JSON]').val(JSON.stringify(hiddenJSON));
});

function addSelectChange(id, val){ 
	var hiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
	var hiddenJSON = JSON.parse(hiddenJsonElementVal);
	hiddenJSON.enteredValue = val;
	$('input[id = '+ id +'_JSON]').val(JSON.stringify(hiddenJSON));
}

function isValidDateOfBirth(enteredDOBValue, id){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	var min18Years = parseFloat(yyyy)-18;

	if(enteredDOBValue.indexOf('/') > 0){
		var dobArray = enteredDOBValue.split('/');
		if(dobArray[2] > min18Years){
			$("#"+id +"_dobClass").each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			$('#'+id).after("<span id='"+id +"_dobClass' class='dobClass error' style='background-color:yellow;' ><font color='red'>Entered Year out of range</font></span>");			
			return false;
		}
		else if(dobArray[2] == min18Years){
			if(parseFloat(dobArray[0]) > parseFloat(mm)){
				$("#"+id +"_dobClass").each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				$('#'+id).after("<span id='"+id +"_dobClass' class='dobClass error' style='background-color:yellow;'><font color='red'>Entered Year out of range</font></span>");
				return false;
			}
			else if(parseFloat(dobArray[0]) == parseFloat(mm)){
				if(dobArray[1] > dd){
					$("#"+id +"_dobClass").each(function(){
						if($(this).text() != null){
							$(this).remove();
						}
					});
					$('#'+id).after("<span id='"+id +"_dobClass' class='dobClass error' style='background-color:yellow;'><font color='red'>Entered Year out of range</font></span>");
					return false;
				}
				else if(dobArray[1] == dd){
					$("#"+id +"_dobClass").each(function(){
						if($(this).text() != null){
							$(this).remove();
						}
					});
					return true;
				}
			}
		} else{
			$("#"+id +"_dobClass").each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			return true;
		}
	}
}
function isDatePickerSelected(id){
	$("input[id=ui-datepicker-div]").bind('onSelect', function(){
		return true;
	});
	return false;
}

function daysInMonth(month,year) {
    return new Date(year, month, 0).getDate();
}

//does two functions
//1. substring the entered value to min of 9(without dashes) to max of 11(with dashes)
//2. formats the entered number and add dashes if not present
function formatSSN(selValue, id){
	selValue = selValue.replace(/\-/g, '');
	if(selValue.length == 9){
		formatNormalSSN(selValue, id);
	}
	else{
		$("#"+id+"_ssnClass").each(function(){
			$(this).remove();
		});
		if($("#"+id+"_ssnClass").text().length == 0){
			$('input[id='+id+']').after("<span id='"+id+"_ssnClass' class='ssnClass error' style='background-color:yellow;'><font color='red'>invalid ssn, please enter a valid ssn</span>");
		}
	}
}

function formatNormalSSN(selValue, id){
	if(selValue.length == 9){
		if(validateSSN(selValue, id)){
			var formatedPhoneNumber = selValue.slice(0,3)+"-"+selValue.slice(3,5)+"-"+selValue.slice(5);
			var hiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
			var hiddenJSON = JSON.parse(hiddenJsonElementVal);
			if(hiddenJSON.maskedValue != null && hiddenJSON.maskedValue.trim().length > 0){
				if(hiddenJSON.valueTargetVal!=formatedPhoneNumber){
					$('input[id='+id+']').val(formatedPhoneNumber);
				}else{
					$('input[id='+id+']').val(hiddenJSON.maskedValue);
				}
			}
			else{
				$('input[id='+id+']').val(formatedPhoneNumber);
			}
			$("#"+id+"_ssnClass").each(function(){
				$(this).remove();
			});
		}
		return false;
	}
	else{

		$("#"+id+"_ssnClass").each(function(){
			$(this).remove();
		});
		if($("#"+id+"_ssnClass").text().length == 0){
			$('input[id='+id+']').after("<span id='"+id+"_ssnClass' class='ssnClass error' style='background-color:yellow;'><font color='red'>invalid ssn, please enter a valid ssn</span>");
		}
	}
}

function formatRoutingNumber(selValue, id){
	if(selValue.length > 9){
		selValue = selValue.substr(0, 9);
		if(validateRoutingNumber(selValue, id)){
			$('input[id='+id+']').val(selValue);
			$("#"+id+"_validateRoutingNumberClass").each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			return true;
		}
		else{
			return false;
		}
	}
	else if(selValue.length == 9){
		if(validateRoutingNumber(selValue, id)){
			$("#"+id+"_validateRoutingNumberClass").each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			return true;
		}
		else{
			return false;
		}
	}
	else{
		$("#"+id+"_validateRoutingNumberClass").each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		if($("#"+id+"_validateRoutingNumberClass").text().length == 0){
			$('#'+id).after("<span id='"+id+"_validateRoutingNumberClass' class='validateRoutingNumberClass error' style='background-color:yellow;'><font color='red'>Routing Number invalid Length</span>");
			return false;
		}
	}
}

function formatPhoneNumber(selValue, id){
	selValue = selValue.replace(/\-/g, '');

	if(selValue.length == 10){
		formatNormalPhoneValue(selValue, id);
		return true;
	}
	else{
		$("#"+id+"_validatePhoneNumberClass").each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		$('input[id='+id+']').after("<span id='"+id+"_validatePhoneNumberClass' class='validatePhoneNumberClass error' style='background-color:yellow;'>" +
		"<font color='red'>invalid phone number</font></span>");
	}
}

function formatNormalPhoneValue(selValue, id){
	if(selValue.length == 10){
		var formatedPhoneNumber = selValue.slice(0,3)+"-"+selValue.slice(3,6)+"-"+selValue.slice(6);
		if(validatePhoneNumber(selValue, id)){
			$("#"+id+"_validatePhoneNumberClass").each(function(){
				$(this).remove();
			});
			$('input[id='+id+']').val(formatedPhoneNumber);
			return true;
		}
	}
}
function validatePinNumberForLenght(selValue, id,pinLength){
	if(selValue.length != pinLength){
		$("#"+id+"_validatePinNumberClass").each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		$('input[id='+id+']').after("<span id='"+id+"_validatePinNumberClass' class='validatePinNumberClass error' " +
		"style='background-color:yellow;'><font color='red'> Pin Number incorrect length </span>");
	}
	else{
		$("#"+id+"_validatePinNumberClass").each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
	}
}
function validatePinNumber(selValue, id){
	if(selValue.length != 4){
		$("#"+id+"_validatePinNumberClass").each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		$('input[id='+id+']').after("<span id='"+id+"_validatePinNumberClass' class='validatePinNumberClass error' " +
		"style='background-color:yellow;'><font color='red'> Pin Number incorrect length </span>");
	}
	else{
		$("#"+id+"_validatePinNumberClass").each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
	}
}

function formatCreditCardNumber(selValue, id){
	if(selValue.length == 16){
		if(validateCreditCardNumber(selValue, id)){
			
			$("#"+id+"_validateCCClass").each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			return true;
		}
	}
	else{
		$("#"+id+"_validateCCClass").each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
			$('input[id='+id+']').after("<span id='"+id+"_validateCCClass' class='validateCCClass error' " +
			"style='background-color:yellow;'><font color='red'> Credit Card Number incorrect length </span>");
			return false;
	}
}

var holidays = ["2014-11-11","2014-11-27","2014-12-25",
                "2015-01-01","2015-01-19","2015-02-16","2015-05-25","2015-07-03",
                "2015-09-07","2015-10-12","2015-11-11","2015-11-26","2015-12-25",
                "2016-01-01","2016-01-18","2016-02-15","2016-05-30","2016-07-04",
                "2016-09-05","2016-10-10","2016-11-11","2016-11-24","2016-12-26",
                "2017-01-01","2017-01-16","2017-02-20","2017-05-29","2017-07-04",
                "2017-09-04","2017-10-09","2017-11-10","2017-11-23","2017-12-25",
                "2018-01-01","2018-01-15","2018-02-19","2018-05-28","2018-07-04",
                "2018-09-03","2018-10-08","2018-11-12","2018-11-22","2018-12-25",
                "2019-01-01","2019-01-21","2019-02-18","2019-05-27","2019-07-04",
                "2019-09-02","2019-10-14","2019-11-11","2019-11-28","2019-12-25"];

function displayCal(validationType, id){
	var val = validationType.split(':');
	var spl = val[1].split('-');
	var min = spl[0];
	var max = spl[1];
	
	if(val[0] == "6CAL"){
		var startDate = new Date();
		endDate = new Date(),
		countMin = parseInt(min),
		countMax = parseInt(max),
	    x = 0,
	    y = 0;

	while(x <= countMin){
	    startDate.setDate(startDate.getDate() + 1);
	    if(startDate.getDay() != 0){
	        x++;
	    }
	}
	
	while(y <= countMax){
		endDate.setDate(endDate.getDate() + 1);
	    if(endDate.getDay() != 0){
	        y++;
	    }
	}
		countMin = parseInt(x);
		countMax = parseInt(y);

		$('input[id='+id+']').datepicker({
			defaultDate: parseInt(countMin),
			beforeShowDay: noSundays,
			minDate: startDate,
			maxDate: endDate
		});
	}
	else if(val[0] == "5CAL"){
		var startDate = new Date(),
		endDate = new Date(),
		countMin = parseInt(min),
		countMax = parseInt(max),
	    x = 0,
	    y = 0;

	while(x <= countMin){
	    startDate.setDate(startDate.getDate() + 1);
	    if(startDate.getDay() != 0 && startDate.getDay() != 6){
	        x++;
	    }
	}
	
	while(y <= countMax){
		endDate.setDate(endDate.getDate() + 1);
	    if(endDate.getDay() != 0 && endDate.getDay() != 6){
	        y++;
	    }
	}
		countMin = parseInt(x);
		countMax = parseInt(y);

		$('input[id='+id+']').datepicker({
			defaultDate: parseInt(countMin),
			beforeShowDay: noWeekends,
			minDate: startDate,
			maxDate: endDate
		});
	}
	else if(val[0] == "7CAL"){
		var startDate = new Date(),
		endDate = new Date(),
		countMin = parseInt(min),
		countMax = parseInt(max);
		startDate.setDate(startDate.getDate() + countMin + 1);
		endDate.setDate(endDate.getDate() + countMax + 1);
		$('input[id='+id+']').datepicker({
			beforeShowDay: noHolidays,
			minDate: startDate,
			maxDate: endDate
			
		});           
	}
	return true;
};

function noSundays(date) {
	var datestring = jQuery.datepicker.formatDate('yy-mm-dd', date);
    return [ holidays.indexOf(datestring) == -1 && date.getDay() != 0, ''];
//	return [date.getDay() != 0, ''];
}

function noWeekends (date) {
	var day = date.getDay();
	var datestring = jQuery.datepicker.formatDate('yy-mm-dd', date);
	return [holidays.indexOf(datestring) == -1 && (day > 0 && day < 6), ''];
}

function noHolidays(date) {
	var datestring = jQuery.datepicker.formatDate('yy-mm-dd', date);
    return [ holidays.indexOf(datestring) == -1];
}

function validateDateValue(selValue){
	if(selValue.length == 8){
		return true;
	}
	else if(selValue.length > 8 && selValue.indexOf('/') > 0){
		if(isValidDateValue(selValue)){
			return true;
		}
		else{
			return false;
		}
	}
	else if(selValue.length > 10){
		var enteredDate = selValue.substr(0, 10);
		if(enteredDate.indexOf('/') > 0){
			if(isValidDateValue(enteredDate)){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
}

function isValidDateValue(dateValue){
	var dateValueArray = dateValue.split('/');
	if(dateValueArray.length == 3 && dateValueArray[0].length == 2 && dateValueArray[1].length == 2 
			&& dateValueArray[2].length  == 4){
		return true;
	}
	else {
		return false;
	}	
}

function formatDateValue(moveDate, idVariable){
	var jsonVal = $('input[id = '+ idVariable +'_JSON]').val();
	var hiddenElementJSONVal = JSON.parse(jsonVal);
	var validationType = hiddenElementJSONVal.validation; 
	var valueTarget;
	var maskedVal;
	if(hiddenElementJSONVal.valueTarget != null && hiddenElementJSONVal.valueTarget == 'consumer.dateOfBirth'){
		valueTarget = hiddenElementJSONVal.valueTarget;
		if(maskedVal != null && maskedVal.trim().length > 0){
			maskedVal = hiddenElementJSONVal.maskedValue;
		}
	}
	if(moveDate != ""){
		
		var isValidDate = validateDateValue(moveDate);
		if(isValidDate){
			moveDate = moveDate.replace(/\//g,"");
			var calDate_re = new RegExp("^\\d{2}/\\d{2}/\\d{4}$");
			if(moveDate.length == 8){
				var date_re = new RegExp("^\\d{2}\\d{2}\\d{4}$");
				var check = true;
				if(moveDate != undefined && moveDate != "" && moveDate.match(date_re)){
					var mm = moveDate.slice(0,2);
					var dd = moveDate.slice(2,4);
					var yyyy = moveDate.slice(4);
					var month = parseInt(mm,10); // was gg (giorno / day)
					var date = parseInt(dd,10); // was mm (mese / month)
					var year = parseInt(yyyy,10); // was aaaa (anno / year)
					var xdata = new Date(year,month-1,date);
					if ( ( xdata.getFullYear() == year ) && ( xdata.getMonth () == month - 1 ) && ( xdata.getDate() == date ) ){
						moveDate =mm+"/"+dd+"/"+yyyy;
//						if(validationType.match(/^([5-7]+)CAL:/)){
//							if(getDatesInRange(moveDate, validationType)){
//								$("#"+idVariable +"_dobClass").each(function(){
//									if($(this).text() != null){
//										$("#"+idVariable +"_dobClass").remove();
//									}
//								});
//							}else{
//								$("#"+idVariable +"_dobClass").each(function(){
//									if($(this).text() != null){
//										$("#"+idVariable +"_dobClass").remove();
//									}
//								});
//
//								$('input[id='+idVariable+']').after("<span id='"+idVariable +"_dobClass' class='dobClass error' style='background-color:yellow;'><font color='red'>" +
//										"Date entered is not in range.</font></span>");
//								return false;
//							}
//						}
						$("#"+idVariable +"_dobClass").each(function(){
							if($(this).text() != null){
								$("#"+idVariable +"_dobClass").remove();
							}
						});
						if(valueTarget != undefined && valueTarget == 'consumer.dateOfBirth'){
							isEnteredValidDateOfBirth(new Date($("#validmaxdate").val()),idVariable);
						}
					} else{
						$("#"+idVariable +"_dobClass").each(function(){
							if($(this).text() != null){
								$("#"+idVariable +"_dobClass").remove();
							}
						});

						$('input[id='+idVariable+']').after("<span id='"+idVariable +"_dobClass' class='dobClass error' style='background-color:yellow;'><font color='red'>Date entered is not valid.</font></span>");
						return false;
					}
				}
				else if(!moveDate.match(calDate_re)){
					$("#"+idVariable +"_dobClass").each(function(){
						if($(this).text() != null){
							$("#"+idVariable +"_dobClass").remove();
						}
					});

					$('input[id='+idVariable+']').after("<span id='"+idVariable +"_dobClass' class='dobClass error' style='background-color:yellow;'><font color='red'>Invalid format, should be mm/dd/yyyy format.</font></span>");
					$(document).keyup(function(e){
						var key = e.keyCode || e.charCode;
						if ( key == 8) {
							e.preventDefault();
						}
					});
					return false;
				}
				if(valueTarget != undefined && valueTarget == 'consumer.dateOfBirth' && maskedVal != null && maskedVal.trim().length > 0){
					$('input[id='+idVariable+']').val(maskedVal);
					$("#"+idVariable +"_dobClass").each(function(){
						if($(this).text() != null){
							$("#"+idVariable +"_dobClass").remove();
						}
					});
					var enteredDOBStatus = true;
					if(valueTarget != undefined && valueTarget == 'consumer.dateOfBirth'){
						enteredDOBStatus = isEnteredValidDateOfBirth(new Date($("#validmaxdate").val()),idVariable);
					}
					return enteredDOBStatus;
				}
				else{
					$('input[id='+idVariable+']').val(moveDate);
					$("#"+idVariable +"_dobClass").each(function(){
						if($(this).text() != null){
							$("#"+idVariable +"_dobClass").remove();
						}
					});
					var enteredDOBStatus = true;
					if(valueTarget != undefined && valueTarget == 'consumer.dateOfBirth'){
						enteredDOBStatus = isEnteredValidDateOfBirth(new Date($("#validmaxdate").val()),idVariable);
					}
					return enteredDOBStatus;
				}
			}
			else {
				$("#"+idVariable +"_dobClass").each(function(){
					if($(this).text() != null){
						$("#"+idVariable +"_dobClass").remove();
					}
				});
				$('input[id='+idVariable+']').after("<span id='"+idVariable +"_dobClass' class='dobClass error' style='background-color:yellow;'><font color='red'>Entered date is invalid</font></span>");
				return false;
			}
		}
		else {
			$("#"+idVariable +"_dobClass").each(function(){
				if($(this).text() != null){
					$("#"+idVariable +"_dobClass").remove();
				}
			});
			$('input[id='+idVariable+']').after("<span id='"+idVariable +"_dobClass' class='dobClass error' style='background-color:yellow;'><font color='red'>Entered date is invalid. Please enter a valid date</font></span>");
			return false;
		}
	}
}

function daysInMonth(month,year) {
	return new Date(year, month, 0).getDate();
}

$('input[name=MonthDD_EXPIRATIONDATE]').live('blur', function(){
	var monthVal = $('input[name=MonthDD_EXPIRATIONDATE]').val();
	if(monthVal.indexOf('*') >= 0){
		var monthJSONVal = $('#MonthDD_JSON').val();
		monthJSONVal = JSON.parse(monthJSONVal);
		monthVal = monthJSONVal.enteredValue;
	}
	var today = new Date();
	var minimumMonth = today.getMonth()+1;
	minimumMonth = parseFloat(minimumMonth);
	monthVal = parseFloat(monthVal);
	var yearVal = $('input[name=YearDD_EXPIRATIONDATE]').val();
	if(yearVal.indexOf('*') >= 0){
		var yearJSONVal = $('#YearDD_JSON').val();
		yearJSONVal = JSON.parse(yearJSONVal);
		yearVal = yearJSONVal.enteredValue;
	}
	var minimumYear = today.getFullYear();
	minimumYear = parseFloat(minimumYear);
	if(yearVal != null && yearVal.trim().length > 0){
		yearVal = parseFloat(yearVal);
	}
	else{
		yearVal = null;
	}
	
	var isSameYear = false;
	var isGreaterThanCurrentYear = false;
	if(yearVal != null || yearVal != undefined){
		if(yearVal == minimumYear){
			isSameYear = true;
		}
		else if(yearVal > minimumYear){
			isGreaterThanCurrentYear = true;
		}
	}
	
	if(monthVal >= 1 && monthVal <= 12){
		if(yearVal != null){
			if(isSameYear && monthVal >= minimumMonth){
				$('.month_exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				$('.exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				$('input[name=YearDD_EXPIRATIONDATE]').removeAttr("disabled");
			}
			else if(isGreaterThanCurrentYear){
				if(yearVal > minimumYear+20){
				$('.month_exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				
				$('.exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				$('input[name="YearDD_EXPIRATIONDATE"]').after("<span class= 'exp_date error' style='background-color:yellow;'><font color='red'>Expiration year out of range</font></span>");
				$('input[name=YearDD_EXPIRATIONDATE]').removeAttr("disabled");
			}
				
				
			else{
				$('.month_exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				$('input[name=YearDD_EXPIRATIONDATE]').removeAttr("disabled");
			}
			}
			else{
				
				$('.month_exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				
				$('.exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				$('input[name="YearDD_EXPIRATIONDATE"]').after("<span class= 'exp_date error' style='background-color:yellow;'><font color='red'>Expiration year should be a future year</font></span>");
				$('input[name=YearDD_EXPIRATIONDATE]').removeAttr("disabled");
			}
		}
		else{
			$('.month_exp_date').each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			
			if($('input[name=YearDD_EXPIRATIONDATE]').is(':disabled')){
				$('input[name=YearDD_EXPIRATIONDATE]').removeAttr("disabled");	
			}
		}
	}else{
		$('.month_exp_date').each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		$('.exp_date').each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		$('input[name=YearDD_EXPIRATIONDATE]').attr("disabled", "disabled"); 
		$('input[name="YearDD_EXPIRATIONDATE"]').after("<span class= 'month_exp_date error' style='background-color:yellow;'><font color='red'>Invalid month</font></span>");
	}
});


$('input[name=YearDD_EXPIRATIONDATE]').live('blur', function(){

	var yearVal = $('input[name=YearDD_EXPIRATIONDATE]').val();
	
	if(yearVal.indexOf('*') >= 0){
		var yearJSONVal = $('#YearDD_JSON').val();
		yearJSONVal = JSON.parse(yearJSONVal);
		yearVal = yearJSONVal.enteredValue;
	}
	
	var monthVal = $('input[name=MonthDD_EXPIRATIONDATE]').val();
	
	// checking whether month value is empty and year is entered , 
	// if the month value is empty, then throw an error
	if(monthVal != null  && monthVal.trim().length > 0){
		if(monthVal.indexOf('*') >= 0){
			var monthJSONVal = $('#MonthDD_JSON').val();
			monthJSONVal = JSON.parse(monthJSONVal);
			monthVal = monthJSONVal.enteredValue;
		}
	}
	else{
		$('.month_exp_date').each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		
		$('.exp_date').each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		$('input[name="YearDD_EXPIRATIONDATE"]').after("<span class= 'month_exp_date error' style='background-color:yellow;'><font color='red'>Month should not be empty.</font></span>");
		$('input[name=YearDD_EXPIRATIONDATE]').attr("disabled", "disabled");
	}
	
	var today = new Date();
	var minimumMonth = today.getMonth()+1;
	minimumMonth = parseFloat(minimumMonth);
	monthVal = parseFloat(monthVal);

	var minimumYear = today.getFullYear();

	minimumYear = parseFloat(minimumYear);

	yearVal = parseFloat(yearVal);
	if(yearVal >=  minimumYear){
		if(yearVal ==  minimumYear){
			if((monthVal >= 1 && monthVal <= 12) && (monthVal >= minimumMonth)){
				$('.exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
			}
			else{
				$('.exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				
				$('.month_exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				
				$('input[name="YearDD_EXPIRATIONDATE"]').after("<span class= 'exp_date error' style='background-color:yellow;'><font color='red'>Expiration date should be a future date</font</span>");
			}
		}
		else if(yearVal > minimumYear+20){

			$('.month_exp_date').each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			
			$('.exp_date').each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			$('input[name="YearDD_EXPIRATIONDATE"]').after("<span class= 'exp_date error' style='background-color:yellow;'><font color='red'>Expiration year out of range</font></span>");
			$('input[name=YearDD_EXPIRATIONDATE]').removeAttr("disabled");
		
		}
		else{
			$('.exp_date').each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
			});
		}
	}
	else{
		$('.exp_date').each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		$('input[name="YearDD_EXPIRATIONDATE"]').after("<span class= 'exp_date error' style='background-color:yellow;'><font color='red'>Expiration year should be a future year</font></span>");
	}
});

function replay(){
	createMap();
	
	var previouslyEnteredData = $("#previouslyGivenDataId").val();
	if(previouslyEnteredData != null && previouslyEnteredData.length > 0){

		var allEnterredJsonData = JSON.parse(previouslyEnteredData);

		var selectedRadioButtons = allEnterredJsonData["radioButtons"];
		if(!isEmptyJsonObject(selectedRadioButtons)){
			for(var radioButtonId in selectedRadioButtons){
				if(document.getElementById(radioButtonId)!=null){
					document.getElementById(radioButtonId).checked = true;
					activate(radioButtonId);
				}
			}
		}

		var selectedCheckBoxes = allEnterredJsonData["checkBoxes"];
		if(!isEmptyJsonObject(selectedCheckBoxes)){
			for(var checkBoxId in selectedCheckBoxes){
				if(document.getElementById(checkBoxId)!=null){
					document.getElementById(checkBoxId).checked = true;
				}
			}
		}

		var selectedSelectBoxes = allEnterredJsonData["selectBoxes"];
		if(!isEmptyJsonObject(selectedSelectBoxes)){
			for(var selectBoxId in selectedSelectBoxes){
				if(document.getElementById(selectBoxId)!=null){
					document.getElementById(selectBoxId).value = selectedSelectBoxes[selectBoxId];
					activate(selectBoxId);
				}
			}
		}

		var selectedTextBoxes = allEnterredJsonData["textBoxes"];
		if(!isEmptyJsonObject(selectedTextBoxes)){
			for(var textBoxId in selectedTextBoxes){
				if(document.getElementById(textBoxId)!=null){
					document.getElementById(textBoxId).value = selectedTextBoxes[textBoxId];
					var hiddenElementJSONVal = $('input[id = '+ textBoxId +'_JSON]').val();
					hiddenElementJSONVal = JSON.parse(hiddenElementJSONVal);
					var validationType = hiddenElementJSONVal.validation;
					var valueTarget = hiddenElementJSONVal.valueTarget;
					var selValue = selectedTextBoxes[textBoxId];
					var regex = /^([0-9]+):(Numeric|Alphanumeric)$/;
					if(valueTarget != null && valueTarget == 'consumer.dateOfBirth'){
						if(selValue.indexOf('*') >= 0){
							selValue = hiddenElementJSONVal.enteredValue;
						}
						formatDateValue(selValue, textBoxId);
						var formattedValue = $('#'+textBoxId).val();
						if(isValidDateOfBirth(formattedValue, textBoxId)){
							formatDateValue(formattedValue, textBoxId);
						}
					}
					else if(validationType != undefined && validationType != null && validationType == 'SSN'){
						if(selValue.indexOf('*') >= 0){
							formatSSN(hiddenElementJSONVal.enteredValue, textBoxId);
						}else{
							formatSSN(selValue, textBoxId);
						}
					}
					else if(validationType != undefined && validationType != null && validationType == 'Routing Number'){
						formatRoutingNumber(selValue, textBoxId);
					}
					else if(validationType != undefined && validationType != null && (validationType == 'Phone' || validationType == 'phone')){
						formatPhoneNumber(selValue, textBoxId);
					}
					else if(validationType != undefined && validationType != null && validationType == 'credit card number'){
						if(selValue.indexOf('*') >= 0){
							formatCreditCardNumber(hiddenElementJSONVal.enteredValue, textBoxId);
						}
						else{
							formatCreditCardNumber(selValue, textBoxId);
						}
					}
					else if(validationType != undefined && validationType != null && validationType.match(regex)){
						//validatePinNumber(selValue, textBoxId);
						var pinLength = 4;
						if(validationType.indexOf(':') > 0){
							var pinLengthVal = validationType.split(':');
							pinLength = pinLengthVal[0];
						}
						validatePinNumberForLenght(selValue, textBoxId,pinLength);
					}

					else if(validationType != undefined && ((validationType != null && validationType == 'Date') || validationType.match(/^([5-7]+)CAL$/)
							|| validationType.match(/^([5-7]+)CAL:/))){
						formatDateValue(selValue, textBoxId);
					}

					if(validationType != undefined && validationType == "Email"){
						var data = {"selectedValue" : selValue};
						$.ajax({
							type: 'POST',
							data:data,
							async:false,
							url: $('#contextPath').val()+"/static/updateDialogue/validateEmail",
							success: function(data){
							var responseJSON = JSON.parse(data);
							if(responseJSON.error != null && responseJSON.error != undefined){
								$('#'+textBoxId +'_emailClass').each(function(){
									if($(this).text() != null){
										$(this).remove();
									}
								});
								$('#'+textBoxId).after("<span id='"+textBoxId +"_emailClass' class='emailClass error' style='background-color:yellow;' ><font color='red'>"+responseJSON.error+"</font></span>");			
								return false;
							}else{
								$('#'+textBoxId +'_emailClass').each(function(){
									if($(this).text() != null){
										$(this).remove();
									}
								});
							}
						},
						error:function(status){

						}
						});
					}
					else if(valueTarget != null){
						if(valueTarget == 'consumer.shippingAddress.dwelling.postalCode' || valueTarget == 'consumer.billingAddress.dwelling.postalCode' 
							|| valueTarget == 'consumer.previousAddress.dwelling.postalCode'){
							prepopulateCityAndState(selValue, textBoxId, valueTarget);
						}
					}

					var jsonObj = $('input[id = '+ textBoxId +'_JSON]').val();
					if(jsonObj!=undefined){
						var jsonValue = JSON.parse(jsonObj);
						jsonValue["enteredValue"] = selectedTextBoxes[textBoxId];
						$('input[id = '+ textBoxId +'_JSON]').val(JSON.stringify(jsonValue));
					}
				}
			}
		}
		$(':text').each(function(){
			var id = $(this).attr('id');
			var name = $(this).attr('name');
			var jsonValue = $('input#'+id+'_JSON').val();
			if(jsonValue != null){
				jsonValue = JSON.parse(jsonValue);
				if(jsonValue.valueTarget=="consumer.homePhoneNumber" || 
						jsonValue.valueTarget=="consumer.cellPhoneNumber" || 
						jsonValue.valueTarget=="consumer.workPhoneNumber"){

					if(jsonValue.valueTargetVal!=null && jsonValue.valueTargetVal.trim().length > 0 && jsonValue.valueTargetVal == jsonValue.enteredValue){

						formatPhoneNumber(jsonValue.valueTargetVal, id);
						var modifiedValue = $('input[id='+id+']').val();
						jsonValue.valueTargetVal = modifiedValue;
						jsonValue.enteredValue = modifiedValue;
						$('input[id = '+ id +'_JSON]').val(JSON.stringify(jsonValue));

					}
				}
				else if(jsonValue.valueTarget=="consumer.socialSecurityNumber"){
					if(jsonValue.valueTargetVal!=null && jsonValue.valueTargetVal.trim().length > 0 && jsonValue.valueTargetVal == jsonValue.enteredValue){
						formatSSN(jsonValue.valueTargetVal, id);
						var modifiedValue = $('input[id='+id+']').val();
						jsonValue.valueTargetVal = modifiedValue;
						jsonValue.enteredValue = modifiedValue;
						$('input[id = '+ id +'_JSON]').val(JSON.stringify(jsonValue));
					}
				}
				if(jsonValue.valueTarget=="consumer.previousAddress.dwelling.stateOrProvince" || jsonValue.valueTarget=="consumer.billingAddress.dwelling.stateOrProvince" 
					|| jsonValue.valueTarget=="consumer.shippingAddress.dwelling.stateOrProvince" || jsonValue.valueTarget=="consumer.identification.driverLicense.state"){
					var enteredValue = "";
					if(jsonValue.enteredValue != null){
						enteredValue = jsonValue.enteredValue;
					}
					$(this).replaceWith('<select id='+id+' name='+name+' class=addressStateOrProvince>'+
							'<option value="">State</option>'+
							+'</select>') ;
					if(enteredValue != null && enteredValue.trim().length > 0 && enteredValue == jsonValue.valueTargetVal){
						populateOptionValue(id, jsonValue.valueTargetVal);
					}
					else if(enteredValue != null && enteredValue.trim().length > 0 && enteredValue != jsonValue.valueTargetVal){
						populateOptionValue(id, enteredValue);
					}
					else{
						populateOptionValue(id, enteredValue);
					}
				}
				if(jsonValue.toBeMasked == 'true' && jsonValue.enteredValue != null && (jsonValue.maskedValue == null 
						|| !(jsonValue.maskedValue.trim().length > 0))){
					var unMaskedValue = $(this).val();
					createMasking(unMaskedValue, id, jsonValue);
				}
			}
		});
		replaySelectedValues();
		displaypageInitialValues();
		reformatMonthYearSize();
		//showInitialFeaturesPriceValues();
	}
	else{
		replaySelectedValues();
		displaypageInitialValues();
		reformatMonthYearSize();
		//showInitialFeaturesPriceValues();
		$(':text').each(function(){
			var id = $(this).attr('id');
			var name = $(this).attr('name');
			var jsonValue = $('input#'+id+'_JSON').val();
			if(jsonValue != null){
				jsonValue = JSON.parse(jsonValue);
				if(jsonValue.valueTarget=="consumer.homePhoneNumber" || 
						jsonValue.valueTarget=="consumer.cellPhoneNumber" || 
						jsonValue.valueTarget=="consumer.workPhoneNumber"){

					if(jsonValue.valueTargetVal!=null && jsonValue.valueTargetVal.trim().length > 0){

						formatPhoneNumber(jsonValue.valueTargetVal, id);
						var modifiedValue = $('input[id='+id+']').val();
						jsonValue.valueTargetVal = modifiedValue;
						jsonValue.enteredValue = modifiedValue;
						$('input[id = '+ id +'_JSON]').val(JSON.stringify(jsonValue));
					}
				}

				if(jsonValue.valueTarget=="consumer.previousAddress.dwelling.stateOrProvince" || 
						jsonValue.valueTarget=="consumer.billingAddress.dwelling.stateOrProvince" || 
						jsonValue.valueTarget=="consumer.shippingAddress.dwelling.stateOrProvince" || jsonValue.valueTarget=="consumer.identification.driverLicense.state"){
					$(this).replaceWith('<select id='+id+' name='+name+' class =addressStateOrProvince>'+
							'<option value="">State</option>'+
							+'</select>') ;
					populateOptionValue(id, jsonValue.valueTargetVal);
				}

				if(jsonValue.toBeMasked == 'true' && jsonValue.enteredValue != null && (jsonValue.maskedValue == null 
						|| !(jsonValue.maskedValue.trim().length > 0))){
					var unMaskedValue = $(this).val();
					createMasking(unMaskedValue, id, jsonValue);
				}
			}
		});
	}
	
	var selectedFeaturesJSON = $('#selectedFeaturesJSONHiddenValue').val();
	var oqSelectedFeaturesJSON = $('#oqSelectedFeaturesHIDDENValue').val();

	if(selectedFeaturesJSON != null && selectedFeaturesJSON != "" && selectedFeaturesJSON.trim().length > 0){
		var selFeatJSON = JSON.parse(selectedFeaturesJSON);
		var selFeatsArray = selFeatJSON.selectedFeatureArray;
	}
	if(oqSelectedFeaturesJSON != null && typeof oqSelectedFeaturesJSON != "undefined" && oqSelectedFeaturesJSON.trim().length > 0){
		var oqSelFeatJSON = JSON.parse(oqSelectedFeaturesJSON);
		if(selFeatsArray != null && typeof selFeatsArray != "undefined"){
			mergeTwoJSONArrays(selFeatsArray,oqSelFeatJSON.oqselfeatures)
		}
		else{
			selFeatsArray = oqSelFeatJSON.oqselfeatures;
		}
		calculateMonthlyPriceAndInstallPrice(selFeatsArray);
		displayPriceDetails(selFeatsArray);
	}
	else{
		getPreviouslySelectedFeatures();
		collectAllSelectedFields();
	}
	$('.disclosure_type_label').each(function(){
		$(this).before("<span style='color:red; font-weight:bold;'>MANDATORY </span>");
	});
	$('.validateZipLoader').css('display','none');
}

function mergeTwoJSONArrays(selectedFeaturesJSON,prevSelFeatures)
{
	var selectedFeaturesJSONString = JSON.stringify(selectedFeaturesJSON);
	var newprevSelFeatures = [];
	for(var i=0; i<prevSelFeatures.length; i++)
	{
		var featureJSONObj = prevSelFeatures[i];
		var featureExtID = featureJSONObj.featureExternalID;
		if( selectedFeaturesJSONString.indexOf(featureExtID)==-1 )
		{
			newprevSelFeatures.push(prevSelFeatures[i]);
		}
	}
	if(newprevSelFeatures.length>0)
	{
		$.merge(selectedFeaturesJSON, newprevSelFeatures);
	}
}


function isEmptyJsonObject(jsonObject){
	for(var key in jsonObject){
		return false;
	}
	return true;
}


function replaySelectedValues(){
	buildEnableValues();
	if(enableFieldArray != undefined){
		for(var i = 0; i<enableFieldArray.length;i++){
			//activate(enableFieldArray[i]);
			if(enableFieldArray[i].indexOf('PAGE_EVENT:') >= 0){
				
			}
			else if(enableFieldArray[i].indexOf('SYSTEM_EVENT:') >= 0){
				
			}
			else{
				$('[id=' + enableFieldArray[i]+ '_FS]').show();
			}
		}
	}
}

$('input[type=text]').live('keyup', function(event){
	var id = $(this).attr('id');
	var hiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
	var hiddenElementJSONVal = JSON.parse(hiddenJsonElementVal);
	var validationType = hiddenElementJSONVal.validation;
	var valueTarget = hiddenElementJSONVal.valueTarget;

	if(valueTarget != null && (valueTarget == 'consumer.dateOfBirth' || 
			valueTarget == 'lineItem.schedulingInfo.desiredStartDate.date' || 
				valueTarget == 'lineItem.schedulingInfo.desiredStartDate2.date')){
		var npa = '', nxx = '';
		var userInput = $(this).val();
		var inputValue = $(this).val();
		var val = inputValue.replace(/\D/g, '');
		var newVal = '';
		var last4 = val;
		if(val.length > 4) {
			npa = val.substring(0, 2);
			nxx = val.substring(2, 4);
			newVal += npa + '/' + nxx+ '/';
			last4 = val.substring(4);
		} else if(val.length > 2) {
			npa = val.substring(0, 2);
			newVal += npa + '/';
			last4 = val.substring(2);
		}
		newVal += last4; 
		//If user types '-' after 3rd digit or 5th digit keep it
		if((userInput.length == 3) && (userInput.charAt(2) == '/')) {
			newVal += '/';
		} else if((userInput.length == 5) && (userInput.charAt(4) == '/'))  {
			newVal += '/';
		}
		$(this).val(newVal);
	}

	if(validationType != null && validationType == 'SSN'){
		var npa = '', nxx = '';
		var userInput = $(this).val();
		var inputValue = $(this).val();
		var val = inputValue.replace(/\D/g, '');
		var newVal = '';
		var last4 = val;
		if(val.length > 5) {
			npa = val.substring(0, 3);
			nxx = val.substring(3, 5);
			newVal += npa + '-' + nxx+ '-';
			last4 = val.substring(5);
		} else if(val.length > 3) {
			npa = val.substring(0, 3);
			newVal += npa + '-';
			last4 = val.substring(3);
		}
		newVal += last4; 
		//If user types '-' after 3rd digit or 5th digit keep it
		if((userInput.length == 4) && (userInput.charAt(3) == '-')) {
			newVal += '-';
		} else if((userInput.length == 7) && (userInput.charAt(6) == '-'))  {
			newVal += '-';
		}
		$(this).val(newVal);
	}
	else if(validationType != null && (validationType == 'Phone' || validationType == 'phone')){
		var npa = '', nxx = '';
		var userInput = $(this).val();
		var inputValue = $(this).val();
		var val = inputValue.replace(/\D/g, '');
		var newVal = '';
		var last4 = val;
		if(val.length > 6) {
			npa = val.substring(0, 3);
			nxx = val.substring(3, 6);
			newVal += npa + '-' + nxx+ '-';
			last4 = val.substring(6);
		} else if(val.length > 3) {
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
		$(this).val(newVal);
	}
});

var states = {"items":{"item":[{"id":1056,"context":2,"description":"Alabama","lookupKey":"AL"},
                               {"id":1057,"context":2,"description":"Alaska","lookupKey":"AK"},
                               {"id":1058,"context":2,"description":"Arizona","lookupKey":"AZ"},
                               {"id":1059,"context":2,"description":"Arkansas","lookupKey":"AR"},
                               {"id":1060,"context":2,"description":"California","lookupKey":"CA"},
                               {"id":1061,"context":2,"description":"Colorado","lookupKey":"CO"},
                               {"id":1062,"context":2,"description":"Connecticut","lookupKey":"CT"},
                               {"id":1063,"context":2,"description":"Delaware","lookupKey":"DE"},
                               {"id":1064,"context":2,"description":"District Of Columbia","lookupKey":"DC"},
                               {"id":1065,"context":2,"description":"Florida","lookupKey":"FL"},
                               {"id":1066,"context":2,"description":"Georgia","lookupKey":"GA"},
                               {"id":1067,"context":2,"description":"Hawaii","lookupKey":"HI"},
                               {"id":1068,"context":2,"description":"Idaho","lookupKey":"ID"},
                               {"id":1069,"context":2,"description":"Illinois","lookupKey":"IL"},
                               {"id":1070,"context":2,"description":"Indiana","lookupKey":"IN"},
                               {"id":1071,"context":2,"description":"Iowa","lookupKey":"IA"},
                               {"id":1072,"context":2,"description":"Kansas","lookupKey":"KS"},
                               {"id":1073,"context":2,"description":"Kentucky","lookupKey":"KY"},
                               {"id":1074,"context":2,"description":"Louisiana","lookupKey":"LA"},
                               {"id":1075,"context":2,"description":"Maine","lookupKey":"ME"},
                               {"id":1076,"context":2,"description":"Maryland","lookupKey":"MD"},
                               {"id":1077,"context":2,"description":"Massachusetts","lookupKey":"MA"},
                               {"id":1078,"context":2,"description":"Michigan","lookupKey":"MI"},
                               {"id":1079,"context":2,"description":"Minnesota","lookupKey":"MN"},
                               {"id":1080,"context":2,"description":"Mississippi","lookupKey":"MS"},
                               {"id":1081,"context":2,"description":"Missouri","lookupKey":"MO"},
                               {"id":1082,"context":2,"description":"Montana","lookupKey":"MT"},
                               {"id":1083,"context":2,"description":"Nebraska","lookupKey":"NE"},
                               {"id":1084,"context":2,"description":"Nevada","lookupKey":"NV"},
                               {"id":1085,"context":2,"description":"New Hampshire","lookupKey":"NH"},
                               {"id":1086,"context":2,"description":"New Jersey","lookupKey":"NJ"},
                               {"id":1087,"context":2,"description":"New Mexico","lookupKey":"NM"},
                               {"id":1088,"context":2,"description":"New York","lookupKey":"NY"},
                               {"id":1089,"context":2,"description":"North Carolina","lookupKey":"NC"},
                               {"id":1090,"context":2,"description":"North Dakota","lookupKey":"ND"},
                               {"id":1091,"context":2,"description":"Ohio","lookupKey":"OH"},
                               {"id":1092,"context":2,"description":"Oklahoma","lookupKey":"OK"},
                               {"id":1093,"context":2,"description":"Oregon","lookupKey":"OR"},
                               {"id":1094,"context":2,"description":"Pennsylvania","lookupKey":"PA"},
                               {"id":1095,"context":2,"description":"Puerto Rico","lookupKey":"PR"},
                               {"id":1096,"context":2,"description":"Rhode Island","lookupKey":"RI"},
                               {"id":1097,"context":2,"description":"South Carolina","lookupKey":"SC"},
                               {"id":1098,"context":2,"description":"South Dakota","lookupKey":"SD"},
                               {"id":1099,"context":2,"description":"Tennessee","lookupKey":"TN"},
                               {"id":1100,"context":2,"description":"Texas","lookupKey":"TX"},
                               {"id":1101,"context":2,"description":"Utah","lookupKey":"UT"},
                               {"id":1102,"context":2,"description":"Vermont","lookupKey":"VT"},
                               {"id":1103,"context":2,"description":"Virginia","lookupKey":"VA"},
                               {"id":1104,"context":2,"description":"Virgin Islands, British","lookupKey":"VG"},
                               {"id":1105,"context":2,"description":"Virgin Islands, U.S.","lookupKey":"VI"},
                               {"id":1106,"context":2,"description":"Washington","lookupKey":"WA"},
                               {"id":1107,"context":2,"description":"West Virginia","lookupKey":"WV"},
                               {"id":1108,"context":2,"description":"Wisconsin","lookupKey":"WI"},
                               {"id":1109,"context":2,"description":"Wyoming","lookupKey":"WY"}]}};

function populateOptionValue(id, prepopulatedVal){
	var stateList = states.items.item;
	$(stateList).each(function(){
		var txt = this.description;
		var val = this.lookupKey;
		if(prepopulatedVal != null && prepopulatedVal.trim().length > 0 && prepopulatedVal == val){
			$("select[id='"+id+"']").append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));
		}
		else{
			$("select[id='"+id+"']").append($("<option></option>").attr("value", val).text(txt));
		}
	});
}

function getDatesInRange(enteredDate, validationType){
	var today = new Date();
	var now = new Date();
	var val = validationType.split(':');
	var spl = val[1].split('-');
	var min = parseFloat(spl[0]);
	var max = parseFloat(spl[1]);

	var dateList = [];
	var todayDay = parseFloat(today.getDate());

	var rangeStart = todayDay + min;
	var rangeEnd = todayDay + max;
	for(var i = rangeStart; i <= rangeEnd; i++){
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		date.setDate(i);
		var fullDate =  parseInt(date.getMonth()+1) + "/" + parseInt(date.getDate()) + "/" + parseInt(date.getFullYear());
		var d = new Date(date.getFullYear(), date.getMonth(), date.getDate());
		if(val[0] == "6CAL"){
			if(d.getDay()==0){
				rangeEnd = rangeEnd +1;
			}
			else{
				var x = fullDate.split("/");
				var mon = x[0];
				var day = x[1];
				var year = x[2];
				if(mon<=9){
					mon = "0"+mon;
				}
				if(day<=9){
					day = "0"+day;
				}
				fullDate = mon+ "/"+day +"/"+year;
				dateList.push(fullDate);
			}
		}
		else if(val[0] == "5CAL"){
			if(d.getDay()==0||d.getDay()==6){
				rangeEnd = rangeEnd +1;
			}
			else{
				var x = fullDate.split("/");
				var mon = x[0];
				var day = x[1];
				var year = x[2];
				if(mon<=9){
					mon = "0"+mon;
				}
				if(day<=9){
					day = "0"+day;
				}
				fullDate = mon+ "/"+day +"/"+year;
				dateList.push(fullDate);
			}
		}
	}
	if(in_array(dateList, enteredDate)){
		return true;
	}
	else{
		return false;
	}
}


/*$(':radio').each(function(){
	if($(this).is(':checked')){
		var id = $(this).attr('id');
		var hiddenValue = $('#'+id+"_ExitOnNo").val();
		if(! typeof hiddenValue == "undefined" && hiddenValue=="ExitOnNo"){
			document.frmMain.action = "<%=request.getContextPath()%>/static/updateDialogue?sentFrom=exitOnClick";
			document.frmMain.submit();
		}
	}
});*/

/*
 * if the customer gives the addressLine1(apt, suite etc.,), then he must give the address line2 also if 
 * not we should show an error 
 */

var unselectedBillingAddrInfo = "";
var addressLineInfoSelected = 0;
var finalAddress = "";
$('.dwellingLineInfo').live('change', function(){
	$('.dwellingLineInfo').each(function(){
		if($(this).val() != null && $(this).val().trim().length > 0){
			addressLineInfoSelected++;
			finalAddress += $(this).val()+" "; 
		}
		else{
			unselectedBillingAddrInfo = $(this).attr('id');
		}
	});
	if(addressLineInfoSelected == 2 || addressLineInfoSelected == 0){
		$('span[id*="_unselectedAddress"]').each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		$('#addressLineAndLineInfo').val(finalAddress);
		unselectedBillingAddrInfo = "";
		addressLineInfoSelected = 0;
		finalAddress = "";
	}
	else{
		var id = $("#"+unselectedBillingAddrInfo).closest("div").attr("id");
		var text = $("div[id*='"+ id+"'] span:first-child");
		$('span[id*="_unselectedAddress"]').each(function(){
			if($(this).text() != null){
				$(this).remove();
			}
		});
		$('#'+unselectedBillingAddrInfo).after("<span id='"+unselectedBillingAddrInfo +"_unselectedAddress' class='emailClass error' style='background-color:yellow;' ><font color='red'>"+text.text()+" should also be selected</font></span>");
		unselectedBillingAddrInfo = "";
		addressLineInfoSelected = 0;
		finalAddress = "";
	}
});

//function displayCal(validationType, id){
//	
//	var val = validationType.split(':');
//	var spl = val[1].split('-');
//	var min = spl[0];
//	var max = spl[1];
//	
//	var natDays = [
//	               [1, 1, 'New Year'], //2015
//	               [1, 19, 'Martin Luther King'], //2015
//	               [2, 16, 'Washingtons Birthday'], //2015       
//	               [5, 25, 'Memorial Day'], //2015
//	               [7, 4, 'Independence Day'], //2015
//	               [9, 7, 'Labour Day'], //2015
//	               [10, 12, 'Columbus Day'], //2015
//	               [11, 11, 'Veterans Day'], //2015
//	               [11, 26, 'Thanks Giving Day'], //2015 
//	               [12, 25, 'Christmas'] //2015     
//	                ];
//
//	var dateMin = new Date();
//	var dateMax = new Date();
//	
//	dateMin.setDate(dateMin.getDate() + (dateMin.getHours() >= 14 ? 1 : 0));
//	dateMax.setDate(dateMax.getDate() + (dateMax.getHours() >= 14 ? 1 : 0));
//	
//	AddBusinessDays(dateMin, min, val[0]);
//	AddBusinessDays(dateMax, max, val[0]);
//	
//	
//	function AddBusinessDays(curdate, weekDaysToAdd) {
//		while (weekDaysToAdd > 0) {
//			curdate.setDate(curdate.getDate() + 1);
//			//check if current day is business day
//			if (noWeekendsOrHolidays(curdate)[0]) {
//				weekDaysToAdd--;
//			}
//		}
//	}
//
//	function noWeekendsOrHolidays(date) {
//		var noWeekend = []
//		if(val[0] == '5CAL'){
//			noWeekend = $.datepicker.noWeekends(date);
//		}
//		else if(val[0] == '6CAL'){
//			noWeekend = noSundays(date);
//		}
//		else if(val[0] == '7CAL'){
//			return noweekend = function (){
//				$('input[id='+id+']').datepicker({
//					minDate: dateMin,
//					maxDate: dateMax,
//					numOfDays: 7
//				});
//			};
//		}
//		if (noWeekend[0]) {
//			return nationalDays(date);
//		} else {
//			return noWeekend;
//		}
//	}
//
//	function nationalDays(date) {
//		for (i = 0; i < natDays.length; i++) {
//			if (date.getMonth() == natDays[i][0] - 1 && date.getDate() == natDays[i][1]) {
//				return [false, natDays[i][2] + '_day'];
//			}
//		}
//		return [true, ''];
//	}
//	
//	$('input[id='+id+']').datepicker({
//		inline: true,
//		beforeShowDay: noWeekendsOrHolidays,
//		minDate: dateMin,
//		maxDate: dateMax
//
//	});
//}
