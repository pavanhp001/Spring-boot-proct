<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<%-- <link rel="stylesheet" href="<c:url value='/style/CKO_att_iframe.css'/>" /> --%>
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.all.css'/>" />

<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/custom_form_elements.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>


<style type="text/css">

			/* #wrapper{background-color: #fff;	color: #333;font-family: Arial,Helvetica,sans-serif;margin: 0 auto;padding: 0;width: 100%;}
			#wrapper, #wrapper > #Container, #Container > #content, #header {
				font-size: 100%;margin: 0 !important;max-width: 100%;overflow: visible;padding: 0;width: auto !important;
			}
			#wrapper #Container{width: 700px !important;max-width: 700px !important;margin: 0 auto !important;overflow: hidden;} */
			.error_page_cont{color: #333;font-family: Arial,Helvetica,sans-serif;display: table;font-size: 32px;font-weight: bold;margin: 0px auto;padding: 0;text-align: center;width: 700px !important;max-width: 700px !important;}
			.error_page_cont .error_page_blk{border: 1px solid #ccc;color: #333;display: block;font-size: 13px;font-weight: normal;margin-top: 30px;padding: 23px;}
			.error_page_blk .product_item_dtls{width: 100%;display: inline-block;border: 1px solid #c50000;padding: 0;}
			.error_page_blk .product_item_dtls .product_logo{display: table-cell;height: 40px;margin: 0;padding: 10px 0 10px 5px;vertical-align: middle;width: 80px;}
			.error_page_blk .product_item_dtls .product_logo img{max-width:100%;max-height:100%;}
			.error_page_blk .product_item_dtls .product_desc{display: table-cell;font-weight: bold;margin: 0 5px 0 0;padding: 10px 5px;text-align: left;vertical-align: middle;width: 70%;line-height: normal;}
			.error_page_blk .product_item_dtls .product_price{display: table-cell;margin: 0;padding: 10px 10px 10px 0;text-align: right;vertical-align: middle;width: 200px;}
			.error_page_blk .error_message{font-size: 15px;font-weight:400;text-align:left;line-height:2;margin-top:25px;}
			.error_page_blk .call_us_toll_free{font-size: 20px;font-weight:bold;margin:25px 0;}
			.error_page_blk .call_us_toll_free span{font-size: 36px;}
			a {background: transparent none repeat scroll 0 0;font-size: 43%;margin: 0;padding: 0;vertical-align: baseline;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	hidePlanSummeryOnErrorPage('error');
	$('.buttonClass').css('cursor', 'pointer');
	var errorMessage = '${errorMessage}';
	var redirectionType = '${redirectionType}';
	if((errorMessage != undefined && errorMessage.length > 0) && (redirectionType != undefined && redirectionType.length > 0 && redirectionType === "redirect_to_home")) {
		redirectToParentApp(redirectionType);
	} else if (redirectionType != undefined && redirectionType.length > 0 && redirectionType === "redirect_to_exception_page") {
		$("#expCustomMsg").show();
	}
	var isContainsPurOrder = '${isContainsPurOrder}';
	if (isContainsPurOrder) {
		$("#completedOrdersLink").show();	
	}
	displayCKOLoader(true);
});
</script>
</head>
<body>
	<input id="iData" name="iData" value='${iData}' type="hidden" />

		<div class="error_page_cont" id="expCustomMsg">
				We're sorry, something went wrong...
			<div class="error_page_blk">
				<div class="product_item_dtls">
				    <div class="product_logo">
				        <img src="<%=request.getContextPath()%>/image/${providerExternalID}.jpg" >
				    </div>
				    <div class="product_desc">${product_name}</div>
				    <c:if test="${empty product_base_recurring_price}">
	   					<div class="product_price">&nbsp;&nbsp;&#x24;0.00</div>
					</c:if>
				    <c:if test="${not empty product_base_recurring_price}">
	   					<div class="product_price">&nbsp;&nbsp;&#x24;${product_base_recurring_price}</div>
					</c:if>
				</div>
				<div class="error_message">
		            Please give us a call and speak to one of our agents to help you with the ${provider_name} you are trying to order.
		        </div>
                <div class="call_us_toll_free">Call us Toll Free: <span>${phoneNo}</span></p>
	        </div>
    	</div>
		<div id="completedOrdersLink" style="float: left; margin-top: 20px;display: none;"><a onclick="redirectToParentApp('cartSummaryPage')" href="#">View completed orders</a></div>
		<div style="float: right; margin-top: 20px;"><a onclick="redirectToParentApp('productResultsPage')" href="#">Back to Shopping</a></div>
	</div>
</body>
</html>