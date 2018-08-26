<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title><fmt:message key="closingcall.header" /></title>

<style type="text/css">
#wrapper {
    margin: 0;
    overflow: hidden;
    text-align: left;
}
.content_border{
  height:532px;
}
.coaching 
{
        color: green;
}
.Suggested
{
 color: grey;
 font-weight:bold;
 padding-top: 14px;
}
.row p{
	padding: 5px;
}
.bottomDweller {
     margin-left: 290px;
    margin-top: 37px;
    position: absolute;
}

#emailTextBox {
  background-color: #FFFFFF;
  border: 1px solid #616264;
  border-radius: 10px 10px 10px 10px;
  height: 125px;
  margin-left: 216px;
  padding: 20px;
  width: 538px;
}
</style>

<script src="<c:url value='/script/html_save.js'/>"></script>

<script type="text/javascript">

var isClicked = true;

$(document).ready(function(){
	$("#emailYes").click(function(){
		showEmail();	
	});
	$("#emailNo").click(function(){
		hideEmail();	
	});

	 $("#emailYes").change(function(){
		 $("span.missingFields").remove();
		 $("span#validatorMsg").css('display','none');
		 if(!isClicked){
			$("#emailYes").prop("checked",false);
			$("#emailNo").prop("checked",true);
			hideEmail();
			isClicked = true;
		 }else{
			$("#emailYes").prop("checked",true);
			$("#emailNo").prop("checked",false);
			showEmail();
			isClicked = true;
		 }
	});
	 $("#emailNo").change(function(){
		 $("span.missingFields").remove();
		 $("span#validatorMsg").css('display','none');
		 if(!isClicked){
			$("#emailYes").prop("checked",true);
			$("#emailNo").prop("checked",false);
			showEmail();
			isClicked = true;
		 }else{
			$("#emailYes").prop("checked",false);
			$("#emailNo").prop("checked",true);
			hideEmail();
			isClicked = true;
		 }
	});

	 $('body').keypress(function(event) {
		 try{
			if(event.keyCode==40||event.keyCode==38){
				isClicked = false;
			}
		 }catch(err){alert(err);}
	});

	//To save the html on page load
	 setTimeout(function(){ 
         savePageHtml(true, "");
       }, 300);
	 
	});


function showEmail() {
	   $("#emailFlag").val('Yes');
	   $("#bestEmail").show();
} 
function hideEmail()	{
	   $("#emailFlag").val('No');
	   $("#bestEmail").hide();
}

var validateEmail = function(){
	$("span.missingFields").remove();
	var preURL = document.referrer;
	$("#preURL").val(preURL);
	 if($('#emailFlag').val() != '') {
		 if($('#emailFlag').val() == 'Yes') {
			 var email =$.trim($('input#bestEmail').val());
			 $('input#bestEmail').val(email);
			 var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
			 if (email != null && email != "" && emailReg.test(email)){
				//To save the html on page submit
				 savePageHtml(false, "");

				 $("form#closingcall").submit();
			 } else {
				 alert("Please enter valid Email"); 
			 } 
	     }
		 else{
			 $("form#closingcall").submit();
		 }
	 }else{
		 $("#no").after($('<span class="missingFields">*</span>'));
		 $("span#validatorMsg").css('display','block');
	 }
	 
}
	</script>
</head>
<section id="contentfullcont">
						<header id="content_header">
							<div class="row">
								<span class="cell">
									<svg version="1.1" id="Layer_5" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
										 width="29px" height="29px" viewBox="0 0 29 29" enable-background="new 0 0 29 29" xml:space="preserve" class="headphonesSVG">
										<g>
											<path fill="#96C43E" d="M29,25.298c0,2.066-1.677,3.744-3.744,3.744H4.411c-2.067,0-3.745-1.678-3.745-3.744V4.328
												c0-2.068,1.678-3.745,3.745-3.745h20.845C27.323,0.583,29,2.26,29,4.328V25.298z"/>
											<path fill="#FFFFFF" d="M23.861,18.677c0.116,0.435,0.177,0.891,0.177,1.362c0,2.921-2.367,5.289-5.288,5.289
												c-0.291,0-0.576-0.023-0.854-0.068l-0.004-0.002c-0.285,0.446-0.843,0.75-1.482,0.75c-0.932,0-1.686-0.64-1.686-1.428
												s0.754-1.428,1.686-1.428c0.563,0,1.061,0.234,1.367,0.594l0,0c0.208,0.029,0.422,0.044,0.641,0.044
												c2.19,0,3.967-1.542,3.967-3.445c0-0.307-0.046-0.604-0.132-0.887l-0.052,0.015c-0.244,0.015-0.527,0.008-0.855-0.031v0.608
												c0,0-0.059,0.398-0.808,0.375s-0.784-0.234-0.796-0.445c-0.012-0.21,0-5.5,0-5.5s0.047-0.409,0.819-0.397
												c0.655,0.012,0.737,0.246,0.761,0.375c0.023,0.128,0.023,0.784,0.023,0.784l0.374-0.059l0.088-0.058
												c0.189-0.664,0.265-1.24,0.265-1.966c0-4.188-3.395-7.583-7.582-7.583c-4.188,0-7.583,3.395-7.583,7.583
												c0,0.722,0.128,1.363,0.316,2.024l0.374,0.059c0,0,0-0.655,0.023-0.784s0.105-0.363,0.761-0.375
												c0.772-0.012,0.819,0.398,0.819,0.398s0.012,5.289,0,5.5s-0.047,0.422-0.796,0.445s-0.808-0.375-0.808-0.375v-0.608
												c-1.193,0.141-1.802-0.141-2.07-0.339c-0.27-0.199-1.065-0.913-0.738-2.293c0.328-1.381,1.451-1.58,1.674-1.58
												s0.164-0.07,0.164-0.07c-0.416-1.001-0.609-2.02-0.609-3.171c0-4.679,3.793-8.472,8.473-8.472c4.678,0,8.472,3.793,8.472,8.472
												c0,1.107-0.212,2.165-0.599,3.134l-0.046,0.037c0,0-0.059,0.07,0.163,0.07c0.223,0,1.346,0.199,1.674,1.58
												c0.205,0.863-0.029,1.465-0.299,1.845L23.861,18.677z"/>
										</g>
									</svg>
								</span>
								<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
								<span class="cell pageTitle"><fmt:message key="closingcall.header"/></span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
					<section id="content">
						<form id="closingcall" name="closingcall" action="${flowExecutionUrl}" method="post">
							<input type="hidden" name="preURL" id="preURL" value="" />
							<input type="hidden" id="emailFlag" name="emailFlag" value="">
							<input type="hidden" id="pageTitle" name="pageTitle" value="Close Call">
							<input type="hidden" id="_eventId" name="_eventId"	value="dispositionsEvent">
							 <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<div class="contentwrapper">
								<section id="dialogue_wrapper">						   
									<section id="dialogue_content">
										${dialogue}
									</section>
									<div id="dialogue_content_balloon"></div>						
								</section>
								<div id="action_wrapper">
									<div class="widget_container">
										<div class="pagecontainer" align="center">
											<div  id="emailTextBox" >
											   <section style="margin-top:10px;width:550px;">
											   <lable>Does customer want to receive link to AL website?</lable>&nbsp; &nbsp;&nbsp;
										        <span id="yes"><input type="radio" id="emailYes" name="optin"  style="vertical-align:top;"/>&nbsp;Yes</span>
											    <span id="no"><input type="radio" id="emailNo" name="optin"  style="vertical-align:top;"/>&nbsp;No</span>
											    <br>
											    <br><br>
												<input type="text" size="30px"id="bestEmail" name="bestEmail" value="${order.customerInformation.customer.bestEmailContact}" style="display:none"  ><br/>
											    <br/>
												</section>
												
											</div>
										</div>
									</div>
								</div>
								
								<span class="row" id="validatorMsg" style="display:none;color:#F00;" >
									<span class="cell" style="font-weight:bold;margin-top:20px;">* Required Field</span>
								</span>
								<div class="bottombuttons">
									<div class="rightbtns">
										<input type="button" id="closingCallForward" name="closingCallForward" value="Forward >" onclick="validateEmail();" />
									</div>
								</div>
							</div>
							<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
							<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							</form>
						</section>
					</section>
