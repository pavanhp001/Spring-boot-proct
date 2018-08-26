package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AccordOrderLICompositeKey implements java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 790211099011300704L;

	@Column(name = "LI_EXT_ID")
	private long liExtId;
	
	@Column(name = "ACCORD_ORDER_ID")
	private long accordOrderId;
	
	public long getLiExtId() {
		return liExtId;
	}

	public void setLiExtId(long liExtId) {
		this.liExtId = liExtId;
	}

	public long getAccordOrderId() {
		return accordOrderId;
	}

	public void setAccordOrderId(long accordOrderId) {
		this.accordOrderId = accordOrderId;
	}
	
	/**
     * 
     * {@inheritDoc}
     */
    public boolean equals( final Object obj )
    {
        if ( obj == null )
        {
            return false;
        }
        if ( !( obj instanceof AccordOrderLICompositeKey ) )
        {
            return false;
        }
        AccordOrderLICompositeKey toCompare = (AccordOrderLICompositeKey) obj;
        return (String.valueOf(accordOrderId).equals(((AccordOrderLICompositeKey)toCompare).accordOrderId) 
                && String.valueOf(liExtId).equals(((AccordOrderLICompositeKey)toCompare).liExtId));
    }

    /**
     * 
     * {@inheritDoc}
     */
    public int hashCode()
    {
        return String.valueOf(accordOrderId).hashCode() + (int) liExtId;
    }
}
