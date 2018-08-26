<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/css/jquery.mCustomScrollbar.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/utils.css'/>" type="text/css" rel="stylesheet">
<style>

.pageRightColumn{
	width: 250px;
  	margin-top: 10px;
}

.itemCheckBox, #checkAll{
    margin-right: 3px;
    vertical-align: text-top;
}

.quickSumBtn{
	line-height: 30px;
    width: 30%;
}

.fRight{
	float:right;
	margin-top: 2px;
}
 
a[id^="add"]
{   
    cursor: pointer; 
}

.itemBlockContent{
	height:170px; 
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
  padding-left: 15px;
}
#pageBody {
    float: left;
    height: 100%;
    margin: 5px 0 0;
    padding: 0;
    width: 99.9%;
}
</style>

<script>

$(document).ready(function(){
	
	$("#removeAllItems").click(function(){
		$(".itemCheckBox").attr("checked", false);
	});

	$("#CKOItems").click(function(){
		if($(".itemCheckBox").length == 0){
			alert("Please add item(s) to the cart to CKO");
			return false;
		} else {
			$("form#summaryForm").submit();
			var len = $(".itemCheckBox:checked").length;
			//if(len == 0){
				//alert("Please select atleast one item");
			//}
		}
	});
	$(".statusToggle").click(toggleStatus);
});

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
</script>
</head>
<body>
  
	<form id="summaryForm" action="<%=request.getContextPath()%>/rest/summary/${order.externalId}" method="post">
	<input type="hidden" id="pageTitle" name="pageTitle" value="Recommendations">
		<div style="margin-top: 20px;margin-left: 30px;" id="pageBody">
			<input type="hidden" name="addressId" id="addressId" value="${addressId}" />
			
			<div class="pageRightColumn">
				<div id="myProfileInCKO" class="sidebarBox">
					 
					<div class="content">
						 
						
						<div id="orderBlock">
							<h3 class="systemColor2"><fmt:message key="orderQuick.orderInfo"/></h3>
							
							<div class="divider"><!-- --></div>
						</div>
						
						<h3 class="systemColor2"><fmt:message key="orderQuick.items"/></h3>
						<div class="divider"><!-- --></div>
							
						<div id="itemsBlock" class="itemBlockContent">
							<input type="hidden" id="liCount" value="${fn:length(order.lineItems.lineItem)}">
							<input type="hidden" id="priceUnits" value="USD">
							<c:set var="totalPrice" value="0" />
							
							<c:forEach var="lineitem" items="${order.lineItems.lineItem}">
								
								<c:set var="displayLi" value='true'/>
									<c:forEach var="entity" items="${lineitem.lineItemAttributes.entity}">
										<c:if test="${entity.source == 'provider_feedback'}" >
											<c:forEach var="attribute" items="${entity.attribute}">
												<c:if test="${attribute.name == 'Display' && attribute.value == 'false'}" >
													<c:set var="displayLi" value='false'/>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
									
								<c:if test="${displayLi == 'true'}" >
								<c:set var="partnerExternalId" value="${lineitem.partnerExternalId}"/>
								<c:set var="productUniqueId" value="${lineitem.lineItemDetail.productUniqueId}"/>
								<c:set var="productExernalId" value="${lineitem.lineItemDetail.detail.productLineItem.externalId}"/>
								<c:set var="productName" value="${lineitem.lineItemDetail.detail.productLineItem.name}"/>
								<c:set var="price" value="${lineitem.lineItemPriceInfo.baseNonRecurringPrice}"/>
								<c:set var="totalPrice" value="${totalPrice + price}" />
								
								<h4 class="systemColor2" id="row_${partnerExternalId}_${productUniqueId}">
								<div style="display: block;">
								<c:if test="${title == 'Recommendations' || title == 'title.addProducts'}">
									<input type="checkbox" class="itemCheckBox" id="item_${partnerExternalId}_${productUniqueId}"/>
									</c:if>
									<span id="prodId">${productExernalId}</span>
									 <span class="fRight"><span id="price">${price}</span>
									 <span class="statusToggle"></span>
									 </span>
									 <div class="liStatus" style="display: none;">${lineitem.lineItemStatus.statusCode}</div>
								</div>
								
								<input id="li_externalId_${lineitem.externalId}" type="hidden" value="${lineitem.externalId}">
								
								</h4>
								<div class="divider"><!-- --></div>
								</c:if>
							</c:forEach>
						</div>
						
					 	<h3 class="systemColor2" id="subtotal">
					 		<input type="hidden" id="totalPrice" value="${totalPrice}">
							<fmt:message key="orderQuick.total.monthly"/> <span id="itemsTotal" class="fRight subtotal">${totalPrice} <fmt:message key="orderQuick.unit"/></span>
						</h3>
						<h3 class="systemColor2" id="subtotal">
					 		<input type="hidden" id="totalPrice" value="${codPrice}">
							<fmt:message key="orderQuick.total.cod"/> <span id="itemsTotal" class="fRight subtotal">${totalPrice} <fmt:message key="orderQuick.unit"/></span>
						</h3>
						<h3 class="systemColor2" id="subtotal">
					 		<input type="hidden" id="installPrice" value="${installPrice}">
							<fmt:message key="orderQuick.total.install.fee"/> <span id="itemsTotal" class="fRight subtotal">${installPrice} <fmt:message key="orderQuick.unit"/></span>
						</h3>
						
 

						
						<div class="divider"><!-- --></div>
 							<c:if test="${title != 'title.orderSummary' && title != 'title.orderConclusion' && title != 'title.orderRecap'}">
 								<div style="text-align:center;">
									<input id="CKOItems" class="quickSumBtn" type="button" value="<fmt:message key="orderQuick.CKO.button"/>"  /> 
									<input class="quickSumBtn" id="remove" type="button" value="<fmt:message key="orderQuick.remove.button" /> "/>
								</div>
 							</c:if>
					</div>
				</div>
				 
				<div class="clr"><!-- --></div>
			</div>
		</div>

	</form>

<script src="<c:url value='/js/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/js/jquery.mousewheel.min.js'/>"></script>
<script src="<c:url value='/js/jquery.mCustomScrollbar.js'/>"></script>

<script>
	(function($){
		$(window).load(function(){
			$("#itemsBlock").mCustomScrollbar();
		});
	})(jQuery);
</script>
</body>
</html>