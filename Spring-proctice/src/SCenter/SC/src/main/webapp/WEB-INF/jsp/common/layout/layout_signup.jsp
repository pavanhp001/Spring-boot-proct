
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="login.title" /></title>


<!-- JQuery and Javascript-->
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<!-- Dependencies -->
<script src="http://yui.yahooapis.com/2.8.0r4/build/yahoo/yahoo-min.js"></script>
<!-- Source file -->
<script src="http://yui.yahooapis.com/2.8.0r4/build/json/json-min.js"></script>


<!-- Style Sheets -->
<link rel='stylesheet' href="<c:url value='/style/fb_css.css'/>" />

</head>
<body class="ego_page home   fbx ff3 mac Locale_en_US">

	<!-- **************************************** -->
	<div  id="blueBar" ></div>
	<div>
	<header  >
			<div id="pageHead"  class="ptm clearfix">
				<div id="headNav"   class="clearfix header">
					<div class="lfloat app-logo">
						<ul id="pageNav" class=" pageNavBold">
							<li></li>
							<li><a href="#"><fmt:message
										key="login.top.left.section" /> </a></li>

						</ul>
					</div>
					<div class="rfloat">
						<ul id="pageNav" class="pageNavBold">
							<li></li>
							<li><a href="<%=request.getContextPath()%>/help.html"><fmt:message
										key="login" /> </a></li>
						</ul>
					</div>
				</div>
			</div>
		</header>
		</div>
		
	<div id="globalContainer" style="width:80%;">
		
		<section>
			<div id="content" class="fb_content clearfix">
				<div>
					<tiles:insertAttribute name="main_content" />
				</div>
			</div>
		</section>
		<footer>
			<section>
				<div id="bottomContent">
					<div id="pageFooter">
						<div id="contentCurve"></div>
						<div id="footerContainer" class="clearfix">
							<div class="lfloat">
								<div class="fsm fwn fcg">
									<span  > <fmt:message key="footer.title" />
										&copy; <fmt:message key="footer.year" />
									</span> · <a   href="" rel="dialog">
										<fmt:message key="footer.language" />
									</a>
								</div>
							</div>
							<div class="rfloat fsm fwn fcg">
								<a title="" accesskey="8" href=""><fmt:message
										key="footer.about" />
								</a> · <a title="" href=""><fmt:message key="footer.advertising" />
								</a> · <a title="" href=""><fmt:message key="footer.developers" />
								</a> · <a title="" href=""><fmt:message key="footer.careers" />
								</a> · <a title="" href=""><fmt:message key="footer.privacy" />
								</a> · <a title="" accesskey="9" href=""><fmt:message
										key="footer.terms" />
								</a> · <a title="" accesskey="0" href=""><fmt:message
										key="footer.help" />
								</a>
							</div>
						</div>
					</div>
				</div>
			</section>
		</footer>
	</div>
</body>
</html>
