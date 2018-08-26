var lineItemIds = [];
var providerIds = [];
var productSrcs = [];
var providerNames = [];
$(document).ready(function() {
	$(".forwardArrowBtn").click(recap);
	$("input[name='orderRecap']").click(fnOpenNormalDialog);
	//TODO: change to add lineitem page
	$(".backArrowBtn").click(recap);

	$("input[name='orderCKOSelected']").click(orderCKOSelected);
//	$("input[name='orderRecap']").click(placeOrder);
	$("input[name='remove']").click(removeLineItem);
	$("input[name='reAdd']").click(reAddOrderSummary);
	
	$('.statusHistoryToggle').unbind("dblclick");
	$(".statusHistoryToggle").click(function(){
	    var parent = $(this).closest("div");
	    var div = parent.parent().parent().next("div").find("div#statusHistory");
	    if(div.is(":hidden")){
	        $(this).addClass("shMinus");
	    } else {
	        $(this).removeClass("shMinus");
	    }
	    div.slideToggle();
	});
	
	//Will enable it based on need
	//To save the html on page load
	//savePageHtml(true, "");
});
var reAddOrderSummary = function(){
	
	$("span#errorMsg").remove();
	
	disablePage();

	var mainDiv = $(this).parent().parent().parent();
	var div = $("<div>").addClass("loaderBlock");
	div.css('height',mainDiv.height());
	div.css('width',mainDiv.width);
	mainDiv.children().hide();
	mainDiv.append(div);
	var oId = $("#orderId").val();
	var value = this.id;
	var path = $("#contextPath").val();
	
	var url = path+"/salescenter/scart/cartRulesValidator";
	var data = {};
	data["orderId"] = oId;
	data["lineItemId"] = value;

	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onLiAddComplete
	});
}


var fnOpenNormalDialog = function(){
	var prds = document.getElementsByClassName('confirmSec'),i = prds.length;
	var hasSec = false;
	var confirmedSecurity = false;
	var confirmedSec = document.getElementById('confirmedSec').innerHTML;
if (confirmedSec.trim() == "true") {
	confirmedSecurity = true;
}
while(i--) {
	var testSec = prds[i].innerHTML;
	if(testSec.trim() == "ADT") {
		hasSec = true;
	}
}
	var allowPlaceOrder = $("#placeOrder").val();
	if (!hasSec && allowPlaceOrder == 'true' && !confirmedSecurity) {	
    $("#dialog-confirm-adt").dialog({
        resizable: false,
        title: "Reminder:  You must offer Home Security",
        height: 197,
        width: 407,
        modal: true,
        zIndex: 1000
    });
	} else {
		placeOrder();
	}
}

var goToSecurityProducts = function() {
	var path = $("#contextPath").val();
	 //To save the html on moving to order recap
	 savePageHtml(false, "");
	window.location.href = path+"/salescenter/recommendationsbyCategory/HOMESECURITY";	
}

var placeOrder = function(){
	var allowPlaceOrder = $("#placeOrder").val();
	if(allowPlaceOrder == 'true'){
		 //To save the html on moving to order recap
		 savePageHtml(false, "");
		 
		 $("form#ordersummary").submit();
	}else{
		alert("All Products must be in CKO_Complete or removed status to place order.");
		return false;
	}
}

var onLiAddComplete = function(data){
	data = JSON.parse(data.responseText);
	var ltId = data.lineItemId;
	if(data.stat){
		var oId = $("#orderId").val();
		var path = $("#contextPath").val();
		$("#ordersummary").attr('action',path+'/salescenter/order/'+oId+'/lineitem/'+ltId+'/add');
		$("#ordersummary").submit();
	} else {
		enablePage();
		//handle error message;
		var mainDiv = $("input#"+ltId).parent().parent().parent();
		var div = $("<div style='margin-top: 15px;'></div>");
		var msg = data.error.split("|");
		var span = $("<span id ='errorMsg'></span>").text(msg[1]);
		div.append(span);
		mainDiv.find('div.os_itemdetails').html(div);
		mainDiv.find('div.loaderBlock').remove();
		mainDiv.children().show();
	}		
}

var reAdd = function(){

	//Removing Cart Errors
	$("h4#cartError").next().remove();
	$("h4#cartError").remove();
	
	disablePage();
	
	var h4 = $(this).closest("h4");
	h4.find(".itemCheckBox").attr("disabled", false);
	h4.find("span#prodId").css("color", "#000")
	h4.find("div").css("display", "none");
	
	addScrollDiv(h4);
	
	var oId = $("#orderId").val();
	var liId = h4.find("input[id^='li_externalId_']").val();
	var path = $("#contextPath").val();
	
	var url = path+"/salescenter/scart/cartRulesValidator";
	var data = {};
	data["orderId"] = oId;
	data["lineItemId"] = liId;

	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onReAddComplete
	});
}

var onReAddComplete = function(data){
	data = JSON.parse(data.responseText);
	if(data.stat){
		var oId = $("#orderId").val();
		var ltId = data.lineItemId;
		var path = $("#contextPath").val();
		$("#ordersummary").attr('action',path+'/salescenter/order/'+oId+'/lineitem/'+ltId+'/add');
		$("#ordersummary").submit();
	}else{
		enablePage();
		
		var liId = data.lineItemId;
		var h4 = $("input#li_externalId_"+liId).parent();
		var msg = data.error.split("|");
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
}

var addScrollDiv = function(h4){
	var path =$("input#contextPath").val();
	var scrollDiv = $("<div></div>").addClass("alignCenter");
	var img = $("<img/>").attr("src", path+"/images/scroll.gif");
	scrollDiv.append(img);
	h4.prepend(scrollDiv);
}
var removeLineItem = function(){
	var value = $(this).attr('id');
	var orderId = $("#orderId").val();
	var lineItemId = value;
	var path = $("#contextPath").val();
	var url = path +"/rest/order/"+ orderId + "/lineitem/" + lineItemId+"/delete";
	disablePage();
	$("form[name='ordersummary']").attr("action",url);
	$("form[name='ordersummary']").submit();
}


function orderCKOSelected(){
	var len = $("input.productCheckBox:checked").length;
	if(len == 0){
		alert("Please select orders to process");
	} else {
		var failedCKORules = false;
		$('input.productCheckBox:checked').each(function() {
	
				var div = $(this).closest("div");
				var provId = div.find("input[name='providerId']").val();
				
				if(!CKORules(provId)){
					failedCKORules = true;
					return false;
				}
				
				var lineItemId = div.find("input[name='ltId']").val();
				var prodSrc = div.find("input[name='productSource']").val();
				var lineItemProviderName = div.find("input[name='lineItemProviderName']").val();
	
				lineItemIds.push(lineItemId);
				providerIds.push(provId);
				productSrcs.push(prodSrc);
				providerNames.push(lineItemProviderName);
			});
			
			if(failedCKORules){
				return false;
			}
			
			//To save the html on multiple product processing
			savePageHtml(false, "");
			
			$("input#lineItemIds").val(lineItemIds);
			$("input#providerIds").val(providerIds);
			$("input#productSrcs").val(productSrcs);
			$("input#providerNames").val(providerNames);
	
			var custId = $("#customerId").val();
			var orderId = $("#orderId").val();
			var path = $("#contextPath").val();
			var url = path+"/rest/CKO/"+ custId + "/order/" + orderId;
	
			$("form[name='ordersummary']").attr("action",url);
			$("form[name='ordersummary']").submit();
		
	}
}

function CKO(liId, provId,lineItemProviderName,prodSrc) {
	
	if(CKORules(provId)){
	
		//To save the html on single product processing
		savePageHtml(false, "");
		
		lineItemIds.push(liId);
		providerIds.push(provId);
		productSrcs.push(prodSrc);
		providerNames.push(lineItemProviderName);
		
		$("input#lineItemIds").val(lineItemIds);
		$("input#providerIds").val(providerIds);
		$("input#productSrcs").val(productSrcs);
		$("input#providerNames").val(providerNames);
		
		var custId = $("#customerId").val();
		var orderId = $("#orderId").val();
		var path = $("#contextPath").val();
		var url = path+"/rest/CKO/"+ custId + "/order/" + orderId;
	
		$("form[name='ordersummary']").attr("action",url);
		$("form[name='ordersummary']").submit();
		
	}else{
		return false;	
	}
}
function disablePage(){
	$("#ordersummary").find("input:button").attr("disabled", "disabled");
	$("#summaryForm").find("*").attr("disabled", "disabled");
	$(".cusinfo_container").find("*").attr("disabled", "disabled");
}
function enablePage(){
	$("#ordersummary").find("input:button").removeAttr("disabled");
	$("#summaryForm").find("*").removeAttr("disabled");
	$(".cusinfo_container").find("*").removeAttr("disabled");
}

function CKORules(providerId){
	
	var hasAtt = 0;
	var	hasVerizon = 0;
	var	hasG2b = 0;
	var	hasDish = 0;
	var	hasDirectStar = 0;
	var	hasComcast = 0;
	var hasCenturyLink=0;
	var hasFrontier = 0;
	var hasCox = 0;
	var hasAdt = 0;
	var hasTxu = 0;
	var success = true;
	
	$('input.productCheckBox').each(function(){
		
		var div = $(this).closest("div");		
		var provId = div.find("input[name='providerId']").val();
		
		if(provId == "15500201" || provId == "24699452"){
			hasAtt++ ;
		}else if(provId == "4353598"){
			hasVerizon++ ;
		}else if(provId == "26069940"){
			hasG2b++ ;
		}else if(provId == "27010360"){
			hasDish++ ;
		}else if(provId == "2314635"){
			hasDirectStar++ ;
		}else if(provId == "26069942"){
			hasComcast++;
		}else if(provId == "32416075"){
			hasCenturyLink++;
		}else if(provId == "32937483"){
		   	hasFrontier++;
		}else if(provId == "15499341"){
		   	hasCox++;
		}
		else if(provId == "15498481"){
		   	hasAdt++;
		}else if(provId == "15499381"){
			hasTxu++;
		}
	});
	
	if(providerId == "15500201" ||  providerId == "24699452"){
		if(hasAtt > 1){
			success = false;
		}
	}else if(providerId == "4353598"){
		if(hasVerizon > 1){
			success = false;
		}
	}else if(providerId == "26069940"){
		if(hasG2b > 1){
			success = false;
		}
		
	}else if(providerId == "27010360"){
		if(hasDish > 1){
			success = false;
		}
	}else if(providerId == "2314635"){
		if(hasDirectStar > 1){
			success = false;
		}
	}else if(providerId == "26069942"){
		if(hasComcast > 1){
			success = false;
		}
	}else if(providerId == "32416075"){
		if(hasCenturyLink > 1){
			success = false;
		}
	}else if(providerId == "32937483"){
		if(hasFrontier > 1){
			success = false;
		}
	}else if(providerId == "15499341"){
		if(hasCox > 1){
			success = false;
		}
	}else if(providerId == "15498481"){
		if(hasAdt > 1){
			success = false;
		}
	}else if(providerId == "15499381"){
		if(hasTxu > 1){
			success = false;
			
		}
	}
	
	if(success){
		return true;
	}else{
	        $(".dialogMultipleproducts").dialog({
				resizable : false,
				title:"Alert",
				height : 200,
				width : 800,
				modal : true,
				zIndex : 99999
			  });
		return false;
	}
	
}

var recap = function() {
	$("form[name='ordersummary']").submit();
}
