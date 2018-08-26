<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Add LineItem</title>

<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/cart/default/cust_screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/cart/default/lineItem.css'/>" type="text/css" rel="stylesheet">

<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/script/lineItem.js'/>"></script>

<style>
#pageContent {
	margin-left: -5px;
	margin-right : -45px;
	padding: 10px
	margin-left: 0;
    width: 700px !important;
}
.rounded {
    width: 665px;
}

.rlabel {
	width: 120px;
}

.addProdsHead {
    font-family: verdana,arial,helvetica,sans-serif !important;
    font-size: 14px ;
    padding: 4px !important;
    vertical-align: sub;
    padding-left: 5px;
    font-weight: bold;
}

.productList {
    height: 420px;
    margin: 5px 0 0;
    overflow-y: scroll;
    padding: 0;
}

.alignCenter {
	text-align: center;
}

</style>

</head>
<body> 
	<form action="<%=request.getContextPath()%>/rest/summary/${order.externalId}" method="post">
		<input type="hidden" name="orderId" id="orderId" value="${order.externalId}" />
		<input type="hidden" name="billingInfoExtId" id="billingInfoExtId" value="${billingInfoExtId}" />
		<input type="hidden" name="svcAddressExtId" id="svcAddressExtId" value="${svcAddressExtId}" />
		<input type="hidden" id="pageTitle" name="pageTitle" value="Recommendations">
		<div style="height: 465px; padding-left: 5px;">
			<div class="pageBox" id="prodListBlock" style="width: 620px; padding-bottom: 5px;">
				<span class="addProdsHead"><fmt:message key="lineItem.addProducts"/></span>
				<div class="productList">
					<c:forEach var="prod" items="${productItems}">
						<div class="prodBlock" id="prod_${prod.partnerExternalId}_${prod.productUniqueId}">
							<input name="prodJson" type="hidden" value='${prod.prodJson}'/>
							<input id="price_${prod.partnerExternalId}_${prod.productUniqueId}" type="hidden" value="${prod.price}"/>
									
							<img src="${providersImageLocation}${prod.partnerExternalId}.jpg" class="imgDisp"></img>
					        <div class="prodDetails">
					        	<span id="productDetails"><fmt:message key="lineItem.providerName"/> : </span><span class="prodRlabel"><c:out value="${prod.providerName}"  /></span>
						        <span id="productDetails" class="cBoth"><fmt:message key="lineItem.productName"/> : </span><span class="prodRlabel"><c:out value="${prod.productExernalId}" /></span>
						    </div>
					        <input type="button" name="input_${prod.partnerExternalId}_${prod.productUniqueId}" id="input_${prod.partnerExternalId}_${prod.productUniqueId}" class="addToCart" value="<fmt:message key="lineItem.addToCart.button"/>"/>
					    </div>
				 	</c:forEach>
				 </div>
				</div> 
			</div>
	</form>
</body>
</html>