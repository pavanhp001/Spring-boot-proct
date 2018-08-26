
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link rel="stylesheet" href="<c:url value='/style/jquery.ui.all.css'/>" />

<link rel="stylesheet" href="<c:url value='/style/CKO_att_iframe.css'/>" />

<link rel="stylesheet" href="<c:url value='/style/jquery.datepick.css'/>" />
<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>

<script src="<c:url value='/js/jquery-1.8.0.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.widget.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>
<script src="<c:url value='/js/jquery.datepick.js'/>"></script>
<script src="<c:url value='/js/utils.js'/>"></script>
<script src="<c:url value='/js/jquery.maskedinput.js'/>"></script>
<script src="<c:url value='/script/dateFormatValidation.js'/>"></script>
<script src="<c:url value='/script/validateTextFields.js'/>"></script>
<script src="<c:url value='/script/textValueMasking.js'/>"></script>
<script src="<c:url value='/script/validateFields.js'/>"></script>
<script src="<c:url value='/script/features.js'/>"></script>
<script src="<c:url value='/js/blockUI.js'/>"></script>


<style type="text/css">
.pc_pdetails_cont .pc_steps {
  width: 95px !important;
}
.address {
	width: 300px;
	height: 110px;
	border: 2px solid #a1a1a1;
	border-radius: 5px;
	padding: 8px;
	display: none;
}

.rLabel {
	
}

.lLabel {
	float: left;
	font-weight: bold;
	width: 25%;
}
.radioLabel{
	vertical-align: top;
	padding-left: 50px; 
}
#astrikLbl {
    display: block;
    overflow: hidden;
    width:95px;
}

#astrik {
    float: left;
    display: block;
    height: 12px;
}

.display_group_header{
	font-weight: bolder;
}

.disclosure_type_label{
	color:black;
	font-weight:bold;
}

.info_type_label{
	color:green;
	text-indent:50px;
}
.CKOContentLoading
{
    background:#fff url('<%=request.getContextPath()%>/images/spinner.gif') no-repeat 50% 50%;
}
.ui-dialog .ui-dialog-titlebar-close {
    display: none;
    height: 18px;
    margin: -10px 0 0;
    padding: 1px;
    position: absolute;
    right: 0.3em;
    top: 50%;
    width: 19px;
}
.ui-dialog-titlebar {
    background: none repeat-x scroll 50% 50% #6ca741;
}
.ui-dialog-title {
  text-align: center;
  width: 100%;
}
.ui-dialog .ui-dialog-content {
    background: none repeat scroll 0 0 #ffffff;
    border: 0 none;
    overflow: auto;
    padding: 0.5em 1em;
}

</style>

<script type="text/javascript">
	function goBackPage(){
		var isValidData = validateDialogue();
		if(isValidData){
			var CKOInputVal = document.getElementById('iData').value;
			collectAllSelectedFields();
			$('#oqSelectedFeaturesHIDDENValue').val($('#selectedFeatureJSONValue').val());
			
			document.frmMain.action = "<%=request.getContextPath()%>/static/CKO?CKOInput="+CKOInputVal;
			document.frmMain.submit();
		}
	}
	
	$(document).ready(function() {
		var labels = $("label");
		labels.each(function(){
			var html = $(this).html();
			html = html.replace(/&lt;/g, "<");
			html = html.replace(/&gt;/g, ">");
			$(this).html(html);
		});

		$('.buttonClass').css('cursor', 'pointer');

		$('label[id*="_UNAVAILABLE"]').each( function() {
			$(this).before('<label style="color:red; font-weight:bold; " id="lblError">UNAVAILABLE FEATURE</label>');
		});
		 $(document).bind("dragstart", function() {
		     return false;
		 });

		$("#confirmDialogueOkForDecline").click(function()
		{
			$("#confirmationDialogueBoxForDecline").dialog("close");
			var dialogueJSON = buildDialogueJSON();
			symCancelCKO(JSON.stringify(dialogueJSON), globalIsUtilityOffer);
			globalIsUtilityOffer = "";
		});
					
		$("#confirmDialogueCancelForDecline").click(function()
		{
			$("#confirmationDialogueBoxForDecline").dialog("close");
			cancelAuthorizationDecline(globalDataFieldId);
			globalDataFieldId = "";
		});
		
		$(window).on('beforeunload', function()
		{
			if($('#isUtilityOffer').val() == 'true')
			{
				$.blockUI({ message: $('#loaderForUtilityOffer') });
			} 
		});

	});


	function cancelAuthorizationDecline(datafieldId)
	{
		var boolY = 'BOOL_Y_';
		var boolN = 'BOOL_N_';
		if(datafieldId.indexOf(boolN) >= 0){
			var newDatafieldId = datafieldId.substr(boolN.length);
			newDatafieldId = boolY+newDatafieldId;
			$('#'+newDatafieldId).attr('checked', 'checked');
			activate(newDatafieldId);
			return true;
		}
		if(datafieldId.indexOf(boolY) >= 0){
			var newDatafieldId = datafieldId.substr(boolY.length);
			newDatafieldId = boolN+newDatafieldId;
			$('#'+newDatafieldId).attr('checked', 'checked');
			activate(newDatafieldId);
			return true;
		}
	}


	var globalDataFieldId = "";
	var globalIsUtilityOffer = "";
	
	var jqConfirm = function(msg, datafieldId, isUtilityOffer) {

		globalDataFieldId = datafieldId;
		globalIsUtilityOffer = isUtilityOffer;
		
		$("#declineMessageId").html(msg);
		$("#confirmationDialogueBoxForDecline").dialog({
			resizable : false,
			title:"Confirmation",
			height : 170,
			width : 550,
			modal : true,
			zIndex : 99999,
			closeOnEscape: false
		});

		/*
		if (confirm(msg)){
			var dialogueJSON = buildDialogueJSON();
			
			symCancelCKO(JSON.stringify(dialogueJSON), isUtilityOffer);
		}
		else
		{
			var boolY = 'BOOL_Y_';
			var boolN = 'BOOL_N_';
			if(datafieldId.indexOf(boolN) >= 0){
				var newDatafieldId = datafieldId.substr(boolN.length);
				newDatafieldId = boolY+newDatafieldId;
				$('#'+newDatafieldId).attr('checked', 'checked');
				activate(newDatafieldId);
				return true;
			}
			if(datafieldId.indexOf(boolY) >= 0){
				var newDatafieldId = datafieldId.substr(boolY.length);
				newDatafieldId = boolN+newDatafieldId;
				$('#'+newDatafieldId).attr('checked', 'checked');
				activate(newDatafieldId);
				return true;
			}
		}
		*/
	};

	
	var showDataFieldMap = new Object();
	var hideDataFieldMap = new Object();
	var enableFieldsMap = new Object();
	var enableFieldArray = new Array();
	var displayList = new Array();
	var newlyCreatedSystemEventHiddenId;
	
	function activate(datafieldId) {
		newlyCreatedSystemEventHiddenId = "";
		var selectedOptin = $('#' + datafieldId).val();
		var arr;
		var dateFieldID_new = datafieldId;
		try{
			if(selectedOptin != null && selectedOptin.trim().length > 0){
				if(selectedOptin.trim().length > 1 && selectedOptin.indexOf('dataConstraint') > 0){
					var selectedValJSON = JSON.parse(selectedOptin);
					selectedOptin = selectedValJSON.quantity;
					if(selectedOptin == null || typeof selectedOptin == "undefined"){
						selectedOptin = selectedValJSON.description;
					}
				}
				else{
					selectedOptin = selectedOptin; 
				}
			}
		}
		catch(err){
			
		}
		
		var boolY = 'BOOL_Y_';
		var boolN = 'BOOL_N_';
		
		if(datafieldId.indexOf(boolY) > -1) {
			datafieldId = datafieldId.substr(boolY.length);
		} else if(datafieldId.indexOf(boolN) > -1) {
			datafieldId = datafieldId.substr(boolN.length);
		} 

		var optionMap = showDataFieldMap[datafieldId];
		
		if(optionMap != undefined){
		
			var showIdList = optionMap[selectedOptin];

			var hideDataList = new Array();

			if(showIdList != undefined) {
				for ( var i = 0; i < showIdList.length; i++) {
					if(showIdList[i].indexOf('PAGE_EVENT:') >= 0){
						var isUtilityOffer = false;
						if($('#isUtilityOffer').val() == 'true'){
							isUtilityOffer = true;
						}
						if(jqConfirm("Product will be cancelled.  Do you want to cancel the product?", dateFieldID_new, isUtilityOffer)){
							if(selectedOptin == 'Y'){
								selectedOptin = 'N';
							}
							else if(selectedOptin == 'N'){
								selectedOptin = 'Y';
							}
						}
					}else if(showIdList[i].indexOf('SYSTEM_EVENT:') >= 0){
						var key = datafieldId+"_system_event";
						newlyCreatedSystemEventHiddenId = datafieldId;
						//Clearing all system event hidden parameters before creating new system event hidden parameter.
						$('input[id*="_system_event"]').each(function(){
							$(this).remove()
						});
						$('#'+datafieldId+'_FS').after('<input type="hidden" id="'+key+'" name="'+key+'" value="'+selectedOptin+'" />');
					}
					else{
						var id = showIdList[i].replace("/", "_");
						
						$('[id=' + id + '_FS]').show();
						showChildrenValues(id);
					}
				}
			}
			
			var i = 0;
			
			<c:forEach var="extId" items="${allDataFieldList}">
			 //loop through showIdList and set flag found = true if $extId is found for any i in the iteration; initialize found = false
				var result = in_array(showIdList, '${extId}');
				
				if(!result){
					hideDataList.push('${extId}');
				}
			</c:forEach>
			var optionMap2 = hideDataFieldMap[datafieldId];
			var hideIdList = optionMap2[selectedOptin];
			if(hideIdList != undefined){
				for ( var i = 0; i < hideIdList.length; i++) {
					if(hideIdList[i].indexOf('PAGE_EVENT:') >= 0){
						
					}
					else if(hideIdList[i].indexOf('SYSTEM_EVENT:') >= 0)
					{
						if(newlyCreatedSystemEventHiddenId != datafieldId)
						{
							$('input[id*="_system_event"]').each(function(){
								$(this).remove()
							});
						}
					}
					else{
						var id = hideIdList[i].replace("/", "_");
						
						var is_present = in_array(hideDataList, hideIdList[i]);
						
						if(is_present){
							$('[id=' + id + '_FS]').hide();
						}
					}
				}
			}
			
		}
	}

	function showChildrenValues(id){
		
		$('#' + id + '_FS').find(':radio').each(function(){
			
			if($(this).is(':checked')){
				var radioId = $(this).attr('id');
				var prevSelectedValue = $(this).val(); 
				if(prevSelectedValue != null && prevSelectedValue.trim().length > 0 && prevSelectedValue.indexOf('::') > 0){
					arr = prevSelectedValue.split('::');
					prevSelectedValue = arr[1];
				}
				if(prevSelectedValue != null && prevSelectedValue.trim().length > 0 && prevSelectedValue.indexOf('$$') > 0){
					arr = prevSelectedValue.split('$$');
					prevSelectedValue = arr[0];
				}
				var boolY = 'BOOL_Y_';
				var boolN = 'BOOL_N_';
				
				if(radioId.indexOf(boolY) > -1) {
					radioId = radioId.substr(boolY.length);
				} else if(radioId.indexOf(boolN) > -1) {
					radioId = radioId.substr(boolN.length);
				}
				
				if(prevSelectedValue != null && prevSelectedValue.trim().length > 0){
					var optionMap = showDataFieldMap[radioId];
					var pageEventID = "";
					if(optionMap != undefined){
						if(prevSelectedValue.indexOf('featureExternalID') >= 0){
							var selectedValJSON = JSON.parse(prevSelectedValue);
							prevSelectedValue = selectedValJSON.quantity;
							if(prevSelectedValue == null || typeof prevSelectedValue == "undefined"){
								prevSelectedValue = selectedValJSON.description;
							}
						}

						var showIdList = optionMap[prevSelectedValue.toUpperCase()];
						
						if(showIdList != undefined) {
							for ( var i = 0; i < showIdList.length; i++) {
								if(showIdList[i].indexOf('PAGE_EVENT:') >= 0){
									//hidefieldSets(radioId);
								}
								else if(showIdList[i].indexOf('SYSTEM_EVENT:') >= 0){
									
								}
								else{
									//checkAndDisplay(radioId);
									var id = showIdList[i].replace("/", "_");
									$('[id=' + id + '_FS]').show();
									showChildrenValues(id);
								}
							}
							
						}
					}
				}
			}
		});

		$('#' + id + '_FS').find('select').each(function(){
			var agreedToCreditCheck = false;
			var radioId = $(this).attr('id');
			var prevSelectedValue = $(this).val(); 
			
			if(prevSelectedValue != null && prevSelectedValue.trim().length > 0 && prevSelectedValue.indexOf('::') > 0){
				arr = prevSelectedValue.split('::');
				prevSelectedValue = arr[1];
			}
			if(prevSelectedValue != null && prevSelectedValue.trim().length > 0 && prevSelectedValue.indexOf('$$') > 0){
				arr = prevSelectedValue.split('$$');
				prevSelectedValue = arr[0];
			}
			
			if(prevSelectedValue != null && prevSelectedValue.trim().length > 0){
				var optionMap = showDataFieldMap[radioId];
				if(optionMap != undefined){
					if(prevSelectedValue.indexOf('featureExternalID') >= 0){
						var selectedValJSON = JSON.parse(prevSelectedValue);
						prevSelectedValue = selectedValJSON.quantity;
						if(prevSelectedValue == null || typeof prevSelectedValue == "undefined"){
							prevSelectedValue = selectedValJSON.description;
						}
					}
					var showIdList = optionMap[prevSelectedValue];
					
					if(showIdList != undefined) {
						for ( var i = 0; i < showIdList.length; i++) {
							if(showIdList[i].indexOf('PAGE_EVENT:') >= 0){
								//hidefieldSets(radioId);
							}
							else if(showIdList[i].indexOf('SYSTEM_EVENT:') >= 0){
								
							}
							else{
								//checkAndDisplay(radioId);
								var id = showIdList[i].replace("/", "_");
								$('[id=' + id + '_FS]').show();
								showChildrenValues(id);
							}
						}
					}
				}
			}
		});
	}
	
	function createMap() {
		<c:if test="${not empty enableDialogueMap}">
			<c:forEach var="dataField" items="${enableDialogueMap}">
				var optionMap = new Object();
				<c:set var="optionMap" value="${dataField.value}"/>
				<c:forEach var="option" items="${optionMap}">
					<c:set var="extIdList" value="${option.value}"/>
					var extIdList = new Array();
					var i = 0;
					<c:forEach var="extId" items="${extIdList}">
						extIdList[i] = '${extId}';
						i++;
					</c:forEach>
					var key = '${option.key}';

					if(key.indexOf('OR') !== -1){
						var array = key.split('OR');
						for(var itr=0;itr<array.length;itr++){
							optionMap[array[itr].trim()] = extIdList;
						}
					}
					else{
						optionMap[key] = extIdList;
					}
				</c:forEach>
				showDataFieldMap['${dataField.key}'] = optionMap;
			</c:forEach>
		</c:if>
		
		<c:if test="${not empty disableDialogueMap}">
			<c:forEach var="dataField" items="${disableDialogueMap}">
				var optionMap2 = new Object();
				
				<c:set var="optionMap2" value="${dataField.value}"/>
				<c:forEach var="option" items="${optionMap2}">

				<c:set var="extIdList2" value="${option.value}"/>
					var extIdList2 = new Array();
					var j = 0;
					<c:forEach var="extId" items="${extIdList2}">
						extIdList2[j] = '${extId}';
						j++;
					</c:forEach>
					optionMap2['${option.key}'] = extIdList2;
				</c:forEach>
				hideDataFieldMap['${dataField.key}'] = optionMap2;
			</c:forEach>
		</c:if> 
	}

	function buildEnableValues(){
		<c:if test="${not empty replayAllEnabledValuesList}">
			<c:set var="enableDataField" value="${replayAllEnabledValuesList}"/>
				var j = 0;
				<c:forEach var="extId" items="${enableDataField}">
					enableFieldArray[j] = '${extId}';
					j++;
				</c:forEach>
		</c:if> 
	}
	
	function reformatMonthYearSize(){
		$("#MonthDD").width('20px');
		$("#MonthDD").attr('maxlength','2');
		$("#YearDD").width('50px');
		$("#YearDD").attr('maxlength','4');
		$(':text').each(function(){
			var id = $(this).attr('id');
			var hiddenJsonElementVal = $('input[id = '+ id +'_JSON]').val();
			if(hiddenJsonElementVal != undefined){
				var hiddenElementJSONVal = JSON.parse(hiddenJsonElementVal);
				var validationType = hiddenElementJSONVal.validation;
				var valueTarget = hiddenElementJSONVal.valueTarget;
				if(valueTarget != null && valueTarget == 'consumer.dateOfBirth'){
					$(this).attr('maxlength','10');
				}
				if(validationType != null && validationType == 'SSN'){
					$(this).attr('maxlength','11');
				}
				else if(validationType != null && (validationType == 'Phone' || validationType == 'phone')){
					$(this).attr('maxlength','12');
				}
				else if(validationType != null && validationType == 'credit card number'){
					$(this).attr('maxlength','16');
				}
				else if(validationType != null && validationType == 'Routing Number'){
					$(this).attr('maxlength','9');
				}
				else if(validationType != null && validationType.match(/^([0-9]+):(Numeric|Alphanumeric)$/)){
					var maxLength = validationType.split(':'); 
					$(this).attr('maxlength', maxLength[0]);
				}
			}
		});
	}
	
	function displaypageInitialValues(){
		var jsonObj = [];
		$(':input[type=text]').each( function() { 
			if($(this).is(':visible') && $(this).val() != ""){
				jsonObj.push({id: $(this).attr("id"), optionValue: $(this).val()});
			}
		});

		$(':input[type=radio]').each( function() {
			if($(this).is(':visible')){
				var id = $(this).attr("id");
				var radiotest = $(this).attr("name");
				if($('#'+id+'').is(':checked')){
					jsonObj.push({id: $(this).attr("id"), optionValue: $(this).val()});
				}
			}
		});

		// Fetch Select fields are visible and unselected name for default values of "Please Select"
		$('select').each( function() {
			if($(this).is(':visible') && $(this).val() != ""){
				jsonObj.push({id: $(this).attr("id"), optionValue: $(this).val()});
			}
		});

		// Fetch Checkboxes are visible and unchecked names
		$(':input[type=checkbox]').each( function() {
			if($(this).is(':visible')){
				if($('input:checkbox[name='+radiotest+']').is(":checked") == true){
					jsonObj.push({id: $(this).attr("id"), optionValue: $(this).val()});
				}
			}
		});
		for(var i = 0; i < jsonObj.length; i++){
			buildEnableMap(jsonObj[i].id, jsonObj[i].optionValue);
			
			for(var li = 0; li < displayList.length; li++){
				if(displayList[li].indexOf('PAGE_EVENT:') >= 0){
					//hidefieldSets(displayList[li]);
				}
				else if(displayList[li] != undefined && displayList[li].indexOf('SYSTEM_EVENT:') >= 0){
					
				}
				else{
					//checkAndDisplay(displayList[li]);
					$('[id=' + displayList[li] + '_FS]').show();
					showChildrenValues(displayList[li]);
				}
			}
		}
	}

	
	function buildEnableMap(selectedId, selectedValue){
		
		<c:if test="${not empty enableDialogueMap}">
			<c:forEach var="dataField" items="${enableDialogueMap}">
				var optionMap = new Object();
				<c:set var="optionMap" value="${dataField.value}"/>
				<c:set var="keys" value="${dataField.key}"/>
				<c:forEach var="key" items="${keys}">
					var id = '${key}';
					var boolY = 'BOOL_Y_';
					var boolN = 'BOOL_N_';
					if(selectedId != undefined){
					if(selectedId.indexOf(boolY) > -1) {
						selectedId = selectedId.substr(boolY.length);
					} else if(selectedId.indexOf(boolN) > -1) {
						selectedId = selectedId.substr(boolN.length);
					}

					if(selectedId == id){
						<c:forEach var="value" items="${optionMap}">
						<c:set var="optionMapKeys" value="${value.key}"/>
						<c:set var="optionMapValues" value="${value.value}"/>
						var selValue = '${optionMapKeys}';
						
						if(selectedValue.indexOf('::') > 0){
							var selValArr = selectedValue.split('::');
							selectedValue = selValArr[1];
						}
						if(selectedValue.indexOf('$$') > 0){
							var selValueSplit = selectedValue.split('$$');
							selectedValue = selValueSplit[0];
						}
						try{
							var selFeatJSON = JSON.parse(selectedValue);
							if(!(typeof selFeatJSON.quantity == "undefined")){
								selectedValue = selFeatJSON.quantity;
							}
						}catch(err){

						}
						if(selectedValue == selValue){
						var j = 0;
						<c:forEach var="extId" items="${optionMapValues}">
							displayList[j] = '${extId}';
							j++;
						</c:forEach>
						}
						</c:forEach>
					}
					}
				</c:forEach>				
			</c:forEach>
		</c:if>
	}

	function in_array(array, id) {
		if(array != undefined){
	    	for(var i=0;i<array.length;i++) {
	        	if(array[i] == id) {
	            	return true;
	        	}
	    	}
		}
	    return false;
	}
	(function (window) {

		  // Stores past URLs that failed to load. Used for a quick lookup
		  // and because `onerror` is not triggered in some browsers
		  // if the response is cached.
		  var errors = {};

		  // Check the existence of an image file at `url` by creating a
		  // temporary Image element. The `success` callback is called
		  // if the image loads correctly or the image is already complete.
		  // The `failure` callback is called if the image fails to load
		  // or has failed to load in the past.
		  window.checkImage = function (url, success, failure) {
		    var img = new Image(),    // the 
		        loaded = false,
		        errored = false;

		    // Run only once, when `loaded` is false. If `success` is a
		    // function, it is called with `img` as the context.
		    img.onload = function () {
		      if (loaded) {
		        return;
		      }

		      loaded = true;

		      if (success && success.call) {
		        success.call(img);
		      }
		    };

		    // Run only once, when `errored` is false. If `failure` is a
		    // function, it is called with `img` as the context.
		    img.onerror = function () {
		      if (errored) {
		        return;
		      }

		      errors[url] = errored = true;

		      if (failure && failure.call) {
		        failure.call(img);
		      }
		    };

		    // If `url` is in the `errors` object, trigger the `onerror`
		    // callback.
		    if (errors[url]) {
		      img.onerror.call(img);
		      return;
		    }
		    
		    // Set the img src to trigger loading
		    img.src = url;

		    // If the image is already complete (i.e. cached), trigger the
		    // `onload` callback.
		    if (img.complete) {
		      img.onload.call(img);
		    }
		  };

		})(this);
	function success() {
	  $("#provider").attr('src','<%=request.getContextPath()%>/image/${parentExternalID}.jpg');
	}

	function failure() {
		checkImage("<%=request.getContextPath()%>/image/${providerExternalID}.jpg", success1, failure1);
	}
	function success1() {
		  $("#provider").attr('src','<%=request.getContextPath()%>/image/${providerExternalID}.jpg');
		}

		function failure1() {
		}
	function checkProdImage(){
		checkImage("<%=request.getContextPath()%>/image/${parentExternalID}.jpg", success, failure);
	}

	function onLoadFunctions(){
		replay(); 
		checkProdImage(); 
		getPreviouslySelectedFeatures();
		symFeedback();
	}

</script>
</head>

<body onload="onLoadFunctions();">

<font color="red">
 <c:choose>
	<c:when test="${not empty errorLog}">
		<div style="display: block">
	</c:when>
	<c:otherwise>
		<div style="display: none">
	</c:otherwise>
</c:choose> <c:forEach var="error" items="${errorLog}">
	<span>${error}</span>
</c:forEach>
</div>
</font>

<input id="iData" name="iData" value='${iData}' type="hidden" />

<div>
<form action="<%=request.getContextPath()%>/static/updateDialogue"  method="post" id="frmMain" name="frmMain"	onsubmit="symFeedbackSubmit()" autocomplete=off>
	
	<input type="hidden" id="formdata" name="formdata" />
	<input type="hidden" id="selectedValues" name="selectedValues" /> 
	<input type="hidden" id="extIDSelectedValues" name="extIDSelectedValues" /> 
	<input type="hidden" id="previouslyGivenDataId" name="previouslyGivenDataId" value='${previouslyEnteredData}'/> 
	
	<input type="hidden" id="from" name="from" value="oqDemoContent"/>
	<input type="hidden" id="addressLineAndLineInfo" name="addressLineAndLineInfo"/>
	<input type="hidden" id="doneLoading" name="doneLoading" class="loadingDone"/>
	<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>" />
	
	<input type="hidden" id="lineItemMonthlyPrice" name="lineItemMonthlyPrice" value="${productMonthlyPrice}" />
	<input type="hidden" id="productMonthlyPrice" name="productMonthlyPrice" value="${productMonthlyPrice}" />
  	<input type="hidden" id="lineItemInstallationPrice" name="lineItemInstallationPrice" value="${productInstallationPrice}" />
 	<input type="hidden" id="productInstallationPrice" name="productInstallationPrice" value="${productInstallationPrice}" />
	<input type="hidden" id="selectedFeatureJSONValue" name="selectedFeatureJSONValue"/>
	<input type="hidden" id="enforceReciever" name="enforceReciever" value="${receiverValidation}"/>
	
	<input type="hidden" id="isUtilityOffer" name="isUtilityOffer" value="${isUtilityOffer}" />
	
	<input type="hidden" id="validmaxdate" value="${hiddenYear}" placeholder="mm/dd/yyyy" title="mm/dd/yyyy" />
	
	<div class="pc_pdetails">
		<div class="pc_pdetails_logo">
    		<c:if test="${not empty providerExternalID && !isUtilityOffer}">
    			<img id="provider" src="" 
    		 style="float: left;max-height:60px;max-width:107px;"/> 
	    	</c:if>
  		</div>
  		<div class="pc_pdetails_info">
		<div class="label">Product Name:</div>
			<c:choose>
			<c:when  test="${productName != null}">
				<div class="value"><c:out escapeXml="false" value="${productName}" />
				</div><br/>
			</c:when>
			<c:otherwise>
				<div class="value"><c:out escapeXml="false" value="${productInfo.product.name}" />
				</div><br/>
			</c:otherwise>
			</c:choose>
			<c:if test="${isUtilityOffer != true}">
				<div class="label">Monthly Total:</div>
				
				<c:choose>
					<c:when test="${not empty baseRecPrice}">
						<div class="value"  style="width:48px;">
							<span id="monthlyCost">
								<fmt:formatNumber type="currency" value="${baseRecPrice}" />
							</span>
						</div>
						<span>&nbsp;&nbsp;
							<jsp:include page="MonthlyTotalWidget.jsp"></jsp:include>
						</span>
						<input type="hidden" id="monthlyCostAmtFld" name="monthlyCostAmtFld" value="${baseRecPrice}" />
						<br/>				
					</c:when>
					<c:otherwise>
						<div class="value" id="dialoguesRecurringCost"  style="width:48px;">
							<fmt:formatNumber type="currency" value="${productInfo.product.priceInfo.baseRecurringPrice}" />
						</div>
						<input type="hidden" id="monthlyCostAmtFld" name="monthlyCostAmtFld" value="${productInfo.product.priceInfo.baseRecurringPrice}" />
						<br/>
					</c:otherwise>
				</c:choose>
				<div class="label">Installation Total:</div>
				<c:choose>
					<c:when  test="${baseNonRecPrice != null}">
						<div class="value"  style="width:48px;">
							<span id="oneTimePrice">
								<fmt:formatNumber type="currency" value="${baseNonRecPrice}" />
							</span>
							<input type="hidden" id="oneTimePriceFld" name="oneTimePriceFld" value="${baseNonRecPrice}" />
						</div>
						<span>&nbsp;&nbsp;
							<jsp:include page="InstallTotalWidget.jsp"></jsp:include>
						</span>
						<br/>				
					</c:when>
					<c:otherwise>
						<div class="value"  style="width:48px;">
							<span id="oneTimePrice">
								<fmt:formatNumber type="currency" value="${productInfo.product.priceInfo.baseNonRecurringPrice}" />
							</span>
							<input type="hidden" id="oneTimePriceFld" name="oneTimePriceFld" value="${productInfo.product.priceInfo.baseNonRecurringPrice}" />
						</div>
						<br/>
					</c:otherwise>
				</c:choose>
				        <span class="label" style="margin-top: -38px; text-align: right; margin-left: 682px;">Account Holder:</span>
                        <span class="value" style="text-align: right; margin-left: 703px; margin-top: -20px; width: 148px;">${customerName}</span>
			</c:if>
	</div>
	</div>
	<div class="pc_pdetails_cont">
		<!-- Left Block -->
		<div class="pc_steps">
			<c:choose>
				<c:when  test="${showBackButton != false && !ishybris && !frontierAsIs}">
					<div id="pc_steps_one" class="pc_steps_item_complete">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>1</span></div></div>
						<div class="pc_steps_item_sttext">Product Customization</div>
					</div>
					<div id="pc_steps_two" class="pc_steps_item_progress pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>2</span></div></div>
						<div class="pc_steps_item_sttext">Customer Qualification</div>
					</div>
					<div id="pc_steps_three" class="pc_steps_item_pending pc_steps_item_margin">
      					<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>3</span></div></div>
     				    <div class="pc_steps_item_sttext">Authorization</div>
    				</div>
				</c:when>
				<c:when  test="${showBackButton != false && (ishybris || frontierAsIs)}">
					<div id="pc_steps_one" class="pc_steps_item_complete">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>1</span></div></div>
						<div class="pc_steps_item_sttext">Product Customization</div>
					</div>
					<div id="pc_steps_two" class="pc_steps_item_progress pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>2</span></div></div>
						<div class="pc_steps_item_sttext">Customer Qualification</div>
					</div>				
				</c:when>
				<c:otherwise>
					<div id="pc_steps_two" class="pc_steps_item_progress pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>1</span></div></div>
						<div class="pc_steps_item_sttext">Customer Qualification</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="pc_frameblk"> 
			<div class="pc_frameblk_title">
			<span id='unselDescription' style="display:none">
					<img id="astrik" src='<%=request.getContextPath()%>/image/red_asterisk.png' style="height:9px !important; width:9px !important;"> </img>
					<label style="color: red; font-size: 12px;" id="astrikLbl"> Required Fields</label>
			</span>
			<span id='equipUnsel' style="display:none">
					<img id="astrik" src='<%=request.getContextPath()%>/image/red_asterisk.png' style="height:9px !important; width:9px !important;"> </img>
					<label style="color: red; font-size: 12px;" id="astrikLbl"> Select at least (1) # Receivers</label>
			</span>
				Please select from the options below:
			</div>
			<div class="pc_frameblk_cont">
				<c:out escapeXml="false" value="${dataField}" />
			</div>
			
			<div id="pageRedirectingButtonsDiv" class="pc_frameblk_right_btns">
				
				<c:if test="${showBackButton != false}">
					<input id="goToProductCustomization" type="button" class="buttonClass" value="< Back" onclick="goBackPage()"/>
				</c:if>
				
				<input id="moveForward" class="buttonClass" type="submit" value="Forward >" onclick="return validateDialogue();"/>
			</div>
			
		</div>
		
		<div id="loaderForUtilityOffer" style="display:none;"> 
			<img src="<%=request.getContextPath()%>/image/loading.gif" border="0"/>
    		<h2>Loading</h2> 
		</div>
		<div id="confirmationDialogueBoxForDecline" style="display:none;">
			<center>
				<p id="declineMessageId"></p>
				<input type="button" id="confirmDialogueOkForDecline" name="Ok" value="OK"/>
				<input type="button" size="2px" id="confirmDialogueCancelForDecline" name="Cancel" value="CANCEL" />
			</center>
		</div>
	</div>
</form>
</div>
</body>
</html>