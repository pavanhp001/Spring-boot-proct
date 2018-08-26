<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<script src="<c:url value='/script/html_save.js'/>"></script>
<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.maskedinput.js'/>"></script>
<script type="text/javascript">

$(document).ready(function () {
	
	var emailType ="";
	if($("#emailType").val() != undefined && $("#emailType").val() != ""){
		emailType = $("#emailType").val();
	}
	
	
	if(emailType != undefined && emailType != ""){
		if(emailType.indexOf("tootip_doNotCall") != -1){
			$("#do_not_call_div").css('display','block');
		}
		if(emailType.indexOf("tootip_IT") != -1){
			$("#it_ticket_div").css('display','block');
		}
		if(emailType.indexOf("tootip_selfReport") != -1){
			$("#self_reporting_div").css('display','block');
		} 
	}
	$("input.contNoId:visible").mask("999-999-9999");
	 var bestContactNumber = $("input.contNoId:visible").val();
	 if(bestContactNumber != undefined && bestContactNumber != ""){
		bestContactNumber = bestContactNumber.substring(0,3)+"-"+bestContactNumber.substring(3,6)+"-"+bestContactNumber.substring(6,bestContactNumber.length);
		$("input.contNoId:visible").val(bestContactNumber); 
	}
	$("input.contNoId:visible").keypress(function(event){
		doNotAllowAlphabets(event);
			var phoneNum = ((this.value).split(" ")).join("");
			if(phoneNum.length >= 12){
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
	var bestContactNumber = '${order.customerInformation.customer.bestPhoneContact}';
	if(bestContactNumber.length>0){
		if(!isValidFormat(bestContactNumber)){
			var splitedArray = bestContactNumber.split("-");
			var tempNumber = "";
			for(var len=0;splitedArray.length>len;len++){
				tempNumber = tempNumber+splitedArray[len];
			}
			bestContactNumber = tempNumber;
			bestContactNumber = bestContactNumber.substring(0,3)+"-"+bestContactNumber.substring(3,6)+"-"+bestContactNumber.substring(6,bestContactNumber.length);
			$("input.contNoId:visible").val(bestContactNumber);
		}
	}
});

function doNotAllowAlphabets(event){
	if(event.key != undefined && event.keyCode != 0){
		if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 47 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
				(event.keyCode == 65 && event.ctrlKey === true) || (event.keyCode == 97 && event.ctrlKey === true) ||
				(event.keyCode >= 35 && event.keyCode <= 39) || (event.keyCode >= 48 && event.keyCode <= 57)) {
			return;
		}
		else {
			event.preventDefault(); 
		}
	}
	else{
		if ( event.keyCode == 46 || event.keyCode == 8 || event.charCode == 47 || event.keyCode == 9 || event.charCode == 27 || event.charCode == 13 || 
				(event.charCode == 65 && event.ctrlKey === true) || (event.charCode == 97 && event.ctrlKey === true) ||
				(event.keyCode >= 35 && event.keyCode <= 39) || (event.charCode >= 48 && event.charCode <= 57)) {
			return;
		}
		else {
			event.preventDefault(); 
		}
	}
};
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
function submitMail(){
	$("span.missingFields").remove();
	$("#validatorMsg").css("display","none");
	var isvalidData = true;
	/* if($("input.message").is(":visible") &&( $("input.message:visible").val()== undefined || $("input.message:visible").val() == "")){
		$("input.message:visible").after($('<span class="missingFields" style="color:red;">*</span>'));
		isvalidData = false;
	}else{
		$("span.missingFields").remove();
	} */
	
	if($("textarea.message:visible").is(":visible") &&( $("textarea.message:visible").val()== undefined || $("textarea.message:visible").val() == "")){
		$("textarea.message:visible").after($('<span class="missingFields" style="color:red;">*</span>'));
		isvalidData = false;
	}
	
	 if($("input.contNoId").is(":visible") &&( $("input.contNoId:visible").val() != undefined && $("input.contNoId:visible").val() != "")){
		 if($("input.contNoId:visible").val().trim().length < 12){
			$("input.contNoId:visible").after($('<span class="missingFields" style="color:red;">*Please enter valid contact number</span>'));
			isvalidData = false;
		 }
	}
	if($("input.emailId").is(":visible") && $("input.emailId:visible").val() != undefined && $("input.emailId:visible").val() != ""){
		console.log("validateEmail="+validateEmail());
		if(!validateEmail()){
			$("input.emailId:visible").after($('<span class="missingFields" style="color:red;">Please enter valid Email</span>'));
			isvalidData = false;
		}
	}
	if(!isvalidData){
		$("#validatorMsg").css("display","block");
		return false;
	}
	
	try{
		var path = "<%=request.getContextPath()%>";
		var url = path+"/salescenter/emailSend";
		var data = {};
		data["orderNumber"] = $("#orderNumber").val();
		data["customerName"] = $("#custNameSpan").text();
		data["bestContactNumber"] = $(".contNoId:visible").val();
		data["email"] = $(".emailId:visible").val();
		data["emailType"] = $("#emailType").val();
		if($("input.emailId").is(":visible")){
			if($("input.message:visible").is(':checked')){
				data["message"] = "Y";
			}else{
				data["message"] = "N";
			}
		}
		if($("textarea.message:visible").val() != undefined && $("textarea.message:visible").val() != ""){
			data["message"] = $("textarea.message:visible").val();
		}
		data["ucid"] = $("#ucid1").val();
		data["guid"] = $("#guid1").val();
		data["vdn"] = $("#vdn1").val();
		data["agentId"] = $("#agentId1").val(); 
		data["referrer"] = $("#referrer1").val();
		data["address"] = $("#address1").val();
		if($("#address1").val() != undefined && $("#address1").val() != ""){
			data["unittype"] = $("#unittype").val();
			data["state"] = $("#state").val();
			data["city"] = $("#city").val();
			data["zip"] = $("#zip").val();
		}else{
			data["unittype"] = "";
			data["state"] = "";
			data["city"] = "";
			data["zip"] = "";
		}
		console.log("data="+JSON.stringify(data));
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			//complete: bulidHintsContentForObjection
			success:function(data) {
				window.close();
    		},
    		error: function(data){
	            console.log("  Refresh results error  ");
	            window.close();
    		}
		}); 

	}catch(e){
		console.log("error in submitMail="+e);
		objectionClick=false;
		window.close();
	}
	//window.close();
}

validateEmail = function(){
	var stat = true;
	
	var email = $.trim($("input.emailId:visible").val());
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	if (!emailReg.test(email)){
		stat = false;
	}
	return stat;
}
			
	
</script>
<style>
.fontBold{
	font-weight: bold;
}
.width30per {
	width: 30%;
}
</style>
<body>
<div class="center">
		<div id="do_not_call_div" style="display: none">
			<table cellpadding="10" cellspacing="0" border="0">
			<h2>Do Not Call</h2>
				<tr><td class="order_number fontBold width30per"><label>Order Number: </label></td><td id="orderNoSpan" value="${order.externalId}"><span>${order.externalId}</span></td></tr>
				<c:choose>  
                    <c:when test="${ not empty salescontext.scMap['consumer.name.first'] &&  not empty salescontext.scMap['consumer.name.last']}">   
                        <tr><td class="customer_name fontBold width30per"><label>Customer Name: </label></td><td id="custNameSpan" value = "${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}"><span>${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}</span></td></tr>
                    </c:when> 
                    <c:when test="${not empty order.customerInformation.customer.firstName && not empty order.customerInformation.customer.lastName}">  
                          <tr><td class="customer_name fontBold width30per"><label>Customer Name: </label></td><td id="custNameSpan" value = "${order.customerInformation.customer.firstName}  ${order.customerInformation.customer.lastName}"><span>${order.customerInformation.customer.firstName} ${order.customerInformation.customer.lastName}</span></td></tr>
                    </c:when>  
                    <c:otherwise>   
                       <tr><td class="customer_name fontBold width30per"><label>Customer Name: </label></td><td id="custNameSpan" value = "${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}"><span>${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}</span></td></tr>
                    </c:otherwise>
             </c:choose>
				<tr><td class="best_cont_num fontBold width30per"><label>Best Contact Number: </label></td><td><input type="text" maxlength="12" class="contNoId" value="">
				 </td></tr>
				<tr><td class="email_donotcall fontBold width30per"><label>Email: </label></td><td><input type="text" class="emailId" value="${order.customerInformation.customer.bestEmailContact}">
				 </td></tr>
				<tr><td class="messageClass fontBold width30per"><label>Did customer ask for copy of Do Not Call policy? </label></td><td>
				<input type="checkbox" id="message" name="message" value="" class="message"/>
				<!-- <input type="text" placeholder="Y" name= "message" id="message" class="message" value=""> --></td></tr>
			</table>
		</div>
		<div id="it_ticket_div" style="display: none">
			<c:if test="${not empty salescontext.scMap['unit.type']}">
				<c:set var="unitType" value="${salescontext.scMap['unit.type']}"/>
			</c:if>
			<c:if test="${not empty salescontext.scMap['unitType']}">
				<c:set var="unitType" value="${salescontext.scMap['unitType']}"/>
			</c:if>
			<c:if test="${empty salescontext.scMap['unit.type']}">
				<c:if test="${empty salescontext.scMap['unitType']}">
					<c:set var="unitType" value=""/>
				</c:if>
			</c:if>
			<c:if test="${not empty salescontext.scMap['unit.number']}">
				<c:set var="unitNum" value="${salescontext.scMap['unit.number']}"/>
			</c:if>
			<c:if test="${not empty salescontext.scMap['unitNum']}">
				<c:set var="unitNum" value="${salescontext.scMap['unitNum']}"/>
			</c:if>
			<c:if test="${empty salescontext.scMap['unit.number']}">
				<c:if test="${empty salescontext.scMap['unitNum']}">
					<c:set var="unitNum" value=""/>
				</c:if>
			</c:if>
		  <h2>IT Ticket</h2>
		  <table cellpadding="10" cellspacing="0" border="0">
		  <tr><td class="message fontBold width30per"><label>Description of the problem:  </label></td><td><textarea rows="4" cols="50" type="text" class="message"></textarea></td></tr>
		  <tr><td style="padding-right: 0px;"><hr/></td><td style="padding-left: 0px;"><hr/></td></tr>
		    <tr><td class="order_number fontBold width30per"><label>Order Number: </label></td><td id="orderNoSpan" value="${order.externalId}"><span>${order.externalId}</span></td></tr>
		    <tr><td class="ucid fontBold width30per"><label>GUID: </label></td><td id="ucid" value="${GUID}"><span>${GUID}</span></td></tr>
		    <tr><td class="agentIdClass fontBold width30per"><label>Agent Id: </label></td><td id="agentId"><span>${salescontext.scMap['agent.id']}</span></td></tr>
		    <c:choose>  
                    <c:when test="${ not empty salescontext.scMap['consumer.name.first'] &&  not empty salescontext.scMap['consumer.name.last']}">   
                        <tr><td class="customer_name fontBold width30per"><label>Customer Name: </label></td><td id="custNameSpan" value = "${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}"><span>${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}</span></td></tr>
                    </c:when> 
                    <c:when test="${not empty order.customerInformation.customer.firstName && not empty order.customerInformation.customer.lastName}">  
                          <tr><td class="customer_name fontBold width30per"><label>Customer Name: </label></td><td id="custNameSpan" value = "${order.customerInformation.customer.firstName}  ${order.customerInformation.customer.lastName}"><span>${order.customerInformation.customer.firstName} ${order.customerInformation.customer.lastName}</span></td></tr>
                    </c:when>  
                    <c:otherwise>   
                       <tr><td class="customer_name fontBold width30per"><label>Customer Name: </label></td><td id="custNameSpan" value = "${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}"><span>${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}</span></td></tr>
                    </c:otherwise>
             </c:choose>
		      <c:if test="${empty salescontext.scMap['address.street1']}">
				<c:set var="address" value=""/>
			</c:if>
			<c:if test="${not empty salescontext.scMap['address.street1']}">
				<c:set var="address" value="${salescontext.scMap['address.street1']} ${unitType} ${unitNum}, ${salescontext.scMap['address.zip']}"/>
			</c:if>
		    <tr><td class="address fontBold width30per"><label>Address: </label></td><td id="address" value = "${address}"><span>${address}</span></td></tr>
		   <tr><td class="vdn fontBold width30per"><label>VDN: </label></td><td id="vdn" value="${salescontext.scMap['vdn']}"><span>${salescontext.scMap['vdn']}</span></td></tr>
		   
		   <tr><td class="referrer fontBold width30per"><label>Referrer (Name and ID): </label></td><td  id="referrer" value="${salescontext.scMap['referrer.businessParty.referrerId']}   ${salescontext.scMap['referrer.businessParty.referrerName']}">${salescontext.scMap['referrer.businessParty.referrerId']}   ${salescontext.scMap['referrer.businessParty.referrerName']}</span></td></tr>
		   
		  </table>
		  <c:if test="${not empty salescontext.scMap['ticketImgString']}">
			  <div id="screenshots_canvas">
			  	<img src="data:image/png;base64,${salescontext.scMap['ticketImgString']}" height="550" alt="${salescontext.scMap['ticketImgName']}" border="0" />
			  </div>
		  </c:if>
		  <c:if test="${not empty salescontext.scMap['ticketIframeImgString']}">
			  <div id="screenshots_canvas">
			  	<img src="data:image/png;base64,${salescontext.scMap['ticketIframeImgString']}" height="550" alt="${salescontext.scMap['ticketIframeImgName']}" border="0" />
			  </div>
		  </c:if>
		</div>
		<div id="self_reporting_div" style="display: none">
		<h2>Self Reporting</h2>
			<table cellpadding="10" cellspacing="0" border="0">
			 <tr><td class="order_number fontBold width30per"><label>Order Number: </label></td><td id="orderNoSpan" value="${order.externalId}"><span>${order.externalId}</span></td></tr>
			 <c:choose>  
                    <c:when test="${ not empty salescontext.scMap['consumer.name.first'] &&  not empty salescontext.scMap['consumer.name.last']}">   
                        <tr><td class="customer_name fontBold width30per"><label>Customer Name: </label></td><td id="custNameSpan" value = "${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}"><span>${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}</span></td></tr>
                    </c:when> 
                    <c:when test="${not empty order.customerInformation.customer.firstName && not empty order.customerInformation.customer.lastName}">  
                          <tr><td class="customer_name fontBold width30per"><label>Customer Name: </label></td><td id="custNameSpan" value = "${order.customerInformation.customer.firstName}  ${order.customerInformation.customer.lastName}"><span>${order.customerInformation.customer.firstName} ${order.customerInformation.customer.lastName}</span></td></tr>
                    </c:when>  
                    <c:otherwise>   
                       <tr><td class="customer_name fontBold width30per"><label>Customer Name: </label></td><td id="custNameSpan" value = "${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}"><span>${salescontext.scMap['consumer.name.first']}  ${salescontext.scMap['consumer.name.last']}</span></td></tr>
                    </c:otherwise>
             </c:choose>
			 <tr><td class="best_cont_num fontBold width30per"><label>Best Contact Number</label></td><td><input type="text" maxlength="12" class="contNoId" value=""></td></tr>
			 <tr><td class="email_donotcall fontBold width30per"><label>Email</label></td><td><input type="text" class="emailId" value="${order.customerInformation.customer.bestEmailContact}">
			 </td></tr>
			<tr><td class="message fontBold width30per"><label>Please provide detailed reason for self-reporting : </label></td><td><textarea rows="4" cols="50" type="text" class="message	"></textarea></td></tr>
			</table>
		</div>
		<div class="row" id="validatorMsg" style="display:none; color:#F00; margin: 0px 0px 10px 0" >
			<span>* Required Field</span>
		</div>
		<input type="hidden" name="orderNoSpan" id="orderNumber" value="${order.externalId}">
		<input type="hidden" name="emailType" id="emailType" value="${mailType}">
		<input type="hidden" name="ucid1" id="ucid1" value="${ucid}">
		<input type="hidden" name="guid1" id="guid1" value="${GUID}">
		<input type="hidden" name="agentId1" id="agentId1" value="${salescontext.scMap['agent.id']}">
		<input type="hidden" name="vdn1" id="vdn1" value="${salescontext.scMap['vdn']}">
		<input type="hidden" name="referrer1" id="referrer1" value="${salescontext.scMap['referrer.businessParty.referrerId']} &nbsp; ${salescontext.scMap['referrer.businessParty.referrerName']}">
	    <input type="hidden" name="address1" id="address1" value="${salescontext.scMap['address.street1']}">
	    <input type="hidden" name="unittype" id="unittype" value="${unitType} ${unitNum}">
	    <input type="hidden" name="state" id="state" value="${salescontext.scMap['address.state']}">
	    <input type="hidden" name="city" id="city" value="${salescontext.scMap['address.city']}">
	    <input type="hidden" name="zip" id="zip" value="${salescontext.scMap['address.zip']}">
	    <input type="hidden" name="canvas_ticket" id="canvas_ticket" value="${salescontext.scMap['canvasImg']}">
	<button onclick="submitMail()">Submit</button>
</div>
</body>
</html>