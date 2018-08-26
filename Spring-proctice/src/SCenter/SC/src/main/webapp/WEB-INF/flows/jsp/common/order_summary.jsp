<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
<title><fmt:message key="orderSummary.title"/></title>
<style>
.orderSummaryContent {
    height: 530px  !important;
    margin: 0 !important;
    overflow-y: auto !important;
    padding: 0 !important;
}
.productLogo {
  border: 1px solid transparent;
  min-height: 100%;
  min-width: 160px;
  float: left;
    padding: 2px;
}
.orderSummaryContent .productImage{
	margin-top: 50px !important;
}
.statBG{
	 padding: 3px;
	 border: 1px solid #000;
}

#pageContent {
	margin-right: 0!important;
	padding: 0!important;
}
.os_statusremove {
  margin-bottom: 5px;
  width: 100%;
  float: none;
}
#statusHistory{
	background-color: #F1F1F1;
	border: 1px solid #D9D9D9;
	border-radius: 4px 4px 4px 4px;
	color: #006699;
	display: block;
	text-align: left;
	line-height: 15px;
	margin-bottom: 5px;
}

#statusHistory ul {
  margin: 3px 0;
  padding: 0;
}

#statusHistory ul li:nth-child(2n+1) {
  background-color: #FFFFFF;
  border: 1px solid #D9D9D9;
}


#statusHistory li {
 margin: 0 2px;
  padding: 3px;
  list-style: none;
}

#statusHistory ul li:nth-child(2n) {
   border: 1px solid #D9D9D9;
   border-top: none;
   border-bottom: none;
}

#statusHistory ul li:last-child {
  border-bottom: 1px solid #D9D9D9 !important;
}

#errorMsg{
	color: #F80000;
	text-decoration: none;
	border: 0 none;
	font-family: inherit;
	font-size: 18px;
	font-style: inherit;
	font-weight: inherit;
	margin: 0;
	padding: 0;
}

.headBtnStyle {
  font-size: 14px;
  margin: 5px;
  padding: 5px;
}
.promoBlk {
  padding-left: 20px;
  padding-top: 5px;
}
.promoHeader {
  color: #0078AE;
  font-weight: bold;
  border-bottom: 1px solid #D9D9D9;
  padding: 2.5px 0;
}
.promoList {
  padding: 3px 0;
}
#closingOfferText{
  color: red;
  font-size: 14px;
  text-align: right;
  width: 867px;
  float: right;
}
</style>
<link media="all" href="<c:url value='/style/utils.css'/>" type="text/css" rel="stylesheet">
<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/script/order_summary_flow.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
<script type="text/javascript">

$(document).ready(function() {
	jQuery(window).load(function () {
	      //To save the html on page load
	      savePageHtml(true, "");
	});

		jQuery('.baseRecurringPrice').each(function(index, currentElement) {
			 var currentElement = $(this);
			 var id = $(this).attr("id")
			    var value = currentElement.val(); 
			    if(value < 0){
					var price = value.replace("-", "");
					$("#priceFormat"+id).text("$"+price);
				}
		});
	
	 var closingOfferFlag = '${closingOfferFlagInOrderSummary}';
	 var isPlaceOrder = $('#placeOrder').val();
		if(closingOfferFlag != ''){
			$('#closingOfferText').css('display' , 'block');
		}
		if( isPlaceOrder != '' && isPlaceOrder == 'false'){
			$('#closingOfferText').css('width' , '766px');
		}
});

</script>


</head>
<body onload="updateTimer();">
<form method="POST" action="${flowExecutionUrl}" name="ordersummary" id="ordersummary" >
			<input type="hidden" name="addressId" id="addressId" value="${addressId}" />
			<input type="hidden" name="lineItemIds" id="lineItemIds" value="" />
			<input type="hidden" name="providerNames" id="providerNames" value="" />
			<input type="hidden" name="providerIds" id="providerIds" value="" />
			<input type="hidden" name="productSrcs" id="productSrcs" value="" />
			<input type="hidden" name="productExtIds" id="productExtIds" value="" />
			<input type="hidden" id="utilityOffer" name="utilityOffer" value="${utilityOffer}" />
			<input type="hidden"  id="contextPath" value="<%=request.getContextPath()%>" />
			<input type="hidden"  id="flowExecutionUrl" value="${flowExecutionUrl}" />
			<input type="hidden" id="placeOrder" value="${isPlaceOrder}">
			<input type="hidden" id="orderId" name="orderId"  value="${order.externalId}">
			<input type="hidden" id="customerId" value="${order.customerInformation.customer.externalId}">
			<input type="hidden" id="customerIdVal" name="customerIdVal" value="${order.customerInformation.customer.externalId}">
			<input type="hidden" id="pageTitle" name="pageTitle" value="Order Summary">
			<input type="hidden" id="_eventId" name="_eventId" value="orderRecapEvent">
			<input type="hidden" id="CategoryName" name="CategoryName" value="HOMESECURITY">
			<input type="hidden" id="flowpath" name="flowpath" value="${flowExecutionUrl}">
			
	<section id="contentfullcont">
							<header id="content_header">
								<div class="row">
									<span class="cell">
										<svg version="1.1" id="Layer_5" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
											 width="29px" height="29px" viewBox="0 0 29 29" enable-background="new 0 0 29 29" xml:space="preserve" class="headphonesSVG">
											<g>
												<path fill="#96C43E" d="M29,25.298c0,2.066-1.677,3.744-3.744,3.744H4.411c-2.067,0-3.745-1.678-3.745-3.744V4.328
													c0-2.068,1.678-3.745,3.745-3.745h20.845C27.323,0.583,29,2.26,29,4.328V25.298z"/>
												<path fill="#FFFFFF" d="M23.861,18.677c0.116,0.435,0.177,0.891,0.177,1.362c0,2.921-2.367,5.289-5.288,5.289
													c-0.291,0-0.576-0.023-0.854-0.068l-0.004-0.002c-0.285,0.446-0.843,0.75-1.482,0.75c-0.932,0-1.686-0.64-1.686-1.428
													s0.754-1.428,1.686-1.428c0.563,0,1.061,0.234,1.367,0.594l0,0c0.208,0.029,0.422,0.044,0.641,0.044
													c2.19,0,3.967-1.542,3.967-3.445c0-0.307-0.046-0.604-0.132-0.887l-0.052,0.015c-0.244,0.015-0.527,0.008-0.855-0.031v0.608
													c0,0-0.059,0.398-0.808,0.375s-0.784-0.234-0.796-0.445c-0.012-0.21,0-5.5,0-5.5s0.047-0.409,0.819-0.397
													c0.655,0.012,0.737,0.246,0.761,0.375c0.023,0.128,0.023,0.784,0.023,0.784l0.374-0.059l0.088-0.058
													c0.189-0.664,0.265-1.24,0.265-1.966c0-4.188-3.395-7.583-7.582-7.583c-4.188,0-7.583,3.395-7.583,7.583
													c0,0.722,0.128,1.363,0.316,2.024l0.374,0.059c0,0,0-0.655,0.023-0.784s0.105-0.363,0.761-0.375
													c0.772-0.012,0.819,0.398,0.819,0.398s0.012,5.289,0,5.5s-0.047,0.422-0.796,0.445s-0.808-0.375-0.808-0.375v-0.608
													c-1.193,0.141-1.802-0.141-2.07-0.339c-0.27-0.199-1.065-0.913-0.738-2.293c0.328-1.381,1.451-1.58,1.674-1.58
													s0.164-0.07,0.164-0.07c-0.416-1.001-0.609-2.02-0.609-3.171c0-4.679,3.793-8.472,8.473-8.472c4.678,0,8.472,3.793,8.472,8.472
													c0,1.107-0.212,2.165-0.599,3.134l-0.046,0.037c0,0-0.059,0.07,0.163,0.07c0.223,0,1.346,0.199,1.674,1.58
													c0.205,0.863-0.029,1.465-0.299,1.845L23.861,18.677z"/>
											</g>
										</svg>
									</span>
									<span class="cell pageTitle"><fmt:message key="${title}"/></span>
									<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
									<span class="callTime" id="startTimeText"></span>
								</div>
							</header>
							<section id="content">
							<div class="contentwrapperos">
								
								<div id="action_wrapper_os">
									
				<div class="widget_container_os">
					<c:forEach var="lineitem" items="${sortedLineItems}">
						<c:set var="productName" value="${lineitem.productName}"/>	
						<c:set var="detailType" value="${lineitem.lineItemDetailType}"/>
						<c:set var="providerId" value="${lineitem.providerExtId}"/>
						<c:set var="productSource" value="${lineitem.productSorce}"/>
						<c:set var="displayLi" value="${lineitem.displayLineItem}"/>
						<c:set var="OneTimePriceValue" value="${lineitem.oneTimePriceValue}"/>
						<c:set var="monthlyPriceAfterPromoEndsValue" value="${lineitem.monthlyPriceAfterPromoEnds}"/>
						<c:set var="statusAttribute" value='${lineitem.lineItemStatusAttribute}'/>
						<c:set var="baseMonthlyNonPromotionPrice" value='${lineitem.baseMonthlyNonPromotionPrice}'/>
						<c:set var="lineItemName" value='${lineitem.lineItemName}'/>
						<c:set var="lineItemProviderName" value='${lineitem.lineItemProviderName}'/>
						<c:set var="parentExternalId" value='${lineitem.parentExternalId}'/>
						<c:set var="falloutOrder" value='${lineitem.fallOutOrder}'/>
						
						
								<c:if test="${displayLi == 'true'}" >
									<c:set var="liStatus" value="${lineitem.lineItemStatus}" />
									<c:set var="productExternalId" value='${lineitem.productExternalId}'/>

									<c:set var="productFeatureMap" value='${productFeatureNameMap[productExternalId]}'/>
											<!-- os header -->
												<div class="os_header">
													<div class="os_header_cont">
														<%-- <div class="selecthead"><fmt:message key="orderSummary.select"/></div> --%>
														<div class="itemhead">&nbsp;</div>
														<div class="itemnamehead"><fmt:message key="orderSummary.item"/></div>
														<div class="statushead">
															<span style="float: left;">
																<fmt:message key="orderSummary.status"/>
															</span>
															<span style="margin-top: 3px;" class="statusHistoryToggle"></span>
														</div>
													</div>
												</div>
												<div class="os_productdata">
													<div class="os_selectdata">
													<!--CheckBox-->
														<c:if test="${liStatus != 'CANCELLED_REMOVED'}">
															 <input class="productCheckBox" type='checkbox' 
																<c:if test="${statusAttribute == 'CKOComplete'}">
																		disabled='disabled'
																</c:if>
																/> 
															<input type="hidden" name="path" value='<%=request.getContextPath()%>'/>
															<input type="hidden" name="ltId" value="${lineitem.lineItemExternalId}" />
															<input type="hidden" name="lineItemProviderName" value="${lineItemProviderName}" />
															<input type="hidden" name="providerId" value="${providerId}" />
															<input type="hidden" name="productSource" value="${productSource}" />
															<input type="hidden" name="productExternalId" value="${productExternalId}" />
															
														</c:if>
														 <c:if test="${liStatus == 'CANCELLED_REMOVED'}">
																<input class="productCheckBox" type='checkbox' disabled='disabled'/>
														</c:if> 
													</div>
													<div class="os_itemdata">
														<div class="os_CKObtn">
														<input type="hidden" name="lineitem_id" id="lineitem_id" />
			                                            <input type="hidden" name="state" id="state" value="" />
														<!--CKO and AddToOrder-->
															<c:if test="${liStatus != 'CANCELLED_REMOVED'}">
															<c:if test="${statusAttribute != 'CKOComplete'}">
																<input id="${productExternalId}" class="os_buttons" value="<fmt:message key="orderSummary.beginOrder.button"/>"  type='button'  
																	onclick="CKO(${lineitem.lineItemExternalId},${providerId},'${lineItemProviderName}','${productSource}','${flowExecutionUrl}','${productExternalId}');"
																	/>
																	<c:set var="OneTimePriceValue" value=''/>
																	<c:set var="monthlyPriceAfterPromoEndsValue" value=''/>
																	
																	</c:if>
															</c:if> 
															<c:if test="${liStatus == 'CANCELLED_REMOVED'}">
																<input class="os_buttons" value="<fmt:message key="orderSummary.AddOrder.button"/>" type='button' id="${lineitem.lineItemExternalId}" name="reAdd"/>
															</c:if>
														</div>
														<div class="os_providerlogo">
														<!--Provider Logo-->
															<a href="#"> 
																<img alt="${lineitem.providerExtId}" src="${providersImageLocation}${lineitem.imageID}.jpg"
																				onError="this.onerror=null;this.src='${providersImageLocation}${lineitem.imageID}.jpg';" style="float: left;">
															</a>
														</div>
													</div>
													<div class="os_itemnamedata">
														<div class="os_itemnameinfo">
															<!--Product Name-->
															<c:choose>
																	<c:when test="${not empty productName && not empty lineitem.providerName}">
																		${lineitem.providerName}&nbsp;-&nbsp;${productName}
																		<div class="confirmSec" style="display:none;">
																		${productName} 
																		</div>
																		<input class="homeSecurityClass" type="hidden" value="${lineitem.productCategoryType}">
																	</c:when>
																	<c:otherwise>
																		${lineItemProviderName}&nbsp;-&nbsp;${lineItemName}
																		<div class="confirmSec" style="display:none;">
																		${lineItemName} 
																		</div>	
																		<input class="homeSecurityClass" type="hidden" value="${lineitem.productCategoryType}">																	
																	</c:otherwise>
															</c:choose>
														</div>
															<div class="os_itemdetails">
															<c:if test="${liStatus != 'CANCELLED_REMOVED'}">
																<c:choose>
																	<c:when test="${not empty lineitem.accountHolderFirstName }">
			                                                           	<div style="text-align:left;font-weight: bold">
			                                                            	<fmt:message key="orderSummary.accountHolderName" /> : ${lineitem.accountHolderFirstName} ${lineitem.accountHolderLastName}
			                                                            </div>
		                                                            </c:when>
		                                                            <c:otherwise>
		                                                            	<c:if test="${statusAttribute == 'CKOComplete'}">
		                                                            		<div style="text-align:left;font-weight: bold">
			                                                            		<fmt:message key="orderSummary.accountHolderName" /> : ${order.customerInformation.customer.firstName} ${order.customerInformation.customer.lastName}
			                                                            	</div>
		                                                            	</c:if>
		                                                            </c:otherwise>
		                                                     	</c:choose>
																<table cellpadding="0" cellspacing="0" class="os_itemdetailstbl">
																	<tr class="os_id_row1">
																		<th><fmt:message key="provider.list.description"/></th>
																		<th><fmt:message key="orderSummary.quantity"/></th>
																		<th style="float:right"><fmt:message key="orderSummary.price"/></th>
																	</tr>
																	<c:choose>
																		<c:when test="${ not empty lineitem.productCategoryType && lineitem.productCategoryType== 'NATURALGAS' }" >
																			<tr class="os_id_row2">
																				<td><fmt:message key="orderSummary.basePackagePriceForRT"/></td>
																				<td></td>
																				<td style="text-align: right;" id="baseMonthlyNonPromotionPriceTdId"><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyNonPromotionPrice}" maxFractionDigits="2" pattern="0.00"/>
																					<input type="hidden" id="hiddenBaseMonthlyNonPromotionPrice" value="${baseMonthlyNonPromotionPrice}">
																				</td>
																			</tr>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${productSource == 'REALTIME'&& (providerId!=32416075 || providerId!=15499341)}">
																					<tr class="os_id_row2">
																						<td><fmt:message key="orderSummary.basePackagePriceForRT"/></td>
																						<td></td>
																						<td style="text-align: right;" id="baseMonthlyNonPromotionPriceTdId"><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyNonPromotionPrice}" maxFractionDigits="2" pattern="0.00"/>
																							<input type="hidden" id="hiddenBaseMonthlyNonPromotionPrice" value="${baseMonthlyNonPromotionPrice}">
																						</td>
																					</tr>
																				</c:when>
																				<c:otherwise>
																					<tr class="os_id_row2">
																						<td><fmt:message key="orderSummary.basePackagePriceNonPromotion"/></td>
																						<td></td>
																						<td style="text-align: right;" id="baseMonthlyNonPromotionPriceTdId"><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyNonPromotionPrice}" maxFractionDigits="2" pattern="0.00"/>
																							<input type="hidden" id="hiddenBaseMonthlyNonPromotionPrice" value="${baseMonthlyNonPromotionPrice}">
																						</td>
																					</tr>
																					<tr class="os_id_row2">
																						<td><fmt:message key="orderSummary.basePackagePrice"/></td>
																						<td></td>
																						<td style="text-align: right;"><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyPriceMap[lineitem.lineItemExternalId]}" maxFractionDigits="2" pattern="0.00"/></td>
																					</tr>
																				</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>
																	
																	<!--Features-->
																	<c:forEach var="feature" items="${lineitem.featuresAndSelecetionsVO}" varStatus="loop">
																		<tr class="os_id_row_grey_first">
																			<td>
																				<c:choose>
																					<c:when test="${not empty productFeatureMap[feature.featureExtrnalId]}">
																						${productFeatureMap[feature.featureExtrnalId]}
																					</c:when>
																					<c:otherwise>
																						${feature.featureExtrnalId}
																					</c:otherwise>
																				</c:choose>
																			</td>
																			<td>
																				<c:choose>
																					<c:when test="${feature.featureValue eq 'true'}">
																						&#x2713;
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${fn:startsWith(feature.featureValue, 'RTS:')}">
																							</c:when>
																							<c:otherwise>
																								${feature.featureValue}
																							</c:otherwise>
																						</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</td>
																				<td style="text-align:right">
																					<span  class="os_montsize"><fmt:message key="orderSummary.monthly"/>:</span>
																					<c:if test="${feature.baseRecurringPrice >= 0}">
																					<span ><fmt:message key="orderQuick.currency"/><fmt:formatNumber value="${feature.baseRecurringPrice}" maxFractionDigits="2" pattern="0.00"/></span>
																					</c:if>
																					<c:if test="${feature.baseRecurringPrice < 0}">
																					<span ><span>-</span><span id="priceFormat${loop.index}"></span></span>
																					<input type="hidden" id="${loop.index}" value="${feature.baseRecurringPrice}" class="baseRecurringPrice" name="baseRecurringPrice" /> 
																					</c:if>
																					<span class="os_instsize"><fmt:message key="orderSummary.install"/>:</span>
																					<span ><fmt:message key="orderQuick.currency"/><fmt:formatNumber value="${feature.baseNonRecurringPrice}" maxFractionDigits="2" pattern="0.00"/></span>
																				</td>
																			</tr>
																		</c:forEach>

																	<!--Monthly Fee-->
																	<tr class="os_id_row3" style="font-weight: bold;">
																		<td ><fmt:message key="orderSummary.monthlyFee"/></td>
																		<td></td>
																		<c:choose>
																			<c:when test="${providerId == 15499381}">
																			<td style="text-align: right;">
																				 <span>N/A</span>
																			</td>
																			</c:when>
																			<c:otherwise>
																			<td style="text-align: right;"><fmt:message key="orderQuick.currency" />
																		     <fmt:formatNumber value="${lineitem.monthlyTotal}" maxFractionDigits="2" pattern="0.00"/>
																		     </td>
																		</c:otherwise>
																		</c:choose>
																	</tr>
																	<!--Installation Fee-->
																	<tr style="font-weight: bold;">
																		<td><fmt:message key="orderSummary.installationFee"/></td>
																		<td></td>
																		<td style="text-align: right;">
																		<c:set var="installationFee" value="${lineitem.instalationTotal}"/>
																		<c:choose>
																			<c:when test="${providerId == 26069940}">
																				<c:choose>
																					<c:when test="${installationFee == -1.00}">
																						<c:out value="Customizable"/>
																					</c:when>
																					<c:when test="${installationFee == -2.00}">
																						<c:out value="Varies by Market"/>
																					</c:when>
																					<c:otherwise>
																						<fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${installationFee}" maxFractionDigits="2" pattern="0.00"/>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${installationFee}" maxFractionDigits="2" pattern="0.00"/>
																			</c:otherwise>
																		</c:choose>
																		</td>
																	</tr>
																	<!--One Time Fee-->
																	<c:if test="${ productSource == 'REALTIME' && statusAttribute == 'CKOComplete'&& (providerId!=32416075 || providerId!=15499341)}">
																		<tr style="font-weight: bold;">
																			<td ><fmt:message key="orderSummary.oneTimeFee"/></td>
																			<td></td>
																			<td style="text-align: right;">
																				<fmt:message key="orderQuick.currency" />
																				<c:choose>
																					<c:when test="${not empty OneTimePriceValue }">
																						<fmt:formatNumber value="${OneTimePriceValue}" maxFractionDigits="2" pattern="0.00"/>
																					</c:when>
																					<c:otherwise>
																						0.00
																					</c:otherwise>
																				</c:choose>
																			</td>
																		</tr>
																	</c:if>
																	<c:if test="${ not empty lineitem.thermRate }">
																				<tr style="font-weight: bold;">
																				<c:if test="${ not empty lineitem.energyUnitName }">
																					<td style="text-transform: capitalize">${lineitem.energyUnitName} <span>Rate</span></td>
																				</c:if>	
																					<td></td>
																					<c:choose>
																					<c:when test="${providerId == 15499381}">
																					<td style="text-align: right;">
																					     <span>$</span>${lineitem.monthlyTotal}
																					 </td>
																					</c:when>
																					<c:otherwise>
																					<td style="text-align: right;">
																						<span>$</span>${lineitem.thermRate}
																					</td>
																					</c:otherwise>
																					</c:choose>
																				</tr>
																		<c:if test="${not empty lineitem.guaranteeAmount && not empty lineitem.productCategoryType && lineitem.productCategoryType== 'NATURALGAS' }">
																			`<tr style="font-weight: bold;">
																				<td ><fmt:message key="orderSummary.guaranteeAmount"/></td>
																				<td></td>
																				<td style="text-align: right;">
																					<fmt:message key="orderQuick.currency" />${lineitem.guaranteeAmount}
																				</td>
																			</tr>
																		</c:if>
																		<c:if test="${not empty lineitem.prepaidAmount && not empty lineitem.productCategoryType && lineitem.productCategoryType== 'NATURALGAS' }">
																			`<tr style="font-weight: bold;">
																				<td ><fmt:message key="orderSummary.prepaidAmount"/></td>
																				<td></td>
																				<td style="text-align: right;">
																					<fmt:message key="orderQuick.currency" />${lineitem.prepaidAmount}
																				</td>
																			</tr>
																		</c:if>
																	</c:if>
																</table>
																</c:if>
															</div>
															<c:if test="${liStatus != 'CANCELLED_REMOVED'}">
																<c:choose>
																	<c:when test="${ not empty LineItemVo.promotionMap}">
																		<c:set var="isPromotionAvail" value="false"></c:set>
																		<c:forEach var="promotionMap"	items="${LineItemVo.promotionMap}">
																			<c:if test="${promotionMap.key eq lineitem.lineItemNumber}">
																			<c:set var="isPromotionAvail" value="true"></c:set>
																				<div class="os_promotions">
																					<div class="os_promo_title"><fmt:message key="orderSummary.Promotions"/></div>
																				<c:set var="combinePromoDesc" value="${LineItemVo.combinePromoDesc}"></c:set>
																				    ${combinePromoDesc}
																					<div class="os_promo_desc">
																							<c:if test="${productSource == 'REALTIME'}">
																								<br/>
																								<font color="red">
																									<c:if test="${not empty monthlyPriceAfterPromoEndsValue }">
																										<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${monthlyPriceAfterPromoEndsValue}" maxFractionDigits="2" pattern="0.00"/>
																									</c:if>
																									<c:if test="${empty monthlyPriceAfterPromoEndsValue && (providerId!=32416075 || providerId!=15499341)}">
																										<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/>NA
																									</c:if>
																								</font>
																						</c:if>
																					</div>
																				</div>	
																			</c:if>
																		</c:forEach>
																		<c:if test="${isPromotionAvail eq 'false' && productSource == 'REALTIME' && statusAttribute == 'CKOComplete'}">
																			<c:choose>
																				<c:when test="${not empty falloutOrder }">
																					<div class="os_promotions">
																			    		<div class="os_promo_title"><fmt:message key="orderSummary.Promotions"/></div>
																			        	<div class="os_promo_desc">
																							Not Available
																							<br/>
																							<font color="red">
																								<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/>Not Available
																							</font>
																			        	</div>
																			    	</div>  
																			    </c:when>
																			    <c:otherwise>
																			   		<div class="os_promotions">
																			   			<div class="os_promo_title"><fmt:message key="orderSummary.Promotions"/></div>
																						<div class="os_promo_desc">
																			      			Not Applicable
																			      			<br/>
																							<font color="red">
																						   		<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/>Not Applicable
																							</font>
																			       		</div>
																			 		</div>  
																				</c:otherwise>
																			</c:choose>
																		</c:if>
																	</c:when>
																	<c:otherwise>
																		<c:if test="${productSource == 'REALTIME' && statusAttribute == 'CKOComplete'}">
																			<c:choose>
																				<c:when test="${not empty falloutOrder }">
																					<div class="os_promotions">
																			    		<div class="os_promo_title"><fmt:message key="orderSummary.Promotions"/></div>
																			        	<div class="os_promo_desc">
																							Not Available
																							<br/>
																							<font color="red">
																								<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/>Not Available
																							</font>
																			        	</div>
																			    	</div>  
																			    </c:when>
																			    <c:otherwise>
																			   		<div class="os_promotions">
																			   			<div class="os_promo_title"><fmt:message key="orderSummary.Promotions"/></div>
																						<div class="os_promo_desc">
																			      			Not Applicable
																			      			<br/>
																							<font color="red">
																						   		<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/>Not Applicable
																							</font>
																			       		</div>
																			 		</div>  
																				</c:otherwise>
																			</c:choose>
																		</c:if>
																	</c:otherwise>
																	
																</c:choose>
															</c:if>
													</div>
													<div class="os_statusdata">
														<input type="hidden" name="lineitem_id" id="lineitem_id"  />
			                                            <input type="hidden" name="state" id="state" value="" />
														<c:if test="${statusAttribute eq 'CKOReady'}"><div class="os_statusreadylbl"><fmt:message key="cart.CKO_READY"/></div></c:if>
														<c:if test="${statusAttribute eq 'CKOComplete'}"><div class="os_statuscompletelbl"><fmt:message key="cart.CKO_COMPLETED"/></div></c:if>
														<c:if test="${statusAttribute eq 'Removed'}"><div class="os_statuserrorlbl"><fmt:message key="cart.REMOVED"/></div></c:if>
														<c:if test="${statusAttribute eq 'CKOError'}"><div class="os_statuserrorlbl"><fmt:message key="cart.CKO_ERROR"/></div></c:if>
														<c:if test="${statusAttribute eq 'CKOInComplete'}"><div class="os_statuserrorlbl"><fmt:message key="cart.CKO_INCOMPLETE"/></div></c:if>
														
														<c:if test="${ statusAttribute != 'CKOComplete' && statusAttribute != 'Removed' }">
											            	<div class="os_statusremove">
											            		<input class="os_buttons" type="button" id="${lineitem.lineItemExternalId}" name="remove" value="Remove" />
											                </div>
											            </c:if>
														
														<div id="statusHistory" style="display: none;">
															<ul>
																<c:if test="${empty lineitem.previousStatus}">
																<li>${liStatus}</li>
																</c:if>
																<c:forEach var="lineItemStatus" items="${lineitem.previousStatus}">
																	<li>${lineItemStatus}</li>
																</c:forEach>
															</ul>
														</div>
														<div style="width:180px;">
															<c:if test="${statusAttribute eq 'CKOInComplete'}">
																<b style="color: red"><fmt:message key="orderSummary.priceNote"/></b>
															</c:if>
														</div>
													</div>
												</div>
											</c:if>
											</c:forEach>
										</div>
								</div>
								<div class="bottombuttonsOrderSummary">
									<div class="leftbtns">
								 		 <c:if test="${isPlaceOrder eq false}">
											<input type="button" id="CKOAllSelected" name="orderCKOSelected"  value="<fmt:message key="orderSummary.beginSelected.button"/>" />
										</c:if> 
										<input type="button" id="placeOrder" name="orderRecap"   value="<fmt:message key="orderSummary.gotoClosecall.button"/>" />
										<span id="closingOfferText" style="display:none">${closingOfferFlagInOrderSummary}</span>
									</div>
								</div>
								<div id="confirmedSec" style="display:none;">
								${confirmedSecurity}
								</div>
								<div id="dialog-confirm-adt" style="display:none;">
								<center>
								<img width="50" height="42" border="0" alt="Security" src="<%=request.getContextPath()%>/images/images_new/nav/Security.png"></img>
								<br/>
								Do you want to add it?	
								<br/><br/>
								<input type="button" id="secYes" value="Yes" onclick="goToSecurityProducts();"/>&nbsp;&nbsp;&nbsp;<input type="button" id="secNo" onclick="placeOrder();" value="No"/>						
								</center>								
								</div>
								<div class="dialogMultipleproducts" style="display:none;">
									<p>Multiple products from same provider may not be checked out. Please remove other products from the same provider and then CKO the current product.</p>
									<input type="button" style="margin-left: 360px;" class="dialogMultipleproductsClose" value="OK"/>				
								</div>
							</div>
						</section>
	</section>
</form>

</body>
</html>