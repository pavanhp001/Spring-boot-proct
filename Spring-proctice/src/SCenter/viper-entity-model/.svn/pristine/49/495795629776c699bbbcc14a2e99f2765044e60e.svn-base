package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "marketingHighlight" )
public class MarketingHighlightBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4071196551847570332L;

	@Id
    @GeneratedValue( generator = "marketingHighlightBeanSequence" )
    @SequenceGenerator( name = "marketingHighlightBeanSequence", sequenceName = "QUESTION_BEAN_SEQ" )
    private long id;

    private String text;

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

    public String getText() 
    {
        return text;
    }

    public void setText( final String text ) 
    {
        this.text = text;
    }
}
