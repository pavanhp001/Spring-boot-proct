<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache,java.util.Calendar,java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en" ng-app="concert">
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/thoughtspotStyles.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/main.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/jquery-ui.css'/>"/>

<script type="text/javascript" src="<c:url value='/js/js_new/jquery-1.9.0.min.js'/>"></script>
<script src="<c:url value='/js/js_new/jquDD5B.js'/>"></script>
<script src="<c:url value='/js/js_new/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/js/angular.min.js'/>"></script>
<script src="<c:url value='/script/timer.js'/>"></script>
<script src="<c:url value='/js/backbuttons.js'/>"></script>
<style>
#closingOfferPage {
  color: red;
  font-size: 14px;
  margin: 0;
  text-align:center;
}
</style>
<script>
$(document).ready(function(){
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
	
</script>
<script>
$(document).ready(function() {
	var absURL = document.URL;
	document.getElementById("currentPageUrlPath").value = absURL;
});

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

function confirmEndCall(){
	try{
		getClosingOfferFlag();
		$("#endcall-dialog-confirm").dialog( {
			resizable : false,
			title : "Warning",
			height : 197,
			width : 477,
			modal : true,
			zIndex : 99999
		});
	}catch (err) {
		alert(err)
	}
}

function getClosingOfferFlag(){
	var path = $("#contextPath").val();
	var url = path+"/salescenter/getClosingOfferFlag";
	var data = {};
	$.ajax({
		type: 'GET',
		url: url,
		data: data,
		complete: getClosingOfferFlagRes
	});
}

var getClosingOfferFlagRes = function(data){
	if(data == "sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else if(data.responseText=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		if(data.responseText != ""){
          $("#closingOfferPage").css("display" , "block");
          $("#closingOfferPage").html(data.responseText);
		}
	}
}



function goToCloseCallPage()
{
	savePageHtml(false, "");
	$("#endcall-dialog-confirm").dialog('close');
	$("form#endCallForm").submit();
}

function hidePopUp()
{
	$("#endcall-dialog-confirm").dialog('close');
}

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

<%
Calendar cal=Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
%>
</head>

	<body onload="updateTimer()">
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
		            	<!--  <div id="userDetails" style="float:left;">
							<span class="boldFont">Username: </span><span>${username}</span>
							<span class="boldFont">PhoneId: </span><span>${phoneId}</span>
					    </div> -->
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
						<nav id="products">
							<ul class="productsNavDbld">
								<li class="prodcutsBtn1">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Star_d.png" width="46" height="39" alt="Power Pitch" border="0" />
									<label id="productLabel" class="productLabel" value="PP" for="PP">P-Pitch</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/mixed-bundles-icon_d.png" width="46" height="39" alt="MIXEDBUNDLES" border="0" />
									<label id="productLabel" class="productLabel" value="MIXEDBUNDLES" for="SyntheticBundle"><fmt:message key="productContent.MIXEDBUNDLES" /></label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Triples_d.png" width="46" height="39" alt="Triples" border="0" />
									<label id="productLabel" class="productLabel" value="Triples" for="Triples">Triples</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Doubles_d.png" width="46" height="39" alt="Doubles" border="0" />
									<label id="productLabel" class="productLabel" value="Doubles" for="Doubles">Doubles</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/TV_d.png" width="46" height="39" alt="Video" border="0" />
									<label id="productLabel" class="productLabel" value="Video" for="Video">Video</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Internet_d.png" width="46" height="39" alt="Internet" border="0" />
									<label id="productLabel" class="productLabel" value="PP" for="PP">Internet</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Phone_d.png" width="46" height="39" alt="Power Pitch" border="0" />
									<label id="productLabel" class="productLabel" value="PP" for="PP">Phone</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Security_d.png" width="46" height="39" alt="Security" border="0" />
									<label id="productLabel" class="productLabel" value="PP" for="Security">Security</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Asis_d.png" width="46" height="39" alt="Asis" border="0" />
									<label id="productLabel" class="productLabel" value="Asis" for="Asis">As Is</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Electric_d.png" width="46" height="39" alt="Electric" border="0" />
									<label id="productLabel" class="productLabel" value="Electric" for="Electric">Electric</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Gas_d.png" width="46" height="39" alt="Gas" border="0" />
									<label id="productLabel" class="productLabel" value="Gas" for="Gas">Gas</label>
								</li>
								<li class="prodcutsBtn">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Water_d.png" width="46" height="39" alt="Water" border="0" />
									<label id="productLabel" class="productLabel" value="Water" for="Water">Water</label>
								</li>
								<li class="prodcutsBtn2">
									<img src="<%=request.getContextPath()%>/images/images_new/nav/Waste_d.png" width="46" height="39" alt="Waste" border="0" />
									<label id="productLabel" class="productLabel" value="Waste" for="Waste">Waste</label>
								</li>
								<li class="prodcutsBtn2">
	                        		<img src="<%=request.getContextPath()%>/images/images_new/nav/ApplianceProtection_d.png" width="46" height="39" alt="ApplianceProtection" border="0" />
	                            	<label id="productLabel" class="productLabel" value="ApplianceProtection" for="ApplianceProtection">Utility</label>
                            	</li>								
							</ul>	
						</nav>
					</section>
					<input type="hidden" id="CKOCompletedLineItems" value='${CKOCompletedLineItems}'>
            		<input type="hidden" id="sessionId" value="${pageContext.session.id}">
            		<input type="hidden" id="callAlertTimeout" value='${callAlertTimeout}'>
        			<input type="hidden" id="newAMBMessageData" value='${newAMBMessageData}'>
        			<tiles:insertAttribute name="main_content" />
				</section>
				<aside id="main_aside">
					<tiles:insertAttribute name="sales_summary_content" />
				</aside>
			</article>
			
			<footer id="main_footer">
				<div class="footer_center">
					
					
						
						<div class="endcall">
							<form name="endCallForm" id="endCallForm" action="${flowExecutionUrl}" method="post">
							<input type="hidden" id="currentPageUrlPath" name="currentPageUrlPath" value="" >
							<input type="hidden" id="pageTitle" name="pageTitle" value="Close Call" >
							<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
							<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							<input type="hidden" id="_eventId" name="_eventId"	value="endCallEvent">
								<c:if test="${!isClosingCall|| !isWarmTransfer}">
										<input type="hidden" id="endCall" name="endCall" value="<fmt:message key="layout.endCall"/>"/>
			                        	<input type="button" id="endCall" name="endCall" value="<fmt:message key="layout.endCall"/>" class="endcallbtn" onClick="confirmEndCall();"/>
			                    </c:if>
							</form>
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
					</div>
				<div id="domMessage_spinner" style="display:none;"> 
					<img src="<%=request.getContextPath()%>/images/loading.gif" border="0"/>
    				<h2>Loading</h2> 
				</div>
				<div id="dialog-confirm" style="display:none;">
					<center>
						We are moving you to a new call
						<br/>
						<br/>
						<input type="hidden" id="isConfirmedToSubmit" value="no">
						<input type="button" id="custAuthYes" name="custAuth" onClick="goToIdlePage();" value="Ok"/>
					</center>
					<br/>
				</div>
				<div id="endcall-dialog-confirm" style="display:none;">
					<center>
						Are you sure you want to end the session?
						<br/>
						<br/>
						<input type="button" id="confirmYes" name="confirmYes" onClick="goToCloseCallPage();" value="Yes"/>
						<input type="button" id="confirmNo" name="confirmNo" onClick="hidePopUp();" value="No"/>
					</center>
					<br/>
					<span id="closingOfferPage" style="display:none;"></span>
				</div>
			</footer>
			<div id="thoughtSpotTool"><div id="thoughtSpotHelp"><jsp:include page = "thoughtspot_data.jsp"/></div></div> 
		</div>
	</body>
</html>