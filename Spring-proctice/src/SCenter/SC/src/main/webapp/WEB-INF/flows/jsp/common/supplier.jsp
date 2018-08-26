<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%><head>
<title>Supplier Selection</title>
<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/warmTransfer.css'/>"/>
<link media="all" href="<c:url value='/style/jquery.ui.all.css'/>" type="text/css" rel="stylesheet">
<script>
$(document).ready(function(){
	$("#isSupplierListEmpty").val("false");
	$('.label_desc').html(function(index, text){  
		text = text.replace( /\.\s\s+/g, '. ' );
		text = text.replace( /\.\s+/g, '.&nbsp;&#32;' );
	    return text;
	});
    
	$("span.missingFields").remove();
	
	/*  this method  trigger  when change and keyup events  occuerring in every select box   */
	 $("select").bind("keyup change",function(event){
         var previousValues = $(this).data($(this).attr('id'));
	    	var theValue = $(this).val();
	         $(this).data($(this).attr('id'), theValue);
	         if(event.keyCode != 9 && event.keyCode !=16){
             activate($(this).attr('id') , previousValues);}
           });
	
	var isProductServiceCallCompleted = $("#isProductServiceCallCompleted").val();
	if(isProductServiceCallCompleted!="" && isProductServiceCallCompleted=="false")
	{
		$('#Supplier_Selection_SCFE_Data_6').after('&nbsp;&nbsp;<a class="reload" id="refreshSuppliersListId" href="#" onclick="refreshSuppliersList()">Refresh Suppliers</a>');
	}

	$('select[id=Supplier_Selection_SCFE_Data_11]').live('change', function(){
		var selectedValue = $(this).val();
		if(selectedValue != "" && selectedValue == "No"){
			var previousElement = $('#Supplier_Selection_SCFE_Data_5');
			if(previousElement.is(':visible') && previousElement.val() == "No"){
				var selectedSupplier = $('#supplier_round_robin').val();
				roundRobinRejected(selectedSupplier);
				var isDisabled = $('#NextSupplier').prop('disabled');
				if(isDisabled){
					$("#NextSupplier").prop("disabled",false);
				}
				activateAll('Supplier_Selection_SCFE_Data_11', "");
			}
		}
	});


	$('#NextSupplier').click(function(){
		$("span.missingFields").remove();
		//To save the html on round robin process
	    savePageHtml(false, "");
	    
		var path ="<%=request.getContextPath()%>";
		//displaying loader.
		$(this).after('<img id="loader" width="18" height="18" alt="Loading ..." src="'+path+'/images/spinner1.gif" style="padding-left: 2px; vertical-align: middle; padding-top: 1px;">');
		
		var url = path+"/salescenter/getRoundRobinSupplier";
		$.ajax({
			type: 'GET',
			url: url,
			success: onCompleteRoundRobin,
		});
	});

	
	$(':text').each(function(){
		var id = $(this).attr('id');
		var name = $(this).attr('name');
		
			if(id=="MailingState_disabled"){
				$(this).replaceWith('<select id='+id+' name='+name+' class=addressStateOrProvince>'+
						'<option value="">State</option>'+
						+'</select>') ;
				populateOptionValue(id);
			}
			if(id=="MailingLine2type_disabled"){
				$(this).replaceWith('<select id='+id+' name='+name+' class=addressUnitType>'+
						'<option value="">Unit Type</option>'+
						+'</select>') ;
				populateOptionValueforUnitType(id);
			}
			
		
	});


	$('input#MailingZipCode_disabled').attr('maxlength','10');
	$('input#MailingZipCode_disabled').live("blur",populateCityState);

  
	$('#Supplier_Selection_SCFE_Data_6').live('change',function()
	{
		var selectedValue=$(this).val();
	    if(selectedValue != "Supplier Not Participating" && selectedValue !="" && selectedValue.trim().length>0){
	    	updateMailAddressFields();
		}
	});

	$('select[id=Supplier_Selection_SCFE_Data_11]').live('change', function(){
		var selectedValue = $(this).val();
		if(selectedValue == "Yes"){
			updateMailAddressFields();
		}
	});
	$('#Supplier_Selection_SCFE_Data_6').live('change keyup',function()
			{
				var selectedValue=$(this).val();
			    if(selectedValue != "Supplier Not Participating" && selectedValue !="" && selectedValue.trim().length>0){
			    	updateMailAddressFields();
				}
			});

	$('select[id=Supplier_Selection_SCFE_Data_11]').live('change keyup', function(){
				var selectedValue = $(this).val();
				if(selectedValue == "Yes"){
					updateMailAddressFields();
				}
			});

	jQuery(window).load(function () {

		$('#NextSupplierDiv').css('background-color','#FFFFFF');
	       //To save the html on page load
	       savePageHtml(true, "");
	 });
});

var states = {"items":{"item":[{"id":1056,"context":2,"description":"Alabama","lookupKey":"AL"},
                               {"id":1057,"context":2,"description":"Alaska","lookupKey":"AK"},
                               {"id":1058,"context":2,"description":"Arizona","lookupKey":"AZ"},
                               {"id":1059,"context":2,"description":"Arkansas","lookupKey":"AR"},
                               {"id":1060,"context":2,"description":"California","lookupKey":"CA"},
                               {"id":1061,"context":2,"description":"Colorado","lookupKey":"CO"},
                               {"id":1062,"context":2,"description":"Connecticut","lookupKey":"CT"},
                               {"id":1063,"context":2,"description":"Delaware","lookupKey":"DE"},
                               {"id":1064,"context":2,"description":"District Of Columbia","lookupKey":"DC"},
                               {"id":1065,"context":2,"description":"Florida","lookupKey":"FL"},
                               {"id":1066,"context":2,"description":"Georgia","lookupKey":"GA"},
                               {"id":1067,"context":2,"description":"Hawaii","lookupKey":"HI"},
                               {"id":1068,"context":2,"description":"Idaho","lookupKey":"ID"},
                               {"id":1069,"context":2,"description":"Illinois","lookupKey":"IL"},
                               {"id":1070,"context":2,"description":"Indiana","lookupKey":"IN"},
                               {"id":1071,"context":2,"description":"Iowa","lookupKey":"IA"},
                               {"id":1072,"context":2,"description":"Kansas","lookupKey":"KS"},
                               {"id":1073,"context":2,"description":"Kentucky","lookupKey":"KY"},
                               {"id":1074,"context":2,"description":"Louisiana","lookupKey":"LA"},
                               {"id":1075,"context":2,"description":"Maine","lookupKey":"ME"},
                               {"id":1076,"context":2,"description":"Maryland","lookupKey":"MD"},
                               {"id":1077,"context":2,"description":"Massachusetts","lookupKey":"MA"},
                               {"id":1078,"context":2,"description":"Michigan","lookupKey":"MI"},
                               {"id":1079,"context":2,"description":"Minnesota","lookupKey":"MN"},
                               {"id":1080,"context":2,"description":"Mississippi","lookupKey":"MS"},
                               {"id":1081,"context":2,"description":"Missouri","lookupKey":"MO"},
                               {"id":1082,"context":2,"description":"Montana","lookupKey":"MT"},
                               {"id":1083,"context":2,"description":"Nebraska","lookupKey":"NE"},
                               {"id":1084,"context":2,"description":"Nevada","lookupKey":"NV"},
                               {"id":1085,"context":2,"description":"New Hampshire","lookupKey":"NH"},
                               {"id":1086,"context":2,"description":"New Jersey","lookupKey":"NJ"},
                               {"id":1087,"context":2,"description":"New Mexico","lookupKey":"NM"},
                               {"id":1088,"context":2,"description":"New York","lookupKey":"NY"},
                               {"id":1089,"context":2,"description":"North Carolina","lookupKey":"NC"},
                               {"id":1090,"context":2,"description":"North Dakota","lookupKey":"ND"},
                               {"id":1091,"context":2,"description":"Ohio","lookupKey":"OH"},
                               {"id":1092,"context":2,"description":"Oklahoma","lookupKey":"OK"},
                               {"id":1093,"context":2,"description":"Oregon","lookupKey":"OR"},
                               {"id":1094,"context":2,"description":"Pennsylvania","lookupKey":"PA"},
                               {"id":1095,"context":2,"description":"Puerto Rico","lookupKey":"PR"},
                               {"id":1096,"context":2,"description":"Rhode Island","lookupKey":"RI"},
                               {"id":1097,"context":2,"description":"South Carolina","lookupKey":"SC"},
                               {"id":1098,"context":2,"description":"South Dakota","lookupKey":"SD"},
                               {"id":1099,"context":2,"description":"Tennessee","lookupKey":"TN"},
                               {"id":1100,"context":2,"description":"Texas","lookupKey":"TX"},
                               {"id":1101,"context":2,"description":"Utah","lookupKey":"UT"},
                               {"id":1102,"context":2,"description":"Vermont","lookupKey":"VT"},
                               {"id":1103,"context":2,"description":"Virginia","lookupKey":"VA"},
                               {"id":1104,"context":2,"description":"Virgin Islands, British","lookupKey":"VG"},
                               {"id":1105,"context":2,"description":"Virgin Islands, U.S.","lookupKey":"VI"},
                               {"id":1106,"context":2,"description":"Washington","lookupKey":"WA"},
                               {"id":1107,"context":2,"description":"West Virginia","lookupKey":"WV"},
                               {"id":1108,"context":2,"description":"Wisconsin","lookupKey":"WI"},
                               {"id":1109,"context":2,"description":"Wyoming","lookupKey":"WY"}]}};


var unitType= {"items":[{"unit":"APT","unitType":"Apartment"},
						{"unit":"BLDG","unitType":"Building"},
						{"unit":"DEPT","unitType":"Department"},
						{"unit":"HNGR","unitType":"Hanger"},
						{"unit":"FL","unitType":"Floor"},
						{"unit":"LOT","unitType":"Lot"},
						{"unit":"OFC","unitType":"Office"},
						{"unit":"SPC","unitType":"Space"},
						{"unit":"STE","unitType":"Suite"},
						{"unit":"TRLR","unitType":"Trailer"},
						{"unit":"UNIT","unitType":"Unit"}]};

function populateOptionValue(id){
	var stateList = states.items.item;
	$(stateList).each(function(){
		var txt = this.description;
		var val = this.lookupKey;
		$("select[id='"+id+"']").append($("<option></option>").attr("value", val).text(txt));
	});
}

function populateOptionValueforUnitType(id){
	var unitTypeList = unitType.items;
	$(unitTypeList).each(function(){
		var txt = this.unitType;
		var val = this.unit;
		$("select[id='"+id+"']").append($("<option></option>").attr("value", val).text(txt));
	});
}


function populateCityState(){
	$("span.missingFields").remove();
	$("div#validatorMsg").css('display','none');
	var zip = $(this).val();
	var zipLength = zip.length;
	var path ="<%=request.getContextPath()%>";
	var url = path+"/salescenter/populate";
	var data = {};
	data["zipCode"] = zip;
		if((zipLength == 5 || zipLength == 10) && isValidZipNumber(zip)){
		var isLoaderRunning = false;
		$("#loader").each(function(){
			if($("#loader").is(":visible"))
			{
				isLoaderRunning = true;
			}
		});
		
		if(!isLoaderRunning){
			$(this).after('<img id="loader" width="18" height="18" alt="Loading ..." src="'+path+'/images/spinner1.gif" style="padding-left: 2px; vertical-align: middle; padding-top: 1px;">');
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
		}
	}else{
		if(zipLength == 0){
			$('input#MailingZipCode_disabled').after($('<span class="missingFields">*</span>'));
			$("span#selectSpan").text("* Required Field.");
			stat = false;
		}else{
			$('input#MailingZipCode_disabled').after($('<span class="missingFields">*</span>'));
			$("span#selectSpan").text("* Invalid Zip.");
			stat = false;
		}
		if(!stat){
			$("div#validatorMsg").css('display','block');
		}else{
			$("div#validatorMsg").css('display','none');
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
	$("div#validatorMsg").css('display','none');
	if(data=="sessionTimeOut"){
		var path = $("#contextPath").val();
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		data = JSON.parse(data);
		if($.isEmptyObject(data)){
			$('#loader').remove();
			$('input#MailingZipCode_disabled').after($('<span class="missingFields">*</span>'));
			$("span#selectSpan").text("* Invalid Zip.");
			$("div#validatorMsg").css('display','block');
		}else{
			var city = data.city;
			var state = data.state;
			$('#loader').remove();
			if(city != undefined && city.trim() != ""){
				$('input#MailingCity_disabled').val(city);
			}
		
			if(state != undefined && state.trim() != ""){
				$('select#MailingState_disabled').val(state);
			}
			if(city.trim()==""){
				
				$('input#MailingZipCode_disabled').after($('<span class="missingFields">*</span>'));
				$("span#selectSpan").text("* Invalid Zip.");
				$("div#validatorMsg").css('display','block');
			}
		}
	}
}



function updateMailAddressFields(){
	try{

		if($("span.missingFields").is(":visible"))
		{
			$("span.missingFields").remove();
		}

	var tempAddress1 = "";
		
	var streetNumber = '${address.streetNumber}';
		
	var streetName = '${address.streetName}';
	var streetType = '${address.streetType}';
	var prefixDirectional = '${address.prefixDirectional}';
	var postfixDirectional = '${address.postfixDirectional}';
	var postalCode='${address.postalCode}';
		
	var city='${address.city}';
	var state='${address.stateOrProvince}';
		
	var addressline2='${address.line2}';
	    	

	if(streetNumber!=null && streetNumber.length>0){
		tempAddress1 = tempAddress1+streetNumber;
	}
	
	if((streetNumber!=null && streetNumber.length>0) && ((streetName!=null && streetName.length>0) || (streetType!=null && streetType.length>0) || (postfixDirectional!=null &&postfixDirectional.length>0) || (prefixDirectional!=null &&prefixDirectional.length>0))){
		tempAddress1 = tempAddress1+" ";
	}
	if(prefixDirectional!=null && prefixDirectional.length>0){
		tempAddress1 = tempAddress1+prefixDirectional+" ";
	}
	
	if(streetName!=null && streetName.length>0){
		tempAddress1 = tempAddress1+streetName;
	}
	
	if((streetName!=null && streetName.length>0) && ((streetType!=null && streetType.length>0) || (postfixDirectional!=null &&postfixDirectional.length>0))){
		tempAddress1 = tempAddress1+" ";
	}
	
	if(streetType!=null && streetType.length>0){
		tempAddress1 = tempAddress1+streetType;
	}
		
	if((streetType!=null && streetType.length>0) && (postfixDirectional!=null &&postfixDirectional.length>0)){
		tempAddress1 = tempAddress1+" ";
	}
		
	if(postfixDirectional!=null &&postfixDirectional.length>0){
			tempAddress1 = tempAddress1+postfixDirectional;
	}
	
	if(addressline2!=null && addressline2.length>0){
		var splitedArray = addressline2.split(" ");
		
		var unitType = "";
		var unitNumber="";
		if(splitedArray.length==2){
			unitType =splitedArray[0];
		    unitNumber=splitedArray[1];
		}
	
	}
	$("#MailingStreetAddress_disabled").val(tempAddress1);
	$("#MailingLine2type_disabled").val(unitType);
	
	$("#MailingLine2type_disabled option").each(function(i){
		if($(this).text()==unitType){
			$(this).prop('selected', true);
		}
	});
	
	$("#MailingLine2Info_disabled").val(unitNumber);
	$("#MailingState_disabled").val(state);
	
	$("#MailingZipCode_disabled").val(postalCode);
	$("#MailingCity_disabled").val(city);
	}catch(err){alert(err);}
}

function updatePreferredSupplier()
{
	try{
		var previousElement = $('#Supplier_Selection_SCFE_Data_5');
		if(previousElement.is(':visible') && previousElement.val() == "Yes"){
			var selectedSupplier = $('#Supplier_Selection_SCFE_Data_6').val();
			if(selectedSupplier != "Supplier Not Participating" )
			{
				var data = {"selectedSupplier" : selectedSupplier};
				var path ="<%=request.getContextPath()%>";
				var url = path+"/salescenter/updatePreferredSupplier";
				$.ajax({
					type: 'POST',
					url: url,
					data: data,
					success: function(data){
						if(data=="sessionTimeOut"){
							var path = "<%=request.getContextPath()%>";
							window.location.href = path+"/salescenter/session_timeout";
						}
					}
				});
			}
		}
	}catch(err){alert(err);}
}

var isRoundRabinSupplier = false;
var onCompleteRoundRobin = function(data){
	try{
		if(data=="sessionTimeOut"){
			var path = "<%=request.getContextPath()%>";
			window.location.href = path+"/salescenter/session_timeout";
		}else{
			isRoundRabinSupplier = true;
			$('#loader').remove();
			if(data!="" && data!="nil"){
				var roundRobinSupplier = data;
				var previous_round_robin_supplier = $("#supplier_round_robin").val();
				
				$("#previously_selected_supplier").val(previous_round_robin_supplier);
				$("#supplier_round_robin").val(roundRobinSupplier);
				$("#selectedSupplierHiddenVal").val(roundRobinSupplier);
				replaceText(roundRobinSupplier);
				activateAll('Supplier_Selection_SCFE_Data_5', "");
				var previousValues = $('#Supplier_Selection_SCFE_Data_11').data('Supplier_Selection_SCFE_Data_11');
			    if(previousValues !=undefined){
				activate('Supplier_Selection_SCFE_Data_11',previousValues);
				 }
				$("#NextSupplier").attr("disabled", "disabled");
				$("#isSupplierListEmpty").val("false");
			}
			else
			{
				$("#isSupplierListEmpty").val("true");
				alert("Suppliers not available.");
			}
		}
	}catch(err){
		alert(err);
	}
}

function refreshSuppliersList(){
	try{
		savePageHtml(false, "");
		var path ="<%=request.getContextPath()%>";
		var url = path+"/salescenter/getSuppliersList";
		$.ajax({
			type: 'GET',
			url: url,
			dataType: "html",
			success: onCompleteGetSuppliersList,
		});
		jQuery(window).load(function () {
		       //To save the html on page load
		       savePageHtml(true, "");
		 });
	   
	}catch(err){alert(err);}
}


function onCompleteGetSuppliersList(data)
{
	try{
		if(data=="sessionTimeOut")
		{
			var path = "<%=request.getContextPath()%>";
			window.location.href = path+"/salescenter/session_timeout";
		}
		else
		{
			if(data!=undefined && data!="" && data.length>0)
			{
				var jsonArray = JSON.parse(data);
				if(jsonArray.length>0)
				{
					if($("#Supplier_Selection_SCFE_Data_6 option").length>0)
					{
						$("#Supplier_Selection_SCFE_Data_6 option").remove();
					}
					
					$("#Supplier_Selection_SCFE_Data_6").append('<option value=""> Please Select </option>')
				    $("#Supplier_Selection_SCFE_Data_6").append('<option value="Supplier Not Participating">Supplier Not Participating</option>')
					for(i=0;i<jsonArray.length;i++)
					{
						$("#Supplier_Selection_SCFE_Data_6").append('<option value="'+jsonArray[i]+'">'+jsonArray[i]+'</option>')
					}
					$("#refreshSuppliersListId").remove();
					$("#isProductServiceCallCompleted").val("true");
				}
				else
				{
					alert("Suppliers not available.");
				}
			}
			else
			{
				alert("Suppliers not found.");
			}
		}
	}catch(err){alert(err);}	
}

function roundRobinRejected(selectedSupplier){

	var data = {"selectedSupplier" : selectedSupplier};
	var path ="<%=request.getContextPath()%>";
	var url = path+"/salescenter/rejectSelectedSupplier";
	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		success: function(data){
			try{
				if(data=="sessionTimeOut"){
					var path = "<%=request.getContextPath()%>";
					window.location.href = path+"/salescenter/session_timeout";
				}
			}catch(err){
				alert(err);
			}
		}
	});
}


function updateRoundRobinAcceptedSupplier(){
	var previousElement = $('#Supplier_Selection_SCFE_Data_5');
	if(previousElement.is(':visible') && previousElement.val() == "No"){
		var customerAcceptedSupplier = $('#Supplier_Selection_SCFE_Data_11');
		if(customerAcceptedSupplier.is(':visible') && customerAcceptedSupplier.val() == "Yes"){
			var selectedSupplier = $('#supplier_round_robin').val();
			var data = {"selectedSupplier" : selectedSupplier};
			var path ="<%=request.getContextPath()%>";
			var url = path+"/salescenter/roundRobinSupplierAccepted";
			$.ajax({
				type: 'POST',
				url: url,
				data: data,
				success: function(data){
					try{
						if(data=="sessionTimeOut"){
							var path = "<%=request.getContextPath()%>";
							window.location.href = path+"/salescenter/session_timeout";
						}
					}catch(err){
						alert(err);
					}
				}
			});
		}
	}
}

var showDataFieldMap = new Object();
var hideDataFieldMap = new Object();
var enableFieldsMap = new Object();
var enableFieldArray = new Array();
var displayList = new Array();

function activate(datafieldId, previousValues) {
	try{

		//$('.spanDefaultClass').remove();
		if(datafieldId=='Supplier_Selection_SCFE_Data_5'){
			$("#selectedSupplierHiddenVal").val("");
			if(datafieldId=='Supplier_Selection_SCFE_Data_5' && ($('#Supplier_Selection_SCFE_Data_5 option:selected').text())=="No"){
				$('.spanDefaultClass').remove();
				activateAll('Supplier_Selection_SCFE_Data_5', previousValues);

				if($('#NextSupplierDiv').parent().is(':hidden')){
					$('#NextSupplierDiv').parent().css({
						"display":"block",
						"padding-left":"385px",
					});
				}
				$('#NextSupplierDiv').css("display", "block");
				$('#Supplier_Selection_SCFE_Data_10_FS').hide();
				$('#Supplier_Selection_SCFE_Data_11_FS').hide();
				var isDisabled = $('#NextSupplier').prop('disabled');
				if(isDisabled){
					$("#NextSupplier").prop("disabled",false);
				}
				
			}
			else{
				if(isRoundRabinSupplier)
				{
					var selectedSupplier = $('#supplier_round_robin').val();
					roundRobinRejected(selectedSupplier);
				}
				isRoundRabinSupplier = false;
				activateAll(datafieldId, previousValues);
				$('.spanDefaultClass').remove();
				$('#NextSupplierDiv').css("display", "none");
			}
		}else{
			if(datafieldId == 'Supplier_Selection_SCFE_Data_6'){
				$("#selectedSupplierHiddenVal").val("");
				var customer_selected_supplier = $('#Supplier_Selection_SCFE_Data_6 option:selected').val();
				var previous_round_robin_supplier = $("#supplier_round_robin").val();
				if(customer_selected_supplier != "" )
				{
					$("#selectedSupplierHiddenVal").val(customer_selected_supplier);
					if(customer_selected_supplier != "Supplier Not Participating"){
						$("#previously_selected_supplier").val(previous_round_robin_supplier);
						$("#supplier_round_robin").val(customer_selected_supplier);	
						replaceText(customer_selected_supplier);
					}
					
				}
			}
			activateAll(datafieldId, previousValues);
		}
		var n=1;
		$('input, select').each(function() {
			if($(this).is(":visible")) {
		    	$(this).attr('tabindex', n++);
			}
		}); 
	}catch(err){
		alert(err);
	}
}

function activateAll(datafieldId, previousValues){
	try{
		createMap();
		var datafieldIdVal= $('#' + datafieldId).val();
		var selectedOptin = datafieldIdVal!=""?datafieldIdVal:previousValues;
		var arr;

		var boolY = 'BOOL_Y_';
		var boolN = 'BOOL_N_';
	
		if(datafieldId.indexOf(boolY) > -1) {
			datafieldId = datafieldId.substr(boolY.length);
		} else if(datafieldId.indexOf(boolN) > -1) {
			datafieldId = datafieldId.substr(boolN.length);
		}
		var optionMap = showDataFieldMap[datafieldId];
		
		var showIdList = optionMap[selectedOptin];
		
		if(datafieldId=="Supplier_Selection_SCFE_Data_6" && showIdList == undefined)
		{
			showIdList = optionMap["{allSuppliers}"];
		}
		var hideDataList = new Array();
		if(showIdList != undefined) {
			var isSelectedSupplierUpdated = false;
			for ( var i = 0; i < showIdList.length; i++) {
				var id = showIdList[i].replace("/", "_");
				  if(datafieldIdVal!= ""){
					  if($('[id=' + id + '_FS]').parent().is(':hidden')){
							$('[id=' + id + '_FS]').parent().css( "display", "block" );
						}
						$('[id=' + id + '_FS]').show();
						selectDefaultValue(id);
				     }else{
					     
				    	 /*colapse feilds for selected element value  when value is  please select*/
					       $('[id=' + id + '_FS]').hide();
					       if($('[id=' + id + '_FS]').parent().is(':visible')){
								$('[id=' + id + '_FS]').parent().css( "display", "none" );
							}
						    if($("#"+id).val() != undefined){
						    	collapseDepdentFields(id, $("#"+id).val());
						    	
					                    }
					                }
	                }
			if(!(datafieldIdVal!= ""))return;
		}
		var i = 0;
		<c:forEach var="extId" items="${allDataFieldList}">
			var result = in_array(showIdList, '${extId}');
			if(!(result == true)){
				hideDataList.push('${extId}');
			}
		</c:forEach>
		var optionMap2 = hideDataFieldMap[datafieldId];
		var hideIdList = optionMap2[selectedOptin];
		if(hideIdList != undefined){
			for ( var i = 0; i < hideIdList.length; i++) {
				var id = hideIdList[i].replace("/", "_");
				//if id is found in the hideDataList, then only hide the data element; if not, don't hide the element
				var is_present = in_array(hideDataList, hideIdList[i]);
				if(is_present){
					$('[id=' + id + '_FS]').hide();
					if($('[id=' + id + '_FS]').parent().is(':visible')){
						$('[id=' + id + '_FS]').parent().css( "display", "none" );
					}
				}
			}
		}
		var increment = 0;
		$('#missingFields').each(function(){
			increment++;
		})
		if(increment == 0){
			$('#validatorMsg').css('display', 'none');
		}
	}catch(err){
		alert(err);
	}
}

function checkEqualsIgnoreCase(string1, string2){

	if(string1.toLowerCase() == string2.toLowerCase()){
		return true;
	}
	return false;
}

function replaceText(selectedValue){
	try{
		var prev_selected = $('#previously_selected_supplier').val();
	
		$('.disclosureDiv').each(function(){
			var id = this.id;
			var html = $(this).html();
			if(html.indexOf("simpleChoice.roundRobinSelectedSupplier") >= 0 && selectedValue != ""){
				html = html.replace("simpleChoice.roundRobinSelectedSupplier", selectedValue); 
			 }
        	else if(html.indexOf(prev_selected) >= 0 && prev_selected != ""){
        		html = html.replace(prev_selected, selectedValue);
        	}
			$('[id=' + id+']').html(html);
      	});
	}catch(err){
		alert(err);
	}
}

function createMap() {
	<c:if test="${not empty enableDialogueMap}">
		<c:forEach var="dataField" items="${enableDialogueMap}">
			var optionMap = new Object();
			<c:set var="optionMap" value="${dataField.value}"/>
			<c:forEach var="option" items="${optionMap}">
				<c:set var="extIdList" value="${option.value}"/>
				var extIdList = new Array();
				var i = 0;
				<c:forEach var="extId" items="${extIdList}">
					extIdList[i] = '${extId}';
					i++;
				</c:forEach>
				var key = '${option.key}';

				if(key.indexOf('OR') !== -1){
					var array = key.split('OR');
					for(var itr=0;itr<array.length;itr++){
						optionMap[array[itr].trim()] = extIdList;
					}
				}
				else{
					optionMap[key] = extIdList;
				}
				
			</c:forEach>
			showDataFieldMap['${dataField.key}'] = optionMap;
		</c:forEach>
	</c:if>
	
	<c:if test="${not empty disableDialogueMap}">
		<c:forEach var="dataField" items="${disableDialogueMap}">
			var optionMap2 = new Object();
			
			<c:set var="optionMap2" value="${dataField.value}"/>
			<c:forEach var="option" items="${optionMap2}">

			<c:set var="extIdList2" value="${option.value}"/>
				var extIdList2 = new Array();
				var j = 0;
				<c:forEach var="extId" items="${extIdList2}">
					extIdList2[j] = '${extId}';
					j++;
				</c:forEach>
				optionMap2['${option.key}'] = extIdList2;
			</c:forEach>
			hideDataFieldMap['${dataField.key}'] = optionMap2;
		</c:forEach>
	</c:if> 
}

function in_array(array, id) {
	if(array != undefined){
    	for(var i=0;i<array.length;i++) {
        	if(array[i] == id) {
            	return true;
        	}
    	}
	}
    return false;
}

function prepareSelectedParameters(){
	try{
		$('.spanDefaultClass').remove();
		$("span.missingFields").remove();
		var isValidFormSubmit = true;
        var unSelectFieldIdsList = [];
		var selectflds = "";
		var selectedDialogueValues = {};
		var selectErrorMsg = "";
		var textErrorMsg = "";
		$('div#validatorMsg').css('display','none');
		
		var isValidationRequired = true;
		//below condition is only for when suppliers not found for round robin selection.
		if($("#isSupplierListEmpty").val() == "true" && ( $("#NextSupplier").is(':visible')) ) 
		{
			isValidationRequired = false;
		}
		
		if( isValidationRequired ) 
		{
			$('select').each( function() { 
				var id = $(this).attr("id");
				if($(this).is(':visible') && $(this).val() != "")
				{
					selectflds+=id+",";
					selectedDialogueValues[id] = $(this).val();
				}
				else if($(this).is(':visible') && $(this).val() == "" && id != "MailingLine2type_disabled" )
				{
					$(this).after($('<span class="missingFields">*</span>'));
					selectErrorMsg = "Please select an option before moving forward.";
				}
			});
	
			$('input[type="text"]').each( function() {
				
				var id = $(this).attr("id");
				if($(this).is(':visible') && $(this).val() != "" )
				{
					selectedDialogueValues[id] = $(this).val();
					
					if(id=="MailingZipCode_disabled")
					{
						var zipLength = $(this).val().length;
						if((zipLength != 5 && zipLength != 10) || !isValidZipNumber($(this).val()) )
						{
							if(zipLength == 0)
							{
								$('input#MailingZipCode_disabled').after($('<span class="missingFields">*</span>'));
								textErrorMsg = "Required Field.";
							}
							else
							{
								$('input#MailingZipCode_disabled').after($('<span class="missingFields">*</span>'));
								textErrorMsg = "Invalid Zip.";
							}
						}
					}
				}
				else if($(this).is(':visible') )
				{
					if( $(this).val() == "" && (id =="MailingStreetAddress_disabled"
						|| id=="MailingCity_disabled" || id=="MailingState_disabled" || id=="MailingZipCode_disabled" || id == "Supplier_Selection_SCFE_NonParticipating") )
					{
						$(this).after($('<span class="missingFields">*</span>'));
						textErrorMsg = "Required Field.";
					}
				}
				
			});
	
			if( $("#MailingLine2type_disabled").is(':visible') && $("#MailingLine2type_disabled").val() != "" )
			{
				if( $("#MailingLine2Info_disabled").val().trim()=="" )
				{
					$("#MailingLine2Info_disabled").after($('<span class="missingFields">*</span>'));
					textErrorMsg = "Required Field.";
				}
				
			}
			
			if( $("#MailingLine2Info_disabled").is(':visible') && $("#MailingLine2Info_disabled").val() != "" )
			{  
				if( $("#MailingLine2type_disabled").val()=="" )
				{
					$("#MailingLine2type_disabled").after($('<span class="missingFields">*</span>'));
					textErrorMsg = "Required Field.";
				}
				
			}
		}

		if(selectErrorMsg != "" && selectErrorMsg.length > 0){
			$("span#selectSpan").text('* '+selectErrorMsg);
			$("div#validatorMsg").css('display','block');
		}
		else if(textErrorMsg != "" && textErrorMsg.length > 0){
			$("span#selectSpan").text('* '+textErrorMsg);
			$("div#validatorMsg").css('display','block');
		}
		
		$("#loader").each( function() 
		{
			isValidFormSubmit = false;
		});
		
		$(".missingFields").each( function() 
		{
			isValidFormSubmit = false;
			$("div#validatorMsg").css('display','block');
		});
		
		
		if( isValidFormSubmit )
		{
			if( $("#selectedSupplierHiddenVal").val()!="" ){
				if(selectflds!="" && selectflds.length>0)
				{
					$("#selectedValues").val(selectflds);
					$("#selectedDialogueValues").val(JSON.stringify(selectedDialogueValues));
				}
				
				if($("#NextSupplier").is(':visible'))
				{
					//updating the round robin supplier in RRService by using the ajax call.
					updateRoundRobinAcceptedSupplier();
				}
				else
				{
					//updating the selected supplier in RRService by using the ajax call.
					updatePreferredSupplier();
				}
					
				//To save the html on page submit
				savePageHtml(false, "");
				return true;
			}
			else
			{
				if( isValidationRequired ){
					if($("#NextSupplier").is(':visible'))
					{
						$("#NextSupplier").after($('<span class="missingFields" style="color: red">*</span>'));
						$("span#selectSpan").text("* Please select an option before moving forward.");
						$("div#validatorMsg").css('display','block');
					}
					return false;
				}
				else
				{
					return true;
				}
			}
		}
		
		return false;
	}catch(err){
		alert(err);
		return false;
	}
}

function selectDefaultValue(selectedId){
	var selectedElement = $("#"+selectedId);
	if(selectedElement.is(':visible') && !(selectedElement.val() == "")){
		$("#"+selectedId).val("");
	}
}
/*colapse dependent feilds for selected element   when value is  please select*/
function collapseDepdentFields(datafieldId, val){
	try
	{
		 try{
			  var optionMap = showDataFieldMap[datafieldId];
	             var showIdList = optionMap[val];
	             }
                 catch(err){return;}             
                  if(showIdList != undefined && optionMap != undefined) {
			var isSelectedSupplierUpdated = false;
			for ( var i = 0; i < showIdList.length; i++) {
				var id = showIdList[i].replace("/", "_");
			    $('[id=' + id + '_FS]').hide();
			     if($('[id=' + id + '_FS]').parent().is(':visible')){
				   $('[id=' + id + '_FS]').parent().css( "display", "none" );
			       }
	               if(showDataFieldMap[id]!=undefined && $("#"+id).val() != undefined){
		           collapseDepdentFields(id, $("#"+id).val());
		           }
		       }
		     }
	}catch(err){
		alert("Error In collapseDepdentFields "+err);
     }
 }
$(window).on('beforeunload', function(){
	$.blockUI({ message: $('#domMessage') }); 
});
</script>
<style type="text/css">
a.reload {
    color: #009CDE;
    cursor: pointer;
}
a.reload:hover {
    color: #0000FF;
    text-decoration: underline;
}

.style_div {
	width: 830px;
	padding: 8px 10px;
	margin: 0 0 0 20px;
	font-size: 14px;
	font-weight: bold;
	line-height: 17px;
}

.missingFields {
    color: #FF0000;
    font-size: 23px;
    vertical-align: middle;
}
</style>

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
								<input type="hidden" id="isProductServiceCallCompleted" value="${isProductServiceCallCompleted}">
								<input type="hidden" id="isSupplierListEmpty" value="false">
								<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
								<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
								<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
								<span class="cell pageTitle">Supplier Selection</span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="supplierSelection" name="supplierSelection" action="${flowExecutionUrl}" method="post" onsubmit="return prepareSelectedParameters()">
							<input type="hidden" id="submitSt" name="submitSt"/>
							<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="pageTitle" name="pageTitle" value="SupplierSelection">
							<input type="hidden" id="selectedSupplierHiddenVal" name="selectedSupplierHiddenVal" value="">
							<input type="hidden" id="selectedDialogueValues" name="selectedDialogueValues" value="">
							<input type="hidden" id="_eventId" name="_eventId" value="supplierEvent">
							<input type="hidden" id="selectedValues" name="selectedValues" value="" />
							<input type="hidden" id="supplier_round_robin" name="supplier_round_robin" value="" />
							<input type="hidden" id="previously_selected_supplier" name="previously_selected_supplier" value="" />
							
							<div class="pc_pdetails_cont">
									<!-- Left Block -->
									<div class="pc_frameblk" style="max-height:534px;">
										<div class="pc_frameblk_title"></div>
										<div class="pc_frameblk_cont">
											<c:out escapeXml="false"  value="${dataField}" />
										</div>
										<div >
												<div class="leftbtns" >
													<div class="row" id="validatorMsg" style="display:none; color:#F00;" >
													  <span id="selectSpan"></span>
														 
													</div>
												</div>
											</div>
									</div>
								</div>
								
								<div>
								<div class="bottombuttons">
									<div class="rightbtns" style="padding-top: 47px;">
										<input  type="submit" id="supplierSelectionForward" value="Forward >" />
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
