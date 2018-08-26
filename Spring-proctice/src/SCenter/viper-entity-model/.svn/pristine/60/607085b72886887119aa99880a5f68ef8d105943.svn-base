package com.A.V.beans.entity;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.MapKey;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 * 
 */

@Entity
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "feature" )
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@org.hibernate.annotations.Table( appliesTo = "feature", 
        indexes = { @Index( name = "FEATURE_EXT_ID_IDX1", columnNames = { "externalid" } ) } )
public class FeatureBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4621279118922465120L;
	
	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "featureBeanSequence" )
    @SequenceGenerator( name = "featureBeanSequence", sequenceName = "FEATURE_BEAN_SEQ",allocationSize = 1 )
    private long id;
    private String externalId;
    private String type;
    private String subtype;
    private String description;
    private String dataConstraintDescription;
    
    private static final int FVB_BATCH_SIZE = 100;
    //private static final int FVLB_BATCH_SIZE = 20;

    //key is item external ID, upping batch size doesn't noticeably affect performance in my tests
    @ElementCollection
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @MapKey( columns = @Column( name = "externalId" ) )
    @org.hibernate.annotations.BatchSize( size = FVB_BATCH_SIZE )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private Map<String, FeatureValueBean> featureValueBeans;

    //key is market item external ID (these beans are in a list as they have in effect dates
    @ElementCollection
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @MapKey( columns = @Column( name = "externalId" ) )
    //batching seems to hurt here, as no reuse of market item data, in clause performs more slowly
    //@org.hibernate.annotations.BatchSize( size = 2 )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private Map<String, FeatureValueListBean> featureValueListBeans;

    @OneToOne( cascade = { CascadeType.ALL } )
    @ForeignKey( name = "DATA_CONSTR_FK01" )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private DataConstraintBean dataConstraint;

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
        if ( !( obj instanceof FeatureBean ) )
        {
            return false;
        }
        FeatureBean toCompare = (FeatureBean) obj;
        return this.externalId.equals( toCompare.externalId );
    }

    /**
     * 
     * {@inheritDoc}
     */
    public int hashCode()
    {
        return this.externalId.hashCode() + (int) id;
    }

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

    public final String getType()
    {
        return type;
    }

    public final void setType( final String name )
    {
        this.type = name;
    }

    public final String getDescription()
    {
        return description;
    }

    public final void setDescription( final String description )
    {
        this.description = description;
    }

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    public Map<String, FeatureValueBean> getFeatureValueBeans()
    {
        return featureValueBeans;
    }

    public void setFeatureValueBeans( final Map<String, FeatureValueBean> featureValueBeans )
    {
        this.featureValueBeans = featureValueBeans;
    }

    public DataConstraintBean getDataConstraint()
    {
        return dataConstraint;
    }

    public void setDataConstraint( final DataConstraintBean dataConstraint )
    {
        this.dataConstraint = dataConstraint;
    }

    public String getDataConstraintDescription()
    {
        return dataConstraintDescription;
    }

    public void setDataConstraintDescription( final String dataConstraintDescription )
    {
        this.dataConstraintDescription = dataConstraintDescription;
    }

    public String getSubtype()
    {
        return subtype;
    }

    public void setSubtype( final String subtype )
    {
        this.subtype = subtype;
    }

    public Map<String, FeatureValueListBean> getMarketItemFeatureValueBeans()
    {
        return featureValueListBeans;
    }

    public void setMarketItemFeatureValueBeans( final Map<String, FeatureValueListBean> marketItemFeatureValueBeans )
    {
        this.featureValueListBeans = marketItemFeatureValueBeans;
    }
}
