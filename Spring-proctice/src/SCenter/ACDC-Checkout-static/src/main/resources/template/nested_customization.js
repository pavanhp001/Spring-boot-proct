 
	$(function () {
	    $('fieldset.other').hide();
	    $('input[name="other"]').click(function () {
	        if (this.checked) {
	            $('fieldset.other').show();
	        } else {
	            $('fieldset.other').hide();
	        }
	    });
	});
 