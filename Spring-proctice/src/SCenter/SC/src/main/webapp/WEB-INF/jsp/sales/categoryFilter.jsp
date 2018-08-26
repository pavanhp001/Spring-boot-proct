<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style type="text/css">
.dataFilterSelect {
	font-size:9px;
	max-width:90px;
}
#latino {
  float: left;
  margin-right: 3px;
  vertical-align: text-top;
}
</style>
<script >
$(document).ready(function(){
	$("#internetSpeed").change(categoryFilter);
	$("#channels").change(categoryFilter);
	$("#contractTerm").change(categoryFilter);
	$("#latino").click(categoryFilter);

	$("#clear").click(clear);
	
});
var clear = function(){
	var path =$("input#contextPath").val();
	var url = path+"/salescenter/recommendationsbyCategory/filtering";
    var data = {};
    
    $("#internetSpeed").val("");
    $("#channels").val("");
    $("#contractTerm").val("");
    $('#latino').attr('checked', false);
    
    data["categoryName"] = $("input#categoryName").val();
    data["internetSpeed"] =   "";
    data["channels"] =  "";
    data["contractTerm"] =  "";
    data["path"] = $("input#contextPath").val();
    data["latino"] =  "0";
    
    //alert(data["latino"]);
    console.log($(this).val());
    data["result"] = $(this).val();
    try{ 
	    $.ajax({
	    	type: 'POST',
	    	url: url,
	    	data: data,
	    	success: onFilterComplete,
	 	});
	} catch(e){
		alert(e);
	}
}
var categoryFilter = function(){
	var path =$("input#contextPath").val();
	var url = path+"/salescenter/recommendationsbyCategory/filtering";
    var data = {};
    data["categoryName"] = $("input#categoryName").val();
    data["internetSpeed"] =   $("#internetSpeed").val();
    data["channels"] =  $("#channels").val();
    data["contractTerm"] =  $("#contractTerm").val();
    data["path"] = $("input#contextPath").val();
    if($("input[id='latino']:checked").length != 0){
    	data["latino"] =  "1";
    }else{
       	data["latino"] =  "0";
    }
    //alert(data["latino"]);
    console.log($(this).val());
    data["result"] = $(this).val();
    try{ 
	    $.ajax({
	    	type: 'POST',
	    	url: url,
	    	data: data,
	    	success: onFilterComplete,
	 	});
	} catch(e){
		alert(e);
	}
}
var onFilterComplete = function(data){
	$(".tabsWrapper").html(data);
	$('.pp_checkbox').click(showCompareButton);
	$('.ViewDetailsBtn').click(viewDetails);
}
</script>
<input type="hidden" value="" id="tempData"/>


					<!-- Widgets Text -->
					<div class="text_widget">
						<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
						<div class="text_container">
							<div class="title">Filter Products</div>
							<a href="#" id="clear" class="clear">Clear</a>
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>Internet Speed</td>
									<td>
										<select class="dataFilterSelect" id="internetSpeed" name="internetSpeed"
											<c:if test="${title != 'recommendations.TRIPLE_PLAY' && title != 'recommendations.DOUBLE_PLAY' &&
											title != 'recommendations.INTERNET' && title != 'recommendations.DOUBLE_PLAY_PI' 
											&& title != 'recommendations.DOUBLE_PLAY_VI'}">
				            					disabled="disabled"
				            				</c:if>
											>
											<option value="">Select</option>
											<option value="1.0-10.0">1-10 Mb</option>
											<option value="10.0-25.0">10-25 Mb</option>
											<option value="25.0">25+ Mb</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>Channels</td>
									<td>
										<select class="dataFilterSelect" id="channels" name="channels"
											<c:if test="${title != 'recommendations.TRIPLE_PLAY' && title != 'recommendations.DOUBLE_PLAY' &&
											title != 'recommendations.VIDEO'  
											&& title != 'recommendations.DOUBLE_PLAY_PV' && title != 'recommendations.DOUBLE_PLAY_VI'}">
				            					disabled="disabled"
				            				</c:if>
											>
											<option value="">Select</option>
											<option value="100">100+</option>
											<option value="120">120+</option>
											<option value="150">150+</option>
											<option value="200">200+</option>
											<option value="250">250+</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>Latino</td>
									<td>
										<input type="checkbox" id="latino" name="latino"
										<c:if test="${title != 'recommendations.TRIPLE_PLAY' && title != 'recommendations.DOUBLE_PLAY' &&
										title != 'recommendations.VIDEO'  
										&& title != 'recommendations.DOUBLE_PLAY_PV' && title != 'recommendations.DOUBLE_PLAY_VI'}">
			            					disabled="disabled"
			            				</c:if>
										/>
									</td>
								</tr>
								<tr>
									<td>Contract term</td>
									<td>
										<select class="dataFilterSelect" id="contractTerm" name="contractTerm"
											<c:if test="${title != 'recommendations.TRIPLE_PLAY' && title != 'recommendations.DOUBLE_PLAY' &&
											title != 'recommendations.VIDEO' && title != 'recommendations.INTERNET' &&
											title != 'recommendations.PHONE' && title != 'recommendations.NATURALGAS' 
											&& title != 'recommendations.DOUBLE_PLAY_PI' 
											&& title != 'recommendations.DOUBLE_PLAY_PV' && title != 'recommendations.DOUBLE_PLAY_VI'}">
				            					disabled="disabled"
				            				</c:if>
											>
											<option value="">Select</option>
											<option value="0">0</option>
											<option value="1">1</option>
											<option value="2">2</option>
										</select>
									</td>
								</tr>
							</table>
						</div>
					</div>
