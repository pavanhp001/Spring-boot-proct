<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/main.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/jquery-ui.css'/>"/>

<style>

.idelpageTitle{
    color: #FFFFFF;
    float: left;
    font-size: 18px;
    font-weight: bold;
   

}

.dynTable {
	margin: 20px 0;
}

.dynTable form {
	
}
#main_aside{
background: none;
}


.startcell{
    /*margin-left:250px;
    margin-top:140px;*/
    float: left;
    text-align: center;
    /*width: 43%;*/
    
}
    

.dynTable label {
	color: #666666;
	display: block;
	float: left;
	font-weight: bold;
	width: 180px;
}

.dynTable form.shorterLabels label {
	width: 90px;
}

.dynTable label.larger {
	width: 250px;
}

.dynTable input {
	float: left;
}

.dynTable button {
	float: none;
}

.dynTable .fieldsGroup * {
	float: left;
}

.dynTable .fieldsGroup input {
	margin-right: 15px;
}

.dynTable .table {
	border-collapse: collapse;
	font-size: 12px;
}

.dynTable .table tbody tr td,.dynTable .table thead tr th {
	background: none repeat scroll 0 0 #FFFFFF;
	border: 1px solid #CCCCCC;
	color: #444444;
	padding: 6px 8px;
}

.dynTable .table thead tr th {
	background: none repeat scroll 0 0 #CCCCCC;
	color: #444444;
	text-align: left;
}

.view {
	background-image:
		url("<%=request.getContextPath()%>/image/find.png");
	background-position: right top;
	background-repeat: no-repeat;
	display: block;
	height: 20px;
	margin: auto;
	padding-top: 2px;
	width: 60px;
}

.viewMetrics {
	background-image:
		url("<%=request.getContextPath()%>/image/chart.png");
	background-position: right top;
	background-repeat: no-repeat;
	display: block;
	height: 20px;
	margin: auto;
	padding-top: 2px;
	width: 60px;
}

.liA {
    background-color: #669933;
    border-radius: 4px;
    color: #FFF !important;
    font-size: 14px;
    font-weight: bold;
    padding: 5px 10px;
}

.submitBlock {
    margin-top: 20px;
    text-align: right;
}

.ambBlock{
	background-color: #DDD;
    border: 1px solid #D0D0D0;
    border-radius: 4px 4px 4px 4px;
    font-family: 'Crete Round','Serif',Helvetica,sans-serif;
    margin-top: 10px;
    padding: 5px;
    width: 800px;
}

.ambHead{
	font-size: 16px;
    font-weight: bold;
    padding: 3px 8px;
    text-shadow: 0 1px 1px #C0D576;
}

.ambUserDetails {
    font-size: 14px;
    padding: 10px;
}

.boldFont {
	font-weight: bold;
}



.messageGrid {
    margin-left: auto;
    margin-right: auto;
    width: 780px !important;
}

.messageGrid th {
    background: none repeat scroll 0 0 #CCC;
    border: 1px solid #CCC;
    color: #006699;
    font-weight: bold;
    padding: 6px 8px;
}

.messageGrid td {
    background: none repeat scroll 0 0 #FFF;
    border: 1px solid #CCC;
    padding: 6px 8px;
}

table.messageGrid > tbody > tr > td:first-child div {
    width: 500px;
    word-wrap: break-word;
}

table.messageGrid > tbody > tr > td:last-child span {
	font-weight: bold;
    line-height: 18px;
}

table.messageGrid > tbody > tr > td:last-child >div {
    width: 110px;
}

/* table, td, th
{
	border:1px solid #d3d3d3;
	width: 50%;
} */

#referralTable, #tab2, #referralTable td, #referralTable th, #tab2 td, #tab2 th{
	border:1px solid #d3d3d3;
	width: 50%;
}

.slideImgBlock {
  float: right;
  margin-right: 70px;
}


#referralTable tr {
  line-height: 20px;
  margin-bottom: 5px;
}

#referralTable tr td {
  padding: 1px 3px;
  cursor: pointer;
}
img {
  border: 1px solid;
}
.poweredBy {
    float: right;
    padding-right: 10px;
    padding-top: 0px;
}
.bottombuttons {
    left: 0;
    position: absolute;
    top: 605px;
    width: 100%;

}

.idl_page_tabs{
	width: inherit;
	height: 28px;
	display:inline-block;
}
.vdn_tab{
	float: left;
	width: 218px;
	height: 29px;
	background-color: #97D444;
	text-align:left;
	color: #fff;
	padding-left: 10px;
	padding-top: 5.22px;
	font-size: 17px;
	font-weight: bold;
	border-radius: 10px 10px 0 0;
	cursor: pointer;
}
/*.pdet_promos_tab:hover{
	background-color: #545454;
}*/
.referrer_tab{
	float: left;
	width: 218px;
	height: 29px;
	background-color: #666666;
	text-align:left;
	color: #fff;
	padding-left: 10px;
	padding-top: 5.22px;
	font-size: 17px;
	font-weight: bold;
	border-radius: 10px 10px 0 0;
	cursor: pointer;
}

.same_call_tab{
	float: left;
	width: 214px;
	height: 29px;
	background-color: #666666;
	text-align:left;
	color: #fff;
	padding-left: 10px;
	padding-top: 5.22px;
	font-size: 17px;
	font-weight: bold;
	border-radius: 10px 10px 0 0;
	cursor: pointer;
}


.ui-autocomplete {
    height: 150px;
    width: 420px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
  }
 
  input.css3SearchButton {
    background: skyblue -moz-linear-gradient(center top , #4cbb17, #9aff9a, #4cbb17) repeat scroll 0 0;
    border-radius: 8px;
    cursor: pointer;
    font-size: 14px;
    height: 28px;
    width: 120px;
}
.ui-menu .ui-menu-item a {
    display: block;
    line-height: 1.5;
    text-decoration: none;
}
.ui-widget {
    font-family: Verdana,Arial,sans-serif;
    font-size: 15px;
}
.ui-widget-content {
    background: #ffffff url("../images/ui-bg_flat_75_ffffff_40x100.png") repeat-x scroll 50% 50%;
    border: 1px solid #d3d3d3;
    color: #222222;
    z-index: 99999;
}
</style>

<!--<style>
@import "<c:url value='/assets/lightface.css'/>";
</style>
--><link rel="stylesheet" href="<c:url value='/style/auth_css.css'/>" />
<!--<link rel="stylesheet" href="<c:url value='/assets/lightface.css'/>" />

--><script	src="https://ajax.googleapis.com/ajax/libs/mootools/1.3.0/mootools.js"></script>

<!--<script src="<c:url value='/assets/mootools-more-drag.js'/>"></script>
<script src="<c:url value='/script/lightface.js'/>"></script>
<script src="<c:url value='/script/lightface.IFrame.js'/>"></script>
<script src="<c:url value='/script/lightface.Image.js'/>"></script>
<script src="<c:url value='/script/lightface.Request.js'/>"></script>
<script src="<c:url value='/script/lightface.Static.js'/>"></script>
<script src="<c:url value='/script/flowplayer-3.2.6.min.js'/>"></script>-->

<script src="<c:url value='/script/html_save.js'/>"></script>
<script src="<c:url value='/js/jquery-1.8.0.js'/>"></script>
<script src="<c:url value='/js/jquery-ui-1.11.4.js'/>"></script>
<script src="<c:url value='/js/blockUI.js'/>"></script>

<script type="text/javascript">
var sessionActiveTime = parseInt('${sessionActiveTime}');

function srollToNextRow(){
	try{
		var tableObject = document.getElementById("referralTable");
		if(document.getElementById(currentSelectedId)!=null){
			var currentIndex = document.getElementById(currentSelectedId).rowIndex;
			
			if(currentIndex+1!=tableObject.rows.length){
				
				for(var i=0;i<tableObject.rows.length;i++){
					
					if((tableObject.rows[i]).id==currentSelectedId){
						tableObject.rows[i].setAttribute("style","background-color: #fff;");
						
						i++;	
						
						currentSelectedId = (tableObject.rows[i]).id;
						tableObject.rows[i].setAttribute("style","background-color: #99CC33;");
						$("#referralDet").val(currentSelectedId);
	
						//this logic is for down scrolling......
						$("#referalBlock").scrollTop(scrollerValue);
						scrollerValue = scrollerValue+15;
					}
				}
			}
		}
	}catch(err){alert(err);}
}


function srollToPreviousRow(){
	try{
		var tableObject = document.getElementById("referralTable");
		if(document.getElementById(currentSelectedId)!=null){
			var currentIndex = document.getElementById(currentSelectedId).rowIndex;
			
			if(currentIndex!=0){
				
				for(var i=0;i<tableObject.rows.length;i++){
					
					if((tableObject.rows[i]).id==currentSelectedId){
						
						currentSelectedId = (tableObject.rows[i-1]).id;
						tableObject.rows[i-1].setAttribute("style","background-color: #99CC33;");
						
						tableObject.rows[i].setAttribute("style","background-color: #fff;");
						$("#referralDet").val(currentSelectedId);
	
						//this logic is for up scrolling......
						$("#referalBlock").scrollTop(scrollerValue);
						scrollerValue = scrollerValue-15;
					}
				}
			}
		}
	}catch(err){alert(err);}
}


var scrollerValue = 0;

var vdnSrchJsonObj = JSON.parse('${vdnWithReferrerNameJSONObject}');
var referrerName = "";
var vdnExID = ""; 
function vdnSearchs()
{
	referrerName = "";
	var vdnNumber = $("#vdnSearchId").val().trim();
	if(vdnNumber.length == 0){
		$("#vdnSearchErrorMsg").text("Please enter the 5 digit number from your phone");
		$("#vdnSearchMsg").css("display","none");
		$("#vdnSearchErrorMsg").css("display","block");
		return;
	}
	if( vdnNumber.length > 4 && vdnSrchJsonObj[vdnNumber] != undefined)
	{
		var referrerNameAndID = vdnSrchJsonObj[vdnNumber];
		var referrerArray = referrerNameAndID.split("\|");
		referrerName = referrerArray[1];
		vdnExID = vdnNumber;
		$( "#referralDet" ).val( referrerNameAndID+"|"+vdnNumber);
		$("#vdnSearchMsg").text("Referrer Name: "+referrerName);
		$("#vdnSearchMsg").css("display","block");
		$("#vdnSearchErrorMsg").css("display","none");
		$("div#subDiv").css("display","block");
	}
	else
	{
		$("#vdnSearchErrorMsg").text("VDN not found, please try again or search by Referrer Name.");
		$("#vdnSearchErrorMsg").css("display","block");
		$("#vdnSearchMsg").css("display","none");
	}
}

 	function enableVDNTab()
 	{
 		
 	 	$("#referralDet").val('');
		$("div#subDiv").css("display","none");
		$("#vdnSearchId").val('');
		$("#vdnSearchMsg").text('');
		$("#vdnSearchErrorMsg").text('');
		$("#vdn_content").show();
	 	$("#referrer_content").hide();
	 	$("#sameCall").hide();
	 	$("#vdnTab").css("background-color", "#97D444");
	 	$("#referrerTab").css("background-color", "#666666");
	 	$("#sameCallTab").css("background-color", "#666666");
	 	$("#vdnTab").unbind("click");
	 	$("#sameCallTab").unbind("click");
	 	$("#referrerTab").unbind("click");
	 	$("#sameCallTab").click(enableSameCallTab);
	 	$("#referrerTab").click(enableReferrerTab);
 	}

 	function enableReferrerTab()
 	{
 		$("#referralDet").val('');
		if($("#vdnSearchId").val().length > 0 && referrerName.length > 0){
			$("#referrerSearch").val(referrerName);
			$("div#subDiv").css("display","block");
		}else{
			$("#referrerSearch").val('');
			$("div#subDiv").css("display","none");
		}
		$("#vdn_content").hide();
		$("#sameCall").hide();
		$("#referrer_content").show();
		$("#vdnTab").css("background-color", "#666666");
		$("#sameCallTab").css("background-color", "#666666");
		$("#referrerTab").css("background-color", "#97D444");
		$("#sameCallTab").unbind("click");
	 	$("#vdnTab").unbind("click");
		$("#vdnTab").click(enableVDNTab);
		$("#sameCallTab").click(enableSameCallTab);
		$("#referrerTab").unbind("click");
		
 	}
 	
 	function enableSameCallTab()
 	{
 		
 		$("#referralDet").val('');
 		var sameCallFlag = '${sameCall}';
 		var firstName = '${firstName}';
 		var middleName = ' ${middleName}';
 		var lastName = ' ${lastName}';
 		var prefix = '${prefix} ';
 		var suffix = ' ${suffix}';
 		var street1 = '${address1}';
 		var unitType = '${unitType}';
 		var unitNumber = ' ${unitNumber}';
 		var city = '${city}';
 		var state = ' ${state}';
 		var zip = ' ${zip}';
 		var vdn = ' ${vdn}';
 		var referrerName = ' ${referrerName}';
 		$("div#subDiv").css("display","none");
 	 	if(sameCallFlag  != '' && sameCallFlag == 'true'){
 	 	 	var referralDet = '${referralDet}';
 	 	 	if(referralDet != ''){
 	 	 		var referralArray = referralDet.split("|");
 	 	 		if(referralArray.length >= 3 ){
 	 	 			$('#sameCallRefName').text('').text(referralArray[1]);
 	 	 			$('#sameCallVDN').text('').text(referralArray[2]);
 	 	 	 	}else if(referralArray.length >= 2 ){
                    $('#sameCallRefName').text('').text(referralArray[1]);
 	 	 	 	}
 	 	 		$("#referralDet").val(referralDet);
 	 	 		
 	 	 		if(referrerName !== undefined && referralArray[1] !== undefined && vdn !== undefined && referralArray[2] !== undefined){
	 	 	 		if((referrerName.trim() === referralArray[1].trim()) &&  ((vdn.trim() === referralArray[2].trim()))){
	 	 	 	 		$("#sameCallCustName").html(prefix+firstName+ middleName+ lastName+suffix);
	 	 	 	 		$("#sameCallAddress1").html(street1);
	 	 	 	 		$("#sameCallAddress2").html(unitType+ unitNumber);
	 	 	 	 		if(city != undefined && city != "" ){
	 	 	 	 			$("#sameCallZip").html($.trim(city)+","+ state+"&nbsp;&nbsp;"+zip);
	 	 	 	 		}
	
	 	 	 		}
 	 	 		}
 	 	 	}
 	 	 	$("div#subDiv").css("display","block");
 	 	}
		$("#vdn_content").hide();
		$("#referrer_content").hide();
		$("#sameCall").show();
		$("#vdnTab").css("background-color", "#666666");
		$("#referrerTab").css("background-color", "#666666");
		$("#sameCallTab").css("background-color", "#97D444");
		$("#vdnTab").unbind("click");
		$("#referrerTab").unbind("click");
		$("#vdnTab").click(enableVDNTab);
		$("#referrerTab").click(enableReferrerTab);
		$("#sameCallTab").unbind("click");
 	}

 var isReferrerSelected = false;
 $(document).ready(function(){
	$("#vdnTab").click(enableVDNTab);
	$("#referrerTab").click(enableReferrerTab);
	$("#sameCallTab").click(enableSameCallTab);
	$("#vdnSearchId").keydown(function(e)
	{
		   var key = e.which || e.keyCode;

	 if (!e.shiftKey && !e.altKey && !e.ctrlKey &&
           // numbers   
               key >= 48 && key <= 57 ||
           // Numeric keypad
               key >= 96 && key <= 105 ||
           // comma, period and minus, . on keypad
              key == 190 || key == 188 || key == 109 || key == 110 ||
           // Backspace and Tab and Enter
              key == 8 || key == 9 || key == 13 ||
           // Home and End
              key == 35 || key == 36 ||
           // left and right arrows
              key == 37 || key == 39 ||
           // Del and Ins
              key == 46 || key == 45){
               
            if( key == 13){
            	  vdnSearchs();
             }

            if(key == 8 && $(this).val().length > 0){
            	   $("div#subDiv").css("display","none");
            	   $("#vdnSearchMsg").css("display","none");
           		    $("#vdnSearchErrorMsg").css("display","none");
               }
        }else{
          e.preventDefault();
        }
	});
	var businessPartyArray = JSON.parse('${businessPartyJSONArray}');
	try{
	$("#referrerSearch").autocomplete({
		minLength: 0,
		source: businessPartyArray,
		focus: function( event, ui ) {
			isReferrerSelected = false;
	  		return false;
		},
		select: function( event, ui ) {
	 		$( this ).val( ui.item.label );
	 		$( "#referralDet" ).val( ui.item.value );
	 		$("div#subDiv").css("display","block");
	 		isReferrerSelected = true;
	 		return false;
		},
		close: function( event, ui ) {
			//To remove content from text box when we not selected because we are storing the value in text box on focus.
			if( !isReferrerSelected )
			{
				$( "#referralDet" ).val( "" );
				$("div#subDiv").css("display","none");
			}
		},
		response: function( event, ui ) {
			isReferrerSelected = false;
	        if((ui.content).length==0)
	        {
	        	$("div#subDiv").css("display","none");
	        	$( "#referralDet" ).val( "" );
	        }
	    }
	 }).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
	  	return $( "<li style='border-bottom:1px solid #d3d3d3;'>" ).append( item.label ).appendTo( ul );
	};
	}catch(err){alert(err);}
	if(isNaN(sessionActiveTime)){
		setInterval(function(){redirectTOSessionTimeout();},1800*1000);
	}else{
		setInterval(function(){redirectTOSessionTimeout();},sessionActiveTime*1000);
	}
	$('#startCall').click(function(){
        $('.bottombuttons').show();
		
		var sameCallFlag = '${sameCall}';
		var samecallEnable = '${enableSamecallRestriction}';
		 if(sameCallFlag != '' && sameCallFlag == 'true' && samecallEnable != undefined && samecallEnable =="true"){
			 $("#sameCallTab").css("display","block");
			 var referralDet = '${referralDet}';
			 if(referralDet != ''){
				 $("#vdnTab").css("display","none");
				 $("#referrerTab").css("display","none");
				 enableSameCallTab();
			 }else{
				 $("#sameCallTab").css("display","none");
				 $("#vdnTab").css("display","block");
				 $("#referrerTab").css("display","block");
				 enableVDNTab();
			 }
		 }else if(sameCallFlag != '' && sameCallFlag == 'true'){
			 $("#sameCallTab").css("display","block");
			 enableSameCallTab();
		 }
		
		var path = "<%=request.getContextPath()%>";
		var url = path+"/salescenter/checkSession";
		$.ajax({
			type: 'POST',
			url: url,
			success: onCompleteRequest,
			error: onError
		});
		
	});

	
	 //$("#referalId").change(sendReferalId);
	 $("#referralTable tr").click(sendReferralDet);

	 $('body').keypress(function(event) {
		 try{
			if(event.keyCode==40)
				srollToNextRow();
			else if(event.keyCode==38)
				srollToPreviousRow();
		 }catch(err){alert(err);}
	});
	 /* var sameCallFlag = '${sameCall}';
		 if(sameCallFlag != '' && sameCallFlag == 'true'){
			 $("#sameCallTab").css("display","block");
			 enableSameCallTab();
		 } */
});

function redirectTOSessionTimeout(){
    var path = "<%=request.getContextPath()%>";
    window.location.href = path+"/salescenter/session_timeout";
}

function confirmEndCall(){
	try{
		$("#endcall-dialog-confirm").dialog( {
			resizable : false,
			title : "Warning",
			height : 197,
			width : 477,
			modal : true
		});
	}catch (err) {
		alert(err)
	}
}

function goToCloseCallPage()
{
	savePageHtml(false, "");
	$("#endcall-dialog-confirm").dialog('close');
	$("form#end").submit();
}

function hidePopUp()
{
	$("#endcall-dialog-confirm").dialog('close');
}

var onCompleteRequest = function(data){
	if(data==true){
		$(".slideImgBlock").hide();
		//Clear the timer object for stop the ajax call to load the images when we click on start button.
		clearTimeout(timerObj);
		$("#referalSearchBlock").show();
		//$("#referalBlock").show();
		//$("#referalBlock").css("float", "right");
		$("#UIContentTopper_text_headlineId").css("display", "block");

		//change Start Call to End Call
		$("#startCall").val("End Call").attr({"id":"endCall","name":"endCall","class":"endcallbtn","onClick":"confirmEndCall();"});
	}else if(data=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}
}


var onError = function() {
	return function(data, textStatus, xhr, h4_id){
		alert("error\n" + data + ", " + textStatus + ", " + xhr);
	}
}

function isEmpty( el ){
    return !$.trim(el.html());
}

function goToGreetings(){
	//referrerSearch	
	if ($('#referrerSearch').is(':visible')) {
		if(document.getElementById("referrerSearch") && document.getElementById("referrerSearch").value) {
			 $("form#profileInfo").submit(); 
		} else {
			$("#validatorMsg").show();
		}
	}
	if ($('#vdnSearchId').is(':visible')) {
		if(document.getElementById("vdnSearchId") && document.getElementById("vdnSearchId").value) {
			 $("form#profileInfo").submit(); 
		} else {
			$("#validatorMsgVDN").show();
		}
	}	

	if ($('#sameCall').is(':visible') && $('#referralDet').val() != ''){
		$("#sameCallValue").val("true");
		 $("form#profileInfo").submit(); 
	}
}

var currentSelectedId;

var sendReferralDet = function () {
    var id = this.id;
    //scrolling value.....
    scrollerValue = (this.rowIndex)*15;
    
    currentSelectedId = this.id;
    $("#referralDet").val(id);
    $('tr.referralName').each(function(){
        if(id == this.id){
        	$(this).css("background-color","#99CC33");
        	 $("div#subDiv").show();
        }else{
        	$(this).css("background-color","#fff");
        }
    });
    //var newId = $("#referralDet").val();
}

var config = {
            contextPath: '${pageContext.request.contextPath}'
};


        function loadPage(list) {
  			location.href = list.options[list.selectedIndex].value;
		}
	
var sendReferalId = function () {
    $("#submit").click();
}

$(window).on('beforeunload', function(){
	$.blockUI({ message: $('#domMessage') }); 
}); 
</script>
</head> 
<body>

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
								<input type="hidden" id="savedAMBMessageData" value='${savedAMBMessageData}'>
								<input type="hidden" id="fuseUrl" value='${fuseUrl}'>
								<input type="hidden" id="enable_fuse_analytics" value='${enable_fuse_analytics}'>
								<input type="hidden" id="newAMBMessageData" value='${newAMBMessageData}'>
								<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
								<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
								<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
								<span class="cell idelpageTitle">Idle Page</span>
							</div>
						</header>
<div class="idleDiv" style="height: 415px;">
<div class="ambBlock" style="display: none;">
	<div>
		<span class="ambHead">Test AgentMessageBroker Client</span>
		<div class="ambUserDetails">
			<span class="boldFont">Username: </span><span>${username}</span>
			<span class="boldFont">PhoneId: </span><span>${phoneId}</span>
			<!--<div id="messages"></div>-->
		</div>
	</div>

	<!--<div style="margin-top: 10px; display:none;">-->
	<div style="margin-top: 10px;">
		<table id="tab2" border="1px" class="messageGrid">
			<thead>
				<tr>
					<th>CTI Messages</th>
					<th>Public Messages</th>
					<th>Greetings Data</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>
<section id="content">
	<form id="profileInfo" name="profileInfo" method="POST" action="<%=request.getContextPath()%>/salescenter/callFlowPath" >
		<input type="hidden" id="referralDet" value="" name="referralDet" />
		<input type="hidden" id="sameCallValue" name="sameCall" value=""/>
		<input type="hidden" id="pageTitle" name="pageTitle" value="IdlePage"/>
		<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
		<div style="width: 100%; padding-top: 5px;"   id="UIContentTopper"
			class="UIContentTopper clearfix">
			<!-- <img class="UIContentTopper_thumbnail"
				style="width: 130px; height: 80px;" src="<%=request.getContextPath()%>/image/google-fiber.jpg"> -->
			<div class="UIContentTopper_text_container" style="text-align: center;">
				<div class="UIContentTopper_text_headline" id="UIContentTopper_text_headlineId"
					style="display:none; width:92%; color: black; font-size: 18px; font-weight: bold; margin-top: 20px;  margin-left: -26px">
					<%=SessionCache.INSTANCE.getUserName(request.getSession()) %>, Please Select The Referrer
				</div>
				<div class="UIContentTopper_text" style="font-size: 14px;"></div>
			</div>
		</div>
		<div align="center">
		<!-- <div><img alt="Home Page" src="../images/SYP_idle.png"> -->
		
			<c:if test="${! empty list && fn:length(list) != 0}">
				<select id="states" name="states" onchange="loadPage(this.form.elements[0])">
					<c:forEach var="state" items="${list}">
						<option value="${state}">${state}</option>
					</c:forEach>
				</select>
			</c:if>
		  	
		  	<div id="referalSearchBlock" style="display: none;">
		  		<div class="idl_page_tabs"style="width: 684px; margin-left: 4px;">
		  		    <div class="same_call_tab" id="sameCallTab" style="color: white; font-weight: bold; font-size: 17px; display:none;">Same Call</div>
					<div class="vdn_tab" id="vdnTab" style="color: white; font-weight: bold; font-size: 17px">Search By VDN</div>
					<div class="referrer_tab" id="referrerTab" style="color: white; font-weight: bold; font-size: 17px">Search By Referrer</div>
			 	</div>
			 	<div>
				 	<div class="referalSearchSection" style="border:3px solid #c1c1c1;padding-left: 8px;width: 666px;height:300px;">
						<div align="left" id="vdn_content">
							<div style="padding-bottom:15px;padding-top:3px;">
								<span id="vdnSearchNote" style="margin:0px; color: black; font-size: 16px;"><br>Agent, please enter the 5 digit number from your phone<br><br></span>
							</div>
							<label style="font-size: 16px; font-weight: bold; color: black;">VDN&nbsp;</label><input type="text" name="vdnSearch" id="vdnSearchId" value="" style="height: 24px;width: 220px;" maxlength="5"/>
							&nbsp;<input type="button" name="submitSearch" value="Search" onclick="vdnSearchs();"  style="padding-left:6px;padding-right:6px;"/>
													<div class="row" id="validatorMsgVDN" style="display:none; color:#F00;" >
														<span>* Required Field</span>
													</div>
							<br/><br/>
							<span id="vdnSearchMsg" style="margin:0px;font-weight: bold;" ></span>
							<span id="vdnSearchErrorMsg" style="margin:0px;font-weight: bold;color: red;" ></span>
						</div>
						<div id="referrer_content" style="display:none;" align="left">	
							<div class="pdet_hl_content_cont" id="pdet_hl_content_cont"><br/><br/>	
							   <label style="font-size: 16px; font-weight: bold; color: black;">Referrer Name Search&nbsp;</label> 
							   <input type="text" name="referrerSearch" id="referrerSearch" value="" style="height: 24px;width: 425px;"></input>
							   						<div class="row" id="validatorMsg" style="display:none; color:#F00;" >
														<span>* Required Field</span>
													</div>
						    </div>
					    </div>
					    <div id="sameCall" style="display:none;" align="left">	
							<div style="padding-bottom:10px;">
								<span id="vdnSearchNote" style="margin:0px;color: black; font-size: 16px;">
								<br><%=SessionCache.INSTANCE.getUserName(request.getSession()) %>, if you’re on the same phone call, just hit ‘Forward’. <br>
                                 You’ll be able to make changes to the name and address, if needed</span> 
                               </div>
                               <br>
							<div class="pdet_hl_content_cont" id="pdet_hl_content_cont">	
							   <label style="font-size: 16px; font-weight: bold; color: black;">Referrer Name: </label>
							   <span id="sameCallRefName"></span><br>
							   <label style="font-size: 16px; font-weight: bold; color: black;">VDN: </label>
							   <span id="sameCallVDN"></span><br/>
							   <label style="font-size: 16px; font-weight: bold; color: black;">Name: </label>
							   <span id="sameCallCustName"></span><br>
							   <label style="font-size: 16px; font-weight: bold; color: black;">Address 1: </label>
							   <span id="sameCallAddress1"></span><br>
							   <label style="font-size: 16px; font-weight: bold; color: black;">Address 2: </label>
							   <span id="sameCallAddress2"></span><br/>
							   <label style="font-size: 16px; font-weight: bold; color: black;">City/State/Zip: </label>
							   <span id="sameCallZip"></span>
						    </div>
					    </div>
					    
				   </div>
		  		</div>
		  	</div>
			<div id="referalBlock" align="center"  style="display: none;text-allgn:center;width:500px;height:323px;overflow-y:auto;">
				
				<table id="referralTable" align="center" style="width:100%;border:1px solid #d3d3d3;font-size: 15px;" cellpadding="2" cellspacing="1" border="1" >
					<c:forEach var="business" items="${businessPartyList}">
						<tr id="${business.externalId}|${business.name}|${business.vdn}" class="referralName">
							<td>
								${business.name}
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			
			<div class="slideImgBlock">
				<img src="${imgPath}slide_1.jpg" id="slideImg" alt="slide_1" height="460" width="850"/>
			</div>
		</div>	
	</form>
	<script type="text/javascript">
		var imgName = 'slide_';	
		var imgType = '.jpg';	
		var imgPath = '${imgPath}';		
		var imgDelay = '${imgDelay}';	
		var i = 1;
		var len = '${imgCount}';
		var timerObj ;
		function defaultLoad(){
			var random1 = Math.random;
			document.getElementById("slideImg").src = imgPath+imgName+i+imgType+"?"+random1;
			timerObj = setTimeout("defaultLoad()", imgDelay);
			if(len > i){
			    i = i + 1;
			}else{
			    i = 1;
			}
		}
		defaultLoad();
	</script>
	</div>
	
	<div id="subDiv" style="display: none;">
	   <div class="bottombuttons">
            <div class="rightbtns" style="padding-right:5px;">		
			<input type="button" id="submit"   value="Forward >" onclick="goToGreetings();"  style="padding-left:6px;padding-right:6px;"/>
			</div>
       </div>
	</div>
	
</section> 
<div id="domMessage" style="display:none;"> 
<img src="<%=request.getContextPath()%>/images/loading.gif" border="0"/>
    <h2>Loading</h2> 
</div>  
</body>
