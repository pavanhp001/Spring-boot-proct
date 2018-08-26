package com.A.V.beans.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.MapKey;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 */

@Entity
@Table( name = "businessParty" )
public class BusinessParty implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5366350482109332132L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "businessPartyBeanSequence" )
    @SequenceGenerator( name = "businessPartyBeanSequence", sequenceName = "BUSINESSPARTY_BEAN_SEQ" ,allocationSize = 1)
    private long id;
    
    private String externalId;
    
    @NotNull
    private String name;
    
    private String coBrandName;
    private String urlForOrdering;
    private String phoneNumForOrdering;
    private String phoneNumForCustomerCare;
    
    @Column(name = "REALTIME_PROVIDER")
    private int realtimeProvider;
    
    @Transient
    private String logoImagePath; 

    @ManyToOne
    @ForeignKey( name = "BUSINESS_PARTY_FK01" )
    private BusinessPartyAddress currentAddress;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<BusinessPartyAddress> previousAddresses;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<BusinessParty> subsidaries; 
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<DescriptiveInfoBean> descriptiveInfo;

    private Boolean referrer;
    
    private Boolean provider;
    
    @ElementCollection
    @MapKey( columns = @Column( name = "name" ) )
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
	private Map<String, String> metadata;
    
    /**
     *
     */
    public BusinessParty()
    {
        //logger.debug( "Constructed a BusinessParty" );
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

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }
    
    public final String getName()
    {
        return name;
    }

    public final void setName( final String name )
    {
        this.name = name;
    }
    
    /**
     * Method that currently just sets a fixed path for where
     * our logos are.  This will be ultimately updated with a content management solution.
     */
    @PostLoad
    public void constructLogoImagePath() 
    {
        // A little bit of defensive programming.  If the name field is null
        // then we wouldn't be able to construct a meaningful filename anyway.
        if ( this.name != null )
        {
            String rootPath = "img/vendors";
            String partyName = this.name.toLowerCase().replaceAll( " ", "" );
            this.logoImagePath = rootPath + "/" + partyName + "_" + this.id + ".gif";
        }
    }
       
    public String getLogoImagePath()
    {
        return logoImagePath;
    }

    public String getCoBrandName()
    {
        return coBrandName;
    }

    public void setCoBrandName( final String coBrandName )
    {
        this.coBrandName = coBrandName;
    }

    public String getUrlForOrdering()
    {
        return urlForOrdering;
    }

    public void setUrlForOrdering( final String urlForOrdering )
    {
        this.urlForOrdering = urlForOrdering;
    }

    public String getPhoneNumForOrdering()
    {
        return phoneNumForOrdering;
    }

    public void setPhoneNumForOrdering( final String phoneNumForOrdering )
    {
        this.phoneNumForOrdering = phoneNumForOrdering;
    }

    public String getPhoneNumForCustomerCare()
    {
        return phoneNumForCustomerCare;
    }

    public void setPhoneNumForCustomerCare( final String phoneNumForCustomerCare )
    {
        this.phoneNumForCustomerCare = phoneNumForCustomerCare;
    }

    public final BusinessPartyAddress getCurrentAddress()
    {
        return currentAddress;
    }

    public final void setCurrentAddress( final BusinessPartyAddress currentAddress )
    {
        this.currentAddress = currentAddress;
    }
    
    public final List<BusinessPartyAddress> getPreviousAddresses()
    {
        return previousAddresses;
    }

    public final void setPreviousAddresses( final List<BusinessPartyAddress> previousAddresses )
    {
        this.previousAddresses = previousAddresses;
    }

    public List<BusinessParty> getSubsidaries()
    {
        return subsidaries;
    }

    public void setSubsidaries( final List<BusinessParty> subsidaries )
    {
        this.subsidaries = subsidaries;
    }

    public List<DescriptiveInfoBean> getDescriptiveInfo() 
    {
        return descriptiveInfo;
    }

    public void setDescriptiveInfo( final List<DescriptiveInfoBean> descriptiveInfo ) 
    {
        this.descriptiveInfo = descriptiveInfo;
    }

	public int isRealtimeProvider() {
		return realtimeProvider;
	}

	public void setRealtimeProvider(int realtimeProvider) {
		this.realtimeProvider = realtimeProvider;
	}

	public Boolean isReferrer() {
		return referrer;
	}

	public void setReferrer(Boolean referer) {
		this.referrer = referer;
	}

	public Boolean isProvider() {
		return provider;
	}

	public void setProvider(Boolean provider) {
		this.provider = provider;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "BusinessParty [id=" + id + ", externalId=" + externalId
				+ ", name=" + name + ", coBrandName=" + coBrandName
				+ ", urlForOrdering=" + urlForOrdering
				+ ", phoneNumForOrdering=" + phoneNumForOrdering
				+ ", phoneNumForCustomerCare=" + phoneNumForCustomerCare
				+ ", realtimeProvider=" + realtimeProvider + ", logoImagePath="
				+ logoImagePath + ", currentAddress=" + currentAddress
				+ ", previousAddresses=" + previousAddresses + ", subsidaries="
				+ subsidaries + ", descriptiveInfo=" + descriptiveInfo + "]";
	}
    
    
}
