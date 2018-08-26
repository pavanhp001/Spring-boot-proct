<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>AL Get Order</title>

<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">

 






</head>

<body>
	 <div id="pageBody">
		<div id="pageContent">


			<form id="basicInfo" action="<%=request.getContextPath()%>/rest/summary/display" method="post"> 
			
			<input type="text" id="id" name="id" value="" />
			
			 <input type="submit" id="submit" value="Get Order"/>
			</form>
					 
					 
					 


		</div>
	</div>
	 
	 
</body>
</html>
