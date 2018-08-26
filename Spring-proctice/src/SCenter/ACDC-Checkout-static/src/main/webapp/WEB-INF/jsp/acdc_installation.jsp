<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>Installation</title>
 <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
-->
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<link rel="stylesheet" href="<c:url value='/style/common_CKO.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/static_CKO.css'/>" />
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="<c:url value='/style/bootstrap-datepicker3.min.css'/>" />
  <script src="<c:url value='/js/bootstrap-datepicker.min.js'/>"></script>
  
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>
<script src="<c:url value='/script/features.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/style/acdc/acdc_installation.css'/>" />
<script src="<c:url value='/script/acdc_cko/acdc_installation.js'/>"></script>
<script src="<c:url value='/script/acdc_cko/CKO_constants.js'/>"></script>
<script src="<c:url value='/script/acdc_cko/acdc_cko_util.js'/>"></script>


  <script type="text/javascript">
  var enableArr = [];
      var installationJson = ${installationVO};
      var CKOPageTitle = "Installation";
      var dataFieldMatrixMap;
      if(!($.isEmptyObject(installationJson))){
	      if(dataLayer!=undefined && dataLayer.pageContent!= undefined)
	      dataLayer.pageContent.pageName= "Installation and Payment";
      	  dataFieldMatrixMap = installationJson.dataFieldMatrixMap;
      }
	  $(document).ready(function(){
			setCKOPageTitle(CKOPageTitle);
			setDynamicHeight();
			   $('.next_step').bind("keydown", function(event) {
				    if (event.keyCode === 13){
				    	 submitInstallationInfo(); 
				    }
				}); 
		});
	
	function gobacktopage(){
		   $("#installationsAndPayments").attr({
		         "action":"<%=request.getContextPath()%>/static/backButtonQualificationVO"
		     });
		   $('#installationsAndPayments').submit();
		}
	
	
</script>
<style type="text/css">

 .dispayPaymentInnerContent {
    margin: 5px auto;
    overflow: hidden;
}/*
.dispayRightCont {
    line-height: 2;
 	overflow: hidden; 
    clear: right;
} */

</style>
 </head>
 <body>
					<form id="installationsAndPayments" method="post" action="<%=request.getContextPath()%>/static/saveInstallation">
					<input id="iData" name="iData" value='${iData}' type="hidden" />
                        <div id="left_sec_content">
                            <div id="steps" class="row">
                                <div id="step1" class="past-step"><span class="step_id">1</span><span class="step_title">Product<br/>Customization</span></div>
                                <div id="step2" class="past-step"><span class="step_id">2</span><span class="step_title">Customer<br> Information</span></div>
                                <div id="step3" class="current-step"><span class="step_id">3</span><span class="step_title">Installation <br/>and Payment</span></div>
                                <div id="step4"><span class="step_id">4</span><span class="step_title">Review and<br/> Place Order </span></div>
                            </div>
                            <div id="customizations" class="row">
                                <div class="cust_title">Please complete Payment information</div>
                                <div class="next_step" class="next" tabindex="0"><span class="nextStep">Next Step</span></div>
                                <div class=" validCont">
                                    <strong>Please fill out required items. </strong>
                                    <div class="validationMsgCont"></div>
                                </div>
                            </div>
                            	<div class="paymentInformationCotent">
                            		<div class="home-services-options row installContent">
                            			<div class="home-services-head">Payment Information</div>
                            			<div class="home-services-body">
                            				<div class="paymentInformation">
                            				
                            				</div>
                            			</div>
                            		</div>
                            	</div>
								<div class="home-services-options row installContent installInfoContent">
									<div class="home-services-head">Installation Information</div>
									<div class="home-services-body">
										<div class="firstinstall">
											<label><span class="mandatoryIndicator">* </span>First Requested Install Date/Time</label>
											<div class="installField">
												<div class="input_container">
													<input type="text" class="form-field-160 dialogueTextInput" id="TWCWebFirstRequestedInstallDate" displaytype="dialogue" valuetarget="lineItem.schedulingInfo.desiredStartDate.date" name="TWCWebFirstRequestedInstallDate">
												</div>
												<div class="installTime firstRequest"></div>
											</div>
										</div>
										<div class="firstinstall"  style="clear: both;">
											<label><span class="mandatoryIndicator">* </span>Second Requested Install Date/Time</label>
											<div class="installField">
												<div class="input_container">
													<input type="text" class="form-field-160 dialogueTextInput" id="TWCWebSecondRequestedInstallDate" displaytype="dialogue" valuetarget="lineItem.schedulingInfo.desiredStartDate2.date" name="TWCWebSecondRequestedInstallDate">
												</div>
												<div class="installTime secondRequest"></div>
											</div>
										</div>
										
										<div class="disclosureContent"></div>
									</div>
								</div>
								<div id="bottom-footer" class="row">
                                	<div class="prev_step"  tabindex="0" ><a id="gobacktopage" onclick="gobacktopage();">Back</a></div>
	                                <div class="next_step" tabindex="0"><span id="next">Next Step</span>
	                                    <!--<input  type="sumbit" value="Next Step" >-->
	                                </div>
                            	</div>
                        </div>
	                    <input type="hidden" name="installationJSON" id="installationJSON" value="">
	                   <input id="lineitemNumber" name="lineitemNumber" value='${lineItemNumber}' type="hidden" />
	                   <input id="orderID" name="orderID" value='${orderID}' type="hidden" />
							<input id="product_name" name="product_name" value='${product_name}' type="hidden" />
							<input id="provider_name" name="provider_name" value='${provider_name}' type="hidden" />
					</form>
					<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>" />
 
</body>
</html>
