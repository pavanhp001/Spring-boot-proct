$(document).ready(function(){
	$("input.dateType").datepicker();
	$("select#typeOfSearch").change(updateInputs);
	
	$("input#button").click(searchOrders);
	$("input#ucid").keydown(validateUcid);
});

var updateInputs = function(){
	$("div#inputBlock > span").hide();
	 $('#preLoader').hide();
	var selectVal = $("select#typeOfSearch").val();
	var searchType = selectVal;
	if(selectVal == "getWebOrdersWithAgent"){
		if($("select#agentOrderId").length > 0 && $("select#agentOrderId").val() != ""){
			searchType = "agentWithOrderId";
		}
	}
	if(selectVal == "getWebOrdersWithCustomer"){
		if($("select#customerOrderId").length > 0 && $("select#customerOrderId").val() != ""){
			searchType = "customerWithOrderId";
		}
	}
	$("input#searchType").val(searchType);
	
	
	
	showHideOptions(selectVal);
}

function showHideOptions(selectVal){
	if(selectVal == "getWebOrdersWithAgent"){
		$("span#agentSpan").show();
	} else if(selectVal == "getWebOrdersWithOrder"){
		$("span#orderSpan").show();
	} else if(selectVal == "getWebOrdersWithCustomer"){
		$("span#customerSpan").show();
	} else if(selectVal == "getWebOrders"){
		$("span#agentOrderSpan").show();
	} else if(selectVal == "getWebOrdersWithProvider"){
		$("span#agentProviderSpan").show();
	} else if(selectVal == "getWebOrdersWithDate"){
		$("span#calendarSpan").show();
	}	
	else if(selectVal == "getWebOrdersWithUcid"){
		$("span#ucidSpan").show();
	}	
}

function searchOrders(){
	//clearing the previous search results
	$("div.dataBlock").remove();
	$("div.navigationBlock").html("");
	$("input#currentPage").val("0");
	$("input#totalPages").val("0");
	 $('#preLoader').show();
	var path = $("#path").val();
	var url = path+"/salescenter/salesOrderSearch";
	var data = {};
	data["typeOfSearch"] = $("select#typeOfSearch").val();
	data["searchType"] 	= $("input#searchType").val();
	data["agentId"] = $("input#agentId").val();
	data["orderId"] = $("input#orderId").val();
	data["agentOrderId"] = $("select#agentOrderId").val();
	data["providerId"] = $("input#providerId").val();
	data["cal1"] = $("input#cal1").val();
	data["cal2"] = $("input#cal2").val();
	data["firstName"] = $("input#firstName").val();
	data["lastName"] = $("input#lastName").val();
	data["zipCode"] = $("input#zipCode").val();
	data["phoneNo"] = $("input#phoneNo").val();
	data["emailId"] = $("input#emailId").val();
	data["address"] = $("input#address").val();
	data["customerOrderId"] = $("select#customerOrderId").val();
	data["ucid"] = $("input#ucid").val();
	
	try{    
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			success: parseResponseData,
			error: function(data){
				alert(data);
			}
		});
	} catch(e){
		alert(e);
	}
}
var validateUcid = function(e){
	if (e.shiftKey || e.ctrlKey || e.altKey) { 
	                e.preventDefault();         
	            } else {
	                var n = e.keyCode;
	                if (!((n == 8)             
	                        || (n == 46)               
	                        || (n >= 35 && n <= 40)     
	                        || (n >= 48 && n <= 57)     
	                        || (n >= 96 && n <= 105))   
	                        ) {
	                    
	                    e.preventDefault();     
	                }
	            }
	}
function parseResponseData(data){
	var data = JSON.parse(data);
	$('#preLoader').hide();
	if($("input#searchType").val() == "getWebOrdersWithAgent"){
		$("select#agentOrderId").remove();
		$("span.clearSpan").remove();
		var orderIdList = data.orderIdList;
		if(orderIdList != undefined && orderIdList.length > 0){
			var select = $("<select><select>").addClass("selectStyle").attr("id", "agentOrderId").css("margin-left", "5px");
			select.append($("<option></option>").val("").text("Select OrderId"));
			for(var k=0; k < orderIdList.length; k++){
				var orderId = orderIdList[k];
				if(orderId != undefined && orderId != ""){
					select.append($("<option></option>").val(orderId).text(orderId));
				}	
			}
			select.change(function(){
				if($(this).val() == ""){
					$("input#searchType").val("getWebOrdersWithAgent");
				} else {
					$("input#searchType").val("agentWithOrderId");	
				}
				
			});
			
			$("span#agentSpan").append(select);
			
			var clear_span = $("<span>Clear</span>").addClass("clearSpan");
			clear_span.click(clearAgentResults);
			$("span#agentSpan").append(clear_span);
		}else {
			$("div.navigationBlock").append($("<div>No Results found</div>").addClass("noData"));
		}

	} else if($("input#searchType").val() == "getWebOrdersWithCustomer"){
		$("select#customerOrderId").remove();
		$("span.clearSpan").remove();
		var orderIdList = data.orderIdList;
		if(orderIdList != undefined && orderIdList.length > 0){
			var select = $("<select><select>").addClass("selectStyle").attr("id", "customerOrderId").css("margin-left", "10px");
			select.append($("<option></option>").val("").text("Select OrderId"));
			for(var k=0; k < orderIdList.length; k++){
				var orderId = orderIdList[k];
				if(orderId != undefined && orderId != ""){
					select.append($("<option></option>").val(orderId).text(orderId));
				}	
			}
			select.change(function(){
				if($(this).val() == ""){
					$("input#searchType").val("getWebOrdersWithCustomer");
				} else {
					$("input#searchType").val("customerWithOrderId");	
				}
				
			});
			var order_label = $("<label>Order Id:</label>").addClass("clearLabel");
			$("span#customerOrdersSpan").append(order_label);
			$("span#customerOrdersSpan").append(select);
			
			var clear_span = $("<span>Clear</span>").addClass("clearSpan");
			clear_span.click(clearCustomerResults);
			$("span#customerOrdersSpan").append(clear_span);
		}else {
			$("div.navigationBlock").append($("<div>No Results found</div>").addClass("noData"));
		}
	}
	else if($("input#searchType").val() == "getWebOrdersWithUcid")
	{
		$("select#agentOrderId").remove();
		$("span.clearSpan").remove();
		var orderIdList = data.orderIdList;
		if(orderIdList != undefined && orderIdList.length > 0){
			var select = $("<select><select>").addClass("selectStyle").attr("id", "agentOrderId").css("margin-left", "5px");
			select.append($("<option></option>").val("").text("Select OrderId"));
			for(var k=0; k < orderIdList.length; k++){
				var orderId = orderIdList[k];
				if(orderId != undefined && orderId != ""){
					select.append($("<option></option>").val(orderId).text(orderId));
				}	
			}
			select.change(function(){
				if($(this).val() == ""){
					$("input#searchType").val("getWebOrdersWithAgent");
				} else {
					$("input#searchType").val("agentWithOrderId");	
				}
				
			});
			
			$("span#ucidSpan").append(select);
			
			var clear_span = $("<span>Clear</span>").addClass("clearSpan");
			clear_span.click(clearUcidResults);
			$("span#ucidSpan").append(clear_span);
		} else {
			$("div.navigationBlock").append($("<div>No Results found</div>").addClass("noData"));
		}
	}
	else {

		var orderList = data.orderList;
		if(orderList != undefined && orderList.length > 0){
			//add previous button
			var a = $("<a></a>").text("<< Previous").addClass("prevousLink").attr({"id":"previous","title":"Previous"});
			a.click(showPage);
			$("div.navigationBlock").append(a);
			
			var div_links = $("<div></div>").attr("id", "linksBlock");
			var i = 1;
			$(orderList).each(function(){
				var page = this.page;
				var customerId = this.customerId;
				var orderId = this.orderId;
				var title = page + " - ";
				if(customerId != undefined && customerId != ""){
					title = title + "Customer Id: "+customerId;
				}
				
				if(orderId != undefined && orderId != ""){
					title = title + "/Order Id: "+orderId;
				}
				
				var id = "page_"+this.id;
				var a = $("<a></a>").text(i).addClass("pageLink_"+i).attr({"id":id,"title":title});
				a.click(showPageContent);
				div_links.append(a);
				
				i++;
			});
			
			$("div.navigationBlock").append(div_links);
			$("input#totalPages").val(i-1);
			
			//add next button
			var a = $("<a></a>").text("Next >>").addClass("prevousLink").attr({"id":"next","title":"Previous"});
			a.click(showPage);
			$("div.navigationBlock").append(a);
			
			$("div.navigationBlock").append($("<span></span>").attr("id", "selectedPage").css("display", "none"));
			
			var dataBlock = $("<div></div>").addClass("dataBlock");
			dataBlock.append($("<div></div>").addClass("noPageContent").html("Click on the link to see the content"));
			$("div.resultsBlock").append(dataBlock);
			
			showHideNavigationLinks();
		} else {
			$("div.navigationBlock").append($("<div>No Results found</div>").addClass("noData"));
		}
	}
}

function showPageContent(){
	$("a[class^='pageLink']").removeClass("selPage");
	$(this).addClass("selPage");
	var page_id = this.id;
	var title = $(this).attr("title");
	var page = parseInt($(this).text(), 10);
	$("input#currentPage").val(page);

	$("span#selectedPage").show();
	$("span#selectedPage").text("");
	$("span#selectedPage").append($("<b></b>").text("Page: "));
	$("span#selectedPage").append(page+". "+title);
	
	var path = $("#path").val();
	var url = path+"/salescenter/getSalesOrderPage";
	var data = {};
	data["page_id"] = page_id.split("_")[1];
	try{    
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			success: function(data){
				$("div.dataBlock").html(data);
				$("body").animate({scrollTop:$('div.resultsBlock').position().top}, 'slow');
			},
			error: function(data){
				alert(data);
			}
		});
	} catch(e){
		alert(e);
	}
}

function showPage(){
	var id = this.id;
	if(id == "previous"){
		var currentPage = parseInt($("input#currentPage").val(), 10);
		if(currentPage == 1 || currentPage == 0){
			stat = false;
			alert("You can't navigate beyond this page");
		} else {
			var prev_page = currentPage - 1;
			$("a.pageLink_"+prev_page).click();
			navigateLinks(id);
		}
	} else {
		var currentPage = parseInt($("input#currentPage").val(), 10);
		var totalPages = parseInt($("input#totalPages").val(), 10);
		if(currentPage == totalPages){
			alert("You can't navigate beyond this page");
		} else {
			var next_page = currentPage + 1;
			$("a.pageLink_"+next_page).click();
			navigateLinks(id);
		}
	}
}

function clearAgentResults(){
	$("select#agentOrderId").remove();
	$("input#agentId").val("");
	$("input#searchType").val("getWebOrdersWithAgent");
	$("span.clearSpan").remove();
}


function clearCustomerResults(){
	$("select#customerOrderId").remove();
	$("input#firstName").val("");
	$("input#lastName").val("");
	$("input#zipCode").val("");
	$("input#phoneNo").val("");
	$("input#emailId").val("");
	$("input#address").val("");
	$("input#searchType").val("getWebOrdersWithCustomer");
	$("span.clearSpan").remove();
	$("label.clearLabel").remove();
	
}


function clearUcidResults(){
	$("select#agentOrderId").remove();
	$("input#ucid").val("");
	$("input#searchType").val("getWebOrdersWithUcid");
	$("span.clearSpan").remove();
}


function showHideNavigationLinks(){
	var currentPage = parseInt($("input#currentPage").val(), 10);
	var totalPages = parseInt($("input#totalPages").val(), 10);
	
	if(totalPages >  10){
		$("a[class^='pageLink_']").hide();
		$("a[class^='pageLink_']").slice(0, 10).show();
	}
}

function navigateLinks(id){
	var currentPage = parseInt($("input#currentPage").val(), 10);
	var totalPages = parseInt($("input#totalPages").val(), 10);
	
	if(totalPages >  10){
		if(id == "next"){
			var lastVisible = parseInt($("a[class^='pageLink_']:visible:last").text(), 10);
			if(totalPages - lastVisible > 0 && lastVisible - currentPage < 7){
				$("a[class^='pageLink_']:visible:first").hide();
				$("a[class^='pageLink_']:visible:last").next().show();
			}
		} else {
			var firstVisible = parseInt($("a[class^='pageLink_']:visible:first").text(), 10);
			if(firstVisible > 1 && currentPage - firstVisible < 7){
				$("a[class^='pageLink_']:visible:last").hide();
				$("a[class^='pageLink_']:visible:first").prev().show();
			}
		}
	}
}