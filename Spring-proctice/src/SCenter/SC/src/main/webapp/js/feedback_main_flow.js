function feedbackFunc(messageEvent) 
{
	try
	{
		var parCommand = messageEvent.data["parCommand"];
		if( parCommand != undefined )
		{
			pauseAndResumeCall(parCommand);
		}

		var transferCustomer = messageEvent.data["transferCustomer"];
		var mamStatus = messageEvent.data["mamStatus"];
		//if( transferCustomer == undefined )
		if( transferCustomer != undefined )
		{
			$.blockUI({ message: $('#domMessage') });
			var path = $("input#contextPath").val();
			var url = path + "/rest/authenticateExistingCustomer";
			//transferCustomer = {"status":"resolved","providerExternalId":"32416075","custtype":"transfer","CustomerInfo":{"WTN":"404-260-5442","UploadSpeed":"768K","DownloadSpeed":"10M"}};
			var data = {};
			data["authenticateJSON"] = transferCustomer;
			$.ajax({
				type: 'POST',
				url: url,
				data: data,
				success: function(data)
				{
					$("#CKOForm input[id='_eventId']").val("recommendationsEvent");
					$("#CKOForm").submit();
				}
			});
		}
		else if( mamStatus != undefined )
		{
			$.blockUI({ message: $('#domMessage') });
			var path = $("input#contextPath").val();
			var url = path + "/rest/getProductsFromSE2Response";
			//transferCustomer = {"status":"resolved","providerExternalId":"32416075","custtype":"transfer","CustomerInfo":{"WTN":"404-260-5442","UploadSpeed":"768K","DownloadSpeed":"10M"}};
			var data = {};
			data["mamStatus"] = mamStatus;
			$.ajax({
				type: 'POST',
				url: url,
				data: data,
				success: function(data)
				{
					$("#CKOForm input[id='_eventId']").val("recommendationsEvent");
					$("#CKOForm input[id='isMAMSuccess']").val("true");
					$("#CKOForm").submit();
				}
			});
		}
		else
		{
			var feedback_main = messageEvent.data["feedback"];
			if( feedback_main != undefined )
			{
				var feedback = feedback_main.feedback;
				var type = feedback_main.type;

				if (feedback_main.type == 'channelLineUp_data') {
			           console.log("channelLineUp data:: "+JSON.stringify(feedback_main.featuresJSON))
			           for (var i = 0; i < feedback_main.featuresJSON.length; i++) {
			            providerExternalId = feedback_main.featuresJSON[i].providerExternalId;
			            providerName =  feedback_main.featuresJSON[i].providerName;
			            parentExternalId = feedback_main.featuresJSON[i].parentExternalId;
			            productExternalId =  feedback_main.featuresJSON[i].productExternalId;
			              // console.log("origin :"+messageEvent.origin);
			              var path = $("input#contextPath").val();;
			              var urlPath;
			              if(parentExternalId != undefined && parentExternalId.length > 0 )
			              {
			            	  providerExternalId = parentExternalId;
			               //urlPath = path+"/salescenter/channelLineUpData?providerExternalId="+parentExternalId+"&providerName="+encodeURIComponent(providerName)+"&productExternalId="+productExternalId;
			              }
			              if(productExternalId != undefined && productExternalId.length > 0 && productExternalId.indexOf('DTV') > 0){
			            	  providerExternalId = '2314635';
			            	  providerName =  'DIRECTV';
			              }
			              urlPath = path+"/salescenter/channelLineUpData?providerExternalId="+providerExternalId+"&providerName="+encodeURIComponent(providerName)+"&productExternalId="+productExternalId;
			              window.open(urlPath,"", "scrollbars=yes, width=1000, height=550");
			              break;
			           }
			          }

				if(type == "show_promotion_alert")
				{
					$.promotionAlert(feedback_main.alertMsg, "Alert", 200, "auto");
				}
				if(type == "show_alert")
				{
					$.alert(feedback_main.alertMsg, "Alert", 200, "auto");
				}
				if(type == "updateShoppingCart")
				{
					updateShoppingCartCKOLineItem(feedback_main);
				}
				else{
					var html = feedback_main.html;
					if(type == "onLoad"){
						if(html != undefined && html.trim() != ""){
							savePageHtml(false, html);
						}
						feedbackLoad(feedback);
					} else if(type == "onSubmit"){
						if(html != undefined && html.trim() != ""){
							savePageHtml(false, html);
						}
						feedbackSubmit(feedback);
					}
					else if(type == "disable_warm_transfer")
					{
						saveWarmTransferEvent(feedback,curCKOProviderID);
					}else if(type == "cancelButton")
					{
						$('input[name="cancelCKO"]').hide();
					}
					else if(type == "cancel_CKO"){
						
						$('iframe#CKOContent').attr( 'src', '' );
						
						$('iframe#CKOContent').load(function() {
							this.className = "CKOContentLoading";
						});
						
						var utilityOffer = feedback_main.utilityOffer;
						
						var activeDialogues = feedback_main.activeDialogue;
						
						activeDialogues = JSON.parse(activeDialogues);
						if(activeDialogues != undefined)
						{
							var feedbackJSON = JSON.parse(feedback); 
							var CKO_json = feedbackJSON.CKO;
							activeDialogues["orderId"] = CKO_json.orderId;
							
							var lineItemJSON = CKO_json.lineItems;
							activeDialogues["lineItems"] = lineItemJSON.long;
							
							activeDialogues["utilityOffer"] = utilityOffer;
							var productExtIds = $("#productExtIds").val();
							var path = $("input#contextPath").val();
							var url = path + "/rest/update_dialogues?data="+JSON.stringify(activeDialogues)+"&productExtIds="+productExtIds;
							$.ajax({
								type: 'POST',
								url: url,
								dataType: 'json',
								async: false,
								success: function(data){
								}
							});
							if(html != undefined && html.trim() != ""){
								savePageHtml(false, html);
							}
							
							CKO_json.status = "CKOError";
							
							feedbackJSON.CKO = CKO_json;
							
							feedbackLoad(JSON.stringify(feedbackJSON));
						}
					}
				}
			}
		}
	}
	catch(err)
	{
		alert(err);
	}
}

function pauseAndResumeCall(parCommand)
{
	try{
		var isPAREnabled = $('.contentwrapperos').find('input[id="isPAREnabled"]').val();
		//Checking SYS_CONFIG par_enable flag value
		if(isPAREnabled=="true")
		{
			var action = "Resume";
			var phoneId = $('.contentwrapperos').find('input[id="phoneId"]').val();
			var pauseAndResumeURL = $('.contentwrapperos').find('input[id="pauseAndResumeURL"]').val();
			if(parCommand=="pauseCommand")
			{
				action = "Pause";
			}
			else{
				action = "Resume";
			}

			pauseAndResumeURL = pauseAndResumeURL+"?phoneId="+phoneId+"&action="+action;

			//calling the Restful service PauseAndResumeServerCommand 
			$.ajax({
				url: pauseAndResumeURL,
				async : false,
				complete: onCompletePAR,
				error : onErrorPAR
			});
		}
	}catch(err){alert(err);}
}

var onCompletePAR = function(data)
{
	//alert("PauseServiceCommand="+data);
}

var onErrorPAR = function(result){
	//alert("Exception Occured While calling PauseORResumeService Command="+result);
}

function saveWarmTransferEvent(feedback,curCKOProviderID){
	var data = "wtDisabledProviderID="+curCKOProviderID;
	var path = $("input#contextPath").val();
	var url = path + "/rest/warmtransfer_event";
	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onSubmitReturn
	});
}

var onLoadReturn = function(data){
	//alert("Return onsubmit ajax");
}

function feedbackLoad(feedback) {
	var data = {};
	//takes to CartFeedbackOnStart, Metrics update happens there
	data["data"] = feedback;
	data["CKO_pageTitle"] = $("#CKOPageTitle").val();
	var path = $("input#contextPath").val();
	var url = path + "/rest/on_start";
	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onLoadReturn
	});

	
	var feedbackJson = JSON.parse(feedback);
	
	var orderId = feedbackJson.CKO.orderId;
	var customerId = feedbackJson.CKO.customerId;
	var status = feedbackJson.CKO.status;
	var orderId = feedbackJson.CKO.orderId;
	var utilityOffer = document.getElementById('utilityOffer').value;
	var isRecommendation = document.getElementById('isRecommendation').value;
	
	var complete = (feedback.indexOf("CKOComplete") != -1);
	var error = (feedback.indexOf("CKOError") != -1 );
	var success = (feedback.indexOf("CKOSubmit") != -1 );
	var hold = (feedback.indexOf("CKOHold") != -1 );
	var lineItemExtID = 0;
	
	if(feedbackJson.CKO.lineItems){
		lineItemExtID = feedbackJson.CKO.lineItems.long[0];
	}
	
	if ((complete) || (error) || (success) ||(hold)) {
		windowProxy1.removeEventListener(feedbackFunc);
		
		var totalCount = document.getElementById('lineItemId').value.split(',').length;
		var currCount = parseInt(document.getElementById('lineItemCount').value, 10);
		var hasMore = currCount < totalCount;
		if(utilityOffer == 'false'){
			if (hasMore && complete) {
				alert('CKO Completed. Proceeding to next CKO.');
			} else if(complete){
				//alert('Completed CKO processing, routing to order summary');
			}
			
			if (hasMore && error) {
				alert('Error in CKO. Proceeding to next CKO.');
			} else if(error){
				//alert('Error routing to order summary');
			}
	
			if (hasMore && success) {
				alert('success CKO. Proceeding to next CKO.');
			} else if(success){
				//alert('Success routing to order summary');
			}
			if(hold){
				//alert('hold status');
			}
		}
		if(hasMore){
			//When multiple products are selected for CKO
			$('#isBackButtonClicked').val("false");
			updateIframeSrc();
		} else {
			console.log("isClosingOffer="+$("#isClosingOffer").val() +"======================"+$("#callView").val());
			if($("#isClosingOffer").val() != '' && $("#isClosingOffer").val() == 'true'){
				window.location = $("#flowpath").val()+"&_eventId="+$("#callView").val()+"&orderId="+orderId;
			}else if(utilityOffer == 'true' && isRecommendation == 'false'){
				window.location = $("#flowpath").val()+"&_eventId=qualificationEvent&orderExternalId="+orderId+"&lineItemExtID="+lineItemExtID;
			}else if(utilityOffer == 'true' && isRecommendation == 'true'){
				window.location = $("#flowpath").val()+"&_eventId=recommendationsEvent&orderExternalId="+orderId+"&lineItemExtID="+lineItemExtID;
			}else{
				window.location = $("#flowpath").val()+"&_eventId=CKOCompleteEvent&orderId="+orderId;
			}
		}
	}
}

var onSubmitReturn = function(data){
	//alert("Return onsubmit ajax");
}

function feedbackSubmit(feedback) {
	var data = {};
	//takes to CartFeedbackOnStart, Metrics update happens there
	data["data"] = feedback;
	data["CKO_pageTitle"] = $("#CKOPageTitle").val();
	//takes to CartFeedbackOnStart, Metrics update happens there
	var path = $("input#contextPath").val();
	var url = path + "/rest/on_submit";
	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onSubmitReturn
	});
}

var onCompleteReturn = function(data){
	//alert("CKO Completed");
}

function completeCKO(iData) {
	var data = "data="+iData;
	var path = $("input#contextPath").val();
	var url = path + "/rest/on_complete";
	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onCompleteReturn 
	});
}

var onTerminateReturn = function(data){
	var data = JSON.parse(data.responseText);
	var CKO = data.CKO;

	var errorCode = CKO.errorCode;
	//alert(errorCode);
	var orderId = CKO.orderId;
	//alert(orderId);
	var customerId = CKO.customerId;
	//alert(customerId);

	var href = window.location.href;
	window.location = href.slice(0, href.lastIndexOf("/")+1)+"summary";
}

function terminateCKO(iData) {
	var data = "data="+iData;
	var path = $("input#contextPath").val();
	var url = path + "/rest/on_terminate";
	alert("calling ajax :: "+data);
	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onTerminateReturn 
	});
}

function updateShoppingCartCKOLineItem(feedBackObj){
	try{
		console.log(JSON.stringify(feedBackObj));
		var path = $("input#contextPath").val();
		var url = path + "/salescenter/updateShoppingCartCKOLineItems";
		var paramData = $.param(feedBackObj);
		var h4 = $("<h4></h4>").addClass("scrollH4");
		addScrollDiv(h4);
		$("div#itemsBlock").find("div.mCSB_container").append(h4);
		$.ajax({
			   type: 'GET',
			   url: url,
			   data: paramData,
			   dataType: "json",
			   success: function( data, textStatus, jqXHR) {
			   console.log("data===="+JSON.stringify(data));
			   upDateShoppingCart(data);
			   },
		       error: function( data, status, err ) {
				   upDateShoppingCart({'status':'error'});
		       }
		});
	}catch(e){
		console.log(e);
		$('.scrollH4').remove();
		$('.alignCenter').remove();
	}
}



