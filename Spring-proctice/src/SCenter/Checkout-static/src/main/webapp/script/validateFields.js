

function validateRoutingNumber(selValue, id){
	var errorList;
	var checkDigit = -1;

	if(selValue != null && selValue.trim().length > 0){
		if(isInteger(selValue)){
			var ssnArray = selValue.split('');
			var results = (3 * parseFloat(ssnArray[0])) + (7* parseFloat(ssnArray[1]))+(1 * parseFloat(ssnArray[2]))+(3 * parseFloat(ssnArray[3]))+
			(7 * parseFloat(ssnArray[4]))+(1 * parseFloat(ssnArray[5]))+(3 * parseFloat(ssnArray[6]))+(7 * parseFloat(ssnArray[7]));
			checkDigit = parseFloat(roundUpTens(results)) - parseFloat(results);
			if (checkDigit == ssnArray[8]) {
				$("#"+id+"_validateRoutingNumberClass").each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				return true;
			}else {
				$("#"+id+"_validateRoutingNumberClass").each(function(){
					if($(this).text() != null){
						$(this).remove();
					}
				});
				if(! ($('.validateRoutingNumberClass').text().length > 0)){
					$('#'+id).after("<span id='"+id+"_validateRoutingNumberClass' class='validateRoutingNumberClass error' style='background-color:yellow;'><font color='red'>Routing Number incorrect.</span>");
					return false;
				}
			}
		}
		else{
			$("#"+id+"_validateRoutingNumberClass").each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			if(! ($('.validateRoutingNumberClass').text().length > 0)){
				$('#'+id).after("<span id='"+id+"_validateRoutingNumberClass' class='validateRoutingNumberClass error' style='background-color:yellow;'><font color='red'>Routing number should not contain alphabets</span>");
				return false;
			}
		}
	}
}

function roundUpTens(p_num){
	var value = p_num;
	var remainder = parseFloat(p_num) % 10;
	remainder = parseFloat(remainder);
	if (remainder > 0){
		value = parseFloat(p_num) + (10 - remainder);
	}
	return value;
}


function validateSSN(selValue, id){

	var firstNumber = selValue.slice(0,3);
	var secondNumber = selValue.slice(3,5);
	var thirdNumber = selValue.slice(5);

	if(firstNumber == '000' || secondNumber == '00' || thirdNumber == '0000'){
		$("#"+id+"_ssnClass").each(function(){
			$(this).remove();
		});
		if(! ($("#"+id+"_ssnClass").text().length > 0)){
			$('input[id='+id+']').after("<span id='"+id+"_ssnClass' class='ssnClass error' style='background-color:yellow;'><font color='red'>None of the three portions of SSN can consist entirely of repeating zeros.</span>");
			return false;
		}
	}
	var firstDigit = firstNumber.substring(0, 1);
	var invalidSSN = firstDigit+firstDigit+firstDigit+"-"+firstDigit+firstDigit+"-"+firstDigit+firstDigit+firstDigit+firstDigit;
	var origSSN = firstNumber +"-"+secondNumber+"-"+thirdNumber;
	if(origSSN == invalidSSN){
		$("#"+id+"_ssnClass").each(function(){
			$(this).remove();
		});
		if(! ($("#"+id+"_ssnClass").text().length > 0)){
			$('input[id='+id+']').after("<span id='"+id+"_ssnClass' class='ssnClass error' style='background-color:yellow;'><font color='red'>SSN cannot consist entirely of the same digit repeated.</span>");
			return false;
		}
	}
	return true;
}
var creditCardList = ["Visa", "MasterCard", "Discover", "AmericanExpress"];

var creditCardNumberStartAndEnd = {"creditCardBound" :[{"ccTypes":"AmericanExpress1", "upperBound":"3499", "lowerBound":"3400"},
                                                       {"ccTypes":"AmericanExpress2", "upperBound":"3799", "lowerBound":"3700"},
                                                       {"ccTypes":"Visa", "upperBound":"4999", "lowerBound":"4000"},
                                                       {"ccTypes":"MasterCard", "upperBound":"5599", "lowerBound":"5100"},
                                                       {"ccTypes":"Discover", "upperBound":"6011", "lowerBound":""}]};

var ccMinLength = {"ccLength":[{"ccType":"MasterCard","length":16},
                               {"ccType":"Visa","length":16},
                               {"ccType":"AmericanExpress","length":15},
                               {"ccType":"Discover","length":16}]};

function validateCreditCardNumber(selValue, id){
	var creditCardType = $(".ccType").val();
	var bound = selValue.substring(0, 4);
	var ccLength = selValue.length;
	if(creditCardType != null && creditCardType.trim().length > 0){
		for(var i = 0; i < creditCardList.length; i++){
			if(creditCardList[i] == creditCardType){
				var isCreditCardValid = false;
				$(ccMinLength.ccLength).each(function(){
					if(this.ccType.indexOf(creditCardType) >= 0){
						if(this.length == ccLength){
							$('#'+id +'_validateCCClass').each(function(){
								if($(this).text() != null){
									$(this).remove();
								}
							});
							isCreditCardValid = true;
						}
						else{
							$("#"+id+"_validateCCClass").each(function(){
								if($(this).text() != null){
									$(this).remove();
								}
							});
							$('input[id='+id+']').after("<span id='"+id+"_validateCCClass' class='validateCCClass error' " +
									"style='background-color:yellow;'><font color='red'>"+this.ccType.toUpperCase()+" card incorrect length </span>");
							isCreditCardValid = false;
						}
					}
				});

				if(isCreditCardValid){
					$(creditCardNumberStartAndEnd.creditCardBound).each(function(){
						if(this.ccTypes == creditCardType){
							var upperBound = this.upperBound;
							var lowerBound = this.lowerBound;
							if(bound >= lowerBound && bound <= upperBound){

								var data = { "ccNumber" : selValue};
								$.ajax({
									type: 'POST',
									data: data,
									async:false,
									url: $('#contextPath').val()+"/static/updateDialogue/isValidCCNumber",
									success: function(data){
									var responseJSON = JSON.parse(data);
									if(responseJSON.error != null && responseJSON.error != undefined){
										$("#"+id +'_validateCCClass').each(function(){
											if($(this).text() != null){
												$(this).remove();
											}
										});

										$('#'+id).after("<span id='"+id +"_validateCCClass' class='validateCCClass error' style='background-color:yellow;' ><font color='red'>"+responseJSON.error+"</font></span>");			
										return false;
									}else{
										$('#'+id +'_validateCCClass').each(function(){
											if($(this).text() != null){
												$(this).remove();
											}
										});
										return true;
									}
								},
								error:function(status){
								}
								});
							
							}else{
								$("#"+id+"_validateCCClass").each(function(){
									if($(this).text() != null){
										$(this).remove();
									}
								});
								$('input[id='+id+']').after("<span id='"+id+"_validateCCClass' class='validateCCClass error' " +
										"style='background-color:yellow;'><font color='red'>"+this.ccTypes.toUpperCase()+" card number is not valid</span>");
								return false;
							}
						}
					});
				}
				else{
					return false;
				}
			}
		}
	}
	else{
		$('input[id='+id+']').after("<span id='"+id+"_validateCCClass' class='validateCCClass error' " +
		"style='background-color:yellow;'><font color='red'>select credit card type</span>");
		return false;
	}
}

/*function validateCreditCardNumberByLuhanNumber(creditCardNumber, id){
	var ccNumberArr = creditCardNumber.split('');
	var productArr = new Array();
	
	for(var i = ccNumberArr.length-2; i >= 0; i=i-2){
        var productVal = ccNumberArr[i]*2;
        productVal = productVal + "";
        if(productVal.length == 1){
        	ccNumberArr[i] = productVal; 
        }
        else{
        	var prodArr = productVal.split('');
        	var ele = 0;
        	for(var i = 0;i<prodArr.length;i++){
        		ele = parseInt(ele)+ parseInt(prodArr[i]);
        	}
        	ccNumberArr[i] = ele; 
        }
	}
	var sum = 0;
	for(var i =0; i<ccNumberArr.length; i++){
	   sum = parseInt(sum) + parseInt(ccNumberArr[i]);
	}
    
    if(sum%10 == 0){
        return true;
    }
    else{
    	$("#"+id+"_validateCCClass").each(function(){
    		if($(this).text().trim().length > 0){
    			$(this).remove();
    		}
    	});
    	$('input[id='+id+']').after("<span id='"+id+"_validateCCClass' class='validateCCClass error' " +
				"style='background-color:yellow;'><font color='red'>"+"Invalid Credit Card Number</span>");
		return false;
    }
}*/

var invalidAreaCode=["000", "111", "999"];
var invalidLastDigits = ["0000000", "1111111", "9999999"];

function validatePhoneNumber(selValue, id){
	var areaCode = selValue.substring(0, 3);
	var lastDigits = selValue.substring(3);
	for(var i = 0; i < invalidAreaCode.length; i++){
		if(areaCode == invalidAreaCode[i]){
			$("#"+id+"_validatePhoneNumberClass").each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			$('input[id='+id+']').after("<span id='"+id+"_validatePhoneNumberClass' class='validatePhoneNumberClass error' style='background-color:yellow;'><font color='red'>" +
			"invalid area code</span>");
			return false;
		}
	}
	for(var i = 0; i < invalidLastDigits.length; i++){
		if(lastDigits.indexOf('-') >= 0){
			lastDigits = lastDigits.replace('-','');
		}
		if(lastDigits == invalidLastDigits[i]){
			$("#"+id+"_validatePhoneNumberClass").each(function(){
				if($(this).text() != null){
					$(this).remove();
				}
			});
			$('input[id='+id+']').after("<span id='"+id+"_validatePhoneNumberClass' class='validatePhoneNumberClass error' style='background-color:yellow;'><font color='red'>" +
			"invalid phone number</span>");
			return false;

		}
	}
	return true;
}

function validateEmail(selValue, id){
	var re = /\S+@\S+\.\S+/;
	return re.test(selValue);
}
