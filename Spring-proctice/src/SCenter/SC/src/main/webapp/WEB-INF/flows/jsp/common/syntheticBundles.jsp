
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
   <title>
      <fmt:message key="recommendations.header" />
   </title>
   <link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/recommendationsbycat.css'/>">
   <link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/rec_product_details.css'/>" />
   <link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/rec_compare.css'/>" />
   <link  rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/synthetic.css">
   <link  rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/priceTable.css">
   <script src="<c:url value='/js/jquery.simplemodal.js'/>"> </script>
   <script src="<c:url value='/script/recommendations.js'/>"></script>
   <script src="<c:url value='/script/features.js'/>"></script>
   <script src="<c:url value='/script/promotions.js'/>"></script>
   <script src="<c:url value='/script/html_save.js'/>"></script>
   <script src="<c:url value='/js/blockUI.js'/>"></script>
   <script>
   function sendCategory(category){
	   //alert(category) 
	    var productsMap = <%=request.getSession().getAttribute("productsMap")%>;
	   console.log("session data: "+<%=request.getSession().getAttribute("productsMap")%>)
	 //  console.log(" category ===="+JSON.stringify(productsMap[category]))
	  var productsMap = <%=request.getSession().getAttribute("productsMap")%>;
	   //alert(JSON.stringify(productsMap))
	   //alert(JSON.stringify(productsMap[category]))
	  // alert(category)
	   externalScope.$apply(function () {
		   
		   if(category == "PP"){
			   $(".ProductDetails").hide();
			   $(".pageTitle").text("Recommendations");
			   console.log("category ::"+category)
			   $("#powerPitchDiv").show();
			   $("#categoryDiv").hide();
			   $("#mixedBundleDiv").hide();
			   externalScope.isPP = true;
		    	 externalScope.isMb = false;
		    	 externalScope.isCategory = false;
		    	 externalScope.productVOJSONList = ${productVOJSONList}; 
		    	 externalScope.syntheticProductVOJSONList = ${syntheticProductVOJSONList};
		    	 externalScope.customerIntractionProductsList = ${customerIntractionProductsList};
			     <%-- externalScope.productVOJSONList = <%=request.getSession().getAttribute("productVOJSONList")%>
			     externalScope.syntheticProductVOJSONList = <%=request.getSession().getAttribute("syntheticProductVOJSONList")%>
			     externalScope.isSyntheticBundle = <%=request.getSession().getAttribute("isSyntheticBundle")%>
			     externalScope.customerIntractionProductsList = <%=request.getSession().getAttribute("customerIntractionProductsList")%> --%>
		   }else if(category == "MIXEDBUNDLES"){
			   $(".pageTitle").text("Mixed Bundles");
			  externalScope.isPP = false;
		    	 externalScope.isMb = true;
		    	 externalScope.isCategory = false;
			   $(".ProductDetails").hide();
			   console.log("category ::"+category)
			   $("#powerPitchDiv").hide();
			   $("#categoryDiv").hide();
			   $("#mixedBundleDiv").show();
			   externalScope.productVOJSONList = productsMap[category];
		   }else{
			   $(".pageTitle").text(category.replace(/_/g, ' '));
			  externalScope.isPP = false;
		    	 externalScope.isMb = true;
		    	 externalScope.isCategory = false;
			   $(".ProductDetails").hide();
			   console.log("category ::"+category)
			   $("#powerPitchDiv").hide();
			   $("#categoryDiv").show();
			   $("#mixedBundleDiv").hide();
			   externalScope.productVOJSONList = productsMap[category];
		   }
		 
		   //alert("category "+category)
		});
   } 
   function getWatchers(root) {
	   root = angular.element(root || document.documentElement);
	   var watcherCount = 0;

	   function getElemWatchers(element) {
	     var isolateWatchers = getWatchersFromScope(element.data().$isolateScope);
	     var scopeWatchers = getWatchersFromScope(element.data().$scope);
	     var watchers = scopeWatchers.concat(isolateWatchers);
	     angular.forEach(element.children(), function (childElement) {
	       watchers = watchers.concat(getElemWatchers(angular.element(childElement)));
	     });
	     return watchers;
	   }

	   function getWatchersFromScope(scope) {
	     if (scope) {
	       return scope.$$watchers || [];
	     } else {
	       return [];
	     }
	   }

	   return getElemWatchers(root);
	 }
   
   addProductsToOrder = function(productJson,h4Array){
	      try{
	      
	      var js = productJson;
	      var prodJson = JSON.parse(js);
	      var isAtleastOnePromotionChecked = false;
	      var isAtleastOneCheckBoxIsAvailable = false;
	      var providerId = prodJson.partnerExternalId;
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
	      
	      var id = "input_"+prodJson.img_id+"_"+prodJson.productExernalId;;
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
	      buildHtml(id,prodJson,h4Array);
	      }catch(e){
	      alert(e);
	      }
	      
	      }
      var productVOJSONList = ${productVOJSONList};
     
      $(document).ready(function(){
    	  $(".prodcutsBtn a").click(function(e){
     		   e.preventDefault();
     		   var category = $(this).find("label").attr("value");
     		   if(category != undefined){
     			   category = category.toUpperCase();
     		   }
      		   sendCategory(category);
      		  priceTable();
	       	 	 salesTips();
      	  });
    	 // $("#loadProducts").remove();
      	$("#action_wrapper .widget_container").removeAttr("style");
    	  $(".addToOrderBtn").hide();
          $("#totalPointsId").text("Points: "+${totalPoints});
          
          console.log("Total Wachers :: "+getWatchers().length);
      });
      jQuery(window).load(function () {
    	//Here we setting  LineitemExternalID in body level of page for score capturing .
     	$( "body" ).data( "li_externalId" , "${lineItemExtID}");
	      salesTips();
	      savePageHtml(true, "");
	      
	   });
      
   </script>
   <script>
   var prodDetailsArray = [];
      angular.module('concert').filter('unsafe', function($sce) {
          return function(val) {
              return $sce.trustAsHtml(val);
          };
      }).filter('words', function () {
          return function (input, words) {
              if (isNaN(words)) return input;
              if (words <= 0) return '';
              if (input) {
                  var inputWords = input.split(/\s+/);
                  if (inputWords.length > words) {
                      input = inputWords.slice(0, words).join(' ') + '...';
                  }
              }
              return input;
          };
      });
         angular.module('concert').controller('syntheticBundleController', ['$scope','$http','$window','$q','$timeout', function($scope,$http,$window,$q,$timeout) {
           var total = 0;
           var temp =0;
         	  $scope.init = function(){
                 $scope.productVOJSONList = ${productVOJSONList};
              },
              $scope.ngDisplayChannelLineUpData = function(providerEXid,providerName, productVO){
                  if(productVO.imageID != undefined && productVO.imageID == 'ATT_DirecTV'){
                  	displayChannelLineUpData('2314635','DIRECTV');
                  }else{
              		displayChannelLineUpData(providerEXid,providerName);
                  }
              },
           	   $scope.showCondtion = function(productVO){
           		   if(productVO.condition != undefined && productVO.condition !="" && productVO.condition != null){
           			   $scope.condition = productVO.condition;
           		   }else{
           			   $scope.condition = "N/A";
           		   }
           	  return true;
       	   },$scope.buildSynPriceGrid = function(priceTireObject, index){
     		  buildPriceGrid(priceTireObject,index);
          	return true;
          },
              $scope.addMixedBundleToOrder = function(){
       				$("#AddToOrderBtn2").attr("disabled","disabled");
               	 	var productDetailsJSON = JSON.stringify(prodDetailsArray);
                  	var h4Array= [];
                  	var path =$("input#contextPath").val();
                     var data = {};
                     
                     if(prodDetailsArray[1].providerName != undefined || prodDetailsArray[1].providerName =="DISH Network"){
                     	prodDetailsArray[1].productType ="VIDEO"
                     }
                     if(prodDetailsArray[0].providerName != undefined || prodDetailsArray[0].providerName =="DISH Network"){
                     	prodDetailsArray[0].productType ="VIDEO"
                     }
                      var selectedArray = {lineItems: []};
                      selectedArray.lineItems.push(JSON.stringify(prodDetailsArray[0]));
                      
                      var selectedArray1 = {lineItems: []};
                      selectedArray1.lineItems.push(JSON.stringify(prodDetailsArray[1]));
                         
                      addProductsToOrder(JSON.stringify(prodDetailsArray[0]),h4Array);
                      addProductsToOrder(JSON.stringify(prodDetailsArray[1]),h4Array);
                      
                        var date = new Date();
              		    var day = date.getDate().toString();
					    var monthIndex = (date.getMonth()) + 1;
					    var year = date.getFullYear().toString();
				     	var hours = date.getHours().toString();
						var minutes = date.getMinutes().toString();
						var seconds = date.getSeconds().toString();
						var mllisEc = date.getMilliseconds().toString();
                      if(monthIndex < 10){
                    	  monthIndex = "0"+monthIndex;
                      }
                      var totalFormat=  year + monthIndex+  day + hours + minutes + seconds + mllisEc;
                      
                      data["productData1"] = JSON.stringify(selectedArray);
                      data["productData2"] =  JSON.stringify(selectedArray1);
                      data["mixedBundleTime"]= totalFormat.trim();
                         
                      data["orderId"] = $("input#orderId").val();
                         try{
                                 $.ajax({
                                 	type: 'POST',
                                 	data:data,
                                 	url: "<%=request.getContextPath()%>/salescenter/recommendations/AddMixedBundleToOrder",
                                 	success: onCompleteMB(h4Array),
               					error: onError(h4Array)
                              	});
                             }catch(err){
                             	alert(err);
                             }
                             
                     //	savePageHtml(false, "");
                     },
                     $scope.bundleProductData = function(productVO,index){
                			bundleProductDataBuild(productVO,index);
                			return true;
                	   },
                	
               	   $scope.buildPriceTire = function(pricingGridJson){
                      	// var priceTireObject = {"headers":["NO_DVR"],"PRICE_JSON":{"TV1":{"NoOfTv":"1 TV","header1":"Monthly Price","header2":"After 12 Months","NO_DVR_MTH":"$79.99","NO_DVR_A12M":"$79.99"},"TV2":{"NoOfTv":"2 TV","header1":"Monthly Price","header2":"After 12 Months","NO_DVR_MTH":"$84.99","NO_DVR_A12M":"$84.99"},"TV3":{"NoOfTv":"3 TV","header1":"Monthly Price","header2":"After 12 Months","NO_DVR_MTH":"$89.99","NO_DVR_A12M":"$89.99"},"TV4":{"NoOfTv":"4 TV","header1":"Monthly Price","header2":"After 12 Months","NO_DVR_MTH":"$94.99","NO_DVR_A12M":"$94.99"},"TV5":{"NoOfTv":"5 TV","header1":"Monthly Price","header2":"After 12 Months","NO_DVR_MTH":"$99.99","NO_DVR_A12M":"$99.99"},"TV6":{"NoOfTv":"6 TV","header1":"Monthly Price","header2":"After 12 Months","NO_DVR_MTH":"$104.99","NO_DVR_A12M":"$104.99"}}};
                      	return JSON.parse(pricingGridJson);
                      	 //return priceTireObject;
                      },
                	   
                     $scope.addSyntBdToOrderAndCKO = function(){
                    	
                         $.blockUI({ message: $('#domMessage') }); 
                    	 console.log("addSyntBdToOrderAndCKO")
                    	 	var productDetailsJSON = JSON.stringify(prodDetailsArray);
	                       	var h4Array= [];
	                       	var path =$("input#contextPath").val();
	                        var data = {};

	                        if(prodDetailsArray[1].providerName != undefined || prodDetailsArray[1].providerName =="DISH Network"){
	                         	prodDetailsArray[1].productType ="VIDEO"
	                         }
	                         if(prodDetailsArray[0].providerName != undefined || prodDetailsArray[0].providerName =="DISH Network"){
	                         	prodDetailsArray[0].productType ="VIDEO"
	                         }
                           
                           var selectedArray = {lineItems: []};
                           selectedArray.lineItems.push(JSON.stringify(prodDetailsArray[0]));
                           
                           var selectedArray1 = {lineItems: []};
                           selectedArray1.lineItems.push(JSON.stringify(prodDetailsArray[1]));
                              
                           addProductsToOrder(JSON.stringify(prodDetailsArray[1]),h4Array);
                           addProductsToOrder(JSON.stringify(prodDetailsArray[0]),h4Array);
                           
                           
	                        var date = new Date();
	                 		var day = date.getDate().toString();
	   					   	var monthIndex = (date.getMonth()) + 1;
	   					    var year = date.getFullYear().toString();
	   				     	var hours = date.getHours().toString();
	   						var minutes = date.getMinutes().toString();
	   						var seconds = date.getSeconds().toString();
	   						var mllisEc = date.getMilliseconds().toString();
                         if(monthIndex < 10){
                       	  monthIndex = "0"+monthIndex;
                         }
                         var totalFormat=  year + monthIndex+  day + hours + minutes + seconds + mllisEc;
                           data["productData1"] = JSON.stringify(selectedArray1);
                           data["mixedBundleTime"]= totalFormat.trim();
                           data["orderId"] = $("input#orderId").val();
                           var path =$("input#contextPath").val();
                       	   var url = path+"/salescenter/recommendations/AddMixedBundleToOrder";
                           try{
                               $.ajax({
                               	type: 'POST',
                               	data:data,
                               	url: url,
                               	success: function(data1){
                               		//alert("data "+data1);
                               		$("form#recommend input[id='mixedBundleTime']").val(totalFormat);
                               		$("form#recommend input[id='productOfferTypeValue']").val($("input#productOfferType").val());
                          			$("form#recommend input[id='productDataToAddOrder']").val(JSON.stringify(selectedArray));
                          			$("form#recommend input[id='_eventId']").val("addToOrderAndCKOEvent");
                          			enablePage();
                          			$("form#[name='recommend']").submit();
                          			
                               	},
                               	error: onError(h4Array)
                            	});
                           }catch(err){
                           	console.log("error ::"+err);
                           }
                         
                     },
                     $scope.showPairedProduct = function(productVO){
                   		 $scope.isPairedProduct=false;
                   		 $scope.providerExtIdS="" ;
                    	  if(productVO != undefined && productVO.pairedProduct != undefined){
                    		var pairedProductVO = JSON.parse(productVO.pairedProduct);
                   		   if(productVO.providerExtId != undefined && productVO.providerExtId !="" && productVO.providerExtId != null){
                   			   $scope.providerExtIdS = $scope.providerExtIdS +"_"+productVO.imageID +"_"+pairedProductVO.imageID;
                   		   }
                      	   }
                   		if(productVO != undefined && productVO.pairedProduct != undefined){
                   			var productJson = JSON.parse(productVO.pairedProduct);
                   			$scope.pairedProductObject = productJson;
                   			$scope.pairedProductProductIconList = productJson.productIconList;
                   			$scope.pairedProductName = productJson.productName;
                   			$scope.pairedProductImageID = productJson.imageID;
                   			$scope.pairedProductProductPointDisplay = productJson.productPointDisplay;
                   			$scope.pairedProductProductExID = productJson.productProductExID;
                   			$scope.pairedProductDescription = productJson.productDescription;
                   			$scope.isPairedProduct=true;
                  			 return true;
                  		 }else{
                  			$scope.isPairedProduct=false;
                  			 return false;
                  		 }
                   	   },
                   	$scope.showPriceInfo = function(productVO){
                 		   $scope.totalPts = 0.0;
                       	   $scope.totalPrice1= 0.0;
                       	   $scope.promotionPr= 0.0;
                       	   $scope.totalInstallation= 0.0;
                       	   
                       	   $scope.displayBasePrice = 0;
                           $scope.totalDisplayBasePrice = 0;
                       	   $scope.totalPromotionPrice = 0;
                           $scope.displayPromotionPrice = 0;
                           $scope.totalDisplayPromotionPrice = 0;
                       	   
                       	   var prodId =[]
                       	   $scope.providerExtIdS="" ;
                           $scope.pairedProduct;
                           var pairedProductObj;
                       	   if(productVO != undefined){
                       		 if(productVO.pairedProduct != undefined){
                           		$scope.pairedProduct = JSON.parse(productVO.pairedProduct);
                           		    pairedProductObj = JSON.parse(productVO.pairedProduct);
                           	   }
                            	//console.log("productVO== "+JSON.stringify(productVO));
                       		   $scope.totalPrice1 = parseFloat(productVO.baseRecurringPrice) + parseFloat(pairedProductObj.baseRecurringPrice);
                       		
                       		   if(productVO.totalPromotionPrice != undefined && parseFloat(productVO.totalPromotionPrice) != 0.0){
                       			$scope.totalPromotionPrice = parseFloat(productVO.totalPromotionPrice);
                       			$scope.promotionPr =  parseFloat(productVO.totalPromotionPrice);
                       		   }
                       		  
                       		    if(productVO.displayBasePrice != undefined ){
                	       		 $scope.displayBasePrice = parseFloat(productVO.displayBasePrice);
                	       		}
                       		    if(productVO.totalDisplayBasePrice != undefined){
                	       		 $scope.totalDisplayBasePrice = parseFloat(productVO.totalDisplayBasePrice);
                	       		 }
                	       	   
                	       		if(productVO.displayPromotionPrice != undefined){
                	       		 $scope.displayPromotionPrice = parseFloat(productVO.displayPromotionPrice);
                	       		}
                	       		if(productVO.totalDisplayPromotionPrice != undefined){
                		       		 $scope.totalDisplayPromotionPrice = parseFloat(productVO.totalDisplayPromotionPrice);
                		       	}
                	       	  
                       		
                       		   $scope.totalInstallation=parseFloat(productVO.baseNonRecurringPrice) + parseFloat(pairedProductObj.baseNonRecurringPrice);
                       		   $scope.totalPts = parseFloat(productVO.totalPoints);
                       		   
                       		   if(productVO.providerExtId != undefined && productVO.providerExtId !="" && productVO.providerExtId != null){
                       			   $scope.providerExtIdS = $scope.providerExtIdS +"_"+productVO.imageID +"_"+pairedProductObj.imageID;
                       		   }
                       	   }
                       	   
                       	  // $scope.providerExtIdS = prodId;
                       	  return true;
                   	   },
                   	$scope.buildPriceTireSyn = function(displayPricingGrid, bundlePrice){
                         	$scope.priceTireObjectSyn = JSON.parse(displayPricingGrid);
                         	$scope.priceTireObjectSynTotal = JSON.parse(bundlePrice);
                         	return true;
                         },
                   	   $scope.syntheticViewDetailsClick = function (object){
        		   prodDetailsArray = [];
        		   $scope.selectedProductInfo = object;
             		//console.log("$scope.selectedProductInfo :: "+JSON.stringify($scope.selectedProductInfo));
             		  // $.blockUI({ message: $('#domMessage') }); 
                        $scope.isShowProductDetails = false;
                        //$("#bmc_img").show();
                        $("#ProductDetails").hide();
                        try{
                   /*  $.each(object,function(index,productObj){
                     prodDetailsArray.push(JSON.parse(productObj.hiddenProductJSON));
                    }); */
                    prodDetailsArray.push(JSON.parse(object.hiddenProductJSON));
                    prodDetailsArray.push(JSON.parse(JSON.parse(object.pairedProduct).hiddenProductJSON));
                    var deferred = $q.defer();
                    deferred.resolve(viewDetails(prodDetailsArray,""));
                    //viewDetails(prodDetailsArray,"");
                    $scope.productDetailsArr = JSON.parse(productDetailsJson);
                    $(".providerOne_td").attr("src"," https://s3.amazonaws.com/AL-content/common/provider/logos/"+productDetailsArr[0].detailsImage+".jpg");
                    $(".providerTwo_td").attr("src"," https://s3.amazonaws.com/AL-content/common/provider/logos/"+productDetailsArr[1].detailsImage+".jpg");
                    
                  	$('.fstProviderST').find("#"+$scope.productDetailsArr[0].detailsImage).css('display','block');
              		$('.secProviderST').find("#"+$scope.productDetailsArr[1].detailsImage).css('display','block');
              		$(".fstProviderST").find(".popupheadcont").hide();
              		$(".secProviderST").find(".popupheadcont").hide();
              		
                       //$("#bmc_img").hide();
                        $("#ProductDetails").show();
                        $timeout(function(){
                         	 savePageHtml(true, "");
                        },500);
                        }catch(e){
                            alert(e);
                        }
                }
           }]);
          
         function displayChannelLineUpData(providerExternalId,providerName)
         {
         var urlPath = "<%=request.getContextPath()%>/salescenter/channelLineUpData?providerExternalId="+providerExternalId+"&providerName="+encodeURIComponent(providerName);
         window.open(urlPath,"", "scrollbars=yes, width=1000, height=550");
         }
         
         var viewDetails = function(productStr,points) {
        	 $("#basic-modal-content1").show();
             $("#basic-modal-content1 #bmc_img").show();
             $(".tabsWrapper").hide();
             $(".ProductDetails").hide();
             $(".addToOrderBtn").hide();
             $("#backToDiscoveryId").hide();
             
             var detailsJson;
             try{
            
             //console.log("points  "+points);
             
             var data = { "aidval" : JSON.stringify(productStr),"productPoints" : points };
             
             //var vals = aidval.split(','); 
             $("#providerName_dialog").html('Product Details');
             $("input#synthfeaturesJson").val(JSON.stringify(productStr));
                 $.ajax({
                 	type: 'POST',
                 	data:data,
                 	async: false,
                 	url: "<%=request.getContextPath()%>/salescenter/recommendations/viewSyntheticBdDetails",
                 	success: function(data1){
                 		$.unblockUI();
                 		
                 		$("#recommendationsDiv").hide();
                 		$("#basic-modal-content1").show();
                 		 $("#basic-modal-content1 #bmc_img").hide();
                 		$(".ProductDetails").show();
                 		$(".addToOrderBtn").show();
                 		$("#backToDiscoveryId").show();
                 		$( ".ProductDetails" ).scrollTop(0);
                 		//$('.fstProviderST').find("#"+productDetailsArr[1].providerExtId).css('display','block');
                  		//$('.secProviderST').find("#"+productDetailsArr[0].providerExtId).css('display','block');
                 		
                 		if(data1=="sessionTimeOut"){
                 			var path = "<%=request.getContextPath()%>";
                 			window.location.href = path+"/salescenter/session_timeout";
                 		}else{
                 			var data = JSON.parse(data1); 
                 			productDetailsJson =JSON.stringify(data);
                 			
             	    		//To save the html on page load
             	    		//savePageHtml(true, "");
             	    		$("#pageTitle").val("RecommendationsByCategory");
                 		}
                 	}
              	});
             }catch(err){
             	alert(err);
             }
             }
 		function backMBToDiscovery(flowEventId){
        	 
        	 if($('div#basic-modal-content1').css('display') != 'none'){
        		 $("h4#cartError").next().remove();
       			$("h4#cartError").remove();
       			
       			$("#basic-modal-content1").hide();
       			$(".tabsWrapper").show();
                $(".ProductDetails").show();
       			$("#recommendationsDiv").show();
       			$(".tabsWrapper").show();
       			$("#addTOOrderBtn2Div").hide();
       			
       			//this code for unchecks the all check boxes. 
       			$('#recommendationsDiv input[type=checkbox]').each(function(){
       				 this.checked = false;
       			});
       			savePageHtml(false, "");
        	 }else{
	        	 if(flowEventId!=undefined && flowEventId!=""){
		        	  $("form#recommend input[id='_eventId']").val(flowEventId);
		        	  $("form#recommend").submit();
	        	 }else{
		        	  var path =$("input#contextPath").val();
		        	  action = path+"/salescenter/discovery";
		        	  $("form#[name='recommend']").attr("action", action);
		        	  $("form#[name='recommend']").submit();
	        	 }
        	 }
       }
   </script>
  
  
</head>

<div id="syntheticBundleController" data-ng-controller="syntheticBundleController" data-ng-init="init()">
   <div id="comparisionDiv">
      <section id="contentfullcont">
         <header id="content_header" >
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
               <input type="hidden" name="productOfferType" id="productOfferType" value="" />
               <span class="cell pageTitle">
                  <fmt:message key="${title}" />
               </span>
               <span class="cell sortOption">Sort by: </span>
               <select class="sortOffer1">
                  <option value="Recommended" >Recommended</option>
                  <option value="Plan_A_Z" >Plan (A-Z)</option>
                  <option value="Plan_Z_A">Plan (Z-A)</option>
                  <option value="Price_High_to_Low" >Price (High - Low)</option>
                  <option value="Price_Low_to_High">Price (Low - High)</option>
                  <option class = "tv_sortOffer" value="Channels_High_Low" >Channels (High - Low)</option>
                  <option class = "tv_sortOffer" value="Channels_Low_High" >Channels (Low - High)</option>
                  <option class = "internet_sortOffer" value="Interent_Speed_High_Low" >Speed (High - Low)</option>
                  <option class = "internet_sortOffer" value="Interent_Speed_Low_High">Speed (Low - High)</option>
               </select>
               <!-- <input type="text" id="search" name="search" placeholder="Search By Name" ng-model="searchByName.productName"> -->
               <span class="callTime" id="startTimeText"></span>
            </div>
         </header>
         <section id="content">
            <form id="recommend" name="recommend" action="${flowExecutionUrl}" method="post">
            
            	<input type="hidden" id="salesTips" value='${salesTips}' name="salesTips"/>
               <input type="hidden" id="providerExternalId" name="providerExternalId" value="" />
               <input type="hidden" id="productDataToAddOrder" name="productDataToAddOrder" value="" />
               <input type="hidden" id="productOfferTypeValue" name="productOfferTypeValue" value="" />
               <input type="hidden" id="mixedBundleTime" name="mixedBundleTime" value="" />
               <input type="hidden" id="_eventId" name="_eventId" value="">
               <input type="hidden" name="selectedProducts" id="selectedProducts" value="" />
               <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
               <input type="hidden" id="categoryName" value="${categoryName}" />
               <input type="hidden" id="customerId" value="${order.customerInformation.customer.externalId}" />
               <input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
               <input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
               <input type="hidden" id="pageTitle" name="pageTitle" value="RecommendationsByCategory">
               <div class="contentwrapperrec">
                  <div id="action_wrapper">
                     <div class="widget_container" style="background-color:#fff !important">
                        <div class="tabsWrapper">
                          <%--  <div id="loadProducts" style="width:inherit;text-align:center;margin-top:100px">
				               <img src="<%=request.getContextPath()%>/images/spinner.gif"></img>
				        </div> --%>
                           <!-- Mock -->
                           <div class="tabs" data-ng-repeat="productVO in productVOJSONList track by $index" ng-init="indVal=$index" ng-cloak>
                            <div class="forplace" data-ng-if="productVO.pairedProduct != undefined && productVO.pairedProduct != '' &&  showPairedProduct(productVO)">
                               <dir class="syncbdlmain bundleProductContent{{$index}}" >
                                    <div class="sync_part" >
                                   		 <div ng-click ="syntheticViewDetailsClick(productVO)" class="tblink ViewDetails">View Details</div>
                                    </div>
                                    <div class="sync_part ltpart">
                                    	<div class="syncbdl_cont">
                                          <div class="sync_logo">
                                          <img id="pairedProductImageID" alt=""></div>
                                          <div class="sync_title">
                                             <span id="pairedProductName"></span>
                                             <p class="productDic" id="pairedProductDescription"></p>
                                          </div>
                                          <div class="sync_icons">
                                           <span ng-repeat="productIcon in pairedProductProductIconList track by $index">
                                           		<img ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/{{productIcon}}.png">
                                           		<span  ng-if="productIcon =='tv' && pairedProductObject.channelsCount != undefined"><b ng-bind="pairedProductObject.channelsCount"></b><span>+</span></span>
	                                            <span ng-if="productIcon =='internet' && pairedProductObject.connectionSpeedCount != undefined"><b ng-bind="pairedProductObject.connectionSpeedCount"></b></span>
                                          </span>
                                           </div> 
                                       </div>
                                     <!--   $(".singleProductContent"+indexValue+""+" #ppFullName").text(productVO.productName+" "+productVO.PairedProduct.productName)  
                                       $(".singleProductContent"+indexValue+""+" #ppFullDec").text(productVO.productDescription);   -->
                                       <div class="syncbdl_cont">
                                          <div class="sync_logo">
                                         	 <img id="imageID">
                                          </div>
                                          <div class="sync_title">
                                             <span id="ppFullName" ng-bind="(productVO.productName) + (productVO.PairedProduct.productName)"></span>
                                             <p class="productDic" id="ppFullDec"  ng-bind="productVO.productDescription | words:12" ></p> 
                                          </div>
                                          <div class="sync_icons" ng-repeat="productIcon in productVO.productIconList track by $index">
                                        	 <img ng-if="productVO.pricingGridJson != undefined" class="gridIcon nobtmbdr"  ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/table_icon.png">
                                        	 <img id="aproduct" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/{{productIcon}}.png">
                                          	<span  ng-if="productIcon =='tv' && productVO.channelsCount != undefined"><b ng-bind="productVO.channelsCount"></b><span>+</span></span>
	                                        <span ng-if="productIcon =='internet' && productVO.connectionSpeedCount != undefined"><b ng-bind="productVO.connectionSpeedCount"></b></span>
                                           
                                              <!--  ng-init="priceTireObject=buildPriceTire(productVO.pricingGridJson) -->
                                          	<span class="ratepopup" ng-if="productVO.pricingGridJson != undefined">
												<table class="ratecomp" cellspacing="0"  ng-init="priceTireObject=buildPriceTire(productVO.pricingGridJson)">
												<thead>
													 <tr>
													  <th colspan="2">Number of TVs</th>
													  <th ng-repeat="headers in priceTireObject.headers track by $index">
													  <span ng-bind="headers"></span>
													 </tr>
												</thead>
												<tbody class="sythaticPriceGrid{{indVal}}" ng-if="buildSynPriceGrid(priceTireObject,indVal)">
													
												</tbody>
											</table>
                                          	</span>
                                          </div>
                                       </div>
                                    </div>
                                    <div class="sync_part rtpart ltpad">
                                       <div class="tblink">
                                          <div class="tblink syncbdl_cont" >
                                             <span class="autottips">Sales Tips</span>
                                           
                                             <span class="leftbottompop salesTipId" >
                                             <span id='{{providerExtIdS}}' class="tip sTips"> </span> 
                                             </span>
                                          </div>
                                       </div>
                                       <div class="tblink">
                                          <span class="autottips">Promotions</span>
                                          <span class="leftbottompop">
                                          <div class="popscroll">
                                                <div class="popupheadcont" id="PProviderName"></div>
                                                <ul>
                                                   <li data-ng-if="pairedProductObject.productDescription != ''" id="PairedpromotionDescription"></li>
                                                   <li data-ng-if="pairedProductObject.productDescription == ''">None</li>
                                                   <li data-ng-if='showCondtion(pairedProductObject)' data-ng-if='condition != "N/A" ' id="tAConditions"></li>
                                                </ul>
                                             </div>
                                             <div class="popscroll">
                                                <div class="popupheadcont" id="popupheadcont"></div>
                                                <ul>
                                                   <li data-ng-if="productVO.productDescription != ''" id="PpromotionDescription"></li>
                                                   <li data-ng-if="productVO.productDescription == ''">None</li>
                                                   <li data-ng-if='showCondtion(productVO)' data-ng-if='condition != "N/A" ' id="PTAConditions"></li>
                                                </ul>
                                             </div>
                                          </span>
                                       </div>
                                       <span>
                                       
                                          <span data-ng-if="productVO.providerExtId =='27010360'">
                                             <div class="tblink bundleChannel" ng-click="ngDisplayChannelLineUpData(productVO.parentProviderExtId,productVO.providerName,productVO)" data-ng-if ="productVO.isChannelLineupProvider && productVO.isVideoCapable" >Channel Lineup</div>
                                          </span>
                                       </span>
                                    </div>
                                    <div class="sync_part rtpart basePriceSyn">
                                       <div class="totalpop">
                                          <table class="pricetbl">
                                           
                                             <tr>
                                                <td class="txtu">Base Price:</td>
                                                <td>
                                                   <span>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice != 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice == undefined"><span>{{productVO.totalPrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalDisplayPromotionPrice != undefined && productVO.totalDisplayBasePrice != undefined"><span>{{productVO.totalDisplayBasePrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalDisplayPromotionPrice != undefined && productVO.totalDisplayBasePrice == undefined"><span>{{productVO.totalPrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice != 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice != undefined"><span>{{productVO.totalDisplayBasePrice | currency}}</span></del>
                                                   
                                                   <span class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice == 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice == undefined">
                                                   {{productVO.totalPrice | currency}}
                                                   </span>
                                                   <span ng-if="productVO.totalPromotionPrice == 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice != undefined ">
                                                   <span><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.totalDisplayBasePrice | currency}}</span>
                                                   </span>
                                                   </span>
                                                </td>
                                             </tr>
                                             <tr>
                                                <td></td>
                                                <td ng-if="productVO.totalDisplayPromotionPrice == undefined && productVO.totalPromotionPrice != 0 && productVO.totalDisplayBasePrice == undefined"><strong class="tblrtlnk lessprice nonDisplayIcon">{{productVO.totalPromotionPrice | currency}}</strong></td>
                                                <td ng-if="productVO.totalDisplayPromotionPrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png"><strong class="tblrtlnk lessprice">{{productVO.totalDisplayPromotionPrice | currency}}</strong></td>
                                                <td ng-if="productVO.totalDisplayPromotionPrice == undefined && productVO.totalPromotionPrice != 0 && productVO.totalDisplayBasePrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png"><strong class="tblrtlnk lessprice">{{productVO.totalPromotionPrice | currency}}</strong></td>
                                                
                                             </tr>
                                             <tr>
                                                <td class="txtu">Total Points:</td>
                                                <td><strong class="tblrtlnk">{{productVO.totalPoints}}</strong></td>
                                             </tr>
                                          </table>
                                          <span class="rightbottompop">
                                           
                                             <table class="poptbl">
                                                <tr>
                                                   <td><strong class="popupheadcont">Base Price:</strong></td>
                                                   <td class="displayIconBlock"><b class="popupheadcont">
		                                           
		                                           <del class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice != 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice == undefined"><span>{{productVO.totalPrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalDisplayPromotionPrice != undefined && productVO.totalDisplayBasePrice != undefined"><span>{{productVO.totalDisplayBasePrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalDisplayPromotionPrice != undefined && productVO.totalDisplayBasePrice == undefined"><span>{{productVO.totalPrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice != 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice != undefined"><span>{{productVO.totalDisplayBasePrice | currency}}</span></del>
                                                   
                                                   <span class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice == 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice == undefined">
                                                   {{productVO.totalPrice | currency}}
                                                   </span>
                                                   <span ng-if="productVO.totalPromotionPrice == 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice != undefined ">
                                                   <span> <img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png"> {{productVO.totalDisplayBasePrice | currency}}</span>
                                                   </span>
		                                                 
                                                   <span class="dispricepop nonDisplayIcon"  ng-if="productVO.totalDisplayPromotionPrice == undefined && productVO.totalPromotionPrice != 0 && productVO.totalDisplayBasePrice == undefined">{{productVO.totalPromotionPrice | currency}}</span>
                                                   <span class="dispricepop" ng-if="productVO.totalDisplayPromotionPrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.totalDisplayPromotionPrice | currency}}</span>
                                                   <span class="dispricepop" ng-if="productVO.totalDisplayPromotionPrice == undefined && productVO.totalPromotionPrice != 0 && productVO.totalDisplayBasePrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.totalPromotionPrice | currency}}</span>
	                                             </b>
                                                   </td>
                                                </tr>
                                                
                                                <tr>
                                                   <td class="thd">{{pairedProductObject.providerName}} : </td>
                                                   <td>
	                                                   
	                                                  <del class="nonDisplayIcon" ng-if="pairedProductObject.promoPrice != undefined && pairedProductObject.displayPromotionPrice == undefined && pairedProductObject.displayBasePrice == undefined">{{pairedProductObject.baseRecurringPrice | currency}} </del> 
	                                                   <del class="nonDisplayIcon" ng-if="pairedProductObject.promoPrice != undefined && pairedProductObject.displayPromotionPrice == undefined && pairedProductObject.displayBasePrice != undefined">{{pairedProductObject.displayBasePrice | currency}}</del> 
	                                                   <del class="nonDisplayIcon" ng-if="pairedProductObject.displayPromotionPrice != undefined  && pairedProductObject.displayBasePrice != undefined">{{pairedProductObject.displayBasePrice | currency}}</del>
	                                                   <del class="nonDisplayIcon" ng-if="pairedProductObject.displayPromotionPrice != undefined  && pairedProductObject.displayBasePrice == undefined">{{pairedProductObject.baseRecurringPrice | currency}}</del>
	                                                   
	                                                   <span class="nonDisplayIcon" ng-if="pairedProductObject.promoPrice == undefined && pairedProductObject.displayPromotionPrice == undefined && pairedProductObject.displayBasePrice == undefined">{{pairedProductObject.baseRecurringPrice | currency}}</span>
	                                                   <span ng-if="pairedProductObject.displayPromotionPrice ==undefined && pairedProductObject.displayBasePrice != undefined && pairedProductObject.promoPrice == undefined"><img  ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{pairedProductObject.displayBasePrice | currency}}</span>
	                                                   
	                                                   <span class="dispricepop nonDisplayIcon" ng-if="pairedProductObject.displayPromotionPrice ==undefined && pairedProductObject.promoPrice !=0">{{pairedProductObject.promoPrice | currency}}</span>
	                                                   <span class="dispricepop" ng-if="pairedProductObject.displayPromotionPrice !=undefined"><img class="displayIcon"  ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{pairedProductObject.displayPromotionPrice | currency}}</span>
	                                                   
	                                                   
                                                   </td>
                                                   
                                                   
                                                </tr>
                                                
                                                <tr>
                                                   <td class="thd">{{productVO.providerName}} : </td>
                                                   <td>
                                                   		
	                                                   <del class="nonDisplayIcon" ng-if="productVO.promoPrice != undefined && productVO.displayPromotionPrice == undefined && productVO.displayBasePrice == undefined">{{productVO.baseRecurringPrice | currency}} </del> 
	                                                   <del class="nonDisplayIcon" ng-if="productVO.promoPrice != undefined && productVO.displayPromotionPrice == undefined && productVO.displayBasePrice != undefined">{{productVO.displayBasePrice | currency}}</del> 
	                                                   <del class="nonDisplayIcon" ng-if="productVO.displayPromotionPrice != undefined  && productVO.displayBasePrice != undefined">{{productVO.displayBasePrice | currency}}</del>
	                                                   <del class="nonDisplayIcon" ng-if="productVO.displayPromotionPrice != undefined  && productVO.displayBasePrice == undefined">{{productVO.baseRecurringPrice | currency}}</del>
	                                                   
	                                                   <span class="nonDisplayIcon" ng-if="productVO.promoPrice == undefined && productVO.displayPromotionPrice == undefined && productVO.displayBasePrice == undefined">{{productVO.baseRecurringPrice | currency}}</span>
	                                                   <span ng-if="productVO.displayPromotionPrice ==undefined && productVO.displayBasePrice != undefined && productVO.promoPrice == undefined"><img class="displayIcon"  ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.displayBasePrice | currency}}</span>
	                                                   
	                                                   <span class="dispricepop nonDisplayIcon" ng-if="productVO.displayPromotionPrice ==undefined && productVO.promoPrice !=0">{{productVO.promoPrice | currency}}</span>
	                                                   <span class="dispricepop" ng-if="productVO.displayPromotionPrice !=undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.displayPromotionPrice | currency}}</span>
	                                                   
                                                   </td>
                                                </tr>
                                                
                                             </table>
                                             <table class="poptbl">
                                                <tr>
                                                   <td class="thd"><strong class="popupheadcont">Total Points:</strong></td>
                                                   <td><b class="popupheadcont bundlePopupheadcont" ></b></td>
                                                </tr>
                                                
                                                <tr>
                                                   <td class="thd bundleThdpOne"> :</td>
                                                   <td  class="bundleThdptDisplaypOne"></td>
                                                </tr>
                                                
                                                <tr>
                                                   <td class="thd  bundleThdpTwo"> :</td>
                                                   <td class="bundleThdptDisplayTwo"></td>
                                                </tr>
                                             </table>
                                          </span>
                                       </div>
                                    </div>
                                 </dir>
                                  <div data-ng-if="bundleProductData(productVO,$index)"></div>
                              <!-- <dir class="syncbdlmain" >
                                    <div class="sync_part" >
                                   		 <div ng-click ="syntheticViewDetailsClick(productVO)" class="tblink ViewDetails">View Details</div>
                                    </div>
                                    <div class="sync_part ltpart">
                                    	<div class="syncbdl_cont">
                                          <div class="sync_logo"><img ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/{{pairedProductImageID}}.jpg" title="Points : {{pairedProductProductPointDisplay}}, Product External Id : {{pairedProductProductExID}}"></div>
                                          <div class="sync_title">
                                             <span>{{pairedProductName}}</span>
                                             <p class="productDic">{{pairedProductDescription | words:12}}</p>
                                          </div>
                                           <div class="sync_icons" ng-repeat="productIcon in pairedProductProductIconList track by $index">
                                           		<img ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/{{productIcon}}.png">
                                          	
                                           </div> 
                                       </div>
                                       <div class="syncbdl_cont">
                                          <div class="sync_logo"><img ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/{{productVO.imageID}}.jpg" title="Points : {{productVO.productPointDisplay}}, Product External Id : {{productVO.productExID}}"></div>
                                          <div class="sync_title">
                                             <span>{{productVO.productName}} {{productVO.PairedProduct.productName}}</span>
                                             <p class="productDic">{{productVO.productDescription | words:12}}</p>
                                          </div>
                                          <div class="sync_icons" ng-repeat="productIcon in productVO.productIconList track by $index">
                                          		<img ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/{{productIcon}}.png">
                                          		
                                          		<img ng-if="productVO.displayPricingGrid != undefined" class="gridIcon nobtmbdr"  ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/table_icon.png">
                                          		
                                          		<span class="ratepopup" ng-if="productVO.displayPricingGrid != undefined && productVO.bundlePriceJson != undefined" >
                                          		<div class="eliteCustomer" ng-if="productVO.providerExtId =='27010360'">Elite Customer Pricing</div>
												<table class="ratecomp" cellspacing="0" ng-if="buildPriceTireSyn(productVO.displayPricingGrid, productVO.bundlePriceJson)">
												<thead>
													 <tr>
													  <th colspan="2">Number of TVs</th>
													  <th ng-repeat="(key, value) in priceTireObjectSyn track by $index">
														<span ng-if="key=='PR_DVR'">PREM DVR</span>
														<span ng-if="key=='NO_DVR' && productVO.providerExtId =='27010360'">Whole-Home DVR</span>
														<span ng-if="key=='NO_DVR' && productVO.providerExtId !='27010360'">No DVR</span>
														<span ng-if="key=='EN_DVR'">ENH DVR</span>
													  </th>
													  <th ng-if="productVO.providerExtId =='27010360'">Total Mixed Bundle Price</th>
													 </tr>
												</thead>
												<tbody ng-repeat="(key, value) in priceTireObjectSyn track by $index" ng-if="$first">
													 <tr ng-repeat="(numTv,priceData) in value track by $index" ng-init="index=$index">
													  <td >
														<span class="tbl_bigtxt" ng-if="numTv=='TV1'">1 TV</span>
														<span class="tbl_bigtxt" ng-if="numTv=='TV2'">2 TV</span>
														<span class="tbl_bigtxt" ng-if="numTv=='TV3'">3 TV</span>
														<span class="tbl_bigtxt" ng-if="numTv=='TV4'">4 TV</span>
														<span class="tbl_bigtxt" ng-if="numTv=='TV5'">5 TV</span>
														<span class="tbl_bigtxt" ng-if="numTv=='TV6'">6 TV</span>
													  </td>
													  <td class="nopadng">
													   <div class="nopadng packgeDetails" ng-repeat="(k,v) in priceData track by $index">
														   <span class="dipsblck" ng-if="k=='M2M'">Monthly Price</span>
														   <span class="dipsblck" ng-if="k=='MTH'">Monthly Price</span>
														   <span class="dipsblck" ng-if="k=='A12M'">After 12 Months</span>
														   <span class="dipsblck" ng-if="k=='A24M'">After 24 Months</span>
													   </div>
													  </td>
													  <td class="nopadng" ng-repeat="(key1, value1) in priceTireObjectSyn track by $index">
													  <div ng-repeat="(numTv1,priceData1) in value1 track by $index">
														   <div class="nopadng packgeDetails" ng-repeat="(k1,v1) in priceData1 track by $index" ng-init="parentIndex=$parent.$index">
															   <span ng-if="parentIndex == index" class="dipsblck">{{v1}}</span>
														   </div>
													   </div>
													  </td>
													  <td class="nopadng" ng-repeat="(key1, value1) in priceTireObjectSynTotal track by $index">
													  <div ng-repeat="(numTv1,priceData1) in value1 track by $index">
														   <div class="nopadng packgeDetails" ng-repeat="(k1,v1) in priceData1 track by $index" ng-init="parentIndex=$parent.$index">
															   <span ng-if="parentIndex == index" class="dipsblck">{{v1}}</span>
														   </div>
													   </div>
													  </td>
													 </tr>
												</tbody>
											</table>
                                          	</span>
                                          </div>
                                       </div>
                                    </div>
                                    <div class="sync_part rtpart ltpad">
                                       <div class="tblink">
                                          <div class="tblink syncbdl_cont" >
                                             <span class="autottips">Sales Tips</span>
                                            
                                             <span class="leftbottompop salesTipId" >
                                             <span id='{{providerExtIdS}}' class="tip"> </span> 
                                             </span>
                                          </div>
                                       </div>
                                       <div class="tblink">
                                          <span class="autottips">Promotions</span>
                                          <span class="leftbottompop">
                                          <div class="popscroll">
                                                <div class="popupheadcont">{{pairedProductObject.providerName}}</div>
                                                <ul>
                                                   <li data-ng-if="pairedProductObject.productDescription != ''">{{pairedProductObject.promotionDescription}}</li>
                                                   <li data-ng-if="pairedProductObject.productDescription == ''">None</li>
                                                   <li data-ng-show='showCondtion(pairedProductObject)' data-ng-if='condition != "N/A" '>Terms and Conditions: {{condition}}</li>
                                                </ul>
                                             </div>
                                             <div class="popscroll">
                                                <div class="popupheadcont">{{productVO.providerName}}</div>
                                                <ul>
                                                   <li data-ng-if="productVO.productDescription != ''">{{productVO.promotionDescription}}</li>
                                                   <li data-ng-if="productVO.productDescription == ''">None</li>
                                                   <li data-ng-show='showCondtion(productVO)' data-ng-if='condition != "N/A" '>Terms and Conditions: {{condition}}</li>
                                                </ul>
                                             </div>
                                          </span>
                                       </div>
                                       <span>
                                          <span data-ng-if="productVO.providerExtId =='27010360'">
                                             <div class="tblink" ng-click="ngDisplayChannelLineUpData(productVO.parentProviderExtId,productVO.providerName,productVO)" data-ng-show ="({{productVO.isChannelLineupProvider}} == true) &&({{productVO.isVideoCapable}} == true)" id="product_{{productVO.providerExtId}}_{{productVO.productExID}}">Channel Lineup</div>
                                          </span>
                                       </span>
                                    </div>
                                    <div class="sync_part rtpart">
                                       <div class="totalpop">
                                          <table class="pricetbl">
                                           
                                             <tr>
                                                <td class="txtu">Base Price:</td>
                                                <td>
                                                   <span>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice != 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice == undefined"><span>{{productVO.totalPrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice != 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice != undefined"><span>{{productVO.totalDisplayBasePrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalDisplayPromotionPrice != undefined && productVO.totalDisplayBasePrice != undefined"><span>{{productVO.totalDisplayBasePrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalDisplayPromotionPrice != undefined && productVO.totalDisplayBasePrice == undefined"><span>{{productVO.totalPrice | currency}}</span></del>
                                                   
                                                   <span ng-if="productVO.totalPromotionPrice == 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice == undefined">
                                                   {{productVO.totalPrice | currency}}
                                                   </span>
                                                   <span ng-if="productVO.totalPromotionPrice == 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice != undefined ">
                                                   <span><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.totalDisplayBasePrice | currency}}</span>
                                                   </span>
                                                   </span>
                                                </td>
                                             </tr>
                                             <tr>
                                                <td></td>
                                                <td ng-if="productVO.totalDisplayPromotionPrice == undefined && productVO.totalPromotionPrice != 0 && productVO.totalDisplayBasePrice == undefined"><strong class="tblrtlnk lessprice nonDisplayIcon">{{productVO.totalPromotionPrice | currency}}</strong></td>
                                                <td ng-if="productVO.totalDisplayPromotionPrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png"><strong class="tblrtlnk lessprice">{{productVO.totalDisplayPromotionPrice | currency}}</strong></td>
                                                <td ng-if="productVO.totalDisplayPromotionPrice == undefined && productVO.totalPromotionPrice != 0 && productVO.totalDisplayBasePrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png"><strong class="tblrtlnk lessprice">{{productVO.totalPromotionPrice | currency}}</strong></td>
                                                
                                             </tr>
                                             <tr>
                                                <td class="txtu">Total Points:</td>
                                                <td><strong class="tblrtlnk">{{productVO.totalPoints}}</strong></td>
                                             </tr>
                                          </table>
                                          <span class="rightbottompop">
                                           
                                             <table class="poptbl">
                                                <tr>
                                                   <td><strong class="popupheadcont">Base Price:</strong></td>
                                                   <td class="displayIconBlock"><b class="popupheadcont">
		                                           <del class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice != 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice == undefined"><span>{{productVO.totalPrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalDisplayPromotionPrice != undefined && productVO.totalDisplayBasePrice != undefined"><span>{{productVO.totalDisplayBasePrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalDisplayPromotionPrice != undefined && productVO.totalDisplayBasePrice == undefined"><span>{{productVO.totalPrice | currency}}</span></del>
                                                   <del class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice != 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice != undefined"><span>{{productVO.totalDisplayBasePrice | currency}}</span></del>
                                                   
                                                   <span class="nonDisplayIcon" ng-if="productVO.totalPromotionPrice == 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice == undefined">
                                                   {{productVO.totalPrice | currency}}
                                                   </span>
                                                   <span ng-if="productVO.totalPromotionPrice == 0 && productVO.totalDisplayPromotionPrice == undefined && productVO.totalDisplayBasePrice != undefined ">
                                                   <span> <img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png"> {{productVO.totalDisplayBasePrice | currency}}</span>
                                                   </span>
		                                                   
		                                           <span class="dispricepop nonDisplayIcon"  ng-if="productVO.totalDisplayPromotionPrice == undefined && productVO.totalPromotionPrice != 0 && productVO.totalDisplayBasePrice == undefined">{{productVO.totalPromotionPrice | currency}}</span>
                                                   <span class="dispricepop" ng-if="productVO.totalDisplayPromotionPrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.totalDisplayPromotionPrice | currency}}</span>
                                                   <span class="dispricepop" ng-if="productVO.totalDisplayPromotionPrice == undefined && productVO.totalPromotionPrice != 0 && productVO.totalDisplayBasePrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.totalPromotionPrice | currency}}</span>
	                                                   </b>
                                                   </td>
                                                </tr>
                                                
                                                <tr>
                                                   <td class="thd">{{pairedProductObject.providerName}} : </td>
                                                   <td>
	                                                   
	                                                   <del class="nonDisplayIcon" ng-if="pairedProductObject.promoPrice != undefined && pairedProductObject.displayPromotionPrice == undefined && pairedProductObject.displayBasePrice == undefined">{{pairedProductObject.baseRecurringPrice | currency}} </del> 
	                                                   <del class="nonDisplayIcon" ng-if="pairedProductObject.promoPrice != undefined && pairedProductObject.displayPromotionPrice == undefined && pairedProductObject.displayBasePrice != undefined">{{pairedProductObject.displayBasePrice | currency}}</del> 
	                                                   <del class="nonDisplayIcon" ng-if="pairedProductObject.displayPromotionPrice != undefined  && pairedProductObject.displayBasePrice != undefined">{{pairedProductObject.displayBasePrice | currency}}</del>
	                                                   <del class="nonDisplayIcon" ng-if="pairedProductObject.displayPromotionPrice != undefined  && pairedProductObject.displayBasePrice == undefined">{{pairedProductObject.baseRecurringPrice | currency}}</del>
	                                                   
	                                                   <span class="nonDisplayIcon" ng-if="pairedProductObject.promoPrice == undefined && pairedProductObject.displayPromotionPrice == undefined && pairedProductObject.displayBasePrice == undefined">{{pairedProductObject.baseRecurringPrice | currency}}</span>
	                                                   <span ng-if="pairedProductObject.displayPromotionPrice ==undefined && pairedProductObject.displayBasePrice != undefined && pairedProductObject.promoPrice == undefined"><img class="displayIcon"  ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{pairedProductObject.displayBasePrice | currency}}</span>
	                                                   
	                                                   <span class="dispricepop nonDisplayIcon" ng-if="pairedProductObject.displayPromotionPrice ==undefined && pairedProductObject.promoPrice !=0">{{pairedProductObject.promoPrice | currency}}</span>
	                                                   <span class="dispricepop" ng-if="pairedProductObject.displayPromotionPrice !=undefined"><img class="displayIcon"  ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{pairedProductObject.displayPromotionPrice | currency}}</span>
	                                                   
	                                                   
                                                   </td>
                                                   
                                                </tr>
                                                <tr>
                                                   <td class="thd">{{productVO.providerName}} : </td>
                                                   <td>
                                                   		
	                                                    <del class="nonDisplayIcon" ng-if="productVO.promoPrice != undefined && productVO.displayPromotionPrice == undefined && productVO.displayBasePrice == undefined">{{productVO.baseRecurringPrice | currency}} </del> 
	                                                   <del class="nonDisplayIcon" ng-if="productVO.promoPrice != undefined && productVO.displayPromotionPrice == undefined && productVO.displayBasePrice != undefined">{{productVO.displayBasePrice | currency}}</del> 
	                                                   <del class="nonDisplayIcon" ng-if="productVO.displayPromotionPrice != undefined  && productVO.displayBasePrice != undefined">{{productVO.displayBasePrice | currency}}</del>
	                                                   <del class="nonDisplayIcon" ng-if="productVO.displayPromotionPrice != undefined  && productVO.displayBasePrice == undefined">{{productVO.baseRecurringPrice | currency}}</del>
	                                                   
	                                                   
	                                                   <span class="nonDisplayIcon" ng-if="productVO.promoPrice == undefined && productVO.displayPromotionPrice == undefined && productVO.displayBasePrice == undefined">{{productVO.baseRecurringPrice | currency}}</span>
	                                                   <span ng-if="productVO.displayPromotionPrice ==undefined && productVO.displayBasePrice != undefined && productVO.promoPrice == undefined"><img class="displayIcon"  ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.displayBasePrice | currency}}</span>
	                                                   
	                                                   <span class="dispricepop nonDisplayIcon" ng-if="productVO.displayPromotionPrice ==undefined && productVO.promoPrice !=0">{{productVO.promoPrice | currency}}</span>
	                                                   <span class="dispricepop" ng-if="productVO.displayPromotionPrice !=undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{productVO.displayPromotionPrice | currency}}</span>
	                                                   
                                                   </td>
                                                </tr>
                                             </table>
                                             <table class="poptbl">
                                                <tr>
                                                   <td class="thd"><strong class="popupheadcont">Total Points:</strong></td>
                                                   <td><b class="popupheadcont">{{productVO.totalPoints}}</b></td>
                                                </tr>
                                                <tr>
                                                   <td class="thd">{{pairedProductObject.providerName}} :</td>
                                                   <td>{{pairedProductObject.productPointDisplay}}</td>
                                                </tr>
                                                <tr>
                                                   <td class="thd">{{productVO.providerName}} :</td>
                                                   <td>{{productVO.productPointDisplay}}</td>
                                                </tr>
                                             </table>
                                          </span>
                                       </div>
                                    </div>
                                 </dir> -->
                              </div>
                           
                        </div>
                           <!-- Mock -->
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
                     <div class="leftbtns addToOrderBtn" id="addTOOrderBtn2Div" >
                  <input type="button" id="AddToOrderBtn2" value="Add to Order" ng-click="addMixedBundleToOrder()"/>&nbsp;&nbsp;	
                  <input type="button" id="addToOrderAndCKO" value="Add to Order and CKO" ng-click = "addSyntBdToOrderAndCKO()" />
               </div>
                     <div class="rightbtns">
                        <input type="button" id="backToDiscoveryId" name="backToDiscoveryId" value="< Back" onclick="backMBToDiscovery('backToDiscoveryEvent');"/>
                     </div>
                  </div>
               </div>
            </form>
         </section>
      </section>
     
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
                  <span class="callTime" id="startTimeTextMBProducts"></span>
               </div>
            </header>
            <!-- Details Section -->
            <section id="contentpdet">
               
            <div id="bmc_img" style="display:none;width:inherit;text-align:center;">
               <img src="<%=request.getContextPath()%>/images/spinner.gif"></img>
            </div>
            <input type="hidden" name="featuresJson" id="featuresJson" value='' /> 
            <input type="hidden" name="synthfeaturesJson" id="synthfeaturesJson" value='' />
            <input type="hidden" name="isAsync" id="isAsync" value='' />
            <input type="hidden" name="prodId" id="prodId" value="" /> 
	<div class="ProductDetails" >
	<div class="rtdiv" ng-show="showPriceInfo(selectedProductInfo)">
		<div class="totl">Total Points: <strong class="pointstxt"> {{totalPts}}
          <span class="pointstip">
          
              <table class="pp_poptbl">
	           <tr>
	              <td class="thd">{{selectedProductInfo.providerName}} :</td>
	              <td>{{selectedProductInfo.productPointDisplay}}</td>
	           </tr>
	           <tr>
	              <td class="thd">{{pairedProduct.providerName}} :</td>
	              <td>{{pairedProduct.productPointDisplay}}</td>
	           </tr>
         </table>
        </span>
        </strong>
      </div>
      
      <div class="totl">
      BASE INSTALLATION PRICE:
       <strong class="pricetxt">
       <span>
	       	<b class="popupheadcont">
	       			<span>{{totalInstallation | currency}}</span>
	       	</b>
       	</span>
          <span class="pricetip">
            <table class="pp_poptbl">
            <tbody>
                  <tr ><td class="thd">{{selectedProductInfo.providerName}} :</td><td>{{selectedProductInfo.baseNonRecurringPrice | currency}}</td></tr>
                  <tr ><td class="thd">{{pairedProduct.providerName}} :</td><td>{{pairedProduct.baseNonRecurringPrice | currency}}</td></tr>
               </tbody>
            </table>
         </span>
      </strong>
      </div>
	
      <div class="totl">
      BASE MONTHLY PRICE:
       <strong class="pricetxt">
       <span>
	       	<b class="popupheadcont">
	       		<del ng-if="promotionPr != 0 && totalDisplayPromotionPrice == 0"><span>{{totalPrice1 | currency}}</span></del>
	       		<del ng-if="totalDisplayPromotionPrice != 0 && totalDisplayBasePrice ==0"><span>{{totalPrice1 | currency}}</span></del>
                <del ng-if="totalDisplayPromotionPrice != 0 && totalDisplayBasePrice !=0"><span>{{totalDisplayBasePrice | currency}}</span></del>
	       		
	       		<span class="nonDisplayIcon" ng-if="promotionPr == 0 && totalDisplayPromotionPrice == 0 && totalDisplayBasePrice == 0">
	       			<span>{{totalPrice1 | currency}}</span>
	       		</span> 
	       		<span ng-if="promotionPr == 0 && totalDisplayPromotionPrice == 0 && totalDisplayBasePrice != 0">
	       			<img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png"><span>{{totalDisplayBasePrice | currency}}</span>
	       		</span> 
	       		<span class="finalprice" ng-if="totalDisplayPromotionPrice != 0">
	       		<img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{totalDisplayPromotionPrice | currency}}
	       		</span>
	       		<span class="finalprice" ng-if="promotionPr != 0 && totalDisplayPromotionPrice == 0"> {{promotionPr | currency}}</span>
	       	</b>
       	</span>
          <span class="pricetip">
            <table class="pp_poptbl">
            <tbody>
               <tr>
                  <td class="thd">{{selectedProductInfo.providerName}} : </td>
                  <td>
                  
                  
                  <del class="nonDisplayIcon" ng-if="selectedProductInfo.promoPrice != undefined && selectedProductInfo.displayPromotionPrice == undefined && selectedProductInfo.displayBasePrice == undefined">{{selectedProductInfo.baseRecurringPrice | currency}} </del> 
	          	 <del class="nonDisplayIcon" ng-if="selectedProductInfo.promoPrice != undefined && selectedProductInfo.displayPromotionPrice == undefined && selectedProductInfo.displayBasePrice != undefined">{{selectedProductInfo.displayBasePrice | currency}}</del> 
	              <del class="nonDisplayIcon" ng-if="selectedProductInfo.displayPromotionPrice != undefined  && selectedProductInfo.displayBasePrice != undefined">{{selectedProductInfo.displayBasePrice | currency}}</del>
	              <del class="nonDisplayIcon" ng-if="selectedProductInfo.displayPromotionPrice != undefined  && selectedProductInfo.displayBasePrice == undefined">{{selectedProductInfo.baseRecurringPrice | currency}}</del>
	                                                   
	              
                  
                  <span class="nonDisplayIcon" ng-if="selectedProductInfo.promoPrice == undefined && selectedProductInfo.displayPromotionPrice == undefined && selectedProductInfo.displayBasePrice == undefined">
                  <span>{{selectedProductInfo.baseRecurringPrice | currency}}</span>
                  </span> 
                  <span class="displayPriceIcon" ng-if="selectedProductInfo.promoPrice == undefined && selectedProductInfo.displayPromotionPrice == undefined && selectedProductInfo.displayBasePrice != undefined">
                  <span><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{selectedProductInfo.displayBasePrice | currency}}</span>
                  </span>
                  
                  <span class="dispricepop displayPriceIcon" ng-if="selectedProductInfo.displayPromotionPrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{selectedProductInfo.displayPromotionPrice | currency}}</span>
                  <span class="dispricepop nonDisplayIcon" ng-if="selectedProductInfo.promoPrice != undefined && selectedProductInfo.displayPromotionPrice == undefined">{{selectedProductInfo.promoPrice | currency}}</span>
                  </td>
                  
               </tr>
               <tr>
                  <td class="thd">{{pairedProduct.providerName}} : </td>
                  <td>
                  <del class="nonDisplayIcon" ng-if="pairedProduct.promoPrice != undefined && pairedProduct.displayPromotionPrice == undefined && pairedProduct.displayBasePrice == undefined">{{pairedProduct.baseRecurringPrice | currency}} </del> 
	          	 <del class="nonDisplayIcon" ng-if="pairedProduct.promoPrice != undefined && pairedProduct.displayPromotionPrice == undefined && pairedProduct.displayBasePrice != undefined">{{pairedProduct.displayBasePrice | currency}}</del> 
	              <del class="nonDisplayIcon" ng-if="pairedProduct.displayPromotionPrice != undefined  && pairedProduct.displayBasePrice != undefined">{{pairedProduct.displayBasePrice | currency}}</del>
	              <del class="nonDisplayIcon" ng-if="pairedProduct.displayPromotionPrice != undefined  && pairedProduct.displayBasePrice == undefined">{{pairedProduct.baseRecurringPrice | currency}}</del>
	               

                  <span class="nonDisplayIcon" ng-if="pairedProduct.promoPrice == undefined && pairedProduct.displayPromotionPrice == undefined && pairedProduct.displayBasePrice == undefined">
                  <span>{{pairedProduct.baseRecurringPrice | currency}}</span>
                  </span> 
                  <span ng-if="pairedProduct.promoPrice == undefined && pairedProduct.displayPromotionPrice == undefined && pairedProduct.displayBasePrice != undefined">
                  <span><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{pairedProduct.displayBasePrice | currency}}</span>
                  </span>
                  <span class="dispricepop" ng-if="pairedProduct.displayPromotionPrice != undefined"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png">{{pairedProduct.displayPromotionPrice | currency}}</span>
                   <span class="dispricepop nonDisplayIcon" ng-if="pairedProduct.promoPrice != undefined && pairedProduct.displayPromotionPrice == undefined">{{pairedProduct.promoPrice | currency}}</span>
                  </td>
               </tr>
               </tbody>
            </table>
         </span>
      </strong>
      </div>
      
      
      <table style="width: 980px;height: 75px;" data-ng-if="productDetailsArr != undefined">
		<tr>
			<th class="providerDetails_td"
				style="width: 109px; height: 53px"></th>
			<th style="text-align: center;" class="providerOne_td"
				style="width:449px; height:53px"><img
				 style="max-height:50px;">
			</th>
			<th style="text-align: center;" class="providerTwo_td"
				style="width:429px; height:53px"><img
				 style="max-height:50px;">
			</th>
		</tr>
	</table>
      
      
</div>
	 
			 <table class="vdtbl" style="width: 980px;" class="forselect">
							<tr>
								<td class="grnbg providerDetails_td">Product Name</td>
								<td class="providerOne_td"
									ng-bind-html="productDetailsArr[0].name | unsafe"></td>
								<td class="providerTwo_td"
									ng-bind-html="productDetailsArr[1].name | unsafe"></td>
							</tr>

							<tr>
								<td class="grnbg providerDetails_td">Sales Tips</td>
								<td valign="top" class="fstProviderST providerOne_td"></td>
								<td valign="top" class="secProviderST providerTwo_td"></td>
							</tr>

							<tr class="promation_viewDetails">
								<td class="grnbg providerDetails_td">Promotions</td>
								<td class="providerOne_td" valign="top"
									ng-bind-html="productDetailsArr[0].promotions | unsafe"></td>
								<td class="providerTwo_td" valign="top"
									ng-bind-html="productDetailsArr[1].promotions | unsafe"></td>
							</tr>

							<tr class="features_viewDetails">
								<td class="grnbg providerDetails_td">Features</td>
								<td class="lespadng providerOne_td" valign="top"
									ng-bind-html="productDetailsArr[0].features | unsafe"></td>
								<td valign="top" class="lespadng providerTwo_td"
									ng-bind-html="productDetailsArr[1].features | unsafe"></td>
							</tr>

							<tr>
								<td class="grnbg providerDetails_td">Highlights</td>
								<td class="providerOne_td" valign="top"
									ng-bind-html="productDetailsArr[0].marketing | unsafe"></td>
								<td class="providerTwo_td" valign="top"
									ng-bind-html="productDetailsArr[1].marketing | unsafe"></td>
							</tr>

							<tr>
								<td class="grnbg providerDetails_td">Description</td>
								<td class="providerOne_td" valign="top"
									ng-bind-html="productDetailsArr[0].longDescription | unsafe"></td>
								<td class="providerTwo_td" valign="top"
									ng-bind-html="productDetailsArr[1].longDescription | unsafe"></td>
							</tr>
						</table>
			
			</div>
            </section>
         </section>
      </div>
   </div>
   <div id="domMessage" style="display:none;">
      <img src="<%=request.getContextPath()%>/images/loading.gif" border="0"/>
      <h2>Loading</h2>
   </div>
</div>

 