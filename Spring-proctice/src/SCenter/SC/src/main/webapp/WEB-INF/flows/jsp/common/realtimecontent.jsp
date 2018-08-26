<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.AL.productResults.managers.ProductResultsManager"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<style>
#dataRefreshDiv{font: bold 10px arial; padding-bottom: 3px; float: left;margin-top: -5px;padding-left: 140px;}
#afterDataRefreshDiv{font: bold 10px arial; padding-bottom: 3px; float: left;margin-top: -5px;padding-left: 140px;}
a.reload{color: #009CDE;cursor: pointer;}
a.reload:hover{text-decoration:underline;color:blue}
.rts_container table {
    color: #000;
    font-size: 10px;
    padding: 4px 0;
    width: 70%;
}
.dataFilterSelect {
	font-size:10px;
	max-width:90px;
}
#latino {
  margin-right: 15px;
  vertical-align: text-top;
}
.realtime_widget_tab {
    background-color: #97d444;
    border-radius: 10px 10px 0 0;
    color: #fff;
    cursor: pointer;
    float: left;
    font-size: 14px;
    font-weight: bold;
    height: 25px;
    padding-left: 5px;
    padding-top: 4px;
    text-align: left;
    width: 125px;
}
.filter_widget_tab {
    background-color: #666666;
    border-radius: 10px 10px 0 0;
    color: #fff;
    cursor: pointer;
    float: right;
    font-size: 14px;
    font-weight: bold;
    height: 25px;
    padding-top: 4px;
    text-align: left;
    width: 120px;
}
.rts_container .filtter_content_display {
    display: inline-block;
    height: 57px;
    overflow-x: hidden;
    overflow-y: auto;
}
#filtter_content_channels,
#filtter_content_internetSpeed, 
#filtter_content_provider, 
#filtter_content_contract {
    width: 245px;
} 
#filtter_content_channels table, #filtter_content_internetSpeed table {
	padding: 0px;
	margin-top: -15px;
}
.underline{
	border-top: 1px solid #ccc;
}

.rts_container table td.filter_values{
	text-align: right;
}
.rts_container table td {
    color: #000;
    font-size: 10px;
    padding: 2px 0;
    
}
.speedContent {
display : table;
float: left;
}
.checkbox_filtter_content_internetSpeed {
    margin: 3px 0 0 7px;
}

.speedContent td:first-child {
   width: 65%;
}
.rts_container table {
    color: #000;
    font-size: 10px;
    padding: 4px 0;
    width: 90% !important;
}
.contractTerm {
display : table;
float: left;
}
.contractTerm td:first-child {
   width: 30%; 
}
</style>
 <%     
     Gson gson = new Gson();
     ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
    // java.util.Map<String, java.util.Map<String, String>> productNameAndExternalId= productResultManager.getCategoryWiseProductNameAndExternalId();
     String productNameAndExternalIdJson = gson.toJson(productResultManager.getCategoryWiseProductNameAndExternalId());
     boolean isHNProductShow = productResultManager.isHNProductShow();
     boolean isProductPollingCompleted = productResultManager.isProductPollingCompleted();
     String hideHughesNet = productResultManager.getHideHughesNet();
    
%>
	<c:set var="productNameAndExternalIdJson" value="<%=productNameAndExternalIdJson%>" />
	<c:set var="isHNProductShow" value="<%=isHNProductShow%>" />
	<c:set var="isProductPollingCompleted" value="<%=isProductPollingCompleted%>" />
	<c:set var="hideHughesNet" value="<%=hideHughesNet%>" />
<script type="text/javascript">
var isBack  = "${isBack}";
var isValidMamPage = true;
var isMAMSuccess = "${isMAMSuccess}";
var MAMproductsUpdated = "${MAMproductsUpdated}";
var ListOfProviderIds = "${ListOfProviderIds}";
var isHNProductShow = ${isHNProductShow};
var filterProviderNamesAndExtIdsMap = ${productNameAndExternalIdJson};
var hideHughesNet = ${hideHughesNet};
var externalScope;
var catPersitFilterJOSN;
var pesistFilterOptions = '${empty pesistFilterOptions ? "" : pesistFilterOptions}';
var isPendingStatus ;
var selectedProviderID = '${selectedProviderID}';
var realTimeScope;
var currentFilter = "";
var currentFilterValue = "";
var isPoolingComleted = ${isProductPollingCompleted};
var isComplete = false;
var mamPendingPages = ["Confirmation","Offers","Utility Offer","Qualification","Exception Page","Supplier Selection","SupplierSelection","Discovery","Discovery New"]; 
angular.module('concert', []).controller('realContentController', ['$scope','$http','$window', function($scope,$http,$window) {
	realTimeScope = $scope;
	var title = $('#pageTitle').text();
	$(mamPendingPages).each(function(key,val){
		if($("#pageTitle").val() != undefined && val.toUpperCase() == $("#pageTitle").val().trim().toUpperCase()){
			isValidMamPage = false;
			if(isBack != undefined && isBack ==""){
				$("#rts_widget_content tbody tr").find("td:contains('Multiple Address')").html("Pending");
			}
		}else if(title != undefined && val.toUpperCase() == title.trim().toUpperCase()){
			isValidMamPage = false;
			if(isBack != undefined && isBack ==""){
				$("#rts_widget_content tbody tr").find("td:contains('Multiple Address')").html("Pending");
			}
		}
	});
	 $scope.init = function(){
		 if(selectedProviderID != "" ){
	    		catPersitFilterJOSN = {'provider':selectedProviderID};
	     }else if(pesistFilterOptions != undefined && pesistFilterOptions != ''){
			 pesistFilterOptions = JSON.parse(pesistFilterOptions);
			 var  category = $("input#categoryName").val();
			 catPersitFilterJOSN = pesistFilterOptions[category];
		 }
     },
     $scope.isChecked = function(value,filterName){
         var  flag = false;
    	  if((pesistFilterOptions != undefined && pesistFilterOptions != '') || selectedProviderID != "" || currentFilter != "" ){
        	  //console.log("catPersitFilterJOSN="+JSON.stringify(catPersitFilterJOSN));
    		  if(  catPersitFilterJOSN != undefined && catPersitFilterJOSN[filterName] != undefined){
    			  $.each((catPersitFilterJOSN[filterName]).split(","),function(index,keyValue){
             	     if(keyValue == value){
             	    	 flag =  true;
                  	}
              });
		      }
          }
          return flag;
     },
     $scope.hideHugheNet = function(key){
   		  if((key == "15500621" || key == "15500581") && !isHNProductShow && hideHughesNet){
   			  return false;
   		  } 
    	 return true;
     },
     $scope.selectedCheckBoxs = function(currentFilterClick,value){
      iFilterClick = "Yes";
      currentFilter = currentFilterClick;
      currentFilterValue = value;
      externalScope.filteringAjaxCall({});
    };
}]);

//This Method Use to dynamically set Filter Options
function resetFiltersOptions(channleRangeList,contractTermList,internetRangeList,isLatinoShow,providerFilterMap,data,isEnglishShow){
	console.log("currentFilter="+currentFilter);
	console.log("providerFilterMap="+ Object.keys(providerFilterMap).length);
	//chhanels
	if(providerFilterMap != undefined 
			&& Object.keys(providerFilterMap).length > 0 
			         && (iFilterClick == "" || currentFilter != 'provider' || data['provider'] ==  undefined)){
		realTimeScope.filterProviderNamesAndExtIdsMap = providerFilterMap;
		$('.providerTR').show();
	}else if((providerFilterMap == undefined || Object.keys(providerFilterMap).length == 0) && currentFilter != 'provider'){
		$('.providerTR').hide();
    }
	//chhanels
	if(channleRangeList != undefined && channleRangeList.length > 0
			   && (iFilterClick == "" || currentFilter != 'channels' || data['channels'] ==  undefined)){
		realTimeScope.channleRangeList = channleRangeList.sort();
		$('.channelsTR').show();
	}else if((channleRangeList == undefined || channleRangeList.length == 0) && currentFilter != 'channels'){
		$('.channelsTR').hide();
	}

	//contract
	if(contractTermList != undefined && contractTermList.length > 0 
			&& ( iFilterClick == ""
			            ||  currentFilter != 'contractTerm' || data['contractTerm'] ==  undefined) ){
		realTimeScope.contractTermList = contractTermList.sort();
		$('.contractTR').show();
	}else if((contractTermList == undefined || contractTermList.length == 0) && currentFilter != 'contractTerm'){
		$('.contractTR').hide();
	}
	
	//internet
	if(internetRangeList != undefined && internetRangeList.length > 0 
			&& (iFilterClick == ""
			             || currentFilter != 'internetSpeed' || data['internetSpeed'] ==  undefined )){
		realTimeScope.internetRangeList = internetRangeList.sort(function (value1, value2) {
			  if (parseFloat(value1.split("-")[0]) > parseFloat(value2.split("-")[0])){
				    return 1;
			  }else if (parseFloat(value1.split("-")[0]) < parseFloat(value2.split("-")[0])){
				    return -1;
			  }else{
				 return 0;
			  }
		});
		$('.internetTR').show();
	}else if(internetRangeList == undefined || internetRangeList.length == 0 && currentFilter != 'internetSpeed'){
	   $('.internetTR').hide();
   }

	//isEnglishShow
	if(isEnglishShow != ''){
      $('.englishLatino').show();
	}else if(currentFilter != 'latino' && isEnglishShow == ""){
		$('.englishLatino').attr('checked',false);
		 $('.englishLatino').hide();
	}
	   
    //latino
	if(isLatinoShow != ''){
      $('.latinoFilterDiv').show();
	}else if(currentFilter != 'latino' && isLatinoShow == ""){
		$('.latinoFilterDiv').attr('checked',false);
		 $('.latinoFilterDiv').hide();
	}

	if(isLatinoShow == 'Yes'&& data['latino']!= undefined  && data['latino'] == 'yes'){
		$('.latinoFilterDiv').attr('checked',true);
    }else if(isEnglishShow == 'Yes' && data['latino']!= undefined  && data['latino'] == 'no'){
		$('.englishLatino').attr('checked',true);
    }else if(isLatinoShow == 'Yes'&& isEnglishShow == 'Yes' && data['latino']!= undefined){
    	$('.latinoFilterDiv').attr('checked',true);
    	$('.englishLatino').attr('checked',true);
    }
    
}
$(document).ready(function(){
	
	showFilterTab();
	$('[id^=filtter_content]').hide();
	$("#provider").change(categoryFilter);
	$("#internetSpeed").change(categoryFilter); 
	$("#channels").change(categoryFilter);
	$("#contractTerm").change(categoryFilter);
	$("#clear").click(clear);
	$('[id^=filtter_content_clear]').click(clearCheckBoxFilter);
	if(selectedProviderID != "" && selectedProviderID != 'null'){
		$('#filter_provider').removeClass('collapse-close').addClass('collapse-open');
		$('#filtter_content_clear_provider').show();
		$('#filtter_content_provider').show();
	}
	$("#dataRefresh").click(function(){
		//getRefreshData();
		refreshResultsData();
	}); 
	$(".filtter_heading").click(function()
	{
		try{
			if($(this).hasClass('collapse-close')){
				$(this).removeClass('collapse-close');
				$(this).addClass('collapse-open');
			}else if($(this).hasClass('collapse-open')){
				$(this).removeClass('collapse-open');
				$(this).addClass('collapse-close');
			}
		 $("#filtter_content_clear_"+$(this).attr("filter_seq")).toggle();
	     $("#filtter_content_"+$(this).attr("filter_seq")).toggle();
	 }catch(e){
		 alert(e);
	  }
	 });
	expandFilterOnload(catPersitFilterJOSN);
});

function expandFilterOnload(data){
	if(data != undefined){
		if(data["provider"] != undefined){
			expandFilter("provider");
		}
		if(data["channels"] != undefined){
			expandFilter("channels");
		}
		if(data["internetSpeed"] != undefined){
			expandFilter("internetSpeed");
		}
		if(data["contractTerm"] != undefined){
			expandFilter("contract");
		}
	}
}
function expandFilter(filterDIV){
	$('#filter_'+filterDIV).removeClass('collapse-close').addClass('collapse-open');
	$('#filtter_content_clear_'+filterDIV).show();
	$('#filtter_content_'+filterDIV).show();
}

function selectedCheckBox(){
	 externalScope.$apply(function() {
	 externalScope.filteringAjaxCall({});
	 });
}
var clear = function(){
	$('input[class^=checkbox_filtter_content]').attr("checked",false);
	iFilterClick = "";
	 externalScope.$apply(function() {
		externalScope.filteringAjaxCall({});
	});
}
function clearCheckBoxFilter(){
	var id = $(this).attr('id');
	id = id.replace("_clear", "");
	$('.checkbox_'+id).attr("checked",false);
	//currentFilter = "";
	 externalScope.$apply(function() {
			externalScope.filteringAjaxCall({});
			
	});
}


var w = window.setInterval(getWidgetInfo, <c:out value="${cycletime}"/>);
function showButtonDiv(){
	var showButton = false;
	var curUrl = window.location.href;
	$('input[name="pageTitle"]').each(function(){
		if(this.value == "Recommendations")
		{
			showButton = true;
		}
	});
	if (showButton) {
		$("div#dataRefreshDiv").show();
		$("div#afterDataRefreshDiv").hide();
	}	
} 

function getRefreshData() {
		var curUrl = window.location.href;
		var showButton =  curUrl.indexOf('recommendationsByCategory') > -1;
		var data = {};
		var flowExecutionUrl = "${flowExecutionUrl}";
		data = {"dynamicFlow" : "Yes"};
		data["flowExecutionUrl"] = flowExecutionUrl;
	 	var url = "<%=request.getContextPath()%>/salescenter/recommendations/refreshProductData";
		if (showButton) {
		    url = "<%=request.getContextPath()%>/salescenter/recommendations/refreshProductDataByCategory"; 
			//var last = curUrl.lastIndexOf('/');
			var lastParameterIndex = curUrl.lastIndexOf('CategoryName');
			var lastParameter  = curUrl.substring(lastParameterIndex+1, curUrl.length);
			var parameterArray = lastParameter.split("=");
			if(parameterArray.length>1)
			{
				var categoryName = parameterArray[1];
				if(parameterArray[1].lastIndexOf("#")>-1)
				{
					categoryName = parameterArray[1].replace("#", "");
				}
				data["categoryName"] = categoryName;
			}
		}
	    $.ajax({
	    	type: 'POST',
	    	data:data,
	    	url: url,
	    	success: successRefresh
	 	});
}

function successRefresh(data1){
	if(data1=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		var data = JSON.parse(data1);
		$("div#dataRefreshDiv").hide();
		$("div#afterDataRefreshDiv").show();
		$("div.tabsWrapper").html(data.value);
		$('.pp_checkbox').click(showCompareButton);
		$('.ViewDetailsBtn').click(viewDetails);
		$('section#maincontent > section.navcontainer > nav#products').html(data.productIconS);
	}
}

getWidgetInfo();

function getWidgetInfo(){
	 var url = "<%=request.getContextPath()%>/salescenter/recommendations/getProviderStatus";
	    $.ajax({
	    	type: 'POST',
	    	url: url,
	    	success: onSuccess
	 	});
}

function onSuccess(data){
	var providerIdEvent;
	var providerIdEventArr = [];
	if(data=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		data = JSON.parse(data);
		var element='';
		try{
			var title = $('#pageTitle').text();
			$(mamPendingPages).each(function(key,val){
				if($("#pageTitle").val() != undefined && val.toUpperCase() == $("#pageTitle").val().trim().toUpperCase()){
					isValidMamPage = false;
					if(isBack != undefined && isBack ==""){
						$("#rts_widget_content tbody tr").find("td:contains('Multiple Address')").html("Pending");
					}
				}else if(title != undefined && val.toUpperCase() == title.trim().toUpperCase()){
					isValidMamPage = false;
					if(isBack != undefined && isBack ==""){
						$("#rts_widget_content tbody tr").find("td:contains('Multiple Address')").html("Pending");
					}
				}
			});
		var curUrl = window.location.href;
		var isRecommendationPage = curUrl.indexOf('recommendations') > -1 || curUrl.indexOf('recommendationsbyCategory') > -1;
		$('input[name="pageTitle"]').each(function(){
			if(this.value == "Recommendations" && !isRecommendationPage)
			{				
				isRecommendationPage = true;
			}
		});		
		if (isRecommendationPage) {
			var value = $("#tempData").val();
			//console.log("data.isPending :::::::::::::: "+data.isPending +" ::::::: data.isSuccessed :: "+data.isSuccessed + " ::::::: "+value)
		 	if(data.isSuccessed) {
		 		$("div#afterDataRefreshDiv").hide();
				$("div#dataRefreshDiv").show();
				$("#tempData").val('true');
			} else{
				if(value=="")
					$("div#dataRefreshDiv").hide();
			}
		}
		
	 	for (var val in data.productMap) 
		 {
	 		if(data.isCompleted != undefined && !data.isCompleted){
	 			isComplete = true;
			 }
		 	if((data.productMap[val]).indexOf("Start Existing")>-1)
			{
		 		var temmpId = data.productMap[val].split("_");
		 		var status = temmpId[0].split("|");
		 		var temVal = val;
		 		if(temVal=="CenturyLink"){
		 			status[0] = "Contact Provider";
		 		}
		 		isPoolingComleted = data.isCompleted;
		 		if(val =="HughesNet"){
		 			val = "<span style='font-weight:bold;font-style:italic;'>"+val+"</span>";
		 		}
		 		if(data.isCompleted != undefined && data.isCompleted && temVal =="HughesNet"){
		 			isHNProductShow = data.isHNProductShow;
		 			if(!isHNProductShow && hideHughesNet){
		 				if(status[0] == "Not Available"){
			 				
			 			}else{
			 				status[0] = "Not Available.";
			 			}
		 			}
		 		}
		 		if(data.isCompleted && !data.isHNProductShow && temVal =="HughesNet" && hideHughesNet){
		 			if(!(status[0] == "Not Available")){
		 				status[0] ="Not Available.";
		 			}
		 		}else if(data.isCompleted && data.isHNProductShow && temVal =="HughesNet"){
		 			val = "<span style='font-weight:bold;font-style:italic;'>"+val+"</span>";
		 			isHNProductShow = data.isHNProductShow;
		 			if(isComplete && isRecommendationPage){
		 					var value = $("#tempData").val();
		 				 		$("div#afterDataRefreshDiv").hide();
		 						$("div#dataRefreshDiv").show();
		 						$("#tempData").val('true');
		 						isComplete = false;
		 			}
		 		}else if(!data.isCompleted && data.isHNProductShow && temVal =="HughesNet"){
		 			status[0] = "Checking internet providers";
		 		}else if(!data.isCompleted && !data.isHNProductShow && temVal =="HughesNet" && hideHughesNet){
		 			if(status[0] != "Available" && status[0] != "Not Available"){
	 					status[1] = "Checking internet providers";
	 				}
		 		}
		 		if(temVal=="CenturyLink"){
		 			status[0] = "Contact Provider";
		 			element += "<tr title = '"+status[0]+"' ><td>" + val + "</td><td>" + status[0]+ "</td></tr>";
		 		}else{
					element += "<tr title = '"+status[0]+"' ><td>" + val + "</td><td><a href='#' onclick='forwardExistingCustomerCKO("+status[1]+");'>" + status[0]+ "</a></td></tr>";
		 		}
			}
		 	else
			{
		 		var temmpId = data.productMap[val].split("_");
		 		var status = temmpId[0].split("|");
		 	
		 		/* console.log("************ "+JSON.stringify(data.productMap))
		        console.log("************ isHNProductShow "+data.isHNProductShow);
		 		console.log("************ isPending "+data.isPending);
		 		console.log("************ isCompleted "+data.isCompleted);
		 		console.log("************ hideHughesNet "+hideHughesNet);  */
		 		
		 		isPoolingComleted = data.isCompleted;
				 var temVal = val;
				 
		 		if(val =="HughesNet"){
		 			val = "<span style='font-weight:bold;font-style:italic;'>"+val+"</span>";
		 		}
		 		if(data.isCompleted != undefined && data.isCompleted && temVal =="HughesNet"){
		 			isHNProductShow = data.isHNProductShow;
		 			if(status[0] == "Available" && isHNProductShow && hideHughesNet){
		 				if(status[1] != undefined){
		 					status[1] == "SUCCESS";
		 				}
		 			}
		 			if(!isHNProductShow && hideHughesNet){
		 				if(status[0] == "Not Available"){
		 					
			 			}else{
			 				status[0] = "Not Available.";
			 			}
		 			}
		 		}
		 		if(data.isCompleted && !data.isHNProductShow && temVal =="HughesNet" && hideHughesNet){
		 			if(status[0] == "Not Available"){
		 				
		 			}else{
		 				status[0] = "Not Available.";
		 			}
		 		}else if(data.isCompleted && data.isHNProductShow && temVal =="HughesNet"){
		 			isHNProductShow = data.isHNProductShow;
		 			if(isComplete && isRecommendationPage){
		 					var value = $("#tempData").val();
		 				 		$("div#afterDataRefreshDiv").hide();
		 						$("div#dataRefreshDiv").show();
		 						$("#tempData").val('true');
		 						isComplete = false;
		 			}
		 			if(status[0] == "Available"){
		 				if(status[1] != undefined){
		 					status[1] == "SUCCESS";
		 				}
		 			}
		 		}else if(!data.isCompleted && data.isHNProductShow && temVal =="HughesNet"){
		 			if(status[0] != "Not Available"){
		 				status[1] = "Checking internet providers";
		 			}
		 		}else if(!data.isCompleted && !data.isHNProductShow && temVal =="HughesNet" && hideHughesNet){
		 			if(status[0] != "Available" && status[0] != "Not Available"){
	 					status[1] = "Checking internet providers";
	 				}
		 		}
		 		if(data.isCompleted != undefined && data.isCompleted && temVal =="HughesNet" && status[1] == undefined && status[0] == "Available" && isHNProductShow && hideHughesNet){
		 			element += "<tr title = 'SUCCESS' ><td>" + val + "</td><td>" + status[0]+ "</td></tr>";
		 		}else{
		 			if(status[0] == "Not Available" && status[1] != undefined && status[2] != undefined ){
						status[0] = status[1];
						status[1] = status[2];
					}
		 			if(data.isCompleted != undefined && data.isCompleted && temVal =="HughesNet" && (status[0] == "Not Available." || status[0] == "Available") && !isHNProductShow && hideHughesNet){
		 				element += "<tr title = 'Not Available.' ><td>" + val + "</td><td> Not Available. </td></tr>";
		 			}else if(data.isCompleted != undefined && data.isCompleted && temVal =="HughesNet" && (status[0] == "Not Available" || status[0] == "Available") && !isHNProductShow && hideHughesNet){
		 				element += "<tr title = 'Not Available' ><td>" + val + "</td><td> Not Available </td></tr>";
		 			}else if(data.isCompleted != undefined && !data.isCompleted && temVal =="HughesNet" && status[0] == "Not Available" && !isHNProductShow && hideHughesNet){
		 				element += "<tr title = 'Not Available' ><td>" + val + "</td><td> Not Available </td></tr>";
		 			}else if(data.isCompleted != undefined && data.isCompleted && temVal =="HughesNet" && status[0] == "Not Available" && !isHNProductShow && !hideHughesNet){
		 				element += "<tr title = '"+status[1]+"' ><td>" + val + "</td><td> Not Available </td></tr>";
		 			}else{
		 				if( status[1] != undefined  && temmpId[1] != undefined && (status[1].indexOf("Multiple Address") > -1)){
		 					//&& isRecommendationPage //&& (status[1].indexOf("Multiple Address") > -1)
							var providerNames = temmpId[1].split("#");
	 						var providerIdEvent = providerNames[0];
	 						var MamFlag = false;
	 						if(ListOfProviderIds != ""){
			 					$(JSON.parse(ListOfProviderIds)).each(function(key,val){
				 					if(val == providerIdEvent){
				 						MamFlag = true;
				 					}
				 				});
			 				}
	 						if(MamFlag){
	 							element += "<tr title = '"+status[1]+"' ><td>" + val + "</td><td>" + status[0]+ "</td></tr>";
	 						}else{
	 							providerIdEvent = "MAM"+"_"+providerIdEvent;
			 					var id = "#"+providerIdEvent
			 					providerIdEventArr.push(id);
			 					if(!isValidMamPage && isBack == ""){
			 						element += "<tr title = '"+status[1]+"' ><td>" + val + "</td><td>Pending</td></tr>";
			 					}else{
			 						element += "<tr title = '"+status[1]+"' ><td>" + val + "</td><td><a href='#' id ='"+providerIdEvent+"'>" + status[1] + "</a></td></tr>";
			 					}
	 						}
						}else{
		 					element += "<tr title = '"+status[1]+"' ><td>" + val + "</td><td>" + status[0]+ "</td></tr>";
		 				} 
		 			}
		 		}
		 	}
		}
	 	isPendingStatus =  data.isPending;
		//element += "</table>";
	 	if (!data.isPending) {
			//showFilterTab();
			window.clearInterval(w);
		}
		}catch(err){alert(err);}
		$("#rts_widget_content").html(element);
			$(providerIdEventArr.toString()).click(function(event){
			    event.preventDefault();
			    forwardExistingCustomerCKO($(this).attr("id"));
			});	
	}
	
	$("#sellProvidersCount").val($("#rts_widget_content tbody tr").find("td:contains('Sell')").length);
	console.log("RealTime Content :: "+$("#sellProvidersCount").val())
}
function showFilterTab() {
	var filterDisplay = '${filterDisplay}';
	if(filterDisplay != '' && filterDisplay == 'Yes' && isPendingStatus == false){
		showFilters(); 
	}
} 
function forwardExistingCustomerCKO(providerExternalId)
{
	providerExternalId = providerExternalId.toString()
	  if(providerExternalId.toString().indexOf("_") != -1){
		  $.blockUI({ message: $('#domMessage') }); 
		$("#existingCustomer input[id='providerExternalId']").val(providerExternalId.split("_")[1]);
		$("#existingCustomer input[id='mamAddress']").val(providerExternalId.split("_")[0]);
		$("#existingCustomer input[id='pageTitleName']").val($('input[name="pageTitle"]').val());
		$("#existingCustomer").submit();
	}else{
		$("#existingCustomer input[id='providerExternalId']").val(providerExternalId);
		$("#existingCustomer input[id='existingCustomerId']").val("true");
		$("#existingCustomer").submit();
	}  
	
}


var categoryFilter = function(){
	var path =$("input#contextPath").val();
	var url = path+"/salescenter/recommendationsbyCategory/filtering";
    var data = {};

    data["provider"] = $("#provider").val();
    console.log("Provider : ",$("#provider").val());
    data["categoryName"] = $("input#categoryName").val();
    data["internetSpeed"] =   $("#internetSpeed").val();
    data["channels"] =  $("#channels").val();
    data["contractTerm"] =  $("#contractTerm").val();
    data["path"] = $("input#contextPath").val();
    if($("input[id='latino']:checked").length != 0){
    	data["latino"] =  "1";
    }else{
       	data["latino"] =  "0";
    }
    console.log($(this).val());
    data["result"] = $(this).val();
    try{ 
	    $.ajax({
	    	type: 'POST',
	    	url: url,
	    	data: data,
	    	success: onFilterComplete,
	 	});
	} catch(e){
		alert(e);
	}
}
var onFilterComplete = function(data){
	if(data=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		$(".tabsWrapper").html(data);
		$('.pp_checkbox').click(showCompareButton);
		$('.ViewDetailsBtn').click(viewDetails);
	}
}

function showFilters()
{
	try
	{
		$("#filterProductsDivId").css("background-color", "#97D444");
	 	$("#realTimeStatusDivId").css("background-color", "#666666");
		$('#filtersDataId').show();
		$('#reaiTimeContentDataId').hide();
	}
	catch(err){alert(err);}
}

function showRealTimeStatusBar()
{
	try
	{
		$("#realTimeStatusDivId").css("background-color", "#97D444");
	 	$("#filterProductsDivId").css("background-color", "#666666");
		$('#filtersDataId').hide();
		$('#reaiTimeContentDataId').show();
	}
	catch(err){alert(err);}
}
 function customJsonEach(jsonObj,selector,filterAttr){
	try{
		var tableData = "<table><tr>";
		var count = 0;
		$.each(jsonObj, function(key, value) {
			if(count==3)
			{
				tableData = tableData+"</tr><tr>";
				count = 0;
			}
			tableData = tableData+"<td class='filter_values'>"+value+":</td><td><input type='checkbox' class=checkbox_"+selector+" filter_seq='"+filterAttr+"' value='"+key+"'/></td>&nbsp;&nbsp;&nbsp;";
			count++;
		});	
		tableData = tableData+"</tr></table>";
		$('#'+selector).append(tableData);
	}catch(e){
	}
 }
</script>

<style type="text/css">
</style>
</head>
<input type="hidden" value="" id="tempData"/>
<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
	<form name="existingCustomer" id="existingCustomer" action="${flowExecutionUrl}" method="post" autocomplete="off">
		<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
		<input type="hidden" id="_eventId" name="_eventId" value="existingCustomerEvent">
		<input type="hidden" id="customerId" value="${order.customerInformation.customer.externalId}" />
		<input type="hidden" id="orderId" value="${order.externalId}" name="orderId" />	
		<input type="hidden" id="mamAddress" value="${mamAddress}" name="mamAddress" />		
		<input type="hidden" id="pageTitleName" value="" name="pageTitleName" />					       
		<input type="hidden" id="providerExternalId" name="providerExternalId" value="">
		<input type="hidden" id="existingCustomerId" name="isexistingCustomer" value="">
	</form>
					<div class="rts_widget" data-ng-controller="realContentController" data-ng-init="init()" ng-cloak>
						<div class="rts_container">
							<div class="pdet_tabs">
								<c:choose>
									<c:when test="${not empty filterDisplay && filterDisplay == 'Yes'}">
										<div class="realtime_widget_tab" onclick="showRealTimeStatusBar()" id="realTimeStatusDivId">Real Time Status&nbsp;&nbsp;</div>
										<div class="filter_widget_tab" onclick="showFilters()" id="filterProductsDivId">&nbsp;&nbsp;Filter Products</div>
									</c:when>
									<c:otherwise>
										<div class="realtime_widget_tab" style="float: none; width: 160px;" id="realTimeStatusDivId">Real Time Status&nbsp;&nbsp;</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div style="border-bottom: 1px solid #000; padding-bottom:1px;">
							</div>
							<div id="reaiTimeContentDataId" >
								<table id="rts_widget_content" cellpadding="0" cellspacing="0">
								</table>
								<div id="dataRefreshDiv" style="display: none;">
									<a id="dataRefresh" class="reload"  title="More products available">Refresh</a>
								</div>
								<div id="afterDataRefreshDiv" style="display: none;color: #FF0063">Products Refreshed</div>
							</div>
							<div style="display: none; overflow-x: hidden; overflow-y: auto; height: 187px" id="filtersDataId" >
								<div>
									<a style="float: right; color: #97d444;font-size:14px;padding-right: 20px;" href="#" id="clear" class="clear">Clear All</a>
								</div>
								<table cellpadding="0" cellspacing="0" style="padding-top: 15px; width: 240px;">
									<tr class = "providerTR" style="padding-bottom: 1px; border-bottom: 1px solid rgb(0, 0, 0);" >
									<td class="underline">Providers<a style="float: right; color: #97d444;font-size:14px;" href="#" id="filtter_content_clear_provider" class="clear">Clear</a>
									 <span class="collapse-close filtter_heading" filter_seq="provider" id="filter_provider"></span></td></tr>
									<tr class = "providerTR">
									<td   id = "filtter_content_provider" filter_seq="provider" class="filtter_content_display">
											<table>
												<tr ng-repeat="(key,value) in filterProviderNamesAndExtIdsMap"  ng-show="hideHugheNet(key)">
												   <td>
												        <span class="provider_checkboxLbl">{{value}}:</span>
												   </td>
												   <td>
													   <span class="provider_checkboxVal" filter_seq="provider">
													       <input type="checkbox" filter_seq="provider" data-ng-click="selectedCheckBoxs('provider',key)" ng-checked = "isChecked(key,'provider');" class ="checkbox_filtter_content_provider" value="{{key}}">
													   </span>
												   </td>
												</tr>
											</table>
										<!--</select>-->
									</td>
								</tr>
									<c:if test="${title == 'recommendations.header' || title == 'recommendations.TRIPLE_PLAY' || title == 'recommendations.DOUBLE_PLAY' ||
													title == 'recommendations.INTERNET' || title == 'recommendations.DOUBLE_PLAY_PI' 
													|| title == 'recommendations.DOUBLE_PLAY_VI'}">
									<tr class="internetTR">
										<td class="underline">Internet Speed
										<a style="float: right; color: #97d444;font-size:14px;" href="#" id="filtter_content_clear_internetSpeed" class="clear">Clear</a>
										<span class="collapse-close filtter_heading" filter_seq="internetSpeed" id="filter_internetSpeed"></span>
										</td>
									 </tr>
									 </c:if>
										<tr  class="internetTR">
										<c:if test="${title == 'recommendations.header' || title == 'recommendations.TRIPLE_PLAY' || title == 'recommendations.DOUBLE_PLAY' ||
													title == 'recommendations.INTERNET' || title == 'recommendations.DOUBLE_PLAY_PI' 
													|| title == 'recommendations.DOUBLE_PLAY_VI'}">
										<td id = "filtter_content_internetSpeed" filter_seq="internetSpeed" class="filtter_content_display">
											<table>
												<tr>&nbsp;</tr>
											<tr class="speedContent" ng-repeat="values in internetRangeList  " >
											 <td>
											    {{values}}M
											  </td>
												<td>
												   <input type="checkbox" filter_seq="internetSpeed" data-ng-click="selectedCheckBoxs('internetSpeed',values)" ng-checked="isChecked(values,'internetSpeed');" class ="checkbox_filtter_content_internetSpeed" value="{{values}}"/>
										        </td>
											</tr>
											</table>	
										</td></c:if>
									</tr>
									<!-- Channel Lineup start -->
									<tr class="channelsTR">
									<c:if test="${title == 'recommendations.header' || title == 'recommendations.TRIPLE_PLAY' || title == 'recommendations.DOUBLE_PLAY' ||
													title == 'recommendations.VIDEO'  
													|| title == 'recommendations.DOUBLE_PLAY_PV' || title == 'recommendations.DOUBLE_PLAY_VI'}">
										<td class="underline">Channel Lineup
										<a style="float: right; color: #97d444;font-size:14px;" href="#" id="filtter_content_clear_channels" class="clear">Clear</a>
										<span class="collapse-close filtter_heading" filter_seq="channels" id="filter_channels"></span>
										</td>
									</c:if>
									</tr>
									<tr class="channelsTR">
										<c:if test="${title == 'recommendations.header' || title == 'recommendations.TRIPLE_PLAY' || title == 'recommendations.DOUBLE_PLAY' ||
													title == 'recommendations.VIDEO'  
													|| title == 'recommendations.DOUBLE_PLAY_PV' || title == 'recommendations.DOUBLE_PLAY_VI'}">
										<td id = "filtter_content_channels" filter_seq="channels" class="filtter_content_display">
											<table>
												<tr>&nbsp;</tr>
											<tr class="speedContent" ng-repeat="value in channleRangeList " >
											 <td>
											    {{value}}
											  </td>
												<td>
												   <input type="checkbox" filter_seq="channels"  data-ng-click="selectedCheckBoxs('channels',value)" ng-checked="isChecked(value,'channels');" class ="checkbox_filtter_content_channels" value="{{value}}"/>
										        </td>
											</tr>
											</table>											
											</td></c:if>
									</tr>
								<!-- Channel Lineup end -->
								<!-- Contract term Start -->
									<tr class="contractTR">
										<td class="underline">Contract term<a style="float: right; color: #97d444;font-size:14px;" href="#" id="filtter_content_clear_contract" class="clear">Clear</a>
										<span class="collapse-close  filtter_heading" filter_seq="contract" id="filter_contract"></span></td></tr>
										<tr class="contractTR">
										
										<td id = "filtter_content_contract" filter_seq="contract" class="filtter_content_display">
												<table><tr class="contractTerm" ng-repeat="value in contractTermList">
												<td>{{value}}</td>
												<td><input type="checkbox" filter_seq="contractTerm" data-ng-click="selectedCheckBoxs('contractTerm',value)" ng-checked="isChecked(value,'contractTerm');" class ="checkbox_filtter_content_contract" value="{{value}}"/></td>
												</tr></table>
											<!--</select>
										--></td>
									
									</tr>
									<!-- Contract term end -->
										<tr >
										<c:if test="${title == 'recommendations.header' || title == 'recommendations.TRIPLE_PLAY' || title == 'recommendations.DOUBLE_PLAY' ||
													title == 'recommendations.VIDEO'  
													|| title == 'recommendations.DOUBLE_PLAY_PV' || title == 'recommendations.DOUBLE_PLAY_VI'}">
											<td class="underline"><label class = "englishLatino">English Only:</label><input type="checkbox" id="latino" name="latino" data-ng-click="selectedCheckBoxs('latino','no')" 	class ="checkbox_filtter_content_latino englishLatino" ng-checked="isChecked('no','latino');" filter_seq="latino" value="no"  
													/><label class="latinoFilterDiv" >Spanish Only:</label><input  type="checkbox" id="latino" name="latino" data-ng-click="selectedCheckBoxs('latino','yes')" ng-checked="isChecked('yes','latino');" class ="checkbox_filtter_content_latino latinoFilterDiv"  filter_seq="latino" value="yes"
													/></td>
												<td >
												</td>
										<td >
										</td>
										</c:if>
									</tr>
								</table>
							</div>
						</div>
					</div>
	 <div id="domMessage" style="display: none;"><img
	src="<%=request.getContextPath()%>/images/loading.gif" border="0" />
<h2>Loading</h2>
</div>