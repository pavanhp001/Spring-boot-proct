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
				checkboxes+=$(this).attr("name")+",";
			}
			else if($('input:checkbox[name='+radiotest+']').is(":checked") == true){
				selCheckboxes += $(this).attr("name")+",";
				selCheckboxesValues+=$(this).attr("id")+":"+$(this).val()+",";
				jsonForCheckBoxes[$(this).attr("id")]=$(this).val();
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
