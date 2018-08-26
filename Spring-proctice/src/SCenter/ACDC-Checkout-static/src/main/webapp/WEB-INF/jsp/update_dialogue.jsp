<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link rel="stylesheet"
	href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_att_iframe.css'/>" />
<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/custom_form_elements.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>

<style type="text/css">
iframe {
	border: 0 none !important;
    display: block !important;
    height: 440px !important;
    margin: 50px auto 0 !important;
    width: 100% !important;
}
.CKOContentLoading {
  background: url("/static/image/spinner.gif") no-repeat scroll 50% 50% #FFFFFF;
  margin-top: 430px;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	$('.buttonClass').css('cursor', 'pointer');
});
</script>

</head>
	<c:choose>
		<c:when test="${not empty errorMap}">
			<body>
		</c:when>
		<c:otherwise>
			<body onload="symFeedback();" class="CKOContentLoading">
		</c:otherwise>
	</c:choose>
	
	<input id="iData" name="iData" value='${iData}' type="hidden" />
	
	</body>
</html>