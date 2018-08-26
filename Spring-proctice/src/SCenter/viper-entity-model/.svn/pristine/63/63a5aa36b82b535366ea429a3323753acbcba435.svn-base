package com.A.V.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import com.A.V.beans.entity.PriceTierBean;
import com.A.V.beans.entity.FeatureDependencyBean;
import com.A.V.beans.entity.FeatureCapabilityBean;

/**
 * @author jgerhard
 * 
 */
@Embeddable
public class ProductFeatureBase 
{
    
    @Basic( optional = false )
    @Column( nullable = false )
    private String externalId;
    private String type;  //short description
    private String description; //long description
    
    private ProductDCValue dcValueBean;

    private FeaturePriceInfo priceInfo;
    
    private String priceTiers;
    private String featureDependencies;
    private String featureCapabilities;
    
    @Transient
    private List<PriceTierBean> priceTiersList;
    
    @Transient
    private List<FeatureDependencyBean> featureDependenciesList;
    
    @Transient
    private List<FeatureCapabilityBean> featureCapabilitiesList;
    
    private Boolean included;
    private Boolean available;
    private Boolean required;
    
    private Boolean featureGroup;
    private String featureGroupType;
    private String parentGroupExtId;
    
    //comma delimited
    private String tags;

    /*private Boolean hasTags;
    private Boolean hasCapabilities;
    private Boolean hasDependencies;
    private Boolean hasPriceTiers;*/
    
    public final FeaturePriceInfo getFeaturePriceInfo()
    {
        return priceInfo;
    }

    public final void setFeaturePriceInfo( final FeaturePriceInfo priceInfo )
    {
        this.priceInfo = priceInfo;
    }

    public Boolean getAvailable()
    {
        return available;
    }

    public void setAvailable( final Boolean available )
    {
        this.available = available;
    }

    public Boolean getIncluded()
    {
        return included;
    }

    public void setIncluded( final Boolean included )
    {
        this.included = included;
    }

    public final Boolean getRequired()
    {
        return required;
    }

    public final void setRequired( final Boolean required )
    {
        this.required = required;
    }

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    public String getTags()
    {
        return tags;
    }

    public void setTags( final String tags )
    {
        this.tags = tags;
    }

    public ProductDCValue getDcValueBean()
    {
        return dcValueBean;
    }

    public void setDcValueBean( final ProductDCValue dcValueBean )
    {
        this.dcValueBean = dcValueBean;
    }

    public Boolean getFeatureGroup()
    {
        return featureGroup;
    }

    public void setFeatureGroup( final Boolean featureGroup )
    {
        this.featureGroup = featureGroup;
    }

    public String getFeatureGroupType()
    {
        return featureGroupType;
    }

    public void setFeatureGroupType( final String featureGroupType )
    {
        this.featureGroupType = featureGroupType;
    }

    public String getParentGroupExtId()
    {
        return parentGroupExtId;
    }

    public void setParentGroupExtId( final String parentGroupExtId )
    {
        this.parentGroupExtId = parentGroupExtId;
    }

    public String getType()
    {
        return type;
    }

    public void setType( final String type )
    {
        this.type = type;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( final String description )
    {
        this.description = description;
    }

    public String getPriceTiers()
    {
        return priceTiers;
    }

    public void setPriceTiers( final String priceTiers )
    {
        this.priceTiers = priceTiers;
    }

    public String getFeatureDependencies()
    {
        return featureDependencies;
    }

    public void setFeatureDependencies( final String featureDependencies )
    {
        this.featureDependencies = featureDependencies;
    }

    public String getFeatureCapabilities()
    {
        return featureCapabilities;
    }

    public void setFeatureCapabilities( final String featureCapabilities )
    {
        this.featureCapabilities = featureCapabilities;
    }

    /**
     * Gets the price tiers list from the compressed format.
     * This method unmarshalls the flattened data into beans from its compressed format.
     * 
     * rangeStart=baseNonRecur,baseRecur
     * example :  1=0,5.00|3=30.00,10.00|5=30.00,20.00
     * @return the feature dependencies list
     */
    public List<PriceTierBean> getPriceTiersList()
    {
        if ( priceTiers != null )
        {
            try 
            {
                String[] tempPT = priceTiers.split( "\\|" );
                
                priceTiersList = new ArrayList<PriceTierBean>(); 
                for ( String s : tempPT )
                {
                    if ( s.trim().length() == 0 )
                    {
                        continue;
                    }
                    PriceTierBean pt = new PriceTierBean();
                    String[] temp = s.split( "=" );
                    if ( temp.length == 1 )
                    {
                        continue;
                    }
    
                    pt.setTierStart( Integer.parseInt( temp[0] ) );
                    String[] nonRecurRecurPrices = temp[1].split( "," );
                    if ( nonRecurRecurPrices.length != 2 )
                    {
                        continue;
                    }
                    pt.setFeaturePriceInfo( new FeaturePriceInfo() );
                    pt.getFeaturePriceInfo().setBaseNonRecurringPrice( Double.parseDouble( nonRecurRecurPrices[0] ) );
                    pt.getFeaturePriceInfo().setBaseRecurringPrice( Double.parseDouble( nonRecurRecurPrices[1] ) );
                    priceTiersList.add( pt );
                }
            }
            catch ( Exception e )
            {
                //fatal exception parsing data, cannot recover
                ;
            }
            
            return priceTiersList;
        }
        else 
        {
            return null;
        }
    }

    /**
     * Gets the feature dependencies list from the compressed format.
     * This method unmarshalls the flattened data into beans from its compressed format.
     * 
     * excludes, requires, recommends are allowed values for dependency
     * 
     * example :  requires=FOO,BAR|excludes=TICK|recommends=TOCK
     * @return the feature dependencies list
     */
    public List<FeatureDependencyBean> getFeatureDependenciesList()
    {
        
        //example :  requires=FOO,BAR|excludes=TICK|recommends=TOCK
        if ( featureDependencies != null )
        {
            String[] tempFD = featureDependencies.split( "\\|" );
            
            featureDependenciesList = new ArrayList<FeatureDependencyBean>(); 
            for ( String s : tempFD )
            {
                if ( s.trim().length() == 0 )
                {
                    continue;
                }
                FeatureDependencyBean fdb = new FeatureDependencyBean();
                String[] temp = s.split( "=" );
                if ( temp.length == 1 )
                {
                    continue;
                }
                // excludes, requires, recommends are allowed values
                fdb.setDependencyType( temp[0] );
                String[] featureExternalIds = temp[1].split( "," );
                fdb.setFeatureExternalIds( Arrays.asList( featureExternalIds ) );
                featureDependenciesList.add( fdb );
            }
            
            return featureDependenciesList;
        }
        else 
        {
            return null;
        }
    }

    /**
     * Gets the feature capabilities list.
     *
     * @return the feature capabilities list
     */
    public List<FeatureCapabilityBean> getFeatureCapabilitiesList()
    {
        //example :  FOO=BAR,integer,Bar branded name string|MOO=COW,string,COW branded string name
        if ( featureCapabilities != null )
        {
            String[] tempFC = featureCapabilities.split( "\\|" );
            
            featureCapabilitiesList = new ArrayList<FeatureCapabilityBean>(); 
            for ( String s : tempFC )
            {
                if ( s.trim().length() == 0 )
                {
                    continue;
                }
                FeatureCapabilityBean fc = new FeatureCapabilityBean();
                String[] temp = s.split( "=" );
                
                fc.setName( temp[0] );
                if ( temp.length > 1 )
                {    
                    String valueString = temp[1];
                    String[] subValues = valueString.split( "," );
                    fc.setValue( subValues[0] );
                    if ( subValues.length > 1 )
                    {
                        fc.setValueType( subValues[1] );
                    }
                    if ( subValues.length > 2 )
                    {
                        fc.setDescription( subValues[2] );
                    }
                }
                featureCapabilitiesList.add( fc );
            }
            
            return featureCapabilitiesList;
        }
        else 
        {
            return null;
        }
    }
}
