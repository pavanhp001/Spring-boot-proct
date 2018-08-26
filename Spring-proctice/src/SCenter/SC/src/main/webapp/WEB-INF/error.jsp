<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link rel='stylesheet' href="<%=SessionCache.INSTANCE.getResource(request)%>/style/tabbedContent.css" />
<link rel='stylesheet' href="<%=SessionCache.INSTANCE.getResource(request)%>/style/head_css.css" />
<link rel='stylesheet' href="<%=SessionCache.INSTANCE.getResource(request)%>/style/layout_left_right.css" />
<link rel="stylesheet" href="<%=SessionCache.INSTANCE.getResource(request)%>/style/auth_css.css" />
<style type="text/css">
.errorDiv{
	border: 2px solid #FF0000;
    border-radius: 6px 6px 6px 6px;
    font-size: 24px;
    font-weight: bold;
    margin-left: auto;
    margin-right: auto;
    padding: 20px;
    text-align: center;
    width: 600px;
}
</style>
</head>
<body>

	<div style="margin-left: 179px;" class="UIContentTopper_text_container">
		 ERROR
	</div>
	<div  id="UIContentTopper"
		class="UIContentTopper clearfix width75Pc"></div>
	<div class="width75Pc" id="main_container">
		<div class="errorDiv">Error.  Please Report.</div>
	</div>
	
</body>
</html>