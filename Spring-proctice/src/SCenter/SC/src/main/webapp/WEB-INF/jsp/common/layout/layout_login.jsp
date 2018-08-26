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

<style>

div#scrollable_content {
   
 
 margin-left: auto;
	margin-right: auto;
}

</style>

</head>

<body>
	<!-- **************************************** -->
 
		
		
		<div id="wrapper">
    <header id="main_header">
        <div id="SYP_toolbar">
            <div id="SYP_title"><fmt:message key="layout.SYP.title"/></div><div id="AL_title"><fmt:message key="layout.AL"/></div>
            <nav id="main_menu">
	<ul>
		<li><a href="#"><fmt:message key="layout.performance"/></a></li>
		<li><a href="#"><fmt:message key="layout.settings"/></a>
			<ul>
				<li><a href="#">Icon Orientation</a>
                	<ul>
						<li><a href="#">Horizontal</a></li>
						<li><a href="#">Verticle</a></li>
					</ul>
                </li>
				<li><a href="#">Icon Theme</a>
					<ul>
						<li><a href="#"><fmt:message key="layout.AL"/></a></li>
						<li><a href="#">Spectrum</a></li>
					</ul>
				</li>
			</ul>
		</li>
		<li><a href="#"><fmt:message key="layout.help"/></a>
		</li>
        <li>
        	<form class="searchform">
<input class="searchfield" type="text" onblur="if (this.value == '') {this.value = 'Search...';}" onfocus="if (this.value == 'Search...') {this.value = '';}" value="<fmt:message key="layout.search"/>">
<input class="searchbutton" type="button" value="<fmt:message key="layout.go"/>">
</form>
        
        </li>
	</ul>
</nav>
        </div>
        <div id="SYP_ALbar"><img src="<%=request.getContextPath()%>/images/img/ALLogoMain.png" width="149" height="20" alt="AL" border="0" />
        </div>
    </header>
    <div id="contentWrapper">
        <aside id="left_aside">
        </aside>
        <section id="main_section">
        	<section id="MiddleContentShell">
        	
        	<div style="float:left;" id="scrollable_content">
        	<tiles:insertAttribute name="main_content" />
        	</div>
        	
        	</section>
        </section>
        <aside id="main_aside">
        </aside>
    </div> 
		
		  <footer id="main_footer">
    <span class="row">
    <span class="cell terms font7">
    <fmt:message key="layout.footer.message"/></span>
					<span class="cell">
                        <form>
                        <input type="submit" name="End Call" value="<fmt:message key="layout.endCall"/>" class="css3button"></input>
                        </form>
                     </span>
                     <span class="cell font9"><fmt:message key="layout.footer.msg2"/></span>
                	</span></footer>
	</div>
</body>
</html>
