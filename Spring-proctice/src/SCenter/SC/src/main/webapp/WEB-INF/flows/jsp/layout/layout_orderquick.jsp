<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache,java.util.Calendar,java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/main.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/tabs.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/orderrecap.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/jquery-ui.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/thoughtspotStyles.css'/>"/>
<script src="<c:url value='/script/timer.js'/>"></script>
<script src="<c:url value='/js/backbuttons.js'/>"></script>
<script src="<c:url value='/js/js_new/jquery-1.9.0.min.js'/>"></script>
<script src="<c:url value='/js/js_new/jquDD5B.js'/>"></script>
<script src="<c:url value='/js/js_new/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/js/angular.min.js'/>"></script>
   <script src="<c:url value='/script/recommendations.js'/>"></script>

<script>
$(document).ready(function() {
	var absURL = document.URL;
	document.getElementById("currentPageUrlPath").value = absURL;
	$(document).bind("dragstart", function() {
        return false;
	});
	var orderId='${order.externalId}';
	var customerName = "${order.customerInformation.customer.firstName} "+"${order.customerInformation.customer.lastName} ";
	if('${order.customerInformation.customer.middleName}' != ''){
		customerName = "${order.customerInformation.customer.firstName} "+"${order.customerInformation.customer.middleName} " +"${order.customerInformation.customer.lastName}";
	}
	if('${order.customerInformation.customer.title}' != ''){
		customerName = "${order.customerInformation.customer.title} "+customerName;
	}
	customerName = customerName+" ${order.customerInformation.customer.nameSuffix}";
	var customerEmailId = '${order.customerInformation.customer.bestEmailContact}';
	var contactNumber='${order.customerInformation.customer.bestPhoneContact}';
	var emailId ="donotcall@AL.com";
	var subject ="Do Not Call";
	var addInfo = "Did customer ask for copy of Do Not Call policy? (Type ‘y’ if they did AND make sure the customer provided a valid email address above):";
	var emailBody ="Order Number = "+orderId+"\r\nCustomer Name = "+customerName+"\r\nBest Contact Number = "+contactNumber+"\r\nEmail = "+customerEmailId+"\r\n\r\n"+addInfo;
	emailBody = encodeURIComponent(emailBody)
	var email="mailto:"+emailId+"&subject="+subject+"&body="+emailBody;
	if(customerName.trim() =="Default Customer" || customerName.trim() == ""){
		$("#DoNotCall").removeAttr("href");
		console.log("::Default Customer::")
	}else{
		$("#DoNotCallImg").removeClass("inActiveIcon");
		$("#DoNotCall").attr("href",email);
	}
	$("input[type=text],input[type=email],input[type=number]").attr("autocomplete","nope");
});

function openNewTab(url){
	url = url + "?select2-drop-mask=" + "donotcall@AL.com" + "&mceu_30=addInfo"+"&Subject="+"Did customer ask";
	window.open(url,"", "scrollbars=yes, width=1000, height=550");	
	};
function showUsername(){
	
	$('div#messages').hide();
	$('div#userDetails').show();
	}
function hideUsername(){
	$('div#userDetails').hide();
	$('div#messages').show();
	}
var config = {
        contextPath: '${pageContext.request.contextPath}'
};
	var cometd_url = '${cometd_url}';

	var username = '${username}';
    var phoneId = '${phoneId}';
</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/org/cometd.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/org/cometd/ReloadExtension.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery.cometd.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery.cometd-reload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application.js"></script>
<style>		
	.dialogue_content_textwidget fieldset {
		border-style: none;
	}
</style>
<style type="text/css">
	.notepadTable td{
		border:1px solid #d3d3d3;
		//border-color:#d3d3d3;
	}
	.notepadTable td{
		padding: 2px;
	}
</style>

<%
Calendar cal=Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
%>
</head>


	<body>
  	<script language="javascript">window.history.forward(0);</script>
  	<div id="concert_tool"><div id="concertHelp"><jsp:include page = "layout_hints.jsp"/></div></div>
		<div class="container">
			<header id="header">
				<div class="headerinfocontainer">
					<div class="headerInformation">
						<div class="headerTextInformation">
							<span class="topRow">Call Branded:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<c:set var="cobrand_name" value="${salescontext.scMap['referrer.businessParty.cobrandName']}"></c:set>
								<c:choose>
									<c:when test="${fn:contains(cobrand_name, 'N/A')}">
									    <c:out value="AL"/>									
									</c:when>
									<c:otherwise>
										<c:out value="${cobrand_name}"/>
									</c:otherwise>
								</c:choose>
							</span><br/>
							<c:if test="${ not empty salescontext.scMap['referrer.businessParty.referrerPhoneNum']}">
								<span class="bottomRow">Callback Number:&nbsp;
								${salescontext.scMap['referrer.businessParty.referrerPhoneNum']}
								</span>
							</c:if>
						</div>
						<div class="headerTextInformation2">
							<c:if test="${ not empty salescontext.scMap['referrer.businessParty.referrerName']}">
								<span class="topRow">Partner Name:&nbsp;
									${salescontext.scMap['referrer.businessParty.referrerName']}
								</span>
							</c:if>
							<br/>
						<c:if test="${ not empty salescontext.scMap['customer.confirmation.number']}">
							<span class="bottomRow">Partner ID#:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								${salescontext.scMap['customer.confirmation.number']}
							</span>
						</c:if>
						</div>
		            	
					</div>
					<!--
					<nav id="main_menu">
						<ul>
							<li><a href="#">Performance</a></li>
							<li><a href="#">Settings</a></li>
							<li><a href="#">Help</a></li>
						</ul>						
						<form class="searchform">
							<input class="searchfield" type="text" onblur="if (this.value == '') {this.value = 'Search...';}" onfocus="if (this.value == 'Search...') {this.value = '';}" value="Search..." />
							<input class="searchbutton" type="button" value="" />
						</form>
					</nav>  
			  -->
				</div>
				<div class="headernavcontainer"></div>
			</header>
			<div class="providerlogo">
				<div class="providerlogocont">
					<img  alt="${salescontext.scMap['referrer.businessParty.referrerId']}" 
					src="${providersImageLocation}${salescontext.scMap['referrer.businessParty.referrerId']}.jpg" 
					onError="this.onerror=null;this.src='<%=request.getContextPath()%>/images/img/ALLogoMain.png';"  />	
				</div>
			</div>
			<%-- <div class="concertlogo">
				<div class="concertlogocont">
					<img src="<%=request.getContextPath()%>/images/images_new/concert_logo.png" />
				</div>
			</div> --%>
			<div class="concertlogo">
				<div class="concertlogocont">
					<div class="helpCenter">Help Center</div> 
					<img class="hintIco" id="help" src="<%=request.getContextPath()%>/images/ObjectionBuster.png" alt="Objection Busters" title="Objection Busters"/>
					<img class="hintIco inActiveIcon" id="help_p"  src="<%=request.getContextPath()%>/images/CompareMatchup.png" alt="Provider Comparison" title="Provider Comparison"/>
					<img class="hintIco inActiveIcon" id="HeavyweightMatchup" src="<%=request.getContextPath()%>/images/HeavyweightMatchup.png" alt="HeavyweightMatchup" title="Lightweight Matchup"/>
					<img class="hintIco inActiveIcon" id="PivotAssist" src="<%=request.getContextPath()%>/images/PivotAssist.png" alt="PivotAssist" title="Pivot Assist"/>
					<img class="hintIco inActiveIcon" id="newEmailId" src="<%=request.getContextPath()%>/images/DoNotCall.png" alt="Email" title="Email"/>
				</div>
			</div>
			
			<article id="mainsection">
				<aside id="left_aside">
	            	<tiles:insertAttribute name="realtime_content" />
	            	
	                <tiles:insertAttribute name="discovery_notepad" />
				</aside>
				<section id="maincontent">
					<section class="navcontainer">
						<tiles:insertAttribute name="product_menu_content" />
					</section>
					<input type="hidden" id="CKOCompletedLineItems" value='${CKOCompletedLineItems}'>
            		<input type="hidden" id="sessionId" value="${pageContext.session.id}"/>
        			<tiles:insertAttribute name="main_content" />
				</section>
				<aside id="main_aside">
					<tiles:insertAttribute name="order_summary_content" />
					<tiles:insertAttribute name="cust_summary_content" />
				</aside>
			</article>
			
			<footer id="main_footer">
				<div class="footer_center">
				<input type="hidden" id="currentPageUrlPath" name="currentPageUrlPath" value="" >
						
						<div class="endcall">
							&nbsp;
						</div>
						<div class="copyright"><!-- <fmt:message key="layout.footer.msg2"/> -->
							Copyright &copy; 2013 AL Inc. All Rights Reserved. ${buildVersion}
						</div>
						<c:if test="${ not empty salescontext.scMap['vdn']}">
								<div style="float:left; font-size:11px; margin-top:4px; width:6%;" >VDN:&nbsp;
								${salescontext.scMap['vdn']}
								</div>
						   </c:if>
					   <div class="ambMsg">
							<div id="messages" >
						</div>
							 <div id="userDetails" >
								<span>${username}</span>
							    <span style="margin-left: 10px;">${phoneId}</span>
							    <span style="margin-left: 10px;">${referrerFlow}</span>
								<span class="guid"><%=request.getSession().getAttribute("GUID")!=null?request.getSession().getAttribute("GUID"):"" %></span>
						        <c:if test="${ not empty sessionScope.siftFileVersion}">
                                    <span class="siftVesion"> Sift:&nbsp; ${siftFileVersion}</span>
                                 </c:if>
						      </div>
						      <input type="hidden" id="agentExtId" value="${agentExtId}">
						 </div>
						 <span class="call-Time" ><%= sdf.format(cal.getTime()) %></span>
						<div class="poweredBy">
							<div class="poweredByImage"></div>
						</div>
						<div id="domMessage_spinner" style="display:none;"> 
							<img src="<%=request.getContextPath()%>/images/loading.gif" border="0"/>
		    				<h2>Loading</h2> 
						</div>
					</div>
			</footer>
			<div id="thoughtSpotTool"><div id="thoughtSpotHelp"><jsp:include page = "thoughtspot_data.jsp"/></div></div>
		</div>
	</body>
</html>
