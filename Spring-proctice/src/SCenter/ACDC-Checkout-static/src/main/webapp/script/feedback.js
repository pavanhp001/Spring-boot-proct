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

function setDynamicHeight() {
    var feedback = document.getElementById("iData").value;
    var height = $("body").height();
    var type = "dynamic_height";
    if (height == undefined) {
        height = '';
    }
    var feedback_main = {       
        type: type,
        ckoBodyHeight: height
    };
    var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
    windowProxy.post({"feedback": feedback_main});
}

function displayCKOLoader(isLandingPage) {
    var feedback = document.getElementById("iData").value;
    var type = "display_CKO_loader";
	   if(isLandingPage == undefined){
		   isLandingPage = false;
	   }
    var feedback_main = {       
        type: type,
        isLandingPage:isLandingPage
    };
    var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
    windowProxy.post({"feedback": feedback_main});
}

function setCKOActions(plansummaryscrollHeight) {
    var feedback = document.getElementById("iData").value;
    var type = "CKO_actions";
    console.log($(this))
    var offset;
    if(plansummaryscrollHeight != undefined){
    	 offset = plansummaryscrollHeight + 10
    }else if(plansummaryscrollHeight == undefined){
 	   if($(".panel-collapse.in").find(".panel-body").offset() != undefined){
	    	  offset = $(".panel-collapse.in").find(".panel-body").offset().top + 10
	    }else if(offset == undefined){
	    	offset = 0;
	    }
    }
 
    var feedback_main = {       
        type: type,
        offset: offset
    };
    var parentUrl = JSON.parse(feedback).CKO.parentUrl;
    var windowProxy = new Porthole.WindowProxy(parentUrl);
    windowProxy.post({"feedback": feedback_main});
}

/*function digitalFeaturesDisplayFeedBack(featuresJSONArray) 
{
	var feedback = document.getElementById("iData").value;
	var type = "show_features_data";
	
	var feedback_main = {
			type: type,
			"featuresJSON" : featuresJSONArray
	};
	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
	windowProxy.post({"feedback": feedback_main});
}*/


function digitalDialogueFeaturesDisplayFeedBack(featuresJSONObj) 
{
	var feedback = document.getElementById("iData").value;
	var type = "show_dialogue_features_data";
	
	var feedback_main = {
			type: type,
			"featuresJSON" : featuresJSONObj
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


function redirectToParentApp(redirectType) 
{
	var feedback = document.getElementById("iData").value;
	var feedback_main = {
			type: redirectType
	};
	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
	windowProxy.post({"feedback": feedback_main});
}

function hidePlanSummeryOnErrorPage(redirectType) 
{
	console.log("redirectType :"+redirectType);
	var feedback = document.getElementById("iData").value;
	var feedback_main = {
			type: redirectType
	};
	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
	windowProxy.post({"feedback": feedback_main});
}

function setCKOPageTitle(pageTitle){
	var feedback = document.getElementById("iData").value;
    var type = "CKO_title";
    var feedback_main = {
		feedback: feedback,
        type: type,
        "CKOPageTitle": pageTitle
    };
    var parentUrl = JSON.parse(feedback).CKO.parentUrl;
    var windowProxy = new Porthole.WindowProxy(parentUrl);
    windowProxy.post({"feedback": feedback_main});
}

function setDataLayer(dataLayer){
	var feedback = document.getElementById("iData").value;
	var feedback_main = {
			dataLayer:dataLayer,
			feedback: feedback,
			type: "data_layer"
	};
	if(dataLayer.CKO!=undefined){
		if(dataLayer.CKO.installation!=undefined && dataLayer.CKO.installation.length!=undefined){
		for(var i=0; i<dataLayer.CKO.installation.length ; i++){
		  if(dataLayer.CKO.installation[i].valueTarget!=undefined && (dataLayer.CKO.installation[i].valueTarget.indexOf('Credit')!=-1 || 
		  dataLayer.CKO.installation[i].valueTarget.indexOf('dob')!=-1 ||
		  dataLayer.CKO.installation[i].valueTarget.indexOf('birth')!=-1 ||
		  dataLayer.CKO.installation[i].valueTarget.indexOf('phone')!=-1 || 
		  dataLayer.CKO.installation[i].valueTarget.indexOf('Date')!=-1)) {
			delete dataLayer.CKO.installation[i];
		  }
		 }
	  }
		if(dataLayer.CKO.accountHolderQualifications!=undefined && dataLayer.CKO.accountHolderQualifications.length !=undefined){
		for(var j=0; j<dataLayer.CKO.accountHolderQualifications.length ; j++){
		  if(dataLayer.CKO.accountHolderQualifications[j].name!=undefined && (dataLayer.CKO.accountHolderQualifications[j].name.indexOf('firstName')!=-1 || 
		  dataLayer.CKO.accountHolderQualifications[j].name.indexOf('lastName')!=-1 ||
		  dataLayer.CKO.accountHolderQualifications[j].name.indexOf('emailAddress')!=-1 ||
		  dataLayer.CKO.accountHolderQualifications[j].name.indexOf('contact')!=-1 ||
		  dataLayer.CKO.accountHolderQualifications[j].name.indexOf('Contact')!=-1 ||
		  dataLayer.CKO.accountHolderQualifications[j].name.indexOf('phone')!=-1 || dataLayer.CKO.accountHolderQualifications[j].name.indexOf('Phone')!=-1 || dataLayer.CKO.accountHolderQualifications[j].name.indexOf('SSN')!=-1 || 
		  dataLayer.CKO.accountHolderQualifications[j].name.indexOf('socialSecurityNumber')!=-1 ||
		  dataLayer.CKO.accountHolderQualifications[j].name.indexOf('dob')!=-1  ||
		  dataLayer.CKO.accountHolderQualifications[j].name.indexOf('dateOfBirth')!=-1  ||
		  dataLayer.CKO.accountHolderQualifications[j].name.indexOf('DateOfBirth')!=-1))
		  {
			delete dataLayer.CKO.accountHolderQualifications[j];
		  }
		 }
	   }
	}
	
	var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	var windowProxy = new Porthole.WindowProxy(parentUrl);
	windowProxy.post({"feedback": feedback_main});
}

function showPopover() {
	 var feedback  = document.getElementById("iData").value;
	 
	 var feedback_main = {
	   feedback: feedback,
	   type: "click"
	 };

	 var parentUrl = JSON.parse(feedback).CKO.parentUrl;
	 var windowProxy = new Porthole.WindowProxy(parentUrl);
	 windowProxy.post({"feedback": feedback_main});

	}
function changeOrientation(){
	if(window.orientation != undefined && window.orientation == 0) // Portrait
	  {
			$("#iData").trigger("click");
	  }
	  else if(window.orientation != undefined) // Landscape
	  {
			$("#iData").trigger("click");
	  }
	setDynamicHeight();
}
function CKOToDigitalActions(actionValue){
	var feedback = document.getElementById("iData").value;
    var type = "CKOToDigitalActions";
    var feedback_main = {
		feedback: feedback,
        type: type,
        actionValue: actionValue
    };
    var parentUrl = JSON.parse(feedback).CKO.parentUrl;
    var windowProxy = new Porthole.WindowProxy(parentUrl);
    windowProxy.post({"feedback": feedback_main});
	
}