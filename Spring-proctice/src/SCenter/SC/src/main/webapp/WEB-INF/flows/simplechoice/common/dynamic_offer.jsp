<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>

<title><fmt:message key="offer.header"/></title>
<style>
.coaching 
{
color: green;
}
.Suggested
{
 color: grey;

}
.pagecontainer {
    background-color: #E5E0D9;
    border: 1px solid #616264;
    display: table-cell;
    height: 342px;
    text-align: center;
    vertical-align: middle;
    width: 997px;
}

.offerformcontent {
    background-color: #FFFFFF;
    border: 1px solid #616264;
    border-radius: 10px 10px 10px 10px;
    font-size: 12px;
    height: 219px;
    margin: 10px 0 10px 191px;
    padding: 13px 20px;
    width: 588px;
}
.offerformcontent select {
    border: 1px solid #616264;
    border-radius: 3px 3px 3px 3px;
    font-size: 12px;
    height: 28px;
    padding: 3px;
}
</style>
<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
<script type="text/javascript">
var submitvalue = '${submitvalue}';
var isClicked = true;
var counter = 0;
var isNotClicked = true;
var orderEmail = '${order.customerInformation.customer.bestEmailContact}';
$(document).ready(function(){
	updateTimer();
	if(submitvalue != null && submitvalue != "" && submitvalue=="Yes"){
		$("#Savers_Offer_Type_Yes").prop("checked",true);
		$("#Savers_Offer_Type_No").prop("checked",false);
			
		document.getElementById("Opening_Offer_NonRR_2").value = "Yes";
	}else{
		$("#Savers_Offer_Type_Yes").prop("checked",false);
		$("#Savers_Offer_Type_No").prop("checked",true);
		
		document.getElementById("Opening_Offer_NonRR_2").value = "No";
	}
		

	/*
	 $("#Opening_Offer_NonRR_2").live("change", function(){
     	$(this).css("color","#000000");	
     	var isEmailEmpty = $('#isEmailEmpty').val();
    	if(isEmailEmpty == 'true'){
    		var selectOffer = $('#Opening_Offer_NonRR_2').val();
    		if(selectOffer == 'Yes'){
    		 $('#emailDiv').show();
    		 }else{
    			 $('#emailDiv').hide();
        		 }
    	}
	    });
	    
	    */
	    
	    $('#offerForward').click(validateEmail);

	    $("#Savers_Offer_Type_Yes").change(function(){
		    try{
		    	isNotClicked = false;
		    	var isEmailEmpty = $('#isEmailEmpty').val();
				var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
				//invalid email case we set isEmailEmpty is true 
				if(!emailReg.test(orderEmail)){
					 isEmailEmpty = 'true';
				}
			 	if(!isClicked){
					$("#Savers_Offer_Type_Yes").prop("checked",false);
					$("#Savers_Offer_Type_No").prop("checked",true);
					document.getElementById("Opening_Offer_NonRR_2").value = "No";
					if(isEmailEmpty == 'true'){
						$('#emailDiv').hide();
			    	}
					isClicked = true;
			 	}else{
					$("#Savers_Offer_Type_Yes").prop("checked",true);
					$("#Savers_Offer_Type_No").prop("checked",false);
					document.getElementById("Opening_Offer_NonRR_2").value = "Yes";
					if(isEmailEmpty == 'true'){
						$('#emailDiv').show();
			    	}
					isClicked = true;
			 	}
		    }catch(err){alert(err);}
		});

		
		 $("#Savers_Offer_Type_No").change(function(){
			 try{
				 isNotClicked = false;
				 var isEmailEmpty = $('#isEmailEmpty').val();
					var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
					//invalid email case we set isEmailEmpty is true 
					if(!emailReg.test(orderEmail)){
						 isEmailEmpty = 'true';
					}
				 if(!isClicked){
					$("#Savers_Offer_Type_Yes").prop("checked",true);
					$("#Savers_Offer_Type_No").prop("checked",false);
					document.getElementById("Opening_Offer_NonRR_2").value = "Yes";
					if(isEmailEmpty == 'true'){
						$('#emailDiv').show();
			    	}
					isClicked = true;
				 }else{
					$("#Savers_Offer_Type_Yes").prop("checked",false);
					$("#Savers_Offer_Type_No").prop("checked",true);
					document.getElementById("Opening_Offer_NonRR_2").value = "No";
					if(isEmailEmpty == 'true'){
						$('#emailDiv').hide();
			    	}
					isClicked = true;
				 }
			 }catch(err){alert(err);}
		});

		$('body').keypress(function(event) {
			try{
				if(event.keyCode==40||event.keyCode==38){
					if(!isNotClicked){
						isClicked = false;
						counter++;
						if(counter%2==0){
							isClicked = true;
						}
					}
				}
			}catch(err){alert(err);}
		});
		jQuery(window).load(function () {
		      //To save the html on page load
		      savePageHtml(true, "");
		});
});


validateEmail = function(){
	var stat = true;
	var isEmailEmpty = $('#isEmailEmpty').val();
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	//invalid email case we set isEmailEmpty is true 
	if(!emailReg.test(orderEmail)){
		 isEmailEmpty = 'true';
	}
	if(isEmailEmpty == 'true'){
		var selectOffer = $('#Opening_Offer_NonRR_2').val();
		if(selectOffer == 'Yes'){
			var email = $('#email').val();
			if(email == ''){
				 $.alert("Please enter Email for Confirmation", "Alert", 200, 330);
				stat =  false;
			}else{
				var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
				if (!emailReg.test(email)){
					$.alert("Please enter valid Email","Alert", 200, 300);
					stat = false;
				}
			}
		}else if(selectOffer == "No"){
			$('#email').val('');	
		}
	}
	
	if(stat){
		//To save the html on page submit
		savePageHtml(false, "");
	} else {
		return false;
	}
}


function goToUtilityOffer(){
	//To save the html on page submit
	savePageHtml(false, "");
	$("form#offer").submit();
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
								<span class="cell pageTitle"><fmt:message key="offer.header" /></span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="offer" name="offer "action="${flowExecutionUrl}" method="post">
							<input type="hidden" name="offerJson" id="offerJson" value='${detailsOfferJson}' />
							<input type="hidden" value="Opening_Offer_NonRR_3" name="Opening_Offer_NonRR_3"/>
							<input type="hidden" id="pageTitle" name="pageTitle" value="Offers">
							<input type="hidden" id="_eventId" name="_eventId" value="saveSaversOfferEvent">
							<input type="hidden" id="endCall" name="endCall" value="End Call" />
							<input type="hidden"  id="contextPath" value="<%=request.getContextPath()%>" />
							<div class="contentwrapper">
								<section id="dialogue_wrapper">						   
									<section id="dialogue_content">
								<!-- 	<span style="color:red; font-weight:bold;">MANDATORY</span> -->
										${dialogue}
									</section>
									<div id="dialogue_content_balloon"></div>						
								</section>
								<div id="action_wrapper">
									<div class="widget_container">
										<div class="pagecontainer" align="center">
											<!-- Form Content -->
											<div class="offerformcontent">
												<div style="margin-left:15px; margin-top:85px;">
												<fmt:message key="offer.selectvalue" />&nbsp;:&nbsp;
												<input type="radio" id="Savers_Offer_Type_Yes" name="Savers_Offer_Type" value="Yes" <c:if test="${submitvalue == 'Yes'}">
													disabled="disabled"
													</c:if> />Yes
												<input type="radio" id="Savers_Offer_Type_No" name="Savers_Offer_Type" value="No" <c:if test="${submitvalue == 'Yes'}">
													disabled="disabled"
													</c:if> />No
													</div>
													<input type="hidden" id="Opening_Offer_NonRR_2" name="Opening_Offer_NonRR_2" value="">
													<!-- 
														<Select id="Opening_Offer_NonRR_2" name="Opening_Offer_NonRR_2" style="margin-left:15px; margin-top:85px;"
															<c:if test="${submitvalue == 'Yes'}">
																disabled="disabled"
															</c:if>
														>												    
												    		<option selected value=""><fmt:message key="offer.selectvalue" /></option>
															<option value="Yes">Yes</option>
															<option value="No">No</option>
														</Select>
													 -->
												<br /><br />	
												<div id="emailDiv" style="display:none">
													<input type="hidden"  id="isEmailEmpty" value='${isEmailEmpty}' />	
													Email:<input type="text" id="email" name="email" size="40" value='${order.customerInformation.customer.bestEmailContact}'>
												</div>
											</div>
											<!-- Form Content -->
										</div>
									</div>
								</div>
								<div class="bottombuttons">
									<div class="rightbtns">
										<input type="submit" id="offerForward" name="offerForward" value="Forward >"/>
									</div>
								</div>
							</div>
							<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
							<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							</form>
						</section><!--  Content -->
					</section><!-- Content Full Cont -->
