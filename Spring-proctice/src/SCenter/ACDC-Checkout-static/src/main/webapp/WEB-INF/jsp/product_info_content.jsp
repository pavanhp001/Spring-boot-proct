<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<!-- link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_base.css'/>" /> 
<link rel="stylesheet" href="<c:url value='/style/main.css'/>" />-->
<link rel="stylesheet" href="<c:url value='/style/CKO_att_iframe.css'/>" />

<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/custom_form_elements.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>
<script src="<c:url value='/script/features.js'/>"></script>
     
<style type="text/css">
 .pc_pdetails_cont .pc_steps {
  width: 95px !important;
}

.promo_desc {
    padding: 7px;
    text-align: left;
    vertical-align: top;
    background-color: #E0F5C3;
    font-size: 14px;
    color: #000000;
    word-wrap: break-word;
    width: 150px;
}

</style>	
<script type="text/javascript">

/*
 * building selected promotions and unselected promotions these values are used to 
 * update the line item status(if the promotion is deselected) or add new line item 
 * if a new promotion is selected
 */

var selectedPromotions = "";
var unSelectedPromotions = "";
function buildSelectedPromotion(){
	$('input[class="promotionClass"]').each(function(){
		if($(this).is(":checked") == false){
			var promoID = $(this).attr("id");
			unSelectedPromotions  += promoID + ",";
		}
	});
	$("#selectedPromotions").val(selectedPromotions);
	$("#unSelectedPromotions").val(unSelectedPromotions);
}


$(document).ready(function(){
	$('.buttonClass').css('cursor', 'pointer');
	$(document).bind("dragstart", function() {
	     return false;
	});
	Custom.init();
});


Array.max = function( array ){
    return Math.max.apply( Math, array );
};


(function (window) {

	  // Stores past URLs that failed to load. Used for a quick lookup
	  // and because `onerror` is not triggered in some browsers
	  // if the response is cached.
	  var errors = {};

	  // Check the existence of an image file at `url` by creating a
	  // temporary Image element. The `success` callback is called
	  // if the image loads correctly or the image is already complete.
	  // The `failure` callback is called if the image fails to load
	  // or has failed to load in the past.
	  window.checkImage = function (url, success, failure) {
	    var img = new Image(),    // the 
	        loaded = false,
	        errored = false;

	    // Run only once, when `loaded` is false. If `success` is a
	    // function, it is called with `img` as the context.
	    img.onload = function () {
	      if (loaded) {
	        return;
	      }

	      loaded = true;

	      if (success && success.call) {
	        success.call(img);
	      }
	    };

	    // Run only once, when `errored` is false. If `failure` is a
	    // function, it is called with `img` as the context.
	    img.onerror = function () {
	      if (errored) {
	        return;
	      }

	      errors[url] = errored = true;

	      if (failure && failure.call) {
	        failure.call(img);
	      }
	    };

	    // If `url` is in the `errors` object, trigger the `onerror`
	    // callback.
	    if (errors[url]) {
	      img.onerror.call(img);
	      return;
	    }
	    
	    // Set the img src to trigger loading
	    img.src = url;

	    // If the image is already complete (i.e. cached), trigger the
	    // `onload` callback.
	    if (img.complete) {
	      img.onload.call(img);
	    }
	  };

	})(this);
function success() {
  $("#provider").attr('src','<%=request.getContextPath()%>/image/${parentExternalID}.jpg');
}

function failure() {
	checkImage("<%=request.getContextPath()%>/image/${providerExternalID}.jpg", success1, failure1);
}
function success1() {
	  $("#provider").attr('src','<%=request.getContextPath()%>/image/${providerExternalID}.jpg');
	}

	function failure1() {
	}
function checkProdImage(){
	checkImage("<%=request.getContextPath()%>/image/${parentExternalID}.jpg", success, failure);
}

function defaultFlyoutValues(){
	var startingPoint =  $('#startingPoint').val();
	if(startingPoint == 'oqDemo'){
		var selFeatureHiddenJSONVal = $('#selectedFeaturesJSONHiddenValue').val();
		var jsonArray = $.parseJSON(selFeatureHiddenJSONVal);
		if(!(typeof selFeatureHiddenJSONVal == "undefined") && selFeatureHiddenJSONVal != null){
			calculateMonthlyPriceAndInstallPrice(jsonArray);
			displayPriceDetails(jsonArray);
		}

		$('.promotionClass').each(function(){
			if($(this).is(':checked')){
				var id = $(this).attr("id");
				isSameTypePromoSelected(id);
			}
		});
	}
	else{
		collectAllSelectedFields();
	}
}

function validateForwardButton()
{
	try
	{
		if(isValidFormSubmit())
		{
			symFeedbackSubmit();
			return true;
		}
		return false;
	}
	catch(err)
	{
		return false;
	}
}


function isValidFormSubmit()
{
	var promoDescript = "";
	var dependencyPromoDescript = "";
	var isValidFormSubmit = true;
	
	$('.promotionClass:checked').each(function()
	{
		var id = $(this).attr("id");
		var promoJSON = JSON.parse($("#"+id+"_hiddenPromo").val());
		var promoIncludesExtIds = promoJSON.promoIncludesExtId;
		if(promoIncludesExtIds!=undefined && promoIncludesExtIds != null && promoIncludesExtIds.trim().length > 0 )
		{
			promoDescript = promoJSON.shortDescription;
			var promoIncludes = promoIncludesExtIds.split(',');
			for(var i = 0; i < promoIncludes.length; i++)
			{
				if( !($("#"+promoIncludes[i]).is(':checked')) )
				{
					isValidFormSubmit = false;
					var dependencyObj = JSON.parse($("#"+promoIncludes[i]+"_hiddenPromo").val());
					dependencyPromoDescript = dependencyObj.shortDescription;
				}
			}
		}

		if(!isValidFormSubmit)
		{
			symShowAlert("Please Select "+dependencyPromoDescript+" promotion OR Unselect "+promoDescript+" promotion.",true)
		}
	
	});
	return isValidFormSubmit;
}


function onLoadFunctions(){
	defaultFlyoutValues(); 
	checkProdImage();
	getPreviouslySelectedFeatures();
	symFeedback();	
}

</script>

</head>
<body onload="onLoadFunctions();">

<input id="iData" name="iData" value='${iData}' type="hidden" />

  <form  action="<%=request.getContextPath()%>/static/save" method="post" onsubmit="return validateForwardButton()" autocomplete=off>
  
  <input id="selectedPromotions" name="selectedPromotions" type="hidden" />
  <input id="unSelectedPromotions" name="unSelectedPromotions" type="hidden" />
  <input id="finalMonthlyPrice" name="finalMonthlyPrice" type="hidden" />
  
  <input type="hidden" id="lineItemMonthlyPrice" name="lineItemMonthlyPrice" value="${baseRecPrice}" />
  <input type="hidden" id="lineItemInstallationPrice" name="lineItemInstallationPrice" value="${baseNonRecPrice}" />
  <input type="hidden" id="startingPoint" name="startingPoint" value="${startingPoint}" />
  <input type="hidden" id="productMonthlyPrice" name="productMonthlyPrice" value="${productMonthlyPrice}" />
  <input type="hidden" id="productInstallationPrice" name="productInstallationPrice" value="${productInstallationPrice}" />
  <input type="hidden" id="previouslyGivenDataId" name="previouslyGivenDataId" value='${previouslyGivenDataId}'/> 
  <input type="hidden" id="selectedFeatureJSONValue" name="selectedFeatureJSONValue"/>
  <input type="hidden" id="oqSelectedFeaturesHIDDENValue" name="oqSelectedFeaturesHIDDENValue" value='${oqSelectedFeaturesHIDDENValue}'/>
  
  <div class="pc_pdetails">
  	
  	<!-- display the image of the product customer selected -->
	<div class="pc_pdetails_logo">
    	<c:if test="${not empty providerExternalID}">
    	 	<img id="provider" src="" 
    		style="float: left;max-height:60px;max-width:107px;"/>
    	</c:if>
  	</div>
  	
  	<!-- show product name, monthly price, installation price of the product -->
	<div class="pc_pdetails_info">
		<div class="label">Product Name:</div><div class="value"><c:out escapeXml="false" value="${productName}" /></div><br/>
		<div class="label">Monthly Total:</div>
		<div class="value"  style="width:48px;">
			<span id="monthlyCost">
				<fmt:formatNumber type="currency" value="${baseRecPrice}" />
			</span>
			<input type="hidden" id="monthlyCostAmtFld" name="monthlyCostAmtFld" value="${productMonthlyPrice}" />
		</div>
		<span>&nbsp;&nbsp;
			<jsp:include page="MonthlyTotalWidget.jsp"></jsp:include>
		</span>
		<br/>
		
		<div class="label">Installation Total:</div>
		<div class="value"  style="width:48px;">
			<span id="oneTimePrice">
				<fmt:formatNumber type="currency" value="${baseNonRecPrice}" />
			</span>
			<input type="hidden" id="oneTimePriceFld" name="oneTimePriceFld" value="${productInstallationPrice}" />
		</div>
		<span>&nbsp;&nbsp;
			<jsp:include page="InstallTotalWidget.jsp"></jsp:include>
		</span>
        <!--<span style="float: right; margin-top: -20px;" class="label">Account Holder:</span>
          <span class="value" style="float: right; margin-right: 61px;">${customerName}</span>-->
		<br/>
		
	</div>
		
  </div>
  <div class="pc_pdetails_cont">
	  	<!-- Display the steps that are present in the CKO process -->
	  	
		<div class="pc_steps">
			<c:choose>
				<c:when  test="${showBackButton != false && !ishybris && !frontierAsIs}">
					<div id="pc_steps_one" class="pc_steps_item_progress">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>1</span></div></div>
						<div class="pc_steps_item_sttext">Product Customization</div>
					</div>
					<div id="pc_steps_two" class="pc_steps_item_pending pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>2</span></div></div>
						<div class="pc_steps_item_sttext">Customer Qualification</div>
					</div>
			 		<div id="pc_steps_three" class="pc_steps_item_pending pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>3</span></div></div>
						<div class="pc_steps_item_sttext">Authorization</div>
			</div>
				</c:when>
				<c:otherwise>
					<div id="pc_steps_one" class="pc_steps_item_progress">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>1</span></div></div>
						<div class="pc_steps_item_sttext">Product Customization</div>
					</div>
					<div id="pc_steps_two" class="pc_steps_item_pending pc_steps_item_margin">
						<div class="pc_steps_item_stepdet"><div class="pc_steps_item_text">Step </div><div class="countbg"><span>2</span></div></div>
						<div class="pc_steps_item_sttext">Customer Qualification</div>
					</div>			
				</c:otherwise>
			</c:choose>
		</div>
		<!-- End Left Block -->
		
		<!-- Display promotions and features, featuregroups that are present for the product -->
		<!-- Right Block -->
		<div class="pc_frameblk">
			<div class="pc_frameblk_cont_big">
			
				<!-- Dislay Promotions -->
				<div class="pdet_promos">
					<div class="pdet_promos_tab">Promotions</div>
					<div class="pdet_promos_content">
					<div class="pdet_promos_content_cont">	
						<c:out escapeXml="false" value="${promotions}"/>
					</div>
					</div>
				</div>
				
				<!-- Display features and featuregroups -->
				<div class="pdet_promos_features">
					<div class="pdet_tabs">
						<div class="pdet_features_tab" id="featuresTab" onclick="featuresTab();">Features</div>
					</div>
					<div class="pdet_features_content" id="pdet_features_content">
						<c:out escapeXml="false" value="${viewObjMarketHighlt}"/>
					</div>
				</div>
				
				<!-- End pdet_promos_features -->
			</div>
			<div class="pc_frameblk_right_btns">
				<input type="submit" id="next" value="Forward >" class="buttonClass" onclick="buildSelectedPromotion();"/>
			</div>
		</div>
	  </div>
	  <input type="hidden" name="nav_steps_one" class="nav_steps" value="stepOne"/>
	</form>
</body>
</html>