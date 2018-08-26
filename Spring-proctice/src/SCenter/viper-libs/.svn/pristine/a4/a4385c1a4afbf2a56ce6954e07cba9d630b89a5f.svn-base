package com.A.Vdao.broadcast;

import java.util.Map;

public class BroadcastMessage {

    private String id;
    private String broadcastMsg;
    private Map<String,String> messageHeaders;
    private String transactionType;

    public BroadcastMessage (String id,String broadcastMsg, Map<String,String> headers,String transType) {
	this.id = id;
	this.broadcastMsg = broadcastMsg;
	this.messageHeaders = headers;
	this.transactionType = transType;
    }

    public String getBroadcastMsg() {
        return broadcastMsg;
    }
    public void setBroadcastMsg(String broadcastMsg) {
        this.broadcastMsg = broadcastMsg;
    }
    public Map<String, String> getMessageHeaders() {
        return messageHeaders;
    }
    public void setMessageHeaders(Map<String, String> messageHeaders) {
        this.messageHeaders = messageHeaders;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
	return "BroadcastMessage [id=" + id + ", broadcastMsg=" + broadcastMsg + ", messageHeaders=" + messageHeaders + ", transactionType=" + transactionType + "]";
    }




}
