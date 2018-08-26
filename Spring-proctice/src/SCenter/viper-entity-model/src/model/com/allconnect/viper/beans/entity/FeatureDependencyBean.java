package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.IndexColumn;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 * 
 */

@Entity
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "featureDependency" )

public class FeatureDependencyBean implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 29773536332690707L;

	private static final int BATCH_SIZE = 10;
    
    @Id
    @GeneratedValue( generator = "featureDependencyBeanSequence" )
    @SequenceGenerator( name = "featureDependencyBeanSequence", sequenceName = "FEATURE_DEP_BEAN_SEQ" )
    private long id;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    @org.hibernate.annotations.BatchSize( size = BATCH_SIZE )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<String> externalIds;
    
    @Basic( optional = false )
    @Column( nullable = false )
    private String dependencyType;
    
    public long getId()
    {
        return id;
    }
    public void setId( final long id )
    {
        this.id = id;
    }
    public String getDependencyType()
    {
        return dependencyType;
    }
    public void setDependencyType( final String dependencyType )
    {
        this.dependencyType = dependencyType;
    }
    public List<String> getFeatureExternalIds()
    {
        return externalIds;
    }
    public void setFeatureExternalIds( final List<String> featureExternalIds )
    {
        this.externalIds = featureExternalIds;
    }

}
