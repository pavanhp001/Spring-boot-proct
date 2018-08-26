package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * A 'tag' used to classify, or help search for, any entity that it is attached to.
 * These values are relatively free-form and are not restricted.
 * This could be used for operational searching/hints, and it is certainly intended to
 * aid the maintenance data by making elements easier to find and reuse.
 * 
 * The optional "value" member makes this able to be used as a name/value pair as well.
 * 
 * @author rchapple
 *
 */
@Entity
@Table( name = "TAGS" )
public class TagBean implements CommonBeanInterface 
{


    /**
	 * 
	 */
	private static final long serialVersionUID = -6463344405028099721L;

	@Id
    @GeneratedValue( generator = "tagBeanSequence" )
    @SequenceGenerator( name = "tagBeanSequence", sequenceName = "TAG_BEAN_SEQ" )
	private long id;
    
    /**
     * Just the name of this tag.  There shouldn't be any restrictions on 
     * what the name of the tag could be. 
     */
    private String name;
    
    /**
     * A short description of what the tag is for and what it is meant to mean. 
     */
    private String description;
    
    /**
     * A short description of what the tag is for and what it is meant to mean. 
     */
    private String value;
    
	
	@Override
	public long getId() 
	{
		return id;
	}

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

	public String getDescription()
	{
		return description;
	}

	public void setDescription( final String description )
	{
		this.description = description;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue( final String value )
	{
		this.value = value;
	}

	
}
