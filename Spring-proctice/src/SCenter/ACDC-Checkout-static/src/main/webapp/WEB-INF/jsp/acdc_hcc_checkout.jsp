<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
 <head>
  <meta charset="UTF-8">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>Account Holder Qualifications</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="<c:url value='/style/common_CKO.css'/>" />
  <link rel="stylesheet" href="<c:url value='/style/static_CKO.css'/>" />
	<!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
-->
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
   <link rel="stylesheet" href="<c:url value='/style/bootstrap-datepicker3.min.css'/>" />
  <script src="<c:url value='/js/bootstrap-datepicker.min.js'/>"></script>
 <script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>
<script src="<c:url value='/script/features.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/style/qualification.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/acdc/acdc_qualification.css'/>" />
<script src="<c:url value='/script/acdc_cko/CKO_constants.js'/>"></script>
<script src="<c:url value='/script/acdc_cko/acdc_qualification.js'/>"></script>
<script src="<c:url value='/script/acdc_cko/acdc_cko_util.js'/>"></script>


<style>
.phoneContactOptIn, .marketingOptIn{
    float: right;
    padding: 3px 6px;
}
.marketOptinContent{
	displaly: none;
}
.phoneContactOptInContent{
	displaly: none;
}
.dataFieldCont.cccheckbox {
    float: right !important;
    max-width: 94% !important;
}
.alert.alert-info {
    margin: 1px;
}
</style>
	<script type="text/javascript">
	var applianceFlag= true;
	console.log("applianceFlag="+applianceFlag);
	var saddress="${serviceAddress}";
	console.log("saddress::::"+saddress);
     var enableArr = [];
     var qualificationVO = ${qualificationVO};
     var showCustomerInformation = ${customerInformation};
     console.log("showCustomerInformation :: "+ showCustomerInformation);
     var dialogueJson = qualificationVO.dialogueTypeList;
     var dataFieldMatrixMap = qualificationVO.dataFieldMatrixMap;
     console.log("$.Constants.STATES  ::: "+$.Constants.STATES)
     var states = $.Constants.STATES;
     console.log("states   ::: "+states)
	$(document).ready(function() {
		applianceFlagValidations(applianceFlag);
		var CKOPageTitle = "Account Holder Qualifications";
		if(dataLayer!=undefined && dataLayer.pageContent!= undefined)
		dataLayer.pageContent.pageName=CKOPageTitle;
		setCKOPageTitle(CKOPageTitle);
		var isShowSmsOptIn = qualificationVO.isShowSmsOptIn
			var isShowOffersOptIn = qualificationVO.isShowOffersOptIn
			if(isShowSmsOptIn){
				$(".marketOptinContent").remove();
			}
		if(isShowOffersOptIn){
			$(".phoneContactOptInContent").remove();
		}
		$("#gobacktopage").on("click", function(){
			   $("#formData").attr({
				   "action":"<%=request.getContextPath()%>/static/backButtonProductVO"
		        });
			   $('#formData').submit();
		   });
		
		  $('.next_step').bind("keydown", function(event) {
			    if (event.keyCode === 13){
			    	 submitInstallationInfo(); 
			    }
			 });
		  
		  if($(".phoneContactOptInContent .phoneContactOptIn").text() == ''){
			  $(".phoneContactOptInContent").remove();
		  }
		  
		  if($(".marketOptinContent .marketingOptIn").text() == ''){
			  $(".marketOptinContent").remove();
		  }
		  $('.dataFieldContSpan').each(function() {
				if($(this).text() == saddress){
					$(this).prev().remove();
					$(this).css('font-weight','bold');
				}
			});
		  
	});
</script>

 </head>
 <body>
					<div id="left_sec_content">
						<div id="error_msg" class="row" style="display: none;">
                        	<div class="validationMsgCont"><strong>Please fill out required items. </strong></div>
                        </div>
						<form id="formData" method="post">
					<!-- 	<div class="home-services-options row">
							<div class="home-services-head">Account and Customer Information</div>
							<div class="home-services-body"></div>
						</div> -->
						
						<div class="dialogueContent"></div> 
						
				        <input type="hidden" name="qualificationSerializeJSON" id="qualificationSerializeJSON" value="">
				        <input id="iData" name="iData" value='${iData}' type="hidden" />
				        <input id="lineitemNumber" name="lineitemNumber" value='${lineitemNumber}' type="hidden" />
				        <input id="orderID" name="orderID" value='${orderID}' type="hidden" />
                         <input id="product_name" name="product_name" value='${product_name}' type="hidden" />
                          <input id="provider_name" name="provider_name" value='${provider_name}' type="hidden" />
                       </form>
						<div id="bottom-footer" class="row">
							<!-- <div class="prev_step"><a href="#" id="gobacktopage">Back</a></div> -->
							<div class="next_step" tabindex="0"><span id="nextStep">Next Step</span></div>
						</div>
					</div>
            <input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>" />
 </body>
</html>
