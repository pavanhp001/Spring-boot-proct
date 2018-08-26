<html>
	<head>
	<style>
		 .AL-green {
	margin-left:auto;
	margin-right:auto;
    -max-width: 60%;
    -background: #F8F8F8;
    padding: 30px 30px 20px 30px;
    font: 12px Arial, Helvetica, sans-serif;
    color: #666;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
}
	</style>
	</head>
	
	<body onload="symFeedback();gotoOrderSummary();">
	<input id="iData" name="iData" value='${iData}' type="hidden" />
	  <div class=" AL-green row" >
		  	<!-- Display the steps that are present in the CKO process -->
		  	<c:import url="/WEB-INF/jsp/acdc/navigation_steps.jsp" />
		 <div class="row ">
				<div class="col-xs-6 col-sm-6 col-md-3">Failure Reason:</div>
				<div class="col-xs-6 col-sm-6 col-md-6">Your session has expired...</div>
		</div>
		</div>
	</body>
</html>