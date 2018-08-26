package com.A.V.beans.entity;

import javax.persistence.Column;
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
@Table( name = "SALES_CONTEXT_ATTRIBUTES" )

public class SalesContextAttributeBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6440598514811963206L;

	@Id
    @GeneratedValue( generator = "salesContextAttributeBeanSequence" )
    @SequenceGenerator( name = "salesContextAttributeBeanSequence", sequenceName = "SLS_CTX_ATTR_BEAN_SEQ" )
    private long id;
    
    private String value;
    
    @Column( name = "SALES_CONTEXT_ID" )
    private long salesContextId;
    
    @Column( name = "ENTITY_NAME" )
    private String entityName;
    
    @Column( name = "ATTRIBUTE_NAME" )
    private String attributeName;

    public long getId()
    {
        return id;
    }
    public void setId( final long id )
    {
        this.id = id;
    }

    public String getValue()
    {
        return value;
    }
    public void setValue( final String value )
    {
        this.value = value;
    }
    public long getSalesContextId()
    {
        return salesContextId;
    }
    public void setSalesContextId( final long salesContextId )
    {
        this.salesContextId = salesContextId;
    }
    public String getEntityName()
    {
        return entityName;
    }
    public void setEntityName( final String entityName )
    {
        this.entityName = entityName;
    }
    public String getAttributeName()
    {
        return attributeName;
    }
    public void setAttributeName( final String attributeName )
    {
        this.attributeName = attributeName;
    }
}
