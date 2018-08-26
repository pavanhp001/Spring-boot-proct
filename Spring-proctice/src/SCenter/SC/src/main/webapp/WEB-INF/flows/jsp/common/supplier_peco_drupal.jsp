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
	$('.value').removeAttr('style');
	var pecoSuppliersJSON =  new Object();
	<c:forEach var="pecoSupplierData" items="${pecoSuppliersMap}">
	<c:set var="pecoSupplierList" value="${pecoSupplierData.value}"/>
		var supplierNamesList = new Array();
		var i = 0;
		<c:forEach var="pecoSupplier" items="${pecoSupplierList}">
			supplierNamesList[i] = '${pecoSupplier}';
			i++;
		</c:forEach>
		var key = '${pecoSupplierData.key}';
		pecoSuppliersJSON[key] = supplierNamesList;
	</c:forEach>
	
	if(Object.keys(pecoSuppliersJSON).length==0)
	{
		
		$('#isSupplierListEmpty').val("true");
	}
	
	$('#pecoSuppliersJSONHidden').val(JSON.stringify(pecoSuppliersJSON));
	
	/*  this method  trigger  when change and keyup events  occuerring in every select box   */
	$("select").on('change keyup', function(event) {
	 	var previousValues = $(this).data($(this).attr('id'));
	    var theValue = $(this).val();
	    $(this).data($(this).attr('id'), theValue);
	    if(event.keyCode != 9 && event.keyCode !=16){
        	activate($(this).attr('id') , previousValues);
        }
	   	if( $(this).attr('id') == "Supplier_Selection_PECO_Cust_Type" )
		{
	   		$('#Supplier_Selection_PECO_10_FS').css("display", "none");
			$('#Supplier_Selection_PECO_10_FS').parent().css( "display", "none" );
			updateSuppliersList(theValue);
		}
	         
	});

	$('#Supplier_Selection_PECO_Select_Supplier').change(function(){
		try{
			var selectedValue = $('#Supplier_Selection_PECO_Select_Supplier').val();
			if(selectedValue != "" )
			{
				replaceText(selectedValue);
				$("#selectedSupplierHiddenVal").val(selectedValue);
				$('#previously_selected_supplier').val(selectedValue);
				if(selectedValue == "Supplier Not Listed" )
				{
					$('#Supplier_Selection_PECO_10_FS').css("display", "none");
					$('#Supplier_Selection_PECO_10_FS').parent().css( "display", "none" );
					
					$('#Supplier_Selection_PECO_8_FS').css("display", "block");
					$('#Supplier_Selection_PECO_8_FS').parent().css( "display", "block" );
					$('.spanDefaultClass').remove();
					if($('#NextSupplierDiv').parent().is(':hidden'))
					{
						$('#NextSupplierDiv').parent().css({
							"display":"block",
							"padding-left":"385px",
						});
					}
					$('#NextSupplierDiv').css("display", "block");
					if( $('#NextSupplier').prop('disabled') )
					{
						$("#NextSupplier").prop("disabled",false);
					}
				}
				else
				{
					hideNextSupplierButton();
					$('#Supplier_Selection_PECO_Desired_Supplier_FS').css("display", "none");
					$('#Supplier_Selection_PECO_Desired_Supplier_FS').parent().css( "display", "none" );
					$('#Supplier_Selection_PECO_8_FS').css("display", "none");
					$('#Supplier_Selection_PECO_8_FS').parent().css( "display", "none" );
					$('#Supplier_Selection_PECO_10_FS').css("display", "block");
					$('#Supplier_Selection_PECO_10_FS').parent().css( "display", "block" );
				}
			}
			else
			{
				hideNextSupplierButton();
			}
		}catch(err){
			alert(err);
		}
	});

	
	$('select[id=Supplier_Selection_PECO_Rand_Selection_2]').live('change', function(){
		var selectedValue = $(this).val();
		if(selectedValue != "" && selectedValue == "No")
		{
			var isDisabled = $('#NextSupplier').prop('disabled');
				
			if(isDisabled)
			{
				$("#NextSupplier").prop("disabled",false);
				
				$('#Supplier_Selection_PECO_10_FS').css("display", "none");
				$('#Supplier_Selection_PECO_10_FS').parent().css( "display", "none" );
			}
		}
		else if(selectedValue != "" && selectedValue == "Yes")
		{
			$("#NextSupplier").prop("disabled",true);
		}
	});
	

	$('select[id=Supplier_Selection_PECO_Cust_Enrolled]').live('change', function(){
		var selectedValue = $(this).val();
		if(selectedValue != "" && selectedValue == "No")
		{
			var selectedSupplier = $('#supplier_round_robin').val();
			if( selectedSupplier != "" && selectedSupplier.trim().length > 0 )
			{
				roundRobinRejected(selectedSupplier);
			}
		}
	});

	
	$('#NextSupplier').click(function(){
		$("span.missingFields").remove();
		$('#refreshSuppliersListId2').remove();
		var isLoaderRunning = false;
		if($("#loaderForRandom").is(":visible"))
		{
			isLoaderRunning = true;
		}
		
		if( !isLoaderRunning )
		{
			var selectedSupplier = $('#supplier_round_robin').val();
			if( selectedSupplier != "" && selectedSupplier.trim().length > 0 )
			{
				roundRobinRejected(selectedSupplier);
			}
			
		    var custType = $('#Supplier_Selection_PECO_Cust_Type').val()
		    
			var path ="<%=request.getContextPath()%>";
			//displaying loader.
			$(this).after('<img id="loaderForRandom" width="18" height="18" alt="Loading ..." src="'+path+'/images/spinner1.gif" style="padding-left: 2px; vertical-align: middle; padding-top: 1px;">');
			
			var url = path+"/salescenter/getRoundRobinSupplier?custType="+custType;
			$.ajax({
				type: 'GET',
				url: url,
				success: onCompleteRoundRobin,
			});
		}
		
	});

	jQuery(window).load(function () {
		//To save the html on page load
	    savePageHtml(true, "");
	 });
});


function replaceText(selectedValue){
	try{
		var prev_selected = $('#previously_selected_supplier').val();
		$('.disclosureDiv').each(function()
		{
			var id = this.id;
			var html = $(this).html();
        	//This is for round robin selection dialogue update
        	if(html.indexOf("peco.roundRobinSelectedSupplier") >= 0 && selectedValue != "")
            {
        		html = html.replace("peco.roundRobinSelectedSupplier", selectedValue); 
				$('[id=' + id+']').html(html);
        	}
        	else if(html.indexOf("RRSelectedSupplier") >= 0 && selectedValue != "")
            {
        		html = html.replace("RRSelectedSupplier", selectedValue); 
				$('[id=' + id+']').html(html);
        	}
        	else if(html.indexOf(prev_selected) >= 0 && prev_selected != "" && selectedValue != ""){
        		html = html.replace(prev_selected, selectedValue);
        		$('[id=' + id+']').html(html);
        	}
      	});
	}catch(err){
		alert(err);
	}
}

function roundRobinRejected(selectedSupplier)
{
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

var isRoundRabinSupplier = false;
function onCompleteRoundRobin(data){
	try{
		if(data=="sessionTimeOut"){
			var path = "<%=request.getContextPath()%>";
			window.location.href = path+"/salescenter/session_timeout";
		}else{
			isRoundRabinSupplier = true;
			$('#loaderForRandom').remove();
			if(data!="" && data!="nil"){
				var roundRobinSupplier = data;
				
				$("#supplier_round_robin").val(roundRobinSupplier);
				$("#selectedSupplierHiddenVal").val(roundRobinSupplier);
				replaceText(roundRobinSupplier);

				if(roundRobinSupplier.length>0)
				{
					$("#previously_selected_supplier").val(roundRobinSupplier);
				}
				
				$("#NextSupplier").attr("disabled", "disabled");
				$('#Supplier_Selection_PECO_Rand_Selection_1_FS').css("display", "block");
				$('#Supplier_Selection_PECO_Rand_Selection_1_FS').parent().css( "display", "block" );
				$('#Supplier_Selection_PECO_Rand_Selection_2_FS').css("display", "block");
				$('#Supplier_Selection_PECO_Rand_Selection_2_FS').parent().css( "display", "block" );
				$('#Supplier_Selection_PECO_11_FS').css("display", "none");
				$('#Supplier_Selection_PECO_11_FS').parent().css( "display", "none" );
				$('#Supplier_Selection_PECO_12_FS').css("display", "none");
				$('#Supplier_Selection_PECO_12_FS').parent().css( "display", "none" );
				
				selectDefaultValue("Supplier_Selection_PECO_Rand_Selection_2");
				
				//To save the html on round robin process
			    savePageHtml(false, "");
			}
			else
			{
				$("#NextSupplier").after('&nbsp;&nbsp;<a class="reload" id="refreshSuppliersListId2" href="#" onclick="refreshSuppliersList2()">Refresh Suppliers</a>');
				alert("Suppliers not available.");
			}
		}
	}catch(err){
		alert(err);
	}
}

function updateSuppliersList(customerType)
{
	try{
	var pecoSuppliersJSON = JSON.parse($('#pecoSuppliersJSONHidden').val());
	$("#refreshSuppliersListId").remove();
	if(Object.keys(pecoSuppliersJSON).length>0 && customerType != "" && customerType.trim().length > 0 )
	{
		var pecoSupplierList = pecoSuppliersJSON[customerType.toUpperCase()];
		$('#Supplier_Selection_PECO_Select_Supplier option').remove();
		$('#Supplier_Selection_PECO_Select_Supplier').append('<option value="">Please Select</option>');
		
		if(pecoSupplierList != undefined)
		{
			for(var i=0;i<pecoSupplierList.length;i++)
			{
				$('#Supplier_Selection_PECO_Select_Supplier').append('<option value="'+pecoSupplierList[i]+'">'+pecoSupplierList[i]+'</option>');
			}
		}
		$('#Supplier_Selection_PECO_Select_Supplier').append('<option value="Supplier Not Listed">Supplier Not Listed</option>');
	}
	else
	{
		$('#Supplier_Selection_PECO_Select_Supplier option').remove();
		$('#Supplier_Selection_PECO_Select_Supplier').append('<option value="">Please Select</option>');
		$('#Supplier_Selection_PECO_Select_Supplier').append('<option value="Supplier Not Listed">Supplier Not Listed</option>');
		$('#Supplier_Selection_PECO_Select_Supplier').after('&nbsp;&nbsp;<a class="reload" id="refreshSuppliersListId" href="#" onclick="refreshSuppliersList()">Refresh Suppliers</a>');
	}
	
	}catch(err){alert(err);}
}


function refreshSuppliersList(){
	try{
		savePageHtml(false, "");
		var path ="<%=request.getContextPath()%>";
		$(this).after('<img id="loaderForRefreshSuppliers" width="18" height="18" alt="Loading ..." src="'+path+'/images/spinner1.gif" style="padding-left: 2px; vertical-align: middle; padding-top: 1px;">');
		$("#refreshSuppliersListId").remove();
		var custType = $('#Supplier_Selection_PECO_Cust_Type').val()
		var url = path+"/salescenter/getSuppliersList?custType="+custType;
		$.ajax({
			type: 'GET',
			url: url,
			dataType: "html",
			success: onCompleteGetSuppliersList,
		});
	   
	}catch(err){alert(err);}
}

function refreshSuppliersList2(){
	try{
		savePageHtml(false, "");
		$('#refreshSuppliersListId2').remove();
		var path ="<%=request.getContextPath()%>";
		$(this).after('<img id="loaderForRefreshSuppliers" width="18" height="18" alt="Loading ..." src="'+path+'/images/spinner1.gif" style="padding-left: 2px; vertical-align: middle; padding-top: 1px;">');
		var custType = $('#Supplier_Selection_PECO_Cust_Type').val()
		var url = path+"/salescenter/getSuppliersList?custType="+custType;
		$.ajax({
			type: 'GET',
			url: url,
			dataType: "html",
			success: onCompleteGetSuppliersList,
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
				$('#isSupplierListEmpty').val("false");
				var jsonArray = JSON.parse(data);
				var supplierJSON = jsonArray[jsonArray.length-1];
				$('#pecoSuppliersJSONHidden').val(JSON.stringify(supplierJSON));
				if(jsonArray.length>0)
				{
					if($("#Supplier_Selection_PECO_Select_Supplier option").length>0)
					{
						$("#Supplier_Selection_PECO_Select_Supplier option").remove();
					}
					$("#Supplier_Selection_PECO_Select_Supplier").append('<option value=""> Please Select </option>');
					for( i=0; i < (jsonArray.length)-1; i++)
					{
						$("#Supplier_Selection_PECO_Select_Supplier").append('<option value="'+jsonArray[i]+'">'+jsonArray[i]+'</option>');
					}
					$("#Supplier_Selection_PECO_Select_Supplier").append('<option value="Supplier Not Listed">Supplier Not Listed</option>');
					$('#loaderForRefreshSuppliers').remove();
				}
				else
				{
					if( $("#NextSupplier").is(':visible') )
					{
						$("#NextSupplier").after('&nbsp;&nbsp;<a class="reload" id="refreshSuppliersListId2" href="#" onclick="refreshSuppliersList2()">Refresh Suppliers</a>');
					}
					else
					{
						$('#Supplier_Selection_PECO_Select_Supplier').after('&nbsp;&nbsp;<a class="reload" id="refreshSuppliersListId" href="#" onclick="refreshSuppliersList()">Refresh Suppliers</a>');
					}
					alert("Suppliers not available.");
				}
			}
			else
			{
				if( $("#NextSupplier").is(':visible') )
				{
					$("#NextSupplier").after('&nbsp;&nbsp;<a class="reload" id="refreshSuppliersListId2" href="#" onclick="refreshSuppliersList2()">Refresh Suppliers</a>');
				}
				else
				{
					$('#Supplier_Selection_PECO_Select_Supplier').after('&nbsp;&nbsp;<a class="reload" id="refreshSuppliersListId" href="#" onclick="refreshSuppliersList()">Refresh Suppliers</a>');
				}
				alert("Suppliers not found.");
			}
		}
	}catch(err){alert(err);}	
}

$(document).ready(function()
		   {
		       $("#Supplier_Selection_PECO_Acc_Num").attr('maxlength','11');
		   });
function pecoAccountNumberValidation(){
	try{
		var pecoAccountNumber = $('input#Supplier_Selection_PECO_Acc_Num').val().trim();
		var messageForPecoAccNum = "";
		$('.missingFields').remove();
		$("div#validatorMsg").css('display','none');
		if(pecoAccountNumber!=null && pecoAccountNumber!="")
		{
			if((pecoAccountNumber.trim()).length<11)
			{
				$('input#Supplier_Selection_PECO_Acc_Num').after($('<span class="missingFields">*</span>'));
				messageForPecoAccNum = "Too short:  Account number should be 11 characters in length.  Please check again and don’t forget to include the dash.";
		   	}
			 if((pecoAccountNumber.trim()).length>11)
			{
				$('input#Supplier_Selection_PECO_Acc_Num').after($('<span class="missingFields">*</span>'));
				messageForPecoAccNum = "Too long:  Account number should only be 11 characters in length (including the dash).  Please check again and remove any spaces.";
		   	}
			if((pecoAccountNumber.trim()).length == 11)
			{
				if(!(pecoAccountNumber.trim().indexOf("-") > 0)){
					$('input#Supplier_Selection_PECO_Acc_Num').after($('<span class="missingFields">*</span>'));
					messageForPecoAccNum = "Please include the dash.";
				}
			}
			if( messageForPecoAccNum != "" && messageForPecoAccNum.length>0 )
			{
				$("span#selectSpan").text('* '+messageForPecoAccNum);
				$("div#validatorMsg").css('display','block');
			}
		}  
		return messageForPecoAccNum;
	}catch(err){
		alert(err);
	}
}

var showDataFieldMap = new Object();
var hideDataFieldMap = new Object();
var enableFieldsMap = new Object();
var enableFieldArray = new Array();
var displayList = new Array();


function activate(datafieldId, previousValues){
	try{
		createMap();
		var datafieldIdVal= $('#' + datafieldId).val();
		var selectedOptin = datafieldIdVal!=""?datafieldIdVal:previousValues;
		var arr;

		var boolY = 'BOOL_Y_';
		var boolN = 'BOOL_N_';
	
		if(datafieldId.indexOf(boolY) > -1) 
		{
			datafieldId = datafieldId.substr(boolY.length);
		} 
		else if(datafieldId.indexOf(boolN) > -1)
		{
			datafieldId = datafieldId.substr(boolN.length);
		}

		var optionMap = showDataFieldMap[datafieldId];
		if(optionMap!=undefined)
		{
			var showIdList = optionMap[selectedOptin];
			var hideDataList = new Array();
			if(showIdList != undefined) 
			{
				var isSelectedSupplierUpdated = false;
				for ( var i = 0; i < showIdList.length; i++) 
				{
					var id = showIdList[i].replace("/", "_");
			        if( datafieldIdVal!= "" )
				    {
			        	if($('[id=' + id + '_FS]').parent().is(':hidden'))
				        {
							$('[id=' + id + '_FS]').parent().css( "display", "block" );
				     	}
				        $('[id=' + id + '_FS]').show();
				        selectDefaultValue(id);
		         	}
		         	else
			        {
		            	/*colapse feilds for selected element value  when value is  please select*/
					    $('[id=' + id + '_FS]').hide();
					 	if($('[id=' + id + '_FS]').parent().is(':visible'))
						{
							$('[id=' + id + '_FS]').parent().css( "display", "none" );
						}
						if($("#"+id).val() != undefined)
						{
						    collapseDepdentFields(id, $("#"+id).val());
					 	}
					}
			        if( id == 'Supplier_Selection_PECO_Supplier_Quest_1' )
					{
						hideNextSupplierButton();
					}
				}
				
				if( datafieldIdVal == "" || datafieldIdVal.trim().length == 0 )
				{
					if(datafieldId=='Supplier_Selection_PECO_Supplier_Quest_1')
					{
						hideNextSupplierButton();
					}
					return;
				}
			} 
			
			<c:forEach var="extId" items="${allDataFieldList}">
				var result = in_array(showIdList, '${extId}');
				if( !result )
				{
					hideDataList.push('${extId}');
				}
			</c:forEach>
			
			var optionMap2 = hideDataFieldMap[datafieldId];
			var hideIdList = optionMap2[selectedOptin];
			if(hideIdList != undefined)
			{
				for ( var i = 0; i < hideIdList.length; i++) 
				{
					var id = hideIdList[i].replace("/", "_");
					//if id is found in the hideDataList, then only hide the data element; if not, don't hide the element
					var is_present = in_array(hideDataList, hideIdList[i]);
					if(is_present)
					{
						if( id == 'Supplier_Selection_PECO_Supplier_Quest_1' )
						{
							hideNextSupplierButton();
						}
						$('[id=' + id + '_FS]').hide();
						if($('[id=' + id + '_FS]').parent().is(':visible'))
						{
							$('[id=' + id + '_FS]').parent().css( "display", "none" );
						}
					}
				}
			}
		}
		
		if(datafieldId=='Supplier_Selection_PECO_Supplier_Quest_1')
		{
			$("#selectedSupplierHiddenVal").val("");
			if( ($('#Supplier_Selection_PECO_Supplier_Quest_1 option:selected').text())=="No")
			{
				$('.spanDefaultClass').remove();
				if($('#NextSupplierDiv').parent().is(':hidden'))
				{
					$('#NextSupplierDiv').parent().css({
						"display":"block",
						"padding-left":"385px",
					});
				}
				$('#NextSupplierDiv').css("display", "block");
				if( $('#NextSupplier').prop('disabled') )
				{
					$("#NextSupplier").prop("disabled",false);
				}
				
				$('#Supplier_Selection_PECO_Rand_Selection_1_FS').css("display", "none");
				$('#Supplier_Selection_PECO_Rand_Selection_1_FS').parent().css( "display", "none" );
				$('#Supplier_Selection_PECO_Rand_Selection_2_FS').css("display", "none");
				$('#Supplier_Selection_PECO_Rand_Selection_2_FS').parent().css( "display", "none" );
				$('#Supplier_Selection_PECO_11_FS').css("display", "none");
				$('#Supplier_Selection_PECO_11_FS').parent().css( "display", "none" );
				$('#Supplier_Selection_PECO_12_FS').css("display", "none");
				$('#Supplier_Selection_PECO_12_FS').parent().css( "display", "none" );
				
				$('#Supplier_Selection_PECO_10_FS').css("display", "none");
				$('#Supplier_Selection_PECO_10_FS').parent().css( "display", "none" );
				$('#Supplier_Selection_PECO_8_FS').css("display", "none");
				$('#Supplier_Selection_PECO_8_FS').parent().css( "display", "none" );
			}
			else
			{
				if( ($('#Supplier_Selection_PECO_Supplier_Quest_1 option:selected').text())=="Yes")
				{
					hideNextSupplierButton();
				}
			}
		}
		var n=1;
		$('input, select').each(function() {
			if($(this).is(":visible")) 
			{
		    	$(this).attr('tabindex', n++);
			}
		}); 
	}catch(err){
		alert(err);
	}
}

function hideNextSupplierButton()
{
	if(isRoundRabinSupplier)
	{
		var selectedSupplier = $('#supplier_round_robin').val();
		roundRobinRejected(selectedSupplier);
	}
	isRoundRabinSupplier = false;
	$('.spanDefaultClass').remove();
	$('#NextSupplierDiv').css("display", "none");
	$('#NextSupplierDiv').parent().css( "display", "none" );
	$('#Supplier_Selection_PECO_Rand_Selection_1_FS').css("display", "none");
	$('#Supplier_Selection_PECO_Rand_Selection_1_FS').parent().css( "display", "none" );
	$('#Supplier_Selection_PECO_Rand_Selection_2_FS').css("display", "none");
	$('#Supplier_Selection_PECO_Rand_Selection_2_FS').parent().css( "display", "none" );
	$('#Supplier_Selection_PECO_10_FS').css("display", "none");
	$('#Supplier_Selection_PECO_10_FS').parent().css( "display", "none" );
	$('#Supplier_Selection_PECO_11_FS').css("display", "none");
	$('#Supplier_Selection_PECO_11_FS').parent().css( "display", "none" );
	$('#Supplier_Selection_PECO_12_FS').css("display", "none");
	$('#Supplier_Selection_PECO_12_FS').parent().css( "display", "none" );
	$('#Supplier_Selection_PECO_8_FS').css("display", "none");
	$('#Supplier_Selection_PECO_8_FS').parent().css( "display", "none" );
}

function checkEqualsIgnoreCase(string1, string2){

	if(string1.toLowerCase() == string2.toLowerCase()){
		return true;
	}
	return false;
}

function createMap() {
	try
	{
		showDataFieldMap = JSON.parse('${enableDialogueMap}');
		hideDataFieldMap = JSON.parse('${disableDialogueMap}');
	}catch(e){alert(e);}
	
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
		//below condition is to move forward without validating when suppliers not found for round robin selection.
		if($("#isSupplierListEmpty").val() == "true" && ( $("#NextSupplier").is(':visible')) ) 
		{
			isValidationRequired = false;
		}
		var isEligible = $('#Supplier_Selection_PECO_Eligible');
		if(isEligible.is(':visible') && isEligible.val() == "No") {
			isValidationRequired = false;
		}
		var isEligible_Yes_No = $('#Supplier_Selection_PECO_Eligible_Yes_No');
		if(isEligible_Yes_No.is(':visible') && isEligible_Yes_No.val() == "No") {
			isValidationRequired = false;
		}
		var isWantToMoveForward = $('#Supplier_Selection_PECO_Active_Supplier_4');
		if(isWantToMoveForward.is(':visible') && isWantToMoveForward.val() == "No") {
			isValidationRequired = false;
		}		
		if( isValidationRequired ) 
		{
			selectErrorMsg = pecoAccountNumberValidation();
	
			var pecoAccountNumber = $('#Supplier_Selection_PECO_Acc_Num').val();
			if(pecoAccountNumber.trim() == "" || pecoAccountNumber == null)
			{
				$('#Supplier_Selection_PECO_Acc_Num').val("");
				$('input#Supplier_Selection_PECO_Acc_Num').after($('<span class="missingFields">*</span>'));
				selectErrorMsg = "PECO Account Number is Required.";
				isValidFormSubmit = false;
			}
			
			$('select').each( function() {
				var id = $(this).attr("id");
				if($(this).is(':visible') && $(this).val() != "")
				{
					selectflds+=id+",";
					selectedDialogueValues[id] = $(this).val();
				}
				else if($(this).is(':visible') && $(this).val() == "")
				{
					//$(this).after($('<span class="spanDefaultClass" style="color: red">Please select an option before moving forward.</span>'));
					$(this).after($('<span class="missingFields">*</span>'));
					if(selectErrorMsg == "" || selectErrorMsg.length == 0)
					{
						selectErrorMsg = "Please select an option before moving forward.";
					}
				}
			});
			
			$('input[type="text"]').each( function() {
				var id = $(this).attr("id");
				if($(this).is(':visible') && $(this).val() != "")
				{
					selectedDialogueValues[id] = $(this).val();
				}
				else if($(this).is(':visible') && $(this).val() == "")
				{
					if(id != "Supplier_Selection_PECO_Acc_Num"){
	           			$(this).after($('<span class="missingFields">*</span>'));
	           			if(textErrorMsg == "" || textErrorMsg.length == 0)
	    				{
	           				textErrorMsg = "Please enter value before moving forward."
	    				}
					}
				}
			});
		}
		
		if(selectErrorMsg != "" && selectErrorMsg.length > 0)
		{
			$("span#selectSpan").text('* '+selectErrorMsg);
			$("div#validatorMsg").css('display','block');
		}
		else if(textErrorMsg != "" && textErrorMsg.length > 0)
		{
			$("span#selectSpan").text('* '+textErrorMsg);
			$("div#validatorMsg").css('display','block');
		}

		$(".spanDefaultClass").each( function() 
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
			$("span.missingFields").remove();
			if( $("#selectedSupplierHiddenVal").val()!="" )
			{
				if(selectflds!="" && selectflds.length>0)
				{
					$("#selectedValues").val(selectflds);
					$("#selectedDialogueValues").val(JSON.stringify(selectedDialogueValues));
				}
				
				if($("#NextSupplier").is(':visible'))
				{
					var isDisabled = $('#NextSupplier').prop('disabled');
					//updating the round robin supplier in RRService by using the ajax call.
					if( isDisabled )
					{
						updateRoundRobinSupplier();
					}
					else if( !isDisabled )
					{
						if($("#Supplier_Selection_PECO_Rand_Selection_2").is(':hidden')) {
						$("#NextSupplier").after($('<span class="missingFields" style="color: red">*</span>'));
						$("span#selectSpan").text("* Please select an option before moving forward.");
						$("div#validatorMsg").css('display','block');
						return false;
						}
					}
				}
				else
				{
					//updating the selected supplier in RRService by using the ajax call.
					var orderProcessedInCIMS = $('#Supplier_Selection_PECO_Cust_Enrolled');
					if(orderProcessedInCIMS.is(':visible') && orderProcessedInCIMS.val() == "Yes") {
					updatePreferredSupplier();
					}
				}
					
				//To save the html on page submit
				savePageHtml(false, "");
				return true;
			}
			else if( !isValidationRequired )
			{
				//To save the html on page submit
				savePageHtml(false, "");
				return true;
			}	
		}
		if($("#NextSupplier").is(':visible'))
		{
			var isDisabled = $('#NextSupplier').prop('disabled');
			if( !isDisabled )
			{
				if($("#Supplier_Selection_PECO_Rand_Selection_2").is(':hidden')) {
				$("#NextSupplier").after($('<span class="missingFields" style="color: red">*</span>'));
				$("span#selectSpan").text("* Please select an option before moving forward.");
				$("div#validatorMsg").css('display','block');
				return false;
				}
			}
		}
		return false;
	}catch(err){
		alert(err);
		return false;
	}
}


function updateRoundRobinSupplier()
{
	var previousElement = $('#Supplier_Selection_PECO_Supplier_Quest_1');
	if(previousElement.is(':visible') && previousElement.val() == "No")
	{
		var customerAcceptedSupplier = $('#Supplier_Selection_PECO_Rand_Selection_2');
		if(customerAcceptedSupplier.is(':visible') && customerAcceptedSupplier.val() == "Yes")
		{
			var orderProcessedInCIMS = $('#Supplier_Selection_PECO_Cust_Enrolled');
			//Round robin supplier accepted scenario.
			if(orderProcessedInCIMS.is(':visible') && orderProcessedInCIMS.val() == "Yes")
			{
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
							if(data=="sessionTimeOut")
							{
								var path = "<%=request.getContextPath()%>";
								window.location.href = path+"/salescenter/session_timeout";
							}
						}catch(err){
							alert(err);
						}
					}
				});
			}
			//Round robin supplier rejected scenario.
			else if(orderProcessedInCIMS.is(':visible') && orderProcessedInCIMS.val() == "No")
			{
				var selectedSupplier = $('#supplier_round_robin').val();
				if( selectedSupplier != "" && selectedSupplier.trim().length > 0 )
				{
					roundRobinRejected(selectedSupplier);
				}
			}
		}
	} else {
		var supplierNotListed = $('#Supplier_Selection_PECO_Select_Supplier');
		if(supplierNotListed.is(':visible') && supplierNotListed.val() == "Supplier Not Listed") {
			var customerAcceptedSupplier = $('#Supplier_Selection_PECO_Rand_Selection_2');
			if(customerAcceptedSupplier.is(':visible') && customerAcceptedSupplier.val() == "Yes")
			{
				var orderProcessedInCIMS = $('#Supplier_Selection_PECO_Cust_Enrolled');
				//Round robin supplier accepted scenario.
				if(orderProcessedInCIMS.is(':visible') && orderProcessedInCIMS.val() == "Yes")
				{
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
								if(data=="sessionTimeOut")
								{
									var path = "<%=request.getContextPath()%>";
									window.location.href = path+"/salescenter/session_timeout";
								}
							}catch(err){
								alert(err);
							}
						}
					});
				}
				//Round robin supplier rejected scenario.
				else if(orderProcessedInCIMS.is(':visible') && orderProcessedInCIMS.val() == "No")
				{
					var selectedSupplier = $('#supplier_round_robin').val();
					if( selectedSupplier != "" && selectedSupplier.trim().length > 0 )
					{
						roundRobinRejected(selectedSupplier);
					}
				}
			}			
		}
	}
}


function updatePreferredSupplier()
{
	try{
		var previousElement = $('#Supplier_Selection_PECO_Supplier_Quest_1');
		if(previousElement.is(':visible') && previousElement.val() == "Yes"){
			var selectedSupplier = $('#Supplier_Selection_PECO_Select_Supplier').val();
			if(selectedSupplier != "Supplier Not Listed" )
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


function selectDefaultValue(selectedId){
	var selectedElement = $("#"+selectedId);
	if(selectedElement.is(':visible') && !(selectedElement.val() == ""))
	{
		$("#"+selectedId).val("");
	}
}
/*colapse dependent feilds for selected element   when value is  please select*/
function collapseDepdentFields(datafieldId, val){
	try
	{
		try
		{
			var optionMap = showDataFieldMap[datafieldId];
            var showIdList = optionMap[val];
        }
        catch(err)
        { 
            return;
        }  
	   	if(showIdList != undefined && optionMap != undefined)
		{
			var isSelectedSupplierUpdated = false;
 			for ( var i = 0; i < showIdList.length; i++) 
 	 		{
				var id = showIdList[i].replace("/", "_");
		   		$('[id=' + id + '_FS]').hide();
		   		if($('[id=' + id + '_FS]').parent().is(':visible')){
					$('[id=' + id + '_FS]').parent().css( "display", "none" );
				}
		   		if( id == 'Supplier_Selection_PECO_Supplier_Quest_1' )
				{
					hideNextSupplierButton();
				}
	      		if(showDataFieldMap[id]!=undefined && $("#"+id).val() != undefined)
		      	{
		       		collapseDepdentFields(id, $("#"+id).val());
	      		}
	      	}
		}
	}
	catch(err)
	{
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
								<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
								<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
								<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
								<input type="hidden" id="isSupplierListEmpty" value="false">
								<span class="cell pageTitle">Supplier Selection</span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="supplierSelection" name="supplierSelection" action="${flowExecutionUrl}" method="post" onsubmit="return prepareSelectedParameters()">
							<input type="hidden" id="submitSt" name="submitSt"/>
							<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="pageTitle" name="pageTitle" value="SupplierSelection">
							<input type="hidden" id="selectedDialogueValues" name="selectedDialogueValues" value="">
							<input type="hidden" id="selectedSupplierHiddenVal" name="selectedSupplierHiddenVal" value="">
							<input type="hidden" id="_eventId" name="_eventId" value="supplierEvent">
							<input type="hidden" id="selectedValues" name="selectedValues" value="" />
							<input type="hidden" id="supplier_round_robin" name="supplier_round_robin" value="" />
							<input type="hidden" id="previously_selected_supplier" name="previously_selected_supplier" value="" />
							<input type="hidden" id="pecoSuppliersJSONHidden" name="pecoSuppliersJSON" value="" />
							
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
								<div class="bottombuttons">
									<div class="rightbtns" style="padding-top: 47px;">
										<input  type="submit" id="supplierSelectionForward" value="Forward >" />
									</div>
								</div>
							</form>
						</section><!--  Content -->
    					<div id="domMessage" style="display:none;"> 
							<img src="<%=request.getContextPath()%>/images/loading.gif" border="0"/>
    						<h2>Loading</h2> 
						</div>
					</section><!-- Content Full Cont -->
