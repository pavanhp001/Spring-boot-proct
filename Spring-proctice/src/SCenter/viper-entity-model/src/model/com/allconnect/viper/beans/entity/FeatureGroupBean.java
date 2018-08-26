package com.A.V.beans.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.MapKey;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 *
 */

@Entity
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "featureGroup" )
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@org.hibernate.annotations.Table( appliesTo = "featureGroup", 
        indexes = { @Index( name = "FEATURE_GRP_EXT_ID_IDX1", columnNames = { "externalid" } ) } )
public class FeatureGroupBean extends FeatureBean implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1530472298349870749L;

	private static final int BATCH_SIZE = 10;
    
    //key is market item or item ID
    @ElementCollection 
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @MapKey( columns = @Column( name = "externalId" ) )
    @org.hibernate.annotations.BatchSize( size = BATCH_SIZE )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private Map<String, FeatGroupFeaturesBean> featureGroupFeatures;
    
    private String featureGroupType;
    
    //used for PickN select type (pick 3 for example)
    private int featureGroupTypeSelectValue;
    
    

    public void setFeatureGroupType( final String featureGroupType ) 
    {
        this.featureGroupType = featureGroupType;
    }

    public String getFeatureGroupType() 
    {
        return featureGroupType;
    }

    public int getFeatureGroupTypeSelectValue() 
    {
        return featureGroupTypeSelectValue;
    }

    public void setFeatureGroupTypeSelectValue( final int featureGroupTypeSelectValue ) 
    {
        this.featureGroupTypeSelectValue = featureGroupTypeSelectValue;
    }

    public void setFeatureGroupFeatures( final Map<String, FeatGroupFeaturesBean> featureGroupFeatures ) 
    {
        this.featureGroupFeatures = featureGroupFeatures;
    }

    public Map<String, FeatGroupFeaturesBean> getFeatureGroupFeatures() 
    {
        return featureGroupFeatures;
    }
}
