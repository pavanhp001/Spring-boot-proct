   var panelCount = 1;   
   var enableArr = [];
   var dataFieldMatrixMap ;
   var isBackButtonInfo = true;
   var selectCount = 0; 
   var arrOfRecievers = [];
   var tvFeatureExtIds = ["HD_RCVR","DIG_RCVR","CHILD_REC","ENHANCED_DVR","HD_DVR","DIGITAL_ADAPTER"];
   var selectedPriceTierArr  ={};
   var enableBackBtnInfoArr = [];
   var moreTvsDispalyInputValueArr = [];
   var selectedNoOfTvs = 0;
   var MultiRMDVR = "";
$(document).ready(function(){
	var collapseCount = 1;  
	var collapseValue = 1;
	var countValue = 0;
	var contextPath  = $(".contextPath").val();
	$(".providerLogo").attr("src", contextPath+"/image/"+productVO.providerID+".jpg");
	$(".providerName").text(productVO.productName);
	var parsedPromotionJson = productVO.promotionVOList;
	var featureJson = productVO.dialogueTypeList;
 	dataFieldMatrixMap = productVO.dataFieldMatrixMap;
 	$(".promotionContent1").hide();	
	$(".promotionContent").hide();
	$("#accordion #panel1").hide();
 	if(parsedPromotionJson.length > 0){
 		//Build Promotions from parsed promotion json
 	    for(var i=0 ; i < parsedPromotionJson.length ; i++ ){
 	          var externalId =  parsedPromotionJson[i].promotionObj.externalId;
 	          var shortDescription = parsedPromotionJson[i].promotionObj.shortDescription;
 	          var conditions = parsedPromotionJson[i].promotionObj.conditions;
 	          var displayInputType =  parsedPromotionJson[i].displayInputType;
 	          var displayInputValue =  parsedPromotionJson[i].displayInputValue;
 	          var qualification = parsedPromotionJson[i].promotionObj.qualification;
 	          if (parsedPromotionJson[i].baseMonthlyPromo){
 	        	 $(".promotionContent1").show();	
 	        	$("#accordion #panel1").show();
 	        	  buildRadioPromotions(externalId,shortDescription,qualification,conditions,displayInputType, displayInputValue, parsedPromotionJson[i].isChecked);
 	          }else{
 	        	 $(".promotionContent").show();
 	        	$("#accordion #panel1").show();
 	        	  buildCheckboxPromotions(externalId,shortDescription,qualification,conditions,displayInputType, displayInputValue,  parsedPromotionJson[i].isChecked);     
 	          }
 	      }
 	}else{
 		$(".promotionContent1").parent().hide();	
 		$(".promotionContent").parent().hide();
 		$("#accordion #panel1").hide();
 		
 	}
 	  $(".detailsAndTermsLink,.termsAndConditionLink").click(showDetailsAndTerms);
	
	
	//Build features 
	for(var i=0; i < featureJson.length ; i++ ) {
	  var dataGroup = featureJson[i].dataGroupList;
	  var title = featureJson[i].title;
	  var dataFeildList = featureJson[i].dataGroupList
	  featureHtmlBulid(title,dataFeildList);
	 }

		
		$('.panel').on('show.bs.collapse', function (e) {
			$("#"+e.currentTarget.id).addClass("accord-selected");
			$(e.target).prev('.panel-heading').find("span.indicator").addClass('glyphicon-triangle-bottom');
			$(e.target).prev('.panel-heading').find("span.leftindicator").toggleClass('glyphicon-plus glyphicon-minus');
			setDynamicHeight();
			setCKOActions();
		})
		$('.panel').on('hide.bs.collapse', function (e) {
			$("#"+e.currentTarget.id).removeClass("accord-selected");
			$(e.target).prev('.panel-heading').find("span.indicator").removeClass('glyphicon-triangle-bottom');
			$(e.target).prev('.panel-heading').find("span.leftindicator").toggleClass('glyphicon-minus glyphicon-plus');
			setDynamicHeight();
			setCKOActions();
		})
		$("input[name='teleno']").on("change", function () {
			var teleno = this.id;
			if(teleno == "new_no"){
				$("#new_no_cont").show();
				$("#port_no_cont").hide();
			}else{
				$("#new_no_cont").hide();
				$("#port_no_cont").show();
			}
		});
        $("select").change(dropDownChnageBuild);
         $("input[type='checkbox']").click(buildCheckedContent);
         console.log("selectedNoOfTvs ::: >> "+selectedNoOfTvs);

         if(isBackButtonInfo && arrOfRecievers.length > 0){
        	 buildTvContent(1);
        	 buildTvsTooltipInfo();
         }else if(selectedNoOfTvs > 0){
        	 var receiverContent = $("<div></div>").addClass("receiverContent");
        	 var tooltipContent = $("<div></div>").addClass("tooltipContent");
        	 receiverContent.append(tooltipContent);
        	 $("div.feature_item:last").after(receiverContent);
        	 if(selectedNoOfTvs < 5  ){
        		 for(var i = 0; i < selectedNoOfTvs; i++){
            		 addTvContent(i + 1);
            		 buildConfigureTvs(i +1);
            		 console.log("Back Info Arr :: "+JSON.stringify(arrOfRecievers))
            		 if(MultiRMDVR != '' && $("input[name="+MultiRMDVR+"]") != undefined && $("input[name="+MultiRMDVR+"]").is(":checked")){
         	        	buildCheckedPlaybackReceivers();
         	        }
            	 }
        	 }else if(selectedNoOfTvs > 4 ){
        		 console.log("selectedNoOfTvs > 4 :::::::::::::::::: "+selectedNoOfTvs);
        		 $("#moreOutletActivations").addClass("selectedTv");
        		 selectTv() ;
        		 for(var i = 0; i < selectedNoOfTvs; i++){
            		 addTvContent(i + 1);
            		 buildConfigureTvs(i +1);
            		 console.log("Back Info Arr :: "+JSON.stringify(arrOfRecievers))
            		 if(MultiRMDVR != '' && $("input[name="+MultiRMDVR+"]") != undefined && $("input[name="+MultiRMDVR+"]").is(":checked")){
         	        	buildCheckedPlaybackReceivers();
         	        }
            	 }
        	 }
        	
        	 buildPreviousCheckedContent();
        	 previousSeletedRecivers();
        	 buildTvsTooltipInfo();
        	 console.log("planSummaryFeaturesJSONArray ::: planSummaryFeaturesJSONArray ::: === > "+planSummaryFeaturesJSONArray);
         }
         $(".listing_type.LISTING_OPTION").on("click",function () {
     		var serviceVal =  $(this).attr("value");
     		var previous = $(this).data("previous");
     		if(previous == undefined){
     			previous = $(".listing_type.LISTING_OPTION.active").attr("value");
     		}
     		$("#" + $(this).attr("name") + " .feature_item").remove();
     		dropDownChnage(serviceVal, previous, $(this));
     		$(".listing_type.LISTING_OPTION").removeClass("active");
     		$(this).addClass("active");
     	});
         $(".listing_type.MODEM_OPTION").on("click",function () {
        	if ($(".listing_type.MODEM_OPTION").hasClass("active")) {
        		$(".listing_type.MODEM_OPTION").removeClass("active");
      		}
        	
        	if (!$(".listing_type.MODEM_OPTION").hasClass("active")) {
      			$(this).addClass("active");
      		}
        	
      		$("#" + $(this).attr("name") + " .feature_item").remove();
      		var serviceVal =  $(this).attr("value");
     		var previous = $(this).data("previous");
     		dropDownChnage(serviceVal, previous, $(this));
     		
      	});
         
        /******************** All accordions are expanding if required field is exist *********** */
     	$(".panel:visible:first").find(".panel-collapse:first").addClass("in");
     	$(".panel:visible:first").find("span.indicator").addClass('glyphicon-triangle-bottom'); 
     	$(".panel:visible:first").find("span.leftindicator").removeClass('glyphicon-plus');
     	$(".panel:visible:first").find("span.leftindicator").addClass('glyphicon-minus');
     	
     	/********************LISTING_OPTION, MODEM_OPTION and OUTLET ACTIVATION TAB press trigger click event automatically *********** */
     	$('.LISTING_OPTION,.listing_type.MODEM_OPTION, a.tv_items').on('keypress', function(e) {
     	    var tag = e.target.tagName.toLowerCase();
     	    if ( (e.which === 13) && (tag == 'div' || tag == 'a') ){
     	    	$(this).trigger("click");
     	    }
     	});
   
       	/*********************** append "/month" string to modem options price ********************/
     	$(".listing_type.MODEM_OPTION .selectedValue").each(function(){
     		var modemPrice = $(this).text();
     		if(modemPrice != undefined && modemPrice != "" && parseFloat(modemPrice.replace(/[$]/g, '')) > 0){
     			var currentText = modemPrice + "/month"  
       		  	$(this).text(currentText)
     		}
     	})
     	
     	/***********Start*************** Move televisions question content before option tv content***********/
     	var questionContent = $(".tvBoxes").clone();
		$(".tvBoxes").remove();
		$(".tv_item").before(questionContent);	
		/***********************************************End**************************************************/
		
     	addToPlanInfo();
     	 $('input').attr('autocomplete','off');
     	$("#iData").click(changeOrientation)
    	$(window).on("orientationchange",changeOrientation);
     	appendEvents("input",setDynamicHeight,"click");
     	appendEvents("select",setDynamicHeight,"change");
     	appendEvents(".listing_type",setDynamicHeight,"click");

     	moveListingOptionChilds();
     	
	});

/**
 * ************************
 * This function is used for show and hide promotion details and term & conditions
 * ************************
 */
function showDetailsAndTerms(){
	if($(this).find(".glyphicon").hasClass("glyphicon-plus")){
		$(this).find(".glyphicon").removeClass("glyphicon-plus");
		$(this).find(".glyphicon").addClass("glyphicon-minus");
	}else if($(this).find(".glyphicon").hasClass("glyphicon-minus")){
		$(this).find(".glyphicon").removeClass("glyphicon-minus");
		$(this).find(".glyphicon").addClass("glyphicon-plus");
	}
	
	if($(this).attr("class") == "detailsAndTermsLink"){
		if($(this).parent().find(".detailsAndTerms").is(":hidden")){
			$(this).parent().find(".detailsAndTerms").show();
		}else{
			$(this).parent().find(".detailsAndTerms").hide();
		}
		
	}else if($(this).attr("class") == "termsAndConditionLink"){
		if($(this).parent().find("div.termsAndCondition").is(":hidden")){
			$(this).parent().find("div.termsAndCondition").show();
			
		}else{
			$(this).parent().find("div.termsAndCondition").hide();
		}
	}
	setDynamicHeight();
}
/*************************************************************************************************
 * 
 * ****************** This function is used for  register onload events and functions ************
 * 
 * ************************************************************************************************/
function registerOnloadEventsAndFunctions() {
	setDynamicHeight();
    $('#accordion').on('hidden.bs.collapse', setDynamicHeight);
    $('#accordion').on('shown.bs.collapse', setDynamicHeight);
    $("#bottom-footer .next_step span").click(submitProuductInfo);
    $("#customizations .next_step span").click(submitProuductInfo);
    $("input").click(addToPlanInfo);
    $(".listing_type, .tv_items").click(addToPlanInfo);
    $("select").change(addToPlanInfo);
    wrapIncludedFeatures();
}
/***********************************************************************************************
 * 
 * ***************** This function is used for submit installation information to back end *****
 * 
 * **********************************************************************************************/
function submitProuductInfo() {
    var validateStatus = submitProductValidations();
    if (validateStatus) {
    	displayCKOLoader(false);
        var formData = $('#formData').serializeObject();
        //console.log("formData   ::::::::: " + JSON.stringify(formData));
        $("#productVOJSON").val(JSON.stringify(formData));
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
            var productCustomization={"productCustomization":JSON.stringify(fd)};
            setDataLayer(JSON.stringify(productCustomization));
         }
        $("#formData").submit();
    } else {
    	expandAccordion();
        $(".validCont").show();
        $("#error_msg").show();
        setDynamicHeight();
        return false;
    }
}

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

/************************************************************************************************
 * ***************** This function is used for empty validations  *******************************
 * **********************************************************************************************/
var validatoinArr = [];
function submitProductValidations() {
	validatoinArr = [];
    var isValid = true;
    $(".receiverContent .error_tooltip .glyphicon-triangle-left").css("float","left");
	$(".receiverContent .tvOptionsCont").css("width","24%");
    $(".mandatoryMessage").removeClass();
    $(".home-services-body.error").removeClass("error");
    $(".receiverContent").removeClass("error");
    $(".error_tooltip").remove();
    var LISTING_OPTION = $(".LISTING_OPTION.active").length
    var MODEM_OPTION = $(".MODEM_OPTION.active").length
    if($(".LISTING_OPTION").length > 0 && LISTING_OPTION != undefined && LISTING_OPTION == 0){
    	$(".LISTING_OPTION").closest(".home-services-body").addClass("error");
    	 required($(".LISTING_OPTION:last"),"required");
    	 isValid = false;
    }
   /* if($("#TWCListingDisplayinputID").length >= 1){
    	var twcinput = $.trim($('#TWCListingDisplayinputID').val());
    	if(twcinput === ''){
        	$("#TWCListingDisplayinputID").closest(".home-services-body").addClass("error");
       	 required($("#TWCListingDisplayinputID:last"),"required");
       	 isValid = false;
    	   }
    }*/
    if($(".MODEM_OPTION").length > 0 && MODEM_OPTION != undefined && MODEM_OPTION == 0){
    	$(".MODEM_OPTION").closest(".home-services-body").addClass("error");
   	    required($(".MODEM_OPTION:last"),"required");
   	    isValid = false;
   }
    if($("#MoreTvsId").val() != undefined && $("#MoreTvsId").val() == "" ){
    	$(".receiverContent").addClass("error");
    	required($("#MoreTvsId"),"required");
    	isValid = false;
    }
    if($(".featureDropDown").val() != undefined && $(".featureDropDown").val() == "" ){
        required($(".featureDropDown"),"required");
        isValid = false;
       }
    
$(".mandatory").parent().parent().find('input').each(function(){
	    var currObj = $(this);
    	var type = currObj.attr("type");
    	if((type == "text") && (currObj.val() == undefined || currObj.val() == "" )){
    		 required(currObj,"required");
    	        isValid = false;
    	}
    })
    if($(".tv_items.OUTLET_ACTIVATION").length > 0 && ($(".tv_items.OUTLET_ACTIVATION.selectedTv").attr("value") != "More")&& ($(".tv_items.OUTLET_ACTIVATION.selectedTv").attr("value") > $("input[name^=receivers_]:checked").length) ){
    	$(".receiverContent").addClass("error");
    	required($(".tvOptionsCont").parent().find(".noOfTvOptionsMessage"),"required");
    	$(".receiverContent .error_tooltip .glyphicon-triangle-left").css("float","none");
    	$(".receiverContent .tvOptionsCont").css("width","45%");
       	$(".tvOptionsCont").each(function(index){
       		console.log("index index :: index :: "+index);
       		var tvIndex = index + 1
       		var isTvsChecked = "receivers_"+tvIndex; 
       		console.log("isTvsChecked isTvsChecked :: isTvsChecked :: "+isTvsChecked);
       		if($("input[name="+isTvsChecked+"]:checked").length > 0){
       			$(this).find(".error_tooltip").remove();
       		}
        	});
      	 isValid = false;
    	}else if($(".tv_items.OUTLET_ACTIVATION").length > 0 && ($(".tv_items.OUTLET_ACTIVATION.selectedTv").attr("value") == "More")&& ($(".moreTvs").val() > $("input[name^=receivers_]:checked").length)){
    		$(".receiverContent").addClass("error");
    		required($(".tvOptionsCont").parent().find(".noOfTvOptionsMessage"),"required");
        	$(".receiverContent .error_tooltip .glyphicon-triangle-left").css("float","");
        	$(".receiverContent .tvOptionsCont").css("width","45%");
         	$(".tvOptionsCont").each(function(index){
           		console.log("index index :: index :: "+index);
           		var tvIndex = index + 1
           		var isTvsChecked = "receivers_"+tvIndex; 
           		console.log("isTvsChecked isTvsChecked :: isTvsChecked :: "+isTvsChecked);
           		if($("input[name="+isTvsChecked+"]:checked").length > 0){
           			$(this).find(".error_tooltip").remove();
           		}
            	});
         	 isValid = false;
    	}
    return isValid;
}

/*************************************************************************************************
 * ***************** This function is used for remove special characters  ************************
 * ************************************************************************************************/
function removeSpecialCharacters(stringValue){
	return stringValue.replace(/[\*#?:\^\'\!]/g, '').split(' ').join(' ')
}

/************************************************************************************************
 * ***************** This function is used for building radio button promotions  *******************************
 * @param externalId -- 
 * @param shortDescription -- 
 * @param qualification --  
 * @param conditions -- 
 * @param displayInputType -- 
 * @param promotionInputValue -- 
 * @param isChecked -- 
 * **********************************************************************************************/
function buildRadioPromotions(externalId,shortDescription,qualification,conditions,displayInputType, promotionInputValue,isChecked){
    var promotionContent = $(".promotionContent1"); 
    var promotionRadioInfo = $("<div></div>").attr({"class":"promotionRadioInfo"});   
    var promotionCheckInfo = $("<div></div>").attr({"class":"promotionCheckboxInfo"});   
    var promotionInfo = $("<div></div>").attr({"class":"promotionInfo"});
    var promotionInputSpan =  $("<div></div>").attr("class","promotionInputSpan");
   	var displayInputValue = null;
    
   	if (promotionInputValue.type != "informationalPromotion" && promotionInputValue.type != "undefined") {
	   	displayInputValue = JSON.stringify(promotionInputValue);
	}
   	var promotionInput;
	    if(displayInputValue != null){
	    	 if(isChecked) {
	    		 promotionInput =  "<input type='radio' id='promotionInputId' checked='checked' name='promoExID' dispalyinputvalue='"+displayInputValue+"' class='promotionInput' displayType='promotion' promoExID="+externalId+">";
	    	 } else {
	    		promotionInput =  "<input type='radio' id='promotionInputId' name='promoExID' dispalyinputvalue='"+displayInputValue+"' class='promotionInput' displayType='promotion' promoExID="+externalId+">";
	    	 }
	    	 var promotionInputDiv = $("<div></div>").attr({"id":"promotionInputDiv","class":"promotionInputDiv"});
	    	   var promotionSD =  $("<div></div>").attr({"class":"promotionSD"});
	    	   var promotionDescriptionDiv = $("<div></div>").attr({"id":"promotionDescription","class":"promotionDescription"});
	    	   var promotiontext =  $("<div></div>").attr({"class":"promotiontext"});
	    	   var promotionCondition =  $("<div></div>").attr({"class":"promotionCondition promconditopspace promotiontext"});
	    	   var detailsAndTerms =  $("<div></div>").attr({"id":externalId,"class":"detailsAndTerms"});
	    	   var detailsAndTermsLink =  $("<div></div>").attr({"class":"detailsAndTermsLink"}).text("Details and Terms");
	    	   var plusToggle =  $("<span></span>").attr({"class":"glyphicon glyphicon-plus"});
	    	   var termsAndConditionLabelSpan = $("<span></span>").attr({"class":"termsandcondition"}).text("Terms and Conditions: ");
	    	   var termsAndConditionTextSpan = $("<span></span>").text(conditions);
	    	    promotionInputSpan.append(promotionInput);
	    	    promotionInputDiv.append(promotionInputSpan);
	    	    promotionDescriptionDiv.append(promotionSD.text(shortDescription));
	    	    promotionInputDiv.append(promotionDescriptionDiv);
	    	    promotionInfo.append(promotionInputDiv);
	    	    detailsAndTerms.append(promotiontext.text(qualification));
	    	    detailsAndTermsLink.append(plusToggle);
	    	    promotionInfo.append(detailsAndTermsLink);
	    	    promotionCondition.append(termsAndConditionLabelSpan);
	    	    promotionCondition.append(termsAndConditionTextSpan);
	    	    detailsAndTerms.append(promotionCondition);
	    	    promotionInfo.append(detailsAndTerms);
	    	    promotionRadioInfo.append(promotionInfo);
	    	    promotionContent.append(promotionRadioInfo);
	    }
	   
}
/*************************************************************************************************
 * ***************** This function is used for building radio button promotions  *****************
 * @param externalId -- 
 * @param shortDescription -- 
 * @param qualification -- 
 * @param conditions -- 
 * @param displayInputType -- 
 * @param promotionInputValue -- 
 * @param isChecked -- 
 * ************************************************************************************************/
function buildCheckboxPromotions(externalId,shortDescription,qualification,conditions,displayInputType, promotionInputValue,isChecked){
    var promotionContent = $(".promotionContent");    
    var promotionCheckBoxInfo = $("<div></div>").attr({"class":"promotionCheckBoxInfo"});  
    var promotionInfo = $("<div></div>").attr({"class":"promotionInfo"});
    var promotionInputSpan =  $("<div></div>").attr("class","promotionInputSpan");
   	var displayInputValue = null;
   	if (promotionInputValue.type != "informationalPromotion" && promotionInputValue.type != "undefined") {
	   	displayInputValue = JSON.stringify(promotionInputValue);
	}
	var promotionInput;
    if(displayInputType === "checkbox"){
	    	if(isChecked){
	    		promotionInput =  "<input type='checkbox' checked='checked' name="+externalId+" dispalyinputvalue='"+displayInputValue+"' class='promotionInput' displayType='promotion'  >";	
	    	}else{
	    		promotionInput =  "<input type='checkbox' name="+externalId+" dispalyinputvalue='"+displayInputValue+"' class='promotionInput' displayType='promotion'  >";
	    	}
    }else if(displayInputType == "radio"){
    	if(isChecked){
    	promotionInput =  "<input type='radio' id='radioPromotionId'  checked='checked' name="+externalId+" dispalyinputvalue='"+displayInputValue+"' class='promotionInput' displayType='promotion'  >";
    	}else{
    		promotionInput =  "<input type='radio' id='radioPromotionId' name="+externalId+" dispalyinputvalue='"+displayInputValue+"' class='promotionInput' displayType='promotion'  >";
    	}
   }
   var promotionInputDiv = $("<div></div>").attr({"id":"promotionInputDiv","class":"promotionInputDiv"});
   var promotionSD =  $("<div></div>").attr({"class":"promotionSD"});
   var promotionDescriptionDiv = $("<div></div>").attr({"id":"promotionDescription","class":"promotionDescription"});
   var promotiontext =  $("<div></div>").attr({"class":"promotiontext"});
   var promotionCondition =  $("<span></span>").attr({"class":"promotionCondition"});
   var termsAndCondition =  $("<div></div>").attr({"id":externalId,"class":"termsAndCondition"});
   var termsAndConditionLink =  $("<div></div>").attr({"class":"termsAndConditionLink"}).text("Terms and Conditions");
   var plusToggle =  $("<span></span>").attr({"class":"glyphicon glyphicon-plus"});
   
    promotionInputSpan.append(promotionInput);
    promotionInputDiv.append(promotionInputSpan);
    promotionDescriptionDiv.append(promotionSD.text(shortDescription));
    promotionInputDiv.append(promotionDescriptionDiv);
    promotionInfo.append(promotionInputDiv);    
    promotionInfo.append(promotiontext.text(qualification));
    termsAndConditionLink.append(plusToggle);
    promotionInfo.append(termsAndConditionLink);
    termsAndCondition.append(promotionCondition.text(conditions));
    promotionInfo.append(termsAndCondition);
    promotionCheckBoxInfo.append(promotionInfo);
    promotionContent.append( promotionCheckBoxInfo);
}
     
/*************************************************************************************************
 * ***************** This function is used for building feature accordion content  *****************
 * @param title -- 
 * @param dataGroupList -- 
 * ************************************************************************************************/
var collapseCount = 0;
function featureHtmlBulid(title,dataGroupList){
    panelCount = panelCount + 1
    collapseCount = collapseCount + 1
    var panelDefault = $("<div></div>").attr({"class":"panel panel-default","id":panelCount});
    var panelHeading = $("<div></div>").attr({"class":"panel-heading"});
    var panelTitle = $("<h4></h4>").attr({"class":"panel-title"});
    var collapse = $("<a></a>").attr({"data-toggle":"collapse","data-parent":"#accordion","href":"#collapse"+collapseCount}); //page
    var leftindicator = $("<span></span>").attr({"class":"leftindicator glyphicon glyphicon-plus"});  
    var leftindicatorValue = $("<span></span>").attr({"class":"leftindicatorValue"}).text(title);  
   
    
    if($(".panel.panel-default").length > 0){
    	 $(".panel:last").after(panelDefault);
    }else{
    	 $("#panel1").after(panelDefault);
    }
   // Start Build Panel Heading content
    collapse.append(leftindicator);
    collapse.append(leftindicatorValue);
    panelTitle.append(collapse);
    panelHeading.append(panelTitle);
    panelDefault.append(panelHeading);
    // End Build Panel Heading content
    
    // Start Build panelCollapse content
    var panelCollapse = $("<div></div>").attr({"class":"panel-collapse collapse","id":"collapse"+collapseCount});
    
    //Start create pannel body content 
    var panelBody = $("<div></div>").attr({"class":"panel-body"});
  $.each(dataGroupList, function(j, item) {
	
       var homeServicesOptions =  $("<div></div>").attr({"class":"home-services-options"});
        var homeServicesHead =  $("<div></div>").attr({"class":"home-services-head"}).text(item.subTitle);   
        var homeServicesBody =  $("<div></div>").attr({"class":"home-services-body"});
        var tv_features_list = $("<div></div>").attr({"class":"tv_features_list"});
        var features_list = $("<div></div>").attr({"class":"features_list"});
        if(item.subTitle.indexOf("Included") >= 0){
            features_list.attr("class","features_list included");
		
        }
        $.each(item.dataFeildList, function(k, dataFeildList) {
    		var isEnabled = dataFeildList.isEnabled;
    		//Back button info
    		if(!isEnabled){
    			enableBackBtnInfoArr.push(dataFeildList);
    		}
    
          });
        
     $.each(item.dataFeildList, function(k, dataFeildList) {
		var isEnabled = dataFeildList.isEnabled;
		if(isEnabled){
			buildDataFieldContent(dataFeildList,features_list);
	  }else{
		  enableArr.push(dataFeildList);
	  }
      });
       tv_features_list.append(features_list);
       
        homeServicesBody.append(tv_features_list);
        homeServicesOptions.append(homeServicesHead);
        homeServicesOptions.append(homeServicesBody);
        panelBody.append(homeServicesOptions);
       
});
 
    panelCollapse.append(panelBody);
    panelDefault.append(panelCollapse);
    appendEvents(".tv_items",selectTv,"click");
    
}

function moveListingOptionChilds(){
	if($(".features_list input[id*='inputID']").parent() != undefined){
		var listedQuation = $(".features_list input[id*='inputID']").parent().clone();
		$(".features_list input[id*='inputID']").parent().remove()
		$(".LISTING_OPTION").parent().parent().after(listedQuation);
		appendEvents("input",setDynamicHeight,"click");
	}
	
}
/*************************************************************************************************
 * ***************** This function is used for building feature html content  *****************
 * @param title -- 
 * @param dataGroupList -- 
 * ************************************************************************************************/
function buildDataFieldContent(dataFeildList,features_list){
	 var feature_item = $("<div></div>").attr({"id":dataFeildList.dataFieldExID,"class":"feature_item"});
	 var No_of_tvs = $("<div></div>").attr({"id":dataFeildList.dataFieldExID,"class":"No_of_tvs"}); 
     var inputTypeValue = dataFeildList.dispalyInput.toLowerCase();
     var mandatoryStar = $("<span></span>").text("*").attr({
         "class": "mandatory"
     });
     var inputType;
     var feature_item_Value = $("<span></span>").attr({"class":"feature_item_Value"}); 
     var dataFieldDisclosure = $("<div></div>").attr({"class": "alert alert-info"}).text(dataFeildList.dataFieldText);
     var dataFieldinforamtion = $("<span></span>").attr({"class": "dataFieldinforamtion"}).text("i");
     
     if(inputTypeValue != undefined && inputTypeValue == "tick"){ 
       inputType = $("<span></span>").attr({"class":"glyphicon glyphicon-ok"});
       feature_item_Value.text(dataFeildList.dataFieldText);
     }else if(inputTypeValue != undefined && inputTypeValue == "checkbox"){
    	 if(dataFeildList.enteredValue=='on'){
    		 inputType = "<input id='"+dataFeildList.dataFieldExID+"' type='checkbox' name='"+dataFeildList.dataFieldExID+"' dispalyinputvalue='"+JSON.stringify(dataFeildList.dispalyInputValue)+"' displayType='"+dataFeildList.type+"' class='featureCheckBoxInput' checked>";
    	 }else{
    		 inputType = "<input id='"+dataFeildList.dataFieldExID+"' type='checkbox' name='"+dataFeildList.dataFieldExID+"' dispalyinputvalue='"+JSON.stringify(dataFeildList.dispalyInputValue)+"' displayType='"+dataFeildList.type+"' class='featureCheckBoxInput'>";
    	 }
       feature_item_Value.text(dataFeildList.dataFieldText);
       feature_item_Value.append(aditionalChannelsPrice(dataFeildList));
       if(dataFeildList.featureExID == "MULTI_RM_DVR"){
    	   MultiRMDVR = dataFeildList.dataFieldExID;
       }
       if (dataFeildList.dataFieldExID != undefined && dataFeildList.dataFieldExID == MultiRMDVR) {
    	   var anchorHelpIcon = $("<a></a>").attr({"data-toggle":"popover","type":"button","data-html":"true","data-placement":"top","data-trigger":"hover click","data-content":$.Constants.HELP_MESSAGE}).addClass("house-dvr-help-icon").text("?");
    	   feature_item_Value.append(anchorHelpIcon);
       }
     }else if(inputTypeValue != undefined && inputTypeValue == "radio"){
       inputType = "<input type='radio' id='featurePromotionInputId' name='"+dataFeildList.dataFieldExID+"' dispalyinputvalue='"+JSON.stringify(dataFeildList.dispalyInputValue)+"' displayType='"+dataFeildList.type+"' class='featureRadioInput'>";
       feature_item_Value.text(dataFeildList.dataFieldText);
       feature_item_Value.append(aditionalChannelsPrice(dataFeildList));
     }
     else if(inputTypeValue != undefined && inputTypeValue == "dropdown"){
    	 var featureVOList = dataFeildList.featureVOList;
    	
    	 if($.inArray(dataFeildList.featureExID, tvFeatureExtIds) !== -1){
    		 feature_item_Value.attr({"class":"feature_item_Value tvBoxes"}).text("");
    		}else{
    			feature_item_Value.attr({"class":"feature_item_Value tvBoxes"}).text(dataFeildList.dataFieldText);
    		} 
         //featureGroup
          if(featureVOList != undefined){
       	   selectCount = ++selectCount;
   			feature_item_Value.attr({"class":"feature_item_Value featureDataField"}).text(dataFeildList.dataFieldText);
       	   if(!(dataFeildList.featureExID == "MODEM_OPTION" || dataFeildList.featureExID == "LISTING_OPTION")){
   	           inputType = $("<select></select>").attr({"id":"featureGroup"+selectCount, "class":"featureDropDown","displayType":dataFeildList.type,"name":dataFeildList.dataFieldExID});
   	           var option = $('<option/>');
   	            option.attr({ 'value': "" }).text("Select");
   	           inputType.append(option);
       	   }
            $.each(featureVOList, function(l,featureVO) {
            	 var featureClass = "listing_type "+dataFeildList.featureExID;
            	if(!(dataFeildList.featureExID == "MODEM_OPTION" || dataFeildList.featureExID == "LISTING_OPTION")){
	            	 option = $('<option/>');
	            	
	            	if(featureVO.isIncluded != undefined && featureVO.isIncluded){
	            		option.attr({"selected":"selected"});
	            	} 
	                 if(l == 0){
	                     feature_item_Value.attr({"class":"feature_item_Value featureDataFieldList dropdownAling"}).text(featureVO.dataFieldText);
	                      option.attr({ 'value': featureVO.featureExID, 'dispalyinputvalue': JSON.stringify(featureVO.dispalyInputValue)}).text(featureVO.dataConstraintValueList[0] + " - $" +featureVO.baseRecurringPrice );
	                      inputType.append(option);
	                      feature_item_Value.append(mandatoryStar);
	                 }else{
	                      option.attr({ 'value': featureVO.featureExID, 'dispalyinputvalue': JSON.stringify(featureVO.dispalyInputValue)}).text(featureVO.dataConstraintValueList[0] + " - $" +featureVO.baseRecurringPrice );
	                      inputType.append(option);
	                 }
            	}else{
	                 var listing_type = $("<div></div>").attr({"class":featureClass, "tabindex":"0"});
	                 /*************** Back button info start**************************/
	                 if(featureVO.enteredValue != undefined && featureVO.enteredValue != ""){
	                	 listing_type.addClass("active");
	                	 console.log("enteredValue :: "+featureVO.enteredValue +" :: feature_item ::" +features_list +" ::: externalID " +featureVO.dataFieldName)
	                	 populateBoxOptions(featureVO.enteredValue, features_list, featureVO.dataFieldName);
	                 }
	                 /*************** Back button info end**************************/
	                 var submitJson = {'displaytype': dataFeildList.type,'name': dataFeildList.dataFieldExID ,'inputType': "div",'value':featureVO.featureExID};
	                 if(l == 0){
	    	       		  listing_type.attr({ 'value': featureVO.dataConstraintValueList[0], 'type':"div", 'submitJson':JSON.stringify(submitJson), 'dispalyinputvalue':JSON.stringify(featureVO.dispalyInputValue),"name":dataFeildList.dataFieldExID}).html("<div class='selectedKey'>"+featureVO.dataConstraintValueList[0] + "</div> <div class='selectedValue'> $" +featureVO.baseRecurringPrice +"</div>");
	    	       		  No_of_tvs.append(listing_type);
	                 }else{
	               	  listing_type.attr({ 'value': featureVO.dataConstraintValueList[0], 'type':"div", 'submitJson':JSON.stringify(submitJson), 'dispalyinputvalue':JSON.stringify(featureVO.dispalyInputValue),"name":dataFeildList.dataFieldExID}).html("<div class='selectedKey'>"+featureVO.dataConstraintValueList[0] + "</div> <div class='selectedValue'> $" +featureVO.baseRecurringPrice +"</div>");
	                	 No_of_tvs.append(listing_type);
	                 }
	                 feature_item_Value.append(mandatoryStar);	
	                 feature_item.append(No_of_tvs);
            	}
                
            console.log("featureVO : dataFieldText : "+featureVO.dataConstraintValueList[0]);
            });
       }else{
           //dataConstraintValueList
           var dataConstraintValueList = dataFeildList.dataConstraintValueList;
           var priceTierMap = dataFeildList.priceTierMap;
           if(dataConstraintValueList != undefined && dataConstraintValueList.length > 0){
        	   selectCount = selectCount + 1;
               inputType = $("<select></select>").attr({"id":"featureGroup"+selectCount, "class":"featureDropDown"}).attr({"displayType":dataFeildList.type}).attr({"name":dataFeildList.dataFieldExID});
               feature_item_Value.attr({"class":"feature_item_Value featureDataFieldList dropdownAling"}).text(dataFeildList.dataFieldText);
                  var option = $('<option/>');
               option.attr({ 'value': "" }).text("Select");
               inputType.append(option);
             $.each(dataConstraintValueList, function(index,dataConstraintValue) {
                option = $('<option/>');
                option.attr({ 'value': dataConstraintValue, 'dispalyinputvalue': JSON.stringify(dataFeildList.dispalyInputValue)}).text(dataConstraintValue);
                if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != "" ) {       
                if(dataFeildList.enteredValue == dataConstraintValue){
            		 option.attr("selected", "selected");
            	}
                }
                inputType.append(option);
             });
           }else if(priceTierMap  != undefined ){
         	//priceTier
        	   if(dataFeildList.dispalyInputValue.featureExternalID == "OUTLET_ACTIVATION"){
        	   var tv_item_div = $("<div></div>").attr({"class":"tv_item"});
               var tv_item_ui = $("<ul></ul>").attr({"class":"nav nav-tabs"});
           		selectCount = +selectCount;
              	 if(dataFeildList.dispalyInputValue.featureExternalID != "OUTLET_ACTIVATION"){
  	         	  inputType = $("<select></select>").attr({"id":"featureGroup"+selectCount, "class":"featureDropDown"}).attr({"displayType":dataFeildList.type}).attr({"name":dataFeildList.dataFieldExID});
  	               feature_item_Value.attr({"class":"feature_item_Value featureDataFieldList"}).text(dataFeildList.dataFieldText);
  	               var option = $('<option/>');
  	               option.attr({ 'value': "" }).text("Select");
  	               inputType.append(option);
              	 }else{
                  		//feature_item_Value.attr({"class":"feature_item_Value tvBoxes"}).text(dataFeildList.dataFieldText);
                   }
              	 var tvSize = Object.keys(priceTierMap).length;
              	 var isMoreOptions = true;
           $.each(priceTierMap, function(priceTierIndex,priceTierMap) {
        	   var submitJson = {'displaytype': dataFeildList.type,'name': dataFeildList.dataFieldExID ,'inputType': "div",'value':priceTierMap.rangeStart};
        	   dataFeildList.dispalyInputValue.recuringPrice = (priceTierMap.baseRecurringPrice).toFixed(2);
        	   dataFeildList.dispalyInputValue.nonRecuringPrice =(priceTierMap.baseNonRecurringPrice).toFixed(2);
        	   if(priceTierIndex < 5){
                   if(dataFeildList.dispalyInputValue.featureExternalID == "OUTLET_ACTIVATION"){
             		  var tv_item_li = $("<li></li>");
             		  var tv_item_a = $("<a></a>").attr({"data-toggle":"tab","class":"tv_items OUTLET_ACTIVATION","tabindex":"0"});
             		  console.log("priceTierIndex ::::: "+priceTierIndex);
             		  
             		  var range = (priceTierMap.rangeStart == 1)?("<div class='tv selectedKey'>"+(priceTierMap.rangeStart)+" TV </div> "):("<div class='tv selectedKey'>"+(priceTierMap.rangeStart)+"  TVs </div> ");
             		  tv_item_a.attr({ 'value': priceTierMap.rangeStart,'submitJson':JSON.stringify(submitJson), 'dispalyinputvalue': JSON.stringify(dataFeildList.dispalyInputValue) }).html(range+"$"+((priceTierMap.baseNonRecurringPrice).toFixed(2)));
             		 /*********
             		  *  ********************************
             		  * *******Back button info start ***
             		  * ********************************
             		  * ***********/
 	                 if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "" && dataFeildList.enteredValue.split(" ")[0] == priceTierIndex){
 	                	 tv_item_a.addClass("selectedTv");
 	                	 console.log("enteredValue :: "+dataFeildList.enteredValue +" :: feature_item ::" +features_list +" ::: externalID " +dataFeildList.dataFieldName)
 	                	 console.log("NO OF TVS ::::::::::::::::: "+dataFeildList.enteredValue.split(" ")[0]);
 	                	 isBackButtonInfo = false;
 	                	selectedNoOfTvs = dataFeildList.enteredValue.split(" ")[0];
 	                 }else if((priceTierIndex == 1) && (dataFeildList.enteredValue == undefined || dataFeildList.enteredValue == "")){
 	                	selectedNoOfTvs = 1;
             			 tv_item_a.addClass("selectedTv");
            		  }
 	                
 	                 /*********
 	                  * ********************************
 	                  * *******Back button info end*****
 	                  * ********************************
 	                  * *******/
 	                 
             		  tv_item_li.append(tv_item_a);
             		  if(priceTierMap.rangeStart > 0){
             			  tv_item_ui.append(tv_item_li);
             		  }
             		  tv_item_div.append(tv_item_ui);
              		  No_of_tvs.append(tv_item_div);
        		      }else{
   		             option = $('<option/>');
   	                 option.attr({ 'value': priceTierMap.rangeStart, 'dispalyinputvalue': JSON.stringify(dataFeildList.dispalyInputValue) }).text(priceTierMap.rangeStart+"-"+"$"+((priceTierMap.baseNonRecurringPrice).toFixed(2))+"/"+((priceTierMap.baseRecurringPrice).toFixed(2)));
   	                 if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != "" ) {                	
   	                	if(dataFeildList.enteredValue == priceTierMap.rangeStart){
   	                		console.log("dataFeildList.enteredValue True " +dataFeildList.enteredValue );
   	                		 option.attr("selected", "selected");
   	                	}
   	          	    }
   	                inputType.append(option);
                  }
        	   }else if(priceTierIndex > 4 && dataFeildList.dispalyInputValue.featureExternalID == "OUTLET_ACTIVATION" && isMoreOptions == true){
        		   var moretvJSON = {};
        		   moretvJSON.dataFeildList = dataFeildList;
        		   moretvJSON.priceTierMap = priceTierMap;
        		   moreTvsDispalyInputValueArr.push(moretvJSON);
    		      var tv_item_li = $("<li></li>");
    		      var tv_item_a = $("<a></a>").attr({"id":"moreOutletActivations","data-toggle":"tab","class":"tv_items OUTLET_ACTIVATION","tabindex":"0","value": "More","length": tvSize}).text("More?");
    		      if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "" && dataFeildList.enteredValue.split(" ")[0] == priceTierIndex){
	                	 tv_item_a.addClass("selectedTv");
	                	 console.log("NO OF TVS :::::::::MORE:::::::: "+dataFeildList.enteredValue.split(" ")[0]);
	                	 isBackButtonInfo = false;
	                	selectedNoOfTvs = dataFeildList.enteredValue.split(" ")[0];
	                 }
    		      
           		  tv_item_li.append(tv_item_a);
           		  tv_item_ui.append(tv_item_li);
           		  isMoreOptions = false;
               }else if(priceTierIndex > 4 && dataFeildList.dispalyInputValue.featureExternalID == "OUTLET_ACTIVATION" && isMoreOptions == false){
            	   var moretvJSON = {};
        		   moretvJSON.dataFeildList = dataFeildList;
        		   moretvJSON.priceTierMap = priceTierMap;
        		   moreTvsDispalyInputValueArr.push(moretvJSON);
        		   if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "" && dataFeildList.enteredValue.split(" ")[0] == priceTierIndex){
    		    	    isBackButtonInfo = false;
	                	selectedNoOfTvs = dataFeildList.enteredValue.split(" ")[0];
        		   }
               }
               feature_item.append(No_of_tvs);

             });
        	}else{
        		
        		if($.inArray(dataFeildList.featureExID, tvFeatureExtIds) === -1){
        		   selectCount = selectCount + 1;
                   inputType = $("<select></select>").attr({"id":"featureGroup"+selectCount, "class":"featureDropDown"}).attr({"displayType":dataFeildList.type}).attr({"name":dataFeildList.dataFieldExID});
                   feature_item_Value.attr({"class":"feature_item_Value featureDataFieldList"}).text(dataFeildList.dataFieldText).append(mandatoryStar);
                      var option = $('<option/>');
                   option.attr({ 'value': "" }).text("Select");
                   inputType.append(option);
                   $.each(priceTierMap, function(priceTierIndex,priceTierMap) {
                    option = $('<option/>');
                    var optionValue =  priceTierMap.rangeStart+"-$"+priceTierMap.baseNonRecurringPrice.toFixed(2)+"/"+priceTierMap.baseRecurringPrice.toFixed(2);//1-$39.95/$0.00
                    option.attr({ 'value': priceTierMap.rangeStart, 'dispalyinputvalue': JSON.stringify(dataFeildList.dispalyInputValue)}).text(optionValue);
                    if (dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "" ) {       
                		 option.attr("selected", "selected");
                    }
                    inputType.append(option);
                 });
        		}
        	}
        
        	 //add tv feature list to arrOfRecievers array 
        	 if($.inArray(dataFeildList.featureExID, tvFeatureExtIds) !== -1){
        		 if(dataFeildList.featureExID == $.Constants.PLAYBACK_RECEIVERS){
        			 arrOfRecievers.push(dataFeildList);
          		   tvOptionsCont(dataFeildList);
        		 }else{
        			 arrOfRecievers.push(dataFeildList);
        		 }
        	}
        	 
           }
       }
     }else if(inputTypeValue != undefined && inputTypeValue == "text"){

         console.log("------------------------------------")
    	 inputType = "<input type='text' id='"+dataFeildList.dataFieldExID+"inputID' name='"+dataFeildList.dataFieldExID+"' dispalyinputvalue='"+dataFeildList.dispalyInputValue+"' validation='"+dataFeildList.validation+"' displayType='"+dataFeildList.type+"' class='featureTextInput'>";
    	  /*************** Back button info start**************************/
         if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != ""){
        	 inputType =  "<input type='text' id='"+dataFeildList.dataFieldExID+"inputID' name='"+dataFeildList.dataFieldExID+"' dispalyinputvalue='"+dataFeildList.dispalyInputValue+"' validation='"+dataFeildList.validation+"' value='"+dataFeildList.enteredValue+"' displayType='"+dataFeildList.type+"' class='featureTextInput'>";
         }
         /*************** Back button info end**************************/
         feature_item_Value.text(dataFeildList.dataFieldText).append(mandatoryStar);
         feature_item_Value.addClass("dropdownAling");
         /*if(dataFeildList.dataFieldExID == "TWCListingDisplay") {
           	 feature_item_Value.append(mandatoryStar);
            }*/
         feature_item_Value.append(aditionalChannelsPrice(dataFeildList));
     }else if (inputTypeValue != undefined && inputTypeValue == "disclosure" || inputTypeValue == "inforamtion" || inputTypeValue == "notable" || inputTypeValue == "informational") {
     if (inputTypeValue == "inforamtion") {
         dataFieldDisclosure.append(dataFieldinforamtion);
         feature_item.append(dataFieldDisclosure);
     } else {
         feature_item.append(dataFieldDisclosure.html(dataFeildList.dataFieldText));
     }
 } else {
     feature_item.append(inputType);
 }
    
    	 feature_item.append(inputType);
    
 	if((inputTypeValue != undefined && inputTypeValue != "disclosure") && (inputTypeValue != undefined && (inputTypeValue != "inforamtion" || inputTypeValue == "notable")) && dataFeildList.dataFieldExID != "TWCPlaybackReceivers"){
 		feature_item.append(feature_item_Value);
 	}
 	 if($.inArray(dataFeildList.featureExID, tvFeatureExtIds) === -1){
 		features_list.append(feature_item);
 	 }
 		console.log("arrOfRecievers ::::::::: "+JSON.stringify(arrOfRecievers));
}

/***********
 * **************
 * **************This function is used for  display price info for all questions and channels list *********
 * **************
 * **************
 * ******/  
function aditionalChannelsPrice(dataFeildList){
	 var featurePrice = "";
     if (dataFeildList.dispalyInputValue != undefined) {
	     featurePrice = dataFeildList.dispalyInputValue.recuringPrice > 0 ? dataFeildList.dispalyInputValue.recuringPrice : dataFeildList.dispalyInputValue.nonRecuringPrice;
	}
     var priceWithSpace = $("<span></span>").attr({"class":"tabspace"}).text("$"+featurePrice);
     return featurePrice!=""?priceWithSpace:"";
}


/***********
 * **************
 * **************This function is used for  enable and disable html tags when click on checkbox or radio button *********
 * **************
 * **************
 * ******/  
function buildCheckedContent() {
    var id = $(this).attr("id");
    var isChecked = $(this).is(':checked');
    if (isChecked) {
        var serviceVal =  "Y";
        var previous = $(this).data("previous");
        if(id == MultiRMDVR){
        	$(".playbackReceiver").show();
        }
    } else {
    	 if(id == MultiRMDVR){
         	$(".playBackRecievers").remove();
         }
        var serviceVal = "N";
        var previous = $(this).data("previous");
    }
    $(this).data("previous", serviceVal);
    dropDownChnage(serviceVal, previous, $(this));
    appendEvents("select",dropDownChnageBuild,"change");
    appendEvents("input",setDynamicHeight,"click");
    appendEvents("select",setDynamicHeight,"change");
}
/***********************************************************************************************
 * ****************** register and unregister events when adding a new elements ****************
 * **********************************************************************************************/
function appendEvents(tagName,functionName,eventName){
	$(tagName).unbind( eventName, functionName);
	$(tagName).bind(eventName,functionName);
}

/***********
 * **************
 * **************This function is used for  remove disable childs html tags when trigger form tag events *********
 * ************************** Passing  params ****************************
 * @param dataFieldExtId -- 
 * @param contentKeyValue -- 
 * **************
 * ******/   
function removeDisableFields(contentKeyValue,dataFieldExtId) {
    var disableJson = dataFieldMatrixMap[dataFieldExtId];
    if(contentKeyValue != undefined && dataFieldExtId != undefined  
    	    && disableJson != undefined){
    	var serviceTypeList;
	    if(disableJson[contentKeyValue] != undefined ){
	    	   serviceTypeList = disableJson[contentKeyValue];
		 }
    if (disableJson != undefined && serviceTypeList != undefined ) {
        $.each(serviceTypeList, function(k, disableItems) {
            if (dataFieldMatrixMap[disableItems.externalId] != undefined) {
                removeDisableChilds(dataFieldMatrixMap[disableItems.externalId]);
            }
            $("#" + disableItems.externalId).remove();
            console.log("disableChildItems2.externalId playbackdataFieldExID" + disableItems.externalId )
            if(disableItems.externalId.toLowerCase().indexOf("playback") >= 0 && isBackButtonInfo){
            	removePlaybackReciver(disableItems.externalId);
            }
         });
    }
    }
}

/***********
 * **************
 * **************This function is used for  remove disable childs html tags when trigger form tag events *********
 * ************************** Passing  params ****************************
 * @param disableItemsJson -- list of disble items 
 * **************
 * ******/
function removeDisableChilds(disableItemsJson) {
        if (disableItemsJson != undefined) {
        $.each(disableItemsJson, function(k, disableChildItems) {
            $.each(disableChildItems, function(k, disableChildItems2) {
                $("#" + disableChildItems2.externalId).remove();
                if (dataFieldMatrixMap[disableChildItems2.externalId] != undefined) {
                    removeDisableChilds(dataFieldMatrixMap[disableChildItems2.externalId]);
                }

            });
        });
    }
}
/***********
 * **************
 * **************This function is used for  remove playback recivers *********
 * **************
 * **************
 * ******/   
function removePlaybackReciver(dataFieldExID) {
	console.log("playback   playback   playback playbackdataFieldExID" + dataFieldExID )
	 for (var i = 0; i < arrOfRecievers.length; i++) {
	        var cur = arrOfRecievers[i];
	       if(cur.dataFieldExID == dataFieldExID){
	        	arrOfRecievers.splice(i, 1);
	            break;
	        }
	    }
}

/***********
 * **************
 * **************This function is used for  enable and disable html tags when changing select box *********
 * **************
 * **************
 * ******/   
function dropDownChnageBuild() {
    var serviceVal = $(this).val();
    var previous = $(this).data("previous");
    $(this).data("previous", serviceVal)
    dropDownChnage(serviceVal, previous,$(this))
}

/***********
 * **************
 * **************This function is used for  enable and disable html tags when trigger form tag events *********
 * **************************Passing multiple params ****************************
 * @param serviceVal -- selected value
 * @param previous -- previous selected value
 * @param thisObj -- selected parent object
 * **************
 * ******/
//enable and disable logic
function dropDownChnage(serviceVal, previous,thisObj) {
    var serviceValArr = serviceVal;
    if (previous != undefined) {
        removeDisableFields(previous,thisObj.attr("name"));
    }
    var dataFeildList = dataFieldMatrixMap[thisObj.attr("name")];
    var enable = false;
    if(dataFeildList != undefined){
        if(dataFeildList[serviceVal] != undefined){
        $.each(dataFeildList[serviceVal], function(k, enableItems) {
            $.each(enableArr, function(k, enableArrObj) {
                if (enableArrObj.dataFieldExID == enableItems.externalId) {
                	buildDataFieldContent(enableArrObj, thisObj.parent());
                   // buildHtml(enableArrObj, enable,thisObj);
                    return false;
                }
            });
        }); 
        
        }
    }
    appendEvents("input[type='checkbox']",buildCheckedContent,"click");
    appendEvents(".tv_items",selectTv,"click");
    appendEvents("input",setDynamicHeight,"click");
    appendEvents("select",setDynamicHeight,"change");
    $('input[validation="Phone"]').attr("maxlength","12");
    appendEvents('input[validation="Phone"]',isNumber,"keypress");
    appendEvents('input[validation="Phone"]',prepopulatePhoneDashes,"keypress keydown keyup change blur");
    $('input[validation="Phone"]').each(prepopulatePhoneDashes);

}  

/********************************* Back button logic start ********************************************/


function buildPreviousCheckedContent() {
	$("input[type='checkbox']:checked").each(function (){
		var id = $(this).attr("id");
	    var isChecked = $(this).is(':checked');
	    if (isChecked) {
	        var serviceVal =  "Y";
	        var previous = $(this).data("previous");
	        $(".playbackReceiver").show();
	    } else {
	    	$(".playBackRecievers").remove();
	        var serviceVal = "N";
	        var previous = $(this).data("previous");
	    }
	    $(this).data("previous", serviceVal);
	    dropDownChnage(serviceVal, previous, $(this));
	});
	appendEvents("select",dropDownChnageBuild,"change");
    appendEvents("input",setDynamicHeight,"click");
    appendEvents("select",setDynamicHeight,"change");
}

/***********
 * ************** This function is used for *************************************
 * ************** populate back button Listing options and modem options  *********
 * **************
 * **************
 * ******/
function populateBoxOptions(enteredValue, currentObj, enableExtId) {
	console.log("entered value ::  "+enteredValue )
    var dataFeildList = dataFieldMatrixMap[enableExtId];
    if(dataFeildList != undefined){
        if(dataFeildList[enteredValue] != undefined){
        $.each(dataFeildList[enteredValue], function(k, enableItems) {
            $.each(enableBackBtnInfoArr, function(k, enableArrObj) {
                if (enableArrObj.dataFieldExID == enableItems.externalId) {
                	buildDataFieldContent(enableArrObj, currentObj);
                    return false;
                }
            });
        }); 
        
        }
    }
    appendEvents("input[type='checkbox']",buildCheckedContent,"click");
    appendEvents(".tv_items",selectTv,"click");
    appendEvents("input",setDynamicHeight,"click");
    appendEvents("select",setDynamicHeight,"change");
}

var receiversCount = [];
function previousSeletedRecivers(){
	 $.each(arrOfRecievers, function(l, dataFeildList) {
		 if(dataFeildList.enteredValue != undefined && dataFeildList.enteredValue !=""){
			 receiversCount.push(dataFeildList.enteredValue+"|"+dataFeildList.featureExID);
		 }else{
			 receiversCount.push(0+"|"+0);
		 }
		 
	 });
	 checkSelectedRecivers();
}
var reciversCount = 0;
function checkSelectedRecivers(){
	for(var i=0;i<receiversCount.length ;i++){
		var enteredValue = receiversCount[i].split("|")[0]
		var extId = receiversCount[i].split("|")[1]
		console.log("enteredValue :: "+enteredValue+" ::: extId ::: " +extId);
		if(enteredValue > 0){
			for(var j = 0;j< enteredValue ;j++){
				reciversCount = reciversCount+1
				recivers = 'receivers_'+reciversCount
				console.log("recivers :: "+recivers+" ::: reciversCount ::: " +reciversCount);
				$("input[name="+recivers+"][id="+extId+"]").attr("checked","checked");
			}
		}
	}
	
	 
}

/************************************************************************** Back button logic end *********************************************************/


/******************************************************************************************
 * ***************** This function is used for wraping 3 include feature to one div *******
 * ****************************************************************************************/    
  function wrapIncludedFeatures(){
	var include = $(".features_list.included .feature_item")
  	for( var i = 0; i < include.length; i+=3 ) {
		  include.slice(i, i+3).wrap('<div class="includeFeature"></div>');
	  }
	  
  }
  
  /*********************************************************************************************************
   * ***************** This function is used for fetch all form field information and convert to json ******
   * *******************************************************************************************************/
  $.fn.serializeObject = function() {
	    var jsonOBJ = [];
	    var blockedSerializationValues = ["iData","receivers_1","receivers_2","receivers_3","receivers_4","","select"];
	    var a = this.serializeArray();
	    $.each(a, function(i,val) {
	    	//console.log(i+"aaaaaa ==== "+val.name);
	    	  if(!($.inArray(val.value, blockedSerializationValues) > -1) && !($.inArray(val.name, blockedSerializationValues) > -1)){
	    		  var jsonOBJ1 = {};
	   	      if(val.name == "promoExID"){ 
	   	    	jsonOBJ1['displaytype'] = $('[name='+val.name+']').attr('displaytype');
	   	    	val.name = $('[name='+val.name+']:checked').attr('promoexid');
		   	   }else{
		   		jsonOBJ1['displaytype'] = $('[name='+val.name+']').attr('displaytype');
			  }
	   	       jsonOBJ1['name'] = val.name;
	   	       jsonOBJ1['value'] = val.value;
	   	       jsonOBJ1['inputype'] = $('[name='+val.name+']').attr('type');
	   	      jsonOBJ.push(jsonOBJ1);
	            }
	    });
	    $(".OUTLET_ACTIVATION.selectedTv,.LISTING_OPTION.active ,.MODEM_OPTION.active").each(function(){
	    	if($(this).attr("submitjson") != undefined){
	    		var submitJson = JSON.parse($(this).attr("submitjson"));
			    jsonOBJ.push(submitJson);
	    	}
		  });
		var television_json = {};
		$(".tvOptions :checked").each(function() {
			console.log(television_json[$(this).attr("value")]+"========================="+$(this).attr("value"));
			if(television_json[$(this).attr("value")]){
				var tvSelectionJSON = television_json[$(this).attr("value")];
				tvSelectionJSON.value = tvSelectionJSON.value + 1;
				television_json[$(this).attr("value")] = tvSelectionJSON;
			}else{
				var tvSelectionJSON = {};
				tvSelectionJSON.name = $(this).attr("value");
				tvSelectionJSON.value = 1;
				tvSelectionJSON.displaytype = 'Feature';
				television_json[tvSelectionJSON.name] = tvSelectionJSON;
			}
        });
		$.each(television_json, function(key, value) {
		  console.log(key+ ':' + value);
		  jsonOBJ.push(value);
		});
	    return jsonOBJ;
	};
	
	function selectTv() {
	    /************* remove tv options when change no of tv's ***********/
	    $(".tvOptionsCont").remove();
	    $(".moreTvs").remove();
	    $(".noOfTvOptions").remove();
	    $(".tv_items").removeClass("selectedTv");
	    $(".configureListContent").text("");
	    $(".error_tooltip").remove();
	    $(".tooltipContent").text("");
	    $(this).addClass("selectedTv");
	    $(".moreTvsText").remove();
	    if($(this) != undefined){
	    	addToPlanInfo(true);
	    }
	    var noOfTvs = $(this).attr("value");
	    if(noOfTvs == undefined && selectedNoOfTvs > 4){
	    	noOfTvs = "More";
	    }
	    if (noOfTvs != "More") {
	        for (var i = 0; i < noOfTvs; i++) {
	            addTvContent(i + 1);
	            buildConfigureTvs(i + 1);
	        }
	        appendEvents("input", addToPlanInfo, "click");
	        console.log("arrOfRecievers Playback ::: "+JSON.stringify(arrOfRecievers));
	        if(MultiRMDVR != '' && $("input[name="+MultiRMDVR+"]") != undefined && $("input[name="+MultiRMDVR+"]").is(":checked")){
	        	buildCheckedPlaybackReceivers();
	        }
	    
	    } else if (noOfTvs == "More") {
	    	var isPreviousSelected = false;
	    	var moreTvsText = $("<span></span>").attr({"class":"moreTvsText"}).text("Please select more televisions?  ");
	        var moreTvsContent = $("<div></div>").addClass("moreTvsContent");
	        var moreLength = $(this).attr("length");
	        if(moreLength == 0 || moreLength == undefined){
	        	isPreviousSelected = true;
	        	$("#moreOutletActivations").addClass("selectedTv");
	        	moreLength = $("#moreOutletActivations").attr("length");
	        }
	        var moreSelectBox = '';
	        var option = '';
	        for (var i = 4; i < moreLength; i++) {
	        	var dataFeildList = moreTvsDispalyInputValueArr[i-4].dataFeildList;// JSON.stringify(moreTvsDispalyInputValueArr[j].dispalyInputValue);
	        	var priceTierMap = moreTvsDispalyInputValueArr[i-4].priceTierMap;
	        	var submitJson = {'displaytype': dataFeildList.type,'name': dataFeildList.dataFieldExID ,'inputType': "div",'value':priceTierMap.rangeStart};
	        	if(i == 4){
	        		moreSelectBox = $("<select></select>").attr({
		  	            "class": "moreTvs",
		  	            'id': "MoreTvsId",
		  	            'name': dataFeildList.dataFieldExID,
		  	            'displaytype': dataFeildList.type
		  	        });
		  	         option = $('<option/>');
		  	        option.attr({
		  	            'value': ""
		  	        }).text("Select");
		  	        moreSelectBox.append(option);
	        		
	        	}
	        	console.log("moreTvsDispalyInputValueArr == "+ JSON.stringify(moreTvsDispalyInputValueArr[i-4]));
	        	dataFeildList.dispalyInputValue.recuringPrice = priceTierMap.baseRecurringPrice; 
	        	dataFeildList.dispalyInputValue.nonRecuringPrice = priceTierMap.baseNonRecurringPrice;
	        	option = $('<option/>').attr({
	                'value': (i+1),
	                'dispalyinputvalue': JSON.stringify(dataFeildList.dispalyInputValue),
	                'submitJson':JSON.stringify(submitJson)
	            }).html((i +1)+" TVs $"+((priceTierMap.baseNonRecurringPrice).toFixed(2))+"/"+((priceTierMap.baseRecurringPrice).toFixed(2)));//.text(i + 1)
	        	if(isPreviousSelected){
	        		if((i+1) == selectedNoOfTvs){
	        			option = option.attr({"selected":"selected"});
	        		}
	        	}
	            moreSelectBox.append(option);
	            
	        }
	        moreTvsContent.append(moreTvsText);
	        moreTvsContent.append(moreSelectBox);
	        
	        $(".receiverContent").append($(".tooltipContent").before(moreTvsContent));
	    }
	    appendEvents(".moreTvs", moreTvs, "change");
	    setDynamicHeight();
	    appendEvents("select",dropDownChnageBuild,"change");
	    appendEvents("select",addToPlanInfo,"change");
	    var plansummaryscrollHeight;
	    if(noOfTvs == "More" && $("#moreOutletActivations").length > 0 && $("#moreOutletActivations").offset() != undefined){
	    	 plansummaryscrollHeight = $("#moreOutletActivations").offset().top
	    	 setCKOActions(plansummaryscrollHeight);
	    }
		
	}
	
	/**********************************************************************************************************
	 * 
	 * ****************** This function is used for selected  Playback Receivers ********
	 * 
	 * *********************************************************************************************************/
	function buildCheckedPlaybackReceivers(){
		 console.log("buildCheckedPlaybackReceivers :::::::::   Inside") 
	       $.each(arrOfRecievers, function(l, dataFeildList) {
	           console.log("buildCheckedPlaybackReceivers :::::::::   "+dataFeildList.dataFieldText) 
	    	   if (dataFeildList.dataFieldText.indexOf('Playback Receivers')  >= 0) {
	            	console.log('Playback Receivers :: ' +dataFeildList.featureExID)
	                tvOptionsCont(dataFeildList);
	                return false;
	            }
	        });
	}
	/**********************************************************************************************************
	 * 
	 * ****************** This function is used for building more tv content when click on more button ********
	 * 
	 * *********************************************************************************************************/
	function moreTvs(){
		$(".configureListContent").text("");
		$(".tvOptionsCont").remove();
		$(".tooltipContent").text("");
		 console.log(" selectedTv====");
       	 var selectedTvs = $('.moreTvs option:selected' ).val();
       	 console.log(" selectedTv===="+ selectedTvs);
       	 if(selectedTvs != ""){
	       	for(var i= 0 ; i < selectedTvs ; i++){
				  addTvContent(i + 1);
				  buildConfigureTvs(i + 1);
			  }
       	 }
       	if(MultiRMDVR != '' && $("input[name="+MultiRMDVR+"]") != undefined && $("input[name="+MultiRMDVR+"]").is(":checked")){
        	buildCheckedPlaybackReceivers();
        }
       	addToPlanInfo(true);
       	setDynamicHeight();
       	
       
	}
	/*************************************************************************************************
	 * 
	 * ****************** This function is used for building play back reciver content ***************
	 * 
	 * ************************************************************************************************/
	function tvOptionsCont(dataFeildList){
		$(".tvOptionsCont").each(function(index){
			var reciverdiv = $("<div></div>").addClass("tvOptions playBackRecievers").attr("id",dataFeildList.dataFieldExID);
			var reciverJson;
			  selectedPriceTierArr[dataFeildList.featureExID] = dataFeildList.priceTierMap;
			if(dataFeildList.dispalyInputValue != undefined){
				   reciverJson = dataFeildList.dispalyInputValue;
				   var count = index + 1;
				   reciverJson["reciverExternalId"] = dataFeildList.featureExID+"_"+count;
			   }
			 var playbackRadio = $("<input />").attr({"id":dataFeildList.featureExID,"class":dataFeildList.featureExID+"_"+count, "type":"radio","index":"2", "name":"receivers_"+(index+1),"value":dataFeildList.dataFieldExID,"dispalyinputvalue":JSON.stringify(reciverJson) });
			 reciverdiv.append(playbackRadio);
			   reciverdiv.append($("<span></span>").addClass("receiverText").text("Playback Receivers"));
			   $(this).append(reciverdiv);
			   $(this).append(reciverdiv);
			   appendEvents("input",addToPlanInfo,"click");
		});
		
		 
	}
	/*************************************************************************************************
	 * 
	 * ****************** This function is used for selected tv content building *********************
	 * 
	 * ************************************************************************************************/
	function addTvContent(groupNumber){
		   console.log("groupNumber   : "+groupNumber);
		   var receiverContent = $(".receiverContent");
		   var tvOptions = $("<div></div>").addClass("tvOptionsCont").attr("id","optionContent");
		   var noOfTvs_message = $("<span></span>").addClass("noOfTvOptions");
		   noOfTvs_message.append($("<span></span>").addClass("noOfTvOptionsMessage").text("TV "+groupNumber+" Select Receiver"));
		   tvOptions.append(noOfTvs_message);
		   var mandatoryAsterisk = $("<span></span>").addClass("mandatory").text("*");
		   tvOptions.prepend(mandatoryAsterisk);
		   $.each(arrOfRecievers, function(l, dataFeildList) {
			   var reciverdiv = $("<div></div>").addClass("tvOptions ");
			   $("div.feature_item#"+dataFeildList.dataFieldExID).remove();
			   console.log("div.feature_item#"+dataFeildList.dataFieldExID);
			   
			   
			   if($.inArray(dataFeildList.featureExID, tvFeatureExtIds) !== -1){
	        		 if(dataFeildList.featureExID != $.Constants.PLAYBACK_RECEIVERS){
	        			 var reciverJson ;
	  				   var selectedPriceTierJson = {}
	  				   selectedPriceTierArr[dataFeildList.featureExID] = dataFeildList.priceTierMap;
	  				   if(dataFeildList.dispalyInputValue != undefined){
	  					   reciverJson = dataFeildList.dispalyInputValue;
	  					   var count = l + groupNumber
	  					   reciverJson["reciverExternalId"] = dataFeildList.featureExID+"_"+count
	  				   }
	  				   var reciverRadio = $("<input />").attr({"type":"radio","id": dataFeildList.featureExID,"class":dataFeildList.featureExID+"_"+count,"name":"receivers_"+groupNumber,"dispalyinputvalue":JSON.stringify(reciverJson), "value":dataFeildList.dataFieldExID});
	  				   reciverdiv.append(reciverRadio);
	  				 reciverdiv.append($("<span></span>").addClass("receiverText").text(dataFeildList.description));
	  				   tvOptions.append(reciverdiv);
	        		 }
	        	}
			    
		   });
		   receiverContent.append($(".tooltipContent").before(tvOptions));
		   $("div.feature_item:last").after(receiverContent);
		   appendEvents("input",addToPlanInfo,"click");
	   }
	/*************************************************************************************************
	 * 
	 * ****************** This function is used for onload tv content building ************************
	 * 
	 * ************************************************************************************************/
	function buildTvContent(){
		   var tvHeading = $("<div></div>").addClass("tvHeading");
		   var noOfTvOptions = $("<span></span>").addClass("noOfTvOptions");
		   noOfTvOptions.append($("<span></span>").addClass("noOfTvOptionsMessage").text("TV 1 Select Receiver"));
		   
		   var mandatoryAsterisk = $("<span></span>").addClass("mandatory").text("*");
		   noOfTvOptions.prepend(mandatoryAsterisk);
		   tvHeading.append(noOfTvOptions);
		   var receiverContent = $("<div></div>").addClass("receiverContent");
		   var tooltipContent = $("<div></div>").addClass("tooltipContent");
		   var tvOptions = $("<div></div>").addClass("tvOptionsCont").attr("id","optionContent");
		   receiverContent.append(tvHeading);
		   console.log("arrOfRecievers :: "+JSON.stringify(arrOfRecievers));
		   console.log("arrOfRecievers length :: "+arrOfRecievers.length);
		   $.each(arrOfRecievers, function(l, dataFeildList) {
			   var reciverdiv = $("<div></div>").addClass("tvOptions ");
			   $("div.feature_item#"+dataFeildList.dataFieldExID).remove();
			   var reciverJson ;
			   var selectedPriceTierJson = {}
			   selectedPriceTierArr[dataFeildList.featureExID] = dataFeildList.priceTierMap;
			   if(dataFeildList.dispalyInputValue != undefined){
				   reciverJson = dataFeildList.dispalyInputValue;
				   var count = l + 1
				   reciverJson["reciverExternalId"] = dataFeildList.featureExID+"_"+count
			   }
			   var reciverRadio = $("<input />").attr({"type":"radio","id": dataFeildList.featureExID,"name":"receivers_1","value":dataFeildList.dataFieldExID,"dispalyinputvalue":JSON.stringify(reciverJson)}) ;//'<input type="radio" name="receivers_"+1+' value='+dataFeildList.dataFieldExID+ ' dispalyinputvalue='+JSON.stringify(dataFeildList.dispalyInputValue)+>';
			   reciverdiv.append(reciverRadio);
			   
			   
			   if($.inArray(dataFeildList.featureExID, tvFeatureExtIds) !== -1){
	        		reciverdiv.append($("<span></span>").addClass("receiverText").text(dataFeildList.description));
	        	}
	
			   tvOptions.append(reciverdiv);
		   });
		   receiverContent.append(tvOptions);
		   receiverContent.append(tooltipContent);
		   $("div.feature_item:last").after(receiverContent);
		   
			 //Default pharameter is one
			   buildConfigureTvs(1);
		   
	   }
	
	/*************************************************************************
	 * 
	 * ****************** This function is used for building tooltip informations ************************
	 * 
	 * @param noOfTv -- No of tv's count
	 * 
	 * ************************************************************************/
	function buildTvsTooltipInfo(){
        var need_help_ordering_addtvs = $("<div></div>").addClass("need_help_ordering_addtvs");
        var button = $("<button></button>").attr({"data-toggle":"popover","type":"button","data-html":"true","data-placement":"top","data-trigger":"hover click","data-content":$.Constants.TV_MESSAGE}).addClass("buttons").text("?");
        var need_help = $("<span></span>").addClass("need_help_ordering_title").text("Need help choosing?");
        var findOut = $("<span></span>").addClass("more_video_equipment").text("Find out more about Video Equipment");
        need_help_ordering_addtvs.append(button);
        need_help_ordering_addtvs.append(need_help);
        need_help_ordering_addtvs.append(findOut);
        $(".receiverContent").after(need_help_ordering_addtvs);
        $('[data-toggle="popover"]').popover();
	}
	/*************************************************************************
	 * 
	 * ****************** TV Configure box's building ************************
	 * 
	 * @param noOfTv -- No of tv's count
	 * 
	 * ************************************************************************/
	function buildConfigureTvs(noOfTv){
		  var configureContent = $("<div></div>").addClass("configureContent");
		  var configureHead = $("<div></div>").addClass("configureHead").attr({"id":"configHeadding"}).text("Configure TV Receiver Options");
		  var configureContentImage = $("<div></div>").addClass("configureContentImage");
		  var configureNoOfTvs = $("<div></div>").addClass("configureNoOfTvs").text("Configure TV"+noOfTv);
		  var configureImage = $("<img></img>").addClass("configure").attr({"src":"../image/tv-icon2.png"});
		  configureContentImage.append(configureImage);
		  configureContent.append(configureContentImage);
		  configureContent.append(configureNoOfTvs);
		  if(noOfTv == 1 && !$("#configHeadding").hasClass("configureHead")){
			  $(".receiverContent").before($(".configureListContent").append(configureHead));
		  }
		 
		  if(noOfTv == 1 && !$("#configureListContent").hasClass("configureListContent") ){
			  var configureListContent = $("<div></div>").addClass("configureListContent").attr({"id":"configureListContent"});
			  $(".receiverContent").before(configureListContent.append(configureHead));
			  configureListContent.append(configureContent);
			  $(".receiverContent").before(configureListContent);
		  }else{
			  $(".receiverContent").before($(".configureListContent").append(configureContent));
		  }
	}

	
function expandAccordion(){
	$(".panel:visible:eq(0)").find(".panel-collapse").removeClass("in").attr("aria-expanded","false").removeAttr("style");;
 	$(".panel:visible:eq(0)").find("span.indicator").addClass('glyphicon-triangle-bottom'); 
 	$(".panel:visible:eq(0)").find("span.leftindicator").removeClass('glyphicon-minus');
 	$(".panel:visible:eq(0)").find("span.leftindicator").addClass('glyphicon-plus');
 	
    $(".panel:visible").not(":eq(0)").find(".panel-collapse").removeClass("in").attr("aria-expanded","false").removeAttr("style");;
 	$(".panel:visible").not(":eq(0)").find("span.indicator").addClass('glyphicon-triangle-bottom'); 
 	$(".panel:visible").not(":eq(0)").find("span.leftindicator").removeClass('glyphicon-minus');
 	$(".panel:visible").not(":eq(0)").find("span.leftindicator").addClass('glyphicon-plus');

	$(".panel").each(function(){
	  if($(this).attr("id") != undefined && $(this).attr("id") != "panel1"){
		  var requereField = $(this).find(".error_tooltip").length 
		  if(requereField > 0){
		    console.log("length")
		   $(this).find(".panel-collapse").addClass("in").attr("aria-expanded","true").removeAttr("style");
		   $(this).find("span.indicator").addClass('glyphicon-triangle-bottom'); 
		   $(this).find("span.leftindicator").removeClass('glyphicon-plus');
		   $(this).find("span.leftindicator").addClass('glyphicon-minus');
		  }
	  }
	})
}
