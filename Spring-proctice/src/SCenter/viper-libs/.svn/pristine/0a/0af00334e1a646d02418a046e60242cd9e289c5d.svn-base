package com.A.wrapper;

import java.io.Serializable;

import com.A.V.beans.entity.Consumer;
/**
 * This class will be used to send across the Customer Management service using JMS.
 * @author PPatel
 *
 */
public class CustomerPayloadWrapper implements Serializable {

    private static final long serialVersionUID = 7429216636955290286L;
    private String transactionType;
    private Consumer  consumer;
    private Object xmlBeanInstance;
    
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public Consumer  getConsumer() {
        return consumer;
    }
    public void setConsumer(Consumer  consumer) {
        this.consumer = consumer;
    }
    public Object getXmlBeanInstance() {
        return xmlBeanInstance;
    }
    public void setXmlBeanInstance(Object xml) {
        this.xmlBeanInstance = xml;
    }
    
    
}
