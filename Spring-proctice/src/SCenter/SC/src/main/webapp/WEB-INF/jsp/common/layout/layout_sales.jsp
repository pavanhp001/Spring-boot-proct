<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache,java.util.Calendar,java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
 

<link rel="stylesheet" href="<c:url value='/css/screen.css'/>" />
<link href="<c:url value='/css/jquery-ui.css'/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/thoughtspotStyles.css'/>"/>

<!--<script src="<c:url value='/script/timer.js'/>"></script>
 
--><script src="<c:url value='/js/jquDD5B.js'/>"></script>
<script src="<c:url value='/js/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/js/backbuttons.js'/>"></script>

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
    margin-right: 17px;
}
p.grey{
	color:grey;
	font-weight:bold;
}
p.black{
	color:black;
	font-weight:bold;
}
/*div#scrollable_content {
  position: absolute;
  top: 50px;
  bottom: 0px;
  width: auto;
  overflow-y:auto;
 
}*/

#MiddleContentShell {
  border: 1px solid #9F9F9F;
  border-radius: 6px 6px 6px 6px;
  box-shadow: 1px 1px 1px #9A9A9A;
  float: left;
  margin-top: 5px;
  padding: 0 5px 5px;
  width: 100%;
}

.dialogue_content_textwidget fieldset {
	border-style: none;
}

.ambUserDetails {
    font-size: 14px;
    padding: 10px;
}

#messages {
	float: right;
    font-weight: bold;
    cursor: pointer;
}
p {
    line-height: 1.25;
    text-align: left;
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
 </head>

<body >
  	<script language="javascript">window.history.forward(0);</script>
<div id="concert_tool"><div id="concertHelp"><jsp:include page = "layout_hints.jsp"/></div></div>
		<div class="container">
			<header id="header">
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}"/>
	        <input type="hidden" id="sessionId" value="${pageContext.session.id}">
			<div class="headerinfocontainer">
			<div class="headerInformation">
		  
            	
            <c:if test="${pageId == 'startCall'}">
	            <div style="float:right; margin-left:990px;"> 
			        <form id="logout" action="<%=request.getContextPath()%>/salescenter/logout">
			           <input type="submit" name="logout" value="logout" class="css3StartButton" id="logout" />
			        </form>
		        </div>
            </c:if>
            </div>
            </div>
	</header>
	<div class="providerlogo">
				<div class="providerlogocont">
					<img  alt="${salescontext.scMap['referrer.businessParty.referrerId']}" 
					src="<%=request.getContextPath()%>/images/img/ALLogoMain.png" 
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
					<img class="hintIco inActiveIcon" id="help_hide" src="<%=request.getContextPath()%>/images/ObjectionBuster.png" alt="Objection Busters" title="Objection Busters"/>
					<img class="hintIco inActiveIcon" id="help_p_hide"  src="<%=request.getContextPath()%>/images/CompareMatchup.png" alt="Provider Comparison" title="Provider Comparison"/>
					<img class="hintIco inActiveIcon" id="HeavyweightMatchup_hide" src="<%=request.getContextPath()%>/images/HeavyweightMatchup.png" alt="HeavyweightMatchup" title="Lightweight Matchup"/>
					<%-- <img class="hintIco inActiveIcon" id="PivotAssist_hide" src="<%=request.getContextPath()%>/images/PivotAssist.png" alt="PivotAssist" title="Pivot Assist"/> --%>
					<img class="hintIco" id="newEmailId" src="<%=request.getContextPath()%>/images/DoNotCall.png" alt="Email" title="Email"/>
				</div>
			</div>
	
	
    
    
    <article id="mainsection">
				<aside id="left_aside">
					<tiles:insertAttribute name="discovery_notepad" />
				</aside>
				<section id="maincontent">
					<section class="navcontainer">
						<nav id="products">
							<ul class="productsNavDbld">
								<li class="prodcutsBtn1">
									<img src="../images/images_new/nav/Star_d.png" width="50" height="42" alt="Power Pitch" border="0" />
									<label id="productLabel" class="productLabel" value="PP" for="PP">P-Pitch</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/mixed-bundles-icon_d.png" width="50" height="42" alt="MIXEDBUNDLES" border="0" />
									<label id="productLabel" class="productLabel" value="MIXEDBUNDLES" for="SyntheticBundle"><fmt:message key="productContent.MIXEDBUNDLES" /></label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/Triples_d.png" width="50" height="42" alt="Triples" border="0" />
									<label id="productLabel" class="productLabel" value="Triples" for="Triples">Triples</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/Doubles_d.png" width="50" height="42" alt="Doubles" border="0" />
									<label id="productLabel" class="productLabel" value="Doubles" for="Doubles">Doubles</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/TV_d.png" width="50" height="42" alt="Video" border="0" />
									<label id="productLabel" class="productLabel" value="Video" for="Video">Video</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/Internet_d.png" width="50" height="42" alt="Internet" border="0" />
									<label id="productLabel" class="productLabel" value="PP" for="PP">Internet</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/Phone_d.png" width="50" height="42" alt="Power Pitch" border="0" />
									<label id="productLabel" class="productLabel" value="PP" for="PP">Phone</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/Security_d.png" width="50" height="42" alt="Security" border="0" />
									<label id="productLabel" class="productLabel" value="PP" for="Security">Security</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/Asis_d.png" width="50" height="42" alt="Asis" border="0" />
									<label id="productLabel" class="productLabel" value="Asis" for="Asis">As Is</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/Electric_d.png" width="50" height="42" alt="Electric" border="0" />
									<label id="productLabel" class="productLabel" value="Electric" for="Electric">Electric</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/Gas_d.png" width="50" height="42" alt="Gas" border="0" />
									<label id="productLabel" class="productLabel" value="Gas" for="Gas">Gas</label>
								</li>
								<li class="prodcutsBtn">
									<img src="../images/images_new/nav/Water_d.png" width="50" height="42" alt="Water" border="0" />
									<label id="productLabel" class="productLabel" value="Water" for="Water">Water</label>
								</li>
								<li class="prodcutsBtn2">
									<img src="../images/images_new/nav/Waste_d.png" width="50" height="42" alt="Waste" border="0" />
									<label id="productLabel" class="productLabel" value="Waste" for="Waste">Waste</label>
								</li>
								<li class="prodcutsBtn2">
	                        		<img src="<%=request.getContextPath()%>/images/images_new/nav/ApplianceProtection_d.png" width="46" height="39" alt="ApplianceProtection" border="0" />
	                           	 	<label id="productLabel" class="productLabel" value="ApplianceProtection" for="ApplianceProtection">Utility</label>
                            	</li>
							</ul>	
						</nav>
					</section>
        			<tiles:insertAttribute name="main_content" />
				</section>
				<aside id="main_aside">
				</aside>
			</article>
			
    <footer id="main_footer">
				<div class="footer_center">
					<div class="footer_content">
						<div class="endcall">
                             <c:choose>
	                            <c:when test="${pageId == 'startCall'}">
	                             <form id="end" action="<%=request.getContextPath()%>/salescenter/dispositions" method="post">
	                             <input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
								 <input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
	                        	<input type="button" name="startCall" value="Start Call" class="css3StartButton" id="startCall" /></input>
	                        	</form>
	                        </c:when>
	                        <c:otherwise>
	                        	 <form  name="endCallID"  id="endCallID" action="<%=request.getContextPath()%>/salescenter/closingcall" method="post">
	                        	 <c:if test= "${!isDispositions}">
	                        	 <input type="button" name="endCall" id="endCall" value="<fmt:message key="layout.endCall"/>" class="endcallbtn" /></input>
	                        		</c:if>
	                        		 </form>
	                        </c:otherwise>
	                        </c:choose>
						</div>
                          <div class="copyright">
							Copyright &copy; 2013 AL Inc. All Rights Reserved. ${buildVersion}
						</div>
						<c:if test="${ not empty salescontext.scMap['vdn']}">
								<div style="float:left; font-size:11px; margin-top:4px; width:6%;" >VDN:&nbsp;
								${salescontext.scMap['vdn']}
								</div>
						   </c:if>
						<div class="ambMsg">
							<div id="messages" ></div>
							 <div id="userDetails" >
								<span>${username}</span>
								<span style="margin-left: 20px;">${phoneId}</span>
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
				</div>
			</footer>
	<div id="thoughtSpotTool"><div id="thoughtSpotHelp"><jsp:include page = "thoughtspot_data.jsp"/></div></div>              	
</div>
</body>

 