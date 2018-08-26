<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link media="all"
	href="<c:url value='/css/jquery.mCustomScrollbar.css'/>"
	type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/jquery.ui.all.css'/>"
	type="text/css" rel="stylesheet">

<script src="<c:url value='/script/recommendations.js'/>"></script>
<script src="<c:url value='/js/jquery.mCustomScrollbar.js'/>"></script>
</head>
<body>
<jsp:include page="customer_summary_quick.jsp" flush="true"/>

<c:if test="${hasProducts == true}">
<jsp:include page="order_summary_quick.jsp" flush="true"/>
</c:if>

</body>
</html>