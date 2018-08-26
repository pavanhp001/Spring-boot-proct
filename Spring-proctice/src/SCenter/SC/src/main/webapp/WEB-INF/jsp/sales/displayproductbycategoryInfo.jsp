<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link rel="stylesheet"
	href="<c:url value='/style/cart/default/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/cart/default/CKO_base.css'/>" />
<%-- <script src="<c:url value='/script/feedback.js'/>"></script> --%>

<style type="text/css">
.address {
	width:350px;
	height: 150px;
	border:2px solid #a1a1a1;
	border-radius:5px;
	padding:8px;
	display: block;
}

</style>
<html>
<head>
<title>Products By Category</title>
</head>
<body>

	<form onload="feedbackFuncExec('product_info');"
		action="<%=request.getContextPath()%>/salescenter/displayBasicInfo"
		method="post">
		<div>
			<!--<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/bundles">bundles</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/cableTV">cableTV</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/dialUpInternet">dialUpInternet</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/electricity">electricity</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/highSpeedInternet">highSpeedInternet</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/homeSecurity">homeSecurity</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/localNewspaper">localNewspaper</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/localPhone">localPhone</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/longDistancePhone">longDistancePhone</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/naturalGas">naturalGas</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/offers">offers</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/satelliteTV">satelliteTV</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/wasteRemoval">wasteRemoval</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/water">water</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/wirelessPhone">wirelessPhone</a>
			<a href="<%=request.getContextPath()%>/salescenter/displayproductbycategoryInfo/powerpitch">powerpitch</a>
		--></div>
		<c:forEach items="${details}" var="product" >
			<div class="address">
				<div>
					<b>Product name :</b>
					<c:out escapeXml="false" value="${product.name}" />
				</div>
				<div>
					<b>External Id :</b>
					<c:out value="${product.externalId}" />
				</div>
				<div>
					<b>Provider name :</b>
					<c:out escapeXml="false" value="${product.provierName}" />
				</div>
				<div>
					<b>Monthly Cost:</b>
					<fmt:formatNumber type="currency"
						value="${product.baseRecurringPrice}" />
				</div>
				<div>
					<b>Installation Cost:</b>
					<fmt:formatNumber type="currency"
						value="${product.baseNonRecurringPrice}" />
				</div>
				<div>
					<b>Score:</b>
					<fmt:formatNumber 
						value="${product.score}" />
				</div>
			</div>
		</c:forEach>
       <input type="submit" value="Next" />
	</form>
</body>
</html>
