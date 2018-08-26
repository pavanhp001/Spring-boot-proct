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
.errorDiv{
	border: 2px solid #FF0000;
    border-radius: 6px 6px 6px 6px;
    font-size: 24px;
    font-weight: bold;
    margin-left: auto;
    margin-right: auto;
    padding: 20px;
    text-align: center;
    width: 600px;
}
</style>
<script>

$(document).ready(function(){
	displayCKOLoader(true)

});
</script>

</head>
<body>
 
	 
		<div class="errorDiv">Error.  Please Report.</div>
 
</body>
</html>