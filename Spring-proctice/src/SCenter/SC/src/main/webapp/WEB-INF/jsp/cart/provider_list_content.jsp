<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>AL Provider List</title>
<style>
.orderSummaryContent {
    height: 430px;
    overflow-y:scroll;
}

</style>
<script	src="<c:url value='/js/jquery.js'/>"></script>
<script>
	$(document).ready(function() {
		
		$('.backArrowBtn').click(function() {
			$("#providersForm").attr('action','<%=request.getContextPath()%>/rest/scart/back');
			$("#providersForm").submit();
      });
		$('.forwardArrowBtn').click(function() {
			$("#providersForm").attr('action','<%=request.getContextPath()%>/rest/scart/order');
			$("#providersForm").submit();
      });
		
	});
</script>
</head>

<body>
	<form method="POST"  id="providersForm" name="providersForm">
	
	<input type="hidden" name="customerId" id="customerId" value="${customerId}" >
	<input type="hidden" name="addressId" id="addressId" value="${addressId}" >
	<input type="hidden" id="address-${addressId}" name="address-${addressId}" value="${addressAsText}"/>
	
		<div id="pageBody">
			<div id="pageContent">
				<div class="pageBox">
					<div class="orderSummaryContent">
						<div style="margin: 5px 5px 5px 0px;">
							<span style="font-weight: bold; font-size: 12px;">${addressAsText}</span>
						</div>

						<table cellspacing="0" cellpadding="0" class="fullLineItem">
							<tbody>
								<tr>
									<td style="width: 25%; text-align: left;"
										class="itemHeader price"><fmt:message key="provider.list.provider"/></td>
									<td style="width: 45%" class="itemHeader"><fmt:message key="provider.list.description"/></td>
									<td style="width: 30%" class="itemHeader"><fmt:message key="provider.list.capability"/></td>

								</tr>
							</tbody>
						</table>
						<c:forEach var="provider" items="${providers}">
							<table class="fullLineItem">
								<tbody>
									<tr>
										<td style="width: 25%">${provider.provider.externalId}

											<div style="width: 64px; height: 64px;">
											<img style="width: 64px; height: 64px;"
													src="${providersImageLocation}${provider.provider.externalId}.png">
												<img style="width: 64px; height: 64px;"
													src="${providersImageLocation}${provider.provider.externalId}.jpg">
													<img style="width: 64px; height: 64px;"
													src="${providersImageLocation}${provider.provider.externalId}.jpg">
											</div>

										</td>

										<td style="text-align: left; float: left; width: 45%;">${provider.provider.name}
										</td>

										<td style="width: 30%">
											<c:forEach var="capability"	items="${provider.capability}">
												<div style="padding-bottom: 3px;">${capability.name}</div>
											</c:forEach>
										</td>
									</tr>
								</tbody>
							</table>

						</c:forEach>
						
					</div>
					
				</div>



			</div>
		</div>

	</form>

</body>
</html>