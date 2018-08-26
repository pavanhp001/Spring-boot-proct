<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<script src="<c:url value='/js/jquery-1.8.0.js'/>"></script>
<script src="<c:url value='/js/feedback_main.js'/>"></script>

<style type="text/css">
.address {
	width: 300px;
	height: 110px;
	border: 2px solid #a1a1a1;
	border-radius: 5px;
	padding: 8px;
	display: none;
}

.rLabel {
	
}

.lLabel {
	float: left;
	font-weight: bold;
	width: 25%;
}

iframe, #CKOContent {
	border: 0 none;
    display: block;
    height: 500px;
    margin: 100px auto 0;
    width: 700px;
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
    padding:5px 10px 6px 7px;
    width: 500px;
}
</style>

<script>

function updateIframeSrc()
{
	var orderId = document.getElementById('orderId').value;
	var customerId = document.getElementById('customerId').value;
	var lineitemId = document.getElementById('lineitemId').value;
	var providerId = document.getElementById('providerId').value;
	var url = document.getElementById('url').value;
	var key = document.getElementById('key').value;
	
	
	var data = '{"CKO":{"customerId":'+customerId+',"orderId":'+orderId+',"status":"CKOReady","lineItems":{"long":['+lineitemId+']},"params":{"string":["certificate='+key+'","page_id=101","provider_id='+providerId+'"]}}}';
	data = url+"?CKOInput=" + data;
	alert(data);

	$("iframe#CKOContent").attr("src", data);
}
</script>
</head>
<body onload="updateIframeSrc()">

<input type="hidden" value="20163" id="orderId" name="orderId"  />
<input type="hidden" value="999" id="customerId" name="customerId"  />
<input type="hidden" value="20751" id="lineitemId" name="lineitemId"  />
<input type="hidden" value="24699452" id="providerId" name="providerId"  />
<input type="hidden" value="http://localhost:9999/att/att/CKO" id="url" name="url"  />
<input type="hidden" value="ABC123" id="key" name="key"  />


  	<iframe id="CKOContent" id="CKOContent" name="CKOContent" src="">
 		Content Goes Here
 	</iframe>
 
 <script type="text/javascript">
<!--
	var currentTime = new Date()
	var hours = currentTime.getHours()
	var minutes = currentTime.getMinutes()
	var seconds = currentTime.getSeconds()

	if (minutes < 10)
	minutes = "0" + minutes
	
	if (seconds < 10)
		seconds = "0" + seconds

	var suffix = "AM";
	if (hours >= 12) {
	suffix = "PM";
	hours = hours - 12;
	}
	if (hours == 0) {
	hours = 12;
	}

	//document.write("<div style='clear:both;'>Parent Initial Time:" + hours + ":" + minutes+ ":" + seconds + " "  + suffix + "</div>")
//-->
</script>
	 
</body>
</html>