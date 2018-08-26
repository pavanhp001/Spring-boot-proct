<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
 
<link rel="stylesheet" href="<c:url value='/css/css_new/main.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/css_new/dispositions.css'/>" />
<!-- <link rel="stylesheet" href="<c:url value='/css/nav.css'/>" /> -->
<link rel="stylesheet" href="<c:url value='/css/classes.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/text.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/formStyles.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/tabs.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/info.css'/>" />

<link rel="stylesheet" href="<c:url value='/css/screen.css'/>" />
<link href="<c:url value='/css/jquery-ui.css'/>" rel="stylesheet" type="text/css"/>

<script src="<c:url value='/js/js_new/jquery-1.9.0.min.js'/>"></script>
<script src="<c:url value='/script/timer.js'/>"></script>
 
<script src="<c:url value='/js/jquDD5B.js'/>"></script>
<script src="<c:url value='/js/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/js/backbuttons.js'/>"></script>

<script>
$(document).ready(function(){
	$(document).bind("dragstart", function() {
	     return false;
	});
});
</script>
<style>
input.css3StartButton {
  border-color: #31D639 #80E337 #3C6838 #3D8A04;
  background: -moz-linear-gradient(center top , #96C43D, #C2FF4B) repeat scroll 0 0 transparent;
  border: 1px solid #EB7373;
  border-radius: 10px 10px 10px 10px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.5), 0 0 2px #FFFFFF inset;
  cursor: pointer;
  font-family: Arial,Helvetica,sans-serif;
  font-size: 10px;
  padding: 2px 5px;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.2), 0 1px 0 rgba(89, 89, 89, 0.4);
}
p.grey{
	color:grey;
	font-weight:bold;
}
p.black{
	color:black;
	font-weight:bold;
}
p {
    line-height: 1.25;
    text-align: left;
}
/*div#scrollable_content {
  position: absolute;
  top: 50px;
  bottom: 0px;
  width: auto;
  overflow-y:auto;
 
}*/

.dialogue_content_textwidget fieldset {
	border-style: none;
}

#messages {
	float: right;
    font-size: 20px;
    font-weight: bold;
}
</style>


<script>

$(document).ready(function(){
	$('div#messages').mouseover();
	$('div#messages').mouseout();
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

</head>

<body onload="updateTimer()">
  <script language="javascript">window.history.forward(0);</script>
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
						<input type="hidden" id="agentExtId" value="${agentExtId}">
		            	<!--  <div id="userDetails" style="float:left;">
							<span class="boldFont">Username: </span><span>${username}</span>
							<span class="boldFont">PhoneId: </span><span>${phoneId}</span>
					    </div> -->
					</div>
					<!--<nav id="main_menu">
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
			<div class="concertlogo">
				<div class="concertlogocont">
					<img src="<%=request.getContextPath()%>/images/images_new/concert_logo.png" />
				</div>
			</div>
         

			<article id="mainsection">
				<aside id="left_aside">
					<tiles:insertAttribute name="discovery_notepad" />
				</aside>
				<aside id="main_aside">
				    <% request.setAttribute("closeCall", true); %>
					<tiles:insertAttribute name="order_summary_content" />				
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
            		<input type="hidden" id="sessionId" value="${pageContext.session.id}">
        			<tiles:insertAttribute name="main_content" />
				</section>
			</article>
			<footer id="main_footer">
				<div class="footer_center">
					<div class="cell terms font9"><!-- <fmt:message key="layout.footer.message"/> -->
						Only users with express authorization from AL, Inc. may use this system. Unauthorized use is strictly prohibited and may expose you to legal liability. Violations will be logged and reported.
					</div>
					<div class="footer_content">
						<div class="copyright"><!-- <fmt:message key="layout.footer.msg2"/> -->
							Copyright &copy; 2013 AL Inc. All Rights Reserved. ${buildVersion}
						</div>
						<div class="poweredBy">
							<div class="poweredByImage"></div>
						</div>
					</div>
				</div>
			</footer>
                	
</div>
</body>
</html>
