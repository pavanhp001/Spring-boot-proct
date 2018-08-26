function symFeedback() {
	var feedback  = document.getElementById("iData").value;
	var html = "";
	if(feedback.indexOf("CKOComplete") == -1){
		html = getCKOHtml(true);
	}
	
	var feedback_main = {
			feedback: feedback,
			type: "onLoad",
			html: html
	};

	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
	windowProxy.post({"feedback": feedback_main});

}


function symCancelCKO(dialogueJSON, isUtilityOffer) {
	
	var feedback  = document.getElementById("iData").value;
	var html = "";
	if(feedback.indexOf("CKOComplete") == -1){
		html = getCKOHtml(false);
	}
	
	var feedback_main = {
			feedback: feedback,
			type: "cancel_CKO",
			html: html,
			utilityOffer: isUtilityOffer,
			activeDialogue : dialogueJSON
	};

	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
	windowProxy.post({"feedback": feedback_main});
	
}

function symShowAlert(alertMsg,isPromotion) {
	
	var feedback = document.getElementById("iData").value;
	var type;
	if(isPromotion!=undefined && isPromotion)
	{
		type = "show_promotion_alert";
	}
	else
	{
		type = "show_alert";
	}
	var feedback_main = {
			type: type,
			alertMsg : alertMsg
	};

	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
	windowProxy.post({"feedback": feedback_main});
	
}

function symSendWarmTransferEvent() {

	var html = "";
	
	var feedback  = document.getElementById("iData").value;
	
	var feedback_main = {
			feedback: feedback,
			type: "disable_warm_transfer",
			html: html
	};

	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
	windowProxy.post({"feedback": feedback_main});

}


function symFeedbackSubmit() {

	var html = "";
	
	var feedback  = document.getElementById("iData").value;
	if(feedback.indexOf("CKOComplete") == -1){
		html = getCKOHtml(false);
	}
	
	
	var feedback_main = {
			feedback: feedback,
			type: "onSubmit",
			html: html
	};

	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
	windowProxy.post({"feedback": feedback_main});

}

function gotoShoppingCart() {
	var html = "";
	var feedback  = document.getElementById("iData").value;
	if(feedback.indexOf("CKOComplete") == -1){
		html = getCKOHtml(true);
	}
	var feedback_main = {
			feedback: feedback,
			type: "onLoad",
			html: html
	};

	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	 var windowProxy = new Porthole.WindowProxy(parentUrl);
	 windowProxy.post({"feedback":feedback_main});
}

function hideCancelButton() {
	try{
		console.log("called hideCancelButton in feedback.js ");
		var feedback  = document.getElementById("iData").value;
		var feedback_main = {
				feedback: feedback,
				type: "cancelButton",
				html: ""
		};
		var parentUrl = JSON.parse(feedback).CKO.parentUrl;
		var windowProxy = new Porthole.WindowProxy(parentUrl);
		windowProxy.post({"feedback":feedback_main});
	}catch(e){
		console.log(e);
	}
}

function getCKOHtml(async){
	var html = "";
	try{
		var html_main = document.getElementsByTagName("html")[0];
		var html_clone = html_main.cloneNode(true);
		
		if(!async){
			updateInputs($(html_clone));
			updateSelects($(html_clone));
		}else{
			updateInputs($(html_clone));
		}
		
		//rename form action attr
		$(html_clone).find("form").each(function() {
		    var $t = $(this);
		    $t.attr({"reaction" : $t.attr('action'),}).removeAttr('action');
		});
		
		//rename anchor hrefs
		$(html_clone).find("a").each(function() {
		    var $t = $(this);
		    $t.attr({"ahref" : $t.attr('href'),}).removeAttr('href');
		});
		
		//update input type submit/button
		$(html_clone).find("input[type=submit]").removeAttr("onclick");
		$(html_clone).find("input[type=button]").removeAttr("onclick");
		
		html = $(html_clone).html();
		var SCRIPT_REGEX = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi;
		while (SCRIPT_REGEX.test(html)) {
		    html = html.replace(SCRIPT_REGEX, "");
		}
		
		html = html.replace(/type="submit"/g, 'type="button" disabled="disabled"');
	} catch(err){
		
	}
	
	return html;
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

function updateSelects(html){
	var selects = html.find("select");
	selects.each(function(){
		var id = escapeSpecialCharacters(this.id);
		if(id != undefined && id.trim() != ""){
			var val = $("select#"+id).val();
			if(val != undefined && val.trim() != "")
			{
				$(this).find('option').each(function()
				{
					if($(this).val() == val)
					{
						this.setAttribute('selected', 'selected');
					}
					else
					{
						this.removeAttribute('selected');
					}
				});
			}
		}
	});
}

/*
 * Escapes the special characters for jquery selectors
 */
function escapeSpecialCharacters(id){
	return id.replace(/([ #;&,.+*~\':"!^$[\]()=>|\/])/g,'\\$1');
}