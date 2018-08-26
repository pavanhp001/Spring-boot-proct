<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<%-- <script src="<c:url value='/script/feedback.js'/>"></script> --%>
<script>
	function formSubmit(){
		var dtxml = document.getElementById("dtxml").value;
		var vdnurl = document.getElementById("vdnurl").value;

		if(dtxml.trim() != "" && vdnurl.trim() != ""){
			
		} 

		else if(dtxml.trim() != "") {
			document.vdnForm.action = "<%=request.getContextPath()%>/salescenter/greeting";
		}
		
	}
</script>
</head>

<body>
 <div style="width: 598px; height: 438px; margin-top: 80px; margin-left: 180px;">
  <form name="vdnForm" action="<%=request.getContextPath()%>/salescenter/vdn/submit"
			method="post">
    Please enter VDN url: <input type="text" id="vdnurl" name="vdnurl"><br/>
    Please enter DT XML:<br/> <textarea id="dtxml" name="dtxml" cols="40" rows="6">
    <DtConsumer>
	  <dtPartner>26040</dtPartner>
	  <dtPartnerAccountId>321654987</dtPartnerAccountId>
	  <dtSequenceNum>10</dtSequenceNum>
	  <dtNamePrefix>MR</dtNamePrefix>
	  <dtNameFirst>Tracy</dtNameFirst>
	  <dtNameMiddle></dtNameMiddle>
	  <dtNameLast>Martin</dtNameLast>
	  <dtNameSuffix></dtNameSuffix>
	  <dtSaStreet1>1106 GREGG ST</dtSaStreet1>
	  <dtSaCity>COLUMBIA</dtSaCity>
	  <dtSaState>SC</dtSaState>
	  <dtSaZip>29201-3825</dtSaZip>
	  <dtEmail>Tracy@test.com</dtEmail>
	  <dtAgentId>Kelly</dtAgentId>
	  <dtConsumerDlnum></dtConsumerDlnum>
	  <dtConsumerSsn>123-97-2349</dtConsumerSsn>
	  <dtSpouseRoommateName>Test</dtSpouseRoommateName>
	  <dtSpouseRoommateSsn></dtSpouseRoommateSsn>
	  <dtCreated></dtCreated>
	  <dtRequestedStartDate>06/15/2007</dtRequestedStartDate>  
	  <dtGasRequestedStartDate>06/16/2007</dtGasRequestedStartDate>
	  <dtSearchId></dtSearchId>
	  <dtReqStartTimeBegin>Kelly</dtReqStartTimeBegin>
	  <dtReqStartTimeEnd>12:00</dtReqStartTimeEnd>
	  <dtGasReqStartTimeBegin>09:00</dtGasReqStartTimeBegin>
	  <dtGasReqStartTimeEnd>13:00</dtGasReqStartTimeEnd>
	  <externalId></externalId>
	  <dtSaStreet2></dtSaStreet2> 
	  <dtPremiseNumber></dtPremiseNumber>
	  <dtTelephoneNum>703-802-8999</dtTelephoneNum>
	</DtConsumer>
    </textarea><br/>
    <input type="submit" value="Submit XML" onclick="formSubmit()"/>
  </form> 
  </div>  
</body>
</html>