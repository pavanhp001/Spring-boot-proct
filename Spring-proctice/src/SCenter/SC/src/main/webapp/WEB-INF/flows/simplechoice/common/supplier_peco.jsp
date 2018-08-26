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

	/*  this method  trigger  when change and keyup events  occuerring in every select box   */
	$("select").on('change keyup', function(event) {
	 var previousValues = $(this).data($(this).attr('id'));
	    	var theValue = $(this).val();
	         $(this).data($(this).attr('id'), theValue);
	         if(event.keyCode != 9 && event.keyCode !=16){
           activate($(this).attr('id') , previousValues);}
	});
			 
	$('#Supplier_Selection_PECO_Select_Supplier').change(function(){
		try{
			var prev_selected = $('#previously_selected_supplier').val();
			var selectedValue = $('#Supplier_Selection_PECO_Select_Supplier').val();
			$('.disclosureDiv').each(function(){
				var id = this.id;
				var html = $(this).html();
				if(html.indexOf("peco.supplier.name") >= 0 && selectedValue != ""){
					html = html.replace("peco.supplier.name", selectedValue); 
					$('[id=' + id+']').html(html);
				}
	        	else if(prev_selected != "" && html.indexOf(prev_selected) >= 0 && selectedValue != ""){
	        		html = html.replace(prev_selected, selectedValue);
	        		$('[id=' + id+']').html(html);
	        	}
				if(selectedValue != ""){
					$('#previously_selected_supplier').val(selectedValue);
				}
	      	});
		}catch(err){
			alert(err);
		}
	});
	


	jQuery(window).load(function () {
	       //To save the html on page load
	       savePageHtml(true, "");
	 });
});


var isRoundRabinSupplier = false;

function pecoAccountNumberValidation(){
	try{
		var pecoAccountNumber = $('input#Supplier_Selection_PECO_Acc_Num').val().trim();
		var messageForPecoAccNum = "";
		$('.missingFields').remove();
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
	
		if(datafieldId.indexOf(boolY) > -1) {
			datafieldId = datafieldId.substr(boolY.length);
		} else if(datafieldId.indexOf(boolN) > -1) {
			datafieldId = datafieldId.substr(boolN.length);
		}

		var optionMap = showDataFieldMap[datafieldId];
		if(optionMap!=undefined)
		{
		var showIdList = optionMap[selectedOptin];
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
	} i = 0;
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

function checkEqualsIgnoreCase(string1, string2){

	if(string1.toLowerCase() == string2.toLowerCase()){
		return true;
	}
	return false;
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
		
		if(selectErrorMsg != "" && selectErrorMsg.length > 0){
			$("span#selectSpan").text('* '+selectErrorMsg);
			$("div#validatorMsg").css('display','block');
		}
		else if(textErrorMsg != "" && textErrorMsg.length > 0){
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
			if(selectflds!="" && selectflds.length>0)
			{
				$("#selectedValues").val(selectflds);
				$("#selectedDialogueValues").val(JSON.stringify(selectedDialogueValues));
			}

				//alert(JSON.stringify(selectedDialogueValues));
					
				//To save the html on page submit
			savePageHtml(false, "");
			return true;
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
            catch(err){ return;}  
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
	 }
	catch(err){
		alert("Error In collapseDepdentFields "+err);
	}
}

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
								<div class="bottombuttons">
									<div class="rightbtns" style="padding-top: 47px;">
										<input  type="submit" id="supplierSelectionForward" value="Forward >" />
									</div>
								</div>
							</form>
						</section><!--  Content -->
					</section><!-- Content Full Cont -->
