$(document).ready(function() 
{
	//submit form
	$("select#typeOfSearchCriteria").change(updateInputs);
	$("#searchSubmit").click(submitForm);

	//To submit form on pressing enter key
	$(document).keypress(function(event) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == '13') {
			submitForm();
		}
	});

	//Constructing datepicker for AgentId search
	var d = new Date();
	var monthNames = ["January", "February", "March", "April", "May", "June",
	                  "July", "August", "September", "October", "November", "December"];
	today = monthNames[d.getMonth()] + ' ' + d.getDate() + ' ' + d.getFullYear();

	$('#toDate').attr('disabled', 'disabled');
	$('#fromDate').datepicker({
		changeMonth: true,
		changeYear: true,
		maxDate:0
	});
	$('#fromDate').change(function () {
		var from = $('#fromDate').datepicker('getDate');
		var date_diff = Math.ceil((from.getTime() - Date.parse(today)) / 86400000);
		var maxDate_d = date_diff+7+'d';
		if(date_diff > -7){
			   maxDate_d = '0d';
			  }
		date_diff = date_diff + 'd';
		$('#fromDate').focus().attr('readonly','readonly');
		$('#toDate').val('').removeAttr('disabled').removeClass('hasDatepicker').datepicker({
			minDate: date_diff,
			maxDate: maxDate_d,
			onSelect: function ()
		    {
		       this.focus();
		    }
		});
	});
	
	$('#fromDate').keyup(function () {
		$('#fromDate,#toDate').val('');
		$('#toDate').attr('disabled', 'disabled');
	});
	
	$('#fromDate').keypress(function () {
		var val = $('#fromDate').val();
		if(val == '')
		{
			alert('Please select date from Calendar');
		}
		
	});
	
	$('#toDate').keyup(function () {
		$(this).val('');
	});
	
	$('#toDate').keypress(function () {
		var val = $('#toDate').val();
		if(val == '')
		{
			alert('Please select date from Calendar');
		}
	});	
	
	$('span#goToAdvance').click(previousResults);
	
	populateOrder();
	
	
	/*var states = ["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", 
	              "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", 
	              "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", 
	              "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];
	for(var k=0; k < states.length; k++){
		var state = states[k];
		$("select#state").append($("<option value=\""+state+"\">"+state+"</option>"));
	}*/
});


//Tab Change
$(function () 
		{
			var tabContainers = $('div.tabs > div');
			tabContainers.hide().filter(':first').show();
			
			$('div.tabs ul.tabNavigation a').click(function () {
				tabContainers.hide();
				tabContainers.filter(this.hash).show();
				$('div.tabs ul.tabNavigation a').removeClass('selected');
				$(this).addClass('selected');
				updateTypeOfSearch(true);
				
				return false;
			}).filter(':first').click();
		});

//To populateOrder from encore
var populateOrder = function(){
	var orderId = $("input#faOrderId").val();
	if(orderId.length > 0){
		$("select#typeOfSearchCriteria").val("getWebOrdersWithOrder");
		updateInputs();
		$("input#orderId").val( orderId );
		$("input#faOrderId").val('');
		submitForm();
	}
}


//Select change event
var updateInputs = function()
{
	$("div#inputBlock > span").hide();
	var selectVal = $("select#typeOfSearchCriteria").val();
	$("input#searchType").val(selectVal);
	showHideOptions(selectVal);
	var div_o = $("div#orderResults");
	div_o.find("div.dataTables_wrapper").remove();
	$("div.scoreErrorMsg").remove();
	clearInputText("div#order");
};

function showHideOptions(selectVal)
{
	if(selectVal == "getWebOrdersWithAgent")
	{
		$("span#agentSpan").show();
	}  else if(selectVal == "getWebOrdersWithOrder")
	{
		$("span#orderSpan").show();
	} 
	else if(selectVal == "getWebOrdersWithUcid")
	{
		$("span#ucidSpan").show();
	}	
	/*else if(selectVal == "getWebOrdersWithGuid")
	{
		$("span#guidSpan").show();
	}*/
	else if(selectVal == "getWebOrdersWithLineItemId")
	{
		$("span#lineItemSpan").show();
	}
}



var submitForm = function()
{
	var stat = true;
	
	var typeOfSearch = $("input#typeOfSearch").val();
	var number_regex = /^[\d]+$/;
	var div_o = $("div#orderResults");
	div_o.find("div.dataTables_wrapper").remove();
	$('span#goToAdvance').hide();
	
	if(typeOfSearch == "CustomerSearch")
	{
		$("input#searchType").val("getWebOrdersWithCustomer");
		var firstname = $("input#firstname").val();
		var lastname = $("input#lastname").val();
		var zipcode = $("input#zipcode").val();
		var email = $("input#email").val();
		var phone = $("input#phone").val();
		
		var regExp = /^[A-Za-z][A-Za-z\d-\s]+$/;
		var zipRegExp = /(^\d{5}$)|(^\d{5}-\d{4}$)/;
		var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		var phone_regx = /^\(?([0-9]{3})\)?[ -.?]?([0-9]{3})[ -.?]?([0-9]{4})$/;
		
		var crit1 = false;
		var crit2 = false;
		if( isNotEmpty(lastname) && (isNotEmpty(zipcode)) ){
			crit1 = true;
		}
		
		/*if( isNotEmpty(email) || isNotEmpty(phone) ){
			crit2 = true;
		}*/
		
		if(crit1 == false){
			alert("Please enter required fields");
			stat = false;
		} else {
			if (isNotEmpty(firstname) && !firstname.match(regExp)) {
				alert('First Name allows alpha numerics and hyphen only');
				stat = false;
			} else if(isNotEmpty(lastname) && !lastname.match(regExp)){
				alert('Last Name allows alpha numerics and hyphen only');
				stat = false;
			} else if(isNotEmpty(zipcode) && !zipcode.match(zipRegExp)){
				alert('Please enter valid ZipCode(nnnnn or nnnnn-nnnn)');
				stat = false;
			} else if(isNotEmpty(email) && !email.match(email_regex)){
				alert('Please enter valid Email');
				stat = false;
			} else if(isNotEmpty(phone) && !phone.match(phone_regx)){
				alert('Please enter valid phone(nnn-nnn-nnnn)');
				stat = false;
			}
		}
	} 
	else if(typeOfSearch == "BasicSearch")
	{
		var selectVal = $("select#typeOfSearchCriteria").val();
		
		if(selectVal == 'getWebOrdersWithOrder')
		{
			var orderId = $("input#orderId").val().trim();
			if(isEmpty(orderId) || !orderId.match(number_regex)){
				alert('Please enter valid Order Id');
				stat = false;
			}
		}
		else if(selectVal == 'getWebOrdersWithAgent')
		{
			var agentId = $("input#agentId").val().trim();
			if( agentId == ""){
				alert('Agent should not be empty');
				stat = false;
			} else {
				var fromDate = $("input#fromDate").val();
				var toDate = $("input#toDate").val();
				if(isEmpty(fromDate)){
					alert('Please choose From date');
					stat = false;
				} else if(isEmpty(toDate)){
					alert('Please choose To date');
					stat = false;
				}
			}
		}
		else if(selectVal == 'getWebOrdersWithUcid')
		{
			var ucid = $("input#ucid").val().trim();
			if(isEmpty(ucid) || !ucid.match(number_regex)){
				alert('Please enter valid UCID');
				stat = false;
			}
		}
		/*else if(selectVal == 'getWebOrdersWithGuid')
		{
			var guid = $("input#guid").val().trim();
			if(isEmpty(guid)){
				alert('Please enter valid GUID');
				stat = false;
			}
		}*/
		else if(selectVal == 'getWebOrdersWithLineItemId')
		{
			var lineItemID  = $("input#lineItemId").val().trim();
			if(isEmpty(lineItemID)){
				alert('Please enter valid LineItemId');
				stat = false;
			}
		}
	} 
	
	if(stat)
	{
		searchOrders();
	}
	
	
};

function searchOrders()
{
	//clearing the previous search results
	$("div.dataBlock").remove();
	$("div.scoreErrorMsg").remove();
	$("div.navigationBlock").html("");
	$("input#currentPage").val("0");
	$("input#totalPages").val("0");
	$('img#loader').show();
	var path = $("#path").val();
	var url = path+"/rest/salesOrderSearch";
	var data = {};
	
	data["searchType"] 	= $("input#searchType").val();
	data["agentId"] = $("input#agentId").val();
	data["orderId"] = $("input#orderId").val();
	data["cal1"] = $("input#fromDate").val();
	data["cal2"] = $("input#toDate").val();
	data["ucid"] = $("input#ucid").val();
	//data["guid"] = $("input#guid").val();
	data["lineItemId"] = $("input#lineItemId").val();
	
	data["firstName"] = $("input#firstname").val();
	data["lastName"] = $("input#lastname").val();
	data["zipCode"] = $("input#zipcode").val();
	data["phoneNo"] = $("input#phone").val();
	data["emailId"] = $("input#email").val();
	data["address"] = $("input#address").val();
	
	try{    
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			success: parseResponseData,
			error: function(data)
			{
				handleErrorMsg(data);
			}
		});
	} catch(e){
		alert(e.message);
	}
}

function parseResponseData(data)
{
	$('img#loader').hide();
	var path = $("#path").val();
	if(data=="sessionTimeOut"){
		window.location = path+"/rest/login";
	}
	var data = JSON.parse(data);
	var searchCustomerData = data.searchCustomerData;
	var div = $("div#orderResults");
	 
	if(searchCustomerData != undefined && searchCustomerData.length > 0)
	{
		    div.find('.dataTables_wrapper').remove();
			var table = $("<table></table>");
			var thead = $("<thead></thead>");
			var tr = $("<tr></tr>");
			tr.append($("<th></th>").append("Order ID"));
			tr.append($("<th></th>").append("Order Date"));
			tr.append($("<th></th>").append("Customer Name"));
			tr.append($("<th></th>").append("Customer Address"));
			tr.append($("<th></th>").append("Agent Id"));
			thead.append(tr);
			table.append(thead);
			
			var tbody = $("<tbody></tbody>");
			
			
			for(var i=0; i< searchCustomerData.length; i++){
				var order = searchCustomerData[i];
				var customerName = order.customerName;
				var customerAddress = order.address;
				var orderId = order.orderId;
				var agentId = order.agentId;
				var orderDate = order.orderDate;
				
				var tr = $("<tr></tr>");
				
				var a_order = $("<a />").attr({"href":"#","id":orderId}).text(orderId);
				a_order.click(getOrder);
				tr.append($("<td></td>").append(a_order));
				
				tr.append($("<td></td>").text(orderDate));
				tr.append($("<td></td>").text(customerName));
				tr.append($("<td></td>").text(customerAddress));
				tr.append($("<td></td>").text(agentId));
				
				tbody.append(tr);
			}
			table.append(tbody);
			div.append(table);
			div.show();
			$('div#orderResults table').dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers"
			});
	} 
	else
	{
		var orderList = data.orderList;
		if(orderList != undefined && orderList.length > 0){
			
			//add previous button
			var a = $("<a></a>").text("< Previous").addClass("prevousLink").attr({"id":"singlePrevious","title":"Previous"});
			a.click(showNextOrPreviuosPage);
			$("div.navigationBlock").append(a);
			
			//add drop down
			var div_links = $("<div></div>").attr("id", "linksBlock");
			var i = 1;
			var firstAnchorId = '';
			var a = '<select id="pageTitle" name="pageTitle" onchange="showPageContent();" class="pageLink" >';
			$(orderList).each(function(){
				page = this.page;
				var customerId = this.customerId;
				var orderId = this.orderId;
				var title = page + " - ";
				if(customerId != undefined && customerId != ""){
					title = title + "Customer Id: "+customerId;
				}
				if(orderId != undefined && orderId != ""){
					title = title + "/Order Id: "+orderId;
				}
				var id = "page_"+this.keyName+ "-" + orderId + "-" + this.id;
				
				if(i == 1)
				{
					firstAnchorId = id;
				}
					a = a+'<option id="'+id+'" value="'+i+'" title="'+title+'" pageLink="'+"pageLink_"+i+'" >'+page+'</option>' ;
				i++;
			});
			a = a+"</select>";
			div_links.append(a);
			$("div.navigationBlock").append(div_links);
			
			//add next button
			var a = $("<a></a>").text("Next >").addClass("prevousLink").attr({"id":"singleNext","title":"Previous"});
			a.click(showNextOrPreviuosPage);
			$("div.navigationBlock").append(a);
			
			//Selected Page Description 
			$("div.navigationBlock").append($("<span></span>").attr("id", "selectedPage").css("display", "none"));
			
			// loader image
			var loader_path = path +"/image/ajax-loader.gif";
			var loader_Img = $("<img></img>").attr({"id":"preLoader","src":loader_path,"alt":"Close","height":"25px","width":"25px"}).addClass("preloader");
			$("div.navigationBlock").append(loader_Img);
			$("input#totalPages").val(i-1);
			var src_path = path +"/images/icon_close.png";
			var close_Img = $("<img></img>").attr({"id":"closeOverlay","src":src_path,"alt":"Close"}).css("display", "none");
			close_Img.click(closeOverLay);
			
			$("div.navigationBlock").append(close_Img);
			var dataBlock = $("<div></div>").addClass("dataBlock");
			dataBlock.append($("<div></div>").addClass("noPageContent"));
			$("div#resultsBlock").append(dataBlock);
			
			$("div#resultsBlock").show();
			$("div#orderResults").hide();
			
			showHideNavigationLinks();
			$("div#resultsBlock").addClass("scoreOverlay");
			$('a#'+firstAnchorId).trigger('click');
			$('div a').click(function(){
			    $(this).next().slideDown();
			});
			

		} else 
		{
			div.find('.dataTables_wrapper').hide();
			var noResult = $("<div>OrderId is not available.</div>").addClass("scoreErrorMsg");
			noResult.append($("<br/>"));
			if(div.find('.dataTables_wrapper').length)
			{
				$('span#goToAdvance').show();
			}
			$("div#orderResults").append(noResult);
			updateTypeOfSearch(false);
		}
		showPageContent();
		
	}
}

function showPageContent()
{
	$('#preLoader').show();
	var selectedOption = $( "#pageTitle option:selected" )
	var selectedPage = selectedOption.text();
	var slectedPageNo = selectedOption.val();
	var page = parseInt(slectedPageNo, 10);
	var title = selectedOption.attr("title");
	var page_id = selectedOption.attr("id");
	var pageLink = selectedOption.attr("pageLink");
	if(page == 1)
	{
		disableScoreCSS();
	}
	$("input#currentPage").val(slectedPageNo);
	$("img#closeOverlay").show();
	$("span#selectedPage").show();
	$("span#selectedPage").text("");
	$("span#selectedPage").append($("<b></b>").text("Page: "));
	$("span#selectedPage").append(page+". "+title);
	
	var path = $("#path").val();
	var url = path+"/rest/getSalesOrderPage";
	var data = {};
	data["page_id"] = page_id.split("_")[1];
	try{    
		$.ajax({
				type: 'POST',
				async: false,
				url: url,
				data: data,
				success: function(data){
					if(data=="sessionTimeOut"){
						window.location = path+"/rest/login";
					}
				$("div#score_search_container").hide();
				var STATIC_REGEX = /\/salescenter/gi;
				while (STATIC_REGEX.test(data)) 
				{
					data = data.replace(STATIC_REGEX, path);
				}
				$("div.dataBlock").html(data);
				$("div.dataBlock").find("img").each(function()
				 {
				   var img_src = $(this).attr('src');
				   if(img_src != undefined && img_src.indexOf('../') != -1)
					{
						img_src = img_src.replace("../", "/");
						if(img_src.indexOf('../') != -1)
						{
							img_src = img_src.replace("../", "");
						}
						$(this).removeAttr("src");
						img_src = path + img_src ;
						$(this).attr("src",img_src);
					}
				});
				// To remove the overlay of concert #121312
				$("div.ui-widget-overlay").hide();
				 replaceTheFeatureType(title);
				$('#preLoader').hide();
				$('#effectInstall, #effectMonthly').css("position","absolute");
			},
			error: function(data)
			{
				if(data=="sessionTimeOut"){
					window.location = path+"/rest/login";
				}
				var div_o = $("div#orderResults");
				div_o.find("div.dataTables_wrapper").hide();
				handleErrorMsg(data);
			}
		});
	} catch(e){
		alert(e);
	}
	//$(this).addClass("selected");
}

function closeOverLay()
{
	enableScoreCSS();
	updateTypeOfSearch(false);
	$("div#resultsBlock").removeClass("scoreOverlay");
	$("div#resultsBlock").hide();	
	$("div#score_search_container").show();
}


function showNextOrPreviuosPage()
{
	var id = this.id;
	var totalPages = $("input#totalPages").val();
	
	if(id == "singlePrevious")
	{
		var currentPage = parseInt($("input#currentPage").val());
			var prev_page = currentPage - 1;
			if(currentPage == 1){
				alert("You can't navigate beyond this page");
			}
			else{
			$("#pageTitle option[pageLink='"+ ("pageLink_"+currentPage) +"']").attr("selected",false);
			$("#pageTitle option[pageLink='"+ ("pageLink_"+prev_page) +"']").attr("selected",true);
			showPageContent();
			}
	} else {
		var currentPage = parseInt($("input#currentPage").val());
		var next_page = currentPage + 1;
		if(currentPage == totalPages){
			alert("You can't navigate beyond this page");
		}else{
			$("#pageTitle option[pageLink='"+ ("pageLink_"+currentPage) +"']").attr("selected",false);
			$("#pageTitle option[pageLink='"+ ("pageLink_"+next_page) +"']").attr("selected",true);
			showPageContent();
		}
	
}
}
function showHideNavigationLinks()
{
	var totalPages = parseInt($("input#totalPages").val(), 10);
	
	if(totalPages >  10){
		$("a[class^='pageLink_']").hide();
		$("a[class^='pageLink_']").slice(0, 10).show();
	}
}


var handleErrorMsg = function(data)
{
	if(data == "sessionTimeOut")
	{
		var path = $("#path").val();
		window.location.href = path+"/rest/login";
	}
	
	$('img#loader').hide();
	$('#preLoader').hide();
	closeOverLay();
	
    var statusCode = data.status;
	if(statusCode == 500 || statusCode == 404 || statusCode == 400)
	{
		var selectedText = $("select#typeOfSearchCriteria option:selected").text();
		var errorHeader = $("<H1>Order is no longer available to view in SCORE.  Please open a ticket with Production Support to retrieve this customer"+"'"+"s session.</H1>");
		var errorDiv = $("<div></div>").addClass("scoreErrorMsg");
		errorDiv.append(errorHeader);
		
		if($("div#orderResults").find('.dataTables_wrapper').length){
			$('span#goToAdvance').show();
		}
		
		$("div#orderResults").append(errorDiv);
	}
};


//Updates the type of serach based on the event
function updateTypeOfSearch(tabChange)
{
	$('#orderResults').css("display", "none");
	var type = $('div.tabs ul.tabNavigation a.selected').text();
	if(type == "Customer")
	{
		$("input#searchType").val("getWebOrdersWithCustomer");
		$("input#typeOfSearch").val("CustomerSearch");
		$('#orderResults').css("display", "inline-block");
	} 
	else
	{
		var selectVal = $("select#typeOfSearchCriteria").val();
		$("input#searchType").val(selectVal);
		$("input#typeOfSearch").val("BasicSearch");
		$('#orderResults').css("display", "inline-block");
	}
	
	$("div.dataBlock").remove();
	$("div#resultsBlock").hide();
	$("input#currentPage").val("0");
	$("input#totalPages").val("0");
	
	if(tabChange)
	{
		$('span#goToAdvance').hide();
		$("select#typeOfSearchCriteria").val("getWebOrdersWithAgent");
		var selectVal = $("select#typeOfSearchCriteria").val();
		$("input#searchType").val(selectVal);
		updateInputs();
		clearInputText("div#customer");
		clearInputText("div#order");
	}
}
function replaceTheFeatureType(title)
{
	if(title != undefined && 
		(   (title.indexOf('Order Recap') != -1) ||
		(title.indexOf('Order Summary')!= -1) ))
		{
			 $('.os_itemdetailstbl,.or_basemonthlyprice_tbl').find('tr').each(
			function()
			{
				var html = $(this).html();
				var format = /\?/gi;
				while ( format.test(html)) 
					{
					 html = html.replace(format, '&#x2713;');
			}
				$(this).html(html);
			});
			}
}
function enableScoreCSS()
{
	$("head").find("link").each(function()
			{
				var css_src = $(this).attr('disabledhref');
				if(css_src != undefined )
				{
					$(this).removeAttr("disabledhref");
					$(this).attr("href",css_src);
				}
			});

}

function disableScoreCSS()
{
	$("head").find("link").each(function()
			{
				var css_src = $(this).attr('href');
				if(css_src != undefined && css_src.indexOf('/salesOrder.css') == -1)
				{
					$(this).removeAttr("href");
					$(this).attr("disabledhref",css_src);
				}
			});

}

var getOrder = function()
{
	var id = $(this).attr('id');
	
	$("select#typeOfSearch").val('getWebOrdersWithOrder');
	$("input#searchType").val('getWebOrdersWithOrder');
	$("input#orderId").val(id);
    searchOrders();
};

var clearInputText = function(id)
{
	var inputs = $(id).find('input:text');
	inputs.each(function()
	{
	$(this).val('');
	});
};

var previousResults = function(){
	var div = $("div#orderResults");
	$("div.scoreErrorMsg").remove();
	div.find('.dataTables_wrapper').show();	
	$('span#goToAdvance').hide();
};

function isEmpty(str)
{
	if(str == undefined || $.trim(str) == ""){
		return true;
	} else {
		return false;
	}
}

function isNotEmpty(str){
	if(str == undefined || $.trim(str) == ""){
		return false;
	} else {
		return true;
	}
}