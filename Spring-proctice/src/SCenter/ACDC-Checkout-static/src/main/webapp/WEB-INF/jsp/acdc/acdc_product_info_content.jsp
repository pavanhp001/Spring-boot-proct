<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<!-- link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_base.css'/>" /> 
<link rel="stylesheet" href="<c:url value='/style/main.css'/>" />-->
 <script>
	var jq180 = jQuery.noConflict();
   </script>
   <script src="https://code.jquery.com/jquery-1.10.2.js"></script>

<script src="<c:url value='/script/custom_form_elements.js'/>"></script>


 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<%-- <script src="<c:url value='/acdc/scripts/navigation.js'/>"></script> --%>
     
<style type="text/css">
/*  .pc_pdetails_cont .pc_steps {
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
 */
 .tblPromotions.table.table-striped.table-bordered.table-hover thead tr th {
    background: rgba(0, 0, 0, 0) linear-gradient(to bottom, #f8ffe8 3%, #e3f5ab 26%, #b7df2d 100%) repeat scroll 0 0;
}
 .AL-green {
	margin-left:auto;
	margin-right:auto;
    -max-width: 60%;
    -background: #F8F8F8;
    padding: 30px 30px 20px 30px;
    font: 12px Arial, Helvetica, sans-serif;
    color: #666;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
}
.AL-green .button {
    -background: rgba(0, 0, 0, 0) linear-gradient(to bottom, #f8ffe8 2%, #e3f5ab 1%, #b7df2d 100%) repeat scroll 0 0;
    border: medium none;
    border-radius: 5px;
    -color: #999;
    font-size: 15px;
    font-weight: bold;
    padding: 10px 25px;
}
.pc_frameblk_right_btns{
margin-top: 10px;
}
#Promotions{
   margin: 9px 0 1px;
    padding: 0 5px 21px 13px;
}
.pc_frameblk_cont_big{
    border: 9px solid #eee;
    border-radius: 4px;
}
</style>	
<script type="text/javascript">

/*
 * building selected promotions and unselected promotions these values are used to 
 * update the line item status(if the promotion is deselected) or add new line item 
 * if a new promotion is selected
 */
var channelCss = '${channelCss}';
var selectedPromotions = "";
var unSelectedPromotions = "";
function buildSelectedPromotion(){
	alert("buildSelectedPromotion calling");
	try{
	$('input[class="promotionClass"]').each(function(){
		if($(this).is(":checked") == false){
			var promoID = $(this).attr("id");
			unSelectedPromotions  += promoID + ",";
		}
	});
	$("#selectedPromotions").val(selectedPromotions);
	$("#unSelectedPromotions").val(unSelectedPromotions);
	}catch(e){
 alert("buildSelectedPromotion"+e);
		};
}


$(document).ready(function(){
	$('.buttonClass').css('cursor', 'pointer');
	$(document).bind("dragstart", function() {
	     return false;
	});
	Custom.init();
	$("#tblPromotions thead tr th").removeAttr("style");
	$("#tblPromotions tbody tr tr").removeAttr("style");
	$("#Promotions").show();
	var isEmptyPromotions = $(".pdet_promos_content_cont fieldset").text().trim();
	if(isEmptyPromotions == "" || isEmptyPromotions == undefined){
		$(".pdet_promos_content_cont").text("No promotions available");
	}
	$(".pdet_promos_content_cont table").addClass("table table-striped table-bordered table-hover");
	$("#tblPromotions").removeAttr("style");
	$("#tblPromotions tbody tr td").removeAttr("style");
	$("#pdet_features_content").addClass("table-responsive");
	$("#pdet_features_content table").addClass("table table-striped table-bordered table-hover");
	$("#promotionsLi").click(function () {
		$("#Features").hide();
		$("#Promotions").show();
		symFeedback();
			
		});
	$("#featuresLi").click(function () {
		$("#Promotions").hide();
		$("#Features").show();
		symFeedback();
			
		});


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
	
}

function validateForwardButton()
{
	try
	{
		if(true)
		{
			//symFeedbackSubmit();
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
	collectAllSelectedFeaturesForDigital();
	symFeedback();	
	
}

</script>

</head>
<body onload="onLoadFunctions();">

<input id="iData" name="iData" value='${iData}' type="hidden" />

  <form  action="<%=request.getContextPath()%>/static/save" method="post" onsubmit="return validateForwardButton()" autocomplete=off>
  <input type="hidden" id="CKOPageName" name="CKOPageName" value='product_info_content'/>
  <input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>" />
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
  <div class="pc_pdetails row ">
  	
  	<!-- display the image of the product customer selected -->
	<div class="pc_pdetails_logo">
    	<c:if test="${not empty providerExternalID}">
    	 	<img id="provider" src="" 
    		style="float: left;max-height:60px;max-width:107px;"/>
    	</c:if>
    	<div class="label">Product Name:</div><div class="value"><c:out escapeXml="false" value="${productName}" /></div><br/>
  	</div>
  </div>
  <div class=" AL-green row" >
	  	<!-- Display the steps that are present in the CKO process -->
	  	<c:import url="/WEB-INF/jsp/acdc/navigation_steps.jsp" />
		<!-- Display promotions and features, featuregroups that are present for the product -->
		<!-- Right Block -->
		<div class="pc_frameblk">
			<div class="pc_frameblk_cont_big">
				 <ul class="nav nav-tabs">
					<li id="promotionsLi" class="active"><a data-toggle="tab" href="#Promotions">Promotions</a></li>
					<li id="featuresLi"> <a data-toggle="tab" id="promotionsTab" href="#Features">Features</a></li>
				 </ul>

				 <div class="tab-content">
				<!-- Dislay Promotions -->
				<div id="Promotions" class="tab-pane fade in ">
					<div class="pdet_promos_content_cont">	
						<c:out escapeXml="false" value="${promotions}"/>
					</div>
				</div>
				
				<!-- Display features and featuregroups -->
				<div id="Features" class="tab-pane fade" >
					<div class="pdet_features_content" id="pdet_features_content">
						<c:out escapeXml="false" value="${viewObjMarketHighlt}"/>
					</div>
				</div>
				</div>
				<!-- End pdet_promos_features -->
			</div>
			<div class="pc_frameblk_right_btns">
				<input type="submit" id="next" value="Forward >" class="button" onclick="buildSelectedPromotion();"/>
			</div>
		</div>
	  </div>
	    <input type="hidden" name="nav_steps_one" class="nav_steps" value="stepOne"/>
	</form>
</body>
</html>