<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<link media="all" href="<c:url value='/css/jquery.mCustomScrollbar.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/jquery.ui.all.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/utils.css'/>" type="text/css" rel="stylesheet">
<style>

.custLoader {
   float: right;
}
.custLoaderImg {
   float: right;
   height: 20px;
   width: 20px;
}
</style>

<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.widget.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>
<script src="<c:url value='/js/jquery.maskedinput.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
<script src="<c:url value='/script/cust_quick_summary.js'/>"></script>

<script >
var previousPhoneNumber = "";
$(document).ready(function(){
	
	$("input#moveDate").keypress(function(event){
		var key = event.charCode || event.keyCode;
		
		 // Ensure that it is a number and stop the keypress
        if(event.shiftKey || (key < 48 || key > 57)){
      		if(key!=8 && key!=46 && key!=9){
      			event.preventDefault(); 
    		}
		}
	});
	
	$('input#moveDate').keyup(autoChange);

	 $("#bestPhoneContact").mask("999-999-9999");
	 
	 $("input#bestPhoneContact").keypress(function(event){
		var phoneNum = ((this.value).split(" ")).join("");
		if(phoneNum.length>=12){
			if(previousPhoneNumber==""){
				previousPhoneNumber = phoneNum;
			}
			else{
				this.value = previousPhoneNumber;
			}
		}else{
			previousPhoneNumber = "";
		}
	});
	
	$("#custToggle").click(function(){
		var div = $("ul.genericList");
		if(div.is(":hidden")){
			$(this).removeClass("itemMinus");
	    } else {
	    	$(this).addClass("itemMinus");
	    }
		div.slideToggle();
	    div.next().slideToggle();
	    $("div#editDiv").slideToggle();
	});
	
	var tempAddress1 = "";
	var streetNumber = '${address.address.streetNumber}';
	var streetName = '${address.address.streetName}';
	var streetType = '${address.address.streetType}';
	var prefixDirectional = '${address.address.prefixDirectional}';
	var postfixDirectional = '${address.address.postfixDirectional}';

	if(streetNumber!=null && streetNumber.length>0){
		tempAddress1 = tempAddress1+streetNumber;
	}
	
	if((streetNumber!=null && streetNumber.length>0) && ((streetName!=null && streetName.length>0) || (streetType!=null && streetType.length>0) || (postfixDirectional!=null &&postfixDirectional.length>0) || (prefixDirectional!=null &&prefixDirectional.length>0))){
		tempAddress1 = tempAddress1+" ";
	}
	if(prefixDirectional!=null && prefixDirectional.length>0){
		tempAddress1 = tempAddress1+prefixDirectional+" ";
	}

	if(streetName!=null && streetName.length>0){
		tempAddress1 = tempAddress1+streetName;
	}

	if((streetName!=null && streetName.length>0) && ((streetType!=null && streetType.length>0) || (postfixDirectional!=null &&postfixDirectional.length>0))){
		tempAddress1 = tempAddress1+" ";
	}

	if(streetType!=null && streetType.length>0){
		tempAddress1 = tempAddress1+streetType;
	}
		
	if((streetType!=null && streetType.length>0) && (postfixDirectional!=null &&postfixDirectional.length>0)){
		tempAddress1 = tempAddress1+" ";
	}
		
	if(postfixDirectional!=null &&postfixDirectional.length>0){
			tempAddress1 = tempAddress1+postfixDirectional;
	}
	
	document.getElementById("address1DataId").innerHTML = tempAddress1;
	
	var bestContactNumber = '${order.customerInformation.customer.bestPhoneContact}';
	if(bestContactNumber.length>0){
		if(!isValidFormat(bestContactNumber)){
			var splitedArray = bestContactNumber.split("-");
			var tempNumber = "";
			for(var len=0;splitedArray.length>len;len++){
				tempNumber = tempNumber+splitedArray[len];
			}
			bestContactNumber = tempNumber;
			document.getElementById("bestPhoneContact").value = bestContactNumber.substring(0,3)+"-"+bestContactNumber.substring(3,6)+"-"+bestContactNumber.substring(6,bestContactNumber.length);
		}
	}
	var pageTitle = $("#pageTitle").val();
	if(pageTitle == undefined || pageTitle.trim() == ""){
		pageTitle = $("#pageTitle").text();
	}
	if(pageTitle == "Confirmation" || pageTitle == "Offers" ){
		$("input#bestEmailContact").attr('readonly','readonly').css({"color": "black", "-moz-user-select": "none", "cursor":"default",'background-color': '#EBEBE4'});
		$( "input#bestEmailContact" ).focus(function() {
			$( "input#bestEmailContact" ).blur();
		});
		
	}
	
});


function isValidFormat(conatactNumber){
	try{
		var splitedArray = conatactNumber.split("-");
		if(splitedArray.length==3){
			return true;
		}else{
			return false;
		}
	}catch(err){alert(err);}
}


</script>
</head>



					<!-- Widget Customer Information -->
					<div class="cusinfo_widget">									
						<c:set var="timestamp" value="${order.moveDate}"/>
						<c:choose>
							<c:when test="${empty closeCall}">
								<c:set var="show" value="true"/>
							</c:when>
							<c:when test="${closeCall eq false}">
								<c:set var="show" value="true"/>
							</c:when>
							<c:otherwise>
								<c:set var="show" value="false"/>
							</c:otherwise>
						</c:choose>
						<c:if test="${not empty timestamp}">
							<c:set var="year" value="${fn:substring(timestamp,	0, 4)}"/>
							<c:set var="month" value="${fn:substring(timestamp,	5, 7)}"/>
							<c:set var="date" value="${fn:substring(timestamp,	8, 10)}"/>
							<c:set var="time" value="${fn:substring(timestamp,	11, 19)}"/>
							<c:set var="timestamp" value="${month}/${date}/${year}"/>
						</c:if>
						<input type="hidden" name="addressId" id="addressId" value="${addressId}" />
						<input type="hidden" id="customerId" value="${order.customerInformation.customer.externalId}"/>
						<input type="hidden" id="orderIdForMoveDate" value="${order.externalId}"/>
						<input type="hidden"  id="contextPath" value="<%=request.getContextPath()%>" />
						<input type="hidden" id="unitTypeHid" value="${salescontext.scMap['unitType']}" /> 
						<input type="hidden" id="unitNoHid" value="${salescontext.scMap['unitNum']}" />
						<input type="hidden" id="dwelHid" value="${salescontext.scMap['dwellingType']}" /> 
						<input type="hidden" id="ownHid" value="${salescontext.scMap['rentOwn']}" /> 
						<input type="hidden" id="moveDateHid" value="${timestamp}" /> 
						<input type="hidden" id="bestEmailContactHid" value="${order.customerInformation.customer.bestEmailContact}"/>
						<input type="hidden" id="bestPhoneContactHid" value="${order.customerInformation.customer.bestPhoneContact}"/>
						<input type="hidden" id="ucid" value="<%=request.getSession().getAttribute("UCID") %>" name="ucid" />
						<div class="cusinfo_container">
							<div class="title" title="Customer Number: ${order.customerInformation.customer.externalId}"><fmt:message key="custQuick.custInfo"/>
								<span class="custLoader" style="display:none;position:absolute;top:3px;right:3px;">
									<img title="Loader" class="custLoaderImg" src="<%=request.getContextPath()%>/images/icon_loader.gif"/>
								</span>
							</div>
							<div class="cusinfo_cont_content">
								<table cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td width="35%">Order #:</td>
										<td>${order.externalId}</td>
									</tr>
									<tr>
										<td>Name:</td>
										<td><c:if test="${!empty order.customerInformation.customer.title}">${order.customerInformation.customer.title}&nbsp;</c:if>${order.customerInformation.customer.firstName}&nbsp;<c:if test="${!empty order.customerInformation.customer.middleName}">${order.customerInformation.customer.middleName}&nbsp;</c:if>${order.customerInformation.customer.lastName}<c:if test="${!empty order.customerInformation.customer.nameSuffix}">&nbsp;${order.customerInformation.customer.nameSuffix}</c:if></td>
									</tr>
									<tr>
										<td>Address 1:</td>
										<td id="address1DataId"></td>
									</tr>
									<tr>
										<td>Address 2:</td>
										<td>${address.address.line2}</td>
									</tr>
									<tr>
										<td>City/State/Zip:</td>
										<td>${address.address.city}&nbsp;${address.address.stateOrProvince}&nbsp;${address.address.postalCode}</td>
									</tr>
									<tr>
										<td>Address Type:</td>
										<td>
										<c:if test="${salescontext.scMap['dwellingType'] != null }">
										${salescontext.scMap['dwellingType']} - ${salescontext.scMap['rentOwn']}</c:if>
										</td>
									</tr>
									<tr>
										<td>Move In Date:</td>
										<td>						
											<input disabled id="moveDate" maxlength="10" name="moveDate" class="inputStyleDis" value="${timestamp}" style="padding: 1px 2px; color:black; min-height: 0px;" />
										</td>
									</tr>
									<tr>
										<td>Email:</td>
										<td><input disabled id="bestEmailContact" name="bestEmailContact" class="inputStyleDis" value="${order.customerInformation.customer.bestEmailContact}" style="padding: 1px 2px; color:black; min-height: 0px;"/></td>
									</tr>
									<tr>
										<td>Best Phone:</td>
										<td><input disabled id="bestPhoneContact" maxlength="12" name="bestPhoneContact" class="inputStyleDis" value="${order.customerInformation.customer.bestPhoneContact}" style="padding: 1px 2px; color:black; min-height: 0px;"/></td>
									</tr>
								</table>
							</div>
							<c:if test="${show eq 'true'}" >
							<div class="buttons" id="editDiv" style="display:block;">
								<input type="button" value="<fmt:message key="edit"/>" id="custEdit" />
							</div>
							<div class="buttons" id="custSaveCancel" style="display:none;">
								<input type="button" value="<fmt:message key="save.button"/>" id="custSave" />
								<input type="button" value="<fmt:message key="cancel.button"/>" id="custCancel" />
							</div>
							</c:if>
						</div>
					</div>
					<!-- End Widget Customer Information -->
