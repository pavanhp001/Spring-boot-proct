$(document).ready(function() {
	$("input:text,input:radio,input:checkbox,select").bind("change",function(){
		storedSessionData();
	});
	var sessionPage = $("#sessionPage").val();
	if(sessionPage != undefined 
			&& sessionPage != '' 
				&& sessionPage == 'oqDemo'){
		/*$("input[readonly='readonly']").datepicker({
			  onSelect: function() {
			     storedSessionData();
			  }
			});*/
	}
});

/**
 * logic to create a data structure when a select box or a check box is selected
 * this data structure is used to build the price display
 */
$('.FeatureSelect').live('change', function(){
	collectAllSelectedFields();
});
$('.FeatureCheck').live('click', function(){
	collectAllSelectedFields();
});

$('.FeatureGroup').live('change', function(){
	collectAllSelectedFields();
});

$('.promotionClass').live('click', function(){
	if(isSameTypePromoSelected($(this).attr("id"))){
		collectAllSelectedFields();
	}
	else{
		$(this).attr('checked', false);
		symShowAlert("Select only one promotion of this type.");
		
	}
});

var prevSelFeatures = [];
function getPreviouslySelectedFeatures(){
	var selectedFeaturesJSONPrev = $('#selectedFeaturesJSONHiddenValue').val();
	var selFeatJSON = null;
	if(selectedFeaturesJSONPrev != null && selectedFeaturesJSONPrev.trim().length > 0){
		selFeatJSON = JSON.parse(selectedFeaturesJSONPrev);
	}
	if(selFeatJSON != null && typeof selFeatJSON != "undefined" && (selFeatJSON.selectedFeatureArray != null && typeof selFeatJSON.selectedFeatureArray != "undefined")){
		prevSelFeatures = selFeatJSON.selectedFeatureArray;
	}
}

/**
 * collects all the selected fields in the page and builds an array of all the selected values
 * later this array is iterated and fields are added to the flyout dynamically
 */
function collectAllSelectedFields(){

	var selectedFeaturesJSON = [];

	var selectedPromotionJSON = [];
	$('.FeatureSelect').each(function(){
		if($(this).is(':visible')){
			var selectedOption = $("#"+$(this).attr("id")+" option:selected").val();
			var id = $(this).attr("id");
			if(typeof selectedOption != "undefined" && selectedOption != ""){
				selectedFeaturesJSON.push(JSON.parse(selectedOption));
			}
		}
	});
	$('.FeatureGroup').each(function(){
		if($(this).is(':visible')){
			var selectedOption = $("#"+$(this).attr("id")+" option:selected").val();
			var id = $(this).attr("id");
			if(typeof selectedOption != "undefined" && selectedOption != ""){
				selectedFeaturesJSON.push(JSON.parse(selectedOption));
			}
		}
	});
	$('.FeatureCheck').each(function(){
		if($(this).is(':visible')){
			if($(this).is(':checked')){
				var selectedOption = $("#"+$(this).attr("id")).val();
				try{
					var selectedOptJSON = JSON.parse(selectedOption);
				}catch(err){
					return;
				}
				var id = $(this).attr("id");
				if(typeof selectedOption != "undefined" && selectedOption != ""){
					selectedFeaturesJSON.push(JSON.parse(selectedOption));
				}
			}
		}
	});

	$('.promotionClass').each(function(){
		if($(this).is(':checked')){
			var selectedOption = $("#"+$(this).attr("id")).val();
			var id = $(this).attr("id");
			isSameTypePromoSelected(id);
			if(typeof selectedOption != "undefined" && selectedOption != ""){
				selectedFeaturesJSON.push(JSON.parse(selectedOption));
			} else {
				selectedOption = $(this).attr("value");
				if(typeof selectedOption != "undefined" && selectedOption != ""){
					selectedFeaturesJSON.push(JSON.parse(selectedOption));
				}
			}
		}
	});

	var oqSelectedFeaturesJSON = $('#oqSelectedFeaturesHIDDENValue').val();

	$('#selectedFeatureJSONValue').val(JSON.stringify(selectedFeaturesJSON));
	
	if(prevSelFeatures != null && typeof prevSelFeatures != "undefined"){
		mergeTwoJSONArrays(selectedFeaturesJSON,prevSelFeatures);
	}
	calculateMonthlyPriceAndInstallPrice(selectedFeaturesJSON);

	displayPriceDetails(selectedFeaturesJSON);
	
	$("#selectedFeaturesJSONHiddenValue").val(JSON.stringify(selectedFeaturesJSON));
	
}
	function storedSessionData(){
		var sessionPage = $("#sessionPage").val();
		//alert("storedSessionData============"+sessionPage);
		var data1;
		var isDataAdded = false;
		if(sessionPage != undefined 
				&& sessionPage != '' 
					&& sessionPage == 'productInfo'){
			collectAllSelectedFields();
			data1 = { "selectedFeaturesJSONHiddenValue" : $("#selectedFeaturesJSONHiddenValue").val()};
		}
		if(sessionPage != undefined 
				&& sessionPage != '' 
					&& sessionPage == 'oqDemo'){
			data1 = {"previouslyGivenDataId" : getPervis()};
		}
		console.log(isDataAdded+"===data==="+JSON.stringify(data1));
		if(data1){
			try{    
				$.ajax({
					type: 'POST',
					data: data1,
					async:false,
					url: $('#contextPath').val()+"/static/storedSessionData",
					success: function(data){
				    }
				});
			}catch(e){
			  }
		}
	}

function getPervis(){
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

	return JSON.stringify({"textBoxes":jsonForTextBoxes,"radioButtons":jsonForRadioButtons,"selectBoxes":jsonForSelectBoxes,"checkBoxes":jsonForCheckBoxes});
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



function predicatBy(prop){
	return function(a,b){
		if( parseFloat(a[prop]) > parseFloat(b[prop])){
			return -1;
		}else if( parseFloat(a[prop]) < parseFloat(b[prop]) ){
			return 1;
		}
		return 0;
	};
}


function isSameTypePromoSelected(id){

	var noOfInfoPromotions = 0;
	var noOfMonthlyPromotions = 0;
	var noOfOneTimePromotions = 0;

	var promoJSON = JSON.parse($("#"+id+"_hiddenPromo").val());
	var conflictExternalID = promoJSON.conflictExternalID;
	if(conflictExternalID!=undefined && conflictExternalID != null && conflictExternalID.trim().length > 0 ){
		var conflicts = conflictExternalID.split(',');
		if($("#"+id).is(':checked'))
		{
			for(var i = 0; i < conflicts.length; i++){
				$('#' + conflicts[i]).attr('disabled', true);
				if($('#' + conflicts[i]).attr('checked')){
					$('#' + conflicts[i]).attr('checked', false);
				}
			}
		}
		else
		{
			for(var i = 0; i < conflicts.length; i++)
			{
				$('#' + conflicts[i]).attr('disabled', false);
				if($('#' + conflicts[i]).attr('checked'))
				{
					$('#' + conflicts[i]).attr('checked', false);
				}
			}
		}
	}
	
	/**
	 *	iterate over each promotion and get all the type of promotions that are present,
	 * 	if there is another promotion of same type that is already selected, we should 
	 *	not allow this promotion to be selected.
	 */

	$('.promotionClass').each(function(){
		if (this.checked) {
			var id = $(this).attr('id');
			var promoJSON = $("#"+id+"_hiddenPromo").val();
			promoJson = JSON.parse(promoJSON);
			var promoType = promoJson.type; 

			if(promoType == "informationalPromotion" || promoType == "unspecifiedType"){
				noOfInfoPromotions++;
			}
			else if(promoType == "baseMonthlyDiscount"){
				noOfMonthlyPromotions++;
			}
			else if(promoType == "oneTimeFeeDiscount"){
				noOfOneTimePromotions++;
			}
		}
	});
	if(noOfMonthlyPromotions > 1){
		return false;
	}
	else if(noOfOneTimePromotions > 1){
		return false;
	}
	return true;
}

/**
 * iterate over the selectFeatureJSON and add up all the monthly price and install price and 
 * display them on the screen
 * 
 * @param selectedFeaturesJSON
 * @return
 */
function calculateMonthlyPriceAndInstallPrice(selectedFeaturesJSON){
	var baseRecPriceOptionsSum = $("#lineItemMonthlyPrice").val();
	var initialBaseRecPriceOptionsSum = $("#lineItemMonthlyPrice").val();
	
	var baseNonRecPriceOptionsSum = $("#lineItemInstallationPrice").val();
	var initialBaseNonRecPriceOptionsSum = $("#lineItemInstallationPrice").val();
	$(selectedFeaturesJSON).each(function(id, value){
		if(value != null){
			//value = JSON.parse(value);
			var type = value.type; 
			var baseRecPrice = value.recuringPrice;
			var isIncluded = value.includedFlag;
			var baseNonRecPrice = value.nonRecuringPrice;
			
			if(!isIncluded){
				if(!(typeof baseRecPrice == "undefined")){
					if(type =="promotion"){
						var typeOfPromotion = value.priceValueType;
						if(typeOfPromotion == 'relative'){
							baseRecPriceOptionsSum = parseFloat(baseRecPriceOptionsSum) - parseFloat(baseRecPrice);
						}
						else if(typeOfPromotion == 'absolute'){
							var incrementInRecPrice = parseFloat(baseRecPriceOptionsSum) - parseFloat(initialBaseRecPriceOptionsSum);
							if(incrementInRecPrice > 0){
								baseRecPriceOptionsSum = parseFloat(baseRecPrice) + parseFloat(incrementInRecPrice);
							}else{
								baseRecPriceOptionsSum = parseFloat(baseRecPrice);
							}
						}
					}else{
						baseRecPriceOptionsSum = parseFloat(baseRecPriceOptionsSum) + parseFloat(baseRecPrice);
					}
				}
				if(!(typeof baseNonRecPrice == "undefined")){
					if(type =="promotion"){
						var typeOfPromotion = value.priceValueType;
						if(typeOfPromotion == 'absolute'){
							baseNonRecPriceOptionsSum = parseFloat(baseNonRecPriceOptionsSum) - parseFloat(initialBaseNonRecPriceOptionsSum);
							baseNonRecPriceOptionsSum = parseFloat(baseNonRecPriceOptionsSum)+parseFloat(baseNonRecPrice);
						}else{
							baseNonRecPriceOptionsSum = parseFloat(baseNonRecPriceOptionsSum) - parseFloat(baseNonRecPrice);
						}
					}
					else{
						baseNonRecPriceOptionsSum = parseFloat(baseNonRecPriceOptionsSum) + parseFloat(baseNonRecPrice);
					}
				}
			}
		}
	});

	$('span#monthlyCost').html("$"+parseFloat(baseRecPriceOptionsSum).toFixed(2));
	$("#monthlyCostAmtFld").val(parseFloat(baseRecPriceOptionsSum).toFixed(2));

	$('span#oneTimePrice').html("$"+parseFloat(baseNonRecPriceOptionsSum).toFixed(2));
	$("#oneTimePriceFld").val(parseFloat(baseNonRecPriceOptionsSum).toFixed(2));
}

/**
 * iterates over the selectedFeaturesJSON and builds the elements of the flyout
 * @param selectedFeaturesJSON
 * @return
 */
function displayPriceDetails(selectedFeaturesJSON){
	try{
	$("#monthlyCostTableData > tbody").html("");
	$("#inStallCostTableData > tbody").html("");
	var productMonthlyPrice = getPriceFormat($("#productMonthlyPrice").val());
	var productInstallationPrice = getPriceFormat($("#productInstallationPrice").val());
	
	var recPriceRow = "<tr> <td style='width: 50%;font-weight:bold;' align='left'>Base Monthly Price</td><td style='width: 25%;font-weight:bold;' align='center'>-</td><td style='width: 25%;font-weight:bold;' align='right'>"+"$"+productMonthlyPrice+"</td></tr>";
	var nonRecPriceRow = "<tr> <td style='width: 50%;font-weight:bold;' align='left'>Base Installation Price</td><td style='width: 25%;font-weight:bold;' align='center'>-</td><td style='width: 25%;font-weight:bold;' align='right'>"+"$"+productInstallationPrice+"</td></tr>";
	var promotionMonthlyRow = "";
	var tMonthlyRow = "";
	$('#monthlyCostTableData tbody').append(recPriceRow);
	$('#inStallCostTableData tbody').append(nonRecPriceRow);
	var recSelectedJSON = new Array();
	if(selectedFeaturesJSON.length>0){
		recSelectedJSON = loadRecAndNonRecPriceValues(selectedFeaturesJSON,"recuringPrice");
		recSelectedJSON.sort(predicatBy("recuringPrice"));
	}
	
	$(recSelectedJSON).each(function(id, value){
		if(value != null){
			//value = JSON.parse(value);
			var description = value.description;
			var isIncluded = value.includedFlag;
			var qty = "-";
			if(typeof value.quantity != "undefined"){
				qty = value.quantity;
				if(qty == 'Y'){
					qty = '&#x2713;';
				}
			}
			var baseRecPrice ="undefined";
			if(value.recuringPrice!=undefined){
				baseRecPrice = getPriceFormat(value.recuringPrice);
			}
			
			var externalID = value.featureExternalID;
			
			if(!isIncluded && baseRecPrice != "undefined" ){
				if(value.type == "promotion"){
					if(value.priceValueType == "relative"){
						promotionMonthlyRow = "<tr id='"+externalID+"_monthly'><td style='width: 50%;' align='left'>Base Monthly Price</td><td style='width: 25%;' align='center'>"+qty+"</td><td style='width: 25%;' align='right'>"+"$"+getPriceFormat(productMonthlyPrice-baseRecPrice)+"</td></tr>";
					}else{
						promotionMonthlyRow = "<tr id='"+externalID+"_monthly'><td style='width: 50%;' align='left'>Base Monthly Price</td><td style='width: 25%;' align='center'>"+qty+"</td><td style='width: 25%;' align='right'>"+"$"+baseRecPrice+"</td></tr>";
					}
				}
				else{
					if( parseFloat(value.recuringPrice) != 0.0){
						tMonthlyRow = tMonthlyRow+"<tr class = 'flyout_class' id='"+externalID+"_monthly'><td style='width: 50%;' align='left'>"+description+"</td><td style='width: 25%;' align='center'>"+qty+"</td><td style='width: 25%;' align='right'>"+"$"+baseRecPrice+"</td></tr>";
					}
				}
			}
		}
	});
	if(promotionMonthlyRow!=""){
		$('#monthlyCostTableData').html(promotionMonthlyRow);
	}else{
		var productPriceTableData = "<tr id='monthlyPriceDisplay' ><td style='width: 50%;font-weight:bold;' align='left'>Base Monthly Price</td><td style='width: 25%;font-weight:bold;' align='center'>-</td><td style='width: 25%;font-weight:bold;' align='right'>"+"$"+productMonthlyPrice+"</td></tr>";
		$('#monthlyCostTableData').html(productPriceTableData);
	}
	if(tMonthlyRow!=""){
		tMonthlyRow = $('#monthlyCostTableData').html()+tMonthlyRow;
		$('#monthlyCostTableData').html(tMonthlyRow);	
	}
	
	
	var promotionInstallationRow = "";
	var tOneTimeInstallationRow = "";
	var nonRecSelectedJSON = new Array();
	if(selectedFeaturesJSON.length>0){
		nonRecSelectedJSON = loadRecAndNonRecPriceValues(selectedFeaturesJSON,"nonRecuringPrice");
		nonRecSelectedJSON.sort(predicatBy("nonRecuringPrice"));
	}
	
	$(nonRecSelectedJSON).each(function(id, value){
		if(value != null){
			var description = value.description;
			var qty = "-";
			var isIncluded = value.includedFlag;
			if(typeof value.quantity != "undefined"){
				qty = value.quantity;
				if(qty == 'Y'){
					qty = '&#x2713;';
				}
			}
			
			var baseNonRecPrice ="undefined";
			if(value.nonRecuringPrice!=undefined){
				baseNonRecPrice = getPriceFormat(value.nonRecuringPrice);
			}
			
			var externalID = value.featureExternalID;
			if(!isIncluded && baseNonRecPrice != "undefined" ){
				if(value.type == "promotion"){
					if(value.priceValueType == "relative"){
						promotionInstallationRow = "<tr id='"+externalID+"_installation'><td style='width: 50%;' align='left'>Base Installation Price</td><td style='width: 25%;' align='center'>"+qty+"</td><td style='width: 25%;' align='right'>"+"$"+getPriceFormat(productInstallationPrice-baseNonRecPrice)+"</td></tr>";
					}else{
						promotionInstallationRow = "<tr id='"+externalID+"_installation'><td style='width: 50%;' align='left'>Base Installation Price</td><td style='width: 25%;' align='center'>"+qty+"</td><td style='width: 25%;' align='right'>"+"$"+baseNonRecPrice+"</td></tr>";
					}
				}
				else {
					if( parseFloat(value.nonRecuringPrice) != 0.0){
						tOneTimeInstallationRow = tOneTimeInstallationRow+"<tr class = 'flyout_class' id='"+externalID+"_installation'><td style='width: 50%;' align='left'>"+description+"</td><td style='width: 25%;' align='center'>"+qty+"</td><td style='width: 25%;' align='right'>"+"$"+baseNonRecPrice+"</td></tr>";
					}
				}
			}
		}
	});
	
	if(promotionInstallationRow!=""){
		$('#inStallCostTableData').html(promotionInstallationRow);
	}else{
		var productPriceTableData = "<tr id='monthlyPriceDisplay' ><td style='width: 50%;font-weight:bold;' align='left'>Base Installation Price</td><td style='width: 25%;font-weight:bold;' align='center'>-</td><td style='width: 25%;font-weight:bold;' align='right'>"+"$"+productInstallationPrice+"</td></tr>";
		$('#inStallCostTableData').html(productPriceTableData);
	}
	if(tOneTimeInstallationRow!=""){
		tOneTimeInstallationRow = $('#inStallCostTableData').html()+tOneTimeInstallationRow;
		$('#inStallCostTableData').html(tOneTimeInstallationRow);	
	}
	
	}catch(err){}
	
}


function loadRecAndNonRecPriceValues(selectedFeaturesJSON,priceType){
	var recAndNonRecSelectedJSON = new Array();
	for(var i=0;selectedFeaturesJSON.length>i;i++){
		if((selectedFeaturesJSON[i])[priceType]!=undefined && (selectedFeaturesJSON[i])[priceType]!=null){
			recAndNonRecSelectedJSON.push(selectedFeaturesJSON[i]);
		}
	}
	return recAndNonRecSelectedJSON;
}

function getPriceFormat(priceValue){
	try{
		var stringPriceValue = priceValue+"";
		var priceArray = stringPriceValue.split(".");
		if(priceArray.length > 1 && priceArray[1].length==1){
			stringPriceValue = priceArray[0]+"."+priceArray[1]+"0";
		}
		else if(priceArray.length == 1){
			stringPriceValue = stringPriceValue+".00";
		}else if(priceArray.length > 1 && priceArray[1].length>2){
			stringPriceValue = priceArray[0]+"."+priceArray[1].substring(0,2);
		}
	}catch(err){}
	
	return stringPriceValue;
}


/**
 * checks whether we came to this page by hitting back or if the values are present from previous CKO.
 * if the values are present during the previous CKO, calculate the prices and add them to the flyout 
 * @return void
 */
function defaultFlyoutValues(){
	var startingPoint =  $('#startingPoint').val();
	if(startingPoint == 'oqDemo'){
		var selFeatureHiddenJSONVal = $('#selectedFeaturesJSONHiddenValue').val();
		var jsonArray = $.parseJSON(selFeatureHiddenJSONVal);
		if(!(typeof selFeatureHiddenJSONVal == "undefined") && selFeatureHiddenJSONVal != null){
			calculateMonthlyPriceAndInstallPrice(jsonArray);
			displayPriceDetails(jsonArray);
		}
		
		$('.promotionClass').each(function(){
			if($(this).is(':checked')){
				var id = $(this).attr("id");
				isSameTypePromoSelected(id);
			}
		});
	}
	else{
		collectAllSelectedFields();
	}
}
