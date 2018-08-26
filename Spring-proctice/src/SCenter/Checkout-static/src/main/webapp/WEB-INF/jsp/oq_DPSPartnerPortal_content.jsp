<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_base.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_att_iframe.css'/>" />
<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/custom_form_elements.js'/>"></script>
<script src="<c:url value='/script/textValueMasking.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>

<style type="text/css">

.lt_pad
{
	padding-left:130px;
}

.pc_pdetails_cont .pc_steps {
  width: 95px !important;
}
.nofloat
{
	float:none!important;
}
	.fwd_btn
	{
		float: right;
		margin-right:50px;
	}
	.statusresult
	{
		display: none;
	}
	.statusresult ul li
	{
		list-style: none;
		padding:5px 0px;
	}
	.Instructional ul li
	{
		list-style: none;
		padding:5px 0px;
	}
	
.hidden_div
{
	display:none;
}
.iborder
{
	/* border:1px solid #000000; */
	/* margin-bottom:20px;
	padding-bottom:10px; */
}

#dominionCKOContent
{
	border:1px solid #00000;
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
.radioLabel{
	vertical-align: top;
	padding-left: 50px; 
}
#astrikLbl {
    display: block;
    overflow: hidden;
    width:95px;
}

#astrik {
    float: left;
    display: block;
    height: 12px;
}

.display_group_header{
	font-weight: bolder;
}

.disclosure_type_label{
	color:black;
	font-weight:bold;
}

.info_type_label{
	color:green;
	text-indent:50px;
}
.CKOContentLoading
{
    background:#fff url('<%=request.getContextPath()%>/images/spinner.gif') no-repeat 50% 50%;
}
.ui-dialog .ui-dialog-titlebar-close {
    display: none;
    height: 18px;
    margin: -10px 0 0;
    padding: 1px;
    position: absolute;
    right: 0.3em;
    top: 50%;
    width: 19px;
}
.ui-dialog-titlebar {
    background: none repeat-x scroll 50% 50% #6ca741;
}
.ui-dialog-title {
  text-align: center;
  width: 100%;
}
.ui-dialog .ui-dialog-content {
    background: none repeat scroll 0 0 #ffffff;
    border: 0 none;
    overflow: auto;
    padding: 0.5em 1em;
}


.pc_steps_errorblk{
 height: 34px;
 margin-top: 10px;
 padding: 3px 20px 3px 20px;
 overflow: hidden;
 background-color: #fff568;
 font-size: 14px;
 font-weight: bold;
 color: #ff0000;
 line-height: 17px;
}
.pc_steps_errorblk div{
 height: 34px;
 font-size: 14px;
 font-weight: bold;
 color: #ff0000;
 display: table-cell;
 vertical-align: middle;
}
.pc_steps_errorblk span{
 height: 32px;
}

.contentwrapperos {
	/*  height: 554px;
	width: 1010px;  */
}


.col-lg-1 {
	margin-top: -20px;
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

div#CKOContent {
	border: 0 none !important;
	display: block !important;
	height: 558px !important;
	margin: 15px auto 0 !important;
	width: 100% !important;
	overflow: scroll;
}

.callButton {
	display: block;
	margin: 0 auto;
	margin-top: 50px;
	display: block;
	background-color: #f5f5f5;
	border: 1px solid #dedede;
	border-top: 1px solid #eee;
	border-left: 1px solid #eee;
	font-family: "Lucida Grande", Tahoma, Arial, Verdana, sans-serif;
	font-size: 12px;
	line-height: 130%;
	text-decoration: none;
	font-weight: bold;
	color: #565656;
	cursor: pointer;
	padding: 5px 10px 6px 7px;
	width: 500px;
}

.customAlert {
	margin-right: 215px !important;
}
#dominion_line{
float: left;
}

#pc_fldset_data_containt{
color:grey;
float: left;
width: 290px;


}
#Inst_containt{
float: left;
margin-right: 5px;

}

.dominionCKOContentLoading {
	background: #fff url('<%=request.getContextPath()%>/image/loading.gif')
		no-repeat 50% 50%;
}


</style>

<script type="text/javascript">

var responseSuccessData = {};

var windowProxy;

$(document).ready(function(){
	

	$('input:radio[name="DominionSuccess"]').change(function(){
	    if($(this).val() == 'Y'){
	    	$("#status_NO").hide();
	    	$("#status_YES").show();
	    }
	    else
	    {
	    	$("#status_YES").hide();
	    	$("#status_NO").show();
	    }
	});
});

$('#dominionCKOContent').load(function(){
    //your code (will be called once iframe is done loading)
	$('#dominionCKOContent').removeClass('dominionCKOContentLoading');
});

function enrollCustomer() {
    $('#lblReference').text('');
    var frmEnrollId = '#dominionCKOForm', frm;
    var dominionurl = $('#DominionUrl').val();
    var productDescription = $('#productDescription').val();
    $('form#dominionCKOForm').attr("action",dominionurl);
    $('input#PartnerURL').val(window.location.href);
    var url = dominionurl;
    windowProxy = new Porthole.WindowProxy(url, 'dominionCKOContent');
    windowProxy.addEventListener(feedbackFuncParentIframe);
    setIFrameSize($(window).height() - 220, $(window).width() - 10);

    $('form#dominionCKOForm').submit();
}

$(window).load(function () {
    setIFrameSize($(window).height() - 220, $(window).width() - 10);
   	enrollCustomer();
});

function addAnEventListener(winObj, eventName, fnMsgReceiver) {
    if ('addEventListener' in winObj) {
        winObj.addEventListener(eventName, fnMsgReceiver, false);
    } else if ('attachEvent' in winObj) {//IE
        winObj.attachEvent('on' + eventName, fnMsgReceiver);
    }
}
 
function setIFrameSize(height, width) {
    var iframeId = '#dominionCKOContent', ifrm;
    ifrm = $(iframeId);
    $(ifrm).height(height);
    $(ifrm).width(width);
};


function feedbackFuncParentIframe(messageEvent){
	console.log("feedbackFuncParentIframe messageEvent: "+JSON.stringify(messageEvent));
	if(messageEvent != undefined && messageEvent != ""){
		responseSuccessData = messageEvent;
		try{
		if(messageEvent.data["status"] == "Success"){
			hideCancelButton();
			//$("#BOOL_Y_DominionSuccess").attr('disabled','');
			var dataValue;
			if(messageEvent.data["data"] != undefined){
				dataValue = messageEvent.data["data"];
				var amt = dataValue.OrderProcessedAmt;
			  $("#status_YES ul").append('<li><b>Product ID:</b> '+dataValue.WebOrderHeaderId+'</li><li><b>Order Processed Amount:</b> $'+amt.toFixed(2)+'</li><li><b>Product Offer Id:</b> '+dataValue.ProductOfferId+'<b> - ${productDescription}</b></li><li><b>Order Status:</b> '+messageEvent.data["status"]+'</li>'
									); 
			}			
		}
		if(messageEvent.data["status"] == "Failure"){
			var message = messageEvent.data["message"];
			dominionErrorMsg = message;
			var path =$("input#contextPath").val();
			$("#dominionErrorMsg").text(message);
			window.location.href = path+"/static/showErrorPage?message="+encodeURIComponent(message);
		} 
		}catch(e){
			console.log(e);
		}
	}
}
function submitOrder(){
	if(responseSuccessData != undefined && responseSuccessData != ""){
		var messageEvent = responseSuccessData;
		var data = {};
		try{
		if(messageEvent.data["status"] == "Success"){
			var dataValue;
			if(messageEvent.data["data"] != undefined){
				dataValue = messageEvent.data["data"];
				
				$("input#WebOrderHeaderId").val(dataValue.WebOrderHeaderId);
				$("input#OrderProcessedAmt").val(dataValue.OrderProcessedAmt);
				$("input#ProductOfferId").val(dataValue.ProductOfferId);
				$("input#DominionStatus").val("Success");
				
				var path =$("input#contextPath").val();
				
				var CKOInputVal = document.getElementById('iData').value;
			}
				
			}else{	
				$("form#frmMain").submit();
			}
		}catch(e){
			console.log(e);
		}
	}
	$("form#frmMain").submit();
	console.log("responseSuccessData: "+responseSuccessData);
}
function onLoadFunctions(){
	
	symFeedback();
}

</script>
	
</head>
<body onload="onLoadFunctions();">
<div>
<input id="iData" name="iData" value='${iData}' type="hidden" />
<input id="DominionUrl" name="dominionUrl" value='${dominionUrl}' type="hidden" />
<input id="productDescription" name="productDescription" value='${productDescription}' type="hidden" />
<input id="contextPath" name="contextPath" value='<%=request.getContextPath()%>' type="hidden" />
<input id="lineItemExternalId" name="lineItemExternalId" value='${lineItemExternalId}' type="hidden" />

<form action="<%=request.getContextPath()%>/static/updateDialogue"  method="post" id="frmMain" name="frmMain"	onsubmit="symFeedbackSubmit()" autocomplete=off>
	<input type="hidden" id="formdata" name="formdata" />
	<input type="hidden" id="WebOrderHeaderId" name="WebOrderHeaderId" value=""/> 
	<input type="hidden" id="OrderProcessedAmt" name="OrderProcessedAmt" value=""/> 
	<input type="hidden" id="selectedValues" name="selectedValues" /> 
	<input type="hidden" id="extIDSelectedValues" name="extIDSelectedValues" /> 
	<input type="hidden" id="ProductOfferId" name="ProductOfferId" value=""/>
	<input type="hidden" id="DominionStatus" name="DominionStatus" value="NAN"/>
	<input type="hidden" id="isDominionPageExecuted" name="isDominionPageExecuted" value="true"/>
	<input type="hidden" id="from" name="from" value="oqDemoContent"/>
	<input type="hidden" id="selectedFeatureJSONValue" name="selectedFeatureJSONValue"/>
	<input type="hidden" id="sessionPage" name="sessionPage" value="oqDemo"/>
	<input type="hidden" id="isUtilityOffer" name="isUtilityOffer" value="${isUtilityOffer}" />
	<input type="hidden" id="validmaxdate" value="${hiddenYear}" placeholder="mm/dd/yyyy" title="mm/dd/yyyy" />
</form>


	</div>
<form id="dominionCKOForm" name="dominionCKOForm" action="${flowExecutionUrl}"
	target="dominionCKOContent" method="post">
	<input name="UDCCode" id="UDCCode" value="${UDCCode}" type="hidden" class="form-control"/>
	<input name="INum" id="INum" value="${inumVal}" type="hidden" class="form-control" />
	<input name="AgentId" id="AgentId" value="${agentId}" type="hidden" class="form-control" />
	<input name="ProductOfferId" id="ProductOfferId" value="${ProductOfferId}" type="hidden" class="form-control" />
	<input name="CustomerProfile.FirstName" id="FirstName" value="${FirstName}" type="hidden" class="form-control " />
	<input name="CustomerProfile.LastName" id="LastName" value="${LastName}" type="hidden" class="form-control" />
	<input name="CustomerProfile.Phone" id="Phone" value="${PhoneNum}" type="hidden" class="form-control" />
	<input name="CustomerProfile.EmailAddress" id="EmailAddress" value="${Email}" type="hidden" class="form-control" />
	<input name="CustomerProfile.ServiceAddress.AddressLine1" id="ServiceAddress.AddressLine1" value="${AddressLine1}" type="hidden" class="form-control" />
	<input name="CustomerProfile.ServiceAddress.AddressLine2" id="ServiceAddress.AddressLine2" value="" type="hidden" class="form-control" />
	<input name="CustomerProfile.ServiceAddress.City" id="ServiceAddress.City" value="${City}" type="hidden" class="form-control" />
	<input name="CustomerProfile.ServiceAddress.State" id="ServiceAddress.State" value="${State}" type="hidden" class="form-control" />
	<input name="CustomerProfile.ServiceAddress.ZipCode" id="ServiceAddress.ZipCode" value="${ZipCode}" type="hidden" class="form-control" />
	<input name="lineItemId" id="lineItemId" value="${lineItemExternalId}" type="hidden" class="form-control" />
	<input name="PartnerURL" id="PartnerURL" value="" type="hidden" class="form-control" />
	
	<div class="pc_pdetails_cont">
		<!-- Left Block -->
		<div class="pc_steps">
			<c:choose>
				<c:when  test="${showBackButton != false && !ishybris && !frontierAsIs}">
					<div id="pc_steps_two" class="pc_steps_item_complete pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>1</span></div></div>
						<div class="pc_steps_item_sttext">Customer Qualification</div>
					</div>
					<div id="pc_steps_three" class="pc_steps_item_progress pc_steps_item_margin">
      					<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>2</span></div></div>
     				    <div class="pc_steps_item_sttext">Complete Order</div>
    				</div>
				</c:when>
				<c:when  test="${showBackButton != false && (ishybris || frontierAsIs)}">
					<div id="pc_steps_two" class="pc_steps_item_progress pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>1</span></div></div>
						<div class="pc_steps_item_sttext">Customer Qualification</div>
					</div>				
				</c:when>
				<c:otherwise>
					<div id="pc_steps_two" class="pc_steps_item_progress pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>2</span></div></div>
						<div class="pc_steps_item_sttext">Complete Order</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="pc_frameblk"> 
			<div class="pc_frameblk_title">
			<span id='unselDescription' style="display:none">
					<img id="astrik" src='<%=request.getContextPath()%>/image/red_asterisk.png' style="height:9px !important; width:9px !important;"> </img>
					<label style="color: red; font-size: 12px;" id="astrikLbl"> Required Fields</label>
			</span>
			<span id='equipUnsel' style="display:none">
					<img id="astrik" src='<%=request.getContextPath()%>/image/red_asterisk.png' style="height:9px !important; width:9px !important;"> </img>
					<label style="color: red; font-size: 12px;" id="astrikLbl"> Select at least (1) # Receivers</label>
			</span><!--
				Please select from the options below:
			--></div>
			<div class="pc_frameblk_cont" style="border: 1px solid #888;background-color: #fff;">
			
			<!-- <legend>
		        <div>Complete Order</div>
		    </legend> -->
			<div class="ifrmtitle" style="padding-left: 20px;padding-top: 16px;">Domain Portal</div>
			<div class="iborder">
				<iframe id="dominionCKOContent" name="dominionCKOContent" class="dominionCKOContentLoading"
					> Content Goes Here </iframe>
					</div>
			</div>
			
		</div>
	</div>
	
	
	
	
	
	
	<div class="lt_pad">
	
	<div id="Instruction" class="Instructional">
       <div class="label_desc" id="Dominion">
  		<div id = "Inst_containt"> <img style="vertical-align:middle" src="<%=request.getContextPath()%>/image/CKO_info_icon.png"></div>
          <div style="padding-right: 40px;"> <label class="info_type_label nofloat">Please do not select an option from below until the order has either been placed or cancelled in the window above to ensure the accurate status of the order has been received.</label></div>
       </div>
   </div>
	
		<div class="pc_fldset_data_item" id="dominionOrderSuccess">
     <div id = "pc_fldset_data_containt" class="label">
         <label>Was the order successfully placed?</label>
     </div>
     <div class="value" id = "dominion_line">
         <span style="display:inline-block;">
             <input id="BOOL_N_DominionSuccess" class="styled radioLabel" value="N" name="DominionSuccess" type="radio">
             <span>No</span>
             <input id="BOOL_Y_DominionSuccess" class="styled radioLabel" value="Y" name="DominionSuccess" type="radio">
             <span>Yes</span>
         </span>
     </div>
     <img style="display:none" id="ominionSuccess_required" class="requiredField error" src="<%=request.getContextPath()%>/image/red_asterisk.png" alt="<span class='requiredField'><font color='red'>*</font></span>">
 </div>
 

 
<!--  <span>Order Details:</span> -->
 
<div id="status_YES" class="statusresult">
	<b>Order Details</b>
	<ul></ul>
	
	<div >
	<input id="moveForward" class="fwd_btn" type="button" value="Forward >" onClick = "submitOrder()"></input>
	</div>
	
</div>
<div id="status_NO" class="statusresult">
       <div class="label_desc" id="Dominion">
           <img style="vertical-align:middle" src="<%=request.getContextPath()%>/image/CKO_info_icon.png">
           <label class="info_type_label nofloat">The order was not placed or has been cancelled. Please click the Cancel button to proceed.</label>
       </div>
</div>
</div>

	</form>

</body>
</html>