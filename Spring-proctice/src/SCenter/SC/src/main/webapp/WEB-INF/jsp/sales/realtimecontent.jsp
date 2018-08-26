<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
#dataRefreshDiv{font: bold 10px arial; padding-bottom: 3px; float: right;margin-top: -5px;}
#afterDataRefreshDiv{font: bold 10px arial; padding-bottom: 3px; float: right;margin-top: -5px;}
a.reload{color: #009CDE;cursor: pointer;}
a.reload:hover{text-decoration:underline;color:blue}
.rts_container table {
  padding: 2px 0;
}
</style>
<script type="text/javascript">

$(document).ready(function(){
	//showButtonDiv();
	$("#dataRefresh").click(function(){
		getRefreshData();
	}); 
});
var w = window.setInterval(getWidgetInfo, <c:out value="${cycletime}"/>);
function showButtonDiv(){
	var curUrl = window.location.href;
	var showButton = curUrl.indexOf('recommendations') > -1 || curUrl.indexOf('recommendationsbyCategory') > -1;
	if (showButton) {
		$("div#dataRefreshDiv").show();
		$("div#afterDataRefreshDiv").hide();
	}	
} 

function getRefreshData() {
		var curUrl = window.location.href;
		var showButton =  curUrl.indexOf('recommendationsbyCategory') > -1;
		var data = {};
	 	var url = "<%=request.getContextPath()%>/salescenter/recommendations/refreshProductData";
		if (showButton) {
		    url = "<%=request.getContextPath()%>/salescenter/recommendations/refreshProductDataByCategory"; 
			var last = curUrl.lastIndexOf('/');
			var str  = curUrl.substring(last+1, curUrl.length);
			data = {"categoryName" : str};
		}
	    $.ajax({
	    	type: 'POST',
	    	data:data,
	    	url: url,
	    	success: successRefresh
	 	});
}

function successRefresh(data1){
	if(data1=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		var data = JSON.parse(data1);
		$("div#dataRefreshDiv").hide();
		$("div#afterDataRefreshDiv").show();
		$("div.tabsWrapper").html(data.value);
		$('.pp_checkbox').click(showCompareButton);
		$('.ViewDetailsBtn').click(viewDetails);
		$('section#maincontent > section.navcontainer > nav#products').html(data.productIconS);
	}
}

getWidgetInfo();

function getWidgetInfo(){
	 var url = "<%=request.getContextPath()%>/salescenter/recommendations/getProviderStatus";
	    $.ajax({
	    	type: 'POST',
	    	url: url,
	    	success: onSuccess
	 	});
}

function onSuccess(data){
	if(data=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		data = JSON.parse(data);
		var element='';
		try{
		var curUrl = window.location.href;
		var isRecommendationPage = curUrl.indexOf('recommendations') > -1 || curUrl.indexOf('recommendationsbyCategory') > -1;
		
		if (isRecommendationPage) {
			//alert(JSON.stringify(data.map));
			//alert(data.isSuccessed);
			var value = $("#tempData").val();
		 	if (data.isSuccessed) {
		 		$("div#afterDataRefreshDiv").hide();
				$("div#dataRefreshDiv").show();
				$("#tempData").val('true');
			} else{
				if(value=="")
					$("div#dataRefreshDiv").hide();
			}
		}
	
	 	for (var val in data.productMap) {
	 		var status = data.productMap[val].split("|");
			element += "<tr title = "+status[1]+" ><td>" + val + "</td><td>" + status[0]+ "</td></tr>";
		}
		//element += "</table>";
		if (!data.isPending) {
			window.clearInterval(w);
		}	
		}catch(err){alert(err);}
		$("#rts_widget_content").html(element);
	}
}
</script>

<style type="text/css">
</style>
<input type="hidden" value="" id="tempData"/>
<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
					<div class="rts_widget">
						<div class="rts_container">
							<div class="title">Real Time Status</div>
							<table id="rts_widget_content" cellpadding="0" cellspacing="0">
							</table>
							<div id="dataRefreshDiv" style="display: none;">
							<a id="dataRefresh" class="reload"  title="More products available">Refresh</a>
							</div>
							<div id="afterDataRefreshDiv" style="display: none;color: #FF0063">Products Refreshed</div>
						</div>
					</div>
