<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Product Customization</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value='/style/common_CKO.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/static_CKO.css'/>" />

<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>
<script src="<c:url value='/script/features.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/style/acdc/acdc_productInfo.css'/>" />
<script src="<c:url value='/script/acdc_cko/acdc_productinfo.js'/>"></script>
<script src="<c:url value='/script/acdc_cko/CKO_constants.js'/>"></script>
<script src="<c:url value='/script/acdc_cko/acdc_cko_util.js'/>"></script>

<script>
	var productVO = ${productVO};
	$(document).ready(function(){
		registerOnloadEventsAndFunctions();
		var CKOPageTitle = "Product Customization";
		if(dataLayer!=undefined && dataLayer.pageContent!= undefined)
			dataLayer.pageContent.pageName=CKOPageTitle;
		setCKOPageTitle(CKOPageTitle);
		    $('.next_step').bind("keydown", function(event) {
			    if (event.keyCode === 13){
			    	submitProuductInfo(); 
			    }
			 }); 
		
	});
</script>
</head>
<body >
<form id="formData" method="post" action="<%=request.getContextPath()%>/static/save">
<div id="left_sec_content">
<div id="steps" class="row">
	<div id="step1" class="current-step">
		<span class="step_id">1</span>
		<span class="step_title">Product<br /> Customization</span>
	</div>
	<div id="step2">
		<span class="step_id">2</span>
		<span class="step_title">Customer <br/> Information</span>
	</div>
	<div id="step3">
		<span class="step_id">3</span>
		<span class="step_title">Installation <br/>and Payment</span></div>
	<div id="step4">
		<span class="step_id">4</span>
		<span class="step_title">Review and<br/> Place Order</span>
	</div>
</div>
<div id="customizations" class="row">
	<div class="cust_title">Please select customizations</div>
	<div class="next_step" class="next" tabindex="0">
		<span id="nextStep">Next Step</span>
	</div>
		<div id="error_msg" class="row" style="display: none;">
        	<div class="validationMsgCont"><strong>Please fill out required items. </strong></div>
        </div>
	<!--<div class="alert alert-danger validCont">
	    <strong>Required Fields :</strong>
	    <div class="validationMsgCont"></div>
	</div>
--></div>

<div id="optionsgroup">

<div class="panel-group" id="accordion">
<div class="panel panel-default accord-selected" id="panel1">
<div class="panel-heading">
	<h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><span class="leftindicator glyphicon glyphicon-minus"></span>Available Promotions</a>
	 <span class="indicator glyphicon glyphicon-triangle-bottom pull-right"></span>
	</h4>
</div>
<div id="collapseOne" class="panel-collapse collapse in">
	<div class="panel-body">
		<div class="home-services-options row promotionContent1">
			<div class="home-services-head">Choose a promotion below</div>
			<div class="home-services-body ">
			</div>
		</div>
		<div class="home-services-options row promotionContent">
			<div class="home-services-head">Additional Promotions and Offers</div>
			<div class="home-services-body">
			</div>
		</div>
	</div>
</div>
</div>
<!-- Pannel Options--> <!-- Panel 2 --> <!-- Panel 3 --></div>

</div>
<div id="bottom-footer" class="row">
<div class="next_step" id="next" tabindex="0"><span id="nextStep">Next Step</span></div>
</div>
</div>
<input type="hidden" name="productVOJSON" id="productVOJSON" value="">
<input id="iData" name="iData" value='${iData}' type="hidden" />
<input id="lineitemNumber" name="lineitemNumber" value='${lineItemNumber}' type="hidden" />
<input id="orderID" name="orderID" value='${orderID}' type="hidden" />
<input id="product_name" name="product_name" value='${product_name}' type="hidden" />
<input id="provider_name" name="provider_name" value='${provider_name}' type="hidden" />
</form>
<input type="hidden" class="contextPath"
	value="<%=request.getContextPath()%>">
</body>
</html>
