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
	#SYP_ALbar{
		background: #efefef; 
		background: linear-gradient(top, #f9f9f9 0%, #bbbbbb 100%);  
		background: -moz-linear-gradient(top, #f9f9f9 0%, #bbbbbb 100%); 
		background: -webkit-linear-gradient(top, #f9f9f9 0%,#bbbbbb 100%); 
		box-shadow: 0px 0px 9px rgba(0,0,0,0.15);
		padding: 2px 15px;
		width: 100%;
		height: 20px;
		color: #494949;
		font-size: 9.5px;
		/*border: 1px solid red;*/
	}
	#main_footer{
		position: fixed;
		bottom: 0px;
		left: 0px;
		margin: 0px;
		min-width: 600px;
		width: 100%;
		height: 20px;
		padding: 2px 0px 6px 0px;
		text-align: left;
		background: #efefef; 
		background: linear-gradient(top, #efefef 0%, #bbbbbb 100%);  
		background: -moz-linear-gradient(top, #efefef 0%, #bbbbbb 100%); 
		background: -webkit-linear-gradient(top, #efefef 0%,#bbbbbb 100%); 
		box-shadow: 0px 0px 9px rgba(0,0,0,0.15);
		font-size: 10px;
		/*border: 1px solid red;*/
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
        <div id="SYP_ALbar"><img src="<%=request.getContextPath()%>/image/ALLogoMain.png" width="149" height="20" alt="AL" border="0" />
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
    <span class="cell terms font8">Only users with express authorization from AL, Inc. may use this system. Unauthorized use is strictly prohibited and may expose you to legal liability. Violations will be logged and reported.</span>
    <span class="cell font9">Copyright &copy; 2013 AL Inc. All Rights Reserved.</span>
    </span>
    </footer>
                	
</div>
</body>
</html>