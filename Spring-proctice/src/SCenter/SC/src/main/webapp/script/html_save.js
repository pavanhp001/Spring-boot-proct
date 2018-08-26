function savePageHtml(async, iframe_html){
	try{
		var path = $("#contextPath").val();
		
		var html_clone = $("html").clone();
		
		var CKOPageTitle;
		var page_type;
		try{
			if(iframe_html != undefined && iframe_html.trim() != ""){
				
				var iframe_html_Data = $('<div/>').html(iframe_html);
				if(iframe_html_Data.find("div").hasClass("pc_steps")){	
					CKOPageTitle = iframe_html_Data.find("div").find(".pc_steps_item_progress .pc_steps_item_sttext").text();
					$("#CKOPageTitle").val(CKOPageTitle);
				}
			}
		}catch (e) {
		}
		
		updateInputs(html_clone);
		updateSelects(html_clone);
		updateTextArea(html_clone);
		
		//rename form action attr
		html_clone.find("form").each(function() {
		    var $t = $(this);
		    $t.attr({"reaction" : $t.attr('action'),}).removeAttr('action');
		});
		
		//rename anchor hrefs
		html_clone.find("a").each(function() {
		    var $t = $(this);
		    $t.attr({"ahref" : $t.attr('href'),}).removeAttr('href');
		});
		
		//update input type submit/button
		html_clone.find("input[type=submit]").removeAttr("onclick");
		html_clone.find("input[type=submit]").prop("type", "button");
		html_clone.find("input[type=button]").removeAttr("onclick");
		html_clone.find("input[type=button]").attr("disabled", "disabled");
		
		html_clone.find(".blockUI.blockOverlay").removeAttr("style");
		html_clone.find(".blockUI.blockMsg.blockPage").removeAttr("style");
		html_clone.find(".blockUI.blockMsg.blockPage img").removeAttr("src");
		
		var html = html_clone.html();
		var SCRIPT_REGEX = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi;
		while (SCRIPT_REGEX.test(html)) {
		    html = html.replace(SCRIPT_REGEX, "");
		}
		
		if(iframe_html != undefined && iframe_html.trim() != ""){
			iframe_html = updateCKOHtml(iframe_html);
			
			html = html.replace("Content Goes Here", "");
			html = html.replace("<iframe id", "<div id");
			html = html.replace("</iframe>", iframe_html+"</div>");
		} else {
			html = html.replace("Content Goes Here", "");
			html = html.replace("<iframe id", "<div id");
			html = html.replace("</iframe>", "</div>");			
		}
		
		//Ajax to save html content
		var url = path+"/salescenter/saveOrderReviewData";
		var data = {};
		var customer_id = $("#customerIdVal").val();
		if(customer_id == undefined || customer_id.trim() == ""){
			customer_id = "0";
		}
		data["customer_id"] = customer_id;
		
		var order_id = $("#orderId").val();
		if(order_id == undefined || order_id.trim() == ""){
			order_id = "0";
		}
		data["order_id"] = order_id;
		
		var ucid = $("#ucid").val();
		if(ucid == undefined || ucid == "null"){
			ucid = "0";
		}
		data["ucid"] = ucid;
		
		var pageTitle = $("#pageTitle").val();
		if(pageTitle == undefined || pageTitle.trim() == ""){
			pageTitle = $("#pageTitle").text();
		}
		
		var catagoryName = $(".pageTitle:visible").text();
		var catagory = catagoryName.trim();
		if(catagory != undefined && catagory.trim() != ""){
			var catagories = ["Recommendations", "Triple Play", "Double Play", "Video / Internet", "Phone / Video",  "Phone / Internet","Video", "Internet", "Phone", "Home Security", "Asis", "Electricity", "Natural Gas", "Water", "Waste Removal", "Utility"]; 
			if((catagories.indexOf(catagory) > -1) ){
				if(catagory == "Recommendations"){
					page_type = "PP";
				}else{
					page_type = "RC";
				}
			}
			pageTitle = catagory;
			if(((catagory.indexOf("CKO") > -1) || (catagory.indexOf("Authentication") > -1)) && (CKOPageTitle == undefined || CKOPageTitle.trim() == "")){
				$("#CKOPageTitle").val(pageTitle);
				page_type = "CKO";
			}
		}
		if(CKOPageTitle != undefined && CKOPageTitle.trim() != ""){
			catagory = catagory.replace("CKO","");
			pageTitle = CKOPageTitle+" ("+catagory.trim()+")";
			page_type = "CKO";
		}
		var productTitle = $("#product_name").text();
		if(productTitle != undefined && productTitle.trim() != ""){
			pageTitle = productTitle;
			page_type = "View Details";
		}
		
		// Here we will get lineItemExternalID from recommendations.jsp
		var lineItemExID = $( "body" ).data( "li_externalId");
		
		//Reset value
		$( "body" ).data( "li_externalId" , undefined);
		
		// Here we will get lineItemExternalID from CKO_container.jsp
		var CKOlineItemExID = $("#lineItemId").val();
		
		var lineItemID = "0";
		
		if(lineItemExID != undefined && lineItemExID != "null" && lineItemExID != ""){
			lineItemID = lineItemExID;
		
		}else if(CKOlineItemExID != undefined && CKOlineItemExID != "null" && CKOlineItemExID != ""){
			lineItemID = CKOlineItemExID;
		}
		
		var guid = $("span.guid").text();
		var providerId = $("#provider_Id").val();
		var prodExtId = $("#prodExtId").val();
		if(guid == undefined || guid == "null"){
			guid = "0";
		}else{
			guid = guid.trim();
		}
		if(page_type == undefined || page_type == ""){
			page_type = pageTitle;
		}
		
		var excludeLineItemPage = ["Dispositions", "Close Call", "View Details", "Order Summary", "Product Details"];
		
		if(page_type != undefined && (excludeLineItemPage.indexOf(page_type) > -1)){
			lineItemID = "0";
		}
	
		data["page_id"] = pageTitle;
		data["lineitem_id"] = lineItemID;
		data["page_type"] = page_type;
		data["page_title"] = pageTitle;
		data["guid"] = guid;
		data["providerId"] = providerId;
		data["prodExtId"] = prodExtId;
	
		var agentId = $("input#agentExtId").val();
		if(agentId == undefined || agentId.trim() == ""){
			agentId = "";
		}
		data["agent_id"] = agentId;
		data["html_content"] = html;
		try{    
			$.ajax({
				type: 'POST',
				async: async,
				url: url,
				data: data
			});
		} catch(e){
		}
	}catch(err){
		
	}
}

function updateInputs(html){
	var inputs = html.find("input[type='text']");
	inputs.each(function(){
		var id = escapeSpecialCharacters(this.id);
		if(id != undefined && id.trim() != ""){
			var val = $(this).val();
			if(val != undefined && val.trim() != ""){
				html.find("input#"+id).attr("value", val);
			}
		}
	});
	
	var input_radios = html.find("input[type='radio']");
	input_radios.each(function(){
		var id = escapeSpecialCharacters(this.id);
		if(id != undefined && id.trim() != ""){
			if(html.find("input#"+id).is(':checked')){
				this.setAttribute('checked', 'checked');
			} else {
				this.removeAttribute('checked');
			}
		}
	});
	
	var input_checks = html.find("input[type='checkbox']");
	input_checks.each(function(){
		var id = escapeSpecialCharacters(this.id);
		if(id != undefined && id.trim() != ""){
			if(html.find("input#"+id).is(':checked')){
				this.setAttribute('checked', 'checked');
			} else {
				this.removeAttribute('checked');
			}
		}
	});
}

function updateTextArea(html){
	var textareas = html.find("textarea");
	textareas.each(function(){
		var id = escapeSpecialCharacters(this.id);
		if(id != undefined && id.trim() != ""){
			var val = $("textarea"+id).val();
			if(val != undefined && val.trim() != ""){
				html.find("textarea"+id).text(val);
			}
		}
	});
}

function updateSelects(html){
	var selects = html.find("select");
	selects.each(function(){
		var id = escapeSpecialCharacters(this.id);
		if(id != undefined && id.trim() != ""){
			var val = $("select#"+id).val();
			if(val != undefined && val.trim() != ""){
				html.find("select#"+id+" > option").removeAttr("selected");
				html.find("select#"+id+" > option[value='"+val+"']").attr("selected", "selected");
			}
		}
	});
}

function updateCKOHtml(iframe_html){
	var path = $("#contextPath").val();

	var SCRIPT_REGEX = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi;
	while (SCRIPT_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(SCRIPT_REGEX, "");
	}

	var STATIC_REGEX = /\/CKO-static/gi;
	while (STATIC_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(STATIC_REGEX, path);
	}

	STATIC_REGEX = /\/static/gi;
	while (STATIC_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(STATIC_REGEX, path);
	}
	var ATTV_REGEX = /\/CKO-attv6/gi;
	while (ATTV_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(ATTV_REGEX, path);
		iframe_html = iframe_html.replace("CKO_att.css", "CKO_att_att.css");
		iframe_html = iframe_html.replace("CKO_att_iframe.css", "CKO_att_att_iframe.css");
	}

	ATTV_REGEX = /\/attv6/gi;
	while (ATTV_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(ATTV_REGEX, path);
		iframe_html = iframe_html.replace("CKO_att.css", "CKO_att_att.css");
		iframe_html = iframe_html.replace("CKO_att_iframe.css", "CKO_att_att_iframe.css");
	}

	var ATT_REGEX = /\/CKO-att/gi;
	while (ATT_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(ATT_REGEX, path);
		iframe_html = iframe_html.replace("CKO_att.css", "CKO_att_att.css");
		iframe_html = iframe_html.replace("CKO_att_iframe.css", "CKO_att_att_iframe.css");
	}

	ATT_REGEX = /\/att/gi;
	while (ATT_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(ATT_REGEX, path);
		iframe_html = iframe_html.replace("CKO_att.css", "CKO_att_att.css");
		iframe_html = iframe_html.replace("CKO_att_iframe.css", "CKO_att_att_iframe.css");
	}

	var DISH_REGEX = /\/CKO-dish/gi;
	while (DISH_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(DISH_REGEX, path);
	}

	DISH_REGEX = /\/dish/gi;
	while (DISH_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(DISH_REGEX, path);
	}

	var VZ_REGEX = /\/CKO-vz/gi;
	while (VZ_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(VZ_REGEX, path);
	}

	VZ_REGEX = /\/vz/gi;
	while (VZ_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(VZ_REGEX, path);
	}

	var COMCAST_REGEX = /\/CKO-comcast/gi;
	while (COMCAST_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(COMCAST_REGEX, path);
	}

	COMCAST_REGEX = /\/comcast/gi;
	while (COMCAST_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(COMCAST_REGEX, path);
	}

	var DS_REGEX = /\/CKO-directstar/gi;
	while (DS_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(DS_REGEX, path);
	}

	DS_REGEX = /\/directstar/gi;
	while (DS_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(DS_REGEX, path);
	}

	var CL_REGEX = /\/CKO-centurylink/gi;
	while (CL_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(CL_REGEX, path);
	}

	CL_REGEX = /\/centurylink/gi;
	while (CL_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(CL_REGEX, path);
	}

	var MON_REGEX = /\/CKO-monitronics/gi;
	while (MON_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(MON_REGEX, path);
	}

	MON_REGEX = /\/monitronics/gi;
	while (MON_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(MON_REGEX, path);
	}
	var GNG_REGEX = /\/CKO-gng/gi;
	while (GNG_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(GNG_REGEX, path);
	}

	GNG_REGEX = /\/gng/gi;
	while (GNG_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(GNG_REGEX, path);
	}
	var FRONTIER_REGEX = /\/CKO-frontier/gi;
	while (FRONTIER_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(FRONTIER_REGEX, path);
	}

	FRONTIER_REGEX = /\/frontier/gi;
	while (FRONTIER_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(FRONTIER_REGEX, path);
	}
	
	var COX_REGEX = /\/CKO-cox/gi;
	while (COX_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(COX_REGEX, path);
	}

	COX_REGEX = /\/cox/gi;
	while (COX_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(COX_REGEX, path);
	}
	
	var ADT_REGEX = /\/CKO-adt/gi;
	while (ADT_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(ADT_REGEX, path);
	}

	ADT_REGEX = /\/adt/gi;
	while (ADT_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(ADT_REGEX, path);
	}
	var TXU_REGEX = /\/CKO-txu/gi;
	while (TXU_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(TXU_REGEX, path);
	}

	TXU_REGEX = /\/txu/gi;
	while (TXU_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(TXU_REGEX, path);
	}
	
	var ALL_REGEX = /\/(CKO-.*?)\//gi;
	while (ALL_REGEX.test(iframe_html)) {
		iframe_html = iframe_html.replace(ALL_REGEX, path+"/");
	}

	return iframe_html;
}


/*
 * Escapes the special characters for jquery selectors
 */
function escapeSpecialCharacters(id){
	return id.replace(/([ #;&,.+*~\':"!^$[\]()=>|\/])/g,'\\$1');
}