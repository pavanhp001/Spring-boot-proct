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

<style>
#pageContent {
	margin-right : 0px;
	padding: 0px
}

.addressList {
    height: 295px;
    margin: 0;
    padding: 0;
}

.addressContent{
	height: 230px;
    margin-bottom: 5px;
    margin-top: 5px;
    overflow-y: scroll;
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

.pHead {
    padding-left: 0;
}

.addressIdRadio{
 	vertical-align: text-top;
}

a[name='editAddress']:hover {
    cursor: pointer;
    color: blue;
}

</style>

<script src="<c:url value='/js/jquery.js'/>"></script>

<script>
$(document).ready(function(){
	$('#addAddress').click(function(){
		var url = window.location.href;
		var index = url.indexOf("scart")+6;
		var id = $("#customerId").val();
		url = url.slice(0, index) + id + "/address/0/create";
		window.location = url;
	});

	$("a[name='editAddress']").click(function(){
		var addrId = this.id;
		var url = window.location.href;
		var index = url.indexOf("scart")+6;
		var id = $("#customerId").val();
		url = url.slice(0, index) + id + "/address/"+addrId+"/create";
		window.location = url;
	});

	$('#custProfile').submit(function(){
		var addrlen = $("input[name='addressId']:checked").length;
		if(addrlen == 0){
			alert("Please select Address");
			return false;
		}
	});
	
	$('a.serviceability').click(function(){
		var id = this.id;
		$("#serviceAddressId").val(id);
		$("input[value='"+id+"']").click();
		
		$("#custProfile").attr('action','<%=request.getContextPath()%>/rest/scart/providers/address/list');
		$("#custProfile").submit();
		
	});

	$('#loginAgain').click(function(){
		var url = window.location.href;
		var index = url.indexOf("scart")+6;
		var id = $("#customerId").val();
		url = url.slice(0, index) + "login";
		window.location = url;
	});
	
	$('.forwardArrowBtn').click(function() {
		var addrlen = $("input[name='addressId']:checked").length;
		if(addrlen == 0){
			alert("Please select Address");
			return false;
		}else{
			$("#custProfile").attr('action','<%=request.getContextPath()%>/rest/scart/order');
			$("#custProfile").submit();
		}
  	});
  	
});
</script>

</head>
<body >
	<form id="custProfile" method="POST" action="<%=request.getContextPath()%>/rest/scart/order">
	<div id="pageBody">
		<div id="pageContent">
			<c:if test="${empty customer.externalId}">
				<div class="pageBox">
					<div style="text-align: center; color: rgb(255, 0, 0); font-weight: bold; font-size: 15px;"><span>Customer with Id: ${searchId} doesn't exist</span></div>
					<div style="text-align: center; padding: 20px 0px 5px;"><input type="button" id="loginAgain" class="submitBtn" value="Try Again"></div>
				</div>
			</c:if>
			
			<c:if test="${not empty customer.externalId}">
				<div class="pageBox">
					<input type="hidden" name="customerId" id="customerId" value="${customer.externalId}" >
					<p class="pHead"><fmt:message key="cust.profile.info"/></p>
					<div class="rounded" style="margin-bottom:5px;">
						<div class="order-level">
							<div >
								<span class="rlabel"><fmt:message key="firstName.label"/> :</span>
								<span id="firstName" class="llabel" style="margin-bottom: 10px;">${customer.firstName}</span>								
							</div>
							<div>
								<span class="rlabel" ><fmt:message key="lastName.label"/> :</span>
								<span id="lastName" class="llabel" style="margin-bottom: 10px;">${customer.lastName}</span>
							</div>
						</div>
						
					</div>
					
					<input type="hidden" id="serviceAddressId" value="" name="serviceAddressId" />
					
					<div class="addressList">
						<p class="pHead"><fmt:message key="cust.profile.addressList.heading"/></p>
						<c:if test="${not empty customer.addressList}">
							<div style="margin-top:5px;" class="rounded">
								<div class="order-level addressContent" id="addressBlock">
									<c:forEach items="${customer.addressList.customerAddress}" var="address" varStatus="index">
										<ul style="margin-bottom: 10px; display: block;margin-left: 5px">
										
										<li><input type="radio" name="addressId" value="${address.address.externalId}" 
										<c:if test="${selectedAddressId == address.address.externalId}">
										checked = "checked"
										</c:if>
										class="addressIdRadio"><span style="margin-left: 5px; font-weight: bold;"><c:out value='${address.addressUniqueId}'/> [<a name="editAddress" id="${address.address.externalId}"><fmt:message key="edit"/></a>]</span></li> 
										<li><span style="margin-left:15px;"><c:out value='${address.address.streetNumber}'/>&nbsp;<c:out value='${address.address.streetName}'/></span></li>
										<li><span style="margin-left:15px;"><c:out value='${address.address.city}'/>,<c:out value='${address.address.stateOrProvince}'/>&nbsp;<c:out value='${address.address.postalCode}'/></span></li>
										 <li style="margin-top:5px; "><span style="text-align: padding:5px; margin-left:12px;"><a id="${address.address.externalId}"  class="serviceability" ><fmt:message key="cust.profile.serviceability"/></a></span></li>
									
									 </ul>
									 
										  <input type="hidden" id="dwelling" value="" name="dwelling" />
									 		<input type="hidden" id="address-${address.address.externalId}" name="address-${address.address.externalId}" value="${address.address.streetNumber} ${address.address.streetName}, ${address.address.city}, ${address.address.stateOrProvince} ${address.address.postalCode}"  />
									</c:forEach>
								</div>
							</div>
						</c:if>
						<c:if test="${empty customer.addressList}">
							<fmt:message key="cust.profile.noaddress"/>
						</c:if>
					
						<div style="margin-top: 10px;">
		   					<span style="float: left; width: 50%; text-align: center;"><input type="submit" id="performServiceability" class="submitBtn" value="<fmt:message key="cust.profile.perform.serviceability.button"/>"></span> 
							<span style="text-align: center; width: 50%; float: left;"><input type="button" id="addAddress" class="submitBtn" value="<fmt:message key="cust.profile.add.address.button"/>"></span>
							
						</div>
					</div>
				</div>
			</c:if>

		</div>
	</div>
	</form>

</body>
</html>