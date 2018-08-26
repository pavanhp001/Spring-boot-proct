<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_base.css'/>" />
<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/custom_form_elements.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>

<script type="text/javascript">
	$(function () {
	    $('fieldset.other').hide();
	    $('input[name="other"]').click(function () {
	        if (this.checked) {
	            $('fieldset.other').show();
	        } else {
	            $('fieldset.other').hide();
	        }
	    });
	});
	$(document).ready(function(){
		Custom.init();
		});	
	</script>
	
</head>
<body onload="symFeedback()">
 
<input id="iData" name="iData" value='${iData}' type="hidden" />



 
 <div style="width:598px; height:438px;margin:0 auto;">
 
 
		Order Submit Successful
		
		
</div>
 
</body>
</html>