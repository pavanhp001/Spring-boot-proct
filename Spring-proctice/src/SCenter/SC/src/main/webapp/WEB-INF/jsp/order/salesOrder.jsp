<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link rel='stylesheet' href="<%=request.getContextPath()%>/style/head_css.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/auth_css.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/jquery.ui.datepicker.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/jquery.ui.all.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/salesOrder.css" />

<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.datepicker.js"></script>
<script src="<%=request.getContextPath()%>/script/salesOrder.js"></script>
<style type="text/css">
.customerInfo  {
 margin-top: 5px;
 }
 .customerInfo > span > input {
 margin-left: 82px;
 }
 .customerInfo > span > label{
      clear: left;
    color: #1E1E1E;
    display: inline-block;
    font-weight: bold;
    text-align: right;
    width: 67px;
}
}
</style>
</head>
<body>
	<form method="post" action="<%=request.getContextPath()%>/srt/salesOrderSearch">
		<c:set var="executeLevel" value="0"/>
		<input type="hidden" id="path" value="<%=request.getContextPath()%>"/>
		<input type="hidden" id="searchType" value="getWebOrdersWithAgent"/>
		<input type="hidden" id="currentPage" value="0"/>
		<input type="hidden" id="totalPages" value="0"/>
		
		<div class="UIContentTopper_text_container">
			<div style="color: rgb(56, 67, 19); font-size: 18px; font-weight: bold; text-shadow: 0px 1px 1px rgb(192, 213, 118);">
				Show Sales Order Flow
			</div>
		</div>
		
		<div class="UIContentTopper clearfix" id="UIContentTopper"></div>
		
		<div id="main_container">
			<div style="border: 0px none; box-shadow: 0px 0px; margin: 10px;">
				<span style="font-weight: bold; float: left; width: 150px;">Search Type: </span>
				<span>
					<select id="typeOfSearch" name="typeOfSearch" class="selectStyle">
						<option value="getWebOrdersWithAgent">Search with Agent</option>
						<option value="getWebOrdersWithOrder">Search with Order</option>
						<option value="getWebOrdersWithCustomer">Search with Customer</option>
						<option value="getWebOrdersWithProvider">Search with Agent-Provider</option>
						<option value="getWebOrdersWithDate">Search with Date</option>
						<option value="getWebOrdersWithUcid">Search with UCID</option>
					</select>
				</span>
				<span id="preLoader" style="display:none;"><img style="vertical-align: top; margin-right: 3px;" src="/image/loader.gif"></span>
				<div id="inputBlock" style="margin: 5px 0px;">
				    <span id="ucidSpan" style="display:none;">
						<span style="font-weight: bold; float: left; width: 150px;">UCID:</span> 
						<input type="text" value="" id="ucid" name="ucid" class="inputStyle">
					</span>
					<span id="agentSpan">
						<span style="font-weight: bold; float: left; width: 150px;">Agent Id:</span> 
						<input type="text" value="" id="agentId" name="agentId" class="inputStyle">
					</span>
					<span id="orderSpan" style="display:none;">
						<span style="font-weight: bold; float: left; width: 150px;">Order Id:</span>
						<input type="text" value="" id="orderId" name="orderId" class="inputStyle">
					</span>
					<span id="customerSpan" style="display:none;" >
					            <div class="customerInfo">
					                <span style="font-weight: bold; width: 150px;"><label>First Name:</label>
										<input type="text" class="inputStyle" name="firstName" id="firstName" value=""> 
									</span>
									
							 		<span style="font-weight: bold; width: 85px;"> <label>Email Id:</label>
										<input type="text" class="inputStyle" name="emailId" id="emailId" value="">
							       	</span>
							 	</div>
							 	<div class="customerInfo">
							 		<span style="font-weight: bold; width: 85px;"><label>Last Name:</label>
										<input type="text" class="inputStyle" name="lastName" id="lastName" value="">	
									</span>
									
									<span style="font-weight: bold;width: 150px;"><label>Zip Code:</label>
										<input type="text" class="inputStyle" name="zipCode" id="zipCode" value="">
									</span>
									<span id="customerOrdersSpan"></span>
									
							 	</div>
							 	
							 	<div class="customerInfo">
							 		<span style="font-weight: bold; width: 85px;"><label>Phone No:</label>
										<input type="text" class="inputStyle" name="phoneNo" id="phoneNo" value="">
									</span>
							       	<span style="font-weight: bold; width: 85px;"><label>Address:</label>
										<input type="text" class="inputStyle" id="address"  name="address"  value="">
									</span>
							 	</div>
							 									
					</span>
					
					<span id="agentProviderSpan" style="display:none;">
						<span style="font-weight: bold; float: left; width: 150px;">Agent Id:</span> 
						<input type="text" value="" id="agentId" name="agentId" class="inputStyle" style="float: left;">
						<span style="font-weight: bold; float: left; width: 150px; margin-left: 5px;">Provider Id:</span>
						<input type="text" value="" id="providerId" name="providerId" class="inputStyle">
					</span>
					
					<span id="calendarSpan" style="display:none;">
						<span style="font-weight: bold; float: left; width: 150px;">Agent Id:</span> 
						<input type="text" class="inputStyle" style="float: left;" name="agentId" id="agentId" value="">
						<span style="font-weight: bold; float: left; margin-left: 5px; width: 85px;">Start Date:</span>
						<input type="text" class="dateType" style="height: 19px; float: left; width: 80px;" id="cal1" name="cal1" value="">
						<span style="font-weight: bold; float: left; margin-left: 5px; width: 85px;">End Date:</span>
						<input type="text" value="" id="cal2" name="cal2" style="height: 19px; width: 80px;" class="dateType">
					</span>
				</div>
				<span><input id="button" type="button" value="Search" /></span>
			</div>
		</div>
		
		<div class="resultsBlock">
			<div class="navigationBlock">
			</div>
		</div>
	</form>
</body>
</html>