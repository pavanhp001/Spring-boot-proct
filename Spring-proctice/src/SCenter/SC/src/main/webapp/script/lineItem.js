$(document).ready(function(){
	var selectedArray = [];
	
	$(".addToCart").click(addProductsToCart);
	$("#remove").click(removeFromCart);
	
	var fieldset = $("form#lineItem_details > fieldset");
	fieldset.each(function() {
		var legend = $(this).find("legend");
		legend.css("cursor", "pointer");
		legend.click(function() {
			var ol = $(this).next();
			if (ol.is(":hidden")) {
				$(this).find("i").attr("class", "minimizeView");
			} else {
				$(this).find("i").attr("class", "maxmizeView");
			}
			ol.slideToggle("slow");
		});
	});
	
	
});

var updateTotalPrice = function(price, status){
	var units = $("#priceUnits").val();
	var totalPrice = parseFloat($("#totalPrice").val());
	if(status === 1){
		totalPrice = totalPrice + price;
	} else {
		totalPrice = totalPrice - price;
	}
	totalPrice = Math.round(totalPrice * 100) / 100;

	$("#totalPrice").val(totalPrice);
	$("span#itemsTotal").html(totalPrice + " " + units);
}

var onComplete = function(h4_id) {
	return function(data){
		
		h4_id = h4_id.replace(/\:/g,"\\:");
		$("h4#"+ h4_id + " div:eq(1)").css("display", "block");
		
		
		data = JSON.parse(data);
		
		var liId = data.liId;
		var status = data.status;
		
		
		$("h4#"+h4_id).find("div.liStatus").html(status);
		
		var hidden_row = $("<input/>").attr({"value":liId, "type":"hidden", "id":"li_externalId_"+liId});
		$("h4#"+h4_id).append(hidden_row);
		
		$("h4#"+ h4_id + " div:eq(0)").remove();
		
		
		$("span#orderInfoHead").unbind("click");
		$("span#orderInfoHead").click(goToOrderSummary);
	}
}

var onError = function(h4_id) {
	return function(data, textStatus, xhr, h4_id){
		alert("error\n" + data + ", " + textStatus + ", " + xhr);
	}
}

var addScrollDiv = function(h4){
	var scrollDiv = $("<div></div>").addClass("alignCenter");
	var img = $("<img/>").attr("src", "/images/scroll.gif");
	scrollDiv.append(img);
	h4.prepend(scrollDiv);
}

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

var addProductsToCart = function(){
	var id = this.id;

	var liCount = parseInt($("input#liCount").val()) + 1;

	var parent_div_id = id.replace("input_", "prod_");
	parent_div_id = parent_div_id.replace(/\:/g,"\\:");
	var prodJsonVal = $("div#"+parent_div_id).find("input[name='prodJson']").val();

	$("input#liCount").val(liCount);

	var prodJson = JSON.parse(prodJsonVal);

	var h4_id = id.replace("input_", "row_");
	//TODO: handle colon in id
	var ind = $("h4[id^='"+h4_id+"']").length + 1;
	h4_id = h4_id + "_"+ ind;
	
	var h4 = $("<h4>").addClass("systemColor2").attr("id", h4_id);
	addScrollDiv(h4);
	
	var contentBlock = $("<div>").css("display", "none");

	var input_id = id.replace("input_", "item_") + "_" + ind;
	var input = $("<input/>").attr({"class":"itemCheckBox", "type":"checkbox", "id":input_id});
	contentBlock.append(input);

	var span = $("<span id='prodId'></span>").text(prodJson.productName);
	contentBlock.append(span);

	var priceId = id.replace("input_", "price_");
	priceId = priceId.replace(/\:/g,"\\:");
	var price = parseFloat($("input#"+priceId).val());

	price = Math.round(price * 100) / 100;

	var units = $("#priceUnits").val();

	var span1 = $("<span>").addClass("fRight");;
	if(isNaN(price)){
		span1.append('<span id="price">--</span>');
	}
	else{
		var price_row = $('<span id="price"/>');
		price_row.text(price + " " + units);
		span1.append(price_row);
		
		updateTotalPrice(price, 1);
	}
	
	var statusSpan = $("<span>").attr("class", "statusToggle");
	statusSpan.click(toggleStatus);
	span1.append(statusSpan);
	contentBlock.append(span1);
	
	var div = $("<div>").addClass('liStatus').css("display","none");
	contentBlock.append(div);
	
	h4.append(contentBlock);

	$("div#itemsBlock").find("div.mCSB_container").append(h4);

	var divider = $("<div>").addClass("divider");
	$("div#itemsBlock").find("div.mCSB_container").append(divider);

	//Ajax to add lineitem, this req goes to CartLineItemController
	var url = "/rest/scart/addProduct";
	var data = {};
	data["productData"] = prodJsonVal;
	data["lineItemNumber"] = liCount;
	//alert('ajax started');
	try{    
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			success: onComplete(h4_id),
			error: onError(h4_id)
		});
	} catch(e){
		alert(e);
	}
}

var onRemoveComplete = function(data){
	data = JSON.parse(data.responseText);
	$(data).each(function(){
		var liId = this.liId;
		var stat = this.stat;
		var status = this.status;
		if(stat){
			var h4 = $("input#li_externalId_"+liId).parent();
			
			h4.find("span.fRight").find("span#price").text("Add");
			h4.find("span.fRight").find("span#price").addClass("addAgain");
			h4.find("span.fRight").find("span#price").click(reAdd);
			h4.find("div.liStatus").html(status);
		}
	});
}

var removeFromCart = function(){
	var len = $(".itemCheckBox:checked").length;

	var lineItemIdArr =[];
	if(len == 0){
		alert("Please select item(s) to be removed");
	}else{
		$(".itemCheckBox:checked").each(function() {
			var parent_row = $(this).closest("h4");

			var span_fright = parent_row.find('span.fRight');

			var price = span_fright.find("span#price").text();
			if(!isNaN(price)){
				updateTotalPrice(price, -1);
			}

			$(this).attr("checked", false);
			$(this).attr("disabled", true);

			parent_row.find('span#prodId').css("color","#F00");

			var liId  = parent_row.find('input[id^="li_externalId_"]').attr('id').slice(14);
			lineItemIdArr.push(liId);
		});
	}

	var url = "/rest/scart/removeProduct";
	var data = {};
	data["orderId"] = $("input#orderId").val();
	data["jsonArr"] = lineItemIdArr.toString();

	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onRemoveComplete
	});
}

var onReAddComplete = function(data){
	data = JSON.parse(data.responseText);
	if(data.stat){
		var liId = data.liId;
		var status = data.status;
		
		var h4 = $("input#li_externalId_"+liId).parent();
		h4.find("div:eq(0)").remove();
		
		h4.find("div").css("display", "block");
		h4.find("span.fRight").find("span#price").removeClass("addAgain");
		h4.find("span.fRight").find("span#price").unbind("click");
		
		h4.find("div.liStatus").html(status);
		
		var h4Id = h4.attr("id");
		h4Id = h4Id.slice(0, h4Id.lastIndexOf("_"));
		
		var priceId = h4Id.replace("row_", "price_");
		priceId = priceId.replace(/\:/g,"\\:");
		
		var price = parseFloat($("input#"+priceId).val());
		price = Math.round(price * 100) / 100;
		var units = $("#priceUnits").val();
		if(isNaN(price)){
			h4.find("span.fRight").find("span#price").text("--");
		} else {
			h4.find("span.fRight").find("span#price").text(price + " " + units);
			updateTotalPrice(price, 1);
		}
	}
}

var reAdd = function(){

	var h4 = $(this).closest("h4");
	h4.find(".itemCheckBox").attr("disabled", false);
	h4.find("span#prodId").css("color", "#000")
	h4.find("div").css("display", "none");
	
	addScrollDiv(h4);
	
	var liId = h4.find("input[id^='li_externalId_']").val();

	var url = "/rest/scart/addStatusChange";
	var data = {};
	data["orderId"] = $("input#orderId").val();
	data["lineItemId"] = liId;

	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onReAddComplete
	});
	
}

function updateCounts(){
	var totalCount = $("input#totalCount").val();
	var selectedCount = $("input#selectedCount").val();
	var selectedPage = $("input#selectedPage").val();

	var start = selectedCount * (selectedPage - 1) + 1;
	var end = selectedCount * selectedPage;
	if(totalCount < end){
		end = totalCount;
	}

	$("span#startCount").text(start);
	$("span#endCount").text(end);
}

function constructPagenation(){
	$("span[id^='page']").remove();
	
	var totalCount = $("#totalCount").val();
	var selectedCount = $("#selectedCount").val();
	var totalPages = parseInt(totalCount/selectedCount);
	var oneMorePage = (totalCount % selectedCount) !== 0;
	if(oneMorePage){
		totalPages = totalPages + 1;
	}
	$("input#totalPages").val(totalPages);

	for(var i=1; i<=totalPages; i++){
		var span = $("<span><b>"+i+"</b></span>").attr("id", "page"+i);
		if(i > 2){
			span.css("display", "none");
		}
		if(i > 1){
			span.addClass("enabledTxt");
			span.mouseover(pageMover);
			span.mouseout(pageMout);
			span.click(showPage);
		}
		$("span#rightNavigator").before(span);
	}
	if(totalPages > 2){
		$("span#rightNavigator").addClass("enabledTxt");
		$("span#rightNavigator").mouseover(pageMover);
		$("span#rightNavigator").mouseout(pageMout);
		$("span#rightNavigator").click(moveNext);

		$("span#rightNavigator > i").removeClass("disRightNavigator");
		$("span#rightNavigator > i").addClass("enableRightNavigator");
	}
}

function showPage(){
	var id = this.id;
	var selectedPage = parseInt(id.slice(4));
	$("input#selectedPage").val(selectedPage);
	var selectedCount = parseInt($("input#selectedCount").val());
	var totalCount = parseInt($("input#totalCount").val());
	
	var start = selectedCount * (selectedPage - 1) + 1;
	var end = start + selectedCount-1;
	if(end > totalCount) {
		end = totalCount;
	}
	
	$("span#startCount").text(start);
	$("span#endCount").text(end);
	
	var firstVisibleSpan = $("span[id^='page']:visible").first();
	if(firstVisibleSpan.attr("id") == id){
		movePrevious();
	} else {
		moveNext();
	}
	showHideJobs();
	
	var pages = $("span[id^='page']");
	pages.each(function(){
		$(this).unbind("mouseover");
		$(this).unbind("mouseout");
		$(this).unbind("click");
		
		if(this.id == id){
			$(this).removeClass("enabledTxt");
			$(this).css("text-decoration", "none");
		} else {
			$(this).attr("class", "enabledTxt");
			$(this).mouseover(pageMover);
			$(this).mouseout(pageMout);
			$(this).click(showPage);
		}
	});
}

function moveNext() {
	var visibleSpan = $("span[id^='page']:visible").last();
	var totalPages = $("input#totalPages").val(); 
	if(totalPages > 2){
		if($("span#leftNavigator").attr("class").indexOf("enabledTxt")){
			enableDisLeftNavigator(true);
		}
		
		var prevSpan = visibleSpan.prev("span[id^='page']");
		var nextSpan = visibleSpan.next("span[id^='page']");

		if(nextSpan.length != 0){

			if(parseInt(nextSpan.attr("id").slice(4)) == totalPages){
				enableDisRightNavigator(false);
			}

			if(prevSpan != 0){
				prevSpan.css("display", "none");
			}
			nextSpan.css("display", "inline");
		}
	}
}

function movePrevious() {
	var visibleSpan = $("span[id^='page']:visible").first();
	var totalPages = $("input#totalPages").val(); 
	if(totalPages > 2){
		if($("span#rightNavigator").attr("class").indexOf("enabledTxt")){
			enableDisRightNavigator(true);
		}
		
		var prevSpan = visibleSpan.prev("span[id^='page']");
		var nextSpan = visibleSpan.next("span[id^='page']");

		if(prevSpan.length != 0){

			if(parseInt(prevSpan.attr("id").slice(4)) == 1){
				enableDisLeftNavigator(false);
			}

			if(prevSpan.length != 0){
				nextSpan.css("display", "none");
			}
			prevSpan.css("display", "inline");
		}
	}
}

function enableDisLeftNavigator(status) {
	if(status){
		$("span#leftNavigator").removeClass("disabledTxt");
		$("span#leftNavigator").addClass("enabledTxt");

		$("span#leftNavigator > i").addClass("enableLeftNavigator");
		
		$("span#leftNavigator").click(movePrevious);
		$("span#leftNavigator").mouseover(pageMover);
		$("span#leftNavigator").mouseout(pageMout);
	} else {
		$("span#leftNavigator").removeClass("enabledTxt");
		$("span#leftNavigator").addClass("disabledTxt");
		$("span#leftNavigator").css("text-decoration", "none");

		$("span#leftNavigator > i").removeClass("enableLeftNavigator");
		
		$("span#leftNavigator").unbind("click");
		$("span#leftNavigator").unbind("mouseover");
		$("span#leftNavigator").unbind("mouseout");
	}	
}

function enableDisRightNavigator(status) {
	if(status){
		$("span#rightNavigator").removeClass("disabledTxt");
		$("span#rightNavigator").addClass("enabledTxt");

		$("span#rightNavigator > i").removeClass("disRightNavigator");
		$("span#rightNavigator > i").addClass("enableRightNavigator");
		
		$("span#rightNavigator").click(moveNext);
		$("span#rightNavigator").mouseover(pageMover);
		$("span#rightNavigator").mouseout(pageMout);
	} else {
		$("span#rightNavigator").removeClass("enabledTxt");
		$("span#rightNavigator").addClass("disabledTxt");
		$("span#rightNavigator").css("text-decoration", "none");

		$("span#rightNavigator > i").removeClass("enableRightNavigator");
		$("span#rightNavigator > i").addClass("disRightNavigator");
		
		$("span#rightNavigator").unbind("click");
		$("span#rightNavigator").unbind("mouseover");
		$("span#rightNavigator").unbind("mouseout");
	}
}

function pageMover(){
	$(this).css("text-decoration", "underline");
}

function pageMout(){
	$(this).css("text-decoration", "none");
}

function showHideJobs(){
	
	var selectedPage = parseInt($("input#selectedPage").val());
	var init_count = (selectedPage -1) * 3;
	var final_count = selectedPage * 3;
	
	$("div#prodListBlock div.prodBlock").css("display", "none");
	
	var job_divs_selected = $("div#prodListBlock div.prodBlock").slice(init_count, final_count);
	job_divs_selected.css("display", "table");
}

