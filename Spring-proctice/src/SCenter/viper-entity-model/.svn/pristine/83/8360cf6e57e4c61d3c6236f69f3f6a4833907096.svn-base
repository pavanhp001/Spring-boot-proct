package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 * 
 */

@Entity
@Table( name = "PCODEMAPPING" )
public class PCodeMappingBean implements CommonBeanInterface 
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1910120688618378413L;
	
	@Id
    @GeneratedValue( generator = "pCodeMappingBeanSequence" )
    @SequenceGenerator( name = "pCodeMappingBeanSequence", sequenceName = "PCODE_MAPPING_BEAN_SEQ" )
    private long id;
    private String itemExternalId;
    private String marketItemExternalId;
    private String featureExternalId;
    private String pCode;

    /**
    *
    */
    public PCodeMappingBean() 
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getId() 
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setId( final long id ) 
    {
        this.id = id;
    }

    public java.lang.String getItemExternalId() 
    {
        return itemExternalId;
    }

    public void setItemExternalId( final String itemExternalId ) 
    {
        this.itemExternalId = itemExternalId;
    }

    public java.lang.String getMarketItemExternalId() 
    {
        return marketItemExternalId;
    }

    public void setMarketItemExternalId( final String marketItemExternalId ) 
    {
        this.marketItemExternalId = marketItemExternalId;
    }

    public java.lang.String getPCode() 
    {
        return pCode;
    }

    public void setPCode( final String code ) 
    {
        pCode = code;
    }

    public String getFeatureExternalId() 
    {
        return featureExternalId;
    }

    public void setFeatureExternalId( final String featureExternalId ) 
    {
        this.featureExternalId = featureExternalId;
    }
}
