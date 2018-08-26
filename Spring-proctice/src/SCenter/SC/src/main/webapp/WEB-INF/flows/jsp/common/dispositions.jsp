<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title>Dispositions</title>

<link rel="stylesheet" href="<c:url value='/style/cart/default/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/cart/default/CKO_base.css'/>" />
<%-- <script src="<c:url value='/script/feedback.js'/>"></script> --%>
<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.maskedinput.js'/>"></script>

<style type="text/css">

#wrapper {
    margin: 0;
    overflow: hidden;
    text-align: left;
}
.content_border{
  height:532px;
}


#left_aside {
    border: 1px solid transparent;
    float: left;
    margin: 0;
    min-width: 120px;
    padding: 0 5px;
}
.address {
	width:300px;
	height: 100px;
	border:2px solid #a1a1a1;
	border-radius:5px;
	padding:8px;
	display: table;
	float: left;
}

.submitDiv {
    clear: both;
    margin-left: 30px;
    padding-top: 20px;
}
#disposition {
 	font-weight: bold;
}
.divideBlock {
    border-right: 1px solid #373737;
    float: left;
    height: 100%;
    min-height: 120px;
    min-width: 200px;
    width: 200px;
}

.submitDispositionBtn {
    padding-bottom: 2px;
	position: absolute;
	top: 564px; left: 0;
	width: 100%;
	z-index: 1000;
}
.submitDispositionBtn input{
	font-size:14px;
	color:#000;
	height:22px;
	border:1px solid #c6c6c6;
	border-radius: 5px;
	padding-bottom: 0px;
	/* background: url("../images/button_back.png") repeat-x; */
	cursor: pointer;
}
.dispositionsTable table,td{
	border:none;
}
.dispositionsTable .title{
	font-size:16px;
}
</style>
<script src="<c:url value='/script/html_save.js'/>"></script>
<script type="text/javascript">

$(document).ready(function(){
	
	$(".radioDiv").click(function(){ 
		showSubmitButton();
 		var currVal=$(this).val();
 		var allRadioButtons =  $('.radioDiv');
 		var count=0;
 		allRadioButtons.each(function(){ 
 			if((this.checked ? 'checked' : 'unchecked')=='checked')  	    
			{
	 			count++;
			}
	 	});
	 	if(count>1){
		 	allRadioButtons.each(function(){
			 if((this.checked ? 'checked' : 'unchecked')=='checked')  	    
				{
			 if($(this).val()!= currVal){
				 $(this).attr('checked', false);
		                 }
				} });
	 	}
	});
	
	$("#submitDisposition").click(saveDispAndRedirect);

	var height = 0;
	$(".address").each(function(){
	    var ht = $(this).height();
	    if(ht > height){
	        height = ht;
	    }
	});

	$(".address").height(height);

	var timer = 0;
	var contextPath = $("input#contextPath").val();
	setInterval(function(){
		if(timer == ${timeOut}){
			if(!isFormSubmitted)
			{
				saveDispAndRedirect();
			}
			
		} else {
			timer ++;
		}
	}, 1000);

	//Here we setting saveoffers LineitemExternalID in body level of page for score capturing .
	$( "body" ).data( "li_externalId" , "${offerLineItemExtID}");
	
	//To save the html on page load
	setTimeout(function(){ 
        savePageHtml(true, "");
    }, 300); 
});

var showSubmitButton = function(){
	if($('.radioDiv:checked').length >= 1 ){
		$('input#submitDisposition').attr("disabled",false);
	}else{
		$('input#submitDisposition').attr("disabled",true);
	}
}

var isFormSubmitted = false;
var isOneCheckboxChecked = false;
var saveDispAndRedirect = function(){
	isFormSubmitted = true;
	var selectedArray = [];
		var allRadioButtons =  $('.radioDiv');  
		allRadioButtons.each(function(){ 
			var name = $(this).attr("name");				    
			if((this.checked ? 'checked' : 'unchecked')=='checked')  	 	    
			{  	
				selectedArray.push($(this).val());      
				$("#selectedDisp").val(selectedArray);
				isOneCheckboxChecked = true;
			}  	      	
		}); 
		if (!isOneCheckboxChecked) {
			//timeout with nothing selected
			$("#selectedDisp").val('11293');
		}				
		//save the html before dispositions form submit
  	  	savePageHtml(false, "");	
		$("form#dispForm").submit();   	
}

function backToClosingCall(){
	var href = window.location.href;
	window.location = href.slice(0, href.lastIndexOf("/")+1) + "closingcall";
}


function goToSaveDispositions(){
	isFormSubmitted = true;
	var selectedArray = [];
	var allRadioButtons =  $('.radioDiv');  
	allRadioButtons.each(function(){ 
		var name = $(this).attr("name");				    
		if((this.checked ? 'checked' : 'unchecked')=='checked')  	 	    
		{  	
			selectedArray.push($(this).val());        
		}  	      	
	}); 
	$("#selectedDisp").val(selectedArray);	
	$("form#dispForm").submit();   	
}
</script>
</head>

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
   		<span class="cell pageTitle">Dispositions</span>
   		<span class="callTime" id="startTimeText"></span>
	</div>
</header>

<section id="content">
<div class="contentwrapperdp">
   	<div id="action_wrapper">
		<form action="<%=request.getContextPath()%>/salescenter/home" method="post" id="dispForm">
		<input type="hidden" name="selectedDisp" id="selectedDisp" value="" />
		<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
		<input type="hidden" id="pageTitle" name="pageTitle" value="Dispositions">
        	<div class="widget_container">
        		<div class="dis_content">
        			<div class="dis_content_header">
        				<div class="dis_content_headtitle">Time or Approval</div>
             			<div class="dis_content_headtitle">Price or Credit</div>
                		<div class="dis_content_headtitle">Products or Service</div>
        			</div>
        			<div class="dis_content_data">
        				<div class="dis_content_data_col">
          					<c:forEach items="${timeAndApprovalDispositionMap}" var="timeDisp" varStatus="loopCounter">
          						<div class="dis_content_data_col_item">
									<span style="label">
										<input type="radio" value="${timeDisp.key}" id="timeDisp${loopCounter.count}" class="radioDiv ">
									</span>
									<span style="value">
										<c:out escapeXml="false" value="${timeDisp.value}" />
									</span>
          						</div>
							</c:forEach>
        				</div>
             			<div class="dis_content_data_col">
         					<c:forEach items="${priceAndCreditDispositionIdMap}" var="priceDisp" varStatus="loopCounter">
          						<div class="dis_content_data_col_item">
	         						<span style="label">
										<input type="radio" id="priceDisp${loopCounter.count}" value="${priceDisp.key}"  class="radioDiv ">
									</span>
									<span style="value">
										<c:out escapeXml="false" value="${priceDisp.value}" />
									</span>
          						</div>
							</c:forEach>
             			</div>
                		<div class="dis_content_data_col">
         					<c:forEach items="${productsAndServiceDispositionIdMap}" var="creditDisp" varStatus="loopCounter">
          						<div class="dis_content_data_col_item">
	         						<span style="label">
										<input type="radio" id="creditDisp${loopCounter.count}" value="${creditDisp.key}" class="radioDiv ">
									</span>
									<span style="value">
										<c:out escapeXml="false" value="${creditDisp.value}" />
									</span>
          						</div>
							</c:forEach>
                		</div>
        			</div>
        		</div>
        		
        		<div class="dis_content_next">
        			<div class="dis_content_header">
             			<div class="dis_content_headtitle">No Sales Opportunity</div>
                	<!-- <div class="dis_content_headtitle">Other</div>- -->	
        			</div>
        			<div class="dis_content_data">
             			<div class="dis_content_data_col">
             				<c:forEach items="${noSalesOpportunityDispositionIdMap}" var="noOportunityDisp" varStatus="loopCounter">
          						<div class="dis_content_data_col_item">
	         						<span style="label">
										<input type="radio" value="${noOportunityDisp.key}" id="noOpprtDisp${loopCounter.count}" class="radioDiv ">
									</span>
									<span style="value">
										<c:out escapeXml="false" value="${noOportunityDisp.value}" />
									</span>
          						</div>
							</c:forEach>
             			</div>
        			</div>
        		</div>
	            
           	</div>
           	<input type="hidden" id="agentId" name="agentId" value="${salescontext.scMap['agent.id']}">
			<input type="hidden" id="customerId" value="<%=request.getSession().getAttribute("customerID") %>" name="customerId" />
			<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId")!=null?request.getSession().getAttribute("orderId"):"" %>" name="orderId" />
			<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
			<input type="hidden" id="requiresCSAT" name="requiresCSAT" value="<%=request.getSession().getAttribute("requiresCSAT")%>">
			<input type="hidden" id="salesCallId" name="salesCallId" value="<%=request.getSession().getAttribute("salesCallId")%>">
			<input type="hidden" id="ambDisconnectdatetime" name="ambDisconnectdatetime" value="<%=request.getSession().getAttribute("ambDisconnectdatetime")!=null?request.getSession().getAttribute("ambDisconnectdatetime"):""%>">
			<input type="hidden" id="isProductSaleInFlow" name="isProductSaleInFlow" value="true">
			<input type="hidden" id="addressId" name="addressId" value="<%=request.getSession().getAttribute("addressId")%>">
			<input type="hidden" id="urlPath" name="urlPath" value="<%=request.getSession().getAttribute("urlPath")%>">
		</form>
	</div>
	<div class="submitDispositionBtn">
        <div class="rightbtns">
             <input type="submit" value="Submit Disposition"  id="submitDisposition" onclick="goToSaveDispositions()" disabled="disabled"/>
    	</div>
	</div>
</section>
	
	
	<footer id="action_footer">
	</footer>

