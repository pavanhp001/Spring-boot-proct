
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="login.title" />
</title>

<link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<!-- JQuery and Javascript-->
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<!-- Dependencies -->
<script src="https://yui.yahooapis.com/2.8.0r4/build/yahoo/yahoo-min.js"></script>
<!-- Source file -->
<script src="https://yui.yahooapis.com/2.8.0r4/build/json/json-min.js"></script>


<!-- Style Sheets -->
<link rel='stylesheet' href="<c:url value='/style/fb_css.css'/>" />

</head>
<body>

	<!-- **************************************** -->
	<div id="globalContainer" style="width: 598px; height: 438px;">

		<section>
			<div id="content" class="fb_content clearfix">
				<div>
					<iframe
						src="http://www.ccheever.com/blog/wp-content/uploads/2008/09/iframe-canvas-page1.png"
						width="598" height="438"> 
						<tiles:insertAttribute name="main_content" />
					</iframe>
				</div>
			</div>
		</section>
	</div>


</body>
</html>
