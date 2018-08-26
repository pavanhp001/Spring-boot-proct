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
	}else if(stepVal == "stepTwo"){
		$(".nav_step_1").removeClass('disabled');
        $(".nav_step_2").removeClass('disabled');
         $(".nav_step_1").addClass('complete');
			$(".nav_step_2").addClass('complete');
	}else if(stepVal == "stepThree"){
		$(".nav_step_1").removeClass('disabled');
        $(".nav_step_2").removeClass('disabled');
        $(".nav_step_3").removeClass('disabled');
        $(".nav_step_1").addClass('complete');
        $(".nav_step_2").addClass('complete');
		$(".nav_step_3").addClass('complete');
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
	
	
/*	var path = $("#path").val();
	var cssPath = path+"/style/acdc/"+channelCss;
	var cssFile = $("<link rel='stylesheet' type='text/css'>");
	cssFile.attr("href",cssPath);
	$("head").append(cssFile);*/
 });