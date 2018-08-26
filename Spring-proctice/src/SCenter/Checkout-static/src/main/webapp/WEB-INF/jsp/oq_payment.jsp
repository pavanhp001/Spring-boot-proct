<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link rel="stylesheet"
	href="<c:url value='/style/html5reset-1.6.1.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/CKO_base.css'/>" />
<script src="<c:url value='/script/jquerytools-1.2.5.js'/>"></script>
<script src="<c:url value='/script/custom_form_elements.js'/>"></script>
<script src="<c:url value='/script/crossdomain.js'/>"></script>
<script src="<c:url value='/script/feedback.js'/>"></script>

<style type="text/css">
.box {
	width: 350px;
	height: 150px;
	border: 2px solid #a1a1a1;
	border-radius: 5px;
	display: none;
	padding: 8px;
}


.lLabel {
    float: left;
    font-weight: bold;
    width: 50%;
}

.rLabel {
    float: left;
    padding-bottom: 3px;
    width: 50%;
}

.card {
	width: 350px;
	height: 85px;
	border: 2px solid #a1a1a1;
	border-radius: 5px;
	padding: 7px;
}
</style>
<script type="text/javascript">
$(document).ready(function(){

	$('input[type="radio"]').bind('click', function(){
		if(this.value == 'newCard') {
			$('#creditCard').css('display', 'block');
		} else {
			$('#creditCard').css('display', 'none');
		}
	});
	Custom.init();
});

</script>

</head>
<body onload="symFeedback()">
<font color="red">
	<c:forEach var="error" items="${errors}">
		<c:out value="${error}"/><br>
	</c:forEach>  
</font>
<input id="iData" name="iData" value='${iData}' type="hidden" />

	<div style="width: 598px; height: 438px; margin: 0 auto;">
		<form action="<%=request.getContextPath()%>/att/updatePayment"
			method="post">
			<div>
				<c:if test="${not empty creditCards}">
					<b>Please select your credit card: </b><br />
					<c:forEach var="card" items="${creditCards}">
						<input type="radio" name="creditcardId"
							value="<c:out value="${card.externalId}"/>">
						<div class="card">
							<b>CreditCard Number :</b>
							<c:out value="${card.creditCardNumber}" /><br /> 
							<b>Card Holder Name :</b>
							<c:out value="${card.cardHolderName}" /><br /> 
							<b>Expiration Date :</b>
							<c:out value="${card.expirationYearMonth}" /><br /> 
							<b>Credit Card Type :</b>
							<c:out value="${card.creditCardType}" />
							<br />
						</div>
					</c:forEach>
				</c:if>	
				<input type="radio" name="creditcardId" id="newCreditCard"
					value="newCard"> <b>Add a new credit card</b>
				<div class="box" id="creditCard">
					<span class="lLabel">Card Type: </span>
					<span class="rLabel">
						<select align="left" name="cardType" padding-left="20px">
						<c:forEach var="type" items="${cardTypes}">
							<option value="${type}">${type}</option>
						</c:forEach>
						</select>
					</span>
					<span class="lLabel" style="">Card Number:</span> 
						<span class="rLabel">
							<input type="text" align="right" size="19" maxlength="30" name="cardNumber">
						</span> 
					<span class="lLabel">Card Holder Name: </span>
					<span class="rLabel">
						<input type="text" size="19" maxlength="15" name="cardHolderName">
					</span> 
					<span class="lLabel">Expiration Date: </span> 
						<span class="rLabel">
							<select name="expYear" style="float: left;">
								<option value="2012">2012</option>
								<option value="2013">2013</option>
								<option value="2014">2014</option>
							</select>
                            <select name="expMonth" style="float: left;">
								<option value="01">01</option>
								<option value="02">02</option>
								<option value="03">03</option>
								<option value="04">04</option>
								<option value="05">05</option>
								<option value="06">06</option>
								<option value="07">07</option>
								<option value="08">08</option>
								<option value="09">09</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
							</select>
						</span>  
                        <span class="lLabel">CVV Code: </span>
                    <span class="rLabel" style="">
						<input type="text" size="5" maxlength="5" name="cvv">
					</span>  
				</div>
			</div>
			<input type="submit" value="Next" />
		</form>
	</div>

</body>
</html>