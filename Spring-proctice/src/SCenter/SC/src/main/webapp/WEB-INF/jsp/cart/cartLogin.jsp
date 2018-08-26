<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title><fmt:message key="login.title"/></title>
<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/cart/default/cust_screen.css'/>" type="text/css" rel="stylesheet">

<link rel="stylesheet" href="<c:url value='/css/main.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/nav.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/classes.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/text.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/formStyles.css'/>" />

<style>
@charset "utf-8";

.searchform {
	background: -moz-linear-gradient(top, #FFFFFF, #EDEDED) repeat scroll 0 0 transparent;
	background: -webkit-linear-gradient(top, #FFFFFF, #EDEDED) repeat scroll 0 0 transparent;
    background: linear-gradient(top, #FFFFFF, #EDEDED) repeat scroll 0 0 transparent; 
	border: 1px solid #D2D2D2;
	border-radius: 2em 2em 2em 2em;
	box-shadow: 0 1px 0 rgba(0, 0, 0, 0.1);
	display: inline-block;
	margin-top: 2px;
	padding: 1px 3px;
}

.searchform .searchfield {
	background: none repeat scroll 0 0 #FFFFFF;
	border: 1px solid #BCBBBB;
	border-radius: 2em 2em 2em 2em;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2) inset;
	outline: medium none;
	padding: 4px 4px 4px 6px;
	width: 102px;
}

.searchform input {
	color: #757575;
	font: 9.5px/100% Arial, Helvetica, sans-serif;
}

.searchform .searchbutton {
	background: -moz-linear-gradient(top, #9E9E9E, #454545) repeat scroll 0 0 transparent;
	background: -webkit-linear-gradient(top, #9E9E9E, #454545) repeat scroll 0 0 transparent;
    background: linear-gradient(top, #9E9E9E, #454545) repeat scroll 0 0 transparent; 
	border: 1px solid #494949;
	border-radius: 2em 2em 2em 2em;
	color: #FFFFFF;
	font-size: 9.5px;
	height: 20px;
	text-shadow: 0 1px 1px rgba(0, 0, 0, 0.6);
	width: 20px;
}

.basicInformationForm {
	
}

.searchform input {
	margin: 0;
	padding: 0;
}

input.css3button {
	background: -moz-linear-gradient(top, #FFFFFF 0%, #FA2525 50%, #E63427 50%, #B40C06) repeat scroll 0 0 transparent;
	background: -webkit-linear-gradient(top, #FFFFFF 0%, #FA2525 50%, #E63427 50%, #B40C06) repeat scroll 0 0 transparent;
    background: linear-gradient(top, #FFFFFF 0%, #FA2525 50%, #E63427 50%, #B40C06) repeat scroll 0 0 transparent; 
	border: 1px solid #EB7373;
	border-radius: 10px 10px 10px 10px;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.5), 0 0 2px #FFFFFF inset;
	color: #FFFFFF;
	cursor: pointer;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 8px;
	padding: 2px 5px;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.2), 0 1px 0 rgba(89, 89, 89, 0.4);
}

input.ViewDetailsBtn {
	background: -moz-linear-gradient(top, #FFFFFF 0%, #204CBA 50%, #224395 50%, #0D2664) repeat scroll 0 0 transparent;
	background: -webkit-linear-gradient(top, #FFFFFF 0%, #204CBA 50%, #224395 50%, #0D2664) repeat scroll 0 0 transparent;
    background: linear-gradient(top, #FFFFFF 0%, #204CBA 50%, #224395 50%, #0D2664) repeat scroll 0 0 transparent; 
	border: 1px solid #4E81FF;
	border-radius: 10px 10px 10px 10px;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.5), 0 0 2px #FFFFFF inset;
	color: #FFFFFF;
	cursor: pointer;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 8px;
	margin-bottom: 0;
	padding: 2px 5px;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.2), 0 1px 0 rgba(89, 89, 89, 0.4);
}

.buttonRight {
	float: right;
	margin: 0 5px;
	padding: 2px;
}

input.addtoorderbtn {
	background: none repeat scroll 0 0 #577D3E;
	border: 1px solid #395427;
	border-radius: 10px 10px 10px 10px;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.5), 0 0 2px #FFFFFF inset;
	color: #FFFFFF;
	cursor: pointer;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 9.5px;
	margin-bottom: 0;
	padding: 2px 5px;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.2), 0 1px 0 rgba(89, 89, 89, 0.4);
}

.addtoorder {
	left: 280px;
	margin: 0;
	padding: 0;
	position: relative;
	top: 3px;
}

.basicInformationForm fieldset {
	margin: 12px 2px 2px;
	padding: 5px;
}

.basicInformationForm fieldset .row {
	max-width: 600px;
}

.basicInformationForm input {
	margin-bottom: 8px;
}

.basicInformationForm input.text {
	background: -moz-linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent;
	background: -webkit-linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent;
    background: linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent; 
	border: 1px solid #AFAFAF;
	border-radius: 0 0 0 0;
	color: #333333;
	font-size: 10px;
	padding: 7px 8px 7px 10px;
	text-shadow: 0 1px 0 #FFFFFF;
}

.basicInformationForm fieldset label.infield {
	color: #333333;
	left: 35px !important;
	line-height: 29px;
	position: absolute;
	text-align: left;
	text-shadow: 0 1px 0 #FFFFFF;
	top: 3px !important;
}

.basicInformationForm select {
	background: -moz-linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent;
	background: -webkit-linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent;
    background: linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent; 
	border: 1px solid #AFAFAF;
	border-radius: 0 0 0 0;
	color: #333333;
	font-size: 10px;
	margin-bottom: 8px;
	padding: 6px 7px 6px 10px;
	text-shadow: 0 1px 0 #FFFFFF;
}

.addressfield {
	width: 452px;
}

fieldset {
	margin: 2px;
	padding: 5px;
}

input {
	margin-bottom: 8px;
}

input.text {
	background: -moz-linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent;
	background: -webkit-linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent;
    background: linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent; 
	border: 1px solid #AFAFAF;
	border-radius: 0 0 0 0;
	color: #333333;
	font-size: 10px;
	padding: 6px 7px 6px 10px;
	text-shadow: 0 1px 0 #FFFFFF;
}

fieldset label.infield {
	color: #333333;
	left: 35px !important;
	line-height: 29px;
	position: absolute;
	text-align: left;
	text-shadow: 0 1px 0 #FFFFFF;
	top: 3px !important;
}

select {
	background: -moz-linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB 100%) repeat scroll 0 0 transparent;
	background: -webkit-linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent;
    background: linear-gradient(center bottom, #E1E1E1 0%, #D7D7D7 54%, #DBDBDB) repeat scroll 0 0 transparent; 
	border: 1px solid #AFAFAF;
	border-radius: 0 0 0 0;
	color: #333333;
	font-size: 10px;
	margin-bottom: 8px;
	padding: 6px 7px 6px 10px;
	text-shadow: 0 1px 0 #FFFFFF;
}

#login {
	background-color: #FFFFFF;
	background-image: -moz-linear-gradient(top, #FFFFFF, #EEEEEE);
	background-image: -webkit-linear-gradient(top, #FFFFFF, #EEEEEE);
    background-image: linear-gradient(top, #FFFFFF, #EEEEEE); 
	border-radius: 3px 3px 3px 3px;
	box-shadow: 0 0 2px rgba(0, 0, 0, 0.2), 0 1px 1px rgba(0, 0, 0, 0.2), 0
		3px 0 #FFFFFF, 0 4px 0 rgba(0, 0, 0, 0.2), 0 6px 0 #FFFFFF, 0 7px 0
		rgba(0, 0, 0, 0.2);
	height: 240px;
	left: 50%;
	margin: -150px 0 0 -230px;
	padding: 30px;
	position: absolute;
	top: 50%;
	width: 400px;
	z-index: 0;
}

#login:before {
	border: 1px solid #CCCCCC;
	bottom: 5px;
	box-shadow: 0 0 0 1px #FFFFFF;
	content: "";
	left: 5px;
	position: absolute;
	right: 5px;
	top: 5px;
	z-index: -1;
}

#inputs input {
	background: url("../images/img/login-sprite.png") no-repeat scroll 0 0
		#F1F1F1;
	border: 1px solid #CCCCCC;
	border-radius: 5px 5px 5px 5px;
	box-shadow: 0 1px 1px #CCCCCC inset, 0 1px 0 #FFFFFF;
	margin: 0 0 10px !important;
	padding: 15px 15px 15px 30px !important;
	width: 353px;
}

fieldset#inputs {
	margin: 0;
	padding: 0;
}

#inputs {
	border: 0 solid transparent;
}

#username {
	background-position: 5px -2px !important;
}

#password {
	background-position: 5px -52px !important;
}

#inputs input:focus {
	background-color: #FFFFFF;
	border-color: #71B26E;
	box-shadow: 0 0 0 1px #B0CBAC inset;
	outline: medium none;
}

#actions {
	border: 0 solid transparent;
	margin: 25px 0 0;
}

#submit {
	background-color: #C2FF4B;
	background-image: -moz-linear-gradient(top, #96C43D, #C2FF4B);
	background-image: -webkit-linear-gradient(top, #96C43D, #C2FF4B);
    background-image: linear-gradient(top, #96C43D, #C2FF4B); 
	border-color: #31D639 #80E337 #3C6838 #3D8A04;
	border-radius: 3px 3px 3px 3px;
	border-style: solid;
	border-width: 1px;
	box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0 rgba(255, 255, 255, 0.3)
		inset;
	color: #7C7341;
	cursor: pointer;
	float: left;
	font: bold 15px Arial, Helvetica;
	height: 35px;
	padding: 0;
	text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
	width: 120px;
}

#submit:hover,#submit:focus {
	background-color: #96C43D;
	background-image: -moz-linear-gradient(top, #C2FF4B, #96C43D);
	background-image: -webkit-linear-gradient(top, #C2FF4B, #96C43D);
    background-image: linear-gradient(top, #C2FF4B, #96C43D); 
}

#submit:active {
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
	outline: medium none;
}

#submit::-moz-focus-inner {
	border: medium none;
}

#actions a {
	color: #3151A2;
	float: right;
	line-height: 35px;
	margin-left: 10px;
}

#back {
	color: #999999;
	display: block;
	position: relative;
	text-align: center;
	top: 60px;
}
</style>


<script src="<c:url value='/js/jquery.js'/>"></script>
<script>
	$(document).ready(function() {
		$('#submit').click(function() {
			$("form#login").submit();
		});
		
		$('#create').click(function() {
			var url = window.location.href;
			url = url.replace("scart/login.html", "rest/scart/customer")
			window.location = url;
		});
	});
</script>
</head>
<body>

	<div id="contentWrapper">
		<aside id="left_aside"></aside>
		<section id="main_section">
			<section id="MiddleContentShell">

				<form action="<%=request.getContextPath()%>/rest/scart/custProfile" class="loginWidget" id="login" method="post">
					<h1 class="loginstyle"><fmt:message key="login.login"/></h1>
					<fieldset id="inputs">
						<input type="text" required="" autofocus="" placeholder="<fmt:message key="login.username"/>"
							id="username" name="username"> <input type="password" required=""
							placeholder="<fmt:message key="login.password"/>" id="password">
					</fieldset>
					<fieldset id="actions">
						<input type="submit" value="<fmt:message key="login.login1"/>" id="submit"> 
						<div style=" float:right; margin-top:20px 0px 0px 5px;">
						  <input type="button" style="height: 30px; width: 160px; font-weight: bold; border-radius: 6px 6px 6px 6px; border: 1px solid rgb(163, 201, 219);" id="create" value="<fmt:message key="login.register"/>"/>
						</div>
						<span> </span>
					</fieldset>
				</form>
			</section>
		</section>
		<aside id="main_aside"></aside>
	</div>


</body>
</html>

</body>
</html>