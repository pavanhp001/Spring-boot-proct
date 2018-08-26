
var priceChange = function(){
if($(this).attr("name").lastIndexOf("Feature") != -1 || 
        $(this).attr("name").lastIndexOf("FeatureGroup")!= -1){
        var name = $(this).attr("name");
        var selectedOption = $("#"+$(this).attr("id")+" option:selected").text();
        var price = selectedOption.slice((selectedOption.lastIndexOf("-")+2), selectedOption.length).split("$");
        var list = [];
        var optionsSum = 0.0;
        var finalBasePrice;
        var chkBoxId;
        var chkBoxVal = 0.0;
        $("select.styled").each(function(){
         	 var checkOption = $("#"+$(this).attr("id")+" option:selected").text().indexOf("$");
        	 var selectedOption = $("#"+$(this).attr("id")+" option:selected").text().split("$");
        	 if(selectedOption != "Please Select"){
        		 if(checkOption != -1){
        		   list.push(parseFloat(selectedOption[1]));
        	 }
        	}	 
        });
        $('input[type=checkbox]').each(function () {
            if (this.checked) {
            	//var chkBoxVal = $(this).attr("value");
            	chkBoxId = "#"+$(this).attr("id")+"_Feature_price";
            	chkBoxVal = $(chkBoxId).val();
            	list.push(parseFloat(chkBoxVal));
            }
        });
        for (var i=0; i < list.length; i++){
        	optionsSum = optionsSum + list[i];
        }
        var basePriceSplitValDup = $("#productBaseInfoDup").val().split("$");
        finalBasePrice = parseFloat(basePriceSplitValDup[1]) + parseFloat(optionsSum);
        $('span#product_priceInfo').html("$"+finalBasePrice.toFixed(2));
   }
};

addFeatures = function(){
		var js = $("#featuresJson").val();
		var prodJson = JSON.parse(js);
		var featureValue = {featureType: []};
		var featureGroup = []; 
		var h4Array= [];
		var selectedArray = {lineItems: []};
		 
		 // Fetch Select fields which are selected
		  var selectflds = new Array();
		 $('select').each( function(){ 
				 if($(this).val() != ""){
					 var selectName = $(this).attr("name");
					 selectName = selectName.replace(/\:/g,"\\:");
						 if(selectName.lastIndexOf("Feature") != -1 || selectName.lastIndexOf("FeatureGroup")!= -1){
							 var name = $(this).attr("name");
							 name = name.replace(/\:/g,"\\:");
							 var selectedOption = $("#"+$(this).attr("id")+" option:selected").text();	
							 var price = selectedOption.slice((selectedOption.lastIndexOf("-")+2), selectedOption.length);
							 var nonRecPrice = $("input#"+name+"_price_nonRecurring").val();
							 if(nonRecPrice == undefined){
								 nonRecPrice = $("input#"+name+selectedOption).val();
							 }
							 selectflds.push($(this).attr("name")+"|"+$(this).val()+"|"+"integer"+"|"+price+"|"+nonRecPrice);
						 }
				 	}
		        });
		 
		 // Fetch Checkboxes which are checked
		 var checkboxes = new Array();
		 $('#featuresContent input[type=checkbox]').each( function(){
		 			 var checkboxName = $(this).attr("name");
		 			 if($(this).is(":checked") == true){
		 					 if(checkboxName.lastIndexOf("Feature") != -1 || 
		 							checkboxName.lastIndexOf("FeatureGroup")!= -1){
		 				         var price = $("input#"+checkboxName.replace(/\:/g,"\\:")+"_price").val();
		 				        var nonRecPrice = $("input#"+checkboxName.replace(/\:/g,"\\:")+"_price_nonRecurring").val();
		 				         if(price != undefined){
		 				         checkboxes.push(checkboxName+"|"+$(this).attr("value")+"|"+"boolean"+"|"+price+"|"+nonRecPrice);
		 				         }else{
		 				        	 checkboxes.push(checkboxName+"|"+$(this).attr("value")+"|"+"boolean"+"|"+"INCLUDED"+"|"+"INCLUDED");
		 				         }
		 					 }
		 			      }
		 		    });
		
		  var allDataArray = $.merge(selectflds, checkboxes);
		  
				  //Constructing the Features JSON
				    for (var i=0; i < allDataArray.length; i++){
						    	
						       var indexValue = new Array();
						  indexValue = allDataArray[i].split('|');
						       var key = indexValue[0];
						       var val = indexValue[1];
						       var type = indexValue[2];
						       var price = indexValue[3];
						       var nonRecPrice = indexValue[4];
						  var fType =  key.slice((key.lastIndexOf("_")+1), key.length);
						  
						   if(fType == 'Feature' ){
									  		key = key.slice(0, key.lastIndexOf("_"));
									  		var j = {"externalId" : key,"value" : val,"type":type,"recuringPrice":price,"nonRecuringPrice":nonRecPrice};
									  		featureValue.featureType.push(j);
						       		}else{
								    	   if(val.lastIndexOf("_ALL") == -1){
								    	       key = key.slice(0, key.lastIndexOf("_"));
								    	       var j = {"externalId" : val.slice(0, val.lastIndexOf("-")),"value" : val.slice((val.lastIndexOf("-")+1), val.length), "type":"string","recuringPrice":price,"nonRecuringPrice":nonRecPrice};
								               featureGroup.push({"featureValue" : j,"externalId" : key,"groupType": 1 });
								    	   }else{
								    		   featureGroup.push({"externalId" : key,"groupType": -1 });
								    	   }
						            }
				       }
				    
				    //Adding features to the LinItem JSON
				     prodJson.featureValue = featureValue;
				     prodJson.featureGroup = featureGroup;
				     
				     var id = $("input#prodId").val();
				     h4_id = id.replace("product_", "row_");
				     var h4_inCart = h4_id.replace(/\:/g,"\\:");
				     //Checking whether the product in cart is the selected product or Not
				     var inCart = $("div#itemsBlock").find("div.mCSB_container > h4[id^='"+h4_inCart+"'][class != 'systemColor2 removedH4']");
				     if((inCart.length != 0)){
				    	 //Product in cart is selected product
				    	 //updating Features for the product in cart
				    	 var input = inCart.find("input:hidden:eq(1)");
				    	 var lineItemId = input.val();
				    	 var orderId = $("input#orderId").val();
				    	 var updateFeaturesJson = {"order" :{"lineItem":{"featureValue":featureValue,"featureGroup":featureGroup,"externalId":lineItemId} ,"externalId":orderId}};
				    	 var path =$("input#contextPath").val();
				    	 addScrollDiv(inCart);
				    	 inCart.find('div:eq(1)').css("display", "none");
							var url = path+"/salescenter/update/lineItemFeatures";
							var data = {};
							data["updateFeaturesJson"] = JSON.stringify(updateFeaturesJson);
							try{ 
							$.ajax({
								type: 'POST',
								url: url,
								data: data,
								success: onUpdateLineItemFeaturesComplete
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
								
							selectedArray.lineItems.push(JSON.stringify(prodJson));
							//AJAX Call To add LineItem to Order
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
				     }
}

var onUpdateLineItemFeaturesComplete = function(data){
	data = JSON.parse(data);
	if(data.stat){
		var liId = data.liId;
		var recurringPrice = data.recurringPrice;
		var h4 = $("input#li_externalId_"+liId).parent();
		var span_fright = h4.find('span.fRight');
		var price = span_fright.find("span#price").text();
		
		price = price.replace("$", "");
		price = parseFloat(price);
		if(!isNaN(price)){
			updateMonthlyPrice(price, -1);
		}
		h4.find("div:eq(1)").css("display", "table");
		h4.find("div:eq(0)").remove();
		h4.find("span.fRight").find("span#monPrice").text(recurringPrice);
		span_fright.find("span#price").text("$"+recurringPrice);
		recurringPrice = parseFloat(recurringPrice);
		if(!isNaN(recurringPrice)){
			updateMonthlyPrice(recurringPrice, 1);
		}
	}else if(!data.stat){
		var liId = data.liId;
		var h4 = $("input#li_externalId_"+liId).parent();
		h4.find("div:eq(1)").css("display", "table");
		h4.find("div:eq(0)").remove();
	}
}
