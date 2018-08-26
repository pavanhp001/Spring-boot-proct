
function eventFeedbackLoad() {
 
	 
	  
//read hidden vield value
 feedback  = document.getElementById("iData").value;

 //feedback is not hardcoded..but the value of the hidden field
 parent.feedbackFunc(feedback);

}



function eventFeedbackSubmit() {
 
//read hidden vield value
 feedback  = document.getElementById("iData").value;

 //feedback is not hardcoded..but the value of the hidden field
 parent.feedbackSubmit(feedback);
}
 

