<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title><fmt:message key="qualification.header"/></title>


<script src="<c:url value='/script/html_save.js'/>"></script>
<script type="text/javascript">

function backToUtilityOffer(){
	var href = window.location.href;
	var offer = $('input#offer').val();
	var utilityOffer = 	$('input#utilityOffer').val();
	var targetURL;
	if(offer == 'true' && utilityOffer == 'false'){
		targetURL = href.slice(0, href.lastIndexOf("/")+1) + "offer";
	}else if(offer == 'false' && utilityOffer == 'false'){
		targetURL = href.slice(0, href.lastIndexOf("/")+1) + "confirmation";
	}
	window.location = targetURL ;
}

function goToDiscovery(){
	
	  var toSubmit = validateDiscoveryForm();
	 // alert(toSubmit);
	  if (toSubmit){
		//To save the html on page submit
			savePageHtml(false, "");
		  $("form#qualification").submit();
		  }
}
function validateDiscoveryForm()
{
	$( "#required_container" ).css("display","none");
	$("span[id^='red_astrick_']").css("display","none")
	var canMoveFarward = false;
	var slectedValue = $("input[name='DF_Scripted_Qual_CL_11']:checked").val();
	if(slectedValue != undefined && slectedValue != null && slectedValue.trim().length > 0 ){
		if(slectedValue == "New"){
			$("#newService").val('Yes');
			$("#existingService").val('');
			$('#typeOfService').val("New");
			$("#yesTransferService").val('');
			$("#noTransferService").val('');
			canMoveFarward = true;
		}
		else if(slectedValue == "Transfer"){
			$('#typeOfService').val("Transfer");
			$("#newService").val('');
			$("#existingService").val('Yes');
			canMoveFarward = true; 
			/* var nonrr_13 = $("input[name='DF_Scripted_Qual_CL_14']:checked").val();
			if( nonrr_13 != undefined && nonrr_13 != null && nonrr_13.trim().length > 0 ){
				if(nonrr_13 == "Y"){
					$("#yesTransferService").val(nonrr_13);
					$("#noTransferService").val('');
					canMoveFarward = true;

				}
				else if(nonrr_13 == "N"){
					$("#yesTransferService").val('');
					$("#noTransferService").val(nonrr_13);
					canMoveFarward = true;
				}
			}
			else{
				$( "#red_astrick_DF_Scripted_Qual_CL_14" ).css("display","inline");
				$( "#required_container" ).css("display","inline");
				//displayNext("DF_Scripted_Qual_CL_14");
			} */
		}
	}
	else{
		$( "#red_astrick_DF_Scripted_Qual_CL_11" ).css("display","inline");
		$( "#required_container" ).css("display","inline");
		//displayNext("DF_Scripted_Qual_CL_11");
	}
	
	return canMoveFarward;
}

function displayNext(externalID){
	$( "#"+externalID ).after($('<span class="missingFields">*</span>'));

}

var showDataFieldMap = new Object();
var hideDataFieldMap = new Object();
var enableFieldsMap = new Object();
var enableFieldArray = new Array();
var displayList = new Array();

function activate(inputfieldId,datafieldId) {
	if( inputfieldId == 'BOOL_N_DF_Scripted_Qual_CL_14'){
		$('#existingProviderDiv').hide();
	}
	if($("#BOOL_Y_DF_Scripted_Qual_CL_14").is(":checked")){
		$('#existingProviderDiv').show();
	}
	
	createMap();
	
	var selectedOptin = $('#' + inputfieldId).val();
	if(selectedOptin != "" && selectedOptin.trim().length > 0){
		if(selectedOptin.trim()=="Transfer"){
			$('#existingProviderDiv').show();
			$("#centuaryLinkDialogue").hide()
			$('.existingCustomerDefaultClass:checkbox').each(function(){
				if(this.name=="CenturyLink" && $(this).is(":checked")){
					$("#centuaryLinkDialogue").show()
				}else if(this.name=="CenturyLink" && !$(this).is(":checked")){
					$("#centuaryLinkDialogue").hide()
				}
			});
		}else{
			$('.existingCustomerDefaultClass:checkbox').each(function(){
				$(this).prop("checked",false);
			});
			$("#centuaryLinkDialogue").hide();
			$('#existingProviderDiv').hide();
		}
		var arr;
	
		var boolY = 'BOOL_Y_';
		var boolN = 'BOOL_N_';
	
		var optionMap = showDataFieldMap[datafieldId];
		var showIdList = optionMap[selectedOptin];
		var hideDataList = new Array();
		if(showIdList != undefined) {
			for ( var i = 0; i < showIdList.length; i++) {
				var id = showIdList[i].replace("/", "_");
				$('[id=' + id + '_FS]').show();
			}
		}
		var i = 0;
		<c:forEach var="extId" items="${allDataFieldList}">
			var result = in_array(showIdList, '${extId}');
			if(result == true){
			
			}
			else{
				hideDataList.push('${extId}');
			}
		</c:forEach>
		var optionMap2 = hideDataFieldMap[datafieldId];
		var hideIdList = optionMap2[selectedOptin];
		if(hideIdList != undefined){
			for ( var i = 0; i < hideIdList.length; i++) {
				var id = hideIdList[i].replace("/", "_");
				//if id is found in the hideDataList, then only hide the data element; if not, don't hide the element
				var is_present = in_array(hideDataList, hideIdList[i]);
				if(is_present){
					$('[id=' + id + '_FS]').hide();
				}
			}
		}
	}
	else{
		var optionMap = hideDataFieldMap[datafieldId];
		for(var key in optionMap)
		{
			var showIdList = optionMap[key];
			if(showIdList != undefined) {
				for ( var i = 0; i < showIdList.length; i++) {
					var id = showIdList[i].replace("/", "_");
					$('[id=' + id+']').val('');
					$('[id=' + id + '_FS]').hide();
				}
			}
		}
	}
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


$(document).ready(function(){
	var showPopUp ='${showPopUp}';
 	 if(showPopUp != undefined && showPopUp != '' && showPopUp == "true"){
 		$("#show-dialog").dialog( {
			resizable : false,
			title : "Good News!",
			height : 197,
			width : 570,
			modal : true,
			zIndex : 99999
		});
 	 }
 	$("#BOOL_Y_DF_Scripted_Qual_CL_14").click(function() {
 		$('#existingProviderDiv').show();
 	});
 	
 	
 	$("#BOOL_New_DF_Scripted_Qual_CL_11").click(function() {
 		$('#BOOL_Y_DF_Scripted_Qual_CL_14').attr("checked", false);
 	 	$('#BOOL_N_DF_Scripted_Qual_CL_14').attr("checked", false);
 		$('#existingProviderDiv').hide();
 	});
 	
	$('.black').html(function(index, text){  
	    return text.replace(". ", ".&nbsp;"); 
	         });

	var typeOfService = '${typeOfService}';
	var typeOfRadioButton = '${typeOfcheckBox}'
	$("input[name='DF_Scripted_Qual_CL_11']").each(function()
	{
		if (typeOfService == 'newService' && typeOfRadioButton.length == 0 ) 
		{
			if($(this).val()=="New")
			{
				$(this).attr("checked",true);
				activate(this.id,this.name)
				return;
			}
		}
		else if (typeOfService == 'existingService' || typeOfRadioButton.length > 0 )
		{
			if($(this).val()=="Transfer")
			{
				$(this).attr("checked",true);
				activate(this.id,this.name)

				$("input[name='DF_Scripted_Qual_CL_14']").each(function()
				{
					if ($(this).val()==typeOfRadioButton) 
					{
						$(this).attr("checked",true);
						activate(this.id,this.name);
						return;
					} 
				});
				return;
			}
		}
	});
    
	/*var typeOfService = $('#typeOfService').val();
	 if (typeOfService == 'New') {
		$("#DF_Scripted_Qual_NonRR_11").val("New");
		$("#DF_Scripted_Qual_NonRR_11").trigger("change");
	} else if (typeOfService == 'Transfer') {
		$("#DF_Scripted_Qual_NonRR_11").val("Transfer");
		$("#DF_Scripted_Qual_NonRR_11").trigger("change");

		if($("#yesTransferService").val() == "Y" && $("#noTransferService").val() == ""){
			$("#DF_Scripted_Qual_NonRR_13").val("Yes");
			$("#DF_Scripted_Qual_NonRR_13").trigger("change");
			
		}
		else if($("#noTransferService").val() == "N" && $("#yesTransferService").val() == ""){
			$("#DF_Scripted_Qual_NonRR_13").val("No");
			$("#DF_Scripted_Qual_NonRR_13").trigger("change");
		}
	}*/
//	var sellCount = $("#rts_widget_content tbody").find("[title='SUCCESS']").length;
	$("#sellProvidersCount").val($("#rts_widget_content tbody tr").find("td:contains('Available')").length);
	console.log("sellCount::::::::::::"+$("#rts_widget_content tbody tr").find("td:contains('Available')").length);
	console.log("sellProvidersCount::::::::"+$("#sellProvidersCount").val());
	 jQuery(window).load(function () {
	      //To save the html on page load
	      savePageHtml(true, "");
	});
	
	  $('.existingCustomerDefaultClass:checkbox').click(function(){
		$('.existingCustomerDefaultClass:checkbox').each(function(){
			if(this.name=="CenturyLink" && $(this).is(":checked")){
				$("#centuaryLinkDialogue").show()
			}else if(this.name=="CenturyLink" && !$(this).is(":checked")){
				$("#centuaryLinkDialogue").hide()
			}
			
		});
	}); 
});


function goToPowerPitchDisplay(){

	 //alert("On Submit"+$("#submitSt").val());
	if($("#submitSt").val()=="no"){
		return false;
	}
	var toSubmit = validateDiscoveryForm();
	$('.missingFields').remove();
	var isValidSubmit = false;
	var selectedExistingProviders = {};
	var selectedExistingProvidersAfterAuthentication = {};
	var isTransfer = false;
	$('.existingCustomerDefaultClass:checkbox').each(function(){
		if($(this).is(":checked")){
		 isTransfer = true;
		}
	});
	
	$('.existingCustomerDefaultClass:checkbox:checked').each(function(){
		isValidSubmit = true;
		if(this.name!="Others" && !this.disabled )
		{
			selectedExistingProviders[this.name] = this.id;
		}
		else if(this.name=="Others")
		{
			$('#isOtherChecked').val("Yes");
		}
		if(this.name!="Others")
		{
			selectedExistingProvidersAfterAuthentication[this.name] = this.id;
		}
	});
	
	var slectedValue = $("input[name='DF_Scripted_Qual_CL_11']:checked").val();
	var newService = false;
	if(slectedValue != undefined && slectedValue != null && slectedValue.trim().length > 0 ){
		if(slectedValue == "New"){
			$('#selectedExistingProviders').val("");
			isValidSubmit = true;
			newService = true;
		}
	}
	var nonrr_13 = $("input[name='DF_Scripted_Qual_CL_14']:checked").val();
	if(slectedValue != undefined && slectedValue == "Transfer" && isTransfer){
		isValidSubmit = true;
	}
	if( nonrr_13 != undefined  && nonrr_13.trim().length > 0 && nonrr_13 == "N"){
		$('#selectedExistingProviders').val("");
		isValidSubmit = true;
		newService = true;
	}
	if( isValidSubmit && toSubmit)
	{
		if(!newService) {
		if(!jQuery.isEmptyObject(selectedExistingProviders))
		{
			$('#selectedExistingProviders').val(JSON.stringify(selectedExistingProviders));
		}
		else
		{
			$('#selectedExistingProviders').val("");
		}
		if(!jQuery.isEmptyObject(selectedExistingProvidersAfterAuthentication)) {
				$('#selectedExistingProvidersAfterAuthentication').val(JSON.stringify(selectedExistingProvidersAfterAuthentication));
			} else {
				 $('#selectedExistingProvidersAfterAuthentication').val("");
				}
		}
		//To save the html on page submit
		savePageHtml(false, "");
		
		$("form#qualification").submit();
	}
	else
	{
		$('#existingProvidersSpan').after($('<span class="missingFields">*</span>'));
		$( "#required_container" ).css("display","block");
	}
}
function hideDialog()
{
	$("#show-dialog").dialog('close');
}
</script>

<style type="text/css">
.displayNone{
	display:none;
}
#errorMessage {
margin-left: 180px; 
margin-top: 80px;
}
#errorMessage span{
	color: red 
}
.widget_container {
    height: auto;
}


.coaching 
{
        color: green;
}
.Suggested
{
 color: grey;

}
.pagecontainer {
    background-color: #E5E0D9;
    border: 1px solid #616264;
    display: table-cell;
    height: 200px;
    text-align: center;
    vertical-align: middle;
    width: 997px;
}
#dialogue_content {
    border: 2px solid #616264;
    line-height: 17px;
    min-height: 50px;
    min-width: 500px;
    padding: 7px 10px 0;
}
.bottombuttons {
    left: 0;
    position: absolute;
    top: 569px;
    width: 100%;
    z-index: 9967;
}
#action_wrapper {
    left: 0;
    margin: 3px;
    position: relative;
    top: 3px;
    z-index: 3000;
}

.qualificationformcontent {
    background-color: #ffffff;
    border: 1px solid #616264;
    border-radius: 10px;
    font-size: 15px;
    font-face:verdana;
    margin-left: 160px;
    padding: 0 20px 32px;
    width: 612px;
    height: 277px;
}

</style>

</head>
					<section id="contentfullcont">
						<header id="content_header">
							<div class="row">
								<span class="cell">
									<svg version="1.1" id="Layer_5" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
										 width="29px" height="29px" viewBox="0 0 29 29" enable-background="new 0 0 29 29" xml:space="preserve" class="headphonesSVG">
										<g>
											<path fill="#96C43E" d="M29,25.298c0,2.066-1.677,3.744-3.744,3.744H4.411c-2.067,0-3.745-1.678-3.745-3.744V4.328
												c0-2.068,1.678-3.745,3.745-3.745h20.845C27.323,0.583,29,2.26,29,4.328V25.298z"/>
											<path fill="#FFFFFF" d="M23.861,18.677c0.116,0.435,0.177,0.891,0.177,1.362c0,2.921-2.367,5.289-5.288,5.289
												c-0.291,0-0.576-0.023-0.854-0.068l-0.004-0.002c-0.285,0.446-0.843,0.75-1.482,0.75c-0.932,0-1.686-0.64-1.686-1.428
												s0.754-1.428,1.686-1.428c0.563,0,1.061,0.234,1.367,0.594l0,0c0.208,0.029,0.422,0.044,0.641,0.044
												c2.19,0,3.967-1.542,3.967-3.445c0-0.307-0.046-0.604-0.132-0.887l-0.052,0.015c-0.244,0.015-0.527,0.008-0.855-0.031v0.608
												c0,0-0.059,0.398-0.808,0.375s-0.784-0.234-0.796-0.445c-0.012-0.21,0-5.5,0-5.5s0.047-0.409,0.819-0.397
												c0.655,0.012,0.737,0.246,0.761,0.375c0.023,0.128,0.023,0.784,0.023,0.784l0.374-0.059l0.088-0.058
												c0.189-0.664,0.265-1.24,0.265-1.966c0-4.188-3.395-7.583-7.582-7.583c-4.188,0-7.583,3.395-7.583,7.583
												c0,0.722,0.128,1.363,0.316,2.024l0.374,0.059c0,0,0-0.655,0.023-0.784s0.105-0.363,0.761-0.375
												c0.772-0.012,0.819,0.398,0.819,0.398s0.012,5.289,0,5.5s-0.047,0.422-0.796,0.445s-0.808-0.375-0.808-0.375v-0.608
												c-1.193,0.141-1.802-0.141-2.07-0.339c-0.27-0.199-1.065-0.913-0.738-2.293c0.328-1.381,1.451-1.58,1.674-1.58
												s0.164-0.07,0.164-0.07c-0.416-1.001-0.609-2.02-0.609-3.171c0-4.679,3.793-8.472,8.473-8.472c4.678,0,8.472,3.793,8.472,8.472
												c0,1.107-0.212,2.165-0.599,3.134l-0.046,0.037c0,0-0.059,0.07,0.163,0.07c0.223,0,1.346,0.199,1.674,1.58
												c0.205,0.863-0.029,1.465-0.299,1.845L23.861,18.677z"/>
										</g>
									</svg>
								</span>
								<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
								<span class="cell pageTitle"><fmt:message key="qualification.header"/></span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="qualification" name="qualification " action="${flowExecutionUrl}" method="post">
							<input type="hidden" id="serviceType" name="serviceType"/>
							<input type="hidden" id="serviceTransferType" name="serviceTransferType"/>
							<input type="hidden" value="${utilityOffer}" id="utilityOffer"/>
							<input type="hidden" value="${offer}" id="offer"/>
							<input type="hidden" value="${order.customerInformation.customer.externalId}" id="customerId"/>
							<input type="hidden" value="${order.externalId}" id="orderId"/>
							<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="pageTitle" name="pageTitle" value="Qualification">
							<input type="hidden" id="_eventId" name="_eventId" value="qualificationEvent">
							
							<input type="hidden" id="newService" name="newService" >
							<input type="hidden" id="existingService" name="existingService">
							<input type="hidden" id="yesTransferService" name="yesTransferService">
							<input type="hidden" id="noTransferService" name="noTransferService">
							<input type="hidden" id="typeOfService" name="typeOfService">
							
							<input type="hidden" id="prev_new_transfer" name="prev_new_transfer">
							<input type="hidden" id="prev_yes_no" name="prev_yes_no">
							<input type="hidden" id="selectedExistingProviders" name="selectedExistingProviders" value="">
							<input type="hidden" id="selectedExistingProvidersAfterAuthentication" name="selectedExistingProvidersAfterAuthentication" value="">
							<input type="hidden" id="sellProvidersCount" name="sellProvidersCount" value="${sellProvidersCount}">
							<div class="contentwrapper">
								<section id="dialogue_wrapper">						   
									<section id="dialogue_content">
										${dialogue_display}
									</section>
									<div id="dialogue_content_balloon"></div>						
								</section>
								<div id="action_wrapper">
									<div class="widget_container">
										<div class="pagecontainer" align="center">
											<!-- Form Content -->
											<section class="qualificationformcontent" style="margin-bottom:5px; margin-top:5px;">
												${selection_display}
												<div id="existingProviderDiv" style="display:none">
														<span class="existing">
															Who is your current service provider?
														</span>
														<span class="existingList" align="center" id="existingProvidersSpan">
															<c:if test="${not empty existingCustomerProvidersMap }">
																<c:set var="isEmptySelectedExistingCustomerStatusMap" value="Yes"/>
																<c:if test="${not empty selectedExistingCustomerStatusMap }">
																	<c:set var="isEmptySelectedExistingCustomerStatusMap" value="No"></c:set>
																</c:if>
																<c:forEach var="providersMap" items="${existingCustomerProvidersMap}">
																	<c:set var="isDisabled" value="" />
																	<c:if test="${isEmptySelectedExistingCustomerStatusMap eq 'No'}">
																		<c:forEach var="selectedProvidersStatusMap" items="${selectedExistingCustomerStatusMap}">
																			<c:if test="${selectedProvidersStatusMap.key eq providersMap.value}">
																				<c:set var="isDisabled" value="${selectedProvidersStatusMap.value}" />
																			</c:if>
																		</c:forEach>	
																	</c:if>
																	<c:choose>
																		<c:when test="${isDisabled eq 'disable'}">
																			<input type="checkbox" id="${providersMap.value}" name="${providersMap.key}" disabled="disabled" checked="checked" class="existingCustomerDefaultClass"/>&nbsp;<c:out value="${providersMap.key}" escapeXml="false" />
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${isDisabled eq 'enable'}">
																					<input type="checkbox" id="${providersMap.value}" name="${providersMap.key}" checked="checked" class="existingCustomerDefaultClass"/>&nbsp;<c:out value="${providersMap.key}" escapeXml="false" />
																				</c:when>
																				<c:otherwise>
																					<input type="checkbox" id="${providersMap.value}" name="${providersMap.key}" class="existingCustomerDefaultClass"/>&nbsp;<c:out value="${providersMap.key}" escapeXml="false" />
																				</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</c:if>
															<c:choose>
																<c:when test="${not empty isOtherChecked && isOtherChecked eq 'Yes'}">
																	<input type="checkbox" id="otherProviderExistingCustomer" name="Others" checked="checked" class="existingCustomerDefaultClass"/>&nbsp;Others
																</c:when>
																<c:otherwise>
																	<input type="checkbox" id="otherProviderExistingCustomer" name="Others" class="existingCustomerDefaultClass"/>&nbsp;Others
																</c:otherwise>
															</c:choose>
															<div style="display:none" id="centuaryLinkDialogue">
       															 <p class="black">We’re unable to process your CL transfer, however, we are partnered with the other major providers in your area. This allows us to get you new customer promotions to save you money. Did you have TV services through CL or was it just internet & phone? </p>
    														</div>
														</span>														
													</div>
											</section> 											
											<!-- Form Content -->
										</div>
									</div>
									<div id="required_container" style="color:red; display:none">
  										<p>* Required</p>
									</div>
								</div>
				
								<div id="show-dialog" style="display: none;">
									<center>
										${popUpMessage} <br /> <br /> 
										 <input	type="button" id="dialogId" name="dialogId"	onClick="hideDialog();" value="OK" />
									</center>
								</div>
				
								<div class="bottombuttons">
									<div class="rightbtns">
										<input type="button" id="qualificationForward" name="qualificationForward" value="Forward >" onclick="goToPowerPitchDisplay();"/>
									</div>
								</div>
							</div>
							<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
							<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							</form>
						</section><!--  Content -->
					</section><!-- Content Full Cont -->