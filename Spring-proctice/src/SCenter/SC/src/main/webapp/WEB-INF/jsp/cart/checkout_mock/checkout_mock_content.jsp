<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>


<script src="<c:url value='/js/feedback_CKO.js'/>"></script>

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
</style>

<script type="text/javascript">


function startTime()
{
	var date = new Date();
	var hh = date.getHours();
	var mm = date.getMinutes();
	var ss = date.getSeconds();

	var suffix = "AM";
	if (hh >= 12) {
		suffix = "PM";
		hh = hh - 12;
	}
	if (hh == 0) {
		hh = 12;
	}
	
	hh = checkTime(hh);
	mm = checkTime(mm);
	ss = checkTime(ss);
	document.getElementById('currentTime').innerHTML = hh + ":" + mm + ":" + ss + " " +suffix;
	
}

var initss = 0;
function startTimer()
{
	initss++;
	var ss = checkTime(initss%60);
	var mm = checkTime(parseInt(initss/60));
	document.getElementById('totalTime').innerHTML = mm + ":" + ss;
}

function checkTime(i)
{
	if (i<10)
	{
	  i="0" + i;
	}
	return i;
}

setInterval(function(){startTime()},500);
setInterval(function(){startTimer()},1000);

</script>
</head>
<body onload="eventFeedbackLoad();" onSubmit="eventFeedbackSubmit()"  >
<form id="iframeForm" action="<%=request.getContextPath()%>/salescenter/CKO_mock_content"	method="post">
	<div>
		Current Time: <span id="currentTime"></span> Total Time: <span id="totalTime">00:00</span>
	</div>
	<div style="border:solid 2px #FFF4C8;;margin-top:10px;width:85%;">
		<textarea rows="15" cols="80" id="iFeedback" name="iFeedback" style="width:100%">${iData}</textarea>
		<input id="iData" name="iData" value='${iData}' type="hidden" />
	<input style="height:30px; width:130px;" type="submit" value="SUBMIT">
	 </div>
	
</form>

 <script type="text/javascript">
 function init() {
    document.getElementById('iframeForm').onsubmit = function() {
        parent.document.getElementById('iframeForm').target = 'CKOContent';  
    }
 }
 </script>
</body>
</html>