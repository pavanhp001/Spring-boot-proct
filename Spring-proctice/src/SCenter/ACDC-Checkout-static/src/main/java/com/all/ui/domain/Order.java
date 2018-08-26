package com.AL.ui.domain;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ktm
 */
public class Order {

   private String externalId;
   
	private String source;
	private String referrerId;
	private String channel;
	private String programType;

   private List<LineItem> lineItems = new ArrayList<LineItem>();
   
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

   public String getExternalId() {
      return externalId;
   }

   public void setExternalId(String externalId) {
      this.externalId = externalId;
   }

   public List<LineItem> getLineItems() {
      if (lineItems == null) {
         lineItems = new ArrayList<LineItem>();
      }
      return lineItems;
   }

   public void setLineItems(List<LineItem> lineItems) {
      this.lineItems = lineItems;
   }

   public void addLineItem(LineItem li) {
      getLineItems().add(li);
   }
   
	public String getReferrerId() {
		return referrerId;
	}
	public void setReferrerId(String arg) {
		this.referrerId = arg;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
}