var totalBaseRecPrice = 0.00;
var preBaseRePrice = 0.00;
var preBaseNonRePrice = 0.00;
var totalNonBaseNonRecPrice = 0.00;
var baseInstalationValue = 0.0;
var isBasePriceUpdate = true;
var isBaseNonPriceUpdate = true;
var internetSortOfferShowList = ["powerPitch","TRIPLE_PLAY","DOUBLE_PLAY_VI","DOUBLE_PLAY_PI","DOUBLE_PLAY","INTERNET"];
var tvSortOfferShowList = ["powerPitch","TRIPLE_PLAY","DOUBLE_PLAY_VI","DOUBLE_PLAY_PV","DOUBLE_PLAY","VIDEO"];
var categoryName="PP";
$(document).ready(function(){
	categoryName = $("#categoryName").val();
	var category = $("input#categoryName").val();
	
	$('.tv_sortOffer').css('display','none');
	$('.internet_sortOffer').css('display','none');
	$("#remove").click(removeFromCart);
	$('a.disabled').live('click', function(event){
        event.preventDefault();
    });
	$(".dialogMultipleproductsClose").click(function(){
		$(".dialogMultipleproducts").dialog("close");
	});
	$.each(internetSortOfferShowList,function(index,value){
		if(category != undefined && category != '' && category == value){
			$('.internet_sortOffer').css('display','block');
			return false;
		}			
	});
	$.each(tvSortOfferShowList,function(index,value){
		if(category != undefined && category != '' && category == value){
			$('.tv_sortOffer').css('display','block');
			return false;
		}
	});
	$( ".sortOffer" ).change(function () {
		  externalScope.$apply(function () {
			  externalScope.isLoading = true;
		  });
		  
 	 	 if( $(".sortOffer option:selected").val() != "" ){
 	 		var data = {};
 	 		filterHistory[currentCategory] =  $(".sortOffer option:selected").val()
 	 		externalScope.$apply(function() {
 	 			data['sortOfferOption'] = $(".sortOffer option:selected").val();
 	 			externalScope.filteringAjaxCall(data);
 	 	   });
 	 	 }
 	 	
 	 	
 	  });
	
    
});
/* */

//updatePreShoppingPrice
function updatePreShoppingPrice(){
	 totalNonBaseNonRecPrice = preBaseNonRePrice.replace("$","");
	 totalBaseRecPrice = preBaseRePrice.replace("$","");
	 totalNonBaseNonRecPrice = parseFloat(totalNonBaseNonRecPrice);
	 totalBaseRecPrice = parseFloat(totalBaseRecPrice);
	 $("#featuresContent select").each(function () {
		 var option = $('option:selected', this).attr('item');
	      // console.log("option="+option );
	       if(option != undefined && isJson(option)){
	    		var priceJsonObj = JSON.parse(option);
	    		if(priceJsonObj != undefined 
	    				&& priceJsonObj.baseRec != undefined 
	    				     && priceJsonObj.baseRec != ""
	    				    	  && !isNaN(priceJsonObj.baseRec)){
	    			totalBaseRecPrice = parseFloat(priceJsonObj.baseRec) + totalBaseRecPrice;
	    		}
	    		if(priceJsonObj != undefined 
	    				&& priceJsonObj.baseNonRec != undefined  
	    				  && priceJsonObj.baseNonRec != ""  
	    					   && !isNaN(priceJsonObj.baseNonRec)){
	    			totalNonBaseNonRecPrice = parseFloat(priceJsonObj.baseNonRec) + totalNonBaseNonRecPrice ;
	    		}
		   }
	});
	 $('#featuresContent input:checked').each(function() {
		 var checkBoxItem = $(this).attr('item');
	       //console.log("checkBoxItem="+checkBoxItem);
	       if(checkBoxItem != undefined && isJson(checkBoxItem)){
	    		var priceJsonObj = JSON.parse(checkBoxItem);
	    		if(priceJsonObj != undefined 
	    				&& priceJsonObj.baseRec != undefined 
	    				     && priceJsonObj.baseRec != ""
	    				    	  && !isNaN(priceJsonObj.baseRec)){
	    			totalBaseRecPrice = parseFloat(priceJsonObj.baseRec) + totalBaseRecPrice;
	    		}
	    		if(priceJsonObj != undefined 
	    				&& priceJsonObj.baseNonRec != undefined  
	    				  && priceJsonObj.baseNonRec != ""  
	    					   && !isNaN(priceJsonObj.baseNonRec)){
	    			totalNonBaseNonRecPrice = parseFloat(priceJsonObj.baseNonRec) + totalNonBaseNonRecPrice ;
	    		}
		   }
	 });
		if(isBasePriceUpdate){
			$("#product_basePriceInfo").text("$"+totalBaseRecPrice.toFixed(2));
			$("#product_priceInfo").text('');
		}else{
			$("#product_priceInfo").text("$"+totalBaseRecPrice.toFixed(2));
		}
		if(isBaseNonPriceUpdate){
			$("#product_baseInstalationInfo").text("$"+totalNonBaseNonRecPrice.toFixed(2));
			$("#product_priceInfo2").text('');
		}else{
			$("#product_priceInfo2").text("$"+totalNonBaseNonRecPrice.toFixed(2));
		}
}

function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        //alert(e);
        return false;
    }
    return true;
}

function sortOfferSuccess(data1){
	if(data1=="sessionTimeOut"){
		var path =$("input#contextPath").val();
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		$(".tabsWrapper").html(data1);
		$('.pp_checkbox').click(showCompareButton);
		$('.ViewDetailsBtn').click(viewDetails);
	}
}

function backToDiscovery(flowEventId){
	 if(flowEventId!=undefined && flowEventId!=""){
	  $("form#recommend input[id='_eventId']").val(flowEventId);
	  $("form#recommend input[id='isBack']").val("true"); 
	  $("form#recommend").submit();
	 }else{
	  var path =$("input#contextPath").val();
	  action = path+"/salescenter/discovery";
	  $("form#[name='recommend']").attr("action", action);
	  $("form#recommend input[id='isBack']").val("true"); 
	  $("form#[name='recommend']").submit();
	 }
	}

	function goToUtilityOffer(flowEventId){
	 if(flowEventId!=undefined && flowEventId!=""){
	  $("form#recommend input[id='_eventId']").val(flowEventId);
	  $("form#recommend").submit();
	 }else{
	  var path =$("input#contextPath").val();
	  var customerId = $("input#customerId").val();
	  var orderId = $("input#orderId").val();
	  action = path+"/salescenter/CKO/"+customerId+"/order/"+orderId;
	  $("form#[name='recommend']").attr("action", action);
	  $("form#[name='recommend']").submit();
	 }
	}

function backToRecommendations(){
	//Removing Cart Errors
	$("h4#cartError").next().remove();
	$("h4#cartError").remove();
	
	$("#basic-modal-content1").hide();
	$("#bundle-modal-content1").hide();
	$("#recommendationsDiv").show();
	$(".tabsWrapper").show();
	
	//this code for unchecks the all check boxes. 
	$('#recommendationsDiv input[type=checkbox]').each(function(){
		 this.checked = false;
	});
	if(currentCategory != undefined){
		$(".pageTitle").text(getTitle(currentCategory));
	}
	savePageHtml(false, "");
}

function backToProducts(){
	$("#comparisionDiv").hide();
	$("#recommendationsDiv").show();
	$(".pp_checkbox").attr('checked', false);
	$('span.Compare').hide();
	savePageHtml(false, "");
}
function goToClosingcall(){
	var len = $(".itemCheckBox").length;
	if(len == 0){
		$("form#recommend").submit();
	}else{
		var orderId = $("input#orderId").val();
		var href = window.location.href;
		window.location = href.slice(0, href.lastIndexOf("/")+1) + "summary/"+orderId;
	}
	
}

/*
 * Toggle function for the Product status on Shopping cart
 */
var toggleStatus = function(){
	var h4 = $(this).closest("h4");
	var div = h4.find("div.liStatus");
	if(div.is(":hidden")){
		$(this).addClass("minus");
    } else {
    	$(this).removeClass("minus");
    }
	div.slideToggle();
}

var addToOrder = function(){
	var selectedArray = {
			lineItems: []		    
		};
	var h4Array= [];
	$("input:checkbox:checked").each(function(){
		//Deselecting the products
		$(this).attr('checked', false);
		
		//Removing Cart Errors
		$("h4#cartError").next().remove();
		$("h4#cartError").remove();
		
		var val=$(this).val().replace(/\:/g,"\\:");
		val = val.replace("input_", "product_");
		
	    var json=$("input#"+val).val();
	    var prodJson = JSON.parse(json); 
	    var id = $(this).val();

	    buildHtml(id,prodJson,h4Array);
	    
	    selectedArray.lineItems.push(JSON.stringify(prodJson));
	}); 
	var path =$("input#contextPath").val();
	var url = path+"/salescenter/recommendations/AddtoOrder";
    var data = {};
    data["productData"] = JSON.stringify(selectedArray);
    data["orderId"] = $("input#orderId").val();
    
    try{ 
	    $.ajax({
	    	type: 'POST',
	    	url: url,
	    	data: data,
	    	success: onComplete(h4Array),
			error: onError(h4Array)
	 	});
	} catch(e){
		alert(e);
	}
	savePageHtml(false, "");
};


var onComplete = function(h4Array) {
	return function(data){
		var totalPt = $("#totalPointsId").text();
		totalPt = totalPt.replace("Points: ","");
        $(h4Array).each(function(i) {
        	enablePage();
			if (data[i] === undefined) {
					h4_id = escapeSpecialCharacters(this);
					var hidden_row = $("<span></span>").text('Services Down :Please try again later');
					hidden_row = hidden_row.css("color","#F00");
					$("h4#"+h4_id).append(hidden_row);
					$("h4#"+ h4_id + " div:eq(0)").remove();
				}else{
					if (data[i].indexOf("Error") !== -1) {
						h4_id = escapeSpecialCharacters(this);
						var msg = data[i].split("|");
						var span_fright = $("h4#"+h4_id).find('span.fRight');
					    var hidden_row = $("<span></span>").text(msg[1]);
					    hidden_row = hidden_row.css("color","#F00");
					    
						$("h4#"+h4_id).append(hidden_row);
						$("h4#"+ h4_id + " div:eq(0)").remove();
						$("div#orderInfoHead").unbind("click");
						$("div#orderInfoHead").click(goToOrderSummary);
						$("h4#"+h4_id).attr("id","cartError");
						
					}else{
						var element = data[i].split("|");
						if(element[6] != undefined){
							totalPt = element[6].replace('"]','');
							totalPt = parseFloat(totalPt).toFixed(1);
						}

						h4_id = escapeSpecialCharacters(this);
						$("h4#"+ h4_id + " div:eq(1)").css("display", "table");
						$("h4#"+h4_id).find("div.liStatus").html(element[2]);
						$( "body" ).data( "li_externalId", element[1] );
						var hidden_row = $("<input/>").attr({"value":element[1], "type":"hidden", "id":"li_externalId_"+element[1]});
						$("h4#"+h4_id).append(hidden_row);
						var hidden_row1 = $("<input/>").attr({"value":element[0], "type":"hidden", "id":"li_number_"+element[1]});
						$("h4#"+h4_id).append(hidden_row1);
						var hidden_row2 = $("<input/>").attr({"value":element[5], "type":"hidden", "id":"li_hasPromotion_"+element[1]});
						$("h4#"+h4_id).append(hidden_row2);
						var price = parseFloat(element[3]);
						var instPrice = parseFloat(element[4]);
						if(categoryName != undefined && categoryName != '' && categoryName == 'NATURALGAS'){
							$("h4#"+h4_id).find("span#price").text("$"+price);
							$("h4#"+h4_id).find("span#monPrice").text(price);
					    }else{
					    	$("h4#"+h4_id).find("span#price").text("$"+price.toFixed(2));
					    	$("h4#"+h4_id).find("span#monPrice").text(price.toFixed(2));
						}
						$("h4#"+h4_id).find("span#instPrice").text(instPrice.toFixed(2));
						if(!isNaN(price)){
							updateInstallationPrice(instPrice,1);
							updateMonthlyPrice(price, 1);
						}
						
						$("h4#"+ h4_id + " div:eq(0)").remove();
						$("div#orderInfoHead").unbind("click");
						$("div#orderInfoHead").click(goToOrderSummary);
					}
			
				}
		});
		
		$("#totalPointsId").text("Points: "+totalPt);
		//To save the html on add product to order
		savePageHtml(false, "");
	}
}
var onCompleteMB = function(h4Array) {
	return function(data){
		var totalPt = $("input#totalPoints").val();
        $(h4Array.reverse()).each(function(i) {
        	enablePage();
			if (data[i] === undefined) {
					h4_id = escapeSpecialCharacters(this);
					var hidden_row = $("<span></span>").text('Services Down :Please try again later');
					hidden_row = hidden_row.css("color","#F00");
					$("h4#"+h4_id).append(hidden_row);
					$("h4#"+ h4_id + " div:eq(0)").remove();
				}else{
					if (JSON.stringify(data[i]).indexOf("Error") !== -1) {
						if (JSON.stringify(data[i]).indexOf("totalPoints") !== -1) {
							var totalPt1=JSON.stringify(data[i]).split(",");
							totalPt=totalPt1[1].split("|")[1].replace('"','').replace(']','');
						}
						h4_id = escapeSpecialCharacters(this);
						var msg1=JSON.stringify(data[i]).split(",");
						var msg=msg1[0].split("|");
						//var msg = JSON.stringify(data[i]).split("|");
						var span_fright = $("h4#"+h4_id).find('span.fRight');
					    var hidden_row = $("<span></span>").text(msg[1].replace('"]','').replace('"',''));
					    hidden_row = hidden_row.css("color","#F00");
					    
						$("h4#"+h4_id).append(hidden_row);
						$("h4#"+ h4_id + " div:eq(0)").remove();
						$("div#orderInfoHead").unbind("click");
						$("div#orderInfoHead").click(goToOrderSummary);
						$("h4#"+h4_id).attr("id","cartError");
					}
					else{
						var element = JSON.stringify(data[i]).split("|");
						if(element[6] != undefined){
							totalPt = element[6].replace('"]','');
							totalPt = parseFloat(totalPt).toFixed(1);
						}
						h4_id = escapeSpecialCharacters(this);
						$("h4#"+ h4_id + " div:eq(1)").css("display", "table");
						$("h4#"+h4_id).find("div.liStatus").html(element[2]);
						$( "body" ).data( "li_externalId", element[1] );
						var hidden_row = $("<input/>").attr({"value":element[1], "type":"hidden", "id":"li_externalId_"+element[1]});
						$("h4#"+h4_id).append(hidden_row);
						var hidden_row1 = $("<input/>").attr({"value":element[0], "type":"hidden", "id":"li_number_"+element[1]});
						$("h4#"+h4_id).append(hidden_row1);
						var hidden_row2 = $("<input/>").attr({"value":element[5], "type":"hidden", "id":"li_hasPromotion_"+element[1]});
						$("h4#"+h4_id).append(hidden_row2);
						var price = parseFloat(element[3]);
						var instPrice = parseFloat(element[4]);
						if(categoryName != undefined && categoryName != '' && categoryName == 'NATURALGAS'){
							$("h4#"+h4_id).find("span#price").text("$"+price);
							$("h4#"+h4_id).find("span#monPrice").text(price);
					    }else{
					    	$("h4#"+h4_id).find("span#price").text("$"+price.toFixed(2));
					    	$("h4#"+h4_id).find("span#monPrice").text(price.toFixed(2));
						}
						$("h4#"+h4_id).find("span#instPrice").text(instPrice.toFixed(2));
						if(!isNaN(price)){
							updateInstallationPrice(instPrice,1);
							updateMonthlyPrice(price, 1);
						}
						
						$("h4#"+ h4_id + " div:eq(0)").remove();
						$("div#orderInfoHead").unbind("click");
						$("div#orderInfoHead").click(goToOrderSummary);
					}
			
				}
		});
		
        $("#totalPointsId").text("Points: "+totalPt);
		savePageHtml(false, "");
		 $("#AddToOrderBtn3").removeAttr("disabled");
		 $("#AddToOrderBtn2").removeAttr("disabled");
	}
}
var onError = function(h4Array) {
	return function(data, textStatus, xhr, h4Array){
		alert("error\n" + data + ", " + textStatus + ", " + xhr);
	}
	
}

var removeFromCart = function(){
	var len = $(".itemCheckBox:checked").length;

	var lineItemIdArr =[];
	if(len == 0){
		alert("Please select item(s) to be removed");
	}else{
		$(".itemCheckBox:checked").each(function() {
			
			//Removing Cart Errors
			$("h4#cartError").next().remove();
			$("h4#cartError").remove();
			
			var parent_row = $(this).closest("h4");
			removeScrollDiv(parent_row);
			parent_row.find('div:eq(1)').css("display", "none");
			var span_fright = parent_row.find('span.fRight');

			var price     = span_fright.find("span#price").text();
			var instPrice = span_fright.find("span#instPrice").text();
			
			price = price.replace("$", "");
			price = parseFloat(price);
			
			instPrice = instPrice.replace("$", "");
			instPrice =  parseFloat(instPrice);
			
			if(!isNaN(price)){
				updateMonthlyPrice(price, -1);
				updateInstallationPrice(instPrice, -1);
			}

			$(this).css("display", "none");
			$(this).attr("checked", false);
			$(this).attr("disabled", true);

			parent_row.find('span#prodId').css("color","#F00");
			
			var liId  = parent_row.find('input[id^="li_externalId_"]').val();
			var providerId  = parent_row.find('#providerId').val();
			
			lineItemIdArr.push(liId+"|"+providerId);
		});
	}
	var path =$("input#contextPath").val();
	var url = path+"/salescenter/scart/removeProduct";
	var data = {};
	data["orderId"] = $("input#orderId").val();
	data["jsonArr"] = lineItemIdArr.toString();
	disablePage();
	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onRemoveComplete
	});
}

var onRemoveComplete = function(data){
	enablePage();
	data = JSON.parse(data.responseText);
	$(data).each(function(){
		var liId = this.liId;
		var stat = this.stat;
		if(stat){
			var status = this.status;
			var h4 = $("input#li_externalId_"+liId).parent();
			h4.find("div:eq(1)").css("display", "table");
			h4.find("div:eq(0)").remove();
			h4.find("span.fRight").find("span#price").text("Add");
			h4.find("span.fRight").find("span#price").addClass("addAgain");
			h4.find("span.fRight").find("span#price").click(reAdd);
			h4.find("div.liStatus").html(status);
			
			$("#totalPointsId").text("Points: "+(this.totalPoints).toFixed(1));
			var removeH4 = $("<h4></h4>").attr({"id": h4.attr("id"), "class": "systemColor2 removedH4"});
			removeH4.append(h4.html());
			removeH4.find("span.fRight").find("span#price").click(reAdd);
			removeH4.find("span.statusToggle").click(toggleStatus);
			$("div#itemsBlock").find("div.mCSB_container").append(removeH4);
			var divider = $("<div></div>").addClass("divider");
			$("div#itemsBlock").find("div.mCSB_container").append(divider);
			h4.next().remove();
			h4.remove();
		}else{
			//Shows the error message if it fails the Cart Rules
			var h4 = $("input#li_externalId_"+liId).parent();
			h4.find("div:eq(0)").remove();
			var hidden_row = $("<span id ='error'></span>").text(this.ErroMsg);
			hidden_row.css("color","#F00");
			
			h4.append(hidden_row);
			    setTimeout(function(){
			    	h4.find(".itemCheckBox").attr("disabled", false);
			    	h4.find("span#prodId").css("color", "#000")
			    	h4.find("div").css("display", "table");
			    	h4.find("span#error").remove();
			    	h4.find("div.liStatus").css("display", "none");
			    	h4.css("display", "block");
			    	var span_fright = h4.find('span.fRight');

					var price     = span_fright.find("span#price").text();
					var instPrice = span_fright.find("span#instPrice").text();
					
					price = price.replace("$", "");
					price = parseFloat(price);
					
					instPrice = instPrice.replace("$", "");
					instPrice =  parseFloat(instPrice);
					
					if(!isNaN(price)){
						updateMonthlyPrice(price, 1);
						updateInstallationPrice(instPrice, 1);
					}
				}, 3000);
		}
	});
	savePageHtml(false, "");
}

/*
 * Function for Re-Adding the product to Cart
 */
var reAdd = function(){

	//Removing Cart Errors
	$("h4#cartError").next().remove();
	$("h4#cartError").remove();
	
	//Gets the Entire HTML component
	var h4 = $(this).closest("h4");
	
	//Enabling the CHECKBOX & changing color of the product to black
	h4.find(".itemCheckBox").attr("disabled", false);
	h4.find("span#prodId").css("color", "#000")
	h4.find("div").css("display", "none");
	
	//Appending the loader image 
	addScrollDiv(h4);
	
	//Preparing the data for AJAX Calling
	var liId = h4.find("input[id^='li_externalId_']").val();
	var liNum = h4.find("input[id^='li_number_']").val();
	var path =$("input#contextPath").val();
	//Controller path
	var url = path+"/salescenter/scart/addStatusChange";
	var data = {};
	data["orderId"] = $("input#orderId").val();
	data["lineItemId"] = liId;
	data["lineItemNumber"] = liNum;
	disablePage();
	//AJAX call to re-add the product onComplete goes to function onReAddComplete
	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onReAddComplete
	});
	
}
/*
 * OnSucessfull execution of the reAdd function this function will get executed
 * 
 * Parse the response from the Controller and prepares the UI
 */
var onReAddComplete = function(data){
	enablePage();
	//Parsing the JSON response
	data = JSON.parse(data.responseText);
	if(data.stat){
		//Collecting the LineItemId and its Status
		var liId = data.liId;
		var status = data.status;
		$("#totalPointsId").text("Points: "+(data.totalPoints).toFixed(1));
		//Getting the HTML using LineItemId
		var h4 = $("input#li_externalId_"+liId).parent();
		
		//Removing the Loader Image
		h4.find("div:eq(0)").remove();
		
		//Prepares the HTML
		h4.find("div").css("display", "table");
		h4.find("span.fRight").find("span#price").removeClass("addAgain");
		h4.find("span.fRight").find("span#price").unbind("click");
		h4.find("div.liStatus").html(status);
		h4.find("div.liStatus").css("display", "none");
		
		//Changing the ID
		var h4Id = h4.attr("id");
		h4.find(".itemCheckBox").css("display", "block");
		h4Id = h4Id.slice(0, h4Id.lastIndexOf("_"));
		
		//Getting the Updated price
		var baseRecurringPrice = parseFloat(data.baseRecurringPrice);
		var baseNonRecurringPrice = parseFloat(data.baseNonRecurringPrice);
		var span_fright = h4.find('span.fRight');
		//Adding the New prices to the HTML 
		span_fright.find("span#instPrice").html(baseNonRecurringPrice.toFixed(2));
		if(categoryName != undefined && categoryName != '' && categoryName == 'NATURALGAS'){
			span_fright.find("span#price").html("$"+baseRecurringPrice);
			span_fright.find("span#monPrice").html(baseRecurringPrice);
		}else{
			span_fright.find("span#price").html("$"+baseRecurringPrice.toFixed(2));
			span_fright.find("span#monPrice").html(baseRecurringPrice.toFixed(2));
		}
		//Adding the New prices to the Cart
		if(!isNaN(baseRecurringPrice) && !isNaN(baseNonRecurringPrice)){
			updateMonthlyPrice(baseRecurringPrice, 1);
			updateInstallationPrice(baseNonRecurringPrice , 1)
		}
		
		//Getting the ProviderId
		var providerId = h4.find("input#providerId").val();
		
		//Checking whether it satisfies the Warm Transfer Rules or not & Creating the New HTML
		if(providerId == '30698347' || providerId == '2314635' || providerId == '4142189' || providerId == '30671669' || providerId == '5793'){
			var readdH4 = $("<h4></h4>").attr({"id": h4.attr("id"), "class": "systemColor2  addBottom"});
		}else{
			var readdH4 = $("<h4></h4>").attr({"id": h4.attr("id"), "class": "systemColor2"});
		}
		readdH4.append(h4.html());
		readdH4.find("span.statusToggle").click(toggleStatus);
		
		//Removing the Old HTML from Cart
		h4.next().remove();
		h4.remove();
		
		//Adding the New HTML to the Cart
		 addtoShoppingCart(providerId,readdH4);
	}else{
		//Shows the error message if it fails the Cart Rules
		var liId = data.liId;
		var msg = data.error.split("|");
		var h4 = $("input#li_externalId_"+liId).parent();
		h4.find("div:eq(0)").remove();
		var hidden_row = $("<span id ='error'></span>").text(msg[1]);
		hidden_row = hidden_row.css("color","#F00");
		h4.append(hidden_row);
		    setTimeout(function(){
		    	h4.find(".itemCheckBox").attr("disabled", true);
		    	h4.find("span#prodId").css("color", "#F00")
		    	h4.find("div").css("display", "table");
		    	h4.find("span#error").css("display", "none");
		    	h4.find("div.liStatus").css("display", "none");
		    	h4.css("display", "block");
			}, 2000);
		    
	}
	savePageHtml(false, "");
}
/*
 * ############################################################################################################################
 * Component functions
 * 
 * 1. buildHtml -{Builds the HTML for Shopping-Cart View}
 * 2. addtoShoppingCart -{Adds the Constructed HTMl to the right position in the Cart}
 * 3. updateMonthlyPrice -{Updates Total Monthly Price(if Status is 1 it adds the prices to cart & 
 * 												if status is -1 it removes the prices from Cart)}
 * 4. updateInstallationPrice -{Updates Total Installation Price(if Status is 1 it adds the prices to cart & 
 * 												if status is -1 it removes the prices from Cart)}
 * 5. addScrollDiv -{Appends the adding Image(blue color) to the HTML}
 * 6. removeScrollDiv -{Appends the Removing Image(red color) to the HTML}
 * 7. disablePage() - Disables all the buttons and events on Recommendation Page.
 * 8. enablePage() - Enables all the buttons and events on Recommendation Page.
 * 9.escapeSpecialCharacters(id) - escapes the special characters[':','&'] for the given id.
 * ############################################################################################################################
 */

/*
 * Generates the HTML  for the product on the Shopping-cart
 */
function buildHtml(id,prodJson,h4Array){
    
    var providerId = prodJson.partnerExternalId;
    
    var lineItemNumber = $("input#liCount").val();
    lineItemNumber = 1 + parseFloat(lineItemNumber);
    $("input#liCount").val(lineItemNumber);
    prodJson.lineItemNumber = lineItemNumber;

     h4_id = id.replace("input_", "row_");
	//TODO: handle colon in id
	var ind = $("h4[id^='"+h4_id+"']").length + 1;
	h4_id = h4_id + "_"+ ind;
	h4Array.push(h4_id);
	if(providerId == '30698347' || providerId == '2314635' || providerId == '4142189' || providerId == '30671669' || providerId == '5793'){
		var h4 = $("<h4></h4>").attr({"class":"systemColor2 addBottom", "id":h4_id});
	}else{
		var h4 = $("<h4></h4>").addClass("systemColor2").attr("id", h4_id);
	}
	addScrollDiv(h4);
	
	var contentBlock = $("<div></div>").css("display", "none");
	
    var input_id = id.replace("input_", "item_")+ "_" + ind;
    var input = $("<input/>").attr({"class":"itemCheckBox", "type":"checkbox", "id":input_id});
    contentBlock.append(input);
    
    var span = $("<span id='prodId'></span>").html(prodJson.productname);
    contentBlock.append(span);
    
    var priceId = id.replace("input_", "price_");
	priceId = escapeSpecialCharacters(priceId);
	
	var price = parseFloat($("input#"+priceId).val());
	if(isNaN(price) || price == undefined){
		price = prodJson.recuring;
		price = price.replace("$","");
	}
	price = Math.round(price * 100) / 100;
	
	prodJson.recuring = price;
	
	var installPriceId = id.replace("input_", "installPrice_");
	installPriceId = escapeSpecialCharacters(installPriceId);
	
	var installPrice = parseFloat($("input#"+installPriceId).val());
	if(isNaN(installPrice) || installPrice == undefined){
		installPrice = prodJson.non_recuring;
		installPrice = installPrice.replace("$","");
	}
	installPrice = Math.round(installPrice * 100) / 100;
	
	prodJson.non_recuring = installPrice;

	var units = $("#priceUnits").val();

	var span1 = $("<span></span>").addClass("fRight");;
	if(isNaN(price)){
		span1.append('<span id="price">--</span>');
	}
	else{
		var price_row = $('<span id="price"/>');
		price_row.text("$"+price.toFixed(2));
		span1.append(price_row);
		var install_price_row = $('<span id="instPrice" style="display : none;"/>');
		install_price_row.text(installPrice.toFixed(2));
		var monthly_price_row = $('<span id="monPrice" style="display : none;"/>');
		monthly_price_row.text(price.toFixed(2));
		
		span1.append(install_price_row);
		span1.append(monthly_price_row);
		
	}
	
	var statusSpan = $("<span>").attr("class", "statusToggle");
	statusSpan.click(toggleStatus);
	span1.append(statusSpan);
	contentBlock.append(span1);
	
	var div = $("<div>").addClass('liStatus').css("display","none");
	contentBlock.append(div);
	
	h4.append(contentBlock);
	
	var hidden_row = $("<input/>").attr({"value":providerId, "type":"hidden", "id":"providerId"});
	h4.append(hidden_row);
	
	 addtoShoppingCart(providerId,h4);
}

/*  ATT V6
 * This method use to update ShoppingCart with new CKO lineitem.
 * 
 * */
function upDateShoppingCart(lineItemJSON) {
	
	if(lineItemJSON.status == 'success'){
		$('.scrollH4').remove();
		$('.alignCenter').remove();
		var h4_id = "row_"+lineItemJSON.providerID+"_"+lineItemJSON.productExID;
		var ind = $("h4[id^='" + h4_id + "']").length + 1;
		h4_id = h4_id + "_" + ind;
		var h4 = $("<h4></h4>").addClass("systemColor2").attr("id", h4_id);
		if (providerId == '30698347' 
			    || providerId == '2314635'
				|| providerId == '4142189' 
				|| providerId == '30671669'
				|| providerId == '5793') {
			  h4 = $("<h4></h4>").attr( {
				"class" : "systemColor2 addBottom",
				"id" : h4_id
			});
		}
		var lineItemNumber = $("input#liCount").val();
	    lineItemNumber = 1 + parseFloat(lineItemJSON.lineItemNumber);
	    $("input#liCount").val(lineItemNumber);
		var input_id = "item_"+lineItemJSON.providerID+"_"+lineItemJSON.productExID;
		var contentBlock = $("<div></div>").css("display", "table");
		var spanRight = $("<span></span>").addClass("fRight");;
		var span = $("<span id='prodId'></span>").html(lineItemJSON.productName);
		contentBlock.append(span);
		var span = $("<span id='price'></span>").html("$"+lineItemJSON.recPrice);
		spanRight.append(span);
		var span = $("<span id='instPrice'></span>").css("display", "none").html(lineItemJSON.recNonPrice);
		spanRight.append(span);
		var span = $("<span id='monPrice'></span>").css("display", "none").html(lineItemJSON.recPrice);
		spanRight.append(span);
		var span = $("<span class='statusToggle'></span>");
		spanRight.append(span);
		contentBlock.append(spanRight);
		var statusBlock = $("<div></div>").attr( {
			"class" : "liStatus"
		}).css("display", "none").text(lineItemJSON.lineItemStatus);
		contentBlock.append(statusBlock);
		h4.append(contentBlock);
		var hidden_row = $("<input/>").attr( {
			"value" : lineItemJSON.providerID,
			"type" : "hidden",
			"id" : "providerId"
		});
		h4.append(hidden_row);
		var hidden_row = $("<input/>").attr( {
			"value" : lineItemJSON.lineItemExID,
			"type" : "hidden",
			"id" : "li_externalId_" + lineItemJSON.lineItemExID
		});
		h4.append(hidden_row);
		var hidden_row = $("<input/>").attr( {
			"value" : lineItemJSON.lineItemNumber,
			"type" : "hidden",
			"id" : "li_number_" + lineItemJSON.lineItemNumber
		});
		h4.append(hidden_row);
		var hidden_row = $("<input/>").attr( {
			"value" : "false",
			"type" : "hidden",
			"id" : "li_hasPromotion_" + lineItemJSON.lineItemNumber
		});
		h4.append(hidden_row);
		addtoShoppingCart(providerId,h4);
		//console.log("lineItemJSON.recNonPrice"+lineItemJSON.recNonPrice);
		//console.log("lineItemJSON.recPrice"+lineItemJSON.recPrice);
		//console.log(!isNaN(lineItemJSON.recPrice));
		//console.log(!isNaN(lineItemJSON.recNonPrice));
		if(!isNaN(lineItemJSON.removeLineItemRecPrice)){
			updateMonthlyPrice(parseFloat(lineItemJSON.removeLineItemRecPrice), -1);
		}
		if(!isNaN(lineItemJSON.removeLineItemNonRecPrice)){
			updateInstallationPrice(parseFloat(lineItemJSON.removeLineItemNonRecPrice),-1);
		}
		if(!isNaN(lineItemJSON.recPrice)){
			updateMonthlyPrice(parseFloat(lineItemJSON.recPrice), 1);
		}
		if(!isNaN(lineItemJSON.recNonPrice)){
			updateInstallationPrice(parseFloat(lineItemJSON.recNonPrice),1);
		}
		if(lineItemJSON.removeLineItemExID != undefined){
			$("input#li_externalId_"+lineItemJSON.removeLineItemExID).parent().css('color','red');
		}
		if(lineItemJSON.removeLineItemStatus != undefined){
			$("h4[id^='" + "row_"+lineItemJSON.removeProviderID+"_"+lineItemJSON.removeProductExID + "']").find('.liStatus').text(lineItemJSON.removeLineItemStatus);
		}
		$("#summaryForm").find("span.statusToggle").unbind("click");
		$("#summaryForm").find("span.statusToggle").click(toggleStatus);
		$("div#orderInfoHead").unbind("click");
		$("div#orderInfoHead").click(goToOrderSummary);
		if(lineItemJSON.totalPoints != undefined){
			$("#totalPointsId").text("Points: "+lineItemJSON.totalPoints);
		}
	}else{
		$('.scrollH4').remove();
		$('.alignCenter').remove();
		var hidden_row = $("<span></span>").text('Services Down :Please try again later');
		hidden_row = hidden_row.css("color","#F00");
		var h4 = $("<h4></h4>").addClass("systemColor2");
		h4.append(hidden_row);
		$("div#itemsBlock").find("div.mCSB_container").append(h4);
		$("div#itemsBlock").find("div.mCSB_container").append($("<div></div>").addClass("divider"));
	}
}

/*
 * Adds the Constructed HTMl to the right position in the Cart
 */
function  addtoShoppingCart(providerId,h4){
	
	if(providerId == '27010360'){
		$("div#itemsBlock").find("div.mCSB_container").prepend($("<div></div>").addClass("divider"));
		$("div#itemsBlock").find("div.mCSB_container").prepend(h4);		
	} else {
		var removedH4 = $("div#itemsBlock").find("div.mCSB_container > h4.removedH4");
		var addBottom = $("div#itemsBlock").find("div.mCSB_container > h4.addBottom");
	    if(removedH4.length){
	    	if(providerId != '30698347' && providerId != '2314635' && providerId != '4142189' && providerId != '30671669' && providerId != '5793' && addBottom.length != 0){
	    		$("div#itemsBlock").find("div.mCSB_container > h4.addBottom:eq(0)").before(h4);
				$("div#itemsBlock").find("div.mCSB_container > h4.addBottom:eq(0)").before($("<div></div>").addClass("divider"));
	    	}else{
	    		cablevision = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_5793'][class != 'systemColor2 removedH4']");
	    		directStar = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_2314635'][class != 'systemColor2 removedH4']");
	    		comcast = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']");
	    		dishtransfer = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']");
	    		adt = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']");
	    		
	    		if(providerId == '5793' && (adt.length != 0 || comcast.length != 0|| dishtransfer.length != 0|| directStar.length != 0)){
	    			if(directStar.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_2314635'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_2314635'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}
	    			else if(dishtransfer.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(adt.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(comcast.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else{
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before($("<div></div>").addClass("divider"));
		    		}
	    		}
	    		else if(providerId == '2314635' && (adt.length != 0 || comcast.length != 0|| dishtransfer.length != 0)){
	    			if(dishtransfer.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(adt.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(comcast.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else{
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before($("<div></div>").addClass("divider"));
		    		}
	    		}else if(providerId == '30671669' && (adt.length != 0 || comcast.length != 0)){
	    			if(adt.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(comcast.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else{
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before($("<div></div>").addClass("divider"));
		    		}
	    		}else if(providerId == '4142189' && (comcast.length != 0)){
	    			if(comcast.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else{
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before($("<div></div>").addClass("divider"));
		    		}
	    		}else {
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4.removedH4:eq(0)").before($("<div></div>").addClass("divider"));
		    		}
	    	}
	    } else {
	    	if(providerId != '30698347' && providerId != '2314635' && providerId != '4142189' && providerId != '30671669' && addBottom.length != 0){
	    		$("div#itemsBlock").find("div.mCSB_container > h4.addBottom:eq(0)").before(h4);
				$("div#itemsBlock").find("div.mCSB_container > h4.addBottom:eq(0)").before($("<div></div>").addClass("divider"));
	    	}else{
	    		cablevision = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_5793'][class != 'systemColor2 removedH4']");
	    		directStar = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_2314635'][class != 'systemColor2 removedH4']");
	    		comcast = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']");
	    		dishtransfer = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']");
	    		adt = $("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']");
	    		
	    		if(providerId == '5793' && (adt.length != 0 || comcast.length != 0|| dishtransfer.length != 0|| directStar.length != 0)){
	    			if(directStar.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_2314635'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_2314635'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}
	    			else if(dishtransfer.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(adt.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(comcast.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else{
	    				$("div#itemsBlock").find("div.mCSB_container").append(h4);
						$("div#itemsBlock").find("div.mCSB_container").append($("<div></div>").addClass("divider"));
	    			}
	    		}
	    		else if(providerId == '2314635' && (adt.length != 0 || comcast.length != 0|| dishtransfer.length != 0)){
	    			if(dishtransfer.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30671669'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(adt.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(comcast.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else{
	    				$("div#itemsBlock").find("div.mCSB_container").append(h4);
						$("div#itemsBlock").find("div.mCSB_container").append($("<div></div>").addClass("divider"));
	    			}
	    		}else if(providerId == '30671669' && (adt.length != 0 || comcast.length != 0)){
	    			if(adt.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_4142189'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else if(comcast.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else{
	    				$("div#itemsBlock").find("div.mCSB_container").append(h4);
						$("div#itemsBlock").find("div.mCSB_container").append($("<div></div>").addClass("divider"));
	    			}
	    		}else if(providerId == '4142189' && (comcast.length != 0)){
	    			if(comcast.length != 0){
	    				$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before(h4);
		    			$("div#itemsBlock").find("div.mCSB_container > h4[id^='row_30698347'][class != 'systemColor2 removedH4']:eq(0)").before($("<div></div>").addClass("divider"));
	    			}else{
	    				$("div#itemsBlock").find("div.mCSB_container").append(h4);
						$("div#itemsBlock").find("div.mCSB_container").append($("<div></div>").addClass("divider"));
	    			}
	    		}else {
	    				$("div#itemsBlock").find("div.mCSB_container").append(h4);
						$("div#itemsBlock").find("div.mCSB_container").append($("<div></div>").addClass("divider"));
	    		}
	    	}
	    }
	}
}
/*
 * Updates Total Monthly Price(if Status is 1 it adds the prices to cart & 
 * 												    if status is -1 it removes the prices from Cart)
 */
var updateMonthlyPrice = function(price, status){
	var units = $("#priceUnits").val();
	var totalPrice = parseFloat($("#totalPrice").val());
	if(status === 1){
		totalPrice = totalPrice + price;
	} else {
		totalPrice = totalPrice - price;
	}
	totalPrice = Math.round(totalPrice * 100) / 100;
	$("#totalPrice").val(totalPrice.toFixed(2));
	$("span#itemsTotal").html("$"+totalPrice.toFixed(2));
}
/*
 * Updates Total Installation Price(if Status is 1 it adds the prices to cart & 
 * 															  if status is -1 it removes the prices from Cart) 
 */
var updateInstallationPrice = function(price, status){
	
	var units = $("#priceUnits").val();
	var totalPrice = parseFloat($("#installPrice").val());
	var isComCastPromo = false;
	var atLeastOneComCastPromo = false;
	
		//If Comcast Product
		if(price == -1 || price == -2){
			if(totalPrice == 0){
			isComCastPromo = true;
			}
			price = 0;
			atLeastOneComCastPromo = true;
		}
		
		if(isComCastPromo){
			if(status === 1){
				var negativePriceSpan = $("<span></span>").attr({"class":"negativePrice"});
				negativePriceSpan.append("Varies By Market");
				$("#atLeastOneComCastPromo").val(atLeastOneComCastPromo);
				$("span#itemsTotal1").html(negativePriceSpan);
			}else{
				totalPrice = totalPrice - price;
				$("#atLeastOneComCastPromo").val('false');
				$("#installPrice").val(totalPrice.toFixed(2));
				$("span#itemsTotal1").html("$"+totalPrice.toFixed(2));
			}
			
		}else{
			if(status === 1){
				totalPrice = totalPrice + price;
			} else {
				totalPrice = totalPrice - price;
			}
			totalPrice = Math.round(totalPrice * 100) / 100;
			if(totalPrice == 0){
				var checkComcastPromo = $("#atLeastOneComCastPromo").val();
				if(checkComcastPromo == 'true'){
					var negativePriceSpan = $("<span></span>").attr({"class":"negativePrice"});
					negativePriceSpan.append("Varies By Market");
					$("#installPrice").val(totalPrice.toFixed(2));
					$("span#itemsTotal1").html(negativePriceSpan);
				}else{
					$("#installPrice").val(totalPrice.toFixed(2));
					$("span#itemsTotal1").html("$"+totalPrice.toFixed(2));
				}
			}else{
				$("#installPrice").val(totalPrice.toFixed(2));
				$("span#itemsTotal1").html("$"+totalPrice.toFixed(2));
			}
		}
}

/*
 * Appends the adding Image(blue color) to the HTML
 */
var addScrollDiv = function(h4){
	var path =$("input#contextPath").val();
	var scrollDiv = $("<div></div>").addClass("alignCenter");
	var img = $("<img/>").attr("src", path+"/images/scroll.gif");
	scrollDiv.append(img);
	h4.prepend(scrollDiv);
}
/*
 * Appends the Removing Image(red color) to the HTML
 */
var removeScrollDiv = function(h4){
	var path =$("input#contextPath").val();
	var scrollDiv = $("<div></div>").addClass("alignCenter");
	var img = $("<img/>").attr("src", path+"/images/delete-ajax-loader.gif");
	scrollDiv.append(img);
	h4.prepend(scrollDiv);
}
/*
 * Disables all the buttons and events
 */
function disablePage(){
	try{
	$("#AddToOrderBtn2").attr("disabled",true);
	$("#addToOrderAndCKO").attr("disabled",true);
	$("#goToOffer").attr("disabled",true);
	$("#UtiltyOffer").attr("disabled",true);
	$("#backToProducts").attr("disabled",true);
	$("#backToDiscoveryId").attr("disabled",true);
	$("#backToRecommendations").attr("disabled",true);
	$("#summaryForm").find("span").unbind("click");
	$("#summaryForm").find("input:button").attr("disabled", "disabled");
	$(".cusinfo_container").find("input:button").attr("disabled", "disabled");
	$("div#orderInfoHead").unbind("click");
	$("ul.productsNav").find("a").attr("class", "disabled");
	}catch(e){alert(e);}
}
/*
 * Enables all the buttons and events
 */
function enablePage(){
	try{
	$("#AddToOrderBtn2").removeAttr("disabled");
	$("#addToOrderAndCKO").removeAttr("disabled");
	$("#goToOffer").removeAttr("disabled");
	$("#UtiltyOffer").removeAttr("disabled");
	$("#backToProducts").removeAttr("disabled");
	$("#backToDiscoveryId").removeAttr("disabled");
	$("#backToRecommendations").removeAttr("disabled");
	
	//unbind click events
	$("#summaryForm").find("span.addAgain").unbind("click");
	$("#summaryForm").find("#CKOItems").unbind("click");
	$("#summaryForm").find("span.statusToggle").unbind("click");
	$("div#orderInfoHead").unbind("click");
	
	$("#summaryForm").find("span.addAgain").click(reAdd);
	$("#summaryForm").find("#CKOItems").click(CKOToOrderSummary);
	$("#summaryForm").find("span.statusToggle").click(toggleStatus);
	$("#summaryForm").find("input:button").removeAttr("disabled");
	$(".cusinfo_container").find("input:button").removeAttr("disabled");
	$("div#orderInfoHead").click(goToOrderSummary);
	$("ul.productsNav").find("a").attr("class", "enabled");
	}catch(e){alert(e);}
}
/*
 * Escapes the special characters for jquery selectors
 */
function escapeSpecialCharacters(id){
	return id.replace(/([ #;&,.+*~\':"!^$[\]()=>|\/])/g,'\\$1');
}

function getContentForSalesTips(){
	console.time("Sales=")
     $(".leftbottompop.salesTipId").each(function( index, value) {
    	 $(this).find(".SalesTipContent").remove();
    	var SalesTipContent = $("<div></div>").addClass("SalesTipContent");
    	var ids = $(this).find('.tip').attr('id').split("_");
    	 var id1 = $("#salesTipsDataContent #"+ids[1]).html();
    	 var id2 = $("#salesTipsDataContent #"+ids[2]).html();
    	 SalesTipContent.append($("<div></div>").html(id2));
    	 SalesTipContent.append($("<div></div>").html(id1));
    	 $(this).append(SalesTipContent);
     });
     console.timeEnd("Sales=")
}

function salesTips(){
	getContentForSalesTips();
	  
	  $(".autottips").mouseover(function(){
	      var x = $(this).parent(".tblink").offset();
	      if(x.top>450)
	      {
	        $(this).parent(".tblink").children(".leftbottompop").css("display", "block");
	        $(this).parent(".tblink").children(".leftbottompop").removeClass("btmpos");
	        $(this).parent(".tblink").children(".leftbottompop").addClass("toppos");
	      }
	      else
	      {
	        $(this).parent(".tblink").children(".leftbottompop").css("display", "block");
	        $(this).parent(".tblink").children(".leftbottompop").removeClass("toppos");
	        $(this).parent(".tblink").children(".leftbottompop").addClass("btmpos");
	      }
	    });


	     $(".autottips").mouseout(function(){
	      $(this).parent(".tblink").children(".leftbottompop").css("display", "none");
	    });

	$(".totalpop").mouseover(function(){
	      var x = $(this).offset();
	      if(x.top>450)
	      {
	        $(this).children(".rightbottompop").css("display", "block");
	        $(this).children(".rightbottompop").removeClass("btmpp");
	        $(this).children(".rightbottompop").addClass("toppp");
	      }
	      else
	      {
	        $(this).children(".rightbottompop").css("display", "block");
	        $(this).children(".rightbottompop").removeClass("toppp");
	      $(this).children(".rightbottompop").addClass("btmpp");
	      }
	    });

	 $(".totalpop").mouseout(function(){
	      $(this).children(".rightbottompop").css("display", "none");
	    });
	  
}

function priceTable(){
	
	$(".bundlePriceGrid .gridIcon, .regProductChannelLineup .gridIcon").mouseover(function(){

		var elem = $(this);
  		var offset_top = elem.offset().top;
  		if(offset_top <= 350)
  		{
  			$(".ratepopup").hide();
			$(this).siblings(".ratepopup").show();
			$(this).siblings(".ratepopup").removeClass("ratepopup_bottom ratepopup_middle ratepopup_top");
			$(this).siblings(".ratepopup").addClass("ratepopup_bottom");
  		}
  		if(offset_top >= 351 && offset_top <= 500)
  		{
  			$(".ratepopup").hide();
			$(this).siblings(".ratepopup").show();
			$(this).siblings(".ratepopup").removeClass("ratepopup_bottom ratepopup_middle ratepopup_top");
			$(this).siblings(".ratepopup").addClass("ratepopup_middle");
  		}
  		if(offset_top >= 501)
  		{
  			$(".ratepopup").hide();
			$(this).siblings(".ratepopup").show();
			$(this).siblings(".ratepopup").removeClass("ratepopup_bottom ratepopup_middle ratepopup_top");
			$(this).siblings(".ratepopup").addClass("ratepopup_top");
  		}
	});
	$(".bundlePriceGrid .gridIcon, .regProductChannelLineup .gridIcon").mouseout(function(){
		$(".ratepopup").hide();
	});
	$(".ratepopup").mouseover(function(){
		$(this).show();
	});
	$(".ratepopup").mouseout(function(){
		$(".ratepopup").hide();
	});
}
function singleProductDataBuild(productVO,indexValue){
	$(".singleProductContent"+indexValue+""+" .promoprc.nonDisplayIcon").text("");
	clearSingleContent(indexValue)
	 	     $(".singleProductContent"+indexValue+""+" .singleHiddenJson").val(productVO.hiddenProductJSON).attr("id","hidden_product_"+productVO.providerExtId+"_"+productVO.productExID);
		   $(".singleProductContent"+indexValue+""+" .singleInstallPrice").val(productVO.baseNonRecurringPrice).attr("id","installPrice_"+productVO.providerExtId+"_"+productVO.productExID);
		   var title = "Points :"+showPoints(productVO.productPointDisplay)+", Product External Id :"+productVO.productExID
		   var title1 =  productVO.productPointDisplay+"|"+productVO.providerExtId
		  
		   $(".singleProductContent"+indexValue+" #productImageId").attr({"alt":productVO.providerExtId,"src":providerIMGLocation+productVO.imageID+".jpg","title":title,"title1":title1});
		   //console.log("key1 :: ");  
		   var bprice = 0.0
		   $.each(productVO.productIconList,function(key,val){
			  // console.log("key :: "+key);  
			   var path = $("input#contextPath").val();
			   var src = path+"/images/images_new/"+val+".png"
			   var img = $("<img/>").attr({"src":src})
			   $(".singleProductContent"+indexValue+""+" .categoryImages").append(img);
			});
		   iconListValues(productVO.productIconList,indexValue);
		   
		 var aidvalue = productVO.providerExtId+"_"+productVO.productExID+","+productVO.productName+","+productVO.providerExtId+","+productVO.productType;
		 //console.log("aidvalue"+aidvalue)
	  $(".singleProductContent"+indexValue+""+" #aidvalue").val(aidvalue);  
		 $(".singleProductContent"+indexValue+""+" .ViewChannelsBtn").attr("id","product_"+productVO.providerExtId+"_"+productVO.productExID);
		 if(productVO.baseRecurringPrice != undefined ){
			 bprice = productVO.baseRecurringPrice.toFixed(2)
		 }
		   $(".singleProductContent"+indexValue+""+" .nonDisplayIconBP").text("$"+bprice);
	    $(".singleProductContent"+indexValue+""+" .displayIconDP").text("$"+productVO.displayBasePrice);
	    $(".singleProductContent"+indexValue+""+" .baseRecPrice").val(productVO.baseRecurringPrice).attr("id","price_"+productVO.providerExtId+"_"+productVO.productExID);
	    $(".singleProductContent"+indexValue+""+" .usageRateValue").text("$"+productVO.usageRate)   
	    $(".singleProductContent"+indexValue+""+" .usageRateUnit").text(productVO.unitName)
	     $(".singleProductContent"+indexValue+""+" .promoDicReg").text(productVO.productDescription);
	    if(productVO.promoPrice != undefined){
	 	   $(".singleProductContent"+indexValue+""+" .promoprc.nonDisplayIcon").text("$"+productVO.promoPrice.toFixed(2))
	    }
	    if(productVO.displayPromotionPrice != undefined){
	 	   $(".singleProductContent"+indexValue+""+" .promoprc>.dispalyPr").text("$"+productVO.displayPromotionPrice)
	    }
	    
	    /*if(productVO.channelsCount != undefined){
		 	   $(".singleProductContent"+indexValue+""+" .ViewChannelsBtn").val(productVO.channelsCount);
		    }*/
		    
	    var list = JSON.stringify(productVO.productIconList).split(",");
		   if(list.length==1 && categoryName != "PP"){
			   $(".RGUs").addClass("singleRgu");
		    }else{
		    	$(".RGUs").removeClass("singleRgu");
		    }
		   //console.log("isHNProductShow *********** "+isHNProductShow)
		   //console.log("hideHughesNet *********** "+hideHughesNet)
		  /* if(hideHughesNet != undefined && hideHughesNet !="" && hideHughesNet && productVO.providerExtId != undefined && productVO.providerExtId != "" && (productVO.providerExtId == "15500621" || productVO.providerExtId == "15500581")){
			   if(!isHNProductShow){
			          $("."+productVO.providerExtId).hide();
			          if(productVO.providerExtId == "15500621" || productVO.providerExtId == "15500581"){
			        	  $("."+productVO.providerExtId).hide();
			          }else{
			        	  $("."+productVO.providerExtId).show();
			          }
			      }else if(isHNProductShow){
			          $("."+productVO.providerExtId).show();
			      }
		   }else{
			   console.log("providerExtId *********** "+productVO.providerExtId)
			   $("."+productVO.providerExtId).show();
		   }*/
} 
function bundleProductDataBuild(productVO,indexValue){
	// $(".bundleProductContent"+indexValue+""+" #SalesTipData").remove();
	clearBundleContent(indexValue)
	   var productJson = JSON.parse(productVO.pairedProduct);
	   var providerExtIdS = "";
			if(productVO.providerExtId != undefined && productVO.providerExtId !="" && productVO.providerExtId != null){
			   providerExtIdS = providerExtIdS +"_"+productVO.imageID +"_"+productJson.imageID;
		   }
			var pairedProductObject = productJson;
			var pairedProductProductIconList = productJson.productIconList;
			var pairedProductName = productJson.productName;
			var pairedProductImageID = productJson.imageID;
			var pairedProductProductPointDisplay = productJson.productPointDisplay;
			var pairedProductProductExID = productJson.productExID;
			var pairedProductDescription = productJson.productDescription;
			var condition = "";
			if(productVO.condition != undefined && productVO.condition !="" && productVO.condition != null){
	   			   condition = productVO.condition;
	   		   }else{
	   			  condition = "N/A";
	   		   }
			 $(".bundleProductContent"+indexValue+""+" .sTips").attr("id",providerExtIdS);
	   $(".bundleProductContent"+indexValue+""+" #pairedProductImageID").attr({"src":"https://s3.amazonaws.com/AL-content/common/provider/logos/"+pairedProductImageID+".jpg","title":"Points :"+pairedProductProductPointDisplay+", Product External Id :"+pairedProductProductExID})
	   $(".bundleProductContent"+indexValue+""+" #pairedProductName").text(pairedProductName);
	   if(pairedProductDescription != undefined){
		   $(".bundleProductContent"+indexValue+""+" #pairedProductDescription").text(truncateText(pairedProductDescription,90));
	   }
    $(".bundleProductContent"+indexValue+""+" #imageID").attr({"src":"https://s3.amazonaws.com/AL-content/common/provider/logos/"+productVO.imageID+".jpg","title":"Points : "+productVO.productPointDisplay+",Product External Id :"+productVO.productExID});
    $(".bundleProductContent"+indexValue+""+" #PProviderName").text(pairedProductObject.providerName);
		  $(".bundleProductContent"+indexValue+""+" #PairedpromotionDescription").text(pairedProductObject.promotionDescription);
		    $(".bundleProductContent"+indexValue+""+" #tAConditions").text("Terms and Conditions: "+condition);
		      $(".bundleProductContent"+indexValue+""+" #popupheadcont").text(productVO.providerName);
		      $(".bundleProductContent"+indexValue+""+" #PpromotionDescription").text(productVO.promotionDescription);
		      $(".bundleProductContent"+indexValue+""+" #PTAConditions").text("Terms and Conditions: "+condition);
		      $(".bundleProductContent"+indexValue+""+" .bundleChannel").attr("product_"+productVO.providerExtId+"_"+productVO.productExID);
			  $(".bundleProductContent"+indexValue+""+" .bundlePopupheadcont").text(productVO.totalPoints);
           $(".bundleProductContent"+indexValue+""+" .bundleThdpOne").text(pairedProductObject.providerName);
           $(".bundleProductContent"+indexValue+""+" .bundleThdptDisplaypOne").text(pairedProductObject.productPointDisplay);
           $(".bundleProductContent"+indexValue+""+" .bundleThdpTwo").text(productVO.providerName);
           $(".bundleProductContent"+indexValue+""+" .bundleThdptDisplayTwo").text(productVO.productPointDisplay);
          
}

function buildPriceGrid(priceJson,productIndex,providerExId,totalHeadings,verizonBasePriceContent,verizonPricingGridContent){
	if(priceJson != undefined){
		//console.log("synt ::"+JSON.stringify(priceJson));
		   $(".sythaticPriceGrid"+productIndex+" tr").remove();
		 	   $.each(priceJson.PRICE_JSON,function(index,value){
		 	   var tr = $("<tr></tr>");
		 	  var NoOfTvtd =  $("<td></td>").attr("class","nopadng");
			   var span0 = $("<span></span>").attr("class","tbl_bigtxt").text(value.NoOfTv);
			   NoOfTvtd.append(span0)
		 	   tr.append(NoOfTvtd);
		 	   var header1 =  $("<td></td>");
		 	   var span1 = $("<span></span>").attr("class","dipsblck1").text(value.header1);
		 	   var span2 = $("<span></span>").attr("class","dipsblck").text(value.header2);
		 	   header1.append(span1);
		 	   header1.append(span2);
		 	   tr.append(header1);
		 	if(value.NO_DVR_MTH != undefined){
		 	   var no_dvr_mth =  $("<td></td>").attr("class","nopadng");
		 	   var span3 = $("<span></span>").attr("class","dipsblck1").text(value.NO_DVR_MTH);
		 	   var span4 = $("<span></span>").attr("class","dipsblck");
		 	   if(value.NO_DVR_A12M != undefined){
		 		 span4 = span4.text(value.NO_DVR_A12M);
		 	   }else if(value.NO_DVR_A24M != undefined){
		 		span4 = span4.text(value.NO_DVR_A24M);
		 	   }
		 	   no_dvr_mth.append(span3);
		 	   no_dvr_mth.append(span4);	   
		 	   tr.append(no_dvr_mth);
		 	}

		 	if(value.TOTAL_MTH != undefined){
		 	   var TOTAL_MTH =  $("<td></td>").attr("class","nopadng");
		 	   var span5 = $("<span></span>").attr("class","dipsblck1").text(value.TOTAL_MTH);
		 	   var span6 = $("<span></span>").attr("class","dipsblck");
		 	   if(value.TOTAL_A12M != undefined){
		 		 span6 = span6.text(value.TOTAL_A12M);
		 		
		 	   }else if(value.TOTAL_A24M != undefined){
		 		span6 = span6.text(value.TOTAL_A24M);
		 	   }
		 	   TOTAL_MTH.append(span5);
		 	    TOTAL_MTH.append(span6);
		 	   tr.append(TOTAL_MTH);
		 	}

		 	if(value.EN_DVR_MTH != undefined){
		 	   var EN_DVR_MTH =  $("<td></td>").attr("class","nopadng");
		 	   var span9 = $("<span></span>").attr("class","dipsblck1").text(value.EN_DVR_MTH);
		 	   var span10 = $("<span></span>").attr("class","dipsblck");
		 	     if(value.EN_DVR_A12M != undefined){
		 		 span10 = span10.text(value.EN_DVR_A12M);
		 		
		 	   }else if(value.EN_DVR_A24M != undefined){
		 		span10 = span10.text(value.EN_DVR_A24M);
		 	   }
		 	   EN_DVR_MTH.append(span9);
		 	   EN_DVR_MTH.append(span10);
		 	   tr.append(EN_DVR_MTH);
		 	}

		 	if(value.PR_DVR_MTH != undefined){
		 	   var PR_DVR_MTH =  $("<td></td>").attr("class","nopadng");
		 	   var span7 = $("<span></span>").attr("class","dipsblck1").text(value.PR_DVR_MTH);
		 	   var span8 = $("<span></span>").attr("class","dipsblck");
		 	    if(value.PR_DVR_A12M != undefined){
		 		 span8 = span8.text(value.PR_DVR_A12M);
		 		
		 	   }else if(value.PR_DVR_A24M != undefined){
		 		span8 = span8.text(value.PR_DVR_A24M);
		 	   }
		 	   PR_DVR_MTH.append(span7);
		 	    PR_DVR_MTH.append(span8);
		 	   tr.append(PR_DVR_MTH);
		 	}

		 		$(".sythaticPriceGrid"+productIndex).append(tr);
		 	   

		 	   });
		 	   if(verizonBasePriceContent != undefined && verizonPricingGridContent != undefined){
		 		  totalHeadings = totalHeadings + 2;
			 		 var discountText="<tr><td class='DiscountTextMessage' colspan='"+totalHeadings+"'>"+verizonBasePriceContent+"<br>"+verizonPricingGridContent+"</td></tr>"
				 	 $(".sythaticPriceGrid"+productIndex).append(discountText);
		 	   }
	}
}


function iconListValues(IconObj,indexValue){
	 var displayStyleSingle = "";
	var displayStyleTriple="";
	var rguWidth = 45;
	if(IconObj != undefined){
		var list = JSON.stringify(IconObj).split(",")
		if(list.length ==1 && JSON.stringify(IconObj).indexOf("tv") != -1){
			displayStyleSingle = "displayStyleSingle";
			$(".product_marker").css({'margin-right':'47px', 'float': 'right'});
		}
		if(list.length == 2){
			 $(".product_marker").css('margin-right',"23px");
		}
		
		if(list.length >1 && JSON.stringify(IconObj).indexOf("tv") != -1){
			 displayStyleTriple = "displayStyleTriple";
		}
		
		/*if(categoryName != "PP"){
			if(list.length == 2){
				rguWidth=55;
				$(".RGUs").css("width","50px");
			}else{
				$(".RGUs").css("width","43px")
			}
			rguWidth=rguWidth*list.length;
			$(".RGUsBlock").css("width",rguWidth+"px");
		}*/
		
	}
	
	 $(".singleProductContent"+indexValue+" #gridImg").attr("class","gridIcon nobtmbdr "+displayStyleSingle+" "+displayStyleTriple);
}
function showPoints(pointData){
	   var pointsDisplay = "";
  	if(pointData == "NA" || pointData == "0.0" ){
  		pointsDisplay = "See Intranet";
  	}else{
  		pointsDisplay = pointData;
  	}
  	 //console.log("pointData :: "+pointsDisplay);  
  	return pointsDisplay;
  }
function truncateText(text,breakPoint){
	var shortText="" 
	if(text!=undefined){
		var wordsCount =text.length; 
			if(wordsCount > breakPoint){
				shortText = text.slice(0, breakPoint) + " ...";
			}else{
				shortText = text;
			}
	}
	return shortText;
}


function clearSingleContent(indexValue){
	$(".singleProductContent"+indexValue+""+" .promoprc.nonDisplayIcon").text("");
	$(".singleProductContent"+indexValue+""+" .singleHiddenJson").val("")
	   $(".singleProductContent"+indexValue+""+" .singleInstallPrice").val("");
	   $(".singleProductContent"+indexValue+" #productImageId").attr({"alt":"","src":"","title":"","title1":""});
	   $(".singleProductContent"+indexValue+""+" #aidvalue").val("");  
	   $(".singleProductContent"+indexValue+""+" .ViewChannelsBtn").attr("id","");
	   $(".singleProductContent"+indexValue+""+" .nonDisplayIconBP").text("");
 $(".singleProductContent"+indexValue+""+" .displayIconDP").text("");
 $(".singleProductContent"+indexValue+""+" .baseRecPrice").val("");
 $(".singleProductContent"+indexValue+""+" .usageRateValue").text("")   
 $(".singleProductContent"+indexValue+""+" .usageRateUnit").text("")
  $(".singleProductContent"+indexValue+""+" .promoDicReg").text("");
	   $(".singleProductContent"+indexValue+""+" .promoprc.nonDisplayIcon").text("")
	   $(".singleProductContent"+indexValue+""+" .promoprc>.dispalyPr").text("")
}


function clearBundleContent(indexValue){
	$(".bundleProductContent"+indexValue+""+" .sTips").attr("id","");
	   $(".bundleProductContent"+indexValue+""+" #pairedProductImageID").attr({"src":"","title":""})
	   $(".bundleProductContent"+indexValue+""+" #pairedProductName").text("");
		   $(".bundleProductContent"+indexValue+""+" #pairedProductDescription").text("");
$(".bundleProductContent"+indexValue+""+" #imageID").attr({"src":""});
$(".bundleProductContent"+indexValue+""+" #PProviderName").text("");
		  $(".bundleProductContent"+indexValue+""+" #PairedpromotionDescription").text("");
		    $(".bundleProductContent"+indexValue+""+" #tAConditions").text("");
		      $(".bundleProductContent"+indexValue+""+" #popupheadcont").text("");
		      $(".bundleProductContent"+indexValue+""+" #PpromotionDescription").text("");
		      $(".bundleProductContent"+indexValue+""+" #PTAConditions").text("");
		      $(".bundleProductContent"+indexValue+""+" .bundleChannel").attr("");
			  $(".bundleProductContent"+indexValue+""+" .bundlePopupheadcont").text("");
      $(".bundleProductContent"+indexValue+""+" .bundleThdpOne").text("");
      $(".bundleProductContent"+indexValue+""+" .bundleThdptDisplaypOne").text("");
      $(".bundleProductContent"+indexValue+""+" .bundleThdpTwo").text("");
      $(".bundleProductContent"+indexValue+""+" .bundleThdptDisplayTwo").text("");
	$(".bundleProductContent"+indexValue+""+" .sTips").attr("id","");
}

function getTitle(category){
	  var title = category;
	  if(category == "PP"){
		  title = "Recommendations";
	  }else if(category == "NATURALGAS"){
		  title = "Natural Gas";
	  }else if(category == "WASTEREMOVAL"){
		  title = "Waste Removal"
	  }else if(category == "MIXEDBUNDLES"){
		  title = "Mixed Bundles"
	  }else if(category == "HOMESECURITY"){
		  title = "Home Security"
	  }else if(category == "DOUBLE_PLAY_VI"){
		  title = "Video / Internet"
	  }else if(category == "DOUBLE_PLAY_PV"){
		  title = "Phone / Video"
	  }else if(category == "DOUBLE_PLAY_PI"){
		  title = "Phone / Internet"
	  }else{
		  title = category.toLowerCase().replace(/_/g, ' ')
	  }
	  return title;
}

