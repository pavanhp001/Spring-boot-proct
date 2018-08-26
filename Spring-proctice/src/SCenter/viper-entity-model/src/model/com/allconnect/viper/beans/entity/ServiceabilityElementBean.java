package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

import com.A.V.beans.CapabilitiesRecord;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * 
 * @author ebaugh
 *
 */
@Entity
@Table( name = "serviceabilityElement" )
@org.hibernate.annotations.Table( appliesTo = "serviceabilityElement", 
   indexes = { @Index( name = "SERVICEABILITYELEMENT_IX1", columnNames = { "provider_id", "postalcoderange_id" } ) } )
public class ServiceabilityElementBean extends CapabilitiesRecord implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4119077687355510626L;

	@Id
    @GeneratedValue( generator = "serviceabiltyElementBeanSequence" )
    @SequenceGenerator( name = "serviceabiltyElementBeanSequence", sequenceName = "SRV_ELEMENT_BEAN_SEQ" )
    @Index( name = "SERVICEABILITYELEMENT_IX2", columnNames = { "id" }  )
    private long id;

    @OneToOne
    @NotNull
    @ForeignKey( name = "SERVICEABILITY_ELMT_FK01" )
    private BusinessParty provider;

    @OneToOne
    @NotNull
    @ForeignKey( name = "SERVICEABILITY_ELMT_FK02" )
    private PostalCodeRangeBean postalCodeRange;

    @Override
    public long getId()
    {
        return id;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    public BusinessParty getProvider()
    {
        return provider;
    }

    public void setProvider( final BusinessParty provider )
    {
        this.provider = provider;
    }

    public PostalCodeRangeBean getPostalCodeRange()
    {
        return postalCodeRange;
    }

    public void setPostalCodeRange( final PostalCodeRangeBean postalCodeRange )
    {
        this.postalCodeRange = postalCodeRange;
    }

}
