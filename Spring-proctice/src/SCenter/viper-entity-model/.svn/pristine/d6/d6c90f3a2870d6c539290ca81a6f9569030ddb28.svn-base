package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;
/**
 * This entity represents the configuration parameters for
 * an integration with an external partner.
 *  
 * @author rchapple
 */
@Entity
@Table( name = "integrationConfig" )
public class IntegrationConfigBean implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8665722227004990441L;

	@Id
    @GeneratedValue( generator = "integrationConfigBeanSequence" )
    @SequenceGenerator( name = "integrationConfigBeanSequence", sequenceName = "INTEG_CONFIG_BEAN_SEQ" )
    private long id;

    @OneToOne
    @NotNull
    @ForeignKey( name = "INTERGRATION_CONFIG_FK01" )
    private BusinessParty businessParty;
   
    /**
     * The name of the Seam component that handles the
     * integration with the asssociated BusinessParty.
     */
    @NotNull
    private String componentName;

    /**
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( final long id )
    {
        this.id = id;
    }

    /**
     * @return the businessParty
     */
    public BusinessParty getBusinessParty()
    {
        return businessParty;
    }

    /**
     * @param businessParty the businessParty to set
     */
    public void setBusinessParty( final BusinessParty businessParty )
    {
        this.businessParty = businessParty;
    }

    /**
     * @return the componentName
     */
    public String getComponentName()
    {
        return componentName;
    }

    /**
     * @param componentName the componentName to set
     */
    public void setComponentName( final String componentName )
    {
        this.componentName = componentName;
    }
   
}
