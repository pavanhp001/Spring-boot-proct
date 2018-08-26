package com.AL.ui.domain.sales;

import com.AL.ui.domain.Order;



public class SalesContext {
   private String guid;
   private Order order;
   private Agent salesAgent;
   private Caller customer;
   private String phaseName;

   public Order getOrder() {
	   return order;
   }

   public void setOrder(Order arg) {
	   order = arg;
   }

   public void setGUID(String arg) {
      guid = arg;
   }
   
   public String getGUID() {
      return guid;
   }

   public boolean hasOrder() 
   {
      return (getOrder() != null);
   }
   
   public boolean hasConsumer() 
   {
      return true;
   }

   public String getConsumerCreditScore() 
   {
      return "350";
   }
   
   public boolean hasSalesAgent() {
	   return true;
   }
   
   public void setSalesAgent(Agent arg) {
	   salesAgent = arg;
   }

   public Agent getSalesAgent() {
	   return salesAgent;
   }

   public boolean hasCustomer() {
	   return (getCustomer() != null);
   }

   public Caller getCustomer() {
	   return customer;
   }

   public void setCustomer(Caller customer) {
	   this.customer = customer;
   }

   public String getPhaseName() {
	   return phaseName;
   }

   public void setPhaseName(String arg) {
      phaseName = arg;
   }
}

