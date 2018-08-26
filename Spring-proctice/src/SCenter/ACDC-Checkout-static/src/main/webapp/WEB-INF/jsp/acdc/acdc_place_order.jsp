<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ page import="com.AL.ui.domain.dynamic.dialogue.DataField"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
   <head>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
      <script>
	var jq180 = jQuery.noConflict();
   </script>
   <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
      	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<c:url value='/common/js/bootstrap-modalmanager.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/common/js/bootstrap-modal.js'/>"></script>
   </head>
   <style>
      .content_new{
    -background: rgba(0, 0, 0, 0) -moz-linear-gradient(center top , #d0e4f7 0%, #73b1e7 -1%, #fff 87%, #fff 8%, #0a77d5 56%, #fff 36%, #d0e4f7 99%, #87bcea 73%) repeat scroll 0 0;
    -background: rgba(0, 0, 0, 0) linear-gradient(to bottom, #f8ffe8 3%, #e3f5ab 26%, #f8ffe8 100%) repeat scroll 0 0;
    -border-color: #616264;
    border-radius: 4px;
    border-style: solid;
    border-width: 1px 2px 2px;
    max-height: 195px;
    padding: 14px;
      }
      .name_red{
      color: red;
      font-size: 14px;
      font-weight: bold;
      }
      .coaching {
      color: green;
      }
      #empty{
      background-color: transparent;    
      height: 24px;
      margin-left: 80%;
      margin-top: -2px;
      width: 51px;
      }
      .line{
      font-size: 14px;
      font-weight: bold;
      padding-left: 5px;
      }
      .last_data{
      border-bottom: 1px solid #000;
      color: #000;
      margin-left: 48px;
      padding: 6px 0 8px;
      }
      .last_data_title{
         border-radius: 5px;
    font-size: 15px;
    font-weight: bold;
    padding: 3px 2px 2px 27px;
      }
      .last_data-lbl{
        color: #666;
    font-weight: bold;
      }
      
      .last_section{
    -background: rgba(0, 0, 0, 0) -moz-linear-gradient(center top , #d0e4f7 0%, #73b1e7 -1%, #fff 87%, #fff 8%, #0a77d5 56%, #fff 36%, #d0e4f7 99%, #87bcea 73%) repeat scroll 0 0;
    -border-color: #616264;
    border-radius: 4px;
    border-style: solid;
    border-width: 1px 2px 2px;
    font-weight: bold;
    padding: 8px;
      }
      .button{
      padding-left: 8px;
      position: relative;
      top: 5px;
      margin:15px;
      }
      tr td{
      padding-right:5px;
      margin-left:50px;
      font-size:14px;
      }
      tr th{
      color: #0066cc;
      font-size: 14px;
      height: 25px;
      vertical-align: middle;
      margin-right:20px;
      padding-right:20px;
      }

	.reqdateCont{
		padding: 5px;
		 margin: 5px;
	}
.container{
 border: 9px solid #eee;
  border-radius: 5px;
}

.AL-green .button {
    -background-color: #9DC45F;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-border-radius: 5px;
    border: none;
    padding: 10px 25px 10px 25px;
    -color: #FFF;
    -text-shadow: 1px 1px 1px #949494;
	font-weight: bold;
}
.AL-green .button:hover {
    background-color:#80A24A;
}
.confirmationDialogueBox.ui-dialog-content.ui-widget-content {
    height: auto !important;
}
.placeOrderContBorder {
 border: 9px solid #eee;
    border-radius: 8px;
    padding: 25px;
}
.reqDates{
    border: 9px solid rgb(238, 238, 238);
    border-radius: 9px;
    margin: 8px auto;
    padding: 2px;
}
   </style>
   <script>
  
   var channelCss = '${channelCss}';
	$(document).ready(function() {
		var path = $("contextPath");
		$(".confirmationDialogueOk").click(function()
		{
			//$(".confirmationDialogueBox").dialog("close");
			 $('#confirmationDialogueBox').modal('hide');
			 //$('#placeOrderLoader').modal('toggle');
			$("form[name='placeOrderForm']").submit();
		});
		
	/* 	$(".confirmationDialogueCancel").click(function()
		{
			$(".confirmationDialogueBox").dialog("close");
		}); */
		
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
          		  $('#confirmationDialogueBox').modal('toggle');
          			/* $(".confirmationDialogueBox").dialog({
            			resizable : false,
            			dialogClass:'confirmationDialogue',
            			title:"Alert",
            			height : 130,
            			width : 320,
            			modal : true,
            			zIndex : 99999
            		}); */
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
		
      <div class="AL-green placeOrderContBorder">
      <div class="row">
	      	<c:if test="${not empty providerExternalID && !isUtilityOffer}">
	    	 	<img style="float: left;max-height:60px;max-width:107px;" alt="${lineItemType.lineItemDetail.detail.productLineItem.provider.externalId}" src="<%=request.getContextPath()%>/image/${parentExternalId}.jpg"
	                  	  onError="this.onerror=null;this.src='<%=request.getContextPath()%>/image/${lineItemType.lineItemDetail.detail.productLineItem.provider.externalId}.jpg';"  id="provider" >
		    </c:if>
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
					<div class="">
						<c:choose>
						<c:when  test="${not empty productName}">
							<div style="padding: 6px;" ><c:out escapeXml="false" value="${productName}" />
							</div>
						</c:when>
						<c:otherwise>
							<div style="padding: 6px;"><c:out escapeXml="false" value="${lineItemName}" />
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="pc_pdetails_cont">
			<c:if test="${displayLi == 'true'}">
			<c:set var="liStatus" value="${lineItemType.lineItemStatus.statusCode}" /> 
			<c:set var="productExternalId" value='${lineItemType.lineItemDetail.detail.productLineItem.externalId}' />
            <input type="hidden" name="lineItemExternalId" value="${lineItemType.externalId}" /> 
         <div class="row">
            <div class="content_new">
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
						<div style="font-weight: bold;"><span class="name_red"><fmt:message key="orderRecap.mandatory"/>&nbsp;</span>${dataFieldText_replaced}</div>
					</c:if>
					<c:if test="${dataFieldType.description=='Coaching'}">
						<div><span class="coaching">${dataFieldType.text}</span></div>
					</c:if>
					<c:if test="${dataFieldType.description=='Suggested'}">
						<div><span class="Suggested">${dataFieldType.text}</span></div>
					</c:if>
					<c:if test="${dataFieldType.description!='Suggested' && dataFieldType.description!='Mandatory' && dataFieldType.description!='Coaching'}">
						<div  class="black"><span class="Suggested">${dataFieldType.text}</span></div>
					</c:if>
				</c:forEach>
            </div>
            <div id="dialogue_content_balloon"></div>
         </div>
         <div clas="row">
               <span class="line"> Review Your <c:out escapeXml="false" value="${lineItemProviderName}" /></span> 
         </div>
         <div class="row">
            <table class="placeOrder  table table-striped table-bordered table-hover">
               <tbody>
                  <tr>
                     <th> Description </th>
                     <th> Selection </th>
                     <th> <span> Price </span> </th>
                  </tr>
                  <tr>
                     <td> Base Monthly Price without Promotion </td>
                     <td> - </td>
                     <td> <span> <fmt:formatNumber value="${baseMonthlyNonPromotionPrice}" maxFractionDigits="2" pattern="0.00" /> </span>
                        <span> plus taxes/surcharges </span> 
                     </td>
                  </tr>
                  <tr>
                     <td> Base Monthly Price with Promotion </td>
                     <td> - </td>
                     <td> <span><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyPrice}" maxFractionDigits="2" pattern="0.00" /></span>
                        <span> plus taxes/surcharges </span>
                     </td>
                  </tr>
                      	<c:forEach var="feature" items="${lineItemType.selectedFeatures.features.featureValue}">
						<c:if test="${empty feature.included}">
                            <tr>
                               <td>
                               	<c:choose>
								<c:when test="${not empty productFeatureMap[feature.externalId]}">${productFeatureMap[feature.externalId]}</c:when>
								<c:otherwise>${feature.externalId}</c:otherwise>
							</c:choose>
						</td>
                               <td>
                               	<c:choose>
								<c:when test="${feature.value eq 'true'}">&#x2713;</c:when>
								<c:otherwise>${feature.value}</c:otherwise>
							</c:choose>
						 </td>
                               <td>
                                  <span>Monthly:</span>
                                  <span>$ <fmt:formatNumber value="${feature.price.baseRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span> 
                                  <br>
                                  <span>Install:</span> 
                                  <span>$ <fmt:formatNumber value="${feature.price.baseNonRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span>
                               </td>
                            </tr>
                            </c:if>
                            </c:forEach>
                            <c:forEach var="featureGroup" items="${lineItemType.selectedFeatures.featureGroup}">
																<c:forEach var="feature" items="${featureGroup.featureValue}">
																	<c:if test="${empty feature.included}">
                            <tr>
                               <td>  
                               	<c:choose>
								<c:when test="${not empty productFeatureMap[feature.externalId]}">${productFeatureMap[feature.externalId]}</c:when>
								<c:otherwise>${feature.externalId}</c:otherwise>
							</c:choose>
						</td>
                               <td>
                               	<c:choose>
								<c:when test="${feature.value eq 'true'}">&#x2713;</c:when>
								<c:otherwise>${feature.value}</c:otherwise>
							</c:choose>
						 </td>
                               <td>
                                  <span>Monthly:</span>
                                  <span><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${feature.price.baseRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span> 
                                  <br>
                                  <span>Install:</span> 
                                  <span><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${feature.price.baseNonRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span>
                               </td>
                            </tr>
                            </c:if>
                            </c:forEach>
                            </c:forEach>
                  <tr>
                     <td>Monthly Total</td>
                     <td>-</td>
                     <td>
                        <span> <fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${lineItemType.lineItemPriceInfo.baseRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span> 
                        <span>plus taxes/surcharges</span>
                     </td>
                  </tr>
                  <tr>
                     <td >Installation Total</td>
                     <td>-</td>
                     <td>
                        <span><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${lineItemType.lineItemPriceInfo.baseNonRecurringPrice}" maxFractionDigits="2" pattern="0.00" /></span>
                     </td>
                  </tr>
               </tbody>
            </table>
         </div>
         </c:if>
         </div>
         <div class="row">
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
         </div>
         <c:if test="${not empty lineItemType.schedulingInfo.desiredStartDate.date || not empty desireDate2}">
			<c:set var="date" value="${fn:substring(lineItemType.schedulingInfo.desiredStartDate.date, 0, 10)}" />
			<c:set var="startTime" value="${fn:substring(lineItemType.schedulingInfo.desiredStartDate.startTime, 0, 5)}" />
			<c:set var="endTime" value="${fn:substring(lineItemType.schedulingInfo.desiredStartDate.endTime,  0, 5)}" />
			<c:set var="time" value="${fn:substring(lineItemType.schedulingInfo.desiredStartDate.time,  0, 5)}" />
			<c:if test="${empty startTime}">
				<c:set var="startTime" value="${time}" />
			</c:if>
			<div class="reqDates" >
	         <div class=" ">
	            <div class="last_data_title">Requested Installation Dates</div>
	         </div>
	         <div class="row reqdateCont">
	            <div class="last_data-lbl col-md-2 col-sm-2">
	            	Desired Date:
	            </div>
	            <div class="col-md-10 col-md-10">
						<fmt:parseDate var="dateObj" value="${date}" type="DATE" pattern="yyyy-MM-dd" /><fmt:formatDate pattern="EEEE, MMMM d, yyyy" value="${dateObj}" />
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
	         <div class="row reqdateCont">
	            <div class="last_data-lbl col-md-2 col-sm-2">Second Desired Date:</div>
	            <div class="col-md-10 col-md-10"><fmt:parseDate var="dateObj" value="${desireDate2}" type="DATE" pattern="yyyy-MM-dd" /><fmt:formatDate pattern="EEEE, MMMM d, yyyy" value="${dateObj}" />&nbsp;${desireDate2Time}  </div>
	         </div>
	         </c:if>
	         </div>
         </c:if>
         <div class="row">
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
         <div class="row">
            <div class="last_section">
               <p>
                  <span>I confirm I have read all the mandatory disclaimer(s).</span>
                  <span> <input type="checkbox"  name="confirmOrder" id="confirmOrder"  > </span>
               </p>
               <p>  <span class="name_red">MANDATORY</span><c:out escapeXml="false" value="${customerName}" />, do I have your authorization to place this order?
                  <input type="radio" name="authorizeOrder" id="authorizeOrder_Y" value="yes" >Yes
                  <input type="radio" name="authorizeOrder" id="authorizeOrder_N" value="no" >No  
               </p>
               <span id="authorizeOrderMissing" style="display: none; color: red; font-size: 23px;padding-left:545px;" >*</span>
            </div>
         </div>
         <div>
         	<div class="bottombuttons">
         	<div class="row">
	            <div>
	               <input type="button" id="submitLineItem" class="button" value="Submit" />
	            </div>
	         </div>
				<div id="validatorMessage" style="display: none; padding-left: 80px;">
					<img id="astrik" src='<%=request.getContextPath()%>/image/red_asterisk.png' style="height:9px !important; width:9px !important;" /> 
					<label style="color: red; font-size: 12px;font-weight: bold;" id="astrikLbl"> Required Fields</label>
				</div>
			</div>
         </div>
         
          <div id="confirmationDialogueBox" data-backdrop="static" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: relative; top: -10px;">&times;</button>
                     <h4 class="modal-title">Alert</h4> 
                </div>
                <div class="modal-body">
                    <h3>Are you sure you want to place this order?</h3>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary confirmationDialogueOk">OK</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                </div>
            </div>
        </div>
    </div>
    
         <div id="placeOrderLoader" data-backdrop="static" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                
                <div class="modal-body">
                    <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                  <img style="position: relative;right: -158px;" alt="Loading" src="<%=request.getContextPath()%>/image/acdc/loading-gallery.gif" class="img-responsive">
                </div>
              </div>
                </div>
                
            </div>
        </div>
    </div>
        <!--  <div class="confirmationDialogueBox" style="display:none;">
			<p align="center" >Are you sure you want to place this order?</p><br>
			<input type="button" style="margin-left:89px;" class="confirmationDialogueOk" name="Ok" value="OK"/><span></span>
			<input type="button" size="2px" class="confirmationDialogueCancel" name="Cancel" value="CANCEL" />				
		</div> -->
        
      </div>
      </form>
   </body>
</html>