<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Create Customer</title>
<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/cart/default/cust_screen.css'/>" type="text/css" rel="stylesheet">

<script src="<c:url value='/js/jquery.js'/>"></script>

</head>
<body>
	<form  action="profile" method="post">
		<div id="pageBody">
			<div id="pageContent">
				<h2 class="sectionHeading">
					<span>Customer</span>
				</h2>
				<div class="pageBox">
					<div class="rounded">
						<div style="margin-top:30px">
							<span class="rlabel">Customer Id:</span>
							<input id="externalId" type="text" name="externalId">	
						</div>
						<div class="submitDiv">
							<input type="submit" id="details" class="submitBtn" value="Show Details">
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>