<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value='/style/demos.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.all.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_base.css'/>" />
<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/custom_form_elements.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>

<script src="/js/jquery-1.8.0.js"></script>
<script src="/js/jquery.ui.core.js"></script>
<script src="/js/jquery.ui.widget.js"></script>
<script src="/js/jquery.ui.datepicker.js"></script>
<style type="text/css">
.lLabel {
    float: left;
    font-weight: bold;
    width: 20%;
}

.rLabel {
    float: left;
    padding-bottom: 3px;
    width: 80%;
}

</style>
<script type="text/javascript">
	var enabledDays = new Array();
	var i = 0;
	<c:if test="${not empty installDates}">
		<c:forEach var="date" items="${installDates}"> 
			enabledDays[i] = "<c:out value="${date}"/>";
			i++;
		</c:forEach>
	</c:if>
	function enableAllTheseDays(date) {
	    var m = date.getMonth(), d = date.getDate(), y = date.getFullYear();
	    for (i = 0; i < enabledDays.length; i++) {
	        if($.inArray((m+1) + '/' + d + '/' + y,enabledDays) != -1) {
	            return [true];
	        }
	    }
	    return [false];
	}
	$(function () {
		$("#datepicker").datepicker(
			{
				dateFormat: 'mm/dd/yy',
				beforeShowDay: enableAllTheseDays
			}		
		);
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
<font color="red">
	<c:forEach var="error" items="${errors}">
		<c:out value="${error}"/><br>
	</c:forEach>  
</font>  
<input id="iData" name="iData" value='${iData}' type="hidden" />
 
 <div style="width:598px; height:438px;margin:0 auto;">
 	<form action="<%=request.getContextPath()%>/att/showPayment" method="post">
 		OrderQual response
 			<c:if test="${not empty processingStatus}">
		 		<div>
			    	<span class="lLabel" style="">Processing Status:</span> <span class="rLabel" style=""><c:out value="${processingStatus}"/></span>
		 		</div>
	 		</c:if>
 			<c:if test="${not empty lineItemStatus}">
		 		<div>
			    	<span class="lLabel" style="">LineItem Status:</span><span class="rLabel" style=""><c:out value="${lineItemStatus}"/></span>
		 		</div>
 			</c:if>
	 		<c:if test="${not empty reasons}">
		 		<div>
			    	<span class="lLabel" style="">Reasons:</span><span class="rLabel" style=""> <c:out value="${reasons}"/></span>
		 		</div>
	 		</c:if>	
	 		
	 		<c:if test="${not empty installTimes}">
		 		<div><b>What is your preferred time of day for install? </b> 
		    	<select name="installTime">
		    		<c:forEach var="time" items="${installTimes}">
						<option value="<c:out value="${time}"/>"><c:out value="${time}"/></option>
					</c:forEach>
				</select>
				</div>	
			</c:if>
	 		
	 		<c:if test="${not empty installDates}">
		 		<div><b>Set Install Date:</b> 
		 			<input type="text" id="datepicker" size="10" maxlength="10" name="installDate"/>
				</div>	
			</c:if>
	 		<c:out escapeXml="false" value="${data}" />
	 		<input type="submit" value="Next" />
	 </form>		
</div>
 
</body>
</html>