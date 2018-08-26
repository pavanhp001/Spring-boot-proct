
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="login.title" /></title>
<link rel="stylesheet" href="<c:url value='/style/CKO_att.css'/>" />
<!-- JQuery and Javascript-->
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<!-- Dependencies -->
<script src="http://yui.yahooapis.com/2.8.0r4/build/yahoo/yahoo-min.js"></script>
<!-- Source file -->
<script src="http://yui.yahooapis.com/2.8.0r4/build/json/json-min.js"></script>
<script src="<c:url value='/script/backbuttons.js'/>"></script>
<script language="javascript">window.history.forward(0);</script>

<link rel='stylesheet' href="<c:url value='/style/placeorder.css'/>" />
<!-- Style Sheets -->
<!-- link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" /> -->

</head>
<body  >

	<!-- **************************************** -->
 <div id="globalContainer" style="width:100%;height:100%;"> 
		
		<section>
			<div id="content" >
				<div>
					<tiles:insertAttribute name="main_content" />
				</div>
			</div>
		</section>
 </div>
		
	 
</body>
</html>
