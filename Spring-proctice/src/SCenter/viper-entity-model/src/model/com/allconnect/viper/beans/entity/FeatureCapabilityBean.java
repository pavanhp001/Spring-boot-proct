package com.A.V.beans.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 * 
 */

@Entity
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "featureCapability" )

public class FeatureCapabilityBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8184433877081420916L;

	@Id
    @GeneratedValue( generator = "featureCapabilityBeanSequence" )
    @SequenceGenerator( name = "featureCapabilityBeanSequence", sequenceName = "FEATURE_CPBLTY_BEAN_SEQ" )
    private long id;
    
    @Basic( optional = false )
    @Column( nullable = false )
    private String name;
    
    private String value;
    
    private String valueType;
    
    private String description;
    
    public long getId()
    {
        return id;
    }
    public void setId( final long id )
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName( final String name )
    {
        this.name = name;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue( final String value )
    {
        this.value = value;
    }
    public String getValueType()
    {
        return valueType;
    }
    public void setValueType( final String valueType )
    {
        this.valueType = valueType;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription( final String description )
    {
        this.description = description;
    }
}
