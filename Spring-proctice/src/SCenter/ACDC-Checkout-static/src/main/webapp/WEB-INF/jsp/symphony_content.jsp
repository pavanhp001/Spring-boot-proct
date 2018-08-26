<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<!-- <link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_base.css'/>" />  -->
<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/custom_form_elements.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>
     
<style type="text/css">

iframe {

	background-color: #FFFFFF;
    float: left;
    width: 1005px;
    height: 600px;
    margin-top: 50px;
    padding: 12px;
	overflow: hidden     
}

	.callButton {
	
    display: block; 
    margin: 0 auto; 
    margin-top:50px;
    display:block;
    
   
    background-color:#f5f5f5;
    border:1px solid #dedede;
    border-top:1px solid #eee;
    border-left:1px solid #eee;

    font-family:"Lucida Grande", Tahoma, Arial, Verdana, sans-serif;
    font-size:12px;
    line-height:130%;
    text-decoration:none;
    font-weight:bold;
    color:#565656;
    cursor:pointer;
    padding:5px 10px 6px 7px; /* Links */
}

</style>	

<script>
function go(){


}
</script>

<script>

function feedbackFunc(data) {
	var feedbackDiv = document.getElementById('feedback');
	feedbackDiv.innerHTML = data;
}

function callIframe() {
	  var iframe = document.getElementById('CKOContent');
	  iframe.src="/static/CKO";
}

//$(document).ready(function(){
///	$('#startCKO').click(function(){
//		var intentVal = $("#CKOInput").val();
				
//	});
//});

</script>
</head>
<body>

<div style="font-color:FFFFCC;" id="feedback" class="callButton">feedback</div>


<form action="<%=request.getContextPath()%>/static/CKO" method="post" target="CKOContent" onsubmit="symFeedbackSubmit()">
  <input type="submit" id="startCKO" class="callButton" value="SYP:Do Load CKO"/>
  <!-- orderNum : 20753 LIne Item Num: 20953-->
  <!-- orderNum : 3802  LIne Item Num: 3591-->
  <!-- orderNum : 22343 LIne Item Num: 21877-->
  <!-- orderNum : 29143 Line Item Num: 33359 -->
  <!-- orderNum : 32221 Line Item Num: 35527 -->
  <!-- orderNum : 46505 Line Item Num: 43235 -->
  <!-- orderNum : 48022 Line Item Num: 44031 -->
  <!-- orderNum : 48288 Line Item Num: 44151 -->
  <!-- orderNum : 48358 Line Item Num: 44187 -->
  <!-- orderNum : 48700 Line Item Num: 44187 -->
  <input type="text" name="CKOInput" id="CKOInput" value='{"CKO":{"customerId":209563797,"orderId":209473564,"parentUrl":"http://localhost:8099/digital/AL?execution=e1s21","status":"CKOReady","lineItems":{"long":[210179710]},"params":{"string":["certificate=ABC","page_id=101","provider_id=4142189","agentId=devtest","productExternalId=SECURITYCHOICE-WEB","guid=undefined","dynamicFlow.userGroup=defaultUserGroup","dynamicFlow.appVersion=1.0","dynamicFlow.flowType=confirm","channelType=web","channelCss=cox.css"]}}}' class="callButton" style="width:90%;" />
  		
</form>

<iframe id="CKOContent" name="CKOContent" src=""   >
 
 IFRAME GOES HERE
 </iframe>
</body>
</html>