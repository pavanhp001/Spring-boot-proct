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
<link media="all" href="<c:url value='/style/jquery.ui.all.css'/>" type="text/css" rel="stylesheet">

<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.widget.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>

<style>

.contactDiv{
	margin-left: 25px;
    margin-top: 15px;
}

.addressDiv{
	margin-left: 25px;
    margin-top: 15px;
}

.contactLabel {
    float: left;
    margin-top: 4px;
    padding-right: 4px;
    text-align: right;
    width: 110px;
}

.addressLabel {
    float: left;
    margin-top: 4px;
    padding-right: 4px;
    text-align: right;
    width: 110px;
}

.contactInput {
}

.fLeft{
	float: left;
}
</style>
<script>

$(document).ready(function(){
	$("#dob").datepicker();
	$("#dob").val(getCurrentDate());
	
	var countries = ${coutries};
	var states = ${states};

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
		}else{
			$("select#stateOrProvince").empty();
			}
		});

		$('#cancel').click(function() {
			var url = window.location.href;
			var index = url.indexOf("scart")+6;
			url = url.slice(0, index) + "login";
			window.location = url;
      	});
		
	$("form").submit(validateFields);
});

function getCurrentDate(){
	var dt = new Date()
	var date = dt.getDate();
	if(date < 10){
		date = "0"+date;
	}
	var month = dt.getMonth()+1;
	if(month < 10){
		month = "0"+month;
	}
	var year = dt.getFullYear();

	return month+"/"+date+"/"+year;
}

function validateFields(){
	
	var fname = $("#firstName").val();	
	if(fname == null || fname == ""){
		alert("Please enter first name");
		return false;
	} 
	var lname = $("#lastName").val();	
	if(lname == null || lname == ""){
		alert("Please enter Last name");
		return false;
	}
	var phone_re = new RegExp("^\\d{3}-\\d{3}-\\d{4}$");
	
	var date_re = new RegExp("^\\d{2}/\\d{2}/\\d{4}$");

	var dateCompleted = $("#dob").val();
	if(dateCompleted != undefined && dateCompleted != ""){
		if (!dateCompleted.match(date_re)) {
			alert("Date format should be mm/dd/yyyy");
			return false;
		}
	}
	var homePhone = $("#homePhoneNumber").val();
	
	if(homePhone != undefined && homePhone != ""){
		
		if (!homePhone.match(phone_re)) {
			alert("Phone number format should be nnn-nnn-nnnn");
			return false;
		}
	}

	var workPhone = $("#workPhoneNumber").val();
	
	if(workPhone != undefined && workPhone != ""){
		
		if (!workPhone.match(phone_re)) {
			alert("Phone number format should be nnn-nnn-nnnn");
			return false;
		}
	}

	
}
</script>
</head>
<body>
	<form action="<%=request.getContextPath()%>/rest/scart/custProfile" method="post">
	<input type="hidden" name="username" id="username" value="0" />
	<div id="pageBody" style="width: 695px;padding:2px;">
	 
			 
			<div class="pageBox" style="width: 610px;height:400px;overflow-y: scroll;">
				<div class="rounded" style="width: 590px; height: 545px;margin-bottom: 15px;">

						<p class="pHead">Customer Info</p>
						<div class="order-level">
							<div>
								<span class="rlabel">First Name:</span>
								<input id="firstName" type="text" name="firstName">
							</div>
							
							<div>
								<span class="rlabel">Last Name:</span>
								<input id="lastName" type="text" name="lastName">
							</div>
							
							<div>
								<span class="rlabel">DOB:</span>
								<input id="dob" type="text" name="dob">
							</div>
						</div>
						
						<p class="pHead">Address Info</p>
						<div class="order-level">
							<div>
								<span class="rlabel">Street Number:</span>
								<input id="streetNumber" type="text" name="streetNumber">
							</div>
							
							<div>
								<span class="rlabel">Street Name:</span>
								<input id="streetName" type="text"  name="streetName">
							</div>
							
							<div>
								<span class="rlabel">City:</span>
								<input  id="city" type="text"  name="city">	
							</div>
							
							<div>
								<span class="rlabel">State:</span>
								<select id="stateOrProvince" name="stateOrProvince"></select>	
							</div>
							
							<div>
								<span class="rlabel">Country:</span>
								<select id="country" name="country" ></select>		
							</div>
							
							<div>
								<span class="rlabel">Postal Code:</span>
								<input id="postalCode" type="text"  name="postalCode">
							</div>
						</div>
							<p class="pHead">Contact Info</p>
							<div class="order-level">
								<div>
									<span class="rlabel">Home Phone:</span>
									<input class="phoneType" id="homePhoneNumber" type="text" name="homePhoneNumber">
								</div>
								<div>
									<span class="rlabel">Work Phone:</span>
									<input id="workPhoneNumber" type="text"  name="workPhoneNumber" class="phoneType">
								</div>
								<div>
									<span class="rlabel">Home Email:</span>
									<input id="homeEMail" type="text"  name="homeEMail">
								</div>
								<div>
									<span class="rlabel">Work Email:</span>
									<input id="workEMail" type="text" name="workEMail">	
								</div>
							</div>
							<div class="contactDiv">
							</div>
							 
						<div class="submitDiv">
							<input class="submitBtn" type="submit" value="Save" style="margin-right: 10px; width: 80px;">
							<input name="cancel" id="cancel" class="submitBtn" type="button" value="Cancel" style="margin-left: 10px; width: 80px;">
						</div>	
					</div>
				</div>
			</div>
 
</form>
</body>
</html>