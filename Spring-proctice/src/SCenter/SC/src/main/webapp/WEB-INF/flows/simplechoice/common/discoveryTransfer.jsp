<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title><fmt:message key="discovery.header" /></title>
<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
<script type="text/javascript">
$(document).ready(function(){
		populateFields();
		$("input[name='Discovery_Transfer_NonRR_3']").attr('maxlength','5').keypress(limitMe);
	    $("#submitSt").val("yes");
		/*$("input[id*='intFld_']").live("change", function(){
		    $("#submitSt").val("yes");
			$.each($("input[id*='intFld_']"), function() {
			    if($(this).val() > 99){
			    	$("#submitSt").val("no");
			    	$(this).after("<label style='color:red;font-size:18px;'>*</label>");
				    alert($(this).val());
				}
			});
		});*/
		$("input[id*='intFld_']").live("blur", function(){
			if($(this).val() > 99){
				//alert("Test");
			    //$(this).css("background-color","red");
				if(!$(this).next().is('label')){
					$(this).after("<label class='star' style='color:red;font-size:18px;'>*</label>");
				}
			}else{
			    //$(this).css("background-color","white");
			    var element = $(this).next();
				if($(this).next().is('label')){
				    $(element).remove();
				}
			}
		    $("#submitSt").val("yes");
			$.each($("input[id*='intFld_']"), function() {
			    if($(this).val() > 99){
			    	$("#submitSt").val("no");
				    //alert($(this).val());
				}
			});

		});
		$("input[id*='intFld_']").live("keydown", function(e) {
			var charCode = (e.which) ? e.which : e.keyCode;
			//alert("Keycode :: "+charCode);
		    if ((charCode >= 48 && charCode <=57) || (charCode >= 96 && charCode <= 105) || charCode == 8 || charCode == 9 || charCode == 46 || charCode == 37 || charCode == 39){
		    	//alert("Keycode :: "+charCode);
				return true;
			}else{return false;}
		});
		$("input[id*='intFld_']").trigger('blur');

		jQuery(window).load(function () {
  	      //To save the html on page load
  	      savePageHtml(true, "");
  	});
});

$(document).ready(function(){
	$("#intFld_Discovery_Transfer_NonRR_3").change(function (){
		 var txtFldVal = $(this).attr("value");
		 if(txtFldVal.lastIndexOf("$") == -1  && txtFldVal != null && txtFldVal != ""){
			txtFldVal = "$"+ txtFldVal;
			$("#intFld_Discovery_Transfer_NonRR_3").val(txtFldVal);
		}
	}); 
});

function limitMe(e) {
    if (e.keyCode == 8) { return true; }
    if(this.value.length < $(this).attr("maxLength")){
    	return true;
    }else{
    	if(this.value.indexOf("$") != -1){
    		return true;
        }else{
        	return false;
           }
        }
}	

function populateFields() {

	var questionsListAsString = $("input[id='questionListForMAV']").val();
	var questionsList;
	if(questionsListAsString.length>0){
		questionsList = JSON.parse(questionsListAsString);
	}
	if(questionsList!=undefined && questionsList['Current TV']!=undefined){
		$("input[name='Discovery_Transfer_NonRR_1']").val(questionsList['Current TV']);
	}else{
		$("input[name='Discovery_Transfer_NonRR_1']").val("");
	}
	if(questionsList!=undefined && questionsList['Current Internet']!=undefined){
		$("input[name='Discovery_Transfer_NonRR_2']").val(questionsList['Current Internet']);
	}else{
		$("input[name='Discovery_Transfer_NonRR_2']").val("");
	}
	
	$("input[name='Discovery_Transfer_NonRR_3']").val("<c:out value="${questionList['Current total monthly bill']}"/>");
	$("input[name='Discovery_Transfer_NonRR_4']").val("<c:out value="${questionList['#TVS']}"/>");
	$("input[name='Discovery_Transfer_NonRR_5']").val("<c:out value="${questionList['#HD']}"/>");
	$("input[name='Discovery_Transfer_NonRR_6']").val("<c:out value="${questionList['#DVR']}"/>");

	if(questionsList!=undefined && questionsList['Channels']!=undefined){
		$("input[name='Discovery_Transfer_NonRR_7']").val(questionsList['Channels']);
	}else{
		$("input[name='Discovery_Transfer_NonRR_7']").val("");
	}
	
	$("input[name='Discovery_Transfer_NonRR_8'][value='<c:out value="${questionList['Movie Channels']}"/>']").attr('checked', 'checked');;
	$("input[name='Discovery_Transfer_NonRR_9'][value='<c:out value="${questionList['Internet']}"/>']").attr('checked', 'checked');

	if(questionsList!=undefined && questionsList['Internet Usage']!=undefined){
		$("input[name='Discovery_Transfer_NonRR_10']").val(questionsList['Internet Usage']);
	}else{
		$("input[name='Discovery_Transfer_NonRR_10']").val("");
	}

	$("input[name='Discovery_Transfer_NonRR_11']").val("<c:out value="${questionList['#Devices']}"/>");
	$("input[name='Discovery_Transfer_NonRR_12'][value='<c:out value="${questionList['Home Phone']}"/>']").attr('checked', 'checked');
}

function backToQualification(flowEventId){
	$("#discovery input[id='_eventId']").val(flowEventId);
	$("form#discovery").submit();
}


function goToPowerPitchDisplay(){

	 //alert("On Submit"+$("#submitSt").val());
	if($("#submitSt").val()=="no"){
		return false;
	}

	//To save the html on page submit
	savePageHtml(false, "");

	
	$("form#discovery").submit();
}
</script>

<style type="text/css">
.coaching 
{
        color: green;
}
#dialogue_content p {
    font-size: 0px;
    margin: 0px;
}
span.any{
	float:left;
	padding:2px;
	width:420px;
}
span.noborder{
	float: left;
	margin-left: 15px;
	margin-top: 11px;
}
span.parenthead{
	display:inline-block;
	line-height: 18px;
}

.discoveryformcontent {
	background-color: #FFFFFF;
	border: 1px solid #616264;
	border-radius: 10px 10px 10px 10px;
	font-size: 12px;
	margin-left: 160px;
	padding: 13px 20px;
	width: 675px;
	text-align: left;
}

.discoveryformcontent input[type="text"] {
    border: 1px solid #616264;
    border-radius: 3px 3px 3px 3px;
    font-size: 12px;
    height: 22px;
    padding: 2px;
}
.pagecontainer {
	height: 450px !important;
}
span.noborder p{
	margin: 0;
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
								<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
								<span class="cell pageTitle"><fmt:message key="discoverytransfer.header"/></span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="discovery" name="discovery "action="${flowExecutionUrl}" method="post" autocomplete="off">
							<input type="hidden" id="questionListForMAV" value="<c:out value="${questionListForMAV}"/>">
							<input type="hidden" id="submitSt" name="submitSt"/>
							<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="pageTitle" name="pageTitle" value="Discovery Transfer">
							<input type="hidden" id="_eventId" name="_eventId" value="discoveryEvent">
							<div class="contentwrapper">
								<div id="action_wrapper">
									<div class="widget_container">
										<div class="pagecontainer" align="center">
											<!-- Form Content -->
											<section class="discoveryformcontent" >
												${dialogueB}
											</section>
										</div>
									</div>
											<div id="errorMsg" style="color:#F00;display:none;font-weight:bold;text-align:center">* Please Enter Two Digit Number Only</div>
											<!-- Form Content -->
										<!-- </div>
									</div> -->
								</div>
								<div class="bottombuttons">
									<div class="rightbtns">
										<input type="button" id="discoveryTransferBack" name="discoveryTransferBack" value="< Back" onclick="backToQualification('backToQualificationEvent');" />
										<input type="button" id="discoveryTransferForward" name="discoveryTransferForward" value="Forward >" onclick="goToPowerPitchDisplay();"/>
									</div>
								</div>
							</div>
							<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
							<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
							</form>
						</section><!--  Content -->
					</section><!-- Content Full Cont -->
