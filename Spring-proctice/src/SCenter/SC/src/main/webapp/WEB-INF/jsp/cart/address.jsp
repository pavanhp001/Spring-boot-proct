<%@ page import="com.AL.ui.repository.SessionCache,com.AL.xml.cm.v4.CustomerType;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Address Profile</title>
<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/cart/default/cust_screen.css'/>" type="text/css" rel="stylesheet">
<style>
#pageContent {
	margin-right : 0px;
	padding: 0px
	
}
.addressList {
    height: 415px;
    margin: 0;
    overflow-y: scroll;
    padding: 0;
    width: 635px;
}
#pageContent {
    float: left;
    width: 100%;
    margin-left: 0px
}
#pageBody {
	border: 5px solid #fff;
	border-width: 0 0 0;
	border-radius: 0 0 0 0
}
.serviceability:hover {
	background: #ffdede;
	cursor: pointer
}
</style>

<script src="<c:url value='/js/jquery.js'/>"></script>
<script>
	var countries = ${coutries};
	var states = ${states};

	$(document).ready(function(){
		var countryList = countries.items.item;
		$(countryList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
			$("select#country").append($("<option></option>").attr("value", val).text(txt));
		});
		
		$("select#country").val("US");
		var stateList = states.items.item;
		$(stateList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
			$("select#stateOrProvince").append($("<option></option>").attr("value", val).text(txt));
		});
			
		$('#country').change(function(){
			var country = $("select#country").val();
			if(country == 'US'){
				$(stateList).each(function(){
					var txt = this.description;
					var val = this.lookupKey;
					$("select#stateOrProvince").append($("<option></option>").attr("value", val).text(txt));
				});
			} else {
				$("select#stateOrProvince").empty();
			}
		});

		$('#addressForm').submit(function(){
			var country = $("select#country").val();
			if(country != 'US'){
				alert('Please select USA');
				return false;
			}
		});

		$('#cancel').click(function() {
			var customerId = $("#customerId").val();
			var url = window.location.href;
			var index = url.indexOf("rest");
			url = url.slice(0, index+5) + "scart/" + customerId + "/address/cancel";
            window.location = url;
      	});
	});
</script>
</head>
<body>

<form action="<%=request.getContextPath()%>/rest/scart/${customer.externalId}/address/${addressId}/save" method="post" id="addressForm" >
		
		<input id="lastName" name="lastName" type="hidden" value="${customer.lastName}"/>
		<input id="customerId" name="customerId" type="hidden" value="${customer.externalId}"/>
		
		<div id="pageBody">
			<div id="pageContent">
				
				<div class="pageBox">
					<div class="rounded">
						<div class="addressList">
							<p class="pHead"><fmt:message key="address.cust.details"/></p>
							<div class="order-level">
							             <div >
											<span class="rlabel"><fmt:message key="firstName.label"/> :</span>
											<span class="llabel" >${customer.firstName}</span>								
										</div>
							</div>
							<div class="order-level">
										<div style="margin-top: 60px;margin-left: 0px">
											<span class="rlabel" ><fmt:message key="lastName.label"/> :</span>
											<span class="llabel" >${customer.lastName}</span>
										</div>
							</div>
							
							<p class="pHead"><fmt:message key="address.addressDetails"/></p>
							<div class="order-level">
								<input type="hidden" name="addressUniqueId" value="${selectedAddress.addressUniqueId}">
								<c:forEach var="role" items="${selectedAddress.addressRoles.role}">
									<input type="hidden" name="roles" value="${role}">
								</c:forEach>
								
								<div>
									<span class="rlabel"><fmt:message key="address.streetNumber"/>:</span>
									<input id="streetNumber" type="text"  name="streetNumber" value="${selectedAddress.address.streetNumber}">
								</div>
								
								<div>
									<span class="rlabel"><fmt:message key="address.streetName"/>:</span>
									<input id="streetName" type="text"  name="streetName" value="${selectedAddress.address.streetName}">
								</div>
								<div>
									<span class="rlabel"><fmt:message key="address.line2"/>:</span>
									<input id="line2" type="text" name="line2" value="${selectedAddress.address.line2}">
								</div>
								
								<div>
									<span class="rlabel"><fmt:message key="address.country"/>:</span>
									<select id="country" name="country"></select>
								</div>
								
								<div>
									<span class="rlabel"><fmt:message key="address.state"/>:</span>
									<select id="stateOrProvince" name="stateOrProvince"></select>			
								</div>
							
								<div>
									<span class="rlabel"><fmt:message key="address.city"/>:</span>
									<input id="city" type="text"  name="city" value="${selectedAddress.address.city}">
								</div>
								
								<div>
									<span class="rlabel"><fmt:message key="address.postalCode"/>:</span>
									<input id="postalCode" type="text"  name="postalCode" value="${selectedAddress.address.postalCode}">
								</div>							
							</div>
							
							<div class="submitDiv">
								<input class="submitBtn" type="submit" value="<fmt:message key="address.saveAddress.button"/>">
								<input name="cancel" id="cancel" class="submitBtn" type="button" value="<fmt:message key="cancel.button"/>">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</form>
</body>
</html>