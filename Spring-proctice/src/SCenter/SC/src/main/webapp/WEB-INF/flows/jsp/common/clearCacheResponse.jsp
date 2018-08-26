<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SYP</title>

<link rel="stylesheet" href="<c:url value='/css/main.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/nav.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/classes.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/text.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/formStyles.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/tabs.css'/>" />



<link rel="stylesheet" href="<c:url value='/css/screen.css'/>" />
<link href="<c:url value='/css/jquery-ui.css'/>" rel="stylesheet" type="text/css"/>
<style>
	#message{
		color:red;
		padding-top: 10px;
		margin-top: 50px;
		margin-left: 30px;
		font-weight: bold;
	}
</style>

<script src="<c:url value='/script/timer.js'/>"></script>

<script src="<c:url value='/js/jquDD5B.js'/>"></script>
<script src="<c:url value='/js/jquery-ui.min.js'/>"></script>

<script>
$(document).ready(function() {
    //$("#datepicker").datepicker();
    //$("#datepickerElectric").datepicker();
    //$("#datepickerGas").datepicker();
});
    
</script>

</head>

<body onload="updateTimer()">
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
        	<div id = "message">${message} </div>
        </section>
        <aside id="main_aside">
        </aside>
    </div>    
    <footer id="main_footer">
    <span class="row">
    <span class="cell terms font8">
    <fmt:message key="layout.footer.message"/></span>
    <span class="cell font9"><fmt:message key="layout.footer.msg2"/></span>
    </span>
    </footer>
                	
</div>
</body>
</html>