
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title><fmt:message key="search.title"/></title>
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/fb_css.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/head_css.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/auth_css.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/search.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/jquery.ui.datepicker.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/jquery.ui.all.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/demo_page.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/demo_table_jui.css" />
<link rel='stylesheet' href="<%=request.getContextPath()%>/style/salesOrder.css" />

<style type="text/css" title="currentStyle">

body {
	font-family: Arial, Helvetica, Sans-Serif;
	font-size: 0.75em;
	color: #000;
}
#closeOverlay {
  border-radius: 4px 4px 4px 4px;
  float: right;
  height: 28px;
  cursor: pointer;
}

.viewAllProducts {
  border: 1px solid #555;
  border-radius: 8px;
  cursor: pointer;
  display: list-item;
  margin-right: 12px;
  width: 148px;
}

.srchLLabel {
    float: left;
    font-size: 13px;
    text-align: right;
    width: 150px;
}

.padTopBottom {
    padding-bottom: 5px;
    padding-top: 5px;
}
.rounded {
    border: 1px solid #A3C9DB;
    border-radius: 6px 6px 6px 6px;
}

.scoreText {
    box-shadow: 0 0 0;
    margin-left: 5px !important;
    min-height: 20px;
    padding: 1px 0 1px 5px !important;
    width: 193px !important;
    background-color: #FFFFFF;
    border: 1px solid #D7D7D7;
    color: #333333;
    font-size: 14px;
}

select#typeOfSearchCriteria, select#state {
    box-shadow: 0 0 0;
    margin-left: 5px !important;
    min-height: 20px;
    padding: 1px 0 1px 2px !important;
    width: 200px !important;
    background-color: #FFFFFF;
    border: 1px solid #D7D7D7;
    color: #333333;
    font-size: 14px;
}

.star {
    color: #FF0000;
    font-size: 18px;
    font-weight: bold;
    margin-left: 5px;
}

div.tabs > div {
	/* border-bottom: medium none; */
}

div#basicSearchBlock{
	border: 1px solid #AAA;
	border-top: medium none;
	padding-bottom: 5px;
    padding-right: 5px;
	padding-left: 165px;
}

input#searchSubmit{
	background-color: #78C864;
    background-image: linear-gradient(#78C864, #9AEA86 50%, #459531);
    border: 1px solid #4D9D39;
    border-radius: 4px;
    cursor: pointer;
    margin-top: 10px;
    padding: 3px 6px;
}

div#bottomContent{
	padding-left: 0;
}

.ui-widget-header {
    background-color: #78C864;
	background-image: linear-gradient(#78C864, #9AEA86);
	border: 1px solid #78C864;
	color: #FFFFFF;
	font-weight: bold;
}

.noResultsFound {
    color: #FF0000;
    font-size: 14px;
    font-weight: bold;
    margin-bottom: 5px;
    margin-right: 5px;
    margin-top: 5px;
}

.scoreOverlay {
	 position:fixed; 
	 top:0; 
	 left:0; 	
	 width:100%;
	 height:100%;
	 background-color: #000;  
	 z-index:10000
	}
.superScript{
	vertical-align: super;
	font-size: 11px;
}

</style>



<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.datepicker.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.dataTables.js"></script>
<script src="<%=request.getContextPath()%>/script/search.js"></script>

</head>
<body class="ego_page home   fbx ff3 mac Locale_en_US" style="overflow:auto;">
<c:set var="viewLevel" value="2"/>
	<!-- **************************************** -->
	<div  id="blueBar" ></div>
	<div>
	<header>
			<div id="pageHead"  class="ptm clearfix">
				<div id="headNav"   class="clearfix header">
					<div class="lfloat app-logo">
						<ul id="pageNav" class=" pageNavBold">
							<li></li>
							<li><a href="<%=request.getContextPath()%>/rest/home"><fmt:message key="search.title"/></a></li>

						</ul>
					</div>
					<div class="rfloat">
						<ul id="pageNav" class="pageNavBold">
							<li><a href="<%=request.getContextPath()%>/rest/logout"><fmt:message key="srt.logout"/></a></li>
						</ul>
					</div>
				</div>
			</div>
		</header>
		</div>
		
	<div id="globalContainer" style="width:80%;">
		
		<section>
			<div id="score_search_container">
				<div>
				
						<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>"/>
						<input type="hidden" id="path" value="<%=request.getContextPath()%>"/>
						<input type="hidden" id="searchType" name="searchType" value="getWebOrdersWithAgent" />
						<input type="hidden" id="typeOfSearch" name="typeOfSearch" value="BasicSearch" />
						<input type="hidden" id="faOrderId" name="faOrderId" value="${faOrderId}" />
						<input type="hidden" id="currentPage" value="0"/>
		                 <input type="hidden" id="totalPages" value="0"/>
				
						<div class="UIContentTopper_text_container">
							<div style="color: rgb(56, 67, 19); font-size: 18px; font-weight: bold; text-shadow: 0px 1px 1px rgb(192, 213, 118);">
								<fmt:message key="search.header"/>
							</div>
						</div>
						<div style="margin-top: 15px;" id="UIContentTopper" class="UIContentTopper clearfix">
				
							<div id="basicSearch" style="float: left; width: 100%;">
								<div class="tabs">
								  <!-- tabs -->
								  <ul class="tabNavigation">
								    <li><a href="#order"><fmt:message key="basic.tab"/></a></li>
									<li><a href="#customer"><fmt:message key="cust.tab"/></a></li>
								  </ul>
							
								  <!-- tab containers -->
									<div id="customer" style="height: 140px;">
										<div style="float: left; margin-left: 5px; padding: 5px 0 0; width: 90%;">
											<div>
												<table style="border: 0px; width: 100%">
													<tbody>
														<tr>
															<td>
																<span class="padTopBottom srchLLabel"><fmt:message key="label.fname"/></span>
																<span style="float: left;" class="padTopBottom">
																	<input type="text" class="scoreText" tabindex="1" id="firstname" name="firstname" value="">
																</span>
															</td>
															<td>
																<span class="padTopBottom srchLLabel"><fmt:message key="label.emailId"/></span>
																<span style="float: left;" class="padTopBottom">
																	<input type="text" class="scoreText" tabindex="2" id="email" name="email" value="">
																</span>
															</td>
															
														</tr>
														<tr>
															<td>
																<span class="padTopBottom srchLLabel"><fmt:message key="label.lname"/></span>
																<span style="float: left;" class="padTopBottom">
																	<input type="text" class="scoreText" tabindex="3" id="lastname" name="lastname" value="">
																	<span class="star">*</span>
																</span>
															</td>
															<td>
																
																<span class="padTopBottom srchLLabel"><fmt:message key="label.phoneNo"/></span>
																
																<span style="float: left;" class="padTopBottom">
																	<input type="text" class="scoreText" tabindex="4" id="phone" name="phone" value="">
																	<span tabindex="9" id="updateFocus"></span>
																</span>
															</td>
														</tr>
														
														<tr>
															<td>
																<span class="padTopBottom srchLLabel"><fmt:message key="label.zip"/></span>
																<span style="float: left;" class="padTopBottom">
																	<input type="text" class="scoreText" tabindex="5" id="zipcode" name="zipcode" value="">
																	<span class="star">*</span>
																</span>
															</td>
															<td>
																<span class="padTopBottom srchLLabel"><fmt:message key="label.address"/></span>
																<span style="float: left;" class="padTopBottom">
																	<input type="text" class="scoreText" tabindex="6" id="address" name="address" value="">
																</span>
															</td>
														</tr>
														<tr><td colspan="2" style="padding: 8px"></td></tr>
													</tbody>
												</table>
											</div>
										</div>
			
									</div>
								  <div id="order" style="height: 75px;">
									<div style="float: left; margin-left: 5px; padding: 5px 0 0;">
									
										<span class="padTopBottom srchLLabel"><fmt:message key="label.searchType"/></span>
										<span>
											<select id="typeOfSearchCriteria" name="typeOfSearchCriteria" class="selectStyle" style=" width: 150px;">
												<option value="getWebOrdersWithAgent"><fmt:message key="search.type.agent"/></option>
												<option value="getWebOrdersWithOrder"><fmt:message key="search.type.order"/></option>
												<option value="getWebOrdersWithUcid"><fmt:message key="search.type.ucid"/></option>
												<!--<option value="getWebOrdersWithGuid"><fmt:message key="search.type.guid"/></option>
												--><option value="getWebOrdersWithLineItemId"><fmt:message key="search.type.lineItemId"/></option>
											</select>
										</span>
										
										<div id="inputBlock" style="margin: 5px 0px;">
										    <span id="ucidSpan" style="display:none;">
												<span class="padTopBottom srchLLabel"><fmt:message key="label.ucid"/></span> 
												<input type="text" class="searchInput" placeholder="Enter UCID" value="" id="ucid" name="ucid" class="inputStyle scoreText">
											</span>
											<!--<span id="guidSpan" style="display:none;">
												<span class="padTopBottom srchLLabel"><fmt:message key="label.guid"/></span> 
												<input type="text" class="searchInput" placeholder="Enter GUID" value="" id="guid" name="guid" class="inputStyle scoreText">
											</span>
											--><span id="lineItemSpan" style="display:none;">
												<span class="padTopBottom srchLLabel"><fmt:message key="label.lineItemId"/></span> 
												<input type="text" class="searchInput" placeholder="Enter LineItemId" value="" id="lineItemId" name="lineItemId" class="inputStyle scoreText">
											</span>
											<span id="agentSpan">
												<span class="padTopBottom srchLLabel"><fmt:message key="label.agentId"/></span>
												<input type="text" class="searchInput scoreText" placeholder="Enter Agent Id" name="agentId" id="agentId" value=""/>
												<input type="text" class="searchInput scoreText" placeholder="Enter From Date" name="fromDate" id="fromDate" value="">
												<input type="text" class="searchInput scoreText" placeholder="Enter To Date" id="toDate" name="toDate" value="">
											</span>
											<span id="orderSpan" style="display:none;">
												<span class="padTopBottom srchLLabel"><fmt:message key="label.orderId"/></span>
												<input type="text"  class="searchInput scoreText" placeholder="Enter Order Id" value="" id="orderId" name="orderId" class="inputStyle">
											</span>
										</div>
									</div>
								  </div>
								</div>
								<div id="basicSearchBlock">
									<input type="button" id="searchSubmit" value="<fmt:message key="search.button"/>" />
								</div>
							</div>
						</div>
					
					<div style="clear: both; height: 20px;"></div>
					
					<div id="orderResults" class="resultsBlock" style="padding: 4px; width: 100%; display:none;">
						<span style="margin-top: 2px; margin-right: 5px; margin-bottom: 5px; font-size: 14px;" class="fLeft"><b><fmt:message key="search.result"/></b></span>
						<span id="goToAdvance" style="margin-top: 2px; margin-right: 5px; margin-bottom: 5px; font-size: 14px; display: none;" class="fRight">Back to Results</span>
						<div style="clear: both; text-align: center;"><img id="loader" alt="Loading ..." src="<%=request.getContextPath()%>/image/ajax-loader_green.gif" style="padding-top: 5px; vertical-align: bottom; padding-bottom: 5px; display: none;"></div>
					</div>
				</div>
			</div>
		</section>
		<div id="resultsBlock" class="resultsBlock" style="padding: 4px; width: 100%; display:none;">
			<div class="navigationBlock">
			</div>
		</div>
	</div>
</body>
</html>