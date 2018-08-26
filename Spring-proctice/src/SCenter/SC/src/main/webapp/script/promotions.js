addFeturesAndPromotions = function(){
	try{
		if(!isValidSubmit())
		{
			return false;
		}

	var js = $("#featuresJson").val();
	var prodJson = JSON.parse(js);
	var h4Array= [];
	var selectedArray = {lineItems: []};
	var isAtleastOnePromotionChecked = false;
	var isAtleastOneCheckBoxIsAvailable = false;
	var providerId = $("#providerId").val();
	disablePage();
	
	 $('#pdet_promos_content input[type=checkbox]').each(function () {
		 isAtleastOneCheckBoxIsAvailable = true;
		 if (this.checked) {
			 isAtleastOnePromotionChecked = true;
			 return false;
		 }
	 });
	 //TO select default promotion for CL or RTS:COX 
	 if(providerId == "32416075" || providerId == "15499341")
	 {
		
		 isAtleastOnePromotionChecked = true;
	 }

	 var promotionJsonArray = [];
	 var isItemInCart = false;
	 var promotionExternalId;
	 selectedArray.lineItems.push(JSON.stringify(prodJson));
	 
	 var id = $("input#prodId").val();
	 h4_id = id.replace("product_", "row_");
	 var h4_inCart = h4_id.replace(/\:/g,"\\:");
	 
	 //we are checking the selected product externalId is exact match with product externalId in cart. 
	 var cartProductExtId = h4_inCart;
	 $("div#itemsBlock").find("div.mCSB_container > h4[id^='"+h4_inCart+"'][class != 'systemColor2 removedH4']") .each(function(){
		 var h4_id_inCart = this.id;
		 if(h4_id_inCart.indexOf(h4_inCart)>-1){
			 var indicator = h4_id_inCart.replace(h4_inCart+"_","");
			 if(!isNaN(indicator)){
				 cartProductExtId = h4_id_inCart;
			 }
		 }
	 });
	 
	//Checking whether the product in cart is the selected product or Not
	 var inCart = $("div#itemsBlock").find("div.mCSB_container > h4[id='"+cartProductExtId+"'][class != 'systemColor2 removedH4']");
	 var hasPromotion = inCart.find('input[id^="li_hasPromotion_"]').val();
	 
	 if(isdefaultPromtions(providerId)){
		 $('.Promotions').each(function(){
			 promotionExternalId = this.value;
			 
			 var promotionJson = {};
			 //Constructing Promotion JSON
			 promotionJson = JSON.parse(promotionExternalId);
			 promotionJson.partnerExternalId = prodJson.partnerExternalId;
			 promotionJson.providerSourceBaseType = prodJson.providerSourceBaseType;
			 promotionJson.providerName = prodJson.providerName;
			 
			 if(!isEmpty(promotionJson)){
					selectedArray.lineItems.push(JSON.stringify(promotionJson));	
			 }
		 });
	 }else{
		 if(isAtleastOnePromotionChecked){
				$('#pdet_promos_content input[type=checkbox]').each(function () {
					if (this.checked) {
						if(hasPromotion != 'true')
						{
							 promotionExternalId = this.value;
							 
							 var promotionJson = {};
							 //Constructing Promotion JSON
							 promotionJson = JSON.parse(promotionExternalId);
							 promotionJson.partnerExternalId = prodJson.partnerExternalId;
							 promotionJson.providerSourceBaseType = prodJson.providerSourceBaseType;
							 promotionJson.providerName = prodJson.providerName;
							
							 if((inCart.length != 0)){
								 isItemInCart = true;
								 //Adding appliesTO LineItemNumber
								 var input = inCart.find('input[id^="li_number_"]');
								 promotionJson.appliesTo = input.val();
							   	 //updating Features for the product in cart
							   	 var input1 = inCart.find('input[id^="li_externalId_"]');
							   	 var lineItemId = input1.val();
							   	 promotionJsonArray.push(JSON.stringify(promotionJson));
							}else{
								if(!isEmpty(promotionJson))
									selectedArray.lineItems.push(JSON.stringify(promotionJson));	 
								}
						 	}
						}
					 });
					 //TO select default promotion for CL or RTS:COX
					 if(providerId == "32416075" || providerId == "15499341"){
						 $('#pdet_promos_content input[type=hidden]').each(function () {
							 
								 promotionExternalId = this.value;
								 var promotionJson = {};
								 //Constructing Promotion JSON
								 promotionJson = JSON.parse(promotionExternalId);
								 promotionJson.partnerExternalId = prodJson.partnerExternalId;
								 promotionJson.providerSourceBaseType = prodJson.providerSourceBaseType;
								 promotionJson.providerName = prodJson.providerName;
								
								 if((inCart.length != 0)){
									 isItemInCart = true;
									 //Adding appliesTO LineItemNumber
									 var input = inCart.find('input[id^="li_number_"]');
									 promotionJson.appliesTo = input.val();
								   	 //updating Features for the product in cart
								   	 var input1 = inCart.find('input[id^="li_externalId_"]');
								   	 var lineItemId = input1.val();
								   	 promotionJsonArray.push(JSON.stringify(promotionJson));
								}else{
									if(!isEmpty(promotionJson))
										selectedArray.lineItems.push(JSON.stringify(promotionJson));	 
								}
								 
								 return;
						 });
					 }

			 }else{
				 if((inCart.length != 0)){
					 if(!isAtleastOnePromotionChecked){
						 //No Change
						 isItemInCart = false;
					 }
				 }
			 }
	 }
	
	if(hasPromotion == 'true'){
		isItemInCart = false;
	}
	    if(isItemInCart){
	    	
	    	
	    	addScrollDiv(inCart);
		   	inCart.find('div:eq(1)').css("display", "none");
		   	
		   	var orderId = $("input#orderId").val();
		   	var updatePromotionJson = {"order" :{"lineItems":promotionJsonArray ,"externalId":orderId}};

		   	var path =$("input#contextPath").val();
			var url = path+"/salescenter/add/lineItemPromotion";
			var data = {};
			data["updatePromotionJson"] = JSON.stringify(updatePromotionJson);
			try{ 
				$.ajax({
					type: 'POST',
					url: url,
					data: data,
					success: onUpdateLineItemPromotionComplete
				});
			} catch(e){
				alert(e);
			}
	    }else{
	   		//selected product is new product 
	   	 	//Adding to Cart
			//Removing Cart Errors
		   	$("h4#cartError").next().remove();
		   	$("h4#cartError").remove();
						
			//Constructing HTML for the Cart
			id = id.replace("product_", "input_");
			buildHtml(id,prodJson,h4Array);
			
			//AJAX Call To add LineItem to Order
			var path =$("input#contextPath").val();
			var url = path+"/salescenter/recommendations/AddtoOrder";
			savePageHtml(true, "");
			var data = {};
			data["productData"] = JSON.stringify(selectedArray);
			data["orderId"] = $("input#orderId").val();
			data["offerType"] = $("input#productOfferType").val();
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
	    }
	}catch(e){
		alert(e);
	}
}


function isValidSubmit()
{
	var dependencyPromoDescript = "";
	var promoDescript = "";
	var isValidPromotionSelection = true;
	$("input[id*='_PROMOTION']:checked").each(function(){
		
		var val = $(this).val();
		var json = JSON.parse(val);
		 
		if('includesPromotions' in json) 
		{
			var includesPromotions = json.includesPromotions;
			promoDescript = json.shortDescription;
			var includePromos = includesPromotions.split(',');
			$.each(includePromos, function(i, value) 
			{
				if(!($("input[name='"+value+"']").is(':checked')))
				{
					isValidPromotionSelection = false;
					var dependencyObj = JSON.parse($("input[name='"+value+"']").val());
					dependencyPromoDescript = dependencyObj.shortDescription;
				}
			});
		}

	});

	if( !isValidPromotionSelection )
	{
		var alertContent = "Please Select "+dependencyPromoDescript+" promotion OR Unselect "+promoDescript+" promotion.";
		$.promotionAlert(alertContent, "Alert", 200, "auto");
		return false;
	}
	return true;
}



function isdefaultPromtions(providerId){
	if(providerId == '26069940'){
		return true;
	}
	return false;
}
function isEmpty(promotionJson){
	for(var key in promotionJson){
		return false;
	}
	return true;
}

var onUpdateLineItemPromotionComplete = function(data){
	data = JSON.parse(data);
	enablePage();
	if(data.stat){
		var liId = data.liId;
		var h4 = $("input#li_externalId_"+liId).parent();
		var baseRecurringPrice = parseFloat(data.baseRecurringPrice);
		var baseNonRecurringPrice = parseFloat(data.baseNonRecurringPrice);
		var span_fright = h4.find('span.fRight');
		var price = span_fright.find("span#price").text();
		var recurringPrice = span_fright.find("span#instPrice").text();
		
		price = price.replace("$", "");
		price = parseFloat(price);
		recurringPrice = recurringPrice.replace("$", "");
		recurringPrice = parseFloat(recurringPrice);
		
		if(!isNaN(price) && !isNaN(recurringPrice)){
			updateMonthlyPrice(price, -1);
			updateInstallationPrice(recurringPrice , -1)
		}
		
		span_fright.find("span#price").html("$"+baseRecurringPrice.toFixed(2));
		span_fright.find("span#monPrice").html("$"+baseRecurringPrice.toFixed(2));
		span_fright.find("span#instPrice").html("$"+baseNonRecurringPrice.toFixed(2));
		h4.find("input#li_hasPromotion_"+liId).val(data.hasPromotion);
		
		if(!isNaN(baseRecurringPrice) && !isNaN(baseNonRecurringPrice)){
			updateMonthlyPrice(baseRecurringPrice, 1);
			updateInstallationPrice(baseNonRecurringPrice , 1)
		}
		
		h4.find("div:eq(1)").css("display", "table");
		h4.find("div:eq(0)").remove();
		
	}else{
		var liId = data.liId;
		var h4 = $("input#li_externalId_"+liId).parent();
		h4.find("div:eq(1)").css("display", "table");
		h4.find("div:eq(0)").remove();
	}
	//To save the html on add product to order
	savePageHtml(false, "");
}
