package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "OM_V_ACCORD_ORDER_MAPPING" )
public class VAccordOrderMapping implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1544209905088689175L;

	@EmbeddedId
    private AccordOrderLICompositeKey id;
	
	@Column(name = "ORDER_EXT_ID")
	private long orderExtId;
	
	/**
    *
    */
   public VAccordOrderMapping()
   {
   }
   
	@Override
	public String toString() {
		return "VAccordOrderMapping [id=" + 0 + ", orderExtId=" + orderExtId + ", liExtId="
				+ id.getLiExtId() + ", accordOrderId=" + id.getAccordOrderId() + "]";
	}

	public AccordOrderLICompositeKey getAccordOrderLICompositeKey() {
		return id;
	}

	public void setAccordOrderLICompositeKey(AccordOrderLICompositeKey id) {
		this.id = id;
	}
	
	public long getOrderExtId() {
		return orderExtId;
	}

	public void setOrderExtId(long orderExtId) {
		this.orderExtId = orderExtId;
	}
}
