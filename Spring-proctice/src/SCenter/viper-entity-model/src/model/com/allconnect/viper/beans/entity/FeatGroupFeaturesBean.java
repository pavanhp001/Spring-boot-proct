package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 *
 */

@Entity
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "featGroupFeatures" )
public class FeatGroupFeaturesBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2251449102407393878L;

	@Id
    @GeneratedValue( generator = "featGroupFeaturesSequence" )
    @SequenceGenerator( name = "featGroupFeaturesSequence", sequenceName = "FEAT_GROUP_FEATURES_BEAN_SEQ" )
    private long id;

    //item or market item external Id;
    private String externalId;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> features;
    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
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

    public String getExternalId() 
    {
        return externalId;
    }

    public void setExternalId( final String externalId ) 
    {
        this.externalId = externalId;
    }

    public void setFeatures( final List<String> features ) 
    {
        this.features = features;
    }

    public List<String> getFeatures() 
    {
        return features;
    }
    
}
