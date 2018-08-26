<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link rel="stylesheet"
	href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_base.css'/>" />
<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/custom_form_elements.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>
<script type="text/javascript">
$(document).ready(function(){
Custom.init();
});
</script>

 
	<!--<c:out escapeXml="false" value="${events}" />-->
 

</head>
<body onload="symFeedback()">


<input id="iData" name="iData" value='${iData}' type="hidden" />


	<div style="width: 598px; height: 438px; margin: 0 auto;">


		<form action="<%=request.getContextPath()%>/att/save" method="post">
			<c:out escapeXml="false" value="${data}" />

			<input type="submit" value="Next" />

		</form>



	</div>

</body>
</html>