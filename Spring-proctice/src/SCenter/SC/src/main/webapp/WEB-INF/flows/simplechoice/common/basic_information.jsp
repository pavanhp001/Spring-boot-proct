
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="basicinfo.header" /></title>

 
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.datepicker.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.all.css'/>" />

<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.widget.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>
<script src="<c:url value='/script/basic_information.js'/>"></script>
 <script src="<c:url value='/script/html_save.js'/>"></script>
 <script src="<c:url value='/js/blockUI.js'/>"></script>
<style>
#dialogue_content p {
    font-size: 16px;
    margin: 0 0 5px;
}
.coaching 
{
        color: green;
      
}
.Suggested
{
 color: grey;
 
}
.copyright {
    color: #757575;
    float: left;
    font-size: 10px;
    padding-left: 8px;
    padding-top: 4px;
    width: 20%;
}
.widget_container {
    background-color: #E5E0D9;
    float: left;
    height: auto;
    margin-top: -16px;
    width: 999px;
}
.pagecontainer {
    background-color: #E5E0D9;
    border: 1px solid #616264;
    display: table-cell;
    height: 284px;
    text-align: center;
    vertical-align: middle;
    width: 997px;
}
.basicinfoformcontent {
    background-color: #FFFFFF;
    border: 1px solid #616264;
    border-radius: 10px 10px 10px 10px;
    font-size: 12px;
    height: 270px;
    margin: 10px 0 10px 191px;
    padding: 8px 20px;
    width: 588px;
}
.pagecontainer {
    background-color: #E5E0D9;
    border: 1px solid #616264;
    display: table-cell;
    height: auto;
    text-align: center;
    vertical-align: middle;
    width: 997px;
}
.bottombuttons {
    left: 0;
    position: absolute;
    top: 570px;
    width: 100%;
    z-index: 1000;
}
#namePrefixList option { color: black;}
#nameSuffixList option { color: black; }
#unitTypeList option { color: black;}
#rentOwnList option { color:  black; }
#serviceAddressTypeList option { color: black; }
#stateList option { color:  black; }

.greyColor{ color: grey !important; }

</style>
 
<script type="text/javascript"> 

$(document).ready(function(){
var pageUrl = location.href;
var moveinDateValue = "${salescontext.scMap['moveInDate']}";
var isMoveInValue  = "${salescontext.scMap['isMoveInValue']}";
	if(pageUrl.indexOf("/movers")>=0)
	{
		$("#moveInDateDivId").css("display","none");
		$(".ismoveindatefield").parent().css("display","block");
	}
	if(pageUrl.indexOf("/nonmovers")>=0)
	{
		$("#moveInDateDivId").css("display","none");
	}
	if(isMoveInValue != '' &&  isMoveInValue == "yes"){
		$("#moveInDateDivId").css("display","block");
		$('#moveindateYesNo').val(isMoveInValue);
	}else if(isMoveInValue != '' &&  isMoveInValue == "no"){
		$('#moveindateYesNo').val(isMoveInValue);
	}
	$('input#datepicker').live("keyup",autoChange);
	$('input#datepickerElectric').live("keyup",autoChange);
	$('input#datepickerGas').live("keyup",autoChange);

	$('input#zipcode').live("blur",populateCityState);
	/*
		$("#datepicker").datepicker({
			beforeShow: function(input, inst){
				inst.dpDiv.css({marginTop:-input.offsetHeight+'px', marginLeft: input.offsetWidth + 'px'});
			}
		});

		$("#datepickerElectric").datepicker({
			beforeShow: function(input, inst){
				inst.dpDiv.css({marginTop:-input.offsetHeight+'px',marginLeft: input.offsetWidth + 'px'});
			}
		});

		$("#datepickerGas").datepicker({
			beforeShow: function(input, inst){
				inst.dpDiv.css({marginTop:-input.offsetHeight+'px',marginLeft: input.offsetWidth + 'px'});
			}
		});
*/
		$("#datepicker").datepicker();
		$("#datepickerElectric").datepicker();
		$("#datepickerGas").datepicker();
		
		
	    /*
	    $("#datepicker").datepicker({
	    	dateFormat: "mm/dd/yyyy",
	        showOn: "button",
	        buttonImage: "<%=request.getContextPath()%>/images/img/calendar.gif",
	        buttonImageOnly: true
	    });
	    $("#datepickerElectric").datepicker({
	        showOn: "button",
	        buttonImage: "<%=request.getContextPath()%>/images/img/calendar.gif",
	        buttonImageOnly: true
	    });
	    $("#datepickerGas").datepicker({
	        showOn: "button",
	        buttonImage: "<%=request.getContextPath()%>/images/img/calendar.gif",
	        buttonImageOnly: true
	    });
	    */
	    
	    $("select").live("change", function(){
        	$(this).css("color","#000000");	        
	    });
	   
	   
        $('#sameBillAddrFlagYes').click(function() {
                $('#bill_address').css('display', 'none');
        });
        $('#sameBillAddrFlagNo').click(function() {
                $('#bill_address').css('display', 'block');
        });

        var states = ${states};
        var namePrefix = ${namePrefix};
        var nameSuffix = ${nameSuffix};
        var unitType = ${unitType};
        var rentOwn = ${rentOwn};
        var serviceAddressType = ${serviceAddressType};
        var consumerPrefix = $('input#consumerPrefix').val();
        var consumerSuffix = $('input#consumerSuffix').val();
        var rentOwnValue = $('input#rentOwnValue').val();
        var dwellingType = $('input#dwellingType').val();
        
        var selectedState = $('input#selectedState').val();
        
        var datepickerElectric = $('input#datepickerElectric').val();
        var datepickerGas = $('input#datepickerGas').val();
        
        if($('input#datepickerElectric').val() == "Electric Start Date"){
        	$('.electricdtfield').hide();
        }
        if($('input#datepickerGas').val() == "Gas Start Date"){
           $('.gasdatefield').hide();
        }
            
		var stateList = states.items.item;
		$(stateList).each(function(){
			
			var txt = this.description;
			var val = this.lookupKey;
			if(val == selectedState){
					$("select#stateList").append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));
			}else{
				$("select#stateList").append($("<option></option>").attr("value", val).text(txt));
			}
		});

		var namePrefixList = namePrefix.items.item;
		$(namePrefixList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
			if(val == consumerPrefix){
				$("select#namePrefixList").append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));
			}
			else{
				$("select#namePrefixList").append($("<option></option>").attr("value", val).text(txt));
			}
		});

		var nameSuffixList = nameSuffix.items.item;
		$(nameSuffixList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;

			if(val == consumerSuffix){
				$("select#nameSuffixList").append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));
			}
			else{
				$("select#nameSuffixList").append($("<option></option>").attr("value", val).text(txt));
			}
		});

		var unitTypeList = unitType.items.item;
		$(unitTypeList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
			var selectedUnitType = $('input#selectedUnitType').val();
			if(selectedUnitType != val){
			    $("select#unitTypeList").append($("<option></option>").attr("value", val).text(txt));
			}
			else{
				$("select#unitTypeList").append($("<option></option>").attr({"value":val,"selected":"selected"}).text(txt));
			}
		});

		var rentOwnList = rentOwn.items.item;
		$(rentOwnList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
			if(val == rentOwnValue){
				$("select#rentOwnList").append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));
			}
			else{
			    $("select#rentOwnList").append($("<option></option>").attr("value", val).text(txt));
			}
		});

		var serviceAddressTypeList = serviceAddressType.items.item;
		$(serviceAddressTypeList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
			if(val == dwellingType){
				$("select#serviceAddressTypeList").append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));
			}
			else{
			    $("select#serviceAddressTypeList").append($("<option></option>").attr("value", val).text(txt));
			}
		});
	    $("#datepicker").live("change", function(){
        	$(this).css("color","#000000");	        
	    });
		$("input").live("keydown", function(e) {
			var charCode = (e.which) ? e.which : e.keyCode;
			//alert("Keycode :: "+charCode);
			if($(this).val() != ""){
			    if (charCode == 8){
			    	//alert("Keycode :: "+charCode);
					return true;
				}
			}else{
				if (charCode == 8 || charCode == 13 ){
					e.preventDefault(); 
					return false;
				}
			}
		});
    	
    	$("#namePrefixList").change(function () {
    	    if($(this).val() == ""){$(this).addClass("greyColor");}
    	    else {$(this).removeClass("greyColor")}
    	});
    	$("#namePrefixList").trigger("change");
        
    	$("#nameSuffixList").change(function () {
    		if($(this).val() == "") $(this).addClass("greyColor");
    	    else $(this).removeClass("greyColor")
    	});
    	$("#nameSuffixList").trigger("change");

    	$("#unitTypeList").change(function () {
    	    if($(this).val() == "") $(this).addClass("greyColor");
    	    else $(this).removeClass("greyColor")
    	});
    	$("#unitTypeList").trigger("change");
    	
    	$("#rentOwnList").change(function () {
    	    if($(this).val() == "") $(this).addClass("greyColor");
    	    else $(this).removeClass("greyColor")
    	});
    	$("#rentOwnList").trigger("change");

    	$("#serviceAddressTypeList").change(function () {
    	    if($(this).val() == "") $(this).addClass("greyColor");
    	    else $(this).removeClass("greyColor")
    	});
    	$("#serviceAddressTypeList").trigger("change");


    	$("#stateList").change(function () {
    	    if($(this).val() == "") $(this).addClass("greyColor");
    	    else $(this).removeClass("greyColor")
    	});
    	$("#stateList").trigger("change");

    	jQuery(window).load(function () {
  	      //To save the html on page load
  	      savePageHtml(true, "");
  	});
   	$("#moveindateYesNo").change(function (){
   		var isMoving = $("#moveindateYesNo").val();
   		if(isMoving == "yes"){
   			$("#moveInDateDivId").css("display","block");
   		}else if(isMoving == "no" || isMoving == ""){
   			$("#datepicker").val('');
   			$("#moveInDateDivId").css("display","none");
   		}
   	});
   	if(moveinDateValue != ''){
        $("input[name=datepicker1]").val(moveinDateValue);
    }
  // causing JS error
   	//Custom.init();
    	
});

var textFldIds = [ "", "", "", "" ];
/* Field Validation */

/*
$("input[type=='text']").live("blur", function(){
	for(var i = 0; i < textFldIds.length; i++) {
        if (textFldIds[i] === $(this).attr("id")) {
        	if($(this).val() == ""){
        		alert("Test");
        		if(!$(this).next().is('label')){
        	    	$(this).after("<label style='color:red;font-size:18px;'>*</label>");
        		}
        	}else{
        	    var element = $(this).next();
        		if($(this).next().is('label')){
        		    $(element).remove();
        		}
        	}
            $("#submitSt").val("yes");
        	$.each($("input[type=='text']"), function() {
        		for(var i = 0; i < textFldIds.length; i++) {
        	        if (textFldIds[i] === $(this).attr("id")) {
        	    		$("#submitSt").val("no");
        		    	//alert($(this).val());
        	        }
        		}
        	});
        }
    }
});
var selectFldIds=[ "", ""];
$('select').live("change", function() { 
	for(var i = 0; i < selectFldIds.length; i++) {
        if (selectFldIds[i] === $(this).attr("id")) {
        	if($(this).val() == ""){
        		alert("Test");
        		if(!$(this).next().is('label')){
        	    	$(this).after("<label style='color:red;font-size:18px;'>*</label>");
        		}
        	}else{
        	    var element = $(this).next();
        		if($(this).next().is('label')){
        		    $(element).remove();
        		}
        	}
            $("#submitSt").val("yes");
        	$.each($("input[type=='text']"), function() {
        		for(var i = 0; i < selectFldIds.length; i++) {
        	        if (selectFldIds[i] === $(this).attr("id")) {
        	    		$("#submitSt").val("no");
        		    	//alert($(this).val());
        	        }
        		}
        	});
        }
    }
});
*/
/* End Field Validation */

function submitForm(){
	for(var i = 0; i < textFldIds.length; i++) {
	    if (textFldIds[i] === $(this).attr("id")) {
	    	if($(this).val() == ""){
	    		if(!$(this).next().is('label')){
	    	    	$(this).after("<label style='color:red;font-size:18px;'>*</label>");
	    		}
	    	}else{
	    	    var element = $(this).next();
	    		if($(this).next().is('label')){
	    		    $(element).remove();
	    		}
	    	}
	        $("#submitSt").val("yes");
	    	$.each($("input[type=='text']"), function() {
	    	    if($(this).val() > 99){
	    	    	$("#submitSt").val("no");
	    		    //alert($(this).val());
	    		}
	    	});
	    }
	}
	$("input[type=='text']").blur();
}

	function capitalizeText(object) {
	 	try{
	        var splitedData = object.value.split(' ');
	        for (var i = 0, len = splitedData.length; i < len; i++){
	        	if(splitedData[i].length>1){
		        	splitedData[i] = splitedData[i].charAt(0).toUpperCase() + (splitedData[i].slice(1)).toLowerCase();
				}else{
					splitedData[i] = splitedData[i].charAt(0).toUpperCase();
				}
	        }
			var textData = splitedData.join(' ');
			
			if(object.name == "firstName" || object.name == "lastName"){
				splitedData = textData.split('-');
				for (var i = 0, len = splitedData.length; i < len; i++){
					if(splitedData[i].length>1){
			        	splitedData[i] = splitedData[i].charAt(0).toUpperCase() + splitedData[i].slice(1);
					}else{
						splitedData[i] = splitedData[i].charAt(0).toUpperCase();
					}
			    }
				textData = splitedData.join('-');   

				splitedData = textData.split("'");
				for (var i = 0, len = splitedData.length; i < len; i++){
					if(splitedData[i].length>1){
			        	splitedData[i] = splitedData[i].charAt(0).toUpperCase() + splitedData[i].slice(1);
					}else{
						splitedData[i] = splitedData[i].charAt(0).toUpperCase();
					}
			    }
				textData = splitedData.join("'");   
			}
			
	        object.value = textData;
	        
		}catch(err){alert(err);}
	}

		
	$(window).on('beforeunload', function(){
		$.blockUI({ message: $('#domMessage') }); 
	}); 
 </script>
 
</head>


					<section id="contentfullcont">
						<header id="content_header">
							<div class="row">
								<span>
									<svg version="1.1" id="Layer_5" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
										 width="29px" height="29px" viewBox="0 0 29 29" enable-background="new 0 0 29 29" xml:space="preserve" class="headphonesSVG">
										<g>
											<path fill="#96C43E" d="M29,25.298c0,2.066-1.677,3.744-3.744,3.744H4.411c-2.067,0-3.745-1.678-3.745-3.744V4.328
												c0-2.068,1.678-3.745,3.745-3.745h20.845C27.323,0.583,29,2.26,29,4.328V25.298z"/>
											<path fill="#FFFFFF" d="M23.861,18.677c0.116,0.435,0.177,0.891,0.177,1.362c0,2.921-2.367,5.289-5.288,5.289
												c-0.291,0-0.576-0.023-0.854-0.068l-0.004-0.002c-0.285,0.446-0.843,0.75-1.482,0.75c-0.932,0-1.686-0.64-1.686-1.428
												s0.754-1.428,1.686-1.428c0.563,0,1.061,0.234,1.367,0.594l0,0c0.208,0.029,0.422,0.044,0.641,0.044
												c2.19,0,3.967-1.542,3.967-3.445c0-0.307-0.046-0.604-0.132-0.887l-0.052,0.015c-0.244,0.015-0.527,0.008-0.855-0.031v0.608
												c0,0-0.059,0.398-0.808,0.375s-0.784-0.234-0.796-0.445c-0.012-0.21,0-5.5,0-5.5s0.047-0.409,0.819-0.397
												c0.655,0.012,0.737,0.246,0.761,0.375c0.023,0.128,0.023,0.784,0.023,0.784l0.374-0.059l0.088-0.058
												c0.189-0.664,0.265-1.24,0.265-1.966c0-4.188-3.395-7.583-7.582-7.583c-4.188,0-7.583,3.395-7.583,7.583
												c0,0.722,0.128,1.363,0.316,2.024l0.374,0.059c0,0,0-0.655,0.023-0.784s0.105-0.363,0.761-0.375
												c0.772-0.012,0.819,0.398,0.819,0.398s0.012,5.289,0,5.5s-0.047,0.422-0.796,0.445s-0.808-0.375-0.808-0.375v-0.608
												c-1.193,0.141-1.802-0.141-2.07-0.339c-0.27-0.199-1.065-0.913-0.738-2.293c0.328-1.381,1.451-1.58,1.674-1.58
												s0.164-0.07,0.164-0.07c-0.416-1.001-0.609-2.02-0.609-3.171c0-4.679,3.793-8.472,8.473-8.472c4.678,0,8.472,3.793,8.472,8.472
												c0,1.107-0.212,2.165-0.599,3.134l-0.046,0.037c0,0-0.059,0.07,0.163,0.07c0.223,0,1.346,0.199,1.674,1.58
												c0.205,0.863-0.029,1.465-0.299,1.845L23.861,18.677z"/>
										</g>
									</svg>
								</span>
								<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
								<span class="cell pageTitle"><fmt:message key="basicinfo.header"/></span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="basicInfo" class="basicInformationForm" name="basicInfo" action="${flowExecutionUrl}" method="post" autocomplete="off">
							<input type="hidden"  id="contextPath" value="<%=request.getContextPath()%>" />
				            <input type="hidden" id="selectedState" value="${salescontext.scMap['address.state']}">
				            <input type="hidden" id="consumerPrefix" value="${salescontext.scMap['consumer.name.prefix']}">
				            <input type="hidden" id="consumerSuffix" value="${salescontext.scMap['consumer.name.suffix']}">
				            <input type="hidden" id="selectedUnitType" value="${salescontext.scMap['unit.type']}">
				            <input type="hidden" id="rentOwnValue" value="${salescontext.scMap['rentOwnVal']}">
				            <input type="hidden" id="dwellingType" value="${salescontext.scMap['dwellingType']}">
				            <input type="hidden" id="pageTitle" name="pageTitle" value="Basic Information">
     			            <input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
							<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							<input type="hidden" id="callStartTimeInGreeting" name="callStartTimeInGreeting" value=""/>
							<div class="contentwrapper">
								<section id="dialogue_wrapper">						   
									<section id="dialogue_content">
										${dialogue}
									</section>
									<div id="dialogue_content_balloon"></div>						
								</section>
								<div id="action_wrapper">
									<div class="widget_container">
								<c:choose>
								<c:when test="${rrContextId == '00'}">											
										<div class="pagecontainer" align="center">
											<!-- Form Content -->
											<div class="basicinfoformcontent">
												<div class="fieldsrow">
													<div class="prefixfield">
														<select id="namePrefixList" name="prefix">
															<option value=""><fmt:message key="basicinfo.prefix"/></option>
														</select>
													</div>
													<div class="firstnmfield">
														<input id="firstName" name="firstName" type="text" class="text" value="${salescontext.scMap['consumer.name.first']}" maxlength="100" onchange="capitalizeText(this);" title="First Name" placeholder="First Name" />
													</div>
													<div class="middlenmfield">
														<input id="middleName" name="middleName" type="text" class="text" value="${salescontext.scMap['consumer.name.middle']}" onchange="capitalizeText(this);"  title="MI" size="2"  maxlength="1"  placeholder="MI" />
													</div>
													<div class="lastnmfield">
														<input id="lastName" name="lastName" type="text" class="text" value="${salescontext.scMap['consumer.name.last']}" onchange="capitalizeText(this);"  maxlength="100" title="Last Name" placeholder="Last Name"/>
														<!--  <input id="tel" name="tel" type="text" class="text" title="Last Name"/>  -->
													</div>
													<div class="suffixfield">
														<select id="nameSuffixList" name="suffix">
														   <option value="" ><fmt:message key="basicinfo.suffix"/></option>
														</select>
													</div>
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="addressfield">
														<input id="address1" name="address1" type="text" class="text addressfield" value="${salescontext.scMap['address.street1']}" onchange="capitalizeText(this);"  maxlength="100" title="Address 1"  placeholder="Address 1"/>
													</div>
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="unittypefield">
														<select name="addressTypeList" id="unitTypeList">
															<option value=""><fmt:message key="basicinfo.unittype"/></option>
														</select>
													</div>   
													<div class="unitnofield">
														<input id="unitNumber" name="unitNumber" type="text" class="text" value="${salescontext.scMap['unit.number']}" title="Unit Number" size="10" placeholder="Unit Number" />
													</div>
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="cityfield">
														<input id="city" name="city" type="text" class="text" value="${salescontext.scMap['address.city']}" onchange="capitalizeText(this);" maxlength="100" title="City"   placeholder="City"/>
													</div>
													<div class="statefield">
														<select id="stateList" name="state">
															<option value=""><fmt:message key="basicinfo.state"/></option>
														</select>
													</div>
													<div class="zipfield">
														<input id="zipcode" name="zipcode" type="text" class="text" value="${salescontext.scMap['address.zip']}" maxlength="10" title="Zip Code" placeholder="Zip Code" />
													</div>
														
														<img id="loader" width="18" height="18" alt="Loading ..." src="<%=request.getContextPath()%>/images/spinner1.gif" style="display:none; padding-left: 2px; vertical-align: top; padding-top: 1px;">
														<span id="error"></span>
													
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="addtypefield">
														<select id="serviceAddressTypeList" name="serviceAddrType">
															<option value=""><fmt:message key="basicinfo.dwellingtype"/>&nbsp;</option>
														</select>
													</div>
													<div class="ownrentfield">
														<select id="rentOwnList" name="rentOwnList">
															<option value=""><fmt:message key="basicinfo.ownorrent"/></option>
														</select>
													</div>
												</div><!-- Row -->
												<div class="fieldsrow" style="display: none">
													<div class="ismoveindatefield">
														<select id="moveindateYesNo" name="moveindateYesNo" >
															<option value=""><fmt:message key="basicinfo.moveindateYesNo"/></option>
															<option value="yes">Yes</option>
															<option value="no">No</option>
														</select>
													</div>
													<div class="moveindatefield" id="moveInDateDivId">
														&nbsp;&nbsp;&nbsp;<input type="text" id="datepicker" maxlength="10" name="datepicker1" value='<c:out value="${salescontext.scMap['moveInDate']}" escapeXml="false"/>'  title="Move-In Date" placeholder="Move in Date" />
													</div>
												</div>
												<div class="fieldsrow">
												<%-- 	<div class="moveindatefield" id="moveInDateDivId">
														<input type="text" id="datepicker" maxlength="10" name="datepicker1" value="${dob}" title="Move-In Date" placeholder="Move in Date" />
													</div> --%>
													<c:if test="${HideElectricDate eq 'false'}">
														<div class="electricdtfield">
															<input type="text" id="datepickerElectric" class="text" maxlength="10" name="datepicker2" value="${customerVO.dtRequestedStartDate}" 
																title="Electric Start Date" placeholder="Electric Start Date"/>
															<input type="hidden" id="datepicker2" class="text" size="12" maxlength="10" name="datepicker2"	value="${customerVO.dtRequestedStartDate}" 
																title="Electric Start Date" style="margin-right:3px;" />
														</div>
													</c:if>
													<c:if test="${HideGasDate eq 'false'}">
														<div class="gasdatefield">
															<input type="text" id="datepickerGas" class="text" size="12" maxlength="10" name="datepicker3" value="${customerVO.dtGasRequestedStartDate}"  
																title="Gas Start Date" placeholder="Gas Start Date"/>
															<input type="hidden" id="datepicker3" class="text" size="12" maxlength="10" name="datepicker3" value="${customerVO.dtGasRequestedStartDate}"  
																title="Gas Start Date" style="margin-right:3px;" />
														</div>
													</c:if>
												</div><!-- Row -->												
											</div>
											<!-- Form Content -->
										</div>
										</c:when>
										<c:otherwise>
										<div class="pagecontainer" align="center">
											<!-- Form Content -->
											<div class="basicinfoformcontent">
												<div class="fieldsrow">
													<div class="prefixfield">
														<select id="namePrefixList" name="prefix">
															<option value=""><fmt:message key="basicinfo.prefix"/></option>
														</select>
													</div>
													<div class="firstnmfield">
														<input id="firstName" name="firstName" type="text" class="text" value="${salescontext.scMap['consumer.name.first']}" maxlength="100" onchange="capitalizeText(this);" title="First Name" placeholder="First Name" />
													</div>
													<div class="middlenmfield">
														<input id="middleName" name="middleName" type="text" class="text" value="${salescontext.scMap['consumer.name.middle']}" onchange="capitalizeText(this);"  title="MI" size="2"  maxlength="1"  placeholder="MI" />
													</div>
													<div class="lastnmfield">
														<input id="lastName" name="lastName" type="text" class="text" value="${salescontext.scMap['consumer.name.last']}" onchange="capitalizeText(this);"  maxlength="100" title="Last Name" placeholder="Last Name"/>
														<!--  <input id="tel" name="tel" type="text" class="text" title="Last Name"/>  -->
													</div>
													<div class="suffixfield">
														<select id="nameSuffixList" name="suffix">
														   <option value="" ><fmt:message key="basicinfo.suffix"/></option>
														</select>
													</div>
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="addressfield">
														<input id="address1" name="address1" type="text" class="text addressfield" value="${salescontext.scMap['address.street1']}" onchange="capitalizeText(this);"  maxlength="100" title="Address 1"  placeholder="Address 1"/>
													</div>
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="unittypefield">
														<select name="addressTypeList" id="unitTypeList">
															<option value=""><fmt:message key="basicinfo.unittype"/></option>
														</select>
													</div>   
													<div class="unitnofield">
														<input id="unitNumber" name="unitNumber" type="text" class="text" value="${salescontext.scMap['unit.number']}" title="Unit Number" size="10" placeholder="Unit Number" />
													</div>
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="cityfield">
														<input id="city" name="city" type="text" class="text" value="${salescontext.scMap['address.city']}" onchange="capitalizeText(this);" maxlength="100" title="City"   placeholder="City"/>
													</div>
													<div class="statefield">
														<select id="stateList" name="state">
															<option value=""><fmt:message key="basicinfo.state"/></option>
														</select>
													</div>
													<div class="zipfield">
														<input id="zipcode" name="zipcode" type="text" class="text" value="${salescontext.scMap['address.zip']}" maxlength="10" title="Zip Code" placeholder="Zip Code" />
													</div>
														
														<img id="loader" width="18" height="18" alt="Loading ..." src="<%=request.getContextPath()%>/images/spinner1.gif" style="display:none; padding-left: 2px; vertical-align: top; padding-top: 1px;">
														<span id="error"></span>
													
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="addtypefield">
														<select id="serviceAddressTypeList" name="serviceAddrType">
															<option value=""><fmt:message key="basicinfo.dwellingtype"/>&nbsp;</option>
														</select>
													</div>
													<div class="ownrentfield">
														<select id="rentOwnList" name="rentOwnList">
															<option value=""><fmt:message key="basicinfo.ownorrent"/></option>
														</select>
													</div>
												</div><!-- Row -->
												<div class="fieldsrow" style="display: none">
													<div class="ismoveindatefield">
														<select id="moveindateYesNo" name="moveindateYesNo" >
															<option value=""><fmt:message key="basicinfo.moveindateYesNo"/></option>
															<option value="yes">Yes</option>
															<option value="no">No</option>
														</select>
													</div>
													<div class="moveindatefield" id="moveInDateDivId">
														&nbsp;&nbsp;&nbsp;<input type="text" id="datepicker" maxlength="10" name="datepicker1" value='<c:out value="" escapeXml="false"/>'  title="Move-In Date" placeholder="Move in Date" />
													</div>
												</div>
												<div class="fieldsrow">
												<%-- 	<div class="moveindatefield" id="moveInDateDivId">
														<input type="text" id="datepicker" maxlength="10" name="datepicker1" value="${dob}" title="Move-In Date" placeholder="Move in Date" />
													</div> --%>
													<c:if test="${HideElectricDate eq 'false'}">
														<div class="electricdtfield">
															<input type="text" id="datepickerElectric" class="text" maxlength="10" name="datepicker2" value="${customerVO.dtRequestedStartDate}" 
																title="Electric Start Date" placeholder="Electric Start Date"/>
															<input type="hidden" id="datepicker2" class="text" size="12" maxlength="10" name="datepicker2"	value="${customerVO.dtRequestedStartDate}" 
																title="Electric Start Date" style="margin-right:3px;" />
														</div>
													</c:if>
													<c:if test="${HideGasDate eq 'false'}">
														<div class="gasdatefield">
															<input type="text" id="datepickerGas" class="text" size="12" maxlength="10" name="datepicker3" value="${customerVO.dtGasRequestedStartDate}"  
																title="Gas Start Date" placeholder="Gas Start Date"/>
															<input type="hidden" id="datepicker3" class="text" size="12" maxlength="10" name="datepicker3" value="${customerVO.dtGasRequestedStartDate}"  
																title="Gas Start Date" style="margin-right:3px;" />
														</div>
													</c:if>
												</div><!-- Row -->												
											</div>
											<!-- Form Content -->
										</div>										
										</c:otherwise>
										</c:choose>										
									</div>
								</div>
								<div>
									<div>
										<div>
										    <div style="margin-top: 296px">
												<div class="leftbtns" style="margin-left:215px;">
													<div class="row" id="validatorMsg" style="display:none; color:#F00; margin: 40px 0px 0px 0" >
														<span>* Required Field</span>
													</div>
													<div class="row" id="poStatValidatorMsg" style="display:none; color:#F00;" >
														<span>"P O Box" address not allowed.</span>
													</div>
												</div>
											</div>
											<div class="bottombuttons">
												<div class="rightbtns">
													<input type="submit" id="basicInfoForward" name="basicInfoForward" value="Forward >" onclick="return goToConfirm();"/>
													<input type="hidden" id="_eventId" name="_eventId" value="basicInfoEvent">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							</form>
						</section><!--  Content -->
						<div id="domMessage" style="display:none;"> 
							<img src="<%=request.getContextPath()%>/images/loading.gif" border="0"/>
    						<h2>Loading</h2> 
						</div> 								
					</section><!-- Content Full Cont -->

