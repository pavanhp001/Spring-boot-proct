var removeAsstrickExtList =["consumer.previousAddress.dwelling.stateOrProvince",
                            "consumer.previousAddress.dwelling.line2info","consumer.previousAddress.dwelling.line2","consumer.cellPhoneNumber"];
var enableBackBtnInfoArr = [];
var isBackButtonInfo = true;
var selectedSerivceType = "";
var enteredValueArr = [];
var phoneOptionsArr = ['consumer.cellPhoneNumber','consumer.homePhoneNumber','consumer.workPhoneNumber'];
var mobileregex = new RegExp('^[0-9]$');
$(document).ready(function() {
 $("input[name='maxdeposit']").on("change", function() {
     if (this.checked) {
         $("#ssn_no").hide();
     } else {
         $("#ssn_no").show();
     }
 });
 $("input[name='mailingadd']").on("change", function() {
     if (this.checked) {
         $("#mailing_address_sec").hide();
     } else {
         $("#mailing_address_sec").show();
     }
 });
 $("input[name='shippingadd']").on("change", function() {
     if (this.checked) {
         $("#shipping_address_sec").hide();
     } else {
         $("#shipping_address_sec").show();
     }
 });
 $("input[name='mailingadd']").trigger("change");
 $("input[name='shippingadd']").trigger("change");
 $("input[name='one']").on("change", function() {
     if (teleno == "new_no") {
         $("#new_no_cont").show();
         $("#port_no_cont").hide();
     } else {
         $("#new_no_cont").hide();
         $("#port_no_cont").show();
     }
 });
 $.each(dialogueJson, function(i, item) {
     var enable = true;
     var dialogueServiceItem;
     $.each(item.dataGroupList, function(j, dataGroupList) {
    	 var homeServicesOptions = $("<div></div>").attr({"class": "home-services-options row"});
    	 var dataServicesHead = $("<div></div>").attr({"class": "home-services-head"}).text(dataGroupList.subTitle);
    	 homeServicesOptions.append(dataServicesHead);
    	 var dataServicesbody = $("<div></div>").attr({"class": "home-services-body"});
   
         $.each(dataGroupList.dataFeildList, function(k, dataFeildList) {
             var isEnabled = dataFeildList.isEnabled;
             if (isEnabled) {
                 dialogueServiceItem = buildHtml(dataFeildList, enable);
                 dataServicesbody.append(dialogueServiceItem);
              } else {
                 enableArr.push(dataFeildList);
                  if(dataFeildList.dataFieldText != undefined && dataFeildList.dataFieldExID == "SMSOptIn"){
             		console.log("dataFeildList.dataFieldText ------ :: "+dataFeildList.dataFieldText)
                 	$(".marketOptinContent").hide();
                 	$(".marketingOptIn").text(dataFeildList.dataFieldText);
             		if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != ""){
                		$("#marketingOptIn").attr({"checked":"checked"});
                     }
             		
             	}
                 
             }
         });
    	 homeServicesOptions.append(dataServicesbody);
    	 if(dataGroupList.subTitle != "Contact Information"){
    		 $(".dialogueContent").append(homeServicesOptions);
    	 }else if(dataGroupList.subTitle == "Contact Information" || dataGroupList.subTitle == "Customer and Contact Information"){
    		    $.each(dataGroupList.dataFeildList, function(k, dataFeildList) {
    	           var tagType = $("[name="+dataFeildList.dataFieldExID+"]");
    	           $("[name="+dataFeildList.dataFieldExID+"]").val(dataFeildList.enteredValue);
    	           if(tagType.is("input")){
    	        	   $("[name="+dataFeildList.dataFieldExID+"]").val(dataFeildList.enteredValue);
    	           }else if(tagType.is("select")){
    	        	   $("[name="+dataFeildList.dataFieldExID+"] option:selected").val(dataFeildList.enteredValue)
    	           }
    	         });
    	 }
     });
     console.log("isBackButtonInfo ===> "+isBackButtonInfo);
     if(enteredValueArr[0] != undefined && enteredValueArr[0].dlState != undefined){
    	 $(".driversLicenseState").val(enteredValueArr[0].dlState); 
     }
     if(!isBackButtonInfo){
    	  buildServiceTypeContent();
    	     buildPreviousCheckedContent();
    	     buildPreviousSelectContent();
    	     $.each(item.dataGroupList, function(j, dataGroupList) {
                 $.each(dataGroupList.dataFeildList, function(k, dataFeildList) {
              		var isEnabled = dataFeildList.isEnabled;
              		//Back button info
              		if(!isEnabled){
              			 if(dataFeildList["enteredValue"] != undefined && dataFeildList["enteredValue"] != ""){
         	        		 dataFeildList["enteredValue"] = "";
         	        	 }
              			enableBackBtnInfoArr.push(dataFeildList);
              		}
              
                    });
                 enableArr = enableBackBtnInfoArr;
    	     });
    	     $('#hcontact').val( prepopulatePreviousInfoDashes( $('#hcontact').val()));
    	     $('#bcontactinstal').val( prepopulatePreviousInfoDashes( $('#bcontactinstal').val()));
    	     $("input[name='SSN']").val( prepopulatePreviousInfoDashes( $("input[name='SSN']").val()));
    	     $(".driversLicenseState").val(enteredValueArr[0].dlState);
    	     $("input[validation='SSN'],input[valueTarget='consumer.dateOfBirth'],input[validation='credit']").each(maskPaymentInformation);
     }
    });
 
 var options = {trigger: 'hover click'}; 
 $('[data-toggle="popover"]').attr('data-content',$.Constants.SSN_MESSAGE);
 $('[data-toggle="popover"]').popover(options); 
 
	/******************** New or transfer Customer tab press automatically trigger click event  *********** */
	$('.newOrTransferCustomer').on('keypress', function(e) {
	    var tag = e.target.tagName.toLowerCase();
	    if ( (e.which === 13) && (tag == 'div') ){
	    	$(this).trigger("click");
	    }
	});
	
 displayCKOLoader(true);
 $('input').attr('autocomplete','off');
 $("#iData").click(changeOrientation)
 $(window).on("orientationchange",changeOrientation);

  $("#bcontactinstal").each(prepopulateDashes);
 $("input[validation='SSN'],input[valueTarget='consumer.dateOfBirth'],input[validation='credit']").each(maskPaymentInformation);
 appendEvents("input[validation*='Numeric']",isNumber,"keypress");
 if( $("#BestContactNum").val() != ""){
	 var bContactVal = $("#BestContactNum");
	 bContactVal.data("previous", bContactVal.val());
	 dropDownChnage(bContactVal.val(), undefined,bContactVal);
 }
 $(".form-row .dialogueServiceItem").remove();
 $("#hcontact .mandatory").hide();
 if($("#bcontactContent select option:selected").val() == "Cell"){
	 $(".marketOptinContent").show();
 }
 if($(".dataFieldCont").val() == saddress){
	 conolse.log("serviceAddress==="+$(".dataFieldCont").val());
	 conolse.log("saddress==="+saddress);
	 
	 removeAsstrickExtList.push(".dataFieldCont");	 
 }
});
function registerOnloadEventsAndFunctions(){
	appendEvents("select#BestContactNum",dropDownChnageBuild,"change");
   	appendEvents("input",setDynamicHeight,"click");
    appendEvents("select",setDynamicHeight,"change");
    dynamicFieldModification();
	setDynamicHeight();
	$("#bottom-footer .next_step span").click(submitAccountHolderQualificationsInfo);
	$("#customizations .next_step span").click(submitAccountHolderQualificationsInfo);
	$('#hcontact,#bcontactinstal,input[name="SSN"],input[name="DateOfBirth"],input[validation*="Numeric"], #hcontactContent input').on("keypress",isNumber);
	$("input[name='DateOfBirth']").on("keypress keydown keyup change blur",prepopulateDobDashes);
	$('#bcontactinstal, #hcontactContent input').on("keypress keydown keyup change blur",prepopulateDashes);
	$("input[name='SSN']").on("keypress keydown keyup change blur",prepopulateSsnDashes);
	 $("input[validation='SSN']").focus(populateMaskedValues);
	 $("input[validation='SSN']").blur(maskPaymentInformation);
	 $("input[valueTarget='consumer.dateOfBirth']").focus(populateMaskedValues);
	 $("input[valueTarget='consumer.dateOfBirth']").blur(maskPaymentInformation);
	 
}
function registerOnloadEventsAndFunctionsAppliance(){
	appendEvents("select#BestContactNum",dropDownChnageBuild,"change");
   	appendEvents("input",setDynamicHeight,"click");
    appendEvents("select",setDynamicHeight,"change");
	setDynamicHeight();
	$("#bottom-footer .next_step span").click(submitAccountHolderQualificationsInfo);
}

function appendEvents(tagName,functionName,eventName){
	$(tagName).unbind( eventName, functionName);
	$(tagName).bind(eventName,functionName);
}

$.fn.serializeObject = function() {
    var jsonOBJ = [];
    var a = this.serializeArray();
    $.each(a, function(i,val) {
    	  if(val.value != "" && val.value != "select"){
    	   var jsonOBJ1 = {};
   	     
   	       if(val.name == "offerPromotionOptIn"){
   	    	  jsonOBJ1['name'] = "OfferPromotionOptIn";
   	       }else if(val.name == "smsOptIn"){
   	    	  jsonOBJ1['name'] = "SMSOptIn";
   	       }else{
   	    	  jsonOBJ1['name'] = val.name;
   	       }
   	       var validation = $('[name='+val.name+']').attr('validation');
   	       var valueTarget = $('[name='+val.name+']').attr('valueTarget');
   	    /********************* customize credit card expirationDate ****************************************/
   	       if($('[name='+val.name+']').attr('valueTarget') == "consumerFinancialInfo.creditCard.expirationDate"){
   	    	jsonOBJ1['value'] = $(".expirationDate:eq(0)").val()+"/"+$(".expirationDate:eq(1)").val();
   	       } 
   	       
   	    /********************* Add masked values ****************************************/
   	       else if((validation != undefined ) &&( validation == "SSN" || validation == "credit" || validation == "debit" || (validation == "Date" && valueTarget == "consumer.dateOfBirth")) ){
   	    	   var maskedValues = $('[name='+val.name+']').attr("maskedValue")
   	    	   if(maskedValues != undefined && maskedValues != ""){
   	    		   jsonOBJ1['value'] = prepopulatePreviousInfoDashes(maskedValues);
   	    	   }
   	       }else{
   	    	jsonOBJ1['value'] = val.value;
   	       }
   	      
   	       //append masked values
   	     
   	       jsonOBJ1['displaytype'] = $('[name='+val.name+']').attr('displaytype');
   	       jsonOBJ1['inputype'] = $('[name='+val.name+']').attr('type');
   	       
   	    	   jsonOBJ1['valueTarget'] = $('[name='+val.name+']').attr('valueTarget');
   	      jsonOBJ.push(jsonOBJ1);
            }
    });

    return jsonOBJ;
};

function appendEvents(tagName,functionName,eventName){
	$(tagName).unbind( eventName, functionName);
	$(tagName).bind(eventName,functionName);
}


//enable and disable logic     
function dropDownChnageBuild() {
    var serviceVal = $(this).val();
    var previous = $(this).data("previous");
    $(this).data("previous", serviceVal);
    showOptOptions(serviceVal,$(this))
    if($(this).attr("id") != undefined){
    	$(this).data("previousContentId", $(this).attr("id"));
    }
    dropDownChnage(serviceVal, previous,$(this))
}
function showOptOptions(serviceVal,thisObj){
	if(serviceVal == "Cell"){
   	 $(".marketOptinContent").show();
   }else if(serviceVal == "Home" || serviceVal == "Work" || (thisObj.attr("valuetarget") != undefined && serviceVal == "" && thisObj.attr("valuetarget") == "consumer.bestContactNumber")){
   	$("#marketingOptIn").attr("checked",false)
   	$(".marketOptinContent").hide();
   }
}
//enable and disable logic
function dropDownChnage(serviceVal, previous,thisObj) {
    var serviceValArr = serviceVal;
    if (previous != undefined) {
        removeDisableFields(previous,thisObj.attr("name"));
    }
    var dataFeildList = dataFieldMatrixMap[thisObj.attr("name")];

    var enable = false;
    if(dataFeildList != undefined){
       var dialogueServiceItem;
        if(dataFeildList[serviceVal] != undefined){
		 $.each(dataFeildList[serviceVal], function(k, enableItems) {
	            $.each(enableArr, function(l, enableArrObj) {
	                if (enableArrObj.dataFieldExID == enableItems.externalId) {
	                	console.log("enableArrObj =============>>>>>>>>>>  "+enableArrObj.dataFieldExID)
	                    dialogueServiceItem = buildHtml(enableArrObj, enable);
	                		thisObj.parent().after(dialogueServiceItem);
	                    return false;
	                }
	            });
	        });
		 $(".form-row .dialogueServiceItem").remove();
		 $(".form-row #hcontact .mandatory").show();
		 	$("input[valuetarget='consumerFinancialInfo.creditCard.number']").attr("maxlength","16");
		    appendEvents('input[valuetarget="consumerFinancialInfo.creditCard.number"]',isNumber,"keypress");
			appendEvents('input[valuetarget="consumerFinancialInfo.creditCard.number"]',validateCreditCardNumber,"blur");
        }
    }
    appendEvents("select",dropDownChnageBuild,"change");
    appendEvents("input",setDynamicHeight,"click");
    appendEvents("select",setDynamicHeight,"change");
    appendEvents("input[validation='credit']",maskPaymentInformation,"blur");
    appendEvents("input[validation='credit']",populateMaskedValues,"focus");
    $('#hcontactContent input').attr("maxlength","12");
    $('#hcontactContent input').on("keypress keydown keyup change blur",prepopulateDashes);
    $("#hcontactContent input").each(prepopulateDashes);
    
    $("input[validation='Date']").each(function(){
    	var dateField = $(this).attr("valueTarget");
    	if(dateField != undefined && dateField != "consumer.dateOfBirth"){
    		$(this).datepicker({
    				format: 'mm/dd/yyyy',			
    			    autoclose: true
    		});
    	}
    });
   
} 

/**
 * This function is used for build account holder qualifications information  html content 
 * @param dataFeildList -- data field list json (dailogue content)
 * @param isEnable -- diplay enalble fields
 * @return dialogueServiceItem -- div object
 * 
 */
function buildHtml(dataFeildList, isEnable) {

    var dataFieldExIDs = dataFeildList.dataFieldExID;
    var mandatoryStar = $("<span></span>").text("*").attr({
        "class": "mandatory"
    });
    var dataFieldContSpan = $("<span></span>").text("*").attr({
        "class": "dataFieldContSpan"
    });
    var dialogueServiceItem = $("<div></div>").attr({
        "class": "dialogueServiceItem"
    });
    var dataFieldText = $("<div></div>").attr({
        "class": "dataFieldText"
    });
    var dataFieldCont = $("<div></div>").attr({
        "class": "dataFieldCont"
    });
    var dataFieldDisclosure = $("<div></div>").attr({
        "class": "alert alert-info"
    }).text(dataFeildList.dataFieldText);
    var dataFieldinforamtion = $("<span></span>").attr({
        "class": "dataFieldinforamtion"
    }).text("i");
    
    var staticSubHeading = $("<div></div>").attr({
        "class": "home-services-head"
    }).text("Previous Address");
   
    
    var dispalyInput = dataFeildList.dispalyInput.toLowerCase();
    var displayHtmlTag;

    if (dispalyInput != undefined && dispalyInput == "text") { 
    	var validation = dataFeildList.validation;
		 var maxLength = "";
		 if(validation != undefined && validation.split(":")[1] == "Numeric"){
			 maxLength = validation.split(":")[0];
		 }
		if(dataFeildList.dataFieldText != undefined && dataFeildList.dataFieldExID == "DateOfBirth"){
			 if(dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != undefined){
				 //$("<input /").attr({"name": dataFieldExIDs,"type":"text" ,"displayType":dataFeildList.type ,"valueTarget":dataFeildList.valueTarget ,"class":"dialogueTextInput" })
	    			displayHtmlTag = "<input id="+dataFeildList.dataFieldExID+"  validation="+dataFeildList.validation+"  name=" + dataFieldExIDs + " type='text' displayType="+dataFeildList.type+" valueTarget="+dataFeildList.valueTarget+" class='dialogueTextInput' maskedvalue='"+dataFeildList.enteredValue+"' value='"+dataFeildList.enteredValue+"'>";
	    		}else{
	        		displayHtmlTag = "<input id="+dataFeildList.dataFieldExID+" validation="+dataFeildList.validation+"  name=" + dataFieldExIDs + " type='text' displayType="+dataFeildList.type+" valueTarget="+dataFeildList.valueTarget+" class='dialogueTextInput'>";
	    		}
		        dataFieldContSpan.text(dataFeildList.dataFieldText);
		        if(isAppendAstreck(dataFeildList)){
		        	dataFieldCont.append(mandatoryStar);
		        }
		        dataFieldCont.append(dataFieldContSpan);
		        dataFieldCont.append("<br>"+displayHtmlTag);
		        dataFieldText.append(dataFieldCont); 
		}else if(dataFeildList.dataFieldText != undefined && dataFeildList.dataFieldExID == "SSN"){
			 var anchorHelpIcon = $("<a></a>").attr({"data-toggle":"popover","type":"button","data-html":"true","data-placement":"top","data-trigger":"hover"}).css({"cursor":"pointer"}).addClass("ssn-help-icon ").text("?");
			 					  
			 if(dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != undefined){
    			displayHtmlTag = "<input name=" + dataFieldExIDs + " validation="+dataFeildList.validation+" type='text' displayType="+dataFeildList.type+" valueTarget="+dataFeildList.valueTarget+" class='dialogueTextInput' maskedvalue='"+dataFeildList.enteredValue+"' value='"+dataFeildList.enteredValue+"'>";
    		}else{
        		displayHtmlTag = "<input name=" + dataFieldExIDs + " validation="+dataFeildList.validation+" type='text' displayType="+dataFeildList.type+" valueTarget="+dataFeildList.valueTarget+" class='dialogueTextInput'>";
    		}
	        dataFieldContSpan.text(dataFeildList.dataFieldText+" ");
	        dataFieldContSpan.append(anchorHelpIcon);
	        if(isAppendAstreck(dataFeildList)){
	        	dataFieldCont.append(mandatoryStar);
	        }
	        dataFieldCont.append(dataFieldContSpan);
	        dataFieldCont.append("<br>"+displayHtmlTag);
	        dataFieldText.append(dataFieldCont); 
		}else if(dataFeildList.dataFieldText != undefined && dataFeildList.dataFieldExID == "DLNumber" ){
			 dlContent = $("<div></div>").attr({"class":"dlContent"});
			 if(dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != undefined){
    			displayHtmlTag = "<input name=" + dataFieldExIDs + " type='text' displayType="+dataFeildList.type+" valueTarget="+dataFeildList.valueTarget+" class='dialogueTextInput' value='"+dataFeildList.enteredValue+"'>";
    		}else{
        		displayHtmlTag = "<input name=" + dataFieldExIDs + " type='text' displayType="+dataFeildList.type+" valueTarget="+dataFeildList.valueTarget+" class='dialogueTextInput'>";
    		}
		 
	        dataFieldContSpan.text(dataFeildList.dataFieldText);
	        if(isAppendAstreck(dataFeildList)){
	        	dlContent.append(mandatoryStar);
	        }
	        dlContent.append(dataFieldContSpan);
	        dlContent.append("<br/>" +displayHtmlTag+"<br/>");	
	        dataFieldCont.append(dlContent);
	        var stateContent = $("<div></div>").attr({"class":"dlContent"});
	        
	        stateContent.append("<span class='mandatory'>*</span>");
	        stateContent.append("<span class=''>State</span>");
	        stateContent.append("<br>");   
	         		
	           
	        
	        displayHtmlTag = $("<select></select>").attr({
                "id": dataFeildList.dataFieldExID,
                "class": dataFeildList.dataFieldExID+" driversLicenseState",
                "name": "DLState",
                "displayType":dataFeildList.type,
                "valueTarget":'consumer.identification.driverLicense.state'
            });
	        
            if (dataFeildList.dataConstraintValueList != undefined) {
            	var stateList = states.items.item;
            	option = $("<option></option>").attr("value", "").text("Select");
            	displayHtmlTag.append(option);
                $(stateList).each(function(){
                    var txt = this.lookupKey;
                    var val = this.description;
                    option = $("<option></option>").attr("value", val).text(txt);
                    if((dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "") && (dataFeildList.enteredValue == val)){
                    	option.attr({"selected":"selected"});
                    }
                    displayHtmlTag.append(option);
                });
            }
            stateContent.append(displayHtmlTag); 
            dataFieldCont.append(stateContent);  
            dataFieldText.append(dataFieldCont); 	     
	} else if(dataFeildList.dataFieldText != undefined && dataFeildList.dataFieldExID == "DLState"){
		  if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != ""){
			  var dlState = {"dlState":dataFeildList.enteredValue};
			  enteredValueArr.push(dlState);
          }
				dataFieldText.hide();
	} else{
	        if(dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != undefined){
    			displayHtmlTag = "<input id="+dataFieldExIDs+"  maxlength="+maxLength+"  validation="+dataFeildList.validation+" name=" + dataFieldExIDs + " type='text' displayType="+dataFeildList.type+" valueTarget="+dataFeildList.valueTarget+" class='dialogueTextInput' value='"+dataFeildList.enteredValue+"'>";
    		}else{
        		displayHtmlTag = "<input id="+dataFieldExIDs+" maxlength="+maxLength+"  validation="+dataFeildList.validation+" name=" + dataFieldExIDs + " type='text' displayType="+dataFeildList.type+" valueTarget="+dataFeildList.valueTarget+" class='dialogueTextInput'>";
    		}
	        
	        
	        if($.inArray(dataFeildList.valueTarget, phoneOptionsArr) > -1){
	        	$("#hcontactContent").show();
	        	$("label#hcontact .hcontactText").append(dataFeildList.dataFieldText);
            	$("label#hcontact").after(displayHtmlTag);
            	
            	if(showCustomerInformation){
            		dataFieldContSpan.text(dataFeildList.dataFieldText);
     		        if(isAppendAstreck(dataFeildList)){
     		        	dataFieldCont.append(mandatoryStar);
     		        }
     		        dataFieldCont.append(dataFieldContSpan);
     		        dataFieldCont.append("<br>"+displayHtmlTag);
     		        dataFieldText.append(dataFieldCont); 
            	}
	        }else{
		        dataFieldContSpan.text(dataFeildList.dataFieldText);
		        if(isAppendAstreck(dataFeildList)){
		        	dataFieldCont.append(mandatoryStar);
		        }
		        dataFieldCont.append(dataFieldContSpan);
		        dataFieldCont.append("<br>"+displayHtmlTag);
		        dataFieldText.append(dataFieldCont); 
	        }

    	}
	
    } else if (dispalyInput != undefined && dispalyInput == "checkbox") {
    	 if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != ""){
    		 displayHtmlTag = "<input type='checkbox' checked='checked' id=" + dataFieldExIDs + " name=" + dataFieldExIDs + " value=" + dataFieldExIDs  + "valueTarget="+dataFeildList.valueTarget+" displayType="+dataFeildList.type+" class='checkBoxInput'>";
          }else{
        	  displayHtmlTag = "<input type='checkbox' id=" + dataFieldExIDs + " name=" + dataFieldExIDs + " value=" + dataFieldExIDs  + "valueTarget="+dataFeildList.valueTarget+" displayType="+dataFeildList.type+" class='checkBoxInput'>";
          }
        dataFieldContSpan.text(dataFeildList.dataFieldText);
      /*  if(isAppendAstreck(dataFeildList)){
        	dataFieldCont.append(mandatoryStar);
        }*/
        if(dataFeildList.dataFieldExID == "OfferPromotionOptIn" ){
        	$(".phoneContactOptInContent").show();
        	$(".phoneContactOptIn").text(dataFeildList.dataFieldText);
        	if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != ""){
        		$("#phoneContactOptIn").attr({"checked":"checked"});
             }
        }else{
        	dataFieldCont.append(dataFieldContSpan);
            dataFieldText.append(dataFieldCont); 
            dataFieldText.append(displayHtmlTag);
        }
        

    } else if (dispalyInput != undefined && dispalyInput == "radio") {
    	 if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != ""){
    		 displayHtmlTag = "<input type='checkbox' checked='checked' name=" + dataFieldExIDs + "valueTarget="+dataFeildList.valueTarget+"' displayType="+dataFeildList.type+" class='radioInput'>";
    	 }else{
    		 displayHtmlTag = "<input type='checkbox' name=" + dataFieldExIDs + "valueTarget="+dataFeildList.valueTarget+"' displayType="+dataFeildList.type+" class='radioInput'>";
    	 }
        dataFieldContSpan.text(dataFeildList.dataFieldText)
        if(isAppendAstreck(dataFeildList)){
        	dataFieldCont.append(mandatoryStar);
        }
        dataFieldCont.appedn(dataFieldContSpan);
        dataFieldText.append(dataFieldCont); 
        dataFieldText.append(displayHtmlTag);
    } else if (dispalyInput != undefined && dispalyInput == "dropdown") {
    		displayHtmlTag = $("<select></select>").attr({
                "id": dataFeildList.dataFieldExID,
                "class": "dialogueDropDown",
                "name": dataFieldExIDs,
                "displayType":dataFeildList.type,
                "valueTarget":dataFeildList.valueTarget
            });
            var option = $('<option/>');
            option.attr({
                'value': ""
            }).text("Select");
            displayHtmlTag.append(option);
            if (dataFeildList.dataConstraintValueList != undefined) {
            	
                $.each(dataFeildList.dataConstraintValueList, function(l, dataConstraintValue) {
                    option = $('<option/>');
                    option.attr({
                        'value': dataConstraintValue
                    }).text(dataConstraintValue);
                    if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != "" ) {       
                        if(dataFeildList.enteredValue == dataConstraintValue){
                    		 option.attr("selected", "selected");
                    		}
                        }
                    displayHtmlTag.append(option);
                });
            }
            
            if(dataFeildList.valueTarget == "consumer.bestContactNumber"){
            	$("#bcontactContent").show();
            	$("label#bcontact .mandatory").after(dataFeildList.dataFieldText);
            	$("label#bcontact").after(displayHtmlTag);
            }else{
            	 dataFieldContSpan.text(dataFeildList.dataFieldText)
                 if(isAppendAstreck(dataFeildList)){
     	        	dataFieldCont.append(mandatoryStar);
     	        }
                 dataFieldCont.append(dataFieldContSpan);        
                 dataFieldText.append(dataFieldCont);
                 dataFieldText.append("<br/>");
                 dataFieldText.append(displayHtmlTag); 
            }
              		
    }
    
    if (dispalyInput != undefined && dispalyInput == "disclosure" || dispalyInput != undefined && dispalyInput == "inforamtion") {
    	
        if (dispalyInput == "inforamtion") {
	         if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != "" ) {     
	       		 dialogueServiceItem.append(dataFieldDisclosure.text(dataFeildList.dataFieldText));
	       	 }else{
	       		 dataFieldDisclosure.append(dataFieldinforamtion);
	             dialogueServiceItem.append(dataFieldDisclosure);
	       	 }
        } else {
        	 if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != "" ) {     
        		 dialogueServiceItem.append(dataFieldDisclosure.text(dataFeildList.dataFieldText));
        	 }else{
        		 dialogueServiceItem.append(dataFieldDisclosure.text(dataFeildList.dataFieldText));
        	 }
        }
    } else {
        dialogueServiceItem.append(dataFieldText);
    }
    if (isEnable == false) {
        dialogueServiceItem.attr({
            "id": dataFeildList.dataFieldExID
        });
    }
    return dialogueServiceItem;
}

/**
 * This function is used for remove enable and disable content when we click on checke box 
 */
function removeDisableFields(contentKeyValue,dataFieldExtId) {
    var disableJson = dataFieldMatrixMap[dataFieldExtId];
    if(contentKeyValue != undefined && dataFieldExtId != undefined  
    	    && disableJson != undefined){
    	var serviceTypeList = "";
	    if(disableJson[contentKeyValue]){
	    	   serviceTypeList = disableJson[contentKeyValue];
		 }
    if (disableJson != undefined && serviceTypeList != undefined  ) {
        
        $.each(serviceTypeList, function(k, disableItems) {
            if (dataFieldMatrixMap[disableItems.externalId] != undefined) {
                removeDisableChilds(dataFieldMatrixMap[disableItems.externalId]);
            }
            if(dataFieldExtId == "BestContactNum"){
            	 $("input#"+disableItems.externalId).remove();
            	 $(".form-row .dialogueServiceItem").remove();
            	 $(".hcontactText").text('');
            	 $("#hcontact .mandatory").hide();
            }else{
            	 $("#" + disableItems.externalId).remove();
            }
         });
    }
    }
}

/**
 * This function is used for remove child's  enable and disable content when we click on checke box 
 */
function removeDisableChilds(disableItems) {
        if (disableItems != undefined) {
        $.each(disableItems, function(k, disableChildItems) {
            $.each(disableChildItems, function(k, disableChildItems2) {
                $("#" + disableChildItems2.externalId).remove();
                if (dataFieldMatrixMap[disableChildItems2.externalId] != undefined) {
                    removeDisableChilds(dataFieldMatrixMap[disableChildItems2.externalId]);
                }

            });
        });
    }
}

/**
 * This function is used for modified dynamic field' after building dynamic account holder qualifications field's  
 */
function dynamicFieldModification(){
 $("input[name='DateOfBirth']").attr("placeholder","mm/dd/yyyy"); 
 $("input[name='DateOfBirth']").attr("autocomplete","off"); 
 $("input[name='DateOfBirth']").attr("maxlength","10"); 
 $("input[name='SSN'] ~ div").append('<a class="help-icon" href="#"> ?</a>');
 $("input[name='SSN']").attr({"maxlength":11});

}
/**
 * This function is used for remove ssn dash(s) when we submit account holder qualifications information to back end 
 */
function removeDashes(){
	if(applianceFlag){
		var primaryPhone = $('input[valuetarget="consumer.bestContactNumber" ]').val().replace(/\-/g,"");
		var mobilePhone = $('input[valuetarget="consumer.cellPhoneNumber"]').val().replace(/\-/g,"");
		$('input').each(function() {
			if($(this).attr('id').indexOf('Account')>=0 || $(this).attr('id').indexOf('account')>=0){
				var firstEnergyAccountnumber=$(this).val().replace(/\-/g,"");
				$(this).val(firstEnergyAccountnumber);
			}
			});
		$('input[valuetarget="consumer.bestContactNumber" ]').val(primaryPhone);
		$('input[valuetarget="consumer.cellPhoneNumber"]').val(primaryPhone);
	}else{
	var ssn = $("input[name='SSN']").val().replace(/\-/g,"");
	var hcontact = $("#hcontact").val().replace(/\-/g,"");
	var bcontactinstal = $("#bcontactinstal").val().replace(/\-/g,"");
	var hcontactContentInput = $("#hcontactContent input").val().replace(/\-/g,"");
	$("#hcontactContent input").val(hcontactContentInput);
	$("input[name='SSN']").val(ssn);
	$("#hcontact").val(hcontact);
	$("#bcontactinstal").val(bcontactinstal);
}
}
/**
 * Need to check 
 */
function ssnDashes(){
	var include = $(".features_list.included .feature_item")
  	for( var i = 0; i < include.length; i+=3 ) {
		  include.slice(i, i+3).wrapAll('<div class="includeFeature"></div>');
	  }
	  
  }
/**
 * This function is used for submit account holder qualifications information to back end 
 */
function applianceFlagValidations(applianceFlag)
{
	if(applianceFlag){
		//$(".dialogueContent :nth-child(2) .home-services-body:nth-child(2) .dataFieldText .dataFieldCont span:first").remove();
		$("#bottom-footer #nextStep").html("Place Order");
		$("#gobacktopage").css("display","none");
		$("#formData").attr('action',$('#contextPath').val()+'/static/submitLineItemForAppliance');
		$('.dialogueServiceItem').css({"float": "left", "margin-right": "19px" });
		$('.home-services-body .dialogueServiceItem:nth-child(3)').addClass("ccInfoemail").css({"clear": "both", "width": "100%" });
		$('.home-services-body .dialogueServiceItem:nth-child(6) .dataFieldText .dataFieldCont').addClass("cccheckbox").css({"float": "right", "margin": "2px 0px 0px 8px" });
		$('.home-services-options.row:nth-child(2) .home-services-body .dialogueServiceItem:nth-child(3)').addClass("ccaccount").css('width',"40%");
		$('input[valuetarget="consumer.bestContactNumber" ],input[valuetarget="consumer.cellPhoneNumber"]').attr('maxlength','12');
		$("#ServiceAddress").hide();
		$('input[valuetarget="consumer.bestContactNumber" ],input[valuetarget="consumer.cellPhoneNumber"]').keyup(function(){
			if (!mobileregex.test(this.value))
			{
			this.value = this.value.replace(/\D/g, '');
			}
			$(this).val($(this).val().replace(/(\d{3})\-?(\d{3})\-?(\d{4})/,'$1-$2-$3'))
			});
		$('input[valuetarget="consumer.bestContactNumber" ],input[valuetarget="consumer.cellPhoneNumber"]').keydown(function(){
			$('input').each(function() {
				if($(this).attr('id').indexOf('MobilePhoneSame')>=0 || $(this).attr('id').indexOf('mobilephonesame')>=0){
					$(this).click(function(){
						if($(this).prop("checked") == true){
							$('input[valuetarget="consumer.cellPhoneNumber"]').val($('input[valuetarget="consumer.bestContactNumber"]').val()).prop("disabled", true);
						}else{
							$('input[valuetarget="consumer.cellPhoneNumber"]').val("").prop("disabled", false);
						}
					});
					
				}
				});
		  });
		
		
		$('input[valuetarget="consumer.bestContactNumber" ]').keyup(function(){
			$('input[valuetarget="consumer.cellPhoneNumber"]').val("").prop("disabled", false);
			$('input').each(function() {
				if($(this).attr('id').indexOf('Same')>=0 || $(this).attr('id').indexOf('same')>=0){
					$(this).prop("checked", false);
					}
				});
		});
		
		$('input').each(function() {
			if($(this).attr('id').indexOf('Account')>=0 || $(this).attr('id').indexOf('account')>=0){
				$(this).attr('maxlength','14');
				$(this).keyup(function(){
					if (!mobileregex.test(this.value))
					{
					this.value = this.value.replace(/\D/g, '');
					}
					$(this).val($(this).val().replace(/(\d{3})\-?(\d{3})\-?(\d{4})/,'$1-$2-$3'))
				});
			}
			});
		registerOnloadEventsAndFunctionsAppliance();
	}else{
		registerOnloadEventsAndFunctions();
		$("#formData").attr('action',$('#contextPath').val()+'/static/updateDialogue');
	}
}

function submitAccountHolderQualificationsInfo(){
	var validateStatus = validateAccountHolderQualifications();
	if(validateStatus){
		/*if(!applianceFlag){*/
			removeDashes();	
		/*}*/
		var formData = $('#formData').serializeObject();
		//console.log("formData   ::::::::: "+JSON.stringify(formData));
		$("#qualificationSerializeJSON").val(JSON.stringify(formData));
		displayCKOLoader(false);
		if(formData!=null){
	        var fd=formData;
	        Array.prototype.removeByValue = function(val) {
	            for(var i=0; i<this.length; i++) {
	                if(this[i].name == val) {
	                    this.splice(i, 1);
	                    break;
	                }
	            }
	        };
	        fd.removeByValue('iData');
	        var accountHolderQualifications={"accountHolderQualifications":JSON.stringify(fd)};
	        setDataLayer(JSON.stringify(accountHolderQualifications));
	    }
		$("#formData").submit();
	}else{
		$(".validCont").show();
		$("#error_msg").show();
		setDynamicHeight();
		return false;
	}
}	

/**
 * This function is used for build required validation content
 * @param requiredObj -- current object
 * @param validationMsg -- validation message
 */
function required(requiredObj,validationMsg){
	var error_tooltip = $("<div></div>").attr({"class":"error_tooltip"});
	var glyphicon_triangle_left = $("<span></span>").attr({"class":"glyphicon glyphicon-triangle-left"});
	var error_tooltip_lbl;
	if(jQuery.type(validationMsg) === "string"){
		 error_tooltip_lbl = $("<span></span>").attr({"class":"error_tooltip_lbl"}).text(validationMsg);
	}else{
		 error_tooltip_lbl = $("<span></span>").attr({"class":"error_tooltip_lbl"});
		 error_tooltip_lbl.append(validationMsg);
	}
	
	error_tooltip.append(glyphicon_triangle_left);
	error_tooltip.append(error_tooltip_lbl);
	requiredObj.after(error_tooltip);
}

/**
 * This function is used for validate account holder qualification fields
 * @return boolean -- isValid or isInvalid
 */
var validatoinArr = [];
function validateAccountHolderQualifications(){
	validatoinArr = [];
    var isValid = true;
    var serviceType = true;
    $(".mandatoryMessage").removeClass();
    $(".home-services-body.error").removeClass("error");
    $(".error_tooltip").remove();
    
	$(".invalidMsg").text("");
	$("#lname,#DLNumber").parent().css("display","");
	$("#DLNumber").closest(".dataFieldCont").css("width","98%");
	
    if ($("input,select").length > 0) {
    	$(".mandatory").parent().parent().find("input,select").each(function(index){
    		if($(this).is(":visible")){
    		  var fieldValue = $(this).val();
    		  var middleName = $(this).attr("id");
    		  var serviceType = $(this).attr("class");
    		  if(middleName == undefined){
    			  middleName = ""
    		  }
    		var date =new Date();
    		  if(fieldValue != ""  && fieldValue == date.getFullYear() && serviceType == "expirationDate" ){
    			  var currentMonth=date.getMonth()+1;
    			  var expMonth =$(".monthContent").find(".expirationDate").val();
    			  $(".expirationDate").each(function(){
    				  var expireMonthYear=$(this).val();
    				  if(expireMonthYear < currentMonth && expireMonthYear.length == 2 && expireMonthYear==expMonth){
    					  $("#lname,#DLNumber").parent().css("display","block");
    		      		    $(this).closest(".home-services-body").addClass("error");
    		      		    required($(".expirationDate:eq(1)"),"Date expired");
    		      		    $(this).parent().find(".mandatory").addClass(".mandatoryMessage");
    		      		    isValid = false;  
    				  }
    			  });
      		  }
    		  if(fieldValue == "" && middleName != "mi" && serviceType != "newOrTransferCustomer" ){
    		    $("#lname,#DLNumber").parent().css("display","block");
    		    $(this).closest(".home-services-body").addClass("error");
    		    required($(this),"required");
    		    $(this).parent().find(".mandatory").addClass(".mandatoryMessage");
    		    isValid = false;  
    		  }else if($(this).attr("type") == "checkbox" && !($(this).is(":checked"))){
    			  required($(this),"required");
    			  isValid = false;
    		  }
    		}
    		});  
    	if($(".yearContent").find(".error_tooltip")){
    		$(".yearContent").find(".error_tooltip").css("margin","7px 0 0");
    	}
    	if(applianceFlag){
       	 var primaryPhoneNumber=$('input[valuetarget="consumer.bestContactNumber" ]').val().trim();
       	 var mobilePhoneNumber=$('input[valuetarget="consumer.cellPhoneNumber"]').val().trim();
        var applianceEmailvalue = $('input[valuetarget="consumer.homeEmail.emailAddressValueType"]').val();
        if(applianceEmailvalue != "" && applianceEmailvalue != undefined && validateEmail(applianceEmailvalue)){
       	 required($('input[valuetarget="consumer.homeEmail.emailAddressValueType"]'),$("<span></span>").addClass("invalidMsg").text("Invalid email address"))
	          	 isValid = false;
	          }
   	 var firstEnergyAccountnumber=$('input[valuetarget="consumer.contractAccountNumber"]').val().trim();
        $('input').each(function() {
			if($(this).attr('id').indexOf('Confirmation')>=0 || $(this).attr('id').indexOf('confirmation')>=0){
				if($(this).prop("checked") == false){
					 required($(this), $("<span></span>").addClass("invalidMsg").text("required"));
			       	 isValid = false;
				}
			}
			});
        if(typeof(primaryPhoneNumber) != 'undefined' && primaryPhoneNumber!="" && (primaryPhoneNumber.length > 12|| primaryPhoneNumber.length < 12 )){
       	 	required($('input[valuetarget="consumer.bestContactNumber" ]'),$("<span></span>").addClass("invalidMsg").text("Invalid phone number"));
        	 isValid = false;
        }
        if(typeof(mobilePhoneNumber) != 'undefined' && mobilePhoneNumber!="" && (mobilePhoneNumber.length > 12 || mobilePhoneNumber.length < 12)){
        	required($('input[valuetarget="consumer.cellPhoneNumber"]'),$("<span></span>").addClass("invalidMsg").text("Invalid phone number"));
        	 isValid = false;
        }
        if(firstEnergyAccountnumber != "" && firstEnergyAccountnumber != undefined &&( firstEnergyAccountnumber.length >14 || firstEnergyAccountnumber.length < 14)){
       	 	required($('input[valuetarget="consumer.contractAccountNumber"]'),$("<span></span>").addClass("invalidMsg").text("Invalid account number"));
        	 isValid = false;
        }else if(firstEnergyAccountnumber != "" && firstEnergyAccountnumber != undefined && firstEnergyAccountnumber!=null && !(firstEnergyAccountnumber.substring(0,2)=="10" || firstEnergyAccountnumber.substring(0, 2)=="11")){
        	 required($('input[valuetarget="consumer.contractAccountNumber"]'),$("<span></span>").addClass("invalidMsg").text("Invalid account number"));
        	 isValid = false;
        }
        
        }else{
    	 var hcontact = $('#hcontact').val().trim(); 
         var bcontactinstal = $('#bcontactinstal').val().trim(); 
         var ssn = $("input[name='SSN']").val();

         if($("input[name='SSN']").attr("maskedvalue") != undefined){
        	ssn = $("input[name='SSN']").attr("maskedvalue").trim(); 
         }
        // var dob=$("input[name='DateOfBirth']").val().trim();
         var dob = "";
         if($("input[name='DateOfBirth']").val() != undefined){
        	 dob = dob.trim();
         }
         if($("input[name='DateOfBirth']").attr("maskedvalue") != undefined){
        	 dob = $("input[name='DateOfBirth']").attr("maskedvalue").trim();
         }
         var emailvalue = $("input[name='emailAddress']").val();
        /* if(emailvalue == "" && emailvalue != undefined){
	          	$("input[name='emailAddress']").after($("<span></span>").addClass("invalidMsg"));
	          	 isValid = false;
	          }*/
         if(emailvalue != "" && emailvalue != undefined && validateEmail(emailvalue)){
        	 required($("input[name='emailAddress']"),$("<span></span>").addClass("invalidMsg").text("Invalid email address"))
	          	 isValid = false;
	          }
         if((hcontact != "" && hcontact != undefined ) && !(hcontact.length > 12) && hcontact.length < 12 ){
        	 required($("#hcontact"),$("<span></span>").addClass("invalidMsg").text("Invalid phone number"))
         	 isValid = false;
         }
         if((bcontactinstal != "" && bcontactinstal != undefined ) && !(bcontactinstal.length > 12) && bcontactinstal.length < 12){
        	 required($('#bcontactinstal'),$("<span></span>").addClass("invalidMsg").text("Invalid phone number"))
         	 isValid = false;
         }
         if((ssn != "" && ssn != undefined ) && !(ssn.length > 11) && ssn.length < 11){
        	 required($("input[name='SSN']"),$("<span></span>").addClass("invalidMsg").text("Invalid ssn number"));
         	isValid = false;
         }
         if((dob != "" && dob != undefined ) && !(dob.length > 10) && dob.length < 10){
        	 required($("input[name='DateOfBirth']"),$("<span></span>").addClass("invalidMsg").text("Invalid Date of Birth"));
         	isValid = false;
         }else if(!isDate(dob) && dob != ""){
        	 required($("input[name='DateOfBirth']"),$("<span></span>").addClass("invalidMsg").text("Invalid Date of Birth"));
        	 	isValid = false;
         }else if(dob.length==10){
         	 var dataArray = dob.split("/");
              var enteredyear = dataArray[2];
              var enteredMonth = dataArray[0];
              var enteredDay = dataArray[1];
              var d = new Date();
              var currentyr = new Date().getFullYear();
              var currentMonth = d.getMonth() + 1;
              var currentDay = d.getDate() - 1;
             
              var endyrs = currentyr - 18;

              enteredyear = parseInt(enteredyear);
              enteredDay = parseInt(enteredDay);
              enteredMonth = parseInt(enteredMonth);
              if (enteredyear <= endyrs) {
                if ((enteredyear == endyrs) && enteredMonth == currentMonth && enteredDay >= (currentDay + 1)) {
                	required($("input[name='DateOfBirth']"),$("<span></span>").addClass("invalidMsg").text("Invalid Date of Birth"));
                  	isValid = false;
                 } else if ((enteredyear == endyrs) && enteredMonth > currentMonth) {
                	 required($("input[name='DateOfBirth']"),$("<span></span>").addClass("invalidMsg").text("Invalid Date of Birth"));
                   	isValid = false;
                 }
              }else {
            	  required($("input[name='DateOfBirth']"),$("<span></span>").addClass("invalidMsg").text("Invalid Date of Birth"));
               	isValid = false;
              }
         }
   
         if($('.validateCCClass').length > 0){
    		 isValid = false;
         }
       /*  if(cardlength != undefined && cardlength == ''){
        	 $('.validateCCClass').remove();
         }*/
     }
    }
     return isValid;
}
/**
 * This function is used for remove special characters
 * @param stringValue -- value
 * 
 */
function removeSpecialCharacters(stringValue){
	return stringValue.replace(/[\*?:\^\'\!]/g, '').split(' ').join(' ')
}

/**
 * This function is used for populate dashe(s) between phone number's
 * @param event -- current event
 * 
 */
function prepopulateDashes(event) {
    if (event.keyCode != 8 && event.keyCode != 46 ) {
        if (this.value.length == 3) {
            this.value = this.value + "-";
        } else if (this.value.length == 7) {
            this.value = this.value + "-";
        } else {
            this.value = this.value.replace(/(\d{3})\-?(\d{3})\-?(\d{4})/, '$1-$2-$3');
        }
    }
}

/**
 * This function is used for validate entered value is number or character
 * @param evt -- current event
 */
/*function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46 && charCode != 39) {
        return false;
    }
    return true;
}*/

/**
 * This function is used for populate dashe(s) between ssn
 * @param event -- current event
 */
 function prepopulateSsnDashes(event){
    if (event.keyCode != 8 && event.keyCode != 46 ) {
        if (this.value.length == 3) {
            this.value = this.value + "-";
        } else if (this.value.length == 6) {
            this.value = this.value + "-";
        } else {
            this.value = this.value.replace(/(\d{3})\-?(\d{2})\-?(\d{4})/, '$1-$2-$3');
        }
    }
 }
 /**
  * This function is used for populate previous inforamtion(click back button) dashe(s) between ssn and phone number's
  * @param value -- current event
  */
 function prepopulatePreviousInfoDashes(previousPhoneOrSsnNumber) {
	 
	 if(previousPhoneOrSsnNumber.length == 9 ){
		 previousPhoneOrSsnNumber = previousPhoneOrSsnNumber.replace(/(\d{3})\-?(\d{2})\-?(\d{4})/, '$1-$2-$3');
	 }else if(previousPhoneOrSsnNumber.length == 10){
		 previousPhoneOrSsnNumber = previousPhoneOrSsnNumber.replace(/(\d{3})\-?(\d{3})\-?(\d{4})/, '$1-$2-$3');
	 }
	     return previousPhoneOrSsnNumber;
	}
 /**
  * This function is used for populate dashe(s) between date of birth 
  * @param event -- current event
  */
 function prepopulateDobDashes(event){
	    if (event.keyCode != 8 && event.keyCode != 46 ) {
	        if (this.value.length == 2) {
	            this.value = this.value + "/";
	        } else if (this.value.length == 5) {
	            this.value = this.value + "/";
	        } else {
	        	var dob=this.value.replace("/","");
	            this.value = this.value.replace(/(\d{2})\/?(\d{2})\/?(\d{4})/, '$1/$2/$3');
	        }
	    }
	 }
 /**
  * This function is used for automatically populate city and state when enter zip code
  * @param dob -- date of birth value
  */
 function isDate(dob) {
	    if (dob != "") {
	        var objDate, // date object initialized from the ExpiryDate string
	            mSeconds, // ExpiryDate in milliseconds
	            day, // day
	            month, // month
	            year; // year
	        // date length should be 10 characters (no more no less)
	        if (dob.length !== 10) {
	            return false;
	        }
	        // third and sixth character should be '/'
	        if (dob.substring(2, 3) != '/' || dob.substring(5, 6) != '/') {
	            return false;
	        }
	        // extract month, day and year from the ExpiryDate (expected format is mm/dd/yyyy)
	        // subtraction will cast variables to integer implicitly (needed
	        // for !== comparing)
	        var arriTxtDob = dob.split('/');
	        // because months in JS start from 0
	        month = parseInt(arriTxtDob[0], 10) - 1;
	        day = parseInt(arriTxtDob[1], 10);
	        year = parseInt(arriTxtDob[2], 10);
	        // convert ExpiryDate to milliseconds
	        mSeconds = (new Date(year, month, day)).getTime();
	        // initialize Date() object from calculated milliseconds
	        objDate = new Date();
	        objDate.setTime(mSeconds);
	        // compare input date and parts from Date() object
	        // if difference exists then date isn't valid
	        if ((objDate.getFullYear()) !== year || (objDate.getMonth()) !== month || (objDate.getDate()) !== day) {
	            return false;
	        }
	        // otherwise return true
	        return true;
	    } else {
	        return false;
	    }
	}
 
 
 /**
  * This function is used for remove asstrick symbols if not mandatory
  * @param dataFeildList -- data field list json
  * 
  */
 function isAppendAstreck(dataFeildList){
     if(($.inArray( dataFeildList.dataFieldExID, removeAsstrickExtList ) > 0 || $.inArray( dataFeildList.valueTarget, removeAsstrickExtList ) > 0)){
     	return false;
     }else{
    	 return true;
     }
 }
 /**
  * This function is used for validate email rules
  * @param emailvalue -- entered email value
  */
 function validateEmail(emailvalue){
     var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-])+(\.[A-Za-z]{2,4}){1,2}$/;
     if (reg.test(emailvalue)) {
    	 return false;
    	 }
     else{
    	 return true;
     }
}
 /**
  * This function is used for set maked value's to maskedValue attribute
  * @Attribute Name : maskedValue
  * Like DOB,Credit card/Debit card and SSN 
  * 
  */
function populateMaskedValues(){
	 var maskedVal =  $(this).attr("maskedValue");
	 var ssnValue = $(this).val();
	  if(ssnValue != undefined && ssnValue != "" && maskedVal != undefined && maskedVal != ""){
	    $(this).val(maskedVal);
	    
	  }
	};
/**
 * This function is used for masking sensitive information 
 * Like DOB,Credit card/Debit card and SSN 
 * 
 */
function maskPaymentInformation(){
	  var validationType = $(this).attr("validation");
	  var  value = $(this).val();
	  if((validationType != undefined && validationType != "undefined" && value != "" && value != undefined) && (validationType == "SSN" || validationType == "credit" || validationType == "debit" || validationType == "Date")){
	    var roundVal = value.trim().length;
		var regExp = new RegExp("^.{"+roundVal+"}");
		var astrekStr = "";
		for ( var int = 0; int < roundVal; int++) {
			astrekStr = astrekStr+"*";
		}
	    if($(this).attr("maskedValue") == undefined){
	      $(this).attr({"maskedValue":value});
	      
	    }else if($(this).attr("maskedValue") != undefined){
	      $(this).attr("maskedValue",value);
	    }
	    $(this).val(value.replace(regExp,astrekStr))
	  }
	}
function validateCreditCardNumber(){
	$('.validateCCClass').remove();
	var ccMinLength = {"ccLength":[{"ccType":"MasterCard","length":16},
	                               {"ccType":"Visa","length":16},
	                               {"ccType":"AmericanExpress","length":15},
	                               {"ccType":"Discover","length":16}]};
	var creditCardNumberStartAndEnd = {"creditCardBound" :[{"ccTypes":"AmericanExpress1", "upperBound":"3499", "lowerBound":"3400"},
	                                                       {"ccTypes":"AmericanExpress2", "upperBound":"3799", "lowerBound":"3700"},
	                                                       {"ccTypes":"Visa", "upperBound":"4999", "lowerBound":"4000"},
	                                                       {"ccTypes":"MasterCard", "upperBound":"5599", "lowerBound":"5100"},
	                                                       {"ccTypes":"Discover", "upperBound":"6011", "lowerBound":""}]};
	var creditCardList = ["Visa", "MasterCard", "Discover", "AmericanExpress"];
	
	var creditCardType = $("select[valuetarget='consumerFinancialInfo.cardType'] option:selected").val();//$(".ccType").val();
	var id = $(this).attr('id');
	var selValue =  $("input[valuetarget='consumerFinancialInfo.creditCard.number']").val();
	var bound = selValue.substring(0, 4);
	var ccLength = selValue.length;
	if(creditCardType != null && creditCardType.trim().length > 0){
		for(var i = 0; i < creditCardList.length; i++){
			if(creditCardList[i] == creditCardType){
				var isCreditCardValid = false;
				$(ccMinLength.ccLength).each(function(){
					if(this.ccType.indexOf(creditCardType) >= 0){
						if(this.length == ccLength){
							$('#'+id +'_validateCCClass').each(function(){
								if($(this).text() != null){
									$(this).remove();
								}
							});
							isCreditCardValid = true;
						}
						else{
							$("#"+id+"_validateCCClass").each(function(){
								if($(this).text() != null){
									$(this).remove();
								}
							});
							$('input[id='+id+']').after("<span id='"+id+"_validateCCClass' class='validateCCClass error' " +
									"style='background-color:yellow;'><font color='red'>"+this.ccType.toUpperCase()+" card incorrect length </span>");
							isCreditCardValid = false;
						}
					}
				});

				if(isCreditCardValid){
					$(creditCardNumberStartAndEnd.creditCardBound).each(function(){
						if(this.ccTypes == creditCardType){
							var upperBound = this.upperBound;
							var lowerBound = this.lowerBound;
							if(bound >= lowerBound && bound <= upperBound){

								var data = { "ccNumber" : selValue};
								$.ajax({
									type: 'POST',
									data: data,
									async:false,
									url: $('#contextPath').val()+"/static/updateDialogue/isValidCCNumber",
									success: function(data){
									var responseJSON = JSON.parse(data);
									if(responseJSON.error != null && responseJSON.error != undefined){
										$("#"+id +'_validateCCClass').each(function(){
											if($(this).text() != null){
												$(this).remove();
											}
										});

										$('input[id='+id+']').after("<span id='"+id +"_validateCCClass' class='validateCCClass error' style='background-color:yellow;' ><font color='red'>"+responseJSON.error+"</font></span>");			
										return false;
									}else{
										$('#'+id +'_validateCCClass').each(function(){
											if($(this).text() != null){
												$(this).remove();
											}
										});
										return true;
									}
								},
								error:function(status){
								}
								});
							
							}else{
								$("#"+id+"_validateCCClass").each(function(){
									if($(this).text() != null){
										$(this).remove();
									}
								});
								$('input[id='+id+']').after("<span id='"+id+"_validateCCClass' class='validateCCClass error' " +
										"style='background-color:yellow;'><font color='red'>"+this.ccTypes.toUpperCase()+" card number is not valid</span>");
								return false;
							}
						}
					});
				}
				else{
					return false;
				}
			}
		}
	}
	else{
		$('input[id='+id+']').after("<span id='"+id+"_validateCCClass' class='validateCCClass error' " +
		"style='background-color:yellow;'><font color='red'>select credit card type</span>");
		return false;
	}
}
