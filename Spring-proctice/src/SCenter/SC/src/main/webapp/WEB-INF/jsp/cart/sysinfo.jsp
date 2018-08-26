<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>System Config</title>
<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/cart/default/cust_screen.css'/>" type="text/css" rel="stylesheet">

<link rel="stylesheet" href="<c:url value='/css/main.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/nav.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/classes.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/text.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/formStyles.css'/>" />
<style type="text/css">

.providerTable {
	border:1px solid #d3d3d3 !important;
	border-color:#d3d3d3 !important;
}

.providerTable {
	margin: 100px;
    padding: 10px !important;
}
.thead{
	font-weight: bold !important;
}
</style>

</head>
<body>
<div id="contentWrapper">
		<aside id="left_aside"></aside>
		<section id="main_section">
			<section id="MiddleContentShell">
			<form  class="loginWidget">
				<div>
					<table id="providerTable" class="providerTable" border="1" width="100%" cellspacing="0" cellpadding="2">
						<thead>
	 						<tr>
	 							<th class=" thead providerTable">ID</th>
	 							<th class="thead providerTable">PROVIDER ID</th>
	 							<th class="thead providerTable">URL</th>
	 							<th class="thead providerTable">CERTIFICATE</th>
	 						</tr>
		 				</thead>
	 					<tbody>
	 						<c:forEach var="provider" items="${providers}" varStatus="count">
	 							<tr>
	 								<td width="90" class="providerTable"><c:out value="${provider.id}"/></td>
									<td class="providerTable"><c:out value="${provider.providerId}"/></td>	
									<td class="providerTable"><c:out value="${provider.url}"/></td>	
									<td class="providerTable"><c:out value="${provider.certificate}"/></td>	
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				</form>
			</section>
		</section>
		<aside id="main_aside"></aside>
		
</div>
		

</body>