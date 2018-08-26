<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>AL Customer Profile</title>
<style>
#pageContent {
    width: 525px;
}
</style>
<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/cart/default/cust_screen.css'/>" type="text/css" rel="stylesheet">

<script src="<c:url value='/js/jquery.js'/>"></script>

</head>
<body>
	<form action="<%=request.getContextPath()%>/rest/scart/createOrder/<c:out value='${customer.externalId}'/>" method="post">
	<div id="pageBody">
		<div id="pageContent">
			<h2 class="sectionHeading">
				<span>Customer Information</span>
			</h2>
			<div class="pageBox">
				<div class="rounded">
						<p class="pHead" style="font-size:15px">Customer Details</p>
						
						<div class="order-level">
							<div >
								<span class="rlabel">First Name :</span>
								<span class="llabel" >${customer.firstName}</span>								
							</div>
							<div>
								<span class="rlabel" >Last Name :</span>
								<span class="llabel" >${customer.lastName}</span>
							</div>
							<div>
								<span class="rlabel" >DOB :</span>
								<span class="llabel" >${customer.dob.value}</span>
							</div>
							<div>
								<span class="rlabel" >Home Phone :</span>
								<span class="llabel" >${customer.homePhoneNumber.value}</span>
							</div>
							<div>
								<span class="rlabel" >Work Phone :</span>
								<span class="llabel" >${customer.workPhoneNumber.value}</span>
							</div>
							<div>
								<span class="rlabel" >Home Email :</span>
								<span class="llabel" >${customer.homeEMail.value}</span>
							</div>
							<div>
								<span class="rlabel" >Work Email :</span>
								<span class="llabel" >${customer.workEMail.value}</span>
							</div>
						</div>
							
						<div class="submitDiv">
							<input class="submitBtn" type="submit" value="Create Order">
						</div>
					</div>
					
				</div> 
			</div>
		</div>
	</form>
</body>
</html>