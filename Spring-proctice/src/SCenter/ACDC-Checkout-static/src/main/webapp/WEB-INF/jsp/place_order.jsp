<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ page import="com.AL.ui.domain.dynamic.dialogue.DataField"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Place order</title>
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.all.css'/>" />

<link rel="stylesheet"	href="<c:url value='/style/CKO_att_iframe.css'/>" />

<link rel="stylesheet"	href="<c:url value='/style/jquery.datepick.css'/>" />

<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>
<script src="<c:url value='/script/features.js'/>"></script>
<script src="<c:url value='/js/jquery.js'/>"></script>


<script>
	$(document).ready(function() {

		$(".confirmationDialogueOk").click(function()
		{
			$(".confirmationDialogueBox").dialog("close");
			$("form[name='placeOrderForm']").submit();
		});
		
		$(".confirmationDialogueCancel").click(function()
		{
			$(".confirmationDialogueBox").dialog("close");
		});
		
  		$("div#or_arrowsbtn").click(basepricetoggle);
 		$("div#or_oidetarrowsbtn").click(orderitemdetailstoggle);
 		
    	$("#submitLineItem").click(function(){
        	try{
        	$('#confirmOrderMissing').css('display', 'none');
           	$('#authorizeOrderMissing').css('display', 'none');
        	$('#validatorMessage').css('display', 'none');
        	
        	$('#orderplace_datacont').css('float', 'left');
        	$('#mandatoryDisclaimer').css('float', 'left');
        	$('#mandatoryDisclaimer').height(30);
        		
     		var confirmOrder = $('#confirmOrder').is(':checked');
     		var auth = $("input[name='authorizeOrder']:checked").val();
     		
          	if( !confirmOrder && auth == undefined )
			{
          		$('#confirmOrderMissing').css('display', 'block');
           		$('#authorizeOrderMissing').css('display', 'block');
           		$('#validatorMessage').css('display', 'block');
			}
          	else if( !confirmOrder )
            {
          		$('#confirmOrderMissing').css('display', 'block');
        		$('#validatorMessage').css('display', 'block');
          	}
          	else if( auth == undefined )
            {
          		$('#authorizeOrderMissing').css('display', 'block');
        		$('#validatorMessage').css('display', 'block');
        		$('#mandatoryDisclaimer').css('float', 'none');
        		$('#mandatoryDisclaimer').height(25);
          	}
          	else 
          	{
          		if(auth != undefined && auth != "no")
              	{
          			$(".confirmationDialogueBox").dialog({
            			resizable : false,
            			dialogClass:'confirmationDialogue',
            			title:"Alert",
            			height : 130,
            			width : 320,
            			modal : true,
            			zIndex : 99999
            		});
              	}
          		else
          		{
          			$("form[name='placeOrderForm']").submit();
          		}
          	}
          	
        	}catch(err){
            	alert(err);
            }
   		});
	});
	
	function basepricetoggle()
	{
   		var div =$(this).parent().parent();
       	if($(this).hasClass("maximize")){
        	$(this).removeClass('or_id_row2').addClass('or_id_row2_1');
           	$("#or_basemonthlyprice").show();
           	$(this).removeClass('maximize').addClass('minimize');
       	}else{
        	$(this).removeClass('or_id_row2_1').addClass('or_id_row2');
        	$("#or_basemonthlyprice").hide();
            $(this).removeClass('minimize').addClass('maximize');
    	}
	}
	
	function orderitemdetailstoggle()
	{
  		var div =$(this).parent().parent();
     	if($(this).hasClass("maximize"))
        {
     		div.find("#or_orderitemdetdata").show();
         	$(this).removeClass('maximize').addClass('minimize');
     	}
     	else
        {
       		div.find("#or_orderitemdetdata").hide();
          	$(this).removeClass('minimize').addClass('maximize');
       	}
	}

	function displayFlyoutValuesOnOrderRecap()
	{
		var selectedFeaturesJSON = $('#selectedFeaturesJSONHiddenValue').val();
		var oqSelectedFeaturesJSON = $('#selectedFeaturesJSONHiddenValue').val();
		var selFeatsArray = null;
		if(selectedFeaturesJSON != null && selectedFeaturesJSON != "" && selectedFeaturesJSON.trim().length > 0)
		{
			var selFeatJSON = JSON.parse(selectedFeaturesJSON);
			if(selFeatJSON.selectedFeatureArray!=null && typeof selFeatJSON.selectedFeatureArray != "undefined")
			{
				selFeatsArray = selFeatJSON.selectedFeatureArray;
			}
			else
			{
				selFeatsArray = selFeatJSON;
			}
		}
		if(oqSelectedFeaturesJSON != null && typeof oqSelectedFeaturesJSON != "undefined" && oqSelectedFeaturesJSON.trim().length > 0)
		{
			var oqSelFeatJSON = JSON.parse(oqSelectedFeaturesJSON);
			if(oqSelFeatJSON != null && typeof oqSelFeatJSON != "undefined")
			{
				if(oqSelFeatJSON.oqselfeatures!=null && typeof oqSelFeatJSON.oqselfeatures != "undefined")
				{
					mergeTwoJSONArrays( selFeatsArray, oqSelFeatJSON.oqselfeatures );
				}
				else
				{
					mergeTwoJSONArrays( selFeatsArray, oqSelFeatJSON );
				}
			}
		}

		if(selFeatsArray!=null && selFeatsArray.length > 0 )
		{
			displayPriceDetails(selFeatsArray);
		}
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
		
	
	function onLoadFunctions()
	{
		displayFlyoutValuesOnOrderRecap()
    	symFeedback();
	}
        
</script>

<style>
.pc_pdetails_cont .pc_steps {
	width: 95px !important;
}

.ui-widget {
    font-family: Trebuchet MS,Tahoma,Verdana,Arial,sans-serif;
    font-size: 14px;
}

.pc_pdetails_cont .pc_frameblock {
	border: 1px solid #616264;
	float: left;
	height: 450px;
	padding: 2px 2px 2px 2px;
	position: relative;
	width: 849px;
}


.bottombuttons {
    position: relative;
    top: 5px;
    padding-left:8px;
}
#action_wrapper {
    height: 355px;
    margin: 0;
    position: static;
    width: 849px;
}
.coaching {
	color: green;
}

.Suggested {
	color: grey;
}
.contentwrapper {
    font-family: Arial,Helvetica,sans-serif,Verdana;
    height: 345px;
    position: relative;
    width: 849px;
}
.widget_container_or_1 {
	background-color: #FFFFFF;
	float: left;
	height: 275px;
	vertical-align: top;
	width: 997px;
}

.pagecontainer_or_1 {
	height: 275px;
	overflow-y: auto;
	padding: 0 10px 0 0;
	width: 987px;
}

#dialogue_content{
	width: 828px;
	border-color: #616264;
	border-style: solid;
	border-width: 2px;
	border-top-width: 1px;
	border-bottom-width: 2px;
	max-height:195px;
}

#dialogue_wrapper {
	width: 828px;
	max-height: 196px;
	background-color: #e0e0e0;
	padding-left:1px;
	padding-right: 1px;
	/*border: 1px solid aqua;*/	
}
.or_itemdetails {
    padding-left: 0px;
    position: relative;
    width: 835px;
}

.confirmationDialogue {
    border: 6px solid #a1a1a1;
    padding: 1px 40px; 
    background: #FDFAFA;
  /*   width: 300px; */
    border-radius: 25px;
    position:fixed;
    top: 50%;
    left: 30%;
}

.element.style {
	height: 100px;
	min-height: 0;
	width : 370px;
}
.confirmationDialogue, .ui-widget-content {
	/* background : none */
	border : none
}
.confirmationDialogue, .ui-widget-header {
	/* background : none repeat scroll 0 0 #FFFFFF; */  
	 display: none;
	border : none;
}
.ui-helper-clearfix:before, .ui-helper-clearfix:after{
display:none;
}
.ui-dialog .ui-dialog-content{
overflow:-moz-hidden-unscrollable;
}

.ui-widget-overlay{
background : none;
}

.confirmationDialogue, .ui-widget-header{

border : medium solid;
color : #A9A9A9;

}


</style>
</head>

<body onload="onLoadFunctions();">
	<input id="iData" name="iData" value='${iData}' type="hidden" />
	<form method="POST"	action="<%=request.getContextPath()%>/static/submitLineItem" name="placeOrderForm" onsubmit="symFeedbackSubmit()" autocomplete=off>
		<input type="hidden" name="addressId" id="addressId" value="${addressId}" /> 
		<input type="hidden" name="orderId" id="orderId" value="${order.externalId}" />
		<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
		<input type="hidden" id="pageTitle" name="pageTitle" value="Order Recap">
		<input type="hidden" id="selectedFeatureJSONValue" name="selectedFeatureJSONValue" value='${selectedFeatureJSONValue}'/>
		 <input type="hidden" id="selectedFeaturesJSONHiddenValue" name="selectedFeaturesJSONHiddenValue" value='${selectedFeaturesJSONHiddenValue}'/>
		<input type="hidden" id="lineItemMonthlyPrice" name="lineItemMonthlyPrice" value="${productMonthlyPrice}" />
		<input type="hidden" id="lineItemInstallationPrice" name="lineItemInstallationPrice" value="${productInstallationPrice}" />
		<input type="hidden" id="productMonthlyPrice" name="productMonthlyPrice" value="${productMonthlyPrice}" />
		<input type="hidden" id="productInstallationPrice" name="productInstallationPrice" value="${productInstallationPrice}" />
		<div class="pc_pdetails">
				<div class="pc_pdetails_logo">
			    	<c:if test="${not empty providerExternalID && !isUtilityOffer}">
			    	 <img style="float: left;max-height:60px;max-width:107px;" alt="${lineItemType.lineItemDetail.detail.productLineItem.provider.externalId}" src="<%=request.getContextPath()%>/image/${parentExternalId}.jpg"
                     	  onError="this.onerror=null;this.src='<%=request.getContextPath()%>/image/${lineItemType.lineItemDetail.detail.productLineItem.provider.externalId}.jpg';"  id="provider" >
				    </c:if>
			  	</div>
			  	<div class="pc_pdetails_info">
			  		<c:set var="productName" value="${lineItemType.lineItemDetail.detail.productLineItem.name}" /> 
					<c:set var="displayLi" value='true' /> 
					<c:set var="providerId" value="${lineItemType.lineItemDetail.detail.productLineItem.provider.externalId}" />
					<c:set var="detailType" value="${lineItemType.lineItemDetail.detailType}" /> 
					<c:set var="statusAttribute" value='' /> 
					<c:set var="lineItemName" value='' />
					<c:set var="lineItemProviderName" value='' /> 
					<c:set var="desireDate2" value='' /> 
					<c:set var="desireDate1Time" value='' /> 
					<c:set var="desireDate2Time" value='' /> 
					<c:if test="${detailType == 'productPromotion' }">
						<c:set var="displayLi" value='false' />
					</c:if>
					<c:forEach var="entity" items="${lineItemType.lineItemAttributes.entity}">
						<c:if test="${entity.source == 'provider_feedback'}">
							<c:forEach var="attribute" items="${entity.attribute}">
								<c:if test="${attribute.name == 'Display' && attribute.value == 'false'}">
									<c:set var="displayLi" value='false' />
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${entity.source == 'CKO'}">
							<c:forEach var="attribute" items="${entity.attribute}">
								<c:if test="${attribute.name == 'STATUS'}">
									<c:set var="statusAttribute" value='${attribute.value}' />
								</c:if>
								<c:if test="${attribute.name == 'baseMonthlyFee'}">
									<c:set var="baseMonthlyNonPromotionPrice" value='${attribute.value}' />
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${entity.source == 'PRODUCT_NAME'}">
							<c:forEach var="attribute" items="${entity.attribute}">
								<c:if test="${attribute.name == 'name'}">
									<c:set var="lineItemName" value='${attribute.value}' />
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${entity.source == 'PROVIDER_NAME'}">
							<c:forEach var="attribute" items="${entity.attribute}">
								<c:if test="${attribute.name == 'name'}">
									<c:set var="lineItemProviderName" value='${attribute.value}' />
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${entity.source == 'SECOND_DESIRED_DATE'}">
							<c:forEach var="attribute" items="${entity.attribute}">
								<c:if test="${attribute.name == 'DATE'}">
									<c:set var="desireDate2" value='${attribute.value}' />
								</c:if>
								<c:if test="${attribute.name == 'TIME'}">
									<c:set var="desireDate2Time" value='${attribute.value}' />
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${entity.source == 'FIRST_INSTALLATION_TIME'}">
							<c:forEach var="attribute" items="${entity.attribute}">
								<c:if test="${attribute.name == 'TIME'}">
									<c:set var="desireDate1Time" value='${attribute.value}' />
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${entity.source == 'IMAGE_ID'}">
							<c:forEach var="attribute" items="${entity.attribute}">
								<c:if test="${attribute.name == 'id'}">
									<c:set var="parentExternalId" value='${attribute.value}' />
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach>

					<div class="label">Product Name:</div>
					<c:choose>
						<c:when  test="${not empty productName}">
							<div class="value"><c:out escapeXml="false" value="${productName}" />
							</div>
							<br/>
						</c:when>
						<c:otherwise>
							<div class="value"><c:out escapeXml="false" value="${lineItemName}" />
							</div>
							<br/>
						</c:otherwise>
					</c:choose>
					<c:if test="${isUtilityOffer != true}">
						<div class="label">Monthly Total:</div>
						<div class="value"  style="width:48px;">
							<span id="monthlyCost">
								<fmt:formatNumber type="currency" value="${lineItemType.lineItemPriceInfo.baseRecurringPrice}" />
							</span>
						</div>
						<span>&nbsp;&nbsp;
							<jsp:include page="MonthlyTotalWidget.jsp"></jsp:include>
						</span>
						<input type="hidden" id="monthlyCostAmtFld" name="monthlyCostAmtFld" value="${lineItemType.lineItemPriceInfo.baseRecurringPrice}" />
						<br/>				
						<div class="label">Installation Total:</div>
						<div class="value"  style="width:48px;">
							<span id="oneTimePrice">
								<fmt:formatNumber type="currency" value="${lineItemType.lineItemPriceInfo.baseNonRecurringPrice}" />
							</span>
							<input type="hidden" id="oneTimePriceFld" name="oneTimePriceFld" value="${lineItemType.lineItemPriceInfo.baseNonRecurringPrice}" />
						</div>
						<span>&nbsp;&nbsp;
							<jsp:include page="InstallTotalWidget.jsp"></jsp:include>
						</span>
					<span class="label" style="margin-top: -38px; text-align: right; margin-left: 687px;">Account Holder:</span>
                    <span class="value" style="text-align: right; margin-left: 702px; margin-top: -20px; width: 157px;">${customerName}</span>
						<br/>				
					</c:if>
				</div>
				</div>
				<div class="pc_pdetails_cont">
					<div class="pc_steps">
						<div id="pc_steps_one" class="pc_steps_item_complete">
							<div class="pc_steps_item_stepdet">
								<div class="pc_steps_item_text">Step</div>
								<div class="countbg"><span>1</span></div>
							</div>
							<div class="pc_steps_item_sttext">Product Customization</div>
						</div>
						<div id="pc_steps_two" class="pc_steps_item_complete">
							<div class="pc_steps_item_stepdet">
								<div class="pc_steps_item_text">Step</div>
								<div class="countbg"><span>2</span></div>
							</div>
							<div class="pc_steps_item_sttext">Customer Qualification</div>
						</div>
						<div id="pc_steps_three" class="pc_steps_item_progress pc_steps_item_margin">
							<div class="pc_steps_item_stepdet">
								<div class="pc_steps_item_text">Step</div>
								<div class="countbg"><span>3</span></div>
							</div>
							<div class="pc_steps_item_sttext">Authorization</div>
						</div>
					</div>
					<!-- End Left Block --> <!-- Display promotions and features, featuregroups that are present for the product -->
					<!-- Right Block -->
					<div class="pc_frameblock">
						<div class="contentwrapper">
							<div id="action_wrapper">
								<input type="hidden" name="lineItemExternalId" value="${lineItemType.externalId}" /> 
								<!-- Display the steps that are present in the CKO process -->
								<div class="pc_pdetails_cont">
									<c:if test="${displayLi == 'true'}">
										<div class="product">
											<c:set var="liStatus" value="${lineItemType.lineItemStatus.statusCode}" /> 
											<c:set var="productExternalId" value='${lineItemType.lineItemDetail.detail.productLineItem.externalId}' />
											<div class="or_content pagecontainer_or">
												<div class="or_itemdetails">
													<section id="dialogue_wrapper" style="font-size:14px;">						   
														<section id="dialogue_content">
															<div style="overflow-x: hidden;overflow-y: auto;padding-left:5px;" id="content_new">
																<c:forEach var="dataFieldType" items="${dataFieldList}">
																	<c:if test="${dataFieldType.description=='Mandatory'}">
																		<c:set var="dataFieldText" value="${dataFieldType.text}"/>
																		<c:choose>
								  											<c:when test="${fn:contains(dataFieldText, '{referrer.businessParty.name}')}">
								  												<c:set var="referrer_name_replaced" value="${businessPartyName}" />
								  												<c:set var="referrer_name" value="{referrer.businessParty.name}" />
								    											<c:set var="dataFieldText_replaced" value="${fn:replace(dataFieldText,referrer_name, referrer_name_replaced)}" />
								  											</c:when>
																			<c:otherwise>
																				<c:set var="dataFieldText_replaced" value="${dataFieldText}" />
																			</c:otherwise>
																		</c:choose>
																		<p style="font-weight: bold;"><span class="red"><fmt:message key="orderRecap.mandatory"/>&nbsp;</span>${dataFieldText_replaced}</p>
																	</c:if>
																	<c:if test="${dataFieldType.description=='Coaching'}">
																		<p><span class="coaching">${dataFieldType.text}</span></p>
																	</c:if>
																	<c:if test="${dataFieldType.description=='Suggested'}">
																		<p><span class="Suggested">${dataFieldType.text}</span></p>
																	</c:if>
																	<c:if test="${dataFieldType.description!='Suggested' && dataFieldType.description!='Mandatory' && dataFieldType.description!='Coaching'}">
																		<p  class="black"><span class="Suggested">${dataFieldType.text}</span></p>
																	</c:if>
																</c:forEach>
															</div>
														</section>
													</section>
													<div id="dialogue_content_balloon"></div>	
													<div style="font-size: 14px;font-weight: bold;padding-left: 5px;">Review Your <c:out escapeXml="false" value="${lineItemProviderName}" /> Order Details:</div>
													<table cellpadding="0" cellspacing="0" class="or_basemonthlyprice">
														<tr class="or_id_row1">
															<th width="3%"></th>
															<th width="30%"><fmt:message key="provider.list.description" /></th>
															<th width="20%"><fmt:message key="orderSummary.quantity" /></th>
															<th width="47%"><span ><fmt:message key="orderSummary.price" /></span></th>
														</tr>
														<tr id="or_id_row2" class="or_id_row2">
															<td width="3%"></td>
															<td width="30%"><fmt:message key="orderSummary.basePackagePriceNonPromotion" /></td>
															<td width="20%">-</td>
															<td width="47%">
																<span  style="text-align: right;"><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyNonPromotionPrice}" maxFractionDigits="2" pattern="0.00" /> </span> 
																<span class="os_normal" id="baseMonthlyNonPromotionPriceTaxSPId"><fmt:message key="orderRecap.plusTax" /> </span>
															</td>
														</tr>
														<tr id="or_id_row2" class="or_id_row2">
															<td width="3%">
																<div class="or_basepricetoggle">
																	<c:if test="${hasFeatures eq true}">
																		<div id="or_arrowsbtn" class="maximize"></div>
																	</c:if>
																</div>
															</td>
															<td width="30%"><fmt:message key="orderSummary.basePackagePrice" /></td>
															<td width="20%">-</td>
															<td width="47%">
																<span ><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyPrice}" maxFractionDigits="2" pattern="0.00" /></span>
																<span class="os_normal"> <fmt:message key="orderRecap.plusTax" /> </span>
															</td>
														</tr>
														<tr class="or_id_row3" id="or_basemonthlyprice" style="display: none;">
															<td colspan="4">
																<table cellpadding="0" cellspacing="0" class="or_basemonthlyprice_tbl">
																	<!--Features-->
																	<c:forEach var="feature" items="${lineItemType.selectedFeatures.features.featureValue}">
																		<c:if test="${empty feature.included}">
																			<tr class="border">
																				<td width="3%"></td>
																				<td>
																					<c:choose>
																						<c:when test="${not empty productFeatureMap[feature.externalId]}">${productFeatureMap[feature.externalId]}</c:when>
																						<c:otherwise>${feature.externalId}</c:otherwise>
																					</c:choose>
																				</td>
																				<td align="center">
																					<c:choose>
																						<c:when test="${feature.value eq 'true'}">&#x2713;</c:when>
																						<c:otherwise>${feature.value}</c:otherwise>
																					</c:choose>
																				</td>
																				<td>
																					<span class="or_montsize"><fmt:message key="orderSummary.monthly" />:</span>
																					<span><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${feature.price.baseRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span> 
																					<br />
																					<span class="or_instsize"><fmt:message key="orderSummary.install" />:</span> 
																					<span><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${feature.price.baseNonRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span>
																				</td>
																			</tr>
																		</c:if>
																	</c:forEach>
																	<!--Feature Groups -->
																	<c:forEach var="featureGroup" items="${lineItemType.selectedFeatures.featureGroup}">
																		<c:forEach var="feature" items="${featureGroup.featureValue}">
																			<c:if test="${empty feature.included}">
																				<tr class="border">
																					<td width="3%"></td>
																					<td>
																						<c:choose>
																							<c:when test="${not empty productFeatureMap[feature.externalId]}">${productFeatureMap[feature.externalId]}</c:when>
																							<c:otherwise>${feature.externalId}</c:otherwise>
																						</c:choose>
																					</td>
																					<td align="center">
																						<c:choose>
																							<c:when test="${feature.value eq 'true'}">&#x2713;</c:when>
																							<c:otherwise>${feature.value}</c:otherwise>
																						</c:choose>
																					</td>
																					<td>
																						<span class="or_montsize"><fmt:message key="orderSummary.monthly" />:</span>
																						<span><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${feature.price.baseRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span> 
																						<br />
																						<span class="or_instsize"><fmt:message key="orderSummary.install" />:</span> 
																						<span><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${feature.price.baseNonRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span>
																					</td>
																				</tr>
																			</c:if>
																		</c:forEach>
																	</c:forEach>
																</table>
															</td>
														</tr>
														<tr class="or_id_row4">
															<td width="3%"></td>
															<td width="30%"><fmt:message key="orderRecap.monthlyCost" /></td>
															<td width="20%">-</td>
															<td width="47%">
																<span > <fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${lineItemType.lineItemPriceInfo.baseRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span> 
																<span class="os_normal"><fmt:message key="orderRecap.plusTax" /></span>
															</td>
														</tr>
														<tr class="or_id_row4">
															<td width="3%"></td>
															<td width="30%"><fmt:message key="orderRecap.installationCost" /></td>
															<td width="20%">-</td>
															<td width="47%">
																<span ><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${lineItemType.lineItemPriceInfo.baseNonRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span>
															</td>
														</tr>
													</table>
												</div>
												<c:if test="${ not empty LineItemVo}">
													<div class="or_promotions">
														<div class="or_promo_title"><fmt:message key="orderRecap.promotions" /></div>
														<c:forEach var="applicableType" items="${LineItemVo}">
															<div class="or_promo_title"><fmt:message key="orderRecap.promoDescription" /></div>
															<div class="or_promo_desc">${applicableType.promotion.description}</div>
															<div class="or_promoterms_title"><fmt:message key="orderRecap.promoTermsConditions" /></div>
															<div class="or_promoterms_desc">${applicableType.promotion.conditions}</div>
														</c:forEach>
													</div>
												</c:if> 
												<c:if test="${not empty lineItemType.schedulingInfo.desiredStartDate.date || not empty desireDate2}">
													<c:set var="date" value="${fn:substring(lineItemType.schedulingInfo.desiredStartDate.date, 0, 10)}" />
													<c:set var="startTime" value="${fn:substring(lineItemType.schedulingInfo.desiredStartDate.startTime, 0, 5)}" />
													<c:set var="endTime" value="${fn:substring(lineItemType.schedulingInfo.desiredStartDate.endTime,  0, 5)}" />
													<c:set var="time" value="${fn:substring(lineItemType.schedulingInfo.desiredStartDate.time,  0, 5)}" />
													<c:if test="${empty startTime}">
														<c:set var="startTime" value="${time}" />
													</c:if>
													<div class="or_reqinstdate">
														<div class="or_reqinstdate_title"><fmt:message key="orderRecap.installDatesTimes" /></div>
														<div>
															<div class="or_reqinstdate_lbl"><fmt:message key="orderRecap.desiredDate" />:</div>
															<div class="or_reqinstdate_date"><fmt:parseDate var="dateObj" value="${date}" type="DATE" pattern="yyyy-MM-dd" /><fmt:formatDate pattern="EEEE, MMMM d, yyyy" value="${dateObj}" /> 
																<c:choose>
																	<c:when test="${not empty desireDate1Time}">&nbsp;${desireDate1Time}</c:when>
																	<c:otherwise>
																		<c:if test="${startTime != '00:00'}">
																			<fmt:parseDate var="startDateObj" value="${date} ${startTime}" type="DATE" pattern="yyyy-MM-dd HH:mm" />
																			<fmt:parseDate var="endDateObj" value="${date} ${endTime}" type="DATE" pattern="yyyy-MM-dd HH:mm" />&nbsp;<fmt:formatDate pattern="h:mm a" value="${startDateObj}" />&nbsp;to&nbsp;<fmt:formatDate pattern="h:mm a" value="${endDateObj}" />
																		</c:if>
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
														<c:if test="${not empty desireDate2}">
															<div>
																<div class="or_reqinstdate_lbl"><fmt:message key="orderRecap.desiredDate2" />:</div>
																<div class="or_reqinstdate_date"><fmt:parseDate var="dateObj" value="${desireDate2}" type="DATE" pattern="yyyy-MM-dd" /><fmt:formatDate pattern="EEEE, MMMM d, yyyy" value="${dateObj}" />&nbsp;${desireDate2Time}</div>
															</div>
														</c:if>
													</div>
												</c:if> 
												<c:if test="${not empty lineItemType.schedulingInfo.scheduledStartDate.date || not empty lineItemType.schedulingInfo.disconnectDate.date || not empty lineItemType.schedulingInfo.actualStartDate.date}">
													<div class="or_orderitemdetails">
														<div class="or_orderitemdettoggle">
															<div id="or_oidetarrowsbtn" class="maximize"></div>
														</div>
											
														<div class="or_orderitemdetails_cont">
															<div class="or_orderitemdet_title">Order Item Details</div>
																<div id="or_orderitemdetdata" class="or_orderitemdetdata" style="display: none;">
																	<div class="or_orderitems_detlist">
																		<div class="col1"><fmt:message key="orderRecap.importDates" /></div>
																		<div class="col2">
																			<div class="col2_item"><fmt:message key="orderRecap.schedDate" />:</div>
																			<div class="col2_item"><fmt:message	key="orderRecap.disconnectDate" />:</div>
																			<div class="col2_item"><fmt:message key="orderRecap.actualDate" />:</div>
																		</div>
																		<div class="col3">
																			<div class="col3_item">
																				<c:if test="${not empty lineItemType.schedulingInfo.scheduledStartDate.date}">
																					<c:set var="date" value="${fn:substring(lineItemType.schedulingInfo.scheduledStartDate.date, 0, 10)}" />
																					<c:set var="startTime" value="${fn:substring(lineItemType.schedulingInfo.scheduledStartDate.startTime, 0, 5)}" />
																					<c:set var="endTime" value="${fn:substring(lineItemType.schedulingInfo.scheduledStartDate.endTime, 0, 5)}" />
																					<c:set var="time" value="${fn:substring(lineItemType.schedulingInfo.scheduledStartDate.time, 0, 5)}" />
																					<c:if test="${empty startTime}">
																						<c:set var="startTime" value="${time}" />
																					</c:if>
																					<fmt:parseDate var="dateObj" value="${date}" type="DATE" pattern="yyyy-MM-dd" />
																					<fmt:formatDate pattern="EEEE, MMMM d, yyyy" value="${dateObj}" />
																					<c:if test="${startTime != '00:00'}">
																						<fmt:parseDate var="startDateObj" value="${date} ${startTime}" type="DATE" pattern="yyyy-MM-dd HH:mm" />
																						<fmt:parseDate var="endDateObj" value="${date} ${endTime}" type="DATE" pattern="yyyy-MM-dd HH:mm" />&nbsp;<fmt:formatDate pattern="h:mm a" value="${startDateObj}" />&nbsp;to&nbsp;<fmt:formatDate pattern="h:mm a" value="${endDateObj}" />
																					</c:if>
																			</c:if>
																		</div>
																		<div class="col3_item">
																			<c:if test="${not empty lineItemType.schedulingInfo.disconnectDate.date}">
																				<c:set var="date" value="${fn:substring(lineItemType.schedulingInfo.disconnectDate.date, 0, 10)}" />
																				<c:set var="startTime" value="${fn:substring(lineItemType.schedulingInfo.disconnectDate.startTime, 0, 5)}" />
																				<c:set var="endTime" value="${fn:substring(lineItemType.schedulingInfo.disconnectDate.endTime,  0, 5)}" />
																				<c:set var="time" value="${fn:substring(lineItemType.schedulingInfo.disconnectDate.time,  0, 5)}" />
																				<c:if test="${empty startTime}">
																					<c:set var="startTime" value="${time}" />
																				</c:if>
																				<fmt:parseDate var="dateObj" value="${date}" type="DATE" pattern="yyyy-MM-dd" />
																				<fmt:formatDate pattern="EEEE, MMMM d, yyyy" value="${dateObj}" />
																				<c:if test="${startTime != '00:00'}">
																					<fmt:parseDate var="startDateObj" value="${date} ${startTime}" type="DATE" pattern="yyyy-MM-dd HH:mm" />
																					<fmt:parseDate var="endDateObj" value="${date} ${endTime}" type="DATE" pattern="yyyy-MM-dd HH:mm" />&nbsp;<fmt:formatDate pattern="h:mm a" value="${startDateObj}" />&nbsp;to&nbsp;<fmt:formatDate pattern="h:mm a" value="${endDateObj}" />
																				</c:if>
																			</c:if>
																		</div>
																		<div class="col3_item">
																			<c:if test="${not empty lineItemType.schedulingInfo.actualStartDate.date}">
																				<c:set var="date" value="${fn:substring(lineItemType.schedulingInfo.actualStartDate.date, 0, 10)}" />
																				<c:set var="startTime" value="${fn:substring(lineItemType.schedulingInfo.actualStartDate.startTime,  0, 5)}" />
																				<c:set var="endTime" value="${fn:substring(lineItemType.schedulingInfo.actualStartDate.endTime,  0, 5)}" />
																				<c:set var="time" value="${fn:substring(lineItemType.schedulingInfo.actualStartDate.time,  0, 5)}" />
																				<c:if test="${empty startTime}">
																					<c:set var="startTime" value="${time}" />
																				</c:if>
																				<fmt:parseDate var="dateObj" value="${date}" type="DATE" pattern="yyyy-MM-dd" />
																				<fmt:formatDate pattern="EEEE, MMMM d, yyyy" value="${dateObj}" />
																				<c:if test="${startTime != '00:00'}">
																					<fmt:parseDate var="startDateObj" value="${date} ${startTime}" type="DATE" pattern="yyyy-MM-dd HH:mm" />
																					<fmt:parseDate var="endDateObj" value="${date} ${endTime}" type="DATE" pattern="yyyy-MM-dd HH:mm" />&nbsp;<fmt:formatDate pattern="h:mm a" value="${startDateObj}" />&nbsp;to&nbsp;<fmt:formatDate pattern="h:mm a" value="${endDateObj}" />
																				</c:if>
																			</c:if>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</c:if>
											</div>
										</div>
									</c:if>
								</div>
							</div>
						</div>
						<div style="padding-left:8px;">
							<div class="orderplace_cont">
								<div id="mandatoryDisclaimer" class="mandatoryDisclaimer" style="font-size: 14px;float: left;">
									<p style="margin-bottom: 0px; margin-top: 5px; margin-left: 10px;">
										<span><fmt:message key="orderRecap.mandatoryAuthorization" /></span>
										<span style="vertical-align: text-top;"><input type="checkbox" name="confirmOrder" id="confirmOrder" /></span>
									</p>
								</div>
								<span id="confirmOrderMissing" style="display: none; color: red; font-size: 23px;padding-left:380px;" >*</span>
								<div id="orderplace_datacont"  class="orderplace_datacont" style="font-weight: bold;float: left;">
									<span class="red" style="font-weight:bold;"><fmt:message key="orderRecap.mandatory" /></span>
									&nbsp;<span style="font-size: 14px;font-weight: bold;padding-left: 5px;"><c:out escapeXml="false" value="${customerName}" />, </span>
									&nbsp;<fmt:message key="orderRecap.authConformation" />
									<input type="radio"	name="authorizeOrder" id="authorizeOrder_Y" value="yes" /><fmt:message key="orderRecap.yes" />
									<input type="radio" name="authorizeOrder" id="authorizeOrder_N" value="no" /><fmt:message	key="orderRecap.no" />
								</div>
								<span id="authorizeOrderMissing" style="display: none; color: red; font-size: 23px;padding-left:545px;" >*</span>
							</div>
						</div>
						<div class="bottombuttons">
							<div class="leftbtns" style="float: left">
								<input type="button" id="submitLineItem" class="submitBtn" value="Submit" />
							</div>
							<div id="validatorMessage" style="display: none; padding-left: 80px;">
								<img id="astrik" src='<%=request.getContextPath()%>/image/red_asterisk.png' style="height:9px !important; width:9px !important;"> </img>
								<label style="color: red; font-size: 12px;font-weight: bold;" id="astrikLbl"> Required Fields</label>
							</div>
						</div>
					</div>
				</div>
				<div class="confirmationDialogueBox" style="display:none;">
					<p align="center" >Are you sure you want to place this order?</p><br>
					<input type="button" style="margin-left:89px;" class="confirmationDialogueOk" name="Ok" value="OK"/><span></span>
					<input type="button" size="2px" class="confirmationDialogueCancel" name="Cancel" value="CANCEL" />				
				</div>
			</section> 
		</section><!-- Content Full Cont --> 
	</form>
</body>
</html>

