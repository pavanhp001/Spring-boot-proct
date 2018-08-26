<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title><fmt:message key="title.orderRecap"/></title>
<link media="all" href="<c:url value='/style/utils.css'/>" type="text/css" rel="stylesheet">
<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
<script src="<c:url value='/script/lineItem.js'/>"></script>

<script>
	$(document).ready(function() {

		/*
		 * Below code is to handled TBD logic for real time products 
		 
		if(!isNaN($("#hiddenBaseMonthlyNonPromotionPrice").val())){
			var baseMonthlyNonPromotionValue = parseFloat($("#hiddenBaseMonthlyNonPromotionPrice").val());
			if(baseMonthlyNonPromotionValue<0){
				$("#baseMonthlyNonPromotionPriceSPId").text("TBD");
				$("#baseMonthlyNonPromotionPriceTaxSPId").css("display", "none");
				
			}else{
				$("#baseMonthlyNonPromotionPriceSPId").text("$"+(baseMonthlyNonPromotionValue.toFixed(2)));
				$("#baseMonthlyNonPromotionPriceTaxSPId").removeAttr('style');
			}
		}
		*/
		
		$("div#or_arrowsbtn").click(basepricetoggle);
		$("div#or_oidetarrowsbtn").click(orderitemdetailstoggle);
		$("#next").click(function(){
			var confirmOrder = $('#confirmOrder').is(':checked');
			   if(confirmOrder){
				   var auth = $("input[name='authorizeOrder']:checked").val();
				   if(auth != undefined){
					     if(auth == "yes"){
						      if (confirm("Are you sure you want to place this order?"))
						        {
						    		//save the html before submit
						    	  	savePageHtml(false, "");
							     //submit
						        }
						      else
						        {
						        	return false;
						        } 
					     }else{
					    	//save the html before moving to order summary page.
								savePageHtml(false, "");
					    	 	var path = $("input#contextPath").val();
					    	 	var id = $("input#orderId").val();
						        var action = path +"/rest/summary/"+id;
						        $("form#[name='orderSummaryRecap']").attr("action", action);
						     }
				    }else{
					      alert("Please choose the Order Authorization");
					      return false;
				      }
			   }else{
			     alert("Please check the Mandatory disclaimer(s)");
			     return false;
			    }
		});
	});
	function basepricetoggle(){
		var div =$(this).parent().parent();
		if($(this).hasClass("maximize")){
			$(this).removeClass('or_id_row2').addClass('or_id_row2_1');
			div.find("#or_basemonthlyprice").show();
			$(this).removeClass('maximize').addClass('minimize');
		}else{
			$(this).removeClass('or_id_row2_1').addClass('or_id_row2');
			div.find("#or_basemonthlyprice").hide();
			$(this).removeClass('minimize').addClass('maximize');
		}
	}
	function orderitemdetailstoggle(){
		var div =$(this).parent().parent();
		if($(this).hasClass("maximize")){
			div.find("#or_orderitemdetdata").show();
			$(this).removeClass('maximize').addClass('minimize');
		}else{
			div.find("#or_orderitemdetdata").hide();
			$(this).removeClass('minimize').addClass('maximize');
		}
	}

	function displayButton(selValue){
		if(selValue == "yes"){
			$('#popUpSubmit').show();
			$('#popUpForward').hide();
			$('#isConfirmedToSubmit').val("yes");
		}
		else if(selValue == "no"){
			$('#popUpForward').show();
			$('#popUpSubmit').hide();
			$('#isConfirmedToSubmit').val("no");
		}
   }
</script>
<style>
.bottombuttons {
  top: 10px;
  position: relative;
  left: -6px;
}

.coaching 
{
        color: green;
      
}
.Suggested
{
 color: grey;
 
}
.widget_container_or_1 {
  background-color: #FFFFFF;
  float: left;
  height: 275px;
  vertical-align: top;
  width: 997px;
}
.pagecontainer_or_1 {
  height: 275px;
  overflow-y: auto;
  padding: 0 10px 0 0;
  width: 987px;
}

</style>
</head>

<body onload="updateTimer();">
	<form method="POST"		action="<%=request.getContextPath()%>/rest/closeCallSale" name="orderSummaryRecap">
		<input type="hidden" name="addressId" id="addressId" value="${addressId}" />
		<input type="hidden" name="orderId" id="orderId" value="${order.externalId}" />
		<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
		<input type="hidden" id="pageTitle" name="pageTitle" value="Order Recap">
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
							<div class="contentwrapper">
								<section id="dialogue_wrapper">						   
									<section id="dialogue_content">
										<div style="overflow-x: hidden; width: 984px; overflow-y: auto; max-height: 165px;" id="content_new">
											<c:forEach var="dataFieldType" items="${dataFieldList}">
												<c:if test="${dataFieldType.description=='Mandatory'}">
													<c:set var="dataFieldText" value="${dataFieldType.text}"/>
													
													<c:choose>
		  												<c:when test="${fn:contains(dataFieldText, '{referrer.businessParty.name}')}">
		  													<c:set var="referrer_name_replaced" value="${salescontextDt.dtPartner}" />
		  													<c:set var="referrer_name" value="{referrer.businessParty.name}" />
		    												<c:set var="dataFieldText_replaced" value="${fn:replace(dataFieldText,referrer_name, referrer_name_replaced)}" />
		  												</c:when>
													  <c:otherwise>
														  <c:set var="dataFieldText_replaced" value="${dataFieldText}" />
													  </c:otherwise>
													</c:choose>
													
													<p class="black"><span class="red"><fmt:message key="orderRecap.mandatory"/>&nbsp;</span>${dataFieldText_replaced}</p>
		
												</c:if>
												<c:if test="${dataFieldType.description=='Coaching'}">
													<p><span class="coaching">${dataFieldType.text}</span></p>
												</c:if>
												<c:if test="${dataFieldType.description=='Suggested'}">
													<p><span class="Suggested">${dataFieldType.text}</span></p>
												</c:if>
												<c:if test="${dataFieldType.description!='Suggested' && dataFieldType.description!='Mandatory' && dataFieldType.description!='Coaching'}">
													<p  class="black"><span class="Suggested">${dataFieldType.text}</span></p>
												</c:if>
											</c:forEach>
										</div>
									</section>
									<div id="dialogue_content_balloon"></div>						
								</section>

								<div id="action_wrapper">
									<c:choose>
										<c:when test="${refferIdValue=='33713' || refferIdValue=='31708' || refferIdValue=='33493'}">
											<div class="widget_container_or_1">
												<div class="pagecontainer_or_1" align="center">
										</c:when>
										<c:otherwise>
											<div class="widget_container_or">
												<div class="pagecontainer_or" align="center">
										</c:otherwise>
									</c:choose>
									<c:forEach var="lineitem" items="${sortedLineItems}">
										<c:set var="productName" value="${lineitem.productName}"/>	
										<c:set var="displayLi" value="${lineitem.displayLineItem}"/>
										<c:set var="providerId" value="${lineitem.providerExtId}"/>
										<c:set var="detailType" value="${lineitem.lineItemDetailType}"/>
										<c:set var="statusAttribute" value='${lineitem.lineItemStatusAttribute}'/>
										<c:set var="lineItemName" value='${lineitem.lineItemName}'/>
										<c:set var="lineItemProviderName" value='${lineitem.lineItemProviderName}'/>
										<c:set var="OneTimePriceValue" value="${lineitem.oneTimePriceValue}"/>
										<c:set var="monthlyPriceAfterPromoEndsValue" value="${lineitem.monthlyPriceAfterPromoEnds}"/>
										<c:set var="baseMonthlyNonPromotionPrice" value='${lineitem.baseMonthlyNonPromotionPrice}'/>
										<c:set var="parentExternalId" value='${lineitem.parentExternalId}'/>
										<c:set var="desireDate2" value='${lineitem.secondDesiredDate}'/>
										<c:set var="desireDate1Time" value='${lineitem.firstDesiredTime}'/>
										<c:set var="desireDate2Time" value='${lineitem.secondDesiredTime}'/>
										<c:set var="productSource" value="${lineitem.productSorce}"/>
										<c:set var="falloutOrder" value='${lineitem.fallOutOrder}'/>
																	
										<c:if test="${displayLi == 'true'}" >
											<div class="product" >
												<c:set var="productExternalId" value='${lineitem.productExternalId}'/>
												<c:set var="productFeatureMap" value='${productFeatureNameMap[productExternalId]}'/>
															
																<!-- Form Content -->
																<div class="or_content_plogo">
																	<a href="#/order/variant/1/"> 
																		<img alt="${lineitem.providerExtId}" src="${providersImageLocation}${parentExternalId}.jpg"
																		 onError="this.onerror=null;this.src='${providersImageLocation}${lineitem.providerExtId}.jpg';">
																	</a>
																</div>
																<div class="or_content">
																	<div class="or_planname">
																		<c:choose>
																				<c:when test="${not empty productName && not empty lineitem.providerName}">
																					${lineitem.providerName}&nbsp;-&nbsp;${productName}
																				</c:when>
																				<c:otherwise>
																					${lineItemProviderName}&nbsp;-&nbsp;${lineItemName}
																				</c:otherwise>
																		</c:choose>
																	</div>
																	<div class="or_itemdetails">
																		<div class="or_basepricetoggle">
																			<c:if test="${hasFeatures[lineitem.lineItemExternalId] eq 'true'}">
																				<div id="or_arrowsbtn" class="maximize"></div>
																			</c:if>
																			
																		</div>
																		<table cellpadding="0" cellspacing="0" class="or_basemonthlyprice">
																			<tr class="or_id_row1">
																				<th><fmt:message key="provider.list.description"/></th>
																				<th><fmt:message key="orderSummary.quantity"/></th>
																				<th><span class="or_priceAlign" style="text-align: right;"><fmt:message key="orderSummary.price"/></span></th>
																			</tr>
																				<c:choose>
																					<c:when test="${productSource == 'REALTIME'&& providerId!=32416075}">
																						<tr id="or_id_row2" class="or_id_row2">
																							<td width="30%"><fmt:message key="orderSummary.basePackagePriceForRT"/></td>
																							<td width="20%">-</td>
																							<td  width="50%">
																								<span class="or_priceAlign" style="text-align: right;">
																									<fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyNonPromotionPrice}" maxFractionDigits="2" pattern="0.00"/>
																								</span> 
																								<span class="os_normal" >
																									<fmt:message key="orderRecap.plusTax" />
																								</span>
																							</td>
																						</tr>
																				</c:when>
																				<c:otherwise>
																					<tr id="or_id_row2" class="or_id_row2">
																						<td width="30%"><fmt:message key="orderSummary.basePackagePriceNonPromotion"/></td>
																						<td width="20%">-</td>
																						<td width="50%">
																							<input type="hidden" id="hiddenBaseMonthlyNonPromotionPrice" value="${baseMonthlyNonPromotionPrice}">
																							<span class="or_priceAlign" style="text-align: right;" id="baseMonthlyNonPromotionPriceSPId" >
																								<fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyNonPromotionPrice}" maxFractionDigits="2" pattern="0.00"/>
																							</span> 
																							<span class="os_normal" id="baseMonthlyNonPromotionPriceTaxSPId">
																							<fmt:message key="orderRecap.plusTax" />
																							</span>
																						</td>
																					</tr>
																					<tr id="or_id_row2" class="or_id_row2">
																						<td width="30%"><fmt:message key="orderSummary.basePackagePrice"/></td>
																						<td width="20%">-</td>
																						<td width="50%">
																							<span class="or_priceAlign">
																								<fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${baseMonthlyPriceMap[lineitem.lineItemExternalId]}" maxFractionDigits="2" pattern="0.00"/>
																							</span> 
																							<span class="os_normal">
																							<fmt:message key="orderRecap.plusTax" />
																							</span>
																						</td>
																					</tr>
																				</c:otherwise>
																			</c:choose>
																			
																			<tr class="or_id_row3" id="or_basemonthlyprice" style="display:none;">
																				<td colspan="3">
																					<table cellpadding="0" cellspacing="0" class="or_basemonthlyprice_tbl">
																					<!--Features-->
																						<c:forEach var="feature" items="${lineitem.featuresAndSelecetionsVO}">
																							<tr class="border">
																								<td width="30%">
																									<c:choose>
																										<c:when test="${not empty productFeatureMap[feature.featureExtrnalId]}">
																											${productFeatureMap[feature.featureExtrnalId]}
																										</c:when>
																										<c:otherwise>
																											${feature.featureExtrnalId}
																										</c:otherwise>
																									</c:choose>
																								</td>
																								<td width="20%">
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
																								<td width="50%">
																									<table width="100%">
																										<tbody width="100%">
																											<tr align="right">
																												<td style="border-bottom: 0px;padding-right:180px;">
																													<span  class="or_montsize"><fmt:message key="orderSummary.monthly"/>:</span>
																													<span ><fmt:message key="orderQuick.currency"/><fmt:formatNumber value="${feature.baseRecurringPrice}" maxFractionDigits="2" pattern="0.00"/></span>
																												</td>
																											</tr>
																											<tr align="right">
																												<td style="border-bottom: 0px;padding-right:180px;">
																													<span class="or_instsize"><fmt:message key="orderSummary.install"/>:</span>
																													<span><fmt:message key="orderQuick.currency"/><fmt:formatNumber value="${feature.baseNonRecurringPrice}" maxFractionDigits="2" pattern="0.00"/></span>
																												</td>
																											</tr>
																										</tbody>
																									</table>
																								</td>
																							</tr>
																						</c:forEach>
																						
																					</table>
																				</td>
																			</tr>
																			<tr class="or_id_row4">
																				<td width="30%"><fmt:message key="orderRecap.monthlyCost" /></td>
																				<td width="20%">-</td>
																				<td width="50%">
																					<span class="or_priceAlign" >
																						<fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${lineitem.monthlyTotal}" maxFractionDigits="2" pattern="0.00"/>
																					</span> 
																					<span class="os_normal">
																						<fmt:message key="orderRecap.plusTax" />
																					</span>
																				</td>
																			</tr>
																			<tr class="or_id_row4">
																				<td width="30%"><fmt:message key="orderRecap.installationCost" /></td>
																				<td width="20%">-</td>
																				<td width="50%">
																					<c:set var="installationFee" value="${lineitem.instalationTotal}"/>
																					<span style="text-align: right;margin-left:40px;float-left">
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
																					</span>
																				</td>
																			</tr>
																			
																			<!--One Time Fee-->
																			<c:if test="${ productSource == 'REALTIME' && statusAttribute == 'CKOComplete'&& providerId!=32416075}">
																				<tr class="or_id_row4">
																					<td width="30%"><fmt:message key="orderSummary.oneTimeFee"/></td>
																					<td width="20%">-</td>
																					<td width="50%" >
																						<span class="or_priceAlign" >
																							<fmt:message key="orderQuick.currency" />
																							<c:choose>
																								<c:when test="${not empty OneTimePriceValue }">
																									<fmt:formatNumber value="${OneTimePriceValue}" maxFractionDigits="2" pattern="0.00"/>
																								</c:when>
																								<c:otherwise>
																									0.00
																								</c:otherwise>
																							</c:choose>
																						</span>
																					</td>
																				</tr>
																			</c:if>
																		</table>
																	</div>
																	<c:choose>
															            <c:when test="${ not empty LineItemVo.promotionMap}">
															            	<c:set var="isPromotionAvail" value="false"></c:set>
																			<c:forEach var="promotionMap"	items="${LineItemVo.promotionMap}">
																				<c:if test="${promotionMap.key eq lineitem.lineItemNumber}">
																					<c:set var="isPromotionAvail" value="true"></c:set>
																					<div class="or_promotions">
																						<div class="or_promo_title"><fmt:message key="orderRecap.promotions"/></div>
																						<c:forEach var="applicableType"	items="${promotionMap.value}">
																							<div class="or_promo_title"><fmt:message key="orderRecap.promoDescription"/></div>
																							<div class="or_promo_desc">
																								${applicableType.promotion.description}
																							</div>
																							<div class="or_promoterms_title"><fmt:message key="orderRecap.promoTermsConditions"/></div>
																							<div class="or_promoterms_desc">
																								${applicableType.promotion.conditions}
																							</div>
																							<c:if test="${productSource == 'REALTIME'}">
																								<div class="or_promo_desc">
																									<font color="red">
																										<c:if test="${not empty monthlyPriceAfterPromoEndsValue }">
																											<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/><fmt:message key="orderQuick.currency" /><fmt:formatNumber value="${monthlyPriceAfterPromoEndsValue}" maxFractionDigits="2" pattern="0.00"/>
																										</c:if>
																										<c:if test="${empty monthlyPriceAfterPromoEndsValue }">
																											<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/>NA
																										</c:if>
																									</font>
																								</div>
																							</c:if>
																						</c:forEach>
																					</div>
																				</c:if>
																			</c:forEach>
																			<c:if test="${isPromotionAvail eq 'false' && productSource == 'REALTIME' && statusAttribute == 'CKOComplete'}">
																				<c:choose>
																					<c:when test="${not empty falloutOrder }">
																						<div class="or_promotions">
																				    		<div class="or_promo_title"><fmt:message key="orderSummary.Promotions"/></div>
																				        	<div class="or_promo_desc">
																								Not Available
																								<br/>
																								<font color="red">
																									<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/>Not Available
																								</font>
																				        	</div>
																				    	</div>  
																				    </c:when>
																				    <c:otherwise>
																				   		<div class="or_promotions">
																				   			<div class="or_promo_title"><fmt:message key="orderSummary.Promotions"/></div>
																							<div class="or_promo_desc">
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
																			 <div class="or_promotions">
																	    		<div class="or_promo_title"><fmt:message key="orderSummary.Promotions"/></div>
																	        	<div class="or_promo_desc">
																					Not Available
																					<br/>
																					<font color="red">
																						<fmt:message key="orderSummary.monthlyTotalAfterPromoEnds"/>Not Available
																					</font>
																	        	</div>
																	    	</div>  
																	    </c:when>
																	    <c:otherwise>
																	   		<div class="or_promotions">
																	   			<div class="or_promo_title"><fmt:message key="orderSummary.Promotions"/></div>
																				<div class="or_promo_desc">
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
																		
																	<c:if test="${not empty lineitem.schedulingStartDate || not empty desireDate2}">
																		<c:set var="date" value="${fn:substring(lineitem.schedulingStartDate, 0, 10)}"/>
																		<c:set var="startTime" value="${fn:substring(lineitem.schedulingStartDateStartTime, 0, 5)}"/>
																		<c:set var="endTime" value="${fn:substring(lineitem.schedulingStartDateEndTime,  0, 5)}"/>
																		<c:set var="time" value="${fn:substring(lineitem.schedulingStartTime,  0, 5)}"/>
																		<c:if test="${empty startTime}">
																			<c:set var="startTime" value="${time}"/>
																		</c:if>
																		<div class="or_reqinstdate">
																			<div class="or_reqinstdate_title"><fmt:message key="orderRecap.installDatesTimes" /></div>
																			<div>
																				<div class="or_reqinstdate_lbl"><fmt:message key="orderRecap.desiredDate"/>:</div>
																				<div class="or_reqinstdate_date"> 
																				<fmt:parseDate var="dateObj" value="${date}" type="DATE" pattern="yyyy-MM-dd"/>
																				<fmt:formatDate pattern="EEEE, MMMM d, yyyy"  value="${dateObj}"/>
																				<c:choose>
																					<c:when test="${not empty desireDate1Time}">
																						&nbsp;${desireDate1Time}
																					</c:when>
																					<c:otherwise>
																						<c:if test="${startTime != '00:00'}">
																						<fmt:parseDate var="startDateObj" value="${date} ${startTime}" type="DATE" pattern="yyyy-MM-dd HH:mm"/>
																						<fmt:parseDate var="endDateObj" value="${date} ${endTime}" type="DATE" pattern="yyyy-MM-dd HH:mm"/>
																							&nbsp;<fmt:formatDate pattern="h:mm a"  value="${startDateObj}"/>&nbsp;to&nbsp;<fmt:formatDate pattern="h:mm a"  value="${endDateObj}"/>
																						</c:if>
																					</c:otherwise>
																					
																				</c:choose>
																				</div>
																			</div>
																			<c:if test="${not empty desireDate2}">
																				<div>
																					<div class="or_reqinstdate_lbl"><fmt:message key="orderRecap.desiredDate2"/>:</div>
																					<div class="or_reqinstdate_date">
																					<fmt:parseDate var="dateObj" value="${desireDate2}" type="DATE" pattern="yyyy-MM-dd"/>
																				<fmt:formatDate pattern="EEEE, MMMM d, yyyy"  value="${dateObj}"/>&nbsp;${desireDate2Time} </div>
																				</div>
																			</c:if>
																		</div>
																	</c:if>

																	<c:if test="${not empty lineitem.scheduledStartDate || not empty lineitem.disconnectStartDate
																				 || not empty lineitem.actualStartDate}">
																				 
																	<div class="or_orderitemdetails">
																		<div class="or_orderitemdettoggle">
																			<div id="or_oidetarrowsbtn" class="maximize"></div>
																		</div>
																		
																		<div class="or_orderitemdetails_cont">
																				<div class="or_orderitemdet_title">Order Item Details</div>
																			<div id="or_orderitemdetdata" class="or_orderitemdetdata" style="display:none;">
																				<div class="or_orderitems_detlist">
																				
																						<div class="col1"><fmt:message key="orderRecap.importDates"/></div>
																						<div class="col2">
																							<div class="col2_item"><fmt:message key="orderRecap.schedDate"/>:</div>
																							<div class="col2_item"><fmt:message key="orderRecap.disconnectDate"/>:</div>
																							<div class="col2_item"><fmt:message key="orderRecap.actualDate"/>:</div>
																						</div>
																						<div class="col3">
																							<div class="col3_item">
																							<c:if test="${not empty lineitem.scheduledStartDate}">
																									
																										<c:set var="date" value="${fn:substring(lineitem.scheduledStartDate, 0, 10)}"/>
																										<c:set var="startTime" value="${fn:substring(lineitem.scheduledStartDateStartTime, 0, 5)}"/>
																										<c:set var="endTime" value="${fn:substring(lineitem.scheduledStartDateEndTime, 0, 5)}"/>
																										<c:set var="time" value="${fn:substring(lineitem.scheduledStartTime, 0, 5)}"/>
																										<c:if test="${empty startTime}">
																											<c:set var="startTime" value="${time}"/>
																										</c:if>
																										<fmt:parseDate var="dateObj" value="${date}" type="DATE" pattern="yyyy-MM-dd"/>
																										<fmt:formatDate pattern="EEEE, MMMM d, yyyy"  value="${dateObj}"/>
																										<c:if test="${startTime != '00:00'}">
																										<fmt:parseDate var="startDateObj" value="${date} ${startTime}" type="DATE" pattern="yyyy-MM-dd HH:mm"/>
																										<fmt:parseDate var="endDateObj" value="${date} ${endTime}" type="DATE" pattern="yyyy-MM-dd HH:mm"/>
																											&nbsp;<fmt:formatDate pattern="h:mm a"  value="${startDateObj}"/>&nbsp;to&nbsp;<fmt:formatDate pattern="h:mm a"  value="${endDateObj}"/>
																										</c:if>
																								</c:if>
																							</div>
																							<div class="col3_item">
																								<c:if test="${not empty lineitem.disconnectStartDate}">
																									
																										<c:set var="date" value="${fn:substring(lineitem.disconnectStartDate, 0, 10)}"/>
																										<c:set var="startTime" value="${fn:substring(lineitem.disconnectStartDateStartTime, 0, 5)}"/>
																										<c:set var="endTime" value="${fn:substring(lineitem.disconnectStartDateEndTime,  0, 5)}"/>
																										<c:set var="time" value="${fn:substring(lineitem.disconnectStartTime,  0, 5)}"/>
																										<c:if test="${empty startTime}">
																											<c:set var="startTime" value="${time}"/>
																										</c:if>
																										<fmt:parseDate var="dateObj" value="${date}" type="DATE" pattern="yyyy-MM-dd"/>
																										<fmt:formatDate pattern="EEEE, MMMM d, yyyy"  value="${dateObj}"/>
																										<c:if test="${startTime != '00:00'}">
																										<fmt:parseDate var="startDateObj" value="${date} ${startTime}" type="DATE" pattern="yyyy-MM-dd HH:mm"/>
																										<fmt:parseDate var="endDateObj" value="${date} ${endTime}" type="DATE" pattern="yyyy-MM-dd HH:mm"/>
																											&nbsp;<fmt:formatDate pattern="h:mm a"  value="${startDateObj}"/>&nbsp;to&nbsp;<fmt:formatDate pattern="h:mm a"  value="${endDateObj}"/>
																										</c:if>
																								</c:if>
																								</div>
																							<div class="col3_item">
																							<c:if test="${not empty lineitem.actualStartDate}">
																									
																										<c:set var="date" value="${fn:substring(lineitem.actualStartDate, 0, 10)}"/>
																										<c:set var="startTime" value="${fn:substring(lineitem.actualStartDateStartTime,  0, 5)}"/>
																										<c:set var="endTime" value="${fn:substring(lineitem.actualStartDateEndTime,  0, 5)}"/>
																										<c:set var="time" value="${fn:substring(lineitem.actualStartTime,  0, 5)}"/>
																										<c:if test="${empty startTime}">
																											<c:set var="startTime" value="${time}"/>
																										</c:if>
																										<fmt:parseDate var="dateObj" value="${date}" type="DATE" pattern="yyyy-MM-dd"/>
																										<fmt:formatDate pattern="EEEE, MMMM d, yyyy"  value="${dateObj}"/>
																										<c:if test="${startTime != '00:00'}">
																										<fmt:parseDate var="startDateObj" value="${date} ${startTime}" type="DATE" pattern="yyyy-MM-dd HH:mm"/>
																										<fmt:parseDate var="endDateObj" value="${date} ${endTime}" type="DATE" pattern="yyyy-MM-dd HH:mm"/>
																											&nbsp;<fmt:formatDate pattern="h:mm a"  value="${startDateObj}"/>&nbsp;to&nbsp;<fmt:formatDate pattern="h:mm a"  value="${endDateObj}"/>
																										</c:if>
																								</c:if>
																								</div>
																						</div>
																					
																				</div>
																		</div>
																	</div>
																	
																</div>
																</c:if>
																<!-- End Form Content -->
															</div>
														</div>
															</c:if>
														
												</c:forEach>
												</div>
								</div>
													<div class="orderplace_cont" >
														<div class="mandatoryDisclaimer">
														<p style="margin-bottom: 0px; margin-top: 5px; margin-left: 10px;"><span><fmt:message key="orderRecap.mandatoryAuthorization"/> </span><span style="vertical-align: top;"><input type="checkbox"  name="confirmOrder" id="confirmOrder"/></span></p>
														</div>
															<div class="orderplace_datacont">
																<span class="red" style="font-size : 16px;"><fmt:message key="orderRecap.mandatory"/></span>&nbsp;<fmt:message key="orderRecap.authConformation"/>
																<input type="radio" id="authOrderYes" name="authorizeOrder" value="yes"/> <fmt:message key="orderRecap.yes"/> 
																<input type="radio" id="authOrderNo" name="authorizeOrder" value="no"/> <fmt:message key="orderRecap.no"/> 
															</div>
															<div class="bottombuttons">
																<div class="leftbtns">
																	<input type="submit" id="next" class="submitBtn" value="Submit"/>
																</div>
															</div>
														</div>
							</div>
						</section>
					</section><!-- Content Full Cont -->
					
				</section>
				<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />

	</form>
	
		<div id="dialog-confirm" style="display:none;">
			<center>
				Did customer give authorization to place order?
				<br/>
				<input type="radio" id="custAuthYes"" name="custAuth" onClick="displayButton('yes');"/>Yes</input>&nbsp;&nbsp;&nbsp;<input type="radio" id="custAuthNo" name="custAuth" onClick="displayButton('no');"/>No</input>
			</center>
			<br/>
			<input type="button" id="popUpForward" value="Forward>" style="display:none; float:right;" onclick="goToIdlePage();"/>
			<input type="button" id="popUpSubmit" value="Submit Order" style="display:none; float:right;" onclick="goToIdlePage();"/>														
		</div>
		<input type="hidden" id="isConfirmedToSubmit" value="">
</body>
</html>
