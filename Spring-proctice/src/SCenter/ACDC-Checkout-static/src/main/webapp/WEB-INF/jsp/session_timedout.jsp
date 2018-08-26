<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_att_iframe.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_att.css'/>" />
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
	</script>
	
</head>
<body onload="symFeedback();gotoOrderSummary();">
<input id="iData" name="iData" value='${iData}' type="hidden" />
 <!-- <div style="width:598px; height:438px;margin:0 auto;"> -->		
	<img src="/images/ajax-loader_green.gif" />
	<br>	
	<div class="pc_pdetails_cont">	
		<!-- Left Block -->
		<div class="pc_steps">
			<div id="pc_steps_one" class="pc_steps_item_complete">
				<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>1</span></div></div>
				<div class="pc_steps_item_sttext">Product Customization</div>
			</div>
			<c:choose>
				<c:when test="${not empty step && step=='3'}">
					<div id="pc_steps_two" class="pc_steps_item_complete">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>2</span></div></div>
						<div class="pc_steps_item_sttext">Customer Qualification</div>
					</div>
						<div id="pc_steps_three" class="pc_steps_item_progress pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>3</span></div></div>
						<div class="pc_steps_item_sttext">Authorization</div>
					</div>
				</c:when>
				<c:otherwise>
					<div id="pc_steps_two" class="pc_steps_item_progress pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>2</span></div></div>
						<div class="pc_steps_item_sttext">Customer Qualification</div>
					</div>
					<div id="pc_steps_three" class="pc_steps_item_pending pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>3</span></div></div>
						<div class="pc_steps_item_sttext">Authorization</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
			
		<div class="pc_frameblk">
			<div class="pc_frameblk_cont">
				<fieldset class="pc_fldset">
			    	<legend><div>Session Time Out</div></legend>
			    	<div class="pc_fldset_cont">
				 		<div class="pc_fldset_data_item">
							<span class="labelfixed bold">Failure Reason:</span>
							<span class="value">Your session has expired.</span>
						 </div>
				 	</div>
				 </fieldset>
			</div>
		</div>
	</div>
</body>
</html>