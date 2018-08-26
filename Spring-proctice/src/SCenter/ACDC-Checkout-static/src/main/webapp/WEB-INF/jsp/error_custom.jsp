<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>
</head>
<body onload="symFeedback()">

<input id="iData" name="iData" value='${iData}' type="hidden" />
	 <b>Error occured </b><br/>
    <c:forEach var="entry" items="${errorMap}">
  		Error Code: <c:out value="${entry.key}"/><br/>
  		Error Message: <c:out value="${entry.value}"/>
	</c:forEach>
</body>
</html>