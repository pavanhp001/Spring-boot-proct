/**
 * This Js is used for navigation steps.
 */

 $(document).ready(function() {
	var stepVal = $(".nav_steps").val();
	var ccr = $(".nav_steps_ccr").val();
	if(stepVal == "stepZero"){
			$(".nav_step_1").removeClass('disabled');
	}else if(stepVal == "stepOne"){
			$(".nav_step_1").removeClass('disabled');
			$(".nav_step_1").addClass('complete');
			$(".ckoNumOne").removeClass('disableCKONumbers');
			$(".ckoNumOne").addClass("CKONumbers");
	}else if(stepVal == "stepTwo"){
		$(".nav_step_1").removeClass('disabled');
        $(".nav_step_2").removeClass('disabled');
         $(".nav_step_1").addClass('complete');
			$(".nav_step_2").addClass('complete');
			$(".ckoNumOne").removeClass('disableCKONumbers');
			$(".ckoNumTwo").removeClass('disableCKONumbers');
			$(".ckoNumOne").addClass("CKONumbers");
			$(".ckoNumTwo").addClass("CKONumbers");
	}else if(stepVal == "stepThree"){
		$(".nav_step_1").removeClass('disabled');
        $(".nav_step_2").removeClass('disabled');
        $(".nav_step_3").removeClass('disabled');
        $(".nav_step_1").addClass('complete');
        $(".nav_step_2").addClass('complete');
		$(".nav_step_3").addClass('complete');
		$(".ckoNumOne").removeClass('disableCKONumbers');
		$(".ckoNumTwo").removeClass('disableCKONumbers');
		$(".ckoNumThree").removeClass('disableCKONumbers');
		$(".ckoNumOne").addClass("CKONumbers");
		$(".ckoNumTwo").addClass("CKONumbers");
		$(".ckoNumThree").addClass("CKONumbers");
	}else if(stepVal == "stepFour"){
		$(".nav_step_1").removeClass('disabled');
        $(".nav_step_2").removeClass('disabled');
        $(".nav_step_3").removeClass('disabled');
        $(".nav_step_4").removeClass('disabled');
        $(".nav_step_1").addClass('complete');
        $(".nav_step_2").addClass('complete');
        $(".nav_step_3").addClass('complete');
		$(".nav_step_4").addClass('complete');
	}
	
	
	var path = $("#contextPath").val();
	var cssPath = path+"/style/acdc/"+channelCss;
	var cssFile = $("<link rel='stylesheet' type='text/css'>");
	cssFile.attr("href",cssPath);
	$("head").append(cssFile);
 });