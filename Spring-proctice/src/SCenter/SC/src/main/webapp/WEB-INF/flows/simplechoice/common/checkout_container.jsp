<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>AL <fmt:message key="${title}"/></title>
<script src="<c:url value='/js/jquery-1.8.0.js'/>"></script>
<script src="<c:url value='/js/feedback_main_flow.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>

<style type="text/css">
.contentwrapperos {
  height: 554px;
  width: 1010px;
}
.address {
	width: 300px;
	height: 110px;
	border: 2px solid #a1a1a1;
	border-radius: 5px;
	padding: 8px;
	display: none;
}

.rLabel {
	
}

.lLabel {
	float: left;
	font-weight: bold;
	width: 25%;
}

iframe {
  border: 0 none !important;
  display: block !important;
  height: 558px !important;
  margin: 15px auto 0 !important;
  width: 100% !important;
}

.callButton {
    display: block; 
    margin: 0 auto; 
    margin-top:50px;
    display:block;
    background-color:#f5f5f5;
    border:1px solid #dedede;
    border-top:1px solid #eee;
    border-left:1px solid #eee;
    font-family:"Lucida Grande", Tahoma, Arial, Verdana, sans-serif;
    font-size:12px;
    line-height:130%;
    text-decoration:none;
    font-weight:bold;
    color:#565656;
    cursor:pointer;
    padding:5px 10px 6px 7px;
    width: 500px;
}

.CKOContentLoading {
    background:#fff url('<%=request.getContextPath()%>/images/spinner.gif') no-repeat 50% 50%;
}
</style>

<script>
var curCKOProviderID = "";
$(window).on('beforeunload', function(){
	$.blockUI({ message: $('#domMessage') }); 
});


var windowProxy1;

function load(url, onContentLoaded) {
 
	//$($('#content')).append('<iframe id="CKOContent" name="CKOContent" src=""></iframe>');
	$('iframe#CKOContent').attr('src', url);
 
	// On iframe content load
	$('iframe#CKOContent').load(function() {
		onContentLoaded(this);
	});
}

function updateIframeSrc()
{
 var isBackButtonClicked = $('#isBackButtonClicked').val();

 if(isBackButtonClicked=="false"){
 var orderId = document.getElementById('orderId').value;
 var customerId = document.getElementById('customerId').value;

 //When multiple products are selected for CKO
 var count = parseInt(document.getElementById('lineItemCount').value, 10);

 var lineItemIdArr = document.getElementById('lineItemId').value.split(',');
 var providerIdArr = document.getElementById('providerId').value.split(',');
 var urlArr = document.getElementById('url').value.split(',');
 var keyArr = document.getElementById('key').value.split(',');
 var providerNameArr = document.getElementById('providerNames').value.split(',');
 var guid = document.getElementById('guid').value;
	var agentId = $("div#userDetails span:eq(0)").text();
	if(agentId == undefined || agentId.trim() == ""){
		agentId = $("input#agentId").val();
	}

 var title = document.getElementById('title').value;
 if(title == 'CKO.title'){
  var providerName = providerNameArr[count];
  var productTitle = providerName + ' CKO';
  $("span#pageTitle").html(productTitle);
 }
 
 var lineitemId = lineItemIdArr[count];
	
 //Inserts the LineItemId which is in CKO 
 $('input#linItemInCKO').val(lineitemId);
 
 var providerId = providerIdArr[count];
 curCKOProviderID = providerId;
 var url = urlArr[count];
 var key = keyArr[count];
 
 document.getElementById('lineItemCount').value = count + 1;
 
 var parentUrl = window.location.href;
 
 var data = '{"CKO":{""agentId":'+agentId+',"guid":'+guid+',customerId":'+customerId+',"orderId":'+orderId+',"parentUrl":"'+parentUrl+'","status":"CKOReady","lineItems":{"long":['+lineitemId+']},"params":{"string":["certificate='+key+'","page_id=101","provider_id='+providerId+'"]}}}';
 data = url+"?CKOInput=" + data;

 load(data, function() {
  $('#CKOContent').removeClass('CKOContentLoading');
 });

 $('#isBackButtonClicked').val("true");
 }else if(isBackButtonClicked=="true"){
  try{
   var title = document.getElementById('title').value;
   if(title == 'CKO.title'){
    var count1 = parseInt(document.getElementById('lineItemCount').value, 10);
    var providerNameArr1 = document.getElementById('providerNames').value.split(',');
     var providerName = providerNameArr1[count1-1];
    var productTitle = providerName + ' CKO';
    $("span#pageTitle").html(productTitle);
   }
   }catch(e){
    alert(e);
   }
 }
 windowProxy1 = new Porthole.WindowProxy(url, 'CKOContent');
 windowProxy1.addEventListener(feedbackFunc);
 
}

window.history.forward(); 
function noBack() {
	window.history.forward(); 
}

function confirmCancelCKO(){
	try{
		$("#cancelCKO-dialog-confirm").dialog( {
			resizable : false,
			title : "Warning",
			/* height : 197, */
			width : 477,
			modal : true,
			zIndex : 99999
		});
	}catch (err) {
		alert(err);
	}
}

function cancelCKO()
{
	savePageHtml(false, "");
	$("#cancelCKO-dialog-confirm").dialog('close');
	$("#CKOForm input[id='_eventId']").val("cancelCKOEvent");
	$("form#CKOForm").submit();
}

function hidePopUp()
{
	$("#cancelCKO-dialog-confirm").dialog('close');
}

</script>
</head>
<body onload="updateTimer();updateIframeSrc();noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
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
									<span class="cell pageTitle" id="pageTitle"><fmt:message key="${title}"/></span>
									<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
									<span class="callTime" id="startTimeText"></span>
								</div>
							</header>
							<section id="content">
								<div class="contentwrapperos">
									<input type="hidden" value="${orderId}" id="orderId" name="orderId"  />
									<input type="hidden" value="${customerId}" id="customerId" name="customerId"  />
									<input type="hidden" value="${customerId}" id="customerIdVal" name="customerIdVal"  />
									<input type="hidden" value="${lineItemId}" id="lineItemId" name="lineItemId"  />
									<input type="hidden" value="${providerId}" id="providerId" name="providerId"  />
									<input type="hidden" value="${phoneId}" id="phoneId" name="phoneId"  />
								    <input type="hidden" value="${pauseAndResumeURL}" id="pauseAndResumeURL"/>
								    <input type="hidden" value="${isPAREnabled}" id="isPAREnabled"/>
									<input type="hidden" value="${providerNames}" id="providerNames" name="providerNames"  />
									<input type="hidden" value="<%=request.getSession().getAttribute("GUID")!=null?request.getSession().getAttribute("GUID"):"" %>" id="guid" name="guid" />														
									<input type="hidden" value="${title}" id="title" name="title"  />
									<input type="hidden" value="${url}" id="url" name="url"  />
									<input type="hidden" value="${key}" id="key" name="key"  />
									<input type="hidden" value="${utilityOffer}" id="utilityOffer" name="utilityOffer"  />
									<input type="hidden" value="false" id="isBackButtonClicked" name="isBackButtonClicked"  />
									<input type="hidden" value="0" id="lineItemCount"/>
									<input type="hidden" value="<%=request.getContextPath()%>" id="contextPath"/>
									<input type="hidden"  id="flowpath" name="flowpath" value="${flowExecutionUrl}"/>
								  	<iframe id="CKOContent" name="CKOContent" src="" class="CKOContentLoading">
								 		Content Goes Here
								 	</iframe>
							 	</div>
							          <form name="CKOForm" action="${flowExecutionUrl}" method="post">
							          		<input type="hidden" value="" id="linItemInCKO" name="linItemInCKO"  />
							          		<c:if test="${utilityOffer eq true}">
							          		    <input type="hidden" id="pageTitle" name="pageTitle" value="Utility Offer">
							          		</c:if>
							          		<c:if test="${isRecommendation eq true}">
							          		    <input type="hidden" id="pageTitle" name="pageTitle" value="Recommendations">
							          		</c:if>
							          		<c:if test="${utilityOffer eq false && isRecommendation eq false}">
							          		    <input type="hidden" id="pageTitle" name="pageTitle" value="CKO">
							          		</c:if>
							          		<input type="hidden" value="${utilityOffer}" id="isUtilityOffer" name="isUtilityOffer"  />
							          		<input type="hidden" value="${isRecommendation}" id="isRecommendation" name="isRecommendation"  />
							          		<input type="hidden" value="" id="CKOPageTitle" name="CKOPageTitle" value=""/>
							          		<c:if test="${!isRecommendation}"  >
							          			<c:if test="${utilityOffer eq true}">
							          		   	 	<input type="button" name="cancelCKO" value="Cancel" class="os_buttons" style="margin-left: 10px;" onClick="confirmCancelCKO();" title="Stop provisioning this product."/>
							          			</c:if>
							          			<c:if test="${utilityOffer eq false}">
							          		   	 	<input type="button" name="cancelCKO" value="Cancel" class="os_buttons" style="margin-left: 10px;" onClick="confirmCancelCKO();" title="Stop provisioning this product and return to Order Summary"/>
							          			</c:if>
							          		</c:if>
							          		<c:if test="${isRecommendation}"  >
							               		<input type="button" name="cancelCKO" value="Cancel" class="os_buttons" style="margin-left: 10px;" onClick="confirmCancelCKO();" title="Stop provisioning this product."/>
							          		</c:if>
							          		<input type="hidden" id="_eventId" name="_eventId" value="cancelCKOEvent">
							          		<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
											<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							          </form>
				 			</section>
				 			<div id="domMessage" style="display:none;"> 
		                        <img src="<%=request.getContextPath()%>/images/loading.gif" border="0"/>
    							<h2>Loading</h2> 
							</div>
			 		</section>
			 		
	<div id="cancelCKO-dialog-confirm" style="display:none;">
		<center>
			Are you sure you want to cancel this order?<br/>
			By cancelling this order you could lose information you've entered.
			<br/>
			<br/>
			<input type="button" id="confirmYes" name="confirmYes" onClick="cancelCKO();" value="Yes"/>
			<input type="button" id="confirmNo" name="confirmNo" onClick="hidePopUp();" value="No"/>
		</center>
	<br/>
	</div>
	 
</body>
</html>