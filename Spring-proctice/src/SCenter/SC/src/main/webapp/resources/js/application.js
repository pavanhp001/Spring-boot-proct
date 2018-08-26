(function($) {
	var cometd = $.cometd;

	$(document)
			.ready(
					function() {
						var title = $('title').text();
						var newMessageData = $("#newAMBMessageData").val();
						var isNewMessage = false;
						var messageData;
						if (newMessageData != null && newMessageData != undefined
								&& newMessageData.length > 0)
						{
							try{
								var locationUrl = window.location.href;
								if (locationUrl.indexOf("login_process") >= 0) {
									isNewMessage = false;
									messageData = $("#newAMBMessageData").val();
								}else{
									dispalyPopUp(newMessageData, true );
									isNewMessage = true;
								}
							}
							catch (err) {
								alert(err)
							}
						}else{
							messageData = $("#savedAMBMessageData").val();
						}
						if (!isNewMessage)
						{
							if (messageData != null && messageData != undefined
									&& messageData.length > 0) {
								var call_data = JSON.parse(messageData);
								if (call_data.eventType == "callAlert") {
									var currentdatetime = new Date();
									var row = $("<tr>");
	
									// set CTI messages here
									var data_cti_msg = $("<td>");
									data_cti_msg
											.append('<div>' + messageData + '</div>');
									row.append(data_cti_msg);
	
									// set public messages here
									var data_public_msg = $("<td>");
									row.append(data_public_msg);
	
									var data_greetings = $("<td>");
									row.append(data_greetings);
	
									$("table.messageGrid tbody").append(row);
									// passing message to handleGreetings function
									// to be processed further
									handleGreetings(messageData);
	
									// handlePopup
									var path = config.contextPath;
									var url = path + "/salescenter/authAMB";
									var data = {};
									data["message"] = messageData;
									data["currentdatetime"] = currentdatetime;
									try {
										$.ajax( {
											url : url,
											data : data,
											type : 'POST',
											complete : function(data) {
												onComplete(data);
											}
										});
									} catch (e) {
										alert("error :: " + e);
									}
								} else {
									console.info('Discarding the message ....... ');
								}
							}
						}

						var timer = 0;
						setInterval(function() {
							timer++;
						}, 1000);

						/*
						 * this function will be called when connection to
						 * cometd/AMB is established
						 */
						function _connectionEstablished() {
							$('#messages').text('CONNECTED');
							$('#messages').css( {
								"color" : "green"
							});
						}

						/*
						 * This function will be called when connection is
						 * broken
						 */
						function _connectionBroken() {
							$('#messages').text('CONNECTION BROKEN');
							$('#messages').css( {
								"color" : "red"
							});
						}

						/*
						 * This function will be called when connection is
						 * closed
						 */
						function _connectionClosed() {
							$('#messages').text('CONNECTION CLOSED');
							$('#messages').css( {
								"color" : "red"
							});
						}

						// Function that manages the connection status with the
						// AMB server
						var _connected = false;
						function _metaConnect(message) {
							if (cometd.isDisconnected()) {
								_connected = false;
								_connectionClosed();
								return;
							}

							var wasConnected = _connected;
							_connected = message.successful === true;
							if (!wasConnected && _connected) {
								_connectionEstablished();
							} else if (wasConnected && !_connected) {
								_connectionBroken();
							}
						}

						/*
						 * Function to handle greetings. This is basically being
						 * called when json message needs to be processed
						 */
						function handleGreetings(data) {
							var obj = jQuery.parseJSON(data);
							// TODO: use the actual url
							var url = location.protocol + "//" + location.host
									+ contextPath + "/salescenter/amb_greeting";
							$
									.post(
											url,
											obj,
											function(data) {
												console
														.info('Server provided data : ' + data);
												var custData = "<div><span>Id :</span> "
														+ data.id
														+ "  <br><span>FirstName :</span> "
														+ data.firstName
														+ " <br><span>LastName :</span> "
														+ data.lastName
														+ "</div>";
												$(
														"table.messageGrid > tbody > tr:last-child > td:last-child")
														.append(custData);
											});
						}

						var onComplete = function(data) {
							var businessList = JSON.parse(data.responseText);
							$(
									"#referalBlock > table#referralTable > tbody > tr")
									.remove();
							$(businessList).each(function() {
								var value = this.value;
								var txt = this.text;
								var tr = $("<tr>").attr( {
									"id" : value,
									"class" : "referralName"
								});
								tr.click(sendReferralDet);
								var td = $("<td>").text(txt);
								tr.append(td);
								$("#referralTable > tbody").append(tr);
							});

							// If there is only one item in the list
							if (businessList.length == 1) {
								$("#referralDet").val(businessList[0].value);
								$("form#profileInfo").submit(); 
							} else {
								$(".slideImgBlock").hide();
								$("#referalBlock").show();

								// change Start Call to End Call
								$("#startCall").val("End Call").attr( {
									"id" : "endCall",
									"name" : "endCall",
									"class" : "endcallbtn",
									"onClick" : "goToDispositions();"
								});
							}
						}

						// Function invoked when first contacting the server and
						// when the server has lost the state of this client
						function _metaHandshake(handshake) {
							if (handshake.successful === true) {
								cometd
										.batch(function() {
											/*
											 * Received messages on atl channel
											 */
											cometd
													.subscribe(
															'/amb/cti/calls',
															function(message) {
																console
																		.info('Message data received : ' + message.data);
																console
																		.info('Message data data received : ' + message.data.data);
																var call_data = JSON
																		.parse(message.data.data);
																if (call_data.eventType == "callAlert") {
																	saveAMBMessage(message.data.data);
																	
																	var locationUrl = window.location.href;
																	if (locationUrl
																			.indexOf("login_process") >= 0) {
																		var currentdatetime = new Date();
																		var row = $("<tr>");

																		// set
																		// CTI
																		// messages
																		// here
																		var data_cti_msg = $("<td>");
																		data_cti_msg
																				.append('<div>' + message.data.data + '</div>');
																		row
																				.append(data_cti_msg);

																		// set
																		// public
																		// messages
																		// here
																		var data_public_msg = $("<td>");
																		row
																				.append(data_public_msg);

																		var data_greetings = $("<td>");
																		row
																				.append(data_greetings);

																		$(
																				"table.messageGrid tbody")
																				.append(
																						row);
																		// passing
																		// message
																		// to
																		// handleGreetings
																		// function
																		// to be
																		// processed
																		// further
																		handleGreetings(message.data.data);

																		// handlePopup
																		var path = config.contextPath;
																		var url = path
																				+ "/salescenter/authAMB";
																		var data = {};
																		data["message"] = message.data.data;
																		data["currentdatetime"] = currentdatetime;
																		try {
																			$
																					.ajax( {
																						url : url,
																						data : data,
																						type : 'POST',
																						complete : onComplete
																					});
																		} catch (e) {
																			alert("error :: "
																					+ e);
																		}
																	} else {
																		saveNewAMBMessage(message.data.data);
																		
																		var isPlaceOrderPopUp = false;
										                    			var lineItemsJson = $("#CKOCompletedLineItems").val();
										                    			if(lineItemsJson !="" && lineItemsJson.length>0 )
										                    			{
										                    				var lineItemsJsonObj = JSON.parse(lineItemsJson);
										                            		if( (lineItemsJsonObj.hasLineItemsTOSubmit).toUpperCase() == "YES" && title == 'Order Recap')
										                            		{
										                            			isPlaceOrderPopUp = true;
										                            		}
										                    			}
										                    			
										                    			if( isPlaceOrderPopUp )
										                    			{
										                    				/*
										                    				 * 2nd parameter of this method is auto redirect enabled flag.
										                    				 *If it is false then auto redirect is off otherwise is on.
										                    				 */
										                    				dispalyPopUp( message.data.data, false );
										                    			}
										                    			else
										                    			{
										                    				/*
										                    				 * 2nd parameter of this method is auto redirect enabled flag.
										                    				 *If it is false then auto redirect is off otherwise is on.
										                    				 */
										                    				dispalyPopUp(message.data.data, true );
										                    			}
																	}
																} else {
																	console
																			.info('Discarding the message ....... ');
																}
															});
											/*
											 * Receives messages on public
											 * channel
											 */
											cometd
													.subscribe(
															'/amb/public',
															function(message) {
																console
																		.info('Message received : ' + message);
																$('#public')
																		.append(
																				'<div>Admin Says: ' + message.data.msg + '</div>');
															});
										});
							} else if (handshake.successful === false) {
								$('#messages').text('Disconnected');
								$('#messages').css( {
									"color" : "red"
								});
							}
						}

						// Disconnect when the page unloads
						$(window).unload(function() {
							cometd.reload();
						});

						var cometURL = cometd_url + "/cometd";
						cometd.configure( {
							url : cometURL,
							logLevel : 'debug'
						});

						// Regestering reload extension on page reload, this
						// will allow us to use same session in AMB server
						cometd.registerExtension('reload',
								new org.cometd.ReloadExtension());

						// Adding listener for diff events from server
						cometd.addListener('/meta/handshake', _metaHandshake);
						cometd.addListener('/meta/connect', _metaConnect);

						// Send the authentication information for handshake, so
						// that AMB can register user/client, password value can
						// be empty
						cometd.handshake( {
							ext : {
								authentication : {
									user : username,
									credentials : '${password}',
									phoneId : phoneId
								}
							}
						});
						
					});
})(jQuery);
var isPopUpClicked = false;
function goToIdlePage() {
	try {
		// capturing html content when clicking forward/place order button on
		// popup.
		isPopUpClicked = true;
		savePageHtml(false, "");
		hideDislogueBox();
		$.blockUI( {
			message : $('#domMessage_spinner')
		});
		var path = config.contextPath;
		var url = path + "/salescenter/storeAMBMessage";

		var data = {};
		data["lineItemsJson"] = $("#CKOCompletedLineItems").val();
		data["messageData"] = messageData;
		data["isConfirmedToSubmit"] = $('#isConfirmedToSubmit').val();
		data["orderId"] = $('#orderId').val();

		$.ajax( {
			url : url,
			data : data,
			type : 'POST',
			complete : function() {
				var url = path + "/salescenter/login_process";
				window.location.href = url;
			}
		});
		
	} catch (err) {
		alert(err);
	}
}

function saveNewAMBMessage(newAMBMessage){
	try{
		var path = config.contextPath;
		var url = path + "/salescenter/newAMBMessage";
		var data = {};
		data["messageData"] = newAMBMessage;
		$.ajax( {
			url : url,
			data : data,
			type : 'POST',
			complete : function() {
			}
		});
	}catch(err){
		alert(err);}
}

function autoRedirectToIdlePage() {
	if (!isPopUpClicked) {
		if (timerObject != undefined) {
			clearTimeout(timerObject);
		}
		goToIdlePage();
	}
}

var timerObject;
var messageData;
function dispalyPopUp(data, isAutoRedirectEabled) {
	try {
		$(".ts_popslide").hide();
		messageData = data;
		$("#dialog-confirm").dialog( {
			resizable : false,
			title : "Alert: You have a new call coming in",
			height : 197,
			width : 477,
			modal : true,
			zIndex : 99999
		});
		// capturing html content when popup message is displayed
		savePageHtml(false, "");
		if( isAutoRedirectEabled )
		{
			timerObject = setTimeout("autoRedirectToIdlePage()", $("#callAlertTimeout").val());
		}
		
	} catch (err) {
		alert(err)
	}
}

function hideDislogueBox() {
	$("#dialog-confirm").dialog('close');
}

function saveAMBMessage(newMessageData){
	try{
		var path = config.contextPath;
		var url = path + "/salescenter/saveAMBMessage";
		var data = {};
		var currentdatetime = new Date();
		data["ambMessageData"] = newMessageData;
		data["currentdatetime"] = currentdatetime;
		$.ajax( {
			url : url,
			data : data,
			type : 'POST',
			async : false,
			complete : function() {
			}
		});
	}catch(err){
		alert(err);}
}
