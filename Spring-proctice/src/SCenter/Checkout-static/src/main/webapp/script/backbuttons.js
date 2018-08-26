function noBack() {
	window.history.forward(0);
}
noBack();
window.onload = noBack;
window.onpageshow = function(evt) {
	if (evt.persisted)
		noBack();
}
window.onunload = function() {
	void (0);
}
