<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link rel="stylesheet"
	href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_att_iframe.css'/>" />
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
<body>
<form>
<input id="iData" name="iData" value='${iData}' type="hidden" />

		<div class="pc_pdetails">
			<div class="pc_pdetails_logo">
    			<c:if test="${not empty providerExternalID}">
    				<img id="provider" src="${providersImageLocation}${providerExternalID}.jpg"  style="float: left;" width="107px;"> </img>
    			</c:if>
  			</div>
		
			<div class="pc_pdetails_info">
			<div class="label">Product Name:</div>
			<div class="value">
				<c:out escapeXml="false" value='<%=request.getSession().getAttribute("product_name")%>' />
			</div><br/>
			<div class="label">Monthly Total:</div>
			<div class="value">
				<span id="monthlyCost">
					<fmt:formatNumber type="currency" value='<%=request.getSession().getAttribute("product_base_recurring_price")%>' />
				</span>
			</div>
			</div>
			<div class="label">Installation Total:</div>
			<div class="value">
				<span id="oneTimePrice">
					<fmt:formatNumber type="currency" value='<%=request.getSession().getAttribute("product_base_nonrecurring_price")%>' />
				</span>
			</div>
			<br/>
		</div>
		<div class="pc_pdetails_cont">
			<div class="pc_steps">
				<div id="pc_steps_one" class="pc_steps_item_pending">
				<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>1</span></div></div>
				<div class="pc_steps_item_sttext">Product Customization</div>
			</div>
			<div id="pc_steps_two" class="pc_steps_item_pending pc_steps_item_margin">
				<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>2</span></div></div>
				<div class="pc_steps_item_sttext">Customer Qualification</div>
			</div>
			</div>
			<div class="pc_frameblk">
			<div class=pc_frameblk_cont>
				<fieldset class="pc_fldset">
						<legend>
							<div class="pc_frameblk_title">This application has experienced an error and is currently unavailable</div>
						</legend>
						<div class="pc_fldset_cont">
							<div class="pc_fldset_data_item">
								<span class="labelfixed bold" style="padding-right:46px;">Provider Id</span> <span class="value"><%=request.getSession().getAttribute("providerExternalID") %></span>
							</div>
							<div class="pc_fldset_data_item">
								<span class="labelfixed bold" style="padding-right:46px;">GUID</span> <span class="value"><%=request.getSession().getAttribute("guid") %></span>
							</div>
							<div class="pc_fldset_data_item">
								<span class="labelfixed bold" style="padding-right:50px;">OrderId</span><span class="value"><%=request.getSession().getAttribute("orderID") %></span>
							</div>
							<div class="pc_fldset_data_item">
								<span class="labelfixed bold" style="padding-right:50px;">LineItemId</span><span class="value"><%=request.getSession().getAttribute("lineItemExternalID") %></span>
							</div>
							<div class="pc_fldset_data_item" >
								<span class="labelfixed bold" style="padding-right:46px;">Description</span> 
								<span class="value" >
										${errorMessage}
								</span>
							</div>
						</div>
				</fieldset>
				<div class="pc_frameblk_right_btns">
					<input type="button" class="buttonClass" value="Forward >" onclick="gotoShoppingCart();"/>
				</div>
			</div>
			</div>
		</div>

	</form>	
</body>
</html>