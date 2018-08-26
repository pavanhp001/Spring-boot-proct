

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
   <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
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
   <script type="text/javascript">
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
  	 var prodDetailsArray = [];
      var productVOJSONList = ${productVOJSONList}; 
      var syntheticMap = ${syntheticMap}; 
      var isSyntheticBundle = ${isSyntheticBundle}; 
      var noOfOneTimePromotionsSelected = 0;
      var noOfMonthlyPromotionsSelected = 0;
      var providerIMGLocation = '${providersImageLocation}';
      var internetRangeJSONValues = '<fmt:message key="internetValInJsonFrmt"/>';
      var channelsRangeJSONValues = '<fmt:message key="channelValinJsonFrmt"/>';
      var allProviderList = {};
      var iFilterClick = "";
      var tempPersistData = {};
      var ATT_PROVIDER_ID = '15500201'; 
      var DirecTV_NAME  = 'DirecTV';
      
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

         angular.module('concert').config(['$compileProvider', function($compileProvider) {
        	  $compileProvider.debugInfoEnabled(false);
        	}]);
      angular.module('concert').controller('recommendationController', ['$scope','$http','$window','$q','$timeout','$element', function($scope,$http,$window,$q,$timeout,$element) {
    	  
      	$scope.contextPath = $("input#contextPath").val();
   		console.time("RecommendationController=");
    	
      	$scope.productVOJSONList = productVOJSONList;
      	$scope.syntheticMap = ${syntheticMap}; 
      	$scope.isSyntheticBundle = ${isSyntheticBundle};
      	$scope.pairedProductObject="";
      	
          externalScope = $scope;
          $scope.providerIMGLocation = providerIMGLocation;
          $scope.showPointsDisplay = function(pointData){
          	if(pointData == "NA" || pointData == "0.0" ){
          		$scope.pointsDisplay = "See Intranet";
          	}else{
          		$scope.pointsDisplay = pointData;
          	}
          	return true;
          }
          $scope.init = function(){
        	  $scope.syntheticMap = ${syntheticMap};
          	if(selectedProviderID != "" ){
          		tempPersistData =  {'provider':selectedProviderID};
          		$scope.filteringAjaxCall({'provider':selectedProviderID});
              }else if( catPersitFilterJOSN != undefined){
              	//catPersitFilterJOSN value intailize realtimecontent.jsp for filter logic excute on page load
              	$scope.filteringAjaxCall(catPersitFilterJOSN);   
              	tempPersistData =  catPersitFilterJOSN;
              }else{
              	$scope.filteringAjaxCall({}); 
              }
          },
          $scope.showCondtion = function(productVO){
   		   if(productVO.condition != undefined && productVO.condition !="" && productVO.condition != null){
   			   $scope.condition = productVO.condition;
   		   }else{
   			   $scope.condition = "N/A";
   		   }
   	  return true;
	   },
	   $scope.syntheticViewDetailsClick = function (object){
		   prodDetailsArray = [];
		   $scope.selectedProductInfo = object;
                $scope.isShowProductDetails = false;
                $("#ProductDetails").hide();
                try{
            prodDetailsArray.push(JSON.parse(object.hiddenProductJSON));
            prodDetailsArray.push(JSON.parse(JSON.parse(object.pairedProduct).hiddenProductJSON));
            var deferred = $q.defer();
            deferred.resolve(syntheticViewDetails(prodDetailsArray,""));
            $scope.productDetailsArr = JSON.parse(productDetailsJson);
            console.log("$scope.productDetailsArr"+$scope.productDetailsArr)
              $(".providerOne_td").attr("src"," https://s3.amazonaws.com/AL-content/common/provider/logos/"+productDetailsArr[0].detailsImage+".jpg");
              $(".providerTwo_td").attr("src"," https://s3.amazonaws.com/AL-content/common/provider/logos/"+productDetailsArr[1].detailsImage+".jpg");
              
          	$('.fstProviderST').find("#"+$scope.productDetailsArr[0].detailsImage).css('display','block');
      		$('.secProviderST').find("#"+$scope.productDetailsArr[1].detailsImage).css('display','block');
      		$(".fstProviderST").find(".popupheadcont").hide();
      		$(".secProviderST").find(".popupheadcont").hide();
                $("#ProductDetails").show();
                $timeout(function(){
                 	 savePageHtml(true, "");
                },500);
                }catch(e){
                    alert(e);
                }
        },
        $scope.buildPriceTire = function(pricingGridJson){
        	 $scope.priceTireObject = JSON.parse(pricingGridJson)
        	return true;
        },
        $scope.iconList = function(IconObj){
        	 $scope.displayStyleDouble = "";
        	 $scope.displayStyleTriple="";
        	if(IconObj != undefined){
        		var list = JSON.stringify(IconObj).split(",")
        		if(list.length ==2 && JSON.stringify(IconObj).indexOf("tv") != -1){
        			 $scope.displayStyleDouble = "displayStyleDouble";
        		}
        		
        		if(list.length ==3 && JSON.stringify(IconObj).indexOf("tv") != -1){
       			 $scope.displayStyleTriple = "displayStyleTriple";
       			}
        		
        	}
        },
        
        $scope.buildPriceTireSyn = function(displayPricingGrid, bundlePrice){
          	$scope.priceTireObjectSyn = JSON.parse(displayPricingGrid);
          	$scope.priceTireObjectSynTotal = JSON.parse(bundlePrice);
          	return true;
          },
          $scope.buildSynPriceGrid = function(priceTireObject, index){
        		  buildPriceGrid(priceTireObject,index);
            	return true;
            },
           
        $scope.addMixedBundleToOrder = function(){
        		$("#AddToOrderBtn3").attr("disabled","disabled");
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
                
                data["mixedBundleTime"]= totalFormat.trim();
                data["productData1"] = JSON.stringify(selectedArray);
                data["productData2"] =  JSON.stringify(selectedArray1);
                   
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
                       
               },
               $scope.addSyntBdToOrderAndCKO = function(){
                   $.blockUI({ message: $('#domMessage') }); 
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
                     	console.log("error "+err);
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
       	   
       	  return true;
   	   },
   	   $scope.showDiv = function(productVO){
   		 if(productVO != undefined && productVO.pairedProduct != undefined){
   			 return true;
   		 }else{
   			 return false;
   		 }
   	   },
   	   $scope.showPairedProduct = function(productVO){
   		 $scope.isPairedProduct=false;
   		 $scope.providerExtIdS="" ;
   			var productJson = JSON.parse(productVO.pairedProduct);
   			if(productVO.providerExtId != undefined && productVO.providerExtId !="" && productVO.providerExtId != null){
    			   $scope.providerExtIdS = $scope.providerExtIdS +"_"+productVO.imageID +"_"+productJson.imageID;
    		   }
   			$scope.pairedProductObject = productJson;
   			$scope.pairedProductProductIconList = productJson.productIconList;
   			$scope.pairedProductName = productJson.productName;
   			$scope.pairedProductImageID = productJson.imageID;
   			$scope.pairedProductProductPointDisplay = productJson.productPointDisplay;
   			$scope.pairedProductProductExID = productJson.productProductExID;
   			$scope.pairedProductDescription = productJson.productDescription;
   		
   			$scope.isPairedProduct=true;
   			return true;
   	   },$scope.singleProductData = function(productVO,index){
   			singleProductDataBuild(productVO,index);
   			return true;
   	   },$scope.bundleProductData = function(productVO,index){
   			bundleProductDataBuild(productVO,index);
   			return true;
   	   },
   	 $scope.showProduct = function(productVO){
     		if(productVO != undefined && productVO.pairedProduct != undefined){
    			 return false;
    		 }else{
    			 return true;
    		 }
     	   },
          $scope.filteringAjaxCall = function filteringAjaxCall(data){
          	try{
          	var sortProductVOList = [];
          	var tempProductVOlit = productVOJSONList;
          	var isFilterStart = false ;
          	var internetRangeList  = [];
              var channleRangeList = [];
              var contractTermList = [];
              var isLatinoShow = '';
              var isEnglishShow = '';
              var providerFilterMap = {};	
              var isUncheck = "";
          	var sortOfferOption = $(".sortOffer option:selected").val();
          	 if(data['sortOfferOption'] != undefined && data['sortOfferOption'] != ''){
          		 sortOfferOption = data['sortOfferOption'];
          		 $(".sortOffer").val(sortOfferOption);
               }
          	 $('input[class^=checkbox_filtter_content]:checked').each(function(){
      	 			var filter_seq = $( this ).attr("filter_seq");
      	 			var value = $(this).val();
      	 			if(data[filter_seq] != undefined){
      	 				data[filter_seq] = data[filter_seq]+","+value;
      	 			}else{
      	 				data[filter_seq] = value;
      	 			}
      	 		});
      	 	if(Object.keys(allProviderList).length == 0){
      	 		allProviderList =  JSON.parse(filterProviderNamesAndExtIdsMap);
      	 		$.each(productVOJSONList, function(index, productVO){
      	    		 allProviderList[productVO.providerExtId] = productVO['providerName'];
      	         });
      	 	}
          	 var currentData = JSON.stringify(data);
      
          	if(data != undefined)
              {
          		if(data['provider'] == undefined 
                 	       	&& data['channels'] == undefined 
                 	       	    && data['contractTerm'] == undefined 
                 	        	       && data['internetSpeed'] == undefined
                 	        	       && data['latino'] == undefined ){
          			tempPersistData = {};
                  }
          		 if(iFilterClick != ""){
          			 
              		 if(currentFilter == 'provider' && data["provider"] == undefined){
               			 isUncheck = "Yes";
               			tempPersistData['provider'] = undefined;
                  	 }else if(currentFilter != "" && currentFilter != 'provider' && data["provider"] != undefined){
                  		 data['provider'] = tempPersistData['provider'];
              	     }
              	     
              		 if(currentFilter == 'channels' && data["channels"] == undefined){
              			 isUncheck = "Yes";
              			 tempPersistData['channels'] = undefined;
              		 }else if(currentFilter != "" && currentFilter != 'channels' && tempPersistData["channels"] != undefined){
              			 data['channels'] = tempPersistData['channels'];
              	     }
      
              		 if(currentFilter == 'internetSpeed' && data["internetSpeed"] == undefined){
              			 isUncheck = "Yes";
              			 tempPersistData['internetSpeed'] = undefined;
              		 }else if(currentFilter != "" && currentFilter != 'internetSpeed' && tempPersistData["internetSpeed"] != undefined){
              			 data['internetSpeed'] = tempPersistData['internetSpeed'];
              	     }
      
              		 if(currentFilter == 'contractTerm' && data["contractTerm"] == undefined){
              			 isUncheck = "Yes";
              			 tempPersistData['contractTerm'] = undefined;
              		 }else if(currentFilter != "" && currentFilter != 'contractTerm' && tempPersistData["contractTerm"] != undefined){
              			 data['contractTerm'] = tempPersistData['contractTerm'];
              	     }
              		 if(currentFilter == 'latino' && data["latino"] == undefined){
              			 isUncheck = "Yes";
              			 tempPersistData['latino'] = undefined;
              		 }else if(currentFilter != "" && currentFilter != 'latino' && tempPersistData["latino"] != undefined){
              			 data['latino'] = tempPersistData['latino'];
              	     }
              		 
              		 if(isUncheck == "Yes"){
              			 data = tempPersistData;
                       }else{
                      	 tempPersistData = data;
                       }
              	 }else{
              		 tempPersistData = {};
                   }
          		//provider
          		if(data["provider"] != undefined){
          			 $.each(data["provider"].split(","),function(index, providerID){
          				 $.each(tempProductVOlit, function(index, productVO) {
              				 if(providerID == ATT_PROVIDER_ID 
                  				 &&  productVO.providerExtId == ATT_PROVIDER_ID
                  				 &&  productVO.capabilitMap != undefined 
                  				 &&  !isATTBuildYourBundle(productVO)
                  				 &&  !isATTHasSatellite(productVO)){
              					 sortProductVOList.push(productVO);
                  			 }else if(providerID == DirecTV_NAME
      			        	        && productVO.providerExtId == ATT_PROVIDER_ID 
      			        	        &&  productVO.capabilitMap != undefined 
      			        	        && !isATTBuildYourBundle(productVO)
          			        	    && isATTHasSatellite(productVO)){
                  				 sortProductVOList.push(productVO);
                      		  }else if(providerID != DirecTV_NAME && providerID != ATT_PROVIDER_ID && productVO.providerExtId == providerID){
                      			  sortProductVOList.push(productVO);
                          	  }
          					});
          				});
          		        	tempProductVOlit = sortProductVOList;
          		        	sortProductVOList = [];
          	     }
          		
          		//channels
          		if(data["channels"] != undefined){
          			 $.each(data["channels"].split(","),function(index, channelValue){
          				 $.each(tempProductVOlit, function(index, productVO) {
          		        		if(productVO.filterMetaDatMap['NUM_CHANNELS'] != undefined && parseFloat(productVO.filterMetaDatMap['NUM_CHANNELS'])){
          		        			var parameterValues = channelValue.split("-");
          		        			if(parameterValues.length == 2 
          				        			&& parseFloat(parameterValues[0]) 
          				        			           && parseFloat(parameterValues[1])){
          		        				if( (parseFloat(parameterValues[0]) <= parseFloat(productVO.filterMetaDatMap['NUM_CHANNELS'])) 
          				        				          && (parseFloat(productVO.filterMetaDatMap['NUM_CHANNELS']) <= parseFloat(parameterValues[1]))){
          				        			sortProductVOList.push(productVO);
          				        		}
          		        			}
          			        	}
          					});
          				});
          		        	tempProductVOlit = sortProductVOList;
          		        	sortProductVOList = [];
          		 }
      
                 // internet Speed Sort
          		 if(data["internetSpeed"] != undefined){
          			 $.each(data["internetSpeed"].split(","),function(index, internetVal){
          				 $.each(tempProductVOlit, function(index, productVO) {
          		        		if(productVO.filterMetaDatMap['CONN_SPEED'] != undefined && parseFloat(productVO.filterMetaDatMap['CONN_SPEED']) >= 0){
          		        			var parameterValues = internetVal.split("-");
          		        			if(parameterValues.length == 2 && parseFloat(parameterValues[0])>= 0 && parseFloat(parameterValues[1])){
          		        				if( (parseFloat(parameterValues[0]) <= parseFloat(productVO.filterMetaDatMap['CONN_SPEED'])) 
          				        				           && (parseFloat(productVO.filterMetaDatMap['CONN_SPEED']) <= parseFloat(parameterValues[1]))){
          				        			sortProductVOList.push(productVO);
          				        		}
          		        			}
          			        	}
          					});
          				});
          			    tempProductVOlit = sortProductVOList;
          	        	sortProductVOList = [];
          		 }
          			
          		// contractTerm sort logic
                 if(data["contractTerm"] != undefined){
              	 $.each(data["contractTerm"].split(","),function(index, termVal){
          	       	$.each(tempProductVOlit, function(index, productVO) {
          	  				if(productVO.filterMetaDatMap.CONTRACT_TERM == termVal ){
          	  					sortProductVOList.push(productVO);
          	  	   			}
          				});
                     });
              	  tempProductVOlit = sortProductVOList;
               	  sortProductVOList = [];
                 }
      
             	// latino
          	  if(data["latino"] != undefined && data["latino"] == 'yes' || data["latino"] == 'no'){
          	       	$.each(tempProductVOlit, function(index, productVO) {
          	  				if("yes" == data["latino"] && productVO.filterMetaDatMap['LATINO_PKG_INCL'] != undefined){
          	  					sortProductVOList.push(productVO);
          	  					 isLatinoShow = "Yes";
          	  	   			}else if("no" == data["latino"] && productVO.filterMetaDatMap['LATINO_PKG_INCL'] == undefined){
          	  	   			      isEnglishShow = "Yes";
          	  	   			    sortProductVOList.push(productVO);
          	  	   	   		}
          				});
          			       	tempProductVOlit = sortProductVOList;
          			       	sortProductVOList = [];
          	  }
          	}else{
          		currentFilter = "";
          		iFilterClick = "";
          		catPersitFilterJOSN = {};
          		data = {};
          		tempPersistData = {};
          		isLatinoShow = "";
          		isEnglishShow = "";
          		console.log("data undefined");
              }
      
          		//sort Price_High_to_Low
                 if("Price_High_to_Low" == sortOfferOption){
              	   tempProductVOlit.sort(function(obj1,obj2){
              		   if(obj1.sortPrice > obj2.sortPrice){
              			   return -1;
                  	  }else if(obj1.sortPrice < obj2.sortPrice){
                  		  return 1;
                        }else{
                            return 0;
                        }
                     });
                 }
                
               //sort Price_Low_to_High
                 if("Price_Low_to_High" == sortOfferOption){
              	   tempProductVOlit.sort(function(obj1,obj2){
              		   if(obj1.sortPrice > obj2.sortPrice){
              			   return 1;
                  	  }else if(obj1.sortPrice < obj2.sortPrice){
                  		  return -1;
                        }else{
                            return 0;
                        }
                     });
                 }
                 
               //sort Interent_Speed_Low_High
                 if("Interent_Speed_Low_High" == sortOfferOption){
              	   tempProductVOlit.sort(function(obj1,obj2){
              		 var CONN_SPEED1 = obj1.connSpeed;
              		 var CONN_SPEED2 = obj2.connSpeed;
  					
              		   if(CONN_SPEED1 != undefined 
              	    		   && CONN_SPEED2 != undefined){
              			   if(parseFloat(CONN_SPEED1) > parseFloat(CONN_SPEED2)){
                  			   return 1;
                      	  }else if(parseFloat(CONN_SPEED1) < parseFloat(CONN_SPEED2)){
                      		  return -1;
                            }else{
                                return 0;
                            }
                         }
                     });
                 }
               //sort Interent_Speed_High_Low 
                 if("Interent_Speed_High_Low" == sortOfferOption){
              	   tempProductVOlit.sort(function(obj1,obj2){
              		   
              		 var CONN_SPEED1 = obj1.connSpeed;
              		 var CONN_SPEED2 = obj2.connSpeed;
              		   
              		   if(CONN_SPEED1 != undefined 
              	    		   && CONN_SPEED2 != undefined){
              			   if(parseFloat(CONN_SPEED1) > parseFloat(CONN_SPEED2)){
                  			   return -1;
                      	  } else if(parseFloat(CONN_SPEED1) < parseFloat(CONN_SPEED2)){
                      		  return 1;
                            }else{
                                return 0;
                            }
                         }
                     });
                 }
               //sort Channels_High_Low
                 if("Channels_High_Low" == sortOfferOption){
              	   tempProductVOlit.sort(function(obj1,obj2){
              		  var NUMCHANNELS1 = obj1.channels;
              		  var NUMCHANNELS2 = obj2.channels;
              		
              		   if(NUMCHANNELS1 != undefined 
              	    		   && NUMCHANNELS2 != undefined){
              			   if(NUMCHANNELS1 > NUMCHANNELS2){
                  			   return -1;
                      	  }else if(NUMCHANNELS1 < NUMCHANNELS2){
                      		  return 1;
                            }else{
                                return 0;
                            }
                         }
                     });
                 }
      
               //sort Channels_Low_High
                 if("Channels_Low_High" == sortOfferOption){
              	   tempProductVOlit.sort(function(obj1,obj2){
              		   
              		 var NUMCHANNELS1 = obj1.channels;
             		  var NUMCHANNELS2 = obj2.channels;
              		
              		   if(NUMCHANNELS1 != undefined 
              	    		   && NUMCHANNELS2 != undefined){
              			   if(NUMCHANNELS1 > NUMCHANNELS2){
                  			   return 1;
                      	  }else if(NUMCHANNELS1 < NUMCHANNELS2){
                      		  return -1;
                      		  }
                           else{
                                return 0;
                            }
                         }
                     });
                 }
      
               //sort Plan_A_Z
                 if("Plan_A_Z" == sortOfferOption){
              	   tempProductVOlit.sort(function(obj1,obj2){
             	        return (obj2.productName.toUpperCase() > obj1.productName.toUpperCase() ) ? -1 : 1; 
                    });
                 }
               //sort Plan_Z_A
                 if("Plan_Z_A" == sortOfferOption){
              	   tempProductVOlit.sort(function(obj1,obj2){
              	        return (obj2.productName.toUpperCase() < obj1.productName.toUpperCase() ) ? -1 : 1; 
                     });
                 }
      
               //sort Recommended
                 if("Recommended" == sortOfferOption){
              	   tempProductVOlit.sort(function(obj1,obj2){
              		   if(parseFloat(obj1.productScore) > parseFloat(obj2.productScore)){
              			   return -1;
                  	  }else  if(parseFloat(obj1.productScore) < parseFloat(obj2.productScore)){
                  		  return 1;
                        }else{
                            return 0;
                        }
                     });
                 }
                 //  we are going to adding AT&T Build Your Bundle prduct add into filter list, when if ATT, DirecTV filter selctec and with out privder filter select. 
                 if((data["provider"] == undefined
                         || isATTDirecTVFilterSelect(data)) 
                         && (allProviderList[ATT_PROVIDER_ID] != undefined 
                              || allProviderList[DirecTV_NAME] != undefined) 
                            ){
                      var isATTBUILDProductExit = false;
                      if(tempProductVOlit != undefined && tempProductVOlit.length != 0){
                         $.each(tempProductVOlit, function(index, productVO) {
                            if(isATTBuildYourBundle(productVO)){
                             isATTBUILDProductExit = true;
                            }
                        }); 
                     }else{
                       tempProductVOlit = [];
                    }
                     if(!isATTBUILDProductExit){
                      $.each(productVOJSONList, function(index, productVO) {
                       if(isATTBuildYourBundle(productVO)){
                        tempProductVOlit.push(productVO);
                          }
                      });
                     }
                 }
                 
               // Based on Product Results dynamically changing filter options
                 $.each(tempProductVOlit, function(index, productVO) {
                     //Build channels filter options
              	   if(productVO.filterMetaDatMap['NUM_CHANNELS'] != undefined 
                      	   && parseFloat(productVO.filterMetaDatMap['NUM_CHANNELS'])){
                       $.each(JSON.parse(channelsRangeJSONValues),function(index,channelRange){
      	                 if(channelRange != '' 
      		                 && channelRange.split("-").length == 2 
      		                 && parseFloat(channelRange.split("-")[1]) 
      		                         && parseFloat(channelRange.split("-")[0])){
      	                	 if( parseFloat(productVO.filterMetaDatMap['NUM_CHANNELS']) >=  parseFloat(channelRange.split("-")[0]) 
      	    	                	    && parseFloat(productVO.filterMetaDatMap['NUM_CHANNELS']) <= parseFloat(channelRange.split("-")[1]) ){
      	                		 if(channleRangeList.indexOf(channelRange) == -1){
      	                		    channleRangeList.push(channelRange);
      	                		 }
      	                	 } 
      	                 }
                       });
                     }
              	   //Build internet filter options
              	   if(productVO.filterMetaDatMap['CONN_SPEED'] != undefined 
                      	   && parseFloat(productVO.filterMetaDatMap['CONN_SPEED']) >= 0 ){
      			        	   $.each(JSON.parse(internetRangeJSONValues),function(index,internetRange){
      				                 if(internetRange != '' 
      					                 && internetRange.split("-").length == 2 
      					                 && parseFloat(internetRange.split("-")[1]) 
      					                         && parseFloat(internetRange.split("-")[0])>= 0){
      				                	 if( parseFloat(productVO.filterMetaDatMap['CONN_SPEED']) >=  parseFloat(internetRange.split("-")[0]) 
      				    	                	    && parseFloat(productVO.filterMetaDatMap['CONN_SPEED']) <= parseFloat(internetRange.split("-")[1]) ){
      				                		 if(internetRangeList.indexOf(internetRange) == -1){
      				                			 internetRangeList.push(internetRange);
      				                		 }
      				                	 } 
      				                 }
      			               });
              	    }
              	    //Build contract filter options
              	      if(productVO.filterMetaDatMap['CONTRACT_TERM'] != undefined){
              	    	  if(contractTermList.indexOf(productVO.filterMetaDatMap['CONTRACT_TERM']) == -1){
              	    		  contractTermList.push(productVO.filterMetaDatMap['CONTRACT_TERM']);
      	   	              }
                       }
                        //Check whether Latino Product is available
              	      if(isLatinoShow == '' && productVO.filterMetaDatMap['LATINO_PKG_INCL'] != undefined){
              	    	  isLatinoShow = "Yes";
                        }
              	      if(isEnglishShow == '' && productVO.filterMetaDatMap['LATINO_PKG_INCL'] == undefined){
              	    	  isEnglishShow = "Yes";
                        }
                        //build ProviderName Filter
                        if(productVO['providerName'] != undefined  
                                && productVO.providerExtId != undefined){
                      	  if( productVO.providerExtId == ATT_PROVIDER_ID 
      			        	      && isATTHasSatellite(productVO)){
                   		          providerFilterMap[DirecTV_NAME] = DirecTV_NAME;
      			         }else if(providerFilterMap[productVO.providerExtId] == undefined){
      			        	 providerFilterMap[productVO.providerExtId] = productVO['providerName'];
      	 			     }
                        }
                 });
                 
              	  if(iFilterClick == "" && currentFilter == '' && data['provider_currentFilter'] != undefined){
                  	  var providerData = data['provider_currentFilter'];
                  	  var providerDataStr = providerData.split(",");
                  	  var providerDataObj = {};
                  	  $.each(providerDataStr,function(index,val){
                      	  var providerEqObj = val.split("=");
                  		  providerDataObj[providerEqObj[0]] = providerEqObj[1];
                       });
                  	  providerFilterMap = providerDataObj;
                    }
                     if(iFilterClick == "" && currentFilter == '' && data['channels_currentFilter'] != undefined){
                      var channelData = data['channels_currentFilter'];
                  	channleRangeList = channelData.split(",");
                    }
                      if( iFilterClick == "" && currentFilter == '' && data['internetSpeed_currentFilter'] != undefined){
            			var internetData = data['internetSpeed_currentFilter'];
            			internetRangeList = internetData.split(",");
                    }
                       if( iFilterClick == "" && currentFilter == '' && data['contractTerm_currentFilter'] != undefined){
            			var contractData = data['contractTerm_currentFilter'];
            			contractTermList = contractData.split(",");
                    }
                     if( iFilterClick == "" && currentFilter == '' && data['latino_currentFilter'] != undefined){
                  	  if("yes" == data["latino_currentFilter"]){
      	  					 isLatinoShow = "Yes";
      	  	   		   }else if("no" == data["latino_currentFilter"]){
      	  	   			      isEnglishShow = "Yes";
      	  	   	   	   }else{
      	  	   	   	       isEnglishShow = "Yes";
      	                   isLatinoShow = "Yes";
      			  	   }
                   }
                   if(currentFilter != ''
                            &&  data['internetSpeed'] == undefined 
                             &&  data['contractTerm'] == undefined 
                              && data['channels'] == undefined 
                                && data['latino'] == undefined 
                                && data['provider'] != undefined ){
                  	  providerFilterMap = allProviderList;
                  	  
                    }
      
                     $scope.productVOJSONList = tempProductVOlit;
                     if(isUncheck == "Yes"){
                     	currentData = JSON.stringify(data);
                     }
                     currentData = JSON.parse(currentData);
      
                 currentData['provider_currentFilter'] = undefined;
                 currentData['channels_currentFilter'] = undefined;
                 currentData['internetSpeed_currentFilter'] = undefined;
                 currentData['contractTerm_currentFilter'] = undefined;
                 currentData['latino_currentFilter'] = undefined;
                 
                //Current Filter Persist resetFiltersOptions(channleRangeList,contractTermList,internetRangeList,isLatinoShow,providerFilterMap,data,isEnglishShow);
                 if(currentFilter != '' && currentFilter == 'provider' && data['provider'] != undefined){
                     var providerData = realTimeScope.filterProviderNamesAndExtIdsMap;
                     var providerStr = "";
                      $.each(providerData,function(key,value){
                      	if(providerStr == ""){
                      		providerStr = key+"="+value;
                          }else{
                          	providerStr = providerStr+","+key+"="+value;;
                          }
                      });
                      currentData['provider_currentFilter'] = providerStr;
                 }else if(data['provider'] != undefined){
              	   var providerData = providerFilterMap;
                     var providerStr = "";
                      $.each(providerData,function(key,value){
                      	if(providerStr == ""){
                      		providerStr = key+"="+value;
                          }else{
                          	providerStr = providerStr+","+key+"="+value;;
                          }
                      });
                      currentData['provider_currentFilter'] = providerStr;
                 }
                  if(currentFilter != '' && currentFilter == 'channels' && data['channels'] != undefined){
                  	currentData['channels_currentFilter'] =  realTimeScope.channleRangeList+"";
                  }else if(data['channels'] != undefined){
                  	currentData['channels_currentFilter'] =  channleRangeList+"";
                  }
      
                   if(currentFilter != '' && currentFilter == 'internetSpeed' && data['internetSpeed'] != undefined){
                  	currentData['internetSpeed_currentFilter'] = realTimeScope.internetRangeList+"";
                  }else if(data['internetSpeed'] != undefined){
                  	currentData['internetSpeed_currentFilter'] = internetRangeList+"";
                  }
      
                 if(currentFilter != '' && currentFilter == 'contractTerm'&& data['contractTerm'] != undefined){
                  	currentData['contractTerm_currentFilter'] =  realTimeScope.contractTermList+"";
                  }else if(data['contractTerm'] != undefined){
                  	currentData['contractTerm_currentFilter'] =  contractTermList+"";
                 }
                  if(currentFilter != '' && currentFilter == 'latino' && data['latino'] != undefined){
                      if($('.englishLatino').is(':visible') && $('.latinoFilterDiv').is(':visible')){
                      	currentData['latino_currentFilter'] =  "no,yes";
                      }else{
                      	currentData['latino_currentFilter'] =  data['latino']+"";
                      }
                  }else if(data['latino'] != undefined){
                  	if(isEnglishShow == "Yes" && isLatinoShow == "Yes"){
                      	currentData['latino_currentFilter'] =  "no,yes";
                      }else if(isEnglishShow == "Yes"){
                      	currentData['latino_currentFilter'] =  "no";
                      }else if(isLatinoShow == "Yes"){
                      	currentData['latino_currentFilter'] =  "yes";
                      }
                  }
      
                   if(iFilterClick == ""){
                  	currentData['provider_currentFilter'] = data['provider_currentFilter'];
                      currentData['channels_currentFilter'] = data['channels_currentFilter'];
                      currentData['internetSpeed_currentFilter'] = data['internetSpeed_currentFilter'];
                      currentData['contractTerm_currentFilter'] = data['contractTerm_currentFilter'];
                      currentData['latino_currentFilter'] = data['latino_currentFilter'];
                  }
                catPersitFilterJOSN = data;
                 var path =$("input#contextPath").val();
             	   var url = path+"/salescenter/perssistFilterOptions";
             	   var category = $("input#categoryName").val();
      	       	currentData['sortOfferOption'] = sortOfferOption;
      	       	currentData['category'] = category;
             	    $.ajax({
             	    	type: 'POST',
             	    	url: url,
             	    	data: currentData,
             	    	success: function(){
             	    		priceTable();
             	    		salesTips();
             	    	}
             	 	});
             	} catch(e){
             	   	console.log(e);
             		$scope.productVOJSONList = productVOJSONList;
             	}
             	if(data['provider'] == undefined 
             	       	&& data['channels'] == undefined 
             	       	    && data['contractTerm'] == undefined 
             	        	       && data['internetSpeed'] == undefined 
             	        	              && data['latino'] == undefined){
             		currentFilter = ''; 
              }
              resetFiltersOptions(channleRangeList,contractTermList,internetRangeList,isLatinoShow,providerFilterMap,data,isEnglishShow);
          },
          $scope.ngDisplayChannelLineUpData = function(providerEXid,providerName, productVO){
              if(productVO.imageID != undefined && productVO.imageID == 'ATT_DirecTV'){
              	displayChannelLineUpData('2314635','DIRECTV');
              }else{
          	displayChannelLineUpData(providerEXid,providerName);
              }
          },
          $scope.viewDetailsClick = function (productStr,points){
          	 var deferred = $q.defer();
             deferred.resolve(viewDetails(productStr,points));
          };
          
          console.timeEnd("RecommendationController=");
       }]);
      // content section show after angular script loaded
      $('#recommend').ready(function(){
          $("#contentfullcont").show();
          $("#basic-modal-content1").hide();
          $("#basic-modal-content1").hide();
          $("#totalPointsId").text("Points: "+${totalPoints});
          
        });
      
      $(document).ready(function(){
    	  console.time("OnLoad=");
    	 // $(".prodcutsBtn a").click(sendCategory)
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
       
      	$(window).on('beforeunload', function(){
      		$.blockUI({ message: $('#domMessage') }); 
      	});
      	$("span.tabcontent span.row").each(function(){
      	    var ht = $(this).height();
      	    if(ht > 70){
      	        $(this).parent().height(ht);
      	    }
      	});
      	
      	//$("#loadProducts").remove();
      	$("#action_wrapper .widget_container").removeAttr("style");
      	//$('.ViewDetailsBtn').click(Details);
      	
      	$("#featuresTab").click(function(){
      	 	$("#pdet_hl_content").show();
      	 	$("#pdet_promos_content").hide();
      	 	$("#featuresTab").css("background-color", "#97D444");
      	 	$("#promotionsTab").css("background-color", "#666666");
       	});
       	
      
      	$("input[id*='_PROMOTION']").live('change', function(){
      		
      		var val = $(this).val();
      
      		var json = JSON.parse(val);
      		var isChecked = false;
      		var conflictExtID = json.promoConflict;
      			 
      		var conflicts = conflictExtID.split(',');
      		
      		if($(this).is(':checked')){
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
      						//for pre shopping cart price
      				         preBaseRePrice = monthlyPromotionPrice.toFixed(2);
      					 	$("#product_priceInfo").html("$"+totalPromotionPrice.toFixed(2));
      						$("#product_basePriceInfo").html("<del>"+basePriceValue+"</del>");
      						isBasePriceUpdate = false;
      						updatePreShoppingPrice();
      					}else{
      						//for pre shopping cart price
      				         preBaseRePrice = basePriceValue;
      						$("#product_priceInfo").html("");
      						$("#product_basePriceInfo").html(basePriceValue);
      						isBasePriceUpdate = true;
      						updatePreShoppingPrice();
      					}
      				 if(noOfOneTimePromotions > 0){
      					 preBaseNonRePrice = oneTimePromotionPrice.toFixed(2);
      					 isBaseNonPriceUpdate = false;
      					 $('#product_baseInstalationInfo').html("<del>"+baseInstalationValue+"</del>");
      					 $('#product_priceInfo2').html("").text("$"+oneTimePromotionPrice.toFixed(2));
      					 updatePreShoppingPrice();
      				 }else{
      					 preBaseNonRePrice = baseInstalationValue;
      					 isBaseNonPriceUpdate = true;
      					 $('#product_priceInfo2').html("").text("$"+baseInstalationValue);
      					 updatePreShoppingPrice();
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
      					//for pre shopping cart price
      			         preBaseRePrice = monthlyPromotionPrice.toFixed(2);
      				 	$("#product_priceInfo").html("$"+totalPromotionPrice.toFixed(2));
      					$("#product_basePriceInfo").html("<del>"+basePriceValue+"</del>");
      					isBasePriceUpdate = false;
      					updatePreShoppingPrice();
      				}else{
      					//for pre shopping cart price
      			         preBaseRePrice = basePriceValue;
      					$("#product_priceInfo").html("");
      					$("#product_basePriceInfo").html(basePriceValue);
      					isBasePriceUpdate = true;
      					updatePreShoppingPrice();
      				}
      			 if(noOfOneTimePromotions > 0){
      					preBaseNonRePrice = oneTimePromotionPrice.toFixed(2);
      					 isBaseNonPriceUpdate = false;
      					 $('#product_baseInstalationInfo').html("<del>"+baseInstalationValue+"</del>");
      					 $('#product_priceInfo2').html("").text("$"+oneTimePromotionPrice.toFixed(2));
      					 updatePreShoppingPrice();
      				 }else{
      					 preBaseNonRePrice = baseInstalationValue;
      					 isBaseNonPriceUpdate = true;
      					 $('#product_priceInfo2').html("").text("$"+baseInstalationValue);
      					 updatePreShoppingPrice();
      			     }
      
      			for(var i=0;i<conflicts.length;i++){
      				$('[id=' + conflicts[i] + '_PROMOTION]').removeAttr('disabled');
      			}
      		}
      		
      	});
      
       	
       	$("#promotionsTab").click(function(){
      	 	$("#pdet_promos_content").show();
      	 	$("#pdet_hl_content").hide();
      	 	$("#featuresTab").css("background-color", "#666666");
      	 	$("#promotionsTab").css("background-color", "#97D444");
      	 	
       	});
       	
       	$("#AddToCompare").click(addToCompare);
       	$('.pp_checkbox').click(showCompareButton);
       	$(".pp_checkbox").attr('checked', false);
       	jQuery(window).load(function () {
      	      //To save the html on page load
      	      savePageHtml(true, "");
      	});
      
       	$.extend({ promotionAlert: function (message, title,height,width) {
      	    $("<div></div>").attr({'class': 'alert'}).html(message).dialog( {
      	      buttons: { "Ok": function () { $(this).dialog("close"); } },
      	      close: function (event, ui) { $(this).remove(); },
      	      create:function () {
      	          $(this).closest(".ui-dialog").find(".ui-button").addClass("customAlert");
      	      },
      	      resizable: false,
      	      title: title,
      	      modal: true,
      	      height : height,
      	        width : width,
      	        zIndex : 10000
      	    }).text(message);
      	  }
      	});
       	
       	console.log("Total Wachers :: "+getWatchers().length);
       	$("#totalPointsId").text("Points: "+${totalPoints});
       	
        console.timeEnd("OnLoad=");
       });
      
      var basePriceValue = 0.0;
      var productTotalPrice = 0.0;
      var basePriceNumricValue = 0.0;
      var usageRate = 0.0;
      var viewDetails = function(productStr,points) {
      	try{
      		$("#pageTitle").val("Product Details");
      		basePriceValue = 0.0;
      		var aidval = '';
      	if($(this) !=  undefined && $(this).attr("id") != undefined){
      		 aidval = $(this).attr("id").replace(/\:/g,":");
      		 $("input#prodId").val( $(this).attr("id"));
      	}else if(productStr != undefined){
      		 aidval = productStr.replace(/\:/g,":");
      		 $("input#prodId").val(productStr);
      	}
      	var hiddenIdVal = "hidden_"+escapeSpecialCharacters(aidval);
      	var json = $("input#"+hiddenIdVal).val();
      	var prodJson = JSON.parse(json);
      	basePriceValue = prodJson.recuring;
      	basePriceNumricValue = (prodJson.recuring).replace("$","");
      	baseInstalationValue = prodJson.non_recuring;
      	preBaseNonRePrice = baseInstalationValue;
      	preBaseRePrice = basePriceValue;
      	var splittedArray = basePriceValue.split("$");
      	productTotalPrice = parseFloat(splittedArray[1]);
      	var stat = false;
      	var data = { "aidval" : JSON.stringify(prodJson),"productPoints" : points };
      	
      	var vals = aidval.split(','); 
      	$("#providerName_dialog").html('Product Details');
      	$("input#featuresJson").val(JSON.stringify(prodJson));
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
          			var a =JSON.stringify(data);
      	        	noOfOneTimePromotionsSelected = 0;
      	       		noOfMonthlyPromotionsSelected = 0;
      	       		isBasePriceUpdate = true;
      	    		isBaseNonPriceUpdate = true;
      	       		var errorImage = '${providersImageLocation}'+data.onErrorImage+'.jpg'; 
      	       		 
      	    		if( (data.isValidProductToAddCart).toUpperCase() == "NO")
              		{
      	    			$("#AddToOrderBtn2").unbind("click");
      		     		$("#AddToOrderBtn2").click(doNotAddToCart);
      		    		$("#addToOrderAndCKO").unbind("click");
      		    		$("#addToOrderAndCKO").click(doNotAddToCart);
      		    		
      		    		$("#AddToOrderBtn2").attr("disabled",true);
              			$("#addToOrderAndCKO").attr("disabled",true);
              			$("#AddToOrderBtn2").hide();
              			$("#addToOrderAndCKO").hide();
              		}
              		else
      	    		{
              			$("#AddToOrderBtn2").removeAttr("disabled");
      	    			$("#addToOrderAndCKO").removeAttr("disabled");
      	    			
      	    			$("#AddToOrderBtn2").unbind("click");
      		     		$("#AddToOrderBtn2").click(addFeturesAndPromotions);
      		    		$("#addToOrderAndCKO").unbind("click");
      		    		$("#addToOrderAndCKO").click(addToOrderAndGotoCKO);
              			$("#AddToOrderBtn2").show();
              			$("#addToOrderAndCKO").show();
      	    		}
      	     		
      	    		$("#basic-modal-content1 #bmc_img").hide();
      	    		$("#basic-modal-content1 #contentwrapperpdet").show();
      	    		$("#product_image img").attr('src', '${providersImageLocation}'+data.detailsImage+'.jpg');
      	    		$("#product_image img").attr('onError', "this.src = '"+errorImage+"'");  
      	    		$("#product_name").html(data.name);
      	    		$("#product_name").html($("#product_name").text()); 
      	    		$("#productBaseInfoDup").val(basePriceValue);
      	    		$("#product_basePriceInfo").html(basePriceValue);
      	    		var displayBasePrice;
            		var displayPromotionPrice;
            		if(data.displayBasePrice != undefined){
            			displayBasePrice = parseFloat(data.displayBasePrice);
            			$("#product_basePriceInfo").html("<img class='displayIcon' src='https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png'>"+"$"+parseFloat(data.displayBasePrice).toFixed(2));
            		}
					if(data.displayPromotionPrice != undefined){
					   displayPromotionPrice = parseFloat(data.displayPromotionPrice);
            		}
      	    		$("#product_priceInfo").html("");
      	    		$("#product_priceInfo2").html("");
      	    		$("#product_baseInstalationInfo").html(baseInstalationValue);
      	    		
      	    		if(data.providerExtId == ATT_PROVIDER_ID && (data.name == 'AT&T BUILD YOUR BUNDLE'||data.productExID == 'RTS:ATTV6:ABYB'||data.productExID == 'RTS:ATTV6:ABYB:2')){
      					$("#product_basePriceInfo").html("N/A");
      					$("#product_baseInstalationInfo").html("N/A");	
      				}
      	    	   if(data.usageRate != undefined && data.usageUnitName != undefined){
      	    			$("#usagerate1").val(data.usageRate);
      	    			$("#usagerate1").html(data.usageRate +"<span> "+data.usageUnitName+"</span>");
      	    	   }else if(data.usageRate != undefined){
      	    				$("#usagerate1").html(data.usageRate);	
      	    	   }
      	    		var description = data.longDescription.replace(/amp;/g,""); 
      	    		$("#product_longDescription").html(description);
      	    		var marketingHighlights=data.marketing.replace(/amp;/g,"");
      	    		$("#pdet_hl_content_cont").html(marketingHighlights);
      	    		if(data.productPointDisplay =='NA' || data.productPointDisplay == '0.0'){
      	    			$("#productPointDisplay").text(" Points:See Intranet");
      	    		}else{
      	    			$("#productPointDisplay").html("Points:"+data.productPointDisplay);
          		    }	
      	    		$("#featuresContent").html(data.features);
      	    		$("#pdet_promos_content").html(data.promotions);
      	    		$("#providerId").val(data.providerExtId);
      	    		$("#productOfferType").val(data.offerType);
      	    		if(data.providerExtId == ATT_PROVIDER_ID && (data.name == 'AT&T BUILD YOUR BUNDLE'||data.productExID == 'RTS:ATTV6:ABYB'||data.productExID == 'RTS:ATTV6:ABYB:2')){
      	 	    	 	$("#pdet_promos_content").text("- Build your bundle to see available promotions and discounts")
      	 	    	 	                         .css({"font-size":"14px" , "padding":"16px" , "background-color":"#e0f5c3"});
      	 	    	 }
      	    		if(data.isExistingCustomer == "true")
      		    	{
      	    			$("#addTOOrderBtn2Div").css("display","none");
      	    			$("#startExistingDiv").css("display","block");
      	    			$("#recommend input[id='providerExternalId']").val(data.providerExtId);
      	    			$("#product_basePriceInfo").html("<span style='color:#ed1c24;'>TBD</span>");
      	    		}
      	    		else
      		    	{
      	    			$("#addTOOrderBtn2Div").css("display","block");
      	    			$("#startExistingDiv").css("display","none");
      		    	}
      	    		if(data.usageRate != null && data.usageRate != undefined && data.providerExtId != '15499381'){
      	    			$("#usageRate").css("display","block");
          			}else{
          				$("#usageRate").css("display","none");
              		}	
      	    		if($("#pdet_promos_content").text().trim() == ''){
      	    		}else{
      	    			$("#promotionsTab").css('display','block');
      		    	}
      		    	
      	    			//disabling future data (select/check boxes)
      	    		 $('#featuresContent input[type=checkbox]').on('change', function() {
      	    			 updatePreShoppingPrice();
      			      });
      	    		 $("#featuresContent select").on('change', function() {
      	    			 updatePreShoppingPrice();
      	    		 });
      	    		 if((data.providerExtId == '32416075' 
      		    		 || data.providerExtId == '15499341' 
      			    	 || data.providerExtId == '15500221' 
      			    	 || data.providerExtId == '15500161') 
      			    	 && data.isExistingCustomer!="true"){
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
      			            		//for pre shopping cart price
      			            		
      					           	preBaseRePrice = monthlyPromotionPrice.toFixed(2);
      					           	if(preBaseRePrice != (parseFloat(basePriceNumricValue).toFixed(2))){
      		            			$("#product_priceInfo").html("$"+monthlyPromotionPrice.toFixed(2));
      			    				$("#product_basePriceInfo").html("<del>"+basePriceValue+"</del>");
      			    				}
      					            isBasePriceUpdate = false;
      			    				if(preBaseRePrice != (parseFloat(basePriceNumricValue).toFixed(2))){
      					    		updatePreShoppingPrice();
      			    				}
      	    		            }
      		    			}
      	    			});
      	    		}
       	    		//To save the html on page load
       	    		savePageHtml(true, "");
       	    		$("#pageTitle").val("RecommendationsByCategory");
           		}
           	}
        	});
          
       	}catch(err){alert(err);}
       	 return false;
       }
      
      function isATTHasSatellite(productVO){
      	return  productVO.capabilitMap['satellite'] != undefined;
      }
      function isATTDirecTVFilterSelect(data){
      	var retVal = false;
      	if(data["provider"] != undefined){
      		$.each(data["provider"].split(","),function(index, providerID){
      			if((providerID == ATT_PROVIDER_ID) || (providerID == DirecTV_NAME)){
      				retVal = true;
      			}
      		});
      	}
      	return retVal;
      }
      
      function isATTBuildYourBundle(productVO){
      	return productVO.productExID  != undefined && (productVO.productExID).includes('RTS:ATTV6:ABYB');
      }
      
      function doNotAddToCart()
      {
      	alert("This is not a valid product to CKO");
      }
      
      
      function addToOrderAndGotoCKO()
      {
      	try{
      		if(!isValidSubmit())
      		{
      			return false;
      		}
      		var isFoundInCart = false;
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
      		 
      		 //TO select default promotion for CL,COX and TXU
      		 if(providerId == "32416075" || providerId == "15499341" || providerId == "15499381")
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
      
      		if( prodJson.providerSourceBaseType.toUpperCase() != "INTERNAL" || prodJson.partnerExternalId == '32946482' || prodJson.partnerExternalId == '32952482' )
      		{
      			var isMultipleRTIMProductsInCart = false;
      			 var cartProviderExtId = "row_"+prodJson.partnerExternalId+"_";
      			 $("div#itemsBlock").find("div.mCSB_container > h4[id^='"+cartProviderExtId+"'][class != 'systemColor2 removedH4']") .each(function(){
      				if((this.id).indexOf(cartProviderExtId)>-1)
      				{
      					var cartProviderId = cartProviderExtId+prodJson.productExernalId;
      					if((this.id).indexOf(cartProviderId)==-1)
      					{
      						isMultipleRTIMProductsInCart = true;
      						return false; 
      					}
      					else
      					{
      						var indicator = (this.id).replace(cartProviderId,"");
      						if(indicator.indexOf("_")>-1)
      						{
      							indicator = indicator.replace("_","")
      						}
      						if( indicator.trim().length > 0 && isNaN(indicator) )
      						{
      							isMultipleRTIMProductsInCart = true;
      							return false; 
      						}
      					}
      				}
      			});
      				
      			if( isMultipleRTIMProductsInCart )
      			{
      				isFoundInCart = true;
      				//JqueryUI Dialogwidget For Prevention Multiple products from same provider
      				$(".dialogMultipleproducts").dialog({
      						resizable : false,
      						title:"Alert",
      						height : 200,
      						width : 800,
      						modal : true,
      						zIndex : 99999
      					  });	
      				}
      				enablePage();
      			}
      
      		var hasPromotion = inCart.find('input[id^="li_hasPromotion_"]').val();
      		if(hasPromotion == 'true')
      		{
      			isFoundInCart = true;
      			addFeturesAndPromotions();
      		}	
      		
      		if( !isFoundInCart )
      		{
      			savePageHtml(true, "");
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
      				if(isAtleastOnePromotionChecked)
      				{
      					$('#pdet_promos_content input[type=checkbox]').each(function () {
      						if (this.checked) 
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
      							}
      									
      							selectedArray.lineItems.push(JSON.stringify(promotionJson));	 
      						}
      					});
      					//TO select default promotion for CL,COX and TXU
      					if(providerId == "32416075" || providerId == "15499341" || providerId == "15499381" )
      					{
      						$('#pdet_promos_content input[type=hidden]').each(function () {
      									 
      							promotionExternalId = this.value;
      							var promotionJson = {};
      							//Constructing Promotion JSON
      							promotionJson = JSON.parse(promotionExternalId);
      							promotionJson.partnerExternalId = prodJson.partnerExternalId;
      							promotionJson.providerSourceBaseType = prodJson.providerSourceBaseType;
      							promotionJson.providerName = prodJson.providerName;
      	
      							selectedArray.lineItems.push(JSON.stringify(promotionJson));	 
      	
      							return;
      						});
      					}
      				}
      			 }
      			//savePageHtml(true, "");
      			$("form#recommend input[id='productOfferTypeValue']").val($("input#productOfferType").val());
      			$("form#recommend input[id='productDataToAddOrder']").val(JSON.stringify(selectedArray));
      			$("form#recommend input[id='_eventId']").val("addToOrderAndCKOEvent");
      			enablePage();
      			$("form#[name='recommend']").submit();
      		}
      		
      	}catch(e){
      		alert(e);
      	}
      	
      }
      	var addToCompare = function(){
      		savePageHtml(true, "");
      		$("#pageTitle").val("Compare");
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
      				var val = escapeSpecialCharacters($(this).val());
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
      			$("#pageTitle").val("RecommendationsByCategory");
      		}
      		
      	}
      	var showCompareButton = function(){
      		if($('.pp_checkbox:checked').length >= 2 ){
      			$('span.Compare').show();
      		}else{
      			$('span.Compare').hide();
      		}
      	}
      
      	function existingCustomerCKO(flowEventId)
      	{
      		$("form#recommend input[id='_eventId']").val(flowEventId);
      		$("form#[name='recommend']").submit();
      	}
      
      	function displayChannelLineUpData(providerExternalId,providerName)
      	{
      		var urlPath = "<%=request.getContextPath()%>/salescenter/channelLineUpData?providerExternalId="+providerExternalId+"&providerName="+encodeURIComponent(providerName);
      		window.open(urlPath,"", "scrollbars=yes, width=1000, height=550");
      	}
      	var syntheticViewDetails = function(productStr,points) {
      		$("#bundle-modal-content1").show();
            $("#bundle-modal-content1 #bmc_img").show();
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
                 		$(".ProductDetails").show();
                 		$(".addToOrderBtn").show();
                 		$("#backToDiscoveryId").show();
                 		$("#bundle-modal-content1").show();
                    	$("#bundle-modal-content1 #contentwrapperpdet").hide();
                    	$("#bundle-modal-content1 #bmc_img").hide();
                    	$( ".ProductDetails" ).scrollTop(0);
                		if(data1=="sessionTimeOut"){
                			var path = "<%=request.getContextPath()%>";
                			window.location.href = path+"/salescenter/session_timeout";
                		}else{
                			var data = JSON.parse(data1); 
                			productDetailsJson =JSON.stringify(data);
            	    		$("#pageTitle").val("RecommendationsByCategory");
                		}
                	}
             	});
            }catch(err){
            	alert(err);
            }
            
            }
   </script>
   <style type="text/css">
      .customAlert{
      margin-right:215px !important;
      }
      .usageRate{
      -margin-top: 22px;
      font-weight: normal;
      color:#000;
      }
      
      .ViewChannelsBtn:hover {
      	text-decoration: none;
      }
      
      .productPriceContent div.promoprc1 , .productPriceContent .promoprc2 {
      color: #ed1c24;
      display: table-row;
      float: none;
      font-size: 17px;
      font-weight: bold;
      line-height: 22px;
      }
      .required{
      color: red;
      float: right;
      margin-right: 85px;
      padding: 0;
      display: none;
      }
      .sortOption {
      color: #fff;
      float: left;
      font-size: 16px;
      font-weight: bold;
      margin-left: 29px;
      margin-top: 1px;
      width: auto;
      }
      .productPointDisplay{
      display: inline-block;
      margin-left: 3px;
      width: 95 px;
      }
   </style>
</head>
<div id="recommendationController"  data-ng-controller="recommendationController" data-ng-init="init()">
<div id="recommendationsDiv">
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
            <select class="sortOffer">
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
            <input type="hidden" id="providerExternalId" name="providerExternalId" value="" />
             <input type="hidden" id="salesTips" value='${salesTips}' name="salesTips"/>
            <input type="hidden" id="mixedBundleTime" name="mixedBundleTime" value="" />
            <input type="hidden" id="productDataToAddOrder" name="productDataToAddOrder" value="" />
            <input type="hidden" id="productOfferTypeValue" name="productOfferTypeValue" value="" />
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
                      	<%-- <div id="loadProducts" style="width:inherit;text-align:center;margin-top:100px">
				               <img src="<%=request.getContextPath()%>/images/spinner.gif"></img>
				        </div> --%>
                        <!-- <div class="tabs" data-ng-repeat="productVO in productVOJSONList | filter:searchByName" ng-cloak> -->
                        <div class="tabs" data-ng-repeat="productVO in productVOJSONList track by $index" ng-init="indVal=$index" ng-cloak >
                            <div class="forplace" data-ng-if="productVO.pairedProduct != undefined && productVO.pairedProduct != '' &&  showPairedProduct(productVO)" >
                               
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
                                           <div class="sync_icons" id="" ng-repeat="productIcon in pairedProductProductIconList track by $index">
                                           		<img ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/{{productIcon}}.png">
                                           </div> 
                                       </div>
                                       <div class="syncbdl_cont">
                                          <div class="sync_logo">
                                         	 <img id="imageID">
                                          </div>
                                          <div class="sync_title">
                                             <span id="ppFullName" ng-bind="(productVO.productName) + (productVO.PairedProduct.productName)"></span>
                                             <p class="productDic" id="ppFullDec"  ng-bind="productVO.productDescription | words:12" ></p> 
                                          </div>
                                          <div class="sync_icons" ng-repeat="productIcon in productVO.productIconList track by $index">
                                          <img id="aproduct" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/{{productIcon}}.png">
                                          	<img ng-if="productVO.pricingGridJson != undefined" class="gridIcon nobtmbdr"  ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/table_icon.png">
                                          	<span class="ratepopup" ng-if="productVO.pricingGridJson != undefined">
												<table class="ratecomp" cellspacing="0"  ng-if="buildPriceTire(productVO.pricingGridJson)">
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
                              </div>  
                           
                           <!-- Regular Product -->
                           
                           
                           <div class="forplace" id="regProductBorder" data-ng-if="productVO != undefined && productVO.pairedProduct == undefined">
                               
                              <dir class="syncbdlmain regProductHeight singleProductContent{{$index}}" >
                                    <input type="hidden" class="singleHiddenJson"  >
                                    <input type="hidden"  class="singleInstallPrice">
                                    <div class="sync_part">
	                                     <div class="pointsReg"  ng-if="productVO.productPointDisplay && showPointsDisplay(productVO.productPointDisplay)">
	                                          <span >Points:</span>
	                                          <span class="tblrtlnk" id="sTotalPt" >{{pointsDisplay}}</span>
	                                     </div>
	                                      <div class="tblink ViewDetails">
											<input type="button" name="" value="View Details" class="ViewDetailsBtn" ng-click = "viewDetailsClick('product_'+productVO.providerExtId+'_'+productVO.productExID,productVO.productPointDisplay)" id="product_{{productVO.providerExtId}}_{{productVO.productExID}}">
	                                   </div>
                                    </div>
                                    <div class="sync_part ltpart">
                                       <div class="syncbdl_cont regProductBlock">
                                          <span class="cell productLogopp" >
                                          	<!-- <img alt="{{productVO.providerExtId}}" ng-src="{{providerIMGLocation}}{{productVO.imageID}}.jpg" title="Points : {{pointsDisplay}}, Product External Id : {{productVO.productExID}}" title1="{{productVO.productPointDisplay}}|{{productVO.providerExtId}}"> -->
                                          	 <img id="productImageId" >
                                          </span>
                                          <div class="sync_title">
                                             <div class="productTitle" ng-bind-html="productVO.productName | unsafe"></div>
                                             <p ng-if="productVO.productDescription !='' " class="productDic promoDicReg" ></p>
                                          </div>
                                          
                                          <div class="sync_icons regProductProviderIcon">
                                           <img ng-src="<%=request.getContextPath()%>/images/images_new/{{producticon}}.png" data-ng-repeat=" producticon in productVO.productIconList track by $index"> 
                                             
                                             <img id="gridImg" ng-if="productVO.pricingGridJson != undefined"   ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/table_icon.png"> 
                                        
                                            <span class="ratepopup" ng-if="productVO.pricingGridJson != undefined">
                                          	<!-- <div class="eliteCustomer" ng-if="productVO.providerExtId =='27010360'">Elite Customer Pricing</div> -->
												<table class="ratecomp" cellspacing="0"  ng-if="buildPriceTire(productVO.pricingGridJson)">
												<thead>
													 <tr>
													  <th colspan="2">Number of TVs</th>
													  <th ng-repeat="headers in priceTireObject.headers track by $index">
													  {{headers}} 
													  </th>
													 </tr>
												</thead>
												<tbody class="sythaticPriceGrid{{indVal}}" ng-if="buildSynPriceGrid(priceTireObject,indVal)">
													
												</tbody>
											</table>
                                          	</span>
                                          </div>
                                       </div>
                                    </div>
                                    
                                    <div class="sync_part rtpart ltpad regProductChannelLineup">
                                    <input type="hidden" name="aidvalue" id="aidvalue"> 
                                          <span>
                                             <div class="tblink" ng-if="productVO.isChannelLineupProvider">
                                            	  <input type="button" name="" value="Channel Lineup" ng-click="ngDisplayChannelLineUpData(productVO.parentProviderExtId,productVO.providerName,productVO)" data-ng-if ="productVO.isChannelLineupProvider && productVO.isVideoCapable"  class="ViewChannelsBtn" >
                                             </div>
                                          </span>
                                    </div>
                                    <div class="sync_part rtpart regBasePrice">
                                       <div>
                                          <table class="pricetbl">
                                         
                                             <tr>
                                                <td>Base Price:</td>
                                                <td>
	                                             <span ng-if="productVO.baseRecNA" ng-bind="productVO.baseRecNA"></span> 
	                                             <span ng-if="productVO.isExistingCustomer" style="color:red;">TBD</span> 
	                                             <span class="nonDisplayIcon" ng-if="!productVO.baseRecNA && !productVO.promoPrice && !productVO.isExistingCustomer && !productVO.displayBasePrice"  style="font-weight:lighter">
	                                             <span class="nonDisplayIconBP"></span>
	                                             </span>
	                                             
	                                             <span ng-if="!productVO.baseRecNA && !productVO.promoPrice && !productVO.displayPromotionPrice && !productVO.isExistingCustomer && productVO.displayBasePrice" style = "font-weight:lighter">
	                                             <img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png" ><span class="displayIconDP"></span></span>
	                                              
	                                             <span class="nonDisplayIcon" ng-if="!productVO.baseRecNA && productVO.promoPrice && !productVO.isExistingCustomer && !productVO.displayBasePrice && !productVO.displayPromotionPrice">
	                                             <del class="nonDisplayIconBP"></del>
	                                             </span>
	                                             <span class="nonDisplayIcon" ng-if="!productVO.baseRecNA && !productVO.isExistingCustomer && productVO.displayPromotionPrice && productVO.displayBasePrice">
	                                             <del class="displayIconDP"></del>
	                                             </span>
	                                             <span class="nonDisplayIcon" ng-if="!productVO.baseRecNA && !productVO.isExistingCustomer && productVO.displayPromotionPrice && !productVO.displayBasePrice">
	                                             <del class="nonDisplayIconBP"></del>
	                                             </span>
                                                   
                                                </td>
                                             </tr>
                                             <tr>
                                                <td ng-if="(productVO.usageRate || productVO.usageRate == 0) && productVO.providerExtId !='15499381' ">USAGE RATE:</td>
                                                <td ng-if="!productVO.usageRate"></td>
                                                <td class="lessprice" style="font-weight: bold;">
                                                  <div class="usageRate nonDisplayIcon" ng-if="(productVO.usageRate || productVO.usageRate == 0) && productVO.providerExtId !='15499381' "><span class="usageRateValue"></span><span  class="usageRateUnit"></span></div>
		                                          <div class="promoprc nonDisplayIcon" ng-if="!productVO.isExistingCustomer && !productVO.baseRecNA && !productVO.displayPromotionPrice"></div>
		                                          <div class="promoprc" ng-if="productVO.displayPromotionPrice"><img class="displayIcon" ng-src="https://s3.amazonaws.com/AL-content/common/provider/logos/OnSymbol.png"><span class="dispalyPr"></span></div>
		                                          <input type="hidden" class="baseRecPrice" >
                                                </td>
                                                 
                                             </tr>
                                          </table>
                                          
                                       </div>
                                    </div>
                                 </dir>
                                 <div data-ng-if="singleProductData(productVO,$index)"></div>
                              </div>
                           <!-- Regular Product_End -->
                        </div>
                     </div>
                  </div>
               </div>
               <div class="bottombuttons">
                  <div class="leftbtns">
                     <span class="Compare" style="display: none;">
                     <input class="addtoorderbtn" type="button" id="AddToCompare" value="Compare" />
                     </span>
                  </div>
                  <div class="rightbtns">
                     <input type="button" id="backToDiscoveryId" name="backToDiscoveryId" value="< Back" onclick="backToDiscovery('backToDiscoveryEvent');"/>
                  </div>
               </div>
            </div>
         </form>
      </section>
   </section>
   <!-- Content Full Cont -->
</div>
 <div id="bundle-modal-content1" style="float:left;display:none;">
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
	       		
	       		<span ng-if="promotionPr == 0 && totalDisplayPromotionPrice == 0 && totalDisplayBasePrice == 0">
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
		<tr >
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
								<td class="providerOne_td" data-ng-if="productDetailsArr != undefined"
									ng-bind-html="productDetailsArr[0].name | unsafe"></td>
								<td class="providerTwo_td" data-ng-if="productDetailsArr != undefined"
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
			<div class="leftbtns addToOrderBtn" id="addTOOrderBtn3Div" >
					<input type="button" id="AddToOrderBtn3" value="Add to Order" ng-click="addMixedBundleToOrder()"/>&nbsp;&nbsp;
					<input type="button" id="addToOrderAndCKO2" value="Add to Order and CKO" ng-click="addSyntBdToOrderAndCKO()" />
			  </div>
			   <div class="rightbtns addToOrderBtn">
                   <input id="backToRecommendations1" onclick="backToRecommendations();" value="< Back" type="button">
               </div>
            
               </section>
         </section>
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
													<div class="pdet_prdprice" style="height: 20px;">
														BASE MONTHLY PRICE:
														<span id="product_basePriceInfo"></span>
														<div style="display:inline;" class="promoprc1">
															<span id="product_priceInfo"></span>
															<input type="hidden" id="productBaseInfoDup" value="">
														</div>
													</div>
													<div class="pdet_prdprice" style="height: 20px;">
														BASE INSTALLATION PRICE: <span id="product_baseInstalationInfo"></span>
													<div class="promoprc2" style="display:inline;">
														<span id="product_priceInfo2"></span>
														<input type="hidden" id="productNonBaseInfoDup" value="">
													 </div>
													</div>
													<div id ="usageRate" class="usagerate">USAGE RATE:
													<span id="usagerate1"> </span>
													</div>
												</div>
											</div>
										</div>
										
										<div class="pdet_content_block">
											<div class="pdet_productdesc" id="product_longDescription"></div>
											<div class="pdet_features">
												<div class="pdet_ftrs_tab">Features</div>
												<div class="pdet_ftrs_dtls">
												<span id="productPointDisplay" style="font-weight: bold;margin-left: 10px;"></span>
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
									<div id='requiredDiv' class='required'>* Please make sure that both dependent promotions are selected</div>
								</div>
								<div class="bottombuttons">
									<div class="leftbtns" id="addTOOrderBtn2Div" >
										<input type="button" id="AddToOrderBtn2" value="Add to Order" />&nbsp;&nbsp;<input type="button" id="addToOrderAndCKO" value="Add to Order and CKO" />
									</div>
									<div class="leftbtns" id="startExistingDiv" style="display: none;" >
										<input type="button" id="startExistingButton" value="Start Existing" onclick="existingCustomerCKO('existingCustomerEvent');"/>
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
   </section>
   <!-- Content Full Cont -->
   <div id="domMessage" style="display:none;">
      <img src="<%=request.getContextPath()%>/images/loading.gif" border="0"/>
      <h2>Loading</h2>
   </div>
   <div class="dialogMultipleproducts" style="display:none;">
      <p>Multiple products from same provider may not be checked out. Please remove other products from the same provider and then CKO the current product.</p>
      <input type="button" style="margin-left: 360px;" class="dialogMultipleproductsClose" value="OK"/>				
   </div>
</div>
</div>