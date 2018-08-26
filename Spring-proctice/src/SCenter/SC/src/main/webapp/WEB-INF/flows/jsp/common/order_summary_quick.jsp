<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link media="all" href="<c:url value='/css/css_new/shoppingcart.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/css/jquery.mCustomScrollbar.css'/>" type="text/css" rel="stylesheet">
<script src="<c:url value='/js/jquery.mCustomScrollbar.js'/>"></script>
<script src="<c:url value='/js/jquery.mousewheel.min.js'/>"></script>
<style>

.pageRightColumn{
	width: 250px;
  	margin-top: 10px;
}

.itemCheckBox, #checkAll{
    margin-right: 3px;
    vertical-align: text-top;
    float: left;
}

.quickSumBtn{
	line-height: 30px;
    width: 30%;
}

.fRight{
	float:right;
	margin-top: 2px;
}

.subfRight{
	float: right;
    
}
h3.systemColor2 {
  color:#323232;
}
a[id^="add"]
{   
    cursor: pointer; 
}

.itemBlockContent{
	height:230px; 
	font-size:12px;
	padding-top: 5px;
}
h4{
	margin:0;
}

.addAgain {
    background-color: #F1F1F1;
    border: 1px solid #6F6F6F;
    border-radius: 4px;
    cursor: pointer;
    font-size: 11px;
    padding: 1px 4px;
}

#custBlock .genericList li, #orderBlock .genericList li {
	padding: 2px 0;
}

.liStatus {
  	clear: both;
  	padding-left: 15px;
}

#orderInfoHead:hover {
    color: #6CA741;
    text-decoration: underline;
    cursor: pointer;
}

#prodId {
  	float: left;
  	width: 145px;
}
.mCSB_container > h4 > div {
  display: table;
  line-height: 20px;
  width: 100%;
}
.mCSB_container .divider {
  	margin: 5px 0px !important;
}
.negativePrice{
	color: green;
	font-size: 12px;
	font-weight: bold;
}
#totalPointsId {
	float: right;
	color: #000;
}
</style>

<script>

$(document).ready(function(){
	var totalPt = "${totalPoints}";
	$("#removeAllItems").click(function(){
		$(".itemCheckBox").attr("checked", false);
	});
	$("#CKOItems").click(CKOToOrderSummary);
	$(".statusToggle").click(toggleStatus);
	
	if( $("h4[id^='row_']").length > 0 ){
		$("div#orderInfoHead").unbind("click");
		$("div#orderInfoHead").click(goToOrderSummary);
	}
	else{
		$("div#orderInfoHead").click(function(){
			var headTitle = $('title').text();
			console.log(headTitle);
			if(headTitle == 'Recommendations' || headTitle == 'Add LineItem'){
				alert("Add item(s) to the Shopping Cart.");
			}
			else{
				goToOrderSummary();	
			}
		});	
	}
	$("span.addAgain").click(reAdd);
	var points = $("#totalPoints").val();
	$("#totalPointsId").text(points);
});

var CKOToOrderSummary = function(){
	if($(".itemCheckBox").length == 0){
		alert("Add item(s) to the Shopping Cart.");
		return false;
	}else {
		var count = 0;
		$(".itemCheckBox").each(function(){
			var hiddenId = this.id;
			var h4Id = hiddenId.replace("item","row");
			if(document.getElementById(h4Id)!=null){
				var htmlText = document.getElementById(h4Id).innerHTML;
				if(htmlText.indexOf("Services Down") != -1 ){
					count = count+1;
				}
			}
		});

		if(count==$(".itemCheckBox").length){
			alert("Add item(s) to the Shopping Cart.");
			return false;
		}else{
			goToOrderSummary();	
		}
	}
}

var toggleStatus = function(){
	var h4 = $(this).closest("h4");
	var div = h4.find("div.liStatus");
	if(div.is(":hidden")){
		$(this).addClass("minus");
    } else {
    	$(this).removeClass("minus");
    }
	div.slideToggle();
}

/*
function CKO(liId, provId,lineItemProviderName,prodSrc) {

		lineItemIds = [];
		providerIds = [];
		productSrcs = [];
		providerNames = [];
		
		lineItemIds.push(liId);
		providerIds.push(provId);
		productSrcs.push(prodSrc);
		providerNames.push(lineItemProviderName);
		$("input#lineItemIds").val(lineItemIds);
		$("input#providerIds").val(providerIds);
		$("input#productSrcs").val(productSrcs);
		$("input#providerNames").val(providerNames);
		var custId = $("#customerId").val();
		var orderId = $("#orderId").val();
		var path = $("#contextPath").val();
		var url = path+"/rest/CKO/"+ custId + "/order/" + orderId;
		//$("form[name='ordersummary']").attr("action",flowpath+"&_eventId=CKO");
		$("#summaryForm input[id='_eventId']").val("CKO");
		goToOrderSummary();

}
*/

function goToOrderSummary(){
	try{
		$("form#summaryForm").submit();	
	}catch(err){alert(err);}
}


</script>
</head>
<body>

					<form id="summaryForm" action="${flowExecutionUrl}" method="post">
					<input type="hidden" name="addressId" id="addressId" value="${addressId}" />
			 		<input type="hidden" name="orderId"  id="orderId" value="${order.externalId}" /> 
			 		<input type="hidden"  name="title" id="title" value="${title}" /> 
			 		<input type="hidden" id="pageTitle" name="pageTitle" value="Recommendations">
			 		<input type="hidden" id="_eventId" name="_eventId" value="orderSummaryViewEvent">
			 		<input type="hidden" name="lineItemIds"  id="lineItemIds" value="" /> 
			 		<input type="hidden" name="providerIds"  id="providerIds" value="" /> 
			 		<input type="hidden" name="productSrcs"  id="productSrcs" value="" /> 
			 		<input type="hidden" name="providerNames"  id="providerNames" value="" /> 
			 		<input type="hidden" name="totalPoints"  id="totalPoints" value="Points: ${totalPoints}" />
					<!-- Widget Shopping Cart -->
					<div class="cart_widget">
						<div class="cart_container">
						
						
						<div id="orderBlock">
							<c:choose>
					        <c:when test="${title == 'title.orderSummary'}">
					         <div class="title"><fmt:message key="orderQuick.orderInfo"/>
					        </c:when>
					        <c:otherwise>
					         <div id="orderInfoHead" class="title"><fmt:message key="orderQuick.orderInfo"/>
					        </c:otherwise>
					       </c:choose>		
					        <div id="totalPointsId" class="totalPoints"></div></div>				
							<div class="divider"><!-- --></div>							
						</div>
						
						
							<!-- <div class="title">Shopping Cart</div>  -->
						
						
						<div id="itemsBlock" class="itemBlockContent">
							<div style="position:relative; height:100%; overflow:auto; max-width:100%;" id="mCSB_1" class="mCustomScrollBox mCS-dark-2">
								<div style="position:relative; top:0;" class="mCSB_container mCS_no_scrollbar">
									<input type="hidden" id="liCount" value="${fn:length(order.lineItems.lineItem)}">
									<input type="hidden" id="priceUnits" value="USD">
									<c:set var="totalPrice" value="0" />
									<c:set var="installPrice" value="0" />
									<c:set var="pvbm" value="false"/>
									<c:set var="atLeastOneComCastPromo" value="false"/>
									<c:choose>
											<c:when test="${empty lineItemList}">
												<c:set var="lineItems" value="${order.lineItems.lineItem}" />
											</c:when>
											<c:otherwise>
												<c:set var="lineItems" value="${lineItemList}" />	
											</c:otherwise>
											</c:choose>
									<c:forEach var="lineitem" items="${lineItems}">
									<c:set var="detailType" value="${lineitem.lineItemDetail.detailType}"/>
									<c:set var="liStatus"	value="${lineitem.lineItemStatus.statusCode}" />
										<c:set var="displayLi" value='true'/>
											<c:forEach var="entity" items="${lineitem.lineItemAttributes.entity}">
												<c:if test="${entity.source == 'provider_feedback'}" >
													<c:forEach var="attribute" items="${entity.attribute}">
														<c:if test="${attribute.name == 'Display' && attribute.value == 'false'}" >
															<c:set var="displayLi" value='false'/>
														</c:if>
													</c:forEach>
												</c:if>
												<c:if test="${entity.source == 'PRODUCT_NAME'}" >
													<c:forEach var="attribute" items="${entity.attribute}">
														<c:if test="${attribute.name == 'name'}" >
														<c:set var="lineItemName" value='${attribute.value}'/>
														</c:if>
													</c:forEach>
												</c:if>
												<c:if test="${entity.source == 'PROVIDER_NAME'}" >
													<c:forEach var="attribute" items="${entity.attribute}">
														<c:if test="${attribute.name == 'name'}" >
														<c:set var="lineItemProviderName" value='${attribute.value}'/>
														</c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
											<c:if test="${detailType == 'productPromotion' }" >
												<c:set var="displayLi" value='false'/>
											</c:if>
										<c:if test="${displayLi == 'true'}" >
										<c:set var="partnerExternalId" value="${lineitem.partnerExternalId}"/>
										<c:set var="productUniqueId" value="${lineitem.lineItemDetail.productUniqueId}"/>
										<c:set var="productExernalId" value="${lineitem.lineItemDetail.detail.productLineItem.externalId}"/>
										<c:set var="productName" value="${lineitem.lineItemDetail.detail.productLineItem.name}"/>

										
										<c:if test="${liStatus != 'CANCELLED_REMOVED'}">
											<c:set var="price" value="${lineitem.lineItemPriceInfo.baseRecurringPrice}"/>
											<c:set var="recPrice" value="${lineitem.lineItemPriceInfo.baseNonRecurringPrice}"/>
											<c:if test="${recPrice == -1.00 || recPrice == -2.00}" >
												<c:if test="${installPrice == 0}" >
													<c:set var="pvbm" value="true"/>
												</c:if>
												<c:set var="recPrice" value="0"/>
												<c:set var="atLeastOneComCastPromo" value="true"/>
											</c:if>
											<c:set var="totalPrice" value="${totalPrice + price}" />
											<c:set var="installPrice" value="${installPrice + recPrice}" />
											
											<h4 
											class="systemColor2"
											id="row_${partnerExternalId}_${productExernalId}">
												<div >
												<c:choose>
													<c:when test="${title == 'title.orderSummary' || title == 'title.orderConclusion' || title == 'title.orderRecap' || title == 'CKO.title'}">
													&nbsp;
													</c:when>
													<c:otherwise>
														<c:if test="${liStatus != 'PROVISION_READY' && liStatus != 'CANCELLED_REMOVED'}">													
														<input type="checkbox" class="itemCheckBox" id="item_${partnerExternalId}_${productUniqueId}"/>
														</c:if>
														<c:if test="${liStatus == 'PROVISION_READY' || liStatus == 'CANCELLED_REMOVED'}">													
														<input type="checkbox" class="itemCheckBox" id="item_${partnerExternalId}_${productUniqueId}" disabled="disabled"/>
														</c:if>
													</c:otherwise>
												</c:choose>
													
													<span id="prodId" >
															<c:if test="${not empty productName}">
																 ${productName} 
															</c:if>
															<c:if test="${empty productName}">
																${lineItemName} 
															</c:if>
													</span>
													<span class="fRight">
														<span id="price"><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${price}" maxFractionDigits="2" pattern="0.00"/></span>
													 	<span id="instPrice" style="display : none;">${lineitem.lineItemPriceInfo.baseNonRecurringPrice}</span>
													 	<span id="monPrice" style="display : none;">${price}</span>
													 	<span class="statusToggle"></span>
													 </span>
													 <div class="liStatus" style="display: none;">${lineitem.lineItemStatus.statusCode}</div>
												</div>
												<input id="providerId" type="hidden" value="${partnerExternalId}">
												<input id="li_externalId_${lineitem.externalId}" type="hidden" value="${lineitem.externalId}">
												<input id="li_number_${lineitem.externalId}" type="hidden" value="${lineitem.lineItemNumber}">
												<input id="li_hasPromotion_${lineitem.externalId}" type="hidden" value="${promoMap[lineitem.lineItemNumber]}">
											</h4>
										</c:if>
										
										<c:if test="${liStatus == 'CANCELLED_REMOVED'}">
											<h4 class="systemColor2 removedH4" id="row_${partnerExternalId}_${productExernalId}">
												<div >
													<c:if test="${title != 'title.orderSummary' && title != 'title.orderConclusion' && title != 'title.orderRecap'}">
														<input style="display: none;" type="checkbox" class="itemCheckBox" id="item_${partnerExternalId}_${productUniqueId}" disabled="disabled"/>
													</c:if>
													<span id="prodId" style="color: rgb(255, 0, 0);" >
															<c:if test="${not empty productName}">
																${productName}
															</c:if>
															<c:if test="${empty productName}">
																${lineItemName}
															</c:if>
													</span>
													 <span class="fRight">
														 <c:if test="${title != 'title.orderSummary' && title != 'title.orderConclusion' && title != 'title.orderRecap' && title != 'CKO.title'}">
															 <span id="price" class="addAgain">Add</span>
														</c:if>
														 <c:if test="${title == 'title.orderSummary' && closeCall == false}">
															 <span id="price" class="addAgain">Add</span>
														</c:if>
														 <span id="instPrice" style="display : none;">${lineitem.lineItemPriceInfo.baseNonRecurringPrice}</span>
														 <span id="monPrice" style="display : none;">${lineitem.lineItemPriceInfo.baseRecurringPrice}</span>
														 <span class="statusToggle"></span>
													 </span>
													 <div class="liStatus" style="display: none;">${lineitem.lineItemStatus.statusCode}</div>
												</div>
												<input id="providerId" type="hidden" value="${partnerExternalId}">
												<input id="li_externalId_${lineitem.externalId}" type="hidden" value="${lineitem.externalId}">
												<input id="li_number_${lineitem.externalId}" type="hidden" value="${lineitem.lineItemNumber}">
												<input id="li_hasPromotion_${lineitem.externalId}" type="hidden" value="${promoMap[lineitem.lineItemNumber]}">
											</h4>
										</c:if>
										<div class="divider"><!-- --></div>
										</c:if>
										
									</c:forEach>
								</div>
								<div style="position: absolute; display: none;" class="mCSB_scrollTools">
									<div class="mCSB_draggerContainer">
										<div oncontextmenu="return false;" style="position: absolute; top: 0px;" class="mCSB_dragger">
											<div style="position:relative;" class="mCSB_dragger_bar"></div>
										</div>
										<div class="mCSB_draggerRail">
										</div>
									</div>
								</div>
							</div>
							
							
						</div>
						
						<table cellpadding="0" cellspacing="0" class="bottom">
							<tr>
								<td width="50%"><fmt:message key="orderQuick.total.monthly"/></td>
								<td><input type="hidden" id="totalPrice" value="${totalPrice}"><span id="itemsTotal" class="subfRight subtotal"><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${totalPrice}" maxFractionDigits="2" pattern="0.00"/></span></td>
							</tr>
							<tr>
								<td width="50%"><fmt:message key="orderQuick.total.install.fee"/></td>
								<td><input type="hidden" id="installPrice" value="${installPrice}">
								<input type="hidden" id="atLeastOneComCastPromo" value="${atLeastOneComCastPromo}">
									<span id="itemsTotal1" class="subfRight subtotal">
										<c:if test="${pvbm eq 'true'}">
										<span class="negativePrice">Varies By Market</span>
										</c:if>
										<c:if test="${pvbm eq 'false'}">
										<fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${installPrice}" maxFractionDigits="2" pattern="0.00"/>
										</c:if>
									</span>
								</td>
							</tr>
						</table>
							<div class="buttons center">
								<c:if test="${title != 'title.orderSummary' && title != 'title.orderConclusion' && title != 'title.orderRecap' && title != 'CKO.title'}">
									<input type="button" id="CKOItems" value="<fmt:message key="orderQuick.CKO.button"/>" />
									<input type="button" id="remove" value="<fmt:message key="orderQuick.remove.button" />" />
	 							</c:if>
							</div>
						</div>
					</div>
				</form>


</body>
</html>