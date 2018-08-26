var initss = 0;
function startTimer()
{
	initss++;
	var ss = checkTime(initss%60);
	var mm = checkTime(parseInt(initss/60));
	document.getElementById('startTimeText').innerHTML = "Call Time: " + mm + ":" + ss;
	if(document.getElementById('startTimeTextPopup') != null){
		document.getElementById('startTimeTextPopup').innerHTML = "Call Time: " + mm + ":" + ss;
	}
	if(document.getElementById('startTimeTextCompareProducts') != null){
		document.getElementById('startTimeTextCompareProducts').innerHTML = "Call Time: " + mm + ":" + ss;
	}
	if(document.getElementById('startTimeTextMBProducts') != null){
		document.getElementById('startTimeTextMBProducts').innerHTML = "Call Time: " + mm + ":" + ss;
	}
	/*if (document.getElementById('totalTime_dialog')) {
		document.getElementById('totalTime_dialog').innerHTML = mm + ":" + ss;
	}*/
}

function checkTime(i)
{
	if (i<10)
	{
	  i="0" + i;
	}
	return i;
}

function updateTimer(){
/*	var callStartTime = parseInt(document.getElementById("callStartTime").value);
	if(!isNaN(callStartTime) && callStartTime != undefined){
		var date_start = new Date(callStartTime);
		var date_current = new Date();
		
		initss = parseInt((date_current.getTime() - date_start.getTime()) / 1000);
	}*/
	
	 initss = parseInt((new Date().getTime() - document.getElementById("callStartTime").value)/1000);
	 if(initss < 0){
		 try{
			 document.getElementById("callStartTimeInGreeting").value=new Date().getTime();
			 initss = 0;
		 }catch(e){}
	 }
	 
	/*document.getElementById("startTimeText").innerHTML = "Call Time: ";
	var spanElement = document.createElement("span");
	spanElement.id = "totalTime";
	document.getElementById("startTimeText").appendChild(spanElement);*/
	
	setInterval(function(){startTimer();},1000);
}
