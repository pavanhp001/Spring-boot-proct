package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 *
 */

@Entity
@Table( name = "descriptiveInfo" )
public class DescriptiveInfoBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8355787533972382705L;

	private static final int MAX_DESCRIPTION_LENGTH = 3000;
    
    @Id
    @GeneratedValue( generator = "descriptiveInfoBeanSequence" )
    @SequenceGenerator( name = "descriptiveInfoBeanSequence", sequenceName = "DESCRIPTIVE_INFO_BEAN_SEQ" )
    private long id;

    private String type;
    @Column ( length = MAX_DESCRIPTION_LENGTH )
    private String description;
    private Boolean display;
    private String label;

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

    public final void setType( final String type )
    {
        this.type = type;
    }

    public final String getDescription()
    {
        return description;
    }

    public final void setDescription( final String description )
    {
        this.description = description;
    }

    public Boolean getDisplay() 
    {
        return display;
    }

    public void setDisplay( final Boolean display ) 
    {
        this.display = display;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel( final String label )
    {
        this.label = label;
    }


}
