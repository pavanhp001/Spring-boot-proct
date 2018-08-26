package com.AL.ie.service.strategy.response.att.stub;

public class AttSti {

	
	public static String getAck(String orderNumber) {
	
		String time = "2011-08-17T21:32:50.614-04:00";
		StringBuilder sb = new StringBuilder();
		
		
	sb.append(" <rtim:rtimRequestResponse xmlns:rtim=\"http://xml.AL.com/v4/rtimRequestResponse/\">");
	sb.append(" <response xsi:type=\"ord:OrderFulfillmentResponse\" ");
	sb.append(" xmlns:ord=\"http://xml.AL.com/v4/orderFulfillment\" ");
	sb.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
	sb.append(" <context><com:sessionId xmlns:com=\"http://xml.AL.com/common\">da64f0aa-c0a8-3cf7-084c-c70e3c632ddb</com:sessionId>");
	sb.append(" <com:orderNumber xsi:nil=\"true\" xmlns:com=\"http://xml.AL.com/common\">"+orderNumber+"</com:orderNumber>");
	sb.append(" <com:responseDate xmlns:com=\"http://xml.AL.com/common\">"+time+"</com:responseDate>");
	sb.append(" <com:transactionType xmlns:com=\"http://xml.AL.com/common\">orderQualification</com:transactionType></context>");
	sb.append(" <orderManagementRequestResponse><v4:status xmlns:v4=\"http://xml.AL.com/v4\">");
	sb.append(" <v4:statusMsg>Order Receipt Acknowledged</v4:statusMsg>");
	sb.append(" </v4:status></orderManagementRequestResponse></response></rtim:rtimRequestResponse>");
	
	return sb.toString();
	
	}
}
