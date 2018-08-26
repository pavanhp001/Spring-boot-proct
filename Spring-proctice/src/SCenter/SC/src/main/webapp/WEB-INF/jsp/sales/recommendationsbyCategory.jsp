<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<title><fmt:message key="recommendations.header" /></title>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/recommendationsbycat.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/rec_product_details.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/rec_compare.css'/>" />

<script src="<c:url value='/js/jquery.simplemodal.js'/>"> </script>
<script src="<c:url value='/script/recommendations.js'/>"></script>
<script src="<c:url value='/script/features.js'/>"></script>
<script src="<c:url value='/script/promotions.js'/>"></script>
<script type="text/javascript">

var noOfOneTimePromotionsSelected = 0;
var noOfMonthlyPromotionsSelected = 0;

$(document).ready(function(){
	$("span.tabcontent span.row").each(function(){
	    var ht = $(this).height();
	    if(ht > 70){
	        $(this).parent().height(ht);
	    }
	});
	$('.ViewDetailsBtn').click(viewDetails);
	
	$("#featuresTab").click(function(){
 		//$("#AddToOrderBtn2").unbind("click");
 		//$("#AddToOrderBtn2").click(addFeatures);
 		//$("select.styled").change(priceChange);
 		//$("checkbox").checked(CheckBoxPriceUpdate);
	 	$("#pdet_hl_content").show();
	 	$("#pdet_promos_content").hide();
	 	$("#featuresTab").css("background-color", "#97D444");
	 	$("#promotionsTab").css("background-color", "#666666");
	 	//$(this).css("background-color", "#585858"); 
	 	//$(this).css("color", "#fff"); 
	 	//$(this).css("margin-top", "0px"); 
	 	
	 	//$("#promotionsTab").css("color", "#000"); 
	 	//$("#promotionsTab").css("margin-top", "-2px");
 	});
 	

	$("input[id*='_PROMOTION']").live('change', function(){
		
		var val = $(this).val();

		var json = JSON.parse(val);
		var isChecked = false;
		var conflictExtID = json.promoConflict;
			 
		var conflicts = conflictExtID.split(',');
		 
		if($(this).attr('checked')){
			try{

				var noOfInfoPromotions = 0;
				var noOfMonthlyPromotions = 0;
				var noOfOneTimePromotions = 0
				
				var totalPromotionPrice = 0.00;
				var monthlyPromotionPrice = 0.00;
				var oneTimePromotionPrice = 0.00;


				//this is for preventing the promotions if he selected same type
				var productJsonObjectTemp = JSON.parse(this.value);
				if(productJsonObjectTemp.type == "baseMonthlyDiscount"){
					if(noOfMonthlyPromotionsSelected > 0){
						this.checked = false;
						noOfMonthlyPromotionsSelected++;
					}
				}

			/*	This is for one time fee discount promotion code */
			
			//this is for preventing the promotions if he selected same type
				if(productJsonObjectTemp.type == "oneTimeFeeDiscount"){
					if(noOfOneTimePromotionsSelected > 0){
						this.checked = false;
						noOfOneTimePromotionsSelected++;
					}
				}

				 if(noOfMonthlyPromotionsSelected>1){
					 alert("You selected same type of promotions(Base Monthly Discount Promotion). So first deselect the selected one and select another.");
				 }
				 if(noOfOneTimePromotionsSelected>1){
					 alert("You selected same type of promotions(OneTime Fee Discount Promotion). So first deselect the selected one and select another.");
				 }

				 $('#pdet_promos_content input[type=checkbox]').each(function () {
					 if (this.checked) {
					 	var productJsonObject = JSON.parse(this.value);
		            	var promotionType = productJsonObject.type;
		            	if(promotionType == "informationalPromotion" || promotionType=="unspecifiedType"){
		            		noOfInfoPromotions++;
		            	}else if(promotionType == "baseMonthlyDiscount"){
		            		noOfMonthlyPromotions++;
		            		if(monthlyPromotionPrice==0.00){
		            			if(productJsonObject.priceValueType=="relative"){
		            				monthlyPromotionPrice = productTotalPrice-parseFloat(productJsonObject.priceValue);
			            		}else{
			            			monthlyPromotionPrice = parseFloat(productJsonObject.priceValue);
			            		}
		            			totalPromotionPrice = totalPromotionPrice+monthlyPromotionPrice;
		            		}
		            	}
		            	else if(promotionType == "oneTimeFeeDiscount"){
		            		noOfOneTimePromotions++;
		            		if(oneTimePromotionPrice==0.00){
		    					oneTimePromotionPrice = parseFloat(productJsonObject.priceValue);
		            		}else{
			            		this.checked = false;
		            		}
		            	}
					 }
				 });

				 noOfOneTimePromotionsSelected = noOfOneTimePromotions;
				 noOfMonthlyPromotionsSelected = noOfMonthlyPromotions;
				 
				 if(noOfMonthlyPromotions>1){
					 alert("You selected same type of promotions. So first deselect the selected promotion and select another.");
				 }
				 if(noOfOneTimePromotions>1){
					 alert("You selected same type of promotions. So first deselect the selected promotion and select another.");
				 }

				 if(noOfMonthlyPromotions>0){
					 	$("#product_priceInfo").html("$"+totalPromotionPrice.toFixed(2));
						$("#product_basePriceInfo").html("<del>"+basePriceValue+"</del>");
					}else{
						$("#product_priceInfo").html("");
						$("#product_basePriceInfo").html(basePriceValue);
					}

				for(var i=0;i<conflicts.length;i++){
			 		$('[id=' + conflicts[i] + '_PROMOTION]').attr('disabled', true);
			 		if($('[id=' + conflicts[i] + '_PROMOTION]').attr('checked')){
			   			$('[id=' + conflicts[i] + '_PROMOTION]').attr('checked', false);
			  		}
				}
			}catch(err){alert(err);}
			
		}else {

			var noOfInfoPromotions = 0;
			var noOfMonthlyPromotions = 0;
			var noOfOneTimePromotions = 0
			
			var totalPromotionPrice = 0.00;
			var monthlyPromotionPrice = 0.00;
			var oneTimePromotionPrice = 0.00;

			 $('#pdet_promos_content input[type=checkbox]').each(function () {
				 if (this.checked) {
					
				 	var productJsonObject = JSON.parse(this.value);
	            	var promotionType = productJsonObject.type;
	            	if(promotionType == "informationalPromotion" || promotionType=="unspecifiedType"){
	            		noOfInfoPromotions++;
	            	}else if(promotionType == "baseMonthlyDiscount"){
	            		noOfMonthlyPromotions++;
	            		if(monthlyPromotionPrice==0.00){
		            		if(productJsonObject.priceValueType=="relative"){
	            				monthlyPromotionPrice = productTotalPrice-parseFloat(productJsonObject.priceValue);
		            		}else{
		            			monthlyPromotionPrice = parseFloat(productJsonObject.priceValue);
		            		}
	            			totalPromotionPrice = totalPromotionPrice+monthlyPromotionPrice;
	            		}
	            	}
	            	else if(promotionType == "oneTimeFeeDiscount"){
	            		noOfOneTimePromotions++;
	            		if(oneTimePromotionPrice==0.00){
	            			oneTimePromotionPrice = parseFloat(productJsonObject.priceValue);
	            		}
	            	}
				 }
			 });

			 noOfOneTimePromotionsSelected = noOfOneTimePromotions;
			 noOfMonthlyPromotionsSelected = noOfMonthlyPromotions;
			 
			 if(noOfMonthlyPromotions>0){
				 	$("#product_priceInfo").html("$"+totalPromotionPrice.toFixed(2));
					$("#product_basePriceInfo").html("<del>"+basePriceValue+"</del>");
				}else{
					$("#product_priceInfo").html("");
					$("#product_basePriceInfo").html(basePriceValue);
				}

			for(var i=0;i<conflicts.length;i++){
				$('[id=' + conflicts[i] + '_PROMOTION]').removeAttr('disabled');
			}
		}
		
	});

 	
 	$("#promotionsTab").click(function(){
 		//$("#AddToOrderBtn2").unbind("click");
 		//$("#AddToOrderBtn2").click(addPromotions);
	 	$("#pdet_promos_content").show();
	 	$("#pdet_hl_content").hide();
	 	$("#featuresTab").css("background-color", "#666666");
	 	$("#promotionsTab").css("background-color", "#97D444");
	 	//$(this).css("background-color", "#585858"); 
	 	//$(this).css("color", "#fff"); 
	 	//$(this).css("margin-top", "0px"); 
	 	
	 	//$("#featuresTab").css("color", "#000"); 
	 	//$("#featuresTab").css("margin-top", "-2px"); 

	 	
 	});
 	$("#AddToCompare").click(addToCompare);
 	$('.pp_checkbox').click(showCompareButton);
 	$(".pp_checkbox").attr('checked', false);

	jQuery(window).load(function () {
	      //To save the html on page load
	      savePageHtml(true, "");
	});
 });

var basePriceValue = 0.0;
var productTotalPrice = 0.0;
var viewDetails = function() {
	try{
		basePriceValue = 0.0;
	var aidval = $(this).attr("id").replace(/\:/g,":");
	var hiddenIdVal = "hidden_"+escapeSpecialCharacters(aidval);
	var json = $("input#"+hiddenIdVal).val();
	
	var prodJson = JSON.parse(json); 
	basePriceValue = prodJson.recuring;
	
	var splittedArray = basePriceValue.split("$");
	productTotalPrice = parseFloat(splittedArray[1]);
	
	var data = { "aidval" : JSON.stringify(prodJson) };
	var vals = aidval.split(','); 
	$("#providerName_dialog").html('Product Details');
	$("input#featuresJson").val(JSON.stringify(prodJson));
	$("input#prodId").val( $(this).attr("id"));
	$("#recommendationsDiv").hide();
	$("#basic-modal-content1").show();
	$("#basic-modal-content1 #contentwrapperpdet").hide();
	$("#basic-modal-content1 #bmc_img").show();
    $.ajax({
    	type: 'POST',
    	data:data,
    	url: "<%=request.getContextPath()%>/salescenter/recommendations/viewOrderDetails",
    	success: function(data1){
    		if(data1=="sessionTimeOut"){
    			var path = "<%=request.getContextPath()%>";
    			window.location.href = path+"/salescenter/session_timeout";
    		}else{
    			var data = JSON.parse(data1); 
	        	noOfOneTimePromotionsSelected = 0;
	       		noOfMonthlyPromotionsSelected = 0;
	       		var errorImage = '${providersImageLocation}'+data.onErrorImage+'.jpg';  
	    		$("#AddToOrderBtn2").unbind("click");
	     		$("#AddToOrderBtn2").click(addFeturesAndPromotions);
	     		
	    		$("#basic-modal-content1 #bmc_img").hide();
	    		$("#basic-modal-content1 #contentwrapperpdet").show();
	    		$("#product_image img").attr('src', '${providersImageLocation}'+data.detailsImage+'.jpg');
	    		$("#product_image img").attr('onError', "this.src = '"+errorImage+"'");  
	    		$("#product_name").html(data.name);
	    		$("#product_name").html($("#product_name").text()); 
	    		$("#productBaseInfoDup").val(basePriceValue);
	    		alert()
	    		$("#product_basePriceInfo").html(basePriceValue);
	    		$("#product_priceInfo").html("");
	    		var description = data.longDescription.replace(/amp;/g,""); 
	    		$("#product_longDescription").html(description);
	    		var marketingHighlights=data.marketing.replace(/amp;/g,"");
	    		$("#pdet_hl_content_cont").html(marketingHighlights);
	    		$("#featuresContent").html(data.features);
	    		$("#pdet_promos_content").html(data.promotions);
	    		$("#providerId").val(data.providerExtId);
	
	    		if($("#pdet_promos_content").text().trim() == ''){
	    			//$("#pdet_promos_content").append("&nbsp;")
	    			//"#promotionsTab").css('display','none');
	    			//$("#featuresTab").trigger("click");
	    		}else{
	    			$("#promotionsTab").css('display','block');
	    			//$("#promotionsTab").trigger("click");
		    	}

	    		 if ($("#featuresContent").height() < $("#tblFeatures").height()) {
		    		 $("#tblFeatures").css("width","100%");
		    	  }else{
		    		  $("#tblFeatures").css("width","96.5%");
		    	  }
		    	
	    			//disabling future data (select/check boxes)
	    		 $('#featuresContent input[type=checkbox]').each(function () {
	        		 this.disabled = true;
			      });
	    		 $("#featuresContent select").on('change', function() {
	        		 this.value = "";
	    		 });
	    		 if(data.providerExtId=='32416075'){
		    			var count = 0;
	    				 $('#pdet_promos_content input[type=hidden]').each(function () {
		    				 if(count==0){
	    					 	count = count+1;
	    					 	var productJsonObject = JSON.parse(this.value);
	    		            	var promotionType = productJsonObject.type;
	    		            	if(promotionType == "baseMonthlyDiscount"){
	    		            		var monthlyPromotionPrice = 0.00;
			            		if(productJsonObject.priceValueType=="relative"){
		            				monthlyPromotionPrice = productTotalPrice-parseFloat(productJsonObject.priceValue);
			            		}else{
			            			monthlyPromotionPrice = parseFloat(productJsonObject.priceValue);
			            		}
		            			$("#product_priceInfo").html("$"+monthlyPromotionPrice.toFixed(2));
			    				$("#product_basePriceInfo").html("<del>"+basePriceValue+"</del>");
	    		            	}
		    				 }
	    				 });
	    		 }
	    		 

	    		//To save the html on page load
	    			savePageHtml(true, "");
    		}
    	}
 	});
   
	}catch(err){alert(err);}
	 return false;
}
	var addToCompare = function(){
		var selectedArray = {
				lineItems: []		    
			};
		if($('.pp_checkbox:checked').length > 3 ){
			alert("Only 3 Products Can be compared");
		}else{
			$("#recommendationsDiv").hide();
			$("#comparisionDiv").show();
			$("#bmc_img_comp").show();
			$("#reccomparecont").hide();
			
			$("input.pp_checkbox:checked").each(function(){
				var val=$(this).val().replace(/\:/g,"\\:");
				val = val.replace("input_", "hidden_product_");
			    var json=$("input#"+val).val();
			    var prodJson = JSON.parse(json); 
			    var productExernalId = prodJson.productExernalId;
			    var providerExernalId = prodJson.partnerExternalId;
			    var categoryName = $("input#categoryName").val();
			    var newJSON = {"productExernalId":productExernalId,"providerExernalId":providerExernalId,"categoryName":categoryName};
			    selectedArray.lineItems.push(JSON.stringify(newJSON));
			});
			
			var path =$("input#contextPath").val();
			var url = path+"/salescenter/recommendations/compareProducts";
		    var data = {};
		    data["selectedProducts"] = JSON.stringify(selectedArray);
	    
		    $.ajax({
		    	type: 'POST',
		    	url: url,
		    	data: data,
		    	success: onComparisionComplete,
		 	});
		}
	}

	var onComparisionComplete = function(data){
		if(data=="sessionTimeOut"){
			var path = "<%=request.getContextPath()%>";
			window.location.href = path+"/salescenter/session_timeout";
		}else{
			data = JSON.parse(data);
			$("#bmc_img_comp").hide();
			data.table = data.table.replace(/amp;/g,"");
			$("#reccomparecont").html(data.table);
			$("#reccomparecont").show();
			//To save the html on page load
			savePageHtml(true, "");
		}
		
	}
	var showCompareButton = function(){
		if($('.pp_checkbox:checked').length >= 2 ){
			$('span.Compare').show();
		}else{
			$('span.Compare').hide();
		}
	}
  function displayChannelLineUpData(providerExternalId,providerName)
	{
		//alert("providerExternalId="+providerExternalId);
		var urlPath = "<%=request.getContextPath()%>/salescenter/channelLineUpData?providerExternalId="+providerExternalId+"&providerName="+providerName;
		window.open(urlPath,"", "scrollbars=yes, width=1000, height=550");
	}

</script>

<style type="text/css">

.tblFeaturesHeader table {
    background-color: #FFFFFF;
    border: 1px solid #666666;
    border-collapse: collapse;
    color: #000000;
    font-size: 12px;
    line-height: 16px;
    width: 97%;
}

.tblFeaturesHeader table th {
    background-color: #C7C7C7;
    border-bottom: 1px solid #666666;
    border-left: 1px solid #666666;
    border-right: 1px solid #666666;
    color: #000000;
    font-size: 14px;
    font-weight: normal;
    height: 22px;
   	padding: 3px 5px;
}

.pdet_features_content {
    background-color: #FFFFFF;
    display: block;
    height: 333px;
    overflow-y: auto;
    width: inherit;
}

.pdet_features_content table td {
    border: 1px solid #666666;
    color: #000000;
    font-size: 14px;
    padding: 7px;
 /*	word-break:break-all;*/
}

.productTitle{
	position: absolute;
	top: 0;
	left: 239px;
	float: left;
	display: block;
	padding: 2px 2px 2px 0px;
	font-size: 14px;
	color: #000;
	text-transform: uppercase;
	line-height: 15px;
	width: 470px;
}
</style>
</head>
				<div id="recommendationsDiv">
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
								<span class="cell pageTitle"><fmt:message key="${title}" /></span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="recommend" name="recommend" action="<%=request.getContextPath()%>/salescenter/closingcall" method="post">
							<input type="hidden" name="selectedProducts" id="selectedProducts" value="" />
							<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="categoryName" value="${categoryName}" />
							<input type="hidden" id="pageTitle" name="pageTitle" value="Recommendations">
							<div class="contentwrapperrec">
								<div id="action_wrapper">
									<div class="widget_container">
										<div class="tabsWrapper">
											${buildHtmlData}
										</div>
									</div>
								</div>
								<div class="bottombuttons">
									<div class="leftbtns">
										<!--<input type="button" id="AddToOrderBtn" value="Add to Order" />
										--><span class="Compare" style="display: none;">
											<input class="addtoorderbtn" type="button" id="AddToCompare" value="Compare" />
										</span>
									</div>
									<div class="rightbtns">
										<input type="button" id="backToDiscoveryId" name="backToDiscoveryId" value="< Back" onclick="backToDiscovery();" />
									</div>
								</div>
							</div>
							</form>
						</section>
					</section><!-- Content Full Cont -->
				</div>

   

								<div id="basic-modal-content1" style="float:left;display:none;">
					<section id="contentfullcontpdet">
						<header id="content_header_pdet">
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
								<span class="cell pageTitle">Product Details</span>
								<span class="callTime" id="startTimeTextPopup"></span>
							</div>
						</header>
						<section id="contentpdet">
							<div id="bmc_img" style="display:none;width:inherit;text-align:center;"><img src="<%=request.getContextPath()%>/images/spinner.gif"></img></div>
							<div class="contentwrapperpdet" id="contentwrapperpdet">
								<input type="hidden" name="featuresJson" id="featuresJson" value='' /> 
								<input type="hidden" name="prodId" id="prodId" value="" /> 
								<div id="action_wrapper_pdet">
									<div class="widget_container_pdet">
										<div class="product_det_cont">
											<div class="pdet_productinfo">
												
												<div class="pdet_providerlogo">
													<div class="pdet_logo" id="product_image">
														<img src="" />
													</div>
												</div>
												<div class="pdet_productname">
													<div class="pdet_prdname" id="product_name"></div>
												</div>
												<div class="pdet_productprice productPriceContent">
													<div class="pdet_prdprice" style="height: 30px;">
														BASE PRICE: <span id="product_basePriceInfo"></span>
													</div>
													<div class="promoprc">
														<span id="product_priceInfo"></span>
														<input type="hidden" id="productBaseInfoDup" value="">
													</div>
												</div>
											</div>
										</div>
										
										<div class="pdet_content_block">
											<div class="pdet_productdesc" id="product_longDescription"></div>
											<div class="pdet_features">
												<div class="pdet_ftrs_tab">Features</div>
												<div class="tblFeaturesHeader">
													<table class="tblFeatures">
														<thead>
															<tr>
																<th style="width:22%;">Feature Type</th>
																<th style="width:18%;">Price</th>
																<th style="width:57%;">Options</th>
															</tr>
														</thead>
													</table>	
												</div>
												<div class="pdet_ftrs_dtls" style="width: 100.25%">
													<div class="pdet_features_content" id="featuresContent">	
													</div>
												</div>
											</div><!-- pdet_highlights Highlights -->
											<div class="pdet_promos_features">
												<div class="pdet_tabs">
												<input type="hidden" id="providerId" value="">
													<div class="pdet_promos_tab" id="promotionsTab" onclick="promotionsTab();">Promotions</div>
													<div class="pdet_features_tab" id="featuresTab" onclick="highlightsTab();">Highlights</div>
												</div>
												<div class="pdet_promos_content" id="pdet_promos_content">
												</div>
												<div class="pdet_hl_content" id="pdet_hl_content" style="display:none;">	
													<div class="pdet_hl_content_cont" id="pdet_hl_content_cont">		
													</div>
												</div>
											</div><!-- pdet_promos_features -->
										</div><!-- pdet_content_block -->
									</div>
								</div>
								<div class="bottombuttons">
									<div class="leftbtns">
										<input type="button" id="AddToOrderBtn2" value="Add to Order" />
									</div>
									<div class="rightbtns">
										<input type="button" id="backToRecommendations" onclick="backToRecommendations();" value="< Back" />
									</div>
								</div>
							</div>
						</section>		
			
					</section><!-- Content Full Cont -->
				</div>
				

				<div id="comparisionDiv" style="display: none;">
					<section id="contentfullcontcompare">
						<header id="content_header_compare">
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
								<span class="cell pageTitle">Compare</span>
								<span class="callTime" id="startTimeTextCompareProducts">Call Time: 03:56</span>
							</div>
						</header>
						<section id="contentcompare">
							<div class="contentwrappercompare">
								<div id="action_wrapper_compare">
									<div class="widget_container_compare">
										<div id="bmc_img_comp" style="display:none;width:inherit;text-align:center;" ><img src="<%=request.getContextPath()%>/images/spinner.gif"></img></div>
										<div class="reccomparecont" id="reccomparecont">
											<!-- Rec Compare Container -->
											<!-- End -->
										</div>
									</div>
								</div>
								<div class="bottombuttons">
									<div class="rightbtns">
										<input type="button" id="backToProducts"  name="backToProducts" value="< Back" onclick="backToProducts();" />
									</div>
								</div>
							</div>
						</section>
					</section><!-- Content Full Cont -->
				</div>
	