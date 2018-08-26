<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>

<link rel="stylesheet" href="<c:url value='/css/main.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/nav.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/classes.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/text.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/formStyles.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/tabs.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/info.css'/>" />
		
<link href="<c:url value='/css/jquery-ui.css'/>" rel="stylesheet" type="text/css"/>

<script src="<c:url value='/js/js_new/jquery-1.9.0.min.js'/>"></script>
<script src="<c:url value='/js/jquDD5B.js'/>"></script>
<script src="<c:url value='/js/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/script/timer.js'/>"></script>

<script>
$(document).ready(function(){
	$(document).bind("dragstart", function() {
	     return false;
	});
});

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
		</style>
</head>
<body onload="updateTimer()">
  <script language="javascript">window.history.forward(0);</script>
<div id="wrapper">
	<header id="main_header">
    	<div id="SYP_toolbar">
        	<div id="SYP_title"><fmt:message key="layout.SYP.title" /></div>
            <div id="AL_title"><img src="<%=request.getContextPath()%>/images/img/ALLogoMain.png" width="149" height="20" alt="AL" border="0" /></div>
        </div>
		 <div id="SYP_ALbar">
            <div class="row">
            <span class="cell rightpadding">${salescontextDt.dtPartner}</span>
            <span class="cell rightpadding">${salescontextDt.dtPartnerAccountId}</span>
             <span class="cell">${salescontextDt.dtNameFirst} ${salescontextDt.dtNameMiddle} ${salescontextDt.dtNameLast}</span>
            </div>
            </div>
	</header>
    <div id="contentWrapper">
            <aside id="left_aside">
            	<tiles:insertAttribute name="realtime_content" />
            </aside>
        <section id="main_section">
        	<section id="MiddleContentShell">
        	<input type="hidden" id="sessionId" value="${pageContext.session.id}">
            	<nav id="products">
                	<ul class="productsNav">
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendations" >
                        		<img src="<%=request.getContextPath()%>/images/nav/Star.png" width="28" height="28" alt="Power Pitch" border="0" />
                            <label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.pp" /></label>
                            </a>
                        </li>
                        <li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/MIXEDBUNDLES" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/mixed-bundles-icon.png" width="28" height="28" alt="Triples" border="0" />
	                           <label id="productLabel" class="productLabel" value="Bundles" for="Bundles"><fmt:message key="productContent.mixedbundles" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/TRIPLE_PLAY" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/Triples.png" width="28" height="28" alt="Triples" border="0" />
	                           <label id="productLabel" class="productLabel" value="Triples" for="Triples"><fmt:message key="productContent.triples" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/DOUBLE_PLAY" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/Doubles.png" width="28" height="28" alt="Doubles" border="0" />
	                            <label id="productLabel" class="productLabel" value="Doubles" for="Doubles"><fmt:message key="productContent.doubles" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/VIDEO" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/TV.png" width="28" height="28" alt="Video" border="0" />
	                            <label id="productLabel" class="productLabel" value="Video" for="Video"><fmt:message key="productContent.video" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/INTERNET" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/Internet.png" width="28" height="28" alt="Internet" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.internet" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/PHONE" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/Phone.png" width="28" height="28" alt="Power Pitch" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.phone" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/HOMESECURITY" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/Security.png" width="28" height="28" alt="Security" border="0" />
	                          <label id="productLabel" class="productLabel" value="PP" for="Security"><fmt:message key="productContent.security" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/ASIS" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/Asis.png" width="28" height="28" alt="Asis" border="0" />
	                             <label id="productLabel" class="productLabel" value="Asis" for="Asis"><fmt:message key="productContent.asis" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/ELECTRICITY" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/Electric.png" width="28" height="28" alt="Electric" border="0" />
	                            <label id="productLabel" class="productLabel" value="Electric" for="Electric"><fmt:message key="productContent.electric" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/NATURALGAS" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/gas.png" width="28" height="28" alt="Gas" border="0" />
	                            <label id="productLabel" class="productLabel" value="Gas" for="Gas"><fmt:message key="productContent.gas" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/WATER" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/Water.png" width="28" height="28" alt="Water" border="0" />
	                            <label id="productLabel" class="productLabel" value="Water" for="Water"><fmt:message key="productContent.water" /></label>
                            </a>
                        </li>
                    	<li class="prodcutsBtn">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/WASTE" >
	                        	<img src="<%=request.getContextPath()%>/images/nav/Trash.png" width="28" height="28" alt="Waste" border="0" />
	                            <label id="productLabel" class="productLabel" value="WASTE" for="Waste"><fmt:message key="productContent.waste" /></label>
                            </a>
                        </li>
                    </ul>	
                </nav>
                
				<table id="addLiGrid">
					<tr>
						<td><tiles:insertAttribute name="main_content" /></td>
						<td>
							<tiles:insertAttribute name="order_summary_content" />
							<tiles:insertAttribute name="cust_summary_content" />
						</td>
					</tr>
				</table>

           </section>
        </section>
        <aside id="main_aside">
        </aside>
    </div>    
     <footer id="main_footer">
    <span class="row">
    <span class="cell terms font7">
     <fmt:message key="layout.footer.message" /></span>
					<span class="cell">
                        <form>
                        <input type="submit" name="endCall" value="End Call" class="css3button" /></input>
                        </form>
                     </span>
                     <span class="cell font9"><fmt:message key="layout.footer.msg2" /></span>
    </span>
    </footer>
</div>
</body>
</html>
