<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>Products</title>

<link rel="stylesheet"	href="<c:url value='/style/cart/default/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/cart/default/CKO_base.css'/>" />

<%-- <script src="<c:url value='/script/feedback.js'/>"></script> --%>

<style type="text/css">
.address {
	width:300px;
	height: 110px;
	border:2px solid #a1a1a1;
	border-radius:5px;
	padding:8px;
	display: block;
}
</style>

</head>
<body>

	<form onload="feedbackFuncExec('product_info');"
		action="<%=request.getContextPath()%>/att/product_info/next"
		method="post">

		<c:forEach items="${details}" var="product">
			<div class="address">
				<div>
					<b>Product name :</b>
					<c:out escapeXml="false" value="${product.name}" />
				</div>
				<div>
					<b>Install Cost:</b>
					<fmt:formatNumber type="currency"
						value="${product.baseNonRecurringPrice}" />
				</div>
				<div>
					<b>Monthly Cost:</b>
					<fmt:formatNumber type="currency"
						value="${product.baseRecurringPrice}" />
				</div>
			</div>
		</c:forEach>
       <input type="submit" value="Next" />
	</form>
</body>
</html>
