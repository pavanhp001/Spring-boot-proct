<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title>No Address Match</title>

 
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.datepicker.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.all.css'/>" />

<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.widget.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script> 
 
<style type="text/css">

.coaching {
	color: green;
	font-weight:bold;
}
.Suggested
{
 color: grey;
 font-weight:bold;
}
#block .row {
	float: left;
	width: 620px;
	/*border: 1px solid purple;	*/
}
.row{

float: left;
}

.row1{
float: left;
text-align:left;
}
.cell{
	float: left;
	padding: 2px;
	/*border: 1px solid orange;	*/
}
.basicinfoformcontent {
    background-color: #FFFFFF;
    border: 1px solid #616264;
     border-radius: 10px 10px 10px 10px;
    font-size: 12px;
    height: 225px;
    margin: 10px 0 10px 191px;
    padding: 8px 20px;
    width: 610px;
}
.basicziponlyformcontent {
    background-color: #FFFFFF;
    border: 1px solid #616264;
     border-radius: 10px 10px 10px 10px;
    font-size: 12px;
    height: 70px;
    margin: 10px 0 10px 191px;
    padding: 8px 20px;
    width: 610px;
}

.pagecontainer {
    background-color: #E5E0D9;
    border: 1px solid #616264;
    display: table-cell;
    text-align: center;
    vertical-align: middle;
    width: 997px;
    height:380px;
}
form fieldset {
    border: medium;
    margin: 0;
    padding: 0;
}
.nofoundrightbtns{
margin-top:28px;
margin-right:520px;
}


#unitTypeList option { color: black;}
#rentOwnList option { color:  black; }
#serviceAddressTypeList option { color: black; }
#stateList option { color:  black; }

.greyColor{ color: grey !important; }
</style>
 
 
<script type="text/javascript"> 
$(document).ready(function(){
	
		$('input#zipcode').live("blur",populateCityState);
	    $("select").live("change", function(){
        	$(this).css("color","#000000");	        
	    });

	    populateDefaultValuesToSelectBox();
	   
        $('#sameBillAddrFlagYes').click(function() {
                $('#bill_address').css('display', 'none');
        });
        $('#sameBillAddrFlagNo').click(function() {
                $('#bill_address').css('display', 'block');
        });
        
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
				
    	//No need of this, causing JS error
        //Custom.init();
});

$("#datepicker").datepicker();

/*
$("#datepicker").datepicker({
	beforeShow: function(input, inst){
		inst.dpDiv.css({marginTop: -input.offsetHeight + 'px', marginLeft: input.offsetWidth + 'px'});
	}
});

*/

var states = JSON.parse('<c:out value="${states}" escapeXml="false"/>');
var unitType =JSON.parse('<c:out value="${unitType}" escapeXml="false"/>');
var serviceAddressType =JSON.parse('<c:out value="${serviceAddressType}" escapeXml="false"/>');
var rentOwn = JSON.parse('<c:out value="${rentOwn}" escapeXml="false"/>');

function populateDefaultValuesToSelectBox(){
	try{
	        
		var stateList = states.items.item;
		var stateSelectedValue = $("input#statevalue").val();
		$(stateList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
				
			if(val == stateSelectedValue){
				$("select#stateList").attr({"style":"color:#000;"}).append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));	
			}else{
				$("select#stateList").append($("<option></option>").attr("value", val).text(txt));
			}
		});

		var unitTypeList = unitType.items.item;
		var selectedValue = $("input#unitTypevalue").val();
		$(unitTypeList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
			if(val == selectedValue){
				$("select#unitTypeList").attr({"style":"color:#000;"}).append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));	
			}else{
				$("select#unitTypeList").append($("<option></option>").attr("value", val).text(txt));
			}
		});

		var serviceAddressTypeList = serviceAddressType.items.item;
		var dwellingTypeselectedValue = $("input#dwellingTypeValue").val();
		$(serviceAddressTypeList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
			if(val == dwellingTypeselectedValue){
				$("select#serviceAddressTypeList").attr({"style":"color:#000;"}).append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));	
			}else{
				$("select#serviceAddressTypeList").append($("<option></option>").attr("value", val).text(txt));
			}
		});
			
		var rentOwnList = rentOwn.items.item;
		var rentOwnselectedValue = $("input#rentown").val();
		$(rentOwnList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
			if(val == rentOwnselectedValue){
				$("select#rentOwnList").attr({"style":"color:#000;"}).append($("<option></option>").attr({"value":val, "selected":"selected"}).text(txt));	
			}else{
				$("select#rentOwnList").append($("<option></option>").attr("value", val).text(txt));
			}
		});
	}catch(err){alert(err);}
}


var populateCityState = function(){
	$('#error').css('display','none');
	var zip = $(this).val();
	var zipLength = zip.length;
	var path = $("#contextPath").val();
	var url = path+"/salescenter/populate";
	var data = {};
	data["zipCode"] = zip;
	if((zipLength == 5 || zipLength == 10) && isValidZipNumber(zip)){
		$('#loader').css('display','inline');
		try{    
			$.ajax({
				type: 'POST',
				url: url,
				data: data,
				success: onCompletePopulateCityState,
				error: onError
			});
		} catch(e){
			alert(e);
		}
	}else{
		if(zipLength == 0){
			$('input#zipcode').after($('<span class="missingFields">*</span>'));
			$("div#validatorMsg").css('display','block');
		}else{
			$('#error').text('Invalid Zip');
			$('#error').css('color','#F00');
			$('#error').css('display','inline');
		}
	}
}



function isValidZipNumber(zip){
	var splittedValue = zip.split("-");
	if(splittedValue.length==2){
		if(!isNaN(splittedValue[0]) && !isNaN(splittedValue[1])){
			return true;
		}
	}else if(splittedValue.length==1){
		if(!isNaN(splittedValue[0])){
			return true;
		}
	}
	return false;
}



var onCompletePopulateCityState = function(data){
	if(data=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		data = JSON.parse(data);
		if($.isEmptyObject(data)){
			$('#loader').css('display','none');
			$('#error').text('Invalid Zip');
			$('#error').css('color','#F00');
			$('#error').css('display','inline');
		}else{
			var city = data.city;
			var state = data.state;
			$('#loader').css('display','none');
			if(city != undefined && city.trim() != ""){
				$('input#city').val(city);
			}
		
			if(state != undefined && state.trim() != ""){
				$('select#stateList').val(state);
				$('select#stateList').removeClass("greyColor");
			}

			if(city.trim()==""){
				$('#loader').css('display','none');
				$('#error').text('Invalid Zip');
				$('#error').css('color','#F00');
				$('#error').css('display','inline');
			}
		}
	}
}
   

var onError = function() {
	return function(data, textStatus, xhr, h4_id){
		alert("error\n" + data + ", " + textStatus + ", " + xhr);
	}
}




function goToConfirm(){
	$("span.missingFields").remove();
	
	var address1 = $('input#address1').val();
	var city = $('input#city').val();
	var zipcode = $('input#zipcode').val();
	var state = $('select#stateList').val();
	var dwellingtype = $('select#serviceAddressTypeList').val();
	var ownership = $('select#rentOwnList').val();
	var unitType = $('select#unitTypeList').val();
	var unitNum = $('input#unitNumber').val();
	
	var stat = true;
		
	if(address1 == null || address1 == "" ||address1=="Address 1"){
		 $('input#address1').after($('<span class="missingFields">*</span>'));
		 //$('input#address1').focus();
		 stat = false;
	}
	
	if(city == null || city == "" || city=="City"){
		 $('input#city').after($('<span class="missingFields">*</span>'));
		 //$('input#address1').focus();
		 stat = false;
	}
	
	if(zipcode == null || zipcode == "" || zipcode=="Zip Code"){
		 $('input#zipcode').after($('<span class="missingFields">*</span>'));
		 //$('input#address1').focus();
		 stat = false;
	}
	
	
	if(state == null || state == "" || state== "State"){
		 $('select#stateList').after($('<span class="missingFields">*</span>'));
		 //$('input#address1').focus();
		 stat = false;
	}
	
	if(dwellingtype==null || dwellingtype == "" || dwellingtype == "Service Address Type"){
		 $('select#serviceAddressTypeList').after($('<span class="missingFields">*</span>'));
		 //$('input#address1').focus();
		 stat = false;
	}
	
	if(ownership==null || ownership == "" || ownership == "addressOwnRent"){
		 $('select#rentOwnList').after($('<span class="missingFields">*</span>'));
		 //$('input#address1').focus();
		 stat = false;
	}
	if(unitType!=null && unitType!="" && unitType!="Unit Type"){
		if(unitNum == null || unitNum == "" || unitNum == "Unit Number"){
			$('input#unitNumber').after($('<span class="missingFields">*</span>'));
			stat = false;
		}
	}
	if(unitNum!=null && unitNum!="" && unitNum != "Unit Number"){
		if(unitType == null || unitType == "" || unitType == "Unit Type"){
			$('select#unitTypeList').after($('<span class="missingFields">*</span>'));
			stat = false;
		}
	}
	if(stat == false){
			$("span#validatorMsg").css('display','block');
	}else{
		$("span#validatorMsg").css('display','none');
	}

	$('input#isFromZipCode').val("No");
	
	if(document.getElementById("error").style.display=="inline"){
		stat = false;
	}

	if(document.getElementById("loader").style.display=="inline"){
		stat = false;
	}
	
	if(stat){
		//To save the html on page submit
		savePageHtml(false, "");
	}

	return stat;
}

var addressLineForZipOnly = '<c:out value="${addressLine1}" escapeXml="false"/>';
var unitNumberForZipOnly = '<c:out value="${unitNumber}" escapeXml="false"/>';
var cityForZipOnly = '<c:out value="${city}" escapeXml="false"/>';
var zipForZipOnly = '<c:out value="${zip}" escapeXml="false"/>';

function goToZipCodeOnly()
{
	try{
		$('input#address1').val(addressLineForZipOnly);
		$('input#unitNumber').val(unitNumberForZipOnly);
		$('input#city').val(cityForZipOnly);
		$('input#zipcode').val(zipForZipOnly);
			
		populateDefaultValuesToSelectBox();
			
		$('input#isFromZipCode').val("Yes");
			
		return true;
	}catch(err){
		alert(err);
		return false;
	}
	return false;
}


 </script>
</head>
	<section id="contentfullcont">
						<header id="content_header">
							<div class="row">
								<span class="cell">
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
								<span class="cell pageTitle">No Address Match</span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="basicInfo" name="basicInfo" action="<%=request.getContextPath()%>/salescenter/isvalidaddress" method="post" autocomplete="off">
							<input type="hidden"  id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="firstName"  name="firstName" value='<c:out value="${firstName}" escapeXml="false"/>'>
							<input type="hidden" id="lastName" name="lastName" value='<c:out value="${lastName}" escapeXml="false"/>'>
							<input type="hidden" id="datepicker1"  name="datepicker1" value= '<c:out value="${datepicker1}" escapeXml="false"/>'>
							<input type="hidden" id="datepicker2"  name="datepicker2" value='<c:out value="${datepicker2}" escapeXml="false"/>'>
							<input type="hidden" id="datepicker3"  name="datepicker3" value='<c:out value="${datepicker3}" escapeXml="false"/>'>
							<input type="hidden" id="unitTypevalue"   value='<c:out value="${unitTypevalue}" escapeXml="false"/>'>
							<input type="hidden" id="statevalue"   value='<c:out value="${state}" escapeXml="false"/>'>
							<input type="hidden" id="dwellingTypeValue"   value='<c:out value="${dwellingType}" escapeXml="false"/>'>
							<input type="hidden" id="rentown"   value='<c:out value="${rentown}" escapeXml="false"/>'>
							<input type="hidden" id="pageTitle" name="pageTitle" value="No Address Match">
							<input type="hidden" id="isFromZipCode" name="isFromZipCode">							
							<div class="contentwrapper">
								<section id="dialogue_wrapper">						   
									<section id="dialogue_content" style="color:green";>
										<!--  ${dialogue}. 
										<br/>
										<br/>  -->
										<ul>
											<li>
												Reconfirm address with customer.  Proceed to ZIP Only search only if unable to resolve address match.
											</li>
											<li>
												Verify that the address is correct prior to submitting a ZIP Only search.  The address cannot be changed after submission.
											</li>
										</ul>
									</section>
									<div id="dialogue_content_balloon"></div>	
								</section>
								
								<div id="action_wrapper">
									<div class="widget_container">
										<div class="pagecontainer" align="center">
											<!-- Form Content -->
										<div class="basicinfoformcontent">
											<fieldset>
												<span class="row"><br/>
													<span style="float:left;"><b>Verify address with customer:</b></span>
												</span>
												<div class="fieldsrow">
													<div class="addressfield">
														<input id="address1" name="address1" type="text" class="text addressfield" value='<c:out value="${addressLine1}" escapeXml="false"/>' title="Address 1" placeholder="Address 1"/>
													</div>
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="unittypefield">
														<select name="addressTypeList" id="unitTypeList">
															<option value=""><fmt:message key="basicinfo.unittype"/></option>
														</select>
													</div>   
													<div class="unitnofield">
														<input id="unitNumber" name="unitNumber" type="text" class="text" value='<c:out value="${unitNumber}" escapeXml="false"/>' title="Unit Number" size="10" placeholder="Unit Number" />
													</div>
												</div><!-- Row -->
												<div class="fieldsrow">
													<div class="cityfield">
														<input id="city" name="city" type="text" class="text" value='<c:out value="${city}" escapeXml="false"/>' maxlength="100" title="City"  placeholder="City"/>
													</div>
													<div class="statefield">
														<select id="stateList" name="state" >
															<option value=""><fmt:message key="basicinfo.state"/></option>
														</select>
													</div>
													<div class="zipfield">
														<input id="zipcode" name="zipcode" type="text" class="text" value='<c:out value="${zip}" escapeXml="false"/>' title="Zip Code" placeholder="Zip Code"/>
														<img id="loader" width="18" height="18" alt="Loading ..." src="<%=request.getContextPath()%>/images/spinner1.gif" style="display:none; padding-left: 2px; vertical-align: top; padding-top: 1px;"><span id="error"></span>
													</div>
						                       	</div>														
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
												</div>	
												<span class="row" id="validatorMsg" style="display:none;color:#F00;" >
													<span class="cell" style="font-weight:bold">* Required Fields</span>
												</span>		
											</fieldset>	
											<div class="rightbtns">
												<input  type="submit" id="addressNotFoundSubmit" name="addressNotFoundSubmit"   value="Forward >" onclick="return goToConfirm();"/>
											</div>
										</div>
										<br>
										<div class="basicziponlyformcontent">
											<fieldset>
					    						<span class="row1"> 
													<b>Entered Service Address from Last page : </b>
												</span>
												<br><br>  
												<div class="row1">
													<c:out value="${completeAddress}" escapeXml="false"/>
												</div>
												<div class="rightbtns">
													<input type="submit" id="zipOnlySearchId" title="Zip Only" name="zipOnlySearch" value="Zip Only" onclick="return goToZipCodeOnly();">
												</div>
											</fieldset>
										</div>
										
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
						<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
					</form>
				</section><!--  Content -->
			</section><!-- Content Full Cont -->

					