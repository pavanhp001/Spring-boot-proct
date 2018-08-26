<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<link media="all" href="<c:url value='/style/utils.css'/>" type="text/css" rel="stylesheet">
<style type="text/css">
</style>

<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.widget.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>

<script src="<c:url value='/script/cust_quick_summary.js'/>"></script>
<script >
$(document).ready(function(){
	getNotepad;
	$('textarea[name=notepadData]').blur(autoSave);
	var isDisCoveryTransfer = false;

	$("#discovery input[id='pageTitle']").each(function(){
		var discoveryPage = $(this).val();
		if( discoveryPage == "New Services" || discoveryPage == "Transfer Services" )
		{
			isDisCoveryTransfer = true;
			return;
		}
	});

	if( isDisCoveryTransfer )
	{
		$('table#dataTable').find("input[type='text']").attr("disabled", true);
	}

	$("input[id*='#']").live("keydown", function(e) {
		var charCode = (e.which) ? e.which : e.keyCode;
		//alert("Keycode :: "+charCode);
	    if ((charCode >= 48 && charCode <=57) || (charCode >= 96 && charCode <= 105) || charCode == 8 || charCode == 9 || charCode == 46 || charCode == 37 || charCode == 39){
	    	//alert("Keycode :: "+charCode);
			return true;
		}else{return false;}
	});	
	
	
});
var getNotepad = function(){
	var title = $('title').text();
	switch(title){
	case('Basic Information') :
	case('Confirmation'): 
	case('Utility Offer'):
	case('Offers'):
	case('Qualification'):
	case('Discovery Questions: New Services'):
	case('Discovery Questions: Transfer Services'):
		changeDisplay;
		break;
	default : table.css("display",'block');
		
	}
}
var changeDisplay = function(){
	var table = $('table#dataTable');
	//var toggleIcon = $('span#notepadToggle');
	//alert(toggleIcon+"icon");
	table.css("display",'none');
	//toggleIcon.css("background",'0');
}
var autoSave = function(){
	var path =$("input#noteContextPath").val();
	var originalValue = $("input#originalvalue").val();
	var newValue = $('textarea[name=notepadData]').val();
	if(originalValue != newValue){
		var url = path+"/salescenter/recommendations/save/notepad";
	    var data = {};
	    data["notepadValue"] = newValue;
	    $.ajax({
	    	type: 'POST',
	    	url: url,
	    	data: data,
	    	success: function(data) {
	    	$("input#originalvalue").val(newValue);
	    	$('div#msgDisplay').html(data).fadeToggle("slow", function () {
	    		$('div#msgDisplay').fadeToggle(1500, "linear");
	    	  });
	    	  }
	 	});
	}
}
function notepadtoggle(){
	if($("#notepad_widget").hasClass("notepad_widget")){
		$('#notepad_widget').removeClass('notepad_widget').addClass('notepad_widget1');
		$('#arrowsbtn').removeClass('minimize').addClass('maximize');
	}else{
		$('#notepad_widget').removeClass('notepad_widget1').addClass('notepad_widget');
		$('#arrowsbtn').removeClass('maximize').addClass('minimize');
	}
}

var autoSaveInput = function(text){
	var path =$("input#noteContextPath").val();
	var newValue = $('input[name="' + text + '"]').val();
	if(text.indexOf('#') > -1) {
		if(isNaN(newValue)) {
			alert(text + ': input needs to be numeric')
			} else {
				var url = path+"/salescenter/recommendations/save/notepadInput";
			    var data = {};
			    data[text] = newValue;
			    $.ajax({
			    	type: 'POST',
			    	url: url,
			    	data: data,
			    	success: function(data) {
			    	$('div#msgDisplay').html(data).fadeToggle("slow", function () {
			    		$('div#msgDisplay').fadeToggle(1500, "linear");
			    	  });
			    	  }
			 	});
				}
		} else {
					var url = path+"/salescenter/recommendations/save/notepadInput";
				    var data = {};
				    data[text] = newValue;
				    $.ajax({
				    	type: 'POST',
				    	url: url,
				    	data: data,
				    	success: function(data) {
				    	$('div#msgDisplay').html(data).fadeToggle("slow", function () {
				    		$('div#msgDisplay').fadeToggle(1500, "linear");
				    	  });
				    	  }
				 	});
			}

}
</script>
</head>


					<!-- Widgets Notepad1.1 -->
					<div id="notepad_widget" class="notepad_widget">
						<input type="hidden" id="noteContextPath" value="<%=request.getContextPath()%>" />
						<input type="hidden" id="originalvalue" value="${notepadValue}" />
						<c:choose>
    						<c:when test="${empty sessionScope.notePadMap}">
        						<c:set var="jspNotePadMap" value="${notePadMap}"/>
    						</c:when>
    						<c:otherwise>
        						<c:set var="jspNotePadMap" value="${sessionScope.notePadMap}"/>
    						</c:otherwise>
						</c:choose>
						<div class="notepad_container">
							<a href="#" onclick="notepadtoggle();"><div id="arrowsbtn" class="minimize"></div></a>
							<div class="title"><fmt:message key="discoverynotepad.header"/></div>
							<table cellpadding="0" cellspacing="0" id="dataTable">
							  	<c:forEach var="entry" items="${jspNotePadMap}" varStatus="count">
							  	<tr>
									<td width="50%"><c:out value="${entry.key}"/></td><td><input type="text" id="${entry.key}" name="${entry.key}" value="${entry.value}" style="border:none; border-color: transparent" onblur="autoSaveInput('${entry.key}')"/></td>	
								</tr>	  	
							  	</c:forEach>
							</table>
							<textarea id="notepadData" name="notepadData"><c:out value="${notepadValue}" escapeXml="false"/></textarea>
						</div>
					</div>
					<!-- End Widgets Notepad -->
