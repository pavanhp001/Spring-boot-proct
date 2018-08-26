<!DOCTYPE html>
<html>
<head>
<link  rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style/helpCenter.css">
<link  rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style/animate.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.js"></script>
<script>
var provider='${showProviderHints}';
var pivotAssistJson = '${pivotAssistJson}';
var ProviderClick=false;
var objectionClick=false;
var pivotClick=false;
var providerLnt=0;
$(document).ready(function(){
	
	
	 var isEmailIconEnabled = ${isEmailIconEnabled};
	console.log("isEmailIconEnabled="+isEmailIconEnabled); 
	if(isEmailIconEnabled){
		$("#newEmailId").removeClass("inActiveIcon");
	}
	
	 $("#newEmailId").click(function(){
		 
		 var pageTitle = $(".pageTitle:visible").text();
		 pageTitle = pageTitle.trim();
		 if(pageTitle == undefined || pageTitle == ""){
			 pageTitle = $(".idelpageTitle:visible").text();
		 }
		 console.log("pageTitle="+pageTitle);
		 var hidePageTiles = ["Idle Page","Greetings","Basic Information","Valid Address","Multiple Address","Confirmation","No Address Match","Offers"];
		 if(hidePageTiles.indexOf(pageTitle) > -1 ){
			 $("#tootip_selfReport").css("display","none");
			 $("#tootip_doNotCall").css("display","none");
			 if(pageTitle === "Confirmation" || pageTitle === "Offers" || pageTitle === "Valid Address"){
			 	$("#tootip_doNotCall").css("display","block");
			 }
		 }
    	$("#emailContentDiv").show();
    	$("#patch_email").fadeIn(); 
		$("#objection_email").fadeIn();
		$(".bg_email").show();
    });
    $("#objection_pt").click(function(){
    });
   
	var hintProviderObj = '${hintProviders}';
	
	
	if(hintProviderObj != undefined && hintProviderObj != ''){
		try{
		hintProviderObj = hintProviderObj.replace("]","").replace("[","").split(",");;
		providerLnt=hintProviderObj.length;
		}catch(e){
			alert(e);
		}
	}
	
	if(provider=='true' && providerLnt>0){
		$("#help_p").removeClass("inActiveIcon");
		$("#help_p").click(function(){
			document.getElementById('help').style.pointerEvents = 'none';
			document.getElementById('PivotAssist').style.pointerEvents = 'none';
			console.log("hintsforproviders");
			if ($('#hintsforproviders').children().length == 0 && ProviderClick==false){
				ProviderClick=true;
				getHintsContentForProvider("porviderHints");
			}
			$("#objection_p").css("display","table"); 
			$("#patch_p").fadeIn();
		    $(".bg_p").show();
			cursorDefault();
		});
			//setTimeout(function(){savePageHtml(true, ""); }, 1000);
 	}
	var pageTitle = $(".pageTitle:visible").text();
	if(pivotAssistJson != undefined && pivotAssistJson != "" && (pageTitle.indexOf("CKO") > -1)){
		console.log("pivotAssistJson : "+JSON.stringify(pivotAssistJson));
		$("#PivotAssist").removeClass("inActiveIcon");
		$("#PivotAssist").click(function(){
			document.getElementById('help_p').style.pointerEvents = 'none';
			document.getElementById('help').style.pointerEvents = 'none';
			
			console.log("showDishAndTelcoPivotAssist");
			if ($('#dishTelcoPivotAssist').children().length == 0 && pivotClick==false){
				pivotClick=true;
				getContentForPivotAssist("porviderHints");
			}
			$("#objection_pt").css("display","table"); 
			$("#patch_pt").fadeIn();
		    $(".bg_pt").show();
			cursorDefault();
		});
			//setTimeout(function(){savePageHtml(true, ""); }, 1000);
 	}
	  
 	 $("#help").click(function(){
 		document.getElementById('help_p').style.pointerEvents = 'none';
		document.getElementById('PivotAssist').style.pointerEvents = 'none';
 		 
 		 console.log("hintsforObjections");
 		
		if ($('#hintsforObjections').children().length == 0 && objectionClick==false){  
			objectionClick=true;
			getHintsContentForObjection("objection");
		} 
		$("#patch").fadeIn(); 
		$("#objection").fadeIn();
		$(".bg").show();
		cursorDefault();
		
		//setTimeout(function(){ savePageHtml(true, ""); }, 1000);
  	});
 	 
 	 
 	$('.do_not_call').on('click', function () { 
 		var mail_type = $(this).attr('id');
 		console.log("mail_type="+mail_type);
 		$("#mail_type").val(mail_type);
 		var urlpath = "<%=request.getContextPath()%>";
 		console.log("path="+urlpath);
 		$("#pathUrl").val(urlpath);
 		 // openNewWindow();
 		
		closeObjection_email();
		var urlPath = "<%=request.getContextPath()%>/salescenter/emailTicket?mail_type="+mail_type;
		if(mail_type == "tootip_IT"){
			var pageTitle = $(".pageTitle:visible").text();
		 	pageTitle = pageTitle.trim();
		 	if(pageTitle == undefined || pageTitle == ""){
				 pageTitle = $(".idelpageTitle:visible").text();
		 	}
			urlPath = urlPath+"&pageTitle="+pageTitle;
			takeScreenShot(mail_type,urlPath);			
		}else{
			window.open(urlPath,"", "scrollbars=yes, width=1000, height=550");
		}
		
 	});
 	$("input[type=text],input[type=email],input[type=number]").attr("autocomplete","nope");

});


function takeScreenShot(mail_type,urlPath){
	try{		
	    var agentId = "${salescontext.scMap['agent.id']}";
	    var orderId = "${salescontext.scMap['order.id']}";
	    var iFrameBlob = null;
	    var iframeLen = $("iframe").length;
	    if(iframeLen > 0){
		    var body = $("iframe").contents().find("body");
			 html2canvas(body, {
			 onrendered: function(canvas) {
				var iFrameImg = canvas.toDataURL();			    
	    		var iFrameBlock = iFrameImg.split(";");
				 // Get the content type of the image
				var iFrameContentType = iFrameBlock[0].split(":")[1];// In this case "image/png"
				// get the real base64 content of the file
				var iFrameRealData = iFrameBlock[1].split(",")[1];			
				// Convert it to a blob to upload
				iFrameBlob = b64toBlob(iFrameRealData, iFrameContentType);
				
				html2canvas(window.parent.document.body, {
					 onrendered: function(canvas) {
					 	var img = canvas.toDataURL();						    
			    		var block = img.split(";");
						 // Get the content type of the image
						var contentType = block[0].split(":")[1];// In this case "image/png"
						// get the real base64 content of the file
						var realData = block[1].split(",")[1];					
						// Convert it to a blob to upload
						var blob = b64toBlob(realData, contentType);
						var formDataToUpload = new FormData();
						formDataToUpload.append("imageData", blob);
						formDataToUpload.append("iFrameImageData", iFrameBlob);
						formDataToUpload.append("orderId", orderId);
						formDataToUpload.append("agentId", agentId);
						var url = "<%=request.getContextPath()%>/salescenter/itTicketImageData";
						$.ajax({
							type: 'POST',
						    contentType:false,
						    processData:false,
						    cache:false,
							url: url,
							data: formDataToUpload,
						    dataType:"json", 
						    complete: function(data){
					    		console.log("inside the success");
					     		window.open(urlPath,"", "scrollbars=yes, width=1000, height=550");		    		
					    	}
						});
					 }
					 });
			 	}
			 });
	    }else{
	    	html2canvas(window.parent.document.body, {
				 onrendered: function(canvas) {
				 	var img = canvas.toDataURL();
					    
		    		var block = img.split(";");
					 // Get the content type of the image
					var contentType = block[0].split(":")[1];// In this case "image/png"
					// get the real base64 content of the file
					var realData = block[1].split(",")[1];
				
					// Convert it to a blob to upload
					var blob = b64toBlob(realData, contentType);
					iFrameBlob = new Blob();
				    
					var formDataToUpload = new FormData();
					formDataToUpload.append("imageData", blob);
					formDataToUpload.append("iFrameImageData", iFrameBlob);
					formDataToUpload.append("orderId", orderId);
					formDataToUpload.append("agentId", agentId);
					var url = "<%=request.getContextPath()%>/salescenter/itTicketImageData";
					$.ajax({
						type: 'POST',
					    contentType:false,
					    processData:false,
					    cache:false,
						url: url,
						data: formDataToUpload,
					    dataType:"json", 
					    complete: function(data){
				    		console.log("inside the success");
				     		window.open(urlPath,"", "scrollbars=yes, width=1000, height=550");		    		
				    	}
					});
				 }
				 });
	    }
	    
		
	} catch(e){
		console.log("exception :: " + e);
 		window.open(urlPath,"", "scrollbars=yes, width=1000, height=550");
	}
 }


function b64toBlob(b64Data, contentType, sliceSize) {
       contentType = contentType || '';
       sliceSize = sliceSize || 512;

       var byteCharacters = atob(b64Data);
       var byteArrays = [];

       for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
           var slice = byteCharacters.slice(offset, offset + sliceSize);

           var byteNumbers = new Array(slice.length);
           for (var i = 0; i < slice.length; i++) {
               byteNumbers[i] = slice.charCodeAt(i);
           }

           var byteArray = new Uint8Array(byteNumbers);

           byteArrays.push(byteArray);
       }

     var blob = new Blob(byteArrays, {type: contentType});
     return blob;
}

function cursorPointer(){
	$("#help").css("cursor","pointer");
	if(provider=='true'){
		$("#help_p").css("cursor","pointer");
	}
}

function cursorDefault(){
	$("#help_p").css("cursor","default");
	$("#help").css("cursor","default");
}

   function objectionBustersMouseOver(obj){
	    $(".allobj li").removeClass("active");
	    $(".subcont").hide();
	    $(".subcontup").hide();
	    $(obj).children(".subcont").show();
	    $(obj).children(".subcontup").show();
	    $(obj).addClass("active");
  }

  function objectionBustersMouseOver_pivot(obj,pivotAsstProviderId, pivotAsstOtherProviderId){
	    $(".allobj li").removeClass("active");
	    $(".subcont").hide();
	    $(".subcontup").hide();
	    $(obj).children(".subcont").show();
	    $(obj).children(".subcontup").show();
	    $(obj).addClass("active");
	    $(obj).find(".providerPivot").html("");
	  	console.log("pivotAsstProviderId "+pivotAsstProviderId);
	  	console.log("pivotAsstOtherProviderId "+pivotAsstOtherProviderId);
	  	var providerData = "";
	    if(pivotAsstProviderId != undefined && pivotAsstProviderId != ""){
		    providerData = $("#pivot_"+pivotAsstProviderId).html();
		    if(providerData != undefined && providerData != ""){
		   		 $(obj).find(".providerPivot").append(providerData);
		    }else{
		    	providerData = $("#pivot_Other").html();
		    	$(obj).find(".providerPivot").append(providerData);
		    }
		    if(pivotAsstOtherProviderId != undefined && pivotAsstOtherProviderId != "" && pivotAsstOtherProviderId > 0){
		    	$(obj).find(".otherProviderPivot").html("");
		    	var otherProviderData = $("#pivot_"+pivotAsstOtherProviderId).html();
			    if(otherProviderData != undefined && otherProviderData != ""){
			   		 $(obj).find(".otherProviderPivot").append(otherProviderData);
			    }else{
			    	otherProviderData = $("#pivot_Other").html();
			    	$(obj).find(".otherProviderPivot").append(otherProviderData);
			    }
		    }else{
		    	$(obj).find(".otherProviderPivot").hide();
		    }
	    }else{
	    	providerData = $("#pivot_Other").html();
	    	$(obj).find(".providerPivot").append(providerData);
	    }
}
  
  function objectionBustersMouseOut(obj){
	   //$(".allobj li").removeClass("active");
	    //$(".subcont").hide();
	    //$(".subcontup").hide();
	    $(obj).hide();
 }


  function objectionBusterspopMouseOut(obj){
	   $(obj).hide();
}
  function closepp(){
      $(".subcont").hide();
      $(".subcontup").hide();
}
  function closepp1(){
      $("#emailContentDiv").hide();
}

function closeObjection(){
	$("#help_p").css("pointerEvents","auto");
	$("#help").css("pointerEvents","auto");
	$("#PivotAssist").css("pointerEvents","auto");
    $(".subcont").hide();
    $(".subcontup").hide();
    
	 $("#patch").fadeOut(); 
	 $("#objection").fadeOut(); 
	 $(".bg").hide();
	 $('#objection_email').hide();
	 cursorPointer();
}
function closeObjection_email(){
	$("#help_p").css("pointerEvents","auto");
	$("#help").css("pointerEvents","auto");
	$("#PivotAssist").css("pointerEvents","auto");
    $("#emailContentDiv").hide();
    
	 $("#patch_email").fadeOut(); 
	 $("#objection_email").fadeOut(); 
	 $(".bg").hide();
	 cursorPointer();
}
function closeEmail(){
	$("#help_p").css("pointerEvents","auto");
	$("#help").css("pointerEvents","auto");
	$("#PivotAssist").css("pointerEvents","auto");
    $(".subcont").hide();
    $(".subcontup").hide();
    
	 $("#patch").fadeOut(); 
	 $(".bg").hide();
	 cursorPointer();
}
function closeObjection_pt(){
	document.getElementById('help_p').style.pointerEvents = 'auto';
	document.getElementById('help').style.pointerEvents = 'auto';
	document.getElementById('PivotAssist').style.pointerEvents = 'auto';
	 $("#patch_pt").fadeOut(); 
	 $("#objection_pt").fadeOut(); 
	 $(".bg_pt").hide();
	    $(".subcont_Pivot").hide();
        $(".subcontup_Pivot").hide();
	 cursorPointer();
}

function closeProviderHints(){
	document.getElementById('help_p').style.pointerEvents = 'auto';
	document.getElementById('help').style.pointerEvents = 'auto';
	document.getElementById('PivotAssist').style.pointerEvents = 'auto';
	$("#patch_p").fadeOut(); 
	 $("#objection_p").fadeOut(); 
	 $(".bg_p").hide();
	 cursorPointer();
}

function setheights()
{
  var mainuldiv = $(".singleoptn .optiondetls").first();
    var mainullist = $(mainuldiv).children("ul");
    for(var i=1; i<= mainullist.length; i++)
    {
      var ulforheight = $(".ul_" + i);
    var ulheight = "";
    ulforheight.each(function(){
      var curul = $(this).height();
      if(curul < ulheight)
      {
        ulheight = ulheight;
      }
      else
      {
        ulheight = curul;
      }
    });
    ulforheight.css("height", ulheight + "px");
    }
}

function getHintsContentForObjection(hintType){
	try{
		var pageTitle = $(".pageTitle:visible").text();
	
		var path = $("#contextPath").val();
		var url = path+"/salescenter/getHintsContentforObjection";
		var data = {};
		data["pageTitle"] = pageTitle;
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			complete: bulidHintsContentForObjection
		});
	}catch(e){
		console.log("getHintsContentForObjection- "+e);
		objectionClick=false;
	}
	
}	
var bulidHintsContentForObjection = function(data){
	
	if(data == "sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else if(data.responseText=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		if(data.responseText != ""){
			$('#hintsforObjections').append(data.responseText);
			$("#close_btn").attr("onclick","closeObjection()");
			$(".allobj li").attr("onmouseover","objectionBustersMouseOver(this)");

			$(".allobj li .subcont").attr("onmouseout","objectionBusterspopMouseOut(this)");
			$(".allobj li .subcontup").attr("onmouseout","objectionBusterspopMouseOut(this)");
			
			
			$(".allobj li").attr("onmouseout","objectionBustersMouseOut()");
			$("#patch").attr("onclick","closeObjection()");
			


			

			$("#objection").attr("onclick","closepp()");
			$("#objection").attr("onclick","closepp()");
			
			
			
			$("#patch").fadeIn(); 
			$("#objection").css("display","table");  
			$(".bg").show();
			
			var timestamp = "${order.moveDate}";
			   
		   if(timestamp != undefined && timestamp != ""){
			   //timestamp = timestamp.split("+")[0];
			   var year = timestamp.substring(0, 4);
			   var month = timestamp.substring(5, 7);
			   var date = timestamp.substring(8, 10);
			   var timestamp = month+"/"+date+"/"+year;
	      		$(".moveInDate").html(timestamp);
		   }else{
			   $(".moveInDate").html("_____");
		   }
			
		}else{
			console.log("drupal content is not there");
	    }
	}
	
}


function getHintsContentForProvider(hintType){
	try{
		var pageTitle = $(".pageTitle:visible").text();
		var path = $("#contextPath").val();
		var url = path+"/salescenter/getHintsContentForProviders";
		var data = {};
		data["pageTitle"] = pageTitle;
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			complete: bulidHintsContentForProvider
		});
	}catch(e){
		console.log("getHintsContentForProvider- "+e);
		ProviderClick=false;
	}
}

function getContentForPivotAssist(hintType){
	try{
		var pageTitle = $(".pageTitle:visible").text();
		var path = $("#contextPath").val();
		var url = path+"/salescenter/getContentForPivotAssist";
		var data = {};
		data["pageTitle"] = pageTitle;
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			complete: bulidContentForPivotAssist
		});
	}catch(e){
		console.log("getHintsContentForProvider- "+e);
		pivotClick=false;
	}
}

var bulidHintsContentForProvider = function(data){
	
	if(data == "sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else if(data.responseText=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		if(data.responseText != ""){
			$('#hintsforproviders').append(data.responseText);
			$("#close_btn_p").attr("onclick","closeProviderHints()");
			$("#patch_p").attr("onclick","closeProviderHints()");
			$("#patch_p").fadeIn(); 
			$("#objection_p").css("display","table");  
			$(".bg_p").show();
			
			$(".singleoptn").hide();
			   var optionscount=0;
			   var hintProvidersVal ='${hintProviders}';
			   if(hintProvidersVal != undefined && hintProvidersVal != ""){
				  
				   hintProvidersVal = hintProvidersVal.replace("]","").replace("[","").split(",");
				   for(obj in hintProvidersVal)
			       {
					   if(hintProvidersVal[obj].trim()!=''){
				    	   $("#"+hintProvidersVal[obj].trim()).show();
				    	   if($("#"+hintProvidersVal[obj].trim()).is(':visible')){optionscount++;}
					   }
			    	}
				    var divwidth = optionscount * 300;
				    $(".optionsmain").css("width", divwidth + "px");
				    if(optionscount<=4){
				   		 $("#objection_p").css("width", divwidth+30 + "px");
				    }
				    setheights();
			   }
		}else{
			console.log("drupal content is not there");
	    }
	}
}

var bulidContentForPivotAssist = function(data){
	
	if(data == "sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else if(data.responseText=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		console.log("bulidContentForPivotAssist else");
		if(data.responseText != ""){
			console.log("bulidContentForPivotAssist data"+data.responseText);
			if(pivotAssistJson != undefined && pivotAssistJson != ""){

				pivotAssistJson = JSON.parse(pivotAssistJson);

				var providerId = pivotAssistJson.providerId;
				console.log("providerId : "+pivotAssistJson.providerId);
				console.log("providerId : "+pivotAssistJson["providerId"]);
				var providerName = pivotAssistJson.providerName;
				var pivotAsstProviderId = pivotAssistJson.pivotAsstProviderId;
				var pivotAsstProviderName = pivotAssistJson.pivotAsstProviderName;
				var pivotAsstOtherProviderId = pivotAssistJson.pivotAsstOtherProviderId;
				var pivotAsstOtherProviderName = pivotAssistJson.pivotAsstOtherProviderName;
				var pivotAsstName = pivotAssistJson.pivotAsstName;
				console.log("pivotAsstProviderId : "+pivotAsstProviderId);

				$('#dishTelcoPivotAssist').append(data.responseText);
				$("#close_btn_pt").attr("onclick","closeObjection_pt()");
			
					 if(pivotAsstOtherProviderId == undefined || pivotAsstOtherProviderId ==""){
						 pivotAsstOtherProviderId = 0;
					 }
					 //$(".allobj li").mouseover(objectionBustersMouseOver_pivot(this,pivotAsstProviderId,pivotAsstOtherProviderId))
					  //var a = "objectionBustersMouseOver_pivot(this,'15500161','0')";
				      var b = "objectionBustersMouseOver_pivot(this,'"+pivotAsstProviderId+"','"+pivotAsstOtherProviderId+"')"
				     // $(".allobj li").mouseover(objectionBustersMouseOver_pivot(pivotAsstProviderId,pivotAsstOtherProviderId))
				      $(".allobj li").attr("onmouseover",b);
				 	//$(".allobj li").attr("onmouseover","objectionBustersMouseOver_pivot(this,"+jsondata+")"); 
					//$(".allobj li").attr("onmouseover","objectionBustersMouseOver_pivot(this,"+pivotAsstProviderId+","+pivotAsstOtherProviderId+")");
					//$(".allobj li").attr("onmouseout","objectionBustersMouseOut()");
				$(".allobj li .subcont_Pivot").attr("onmouseout","objectionBustersMouseOut(this)");
				$("#patch_pt").attr("onclick","closeObjection_pt()");
				$("#patch_pt").fadeIn(); 
				$("#objection_pt").css("display","table");  
				$(".bg_pt").show();
				$("#objection_pt").find("#"+providerId).show();
				
			   if(pivotAsstProviderName != undefined && pivotAsstProviderName != ""){
				   if(pivotAsstProviderName == "ATTV6" ){
					   pivotAsstProviderName = "ATTV6/DirecTV";
				   }
				   if(pivotAsstOtherProviderName != undefined && pivotAsstOtherProviderName != ""){
					   if(pivotAsstOtherProviderName == "ATTV6" ){
						   pivotAsstOtherProviderName = "ATTV6/DirecTV";
					   }
					    $(".pivotAsstProviderName").html(pivotAsstProviderName+" and "+pivotAsstOtherProviderName);
				   }else{
		      			$(".pivotAsstProviderName").html(pivotAsstProviderName);
				   }
			   }else{
				   $(".pivotAsstProviderName").html(" ");
			   }
			   var pageTitle = $(".pageTitle:visible").text();
			   if(pageTitle != undefined && pageTitle != ""){
				   if(pageTitle.indexOf("ATTV6") != -1){
					   pageTitle = "ATTV6/DirecTV";
				   }
				   pageTitle = pageTitle.replace("CKO", "");
		      		$(".providerName").html(pageTitle);
			   }else{
				   $(".providerName").html(" ");
			   }
			}else{
				console.log("drupal content is not there");
		    }
		}
	}
	

}



		
</script>
</head>
	
<body>
<div id = "hintsforObjections">
</div>
<div id = "hintsforproviders">
</div>
<div id = "dishTelcoPivotAssist">
</div>

	<div id="emailContentDiv" style="display:none">
		<div class="bg_email" style="display: block;">
			<div id="patch_email" onclick="closeObjection_email()" style="display: block;"></div>
			<div id="objection_email" class="animated bounceInDown" onclick="closepp()"
				style="display: table;">
				<div id="close_btn_eamil" onclick="closeObjection_email()">X</div>
				<div class="topicmain topicmain_email">
					<div class="tooltip do_not_call" id="tootip_doNotCall">
    					Do Not Call
  					 </div> 
  					 <div class="tooltip do_not_call" id="tootip_IT">
    					IT Ticket
  					 </div> 
  					 <div class="tooltip do_not_call" id="tootip_selfReport">
    					Self Report
  					 </div> 
					
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</body>


