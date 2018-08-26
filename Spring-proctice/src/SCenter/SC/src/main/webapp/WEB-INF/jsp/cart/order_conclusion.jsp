<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title><fmt:message key="${title}"/></title>


<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
<script src="<c:url value='/script/lineItem.js'/>"></script>
<script src="<c:url value='/js/jquery.maskedinput.js'/>"></script>
<script type="text/javascript">
$(document).ready(function() {
	
	$("#forward").click(function(){
		//Here we setting saveoffers LineitemExternalID in body level of page for score capturing.
		$( "body" ).data( "li_externalId" , "${offerLineItemExtID}");
		savePageHtml(false, "");
	});
});
</script>

<style>
.bubble {
  color: red;
  font-size: 14px;
  margin: 0;
  text-align:center;
  position:absolute;
  margin: 0 0 0 425px;
}
#dialogue_content p {
  font-family: arial;
  font-size: 16px;
  margin: 0 0 7px;
}
.bottombuttons {
    left: 0;
    position: absolute;
    top: 570px;
    width: 100%;
    z-index: 1000000;
}
.rightbtns{
margin-left:915px;
position:absolute;
}
.widget_container{
	/*min-height: 90%;
	height: 50%;*/
	width: 999px;
	height: 230px;
	float:left;
	background-color:#e5e0d9;
}
.pagecontainer{
	border: 1px solid #616264;
	background-color: #e5e0d9;
	display:table-cell;
	width: 997px;
	height: 230px;
	vertical-align:middle;
	text-align:center;
}
</style>
</head>

<body onload="updateTimer();">
	
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
								<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
								<span class="cell pageTitle"><fmt:message key="closingcall.header"/></span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
					<section id="content">
						<form id="closingcall" name="closingcall" action="<%=request.getContextPath()%>/salescenter/home" method="post">
							<input type="hidden" name="preURL" id="preURL" value="" />
							<input type="hidden" id="emailFlag" name="emailFlag" value="">
							<input type="hidden" id="pageTitle" name="pageTitle" value="Close Call">
							<input type="hidden" id="isWarmTransferEnabled"  value="${isWarmTransferEnabled}" name="isWarmTransferEnabled">
							<input type="hidden" id="isTPVEnabled"  value="${isTPVEnabled}" name="isTPVEnabled">
							<input type="hidden" id="orderId" name="orderId" value="${orderId}">
							<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
							<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							<input type="hidden" id="requiresCSAT" name="requiresCSAT" value="<%=request.getSession().getAttribute("requiresCSAT")%>">
			                <input type="hidden" id="salesCallId" name="salesCallId" value="<%=request.getSession().getAttribute("salesCallId")%>">
			                <input type="hidden" id="ambDisconnectdatetime" name="ambDisconnectdatetime" value="<%=request.getSession().getAttribute("ambDisconnectdatetime")!=null?request.getSession().getAttribute("ambDisconnectdatetime"):""%>">
			                <input type="hidden" id="addressId" name="addressId" value="<%=request.getSession().getAttribute("addressId")%>">
			                <input type="hidden" id="urlPath" name="urlPath" value="<%=request.getSession().getAttribute("urlPath")%>">
			                <input type="hidden" id="customerId" value="<%=request.getSession().getAttribute("customerID") %>" name="customerId" />
			                <input type="hidden" id="offerLineItemExtID" value="${offerLineItemExtID}" name="offerLineItemExtID" />
							<input type="hidden" id="utilityLineItemExtID" value="${utilityLineItemExtID}" name="utilityLineItemExtID" />
							<div class="contentwrapper">
							<section id="dialogue_wrapper">						   
								<section id="dialogue_content"> 
									<c:choose>
	                                         <c:when test="${isDialoguesFromDrupal}">
		                                           ${dialoguesFromDrupal}
	                                         </c:when>
	                                     <c:otherwise>
		                                    <c:forEach var="dataFieldType" items="${dataFieldList}">
			                                   <p class="black">${dataFieldType.text}</p>
		                                    </c:forEach>
	                                    </c:otherwise>
                                     </c:choose> 
                                  </section>
									<div id="dialogue_content_balloon"></div>						
							</section>
								<div id="action_wrapper">
									<div class="widget_container">
										<div class="pagecontainer" align="center">
											
										</div>
									</div>
								</div>
								<div class="bottombuttons">
								    <c:choose>
										<c:when test="${isTPVEnabled}">
										   <span class="bubble">Account holder name: ${tpvAccountHolderName}   &nbsp;&nbsp;&nbsp;   Next Page: TPV</span>
										</c:when>
										<c:otherwise>
											<c:if test="${isWarmTransferEnabled}">
											<span class="bubble">Account holder name: ${accountHolderName}   &nbsp;&nbsp;&nbsp;  Next Page: Warm Transfer</span>
											</c:if>
										</c:otherwise>
									</c:choose>
									<div class="rightbtns">
										<input type="submit" id="forward" name="" value="Forward >"  />
									</div>
								</div>
							</div>
							</form>
						</section>
					</section>
</body>
</html>
