function feedbackFunc(messageEvent) {

	var feedback_main = messageEvent.data["feedback"];
	var feedback = feedback_main.feedback;
	var type = feedback_main.type;
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
}

var onLoadReturn = function(data){
	//alert("Return onsubmit ajax");
}

function feedbackLoad(feedback) {

	//takes to CartFeedbackOnStart, Metrics update happens there
	var data = "data="+feedback;
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
			var href = window.location.href;
			if(utilityOffer == 'true' && isRecommendation == 'false'){
				var index = href.lastIndexOf("salescenter");
				window.location = href.slice(0, index+12)+"qualification";
			}else if(utilityOffer == 'true' && isRecommendation == 'true'){
				var index = href.lastIndexOf("salescenter");
				window.location = href.slice(0, index+12)+"recommendations";
			}else{
				var index = href.indexOf("rest");
				window.location = href.slice(0, index+5)+"summary/" + orderId;
			}
		}
	}
}

var onSubmitReturn = function(data){
	//alert("Return onsubmit ajax");
}

function feedbackSubmit(feedback) {
	
	//takes to CartFeedbackOnStart, Metrics update happens there
	var data = "data="+feedback;
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
	alert("calling ajax :: "+data);
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



