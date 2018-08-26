package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * 
 * @author ebaugh
 *
 */

@Entity
@Table( name = "capability" )
public class CapabilityBean implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6648200613429289225L;

	@Id
    @GeneratedValue( generator = "capabilityBeanSequence" )
    @SequenceGenerator( name = "capabilityBeanSequence", sequenceName = "CAPABILITY_BEAN_SEQ" )
    private long id;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<FeatureBean> features;

    /**
     * Default Constructor.
     */
    public CapabilityBean()
    {
        // Default Constructor.
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

    public List<FeatureBean> getFeatures()
    {
        return features;
    }

    public void setFeatures( final List<FeatureBean> features )
    {
        this.features = features;
    }
}
