<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link rel='stylesheet' href="<c:url value='/style/tabbedContent.css'/>" />
<link rel='stylesheet' href="<c:url value='/style/head_css.css'/>" />
<link rel='stylesheet' href="<c:url value='/style/layout_left_right.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/auth_css.css'/>" />
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
	 
		<div style="margin-top:20px; margin-left: 179px;"  class="navigationBar">
			 - Missing Page 404
		
		<img style="float: left; margin:20px 0px 10px 5px; clear:both;"
					src="<%=request.getContextPath()%>/image/404.png" />
		</div>
	 
	
 
	 
</body>
</html>