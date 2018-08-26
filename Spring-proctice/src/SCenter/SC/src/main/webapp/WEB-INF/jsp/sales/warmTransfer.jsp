<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title>WarmTransfer</title>
<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/warmTransfer.css'/>"/>
<link media="all" href="<c:url value='/style/jquery.ui.all.css'/>" type="text/css" rel="stylesheet">
<script>
$(document).ready(function(){
	try{
		
		$("input[id*='_datepicker']").live("focus", function(){
			$(this).datepicker();
			
		});


		$("input[id*='_datepicker']").keyup(function(event){
			try{
				var key = event.keyCode || event.charCode;
				if (key ==  8) {
					
					event.preventDefault();
					
				}else{
					var moveDate = $(this).val();
					var title = $(this).attr('title');
					if(moveDate.length == 8){
						var date_re = new RegExp("^\\d{2}\\d{2}\\d{4}$");
						var calDate_re = new RegExp("^\\d{2}/\\d{2}/\\d{4}$");
						var check = true;
						if(moveDate != undefined && moveDate != "" && moveDate.match(date_re)){
							var mm = moveDate.slice(0,2);
							var dd = moveDate.slice(2,4);
							var yyyy = moveDate.slice(4);
							var month = parseInt(mm,10); // was gg (giorno / day)
							var date = parseInt(dd,10); // was mm (mese / month)
							var year = parseInt(yyyy,10); // was aaaa (anno / year)
							var xdata = new Date(yyyy,mm-1,dd);
							if ( ( xdata.getFullYear() == year ) && ( xdata.getMonth () == month - 1 ) && ( xdata.getDate() == date ) ){
								moveDate =mm+"/"+dd+"/"+yyyy;
							} 
							else{
								alert("Invalid "+title+". Please enter a valid date.");
								//$('input#datepicker').val(defaultValue);
								$(document).keyup(function(e){
									var key = e.keyCode || e.charCode;
								    if ( key == 8) {
								        e.preventDefault();
								    }
								});
								return false;
							}
						}else if(!moveDate.match(calDate_re) && moveDate != title){
							alert("Invalid"+ title+"format.  Please enter date in mmddyyyy format.");
							//$('input#datepicker').val(defaultValue);
							$(document).keyup(function(e){
								var key = e.keyCode || e.charCode;
							    if ( key == 8) {
							        e.preventDefault();
							    }
							});
							return false;
						}
					}
					$(this).val(moveDate);
				}
				}catch(err){alert(err);}
		});


		$("input[id*='_datepicker']").each(function () {
			var title = $(this).attr('title');
			if(title=="Move in date"){
				var tempFullDate = "${order.moveDate}";

				var tempYear = tempFullDate.substring(0, 4);
				var tempMonth = tempFullDate.substring(5, 7);
				var tempDate = tempFullDate.substring(8, 10);

				var fullDate = tempMonth+"/"+tempDate+"/"+tempYear;
				$(this).val(fullDate);
			}
		 });

		//To save the html on page load
		savePageHtml(true, "");

	}catch(err){alert(err);}
});



var showDataFieldMap = new Object();
var hideDataFieldMap = new Object();
var enableFieldsMap = new Object();
var enableFieldArray = new Array();
var displayList = new Array();
function validateDialogue(){
	
	var insertData = $("#formdata");
	var selectedValues = $("#selectedValues");
	
	var jsonForTextBoxes = {};
	var jsonForRadioButtons = {};
	var jsonForSelectBoxes = {};
	var jsonForCheckBoxes = {};
	
	// Fetch Textboxes are visible and empty fieldnames
	var txt="";
	var selTxt = "";
	var selTxtValue = "";
	$(':input[type=text]').each( function() { 
		if($(this).is(':visible') && $(this).val() == ""){
			txt+=$(this).attr("name")+","; 
		}
		else if($(this).is(':visible') && $(this).val() != ""){
			selTxt += $(this).attr("name")+",";
			var hiddenJsonElementVal = $("#"+$(this).attr("id")+"_JSON").val();
            var enteredJSONDataObj = JSON.parse(hiddenJsonElementVal);
			selTxtValue +=$(this).attr("id")+":"+enteredJSONDataObj.enteredValue+",";
			jsonForTextBoxes[$(this).attr("id")] = enteredJSONDataObj.enteredValue;
		}
	});

	// Fetch radio buttons are visible and unchecked names
	var rad="";
	var selRad = "";
	var selRadValue = "";
	$(':input[type=radio]').each( function() {
		if($(this).is(':visible')){
			var id = $(this).attr("id");
			var radiotest = $(this).attr("name");
			if($("input[name="+radiotest+"]:checked").length == 0){
				if(rad.indexOf(radiotest) < 0){
					rad+=$(this).attr("name")+",";
				}
			}
			else if($("input[name="+radiotest+"]:checked").length != 0){
				if(selRad.indexOf(radiotest) < 0){
					selRad +=$(this).attr("name")+",";
				}
			}
			if($('input#'+id+'').is(':checked')){
				selRadValue+=$(this).attr("id")+":"+$(this).val()+",";
				jsonForRadioButtons[$(this).attr("id")] = $(this).val();
			}
		}
	});
	// Fetch Select fields are visible and unselected name for default values of "Please Select"
	var selectflds="";
	var selSelectBox = "";
	var selSelectValue = "";
	$('select').each( function() {
		if($(this).is(':visible') && $(this).val() == ""){
			selectflds+=$(this).attr("name")+",";
		}
		else if($(this).is(':visible') && $(this).val() != ""){
			selSelectBox += $(this).attr("name")+",";
			selSelectValue += $(this).attr("id")+":"+$(this).val()+",";
			jsonForSelectBoxes[$(this).attr("id")] = $(this).val();
		}
	});
	// Fetch Checkboxes are visible and unchecked names
	var checkboxes="";
	var selCheckboxes="";
	var selCheckboxesValues="";
	$(':input[type=checkbox]').each( function() {
		if($(this).is(':visible')){
			var radiotest = $(this).attr("name");
			if($('input:checkbox[name='+radiotest+']').is(":checked") == false){
				checkboxes+=$(this).attr("name")+",";
			}
			else if($('input:checkbox[name='+radiotest+']').is(":checked") == true){
				selCheckboxes += $(this).attr("name")+",";
				selCheckboxesValues+=$(this).attr("id")+":"+$(this).val()+",";
				jsonForCheckBoxes[$(this).attr("id")]=$(this).val();
			}
		}
	});
	
	
	
	var allSelectedVal = selTxt + selRad + selSelectBox + selCheckboxes;
	$("#selectedValues").val(allSelectedVal);
	return true;
}

function activate(datafieldId) {
	createMap();
	
	var selectedOptin = $('#' + datafieldId).val();
	var arr;
	if(selectedOptin.indexOf('::') > 0){
		arr = selectedOptin.split('::');
		selectedOptin = arr[1];
	}
	if(selectedOptin.indexOf('$$') > 0){
		arr = selectedOptin.split('$$');
		selectedOptin = arr[0];
	}
	var boolY = 'BOOL_Y_';
	var boolN = 'BOOL_N_';
	
	if(datafieldId.indexOf(boolY) > -1) {
		datafieldId = datafieldId.substr(boolY.length);
	} else if(datafieldId.indexOf(boolN) > -1) {
		datafieldId = datafieldId.substr(boolN.length);
	}
	var optionMap = showDataFieldMap[datafieldId];
	var showIdList = optionMap[selectedOptin];
	var hideDataList = new Array();
	if(showIdList != undefined) {
		for ( var i = 0; i < showIdList.length; i++) {
			var id = showIdList[i].replace("/", "_");
			$('[id=' + id + '_FS]').show("slow");
		}
	}
	var i = 0;
	<c:forEach var="extId" items="${allDataFieldList}">
		var result = in_array(showIdList, '${extId}');
		if(result == true){
			
		}
		else{
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
				$('[id=' + id + '_FS]').hide("slow");
			}
		}
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

  /**function isRequiredSelected(){
	$("span.missingFields").remove();
	var pattern = /BOOL_Y_/;
	var status = false;
	try{
		var input_name = '';
		$("input[class*='Required']").each(function(){
			var input_id = $(this).attr('id');
			input_name = $(this).attr('name');		
			if ($(this).is(':visible')) {
			if ($("#"+"BOOL_N_"+input_name).prop("checked") || $("#"+"BOOL_Y_"+input_name).prop("checked")) {
				status = true;
			} else {
				status = false;	
				if (pattern.test(input_id)) {
				$('#'+input_name +'_SPAN').after($('<span class="missingFields">*</span>'));
				}
			}
		}
		});	

	}catch(err){
		alert(err);
	}
	return status;
}  */
function isRequiredSelected(){
	  var status = false;
	  try{
		$('span[id*="red_astrick"]').css("display","none");
		$('#red_astrick_div').css("display","none");
	   var count = 0;
	   var checkedCount = 0;
	   var uncheckedNames = [];
	   var checkedNames = [];
	   var validUncheckedNames = [];
	   $("input[class*='Required']").each(function(){
	    count++;
	    var input_id = $(this).attr('id');
	    
	    if ($("#"+input_id).prop("checked"))
		{
	     	checkedCount++;
	     	checkedNames.push(this.name);
	    }
	    else
	    {
	    	 uncheckedNames.push(this.name);
	    }
	    
	   });

		var checkedNamesString = JSON.stringify(checkedNames);
	   for(var i=0;i<uncheckedNames.length;i++)
		{
			if(checkedNamesString.indexOf(uncheckedNames[i])==-1)
			{
				validUncheckedNames.push(uncheckedNames[i]);
			}
	   	}

	   if(checkedCount != (count/2))
	   {
		   	for(var i=0;i<validUncheckedNames.length;i++)
			{
				var astrickId = "red_astrick_"+validUncheckedNames[i];
			   	$('#'+astrickId).css("display","inline");
				$('#red_astrick_div').css("display","block");
		   	}
	   }
	   else{
	    status = true;
	   }
	  }catch(err){
	   alert(err);
	  }
	  
	  return status;
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
								<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
								<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
								<input type="hidden" id="selectedValues" name="selectedValues" />
								<span class="cell pageTitle">WarmTransfer</span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="warmTransfer" name="warmTransfer" action="<%=request.getContextPath()%>/salescenter/warmTransferSave" method="post" onsubmit='savePageHtml(false, "");' >
							<input type="hidden" id="submitSt" name="submitSt"/>
							<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="pageTitle" name="pageTitle" value="WarmTransfer">
							<input type="hidden" id="urlPath" name="urlPath" value="<%=request.getSession().getAttribute("urlPath")%>">
							<input type="hidden" id="warmTransferLineItemId" name="warmTransferLineItemId" value="${warmTransferLineItemId}" />
                            <input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
                            <input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							<div class="pc_pdetails_cont">
									<!-- Left Block -->
									<div class="pc_frameblk" style="max-height:534px;">
										<div class="pc_frameblk_title"></div>
										<div class="pc_frameblk_cont">
											<c:out escapeXml="false"  value="${dataField}" />
										</div>
									</div>
								</div>
								<div id="red_astrick_div" style="color: red; font-size: 15px; display:none">
									* Required Field
								</div>
								<div class="bottombuttons">
									<div class="rightbtns" style="padding-top: 47px;">
										<input  type="submit" id="warmTransferForward" value="Forward >" onclick="return isRequiredSelected();" />
									</div>
								</div>
							</form>
						</section><!--  Content -->
					</section><!-- Content Full Cont -->
