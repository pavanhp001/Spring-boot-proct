<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/warmTransfer.css'/>"/>
<head>
<title><fmt:message key="closingcall.header" /></title>

<style type="text/css">
#wrapper {
    margin: 0;
    overflow: hidden;
    text-align: left;
}
.content_border{
  height:532px;
}
.coaching {
    color: #008000;
    margin: 0 43px;
}
.Suggested
{
 color: grey;
 font-weight:bold;
 padding-top: 14px;
}
.row p{
	padding: 5px;
}
.bottomDweller {
     margin-left: 290px;
    margin-top: 37px;
    position: absolute;
}

#emailTextBox {
  background-color: #FFFFFF;
  border: 1px solid #616264;
  border-radius: 10px 10px 10px 10px;
  height: 125px;
  margin-left: 216px;
  padding: 20px;
  width: 538px;
}

#cSATEmailTextBox {
    background-color: #FFFFFF;
    border: 1px solid #616264;
    border-radius: 10px;
    height: 140px;
    margin-left: 216px;
    padding: 20px;
    width: 538px;
}

.pagecontainer {
    background-color: #E5E0D9;
    border: 1px solid #616264;
    display: table-cell;
    height: 225px;
    text-align: center;
    vertical-align: middle;
    width: 997px;
}
.widget_container {
    background-color: #E5E0D9;
    float: left;
    height: 225px;
    width: 997px;
}
.missingFields {
    color: #FF0000;
    font-size: 16px;;
    vertical-align: top;
}

.bubble {
  color: red;
  font-size: 14px;
  margin: 0;
  text-align:center;
  width:180px;
  margin: 0 0 0 425px;
}
</style>

<script src="<c:url value='/script/html_save.js'/>"></script>

<script type="text/javascript">
$(document).ready(function(){ 
	$('#CloseCallNoSale_Webflow_Email_FS').length!=0?$('#CloseCallNoSale_Webflow_Email_FS').hide():"";
		$('.black').html(function(index, text){  
			text = text.replace( /\.\s\s+/g, '. ' );
			text = text.replace( /\.\s+/g, '.&nbsp;&#32;' );
			text = text.replace( /\?\s\s+/g, '? ' );
			text = text.replace( /\?\s+/g, '?&nbsp;&#32;' );
		    return text;
		});

	$("#emailYes").click(function(){
		showEmail();	
	});
	$("#emailNo").click(function(){
		hideEmail();	
	});
	//$('#CloseCallNoSale_Webflow_Email').hide();
	 $("#emailYes").change(function(){
		 $("span.missingFields").remove();
		 $("span#validatorMsg").css('display','none');
		 if(!isClicked){
			$("#emailYes").prop("checked",false);
			$("#emailNo").prop("checked",true);
			hideEmail();
			isClicked = true;
		 }else{
			$("#emailYes").prop("checked",true);
			$("#emailNo").prop("checked",false);
			showEmail();
			isClicked = true;
		 }
	});
	 $("#emailNo").change(function(){
		 $("span.missingFields").remove();
		 $("span#validatorMsg").css('display','none');
		 if(!isClicked){
			$("#emailYes").prop("checked",true);
			$("#emailNo").prop("checked",false);
			showEmail();
			isClicked = true;
		 }else{
			$("#emailYes").prop("checked",false);
			$("#emailNo").prop("checked",true);
			hideEmail();
			isClicked = true;
		 }
	});
		

	 $('body').keypress(function(event) {
		 try{
			if(event.keyCode==40||event.keyCode==38){
				isClicked = false;
			}
		 }catch(err){alert(err);}
	});
	
	var url = location.href;
	if(url.indexOf("movers")>=0 && url.indexOf("nonmovers")<0)
	{
		$('#cSATEmailTextBox').css("display","none");
		$('#emailTextBox').css("display","block");
	}
	else
	{
		$('#cSATEmailTextBox').css("display","block");
		$('#emailTextBox').css("display","none");
	}
	
	var em = $('input#bestEmail').val();
	$('input#CloseCallNoSale_CSAT_Customer_Email').each(function(){
		$('input#CloseCallNoSale_CSAT_Customer_Email').attr("size", "30px;");
		$('input#CloseCallNoSale_CSAT_Customer_Email').val(em); 
	});

	
	
	//To save the html on page load
	setTimeout(function(){ 
        savePageHtml(true, "");
      }, 300);
});

	function showEmail(){
	   $("#emailFlag").val('Yes');
	   $("#bestEmailContact").show();
	} 
	function hideEmail(){
	   $("#emailFlag").val('No');
	   $("#bestEmailContact").hide();
	}



function validateEmail(){
	var isSelected = "";
	$("span.missingFields").remove();
	var isSelectionAvailable=false;
    $('select').each(function(){
		if($(this).is(":visible") )
		{
			isSelected = $( "#"+(this.id)+" option:selected" ).text();
			isSelectionAvailable=true;
		}
	});
	
	 var email = $.trim($('input#bestEmail').val());
	 $('input#bestEmail').val(email);
	$("input[type='text']").each(function(){
		if( this.id == "CloseCallNoSale_CSAT_Customer_Email" )
		{
			email = this.value;
			
		}
	});

	if($("#bestEmailContact").is(":visible") )
	{
		email = $.trim($("#bestEmailContact").val());
		$("#bestEmailContact").val(email);
		isSelected = $("#emailFlag").val();
	}
			
	if((isSelected.toUpperCase())=="YES" ){
		email=email.trim();
		if(email!=""){
			var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
			if (email != null && email != "" && emailReg.test(email)){
				$("#emailFlag").val(isSelected);
				$("#bestEmail").val(email);
				//To save the html on page submit
				 savePageHtml(false, "");
			 	$("form#closingcall").submit();
			} else {
				$.alert("Please enter valid Email", "Alert", 200, 300);
			 	return false; 
			} 
		}
		else
		{
			$.alert("Please enter valid Email", "Alert", 200, 300);
			return false; 
		}
	}else if((isSelected.toUpperCase())=="NO"){
		
		$("#emailFlag").val(isSelected);
		$("#bestEmail").val("");
		//To save the html on page submit
		 savePageHtml(false, "");
		$("form#closingcall").submit();
		return;
	}
 

	if(!isSelectionAvailable)
	{
		var isOptionSelected = true;
		if($("#emailTextBox").is(":visible") )
		{
			isOptionSelected = false;
			if($("input:radio[name='optin']").is(":checked")) {
				isOptionSelected = true;
			}
		}
		
		if(isOptionSelected)
		{
			var email = $("#bestEmail").val();
			email = email.trim();
			$("#bestEmail").val(email);
			savePageHtml(false, "");
			$("form#closingcall").submit();
		}
		else
		{
			$('#no').after('<span class="missingFields">* required.</span>');
		}
	}
	else
	{
		//alert($('#CloseCallNoSale_Webflow_1').length+"======"+$('#cSATEmailTextBox').is(":visible")+"==="+$('#CloseCallNoSale_Webflow_1').is(":visible"));
		if($('#cSATEmailTextBox').is(":visible"))
		{
			if($('#CloseCallNoSale_CSAT_Survey_Email').length!=0)
			{
              if($('#CloseCallNoSale_CSAT_Survey_Email').val()==""){
				$('#CloseCallNoSale_CSAT_Survey_Email').after('<span class="missingFields">* required.</span>');
			}
			else{
				var email=$('#CloseCallNoSale_CSAT_Customer_Email').val().trim();
			
				$('#CloseCallNoSale_CSAT_Customer_Email').val(email);
			}
		}
			
		}
		
		if($('#CloseCallNoSale_Webflow_Survey_Email').length!=0)
		{
			if($('#CloseCallNoSale_Webflow_Survey_Email').val()== "" || $('#CloseCallNoSale_Webflow_Email').val()==""){
				$('#CloseCallNoSale_Webflow_Survey_Email').after('<span class="missingFields">*</span>');
				$("span#validatorMsg").css('display','block');
				
			}
		}
	}
}


//WebCallFlow closeCallNoSale Email Script

$("#CloseCallNoSale_Webflow_Survey_Email").live("change", function(){
	$('span.row').css('display','none');
    $("#emailFlag").val($(this).val());
	$('#CloseCallNoSale_Webflow_Email').val('${order.customerInformation.customer.bestEmailContact}');
	$("#bestEmail").val($('#CloseCallNoSale_Webflow_Email').val());
});
$('#CloseCallNoSale_Webflow_Email').live('change', function(){
	$("#bestEmail").val($(this).val());
});

var showDataFieldMap = new Object();
var hideDataFieldMap = new Object();
var enableFieldsMap = new Object();
var enableFieldArray = new Array();
var displayList = new Array();

function activate(datafieldId) {
	try{
		
	createMap();
	$("span.missingFields").remove();
	var selectedOptin = $('#' + datafieldId).val();
	var arr;
	var optionMap = showDataFieldMap[datafieldId];
	var showIdList = optionMap[selectedOptin];
	var hideDataList = new Array();
	if(showIdList != undefined) {
		for ( var i = 0; i < showIdList.length; i++) {
			var id = showIdList[i].replace("/", "_");
			$('[id=' + id + '_FS]').show();
		
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
				$('[id=' + id + '_FS]').hide();
			}
		}
	}
	}catch(err){alert(err);}
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
								<span class="cell pageTitle"><fmt:message key="closingcall.header"/></span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
					<section id="content">
						<form id="closingcall" name="closingcall" action="${flowExecutionUrl}" method="post">
						    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" name="preURL" id="preURL" value="" />
							<input type="hidden" id="emailFlag" name="emailFlag" value="">
							<input type="hidden" id=bestEmail name="bestEmail" value="${order.customerInformation.customer.bestEmailContact}">
							<input type="hidden" id="pageTitle" name="pageTitle" value="Close Call No Sale">
							<div class="contentwrapper">
								<section id="dialogue_wrapper">						   
									<section id="dialogue_content" >
										${dialogue}
									</section>
									<div id="dialogue_content_balloon"></div>
								</section>
								
								<div id="action_wrapper">
									<div class="widget_container">
										<div class="pagecontainer" align="center">
											<div  id="emailTextBox" style="display: none;" >
											   <section style="margin-top:10px;width:550px;">
												   <lable>Does customer want to receive link to AL website?</lable>&nbsp; &nbsp;&nbsp;
											        <span id="yes"><input type="radio" id="emailYes" name="optin"  style="vertical-align:top;"/>&nbsp;Yes</span>
												    <span id="no"><input type="radio" id="emailNo" name="optin"  style="vertical-align:top;"/>&nbsp;No</span>
												    <br>
												    <br><br>
													<input type="text" size="30px"id="bestEmailContact" name="bestEmailContact" value="${order.customerInformation.customer.bestEmailContact}" style="display:none"  ><br/>
												    <br/>
												</section>
											</div>
											<div id="cSATEmailTextBox" style="display: none;">
												${cSATDialogue}
											</div>
										</div>
									</div>
								</div>
								
								<span class="row" id="validatorMsg" style="display:none;color:#F00;" >
									<span class="cell" style="font-weight:bold;margin-top:20px;">* Required Field</span>
								</span>
								<div class="bottombuttons">
								     <c:choose>
										<c:when test="${isWarmTransferEnabled}">
										  <span class="bubble">Next Page: Warm Transfer</span>
										  <input type="hidden" id="_eventId" name="_eventId"	value="warmTransferEvent">
										</c:when>
										<c:otherwise>
											<input type="hidden" id="_eventId" name="_eventId"	value="dispositionsEvent">
										</c:otherwise>
									</c:choose>
									<div class="rightbtns">
										<input type="button" id="closingCallForward" name="closingCallForward" value="Forward >" onclick="validateEmail();" />
									</div>
								</div>
							</div>
							<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
							<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							
							</form>
						</section>
					</section>
