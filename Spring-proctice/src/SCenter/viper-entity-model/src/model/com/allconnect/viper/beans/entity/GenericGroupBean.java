package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * 
 * @author ebaugh
 *
 */
@Entity
@Table( name = "genericGroup" )
public class GenericGroupBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7058309298007570987L;

	@Id
    @GeneratedValue( generator = "genericGroupBeanSequence" )
    @SequenceGenerator( name = "genericGroupBeanSequence", sequenceName = "GEN_GROUP_BEAN_SEQ" )
    private long id;
    
    private String name;
    
    /**
     * Generic constructor for the GenericGroupBean class.
     */
    public GenericGroupBean()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId( final long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }
    
}
