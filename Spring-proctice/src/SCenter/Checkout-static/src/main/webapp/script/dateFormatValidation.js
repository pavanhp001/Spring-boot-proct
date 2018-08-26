//selecting all unselected values 
function validateDialogue(){

	var insertData = $("#formdata");
	var selectedValues = $("#selectedValues");

	var jsonForTextBoxes = {};
	var jsonForRadioButtons = {};
	var jsonForSelectBoxes = {};
	var jsonForCheckBoxes = {};

	// Fetch Textboxes are visible and empty fieldnames
	var txt="";
	var selTxt = "";
	var selTxtValue = "";
	$(':input[type=text]').each( function() { 
		if($(this).is(':visible') && $(this).val() == ""){
			txt+=$(this).attr("name")+","; 
		}
		else if($(this).is(':visible') && $(this).val() != ""){
			selTxt += $(this).attr("name")+",";
			var hiddenJsonElementVal = $("#"+$(this).attr("id")+"_JSON").val();
			var enteredJSONDataObj = JSON.parse(hiddenJsonElementVal);
			selTxtValue +=$(this).attr("id")+":"+enteredJSONDataObj.enteredValue+",";
			jsonForTextBoxes[$(this).attr("id")] = enteredJSONDataObj.enteredValue;
		}
	});

	// Fetch radio buttons are visible and unchecked names
	var rad="";
	var selRad = "";
	var selRadValue = "";
	$(':input[type=radio]').each( function() {
		if($(this).is(':visible')){
			var id = $(this).attr("id");
			var radiotest = $(this).attr("name");
			if($("input[name="+radiotest+"]:checked").length == 0){
				if(rad.indexOf(radiotest) < 0){
					rad+=$(this).attr("name")+",";
				}
			}
			else if($("input[name="+radiotest+"]:checked").length != 0){
				if(selRad.indexOf(radiotest) < 0){
					selRad +=$(this).attr("name")+",";
				}
			}
			if($('input#'+id+'').is(':checked')){
				selRadValue+=$(this).attr("id")+":"+$(this).val()+",";
				jsonForRadioButtons[$(this).attr("id")] = $(this).val();
			}
		}
	});
	// Fetch Select fields are visible and unselected name for default values of "Please Select"
	var selectflds="";
	var selSelectBox = "";
	var selSelectValue = "";
	$('select').each( function() {
		if($(this).is(':visible') && $(this).val() == ""){
			selectflds+=$(this).attr("name")+",";
		}
		else if($(this).is(':visible') && $(this).val() != ""){
			selSelectBox += $(this).attr("name")+",";
			selSelectValue += $(this).attr("id")+":"+$(this).val()+",";
			jsonForSelectBoxes[$(this).attr("id")] = $(this).val();
		}
	});
	// Fetch Checkboxes are visible and unchecked names
	var checkboxes="";
	var selCheckboxes="";
	var selCheckboxesValues="";
	$(':input[type=checkbox]').each( function() {
		if($(this).is(':visible')){
			var radiotest = $(this).attr("name");
			if($('input:checkbox[name='+radiotest+']').is(":checked") == false){
				if(radiotest != "mandatory_disclosure_checkbox"){
					checkboxes+=$(this).attr("name")+",";
				}
			}
			else if($('input:checkbox[name='+radiotest+']').is(":checked") == true){
				if(radiotest != "mandatory_disclosure_checkbox"){
					selCheckboxes += $(this).attr("name")+",";
					selCheckboxesValues+=$(this).attr("id")+":"+$(this).val()+",";
					jsonForCheckBoxes[$(this).attr("id")]=$(this).val();
				 }
				}
			}
	});

	var allPreviouslyGivenData = {"textBoxes":jsonForTextBoxes,"radioButtons":jsonForRadioButtons,"selectBoxes":jsonForSelectBoxes,"checkBoxes":jsonForCheckBoxes};

	var alldata = txt+rad+selectflds+checkboxes;

	var allSelectedVal = selTxt + selRad + selSelectBox + selCheckboxes;

	var extIDSelectedValues = selTxtValue + selRadValue + selSelectValue + selCheckboxesValues;

	$("#extIDSelectedValues").val(extIDSelectedValues);
	$("#selectedValues").val(allSelectedVal);
	$("#formdata").val(alldata);
	$("#previouslyGivenDataId").val(JSON.stringify(allPreviouslyGivenData));
	collectAllSelectedFields();
	
	var increment = 0;
	$('input[id*="_system_event"]').each(function(){
		var id = $(this).attr('id');
		if($('#'+id).val() == "N"){
			increment++;
		}
	});
	if(increment > 0){
		symSendWarmTransferEvent();
	}
	$(".pc_steps_errorblk").css("display","none");
	//alert("isBackButtonClick"+isBackButtonClick);
	/* receiver count logic*/
	if(!isBackButtonClick 
			&& $('#isReceiverMatch') != undefined 
			  && $('#isReceiverMatch').val() != '' 
		        && $('#isReceiverMatch').val() == "true"
		          && $('#receiversList') != undefined 
		            && $('#receiversList').val() != '' ){
		var receiverCount = 0;
		//alert("isReceiverMatch"+$('#isReceiverMatch').val());
		var receiverList = JSON.parse($('#receiversList').val().replace(/'/g, '"'));
		$.each(receiverList,function(index,recevierName){
			var receiverOBJ = $("[name='"+recevierName+"']");
			//alert("receiverOBJ"+receiverOBJ);
			if(receiverOBJ != undefined && receiverOBJ.is(':visible')){
				 //alert(recevierName+"===chcked "+receiverOBJ.filter(':checked'));
					   if((receiverOBJ.is(":radio") || receiverOBJ.is(":checkbox")) 
						  && receiverOBJ.is(":checked")
						  && receiverOBJ.filter(':checked' ).val() != 'N' 
						     && JSON.parse(receiverOBJ.filter(':checked' ).val())['quantity'] != undefined
							   && JSON.parse(receiverOBJ.filter(':checked' ).val())['quantity'] != '' 
								   && JSON.parse(receiverOBJ.filter(':checked' ).val())['quantity'] == 'Y'){
						   receiverCount = receiverCount + 1;
						   console.log(recevierName+"====quantity==="+JSON.parse(receiverOBJ.filter(':checked' ).val())['quantity']);
					   }else if((!(receiverOBJ.is(":radio") || receiverOBJ.is(":checkbox"))) 
							   && receiverOBJ.val() != undefined 
								  && receiverOBJ.val() != ''  
									  && JSON.parse(receiverOBJ.val())['quantity'] != undefined
						                 && JSON.parse(receiverOBJ.val())['quantity'] != ''){
						   console.log(recevierName+"====quantity==="+parseInt(JSON.parse(receiverOBJ.val())['quantity']));
							receiverCount = receiverCount + parseInt(JSON.parse(receiverOBJ.val())['quantity']);
					   }
					   
				console.log("receiverCount==="+receiverCount);
			}
		});
		if($('#OUTLET_ACTIVATION')!= undefined &&  $('#OUTLET_ACTIVATION').val() != undefined && $('#OUTLET_ACTIVATION').val() != ''){
			var outletJSONOBj = JSON.parse($('#OUTLET_ACTIVATION').val());
			if(outletJSONOBj['quantity'] != undefined && outletJSONOBj['quantity'] != '' && !(parseInt(outletJSONOBj['quantity']) == receiverCount)){
				$(".pc_steps_errorblk").css("display","block");
				return false;
			}else{
				$(".pc_steps_errorblk").css("display","none");
			}
		}
	}
	$(".pc_steps_errorblk").css("display","none");
	return true;
}

function validateDialogues(){

	var isUnchecked = false;
	$(".error_astrick").hide();
	$(".error_message").remove();
	$('.mandatory_disclosure_checkbox').each(function(){
	if($(this).is(":visible")){
		$(this).attr("alt","visible");
		if(!$(this).is(":checked")){
				$(this).next().find('img').css({"display":"inline-block"});
				isUnchecked = true;
			}
		}else{
			$(this).removeAttr("alt");
			$(this).attr("checked",false);
		}
	});

	if(isUnchecked){
		var span = "<span class='error_message' style='color:red'>* Required Fields</span>";
	//	$(".pc_frameblk_cont").append(span);
		$(".pc_frameblk").append(requiredFields);
		return false;
	}
	var isValid = validateDialogue();
	if(!isValid){
		return false;
	}
	return true;
}
function buildDialogueJSON(){

	var dialogueJson = new Object();
	var childJson = new Object();
	var childJsonArray = [];

	$(':input[type=text]').each( function() {
		if($(this).is(':visible') && $(this).val() != ""){
			var hiddenJsonElementVal = $("#"+$(this).attr("id")+"_JSON").val();
			if(isJSON(hiddenJsonElementVal)){
				var enteredJSONDataObj = JSON.parse(hiddenJsonElementVal);
				var enteredValue;
				if(enteredJSONDataObj.enteredValue != null && enteredJSONDataObj.enteredValue.trim().length != 0){
					enteredValue =   enteredJSONDataObj.enteredValue;	
				}
				else{
					enteredValue = enteredJSONDataObj.valueTargetVal;
				}
				childJson = {
						name: $(this).attr("name"),
						selected_value : enteredValue,	
						dialogue_type: "string",
						type: "dialogue"
				};
				childJsonArray.push(JSON.stringify(childJson));
			}
			else{
				var childJson = {
						name: $(this).attr("name"),
						selected_value : $(this).val(),	
						dialogue_type: "string",
						type: "dialogue"
				};
				childJsonArray.push(JSON.stringify(childJson));
			}
		}
	});

	$(':input[type=radio]').each( function() {
		if($(this).is(':visible')){
			var id = $(this).attr("id");
			var radiotest = $(this).attr("name");
			if($('input#'+id+'').is(':checked')){
				if(isJSON($(this).val())){
					var radioJSONObject = JSON.parse($(this).val());

					var childJson = {
							name: $(this).attr("name"),
							selected_value : radioJSONObject.quantity,	
							dialogue_type: radioJSONObject.dataConstraint,
							type: radioJSONObject.type
					};
					childJsonArray.push(JSON.stringify(childJson));

				}
				else
					var childJson = {
							name: $(this).attr("name"),
							selected_value : $(this).val(),	
							dialogue_type: "boolean",
							type: "dialogue"
					};
					childJsonArray.push(JSON.stringify(childJson));

				}
			}
	});
	
	$('select').each( function() {
		if($(this).is(':visible') && $(this).val() != ""){
			if(isJSON($(this).val())){
				var selectBoxJSONObject = JSON.parse($(this).val())
				if(selectBoxJSONObject.type == "featureGroup"){
					var childJson = {
							name: selectBoxJSONObject.featureExternalID,
							selected_value : selectBoxJSONObject.quantity,	
							type: selectBoxJSONObject.type,
							featuregroup:selectBoxJSONObject.featureGroupExternalID,
							dialogue_type:"string"
					};
					
					childJsonArray.push(JSON.stringify(childJson));
				}
				else if(selectBoxJSONObject.type == "feature"){
					var childJson = {
							name: $(this).attr("name"),
							selected_value : selectBoxJSONObject.quantity,	
							type: selectBoxJSONObject.type,
							featuregroup:selectBoxJSONObject.featureGroupExternalID,
							dialogue_type:selectBoxJSONObject.dataConstraint
					};
					
					childJsonArray.push(JSON.stringify(childJson));
				}
				else{
					var childJson = {
							name: $(this).attr("name"),
							selected_value : $(this).val(),	
							type: "dialogue",
							dialogue_type:"string"
					};
					
					childJsonArray.push(JSON.stringify(childJson));
				}
			}
		}
	});
	var dialogueJson = {
			dialgoues: JSON.stringify(childJsonArray)
	};
	
	return dialogueJson;
}

function isJSON(stringValue){
	var is_json = true;
	try{
		var json = $.parseJSON(stringValue);
	}catch(err){
		is_json = false;
	}
	return is_json;
}
