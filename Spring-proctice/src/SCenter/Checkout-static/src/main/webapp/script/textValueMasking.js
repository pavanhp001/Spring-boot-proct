
$('#moveForward').live('click', function(){
		if(!returnUnselectedDialogues()){
			$('#unselDescription').css("display","block");
			$('#equipUnsel').css("display","none");
			return false;
		}
		else{
			$('#unselDescription').css("display","none");
			$('#equipUnsel').css("display","none");
		}
		if(!equipValidated()) {
			$('#equipUnsel').css("display","block");
			$('#unselDescription').css("display","none");
			return false;
		} else {
			$('#equipUnsel').css("display","none");
			$('#unselDescription').css("display","none");
		}
		
		var numberOfErrors = 0;
		$('.error').each(function(){
			if($(this).is(':visible')){
				numberOfErrors++;	
			}
		});
		if(numberOfErrors > 0){
			return false;
		}
		else{
			if($('#MonthDD').is(':visible') && $('#YearDD').is(':visible')){
				var monthVal = $('input[id=MonthDD]').val();
				var yearVal = $('input[id=YearDD]').val();
			
				var monthJSONVal = $('input[id=MonthDD_JSON]').val();
				var yearJSONVal = $('input[id=YearDD_JSON]').val();
				var monthParsedJsonValue = null;
				var yearParsedJSONValue = null;
				if(monthJSONVal != undefined){
					monthParsedJsonValue = JSON.parse(monthJSONVal);
				}
				if(yearJSONVal != undefined){
					yearParsedJSONValue = JSON.parse(yearJSONVal);
				}

				if(monthVal != null && monthVal != undefined && monthVal.indexOf('*') >= 0){
					monthVal = monthParsedJsonValue.enteredValue;
				}
				if(yearVal != null && yearVal != undefined && yearVal.indexOf('*') >= 0){
					yearVal = yearParsedJSONValue.enteredValue;
				}
				if(monthVal != undefined && monthVal.trim().length > 0 && monthParsedJsonValue != undefined){
					createMasking(monthVal, "MonthDD", monthParsedJsonValue);
				}
				if(yearVal != undefined && yearVal.trim().length > 0 && yearParsedJSONValue != undefined){
					createMasking(yearVal, "YearDD", yearParsedJSONValue);
				}
				var today = new Date();
				var minimumMonth = today.getMonth()+1;
				var minimumYear = today.getFullYear();
				minimumMonth = parseFloat(minimumMonth);
				minimumYear = parseFloat(minimumYear);
				monthVal = parseFloat(monthVal);
				yearVal = parseFloat(yearVal);

				if(monthVal >= 1 && monthVal <= 12){
					if(yearVal >=  minimumYear){
						if(yearVal ==  minimumYear){
							if(monthVal > minimumMonth){
								$('input[id *= "_EXPIRATIONDATE"]').val(monthVal+'/'+yearVal);
							}
						}
						else if(yearVal > minimumYear){
							$('input[id *= "_EXPIRATIONDATE"]').val(monthVal+'/'+yearVal);
						}
					}
				}
				var hiddenFields = "";
				$(':input[type=hidden]').each( function(){
					var eleName = this.name;
					if(eleName.indexOf('_CCExpDate') >= 0){
						hiddenFields = this.value;
						var externalID = eleName.substring(0, eleName.indexOf("_CCExpDate"));

						var hiddenJsonElementVal = $('input[name = '+ externalID +'_JSONVAL]').val();
						if(hiddenJsonElementVal!=undefined){
							var hiddenElementJSONVal = JSON.parse(hiddenJsonElementVal);
							hiddenElementJSONVal.enteredValue = hiddenFields;
							$('input[name = '+ externalID +'_JSONVAL]').val(JSON.stringify(hiddenElementJSONVal));
						}
					
						var selectedValue = $("#selectedValues").val();

						if(hiddenFields != null && hiddenFields.trim().length > 0){
							selectedValue += ","+eleName+":"+hiddenFields;

						}
						$("#selectedValues").val(selectedValue);

						return;
					}
				});
			}
		}
		
		$('input[type="text"]').each(function(){
			var id = $(this).attr('id');
			var jsonValue = $('input[id='+id+'_JSON]').val();
			if(jsonValue != undefined){
				jsonValue = JSON.parse(jsonValue);

				if(jsonValue.toBeMasked == 'true'){
					var unMaskedValue = $(this).val();
					createMasking(unMaskedValue, id, jsonValue);
				}
			}
		});
	/*}
	else{
		return false;
	}*/
});

function returnUnselectedDialogues(){
	// Fetch Textboxes are visible and empty fieldnames
	var areThereAnyOptional = 0;
	var txt="";
	$(':input[type=text]').each( function() { 
		if($(this).is(':visible') && $(this).val() == ""){
			txt+=$(this).attr("id")+","; 
		}
	});
	
	// Fetch radio buttons are visible and unchecked names
	var rad="";
	$(':input[type=radio]').each( function() {
		if($(this).is(':visible')){
			var id = $(this).attr("id");
			var radiotest = $(this).attr("name");
			if($("input[name="+radiotest+"]:checked").length == 0){
				if(rad.indexOf(radiotest) < 0){
					rad+=id+",";
				}
			}
		}
	});
	
	// Fetch Select fields are visible and unselected name for default values of "Please Select"
	var selectflds="";
	$('select').each( function() {
		var id = $(this).attr("id");
		if($(this).is(':visible') && $(this).val() == ""){
			selectflds+=id+",";
		}
	});
	
	// Fetch Checkboxes are visible and unchecked names
	var checkboxes="";
	$(':input[type=checkbox]').each( function() {
		if($(this).is(':visible')){
			var id = $(this).attr("id");
			var radiotest = $(this).attr("name");
			if($('input:checkbox[name='+radiotest+']').is(":checked") == false){
				checkboxes+=id+",";
			}
		}
	});
	
	var alldata = txt+rad+selectflds+checkboxes;
	if(alldata != null && endsWith(alldata,',')){
		alldata = alldata.substring(0, (alldata.trim().length-1));
	}
	try{
		$('.requiredField').each(function(){
			if($(this).is(':visible')){
				if($(this).text() != null){
					$("#" + $(this).attr("id")).css("display","none");
				}
			}
		});
	}catch(err){}
	if(alldata != null && alldata.trim().length > 0){
		var alldataArr = alldata.split(','); 
		var path = $('#contextPath').val();
		
		$(alldataArr).each(function(place, value){
			var id = value;
			var boolY = 'BOOL_Y_';
			var boolN = 'BOOL_N_';
			
			if(id.indexOf(boolY) > -1) {
				id = id.substr(boolY.length);
			} else if(id.indexOf(boolN) > -1) {
				id = id.substr(boolN.length);
			}
			//$("#elementId").is("input")
			if($("#" + id+"_required").is("img")){
				$("#" + id+"_required").css("display","inline");
				areThereAnyOptional++;
			}else
			{
				var nameValue = $("#" + value).attr("name");
				if($("#" + nameValue+"_required").is("img")){
					$("#" + nameValue+"_required").css("display","inline");
					areThereAnyOptional++;
				}
			}
			
		});
		if(areThereAnyOptional > 0){
			return false;	
		}
	}
	return true;
}

function createMasking(value, id, jsonValue){
	var formattedValue;
	var formattedValueArray;
	var containSlash = false;
	var containHypen = false;
	var validation = jsonValue.validation;
	formattedValue = value;

	if(value.indexOf('/') > 0){
		formattedValue = value.replace(/\//g,"");
		formattedValueArray = value.split('/');
		containSlash = true;
	}
	if(value.indexOf('-') > 0){
		formattedValue = value.replace(/\-/g,"");
		formattedValueArray = value.split('-');
		containHypen = true;
	}

	var maskedVal="";
	formattedValue = formattedValue.replace(/\d/g, "*");
	if(formattedValueArray != null){
		for(var len = 0; len < formattedValueArray.length; len++){
			var ele = formattedValueArray[len];
			var elementLength = ele.length;
			if(containSlash){
				maskedVal += formattedValue.substring(0, elementLength)+'/';
				formattedValue = formattedValue.substring(elementLength);
			}
			else if(containHypen){
				maskedVal += formattedValue.substring(0, elementLength)+'-';
				formattedValue = formattedValue.substring(elementLength);
			}
		}
	}
	if(maskedVal != null && maskedVal.trim().length > 0){
		if(containSlash){
			if(endsWith(maskedVal, '/')){
				maskedVal = maskedVal.substring(0, (maskedVal.length-1));
			}
			if(validation == 'SSN' || validation == 'credit card number'){
				var lastFour = value.substring(value.length - 4);
				maskedVal = maskedVal.substring(0, (maskedVal.length-4))+""+lastFour;
			}
			$('input[id='+id+']').val(maskedVal);
		}
		else if(containHypen){
			if(endsWith(maskedVal, '-')){
				maskedVal = maskedVal.substring(0, (maskedVal.length-1));
			}
			if(validation == 'SSN' || validation == 'credit card number'){
				var lastFour = value.substring(value.length - 4);
				maskedVal = maskedVal.substring(0, (maskedVal.length-4))+""+lastFour;
			}
			$('input[id='+id+']').val(maskedVal);
		}
	}
	else{
		if(validation == 'SSN' || validation == 'credit card number'){
			var lastFour = value.substring(value.length - 4);
			formattedValue = formattedValue.substring(0, (formattedValue.length-4))+""+lastFour;
		}
		$('input[id='+id+']').val(formattedValue);
	}
	if(!(value.indexOf('*') >= 0)){
		jsonValue.enteredValue = value;	
	}
	if(maskedVal != null && maskedVal.trim().length > 0){
		jsonValue.maskedValue = maskedVal;
	}
	else{
		jsonValue.maskedValue = formattedValue;
	}
	$('input[id='+id+'_JSON]').val(JSON.stringify(jsonValue));
}

function equipValidated() {
	if($("#enforceReciever").val().length>0 && $("#enforceReciever").val() == "true") {
		var equipValid = false;
		var DIG_RCVR = $("#DIG_RCVR option:selected").text();
		var HD_RCVR = $("#HD_RCVR option:selected").text();
		var HD_DVR = $("#HD_DVR option:selected").text();
		var DVR = $("#DVR option:selected").text();
		var CHILD_REC = $("#CHILD_REC option:selected").text();
		var RECORD_2_DVR = $("#RECORD_2_DVR option:selected").text();
		var ENHANCED_DVR = $("#ENHANCED_DVR option:selected").text();
		var DIGITAL_ADAPTER = $("#DIGITAL_ADAPTER option:selected").text();
		
//		if(DIG_RCVR.length == 0 && HD_RCVR.length == 0 && HD_DVR.length == 0 
//			&& DVR.length == 0 && CHILD_REC.length == 0 && RECORD_2_DVR.length == 0 
//			&& ENHANCED_DVR.length == 0)
//		{
//			equipValid = true;
//		}
		
		if(DIG_RCVR.length > 0 && DIG_RCVR.indexOf("0-") < 0 && DIG_RCVR.indexOf("Please") < 0){
			equipValid = true;
		} 
		if(HD_RCVR.length > 0 && HD_RCVR.indexOf("0-") < 0 && HD_RCVR.indexOf("Please") < 0){
			equipValid = true;
		} 
		if(HD_DVR.length > 0 && HD_DVR.indexOf("0-") < 0 && HD_DVR.indexOf("Please") < 0){
			equipValid = true;
		} 
		if(DVR.length > 0 && DVR.indexOf("0-") < 0 && DVR.indexOf("Please") < 0){
			equipValid = true;
		} 
		if(CHILD_REC.length > 0 && CHILD_REC.indexOf("0-") < 0 && CHILD_REC.indexOf("Please") < 0){
			equipValid = true;
		} 
		if(RECORD_2_DVR.length > 0 && RECORD_2_DVR.indexOf("0-") < 0 && RECORD_2_DVR.indexOf("Please") < 0){
			equipValid = true;
		} 
		if(ENHANCED_DVR.length > 0 && ENHANCED_DVR.indexOf("0-") < 0 && ENHANCED_DVR.indexOf("Please") < 0){
			equipValid = true;
		} 
		if(DIGITAL_ADAPTER.length > 0 && DIGITAL_ADAPTER.indexOf("0-") < 0 && DIGITAL_ADAPTER.indexOf("Please") < 0){
			equipValid = true;
		} 
		
		return equipValid;
	} else {
		return true;
	}
}
