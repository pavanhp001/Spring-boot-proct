package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;
/**
 * 
 * @author ebaugh
 * 
 */

@Entity
@Table( name = "CM_CONTACT_CHANNEL" )
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = "channelType", discriminatorType = DiscriminatorType.STRING )
public abstract class ContactChannel implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3970701814596463800L;
	
	private String value;
    private String description;
    private int preferenceOrder;
    
    public Long getConsumerExternalId() {
		return consumerExternalId;
	}

	public void setConsumerExternalId(Long consumerExternalId) {
		this.consumerExternalId = consumerExternalId;
	}

	@Column(name = "CONSUMER_EXTERNAL_ID")
    private Long consumerExternalId;

    @Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "contactChannelBeanSequence" )
    @SequenceGenerator( name = "contactChannelBeanSequence", sequenceName = "CM_CONTACT_CHANNEL_BEAN_SEQ",allocationSize = 1)
    private long id;

    /**
    *
    */
    public ContactChannel()
    {
    }

    /**
     * Default full constructor.
     * @param value value to set.
     * @param description description to set.
     * @param preferenceOrder preferenceOrder to set.
     */
    public ContactChannel( final String value, final String description, final int preferenceOrder )
    {
        this.value = value;
        this.description = description;
        this.preferenceOrder = preferenceOrder;
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

    /**
     * @return the value
     */
    public final String getValue()
    {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public final void setValue( final String value )
    {
        this.value = value;
    }

    /**
     * @return the description
     */
    public final String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public final void setDescription( final String description )
    {
        this.description = description;
    }

    /**
     * @return the preferenceOrder
     */
    public final int getPreferenceOrder()
    {
        return preferenceOrder;
    }

    /**
     * @param preferenceOrder
     *            the preferenceOrder to set
     */
    public final void setPreferenceOrder( final int preferenceOrder )
    {
        this.preferenceOrder = preferenceOrder;
    }

}
