<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/css/jquery.mCustomScrollbar.css'/>" type="text/css" rel="stylesheet">

<script src="<c:url value='/js/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/js/jquery.mousewheel.min.js'/>"></script>
<script src="<c:url value='/js/jquery.mCustomScrollbar.js'/>"></script>

<style>

.pageRightColumn{
	width: 250px;
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
}
 
a[id^="add"]
{   
    cursor: pointer; 
}

.itemBlockContent{
	height:170px; 
}
</style>

<script>

function CKO(orderId) {
	
	window.location.href = "<%=request.getContextPath()%>/salescenter/summary/"+orderId;
	  
} 

$(document).ready(function(){
	$("#removeAllItems").click(function(){
		$(".itemCheckBox").attr("checked", false);
	});

	$("#CKOItems").click(function(){
		if($(".itemCheckBox").length == 0){
			alert("Please add item(s) to the cart to CKO");
		} else {
			var len = $(".itemCheckBox:checked").length;
			//if(len == 0){
				//alert("Please select atleast one item");
			//}
		}
	});

	$("input#checkAll").click(function(){
	    if($("input#checkAll:checked").length == 0){
	    	$("input.itemCheckBox").attr("checked", false);
	    } else {
	    	$("input.itemCheckBox").attr("checked", true);
	    }
	});
	
	$("#remove").live('click',function(){
		var len = $(".itemCheckBox:checked").length;
		
		var jsonArr =[];
		if(len == 0){
			alert("Please select item(s) to be removed");
		}else{
			$(".itemCheckBox:checked").each(function() {
				
				var parent_row = $(this).parent();
				
				var span_fright = parent_row.find('span.fRight');
				
		    	
				span_fright.find('a[id^="add"]').css("display","block");
				span_fright.find('span#price').css("display","none");
				
				
				
				var row_prodid = parent_row.find('span#prodId');
				row_prodid.css("color","red");
				
				var lineitem  = parent_row.find('input[id^="hidden"]').attr('id').slice(7);
				//alert('from remove'+lineitem);
			    jsonArr.push(lineitem);
		   });
		}
	
		 var url = "<%=request.getContextPath()%>/salescenter/scart/removeProduct";
		    var data = {};
		    data["orderId"] = ${order.externalId};
		    data["jsonArr"] = jsonArr.toString();
		        
		    $.ajax({
		    	type: 'POST',
		    	url: url,
		    	data: data,
		    	complete: onComplete
		    });
		    var onComplete = function(data){
		    };
	});
	$('a[id^="add"]').live('click',function(){

		$(this).css("display","none");
		
		var add_id = $(this).attr('id');

		var span_row = $(this).parent();
		var price_row = span_row.find('span#price');
		price_row.css("display","block");

		var h4 = span_row.parent();
		var row_prodid = h4.find('span#prodId')
		row_prodid.css("color","black");

		var lineitem  = h4.find('input[id^="hidden"]').attr('id').slice(7);
		//alert('from add'+lineitem);
		

		var url = "<%=request.getContextPath()%>/salescenter/scart/addStatusChange";
	    var data = {};
	    data["orderId"] = ${order.externalId};
	    data["lineItemId"] = lineitem;
	        
	    $.ajax({
	    	type: 'POST',
	    	url: url,
	    	data: data,
	    	complete: onComplete
	    });
	    var onComplete = function(data){
	    };

		
	});
});
</script>
</head>
  
	<form id="summaryForm" action="" method="post">
		<div style="margin-top: 20px;margin-left: 30px;" id="pageBody">

			<div class="pageRightColumn">
				<div id="myProfileInCKO" class="sidebarBox">
					 
					<div class="content">
						<div id="custBlock">
							<h3 class="systemColor2">Customer Information</h3>
							<div class="divider"><!-- --></div>
							<ul class="genericList">
							 	<li>Customer Number: ${order.customerInformation.customer.externalId}</li>	
								<li>${order.customerInformation.customer.firstName}&nbsp;${order.customerInformation.customer.lastName}</li>
								<li>${salescontext.scMap['dwelling.addressBlock']}</li>
								
								
								<c:if test="!empty address.address.addressOwnership" >
								<li>House:Own</li>
								</c:if>
								  
							</ul>
							<div class="divider"><!-- --></div>
						</div>
						
						<div id="orderBlock">
							<h3 class="systemColor2">Customer Order</h3>
							
							<div class="divider"><!-- --></div>
							<ul class="genericList">
								<li>Order Number: ${order.externalId}</li>
								<li> Order Status: ${order.orderStatus.status}  </li> 	
							</ul>
							<div class="divider"><!-- --></div>
						</div>
						
						<h3 class="systemColor2"><input type="checkbox" id="checkAll">Items</h3>
						<div class="divider"><!-- --></div>
							
						<div id="itemsBlock" class="itemBlockContent">
							<input type="hidden" id="liCount" value="${fn:length(order.lineItems.lineItem)}">
							<input type="hidden" id="priceUnits" value="USD">
							<c:set var="totalPrice" value="0" />
							
							<c:forEach var="lineitem" items="${order.lineItems.lineItem}">
								
								<c:set var="partnerExternalId" value="${lineitem.partnerExternalId}"/>
								<c:set var="productUniqueId" value="${lineitem.lineItemDetail.productUniqueId}"/>
								<c:set var="productExernalId" value="${lineitem.lineItemDetail.detail.productLineItem.externalId}"/>
								<c:set var="price" value="${lineitem.lineItemPriceInfo.baseNonRecurringPrice}"/>
								<c:set var="totalPrice" value="${totalPrice + price}" />
								
								<h4 class="systemColor2" id="row_${partnerExternalId}_${productUniqueId}">
									<input type="checkbox" class="itemCheckBox" id="item_${partnerExternalId}_${productUniqueId}"/><span>${productExernalId}</span> <span class="fRight">${price}</span>
								</h4>
								<div class="divider"><!-- --></div>
							</c:forEach>
						</div>
						
					 	<h3 class="systemColor2" id="subtotal">
					 		<input type="hidden" id="totalPrice" value="${totalPrice}">
							SubTotal <span id="itemsTotal" class="fRight subtotal">${totalPrice} USD</span>
						</h3>
						
						<div class="divider"><!-- --></div>
						
						<input id="CKOItems" onclick="CKO('${order.externalId}');" class="quickSumBtn" type="button" value="CKO"  /> 
						<input class="quickSumBtn" id="remove" type="button" value="Remove" /> 
						<input id="removeAllItems" class="quickSumBtn" type="button" value="Clear All" /> 
						 
					</div>
				</div>
				 
				<div class="clr"><!-- --></div>
			</div>
		</div>

	</form>

	<script>
		(function($){
			$(window).load(function(){
				$("#itemsBlock").mCustomScrollbar();
			});
		})(jQuery);
	</script>