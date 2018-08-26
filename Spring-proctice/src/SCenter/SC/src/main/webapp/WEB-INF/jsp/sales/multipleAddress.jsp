<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title><fmt:message key="basicinfo.header" /></title>

 
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.datepicker.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.all.css'/>" />
<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.widget.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
 
<script type="text/javascript"> 
$(document).ready(function(){
	jQuery(window).load(function () {
	      //To save the html on page load
	      savePageHtml(true, "");
	});
});

function goToConfirm(){
	
	if(document.getElementById("validationMessageId")!=null)
		$('#multipleaddrformcontent').children('#validationMessageId').remove();
	
	var allRadioButtons =  $('.addRadio');
	var val = "";
		allRadioButtons.each(function(){ 
			if((this.checked ? 'checked' : 'unchecked')=='checked')  	    
		{
				val=$(this).val();	
		}
 	});
	 	
	if(val!=""){
		var path = $("#contextPath").val();
		
		//To save the html on page submit
		savePageHtml(false, "");
		
		$("form#basicInfo").submit();
	}else{
		//$("form#basicInfo").submit();
		$('#multipleaddrformcontent').append($('<span id="validationMessageId" style="font-size: 13px;" class="missingFields multiaddrrow">* Address selection required.</span>'));
	}
	
}

 </script>

<style type="text/css">
span{
	font-size:13px;
}
.coaching {
	color: green;
	font-weight:bold;
}
.Suggested
{
 color: grey;
 font-weight:bold;
}
.content_border {
    background-color: #FFFFFF;
    border-color: transparent #616264 #616264;
    border-right: 1px solid #616264;
    border-style: solid;
    border-width: 1px;
    box-shadow: 11px 10px 21px rgba(0, 0, 0, 0.8);
    display: block;
    height: 532px;
    margin: 0;
    padding: 0;
}
.multipleaddrformcontent {
    background-color: #FFFFFF;
    border: 1px solid #616264;
    border-radius: 10px 10px 10px 10px;
    font-size: 12px;
    height: 280px;
    margin: 10px 0 10px 191px;
    padding: 8px 20px;
    width: 588px;
}
.pagecontainer {
    background-color: #E5E0D9;
    border: 1px solid #616264;
    display: table-cell;
   height: auto;
    text-align: center;
    vertical-align: middle;
    width: 997px;
}
.multiaddrrow {
    float: left;
    text-align: left;
    width: 100%;
}
.multirefreshButton{

margin-left:520px;
}
</style>

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
								<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
								<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
								<span class="cell pageTitle">Multiple Address</span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="basicInfo" name="basicInfo" action="<%=request.getContextPath()%>/salescenter/isvalidaddress" method="post" autocomplete="off">
							<input type="hidden"  id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="firstName"  name="firstName" value="${firstName}">
							<input type="hidden" id="lastName" name="lastName" value="${lastName}">
							<input type="hidden" id="Prefix" name="Prefix" value="${Prefix}">
							<input type="hidden" id="Suffix" name="Suffix" value="${Suffix}">
							<input type="hidden" id="middleName" name="middleName" value="${middleName}">
							<input type="hidden" id="unittype" name="unittype" value="${unittype}">
							<input type="hidden" id="addressTypeList" name="addressTypeList" value="${unittype}">
							<input type="hidden" id="unitNumber" name="unitNumber" value="${unitnumber}">
							<input type="hidden" id="serviceAddrType" name="serviceAddrType" value="${serviceAddrType}">
							<input type="hidden" id="rentOwnList" name="rentOwnList" value="${rentown}">
							<input type="hidden" id="datepicker1" name="datepicker1" value="${datepicker1}">
							<input type="hidden" id="datepicker2" name="datepicker2" value="${datepicker2}">
							<input type="hidden" id="datepicker3" name="datepicker3" value="${datepicker3}">
							<input type="hidden" id="noneoftheabove" name="noneoftheabove" value="${completeAddress}">
							<input type="hidden" id="isMultiAddress" name="isMultiAddress" value="isMultiAddress">
							<input type="hidden" id="pageTitle" name="pageTitle" value="Multiple Address">
							<div class="contentwrapper">
								<section id="dialogue_wrapper">						   
									<section id="dialogue_content">
										<div class="row" style="padding:10px;">
											<span class="styled coaching custom_padding" style="line-height:18px;">
												Multiple addresses match.<br /><br />
												Please select one of the following normalized addresses that most closely represents
												the address above. If available, select an address with a 9 digit (zip+4) zipcode.<br /><br />
												If the dwelling is an apartment, be sure that the correct unit number is in the
												address field.
											</span>
										</div>
									</section>
									<div id="dialogue_content_balloon"></div>						
								</section>
								<div id="action_wrapper">
									<div class="widget_container">
										<div class="pagecontainer" align="center">
											<!-- Form Content -->
											<div class="multipleaddrformcontent" id="multipleaddrformcontent">
												<fieldset>
							    					
														<span class="multiaddrrow"> 
															<b>Entered Service Address from Last page :</b> <br /><br />
															${completeAddress}
														</span> <br />
														<span class="multiaddrrow"  style="margin-top:10px; padding:5px"> 
														<b>Please select one of the following normalized addresses that most closely represents the
														entered address above. <span class="styled coaching custom_padding">If available,select an address with a 9 digit (Zip+4) Zipcode:</span></b>
														<br/></span>
														<c:forEach var="address" items="${dialogue}" varStatus="loop">
															<span class="multiaddrrow"> 
								                                 <input id="${loop.index}" name="address1" type="radio" class="addRadio"  title="Address 1" style="margin-right :3px;" value="${address}"/>${address}
															</span> 
														</c:forEach>
													</div>
													
													
												</fieldset>	
											<!-- Form Content -->
										</div>
									</div>
								</div>
								
									<div class="bottombuttons">
									<div class="rightbtns">
										              <input type="button" id="multipleAddressSubmit" name="multipleAddressSubmit" value="Forward >" onclick="goToConfirm();"/>
										</div>
								</div>
								
							</div>
							</form>
						</section><!--  Content -->
					</section><!-- Content Full Cont -->
